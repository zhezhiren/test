package com.plj.domain.bean.sys;

import java.io.Serializable;

public class FunctionGroupBean implements Serializable
{
	private static final long serialVersionUID = -746947354972216447L;

	private String functionGroupId;
	
	private String appId;
	
	private String functionGroupName;
	
	private String parentGroup;

	public String getFunctionGroupId() {
		return functionGroupId;
	}

	public void setFunctionGroupId(String functionGroupId) {
		this.functionGroupId = functionGroupId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getFunctionGroupName() {
		return functionGroupName;
	}

	public void setFunctionGroupName(String functionGroupName) {
		this.functionGroupName = functionGroupName;
	}

	public String getParentGroup() {
		return parentGroup;
	}

	public void setParentGroup(String parentGroup) {
		this.parentGroup = parentGroup;
	}

}
