package com.plj.domain.base.sys;

import java.io.Serializable;

/**
 * 表ac_rolefunc映射对象
 * @author zhengxing
 * @version 1.0
 * @date 2013/2/20
 */
public class BaseConRoleFunction implements Serializable
{
	private static final long serialVersionUID = -980129119419854122L;
	
	private Integer roleId;
	
	private Integer functionCode;
	
	private Integer applicationCode;
	
	private Integer functionGroupId;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getFunctionCode() {
		return functionCode;
	}

	public void setFunctionCode(Integer functionCode) {
		this.functionCode = functionCode;
	}

	public Integer getApplicationCode() {
		return applicationCode;
	}

	public void setApplicationCode(Integer applicationCode) {
		this.applicationCode = applicationCode;
	}

	public Integer getFunctionGroupId() {
		return functionGroupId;
	}

	public void setFunctionGroupId(Integer functionGroupId) {
		this.functionGroupId = functionGroupId;
	}
	
}
