package com.plj.service.sys;

import java.util.HashMap;
import java.util.List;

import com.plj.domain.decorate.sys.FunctionGroup;

public interface FunctionGroupService 
{
	/**
	 * 插入功能�?
	 * @param functionGroup
	 * @return
	 */
	public FunctionGroup insertFunctionGroup(FunctionGroup functionGroup);
	
	/**
	 * 修改功能�?
	 * @param functionGroup
	 * @return
	 */
	public Integer updateFunctionGroup(FunctionGroup functionGroup);
	
	/**
	 * 根据id删除功能�?
	 * @param ids
	 * @return
	 */
	public Integer deleteByIds(List<String> ids);
	
	/**
	 * 删除功能�?
	 * @param id
	 * @return
	 */
	public Integer deleteById(Integer id);
	
	/**
	 * 查询功能�?
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<FunctionGroup> searchFunctionGroups(HashMap map);
}
