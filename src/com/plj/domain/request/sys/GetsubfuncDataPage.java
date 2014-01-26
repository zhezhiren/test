package com.plj.domain.request.sys;

import com.plj.common.tools.mybatis.page.bean.Pagination;

public class GetsubfuncDataPage  extends Pagination
{
	private static final long serialVersionUID = 6138888211846416252L;
	
	private String funcCode;

	public String getFuncCode() {
		return funcCode;
	}

	public void setFuncCode(String funcCode) {
		this.funcCode = funcCode;
	}

	
}
