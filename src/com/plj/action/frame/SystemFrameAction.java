package com.plj.action.frame;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/frame/admin")
public class SystemFrameAction
{
	/**
	 * 获取用用管理页面
	 * @return 用户管理页面�?��位置
	 */
	@RequestMapping(value = "/operator.jspx")
	public String operatorPage(HttpServletRequest request
			, HttpServletResponse response)
	{
		return "admin/operator";
	}
	
	@RequestMapping(value = "/main.jspx")
	public String mainPage(HttpServletRequest request
			, HttpServletResponse response)
	{
		return "admin/main_nx2";
	}
	
	@RequestMapping(value = "/mainnx.jspx")
	public String mainPagenx(HttpServletRequest request
			, HttpServletResponse response)
	{
		return "admin/main_nx";
	}
	
	@RequestMapping(value = "/main_yubaoyuan.jspx")
	public String mainYuBaoYuanPage(HttpServletRequest request
			, HttpServletResponse response)
	{
		return "admin/main_yubaoyuan";
	}
	
	@RequestMapping(value = "/menu.jspx")
	public String menuPage(HttpServletRequest request
			, HttpServletResponse response)
	{
		return "admin/menu";
	}
	
	@RequestMapping(value = "role.jspx")
	public String rolePage(HttpServletRequest request
			, HttpServletResponse response)
	{
		return "admin/role";
	}
	
	@RequestMapping(value = "/applicationModule.jspx")
	public String applicationModulePage(HttpServletRequest request
			, HttpServletResponse response)
	{
		return "admin/applicationModule";
	}
	
	@RequestMapping(value = "/org.jspx")
	public String orgPage(HttpServletRequest request
			, HttpServletResponse response)
	{
		return "admin/org";
	}
	/**
	 * 获取交接班管理页�?
	 * @return 交接班管理页面所在位�?
	 */
	@RequestMapping(value = "/dutyManage.jspx")
	public String dutyManagePage(HttpServletRequest request
			, HttpServletResponse response)
	{
		return "admin/dutyManage";
	}
	
	/**
	 * 获取欢迎页面
	 * @return 欢迎页面�?��位置
	 */
	@RequestMapping(value = "/welcome.jspx")
	public String welcomePage(HttpServletRequest request
			, HttpServletResponse response)
	{
		return "admin/welcome";
	}
	
	
	/**
	 * 获取欢迎页面
	 * @return 欢迎页面�?��位置
	 */
	@RequestMapping(value = "/baseDataManage.jspx")
	public String baseDataManagePage(HttpServletRequest request
			, HttpServletResponse response)
	{
		return "admin/baseDataManage";
	}
	
	@RequestMapping(value = "/messageManager.jspx")
	public String messageManager()
	{
		return "admin/messageManager";
	}
}
