package com.plj.domain.request.sys;

import java.io.Serializable;


public class EmpCodeExistsBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7819597657978430700L;
	private String empCode;
	private Integer empId;
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public Integer getEmpId() {
		return empId;
	}
	public void setEmpId(Integer empId) {
		this.empId = empId;
	}
	
}
