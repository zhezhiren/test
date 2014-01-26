package com.plj.service.sys;

import java.util.HashMap;
import java.util.List;

import com.plj.domain.decorate.sys.Operator;
import com.plj.domain.request.sys.UpdateOperatorBean;

public interface OperatorService 
{
	/**
	 * 修改操作�?
	 * @param operator
	 * @param roleIds
	 * @return
	 */
	public Integer updateOperator(Operator operator);
	
	@SuppressWarnings("rawtypes")
	public List<Operator> SearchOperator(HashMap map);
	
	public List<Operator> getOperatorNotEmployee(HashMap map);
	
	public Integer deleteOperatorByIds(List<Integer> ids);
	
//	public Integer deleteOperatorRolesById(Integer operatorId);
//	
//	public Object addOperatorRoles(List<Map<String,Integer>> list);
	
	public Operator getOperatorByUserName(String name);

	public Boolean insertOperator(Operator operator);
	
	public Operator login(String userId, String password);
	
	public Integer updateLoginTime(Operator operator);
	
	public Integer updatePassword(String oldPassword, String newPassword, String userId);
	
	public UpdateOperatorBean loadOperatorByEmpId(Integer empId);
}
