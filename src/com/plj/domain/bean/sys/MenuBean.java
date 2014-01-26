package com.plj.domain.bean.sys;

import java.io.Serializable;

/**
 * 用于接收页面传来的数�?
 *
 */
public class MenuBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String menuId;
	private String menuName;
	private String menuLabel;
	private String menuCode;
	private String isLeaf;
	private String menuAction;
	private String menuLevel;
	private String parentsId;
	private String displayOrder;
	private String openMode;
	private String subCount;
	private String appId;
	private String funcCode;
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuLabel() {
		return menuLabel;
	}
	public void setMenuLabel(String menuLabel) {
		this.menuLabel = menuLabel;
	}
	public String getMenuCode() {
		return menuCode;
	}
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	public String getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}
	public String getMenuAction() {
		return menuAction;
	}
	public void setMenuAction(String menuAction) {
		this.menuAction = menuAction;
	}
	public String getMenuLevel() {
		return menuLevel;
	}
	public void setMenuLevel(String menuLevel) {
		this.menuLevel = menuLevel;
	}
	public String getParentsId() {
		return parentsId;
	}
	public void setParentsId(String parentsId) {
		this.parentsId = parentsId;
	}
	public String getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(String displayOrder) {
		this.displayOrder = displayOrder;
	}
	public String getOpenMode() {
		return openMode;
	}
	public void setOpenMode(String openMode) {
		this.openMode = openMode;
	}
	public String getSubCount() {
		return subCount;
	}
	public void setSubCount(String subCount) {
		this.subCount = subCount;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getFuncCode() {
		return funcCode;
	}
	public void setFuncCode(String funcCode) {
		this.funcCode = funcCode;
	}
	
	
	

	
	

}
