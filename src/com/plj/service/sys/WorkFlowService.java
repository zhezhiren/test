package com.plj.service.sys;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.plj.domain.decorate.sys.WorkFlow;
import com.plj.domain.decorate.sys.WorkFlowExecute;

@SuppressWarnings("rawtypes")
public interface WorkFlowService {
	public List<WorkFlow> getWorkFlow(Map bean);
	public List<WorkFlow> getWorkFlowHome(Map map);
	public Boolean addWorkFlow(WorkFlow bean,WorkFlowExecute wfe);
	public Boolean deleteWorkFlow(Map map);
	public Boolean updateWorkFlow(WorkFlow bean);
	public Boolean completeWorkFlow(Map map);
	public Boolean cancelRemindWork(Map map);
	public Date getWorkEndTime(Integer id);
	public void initToday();
}
