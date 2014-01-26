package com.plj.dao.sys;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.plj.domain.decorate.sys.ConRoleFunction;

/**
 * 表ac_rolefunc操作对象
 * @author zhengxing
 * @version 1.0
 * @date 2013/2/20
 */
@Repository
public interface ConRoleFunctionDao 
{
	/**
	 * 根据角色id，获取有权限的功能�?
	 * @param roleId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> fetchFuncs(String roleId);
	
	/**
	 * 根据角色id删除对应的角色与功能的关联关系�?
	 * @param roleId
	 * @return
	 */
	public Integer deleteFuncByRole(String roleId);
	
	/**
	 * 授予角色相应的功�?即新增关联关�?
	 * @param bean
	 * @return
	 */
	public Integer addRolePerm(ConRoleFunction bean);
}
