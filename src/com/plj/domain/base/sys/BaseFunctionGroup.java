package com.plj.domain.base.sys;

import java.io.Serializable;

/**
 * 表ac_funcgroup映射对象
 * @author zhengxing
 * @version 1.0
 * @date 2013/2/20
 */
public class BaseFunctionGroup implements Serializable
{
	private static final long serialVersionUID = -8829475276275485296L;
	
	private Integer functionGroupId;
	
	private Integer applicationId;
	
	private String functionGroupName;
	
	private Integer parentGroup;
	
	private Integer groupLevel;
	
	private String functionGroupSeq;
	
	private String isLeaf;
	
	private Integer subCount;

	public Integer getFunctionGroupId() 
	{
		return functionGroupId;
	}

	public void setFunctionGroupId(Integer functionGroupId) 
	{
		this.functionGroupId = functionGroupId;
	}

	public Integer getApplicationId() 
	{
		return applicationId;
	}

	public void setApplicationId(Integer applicationId) 
	{
		this.applicationId = applicationId;
	}

	public String getFunctionGroupName() 
	{
		return functionGroupName;
	}

	public void setFunctionGroupName(String functionGroupName) 
	{
		this.functionGroupName = functionGroupName;
	}

	public Integer getParentGroup() 
	{
		return parentGroup;
	}

	public void setParentGroup(Integer parentGroup) 
	{
		this.parentGroup = parentGroup;
	}

	public Integer getGroupLevel() 
	{
		return groupLevel;
	}

	public void setGroupLevel(Integer groupLevel) 
	{
		this.groupLevel = groupLevel;
	}

	public String getFunctionGroupSeq() 
	{
		return functionGroupSeq;
	}

	public void setFunctionGroupSeq(String functionGroupSeq)
	{
		this.functionGroupSeq = functionGroupSeq;
	}

	public String getIsLeaf() 
	{
		return isLeaf;
	}

	public void setIsLeaf(String isLeaf) 
	{
		this.isLeaf = isLeaf;
	}

	public Integer getSubCount() 
	{
		return subCount;
	}

	public void setSubCount(Integer subCount)
	{
		this.subCount = subCount;
	}
	
}
