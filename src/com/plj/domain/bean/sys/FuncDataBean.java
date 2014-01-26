package com.plj.domain.bean.sys;

import java.io.Serializable;

public class FuncDataBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6947619275424572904L;
	private String funcCode;
	private String funcName;
	private String funcAction;
	private String funcDesc;
	private int funcGroupId;
	public String getFuncCode() {
		return funcCode;
	}
	public void setFuncCode(String funcCode) {
		this.funcCode = funcCode;
	}
	public String getFuncName() {
		return funcName;
	}
	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}
	public String getFuncAction() {
		return funcAction;
	}
	public void setFuncAction(String funcAction) {
		this.funcAction = funcAction;
	}
	public String getFuncDesc() {
		return funcDesc;
	}
	public void setFuncDesc(String funcDesc) {
		this.funcDesc = funcDesc;
	}
	public int getFuncGroupId() {
		return funcGroupId;
	}
	public void setFuncGroupId(int funcGroupId) {
		this.funcGroupId = funcGroupId;
	}
}
