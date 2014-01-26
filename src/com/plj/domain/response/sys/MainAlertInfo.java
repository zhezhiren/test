package com.plj.domain.response.sys;

import java.io.Serializable;
import java.util.Date;

/**
 * 显示在首页上的预警信�?
 * 
 * @author bin
 * 
 */
public class MainAlertInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7165141306305355642L;
	
	private String id;
	private String icon;
	private String station;
	private String publishTime;
	private String type;
	private String level;
	private String content;
	private Date start;
	private Date end;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}


}
