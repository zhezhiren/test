package com.plj.domain.base.sys;

import java.io.Serializable;

public class BaseArea implements Serializable
{
	private static final long serialVersionUID = 6097998780267207477L;

	private String area;
	
	private String uparea;
	
	private String areaName;
	
	private Integer nodeLvl;
	
	private Float maxLatitude;
	
	private Float minLatitude;
	
	private Float maxLongitude;
	
	private Float minLongitude;

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getUparea() {
		return uparea;
	}

	public void setUparea(String uparea) {
		this.uparea = uparea;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Integer getNodeLvl() {
		return nodeLvl;
	}

	public void setNodeLvl(Integer nodeLvl) {
		this.nodeLvl = nodeLvl;
	}

	public Float getMaxLatitude() {
		return maxLatitude;
	}

	public void setMaxLatitude(Float maxLatitude) {
		this.maxLatitude = maxLatitude;
	}

	public Float getMinLatitude() {
		return minLatitude;
	}

	public void setMinLatitude(Float minLatitude) {
		this.minLatitude = minLatitude;
	}

	public Float getMaxLongitude() {
		return maxLongitude;
	}

	public void setMaxLongitude(Float maxLongitude) {
		this.maxLongitude = maxLongitude;
	}

	public Float getMinLongitude() {
		return minLongitude;
	}

	public void setMinLongitude(Float minLongitude) {
		this.minLongitude = minLongitude;
	}
	
}
