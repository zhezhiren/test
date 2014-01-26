package com.plj.dao.common;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface CommonDao
{
	public int insert(Map<String, ?> map);
	
	public int insertList(Map<String, ?> map);
	
	public int updateByPrimaryKey(Map<String, ?> map);
	
	public Map<String, ?> selectOne(Map<String, ?> map);
	
	public List<Map<String, ?>> selectList(Map<String, ?> map);
	
	public int deleteByPrimaryKey(Map<String, ?> map);
	
	public int deleteByPrimaryKeys(Map<String, ?> map);
	
}
