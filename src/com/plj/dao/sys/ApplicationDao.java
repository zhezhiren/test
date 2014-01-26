package com.plj.dao.sys;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.plj.domain.decorate.sys.Application;
import com.plj.domain.decorate.sys.Function;

/**
 * 表ac_application映射对象
 * @author zhengxing
 * @version 1.0
 * @date 2013/2/20
 */
@Repository
public interface ApplicationDao 
{
	/**
	 * 新增应用
	 * @param application
	 * @return
	 */
	public Integer save(Application application);
	
	/**
	 * 修改应用
	 * @param application
	 * @return
	 */
	public Integer updateApplication(Application application);
	
	/**
	 * 根据id列表删除应用
	 * @param ids
	 * @return
	 */
	public Integer deleteApplicationsByIds(List<Integer> ids);
	
	/**
	 * 获取�?��应用
	 * @return
	 */
	public List<Application> getAllApplication();
	
	/**
	 * 根据查询条件获取应用分页对象
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Application> searchApplication(Map map);

	/**
	 * 获取funcName已经存在的个�?
	 * @param funcName
	 * @return
	 */
	public int funcNameExists(String funcName);

	/**
	 * 获取funcGroupName已经存在的个�?
	 * @param funcName
	 * @return
	 */
	public int funcGroupNameExists(String funcGroupName);

	/**
	 * 插入Function数据
	 * @param function
	 * @return
	 */
	public int insertFunction(Function function);

	/**
	 * 更新Function数据
	 * @param function
	 * @return
	 */
	public int updateFunction(Function function);

	/**
	 * 根据Id删除Function数据
	 * @param ids
	 * @return
	 */
	public int deleteFunctionByIds(List<Integer> ids);
	
	/**
	 * 验证应用名存�?
	 * @param appName
	 * @return
	 * */
	public int appNameExits(String appName);
}
