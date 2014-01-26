package com.plj.dao.sys;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.plj.domain.decorate.sys.Role;

import java.util.HashMap;

/**
 * 表ac_role操作对象
 * @author 
 *
 */
@SuppressWarnings("rawtypes")
@Repository
public interface RoleDao 
{
	/**
	 * 新增角色记录
	 * @param role
	 * @return
	 */
	public Integer save(Role role);
	
//	/**
//	 * 修改角色记录
//	 * @param role
//	 * @return
//	 */
//	public Role update(Role role);
	/**
	 * 修改角色记录
	 * @param role
	 * @return
	 */
	public Integer update(Role role);
	/**
	 * 删除角色记录
	 * @param roleId
	 * @return
	 */
	public Integer delete(String roleId);
	
	/**
	 * 查询功能
	 * @param map
	 * @return
	 */
	public List<Role> loadOperatorRolesById(String operatorId);
	
	/**
	 * 获取�?��角色
	 * @return
	 */
	public List<Role> loadAllRoles();
	
	/**
	 * 获取角色功能详情
	 * @return
	 */
	public List<Map> getRoleDetailTree();
	
	/**
	 * 根据查询条件获取角色列表
	 * @param map
	 * @return
	 */
	public List<Role> getAllRoles(Map map);
	
	/**
	 * 判断当前角色是否已经存在
	 * @param roleId
	 * @return
	 */
	public Integer roleIdExists(String roleId);

	/**
	 * 获取角色名称已经存在的个�?
	 * @param roleName
	 * @return
	 */
	public int getRoleNameExists(String roleName);
}
