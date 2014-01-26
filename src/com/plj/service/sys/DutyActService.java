package com.plj.service.sys;

import java.util.Date;
import java.util.List;

import com.plj.common.tools.mybatis.page.bean.Pagination;
import com.plj.domain.decorate.sys.DutyAct;


public interface DutyActService
{
	public Integer saveDutyAct(DutyAct bean);
	
	public List<DutyAct> searchDutyAct(String empName, Integer orgId
			, Date startTime, Date endTime, Pagination page);
	
	public DutyAct getLastDutyActOfOrg(Integer orgId);
}
