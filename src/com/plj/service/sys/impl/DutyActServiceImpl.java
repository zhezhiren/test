package com.plj.service.sys.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plj.common.tools.mybatis.page.bean.Pagination;
import com.plj.dao.sys.DutyActDao;
import com.plj.domain.decorate.sys.DutyAct;
import com.plj.service.sys.DutyActService;

@Service("dutyActService")
public class DutyActServiceImpl implements DutyActService
{

	@Override
	public Integer saveDutyAct(DutyAct bean)
	{
		Integer result = -1;
		if(null != bean)
		{
			if(null != bean.getDutyOperatorId() && null != bean.getOrgId()
					/*&& null != bean.getStartTime() */&& null != bean.getEndTime())
			{
				result = dutyActDao.save(bean);
			}
		}
		return result;
	}
	
	public List<DutyAct> searchDutyAct(String empName, Integer orgId, Date startTime
			, Date endTime, Pagination page)
	{
		HashMap<String, Object> map = new HashMap<String, Object>(4);
		if(StringUtils.isNotBlank(empName))
		{
			map.put("empName", "%" + empName.trim() + "%");
		}
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("pagination", page);
		map.put("orgId", orgId);
		List<DutyAct> dutyActs = dutyActDao.searchDutyAct(map);
		return dutyActs;
	}
	
	public DutyAct getLastDutyActOfOrg(Integer orgId)
	{
		DutyAct orgLastDuty = null;
		if(null != orgId)
		{
			orgLastDuty = dutyActDao.getLastDutyActOfOrg(orgId);
		}
		return orgLastDuty;
	}
	
	@Autowired
	private DutyActDao dutyActDao;

}
