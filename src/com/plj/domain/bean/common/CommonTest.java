package com.plj.domain.bean.common;

import com.plj.domain.request.common.CommonParameter;

public class CommonTest extends CommonParameter
{
	private static final long serialVersionUID = 3806977370325905386L;

	private int id;
	
	private String name;
	
	private String password;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "CommonTest [id=" + id + ", name=" + name + ", password="
				+ password + "]";
	}
}
