/**
 * 
 */
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
import com.plj.common.tools.mybatis.page.bean.Pagination;
import com.plj.domain.bean.sys.OperatorBean;
import com.plj.domain.bean.sys.OrganizationBean;
import com.plj.domain.bean.sys.OrganizationTreeBean;
import com.plj.domain.bean.sys.TreeBasic;
import com.plj.domain.bean.sys.TreeUtil;
import com.plj.domain.decorate.sys.Operator;
import com.plj.domain.decorate.sys.Organization;
import com.plj.domain.request.common.DeleteByIntegerIds;
import com.plj.domain.request.sys.EmpCodeExistsBean;
import com.plj.domain.request.sys.GetAreaRequest;
import com.plj.domain.request.sys.GetOperatorsPage;
import com.plj.domain.request.sys.InsertOrgBean;
import com.plj.domain.request.sys.OrgCodeExistsBean;
import com.plj.domain.request.sys.SearchOrganization;
import com.plj.domain.request.sys.UpdateOrgByIdBean;
import com.plj.domain.response.sys.AreaBean;
import com.plj.domain.response.sys.OrgInfo;
import com.plj.domain.response.sys.OrgInfoById;
import com.plj.domain.response.sys.TreeBean;
import com.plj.service.sys.OperatorService;
import com.plj.service.sys.OrganizationService;

/**机构相关处理请求**/
@Controller
@RequestMapping(value="/Organization")
public class OrganizationAction {
	@Autowired
	@Qualifier(value = "organizationService")
	private OrganizationService organizationService;
	
	@Autowired
	@Qualifier("operatorService")
	OperatorService operatorService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/getOrgTrees.json")
	@ResponseBody
	public Object getOrgTrees (Pagination pagination, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{		
		HashMap param = new HashMap();
		JsonResult result = new JsonResult();
		param.put("pagination", pagination);
		List<OrganizationTreeBean> orgs = organizationService.getOrgTrees(param);
		//ListData<String> lr = new ListData<String>(pagination.getTotalCount(), orgs);
		result.setData(orgs);
		return JSON.toJSON(result);
	}
	
	@RequestMapping(value="/getOperatorNotEmployee.json")
	@ResponseBody
	public Object searchOperator(GetOperatorsPage bean, HttpServletRequest request,
			HttpServletResponse response)
	{
		HashMap map = new HashMap();
		map.put("pagination", bean);
		if(StringUtils.isNotBlank(bean.getUserId()))
		{
			map.put("userId", "%" + bean.getUserId() + "%");
		}
		if(StringUtils.isNotBlank(bean.getOperatorName()))
		{
			map.put("operatorName", "%" + bean.getOperatorName() + "%");
		}
		List<Operator> operators = operatorService.getOperatorNotEmployee(map);
		JsonResult result = new JsonResult();
		ListData<OperatorBean> datas = new ListData<OperatorBean>(
				bean.getTotalCount(), OperatorAction.operatorsToPageBeans(operators));
		result.setData(datas);
		return JSON.toJSON(result);
	}

	/**
	 * 查询树状结构数据
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getOrgTree.json")
	@ResponseBody
	public Object getOrgTree(HttpServletRequest request,
			HttpServletResponse response) {
		List<Map> list = organizationService.getOrgTree();
		List<TreeBasic> basicTree = new ArrayList<TreeBasic>();
		TreeBasic top = new TreeBasic();
		top.setId("0");
		top.setName("");//TODO
		top.setExpanded(true);
		top.setParentId("");
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
			attrMap.put("isLeaf", map.get("ISLEAF")==null?null:map.get("ISLEAF").toString());
			tb.setAttributes(attrMap);
			basicTree.add(tb);
		}
		List<TreeBean> treeNodes = TreeUtil.onTree(basicTree, "", "");		
		return JSON.toJSON(treeNodes);
	}
	
	/**
	 * 查询机构列表作为分页数据�?
	 * @author xujianquan
	 * @param searchKey
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings( "rawtypes")
	@RequestMapping(value="/getOrgs.json")
	@ResponseBody
	public Object getOrgs(SearchOrganization searchKey, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{		
		HashMap param = new HashMap();
		JsonResult result = checkSearchOrg(searchKey);
		if(!result.isSuccess()){
			return result;
		}
		param = (HashMap)result.getCacheData(true);
		List<Organization> orgs = organizationService.searchOrganization(param);
		ListData<OrganizationBean> lr = new ListData<OrganizationBean>(searchKey.getTotalCount(), organizationsToOrgRecords(orgs));
		result.setData(lr);
		return JSON.toJSON(result);
	}
	
	/**
	 * 增加机构
	 * @author shijun
	 * @param bean
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/insertOrg.json")
	@ResponseBody
	public Object insertOrg(InsertOrgBean bean,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		JsonResult result =  checkInsertOrg(bean);
		if(!result.isSuccess())
		{
			return result;
		}
		Organization org = (Organization)result.getCacheData(true);
		Organization ooo=organizationService.insertOrg(org);
		if(ooo!=null){
			result.setData(ooo);
		}
		return JSON.toJSON(result);
	}
	
	/**
	 * 根据ID删除机构 （存储过程）
	 * @author shijun
	 * @param ids
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/deleteOrg.json")
	@ResponseBody
	public Object deleteOrg(DeleteByIntegerIds idsbean,HttpServletRequest request,
			HttpServletResponse response){
		JsonResult result = new JsonResult();
		if(idsbean==null){
			result.addError(new MyError(MyError.SystemCode.ORGANIZATION_NOT_ID, 
					MyError.SystemMsg.ORGANIZATION_NOT_ID));
				return result;
		}
		List<Integer> ids = new ArrayList<Integer>();
		ids=idsbean.getIds();
		if(ids==null || ids.size()==0){
			result.addError(new MyError(MyError.SystemCode.ORGANIZATION_NOT_ID, 
					MyError.SystemMsg.ORGANIZATION_NOT_ID));
				return result;
		}
		for(int i = 0;i<ids.size();i++){
				if(ids.get(i)==null){
					result.addError(new MyError(MyError.SystemCode.ORGANIZATION_NOT_ID, 
							MyError.SystemMsg.ORGANIZATION_NOT_ID));
				return result;
				}
		}
		int c = organizationService.deleteOrg(ids);
		if(c>0){
			result.setData(c);
		}else{
			result.addError(new MyError(MyError.SystemCode.ORGANIZATION_RETURN_NULL, 
					MyError.SystemMsg.ORGANIZATION_RETURN_NULL));
		}
		return JSON.toJSON(result);
	}
	/**
	 * 判断机构代码是否重复
	 * @author shijun
	 * @param bean
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/orgCodeExists.json")
	@ResponseBody
	public Object orgCodeExists(OrgCodeExistsBean bean,HttpServletRequest request,
			HttpServletResponse response){
		JsonResult result =  new JsonResult();
		if(bean==null){
			result.addError(new MyError(MyError.SystemCode.ORGANIZATION_CODE_NOT_EXISTS, 
				MyError.SystemMsg.ORGANIZATION_CODE_NOT_EXISTS));
		return result;
		}
		if(bean.getOrgCode()==null||"".equals(bean.getOrgCode())){
				result.addError(new MyError(MyError.SystemCode.ORGANIZATION_CODE_NOT_EXISTS, 
					MyError.SystemMsg.ORGANIZATION_CODE_NOT_EXISTS));
			return result;
		}
		int c = organizationService.orgCodeExists(bean);
		result.setData(c);
		return JSON.toJSON(result);
	}
	/**
	 * 判断员工代码是否重复
	 * @author shijun
	 * @param bean
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/empCodeExists.json")
	@ResponseBody
	public Object empCodeExists(EmpCodeExistsBean bean,HttpServletRequest request,
			HttpServletResponse response){
		JsonResult result =  new JsonResult();
		if(bean==null){
			result.addError(new MyError(MyError.SystemCode.ORGANIZATION_CODE_NOT_EXISTS, 
				MyError.SystemMsg.ORGANIZATION_CODE_NOT_EXISTS));
		return result;
		}
		if(bean.getEmpCode()==null||"".equals(bean.getEmpCode())){
				result.addError(new MyError(MyError.SystemCode.EMPLOYEE_CODE_NOT_EXISTS, 
					MyError.SystemMsg.EMPLOYEE_CODE_NOT_EXISTS));
			return result;
		}
		int c = organizationService.empCodeExists(bean);
		result.setData(c);
		return JSON.toJSON(result);
	}

	@RequestMapping(value="/orgNameExists.json")
	@ResponseBody
	public Object orgNameExists(String orgName,HttpServletRequest request,
			HttpServletResponse response){
		JsonResult result =  new JsonResult();
		if(orgName==null||orgName.equals("")){
			result.addError(new MyError("180", 
				"orgName为空"));
			return result;
		}
		int c = organizationService.orgNameExists(orgName);
		result.setData(c);
		return JSON.toJSON(result);
	}
	
	/**
	 * 修改结构信息
	 * @author shijun
	 * @param bean
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/updateOrgById.json")
	@ResponseBody
	public Object updateOrgById(UpdateOrgByIdBean bean,HttpServletRequest request,
			HttpServletResponse response){
		JsonResult result =  new JsonResult();
		if(bean==null){
			result.addError(new MyError(MyError.SystemCode.ORGANIZATION_UPDATE_BEAN_ERROR, 
				MyError.SystemMsg.ORGANIZATION_UPDATE_BEAN_ERROR));
			return result;
		}
		if(bean.getOrgId()==null){
				result.addError(new MyError(MyError.SystemCode.ORGANIZATION_UPDATE_BEAN_ERROR, 
					MyError.SystemMsg.ORGANIZATION_UPDATE_BEAN_ERROR));
			return result;
		}
		int a = organizationService.updateOrgById(bean);	
		if(a==0){
			result.addError(new MyError(MyError.SystemCode.ORGANIZATION_RETURN_NULL, 
					MyError.SystemMsg.ORGANIZATION_RETURN_NULL));
			return result;
		}else{
			result.setData(bean);
		}
		return JSON.toJSON(result);
	}
	
	/**
	 * 根据ID查询加载机构
	 * @author liufan
	 * @param request
	 * @param response
	 * @return
	 * **/
	@RequestMapping(value="/loadOrgById.json")
	@ResponseBody
	public Object loadOrgById(HttpServletRequest request,
			HttpServletResponse response){
		String orgId=request.getParameter("orgId");
		JsonResult result=new JsonResult();
		if(StringUtils.isBlank(orgId)){
			result.addError(new MyError(
					MyError.SystemCode.ORGANIZATION_ORGID_IS_NULL,
					MyError.SystemMsg.ORGANIZATION_ORGID_IS_NULL));
			
		}
		List<OrgInfo> list=organizationService.loadOrgById(orgId);
		ListData<OrgInfo> lr=new ListData<OrgInfo>((long)list.size(),list);
		result.setData(lr);
		return JSON.toJSON(result);
	}
	
	/**
	 * 根据ID加载当前机构的父机构
	 * @author liufan
	 * @param request
	 * @param response
	 * @return
	 * **/
	@RequestMapping(value="/loadParentOrg.json")
	@ResponseBody
	public Object loadParentOrg(HttpServletRequest request,
			HttpServletResponse response){
		String orgId=request.getParameter("orgId");
//		String orgId="2";
		JsonResult result=new JsonResult();
		if(StringUtils.isBlank(orgId)){
			result.addError(new MyError(
					MyError.SystemCode.ORGANIZATION_ORGID_IS_NULL,
					MyError.SystemMsg.ORGANIZATION_ORGID_IS_NULL));
			
		}
		List<OrgInfo> list=organizationService.loadOrgById(orgId);
		ListData<OrgInfo> lr=new ListData<OrgInfo>((long)list.size(),list);
		result.setData(lr);
		return JSON.toJSON(result);
	}
	
	/**
	 * 根据ID查询机构信息
	 * @author liufan
	 * @param request
	 * @param response
	 * @return
	 * **/
	@RequestMapping(value="/loadOrgInfoById.json")
	@ResponseBody
	public Object loadOrgInfoById(HttpServletRequest request,
			HttpServletResponse response){
		
		String orgId=request.getParameter("orgId");
		JsonResult result=new JsonResult();
		if(StringUtils.isBlank(orgId)){
			result.addError(new MyError(
					MyError.SystemCode.ORGANIZATION_ORGID_IS_NULL,
					MyError.SystemMsg.ORGANIZATION_ORGID_IS_NULL));
			
		}
		OrgInfoById list=organizationService.loadOrgInfoById(orgId);
		result.setData(list);
		return JSON.toJSON(result);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/loadArea.json")
	@ResponseBody
	public Object loadArea(GetAreaRequest bean,HttpServletRequest request,
			HttpServletResponse response){
		JsonResult result=new JsonResult();
		Map param=new HashMap();
		param.put("pagination", bean);
		if(StringUtils.isNotBlank(bean.getArea())){
			param.put("area", bean.getArea().trim());
		}
		if(StringUtils.isNotBlank(bean.getAreaName())){
			param.put("areaName", bean.getAreaName().trim());
		}
		List<AreaBean> list =organizationService.loadArea(param);
		ListData<AreaBean> LA = 
				new ListData<AreaBean>(bean.getTotalCount(),list);
		result.setData(LA);
		return JSON.toJSON(result);
	}
	/**
	 * �?��searchOrg的数�?
	 * @author xujianquan
	 * @param searchKey
	 * @return
	 */
	@SuppressWarnings( {"rawtypes","unchecked"})
	private JsonResult checkSearchOrg(SearchOrganization searchKey)
	{
		Integer organizationId = searchKey.getOrgId();
		String organizationName = searchKey.getOrgName();
		String organizationCode = searchKey.getOrgCode();
		String organizationType = searchKey.getOrgType();
		String status = searchKey.getStatus();
		String optype = searchKey.getOptype();
		
		HashMap param = new HashMap();
		JsonResult result = new JsonResult();
		
//		if(!StringUtils.isNotBlank(optype))
//		{
//			result.addError(new MyError(
//					MyError.SystemCode.ORGANIZATION_SEARCHORGANIZATION_OPTYPE_IS_NULL,
//					MyError.SystemMsg.ORGANIZATION_SEARCHORGANIZATION_OPTYPE_IS_NULL
//					));
//			return result;
//		}
//		if(Integer.parseInt(optype) == 0 && null == organizationId)
//		{
//			result.addError(new MyError(
//					MyError.SystemCode.ORGANIZATION_ORGID_IS_NULL,
//					MyError.SystemMsg.ORGANIZATION_ORGID_IS_NULL
//					));
//			return result;
//		}
		if(!StringUtils.isBlank(organizationName)){
			StringBuilder sb = new StringBuilder("%");
			sb.append(organizationName.trim()).append("%");
			param.put("organizationName", sb.toString());
		}
		if(!StringUtils.isBlank(optype)){
			param.put("optype", optype.trim());
		}
		if(null!=organizationId){
			param.put("organizationId", organizationId);				
		}
		if(!StringUtils.isBlank(organizationCode)){
			param.put("organizationCode", organizationCode.trim());			
		}
		if(!StringUtils.isBlank(organizationType)){
			param.put("organizationType", organizationType.trim());			
		}
		if(!StringUtils.isBlank(status)){
			param.put("status", status.trim());			
		}
		param.put("pagination", searchKey);
		result.setData(param);
		return result;
	}
	
	/**
	 * 把机构对象列表转化为页面机构对象列表
	 * @author xujianquan
	 * @param organizations
	 * @return
	 */
	private List<OrganizationBean> organizationsToOrgRecords(List<Organization> organizations )
	{
		ArrayList<OrganizationBean> orgs = null;
		if(null != organizations && organizations.size() > 0)
		{
			orgs = new ArrayList<OrganizationBean>();
			for(int i = 0; i < organizations.size(); i++	)
			{
				OrganizationBean org = organizationToOrgRecord(organizations.get(i));
				orgs.add(org);
			}
		}
		return orgs;
	}
	
	/**
	 * �?��inserOrg的数�?
	 * @author shijun
	 * @param bean
	 * @return
	 */
	private JsonResult checkInsertOrg(InsertOrgBean bean){
		JsonResult result = new JsonResult();
		Organization org = new Organization();
		if(bean==null){
			result.addError(new MyError(MyError.SystemCode.ORGANIZATION_BEAN_NOT_EXISTS,
					MyError.SystemMsg.ORGANIZATION_BEAN_NOT_EXISTS));
			return result;
		}
		if(bean.getParentOrgId()!=null){
			org.setParentOrganizationId(bean.getParentOrgId());
		}
		if(StringUtils.isNotBlank(bean.getAcCountName())){
			org.setAccountName(bean.getAcCountName());
		}
		if(StringUtils.isNotBlank(bean.getAcCountNumber())){
			org.setAccountNumber(bean.getAcCountNumber());
		}
		if(StringUtils.isNotBlank(bean.getArea())){
			org.setArea(bean.getArea());
		}
		if(StringUtils.isNotBlank(bean.getBankName())){
			org.setBankName(bean.getBankName());
		}
		if(StringUtils.isNotBlank(bean.getOpenBank())){
			org.setOpenBank(bean.getOpenBank());
		}
		if(StringUtils.isNotBlank(bean.getOrgCode())){
			org.setOrganizationCode(bean.getOrgCode());
		}
		if(bean.getOrgLevel()!=null){
			org.setOrganizationLevel(bean.getOrgLevel());
		}
		if(StringUtils.isNotBlank(bean.getOrgName())){
			org.setOrganizationName(bean.getOrgName());
		}
		if(StringUtils.isNotBlank(bean.getOrgType())){
			org.setOrganizationType(bean.getOrgType());
		}
		if(StringUtils.isNotBlank(bean.getRemark())){
			org.setRemark(bean.getRemark());
		}
		if(StringUtils.isNotBlank(bean.getStatus())){
			org.setStatus(bean.getStatus());
		}
		if(StringUtils.isNotBlank(bean.getIsLeaf())){
			org.setIsLeaf(bean.getIsLeaf());
		}
		if(result.isSuccess())
		{
			result.setData(org);
		}
		return result;
	}
	/**
	 * 把机构对象转化为页面机构对象
	 * @author xujianquan
	 * @param organization
	 * @return
	 */
	private OrganizationBean organizationToOrgRecord(Organization organization)
	{
		OrganizationBean org = null;
		if(organization != null)
		{
			org = new OrganizationBean();
			org.setOrgCode(organization.getOrganizationCode());
			org.setOrgId(organization.getOrganizationId());
			org.setOrgLevel(organization.getOrganizationLevel());
			org.setOrgName(organization.getOrganizationName());
			org.setOrgSeq(organization.getOrganizationSeq());
			org.setOrgType(organization.getOrganizationType());
			org.setStatus(organization.getStatus());
		}
		return org;
	}
}
