package com.plj.domain.request.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.plj.common.error.MyError;
import com.plj.common.result.JsonResult;

public class DeleteByStringIds implements Serializable
{
	private static final long serialVersionUID = 3555219793596672904L;
	
	private List<String> ids;

	public List<String> getIds() 
	{
		return ids;
	}

	public void setIds(List<String> ids) 
	{
		this.ids = ids;
	}
	
	public JsonResult checkParamToInteger()
	{
		JsonResult result = checkParameters();
		if(result.isSuccess())
		{
			ArrayList<Integer> intIds = new ArrayList<Integer>();
			result.setData(intIds);
			for(int i = 0; i < ids.size(); i++)
			{
				try
				{
					intIds.add(Integer.parseInt(ids.get(i)));
				}catch (Exception e) 
				{
					result.addError(new MyError(MyError.CommonCode.DELETE_PARAM_ERROR, MyError.CommonMsg.DELETE_PARAM_ERROR));
					result.setData(null);
				}
			}
			
		}
		return result;
	}
	
	public JsonResult checkParameters()
	{
		JsonResult result = new JsonResult();
		
		if(null == ids ||ids.size() <= 0)
		{
			result.addError(new MyError(MyError.CommonCode.DELETE_IDS_NULL, MyError.CommonMsg.DELETE_IDS_NULL));
		}
		return result;
	}
}
