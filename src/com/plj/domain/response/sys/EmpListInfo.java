package com.plj.domain.response.sys;

import java.io.Serializable;

public class EmpListInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3972317332265275661L;
	private Integer employeeId;
	private String empCode;
	private String empName;
    private String gender;
    private String empStatus;
    private String orgName;
    private String ooperatorId;
	private String uuserId;
	
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmpStatus() {
		return empStatus;
	}
	public void setEmpStatus(String empStatus) {
		this.empStatus = empStatus;
	}
	
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public String getOoperatorId() {
		return ooperatorId;
	}
	public void setOoperatorId(String ooperatorId) {
		this.ooperatorId = ooperatorId;
	}
	public String getUuserId() {
		return uuserId;
	}
	public void setUuserId(String uuserId) {
		this.uuserId = uuserId;
	}

}
