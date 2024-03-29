package com.plj.domain.response.sys;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * æ èç?
 * 
 *
 */
@SuppressWarnings("rawtypes")
public class TreeBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String text;
	private String id;
	private boolean disabled;
	private boolean leaf;
	private String href="";
	private String uiProvider;//å½åèç¹UIæä¾ç±?
	private String iconCls="";
	private String hrefTarget="";
	private HashMap attributes;
	private List<TreeBean> children;
	private boolean expanded; //æ¯å¦å±å¼
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(String leaf) {
		if(leaf!=null && leaf.equals("1"))
		this.leaf = true;
		else this.leaf = false;
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
	public List<TreeBean> getChildren() {
		return children;
	}
	public void setChildren(List<TreeBean> children) {
		this.children = children;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
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
