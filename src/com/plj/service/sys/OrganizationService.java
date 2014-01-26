package com.plj.service.sys;

import java.util.List;
import java.util.Map;

import com.plj.domain.bean.sys.OrganizationTreeBean;
import com.plj.domain.decorate.sys.Organization;
import com.plj.domain.request.sys.EmpCodeExistsBean;
import com.plj.domain.request.sys.OrgCodeExistsBean;
import com.plj.domain.request.sys.UpdateOrgByIdBean;
import com.plj.domain.response.sys.AreaBean;
import com.plj.domain.response.sys.OrgInfo;
import com.plj.domain.response.sys.OrgInfoById;

@SuppressWarnings("rawtypes")
public interface OrganizationService 
{
	/**
	 * 查询组织机构
	 * @param map
	 * @return
	 */
	public List<Organization> searchOrganization(Map map);
	public List<OrganizationTreeBean> getOrgTrees(Map map);
	public List<Map> getOrgTree();
	
	public Organization insertOrg(Organization org); 
	public Integer deleteOrg(List<Integer> ids);
	public int orgCodeExists(OrgCodeExistsBean bean);
	public int empCodeExists(EmpCodeExistsBean bean);
	public Integer updateOrgById(UpdateOrgByIdBean bean);
	public List<OrgInfo> loadOrgById(String orgId);
	public List<OrgInfo> loadParentOrg(String orgId);
	public OrgInfoById loadOrgInfoById(String orgId);
	public List<AreaBean> loadArea(Map map);
	public Integer orgNameExists(String orgName);
}
