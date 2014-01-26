package com.plj.dao.sys;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.plj.domain.decorate.sys.DutyAct;

@Repository
public interface DutyActDao 
{
	public Integer save(DutyAct bean);
	
	public List<DutyAct> searchDutyAct(Map<String, Object> map);
	
	public DutyAct getLastDutyActOfOrg(Integer orgId);
	
}
