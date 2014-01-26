package com.plj.domain.base.sys;

import java.io.Serializable;
import java.util.Date;

/**
 * 本对象暂时未启用
 * @author zhengxing
 * @version 1.0
 * @date 2013/2/20
 */
public class BaseAuthentication implements Serializable{

	private static final long serialVersionUID = -4823451267608416814L;
	
	private String authenticationId;//认证ID
	
	private Integer userId;//用户ID
	
	private String username;//用户�?
	
	private Date loginTime;//登录时间

	private String loginIp;//登录ip
	
	private Date updateTime;//更新时间
	
	public String getAuthenticationId() {
		return authenticationId;
	}

	public void setAuthenticationId(String authenticationId) {
		this.authenticationId = authenticationId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
