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
	     Ext.onReady(function(){ 			
			 var _center=new Ext.Panel({
					id:'workflow',
					title:'业务流程图',				
					header:false,							
					width:'auto',
					heigth:'auto',
					//layout : 'border',
					split:true,//显示分隔条
					//region:'center',
					collapsible:true,
					
				//	html :' <br><br><br><br><br><img src="images/productImages/ncep_slp_2013042500_096.gif" margin-left:50px">'	
					html:"<div align=center><img margin-left:50px margin-right:50px id ='img_workflow' height='100%'width='100%' src='images/productImages/workflow2.jpg'></div>"
			});
			
			var vp=new Ext.Viewport({
				layout: 'fit',
				items:[	_center ]
			});			 
		}); 
</script> 




	      
		
	
	</head>
	<body>

	</body>

  
</html>
