package com.plj.common.tools.spring;

import org.springframework.context.ApplicationContext;

public class ContextUtils 
{
	private static ApplicationContext applicationContext;
		

	public static void setApplicationContext(ApplicationContext applicationContext)
	{
		synchronized (ContextUtils.class)
		{
		   ContextUtils.applicationContext = applicationContext;
		   ContextUtils.class.notifyAll();
		}
	}
	
	public static ApplicationContext getApplicationContext()
	{
		synchronized (ContextUtils.class) 
		{
		    while (applicationContext == null) 
		    {
			    try 
			    {
			    	ContextUtils.class.wait(60000);
			    	if (applicationContext == null) 
			    	{
			    	}
			    } catch (InterruptedException ex) 
			    {
			    }
		    }
		    return applicationContext;
		}
	}
	
	public static Object getBean(String name)
	{
		return getApplicationContext().getBean(name);
	}
}
