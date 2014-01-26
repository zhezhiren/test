package com.plj.dao.sys;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.plj.domain.bean.sys.SubFunctionDataBean;
import com.plj.domain.decorate.sys.SubFunctionData;

@Repository
public interface SubFunctionDataDao {
	
	
	public Integer insertSubFunctionData(SubFunctionData subfucData);
	public Integer deleteSubFunctionData(SubFunctionDataBean subfucData);
	public Integer updateSubFunctionData(SubFunctionData subfucData);

	/**
	 * 根据查询条件获取功能组分页记录
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<SubFunctionData> getSubFuncData(HashMap map);
}
