package com.plj.common.tools.logbak;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class LogbackConfigListener implements ServletContextListener 
{
    public void contextInitialized(ServletContextEvent event)
    {
        LogbackWebConfigurer.initLogging(event.getServletContext());
    }

    public void contextDestroyed(ServletContextEvent event) 
    {
        LogbackWebConfigurer.shutdownLogging(event.getServletContext());
    }
}
