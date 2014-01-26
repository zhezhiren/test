package com.plj.domain.request.sys;

import com.plj.common.tools.mybatis.page.bean.Pagination;

public class GetAreaRequest  extends Pagination{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8719877252642632436L;
	private String area;
	private String areaName;
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

}
