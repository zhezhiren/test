package com.plj.common.tools.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.Ordered;

@Aspect
public class BeforeExample2 implements Ordered
{
	@Before("execution(* com.plj.service..*.*(..))")
	public void doAccessCheck(JoinPoint jp)
	{
		System.out.println("before3");
	}
	
	@Before("execution(* com.plj.service..*.*(..))")
	public void daAccessCheck2(JoinPoint jp)
	{
		System.out.println("before4");
	}

	@Override
	public int getOrder()
	{
		return 100;
	}
	
}
