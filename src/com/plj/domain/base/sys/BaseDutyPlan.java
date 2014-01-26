package com.plj.domain.base.sys;

import java.io.Serializable;
import java.util.Date;

public class BaseDutyPlan implements Serializable
{
	private static final long serialVersionUID = -8419811151398403439L;
	private Integer id;
	
	private Date startTime;
	
	private Date endTime;
	
	private Integer dutyOperatorId;
	
	private String dutyOperatorName;
	
	private Integer dutyLeaderId;
	
	private String dutyLeaderName;
	
	private Integer orgId;
	
	private String empName;
	
	private String jobContent;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Date getStartTime()
	{
		return startTime;
	}

	public void setStartTime(Date startTime)
	{
		this.startTime = startTime;
	}

	public Date getEndTime()
	{
		return endTime;
	}

	public void setEndTime(Date endTime)
	{
		this.endTime = endTime;
	}

	public Integer getDutyOperatorId()
	{
		return dutyOperatorId;
	}

	public void setDutyOperatorId(Integer dutyOperatorId)
	{
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

	public Integer getOrgId() 
	{
		return orgId;
	}

	public void setOrgId(Integer orgId) 
	{
		this.orgId = orgId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getJobContent() {
		return jobContent;
	}

	public void setJobContent(String jobContent) {
		this.jobContent = jobContent;
	}
}
