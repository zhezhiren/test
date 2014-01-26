package com.plj.dao.sys;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.plj.domain.decorate.sys.WorkFlow;
import com.plj.domain.decorate.sys.WorkFlowExecute;

/**
 * 表ac_role操作对象
 * @author 
 *
 */
@SuppressWarnings("rawtypes")
@Repository
public interface WorkFlowDao 
{
	public List<WorkFlow> getWorkFlow(Map bean);
	public List<WorkFlow> getWorkFlowHome(Map map);
	public Integer addWorkFlow(WorkFlow bean);
	public Integer deleteWorkFlow(Map id);
	public Integer updateWorkFlow(WorkFlow bean);
	public Integer updateWorkFlowExecute(Map<String, Object> map);
	public Integer initWorkFlowExecute(WorkFlowExecute bean);
	public Integer getWorkFlowId();
	public Integer completeWorkFlow(Map map);
	public Integer cancelRemindWork(Map map);
	public Date getWorkEndTime(Integer id);
	
	public List<WorkFlow> getUnInitWorkFlow(Date toDay);
}
