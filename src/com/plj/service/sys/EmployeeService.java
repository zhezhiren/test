package com.plj.service.sys;

import java.util.List;
import java.util.Map;

import com.plj.domain.decorate.sys.Employee;
import com.plj.domain.response.sys.EmpInfo;
import com.plj.domain.response.sys.EmpListInfo;
import com.plj.domain.response.sys.EmployeeInfo;

public interface EmployeeService {

	@SuppressWarnings("rawtypes")
	public List<EmployeeInfo> getEmps(Map map);
	public Integer deleteEmp(List<Integer> ids);
	public Employee insertEmp(Employee emp);
	public Integer updateEmp(Employee emp);
	public EmpInfo loadEmpById(String empId);
	@SuppressWarnings("rawtypes")
	public List<EmpListInfo> loadEmpByOrgId(Map map);
	public Employee getEmployeeByOperatorId(Integer operatoryId);
	public Integer getOperIdByEmpId(Integer empId);
	
}
