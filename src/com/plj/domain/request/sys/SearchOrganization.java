package com.plj.domain.request.sys;

import com.plj.common.tools.mybatis.page.bean.Pagination;

public class SearchOrganization extends Pagination {
	
	private static final long serialVersionUID = -7428478189806427790L;

	private Integer orgId;

	private String orgName;

	private String orgCode;

	private String orgType;

	private String status;

	private String optype;

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

	public String getOptype() {
		return optype;
	}

	public void setOptype(String optype) {
		this.optype = optype;
	}
	
	
}
