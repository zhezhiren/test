package com.plj.domain.base.sys;

import java.io.Serializable;
import java.util.Date;

public class BaseOrganization implements Serializable {
	private static final long serialVersionUID = -3644527829921805700L;

	private Integer organizationId;

	private String organizationName;

	private String organizationCode;

	private String organizationDegree;

	private Integer organizationLevel;

	private Integer parentOrganizationId;

	private String organizationSeq;

	private String organizationType;
	
	private String organizationAddress;
	
	private String zipCode;
	
	private Integer manaposition;
	
	private Integer managerid;
	
	private String organizationManager;
	
	private String linkMan;
	
	private String linkTel;
	
	private String email;
	
	private String webURL;
	
	private Date startDate;
	
	private Date endDate;
	
	private String status;
	
	private String area;
	
	private Date creatTime;
	
	private Date lastUpdate;
	
	private Integer updator;
	
	private Integer sortNo;
	
	private String isLeaf;
	
	private Integer subCount;
	
	private String webName;
	
	private String remark;
	
	private Integer agentLevel;
	
	private Integer referralID;
	
	private String accountName;
	
	private String accountNumber;
	
	private String bankName;
	
	private String openBank;
	
	private Integer subCnt;
	
	private Integer mineCnt;
	
	private String dwMcjc;
	
	private String dwLb;
	
	private String dwLbNotes;

	public Integer getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Integer organizationId) {
		this.organizationId = organizationId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public String getOrganizationDegree() {
		return organizationDegree;
	}

	public void setOrganizationDegree(String organizationDegree) {
		this.organizationDegree = organizationDegree;
	}

	public Integer getOrganizationLevel() {
		return organizationLevel;
	}

	public void setOrganizationLevel(Integer organizationLevel) {
		this.organizationLevel = organizationLevel;
	}

	public Integer getParentOrganizationId() {
		return parentOrganizationId;
	}

	public void setParentOrganizationId(Integer parentOrganizationId) {
		this.parentOrganizationId = parentOrganizationId;
	}

	public String getOrganizationSeq() {
		return organizationSeq;
	}

	public void setOrganizationSeq(String organizationSeq) {
		this.organizationSeq = organizationSeq;
	}

	public String getOrganizationType() {
		return organizationType;
	}

	public void setOrganizationType(String organizationType) {
		this.organizationType = organizationType;
	}

	public String getOrganizationAddress() {
		return organizationAddress;
	}

	public void setOrganizationAddress(String organizationAddress) {
		this.organizationAddress = organizationAddress;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Integer getManaposition() {
		return manaposition;
	}

	public void setManaposition(Integer manaposition) {
		this.manaposition = manaposition;
	}

	public Integer getManagerid() {
		return managerid;
	}

	public void setManagerid(Integer managerid) {
		this.managerid = managerid;
	}

	public String getOrganizationManager() {
		return organizationManager;
	}

	public void setOrganizationManager(String organizationManager) {
		this.organizationManager = organizationManager;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getLinkTel() {
		return linkTel;
	}

	public void setLinkTel(String linkTel) {
		this.linkTel = linkTel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebURL() {
		return webURL;
	}

	public void setWebURL(String webURL) {
		this.webURL = webURL;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Integer getUpdator() {
		return updator;
	}

	public void setUpdator(Integer updator) {
		this.updator = updator;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public String getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}

	public Integer getSubCount() {
		return subCount;
	}

	public void setSubCount(Integer subCount) {
		this.subCount = subCount;
	}

	public String getWebName() {
		return webName;
	}

	public void setWebName(String webName) {
		this.webName = webName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getAgentLevel() {
		return agentLevel;
	}

	public void setAgentLevel(Integer agentLevel) {
		this.agentLevel = agentLevel;
	}

	public Integer getReferralID() {
		return referralID;
	}

	public void setReferralID(Integer referralID) {
		this.referralID = referralID;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getOpenBank() {
		return openBank;
	}

	public void setOpenBank(String openBank) {
		this.openBank = openBank;
	}

	public Integer getSubCnt() {
		return subCnt;
	}

	public void setSubCnt(Integer subCnt) {
		this.subCnt = subCnt;
	}

	public Integer getMineCnt() {
		return mineCnt;
	}

	public void setMineCnt(Integer mineCnt) {
		this.mineCnt = mineCnt;
	}

	public String getDwMcjc() {
		return dwMcjc;
	}

	public void setDwMcjc(String dwMcjc) {
		this.dwMcjc = dwMcjc;
	}

	public String getDwLb() {
		return dwLb;
	}

	public void setDwLb(String dwLb) {
		this.dwLb = dwLb;
	}

	public String getDwLbNotes() {
		return dwLbNotes;
	}

	public void setDwLbNotes(String dwLbNotes) {
		this.dwLbNotes = dwLbNotes;
	}

}
