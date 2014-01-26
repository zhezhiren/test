package com.plj.domain.base.sys;

import java.io.Serializable;

/**
 * 表ac_rolesub映射对象
 * @author zhengxing
 * @version 1.0
 * @date 2013/2/20
 */
public class BaseConRoleSub implements Serializable
{
	private static final long serialVersionUID = -6341774437003431482L;
	
	private Integer roleId;
	
	private Integer hadRoleId;
	
	private String isValid;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getHadRoleId() {
		return hadRoleId;
	}

	public void setHadRoleId(Integer hadRoleId) {
		this.hadRoleId = hadRoleId;
	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
}
