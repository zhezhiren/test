package com.plj.domain.request.sys;

import com.plj.common.tools.mybatis.page.bean.Pagination;

public class SearchApplication extends Pagination
{
	private static final long serialVersionUID = -6511613520864286376L;
	
	private String APPNAME;
	
	private String APPTYPE;

	public String getAPPNAME() {
		return APPNAME;
	}

	public void setAPPNAME(String aPPNAME) {
		APPNAME = aPPNAME;
	}

	public String getAPPTYPE() {
		return APPTYPE;
	}

	public void setAPPTYPE(String aPPTYPE) {
		APPTYPE = aPPTYPE;
	}
	
}
