package com.plj.domain.request.sys;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.plj.common.error.MyError;
import com.plj.common.tools.mybatis.page.bean.Pagination;

public class GetMessagesRequest extends Pagination
{
	private static final long serialVersionUID = 4132968168895762230L;
	
	private DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	private String startTime;
	
	private String endTime;
	
	private String content;
	
	private Date time1;
	
	private Date time2;

	public Date getTime1() 
	{
		return time1;
	}

	public Date getTime2() 
	{
		return time2;
	}

	public String getStartTime() 
	{
		return startTime;
	}

	public void setStartTime(String startTime)
	{
		this.startTime = startTime;
	}

	public String getEndTime() 
	{
		return endTime;
	}

	public void setEndTime(String endTime) 
	{
		this.endTime = endTime;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}
	
	public List<MyError> checkError()
	{
		List<MyError> errors = null;
		try
		{
			if(null != startTime)
			{
				time1 = format.parse(startTime);
			}
			if(null != endTime)
			{
				time2 = format.parse(endTime);
			}
		} catch (ParseException e)
		{
			errors = new ArrayList<MyError>(1);
		}
		return errors;
	}
}
