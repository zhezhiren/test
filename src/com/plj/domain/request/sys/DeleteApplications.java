package com.plj.domain.request.sys;

import java.io.Serializable;
import java.util.List;

public class DeleteApplications implements Serializable
{
	private static final long serialVersionUID = -7842312093533290558L;
	
	private List<Integer> appIds;

	public List<Integer> getAppIds() {
		return appIds;
	}

	public void setAppIds(List<Integer> appIds) {
		this.appIds = appIds;
	}
}
