package com.plj.service.sys;

import java.util.Date;
import java.util.List;

import com.plj.common.tools.mybatis.page.bean.Pagination;
import com.plj.domain.decorate.sys.DutyPlan;


public interface DutyPlanService
{
	public Integer saveDutyPlan(DutyPlan bean);
	
	public DutyPlan getPossibleDutyPlan(Integer orgId);
	
	public List<DutyPlan> searchDutyPlan(String empName, Integer orgId, Date StartTime
			, Date endTime, Pagination page);
	
	public Integer deleteByIds(List<Integer> ids);
	
	public Integer updateDutyPlan(DutyPlan bean);
}
