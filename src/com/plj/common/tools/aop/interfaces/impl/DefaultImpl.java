package com.plj.common.tools.aop.interfaces.impl;

import com.plj.common.tools.aop.interfaces.TestInterface1;

public class DefaultImpl implements TestInterface1 {

	@Override
	public void test()
	{
		System.out.println("test:" + this.getClass().getName());
	}

}
