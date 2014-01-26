package com.plj.domain.request.sys;

import java.io.Serializable;
import java.util.List;

public class DeleteRole implements Serializable
{
	private static final long serialVersionUID = -5041245455342070512L;
	
	private List<String> roleIds;

	public List<String> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}
	
}
