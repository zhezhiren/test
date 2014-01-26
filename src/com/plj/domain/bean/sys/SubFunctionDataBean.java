package com.plj.domain.bean.sys;

import java.io.Serializable;

public class SubFunctionDataBean implements Serializable{

	private static final long serialVersionUID = 4530037008371786972L;
	
	private String subFuncId;
	
	private String funcCode;
	
	private String subType;
	
	private String subDesc;
	
	private String subExpr;
	
	private String remark;

	public String getSubFuncId() {
		return subFuncId;
	}

	public void setSubFuncId(String subFuncId) {
		this.subFuncId = subFuncId;
	}

	public String getFuncCode() {
		return funcCode;
	}

	public void setFuncCode(String funcCode) {
		this.funcCode = funcCode;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getSubDesc() {
		return subDesc;
	}

	public void setSubDesc(String subDesc) {
		this.subDesc = subDesc;
	}

	public String getSubExpr() {
		return subExpr;
	}

	public void setSubExpr(String subExpr) {
		this.subExpr = subExpr;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

}
