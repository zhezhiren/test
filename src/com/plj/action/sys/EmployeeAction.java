package com.plj.action.sys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.plj.common.constants.FieldEnum.EmployeesStatus;
import com.plj.common.error.MyError;
import com.plj.common.result.JsonResult;
import com.plj.common.result.ListData;
import com.plj.domain.decorate.sys.Employee;
import com.plj.domain.request.common.DeleteByIntegerIds;
import com.plj.domain.request.sys.EmpListByOrgIdBean;
import com.plj.domain.request.sys.GetEmployees;
import com.plj.domain.request.sys.UpdateOperatorBean;
import com.plj.domain.response.sys.EmpInfo;
import com.plj.domain.response.sys.EmpListInfo;
import com.plj.domain.response.sys.EmployeeInfo;
import com.plj.service.sys.EmployeeService;

@Controller
@RequestMapping(value="/employee")
public class EmployeeAction {
	
	@Autowired
	EmployeeService employeeService;

	/**
	 * 获取雇员信息
	 * **/
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/getEmps.json")
	@ResponseBody
	public Object getEmps(GetEmployees bean,HttpServletRequest request,
			HttpServletResponse response){		
		String optionType="1";			//指定optionType默认值为"1"
		
		String organizationId=bean.getOperId();
		String employeeCode=bean.getEmpCode();
		String employeeName=bean.getEmpName();
		Integer operatorId=Integer.parseInt(bean.getOperId());
		String belongToOrgId=bean.getBelongToOrgId();
		String selectedOrgName=bean.getSelectedOrgName();
		
		HashMap param=new HashMap();
		JsonResult result=new JsonResult();
		if(StringUtils.isNotBlank(bean.getOptype())){
			optionType=bean.getOptype();
		}
		if(optionType.equals("0")){
			if(StringUtils.isBlank(organizationId)){
				result.addError(new MyError(
						MyError.SystemCode.EMPLOYEE_ORGID_IS_NULL,
						MyError.SystemMsg.EMPLOYEE_ORGID_IS_NULL));
			}else{
				param.put("organizationId", organizationId.trim());
			}
		}else if(optionType.equals("1")){
			if(StringUtils.isBlank(selectedOrgName)){
				result.addError(new MyError(
						MyError.SystemCode.EMPLOYEE_ORGNAME_ID_NULL,
						MyError.SystemMsg.EMPLOYEE_ORGNAME_ID_NULL));
			}else{
				param.put("selectedOrgName", selectedOrgName.trim());
			}
			if(StringUtils.isNotBlank(employeeCode)){
				param.put("employeeCode", employeeCode.trim());
			}
			if(StringUtils.isNotBlank(employeeName)){
				param.put("employeeName", employeeName.trim());
			}
			if(null!=operatorId){
				param.put("operatorId", operatorId);
			}
			if(StringUtils.isNotBlank(belongToOrgId)){
				param.put("belongToOrgId", belongToOrgId.trim());
			}
		}
		param.put("pagination", bean);
		List<EmployeeInfo> list=employeeService.getEmps(param);
		ListData<EmployeeInfo> lr=new ListData<EmployeeInfo>(bean.getTotalCount(),list);
		result.setData(lr);
		return JSON.toJSON(result);
	}
	/**
	 * 增加员工
	 * @author tangxueming
	 * @param bean
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/insertEmp.json")
	@ResponseBody
	public Object insertEmp(UpdateOperatorBean bean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		JsonResult result =  checkInsertEmp(bean);
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
	
	/**
	 * 根据ID删除员工 （存储过程）
	 * @author tangxueming
	 * @param ids
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/deleteEmp.json")
	@ResponseBody
	public Object deleteEmp(DeleteByIntegerIds idsbean,HttpServletRequest request,
			HttpServletResponse response){
		JsonResult result =  new JsonResult();
		if(idsbean==null){
			result.addError(new MyError(MyError.SystemCode.EMPLOYEE_NOT_ID, 
					MyError.SystemMsg.EMPLOYEE_NOT_ID));
				return result;
		}
		List<Integer> ids =idsbean.getIds();
		if(ids==null || ids.size()==0){
			result.addError(new MyError(MyError.SystemCode.EMPLOYEE_NOT_ID, 
					MyError.SystemMsg.EMPLOYEE_NOT_ID));
				return result;
		}
		for(int i = 0;i<ids.size();i++){
				if(ids.get(i)==null){
					result.addError(new MyError(MyError.SystemCode.EMPLOYEE_NOT_ID, 
							MyError.SystemMsg.EMPLOYEE_NOT_ID));
				return result;
				}
		}
		int c = employeeService.deleteEmp(ids);
		if(c>0){
			result.setData(c);
		}else{
			result.addError(new MyError(MyError.SystemCode.EMPLOYEE_RETURN_NULL, 
					MyError.SystemMsg.EMPLOYEE_RETURN_NULL));
		}
		return result;
	}
	
	/**
	 * 根据ID查找员工信息
	 * @author tangxueming
	 * @param request
	 * @param response
	 * @return
	 * **/
	@RequestMapping(value="/loadEmpById.json")
	@ResponseBody
	public Object loadEmpById(HttpServletRequest request,
			HttpServletResponse response){
		
		String empId=request.getParameter("empId");
		JsonResult result=new JsonResult();
		if(StringUtils.isBlank(empId)){
			result.addError(new MyError(
					MyError.SystemCode.EMPLOYEE_EMPID_IS_NULL,
					MyError.SystemMsg.EMPLOYEE_EMPID_IS_NULL));
			
		}
		EmpInfo emp=employeeService.loadEmpById(empId);
		if(emp!=null){
			result.setData(emp);
		}
		return JSON.toJSON(result);
	}
	
	/**
	 * 修改结构信息
	 * @author tangxueming
	 * @param bean
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/updateEmp.json")
	@ResponseBody
	public Object updateEmp(Employee bean,HttpServletRequest request,
			HttpServletResponse response){
		JsonResult result =  new JsonResult();
		if(bean==null||bean.getEmployeeId()==null){
				result.addError(new MyError(MyError.SystemCode.EMPLOYEEN_UPDATE_BEAN_ERROR, 
					MyError.SystemMsg.EMPLOYEE_UPDATE_BEAN_ERROR));
			return result;
		}
		Integer i = employeeService.updateEmp(bean);	
		if(i<=0){
			result.addError(new MyError(MyError.SystemCode.EMPLOYEE_RETURN_NULL, 
					MyError.SystemMsg.EMPLOYEE_RETURN_NULL));
			return result;
		}else{
			result.setData(bean);
		}
		return result;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/loadEmpByOrgId.json")
	@ResponseBody
	public Object loadEmpByOrgId(EmpListByOrgIdBean bean,HttpServletRequest request,
			HttpServletResponse response){
		JsonResult result =  new JsonResult();
		Map param=new HashMap();
		if(bean==null){
			result.addError(new MyError(MyError.SystemCode.EMPLOYEE_ORGID_IS_NULL, 
					MyError.SystemMsg.EMPLOYEE_ORGID_IS_NULL));
			return result;
		}
		param.put("pagination", bean);
		if(StringUtils.isNotBlank(bean.getOrgId())&&!bean.getOrgId().trim().equals("0")){
			param.put("orgId", Integer.parseInt(bean.getOrgId().trim()));
		}
		if(StringUtils.isNotBlank(bean.getEmpCode())){
			param.put("empCode", bean.getEmpCode().trim());
		}
		if(StringUtils.isNotBlank(bean.getEmpName())){
			param.put("empName", bean.getEmpName().trim());
		}
		List<EmpListInfo> listInfo = employeeService.loadEmpByOrgId(param);
		ListData<EmpListInfo> listdata = new ListData<EmpListInfo>(bean.getTotalCount(),listInfo);
		result.setData(listdata);
		return JSON.toJSON(result);
	}
	
	public static JsonResult checkInsertEmp(UpdateOperatorBean bean){
		JsonResult result = new JsonResult();
		Employee emp = new Employee();
		if(bean==null){
			result.addError(new MyError(MyError.SystemCode.EMPLOYEE_BEAN_NOT_EXISTS,
					MyError.SystemMsg.EMPLOYEE_BEAN_NOT_EXISTS));
			return result;
		}
		if(StringUtils.isNotBlank(bean.getMobileNo())){
			emp.setMobileNumber(bean.getMobileNo());
		}
		if(bean.getOperatorId()!=null){
			emp.setOperatorId(bean.getOperatorId());
		}
		if(StringUtils.isNotBlank(bean.getUserId())){
			emp.setUserId(bean.getUserId());
		}
		if(StringUtils.isNotBlank(bean.getGender())){
			emp.setGender(bean.getGender());
		}
		if(StringUtils.isNotBlank(bean.getEmpCode())){
			emp.setEmployeeCode(bean.getEmpCode());
		}
		if(bean.getOrgId()!=null){
			emp.setOrgId(bean.getOrgId());
		}
		if(StringUtils.isNotBlank(bean.getEmpName())){
			emp.setEmployeeName(bean.getEmpName());
		}
		if(StringUtils.isNotBlank(bean.getEmpStatus())){
			emp.setEmployeeStatus(bean.getEmpStatus());
		}
		emp.setEmployeeStatus(EmployeesStatus.noraml.name());
		if(result.isSuccess())
		{
			result.setData(emp);
		}
		return result;
	}
}
