package com.plj.domain.base.sys;

import java.io.Serializable;

/**
 * 表ac_subfunctiondata映射对象
 * @author zhengxing
 * @version 1.0
 * @date 2013/2/20
 */
public class BaseSubFunctionData implements Serializable
{
	private static final long serialVersionUID = 2327672034643818292L;
	
	private Integer subFunctionId;
	
	private Integer functionCode;
	
	private String subType;
	
	private String subDesc;
	
	private String subExpression;
	
	private String remark;

	public Integer getSubFunctionId() {
		return subFunctionId;
	}

	public void setSubFunctionId(Integer subFunctionId) {
		this.subFunctionId = subFunctionId;
	}

	public Integer getFunctionCode() {
		return functionCode;
	}

	public void setFunctionCode(Integer functionCode) {
		this.functionCode = functionCode;
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

	public String getSubExpression() {
		return subExpression;
	}

	public void setSubExpression(String subExpression) {
		this.subExpression = subExpression;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
