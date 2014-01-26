package com.plj.domain.request.common;

import java.io.Serializable;
import java.util.List;

public class DeleteByIntegerIds implements Serializable 
{
	private static final long serialVersionUID = -7285382955032588296L;
	
	private  List<Integer> ids;

	public List<Integer> getIds() 
	{
		return ids;
	}

	public void setIds(List<Integer> ids) 
	{
		this.ids = ids;
	}
}
