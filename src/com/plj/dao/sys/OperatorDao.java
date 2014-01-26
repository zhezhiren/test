package com.plj.dao.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.plj.domain.decorate.sys.Operator;
import com.plj.domain.request.sys.UpdateOperatorBean;

/**
 * ��ac_operator��������
 * @author zhengxing
 * @version 1.0
 * @date 2013/2/20
 */
@Repository
public interface OperatorDao
{
	/**
	 * ����Ψһ��������ȡ�û���¼
	 * @param bean
	 * @return
	 */
	public Operator getByUniqueKey(Operator bean);
	
	/**
	 * �޸��û���¼
	 * @param bean
	 * @return
	 */
	public Integer update(Operator bean);
	
	/**
	 * �������������û��б�
	 * @param map
	 * @return
	 */
	public List<Operator> searchOperator(Map map);
	
	public List<Operator> getOperatorNotEmployee(HashMap map);
	
	public Integer deleteOperatorByIds(List<Integer> list);
	
	public Integer deleteOperatorById(Integer value);
	/**
	 * ɾ���û��Ľ�ɫ��Ϣ
	 * @param operatorId
	 * @return
	 */
	public Integer deleteOperatorRolesById(Integer operatorId);
	/**
	 * ����û��Ľ�ɫ��Ϣ
	 * @param list
	 * @return
	 */
	public Object addOperatorRoles(Operator operator);
	/**
	 * �����û��������û�
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
