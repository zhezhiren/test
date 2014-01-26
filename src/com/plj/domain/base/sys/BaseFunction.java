package com.plj.domain.base.sys;

import java.io.Serializable;

/**
 * 表ac_function映射对象
 * @author zhengxing
 * @version 1.0
 * @date 2013/2/20
 */
public class BaseFunction implements Serializable
{
	private static final long serialVersionUID = -3710353208992414911L;
	
	private Integer functionCode;
	
	private Integer functionGroupId;
	
	private String functionName;
	
	private String functionDesc;
	
	private String funcaction;
	
	private String paraInfo;
	
	private String isCheck;
	
	private String functionType;
	
	private String isMenu;

	public Integer getFunctionCode() {
		return functionCode;
	}

	public void setFunctionCode(Integer functionCode) {
		this.functionCode = functionCode;
	}

	public Integer getFunctionGroupId() {
		return functionGroupId;
	}

	public void setFunctionGroupId(Integer functionGroupId) {
		this.functionGroupId = functionGroupId;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getFunctionDesc() {
		return functionDesc;
	}

	public void setFunctionDesc(String functionDesc) {
		this.functionDesc = functionDesc;
	}

	public String getFuncaction() {
		return funcaction;
	}

	public void setFuncaction(String funcaction) {
		this.funcaction = funcaction;
	}

	public String getParaInfo() {
		return paraInfo;
	}

	public void setParaInfo(String paraInfo) {
		this.paraInfo = paraInfo;
	}

	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public String getFunctionType() {
		return functionType;
	}

	public void setFunctionType(String functionType) {
		this.functionType = functionType;
	}

	public String getIsMenu() {
		return isMenu;
	}

	public void setIsMenu(String isMenu) {
		this.isMenu = isMenu;
	}
}
