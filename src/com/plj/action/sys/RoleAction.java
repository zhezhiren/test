package com.plj.action.sys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.plj.common.error.MyError;
import com.plj.common.result.JsonResult;
import com.plj.common.result.ListData;
import com.plj.domain.bean.sys.TreeBasic;
import com.plj.domain.bean.sys.TreeUtil;
import com.plj.domain.decorate.sys.Role;
import com.plj.domain.request.sys.AuthorizationBean;
import com.plj.domain.request.sys.DeleteRole;
import com.plj.domain.request.sys.GetAllRoles;
import com.plj.domain.request.sys.RoleBean;
import com.plj.domain.response.sys.TreeBean;
import com.plj.service.sys.RoleService;

/**
 * 角色管理相关请求处理对象
 * @author zhengxing
 *
 */
@Controller
@RequestMapping(value="/role")
public class RoleAction 
{
	/**
	 * 获取角色权限详情接口
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/getRoleDetailTree.json")
	@ResponseBody
	public Object getRoleDetailTree(HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		List<Map> list = roleService.getRoleDetailTree();
		List<TreeBasic> tree = new ArrayList<TreeBasic>();
		TreeBasic top = new TreeBasic();
		top.setId("root-node");
		top.setName("可�?权限");
		top.setUiProvider("checkBox");
		top.setExpanded(true);
		top.setParentId("");
		tree.add(top);
		for(Map map : list){
			TreeBasic tb = new TreeBasic();
			tb.setId(map.get("ID").toString()); //可以外挂属�?
			tb.setName(map.get("TEXT").toString());
			tb.setUiProvider("checkBox");
			tb.setExpanded(true);
			tb.setParentId(map.get("PARENTID").toString());
			tree.add(tb);
		}
		List<TreeBean> result = TreeUtil.onTree(tree,"","");
		return JSON.toJSON(result);
	}
	
	/**
	 * 保存角色接口
	 * @param roleBean
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/saveRole.json")
	@ResponseBody
	public Object saveRole(RoleBean roleBean, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		JsonResult result = checkRoleSave(roleBean);
		if(!result.isSuccess())
		{
			return result;
		}
		Role role = (Role) result.getCacheData(true);
		roleService.insertRole(role);
		return JSON.toJSON(result);
	}
	
	/**
	 * 根据角色id列表，删除角色�?
	 * @param bean
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteRole.json")
	@ResponseBody
	public Object deleteRole(DeleteRole bean, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		JsonResult result = checkRoleDelete(bean);
		if(!result.isSuccess())
		{
			return result;
		}
		roleService.deleteRoles(bean.getRoleIds());
		return JSON.toJSON(result);
	}
	
	/**
	 * 判断角色的Id是否已经使用�?
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/roleIdExists.json")
	@ResponseBody
	public Object roleIdExists(String id, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		JsonResult result = new JsonResult();
		if(StringUtils.isBlank(id))
		{
			MyError err = new MyError(MyError.SystemCode.ROLE_ID_IS_NULL, MyError.SystemMsg.ROLE_ID_IS_NULL);
			result.addError(err);
			return result;
		}
		Boolean exists = roleService.checkRoleIdUsable(id);
		result.setData(exists);
		return JSON.toJSON(result);
	}
	
	/**
	 * 判断角色的Name是否已经使用�?
	 * @author bin
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/roleNameExists.json")
	@ResponseBody
	public Object roleNameExists(String roleName, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		JsonResult result = new JsonResult();
		if(StringUtils.isBlank(roleName))
		{
			MyError err = new MyError(MyError.SystemCode.ROLE_NAME_IS_NULL, MyError.SystemMsg.ROLE_NAME_IS_NULL);
			result.addError(err);
			return result;
		}
		Boolean exists = roleService.checkRoleNameExists(roleName);
		result.setData(exists);
		return JSON.toJSON(result);
	}
	
	/**
	 * 根据查询条件获取角色分页记录列表请求
	 * @author gudingyin
	 * @param seachKey
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getAllRoles.json")
	@ResponseBody
	public Object getAllRoles(GetAllRoles seachKey, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		JsonResult result = new JsonResult();
		List<Role> roles = roleService.getAllRoles(seachKey);
		List<RoleBean> beans = localRolesToBeans(roles);
		ListData<RoleBean> lr = new ListData<RoleBean>(seachKey.getTotalCount(), beans);
		result.setData(lr);
		return JSON.toJSON(result);
	}
	
	/**
	 * 获取角色对应的功能列表请�?
	 * @param roleId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/getRoleFunctions.json")
	@ResponseBody
	public Object getRoleFunctions(String roleId, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		JsonResult result = new JsonResult();
		if(StringUtils.isBlank(roleId))
		{
			result.addError(new MyError(MyError.SystemCode.ROLE_ID_IS_NULL,MyError.SystemMsg.ROLE_ID_IS_NULL));
			return result;
		}
		List<Map> functions = roleService.fetchFuncs(roleId);
		result.setData(functions);
		return JSON.toJSON(result);
	}
	
	/**
	 * 对角色授权请求�?
	 * @param bean
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/onAuthorization.json")
	@ResponseBody
	public Object onAuthorization(AuthorizationBean bean, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		JsonResult result = checkAuthorization(bean);
		if(!result.isSuccess())
		{
			return result;
		}
		roleService.onAuthorization(bean.getRoleId(), bean.getFunctionCodes());
		result.setData(true);
		return JSON.toJSON(result);
	}
	
	/**
	 * 修改角色信息
	 * @author xujianquan
	 * @param bean
	 * @return
	 */
	@RequestMapping(value="/updateRole.json")
	@ResponseBody
	public Object updateRole(RoleBean bean,HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		JsonResult jr = checkRoleSave(bean);
		Role role  = (Role)jr.getCacheData(true);
		Integer result = roleService.updateRole(role);
		if(result.intValue()!= 0){
			jr.setData(true);
		}
		return jr;
	}
	
	
	/**
	 * �?��新增或修改角色时，角色信息的完整�?
	 * @param role
	 * @return
	 */
	private JsonResult checkRoleSave(RoleBean role)
	{
		JsonResult result = new JsonResult();
		if(null == role)
		{
			MyError err = new MyError(MyError.SystemCode.ROLE_NAME_IS_NULL, MyError.SystemMsg.ROLE_IS_NULL);
			result.addError(err);
			return result;
		}
		if(StringUtils.isBlank(role.getRoleName()))
		{
			MyError err = new MyError(MyError.SystemCode.ROLE_NAME_IS_NULL, MyError.SystemMsg.ROLE_IS_NULL);
			result.addError(err);
		}
		if(null == role.getAppId() || 0 == role.getAppId())
		{
			MyError err = new MyError(MyError.SystemCode.ROLE_NAME_IS_NULL, MyError.SystemMsg.ROLE_IS_NULL);
			result.addError(err);
		}
		if(StringUtils.isBlank(role.getRoleType()))
		{
			MyError err = new MyError(MyError.SystemCode.ROLE_TYPE_IS_NULL, MyError.SystemMsg.ROLE_TYPE_IS_NULL);
			result.addError(err);
		}
		if(result.isSuccess())
		{
			result.setData(changeRoleTOLocal(role));
		}
		return result;
	}
	
	/**
	 * 把页面参数转换为本地参数
	 * @param roleBean
	 * @return
	 */
	private Role changeRoleTOLocal(RoleBean roleBean)
	{
		Role role = new Role();
		if(null != roleBean.getRoleId())
		{
			role.setRoleId(Integer.valueOf(roleBean.getRoleId()));
		}
		role.setRoleName(roleBean.getRoleName());
		role.setRoleType(roleBean.getRoleType());
		role.setApplicationId(roleBean.getAppId());
		return role;
	}
	
	/**
	 * 验证删除角色请求的参数的正确�?
	 * @param bean
	 * @return
	 */
	private JsonResult checkRoleDelete(DeleteRole bean)
	{
		JsonResult result = new JsonResult();
		if(null == bean || null == bean.getRoleIds())
		{
			MyError err = new MyError(MyError.SystemCode.ROLE_DELETE_IDS_NULL, MyError.SystemMsg.ROLE_DELETE_IDS_NULL);
			result.addError(err);
		}
		return result;
	}
	
	/**
	 * 把角色列表从本地对象转化为页面识别对�?
	 * @param roles
	 * @return
	 */
	private List<RoleBean> localRolesToBeans(List<Role> roles)
	{
		List<RoleBean> beans = null;
		
		if(null != roles && roles.size() > 0)
		{
			beans = new ArrayList<RoleBean>();
			for(int i = 0; i < roles.size(); i++)
			{
				RoleBean bean = localRoleToBean(roles.get(i));
				if(null != bean)
				{
					beans.add(bean);
				}
			}
		}
		return beans;
	}
	
	/**
	 * 把角色从本地转化为页面所�?��的对�?
	 * @param role
	 * @return
	 */
	private RoleBean localRoleToBean(Role role)
	{
		RoleBean bean = null;
		if(null != role)
		{
			bean = new RoleBean();
			bean.setAppId(role.getApplicationId());
			bean.setRoleId(role.getRoleId().toString());
			bean.setRoleName(role.getRoleName());
			bean.setRoleType(role.getRoleType());
		}
		return bean;
	}
	
	/**
	 * 验证角色权限授予时参数的正确�?
	 * @param bean
	 * @return
	 */
	private JsonResult checkAuthorization(AuthorizationBean bean)
	{
		JsonResult js = new JsonResult();
		if(null == bean || null == bean.getRoleId())
		{
			js.addError(new MyError(MyError.SystemCode.AUTHORIZATION_IS_NULL,MyError.SystemMsg.ROLE_ID_IS_NULL));
			return js;
		}
		if(null != bean.getFunctionCodes() && bean.getFunctionCodes().size() > 0)
		{
			for(int i = 0; i < bean.getFunctionCodes().size(); i++)
			{
				if(StringUtils.isBlank(bean.getFunctionCodes().get(i)))
				{
					bean.getFunctionCodes().remove(i);
				}
			}
		}
		return js;
	}
	
	@Autowired
	@Qualifier(value = "roleService")
	private RoleService roleService; 
}
