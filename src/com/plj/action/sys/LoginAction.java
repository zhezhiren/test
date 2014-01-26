package com.plj.action.sys;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.plj.common.result.JsonResult;
import com.plj.common.session.UserSession;
import com.plj.common.utils.MD5Utils;
import com.plj.domain.decorate.sys.Employee;
import com.plj.domain.decorate.sys.Operator;
import com.plj.domain.decorate.sys.Role;
import com.plj.domain.response.sys.OrgInfo;
import com.plj.service.sys.EmployeeService;
import com.plj.service.sys.OperatorService;
import com.plj.service.sys.OrganizationService;
import com.plj.service.sys.RoleService;

@Controller
public class LoginAction 
{
	private static final String LOGIN_PAGE = "redirect:/login_nx.jsp";
	private static final String INDEX_PAGE = "redirect:/index.html";
	
	private static final String SUCCESS_PAGE_SUPER ="redirect:/frame/admin/main.jspx";
	private static final String SUCCESS_PAGE_YUBAO ="redirect:/frame/admin/main_yubaoyuan.jspx";
	//private static final String SUCCESS_PAGE_LIULAN ="redirect:/frame/admin/main.jspx";
	
	
	//验证码图片的宽度�?
	private int width=60; 
	//验证码图片的高度�?
	private int height=20; 
	//验证码字符个�?
	private int codeCount=4; 
	
	//private int x=0; 
	//字体高度
	//private int fontHeight; 
	
	//private int codeY; 
	
	private char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
			'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
			'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	
	@Autowired
	@Qualifier("operatorService")
	OperatorService operatorService;
	
	@Autowired
	@Qualifier("employeeService")
	EmployeeService employeeSvc;
	
	@Autowired
	@Qualifier(value = "organizationService")
	private OrganizationService organizationService;
	
	@Autowired
	@Qualifier(value = "roleService")
	private RoleService roleService;
	
	/**
	 * 没登陆跳转页�?
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/base/loginPage.jspx", method = {RequestMethod.GET, RequestMethod.POST})
	public Object toLoginPage(HttpServletRequest request, HttpServletResponse response)
	{
		//System.out.println(request.getContentType());
		//System.out.println(response.getContentType());
		//response.setContentType("text/html");
	    return LOGIN_PAGE;
	}
	
	/**
	 * 没登陆返回json
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/base/loginWarning.jspx", method = {RequestMethod.GET, RequestMethod.POST})
	public void loginWarning(HttpServletRequest request, HttpServletResponse response) throws IOException
	{

        	response.addHeader("sessionstatus", "timeout");    
            Map<String, Object> result = new HashMap<String, Object>();   
            //result.put("success", false);   
            result.put("timeout",true);   
            PrintWriter out = response.getWriter();   
            
            JsonResult re = new JsonResult();
            re.setSuccess(false);
            re.setData(result);
            out.print(JSON.toJSON(re));   
            out.flush();   
            out.close();   
	}
	
	/**
	 * 获取验证�?
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/base/{random}/validateCode.jspx", method = {RequestMethod.GET, RequestMethod.POST})
	public void validateCode (HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		BufferedImage buffImg = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB); 
				Graphics2D g = buffImg.createGraphics(); 
		//创建�?��随机数生成器�?
		Random random = new Random(); 
		//将图像填充为白色
		g.setColor(Color.WHITE); 
		g.fillRect(0, 0, width, height); 
		//创建字体，字体的大小应该根据图片的高度来定�?
		Font font = new Font("Fixedsys", Font.PLAIN, height-2); 
		//设置字体�?
		g.setFont(font); 
		//画边框�?
		g.setColor(Color.BLACK); 
		g.drawRect(0, 0, width - 1, height - 1); 
		//随机产生160条干扰线，使图象中的认证码不易被其它程序探测到�?
		g.setColor(Color.LIGHT_GRAY); 
		for(int i = 0; i < 160; i++) 
		{
			int x = random.nextInt(width); 
			int y = random.nextInt(height); 
			int xl = random.nextInt(12); 
			int yl = random.nextInt(12); 
			g.drawLine(x, y, x + xl, y + yl); 
		}
		//randomCode用于保存随机产生的验证码，以便用户登录后进行验证�?
		StringBuffer randomCode = new StringBuffer(); 
		int red = 0, green = 0, blue = 0; 
		//随机产生codeCount数字的验证码�?
		for (int i = 0; i < codeCount; i++) {
			//得到随机产生的验证码数字�?
			String strRand = String.valueOf(codeSequence[random.nextInt(36)]); 
			//产生随机的颜色分量来构�?颜色值，这样输出的每位数字的颜色值都将不同�?
			red = random.nextInt(155); 
			green = random.nextInt(155); 
			blue = random.nextInt(155); 
	
			//用随机产生的颜色将验证码绘制到图像中�?
			g.setColor(new Color(red, green, blue)); 
			g.drawString(strRand, (i + 1) * width/(codeCount+1), height-4); 
	
			//将产生的四个随机数组合在�?���?
			randomCode.append(strRand); 
		}
		// 将四位数字的验证码保存到Session中�?
		HttpSession session = request.getSession(); 
		session.setAttribute("validateCode", randomCode.toString()); 

		// 禁止图像缓存�?
		response.setHeader("Pragma", "no-cache"); 
		response.setHeader("Cache-Control", "no-cache"); 
		response.setDateHeader("Expires", 0); 

		response.setContentType("image/jpeg"); 

		//将图像输出到Servlet输出流中�?
		ServletOutputStream sos = response.getOutputStream(); 
		ImageIO.write(buffImg, "jpeg", sos); 
		sos.close(); 
	}
	
	/**
	 * 用户平台登陆
	 * @param currentUser
	 * @param result
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
    @RequestMapping(value = "/base/login.jspx", method = {RequestMethod.POST})
    public String login(Operator operator, BindingResult result, Model model,
        HttpServletRequest request, HttpServletResponse response)
    {
    	String userId = operator.getUserId();
    	String password = operator.getPassword();
    	if(null != operator && StringUtils.isNotBlank(userId)
    			&& StringUtils.isNotBlank(password))
    	{
    		password = (MD5Utils.getMD5String(password));
    		operator = operatorService.login(userId, password);
    		if(null != operator && operator.getOperatorId() != null)
    		{
    			operator.setLastLoginTime(new Date());
    			operatorService.updateLoginTime(operator);
    			Employee employee = employeeSvc.getEmployeeByOperatorId(operator.getOperatorId());
    			UserSession user = new UserSession();
    			user.setOperatorId(operator.getOperatorId());
    			user.setUserName(operator.getUserId());
    			String userName = null;
    			String orgName = "";
    			if(null != employee)
    			{
    				user.setEmpName(employee.getEmployeeName());
    				user.setOrgId(employee.getOrgId());
    				if(null != employee.getOrgId())
    				{
    					List<OrgInfo> info = organizationService.loadOrgById(employee.getOrgId().toString());
    					if(info != null && info.size() > 0)
    					{
    						user.setAreaCode(info.get(0).getArea());
    						request.getSession().setAttribute(UserSession.USER_AREA, user.getAreaCode());
    						orgName = info.get(0).getOrgName();
    						orgName = StringUtils.isBlank(orgName)? "" : orgName;
    					}
    				}
    				userName = employee.getEmployeeName();
    				if(StringUtils.isBlank(userName))
    				{
    					userName = StringUtils.isBlank(operator.getOperatorName())?operator.getUserId():operator.getOperatorName();
    				}
    			}else
    			{
    				userName = StringUtils.isBlank(operator.getOperatorName())?operator.getUserId():operator.getOperatorName();
    			}
    			request.getSession().setAttribute("userOrg", orgName);
    			request.getSession().setAttribute(UserSession.USER_SESSION, user);
    			request.getSession().setAttribute("operatorId", operator.getOperatorId());
    			request.getSession().setAttribute("userId", operator.getUserId());
    			request.getSession().setAttribute("userName", userName);
    			return /*getSuccessPage(operator.getOperatorId())*/SUCCESS_PAGE_SUPER;
    		}
    	}
        return LOGIN_PAGE + "?errorCode=1";
    }
    
    @RequestMapping(value = "/jumpTo.jspx")
    public String jumpTo(HttpServletRequest request, HttpServletResponse response)
    {
    	UserSession user = (UserSession) request.getSession().getAttribute(UserSession.USER_SESSION);
    	return getSuccessPage(user.getOperatorId());
    }
    
    /**
     * 登出接口
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/base/logout.jspx", method = {RequestMethod.POST, RequestMethod.GET})
    public String loginOut(HttpServletRequest request, HttpServletResponse response)
    {	
		request.getSession().invalidate();
		return INDEX_PAGE;
    }
	
    /**
     * �?��是否已经登录 true为以登录，false为未登录
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/base/checkLogin.json")
	@ResponseBody
	public Object checkLogin(HttpServletRequest request,
			HttpServletResponse response)
	{
		UserSession user = (UserSession) request.getSession().getAttribute(UserSession.USER_SESSION);
		JsonResult result = new JsonResult();
		if(null != user)
		{
			result.setData(true);
		}else
		{
			result.setData(false);
		}
		return JSON.toJSON(result);
	}
    
    private String getSuccessPage(Integer operatorId)
    {
    	List<Role> roles = roleService.loadOperatorRolesById(String.valueOf(operatorId));
		List<Integer> roleIds = new ArrayList<Integer>(roles.size());
		if(null != roles)
		{
			for(int i = 0; i < roles.size(); i++)
			{
				Role role = roles.get(i);
				if(role != null)
				{
					roleIds.add(role.getRoleId());
				}
			}
		}
		if(roleIds.contains(1))
		{
			return SUCCESS_PAGE_SUPER;
		}else
		{
			return SUCCESS_PAGE_YUBAO;
		}
    }
}
