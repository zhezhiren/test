package com.plj.service.sys.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plj.common.tools.mybatis.page.bean.Pagination;
import com.plj.dao.sys.DutyPlanDao;
import com.plj.domain.decorate.sys.DutyPlan;
import com.plj.service.sys.DutyPlanService;

@Service("dutyPlanService")
public class DutyPlanServiceImpl implements DutyPlanService
{

	@Override
	public Integer saveDutyPlan(DutyPlan bean)
	{
		Integer result = -1;
		if(null != bean)
		{
			if(null != bean.getDutyOperatorId() && /*null != bean.getStartTime() &&*/
					null != bean.getOrgId() && null != bean.getEndTime())
			{
				result = dutyPlanDao.save(bean);
			}
		}
		return result;
	}
	
	public Integer updateDutyPlan(DutyPlan bean)
	{
		Integer result = -1;
		if(null != bean)
		{
			if(bean.getId() != null && null != bean.getDutyOperatorId() 
				&& /*null != bean.getStartTime() && */null != bean.getOrgId() 
				&& null != bean.getEndTime())
			{
				result = dutyPlanDao.update(bean);
			}
		}
		return result;
	}
	
	@Override
	public DutyPlan getPossibleDutyPlan(Integer orgId)
	{
		DutyPlan possiblePlan = null;
		Date now = new Date();
		HashMap<String, Object> map = new HashMap<String, Object>(2);
		map.put("time", now);
		map.put("orgId", orgId);
		List<DutyPlan> dutyPlans = dutyPlanDao.getPossibleDutyPlan(map);
		DutyPlan beffore = null;
		DutyPlan after = null;
		if(null != dutyPlans && dutyPlans.size() > 0)//分别获取结束时间大于当前时间的计划与结束时间小于当前时间的计�?
		{
			for(int i = 0; i < dutyPlans.size(); i++)
			{
				DutyPlan buffer = dutyPlans.get(i);
				if(buffer.getEndTime().before(now))
				{
					beffore = buffer;
				}
				else
				{
					after = buffer;
				}
			}
		}
		if(null != beffore && null != after)//哪个计划从时间上更接近当前时间，更接近当前时间的计划更可能为当前计划�?
		{
			if(now.getTime() - beffore.getEndTime().getTime() 
					< after.getEndTime().getTime() - now.getTime())
			{
				possiblePlan = beffore;
			}
			else
			{
				possiblePlan = after;
			}
		}else//如果总计就一个计划，则该计划为当前计划�?
		{
			if(null != beffore)
			{
				possiblePlan = beffore;
			}else
			{
				possiblePlan = after;
			}
		}
		if(null != possiblePlan)//当当前计划不为空时，判断当前计划的结束时间是不是与当前时间差距大�?8小时；是的，则表示该计划不可能为当前计划�?
		{
			if(Math.abs(possiblePlan.getEndTime().getTime() - now.getTime()) > 1000 * 60 * 60 * 48)
			{
				possiblePlan = null;
			}
		}
		return possiblePlan;
	}
	
	@Override
	public List<DutyPlan> searchDutyPlan(String empName, Integer orgId, Date startTime
			, Date endTime, Pagination page)
	{
		HashMap<String, Object> param = new HashMap<String, Object>(4);
		if(StringUtils.isNotBlank(empName))
		{
			param.put("empName", "%" + empName.trim() + "%");
		}
		param.put("startTime", startTime);
		param.put("endTime", endTime);
		param.put("pagination", page);
		param.put("orgId", orgId);
		List<DutyPlan> plans = dutyPlanDao.searchDutyPlan(param);
		return plans;
	}
	
	public Integer deleteByIds(List<Integer> ids)
	{
		Integer result = 0;
		if(null != ids && ids.size() > 0 && null != ids.get(0))
		{
			result = dutyPlanDao.deleteByIds(ids);
		}
		return result;
	}
	
	@Autowired
	private DutyPlanDao dutyPlanDao;
}
