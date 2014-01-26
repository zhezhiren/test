package com.plj.domain.bean.sys;

import java.io.Serializable;
import java.util.HashMap;
/**
 * tree basic data
 *
 */
@SuppressWarnings("rawtypes")
public class TreeBasic implements Serializable{
	private static final long serialVersionUID = -1975151253871079862L;
	private String id;
	private String name;
	private String parentId;
	private String href;
	private String hrefTarget;
	private String uiProvider; //当前节点UI提供�?
	private HashMap attributes;
	private boolean expanded; //是否展开
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getHrefTarget() {
		return hrefTarget;
	}
	public void setHrefTarget(String hrefTarget) {
		this.hrefTarget = hrefTarget;
	}
	public HashMap getAttributes() {
		return attributes;
	}
	public void setAttributes(HashMap attributes) {
		this.attributes = attributes;
	}
	public String getUiProvider() {
		return uiProvider;
	}
	public void setUiProvider(String uiProvider) {
		this.uiProvider = uiProvider;
	}
	public boolean isExpanded() {
		return expanded;
	}
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	

}
