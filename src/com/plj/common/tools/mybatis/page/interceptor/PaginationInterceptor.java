package com.plj.common.tools.mybatis.page.interceptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.builder.xml.dynamic.ForEachSqlNode;
import org.apache.ibatis.executor.ErrorContext; 
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.plj.common.tools.mybatis.page.bean.PageContext;
import com.plj.common.tools.mybatis.page.bean.Pagination;
import com.plj.common.tools.mybatis.page.dialect.Dialect;
import com.plj.common.tools.mybatis.page.tool.ReflectHelper;
import com.plj.common.tools.mybatis.page.tool.SQLHelper;

/**
 * ������һ��mybatis�����������ȡ��ѯ����������ѯ�������ݷ�ҳ������ӷ�ҳ��Ϣ��
 * @author zhengxing
 * @see Dialect;Plugin;Invocation
 * @version 1.0
 */
@Intercepts({@Signature(type=Executor.class, method="query"
		,args={MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class PaginationInterceptor implements Interceptor
{
	private final static Logger log = LoggerFactory.getLogger(PaginationInterceptor.class);
	
	private Dialect dialect;	
	
	public Dialect getDialect() {
		return dialect;
	}

	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
	}

	public Object intercept(Invocation invocation) throws Throwable 
	{
		
		//��ȡsql��mappedStatement ����������Ϣ�� sql��Ϣ
		MappedStatement mappedStatement = (MappedStatement)invocation.getArgs()[0];
		
		//��ȡsql����ִ�в�����
		Object parameter = invocation.getArgs()[1];
		
		//��ȡsqlbound��������ȡsql��䡣
		BoundSql boundSql = mappedStatement.getBoundSql(parameter);
		
		//ԭ��ִ�е�sql���
		String originalSql = boundSql.getSql();
		
		//����Ϣ
		RowBounds rowBounds = (RowBounds)invocation.getArgs()[2];
		
		//��ȡ������Ϣ����parameterͬ��
		Object parameterObject = boundSql.getParameterObject();
		
		//sqlΪ�գ�����
		if(boundSql==null || boundSql.getSql()==null || "".equals(boundSql.getSql().trim()))
		{
			return null;
		}
		
		//��ҳ��Ϣ����sql�������������������˷�ҳ��Ϣʱ������PageContext������
		Pagination pagination = null;
		
		//������Ϊ�գ����Ի�ȡ��ҳ��Ϣ��
		if(parameterObject!=null)
		{
			pagination = (Pagination)ReflectHelper.isPage(parameterObject,"pagination");
		}
		
		//�����ҳ������û�и�����ҳ�����������ҳ��PageContext���ã�(Ĭ��Ϊ����Ҫ���з�ҳ)��
		if(pagination == null && PageContext.getIsNeedPagination())
        {  
			pagination = PageContext.getContext();;  
        } 
		
		//�з�ҳ��Ϣ����д���û��ҳ��Ϣֱ�ӷ��ؽ����
		if(pagination != null)
		{
			//����ҳ���ܼ�¼��Ϊ0ʱ����ѯ�ܼ�¼����
			long totCount= pagination.getTotalCount();
			
			if(totCount == 0 && pagination.isReadTotalCount() == true )
			{
				//StringBuffer countSql = new StringBuffer(originalSql.length() + 50);//��ѯ��¼����sql
				//countSql.append("select count(*) from (").append(originalSql).append(") t");
				String countSqlString = SQLHelper.getCountString(originalSql);
				//String countSqlString = SQLHelper.getLineSql(countSql.toString());
				if(log.isDebugEnabled())
				{
					StringBuilder sb = new StringBuilder();
					sb.append("Executing: ").append(countSqlString);
					log.debug(sb.toString());
				}
				//��ѯ��¼����connection
				Connection connection = mappedStatement.getConfiguration().getEnvironment()
						.getDataSource().getConnection();
				
				//��ѯ��¼����statement��ִ�� ��ѯ��¼����sql
				PreparedStatement countStmt = connection.prepareStatement(countSqlString);
				
				//��¼��ѯ��������BoundSql
				BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), 
						countSqlString,boundSql.getParameterMappings(), parameterObject);
				
				//����AdditionalParameter��������copy������ûcopy�ò���������AdditionalParameter��ʧ�����¸�ֵ�����򲿷ֲ�ѯ������ʧΪ�ա�
				for (ParameterMapping mapping : boundSql.getParameterMappings()) {
	                String prop = mapping.getProperty();
	                if (boundSql.hasAdditionalParameter(prop)) {
	                	countBS.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
	                }
	            }
				
				//����BoundSql�Ĳ���ֵ�����͡�
				setParameters(countStmt, mappedStatement, countBS, parameterObject);
				
				//��ȡ��ѯ���
				ResultSet rs = countStmt.executeQuery();
				if(rs.next())
				{
					totCount = rs.getLong(1);
					pagination.setTotalCount(totCount);
				}
				

				
				rs.close();   
				countStmt.close();
                connection.close();
			}
			
			//��pagination�����ݺ����Խ����жϴ���
			checkPagination(pagination);
			
			//�����ѯ��¼��
			if(rowBounds == null || rowBounds == RowBounds.DEFAULT)
			{
				/*rowBounds = new RowBounds(pagination.getPageSize() * (pagination.getPageNo() - 1)
						, pagination.getPageSize());*/
				rowBounds = new RowBounds(pagination.getStart()
						, pagination.getPageSize());
			}
			
			//��ȡ����ָ�����ݿ�� ��ѯ����
			String pageSql = dialect.getLimitString(originalSql, rowBounds.getOffset()
					, rowBounds.getLimit());
			
			//���ò�ѯ����
			invocation.getArgs()[2] = new RowBounds(RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT); 
			BoundSql newBoundSql = new BoundSql(mappedStatement.getConfiguration()
					, pageSql,boundSql.getParameterMappings(),boundSql.getParameterObject()); 
			
			//����AdditionalParameter��������copy������ûcopy�ò���������AdditionalParameter��ʧ�����¸�ֵ�����򲿷ֲ�ѯ������ʧΪ�ա�
			for (ParameterMapping mapping : boundSql.getParameterMappings()) {
                String prop = mapping.getProperty();
                if (boundSql.hasAdditionalParameter(prop)) {
                	newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
                }
            }
			//�޸Ĳ�����
			MappedStatement newMs  = copyFromMappedStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql));
			invocation.getArgs()[0] = newMs;
		}
		
		//����ԭ������
		return invocation.proceed();
	}

	public Object plugin(Object target) 
	{
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) 
	{
		//dialect = (Dialect) properties.get("dialect");
	}
	
	/**
	 * ��ҳ�����ݵĺ����Խ����жϴ���
	 * @param pagination
	 */
	public void checkPagination(Pagination pagination)
	{	
		int numPerpage = pagination.getPageSize();
		//ÿҳ������С�ڵ���2������Ϊ20
		if(numPerpage < 2)
		{
			pagination.setPageSize(20);
		}
		
		//��ҳ��С��1ʱ������Ϊ1
		if(pagination.getPageNo() < 1)
		{
			pagination.setPageNo(1);
		}
	}
	
	/**
	 * �������в���
	 * @param ps
	 * @param mappedStatement
	 * @param boundSql
	 * @param parameterObject
	 * @throws SQLException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, 
			BoundSql boundSql, Object parameterObject) throws SQLException
	{
		
		ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
		
		//��ȡParameterMapping
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		if(parameterMappings != null)
		{
			//��ȡ������Ϣ
			Configuration configuration = mappedStatement.getConfiguration();
			
			//��ȡһ���µ�TypeHandlerRegistry
			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
			
			//������Ԫ����ӳ��
			MetaObject metaObject = parameterObject == null ? null : configuration.newMetaObject(parameterObject);
			
			//���ò�����
			for(int i = 0; i < parameterMappings.size(); i++)
			{
				ParameterMapping parameterMapping = parameterMappings.get(i);
				if(parameterMapping.getMode() != ParameterMode.OUT)
				{
					Object value;
					String propertyName = parameterMapping.getProperty();
					
					PropertyTokenizer prop = new PropertyTokenizer(propertyName);
					//��ȡ����ֵ
					if(parameterObject == null)
					{
						value = null;
					}
					else if(typeHandlerRegistry.hasTypeHandler(parameterObject.getClass()))
					{
						value = parameterObject;
					}
					else if(boundSql.hasAdditionalParameter(propertyName))
					{
						value = boundSql.getAdditionalParameter(propertyName);
					}
					else if(propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX) && 
							boundSql.hasAdditionalParameter(prop.getName()))
					{
						value = boundSql.getAdditionalParameter(prop.getName());
						if(value != null)
						{
							value = configuration.newMetaObject(value).
								getValue(propertyName.substring(prop.getName().length()));
						}
					}
					else
					{
						value = metaObject == null ? null : metaObject.getValue(propertyName);
					}
					
					//��ȡtypeHandler,Ϊ�����쳣
					TypeHandler typeHandler = parameterMapping.getTypeHandler();
					if(typeHandler == null)
					{
						throw new ExecutorException("There was no TypeHandler found for parameter "
								+ propertyName + " of statement "+ mappedStatement.getId()); 
					}
					
					//���ò���ֵ
					typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
				}
			}
		}
	}
	
	/**
	 * ��ȡһ���µ�MappedStatement
	 * @param ms
	 * @param newSqlSource
	 * @return
	 */
	private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource)
	{
		Builder builder = new MappedStatement.Builder(ms.getConfiguration()
				, ms.getId(), newSqlSource, ms.getSqlCommandType());
		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());
		builder.keyProperty(ms.getKeyProperty());
		builder.timeout(ms.getTimeout());
		builder.parameterMap(ms.getParameterMap());
		builder.resultMaps(ms.getResultMaps());
		builder.cache(ms.getCache());
		builder.resultSetType( ms.getResultSetType());
		builder.useCache(ms.isUseCache());
		MappedStatement newMs = builder.build();
		return newMs;
		
		
	}
	
	public static class BoundSqlSqlSource implements SqlSource 
	{  
        BoundSql boundSql;  
  
        public BoundSqlSqlSource(BoundSql boundSql) 
        {  
            this.boundSql = boundSql;  
        }  
  
        public BoundSql getBoundSql(Object parameterObject) 
        {  
            return boundSql;  
        }  
    }  
	
}
