package com.plj.common.tools.springmvc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.plj.common.session.UserSession;

public class AccessControlInterceptor extends HandlerInterceptorAdapter 
{
	private String text_html = "/base/loginPage.jspx";
	
	private String application_json="/base/loginWarning.jspx";
	
    private String[] whiteList = null;
    public String[] getWhiteList() 
    {
        return whiteList;
    }
    public void setWhiteList(String[] whiteList) 
    {
        this.whiteList = whiteList;
    }
    
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
        Object handler) throws IOException, ServletException 
    {
    	if(handler.getClass().getName().startsWith("com.dhcc.spring.action.exist"))
    	{
    		return true;
    	}
        //如果是白名单则允许访问
    	
        String contextPath = request.getContextPath();
        if ("/".equals(contextPath)) 
        {
            contextPath = "";
        }
        String requestUri = request.getRequestURI();
        for (String whiteUri : whiteList) 
        {
            whiteUri = contextPath + whiteUri;
            if (requestUri.startsWith(whiteUri)) 
            {
                return true;
            }
        }
        if(null == request.getSession().getAttribute(UserSession.USER_SESSION))
        {
        	String contentType = request.getContentType();
			if(contentType != null && contentType.indexOf("application/json") != -1 || requestUri.endsWith(".json"))
			{
				request.getRequestDispatcher(application_json).forward(request, response);
				return false;
			}
			request.getRequestDispatcher(text_html).forward(request, response);
        	return false;
        }
        return true;
    }
}
