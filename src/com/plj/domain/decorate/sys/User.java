package com.plj.domain.decorate.sys;

import com.plj.domain.base.sys.BaseUser;

public class User extends BaseUser
{
	public User()
	{
		
	}
	
	public User(String id, String email, String loginName, String name,
			String password, String departId, Integer roleId) {
		super();
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5483065283205298407L;

}
