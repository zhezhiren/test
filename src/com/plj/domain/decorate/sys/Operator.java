package com.plj.domain.decorate.sys;

import java.util.Date;

import com.plj.domain.base.sys.BaseOperator;

public class Operator extends BaseOperator
{
	private static final long serialVersionUID = 4492878224058461329L;
	private Integer[] roleIds;
	public Operator()
	{
	}
	
	public Operator(Integer operatorId, String userId, String password
			, Date invaldate, String operatorName, String authMode
			, String status, Date unlockTime, String menuType, Date lastLoginTime
			, Integer loginErrorCounts, Date startTime, Date endTime
			, Date validTime, String macCode, String ipAddress, String email
			, String passwordTMP, Date lossTime)
	{
		super(operatorId, userId, password, invaldate, operatorName, authMode
				, status, unlockTime, menuType, lastLoginTime, loginErrorCounts
				, startTime, endTime, validTime, macCode, ipAddress, email
				, passwordTMP, lossTime);
	}

	public Integer[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(Integer[] roleIds) {
		this.roleIds = roleIds;
	}
}
