package com.plj.domain.request.sys;

import com.plj.common.tools.mybatis.page.bean.Pagination;

public class MenuFuncListBean extends Pagination{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6184347942721001053L;
	
	private String appName;
	private String funcName;
	private String funcGroupName;
	
	
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getFuncName() {
		return funcName;
	}
	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}
	public String getFuncGroupName() {
		return funcGroupName;
	}
	public void setFuncGroupName(String funcGroupName) {
		this.funcGroupName = funcGroupName;
	}
	
	
	

}
