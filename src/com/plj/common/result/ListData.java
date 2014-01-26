package com.plj.common.result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListData<T> implements Serializable
{
	private static final long serialVersionUID = 6222999770496011608L;
	
	public ListData()
	{
	}
	
	public ListData(Long totalProperty, List<T> arr)
	{
		this.totalProperty = totalProperty;
		this.arr = arr;
	}
	
	private Long totalProperty;
	
	private List<T> arr;

	public Long getTotalProperty() {
		return totalProperty;
	}

	public void setTotalProperty(Long totalProperty) {
		this.totalProperty = totalProperty;
	}

	public List<T> getArr() {
		if(null == arr)
		{
			arr = new ArrayList<T>(0);
		}
		return arr;
	}

	public void setArr(List<T> arr) {
		this.arr = arr;
	}
	
	
}
