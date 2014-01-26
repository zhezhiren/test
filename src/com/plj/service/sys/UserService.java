package com.plj.service.sys;

import com.plj.domain.decorate.sys.User;

/**
 * 用户逻辑服务接口
 * @author zhengxing
 * @version 1.0
 * @date 2013.1.17
 */
public interface UserService 
{
	public User addNewUser(User user);
	
	public User getUserByIdtest1(String userId);
	
	public User getUserByIdtest2(String userId);
	
	public User deleteById(String userId);
}
