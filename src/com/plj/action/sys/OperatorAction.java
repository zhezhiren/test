package com.plj.action.sys;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.plj.common.constants.FieldEnum.EmployeesStatus;
import com.plj.common.error.MyError;
import com.plj.common.result.JsonResult;
import com.plj.common.result.ListData;
import com.plj.common.session.UserSession;
import com.plj.common.utils.MD5Utils;
import com.plj.common.utils.OperatorNameUtils;
import com.plj.domain.bean.sys.OperatorBean;
import com.plj.domain.decorate.sys.Employee;
import com.plj.domain.decorate.sys.Operator;
import com.plj.domain.decorate.sys.Role;
import com.plj.domain.request.common.DeleteByIntegerIds;
import com.plj.domain.request.sys.GetOperatorsPage;
import com.plj.domain.request.sys.RoleBean;
import com.plj.domain.request.sys.UpdateOperatorBean;
import com.plj.service.sys.EmployeeService;
import com.plj.service.sys.OperatorService;
import com.plj.service.sys.RoleService;

/**
 * 用户管理功能 对应请求处理对象
 * @author zhengxing
 * @version 1.0
 * @date 2013/2/20
 */
@Controller
@RequestMapping(value="/operator")
public class OperatorAction 
{
	
	@RequestMapping(value="/getOperatorNameMap.json")
	@ResponseBody
	public Object getOperatorNameMap(HttpServletRequest request,
			HttpServletResponse response)
	{
		JsonResult result = new JsonResult();
		String code=(String) request.getSession().getAttribute(UserSession.USER_AREA);
		String[][] map = OperatorNameUtils.getAllOperatorNameMap(code);
		result.setData(map);
		return JSON.toJSON(result);
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/searchOperator.json")
	@ResponseBody
	public Object searchOperator(GetOperatorsPage bean, HttpServletRequest request,
			HttpServletResponse response)
	{
		JsonResult result = new JsonResult();
		if(bean==null){
			result.addError(new MyError("87",""));
			return result;
		}
		HashMap map = new HashMap();
		map.put("pagination", bean);
		if(StringUtils.isNotBlank(bean.getUserId().trim())){
			map.put("userId", bean.getUserId().trim());
		}
		if(StringUtils.isNotBlank(bean.getOperatorName().trim())){
			map.put("operatorName", bean.getOperatorName().trim());
		}
		List<Operator> operators = operatorService.SearchOperator(map);
		ListData<OperatorBean> datas = new ListData<OperatorBean>(
				bean.getTotalCount(), operatorsToPageBeans(operators));
		result.setData(datas);
		return JSON.toJSON(result);
	}
	
	@RequestMapping(value="/deleteOperators.json")
	@ResponseBody
	public Object deleteOperators(DeleteByIntegerIds bean, HttpServletRequest request,
			HttpServletResponse response)
	{
		JsonResult result = new JsonResult();
		int i = operatorService.deleteOperatorByIds(bean.getIds());
		result.setData(i);
		return JSON.toJSON(result);
	}
	
	@RequestMapping(value="/userExists.json")
	@ResponseBody
	public Object userExists(String useId, HttpServletRequest request,
			HttpServletResponse response)
	{	
//		String useId="abc";
		JsonResult result = new JsonResult();
		if(null == useId || "".equals(useId.trim()))
		{
			result.addError(new MyError(MyError.SystemCode.OPERATOR_GET_ID_NULL, MyError.SystemMsg.OPERATOR_GET_ID_NULL));
			return result;
		}
		Operator operator = operatorService.getOperatorByUserName(useId);
		if(null != operator)
		{
			result.addError("","");
//			result.setData(false);//存在时返回false,表示不能使用
		}
		else
		{
			result.setData("可用");//不存在时返回true,表示能使�?
		}
		return JSON.toJSON(result);
	}
	
	static List<OperatorBean> operatorsToPageBeans(List<Operator> operators)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ArrayList<OperatorBean> beans = null;
		if(null != operators && operators.size() > 0)
		{
			beans = new ArrayList<OperatorBean>();
			for(int i = 0; i < operators.size(); i++)
			{
				OperatorBean bean = operatorToPageBean(operators.get(i), format);
				if(null != bean)
				{
					beans.add(bean);
				}
			}
		}
		return beans;
	}
	
	static OperatorBean operatorToPageBean(Operator operator, SimpleDateFormat format)
	{
		OperatorBean bean = null;
		if(null != operator)
		{
			bean = new OperatorBean();
			bean.setOperatorId(operator.getOperatorId());
			bean.setOperatorName(operator.getOperatorName());
			bean.setUserId(operator.getUserId());
			bean.setStatus(operator.getStatus());
			if(null != operator.getLastLoginTime())
			{
				bean.setLastLoginTime(format.format(operator.getLastLoginTime()));
			}
			bean.setEmail(operator.getEmail());
			/*if(operator.getLastLoginTime()!=null){
				bean.setLastLoginTime(operator.getLastLoginTime());
			}*/
		}
		return bean;
	}
	
	@Autowired
	@Qualifier("operatorService")
	OperatorService operatorService;
	
	/**
	 * 根据操作员ID查询操作员所拥有的角�?
	 * 
	 * @author gudingyin
	 * @param bean
	 * @param operatorId
	 * @param List<Map>
	 * @return
	 */
	@RequestMapping(value = "/loadOperatorRolesById.json")
	@ResponseBody
	public Object loadOperatorRolesById(Integer empId,
			HttpServletRequest request, HttpServletResponse response) {
		String operatorId = employeeService.getOperIdByEmpId(empId).toString();
		JsonResult result = checkSearchRoles(operatorId);
		if (!result.isSuccess()) {
			result.addError(new MyError(MyError.SystemCode.ROLE_ID_IS_NULL,MyError.SystemMsg.ROLE_ID_IS_NULL));
			return result;
		}
		List<Role> role = roleService.loadOperatorRolesById(operatorId);
		result.setData(role);
		return JSON.toJSON(result);
	}
	
	
	
	/**
	 * 加载�?��角色信息 用于操作员赋权限
	 * 
	 * @author gudingyin
	 * @param bean
	 * @param roleId
	 * @param List<Map>
	 * @return
	 */
	@RequestMapping(value = "/loadAllRoles.json")
	@ResponseBody
	public Object loadAllRoles(HttpServletRequest request,
			HttpServletResponse response) {
		JsonResult result = new JsonResult();
		List<Role> role = roleService.loadAllRoles();
		result.setData(role);
		return JSON.toJSON(result);
	}

	/**
	 * 修改操作员信�?
	 * 
	 * @author gudingyin
	 * @param bean
	 * @param operatorName,userId, email,password, status,roles
	 * @param operatorName,userId, email,password, status,roles
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/updateOperator.json")
	public Object updateOperator(UpdateOperatorBean bean,
			HttpServletRequest request,HttpServletResponse response)
				throws Exception{
		JsonResult result=new JsonResult();
		//�?��ID是否为空
		bean.setOperatorId(employeeService.getOperIdByEmpId(bean.getEmpId()));
		bean.setOperatorName(bean.getEmpName());
		bean.setStatus(bean.getEmpStatus());
		if(StringUtils.isBlank(bean.getOperatorId().toString())){
			result.addError(new MyError(
					MyError.SystemCode.OPERATOR_UPDATE_ERROR, 
					MyError.SystemMsg.OPERATOR_UPDATE_ERROR));
			return result;
		}
		//处理要修改的数据
		result=QequestDataProcess(bean,true);
		//清除页面中的缓存数据
		Operator operator=(Operator)result.getCacheData(true);
		Integer i = operatorService.updateOperator(operator);
		if(i<=0){
			result.addError(new MyError(
					MyError.SystemCode.OPERATOR_UPDATE_ERROR, 
					MyError.SystemMsg.OPERATOR_UPDATE_ERROR));
			return result;
		}
		Employee e = new Employee();
		e.setOperatorId(bean.getOperatorId());
		e.setEmployeeId(bean.getEmpId());
		e.setEmployeeCode(bean.getEmpCode());
		e.setEmployeeName(bean.getOperatorName());
		e.setGender(bean.getGender());
		if(bean.getEmpStatus().equals("����")){
			e.setEmployeeStatus(EmployeesStatus.noraml.name());
		}else{
			e.setEmployeeStatus(EmployeesStatus.delete.name());
		}
		e.setOrgId(bean.getOrgId());
		Integer j = employeeService.updateEmp(e);	
		if(j<=0){
			result.addError(new MyError(MyError.SystemCode.EMPLOYEE_RETURN_NULL, 
					MyError.SystemMsg.EMPLOYEE_RETURN_NULL));
//			List ids = new ArrayList();
//			ids.add(bean.getOperatorId());
//			operatorService.deleteOperatorByIds(ids);
			return result;
		}
		result.setData("�޸ĵļ�¼������"+j);
		return JSON.toJSON(result);
	}
	
	/**
	 * 新增操作员信�?
	 * 
	 * @author bin
	 * @param bean
	 * @param operatorName,userId, email,password, status,roles
	 * @param operatorName,userId, email,password, status,roles
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/insertOperator.json")
	public Object insertOperator(UpdateOperatorBean bean,
			HttpServletRequest request,HttpServletResponse response)
				throws Exception{
		JsonResult result=new JsonResult();
		//处理要修改的数据
		bean.setOperatorName(bean.getEmpName());
		bean.setStatus(bean.getEmpStatus());
		result=QequestDataProcess(bean,false);
		//清除页面中的缓存数据
		Operator operator=(Operator)result.getCacheData(true);
		Boolean success = operatorService.insertOperator(operator);	
		if(success){
			result.setData(""+ operator.getOperatorId());
		}
		else{
			result.setData("");
		}
		bean.setOperatorId(operator.getOperatorId());
		result =  EmployeeAction.checkInsertEmp(bean);
		if(!result.isSuccess())
		{
			return result;
		}
		Employee emp = (Employee)result.getCacheData(true);
		Employee e = employeeService.insertEmp(emp);
		if(e!=null){
			result.setData(e);
		}
		return JSON.toJSON(result);
	}
	
	@ResponseBody
	@RequestMapping(value="/loadOperatorByEmpId.json")
	public Object loadOperatorByEmpId(Integer empId,
			HttpServletRequest request,HttpServletResponse response){
		JsonResult result = new JsonResult();
		if(StringUtils.isBlank(empId.toString())){
			result.addError(new MyError(
					MyError.SystemCode.EMPLOYEE_EMPID_IS_NULL,
					MyError.SystemMsg.EMPLOYEE_EMPID_IS_NULL));
			
		}
		UpdateOperatorBean bean = operatorService.loadOperatorByEmpId(empId);
		result.setData(bean);
		return JSON.toJSON(result);
	}
	
	/**
	 * 处理新增和修改时页面传来的数据，并用JsonResult包装
	 * 修改时需要id为true，新增时为false
	 * @param bean
	 * @return
	 */
	private JsonResult QequestDataProcess(UpdateOperatorBean bean,boolean needId){
		JsonResult result=new JsonResult();
		Operator operator=new Operator();
		//将页面接收到的数据存入Operator�?
		if(needId){
			if(bean.getOperatorId()!=null){
				try {
					operator.setOperatorId(bean.getOperatorId());
				} catch (Exception e) {
					result.addError(new MyError(
							MyError.SystemCode.OPERATOR_UPDATE_ERROR,
							MyError.SystemMsg.OPERATOR_UPDATE_ERROR));
				}
			}else{
				result.addError(new MyError(
							MyError.SystemCode.OPERATOR_UPDATE_ERROR,
							MyError.SystemMsg.OPERATOR_UPDATE_ERROR));
			}
		}
		operator.setOperatorName(bean.getOperatorName());
		operator.setUserId(bean.getUserId());
		operator.setEmail(bean.getEmail());
		if(!StringUtils.isEmpty(bean.getPassword()))
		{
			operator.setPassword(MD5Utils.getMD5String(bean.getPassword()));
		}
		operator.setStatus(bean.getStatus());
		Integer a[] = new Integer[10];
		List<Integer> b = bean.getRoleIds();
		if(b.get(0) == null){
			result.addError(new MyError(
				MyError.SystemCode.OPERATOR_UPDATE_ERROR,
				MyError.SystemMsg.OPERATOR_UPDATE_ERROR));
		}
		else
		{bean.getRoleIds().toArray(a);
		operator.setRoleIds(a);}
		//将处理好的menu数据用result包装
		result.setData(operator);
		return result;
	}


	/**
	 * 把角色列表从role转化为bean对象
	 * 
	 * @param role
	 * @return
	 */
	private List<RoleBean> roleToBeans(List<Role> maps) {
		List<RoleBean> beans = null;

		if (null != maps && maps.size() > 0) {
			beans = new ArrayList<RoleBean>();
			for (int i = 0; i < maps.size(); i++) {
				RoleBean bean = roleToBean(maps.get(i));
				if (null != bean) {
					beans.add(bean);
				}
			}
		}
		return beans;
	}

	/**
	 * 角色从role转化为bean对象
	 * 
	 * @param role
	 * @return
	 */
	private RoleBean roleToBean(Role map) {
		RoleBean bean = null;
		if (null != map) {
			bean = new RoleBean();
			bean.setRoleId(map.getRoleId().toString());
			bean.setRoleName(map.getRoleName());
		}
		return bean;
	}
	
	/**
	 * 把角色对象列表转化为页面角色对象列表
	 * 
	 * @param role
	 * @return
	 */

	private List<RoleBean> rolesToRoleRecord(List<Role> roles) {
		ArrayList<RoleBean> asrs = null;
		if (null != roles && roles.size() > 0) {
			asrs = new ArrayList<RoleBean>();
		for (int i = 0;i<roles.size();i++){
			RoleBean asr = roleToRoleRecord(roles.get(i));
			asrs.add(asr);
		}
			}
		return asrs;
		}	
	/**
	 * 把角色对象转化为页面角色对象
	 * 
	 * @param application
	 * @return
	 */
	private RoleBean roleToRoleRecord(Role role) {
		RoleBean asr = null;
		if (role != null) {
			asr = new RoleBean();
			asr.setRoleId(String.valueOf(role.getRoleId()));
			asr.setRoleName(role.getRoleName());
		}
		return asr;
	}

	/**
	 * 验证搜索功能时参数的正确性�?
	 * 
	 * @param bean
	 * @return
	 */
	private JsonResult checkSearchRoles(String operatorId) {
		JsonResult result = new JsonResult();
		if (operatorId == null) {
			result.addError(new MyError(MyError.SystemCode.ROLEID_IS_NULL,
					MyError.SystemMsg.ROLENAME_IS_NULL));
		}
		return result;
	}

	@Autowired
	@Qualifier(value = "roleService")
	private RoleService roleService;
	
	@Autowired
	@Qualifier(value = "employeeService")
	EmployeeService employeeService;
}

