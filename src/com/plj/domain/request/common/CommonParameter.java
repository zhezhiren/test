package com.plj.domain.request.common;

import java.io.Serializable;

import com.plj.service.common.CommonService;

public class CommonParameter implements Serializable
{
	private static final long serialVersionUID = -5433426781696743161L;
	
	private String statement;
	
	private String excuteType;

	public String getStatement()
	{
		return statement;
	}

	public void setStatement(String statement)
	{
		this.statement = statement;
	}
	
	public String getExcuteType()
	{
		return excuteType;
	}

	public void setExcuteType(String excuteType)
	{
		this.excuteType = excuteType;
	}

}

enum ExcuteType
{
	selectOne 
	{
		@Override
		public void excute(CommonService service) 
		{
			
			
		}
	},
	selectlist 
	{
		@Override
		public void excute(CommonService service)
		{
			
		}
	},
	insertOne
	{
		@Override
		public void excute(CommonService service)
		{
			
		}
	},
	insertList 
	{
		@Override
		public void excute(CommonService service) 
		{
			
		}
	},
	deleteOne
	{
		@Override
		public void excute(CommonService service) {
			
		}
	},
	deleteList
	{
		@Override
		public void excute(CommonService service) 
		{
			
		}
	};
	
	public abstract void excute(CommonService service);
	
	
}
