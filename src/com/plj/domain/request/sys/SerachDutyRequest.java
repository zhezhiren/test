package com.plj.domain.request.sys;

import java.io.Serializable;

import com.plj.common.tools.mybatis.page.bean.Pagination;

public class SerachDutyRequest extends Pagination implements Serializable
{
	private static final long serialVersionUID = -965015696758202249L;
	
	private String startTime;
	
	private String endTime;
	
	private String empName;

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

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}
	
}
