package com.plj.action.sys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.plj.common.error.MyError;
import com.plj.common.result.JsonResult;
import com.plj.common.result.ListData;
import com.plj.domain.bean.sys.MenuBean;
import com.plj.domain.bean.sys.TreeBasic;
import com.plj.domain.bean.sys.TreeUtil;
import com.plj.domain.decorate.sys.Menu;
import com.plj.domain.request.common.DeleteByStringIds;
import com.plj.domain.request.sys.MenuFuncListBean;
import com.plj.domain.request.sys.MenuPage;
import com.plj.domain.response.sys.MenuInfo;
import com.plj.domain.response.sys.MenuListReturnBean;
import com.plj.domain.response.sys.MenuLoadInfo;
import com.plj.domain.response.sys.TreeBean;
import com.plj.service.sys.MenuService;

/**
 * 菜单相关请求处理对象**/
@Controller
@RequestMapping(value="/menu")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class MenuAction {
	
	@Autowired
	@Qualifier(value="menuService")
	private MenuService menuService;
	
	
	@RequestMapping(value="/getAllMenu.json")
	@ResponseBody
	public Object getAllMenu(MenuPage bean,HttpServletRequest request,
			HttpServletResponse response){

		HashMap param=new HashMap();
		param.put("pagination", bean);
		if(StringUtils.isNotBlank(bean.getMenuCode())){
			param.put("menuCode", bean.getMenuCode().trim());		
		}
		if(StringUtils.isNotBlank(bean.getMenuLevel())){
			param.put("menuLevel", bean.getMenuLevel().trim());		
		}
		if(StringUtils.isNotBlank(bean.getMenuLabel())){
			param.put("menuLabel", bean.getMenuLabel().trim());		
		}
		if(StringUtils.isNotBlank(bean.getMenuName())){
			param.put("menuName", bean.getMenuName().trim());		
		}
		
		List<Map> list=menuService.getAllMenuList(param);
		JsonResult result=new JsonResult();
		ListData<MenuInfo> lr=new ListData<MenuInfo>(bean.getTotalCount(), menuInfosToAPPRecords(list));
		result.setData(lr);
		return JSON.toJSON(result);
	}
	
	/**
	 * 查询树状结构数据
	 * @author bin
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/allMenuAction.json")
	@ResponseBody
	public Object getMenuAction(HttpServletRequest request,
			HttpServletResponse response) {
		List<Map> list = menuService.getMenuAction();
		List<TreeBasic> basicTree = new ArrayList<TreeBasic>();
		TreeBasic top = new TreeBasic();
		top.setId("0");
		top.setName("应用菜单");
		top.setExpanded(true);
		top.setParentId("");
		HashMap topAttrMap = new HashMap();
		topAttrMap.put("action", null);
		top.setAttributes(topAttrMap);
		basicTree.add(top);				
		for(Map map : list){
			TreeBasic tb = new TreeBasic();
			tb.setId(map.get("ID").toString()); //可以外挂属�?
			if(map.get("NAME")!=null){
				tb.setName(map.get("NAME").toString());
			}			
			tb.setExpanded(true);		
			if(map.get("PARENTID")!=null){
				tb.setParentId(map.get("PARENTID").toString());
			}
			else{
				tb.setParentId("0");
			}
			HashMap attrMap = new HashMap();
			attrMap.put("action", map.get("ACTION")==null?null:map.get("ACTION").toString());
			attrMap.put("isLeaf", map.get("ISLEAF")==null?null:map.get("ISLEAF").toString());
			tb.setAttributes(attrMap);
			basicTree.add(tb);
		}
		List<TreeBean> treeNodes = TreeUtil.onTree(basicTree, "", "");		
		return JSON.toJSON(treeNodes);
	}
	
	@RequestMapping(value = "/allMenuNoSonAction.json")
	@ResponseBody
	public Object getMenuNoSonAction(HttpServletRequest request,
			HttpServletResponse response) {
		List<Map> list = menuService.getMenuAction();
		List<TreeBasic> basicTree = new ArrayList<TreeBasic>();
		TreeBasic top = new TreeBasic();
		top.setId("0");
		top.setName("");//TODO
		top.setExpanded(true);
		top.setParentId("");
		HashMap topAttrMap = new HashMap();
		topAttrMap.put("action", null);
		top.setAttributes(topAttrMap);
		basicTree.add(top);				
		for(Map map : list){
			TreeBasic tb = new TreeBasic();
			tb.setId(map.get("ID").toString()); //可以外挂属�?
			if(map.get("NAME")!=null){
				tb.setName(map.get("NAME").toString());
			}			
			tb.setExpanded(true);		
			if(map.get("PARENTID")!=null){
				tb.setParentId(map.get("PARENTID").toString());
			}
			else{
				tb.setParentId("0");
			}
			HashMap attrMap = new HashMap();
			attrMap.put("action", map.get("ACTION")==null?null:map.get("ACTION").toString());
			attrMap.put("isLeaf", map.get("ISLEAF")==null?null:map.get("ISLEAF").toString());
			if(attrMap.get("isLeaf").toString().equalsIgnoreCase("n")){
				tb.setAttributes(attrMap);
				basicTree.add(tb);
			}
		}
		List<TreeBean> treeNodes = TreeUtil.onTree(basicTree, "", "");
		
 		return JSON.toJSON(treeNodes);
	}
	
	@RequestMapping(value="/selectFuncList.json")
	@ResponseBody
	public Object selectFuncList(MenuFuncListBean bean, HttpServletRequest request,
			HttpServletResponse response){
		Map map = new HashMap();
		map.put("pagination", bean);
		if(StringUtils.isNotBlank(bean.getAppName())){
			map.put("appName", bean.getAppName().trim());
		}
		if(StringUtils.isNotBlank(bean.getFuncName())){
			map.put("funcName",bean.getFuncName().trim());
		}
		if(StringUtils.isNotBlank(bean.getFuncGroupName())){
			map.put("funcGroupName",bean.getFuncGroupName().trim());
		}
		
		List<Map> lm = menuService.selectFuncList(map);
		JsonResult result = new JsonResult();
		ListData lr = new ListData(bean.getTotalCount(), menuMapToListReturn(lm));
		result.setData(lr);
		return JSON.toJSON(result);
		
	}
	
	/**
	 * 添加菜单信息
	 * @param bean
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/insertMenu.json")
	public Object insertMenu(MenuBean bean,
			HttpServletRequest request,HttpServletResponse response)
				throws Exception{
		//menuId自动增长，不�?���?��，只�?��行数据处�?
		JsonResult result=QequestDataProcess(bean,false);
		//清空缓存
		Menu menu=(Menu)result.getCacheData(true);
		//将menu中的数据插入数据库，并返回插入的条数
		Integer i=menuService.insertMenu(menu);
		if(!result.isSuccess()){
			return result;
		}
		result.setData(i);
		return JSON.toJSON(result);
	}
	
	@ResponseBody
	@RequestMapping(value="/deleteMenuById.json")
	public Object deleteMenuById(DeleteByStringIds ids,
			HttpServletRequest request,HttpServletResponse response)
				throws Exception{
		//处理DeleteByStringIds�?��收的String类型的多个id
		JsonResult result=ids.checkParameters();
		if(!result.isSuccess()){//数据为空�?返回null
			return result;
		}
		//执行删除多条Id，并返回删除记录的条�?
		Integer i=menuService.deleteMenuById(ids.getIds());
		if(i<=0){
			result.addError(new MyError(
						MyError.CommonCode.DELETE_IDS_NULL,
						MyError.CommonMsg.DELETE_IDS_NULL ));
		}else{
			result.setData(i);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/updateMenuById.json")
	public Object updateMenuById(MenuBean bean,
			HttpServletRequest request,HttpServletResponse response)
				throws Exception{
		JsonResult result=new JsonResult();
		//�?��menuId是否为空
		if(StringUtils.isBlank(bean.getMenuId())){
			result.addError(new MyError(
						MyError.SystemCode.SUBFUNCTIONDATA_INSERT_ERROR, 
						MyError.SystemMsg.SUBFUNCTIONDATA_INSERT_ERROR));
			return result;
		}
		//处理要修改的数据
		result=QequestDataProcess(bean,true);
		if(!result.isSuccess())
		{
			return result;
		}
		//清除页面中的缓存数据
		Menu menu=(Menu)result.getCacheData(true);
		Integer i=menuService.updateMenuById(menu);
		String oldSEQ = menuService.selectSeqById(menu.getMenuId());
		Map map = new HashMap();
		if(menu.getParentsId()!=null){
			map.put("parentsId", menu.getParentsId());
			map.put("id", menu.getMenuId());
			menuService.updateMenuPMenuSEQ(map);
		}else{
			menuService.updateMenuMenuSEQ(menu.getMenuId());
		}
		String newSEQ = menuService.selectSeqById(menu.getMenuId());
		Map mapSeq = new HashMap();
		mapSeq.put("oldSEQ", oldSEQ);
		mapSeq.put("newSEQ", newSEQ);
		menuService.updateAllSeq(mapSeq);
		if(i<=0){
			result.addError(new MyError(
					MyError.SystemCode.MENU_MENUID_NOT_MATCH, 
					MyError.SystemMsg.MENU_MENUID_NOT_MATCH));
			return result;
		}
		result.setData("修改的记录条数："+i);
		return JSON.toJSON(result);
	}
	
	@ResponseBody
	@RequestMapping(value="/loadMenuById.json")
	public Object loadMenuById(String menuId,
			HttpServletRequest request,HttpServletResponse response)
					throws Exception{
		JsonResult result=new JsonResult();
		//�?��页面传来的menuId是否为空
		if(StringUtils.isBlank(menuId)){
			result.addError(new MyError(
					MyError.SystemCode.SUBFUNCTIONDATA_INSERT_ERROR, 
					MyError.SystemMsg.SUBFUNCTIONDATA_INSERT_ERROR));
			return result;
		}
		//将页面接收的menuId装换为Integer类型

		Integer id=0;
		try {
			id = Integer.parseInt(menuId);
		} catch (Exception e) {
			result.addError(new MyError(
					MyError.SystemCode.MENU_MENUID_DATA_CONVERSION_FAIL,
					MyError.SystemMsg.MENU_MENUID_DATA_CONVERSION_FAIL));
			return result;
		}
		HashMap map = menuService.loadMenuById(id);//获取加载的数�?
		if(map!=null){
			MenuLoadInfo info=(MenuLoadInfo)LoadDataProcess(map);
			result.setData(info);
		}else{
			//map为空，没有和menuId相匹配的数据
			result.addError(new MyError(
					MyError.SystemCode.MENU_MENUID_NOT_MATCH, 
					MyError.SystemMsg.MENU_MENUID_NOT_MATCH));
		}
		return result;
	}
	
	
	/**
	 * 将LoadMenuById从数据库加载再�?过Map传来的一条记录�?过处理，
	 * 用MenuLoadInfo保存,用于响应页面
	 * @param map
	 * @return
	 */
	private MenuLoadInfo LoadDataProcess(Map map){
		MenuLoadInfo info=new MenuLoadInfo();
		if(map.get("parentMenuLabel")!=null){
			info.setM_parentMenuLabel((String)map.get("parentMenuLabel"));
		}
		if(map.get("menuName")!=null){
			info.setM_menuName((String)map.get("menuName"));
		}
		if(map.get("menuLabel")!=null){
			info.setM_menuLabel((String)map.get("menuLabel"));
		}
		if(map.get("menuCode")!=null){
			info.setM_menuCode((String)map.get("menuCode"));
		}
		if(map.get("isLeaf")!=null){
			info.setM_isLeaf((String)map.get("isLeaf"));
		}
		if(map.get("funcAtion")!=null){
			info.setM_funcAtion((String)map.get("funcAtion"));
		}
		if(map.get("funcName")!=null){
			info.setM_funcName((String)map.get("funcName"));
		}
		if(map.get("desplayOrder")!=null){
			info.setM_desplayOrder((Integer)map.get("desplayOrder"));
		}
		if(map.get("menuLevel")!=null){
			info.setM_menuLevel((Integer)map.get("menuLevel"));
		}
		if(map.get("subCount")!=null){
			info.setM_subCount((Integer)map.get("subCount"));
		}
		if(map.get("funcCode")!=null){
			info.setM_funcCode((Integer)map.get("funcCode"));
		}
		if(map.get("parentsId")!=null){
			info.setM_parentsId((Integer)map.get("parentsId"));
		}
		return info;
	}
	
	/**
	 * 处理新增和修改时页面传来的数据，并用JsonResult包装
	 * 修改时需要id为true，新增时为false
	 * @param bean
	 * @return
	 */
	private JsonResult QequestDataProcess(MenuBean bean,boolean needId){
		JsonResult result=new JsonResult();
		Menu menu=new Menu();
		//将页面接收到的数据存入Menu�?
		if(needId){
			if(bean.getMenuId()!=null){
				try {
					menu.setMenuId(Integer.parseInt(bean.getMenuId()));
				} catch (Exception e) {
					result.addError(new MyError(
							MyError.SystemCode.MENU_MENUID_DATA_CONVERSION_FAIL,
							MyError.SystemMsg.MENU_MENUID_DATA_CONVERSION_FAIL));
				}
			}else{
				result.addError(new MyError(
							MyError.SystemCode.MENU_MENUID_NOT_NULL,
							MyError.SystemMsg.MENU_MENUID_NOT_NULL));
			}
		}
		menu.setMenuName(bean.getMenuName());
		menu.setMenuLabel(bean.getMenuLabel());
		menu.setMenuCode(bean.getMenuCode());
		menu.setIsLeaf(bean.getIsLeaf());
		menu.setMenuAction(bean.getMenuAction());
		//菜单等级
		if(bean.getMenuLevel()!=null){
			try
			{
				menu.setMenuLevel(Integer.parseInt(bean.getMenuLevel()));
			}catch(Exception e)
			{
				result.addError(new MyError(
						MyError.SystemCode.MENU_MENUID_DATA_CONVERSION_FAIL,
						MyError.SystemMsg.MENU_MENUID_DATA_CONVERSION_FAIL));
			}
			
		}else{
			result.addError(new MyError(
						MyError.SystemCode.MENU_MENULEVEL_NOT_NULL,
						MyError.SystemMsg.MENU_MENULEVEL_NOT_NULL));
		}
		//父结点ID
		if(bean.getParentsId()!=null
				&&!bean.getParentsId().equals("0")
				&&!bean.getParentsId().equals("")){
			try {
				menu.setParentsId(Integer.parseInt(bean.getParentsId().trim()));
			} catch (Exception e) {
				result.addError(new MyError(
						MyError.SystemCode.MENU_MENUID_DATA_CONVERSION_FAIL,
						MyError.SystemMsg.MENU_MENUID_DATA_CONVERSION_FAIL));
			}
		}else {
			menu.setParentsId(null);
		}
		//显示顺序
		if(bean.getDisplayOrder()!=null){
			try {
				menu.setDisplayOrder(Integer.parseInt(bean.getDisplayOrder()));
			} catch (Exception e) {
				result.addError(new MyError(
						MyError.SystemCode.MENU_MENUID_DATA_CONVERSION_FAIL,
						MyError.SystemMsg.MENU_MENUID_DATA_CONVERSION_FAIL));
			}
		}else{
			result.addError(new MyError(
						MyError.SystemCode.MENU_DISPLAYORDER_NOT_NULL,
						MyError.SystemMsg.MENU_DISPLAYORDER_NOT_NULL));
		}
		
		menu.setOpenMode(bean.getOpenMode());
		
		if(null != bean.getAppId())
		{
			try {
				menu.setAppId(Integer.parseInt(bean.getAppId()));
			} catch (Exception e) {
				result.addError(new MyError(
						MyError.SystemCode.MENU_DISPLAYORDER_NOT_NULL,
						MyError.SystemMsg.MENU_DISPLAYORDER_NOT_NULL));
			}
		}
		
		if(bean.getFuncCode()!=null){
			try {
				menu.setFuncCode(Integer.parseInt(bean.getFuncCode()));
			} catch (Exception e) {
				result.addError(new MyError(
						MyError.SystemCode.MENU_DISPLAYORDER_NOT_NULL,
						MyError.SystemMsg.MENU_DISPLAYORDER_NOT_NULL));
			}
		}else{
			result.addError(new MyError(
								MyError.SystemCode.MENU_FUNCCODE_NOT_NULL,
								MyError.SystemMsg.MENU_FUNCCODE_NOT_NULL));
		}
		
		//将处理好的menu数据用result包装
		if(result.isSuccess())
		{
			result.setData(menu);
		}
		return result;
	}
	
	
	private List<MenuListReturnBean> menuMapToListReturn(List<Map> listmap){
		if(listmap == null || listmap.size()==0){
			return null;
		}
		List<MenuListReturnBean> listBean = new ArrayList<MenuListReturnBean>();
		for(int i = 0 ; i < listmap.size();i++){
			MenuListReturnBean Mbean = new MenuListReturnBean();
			Map map = listmap.get(i);
			if(map.get("APPID")!=null){
				Mbean.setAppId(Integer.parseInt(map.get("APPID").toString()));
			}
			if(map.get("APPNAME")!=null){
				Mbean.setAppName(map.get("APPNAME").toString());
			}
			if(map.get("FUNCACTION")!=null){
				Mbean.setFuncAction(map.get("FUNCACTION").toString());
			}
			if(map.get("FUNCCODE")!=null){
				Mbean.setFuncCode(map.get("FUNCCODE").toString());
			}
			if(map.get("FUNCGROUPNAME")!=null){
				Mbean.setFuncGroupName(map.get("FUNCGROUPNAME").toString());
			}
			if(map.get("FUNCNAME")!=null){
				Mbean.setFuncName(map.get("FUNCNAME").toString());
			}
			listBean.add(Mbean);
		}
	
		return listBean;
	}
	
	private List<MenuInfo> menuInfosToAPPRecords(List<Map> list){
		
		List<MenuInfo> menuInfos=new ArrayList<MenuInfo>();
		for(int i=0;i<list.size();i++){
			Map map=list.get(i);
			MenuInfo menuInfo=new MenuInfo();
			if(map.get("MENUCODE")!=null){
				menuInfo.setMenuCode(map.get("MENUCODE").toString());				
			}
			if(map.get("MENULABEL")!=null){
				menuInfo.setMenuLabel(map.get("MENULABEL").toString());				
			}
			if(map.get("MENULEVEL")!=null){
				menuInfo.setMenuLevel(map.get("MENULEVEL").toString());			
			}
			if(map.get("MENUNAME")!=null){
				menuInfo.setMenuName(map.get("MENUNAME").toString());				
			}
			if(map.get("ISLEAF")!=null){
				if(map.get("ISLEAF").equals("y")){
					menuInfo.setIsLeaf("");//TODO			
				}else if(map.get("ISLEAF").equals("n")){
					menuInfo.setIsLeaf("");//TODO		
				}
			}
			if(map.get("DISPLAYORDER")!=null){
				menuInfo.setDisplayOrder((Integer)map.get("DISPLAYORDER"));			
			}

			menuInfos.add(menuInfo);
		}
		
		return menuInfos;
	}

}
