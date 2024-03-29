package com.plj.common.session;

import java.io.Serializable;

public class UserSession implements Serializable
{
	public static final String USER_SESSION = "user";
	public static final String USER_AREA = "user_area";
	
	private Integer operatorId;//ç¨æ·id
	private String userName;//ç¨æ·å?ä¹å³ç»å½å?
	private Integer orgId;// ç¨æ·æ?¨æºæ
	private String areaCode;//ç¨æ·æ?¨æºæå¯¹åºçè¡æ¿åºåç 
	private String empName;//ç¨æ·çå®å§å

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
