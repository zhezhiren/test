package com.plj.dao.sys;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.plj.domain.decorate.sys.Function;

/**
 * 表ac_function操作对象
 * @author gdy
 * @version 1.0
 * @date 2013/2/20
 */
@Repository
public interface FunctionDao 
{
	/**
	 * 新增功能
	 * @param function
	 * @return
	 */
	public Integer insert(Function function);
	
	/**
	 * 修改功能
	 * @param function
	 * @return
	 */
	public Integer update(Function function);
	
	/**
	 * 根据id删除功能
	 * @param id
	 * @return
	 */
	public Integer deleteById(Integer id);
	
	/**
	 * 根据id列表删除功能
	 * @param ids
	 * @return
	 */
	public Integer deleteByIds(List<Integer> ids);
	
	/**
	 * 根据查询条件获取功能分页记录
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Function> searchFunction(HashMap map);
}
