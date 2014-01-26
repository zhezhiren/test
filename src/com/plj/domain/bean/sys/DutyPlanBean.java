package com.plj.domain.bean.sys;

import java.io.Serializable;

public class DutyPlanBean implements Serializable
{
	private static final long serialVersionUID = 480018709891841020L;
	private Integer id;
	
	private String startTime;
	
	private String endTime;
	
	private Integer dutyOperatorId;
	
	private String dutyOperatorName;
	
	private Integer dutyLeaderId;
	
	private String dutyLeaderName;
	
	private Integer orgId;
	
	private String jobContent;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getDutyOperatorId() {
		return dutyOperatorId;
	}

	public void setDutyOperatorId(Integer dutyOperatorId) {
		this.dutyOperatorId = dutyOperatorId;
	}

	public String getDutyOperatorName() {
		return dutyOperatorName;
	}

	public void setDutyOperatorName(String dutyOperatorName) {
		this.dutyOperatorName = dutyOperatorName;
	}

	public Integer getDutyLeaderId() {
		return dutyLeaderId;
	}

	public void setDutyLeaderId(Integer dutyLeaderId) {
		this.dutyLeaderId = dutyLeaderId;
	}

	public String getDutyLeaderName() {
		return dutyLeaderName;
	}

	public void setDutyLeaderName(String dutyLeaderName) {
		this.dutyLeaderName = dutyLeaderName;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getJobContent() {
		return jobContent;
	}

	public void setJobContent(String jobContent) {
		this.jobContent = jobContent;
	}
	
	
}
