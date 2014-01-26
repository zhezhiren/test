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
import com.plj.dao.sys.FunctionGroupDao;
import com.plj.domain.decorate.sys.FunctionGroup;
import com.plj.service.sys.FunctionGroupService;

@Transactional
@Service("functionGroupService")
@SuppressWarnings({"rawtypes","unchecked"})
public class FunctionGroupServiceImpl implements FunctionGroupService {

	@Override
	public FunctionGroup insertFunctionGroup(FunctionGroup functionGroup) {
		//插入数据
		int i = functionGroupDao.insertFuncGroup(functionGroup);
		int funcGroupId=functionGroup.getFunctionGroupId();
		//父节点为空，则修改FUNCGROUPSEQ,插入数据完成
		if(functionGroup.getParentGroup()==null){
			functionGroupDao.updateFuncGroupSEQ(funcGroupId);
			if(i==1){
				return functionGroup;
			}else{
				return null;
			}
		}
		
		int parentGroup=functionGroup.getParentGroup();
		
		//修改PARENTGROUP
		if(parentGroup==0){
			functionGroupDao.updateParentGroup(parentGroup);
			functionGroupDao.updateFuncGroupSEQ(funcGroupId);
		}else{
			int count=functionGroupDao.searchCount(parentGroup);
			//修改FUNCGROUPSEQ
			if(count==0){
				functionGroupDao.updateFuncGroupSEQ(funcGroupId);
			}else{
				Map map=new HashMap();
				map.put("parentGroup", parentGroup);
				map.put("funcGroupId", funcGroupId);
				functionGroupDao.updateFuncGroupSEQ_P(map);
			}
		}
		
		if (i == 1) {
			return functionGroup;
		} else {
			return null;
		}
	}

	@Override
	public Integer updateFunctionGroup(FunctionGroup functionGroup) {
		int i = functionGroupDao.updateById(functionGroup);
		return i;
	}

	@Override
	public Integer deleteByIds(List<String> ids) {
		JsonResult result=new JsonResult();
		Integer id=0;
		int count = 0;
		for (int i = 0; i < ids.size(); i++) {
			if(StringUtils.isBlank(ids.get(i))){
				continue;
			}
			try {
				id=Integer.parseInt(ids.get(i));
			} catch (Exception e) {
				result.addError(new MyError(
						MyError.SystemCode.MENU_MENUID_DATA_CONVERSION_FAIL,
						MyError.SystemMsg.MENU_MENUID_DATA_CONVERSION_FAIL));
			}
			if(id==0){
				continue;
			}
			int nodecount = functionGroupDao.deleteById(id);
			count += nodecount;
		}
		return count;
	}

	public Integer deleteById(Integer id) {
		return functionGroupDao.deleteById(id);
	}

	public List<FunctionGroup> searchFunctionGroups(HashMap map) {
		return functionGroupDao.searchFunctionGroups(map);
	}

	@Autowired
	private FunctionGroupDao functionGroupDao;
}
