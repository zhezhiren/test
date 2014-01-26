package com.plj.domain.response.sys;

import java.io.Serializable;

/**
 * 响应LoadMenuById的数据对应的�?
 * @author Administrator
 *
 */
public class MenuLoadInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String m_parentMenuLabel;
	private String m_menuName;
	private String m_menuLabel;
	private String m_menuCode;
	private String m_isLeaf;
	private String m_funcAtion;
	private String m_funcName;
	private int m_desplayOrder;
	private int m_menuLevel;
	private int m_subCount;
	private int m_funcCode;
	private int m_parentsId;
	public int getM_parentsId() {
		return m_parentsId;
	}
	public void setM_parentsId(int id) {
		m_parentsId = id;
	}
	public String getM_parentMenuLabel() {
		return m_parentMenuLabel;
	}
	public void setM_parentMenuLabel(String m_parentMenuLabel) {
		this.m_parentMenuLabel = m_parentMenuLabel;
	}
	public String getM_menuName() {
		return m_menuName;
	}
	public void setM_menuName(String m_menuName) {
		this.m_menuName = m_menuName;
	}
	public String getM_menuLabel() {
		return m_menuLabel;
	}
	public void setM_menuLabel(String m_menuLabel) {
		this.m_menuLabel = m_menuLabel;
	}
	public String getM_menuCode() {
		return m_menuCode;
	}
	public void setM_menuCode(String m_menuCode) {
		this.m_menuCode = m_menuCode;
	}
	public String getM_isLeaf() {
		return m_isLeaf;
	}
	public void setM_isLeaf(String m_isLeaf) {
		this.m_isLeaf = m_isLeaf;
	}
	public String getM_funcAtion() {
		return m_funcAtion;
	}
	public void setM_funcAtion(String m_funcAtion) {
		this.m_funcAtion = m_funcAtion;
	}
	public String getM_funcName() {
		return m_funcName;
	}
	public void setM_funcName(String m_funcName) {
		this.m_funcName = m_funcName;
	}
	public int getM_desplayOrder() {
		return m_desplayOrder;
	}
	public void setM_desplayOrder(int m_desplayOrder) {
		this.m_desplayOrder = m_desplayOrder;
	}
	public int getM_menuLevel() {
		return m_menuLevel;
	}
	public void setM_menuLevel(int m_menuLevel) {
		this.m_menuLevel = m_menuLevel;
	}
	public int getM_subCount() {
		return m_subCount;
	}
	public void setM_subCount(int m_subCount) {
		this.m_subCount = m_subCount;
	}
	public int getM_funcCode() {
		return m_funcCode;
	}
	public void setM_funcCode(int m_funcCode) {
		this.m_funcCode = m_funcCode;
	}
	
	
	
}


