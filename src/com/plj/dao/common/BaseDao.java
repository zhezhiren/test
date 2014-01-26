package com.plj.dao.common;

import java.util.List;
import java.util.Map;

public interface BaseDao<T>
{
	public int insert(T param);
	
	public int insertList(List<T> param	);
	
	public int updateByPrimartKey(T param);
	
	public int updateByPrimartKeySelective(T param);
	
	public int updateByExampleSelective(Map<String, T> param);
	
	public T selectByPrimaryKey(Object value); 
	
	public List<T> selectList(Map<String, ? extends Object> param);
	
	public int deleteByPrimaryKeys(List<? extends Object> keys);
	
	public int deleteByKeys(Map<String, ? extends Object> params);
}
