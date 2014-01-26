package com.plj.common.session;

import java.io.Serializable;

public class UserSession implements Serializable
{
	public static final String USER_SESSION = "user";
	public static final String USER_AREA = "user_area";
	
	private Integer operatorId;//用户id
	private String userName;//用户�?也即登录�?
	private Integer orgId;// 用户�?��机构
	private String areaCode;//用户�?��机构对应的行政区域码
	private String empName;//用户真实姓名

	public Integer getOperatorId()
	{
		return operatorId;
	}

	public void setOperatorId(Integer operatorId)
	{
		this.operatorId = operatorId;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	
	public Integer getOrgId()
	{
		return orgId;
	}

	public void setOrgId(Integer orgId)
	{
		this.orgId = orgId;
	}

	public String getAreaCode()
	{
		return areaCode;
	}

	public void setAreaCode(String areaCode)
	{
		this.areaCode = areaCode;
	}

	public String getEmpName() {
		if(null != empName)
		{
			return empName;
		}else
		{
			return userName;
		}
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	private static final long serialVersionUID = -7993056295537603619L;
}
