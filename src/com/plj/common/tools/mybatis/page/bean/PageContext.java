package com.plj.common.tools.mybatis.page.bean;

/**
 * 这是一个与线程相关的分页信息类，保存了当前运行线程的分页状态
 * @author zhengxing
 * @see Pagination;
 * @version 1.0
 */
public class PageContext extends Pagination
{
	private static ThreadLocal<PageContext> context = new ThreadLocal<PageContext>();
	
	private static ThreadLocal<Boolean> isNeedPagination = new ThreadLocal<Boolean>();
	
	public static PageContext getContext()
	{
		PageContext ci = context.get();
		if(ci == null)
		{
			ci = new PageContext();
			context.set(ci);
		}
		return ci;
	}
	
	public static void setIsNeedPagination(boolean b)
	{
		isNeedPagination.set(b);
	}
	
	public static boolean getIsNeedPagination()
	{
		Boolean result = isNeedPagination.get();
		if(result == null)
		{
			result = false;
			isNeedPagination.set(result);
		}
		return result;
	}
	
	public  static void removeContext()
	{
		isNeedPagination.remove();
		context.remove();
	}
}
