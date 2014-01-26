package com.plj.domain.request.sys;

import com.plj.common.tools.mybatis.page.bean.Pagination;

public class GetOperatorsPage extends Pagination 
{
	private static final long serialVersionUID = 9008550532024095100L;
	
	private String userId;
	
	private String operatorName;

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getOperatorName() 
	{
		return operatorName;
	}

	public void setOperatorName(String operatorName)
	{
		this.operatorName = operatorName;
	}
}
