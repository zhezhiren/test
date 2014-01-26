package com.plj.dao.sys;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.plj.domain.decorate.sys.DutyPlan;

@Repository
public interface DutyPlanDao
{
	public Integer save(DutyPlan bean);
	
	public List<DutyPlan> getPossibleDutyPlan(Map<String, Object> param);
	
	public List<DutyPlan> searchDutyPlan(Map<String, Object> param);
	
	public Integer deleteByIds(List<Integer> list);
	
	public Integer update(DutyPlan bean);
}
