package com.plj.service.sys.impl;

import java.util.HashMap;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plj.dao.sys.UserDao;
import com.plj.domain.decorate.sys.User;
import com.plj.service.sys.UserService;

/**
 * 用户逻辑服务实现�?
 * @author zhengxing
 * @version 1.0
 * @date 2013.1.17
 */
@Transactional
@Service("userService")
public class UserServiceImpl implements UserService
{
	public User addNewUser(User user)
	{
		user.setId(StringUtils.remove(UUID.randomUUID().toString(), '-'));
		userDao.save(user);
		return user;
	}
	
	public User getUserByIdtest1(String id)
	{
		return userDao.getUserByIdtest1(id);
	}
	
	public User getUserByIdtest2(String id)
	{
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userId", id);
		return userDao.getUserByIdtest1(id);
	}
	
	public User deleteById(String userId)
	{
		User user = userDao.getUserByIdtest1(userId);
		if(user != null)
		{
			if("6d6c88db2df4475a809872acdf0b85a1".equals(userId))
			{
				return null;
			}
			userDao.deleteById(userId);
			return user;
		}
		return null;
	}
	
	@Autowired
	UserDao userDao;
}
