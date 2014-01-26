package com.plj.domain.base.sys;

import java.io.Serializable;
import java.util.Date;

/**
 * 雇员(om_employee�?信息基础�?
 * @author liufan
 * @date 2013/2/25
 * ***/
public class BaseEmployee implements Serializable{
	
	private static final long serialVersionUID = 8083188239425923224L;
	private Integer employeeId;
	private String employeeCode;
	private Integer operatorId;
	private String userId;
	private String employeeName;
	private String realName;
	private String gender;
	private Date birthdayDate;
	private Integer position;
	private String employeeStatus;
	private String cardType;
	private String cardNumber;
	private Date inDate;
	private Date outDate;
	private String oTel;
	private String oAddress;
	private String oZipCode;
	private String oEmail;
	private String faxNumber;
	private String mobileNumber;
	private String msn;
	private String hTel;
	private String hAddress;
	private String hZipCode;
	private String pEmail;
	private String party;
	private String degree;
	private Integer major;
	private String specialty;
	private String wordExperience;
	private Date regDate;
	private Date createTime;
	private Date lastModyTime;
	private String orgIdList;
	private Integer orgId;
	private String remark;
	private Integer upEmployeeId;
	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeCode() {
		return employeeCode;
	}
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getBirthdayDate() {
		return birthdayDate;
	}
	public void setBirthdayDate(Date birthdayDate) {
		this.birthdayDate = birthdayDate;
	}
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
	public String getEmployeeStatus() {
		return employeeStatus;
	}
	public void setEmployeeStatus(String employeeStatus) {
		this.employeeStatus = employeeStatus;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public Date getInDate() {
		return inDate;
	}
	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}
	public Date getOutDate() {
		return outDate;
	}
	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}
	public String getoTel() {
		return oTel;
	}
	public void setoTel(String oTel) {
		this.oTel = oTel;
	}
	public String getoAddress() {
		return oAddress;
	}
	public void setoAddress(String oAddress) {
		this.oAddress = oAddress;
	}
	public String getoZipCode() {
		return oZipCode;
	}
	public void setoZipCode(String oZipCode) {
		this.oZipCode = oZipCode;
	}
	public String getoEmail() {
		return oEmail;
	}
	public void setoEmail(String oEmail) {
		this.oEmail = oEmail;
	}
	public String getFaxNumber() {
		return faxNumber;
	}
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getMsn() {
		return msn;
	}
	public void setMsn(String msn) {
		this.msn = msn;
	}
	public String gethTel() {
		return hTel;
	}
	public void sethTel(String hTel) {
		this.hTel = hTel;
	}
	public String gethAddress() {
		return hAddress;
	}
	public void sethAddress(String hAddress) {
		this.hAddress = hAddress;
	}
	public String gethZipCode() {
		return hZipCode;
	}
	public void sethZipCode(String hZipCode) {
		this.hZipCode = hZipCode;
	}
	public String getpEmail() {
		return pEmail;
	}
	public void setpEmail(String pEmail) {
		this.pEmail = pEmail;
	}
	public String getParty() {
		return party;
	}
	public void setParty(String party) {
		this.party = party;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public Integer getMajor() {
		return major;
	}
	public void setMajor(Integer major) {
		this.major = major;
	}
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	public String getWordExperience() {
		return wordExperience;
	}
	public void setWordExperience(String wordExperience) {
		this.wordExperience = wordExperience;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastModyTime() {
		return lastModyTime;
	}
	public void setLastModyTime(Date lastModyTime) {
		this.lastModyTime = lastModyTime;
	}
	public String getOrgIdList() {
		return orgIdList;
	}
	public void setOrgIdList(String orgIdList) {
		this.orgIdList = orgIdList;
	}
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer string) {
		this.orgId = string;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getUpEmployeeId() {
		return upEmployeeId;
	}
	public void setUpEmployeeId(Integer upEmployeeId) {
		this.upEmployeeId = upEmployeeId;
	}
	public Integer getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}
}
