package com.plj.domain.base.sys;

import java.io.Serializable;
import java.util.Date;

public class BaseWorkFlow implements Serializable
{ 
	private static final long serialVersionUID = 4348797178023968359L;
	private int id;
	private String name;
	private int orgId;
	private Date startTime;
	private Date endTime;
	private String handleFlow;
	private String remark;
	private String status;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getOrgId() {
		return orgId;
	}
	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}
	
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getHandle_flow() {
		return handleFlow;
	}
	public void setHandle_flow(String handle_flow) {
		this.handleFlow = handle_flow;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public synchronized String getStatus() {
		return status;
	}
	public synchronized void setStatus(String status) {
		this.status = status;
	}
}
