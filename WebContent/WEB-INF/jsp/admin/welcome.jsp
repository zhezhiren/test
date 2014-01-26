<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>欢迎页 </title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->


      <link rel="stylesheet" type="text/css" href="foundation/css/global.css" />
    <!-- ext-base.js表示框架的基础库。。。ext-all.js表示框架的核心库 -->
	<link rel="stylesheet" type="text/css" href="js/ext-3.4.0/resources/css/ext-all.css" />
	<script type="text/javaScript" src="js/ext-3.4.0/adapter/ext/ext-base.js"></script>
	<script type="text/javaScript" src="js/ext-3.4.0/ext-all.js"></script>
	<script type="text/javascript" src="js/ext-3.4.0/ext-lang-zh_CN.js" charset="utf-8" ></script>
	<script type="text/javascript">
	
	     var userId="<%=request.getSession().getAttribute("userId")%>";
	     var areaCode="<%=request.getSession().getAttribute("user_area")%>";	    
		 var url="<%=com.dhcc.common.constants.SystemPathConstants.gisUrl%>"+"?userId="+userId;
		
	     if(areaCode != null){
	    	 url = url + "&areaCode=" + areaCode;
	     }   
	     
	     Ext.onReady(function(){ 			
			 var map_center=new Ext.Panel({
					id:'map',
					title:'GIS地图',				
					header:false,							
					width:'auto',
					heigth:'auto',
					//layout : 'border',
					split:true,//显示分隔条
					//region:'center',
					collapsible:true,
					html : "<iframe src='"+url +"' name='welcome' id='welcome' width='100%' height='100%' style='border:0' frameborder='0'></iframe>"														
			});
			
			var vp=new Ext.Viewport({
				layout: 'fit',
				items:[	map_center ]
			});			 
		}); 
</script> 




	      
		
	
	</head>
	<body>

	</body>

  
</html>
