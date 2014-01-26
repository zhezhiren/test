package com.plj.common.tools.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.DeclareParents;
import org.springframework.core.Ordered;

import com.alibaba.fastjson.JSON;
import com.plj.common.tools.aop.interfaces.TestInterface1;
import com.plj.common.tools.aop.interfaces.impl.DefaultImpl;

@Aspect
public class BeforeExample implements Ordered
{
	@DeclareParents(value = "com.plj.service..*",defaultImpl=DefaultImpl.class)
	public static TestInterface1 tif;
	
	@Before("execution(* com.plj.service..*.*(..)) &&" + "this(testInf)" )
	public void doAccessCheck(JoinPoint jp, TestInterface1 testInf)
	{
		testInf.test();
		System.out.println(JSON.toJSON(null));
		if(jp != null)
		{
			System.out.println(jp);
			System.out.println(jp.getArgs().length);
			for(int i = 0; i < jp.getArgs().length; i++)
			{
				//System.out.println(JSON.toJSON(jp.getArgs()[i]));
				System.out.println(i);
			}
		}
		System.out.println("before1");
		
	}
	
	@Before("execution(* com.plj.service..*.*(..))")
	public void daAccessCheck2()
	{
		System.out.println("before2");
	}

	@Override
	public int getOrder()
	{
		return 10;
	}
	
}
