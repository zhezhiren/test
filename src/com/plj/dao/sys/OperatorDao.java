package com.plj.dao.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.plj.domain.decorate.sys.Operator;
import com.plj.domain.request.sys.UpdateOperatorBean;

/**
 * 表ac_operator操作对象
 * @author zhengxing
 * @version 1.0
 * @date 2013/2/20
 */
@Repository
public interface OperatorDao
{
	/**
	 * 根据唯一索引，获取用户记录
	 * @param bean
	 * @return
	 */
	public Operator getByUniqueKey(Operator bean);
	
	/**
	 * 修改用户记录
	 * @param bean
	 * @return
	 */
	public Integer update(Operator bean);
	
	/**
	 * 根据条件搜索用户列表
	 * @param map
	 * @return
	 */
	public List<Operator> searchOperator(Map map);
	
	public List<Operator> getOperatorNotEmployee(HashMap map);
	
	public Integer deleteOperatorByIds(List<Integer> list);
	
	public Integer deleteOperatorById(Integer value);
	/**
	 * 删除用户的角色信息
	 * @param operatorId
	 * @return
	 */
	public Integer deleteOperatorRolesById(Integer operatorId);
	/**
	 * 添加用户的角色信息
	 * @param list
	 * @return
	 */
	public Object addOperatorRoles(Operator operator);
	/**
	 * 根据用户名搜索用户
	 * @param userId
	 * @return
	 */
	public Operator getOperatorByUserId(String userId);
	
	public Integer updateOperator(Operator operator);

	public void insertOperator(Operator operator);
	
	public Operator login(Map<String, String> map);
	
	public Integer updateLoginTime(Operator operator);
	
	public Integer updateCurPassword(Map map);
	
	public UpdateOperatorBean loadOperatorByEmpId(Integer empId);
}
