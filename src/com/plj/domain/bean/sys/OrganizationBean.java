package com.plj.domain.bean.sys;

import java.io.Serializable;

public class OrganizationBean implements Serializable {
	private static final long serialVersionUID = 4441497702334966931L;

	private Integer orgId;

	private String orgName;

	private String orgCode;

	private String orgType;

	private String status;

	private Integer orgLevel;

	private String orgSeq;

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
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

	public Integer getOrgLevel() {
		return orgLevel;
	}

	public void setOrgLevel(Integer orgLevel) {
		this.orgLevel = orgLevel;
	}

	public String getOrgSeq() {
		return orgSeq;
	}

	public void setOrgSeq(String orgSeq) {
		this.orgSeq = orgSeq;
	}

}
