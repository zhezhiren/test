package com.plj.domain.decorate.sys;

import java.util.Date;

import com.plj.domain.base.sys.BaseMessage;

public class Message extends BaseMessage 
{
	private static final long serialVersionUID = 5899443039764561828L;
	
	public void init()
	{
		this.setCreateTime(new Date());
	}
}
