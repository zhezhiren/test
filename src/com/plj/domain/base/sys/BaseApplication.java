package com.plj.domain.base.sys;

import java.io.Serializable;
import java.util.Date;

/**
 * 表ac_application映射对象
 * @author zhengxing
 * @version 1.0
 * @date 2013/2/20
 */
public class BaseApplication implements Serializable
{
	private static final long serialVersionUID = -7882190114546205206L;
	
	private Integer applicationId;
	private String applicationCode;
	private String applicationName;
	private String applciationType;
	private String isOpen;
	private Date openDate;
	private String url;
	private String applicationDesc;
	private Integer maintenance;
	private String manaRole;
	private String demo;
	private String iniwp;
	private String inTaskCenter;
	private String ipAddr;
	private String ipPort;
	
	public Integer getApplicationId() {
		return applicationId;
	}
	
	public void setApplicationId(Integer applicationId) {
		this.applicationId = applicationId;
	}
	
	public String getApplicationCode() {
		return applicationCode;
	}
	
	public void setApplicationCode(String applicationCode) {
		this.applicationCode = applicationCode;
	}
	
	public String getApplicationName() {
		return applicationName;
	}
	
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	
	public String getApplciationType() {
		return applciationType;
	}
	
	public void setApplciationType(String applciationType) {
		this.applciationType = applciationType;
	}
	
	public String getIsOpen() {
		return isOpen;
	}
	
	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}
	
	public Date getOpenDate() {
		return openDate;
	}
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getApplicationDesc() {
		return applicationDesc;
	}
	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}
	
	public Integer getMaintenance() {
		return maintenance;
	}
	
	public void setMaintenance(Integer maintenance) {
		this.maintenance = maintenance;
	}
	
	public String getManaRole() {
		return manaRole;
	}
	
	public void setManaRole(String manaRole) {
		this.manaRole = manaRole;
	}
	
	public String getDemo() {
		return demo;
	}
	
	public void setDemo(String demo) {
		this.demo = demo;
	}
	
	public String getIniwp() {
		return iniwp;
	}
	
	public void setIniwp(String iniwp) {
		this.iniwp = iniwp;
	}
	
	public String getInTaskCenter() {
		return inTaskCenter;
	}
	
	public void setInTaskCenter(String inTaskCenter) {
		this.inTaskCenter = inTaskCenter;
	}
	
	public String getIpAddr() {
		return ipAddr;
	}
	
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	
	public String getIpPort() {
		return ipPort;
	}
	
	public void setIpPort(String ipPort) {
		this.ipPort = ipPort;
	}
}
