package com.plj.domain.response.sys;

import java.io.Serializable;

public class HiddenDangerList implements Serializable
{
	private static final long serialVersionUID = 1679699826706743507L;
	
	private Integer id;
	
	private String areaCode;
	
	private String name;
	
	private String type;
	
	private String pointLongitude;
	
	private String pointLatitude;
	
	private String pointHeight;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPointLongitude() {
		return pointLongitude;
	}

	public void setPointLongitude(String pointLongitude) {
		this.pointLongitude = pointLongitude;
	}

	public String getPointLatitude() {
		return pointLatitude;
	}

	public void setPointLatitude(String pointLatitude) {
		this.pointLatitude = pointLatitude;
	}

	public String getPointHeight() {
		return pointHeight;
	}

	public void setPointHeight(String pointHeight) {
		this.pointHeight = pointHeight;
	}
	
}
