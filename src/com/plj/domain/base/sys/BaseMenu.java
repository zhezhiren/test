package com.plj.domain.base.sys;

import java.io.Serializable;

/**
 * 表ac_menu映射对象
 * @author zhengxing
 * @version 1.0
 * @date 2013/2/20
 */
public class BaseMenu implements Serializable
{
	private static final long serialVersionUID = -1031270388101549550L;
	
	private Integer menuId;
	
	private String menuName;
	
	private String menuLabel;
	
	private String menuCode;
	
	private String isLeaf;
	
	private String menuAction;
	
	private String parameter;
	
	private String uientry;
	
	private Integer menuLevel;
	
	private String rootId;
	
	private Integer parentsId;
	
	private Integer displayOrder;
	
	private String imagePath;
	
	private String expandPath;
	
	private String menuSeq;
	
	private String openMode;
	
	private Integer subCount;
	
	private Integer appId;
	
	private Integer funcCode;

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
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

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getUientry() {
		return uientry;
	}

	public void setUientry(String uientry) {
		this.uientry = uientry;
	}

	public Integer getMenuLevel() {
		return menuLevel;
	}

	public void setMenuLevel(Integer menuLevel) {
		this.menuLevel = menuLevel;
	}

	public String getRootId() {
		return rootId;
	}

	public void setRootId(String rootId) {
		this.rootId = rootId;
	}

	public Integer getParentsId() {
		return parentsId;
	}

	public void setParentsId(Integer parentsId) {
		this.parentsId = parentsId;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getExpandPath() {
		return expandPath;
	}

	public void setExpandPath(String expandPath) {
		this.expandPath = expandPath;
	}

	public String getMenuSeq() {
		return menuSeq;
	}

	public void setMenuSeq(String menuSeq) {
		this.menuSeq = menuSeq;
	}

	public String getOpenMode() {
		return openMode;
	}

	public void setOpenMode(String openMode) {
		this.openMode = openMode;
	}

	public Integer getSubCount() {
		return subCount;
	}

	public void setSubCount(Integer subCount) {
		this.subCount = subCount;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public Integer getFuncCode() {
		return funcCode;
	}

	public void setFuncCode(Integer funcCode) {
		this.funcCode = funcCode;
	}


	
}
