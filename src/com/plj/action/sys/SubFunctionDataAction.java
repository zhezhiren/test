package com.plj.action.sys;

import java.io.IOException;
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
import com.plj.domain.bean.sys.SubFunctionDataBean;
import com.plj.domain.decorate.sys.SubFunctionData;
import com.plj.domain.request.sys.GetsubfuncDataPage;
import com.plj.service.sys.SubFunctionDataService;

@Controller
@RequestMapping(value="/subFunctionData")
public class SubFunctionDataAction {
	
	@Autowired
	@Qualifier(value="subFunctionDataService")
	private SubFunctionDataService subFunctionDataService;

	
	@RequestMapping(value="/insertSubFunctionData.json")
	@ResponseBody
	public Object insertSubFunctionData(SubFunctionDataBean bean,
			HttpServletRequest request,HttpServletResponse response)
			throws Exception{
		JsonResult result = new JsonResult();
		SubFunctionData subFunctionData=new SubFunctionData();
		if(StringUtils.isNotBlank(bean.getFuncCode())){
			subFunctionData.setFunctionCode(Integer.parseInt(bean.getFuncCode()));
		}
		if(StringUtils.isNotBlank(bean.getSubDesc())){
			subFunctionData.setSubDesc(bean.getSubDesc());			
		}
		if(StringUtils.isNotBlank(bean.getSubType())){
			subFunctionData.setSubType(bean.getSubType());
		}
		if(StringUtils.isNotBlank(bean.getSubExpr())){
			subFunctionData.setSubExpression(bean.getSubExpr());
		}
		if(StringUtils.isNotBlank(bean.getRemark())){
			subFunctionData.setRemark(bean.getRemark());
		}
		SubFunctionData subfunc = (SubFunctionData) result.getCacheData(true);
		SubFunctionData e=subFunctionDataService.insertSubFunctionData(subfunc);
		result.setData(e);
		return JSON.toJSON(result);
	}
	
	@RequestMapping(value="/deleteSubFunctionData.json")
	@ResponseBody
	public Object deleteSubFunctionData(SubFunctionDataBean bean,
			HttpServletRequest request,HttpServletResponse response)
		throws Exception{
		JsonResult result = new JsonResult();
		if(bean==null){
			result.addError(new MyError(MyError.SystemCode.SUBFUNCTIONDATA_IS_NULL,MyError.SystemMsg.SUBFUNCTIONDATA_IS_NULL));
			return result;
		}
		if(StringUtils.isBlank(bean.getSubFuncId())){
				result.addError(new MyError(MyError.SystemCode.SUBFUNCTIONDATA_ID_ERROR, MyError.SystemMsg.SUBFUNCTIONDATA_ID_ERROR));
				return result;
		}
		if(result.isSuccess())
		{
			result.setData(bean);
		}
		SubFunctionDataBean subFunctionDataBean = subFunctionDataService.deleteSubFunctionData(bean);
		if(subFunctionDataBean!=null){
			result.setData(subFunctionDataBean);
		}
		return JSON.toJSON(result);
	}
	
	@RequestMapping(value="/updateSubFunctionData.json")
	@ResponseBody
	public Object updateSubFunctionData(SubFunctionDataBean bean,
			HttpServletRequest request,HttpServletResponse response)
		throws Exception{
		
		JsonResult result = checkSubFunctionData(bean, true);
		if(!result.isSuccess())
		{
			return result;
		}
		SubFunctionData subFunctionData = (SubFunctionData) result.getCacheData(true);
		subFunctionDataService.updateSubFunctionData(subFunctionData);
		return JSON.toJSON(result);
	}
	
	private JsonResult checkSubFunctionData(SubFunctionDataBean bean, boolean checkId)
	{
		JsonResult result = new JsonResult();
		SubFunctionData subFunctionData = new SubFunctionData();
		
		if(checkId)
		{
			if(StringUtils.isBlank(bean.getSubFuncId()))
			{
				result.addError(new MyError(MyError.SystemCode.SUBFUNCTIONDATA_INSERT_ERROR, MyError.SystemMsg.SUBFUNCTIONDATA_INSERT_ERROR));
			}
			try
			{
				if(StringUtils.isNotBlank(bean.getSubFuncId())){
					subFunctionData.setSubFunctionId(Integer.parseInt(bean.getSubFuncId()));
				}
			}catch(Exception e)
			{
				result.addError(new MyError(MyError.SystemCode.SUBFUNCTIONDATA_ID_ERROR, MyError.SystemMsg.SUBFUNCTIONDATA_ID_ERROR));
			}
		}
		if(StringUtils.isNotBlank(bean.getFuncCode())){
			subFunctionData.setFunctionCode(Integer.parseInt(bean.getFuncCode()));
		}
		if(StringUtils.isNotBlank(bean.getSubDesc())){
			subFunctionData.setSubDesc(bean.getSubDesc());			
		}
		if(StringUtils.isNotBlank(bean.getSubType())){
			subFunctionData.setSubType(bean.getSubType());
		}
		if(StringUtils.isNotBlank(bean.getSubExpr())){
			subFunctionData.setSubExpression(bean.getSubExpr());
		}
		if(StringUtils.isNotBlank(bean.getRemark())){
			subFunctionData.setRemark(bean.getRemark());
		}
		if(result.isSuccess())
		{
			result.setData(subFunctionData);
		}
		return result;
	}
	@ResponseBody
	@RequestMapping(value="/getsubfuncData.json")
	@SuppressWarnings({"rawtypes","unchecked"})
	public Object getSubFunctionData(GetsubfuncDataPage bean, HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		HashMap map = new HashMap();
		map.put("pagination", bean);
		map.put("funccode", bean.getFuncCode());
		List<SubFunctionData> sub=subFunctionDataService.getSubFunctionData(map);
		JsonResult result = new JsonResult();
		ListData<SubFunctionDataBean> lr = 
				new ListData<SubFunctionDataBean>(bean.getTotalCount(), 
													subFunctionDataToSUBRecords(sub));
		result.setData(lr);
		return JSON.toJSON(result);
	}
	
	/**
	 * 把应用对象列表转化为页面应用对象列表
	 * @param subFunctionData
	 * @return
	 */
	private List<SubFunctionDataBean> subFunctionDataToSUBRecords
										(List<SubFunctionData> subFunctionData )
	{
		ArrayList<SubFunctionDataBean> subs = null;
		if(null != subFunctionData && subFunctionData.size() > 0)
		{
			subs = new ArrayList<SubFunctionDataBean>();
			for(int i = 0; i < subFunctionData.size(); i++	)
			{
				SubFunctionDataBean sub = subFunctionDataToSUBRecord(subFunctionData.get(i));
				subs.add(sub);
			}
		}
		return subs;
	}
	
	/**
	 * 把应用对象转化为页面应用对象
	 * @param subFunctionData
	 * @return
	 */
	private SubFunctionDataBean subFunctionDataToSUBRecord(SubFunctionData subFunctionData)
	{
		SubFunctionDataBean sub = null;
		if(subFunctionData != null)
		{
			sub=new SubFunctionDataBean();
			sub.setSubFuncId(subFunctionData.getSubFunctionId().toString());
			sub.setFuncCode(subFunctionData.getFunctionCode().toString());
			sub.setSubDesc(subFunctionData.getSubDesc());
			sub.setSubType(subFunctionData.getSubType());
			sub.setSubExpr(subFunctionData.getSubExpression());
			sub.setRemark(subFunctionData.getRemark());
		}
		return sub;
	}

}
