package com.plj.service.common.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.plj.dao.common.BaseDao;
import com.plj.service.common.BaseService2;

public abstract class BaseServiceImpl2<T, D extends BaseDao<T>> implements BaseService2<T, D>
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
	 * ���磬�������ac_role����Ӧ�Ķ���ΪRole��RoleDaoΪac_role�Ĳ���dao��RoleServiceΪac_role�Ĳ���service��
	 * ��ôRoleService�̳б�����D����RoleDao�� T����Role��
	 * 
	 */
	protected abstract D getDao();
	
	@Override
	public int insert(T param)
	{
		int result = 0;
		if(null != param)
		{
			result = getDao().insert(param);
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
			result = getDao().updateByPrimartKey(param);
		}
		return result;
	}
	
	@Override
	public int updateByPrimartKeySelective(T param)
	{
		int result = 0;
		if(null != param)
		{
			result = getDao().updateByPrimartKeySelective(param);
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
			result = getDao().updateByExampleSelective(map);
		}
		return result;
	}
	
	@Override
	public T selectByPrimaryKey(Object value)
	{
		T result = null;
		if(value != null)
		{
			result = getDao().selectByPrimaryKey(value);
		}
		return result;
	}
	
	@Override
	public List<T> selectList(Map<String, ? extends Object> param)
	{
		List<T> result = getDao().selectList(param);
		return result;
	}
	
	@Override
	public int deleteByPrimaryKeys(List<? extends Object> keys)
	{
		int result = 0;
		if(keys != null && keys.size() > 0)
		{
			result = getDao().deleteByPrimaryKeys(keys);
		}
		return result;
	}
	
	@Override
	public int deleteByKeys(Map<String, ? extends Object> params)
	{
		int result = 0;
		if(params != null && params.keySet() != null && params.keySet().size() > 0)
		{
			result = getDao().deleteByKeys(params);
		}
		return result;
	}
}
