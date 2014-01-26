package com.plj.domain.base.sys;

import java.io.Serializable;
import java.util.Date;

/**
 * 表ac_operator映射对象
 * @author zhengxing
 * @version 1.0
 * @date 2013/2/20
 */
public class BaseOperator implements Serializable
{
	private static final long serialVersionUID = -5075728608882893615L;
	
	private Integer operatorId;
	
	private String userId;
	
	private String password;
	
	private Date invaldate;
	
	private String operatorName;
	
	private String authMode;
	
	private String status;
	
	private Date unlockTime;
	
	private String menuType;
	
	private Date lastLoginTime;
	
	private Integer loginErrorCounts;
	
	private Date startTime;
	
	private Date endTime;
	
	private Date validTime;
	
	private String macCode;
	
	private String ipAddress;
	
	private String email;
	
	private String passwordTMP;
	
	private Date lossTime;
	
	public BaseOperator()
	{
	}
	
	public BaseOperator(Integer operatorId, String userId, String password
			, Date invaldate, String operatorName, String authMode
			, String status, Date unlockTime, String menuType, Date lastLoginTime
			, Integer loginErrorCounts, Date startTime, Date endTime
			, Date validTime, String macCode, String ipAddress, String email
			, String passwordTMP, Date lossTime)
	{
		this.operatorId = operatorId;
		this.userId = userId;
		this.password = password;
		this.invaldate = invaldate;
		this.operatorName = operatorName;
		this.authMode = authMode;
		this.status = status;
		this.unlockTime = unlockTime;
		this.menuType = menuType;
		this.lastLoginTime = lastLoginTime;
		this.loginErrorCounts = loginErrorCounts;
		this.startTime = startTime;
		this.endTime = endTime;
		this.validTime = validTime;
		this.macCode = macCode;
		this.ipAddress = ipAddress;
		this.email = email;
		this.passwordTMP = passwordTMP;
		this.lossTime = lossTime;
	}
	
	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getInvaldate() {
		return invaldate;
	}

	public void setInvaldate(Date invaldate) {
		this.invaldate = invaldate;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getAuthMode() {
		return authMode;
	}

	public void setAuthMode(String authMode) {
		this.authMode = authMode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getUnlockTime() {
		return unlockTime;
	}

	public void setUnlockTime(Date unlockTime) {
		this.unlockTime = unlockTime;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Integer getLoginErrorCounts() {
		return loginErrorCounts;
	}

	public void setLoginErrorCounts(Integer loginErrorCounts) {
		this.loginErrorCounts = loginErrorCounts;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getValidTime() {
		return validTime;
	}

	public void setValidTime(Date validTime) {
		this.validTime = validTime;
	}

	public String getMacCode() {
		return macCode;
	}

	public void setMacCode(String macCode) {
		this.macCode = macCode;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordTMP() {
		return passwordTMP;
	}

	public void setPasswordTMP(String passwordTMP) {
		this.passwordTMP = passwordTMP;
	}

	public Date getLossTime() {
		return lossTime;
	}

	public void setLossTime(Date lossTime) {
		this.lossTime = lossTime;
	}
	
}
