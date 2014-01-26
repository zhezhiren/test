/*package com.plj.common.tools.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

public class AroundExample 
{
	@Around("execution(* com.plj.service..*.*(..))")
	public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable
	{
		//
		Object obj = pjp.proceed();
		return obj;
	}
}
*/