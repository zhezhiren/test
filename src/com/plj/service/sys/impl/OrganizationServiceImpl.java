package com.plj.service.sys.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plj.dao.sys.OrganizationDao;
import com.plj.domain.bean.sys.OrganizationTreeBean;
import com.plj.domain.decorate.sys.Organization;
import com.plj.domain.request.sys.EmpCodeExistsBean;
import com.plj.domain.request.sys.OrgCodeExistsBean;
import com.plj.domain.request.sys.UpdateOrgByIdBean;
import com.plj.domain.response.sys.AreaBean;
import com.plj.domain.response.sys.OrgInfo;
import com.plj.domain.response.sys.OrgInfoById;
import com.plj.service.sys.OrganizationService;

@Service("organizationService")
public class OrganizationServiceImpl implements OrganizationService
{
	
	@SuppressWarnings("rawtypes")
	public List<Organization> searchOrganization(Map map)
	{
		return organizationDao.searchOrganization(map);
	}
	
	@Autowired
	OrganizationDao organizationDao;

	@Override
	public Organization insertOrg(Organization bean) {
		int a = organizationDao.insertOrg(bean);
		if(a==1){
			return bean;
		}else{
			return null;
		}
	}

	@Override
	public Integer deleteOrg(List<Integer> ids) {
		int c=0;
		for(int i = 0 ; i < ids.size(); i++){
				int b = organizationDao.deleteOrg(ids.get(i));
				c+=b;
		}
		return c;
	}

	@Override
	public int orgCodeExists(OrgCodeExistsBean bean) {
		int c = organizationDao.orgCodeExists(bean);
		if(c!=0){
			return c;
		}else{
			return 0;
		}
	}

	@Override
	public int empCodeExists(EmpCodeExistsBean bean) {
		int d = organizationDao.empCodeExists(bean);
		if(d!=0){
			return d;
		}else{
			return 0;
		}
	}

	@Override
	public Integer updateOrgById(UpdateOrgByIdBean bean) {
		int e = organizationDao.updateOrgById(bean);
		
			return e;
		
	}

	@Override
	public List<OrgInfo> loadOrgById(String orgId) {
		return organizationDao.loadOrgById(orgId);
	}

	@Override
	public List<OrgInfo> loadParentOrg(String orgId) {
		return organizationDao.loadParentOrg(orgId);
	}

	@Override
	public OrgInfoById loadOrgInfoById(String orgId) {
		
		return organizationDao.loadOrgInfoById(orgId);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<OrganizationTreeBean> getOrgTrees(Map map) {
		return organizationDao.getOrgTrees(map);
	}
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> getOrgTree() {
		return organizationDao.getOrgTree();
	}

	@Override
	public List<AreaBean> loadArea(Map map) {
		return organizationDao.loadArea(map);
	}

	@Override
	public Integer orgNameExists(String orgName) {
		return organizationDao.orgNameExists(orgName);
	}
}
