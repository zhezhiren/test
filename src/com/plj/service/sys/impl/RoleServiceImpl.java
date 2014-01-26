package com.plj.service.sys.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plj.dao.sys.ConRoleFunctionDao;
import com.plj.dao.sys.RoleDao;
import com.plj.domain.decorate.sys.ConRoleFunction;
import com.plj.domain.decorate.sys.Role;
import com.plj.domain.request.sys.GetAllRoles;
import com.plj.service.sys.RoleService;

@Transactional
@Service("roleService")
public class RoleServiceImpl implements RoleService
{
	
	public Role insertRole(Role role)
	{
		roleDao.save(role);
		return role;
	}
	
//	public Role updateRole(Role role)
//	{
//		roleDao.update(role);
//		return role;
//	}
	public Integer updateRole(Role role){
		return roleDao.update(role);
	}
	public Integer deleteRoles(List<String> roleIds)
	{
		int count = 0;
		if(null != roleIds)
		{
			for(int i = 0; i < roleIds.size(); i++)
			{
				String roleId = roleIds.get(i);
				if(StringUtils.isNotBlank(roleId))
				{
					count += roleDao.delete(roleIds.get(i));
				}
			}
		}
		return count;
	}
	
	@Override
	public List<Role> loadAllRoles() 
	{
		return roleDao.loadAllRoles();
	}
	
	public List<Role> getAllRoles(GetAllRoles bean)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("pagination", bean);
		return roleDao.getAllRoles(map);
	}

	@Override
	public boolean checkRoleIdUsable(String roleId) 
	{
		int a = roleDao.roleIdExists(roleId);
		if(a==0) return true;
		return false;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> getRoleDetailTree() 
	{
		return roleDao.getRoleDetailTree();
	}
	@Override
	public boolean onAuthorization(String roleId, List<String> funcs) 
	{
		roleFunctionDao.deleteFuncByRole(roleId);
		if((funcs!=null) && (!funcs.isEmpty()))
		{
			for(int i = 0; i < funcs.size(); i++ )
			{
				if(StringUtils.isNotBlank(funcs.get(i)))
				{
					ConRoleFunction entity = new ConRoleFunction();
					entity.setRoleId(Integer.parseInt(roleId));
					entity.setFunctionCode(Integer.parseInt(funcs.get(i).trim()));
					roleFunctionDao.addRolePerm(entity);
				}
			}
		}
		return true;
	}

	@Override
	public void updateRoleCache() 
	{
		// TODO 
		
	}
	
	@SuppressWarnings("rawtypes")
	public List<Map> fetchFuncs(String roleId)
	{
		return roleFunctionDao.fetchFuncs(roleId);
	}
	
	public List<Role> loadOperatorRolesById(String operatorId){
		return roleDao.loadOperatorRolesById(operatorId);
	}
	@Autowired
	private RoleDao roleDao;
	

	
	@Autowired
	ConRoleFunctionDao roleFunctionDao;



	@Override
	public Boolean checkRoleNameExists(String roleName) {
		return roleDao.getRoleNameExists(roleName)>0;
	}





}
