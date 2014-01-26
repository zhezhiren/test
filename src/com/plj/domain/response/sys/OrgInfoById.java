package com.plj.domain.response.sys;

import java.io.Serializable;

public class OrgInfoById implements Serializable{

	private static final long serialVersionUID = -2603914990690112834L;
	private String infoArea;
	private String displayArea;
	private String infoParentOrgId;
	private String parentOrgName;
	private String orgName;
	private String remark;
	private String isLeaf;
	private String orgCode;
	public String getInfoArea() {
		return infoArea;
	}
	public void setInfoArea(String infoArea) {
		this.infoArea = infoArea;
	}
	public String getDisplayArea() {
		return displayArea;
	}
	public void setDisplayArea(String displayArea) {
		this.displayArea = displayArea;
	}
	public String getInfoParentOrgId() {
		return infoParentOrgId;
	}
	public void setInfoParentOrgId(String infoParentOrgId) {
		this.infoParentOrgId = infoParentOrgId;
	}
	public String getParentOrgName() {
		return parentOrgName;
	}
	public void setParentOrgName(String parentOrgName) {
		this.parentOrgName = parentOrgName;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	

}
