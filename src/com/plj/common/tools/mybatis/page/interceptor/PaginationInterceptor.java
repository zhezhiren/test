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
 * 本类是一个mybatis插件，用来截取查询方法，给查询方法根据分页参数添加分页信息。
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
		
		//获取sql的mappedStatement 包含连接信息与 sql信息
		MappedStatement mappedStatement = (MappedStatement)invocation.getArgs()[0];
		
		//获取sql语句的执行参数。
		Object parameter = invocation.getArgs()[1];
		
		//获取sqlbound，用来获取sql语句。
		BoundSql boundSql = mappedStatement.getBoundSql(parameter);
		
		//原本执行的sql语句
		String originalSql = boundSql.getSql();
		
		//行信息
		RowBounds rowBounds = (RowBounds)invocation.getArgs()[2];
		
		//获取参数信息，与parameter同。
		Object parameterObject = boundSql.getParameterObject();
		
		//sql为空，返回
		if(boundSql==null || boundSql.getSql()==null || "".equals(boundSql.getSql().trim()))
		{
			return null;
		}
		
		//分页信息，由sql参数附带，当参数带了分页信息时，忽略PageContext的设置
		Pagination pagination = null;
		
		//参数不为空，则尝试获取分页信息。
		if(parameterObject!=null)
		{
			pagination = (Pagination)ReflectHelper.isPage(parameterObject,"pagination");
		}
		
		//处理分页，但是没有附带分页参数情况，分页由PageContext设置，(默认为不需要进行分页)。
		if(pagination == null && PageContext.getIsNeedPagination())
        {  
			pagination = PageContext.getContext();;  
        } 
		
		//有分页信息则进行处理，没分页信息直接返回结果。
		if(pagination != null)
		{
			//当分页中总记录数为0时，查询总记录数。
			long totCount= pagination.getTotalCount();
			
			if(totCount == 0 && pagination.isReadTotalCount() == true )
			{
				//StringBuffer countSql = new StringBuffer(originalSql.length() + 50);//查询记录数的sql
				//countSql.append("select count(*) from (").append(originalSql).append(") t");
				String countSqlString = SQLHelper.getCountString(originalSql);
				//String countSqlString = SQLHelper.getLineSql(countSql.toString());
				if(log.isDebugEnabled())
				{
					StringBuilder sb = new StringBuilder();
					sb.append("Executing: ").append(countSqlString);
					log.debug(sb.toString());
				}
				//查询记录数的connection
				Connection connection = mappedStatement.getConfiguration().getEnvironment()
						.getDataSource().getConnection();
				
				//查询记录数的statement，执行 查询记录数的sql
				PreparedStatement countStmt = connection.prepareStatement(countSqlString);
				
				//记录查询总数量的BoundSql
				BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), 
						countSqlString,boundSql.getParameterMappings(), parameterObject);
				
				//设置AdditionalParameter参数，在copy过程中没copy该参数，导致AdditionalParameter丢失，重新赋值。否则部分查询条件丢失为空。
				for (ParameterMapping mapping : boundSql.getParameterMappings()) {
	                String prop = mapping.getProperty();
	                if (boundSql.hasAdditionalParameter(prop)) {
	                	countBS.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
	                }
	            }
				
				//设置BoundSql的参数值与类型。
				setParameters(countStmt, mappedStatement, countBS, parameterObject);
				
				//获取查询结果
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
			
			//对pagination的数据合理性进行判断处理
			checkPagination(pagination);
			
			//计算查询记录数
			if(rowBounds == null || rowBounds == RowBounds.DEFAULT)
			{
				/*rowBounds = new RowBounds(pagination.getPageSize() * (pagination.getPageNo() - 1)
						, pagination.getPageSize());*/
				rowBounds = new RowBounds(pagination.getStart()
						, pagination.getPageSize());
			}
			
			//获取基于指定数据库的 查询语言
			String pageSql = dialect.getLimitString(originalSql, rowBounds.getOffset()
					, rowBounds.getLimit());
			
			//重置查询条件
			invocation.getArgs()[2] = new RowBounds(RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT); 
			BoundSql newBoundSql = new BoundSql(mappedStatement.getConfiguration()
					, pageSql,boundSql.getParameterMappings(),boundSql.getParameterObject()); 
			
			//设置AdditionalParameter参数，在copy过程中没copy该参数，导致AdditionalParameter丢失，重新赋值。否则部分查询条件丢失为空。
			for (ParameterMapping mapping : boundSql.getParameterMappings()) {
                String prop = mapping.getProperty();
                if (boundSql.hasAdditionalParameter(prop)) {
                	newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
                }
            }
			//修改参数。
			MappedStatement newMs  = copyFromMappedStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql));
			invocation.getArgs()[0] = newMs;
		}
		
		//调用原方法。
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
	 * 对页码数据的合理性进行判断处理
	 * @param pagination
	 */
	public void checkPagination(Pagination pagination)
	{	
		int numPerpage = pagination.getPageSize();
		//每页数据量小于等于2，设置为20
		if(numPerpage < 2)
		{
			pagination.setPageSize(20);
		}
		
		//当页码小于1时，设置为1
		if(pagination.getPageNo() < 1)
		{
			pagination.setPageNo(1);
		}
	}
	
	/**
	 * 设置运行参数
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
		
		//获取ParameterMapping
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		if(parameterMappings != null)
		{
			//获取配置信息
			Configuration configuration = mappedStatement.getConfiguration();
			
			//获取一个新的TypeHandlerRegistry
			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
			
			//参数的元数据映射
			MetaObject metaObject = parameterObject == null ? null : configuration.newMetaObject(parameterObject);
			
			//设置参数至
			for(int i = 0; i < parameterMappings.size(); i++)
			{
				ParameterMapping parameterMapping = parameterMappings.get(i);
				if(parameterMapping.getMode() != ParameterMode.OUT)
				{
					Object value;
					String propertyName = parameterMapping.getProperty();
					
					PropertyTokenizer prop = new PropertyTokenizer(propertyName);
					//获取参数值
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
					
					//获取typeHandler,为空则异常
					TypeHandler typeHandler = parameterMapping.getTypeHandler();
					if(typeHandler == null)
					{
						throw new ExecutorException("There was no TypeHandler found for parameter "
								+ propertyName + " of statement "+ mappedStatement.getId()); 
					}
					
					//设置参数值
					typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
				}
			}
		}
	}
	
	/**
	 * 获取一个新的MappedStatement
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
