package com.plj.domain.request.sys;

import com.plj.common.tools.mybatis.page.bean.Pagination;

public class GetOperatorRolesPage extends Pagination{
	private static final long serialVersionUID = -7700854198168925206L;
	
	private String operatorId;

	public String getOperatorRoleId() {
		return operatorId;
	}

	public void setOperatorRoleId(String operatorId) {
		this.operatorId = operatorId;
	}
}
