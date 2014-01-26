package com.plj.common.tools.mybatis.bean;

import java.util.List;

public class Record
{
	private List<Field<?>> fields;

	public List<Field<?>> getFields()
	{
		return fields;
	}

	public void setFields(List<Field<?>> fields)
	{
		this.fields = fields;
	}
	
	public int fieldSize()
	{
		if(null == fields)
		{
			return 0;
		}else
		{
			return fields.size();
		}
	}
	
	
}
