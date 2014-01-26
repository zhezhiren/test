package com.plj.domain.bean.sys;

import java.io.Serializable;

public class DutyActBean implements Serializable
{
	private static final long serialVersionUID = 8285757361838297604L;

	private Integer id;
	
	private String startTime;
	
	private String endTime;
	
	private Integer dutyOperatorId;
	
	private String dutyOperatorName;
	
	private Integer dutyLeaderId;
	
	private String dutyLeaderName;
	
	private String jobContent;
	
	private String warningContent;
	
	private Integer orgId;

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

	public String getJobContent() {
		return jobContent;
	}

	public void setJobContent(String jobContent) {
		this.jobContent = jobContent;
	}

	public String getWarningContent() {
		return warningContent;
	}

	public void setWarningContent(String warningContent) {
		this.warningContent = warningContent;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
}
