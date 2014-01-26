package com.plj.common.tools.aop;

import org.springframework.core.Ordered;

public class BeforeExample3 implements Ordered
{
	public void doAccessCheck()
	{
		System.out.println("before5");
	}
	
	public void daAccessCheck2()
	{
		System.out.println("before6");
	}

	@Override
	public int getOrder()
	{
		return 10000;
	}
	
}
