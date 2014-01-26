package com.plj.domain.response.sys;

import java.io.Serializable;
import java.util.Date;

public class WorkFlowCopy implements Serializable
{ 
	private static final long serialVersionUID = 4348797178023968359L;
	private int wfid;
	private String wfname;
	private int wforgId;
	private String wfstartTime;
	private String wfendTime;
	private String wfhandleFlow;
	private String wfremark;
	private String wfstatus;
	private String wforgName;
	private String wfworkStatus;
	private String wfcompleteStatus;
	public int getWfid() {
		return wfid;
	}
	public void setWfid(int wfid) {
		this.wfid = wfid;
	}
	public String getWfname() {
		return wfname;
	}
	public void setWfname(String wfname) {
		this.wfname = wfname;
	}
	public int getWforgId() {
		return wforgId;
	}
	public void setWforgId(int wforgId) {
		this.wforgId = wforgId;
	}
	
	public String getWfstartTime() {
		return wfstartTime;
	}
	public void setWfstartTime(String wfstartTime) {
		this.wfstartTime = wfstartTime;
	}
	public String getWfendTime() {
		return wfendTime;
	}
	public void setWfendTime(String wfendTime) {
		this.wfendTime = wfendTime;
	}
	public String getWfhandle_flow() {
		return wfhandleFlow;
	}
	public void setWfhandle_flow(String wfhandle_flow) {
		this.wfhandleFlow = wfhandle_flow;
	}
	public String getWfremark() {
		return wfremark;
	}
	public void setWfremark(String wfremark) {
		this.wfremark = wfremark;
	}
	public synchronized String getWfhandleFlow() {
		return wfhandleFlow;
	}
	public synchronized void setWfhandleFlow(String wfhandleFlow) {
		this.wfhandleFlow = wfhandleFlow;
	}
	public synchronized String getWfstatus() {
		return wfstatus;
	}
	public synchronized void setWfstatus(String wfstatus) {
		this.wfstatus = wfstatus;
	}
	public synchronized String getWforgName() {
		return wforgName;
	}
	public synchronized void setWforgName(String wforgName) {
		this.wforgName = wforgName;
	}
	public synchronized String getWfworkStatus() {
		return wfworkStatus;
	}
	public synchronized void setWfworkStatus(String wfworkStatus) {
		this.wfworkStatus = wfworkStatus;
	}
	public synchronized String getWfcompleteStatus() {
		return wfcompleteStatus;
	}
	public synchronized void setWfcompleteStatus(String wfcompleteStatus) {
		this.wfcompleteStatus = wfcompleteStatus;
	}
	
}
