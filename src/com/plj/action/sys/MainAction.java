package com.plj.action.sys;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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
import com.plj.common.session.UserSession;
import com.plj.common.utils.MD5Utils;
import com.plj.domain.bean.sys.TreeBasic;
import com.plj.domain.bean.sys.TreeUtil;
import com.plj.domain.decorate.sys.Operator;
import com.plj.domain.request.sys.UpdatePasswordRequest;
import com.plj.domain.response.sys.TreeBean;
import com.plj.service.sys.MainService;
import com.plj.service.sys.MenuService;
import com.plj.service.sys.OperatorService;

@Controller
@RequestMapping(value="/main")
public class MainAction 
{
	/**
	 * 获取用户拥有功能�?
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/getTreeNodes.json")
	@ResponseBody
	public Object getTreeNodes(HttpServletRequest request,
			HttpServletResponse response)
	{
		UserSession user = (UserSession) request.getSession().getAttribute(UserSession.USER_SESSION);
		List<Map> obj = menuService.getMenuByUserId(user.getOperatorId());
		List<TreeBasic> list = new ArrayList<TreeBasic>();
		for (Map h : obj) {
			TreeBasic tb = new TreeBasic();
			tb.setName(h.get("NAME").toString());
			tb.setId(h.get("ID").toString());
			tb.setParentId(h.get("PARENTID") == null ? "" : h.get("PARENTID")
					.toString());
			HashMap para = new HashMap();
			para.put("action", h.get("ACTION") == null ? "" : h.get("ACTION")
					.toString());
			tb.setAttributes(para);
			list.add(tb);
		}
		List<TreeBean> tree = TreeUtil.onTree(list,"","");
		return JSON.toJSON(tree);
	}
	
	/**
	 * 修改当前操作用户请求�?
	 * @param operator
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/updateCurOperator.json")
	@ResponseBody
	public Object updateCurOperator(Operator operator, HttpServletRequest request,
			HttpServletResponse response)
	{
		UserSession user = (UserSession) request.getSession().getAttribute("user");
		JsonResult result = checkUpdateOperator(user, operator);
		if(null != user && user.getOperatorId() != null)
		{
			operator.setOperatorId(user.getOperatorId());
			operatorService.updateOperator(operator);
		}
		else
		{
			
		}
		return result;
	}
	
	/**
	 * 修改当前操作用户请求�?
	 * @param operator
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/updateCurPassword.json")
	@ResponseBody
	public Object updateCurPassword(UpdatePasswordRequest bean, HttpServletRequest request,
			HttpServletResponse response)
	{
		JsonResult result = new JsonResult();
		UserSession user = (UserSession) request.getSession().getAttribute("user");
		if(null == bean || StringUtils.isBlank(bean.getOldPassword())
				|| StringUtils.isBlank(bean.getNewPassword()))
		{
			result.addError("", "");
			return JSON.toJSON(result);
		}
		String oldPassword = (MD5Utils.getMD5String(bean.getOldPassword()));
		String newPassword = (MD5Utils.getMD5String(bean.getNewPassword()));
		int i = operatorService.updatePassword(oldPassword, newPassword, user.getUserName());
		if(i == 0)
		{
			result.addError("", "�޸ĵ��û�������");
		}
		return result;
	}
	
	/**
	 * 验证修改当前用户请求参数正确性�?
	 * @param user
	 * @param operator
	 * @return
	 */
	private JsonResult checkUpdateOperator(UserSession user, Operator operator)
	{
		JsonResult result = new JsonResult();//TODO
		return result;
	}


	@Autowired
	@Qualifier(value = "menuService")
	private MenuService menuService;
	
	@Autowired
	@Qualifier(value = "operatorService")
	private OperatorService operatorService;
}
