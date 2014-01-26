package com.plj.domain.response.sys;

import java.io.Serializable;

public class MenuListReturnBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1737381793616449737L;
	
	private String funcName;
	private String funcGroupName;
	private String appName;
	private String funcAction;
	private Integer appId;
	private String funcCode;
	
	
	
	public String getFuncGroupName() {
		return funcGroupName;
	}
	public void setFuncGroupName(String funcGroupName) {
		this.funcGroupName = funcGroupName;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getFuncAction() {
		return funcAction;
	}
	public void setFuncAction(String funcAction) {
		this.funcAction = funcAction;
	}
	public String getFuncName() {
		return funcName;
	}
	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}
	public String getFuncCode() {
		return funcCode;
	}
	public void setFuncCode(String funcCode) {
		this.funcCode = funcCode;
	}
	public Integer getAppId() {
		return appId;
	}
	public void setAppId(Integer appId) {
		this.appId = appId;
	}
	
	
	
}
