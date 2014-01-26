package com.plj.dao.sys;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.plj.domain.decorate.sys.Employee;
import com.plj.domain.response.sys.EmpInfo;
import com.plj.domain.response.sys.EmpListInfo;
import com.plj.domain.response.sys.EmployeeInfo;

@Repository
public interface EmployeeDao {
	
	/**获取雇员信息*/
	@SuppressWarnings("rawtypes")
	public List<EmployeeInfo> getEmps(Map map);
	public Integer deleteEmp(Integer operatorId);
	public Integer insertEmp(Employee bean);
	public EmpInfo loadEmpById(String empId);
	public Integer updateEmp(Employee bean);
	@SuppressWarnings("rawtypes")
	public List<EmpListInfo> loadEmpByOrgId(Map map);
	
	public Employee getEmployeeByOperatorId(Integer value);
	
	public Integer getOperIdByEmpId(Integer empId);

}
