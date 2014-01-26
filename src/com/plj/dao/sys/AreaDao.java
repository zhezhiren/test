package com.plj.dao.sys;

import java.util.List;

import com.plj.domain.decorate.sys.Area;

public interface AreaDao
{
	public List<Area> getChildArea(String areaCode);
}
