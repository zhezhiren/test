package com.plj.common.tools.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextRegister implements ApplicationContextAware
{

	@Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException
    {
		ContextUtils.setApplicationContext(applicationContext);
    }
}
