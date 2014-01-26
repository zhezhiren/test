package com.plj.domain.request.sys;

import java.io.Serializable;

public class OrgCodeExistsBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5201486057010001492L;
	
	private String orgCode;
	private Integer orgId;//(dynamic)
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	
}
