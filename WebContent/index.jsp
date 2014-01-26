<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>宁夏气象局三级业务系统</title>
 
   <link href="css/css_nx.css" rel="stylesheet" type="text/css" />
    <!-- ext-base.js表示框架的基础库。。。ext-all.js表示框架的核心库 -->
	<link rel="stylesheet" type="text/css" href="js/ext-3.4.0/resources/css/ext-all.css" />
	<script type="text/javaScript" src="js/ext-3.4.0/adapter/ext/ext-base.js"></script>
	<script type="text/javaScript" src="js/ext-3.4.0/ext-all.js"></script> 
	<script type="text/javascript" src="js/ext-3.4.0/ext-lang-zh_CN.js" charset="utf-8" ></script>
  </head>




  <body>
  
   <div id="bg" height="650" width="1290">
<div id="logo" height="56" width="358"></div>
             <div id="dlk" height="142" width="264">
             <form id = "form" action ="<%=basePath%>base/login.jspx" method="post">
              <ul>
                 <li>
    			<%
    				String errorCode = request.getParameter("errorCode");
    				if(null != errorCode)
    				{
    					if("1".equals(errorCode))
    					{
    			%>
    				<span style="color:red">用户名或密码不正确！</span>
    			<%
    					}else if("2".equals(errorCode))
    					{
    			%>
    			<span style="color:red">您输入的验证码有误</span>
    			<%
    					}else
    					{
				%>
    			<span style="color:red">未知错误</span>
    			<%	
    					}
    				}
    			%>
    		</li>
                 
                  <div id="shu">
                    <img src="images/nx/icon_5.jpg" height="31" width="31" class="tu" /><input id="userId" name="userId" type="text" value="" class="shuru" height="31" width="230" value="" />
                    
                  </div>
                  <div id="shu">
                     <img src="images/nx/icon_6.jpg" height="31" width="31" class="tu" /><input id ="password" name="password" type="password" value="" class="shuru" height="31" width="230" value="" />
                     <td><font id="mypassword" style="color:red">
							&nbsp;</font></td>
                  </div>
                  <div id="anniu">
                        <a href="javascript:onlogin();"><div id="denglu"></div></a>
                        <a href="javascript:onReset();"><div id="quxiao"></div></a>
                  </div>
                  
                  </ul>
              </form>
             </div>
</div>
   
   
   <script language="JavaScript" type="text/javascript">
  if (top !== self) {
          top.location=self.location;
      }
 
   function addListener(element,e,fn){    
        if(element.addEventListener){    
             element.addEventListener(e,fn,false);    
         } else {    
             element.attachEvent("on" + e,fn);    
          }    
   }
   var userName = document.getElementById("userId");
   var passWord = document.getElementById("password");
		/*   addListener(userName,"click",function(){
		    userName.value = "";
		   });
		   addListener(userName,"blur",function(){
		     if( userName.value == ""){
		      userName.value = "用户名";
		     }
		     
		   });
	   */
	   
	   
	   
	   function onReset(){
	   document.getElementById("userId").value="";
       document.getElementById("password").value="";
	   }
	   
	   
	  function onlogin(){
	   var userName = document.getElementById("userId").value;
       var passWord = document.getElementById("password").value;
	     if(userName==""){
	       Ext.Msg.alert("提示:","<font color=red>用户名不能为空!</font>");
	       return;
	     }
	     if(passWord==""){
	       Ext.Msg.alert("提示:","<font color=red>密码不能为空!</font>");
	       return;
	     }
	    
	    document.getElementById("form").submit()
	    
	   }
	
  
  
       
 Ext.onReady(function(){
	   Ext.getBody().on("keyup",function(e){
	     if(e.keyCode==Ext.EventObject.ENTER){
	       onlogin();
	     }
	     });
	 
	    
	});
   
   
   
</script>
  </body>
</html>
