package com.plj.domain.request.common;

import java.io.Serializable;
import java.util.List;

public class DeleteByLongIds implements Serializable 
{

	private static final long serialVersionUID = 7682979427439614702L;
	private  List<Long> ids;

	public List<Long> getIds() 
	{
		return ids;
	}

	public void setIds(List<Long> ids) 
	{
		this.ids = ids;
	}
}
