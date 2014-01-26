package com.plj.domain.base.sys;

import java.io.Serializable;

/**
 * 本对象暂时未启用
 * @author zhengxing
 * @version 1.0
 * @date 2013/2/20
 */
public class BaseUser implements Serializable
{
	
	private static final long serialVersionUID = -921346144742392639L;
	
	private String id;
	
	private String email;
	
	private String loginName;
	
	private String name;
	
	private String password;
	
	private String departId;
	
	private Integer roleId;
	
	public BaseUser()
	{
		
	}
	
	public BaseUser(String id, String email, String loginName, String name,
			String password, String departId, Integer roleId) {
		super();
		this.id = id;
		this.email = email;
		this.loginName = loginName;
		this.name = name;
		this.password = password;
		this.departId = departId;
		this.roleId = roleId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDepartId() {
		return departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Override
	public String toString() {
		return "BaseUser [id=" + id + ", email=" + email + ", loginName="
				+ loginName + ", name=" + name + ", password=" + password
				+ ", departId=" + departId + ", roleId=" + roleId + "]";
	}

}
