package com.plj.domain.request.sys;

import java.io.Serializable;
import java.util.List;

public class AuthorizationBean implements Serializable
{
	private static final long serialVersionUID = -4211347172682219346L;

	private String roleId;
	
	private List<String> functionCodes;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public List<String> getFunctionCodes() {
		return functionCodes;
	}

	public void setFunctionCodes(List<String> functionCodes) {
		this.functionCodes = functionCodes;
	}
	
	
}
