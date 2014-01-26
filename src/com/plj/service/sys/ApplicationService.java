package com.plj.service.sys;

import java.util.List;
import java.util.Map;

import com.plj.domain.decorate.sys.Application;
import com.plj.domain.decorate.sys.Function;

public interface ApplicationService 
{
	/**
	 * 插入应用
	 * @param application
	 * @return
	 */
	public Application insertApplication(Application application);
	
	/**
	 * 修改应用
	 * @param application
	 * @return
	 */
	public Application updateApplication(Application application);
	
	/**
	 * 删除应用
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
	 * 查询应用
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Application> searchApplication(Map map);
	
	/**
	 * 验证功能名称是否存在
	 * @param funcName
	 * @return
	 */
	public boolean funcNameExists(String funcName);

	/**
	 * 验证功能组名称是否存�?
	 * @param funcName
	 * @return
	 */
	public Boolean funcGroupNameExists(String funcGroupName);

	/**
	 * 插入Function数据
	 * @param function
	 */
	public Function insertFunction(Function function);

	/**
	 * 更新Function数据
	 * @param function
	 */
	public Function updateFunction(Function function);

	/**
	 * 删除Function数据
	 * @param ids
	 * @return
	 */
	public int deleteFunction(List<Integer> ids);
	
	/**
	 * 验证应用名存�?
	 * @param appName
	 * @return
	 * */
	public boolean appNameExits(String appName);
	
}
