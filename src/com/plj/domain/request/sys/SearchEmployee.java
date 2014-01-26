package com.plj.domain.request.sys;

import com.plj.common.tools.mybatis.page.bean.Pagination;

public class SearchEmployee extends Pagination {


	/**
	 * 
	 */
	private static final long serialVersionUID = 87381938932153422L;
	private Integer OrgId;

	private String empCode;

	private String empName;

	private Integer operId;

	private String belongToOrgId;
	
	private String optype;
	
	private String selectedOrgName;

	public Integer getOrgId() {
		return OrgId;
	}

	public void setOrgId(Integer orgId) {
		OrgId = orgId;
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

	public Integer getOperId() {
		return operId;
	}

	public void setOperId(Integer operId) {
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
	