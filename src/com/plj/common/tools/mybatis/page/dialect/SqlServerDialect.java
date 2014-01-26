package com.plj.common.tools.mybatis.page.dialect;

import com.plj.common.tools.mybatis.page.tool.SQLHelper;

public class SqlServerDialect extends Dialect
{
	public String getLimitString(String sql, boolean hasOffset)
	{
		sql = sql.trim();
		boolean isForUpdate = false;
		if ( sql.toLowerCase().endsWith(" for update") )
		{
			sql = sql.substring( 0, sql.length()-11 );
			isForUpdate = true;
		}
		StringBuffer pagingSelect = new StringBuffer( sql.length()+100 );
		if (hasOffset)
		{
			pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
		}else 
		{
			pagingSelect.append("select * from ( ");
		}
		pagingSelect.append(sql);
		if (hasOffset)
		{
			pagingSelect.append(" ) row_ where rownum <= ?) where rownum_ > ?");
		}else 
		{
			pagingSelect.append(" ) where rownum <= ?");
		}
		if ( isForUpdate )
		{
			pagingSelect.append( " for update" );
		}
		return pagingSelect.toString();
	}
	
	public String getLimitString(String sql, int offset, int limit) 
	{
		sql = sql.trim();
		sql = SQLHelper.getLineSql(sql);
		boolean isForUpdate = false;
		if ( sql.toLowerCase().endsWith(" for update") ) 
		{
			sql = sql.substring( 0, sql.length()-11 );
			isForUpdate = true;
		}
		StringBuffer pagingSelect = new StringBuffer( sql.length()+100 );
		pagingSelect.append("select * from (select ROW_Number() over (order by  ident_current('temp_t')) as num,temp_t.* from ( ");
		int count =offset + limit;
		if (sql.toLowerCase().indexOf("select distinct") != -1)
		{
			pagingSelect.append("SELECT DISTINCT").append(" TOP ").append(count).append(sql.substring(15, sql.length()));
		}else
		{
			pagingSelect.append("SELECT TOP ").append(count).append(sql.substring(6, sql.length()));
		}
		pagingSelect.append(") temp_t) U where U.num >").append(offset)/*.append(" and U.num<=").append(offset + limit)*/;
		if ( isForUpdate ) 
		{
			pagingSelect.append( " for update" );
		}
		return pagingSelect.toString();
	}
}
