package com.plj.domain.bean.sys;

import java.util.ArrayList;
import java.util.List;

import com.plj.domain.response.sys.TreeBean;

public class TreeUtil { 
//	private static LogUtil log = new LogUtil(TreeUtil.class);
	/**
	 * 根据id,name,和parentId创建�?
	 * @author liuzhuoyu
	 * @param list 节点集合
	 * @param nodeCls 树节点的图片,如果为空则默认图�?
	 * @param leafCls 叶子节点的图�?如果为空则默认图�?
	 * @return
	 */
	public static List<TreeBean> onTree(List<TreeBasic> list,String nodeCls,String leafCls){
		try{
		nodeCls = (nodeCls==null)?"":nodeCls;
		leafCls = (leafCls==null)?"":leafCls;
		 List<TreeBean> result = new ArrayList<TreeBean>();
			for(TreeBasic t : list)
				if(t.getParentId()==null || t.getParentId().equals("")){
					TreeBean tree = new TreeBean();
					tree.setId(t.getId());
					tree.setText(t.getName());
					tree.setHref(t.getHref());
					tree.setExpanded(t.isExpanded());
					tree.setUiProvider(t.getUiProvider());
					tree.setHrefTarget(t.getHrefTarget());
					tree.setIconCls(nodeCls);
					tree.setAttributes(t.getAttributes());
					result.add(tree);
				}
				
			for(TreeBean tree : result){
				createSubTree(tree,list,nodeCls,leafCls);
			}
			return result;
		}catch(Exception e){
//			log.error(e);
			return null;
		}
	}
	
	/**
	 * 递归查找子树
	 * @author liuzhuoyu
	 * @param top
	 * @param list
	 * @param nodeCls
	 * @param leafCls
	 */
	private static void createSubTree(TreeBean top, List<TreeBasic> list,String nodeCls,String leafCls) {
		try{
			if(!isLeaf(top.getId(),list)) {
				List<TreeBean> subNodes = getDirectSub(top.getId(),list);
				for(TreeBean t : subNodes){
					top.setLeaf("0");
					top.setIconCls(nodeCls);
					if(top.getChildren()==null)
						top.setChildren(new ArrayList<TreeBean>());
					top.getChildren().add(t);
					createSubTree(t,list,nodeCls,leafCls);
				}
			}else {
				top.setIconCls(leafCls);
				top.setLeaf("1");
			}
		}catch(Exception e){
//			log.error(e);
		}
	}
	
	/**
	 * 得到直接下级节点
	 * @author liuzhuoyu
	 * @param id
	 * @param list
	 * @return
	 */
	private static List<TreeBean> getDirectSub(String id,List<TreeBasic> list){
		try{
			List<TreeBean> result = new ArrayList<TreeBean>();
			for(TreeBasic m : list){
				if(m.getParentId()!=null && m.getParentId().equals(id) && !m.getId().equals(id)){//节点本身要除�?
					m.setParentId(id);
					TreeBean tree = new TreeBean();
					tree.setId(m.getId());
					tree.setExpanded(m.isExpanded());
					tree.setText(m.getName());
					tree.setUiProvider(m.getUiProvider());
					tree.setHref(m.getHref());
					tree.setHrefTarget(m.getHrefTarget());
					tree.setAttributes(m.getAttributes());
					result.add(tree);
				}
			}
			return result;
		}catch(Exception e){
//			log.error(e);
			return null;
		}
	}

	/**
	 * 判断是否叶子节点
	 * @author liuzhuoyu
	 * @param id
	 * @param list
	 * @return
	 */
	private static boolean isLeaf(String id,List<TreeBasic> list){
		try{
			for(TreeBasic m : list){
				if(m.getParentId()!=null && m.getParentId().equals(id)){
					return false;
				}
			}
			return true;
		}catch(Exception e){
//			log.error(e);
			return false;
		}
	}
}
