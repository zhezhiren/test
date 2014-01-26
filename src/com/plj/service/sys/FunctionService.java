package com.plj.service.sys;

import java.util.HashMap;
import java.util.List;

import com.plj.domain.decorate.sys.Function;

/**
 * 
 * @author gdy
 *
 */
public interface FunctionService {
	/**
	 * 插入功能
	 * @param function
	 * @return
	 */
	public Function insertFunction(Function function);
	
	/**
	 * 修改功能
	 * @param function
	 * @return
	 */
	public Integer updateFunction(Function function);
	
	/**
	 * 根据id删除功能
	 * @param ids
	 * @return
	 */
	public Integer deleteByIds(List<Integer> ids);
	
	/**
	 * 删除功能
	 * @param id
	 * @return
	 */
	public Integer deleteById(Integer id);
	
	/**
	 * 查询功能
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Function> searchFunction(HashMap map);
}
