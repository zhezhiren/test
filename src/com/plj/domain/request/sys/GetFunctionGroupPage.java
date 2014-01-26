package com.plj.domain.request.sys;

import com.plj.common.tools.mybatis.page.bean.Pagination;

public class GetFunctionGroupPage extends Pagination
{
	private static final long serialVersionUID = -8800854198168925206L;
	
	private String appId;
	
	private String parentId;

	public String getAppId() 
	{
		return appId;
	}

	public void setAppId(String appId) 
	{
		this.appId = appId;
	}

	public String getParentId() 
	{
		return parentId;
	}

	public void setParentId(String parentId) 
	{
		this.parentId = parentId;
	}
}
