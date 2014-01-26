package com.plj.domain.request.sys;

import com.plj.common.tools.mybatis.page.bean.Pagination;

public class MenuPage extends Pagination {

	private static final long serialVersionUID = 5965594997072966784L;
	
	private String menuCode;
	private String menuLevel;
	private String menuName;
	private String menuLabel;
	
	public String getMenuCode() {
		return menuCode;
	}
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	public String getMenuLevel() {
		return menuLevel;
	}
	public void setMenuLevel(String menuLevel) {
		this.menuLevel = menuLevel;
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
	

}
