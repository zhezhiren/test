package com.plj.domain.response.sys;

import java.io.Serializable;

public class AppInfo implements Serializable
{
	private static final long serialVersionUID = -5699449728708923103L;

	private Integer appId;
	
	private String appName;

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
	
}
