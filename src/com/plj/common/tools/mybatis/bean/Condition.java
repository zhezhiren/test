package com.plj.common.tools.mybatis.bean;

import com.plj.common.constants.Constants;

public class Condition<T>
{
	private Connector connector;
	
	private String fieldName;
	
	private Sign sign;
	
	private T fieldValue;
	
	public Condition(Connector connector)
	{
		setConnector(connector);
	}
	
	public Condition(String fieldName, Sign sign, T fieldValue)
	{
		setFieldName(fieldName);
		setFieldValue(fieldValue);
		setSign(sign);
	}

	public String getConnector()
	{
		if(connector != null)
		{
			return connector.value;
		}
		return null;
	}

	private void setConnector(Connector connector)
	{
		if(null == connector)
		{
			throw new RuntimeException(Constants.CONNECTOR_CANNOT_BENULL);//TODO
		}
		this.connector = connector;
	}
	
	public String getFieldName()
	{
		return fieldName;
	}

	private void setFieldName(String fieldName) 
	{
		if(null == fieldName || "".equals(fieldName.trim()))
		{
			throw new RuntimeException(Constants.FIELD_CANNOT_BENULL);
		}
		this.fieldName = fieldName;
	}

	public String getSign() 
	{
		return sign.value;
	}
	
	public String getXmlSign()
	{
		if(sign != null)
		{
			return sign.xmlValue;
		}
		return null;
	}

	private void setSign(Sign sign)
	{
		if(sign != null)
		{
			this.sign = sign;
		}else
		{
			throw new RuntimeException("");
		}
	}
	
	public T getFieldValue()
	{
		return fieldValue;
	}

	public void setFieldValue(T fieldValue) 
	{
		this.fieldValue = fieldValue;
	}
	
	public enum Sign 
	{
		greaterThan(">", ">"),
		lessThan("<", "<![CDATA[ < ]]>"),
		greaterEqual(">=", ">="),
		lessEqual("<=", "<![CDATA[ <= ]]>"),
		equal("=", "="),
		like("like", "like"),
		;
		
		private String value;
		
		private String xmlValue;
		
		Sign(String value, String xmlValue)
		{
			this.value = value;
			this.xmlValue = xmlValue;
		}
	}
	
	public enum Connector
	{
		AND(" AND "),
		OR(" OR "),
		openParenthesis("("),
		closeParenthesis(")"),
		;
		
		private String value;
		
		Connector(String value)
		{
			this.value = value;
		}
	}
}
