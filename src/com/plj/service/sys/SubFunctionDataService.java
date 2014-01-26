package com.plj.service.sys;

import java.util.HashMap;
import java.util.List;

import com.plj.domain.bean.sys.SubFunctionDataBean;
import com.plj.domain.decorate.sys.SubFunctionData;

public interface SubFunctionDataService {
	
	public SubFunctionData insertSubFunctionData(SubFunctionData subFuncData);
	
	public SubFunctionDataBean deleteSubFunctionData(SubFunctionDataBean subFuncDataBean);
	
	public SubFunctionData updateSubFunctionData(SubFunctionData subFuncData);
	
	/**
	 * 分页查询
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<SubFunctionData> getSubFunctionData(HashMap map);
	
	
}
