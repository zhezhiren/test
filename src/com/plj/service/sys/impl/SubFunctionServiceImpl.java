package com.plj.service.sys.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plj.dao.sys.SubFunctionDataDao;
import com.plj.domain.bean.sys.SubFunctionDataBean;
import com.plj.domain.decorate.sys.SubFunctionData;
import com.plj.service.sys.SubFunctionDataService;

@Transactional
@Service("subFunctionDataService")
public class SubFunctionServiceImpl implements SubFunctionDataService{

	@Autowired
	SubFunctionDataDao subFuncDataDao;
	
	@Override
	public SubFunctionData insertSubFunctionData(SubFunctionData subFucData) {
		Integer i=subFuncDataDao.insertSubFunctionData(subFucData);
		if(i==1){
			return subFucData;
		}
		else{
			return null;	
		}
	}
	
	@Override
	public SubFunctionData updateSubFunctionData(SubFunctionData subFucData) {
		Integer i=subFuncDataDao.updateSubFunctionData(subFucData);
		if(i==1){
			return subFucData;
		}
		else{
			return null;	
		}
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List<SubFunctionData> getSubFunctionData(HashMap map) 
	{
		return subFuncDataDao.getSubFuncData(map);
	}

	@Override
	public SubFunctionDataBean deleteSubFunctionData(SubFunctionDataBean subFuncData) {
		Integer j=subFuncDataDao.deleteSubFunctionData(subFuncData);
		if(j==1){
			return subFuncData;
		}
		else{
			return null;	
		}
	}



}
