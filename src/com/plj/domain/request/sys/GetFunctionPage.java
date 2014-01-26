package com.plj.domain.request.sys;

import com.plj.common.tools.mybatis.page.bean.Pagination;

public class GetFunctionPage extends Pagination{
	private static final long serialVersionUID = -7700854198168925206L;
	
	private Integer funcGroupId;

	public Integer getFuncGroupId() {
		return funcGroupId;
	}

	public void setFuncGroupId(Integer funcGroupId) {
		this.funcGroupId = funcGroupId;
	}
}
