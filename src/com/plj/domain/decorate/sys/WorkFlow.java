package com.plj.domain.decorate.sys;

import com.plj.domain.base.sys.BaseWorkFlow;

public class WorkFlow extends BaseWorkFlow
{ 
	private static final long serialVersionUID = 4348797178023968359L;
	private String orgName;
	private String workStatus;
	private String completeStatus;
	
	public String getWorkStatus() {
		return workStatus;
	}
	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}
	public String getCompleteStatus() {
		return completeStatus;
	}
	public void setCompleteStatus(String completeStatus) {
		this.completeStatus = completeStatus;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
}
