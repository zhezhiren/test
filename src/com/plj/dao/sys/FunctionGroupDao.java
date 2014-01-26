package com.plj.dao.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.plj.domain.decorate.sys.FunctionGroup;

/**
 * 表ac_funcgroup操作对象
 * @author zhengxing
 * @version 1.0
 * @date 2013/2/20
 */
@Repository
@SuppressWarnings("rawtypes")
public interface FunctionGroupDao 
{
	/**
	 * 新增功能�?
	 * @param functionGroup
	 * @return
	 */
	public Integer insertFuncGroup(FunctionGroup functionGroup);
	
	/**
	 * 修改功能�?
	 * @param functionGroup
	 * @return
	 */
	public Integer updateById(FunctionGroup functionGroup);
	
	/**
	 * 根据id删除功能�?
	 * @param id
	 * @return
	 */
	public Integer deleteById(Integer id);
	
	/**
	 * 根据id列表删除功能�?
	 * @param ids
	 * @return
	 */
	public Integer deleteByIds(List<Integer> ids);
	
	/**
	 * 根据查询条件获取功能组分页记�?
	 * @param map
	 * @return
	 */
	public List<FunctionGroup> searchFunctionGroups(HashMap map);
	
	/**
	 * 将PARENTGROUP=0修改为null
	 * @param parentGroup
	 * @return
	 */
	public Integer updateParentGroup(Integer parentGroup);
	
	/**
	 * 通过parentGroup查询记录条数
	 * @param parentGroup
	 * @return
	 */
	public Integer searchCount(Integer parentGroup);
	
	/**
	 * 修改count=0时FUNCGROUPSEQ的�?
	 * @param funcGroupId
	 * @return
	 */
	public Integer updateFuncGroupSEQ(Integer funcGroupId);
	
	/**
	 * 修改count!=0时FUNCGROUPSEQ的�?
	 * @param funcGroupId
	 * @return
	 */
	public Integer updateFuncGroupSEQ_P(Map map);
	
}
