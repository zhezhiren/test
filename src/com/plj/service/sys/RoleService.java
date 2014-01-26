package com.plj.service.sys;

import java.util.List;
import java.util.Map;

import com.plj.domain.decorate.sys.Role;
import com.plj.domain.request.sys.GetAllRoles;

@SuppressWarnings("rawtypes")
public interface RoleService 
{
	/**
	 * 新增角色
	 * @param role
	 * @return
	 */
	public Role insertRole(Role role);
	
//	/**
//	 * 修改角色
//	 * @param role
//	 * @return
//	 */
//	public Role updateRole(Role role);
	
	/**
	 * 修改角色
	 * @param role
	 * @return
	 */
	public Integer updateRole(Role role);
	/**
	 * 根据id列表删除角色
	 * @param roleIds
	 * @return
	 */
	public Integer deleteRoles(List<String> roleIds);
	
	/**
	 * 获取�?��角色
	 * @param bean
	 * @return
	 */		
	public List<Role> loadAllRoles();
	
	/**
	 * 获取角色分页列表
	 * @param bean
	 * @return
	 */
	public List<Role> getAllRoles(GetAllRoles bean);
	
	/**
	 * 判断角色id是否已经存在
	 * @param roleid
	 * @return
	 */
	public boolean checkRoleIdUsable(String roleid);
	
	/**
	 * 获取角色权限列表
	 * @return
	 */
	public List<Map> getRoleDetailTree();
	
	/**
	 * 授予角色权限列表
	 * @param roleId
	 * @param funCodes
	 * @return
	 */
	public boolean onAuthorization(String roleId,List<String> funCodes);
	
	/**
	 * 修建角色缓存信息
	 */
	public void updateRoleCache();
	
	/**
	 * 获取角色的功能组
	 * @param roleId
	 * @return
	 */
	public List<Map> fetchFuncs(String roleId);
	
	/**
	 * 根据操作员ID查询操作员所拥有的角�?
	 * @param map
	 * @return
	 */
	public List<Role> loadOperatorRolesById(String operatorId);

	/**
	 * 判断角色名称是否已经存在
	 * @param roleName
	 * @return
	 */
	public Boolean checkRoleNameExists(String roleName);
	
}
