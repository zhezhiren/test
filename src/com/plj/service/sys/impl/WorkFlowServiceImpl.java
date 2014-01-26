package com.plj.service.sys.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plj.common.constants.Constants;
import com.plj.dao.sys.WorkFlowDao;
import com.plj.domain.decorate.sys.WorkFlow;
import com.plj.domain.decorate.sys.WorkFlowExecute;
import com.plj.service.sys.WorkFlowService;

@Service("workFlowService")
@SuppressWarnings("rawtypes")
public class WorkFlowServiceImpl implements WorkFlowService {

	@Autowired
	private WorkFlowDao dao;
	@Override
	public List<WorkFlow> getWorkFlow(Map bean) {
		
		return dao.getWorkFlow(bean);
	}
	@Override
	public Boolean addWorkFlow(WorkFlow bean,WorkFlowExecute wfe) {
		Integer result = dao.addWorkFlow(bean);
		//Integer flowId = dao.getWorkFlowId();
		wfe.setFlowId(bean.getId());
		Integer result1 = dao.initWorkFlowExecute(wfe);
		if(result>0&&result1>0){
			return true;
		}
		return false;
	}
	@Override
	public Boolean deleteWorkFlow(Map id) {
		Integer result = dao.deleteWorkFlow(id);
		if(result>0){
			return true;
		}
		return false;
	}
	@Override
	public Boolean updateWorkFlow(WorkFlow bean) {
		Integer result = dao.updateWorkFlow(bean);
		if(result>0){
			HashMap<String, Object> map = new HashMap<String, Object>(3);
			map.put("endTime", bean.getEndTime());
			map.put("flowId", bean.getId());
			map.put("today", getToday());
			result = dao.updateWorkFlowExecute(map);
			return true;
		}
		return false;
	}
	
	@Override
	public List<WorkFlow> getWorkFlowHome(Map map)
	{
		map.put("today", getToday());
		return dao.getWorkFlowHome(map);
	}
	
	@Override
	public void initToday()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 1);
		List<WorkFlow> flows = dao.getUnInitWorkFlow(calendar.getTime());
		if(null != flows && flows.size() > 0)
		{
			for(int i = 0; i < flows.size(); i++)
			{
				WorkFlow flow = flows.get(i);
				WorkFlowExecute execute = new WorkFlowExecute();
				execute.setFlowId(flow.getId());
				execute.setOrgId(flow.getOrgId());
				execute.setWorkStatus(Constants.REMIND);
				execute.setCompleteStatus(Constants.UNFINISH);
				if(null != flow.getEndTime())
				{
					calendar.set(Calendar.HOUR_OF_DAY, flow.getEndTime().getHours());
					calendar.set(Calendar.MINUTE, flow.getEndTime().getMinutes());
					calendar.set(Calendar.SECOND, 0);
				}
				execute.setEndTime(calendar.getTime());
				dao.initWorkFlowExecute(execute);
			}
		}
	}
	
	@Override
	public Boolean completeWorkFlow(Map map) {
		Integer result = dao.completeWorkFlow(map);
		if(result>0){
			return true;
		}
		return false;
	}
	@Override
	public Boolean cancelRemindWork(Map map) {
		Integer result = dao.cancelRemindWork(map);
		if(result>0){
			return true;
		}
		return false;
	}
	@Override
	public Date getWorkEndTime(Integer id) {
		return dao.getWorkEndTime(id);
	}
	
	private Date getToday()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 1);
		return calendar.getTime();
	}
}
