package com.plj.service.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.plj.common.tools.mybatis.bean.Condition;
import com.plj.common.tools.mybatis.bean.Field;
import com.plj.common.tools.mybatis.bean.Order;
import com.plj.common.tools.mybatis.bean.Record;
import com.plj.common.tools.mybatis.page.bean.Pagination;

public interface CommonService 
{
	public int insertOne(String tableName, List<Field<?>> fields);
	
	public int insertList(String tableName, List<Record> records);
	
	public int updateByPrimaryKey(String tableName, Field<?> key, List<Field<?>> fields);
	
	public Map<String, ?> selectOne(String tableName
			, ArrayList<Condition<?>> conditions, ArrayList<Order> orders);
	
	public List<Map<String, ?>> selectList(String tableName, ArrayList<Condition<?>> conditions
			, ArrayList<Order> orders, Pagination pagination);
	
	public int deleteByPrimaryKey(String tableName, String field, Object b);
	
	public int deleteByPrimaryKeys(String tableName, String field, List<?> bs);
}
