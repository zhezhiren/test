package com.plj.domain.base.sys;

import java.io.Serializable;

/**
 * 表ac_operatorrole映射对象
 * @author zhengxing
 * @version 1.0
 * @date 2013/2/20
 */
public class BaseConOperatorRole implements Serializable
{
	private static final long serialVersionUID = -5397020910108670977L;
	
	private Integer operatorId;
	private Integer roleId;
	private String auth;
	
	public Integer getOperatorId() {
		return operatorId;
	}
	
	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}
	
	public Integer getRoleId() {
		return roleId;
	}
	
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
	public String getAuth() {
		return auth;
	}
	
	public void setAuth(String auth) {
		this.auth = auth;
	}
}
