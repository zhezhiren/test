package com.plj.service.common.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.plj.common.tools.mybatis.bean.Condition;
import com.plj.common.tools.mybatis.bean.Field;
import com.plj.common.tools.mybatis.bean.Order;
import com.plj.common.tools.mybatis.bean.Record;
import com.plj.common.tools.mybatis.page.bean.Pagination;
import com.plj.dao.common.CommonDao;
import com.plj.service.common.CommonService;

@Service("commonService")
public class CommonServiceImpl implements CommonService
{
	@Autowired
	CommonDao commonDao;
	
	@Autowired
	@Qualifier("batchSqlSession")
	SqlSessionTemplate batchSession;
	
	@Override
	public int insertOne(String tableName, List<Field<?>> fields)
	{
		int result = 0;
		if(null != tableName && !"".equals(tableName.trim())
				&& null!= fields && fields.size() > 0)
		{
			HashMap<String, Object> map = new HashMap<String, Object>(2);
			map.put("table", tableName);
			map.put("fields", fields);
			result = commonDao.insert(map); 
		}
		return result;
	}
	
	@Override
	public int insertList(String tableName, List<Record> records)//TODO
	{
		int result = 0;
		if(null != records)
		{
			result = batchSession.insert(tableName, records); 
		}
		return result;
	}
	
	@Override
	public int updateByPrimaryKey(String tableName, Field<?> key, List<Field<?>> fields)
	{
		int result = 0; 
		if(null != tableName && !"".equals(tableName.trim()) && null != key
				&& null!= fields && fields.size() > 0)
		{
			HashMap<String, Object> map = new HashMap<String, Object>(3);
			map.put("table", tableName);
			map.put("key", key);
			map.put("fields", fields);
			result = commonDao.updateByPrimaryKey(map);
		}
		return result;
	}
	
	@Override
	public Map<String, ?> selectOne(String tableName
			, ArrayList<Condition<?>> conditions, ArrayList<Order> orders)
	{
		if(null != tableName && !"".equals(tableName.trim()) 
				&& null != conditions && conditions.size() > 0)
		{
			HashMap<String, Object> map = new HashMap<String, Object>(2);
			map.put("table", tableName);
			map.put("conditions", conditions);
			if(orders != null && orders.size() > 0)
			{
				map.put("orders", orders);
			}
			Map<String, ?> t = commonDao.selectOne(map);
			return t;
		}else
		{
			throw new RuntimeException();
		}
	}
	
	@Override
	public List<Map<String, ?>> selectList(String tableName, ArrayList<Condition<?>> conditions
			, ArrayList<Order> orders, Pagination pagination)
	{
		testAop();
		if(null != tableName && !"".equals(tableName.trim()))
		{
			HashMap<String, Object> map = new HashMap<String, Object>(2);
			map.put("table", tableName);
			map.put("conditions", conditions);
			if(orders != null && orders.size() > 0)
			{
				map.put("orders", orders);
			}
			if(null != pagination)
			{
				map.put("pagination", pagination);
			}
			List<Map<String, ?>> t = commonDao.selectList(map);
			return t;
		}else
		{
			throw new RuntimeException();
		}
		
	}
	
	public void testAop()
	{
		System.out.println("test in target Object");
	}
	
	@Override
	public int deleteByPrimaryKey(String tableName, String field, Object b)
	{
		int result = 0;
		if(null != tableName && !"".equals(tableName.trim())
				&& null != field && !"".equals(field) && null != b)
		{
			HashMap<String, Object> map = new HashMap<String, Object>(3);
			map.put("table", tableName);
			map.put("field", field);
			map.put("value", b);
			result = commonDao.deleteByPrimaryKey(map); 
		}
		return result;
	}
	
	@Override
	public int deleteByPrimaryKeys(String tableName, String field, List<?> bs)
	{
		int result = 0;
		if(null != tableName && !"".equals(tableName.trim()) && null != field 
				&& !"".equals(field) && null != bs && bs.size() > 0)
		{
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("table", tableName);
			map.put("field", field);
			map.put("values", bs);
			result = commonDao.deleteByPrimaryKeys(map); 
		}
		return result;
	}
}
