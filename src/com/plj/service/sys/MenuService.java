package com.plj.service.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.plj.domain.decorate.sys.Menu;

@SuppressWarnings("rawtypes")
public interface MenuService 
{
	/**
	 * 获取用户拥有权限的菜单按�?
	 * @param userId
	 * @return
	 */
	
	public List<Map> getMenuByUserId(Integer userId);
	
	/**
	 * �?��获取�?��menu
	 * @param menuCode,menuName,menuLevel,menuLabel
	 * @return
	 * **/
	public List<Map> getAllMenuList(Map map);
	
	
	public List<Map> selectFuncList(Map menu);
	
	/**
	 * 插入菜单信息
	 * @param menu
	 * @return
	 */
	public Integer insertMenu(Menu menu); 
	
	/**
	 * 通过id删除菜单信息
	 * @param id
	 * @return
	 */
	public Integer deleteMenuById(List<String> ids);
	
	/**
	 * 通过id修改菜单信息
	 * @param menu
	 * @return
	 */
	public Integer updateMenuById(Menu menu);
	
	/**
	 * 通过id加载菜单信息
	 * @param id
	 * @return
	 */
	public HashMap loadMenuById(Integer id);
	
	/**
	 * 通过id加载AC_MENU表中的信�?
	 * @param id
	 * @return
	 */
	public Menu getMenuById(Integer id);

	/**
	 * 查询菜单�?
	 * @return
	 */
	public List<Map> getMenuAction();

	public Integer updateMenuPMenuSEQ(Map map);

	public Integer updateMenuMenuSEQ(Integer menuId);

	public String selectSeqById(Integer menuId);

	public Integer updateAllSeq(Map mapSeq);
	
	

}
