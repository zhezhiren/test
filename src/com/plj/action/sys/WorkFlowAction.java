package com.plj.action.sys;

import static com.plj.common.session.UserSession.USER_SESSION;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.plj.common.constants.Constants;
import com.plj.common.error.MyError;
import com.plj.common.result.JsonResult;
import com.plj.common.result.ListData;
import com.plj.common.session.UserSession;
import com.plj.common.tools.mybatis.page.bean.Pagination;
import com.plj.domain.decorate.sys.SearchWorkFlow;
import com.plj.domain.decorate.sys.WorkFlow;
import com.plj.domain.decorate.sys.WorkFlowExecute;
import com.plj.domain.request.sys.WorkFlowConvert;
import com.plj.domain.response.sys.WorkFlowCopy;
import com.plj.service.sys.WorkFlowService;

@Controller
@RequestMapping(value="/workFlow")
public class WorkFlowAction 
{
	/**
	 * 业务流程查询
	 * @param bean
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getWorkFlow.json")
	@ResponseBody
	public Object getWorkFlow(SearchWorkFlow bean,
			HttpServletRequest request, HttpServletResponse response) {
		JsonResult result = new JsonResult();
		HashMap param = new HashMap();
		UserSession user = (UserSession) request.getSession().getAttribute(USER_SESSION);
		if(null==user.getOrgId()){
			result.addError(new MyError(
					MyError.SystemCode.ORGID_IS_NULL,
					MyError.SystemMsg.ORGID_IS_NULL
					));
		}else{
			param.put("orgId", user.getOrgId());
		}
		if(StringUtils.isNotBlank(bean.getName()))
			param.put("name", bean.getName());
		if(StringUtils.isNotBlank(bean.getStartTime())){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String nowDay = sdf.format(Calendar.getInstance().getTime()).substring(0,11);
			String date = nowDay+bean.getStartTime()+":00";
			try {
				param.put("startTime", sdf.parse(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(StringUtils.isNotBlank(bean.getEndTime())){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String nowDay = sdf.format(Calendar.getInstance().getTime()).substring(0,11);
			String date = nowDay+bean.getEndTime()+":00";
			try {
				param.put("endTime", sdf.parse(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		param.put("pagination", bean);
		param.put("status", "normal");
		List<WorkFlow> list = workFlowService.getWorkFlow(param);
		ListData<WorkFlowCopy> lr = new ListData<WorkFlowCopy>(
				bean.getTotalCount(), workFlowToWorkFlowCopy(list));
		result.setData(lr);
		
		return JSON.toJSON(result);
	}
	/**
	 * 提醒展示
	 * @param bean
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getWorkFlowHome.json")
	@ResponseBody
	public Object getWorkFlowHome(Pagination bean,
			HttpServletRequest request, HttpServletResponse response) {
		Map param = new HashMap();
		JsonResult result = new JsonResult();
		UserSession user = (UserSession) request.getSession().getAttribute(USER_SESSION);
		if(null==user.getOrgId()){
			result.addError(new MyError(
					MyError.SystemCode.ORGID_IS_NULL,
					MyError.SystemMsg.ORGID_IS_NULL
					));
			return result;
		}else{
			param.put("orgId", user.getOrgId());
		}
		param.put("pagination", bean);
		param.put("status", "normal");
		List<WorkFlow> list = workFlowService.getWorkFlowHome(param);
		ListData<WorkFlowCopy> lr = new ListData<WorkFlowCopy>(
				bean.getTotalCount(), workFlowToWorkFlowCopy(list));
		result.setData(lr);
		return JSON.toJSON(result);
	}
	/**
	 * 完成
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/completeWorkFlow.json")
	@ResponseBody
	public Object completeWorkFlow(Integer id,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String str = isDelayFinish(id);
		JsonResult result = new JsonResult();
		Map map = new HashMap();
		map.put("id", id);
		map.put("workStatus",Constants.NOTREMIND);
		map.put("completeStatus",str);
	    Boolean i = workFlowService.completeWorkFlow(map);
	 	result.setData(i);
		return JSON.toJSON(result);
	}
	/**
	 * 判断延迟完成
	 */
	private String isDelayFinish(int id){
		Date nowDay =Calendar.getInstance().getTime();
		Date endTime = workFlowService.getWorkEndTime(id);
		if(nowDay.before(endTime))
			return Constants.FINISH;
		return Constants.DELAY_FINISH;
	}
	/**
	 * 取消提醒
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/cancelRemindWork.json")
	@ResponseBody
	public Object cancelRemindWork(Integer id,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JsonResult result = new JsonResult();
		Map map = new HashMap();
		map.put("id", id);
		map.put("workStatus",Constants.NOTREMIND);
	 	Boolean i = workFlowService.cancelRemindWork(map);
	 	result.setData(i);
		return result;
	}
	/**
	 * 新增
	 * 
	 */
	@RequestMapping(value = "/insertWorkFlow.json")
	@ResponseBody
	public Object insertWorkFlow(WorkFlowConvert wfc,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WorkFlow bean = toWrokFlow(wfc);
		UserSession user = (UserSession) request.getSession().getAttribute(USER_SESSION);
		bean.setOrgId(user.getOrgId());
		bean.setStatus("normal");
		JsonResult result = checkWorkFlow(bean, false);
		if (!result.isSuccess()) {
			return result;
		}
		WorkFlowExecute wfe = initWFE(bean);
		Boolean b = workFlowService.addWorkFlow(bean,wfe);
		result.setData(b);
		return result;
	}

	/**
	 * 修改
	 *
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateWorkFlow.json")
	@ResponseBody
	public Object updateWorkFlow(WorkFlowConvert wfc,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WorkFlow bean = toWrokFlow(wfc);
		JsonResult result = checkWorkFlow(bean, true);
		if (!result.isSuccess()) {
			return result;
		}
		Boolean b = workFlowService.updateWorkFlow(bean);
		result.setData(b);
		return result;
	}

	/**
	 * 根据Id删除,分为完成和取消两种情况删�?
	 * 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/deleteById.json")
	@ResponseBody
	public Object deleteWorkFlowById(Integer id,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JsonResult result = new JsonResult();
		Map map = new HashMap();
		map.put("id", id);
		map.put("status","deleted");
		Boolean i = workFlowService.deleteWorkFlow(map);
		result.setData(i);
		return result;
	}

	/**
	 * 把对象列表转化为页面对象列表
	 * 
	 */
	private List<WorkFlowCopy> workFlowToWorkFlowCopy(
			List<WorkFlow> workFlow) {
		ArrayList<WorkFlowCopy> asrs = null;
		if (null != workFlow && workFlow.size() > 0) {
			asrs = new ArrayList<WorkFlowCopy>();
			for (int i = 0; i < workFlow.size(); i++) {
				WorkFlowCopy bean = new WorkFlowCopy();
				WorkFlow wf = workFlow.get(i);		
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if(null != wf.getStartTime())
				{
					bean.setWfstartTime(sdf.format(wf.getStartTime()).substring(11,16));
				}
				if(null != wf.getEndTime())
				{
					bean.setWfendTime((sdf.format(wf.getEndTime()).substring(11,16)));
				}				
				if(null!=wf.getHandle_flow())
					bean.setWfhandle_flow(wf.getHandle_flow());
				if(StringUtils.isNotBlank(wf.getName()))
					bean.setWfname(wf.getName());
				if(StringUtils.isNotBlank(wf.getStatus()))
					bean.setWfstatus(wf.getStatus());
				if(StringUtils.isNotBlank(wf.getOrgName()))
					bean.setWforgName(wf.getOrgName());
				if(StringUtils.isNotBlank(wf.getRemark()))
					bean.setWfremark(wf.getRemark());
				if(0!=wf.getId())
					bean.setWfid(wf.getId());
				if(0!=wf.getOrgId())
					bean.setWforgId(wf.getOrgId());
				if(StringUtils.isNotBlank(wf.getWorkStatus()))
					bean.setWfworkStatus(wf.getWorkStatus());
				if(StringUtils.isNotBlank(wf.getCompleteStatus()))
					bean.setWfcompleteStatus(wf.getCompleteStatus());
				asrs.add(bean);
			}
		}
		return asrs;
	}
	/**
	 * 验证新增修改时参数的正确性�?
	 * 
	 */
	private JsonResult checkWorkFlow(WorkFlow bean,
			boolean checkId) {
		JsonResult result = new JsonResult();
		
		if(null == bean)
		{
			result.addError("", "参数不能为空");
		}else
		{
			if(checkId)
			{
				if(0 == bean.getId())
				{
					result.addError("", "");
				}
			}else
			{
				if(0 == bean.getOrgId())
				{
					result.addError("", "");
				}
				
			}
			if(null == bean.getEndTime() || null == bean.getStartTime())
			{
				result.addError("","");
			}
		}
		return result;
	}
	/**
	 * 新增插入时都为未完成
	 */
	private WorkFlowExecute initWFE(WorkFlow bean){
		WorkFlowExecute wfe = new WorkFlowExecute();
		wfe.setCompleteStatus(Constants.UNFINISH);
		wfe.setEndTime(bean.getEndTime());
		wfe.setOrgId(bean.getOrgId());
		wfe.setWorkStatus(Constants.REMIND);
		return wfe;
	}
	/**
	 * WorkFlowConvert to WorkFlow
	 */
	private WorkFlow toWrokFlow(WorkFlowConvert bean){
		WorkFlow wf = new WorkFlow();
		if(0!= bean.getId())
			wf.setId(bean.getId());
		if(StringUtils.isNotBlank(bean.getHandle_flow()))
			wf.setHandle_flow(bean.getHandle_flow());
		if(StringUtils.isNotBlank(bean.getName()))
			wf.setName(bean.getName());
		if(StringUtils.isNotBlank(bean.getRemark()))
			wf.setRemark(bean.getRemark());
		if(0!=bean.getOrgId())
			wf.setOrgId(bean.getOrgId());
		if(null!=bean.getEndTime()){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String nowDay = sdf.format(Calendar.getInstance().getTime()).substring(0,11);
			String date = nowDay+bean.getEndTime()+":00";
			try {
				wf.setEndTime(sdf.parse(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(null!=bean.getStartTime()){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String nowDay = sdf.format(Calendar.getInstance().getTime()).substring(0,11);
			String date = nowDay+bean.getStartTime()+":00";
			try {
				wf.setStartTime(sdf.parse(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}	
		return wf;
	}
	@Autowired
	@Qualifier(value = "workFlowService")
	private WorkFlowService workFlowService;
	
}
