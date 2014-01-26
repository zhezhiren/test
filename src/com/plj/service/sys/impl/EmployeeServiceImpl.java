package com.plj.service.sys.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plj.dao.sys.EmployeeDao;
import com.plj.domain.decorate.sys.Employee;
import com.plj.domain.response.sys.EmpInfo;
import com.plj.domain.response.sys.EmpListInfo;
import com.plj.domain.response.sys.EmployeeInfo;
import com.plj.service.sys.EmployeeService;

@Service(value="employeeService")
public class EmployeeServiceImpl implements EmployeeService{
	
	@Autowired
	EmployeeDao employeeDao;

	@SuppressWarnings("rawtypes")
	@Override
	public List<EmployeeInfo> getEmps(Map map) {

		return employeeDao.getEmps(map);
	}
	@Override
	public Integer deleteEmp(List<Integer> ids) {
		int c=0;
		for(int i = 0 ; i < ids.size(); i++){
		    Integer id = employeeDao.getOperIdByEmpId(ids.get(i));
			int b = employeeDao.deleteEmp(id);
			c+=b;
		}
		return c;
	}

	@Override
	public Employee insertEmp(Employee bean) {
		int a = employeeDao.insertEmp(bean);
		if(a==1){
			return bean;
		}else{
			return null;
		}
	}

	@Override
	public EmpInfo loadEmpById(String empId) {
		return employeeDao.loadEmpById(empId);
	}

	@Override
	public Integer updateEmp(Employee bean) {
		return employeeDao.updateEmp(bean);
	}
	@SuppressWarnings("rawtypes")
	@Override
	public List<EmpListInfo> loadEmpByOrgId(Map map) {
		return employeeDao.loadEmpByOrgId(map);
	}
	
	public Employee getEmployeeByOperatorId(Integer operatorId)
	{
		if(null != operatorId)
		{
			return employeeDao.getEmployeeByOperatorId(operatorId);
		}else
		{
			return null;
		}
	}
	
	@Override
	public Integer getOperIdByEmpId(Integer empId) {
		return employeeDao.getOperIdByEmpId(empId);
	}
}
