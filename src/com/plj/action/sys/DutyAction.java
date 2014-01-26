package com.plj.action.sys;

import static com.plj.common.session.UserSession.USER_SESSION;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.plj.common.result.JsonResult;
import com.plj.common.result.ListData;
import com.plj.common.session.UserSession;
import com.plj.domain.bean.sys.DutyActBean;
import com.plj.domain.bean.sys.DutyPlanBean;
import com.plj.domain.bean.sys.OperatorBean;
import com.plj.domain.decorate.sys.DutyAct;
import com.plj.domain.decorate.sys.DutyPlan;
import com.plj.domain.decorate.sys.Operator;
import com.plj.domain.request.common.DeleteByIntegerIds;
import com.plj.domain.request.sys.GetOperatorsPage;
import com.plj.domain.request.sys.SerachDutyRequest;
import com.plj.service.sys.DutyActService;
import com.plj.service.sys.DutyPlanService;
import com.plj.service.sys.OperatorService;

@Controller
@RequestMapping("/duty")
public class DutyAction 
{
	@RequestMapping("/addDutyPlan.json")
	@ResponseBody
	public Object addDutyPlan(DutyPlanBean bean, HttpServletRequest request
			,HttpServletResponse response)
	{
		UserSession user = (UserSession) request.getSession().getAttribute(USER_SESSION);
		JsonResult result = new JsonResult();
		/*if(null ==user.getOrgId())
		{
			result.addError("","该用户没有单位信息，请联系管理员分配�?��单位，或者切换帐号�?");
			return result;
		}*/
		DutyPlan dutyPlan = beanToDutyPlan(result, bean, false);
		if(result.isSuccess())
		{
			dutyPlan.setOrgId(user.getOrgId());
			Integer count = dutyplanSvc.saveDutyPlan(dutyPlan);
			if(count == -1)
			{
				result.addError("","���Ӽƻ�ʧ��");
			}
			result.setData(count);
		}
		return result;
	}
	
	@RequestMapping("/getUserList.json")
	@ResponseBody
	public Object getUserList(GetOperatorsPage bean, HttpServletRequest request,
			HttpServletResponse response)
	{
		
		UserSession user = (UserSession) request.getSession().getAttribute(USER_SESSION);
		JsonResult result = new JsonResult();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("pagination", bean);
		if(StringUtils.isNotBlank(bean.getUserId()))
		{
			map.put("userId", "%" + bean.getUserId() + "%");
		}
		if(StringUtils.isNotBlank(bean.getOperatorName()))
		{
			map.put("operatorName", "%" + bean.getOperatorName() + "%");
		}
		map.put("orgId", user.getOrgId());
		List<Operator> operators = operatorService.SearchOperator(map);
		ListData<OperatorBean> datas = new ListData<OperatorBean>(
				bean.getTotalCount(), OperatorAction.operatorsToPageBeans(operators));
		result.setData(datas);
		return JSON.toJSON(result);
	}
	
	@RequestMapping("/updateDutyPlan.json")
	@ResponseBody
	public Object updateDutyPlan(DutyPlanBean bean, HttpServletRequest request
			,HttpServletResponse response)
	{
		UserSession user = (UserSession) request.getSession().getAttribute(USER_SESSION);
		JsonResult result = new JsonResult();
		/*if(null ==user.getOrgId())
		{
			result.addError("","该用户没有单位信息，请联系管理员分配�?��单位，或者切换帐号�?");
			return result;
		}*/
		DutyPlan dutyPlan = beanToDutyPlan(result, bean, true);
		if(result.isSuccess())
		{
			dutyPlan.setOrgId(user.getOrgId());
			Integer count = dutyplanSvc.updateDutyPlan(dutyPlan);
			if(count == -1)
			{
				result.addError("","");
			}
			result.setData(count);
		}
		return result;
	}
	
	@RequestMapping("/getPossibleDutyPlan.json")
	@ResponseBody
	public Object getPossibleDutyPlan(HttpServletRequest request
			,HttpServletResponse response)
	{
		JsonResult result = new JsonResult();
		UserSession user = (UserSession) request.getSession().getAttribute(USER_SESSION);
		if(null ==user.getOrgId())
		{
			result.addError("","该用户没有单位信息，请联系管理员分配�?��单位，或者切换帐号�?");
			return result;
		}
		DutyPlan dutyPlan = dutyplanSvc.getPossibleDutyPlan(user.getOrgId());
		result.setData(dutyPlan);
		return JSON.toJSON(result);
	}
	
	@RequestMapping("/searchDutyPlan.json")
	@ResponseBody
	public Object searchDutyPlan(SerachDutyRequest requestBean, HttpServletRequest request
			,HttpServletResponse response)
	{
		JsonResult result = new JsonResult();
		UserSession user = (UserSession) request.getSession().getAttribute(USER_SESSION);
		/*if(null ==user.getOrgId())
		{
			result.addError("","该用户没有单位信息，请联系管理员分配�?��单位，或者切换帐号�?");
			return result;
		}*/
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = null;
		Date endDate = null;
		try {
			if(StringUtils.isNotBlank(requestBean.getStartTime()))
			{
					startDate = format.parse(requestBean.getStartTime());
				
			}
			if(StringUtils.isNotBlank(requestBean.getEndTime()))
			{
				endDate = format.parse(requestBean.getEndTime());
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(endDate);
				calendar.add(Calendar.DAY_OF_YEAR, 1);
				endDate = calendar.getTime();
			}
		} catch (ParseException e) {
			result.addError("", "您输入的时间格式有误，请重新�?��");
		}
		List<DutyPlan> dutyPlan = dutyplanSvc.searchDutyPlan(requestBean.getEmpName()
				, user.getOrgId(), startDate, endDate, requestBean);
		result.setData(new ListData<DutyPlanBean>(requestBean.getTotalCount()
				, dutyPlanToBeans(dutyPlan)));
		return JSON.toJSON(result);
	}
	
	@RequestMapping("/deleteDutyPlan.json")
	@ResponseBody
	public Object deleteDutyPlan(DeleteByIntegerIds ids, HttpServletRequest request
			,HttpServletResponse response)
	{
		JsonResult result = new JsonResult();
		Integer i = dutyplanSvc.deleteByIds(ids.getIds());
		
		result.setData(i);
		return JSON.toJSON(result);
	}
	
	@RequestMapping("/addDutyAct.json")
	@ResponseBody
	public Object addDutyAct(DutyActBean bean, HttpServletRequest request
			,HttpServletResponse response)
	{
		UserSession user = (UserSession) request.getSession().getAttribute(USER_SESSION);
		JsonResult result = new JsonResult();
		
		if(null ==user.getOrgId())
		{
			result.addError("","该用户没有单位信息，请联系管理员分配�?��单位，或者切换帐号�?");
			return result;
		}
		DutyAct dutyAct = beanTODutyAct(result, bean);
		if(result.isSuccess())
		{
			dutyAct.setOrgId(user.getOrgId());
			int i = dutyActSvc.saveDutyAct(dutyAct);
			if(i == -1)
			{
				result.addError("","");
			}
		}
		return result;
	}
	
	@RequestMapping("/searchDutyAct.json")
	@ResponseBody
	public Object searchDutyAct(SerachDutyRequest requestBean, HttpServletRequest request
			,HttpServletResponse response)
	{
		JsonResult result = new JsonResult();
		UserSession user = (UserSession) request.getSession().getAttribute(USER_SESSION);
		/*if(null ==user.getOrgId())
		{
			result.addError("","该用户没有单位信息，请联系管理员分配�?��单位，或者切换帐号�?");
			return result;
		}*/
		Integer orgId = user.getOrgId();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = null;
		Date endDate = null;
		try 
		{
			if(StringUtils.isNotBlank(requestBean.getStartTime()))
			{
				startDate = format.parse(requestBean.getStartTime());
				
			}
			if(StringUtils.isNotBlank(requestBean.getEndTime()))
			{
				endDate = format.parse(requestBean.getEndTime());
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(endDate);
				calendar.add(Calendar.DAY_OF_YEAR, 1);
				endDate = calendar.getTime();
			}
		} catch (ParseException e)
		{
			result.addError("", "您输入的时间格式有误，请重新�?��");
		}
		List<DutyAct> dutyActs = dutyActSvc.searchDutyAct(requestBean.getEmpName(), orgId
				, startDate, endDate, requestBean);
		result.setData(new ListData<DutyActBean>(
				requestBean.getTotalCount(), dutyActToBeans(dutyActs)));
		return JSON.toJSON(result);
	}
	
	@RequestMapping("/onDuty.json")
	@ResponseBody
	public Object onDuty(HttpServletRequest request, HttpServletResponse response)
	{
		JsonResult result = new JsonResult();
		UserSession user = (UserSession) request.getSession().getAttribute(USER_SESSION);
		if(null == user.getOrgId())
		{
			result.addError("","该用户没有单位信息，请联系管理员分配�?��单位，或者切换帐号�?");
			return result;
		}
		DutyAct dutyAct = dutyActSvc.getLastDutyActOfOrg(user.getOrgId());
		result.setData(dutyActToBean(dutyAct));
		return JSON.toJSON(result);
	}
	
	private DutyPlan beanToDutyPlan(JsonResult result, DutyPlanBean bean, boolean checkId)
	{
		if(null == bean)
		{
			result.addError("","参数为空");
		}
		DutyPlan dutyPlan = new DutyPlan();
		if(checkId && null == bean.getId())
		{
			result.addError("","修改排班计划时，id不能为空");
		}
		dutyPlan.setId(bean.getId());
		if(null != bean.getDutyOperatorId())
		{
			dutyPlan.setDutyOperatorId(bean.getDutyOperatorId());
			dutyPlan.setDutyOperatorName(bean.getDutyOperatorName());
		}else
		{
			result.addError("", "");
		}
		if(null != bean.getDutyLeaderId())
		{
			dutyPlan.setDutyLeaderId(bean.getDutyLeaderId());
			dutyPlan.setDutyLeaderName(bean.getDutyLeaderName());
		}else
		{
			result.addError("", "");
		}
		try{
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			dutyPlan.setEndTime(format.parse(bean.getEndTime()));
			//dutyPlan.setStartTime(format.parse(bean.getStartTime()));
		}catch(Exception e)
		{
			e.printStackTrace();
			result.addError("", "");
		}
		dutyPlan.setJobContent(bean.getJobContent());
		dutyPlan.setOrgId(bean.getOrgId());
		return dutyPlan;
	}
	
	private List<DutyPlanBean> dutyPlanToBeans(List<DutyPlan> dutyPlans)
	{
		List<DutyPlanBean> beans = null;
		if(null != dutyPlans && dutyPlans.size() > 0)
		{
			beans = new ArrayList<DutyPlanBean>(dutyPlans.size());
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			for(int i = 0; i < dutyPlans.size(); i++)
			{
				DutyPlan dutyAct = dutyPlans.get(i);
				DutyPlanBean bean = dutyPlanToBean(dutyAct,format);
				if(null != bean)
				{
					beans.add(bean);
				}
			}
		}
		return beans;
	}
	
	private DutyPlanBean dutyPlanToBean(DutyPlan dutyPlan, DateFormat format)
	{
		DutyPlanBean bean = null;
		if(null != dutyPlan )
		{
			bean = new DutyPlanBean();
			bean.setDutyOperatorId(dutyPlan.getDutyOperatorId());
			bean.setDutyOperatorName(dutyPlan.getDutyOperatorName());
			bean.setDutyLeaderId(dutyPlan.getDutyLeaderId());
			bean.setDutyLeaderName(dutyPlan.getDutyLeaderName());
			if(null != dutyPlan.getEndTime())
			{
				bean.setEndTime(format.format(dutyPlan.getEndTime()));
			}
			if(null != dutyPlan.getStartTime())
			{
				bean.setStartTime(format.format(dutyPlan.getStartTime()));
			}
			bean.setId(dutyPlan.getId());
			bean.setJobContent(dutyPlan.getJobContent());
			bean.setOrgId(dutyPlan.getOrgId());
		}
		return bean;
	}
	
	private DutyAct beanTODutyAct(JsonResult result, DutyActBean bean)
	{
		if(null == bean)
		{
			result.addError("","");
		}
		DutyAct dutyAct = new DutyAct();
		dutyAct.setId(bean.getId());
		if(null != bean.getDutyOperatorId())
		{
			dutyAct.setDutyOperatorId(bean.getDutyOperatorId());
			dutyAct.setDutyOperatorName(bean.getDutyOperatorName());
		}else
		{
			result.addError("", "");
		}
		/*if(null != bean.getDutyLeaderId())
		{
			dutyAct.setDutyLeaderId(bean.getDutyLeaderId());
			dutyAct.setDutyLeaderName(bean.getDutyLeaderName());
		}else
		{
			result.addError("", "没有填写");
		}*/
		try{
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			dutyAct.setEndTime(format.parse(bean.getEndTime()));
			//dutyAct.setStartTime(format.parse(bean.getStartTime()));
		}catch(Exception e)
		{
			result.addError("", "");
		}/*if(null != bean.getJobContent())
		{
			dutyAct.setJobContent(bean.getJobContent());
		}else
		{
			result.addError("", "工作内容不能为空");
		}*/
		dutyAct.setOrgId(bean.getOrgId());
		if(null != bean.getWarningContent())
		{
			dutyAct.setWarningContent(bean.getWarningContent());
		}else
		{
			result.addError("", "交班内容不能为空");
		}
		return dutyAct;
	}
	
	private List<DutyActBean> dutyActToBeans(List<DutyAct> dutyActs)
	{
		List<DutyActBean> beans = null;
		if(null != dutyActs && dutyActs.size() > 0)
		{
			beans = new ArrayList<DutyActBean>(dutyActs.size());
			for(int i = 0; i < dutyActs.size(); i++)
			{
				DutyAct dutyAct = dutyActs.get(i);
				DutyActBean bean = dutyActToBean(dutyAct);
				if(null != bean)
				{
					beans.add(bean);
				}
			}
		}
		return beans;
	}
	
	private DutyActBean dutyActToBean(DutyAct dutyAct)
	{
		DutyActBean bean = null;
		if(null != dutyAct )
		{
			
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			bean = new DutyActBean();
			bean.setDutyOperatorId(dutyAct.getDutyOperatorId());
			bean.setDutyOperatorName(dutyAct.getDutyOperatorName());
			if(null != dutyAct.getEndTime())
			{
				bean.setEndTime(format.format(dutyAct.getEndTime()));
			}
			if(null != dutyAct.getStartTime())
			{
				bean.setStartTime(format.format(dutyAct.getStartTime()));
			}
			bean.setId(dutyAct.getId());
			bean.setJobContent(dutyAct.getJobContent());
			bean.setOrgId(dutyAct.getOrgId());
			bean.setWarningContent(dutyAct.getWarningContent());
		}
		return bean;
	}
	
	
	@Autowired
	@Qualifier("operatorService")
	OperatorService operatorService;
	
	@Autowired
	@Qualifier("dutyPlanService")
	private DutyPlanService dutyplanSvc;
	
	@Autowired
	@Qualifier("dutyActService")
	private DutyActService dutyActSvc;
}
