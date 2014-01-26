package com.plj.common.tools.ehcache;

import org.apache.ibatis.cache.decorators.LoggingCache;

public class LoggingEhcache  extends LoggingCache 
{
	public LoggingEhcache(final String id) 
	{
        super(new EhcacheCache(id));
    }
}
