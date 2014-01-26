package com.plj.domain.request.sys;

import com.plj.common.tools.mybatis.page.bean.Pagination;

public class GetEmployees extends Pagination {

	private static final long serialVersionUID = 3156161184802252263L;
	private String orgId;
	private String empCode;
	private String empName;
	private String operId;
	private String belongToOrgId;
	private String optype;
	private String selectedOrgName;
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
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
	public String getOperId() {
		return operId;
	}
	public void setOperId(String operId) {
		this.operId = operId;
	}
	public String getBelongToOrgId() {
		return belongToOrgId;
	}
	public void setBelongToOrgId(String belongToOrgId) {
		this.belongToOrgId = belongToOrgId;
	}
	public String getOptype() {
		return optype;
	}
	public void setOptype(String optype) {
		this.optype = optype;
	}
	public String getSelectedOrgName() {
		return selectedOrgName;
	}
	public void setSelectedOrgName(String selectedOrgName) {
		this.selectedOrgName = selectedOrgName;
	}
	

}
