package com.plj.dao.sys;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.plj.domain.decorate.sys.User;

/**
 * 
 * 表user操作对象，本对象暂时未启�?
 * @author zhengxing
 * @version 1.0
 * @date 2013/2/20
 */
@Repository
public interface UserDao 
{
	public Integer save(User user);
	
	public User getUserByIdtest1(String userId);
	
	@SuppressWarnings("rawtypes")
	public User getUserByIdtest2(Map map);
	
	public Integer deleteById(String userId);
	
}
