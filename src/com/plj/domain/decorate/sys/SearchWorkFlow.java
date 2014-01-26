package com.plj.domain.decorate.sys;

import com.plj.common.tools.mybatis.page.bean.Pagination;

public class SearchWorkFlow extends Pagination
{ 
	/**
	 * 
	 */
	private static final long serialVersionUID = 4916071969175679602L;
	private String name;
	private String startTime;
	private String endTime;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	
}
