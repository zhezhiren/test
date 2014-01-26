package com.plj.common.tools.mybatis.bean;

import com.plj.common.constants.Constants;

public class Field<T>
{
	private String fieldName;
	
	private T fieldValue;
	
	/**
	 * @param fieldName
	 * @param fieldValue
	 */
	public Field(String fieldName, T fieldValue) 
	{
		setFieldName(fieldName);
		setFieldValue(fieldValue);
	}

	public String getFieldName()
	{
		return fieldName;
	}

	private void setFieldName(String fieldName) 
	{
		if(null == fieldName || "".equals(fieldName.trim()))
		{
			throw new RuntimeException(Constants.FIELD_CANNOT_BENULL);//TODO
		}
		this.fieldName = fieldName;
	}

	public T getFieldValue()
	{
		return fieldValue;
	}

	public void setFieldValue(T fieldValue) 
	{
		this.fieldValue = fieldValue;
	}
	
	
}
