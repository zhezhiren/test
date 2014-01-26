package com.plj.service.sys.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plj.dao.sys.OperatorDao;
import com.plj.domain.decorate.sys.Operator;
import com.plj.domain.request.sys.UpdateOperatorBean;
import com.plj.service.sys.OperatorService;

@Transactional
@Service("operatorService")
public class OperatorSericeImpl implements OperatorService
{
	@Override
	public Integer updateOperator(Operator operator)
	{		
		int e = operatorDao.updateOperator(operator);
		Integer[] roleIds = operator.getRoleIds();
		if(roleIds != null){
			operatorDao.deleteOperatorRolesById(operator.getOperatorId());			
			operatorDao.addOperatorRoles(operator);
		}
		return e;
	}
	
	@SuppressWarnings("rawtypes")
	public List<Operator> SearchOperator(HashMap map)
	{
		return operatorDao.searchOperator(map);
	}
	
	public List<Operator> getOperatorNotEmployee(HashMap map)
	{
		return operatorDao.getOperatorNotEmployee(map);
	}
	
	public Integer deleteOperatorByIds(List<Integer> ids)
	{
		int count = 0;
		if(null != ids)
		{
			for(int i =0; i < ids.size(); i++)
			{
				Integer id = ids.get(i);
				if(null != id)
				{
					count += operatorDao.deleteOperatorById(id);
				}
			}
		}
		return count;
	}
	
	public Integer deleteOperatorRolesById(Integer operatorId){
		return operatorDao.deleteOperatorRolesById(operatorId);
	}
	
//	public Object addOperatorRoles(List<Map<String,Integer>> list){
//		return operatorDao.addOperatorRoles(list);
//	}
	
	public Operator getOperatorByUserName(String name)
	{
		return operatorDao.getOperatorByUserId(name);
	}
	
	@Autowired
	private OperatorDao operatorDao;

	@Override
	public Boolean insertOperator(Operator operator) {
		operatorDao.insertOperator(operator);
		Integer[] roleIds = operator.getRoleIds();
		if(roleIds!=null){
			operatorDao.addOperatorRoles(operator);
		}
		return true;
	}
	
	public Operator login(String userId, String password)
	{
		Operator op = null;
		if(StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(password))
		{
			HashMap<String, String> param = new HashMap<String, String>(2);
			param.put("userId", userId);
			param.put("password", password);
			op = operatorDao.login(param);
		}
		return op;
	}
	
	public Integer updateLoginTime(Operator operator)
	{
		Integer result = 0;
		if(null != operator && null != operator.getOperatorId()
				&& operator.getLastLoginTime() != null)
		{
			result = operatorDao.updateLoginTime(operator);
		}
		return result;
	}
	
	public Integer updatePassword(String oldPassword
			, String newPassword, String userId)
	{
		Integer i = 0;
		HashMap<String, String> map = new HashMap<String, String>();
		if(StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)
				&& StringUtils.isNotBlank(userId))
		{
			map.put("oldPassword", oldPassword);
			map.put("newPassword", newPassword);
			map.put("userId", userId);
			i = operatorDao.updateCurPassword(map);
		}
		return i;
	}
	
	public UpdateOperatorBean loadOperatorByEmpId(Integer empId){
		return operatorDao.loadOperatorByEmpId(empId);
	}

//	private List<Map<String, Integer>> getMapList(Integer[] roleIds,
//			Operator operator) {
//		List<Map<String, Integer>> list = new ArrayList<Map<String, Integer>>();		
//		for(int i=0; i< roleIds.length; i++){
//			Map<String, Integer> map = new HashMap<String, Integer>();
//			map.put("operatorId", operator.getOperatorId());
//			map.put("rolesId", roleIds[i]);
//			list.add(map);
//		}
//		return list;
//	}

}