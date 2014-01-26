package com.plj.domain.request.sys;

import java.io.Serializable;

public class WorkFlowConvert implements Serializable
{ 
	private static final long serialVersionUID = 4348797178023968359L;
	private int id;
	private String name;
	private int orgId;
	private String startTime;
	private String endTime;
	private String handleFlow;
	private String remark;
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
	public synchronized String getStartTime() {
		return startTime;
	}
	public synchronized void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public synchronized String getEndTime() {
		return endTime;
	}
	public synchronized void setEndTime(String endTime) {
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
	
	

}
