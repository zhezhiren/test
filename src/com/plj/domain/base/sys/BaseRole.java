package com.plj.domain.base.sys;

import java.io.Serializable;

/**
 * 表ac_role映射对象
 * @author zhengxing
 * @version 1.0
 * @date 2013/2/20
 */
public class BaseRole implements Serializable
{
	private static final long serialVersionUID = 8222014321823641708L;
	
	private Integer roleId;
	private String roleName;
	private String roleType;
	private String roleDesc;
	private Integer applicationId;
	
	public Integer getRoleId() {
		return roleId;
	}
	
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
	public String getRoleName() {
		return roleName;
	}
	
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public String getRoleType() {
		return roleType;
	}
	
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	
	public String getRoleDesc() {
		return roleDesc;
	}
	
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	
	public Integer getApplicationId() {
		return applicationId;
	}
	
	public void setApplicationId(Integer applicationId) {
		this.applicationId = applicationId;
	}
	
}
