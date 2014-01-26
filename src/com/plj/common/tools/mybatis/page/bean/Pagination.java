package com.plj.common.tools.mybatis.page.bean;

import java.io.Serializable;

public class Pagination implements Serializable
{
	private static final long serialVersionUID = 7538241392306952852L;
	
	private int pageNo = 1;
	private int pageSize = 20;
	private long totalCount = 0;
	private boolean isReadTotalCount = true;
	private int limit = 20;
	private int start = 0;

	public int getPageNo()
	{
		return pageNo;
	}

	public void setPageNo(int pageNo)
	{
		this.pageNo = pageNo < 1 ? 1 : pageNo;
	}

	public int getPageSize()
	{
		return pageSize;
	}

	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize < 2 ? 20 : pageSize;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit < 2 ? 20 : limit;
		this.pageSize = limit < 2 ? 20 : limit;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		if(start < 0)
		{
			start = 0;
		}
		this.start = start;
	}

	public long getTotalCount()
	{
		return totalCount;
	}

	public void setTotalCount(long totalCount)
	{
		this.totalCount = totalCount;
	}

	public boolean isReadTotalCount()
	{
		return isReadTotalCount;
	}

	public void setReadTotalCount(boolean isReadTotalCount)
	{
		this.isReadTotalCount = isReadTotalCount;
	}

	public long getTotalPage()
	{
		if (totalCount <= 0)
		{
			return 1;
		}
		else
		{
			long count = totalCount / pageSize;
			if (totalCount % pageSize > 0)
			{
				count++;
			}
			return count;
		}
	}

	public boolean isHasPrevPage()
	{
		return (pageNo - 1 >= 1);
	}

	public int getPrevPage()
	{
		return isHasPrevPage() ? (pageNo - 1) : pageNo;
	}

	public boolean isHasNextPage()
	{
		if (isReadTotalCount)
		{
			return (pageNo + 1 <= getTotalPage());
		} 
		else
		{
			return true;
		}

	}

	public int getNextPage()
	{
		return isHasNextPage() ? (pageNo + 1) : pageNo;
	}

	public int getCurrentlyPageFirstResoultIndex()
	{
		return (pageNo - 1) * pageSize;
	}
}
