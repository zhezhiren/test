package com.plj.domain.request.sys;

import java.io.Serializable;

public class UpdateOrgByIdBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4480105929848199226L;
	
	private String orgCode;
	private String orgName;
	private String orgType;
	private String status;
	private String area;
	private String acCountName;
	private String acCountNumber;
	private String bankName;
	private String openBank;
	private String remark;
	private Integer orgId;
	private String isLeaf;
	private Integer parentOrgId;
 	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrgType() {
		return orgType;
	}
	public void setOrgType(String orgType) {
		this.orgType = orgType;
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
	public String getAcCountName() {
		return acCountName;
	}
	public void setAcCountName(String acCountName) {
		this.acCountName = acCountName;
	}
	public String getAcCountNumber() {
		return acCountNumber;
	}
	public void setAcCountNumber(String acCountNumber) {
		this.acCountNumber = acCountNumber;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	public String getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}
	public Integer getParentOrgId() {
		return parentOrgId;
	}
	public void setParentOrgId(Integer parentOrgId) {
		this.parentOrgId = parentOrgId;
	}
}
