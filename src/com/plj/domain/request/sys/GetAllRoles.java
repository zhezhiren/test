package com.plj.domain.request.sys;

import com.plj.common.tools.mybatis.page.bean.Pagination;

public class GetAllRoles extends Pagination
{
	private static final long serialVersionUID = 6692605559741279070L;
	
	private String keyWord;

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	
}
