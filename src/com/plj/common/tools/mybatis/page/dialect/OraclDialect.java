package com.plj.common.tools.mybatis.page.dialect;

public class OraclDialect
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
		boolean isForUpdate = false;
		if ( sql.toLowerCase().endsWith(" for update") )
		{
			sql = sql.substring( 0, sql.length()-11 );
			isForUpdate = true;
		}
		StringBuffer pagingSelect = new StringBuffer( sql.length()+100 );
		pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
		pagingSelect.append(sql);
		pagingSelect.append(" ) row_ where rownum <=").append(offset + limit).append(") where rownum_ >").append(offset);
		if ( isForUpdate ) 
		{
			pagingSelect.append( " for update" );
		}
		return pagingSelect.toString();
	}
}
