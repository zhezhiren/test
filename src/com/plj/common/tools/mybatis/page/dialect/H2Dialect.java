package com.plj.common.tools.mybatis.page.dialect;

import com.plj.common.tools.mybatis.page.tool.SQLHelper;

public class H2Dialect extends Dialect {
	
	public String getLimitString(String sql, int offset, int limit) {
		String newsql = SQLHelper.getLineSql(sql);
		
		String returnSql =  newsql +" limit "+ offset +" ,"+ limit;
		
		return returnSql;
	}

	public boolean supportsLimit() {
		return true;
	}
	

}
