package com.plj.action.sys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import com.plj.common.utils.ArrayUtils;
import com.plj.domain.bean.sys.ApplicationBean;
import com.plj.domain.bean.sys.FuncDataBean;
import com.plj.domain.bean.sys.FunctionBean;
import com.plj.domain.bean.sys.FunctionGroupBean;
import com.plj.domain.bean.sys.TreeBasic;
import com.plj.domain.bean.sys.TreeUtil;
import com.plj.domain.decorate.sys.Application;
import com.plj.domain.decorate.sys.Function;
import com.plj.domain.decorate.sys.FunctionGroup;
import com.plj.domain.request.common.DeleteByStringIds;
import com.plj.domain.request.sys.DeleteApplications;
import com.plj.domain.request.sys.GetFunctionGroupPage;
import com.plj.domain.request.sys.GetFunctionPage;
import com.plj.domain.request.sys.SearchApplication;
import com.plj.domain.response.sys.AppInfo;
import com.plj.domain.response.sys.TreeBean;
import com.plj.service.sys.ApplicationService;
import com.plj.service.sys.FunctionGroupService;
import com.plj.service.sys.FunctionService;

/**
 * 应用管理功能 对应请求处理对象
 * 
 * @author zhengxing
 * @version 1.0
 * @date 2013/2/20
 */
@Controller
@RequestMapping(value = "/application")
@SuppressWarnings({"rawtypes","unchecked"})
public class ApplicationAction {
	/**
	 * 新增应用
	 * 
	 * @param bean
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertApplication.json")
	@ResponseBody
	public Object insertApplication(ApplicationBean bean,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JsonResult result = checkApplicationBean(bean, false);
		if (!result.isSuccess()) {
			return result;
		}
		Application application = (Application) result.getCacheData(true);
		applicationService.insertApplication(application);
		return JSON.toJSON(result);
	}

	/**
	 * 修改应用请求
	 * 
	 * @param bean
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateApplication.json")
	@ResponseBody
	public Object updateApplication(ApplicationBean bean,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JsonResult result = checkApplicationBean(bean, true);
		if (!result.isSuccess()) {
			return result;
		}
		Application application = (Application) result.getCacheData(true);
		applicationService.updateApplication(application);
		return JSON.toJSON(result);
	}

	/**
	 * 根据Id列表删除应用
	 * 
	 * @param ids
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteByIds.json")
	@ResponseBody
	public Object deleteApplicationByIds(DeleteApplications ids,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JsonResult result = checkDeleteApplicationByIds(ids);
		if (!result.isSuccess()) {
			return result;
		}
		Integer i = applicationService.deleteApplicationsByIds(ids.getAppIds());
		result.setData(i);
		return JSON.toJSON(result);
	}

	/**
	 * 获取�?��应用记录，用作下拉数据�?
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAllApp.json")
	@ResponseBody
	public Object getAllApp(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<Application> applications = applicationService.getAllApplication();
		List<AppInfo> apps = applicationsLocalTOPage(applications);
		ListData<AppInfo> lr = new ListData<AppInfo>(0L, apps);
		JsonResult result = new JsonResult();
		result.setData(lr);
		return JSON.toJSON(result);
	}

	/**
	 * 查询应用列表作为分页数据�?
	 * 
	 * @param searchKey
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/searchApplication.json")
	@ResponseBody
	public Object searchApplication(SearchApplication searchKey,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String applicationName = request.getParameter("APPNAME");
		String applicationType = request.getParameter("APPTYPE");
		HashMap param = new HashMap();
		if (StringUtils.isNotBlank(applicationName)) {
			StringBuilder sb = new StringBuilder("%");
			sb.append(applicationName.trim()).append("%");
			param.put("applicationName", sb.toString());
		}
		if (StringUtils.isNotBlank(applicationType)) {
			param.put("applicationType", applicationType.trim());
		}
		param.put("pagination", searchKey);
		List<Application> apps = applicationService.searchApplication(param);
		JsonResult result = new JsonResult();
		ListData<ApplicationBean> lr = new ListData<ApplicationBean>(
				searchKey.getTotalCount(), applicationsToAPPRecords(apps));
		result.setData(lr);
		return JSON.toJSON(result);
	}

	/**
	 * 插入功能组记�?
	 * @author hu
	 * @param bean
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertFunctionGroup.json")
	@ResponseBody
	public Object insertFunctionGroup(FunctionGroupBean bean,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JsonResult result = checkFunctionGroup(bean);
		if (!result.isSuccess()) {
			return result;
		}
		FunctionGroup functionGroup = (FunctionGroup) result.getCacheData(true);
		functionGroup=functionGroupService.insertFunctionGroup(functionGroup);
		result.setData(functionGroup.getFunctionGroupId());
		return JSON.toJSON(result);
	}

	/**
	 * 修改功能�?
	 * 
	 * @param bean
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateFunctionGroup.json")
	@ResponseBody
	public Object updateFunctionGroup(FunctionGroupBean bean,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JsonResult result = checkUpdateFunctionGroup(bean);
		if (!result.isSuccess()) {
			return result;
		}
		FunctionGroup functionGroup = (FunctionGroup) result.getCacheData(true);
		Integer i=functionGroupService.updateFunctionGroup(functionGroup);
		if(!result.isSuccess()){
			return result;
		}
		result.setData(i);
		return JSON.toJSON(result);
	}

	/**
	 * 根据id列表删除功能�?
	 * @author hu
	 * @param bean
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteFunctionGroup.json")
	@ResponseBody
	public Object deleteFunctionGroup(DeleteByStringIds bean,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JsonResult result = bean.checkParameters();
		if (!result.isSuccess()) {
			return result;
		}
		
		Integer i=functionGroupService.deleteByIds(bean.getIds());
		result.setData(i);
		return JSON.toJSON(result);
	}

	/**
	 * 查询功能组列表分页记�?
	 * 
	 * @param bean
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getFunctionGroupList.json")
	@ResponseBody
	public Object getFunctionGroupList(GetFunctionGroupPage bean,
			HttpServletRequest request, HttpServletResponse response) {
		JsonResult result = checkSearchFunctionGroup(bean);
		if (!result.isSuccess()) {
			return result;
		}
		HashMap map = new HashMap();
		map.put("pagination", bean);
		if(StringUtils.isNotBlank(bean.getAppId())){
			map.put("appId", bean.getAppId());
		}
		List<FunctionGroup> functionGroups = functionGroupService
				.searchFunctionGroups(map);
		ListData<FunctionGroup> list = new ListData<FunctionGroup>(bean.getTotalCount(), functionGroups);
		//list.setArr(functionGroups);
		result.setData(list);		
		return JSON.toJSON(result);
	}

	
	@RequestMapping(value="/getApplicationTree.json")
	@ResponseBody
	public Object getApplicationTree(String appId, HttpServletRequest request,
			HttpServletResponse response)
	{
		List<TreeBean> result = null;
		if(null == appId || "".endsWith(appId.trim()))
		{
			List<Application> applications = applicationService.getAllApplication();
			result = applicationsToTreeBeans(applications);
		}
		else
		{
			appId = appId.substring(0,appId.length()-1);
			HashMap map = new HashMap();
			map.put("appId", appId);
			List<FunctionGroup> functionGroups = functionGroupService.searchFunctionGroups(map);
			result = funcGroupsToTreeBeans(functionGroups);
		}
		return result;
	}
	
	/**
	 * 验证功能名称是否存在
	 * @author bin
	 * @param funcName
	 * @return
	 */
	@RequestMapping(value = "/funcNameExists.json")
	@ResponseBody
	public Object funcNameExists(String funcName, HttpServletRequest request,
			HttpServletResponse response){
		JsonResult result = new JsonResult();
		if(!StringUtils.isNotBlank(funcName)){
			result.addError(new MyError(MyError.SystemCode.FUNCNAME_IS_NULL, MyError.SystemMsg.FUNCNAME_IS_NULL));
			return result;
		}
		Boolean exist = applicationService.funcNameExists(funcName);		
		result.setData(exist);
		return JSON.toJSON(result);
	}
	/**
	 * 验证功能组名称是否存�?
	 * @author bin
	 * @param funcGroupName
	 * @return
	 */
	@RequestMapping(value = "/funcGroupNameExists.json")
	@ResponseBody
	public Object funcGroupNameExists(String funcGroupName, HttpServletRequest request,
			HttpServletResponse response){
		JsonResult result = new JsonResult();
		if(!StringUtils.isNotBlank(funcGroupName)){
			result.addError(new MyError(MyError.SystemCode.FUNCGROUPNAME_IS_NULL, MyError.SystemMsg.FUNCGROUPNAME_IS_NULL));
			return result;
		}
		Boolean exist = applicationService.funcGroupNameExists(funcGroupName);		
		result.setData(exist);
		return JSON.toJSON(result);
	}
	
	/**
	 * 新增function数据
	 * @author bin
	 * @param funcGroupName
	 * @return
	 */
	@RequestMapping(value = "/insertFuncData.json")
	@ResponseBody
	public Object insertFuncData(FuncDataBean bean, HttpServletRequest request,
			HttpServletResponse response){
		JsonResult result = checkFuncDataBean(bean, false);
		if (!result.isSuccess()) {
			return result;
		}
		Function function = (Function) result.getCacheData(false);
		applicationService.insertFunction(function);
		return JSON.toJSON(result);
		
	}
	

	
	private JsonResult checkFuncDataBean(FuncDataBean bean, boolean checkId) {
		JsonResult result = new JsonResult();
		Function function = new Function();
		if(checkId){
			if(StringUtils.isBlank(bean.getFuncCode())){
				result.addError(new MyError(
						MyError.SystemCode.APPLICATION_FUNCCODE_IS_NULL,
						MyError.SystemMsg.APPLICATION_FUNCCODE_IS_NULL
						));
			}
			else{
				function.setFunctionCode(Integer.parseInt(bean.getFuncCode()));
			}	
		}	
		function.setFunctionDesc(bean.getFuncDesc());
		if(StringUtils.isBlank(bean.getFuncName())){
			result.addError(new MyError(
					MyError.SystemCode.APPLICATION_FUNCNAME_IS_NULL,
					MyError.SystemMsg.APPLICATION_FUNCNAME_IS_NULL
					));
		}
		else{
			function.setFunctionName(bean.getFuncName());
		}	

		function.setFunctionGroupId(bean.getFuncGroupId());
		function.setFuncaction(bean.getFuncAction());		
		result.setData(function);
		return result;
	}

	/**
	 * 修改function数据
	 * @author bin
	 * @param funcGroupName
	 * @return
	 */
	@RequestMapping(value = "/updateFuncData.json")
	@ResponseBody
	public Object updateFuncData(FuncDataBean bean,HttpServletRequest request,
			HttpServletResponse response){
		JsonResult result = this.checkFuncDataBean(bean, true);
		if (!result.isSuccess()) {
			return result;
		}
		Function function = (Function) result.getCacheData(false);
		applicationService.updateFunction(function);
		return JSON.toJSON(result);
	}
	
	/**
	 * 刪除function数据
	 * @author bin
	 * @param funcGroupName
	 * @return
	 */
	@RequestMapping(value = "/deleteFuncData.json")
	@ResponseBody
	public Object deleteFuncData(Integer[] ids,HttpServletRequest request,
			HttpServletResponse response){
		JsonResult result = new JsonResult();
		if(ids==null||ids.length==0){
			result.addError(new MyError(MyError.SystemCode.FUNCTION_IDS_NULL_DELETE, 
					MyError.SystemMsg.FUNCTION_IDS_NULL_DELETE));
		}		
		if(!result.isSuccess()){
			return result;
		}
		int c=applicationService.deleteFunction(ArrayUtils.toList(ids));
		result.setData(c);
		return JSON.toJSON(result);
	}
	
	
	/**
	 * 获取application的name和Id
	 * @author liufan
	 * @return
	 * **/
	@RequestMapping(value="/getAppNameId.json")
	@ResponseBody
	public Object getApplicationNameId(HttpServletRequest request,
			HttpServletResponse response){
		JsonResult result=new JsonResult();
		List<Application> appList = 
				applicationService.getAllApplication();
		String[][] appArray = null;
		if(appList == null || appList.size() <= 0){
			return result;
		}
		appArray = new String[appList.size()][2];
		int index = 0;
		for(Application app : appList){
			String[] buffer = new String[2];
			buffer[0] = app.getApplicationId().toString();
			buffer[1] = app.getApplicationName();
			appArray[index++] = buffer;
		}
		return appArray;
	}
	/**
	 * 验证appName存在
	 * @author liufan
	 * */
	@RequestMapping(value="/appNameExits.json")
	@ResponseBody
	public Object appNameExits(String appName,
			HttpServletRequest request,HttpServletResponse response){
		JsonResult result = new JsonResult();
		if(StringUtils.isBlank(appName)){
			result.addError("","applicationNameCanNotBeNull");
			return result;
		}
		Boolean exit=applicationService.appNameExits(appName.trim());
		result.setData(exit);
		return JSON.toJSON(result);
	}
	
	
	/**
	 * 把应用列表转化为页面可识别的下拉数据列表�?
	 * 
	 * @param applications
	 * @return
	 */
	private List<AppInfo> applicationsLocalTOPage(List<Application> applications) {
		ArrayList<AppInfo> apps = null;
		if (null != applications && applications.size() > 0) {
			apps = new ArrayList<AppInfo>();
			for (int i = 0; i < applications.size(); i++) {
				AppInfo app = applicationLocalTOPage(applications.get(i));
				apps.add(app);
			}
		}
		return apps;
	}

	/**
	 * 把应用转化为页面可识别的下拉数据
	 * 
	 * @param application
	 * @return
	 */
	private AppInfo applicationLocalTOPage(Application application) {
		AppInfo app = null;
		if (null != application) {
			app = new AppInfo();
			app.setAppId(application.getApplicationId());
			app.setAppName(application.getApplicationName());
		}
		return app;
	}
	
	/**
	 * 把应用对象列表转化为页面应用对象列表
	 * 
	 * @param applications
	 * @return
	 */
	private List<ApplicationBean> applicationsToAPPRecords(
			List<Application> applications) {
		ArrayList<ApplicationBean> asrs = null;
		if (null != applications && applications.size() > 0) {
			asrs = new ArrayList<ApplicationBean>();
			for (int i = 0; i < applications.size(); i++) {
				ApplicationBean asr = applicationToAPPRecord(applications
						.get(i));
				asrs.add(asr);
			}
		}
		return asrs;
	}

	/**
	 * 把应用对象转化为页面应用对象
	 * 
	 * @param application
	 * @return
	 */
	private ApplicationBean applicationToAPPRecord(Application application) {
		ApplicationBean asr = null;
		if (application != null) {
			asr = new ApplicationBean();
			asr.setAppId(String.valueOf(application.getApplicationId()));
			asr.setAppName(application.getApplicationName());
			asr.setAppType(application.getApplciationType());
			asr.setIsOpen(application.getIsOpen());
			asr.setIpAddr(application.getIpAddr());
			asr.setIpPort(application.getIpPort());
			asr.setAppDesc(application.getApplicationDesc());
		}
		return asr;
	}

	/**
	 * 验证删除应用请求的参数正确�?并返回相应的验证结果�?
	 * 
	 * @param ids
	 * @return
	 */
	private JsonResult checkDeleteApplicationByIds(DeleteApplications ids) {
		JsonResult result = new JsonResult();

		if (null == ids || null == ids.getAppIds()
				|| ids.getAppIds().size() <= 0) {
			result.addError(new MyError(
					MyError.SystemCode.APPLICATION_IDS_NULL_DELETE,
					MyError.SystemMsg.APPLICATION_IDS_NULL_DELETE));
			return result;

		}
		return result;
	}

	/**
	 * 验证新增修改应用时参数的正确性�?
	 * 
	 * @param bean
	 * @param checkId
	 * @return
	 */
	private JsonResult checkApplicationBean(ApplicationBean bean,
			boolean checkId) {
		JsonResult result = new JsonResult();
		Application application = new Application();

		if (checkId) {
			if (StringUtils.isBlank(bean.getAppId())) {
				result.addError(new MyError(
						MyError.SystemCode.APPLICATION_UPDATE_ID_ERROR,
						MyError.SystemMsg.APPLICATION_UPDATE_ID_ERROR));
			}
			try {
				application.setApplicationId(Integer.parseInt(bean.getAppId()));
			} catch (Exception e) {
				result.addError(new MyError(
						MyError.SystemCode.APPLICATION_UPDATE_ID_ERROR,
						MyError.SystemMsg.APPLICATION_UPDATE_ID_ERROR));
			}
		}
		if (StringUtils.isBlank(bean.getAppName())) {
			result.addError(new MyError(
					MyError.SystemCode.APPLICATION_NAME_NULL,
					MyError.SystemMsg.APPLICATION_NAME_NULL));
		} else {
			application.setApplicationName(bean.getAppName());
		}
		if (StringUtils.isBlank(bean.getAppType())) {
			result.addError(new MyError(
					MyError.SystemCode.APPLICATION_TYPE_ERROR,
					MyError.SystemMsg.APPLICATION_TYPE_ERROR));
		} else {
			application.setApplciationType(bean.getAppType());
		}
		application.setIpAddr(bean.getIpAddr());
		application.setIpPort(bean.getIpPort());
		application.setIsOpen(bean.getIsOpen());
		application.setApplicationDesc(bean.getAppDesc());
		result.setData(application);
		return result;
	}

	/**
	 * 验证新增，修改functionGroup时参数的正确性，并返回相应结果�?
	 * 
	 * @param bean
	 * @return
	 */
	private JsonResult checkFunctionGroup(FunctionGroupBean bean) {
		JsonResult result = new JsonResult();
		if (bean == null) {
			result.addError(new MyError(
					MyError.SystemCode.FUNCGROUP_IS_NULL,
					MyError.SystemMsg.FUNCGROUP_IS_NULL));
			return result;
		}
		
		FunctionGroup group = new FunctionGroup();
		
		if(bean.getAppId()!=null){
			try {
				group.setApplicationId(Integer.parseInt(bean.getAppId()));
			} catch (Exception e) {
				result.addError(new MyError(
						MyError.SystemCode.MENU_MENUID_DATA_CONVERSION_FAIL,
						MyError.SystemMsg.MENU_MENUID_DATA_CONVERSION_FAIL));
			}
		}

		group.setFunctionGroupName(bean.getFunctionGroupName());

		if(bean.getParentGroup()!=null){
			try {
				group.setParentGroup(Integer.parseInt(bean.getParentGroup()));
			} catch (Exception e) {
				result.addError(new MyError(
						MyError.SystemCode.MENU_MENUID_DATA_CONVERSION_FAIL,
						MyError.SystemMsg.MENU_MENUID_DATA_CONVERSION_FAIL));
			}
		}
		result.setData(group);
		return result;
	}

	/**
	 * 验证搜索功能组时参数的正确�?�?
	 * 
	 * @param bean
	 * @return
	 */
	private JsonResult checkSearchFunctionGroup(GetFunctionGroupPage bean) {
		JsonResult result = new JsonResult();// TODO

		return result;
	}

	@Autowired
	@Qualifier(value = "applicationService")
	private ApplicationService applicationService;

	@Autowired
	@Qualifier(value = "functionGroupService")
	private FunctionGroupService functionGroupService;

	/**
	 * 查询功能记录
	 * 
	 * @author gdy
	 * @param bean
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getappFuncData.json")
	@ResponseBody 
	public Object getappFuncData(GetFunctionPage bean,
			HttpServletRequest request, HttpServletResponse response) {
		JsonResult result = checkSearchFunction(bean);
		if (!result.isSuccess()) {
			return result;
		}
		HashMap map = new HashMap();
		map.put("funcgroupid", bean.getFuncGroupId());
		map.put("pagination", bean);
		List<Function> function = fuctionService.searchFunction(map);
		List<FunctionBean> beans = functionToBeans(function);
		ListData<FunctionBean> lr = new ListData<FunctionBean>(
				bean.getTotalCount(), beans);
		result.setData(lr);
		return result;
	}

	/**
	 * 把功能记录列表从function转化为bean对象
	 * 
	 * @param function
	 * @return
	 */
	private List<FunctionBean> functionToBeans(List<Function> maps) {
		List<FunctionBean> beans = new ArrayList<FunctionBean>();

		if (null != maps && maps.size() > 0) {
			for (int i = 0; i < maps.size(); i++) {
				FunctionBean bean = functionToBean(maps.get(i));
				if (null != bean) {
					beans.add(bean);
				}
			}
		}
		return beans;
	}

	/**
	 * 把功能记录从function转化为bean对象
	 * 
	 * @param function
	 * @return
	 */
	private FunctionBean functionToBean(Function map) {
		FunctionBean bean = null;
		if (null != map) {
			bean = new FunctionBean();
			bean.setFunccode(map.getFunctionCode().toString());
			bean.setFuncname(map.getFunctionName());
			bean.setFuncdesc(map.getFunctionDesc());
			bean.setFuncaction(map.getFuncaction());
		}
		return bean;
	}

	/**
	 * 验证搜索功能时参数的正确性�?
	 * 
	 * @param bean
	 * @return
	 */
	private JsonResult checkSearchFunction(GetFunctionPage bean) {
		JsonResult result = new JsonResult();
		if (bean == null) {
			result.addError(new MyError(MyError.SystemCode.FUNCTION_IS_NULL,
					MyError.SystemMsg.FUNCTION_IS_NULL));
		}
		return result;
	}

	
	private List<TreeBean> applicationsToTreeBeans(List<Application> applications)
	{
		List<TreeBean> beans = null;
		if(applications != null && applications.size() > 0)
		{
			beans = new ArrayList<TreeBean>();
			for(int i = 0; i < applications.size(); i++)
			{
				TreeBean bean = applicationToTreeBean(applications.get(i));
				if(null != bean)
				{
					beans.add(bean);
				}
			}
		}
		return beans;
	}
	
	private TreeBean applicationToTreeBean(Application application)
	{
		TreeBean bean = null;
		if(application != null)
		{
			bean = new TreeBean();
			bean.setId(String.valueOf(application.getApplicationId())+"r"); //主节�?r区分和子节点的ID
			bean.setText(String.valueOf(application.getApplicationName()));
			bean.setHref("");
			bean.setIconCls("icon-nav");
			bean.setLeaf("0");
		}
		return bean;
	}
	
	private List<TreeBean> funcGroupsToTreeBeans(List<FunctionGroup> functionGroups)
	{
		List<TreeBasic> beans = null;
		if(functionGroups != null && functionGroups.size() > 0)
		{
			beans = new ArrayList<TreeBasic>();
			for(int i = 0; i < functionGroups.size(); i++)
			{
				TreeBasic bean = funcGroupToTreeBasic(functionGroups.get(i));
				if(null != bean)
				{
					beans.add(bean);
				}
			}
		}
		return TreeUtil.onTree(beans,"icon-nav","icon-nav");
	}
	
	private TreeBasic funcGroupToTreeBasic(FunctionGroup functionGroup)
	{
			TreeBasic treeBasic = new TreeBasic();
			treeBasic.setId(String.valueOf(functionGroup.getFunctionGroupId()));
			treeBasic.setName(functionGroup.getFunctionGroupName());
			String parentId = functionGroup.getParentGroup() == null || 0 == functionGroup.getParentGroup()
					? "" : String.valueOf(functionGroup.getParentGroup());
			treeBasic.setParentId(parentId);
			HashMap attributes = new HashMap();
			attributes.put("appid", functionGroup.getApplicationId());
			attributes.put("parentid", functionGroup.getParentGroup());
			treeBasic.setAttributes(attributes);
			return treeBasic;
	}
	
	private JsonResult checkUpdateFunctionGroup(FunctionGroupBean bean){
		JsonResult result=new JsonResult();
		FunctionGroup functionGroup=new FunctionGroup();
		
		if(bean.getFunctionGroupId()!=null){
			try {
				functionGroup.setFunctionGroupId(
						Integer.parseInt(bean.getFunctionGroupId()));
			} catch (Exception e) {
				result.addError(new MyError(
						MyError.SystemCode.MENU_MENUID_DATA_CONVERSION_FAIL,
						MyError.SystemMsg.MENU_MENUID_DATA_CONVERSION_FAIL));
			}
		}else{
			result.addError(new MyError(
					MyError.SystemCode.SUBFUNCTIONDATA_INSERT_ERROR,
					MyError.SystemMsg.SUBFUNCTIONDATA_INSERT_ERROR));
		}
		functionGroup.setFunctionGroupName(
					bean.getFunctionGroupName());
		result.setData(functionGroup);
		return result;
	}
	
	@Autowired
	@Qualifier(value = "functionService")
	private FunctionService fuctionService;
}
