package com.plj.domain.base.sys;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 本对象暂时未启用
 * @author zhengxing
 * @version 1.0
 * @date 2013/2/20
 */
public class BaseUserAccount implements Serializable
{
	private static final long serialVersionUID = 5126379398626693973L;

	private Integer id;//id
	
	private String username;//用户�?
	
	private String password;//用户密码
	
	private String realname;//用户真实姓名
	
	private String email;//用户email
	
	private Date lastLoginTime;//用户上次登录时间
	
	private String lastLoginIp;//用户上次登录ip
	
	private Integer cardType;//用户证类�?
	
	private String idCard;//用户身份证件�?
	
	private Date registerTime;//用户注册时间
	
	private String registerIp;//用户注册ip
	
	private Integer loginCount;//用户登录次数
	
	private Integer userStatus;//用户状�? -1为删�? 1为正�? 2未激�? 3禁用
	
	private Date forbiddenDate;//上次禁用时间
	
	private Long timeToForbidden;//上次禁用时长
	
	private Integer forbiddenCount;//禁用次数
	
	private String resetKey;//重置密码key
	
	private String resetPwd;//重置密码value
	
	private Date errorTime;//登录错误时间
	
	private Integer errorCount;//登录错误次数
	
	private String errorIp;//登录错误IP
	
	private String activationCode;//�?���?32位激活码
	
	private String activationIp;//用户注册ip
	
	private String address;//用户�?��地址
	
	private String countryCode;//国家地区代码
	
	private String provinceCode;//省份代码
	
	private String cityCode;//城市代码
	
	private String countyCode;//县区代码
	
	private int hashCode = Integer.MIN_VALUE;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public String getRegisterIp() {
		return registerIp;
	}

	public void setRegisterIp(String registerIp) {
		this.registerIp = registerIp;
	}

	public Integer getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}

	public Integer getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}

	public Date getForbiddenDate() {
		return forbiddenDate;
	}

	public void setForbiddenDate(Date forbiddenDate) {
		this.forbiddenDate = forbiddenDate;
	}

	public Long getTimeToForbidden() {
		return timeToForbidden;
	}

	public void setTimeToForbidden(Long timeToForbidden) {
		this.timeToForbidden = timeToForbidden;
	}

	public Integer getForbiddenCount() {
		return forbiddenCount;
	}

	public void setForbiddenCount(Integer forbiddenCount) {
		this.forbiddenCount = forbiddenCount;
	}

	public String getResetKey() {
		return resetKey;
	}

	public void setResetKey(String resetKey) {
		this.resetKey = resetKey;
	}

	public String getResetPwd() {
		return resetPwd;
	}

	public void setResetPwd(String resetPwd) {
		this.resetPwd = resetPwd;
	}

	public Date getErrorTime() {
		return errorTime;
	}

	public void setErrorTime(Date errorTime) {
		this.errorTime = errorTime;
	}

	public Integer getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(Integer errorCount) {
		this.errorCount = errorCount;
	}

	public String getErrorIp() {
		return errorIp;
	}

	public void setErrorIp(String errorIp) {
		this.errorIp = errorIp;
	}

	public String getActivationCode() {
		return activationCode;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

	public String getActivationIp() {
		return activationIp;
	}

	public void setActivationIp(String activationIp) {
		this.activationIp = activationIp;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}
	
	@Override
	public int hashCode()
	{
		return hashCode;
	}
}
