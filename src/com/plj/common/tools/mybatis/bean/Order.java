package com.plj.common.tools.mybatis.bean;

import com.plj.common.constants.Constants;

public class Order 
{
	private String fieldName;
	
	private OrderDir orderDir;
	
	public Order(String field, OrderDir orderDir)
	{
		setFieldName(field);
		setOrderDir(orderDir);
	}

	public String getFieldName()
	{
		return fieldName;
	}

	public void setFieldName(String fieldName)
	{
		if(fieldName == null)
		{
			throw new RuntimeException(Constants.ORDER_FIELD_CANNOT_BENULL);//TODO
		}
		this.fieldName = fieldName;
	}

	public String getOrderDir()
	{
		if(null != orderDir)
		{
			return orderDir.value;
		}else
		{
			return null;
		}
	}

	public void setOrderDir(OrderDir orderDir)
	{
		if(orderDir == null)
		{
			orderDir = OrderDir.ASC;
		}
		this.orderDir = orderDir;
	}

	public enum OrderDir
	{
		ASC("ASC"),
		DESC("DESC");
		
		private String value;
		
		OrderDir(String value)
		{
			this.value = value;
		}
	}
}
