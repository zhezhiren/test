package com.plj.service.common.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.plj.dao.common.BaseDao;
import com.plj.service.common.BaseService;

public abstract class BaseServiceImpl<T, D extends BaseDao<T>> implements BaseService<T, D>
{
	@Autowired
	@Qualifier("simpleSqlSession")
	SqlSessionTemplate session;
	
	@Autowired
	@Qualifier("batchSqlSession")
	SqlSessionTemplate batchSession;
	
	/**
	 * 
	 * @return ���ر�service ��Ҫ���õ�Dao�� ���dao������ӦT�������ı��������dao�ķ���ʵ��ΪT����
	 * ���磬�����Ӧac_role�����صĶ���Ϊrole��
	 * ��ôgetDao�ͷ���RoleDao��
	 * RoleDao��Ӧ�ı�Ϊac_role, 
	 * 
	 */
	protected abstract String getDaoNameSpace();
	
	@Override
	public int insert(T param)
	{
		int result = 0;
		if(null != param)
		{
			String statement = getDaoNameSpace() + "insert";
			result = session.insert(statement, param);
		}
		return result;
	}
	
	@Override
	public int insertList(List<T> param)
	{
		return 0;
	}
	
	@Override
	public int updateByPrimartKey(T param)
	{
		int result = 0;
		if(null != param)
		{
			String statement = getDaoNameSpace() + "updateByPrimartKey";
			result = session.update(statement, param);
		}
		return result;
	}
	
	@Override
	public int updateByPrimartKeySelective(T param)
	{
		int result = 0;
		if(null != param)
		{
			String statement = getDaoNameSpace() + "updateByPrimartKeySelective";
			result = session.update(statement, param);
		}
		return result;
	}
	
	@Override
	public int updateByExampleSelective(T value, T param)
	{
		int result = 0;
		if(null != param && value != null)
		{
			HashMap<String, T> map = new HashMap<String, T>(2);
			map.put("param", param);
			map.put("value", value);
			String statement = getDaoNameSpace() + "updateByExampleSelective";
			result = session.update(statement, map);
		}
		return result;
	}
	
	@Override
	public T selectByPrimaryKey(Object value)
	{
		T result = null;
		if(value != null)
		{
			String statement = getDaoNameSpace() + "selectByPrimaryKey";
			result = session.selectOne(statement, value);
		}
		return result;
	}
	
	@Override
	public List<T> selectList(Map<String, ? extends Object> param)
	{
		String statement = getDaoNameSpace() + "selectList";
		List<T> result = session.selectList(statement, param);
		return result;
	}
	
	@Override
	public int deleteByPrimaryKeys(List<? extends Object> keys)
	{
		int result = 0;
		if(keys != null && keys.size() > 0)
		{
			String statement = getDaoNameSpace() + "deleteByPrimaryKeys";
			result = session.delete(statement, keys);
		}
		return result;
	}
	
	@Override
	public int deleteByKeys(Map<String, ? extends Object> params)
	{
		int result = 0;
		if(params != null && params.keySet() != null && params.keySet().size() > 0)
		{
			String statement = getDaoNameSpace() + "deleteByKeys";
			result = session.delete(statement, params);
		}
		return result;
	}
}
