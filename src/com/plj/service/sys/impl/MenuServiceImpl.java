package com.plj.service.sys.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plj.common.error.MyError;
import com.plj.common.result.JsonResult;
import com.plj.dao.sys.MenuDao;
import com.plj.domain.decorate.sys.Menu;
import com.plj.service.sys.MenuService;

@Transactional
@Service("menuService")
@SuppressWarnings({"rawtypes","unchecked"})
public class MenuServiceImpl implements MenuService
{
	@Autowired
	private MenuDao menuDao;
	
	
	@Override
	public List<Map> getMenuByUserId(Integer userId)
	{
		return menuDao.getMenuByUserId(userId);
	}
	
	
	@Override
	public List<Map> selectFuncList(Map menu) {
		if(menu!=null){
			return menuDao.selectFuncList(menu);
		}else{
			return null;
		}
		
	}

	
	@Override
	public List<Map> getAllMenuList(Map map) {
		return menuDao.getAllMenuList(map);
	}

	
	@Override
	public Integer insertMenu(Menu menu) {
		//插入页面传来的数据，并返回menuId
		menuDao.insertMenu(menu);
		int menuId=menu.getMenuId();
		//parentsId为空则只修改MENUSEQ,再返�?
		if(menu.getParentsId()==null){
			menuDao.updateMenuMenuSEQ(menuId);
			return menuId;
		}
		//查询出插入数据menu中的父结点中的数据pmenu
		Menu pmenu=menuDao.getMenuById(menu.getParentsId());
		if(pmenu!=null){//若该结点有父结点，则修改表中SUBCOUNT和MENUSEQ的�?
			//修改有父节点时MENUSQE中的数据
			Map map=new HashMap();
			map.put("id", menuId);
			map.put("parentsId",menu.getParentsId() );
			menuDao.updateMenuPMenuSEQ(map);
			//直到没父节点，�?出循�?
			while(pmenu!=null){
				//父节点的subCount++
				menuDao.updateMenuSubCount(pmenu.getMenuId());
				if(pmenu.getParentsId()!=null){
					pmenu=menuDao.getMenuById(pmenu.getParentsId());
				}else{
					pmenu=null;//没有父节�?
				}
			}
		}else{
			menuDao.updateMenuMenuSEQ(menu.getMenuId());
		}
		return menuId;
	}


	
	@Override
	public Integer deleteMenuById(List<String> ids) {
		Integer id=0;
		Integer count=0;
		JsonResult result=new JsonResult();	
		for(int i=0;i<ids.size();i++){
			if(StringUtils.isBlank(ids.get(i))){
				continue;//id为空则跳出当前循�?
			}
			try {
				id=Integer.parseInt((String)ids.get(i));
			} catch (Exception e) {
				result.addError(new MyError(
						MyError.SystemCode.MENU_MENUID_DATA_CONVERSION_FAIL,
						MyError.SystemMsg.MENU_MENUID_DATA_CONVERSION_FAIL));
			}
			//查询要删除的节点的信�?
			if(id==0){
				continue; //没有id=0的记�?
			}
			Menu menu=menuDao.getMenuById(id);
			if(menu!=null){
				//查询要删除节点的�?��子节点（包括自身）的总数count
				Integer nodeCount=menuDao.menuNodeCount(id);
				//将要删除节点的所有父节点的SUBCOUNT值减去其子节点的总数
				while(menu.getParentsId()!=null&&menu.getParentsId()!=0){
					//获得父节点menuId
					menu=menuDao.getMenuById(menu.getParentsId());
					if(menu!=null){
						Map map=new HashMap();
						map.put("count", nodeCount);
						map.put("id", menu.getMenuId());
						//该结点subCount-count
						menuDao.reduceSubCount(map);
					}else{
						break;
					}
				}
				//删除该节点和子节�?
				Integer c=menuDao.deleteMenuById(id);
				//删除的记录数count++
				count=count+c;
			}else{
					result.addError(new MyError(
						MyError.SystemCode.MENU_MENUID_NOT_MATCH,
						MyError.SystemMsg.MENU_MENUID_NOT_MATCH+":"+id ));
			}
		}
		return count;
	}



	@Override
	public Integer updateMenuById(Menu menu){
		Integer menuid = menu.getMenuId();
		Menu oldmenu = menuDao.getMenuById(menuid);
		Integer oldlevel = oldmenu.getMenuLevel();
		Integer newlevel = menu.getMenuLevel();
		Integer nodeCount=menuDao.menuNodeCount(menuid);
		if(oldmenu.getParentsId()!=menu.getParentsId()){
			while(oldmenu.getParentsId()!=null&&oldmenu.getParentsId()!=0){
				oldmenu=menuDao.getMenuById(oldmenu.getParentsId());
				if(oldmenu!=null){
					Map map=new HashMap();
					map.put("count", nodeCount);
					map.put("id", oldmenu.getMenuId());
					//该结点subCount-count
					menuDao.reduceSubCount(map);
				}else{
					break;
				}
			}
			Integer updataNum= menuDao.updateMenuById(menu);
			Map<String,Integer> map2=new HashMap<String,Integer>();
			map2.put("num", newlevel-oldlevel+1);
			map2.put("id", menuid);
			menuDao.updateLevel(map2);
			while(menu.getParentsId()!=null&&menu.getParentsId()!=0){
				menu=menuDao.getMenuById(menu.getParentsId());
				if(menu!=null){
					Map map=new HashMap();
					map.put("count", nodeCount);
					map.put("id", menu.getMenuId());
					//该结点subCount+count
					menuDao.addSubCount(map);
				}else{
					break;
				}
			}
			return updataNum;
		}else{
			return menuDao.updateMenuById(menu);
		}
	}



	@Override
	public HashMap loadMenuById(Integer id) {
		return menuDao.loadMenuById(id);
	}

	@Override
	public Menu getMenuById(Integer id) {
		return menuDao.getMenuById(id);
	}

	@Override
	public List<Map> getMenuAction() {
		return menuDao.getMenuAction();
	}


	@Override
	public Integer updateMenuPMenuSEQ(Map map) {
		return menuDao.updateMenuPMenuSEQ(map);
	}


	@Override
	public Integer updateMenuMenuSEQ(Integer menuId) {
		return menuDao.updateMenuMenuSEQ(menuId);
	}


	@Override
	public String selectSeqById(Integer menuId) {
		return menuDao.selectSeqById(menuId);
	}


	@Override
	public Integer updateAllSeq(Map mapSeq) {
		return menuDao.updateAllSeq(mapSeq);
	}




}
