package com.plj.domain.response.sys;

import java.io.Serializable;

public class AreaBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8877604996379798815L;
	private String area;
	private String upArea;
	private String areaName;
	private Integer nodelvl;
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getUpArea() {
		return upArea;
	}
	public void setUpArea(String upArea) {
		this.upArea = upArea;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public Integer getNodelvl() {
		return nodelvl;
	}
	public void setNodelvl(Integer nodelvl) {
		this.nodelvl = nodelvl;
	}

}
