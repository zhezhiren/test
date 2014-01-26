package com.plj.domain.request.common;

import java.io.Serializable;
import java.util.List;

public class CommonDeleteByIds implements Serializable
{
	private static final long serialVersionUID = 2831984212592778977L;
	
	private String tableName;
	
	private String field;
	
	private List<Long> ids;
	
	public String getTableName()
	{
		return tableName;
	}
	
	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}
	
	public String getField() 
	{
		return field;
	}
	
	public void setField(String field)
	{
		this.field = field;
	}
	
	public List<Long> getIds()
	{
		return ids;
	}
	
	public void setIds(List<Long> ids)
	{
		this.ids = ids;
	}
}
