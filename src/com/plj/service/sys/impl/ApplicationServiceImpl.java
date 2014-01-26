package com.plj.service.sys.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plj.dao.sys.ApplicationDao;
import com.plj.domain.decorate.sys.Application;
import com.plj.domain.decorate.sys.Function;
import com.plj.service.sys.ApplicationService;

@Transactional
@Service("applicationService")
public class ApplicationServiceImpl implements ApplicationService
{
	public Application insertApplication(Application application)
	{
		Integer i = applicationDao.save(application);
		if(i == 1)
		{
			return application;
		}
		else
		{
			return null;
		}
	}
	
	@Override
	public Application updateApplication(Application application)
	{
		applicationDao.updateApplication(application);
		return application;
	}
	
	@Override
	public Integer deleteApplicationsByIds(List<Integer> ids)
	{
		return applicationDao.deleteApplicationsByIds(ids);
	}
	

	@Override
	public List<Application> getAllApplication() 
	{
		return applicationDao.getAllApplication();
	}
	
	@SuppressWarnings("rawtypes")
	public List<Application> searchApplication(Map map)
	{
		return applicationDao.searchApplication(map);
	}
	
	@Autowired
	ApplicationDao applicationDao;

	@Override
	public boolean funcNameExists(String funcName) {
		return applicationDao.funcNameExists(funcName)>0;
	}

	@Override
	public Boolean funcGroupNameExists(String funcGroupName) {
		return applicationDao.funcGroupNameExists(funcGroupName)>0;
	}

	@Override
	public Function insertFunction(Function function) {
		int i = applicationDao.insertFunction(function);
		return i==1?function:null;
	}

	@Override
	public Function updateFunction(Function function) {
		return applicationDao.updateFunction(function)==1?function:null;
	}

	@Override
	public int deleteFunction(List<Integer> ids) {
		return applicationDao.deleteFunctionByIds(ids);
	}

	@Override
	public boolean appNameExits(String appName) {
		return applicationDao.appNameExits(appName)>0;
	}
	
}
