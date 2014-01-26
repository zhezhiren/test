package com.plj.common.tools.logbak;

import javax.servlet.ServletContextEvent;  
import javax.servlet.ServletContextListener;  

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

/**
 * �����һ��Ц��������
 * @author Administrator
 *
 */
public class LogbackConfigListener2 implements ServletContextListener
{
	private static final Logger logger = LoggerFactory.getLogger(LogbackConfigListener2.class);  
    
    private static final String CONFIG_LOCATION = "logbackConfigLocation";  
    @Override  
    public void contextInitialized(ServletContextEvent event) {  
        //��web.xml�м���ָ���ļ�������־�����ļ�  
        String logbackConfigLocation = event.getServletContext().getInitParameter(CONFIG_LOCATION);  
        String fn = event.getServletContext().getRealPath(logbackConfigLocation);  
        try {  
            LoggerContext loggerContext = (LoggerContext)LoggerFactory.getILoggerFactory();  
            loggerContext.reset();  
            JoranConfigurator joranConfigurator = new JoranConfigurator();  
            joranConfigurator.setContext(loggerContext);  
            joranConfigurator.doConfigure(fn);  
            logger.debug("loaded slf4j configure file from {}", fn);  
        }  
        catch (JoranException e) {  
            logger.error("can loading slf4j configure file from " + fn, e);  
        }  
    }  
    @Override  
    public void contextDestroyed(ServletContextEvent event) {  
    }  
}
