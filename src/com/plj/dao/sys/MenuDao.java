package com.plj.dao.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.plj.domain.decorate.sys.Menu;

/**
 * 表ac_menu操作对象
 * @author zhengxing
 * @version 1.0
 * @date 2013/2/20
 */
@Repository
@SuppressWarnings("rawtypes")
public interface MenuDao 
{
	/**
	 * 根据用户id获取用户拥有的功能菜单�?
	 * @param userId
	 * @return
	 */

	public List<Map> getMenuByUserId(Integer userId);
	
	
	public List<Map> selectFuncList(Map menu);
	
	/**
	 * 根据菜单编号、菜单名称�?菜单层次、显示名称一次获取所有menu
	 * @param menuCode,menuName,menuLevel,menuLabel
	 * @return List<Map>
	 * **/
	public List<Map> getAllMenuList(Map map);
	
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
	public Integer deleteMenuById(Integer id);
	
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
	public HashMap loadMenuById(Integer menuId);
	
	/**
	 * 通过id查询AC_MENU表中的信�?
	 * @param id
	 * @return
	 */
	public Menu getMenuById(Integer id);

	/**
	 * 	查询菜单�?
	 * @return
	 */
	public List<Map> getMenuAction();
	
	/**
	 * 修改AC_MENU中有父节点时的MENUSEQ字段
	 * @param id
	 * @return
	 */
	public Integer updateMenuPMenuSEQ(Map map);
	
	/**
	 * 修改无父节点时的MENUSEQ
	 * @param id
	 * @return
	 */
	public Integer updateMenuMenuSEQ(Integer id);
	
	/**
	 * 修改�?��父节点的SUBCOUNT++
	 * @return
	 */
	public Integer updateMenuSubCount(Integer id);
	
	/**
	 * 查询要删除节点的�?��子节点（包括自身）的总数count
	 * @param id
	 * @return
	 */
	public Integer menuNodeCount(Integer id);
	
	/**
	 * 将该节点的SUBCOUNT减去其子节点的�?�?
	 * @param id
	 * @return
	 */
	public Integer reduceSubCount(Map map);
	
	public Integer addSubCount(Map map);
	
	public Integer updateLevel(Map map);


	public String selectSeqById(@Param("menuId")Integer menuId);


	public Integer updateAllSeq(Map mapSeq);
}
