package com.plj.domain.base.sys;

import java.io.Serializable;

/**
 * 表ac_rolesubfuncdata映射对象
 * @author zhengxing
 * @version 1.0
 * @date 2013/2/20
 */
public class BaseConRoleSubFunctionData implements Serializable
{
	private static final long serialVersionUID = 9129736525496893121L;
	
	private Integer id;
	
	private Integer roleId;
	
	private Integer functionCode;
	
	private Integer subFucntionId;
	
	private String subExpression;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public Integer getSubFucntionId() {
		return subFucntionId;
	}

	public void setSubFucntionId(Integer subFucntionId) {
		this.subFucntionId = subFucntionId;
	}

	public String getSubExpression() {
		return subExpression;
	}

	public void setSubExpression(String subExpression) {
		this.subExpression = subExpression;
	}
}
