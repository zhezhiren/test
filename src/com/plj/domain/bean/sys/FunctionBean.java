package com.plj.domain.bean.sys;

import java.io.Serializable;


public class FunctionBean implements Serializable
{
	private static final long serialVersionUID = 4461352831466225247L;
	
	private String funccode;
	private String funcname;
	private String funcdesc;
	private String funcaction;
	
	public String getFunccode() {
		return funccode;
	}
	public void setFunccode(String funccode) {
		this.funccode = funccode;
	}
	public String getFuncname() {
		return funcname;
	}
	public void setFuncname(String funcname) {
		this.funcname = funcname;
	}
	public String getFuncdesc() {
		return funcdesc;
	}
	public void setFuncdesc(String funcdesc) {
		this.funcdesc = funcdesc;
	}
	public String getFuncaction() {
		return funcaction;
	}
	public void setFuncaction(String funcaction) {
		this.funcaction = funcaction;
	}
}
