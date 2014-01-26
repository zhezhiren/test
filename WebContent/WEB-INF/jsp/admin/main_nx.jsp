<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="/admin/tag/ext.tld" prefix="foundation"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<base href="<%=basePath%>">

<title>系统主页</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link href="css/css_nx.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="foundation/css/global.css" />
<!-- ext-base.js表示框架的基础库。。。ext-all.js表示框架的核心库 -->
<link rel="stylesheet" type="text/css"
	href="js/ext-3.4.0/resources/css/ext-all.css" />
<script type="text/javaScript"
	src="js/ext-3.4.0/adapter/ext/ext-base.js"></script>
<script type="text/javaScript" src="js/ext-3.4.0/ext-all.js"></script>
</head>
<body>
	<script type="text/javascript">
		if (top !== self) {
          top.location=self.location;
        }
		
		 var userName="<%=request.getSession().getAttribute("userName")%>";
		 var basePath= "<%=basePath%>";
	     var task,taskRunner;
	     var addWindow=null;
	     var infoForm=null;	  
	     var time;
	     
	     function getMenuFromBean(objectJson){
	     	 //var bbar = new Array(objectJson.length);
	     	 var bbar = new Ext.Toolbar();
	     	 //bbar.render('toolbar');
	      	 for(var i = 0 ; i < objectJson.length; i ++){
	      		 /* var bean = objectJson[i];
	  	         var bar = {pressed:false,text:"&nbsp;&nbsp;&nbsp;&nbsp;"+"<font size=3px  face='微软雅黑'color='#222222'>" + bean.text + "</font>"};
	      		 if(bean.leaf){
	  	    		 bar.url = bean.href;
	  	    		 bar.handler=handlerMenu 
	      		 //alert("");
	      		 }else{
	  	    		 bar.menu = getChildMenu(bean);
	      		 }
	      		 bbar[i] = bar; */
	      		 var bean = objectJson[i];
	      		 if(bean.leaf){
	      			bbar.add({
	      				pressed:false,
	      				text:"&nbsp;&nbsp;&nbsp;&nbsp;"+"<font size=3px  face='微软雅黑'color='#222222'>" + bean.text + "</font>",
	      				url:bean.attributes.action,
	      				handler:handlerMenu
	      			});
	      		 }else{
	      			 var menu=getChildMenu(bean);
	      			bbar.add({
	      				pressed:false,
	      				text:"&nbsp;&nbsp;&nbsp;&nbsp;"+"<font size=3px  face='微软雅黑'color='#222222'>" + bean.text + "</font>",
	      				menu:menu
	      			});
	      		 }
	           }
	      	 return bbar;
	       }
	       
	       function getChildMenu(object){
	      	 var menu = new Ext.menu.Menu();
	      	 if(object.children != null && object.children != undefined){
	      	     //var childItems = new Array(object.children.length);
	  	    	 for(var i = 0 ; i < object.children.length; i ++){
	  	    		 var bean = object.children[i];
	      			 /* var item = {
	  					 text:bean.text,
	  					 icon:""		//菜单项前面的图标
	      			 } */
	  	    		 if(bean.leaf){
	  	    			 /* item.handler=handlerMenu;
	  					 item.url=bean.href; */
	  	    			menu.add({
	  	    				text:bean.text,
	  	  					icon:"",		//菜单项前面的图标
	  	  					handler:handlerMenu,
	  	  					url:bean.attributes.action
	  	    			});
	  	    		 }else{
	  	    			 //item.menu=getChildMenu(bean);
	  	    			var childMenu=getChildMenu(bean);
	  	    			menu.add({
	  	    				text:bean.text,
	  	  					icon:"",		//菜单项前面的图标
	  	  					menu:childMenu
	  	    			});
	  	    		 }
	      			 //childItems[i] = item;
	  	    	 }
	      	     //menu.items = childItems;
	      	 }
	      	 return menu;
	       }
	       
	       function handlerMenu(item){
	    	 //  alert("in" + item.url);
	      	 if(!Ext.isEmpty(item.url)){               
	               Ext.getDom("x-workspace").src=item.url;
	           }
	       }
	       
	     
	     
	     
	     
	     
	   	 function display()
				{
				var now=new Date();   // 创建Date对象
				var year=now.getFullYear(); // 获取年份
				var month=now.getMonth(); // 获取月份
				var date=now.getDate();  // 获取日期
				var day=now.getDay();  // 获取星期
				var hour=now.getHours(); // 获取小时
				var minu=now.getMinutes(); // 获取分钟
				var sec=now.getSeconds(); // 获取秒钟
				month=month+1;
				var arr_week=new Array("星期日","星期一","星期二","星期三","星期四","星期五","星期六");
				var week=arr_week[day];  // 获取中文的星期
				time=year+"年"+month+"月"+date+"日 "+week+" "// +hour+":"+minu+":"+sec;
							// //组合系统时间
				 // document.getElementById("myclock").value=time;
				 // window.setTimeout("display()",1000);
				
				}
	    display();
	 



	// 系统超时检测
	  function sessionExpireCallBack(){			  
			Ext.Ajax.request({
				method : 'POST',
				url:'base/checkLogin.json',
				params:{
					
				},
				// 成功时回调
				success:function(response,options){				
					// 获取响应的json字符串
				var objectJson = Ext.util.JSON.decode(response.responseText);				
				// alert(response.responseText)
				if(!objectJson.data){
					taskRunner.stop(task);
				  Ext.MessageBox.show({
				       title: decodeURIComponent("%E5%AE%89%E5%85%A8%E6%8F%90%E7%A4%BA%EF%BC%9A "),
				   msg: decodeURIComponent('%E7%A9%BA%E9%97%B2%E6%97%B6%E9%97%B4%E6%8C%89%E8%B6%85%E8%BF%87%E5%AE%89%E5%85%A8%E6%97%B6%E9%97%B4%EF%BC%8C%E7%B3%BB%E7%BB%9F%E8%87%AA%E5%8A%A8%E8%B7%B3%E8%BD%AC%E5%88%B0%E7%99%BB%E5%BD%95%E9%A1%B5%E9%9D%A2%EF%BC%81 '),
				   buttons: Ext.MessageBox.OK,
				   fn: function(){window.location = basePath+"login.jsp";},
				           icon: Ext.MessageBox.WARNING
				       });
						
					}
				},
				// 失败
				failure:function(response, options){
					var responseJson =  Ext.util.JSON.decode(response.responseText);
					Ext.Msg.alert("提示:", "操作失败！", responseJson.errors);
							} 
						});
		}
				
				 
	   function sessionIsTimeOut(){
	        Ext.Ajax.request({
				method : 'POST',
			url:'base/checkLogin.json',
			params:{	},
			// 成功时回调
			success:function(response,options){				
				// 获取响应的json字符串
			var objectJson = Ext.util.JSON.decode(response.responseText);
			// alert(response.responseText)
			if(!objectJson.data){
			 window.location = basePath+"login.jsp";									
			}
			else{	
						
			   task = {
				   run:sessionExpireCallBack,
				   interval:35*60*1000  // (sessionTimeOut+5)*
			  };
			  taskRunner = new Ext.util.TaskRunner();
			  taskRunner.start(task);
			  
			/*
			 * var task = { run: sessionExpireCallBack, interval: 1000 //1 second } var
			 * runner = new Ext.util.TaskRunner(); runner.start(task);
			 */	  
					  
					  
				}
			},
			// 失败
			failure:function(response, options){
				var responseJson =  Ext.util.JSON.decode(response.responseText);
				Ext.Msg.alert("提示:", "操作失败！", responseJson.errors);
						} 
					});
			   
	   }
	 //   sessionIsTimeOut();
	   
	  // 密码修改-窗口
	function showWindow(){
	      if(addWindow==null){
	       infoForm=new Ext.FormPanel( {
				collapsible : false,
				frame:true,
				border:false,
				labelAlign : 'left',
				items : [{
				layout : 'column',
				 xtype:'fieldset',
				title:'修改密码:',
				items : [
						{
							columnWidth : 1,
							layout : 'form',
				labelWidth:60,
				items : [new Ext.form.TextField({
					fieldLabel : '原密码',
				name:'ADD_PASSWORD_OLD',
				id:'ADD_PASSWORD_OLD',
				width:180,
				inputType: 'password',
				allowBlank:false
			
				}),new Ext.form.TextField({
				fieldLabel : '新密码',
				name:'ADD_PASSWORD',
				id:'ADD_PASSWORD',
				width:180,
				inputType: 'password',
				allowBlank:false
			
				}),new Ext.form.TextField({fieldLabel : '确认密码',id:'ADD_PASSWORD_CONFIRM',name:'ADD_PASSWORD_CONFIRM',width:180,inputType: 'password',allowBlank:false,
				   initialPassField: 'add_password',listeners:{blur:function(field){
				   if(field.getValue()!=Ext.getCmp("ADD_PASSWORD").getValue()){
				   Ext.getCmp("ADD_PASSWORD_CONFIRM").markInvalid();
				   Ext.Msg.alert("提示:","两次密码输入不一致！请重新输入！");
											       return;
											       
											       }}} 
									})
												
												]
									}
								]
							}]
			});
	  
	      addWindow = new Ext.Window( { // 定义对话框
							width : 330,
							height : 210,
							shadow : true,
							title: '',
							closeAction : 'hide', // hide表示关闭的时候对话框是隐藏的，并没有真正的销毁，下次要用时在show出来
							modal : true,
							closable : true,
							minWidth : 330,
							layout : 'fit', 
							minHeight :210,
							buttons : [{
								text :'保存',
							id:'onSaveButton',
								handler:onSave
							},{
								text : '关闭',
								handler : function (){addWindow.hide();}
							}],
							items:[infoForm] // 将定义的表单加到对话框里
										});
							}
							Ext.getCmp("onSaveButton").setDisabled(false);
							    infoForm.getForm().reset();
							    addWindow.show();
							}

	// 信息修改-保存
	function onSave(){
		    if(infoForm.getForm().isValid()){
		        if(Ext.getCmp("ADD_PASSWORD").getValue()!=Ext.getCmp("ADD_PASSWORD_CONFIRM").getValue()){
					Ext.getCmp("ADD_PASSWORD_CONFIRM").markInvalid();
	      		return;
	     	 }

	Ext.Ajax.request({
		method : 'POST',
			url:'main/updateCurPassword.json',
			params:{newPassword:Ext.getCmp("ADD_PASSWORD").getValue(),
			 oldPassword:Ext.getCmp("ADD_PASSWORD_OLD").getValue()	},
			// 成功时回调
			success:function(response,options){				
				// 获取响应的json字符串
				var objectJson = Ext.util.JSON.decode(response.responseText);
			   alert(response.responseText);
				if(objectJson.success){
				   Ext.Msg.alert("提示:", "修改密码成功！");
			   addWindow.hide();
			}else{
			    Ext.Msg.alert("提示:", "修改密码失败！旧密码输入有误！");
				}
			},
			// 失败
			failure:function(response, options){	  
				Ext.Msg.alert("提示:", "操作失败！错误码：", response.status);
						} 
					});
						  
					      }
			  }
			     
	   
	   Ext.onReady(function(e){
				// 路径由你自己的EXT路径决定
	// Ext.BLANK_IMAGE_URL =
	// "../framework/ext2.0/resources/images/default/s.gif";
	Ext.QuickTips.init();
	// TOP显示
	var top = new Ext.Panel({
	// 这里可以自己写HTML实现,不需要EXT的代码
	region : 'north',
	height : 75,
	html : '<div id="top"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="top1"><tr><td id="L"></td><td width="30px"></td><td align="center" valign="bottom" id="M" ><ul class="lie"><a ><li class="liebiao">监测预报</li></a><a ><li class="liebiao">预报预警</li></a><a ><li class="liebiao">产品检验</li></a><a ><li class="liebiao">模拟学习</li></a><a ><li class="liebiao">系统管理</li></a></ul></td></tr></table></div>'			   
	});

	    
	var menuTree = <foundation:extTree
		   treeId="menuTree"			    
		   useArrows="true"			   
		   dataUrl="main/getTreeNodes.json"
		   rootId="menuTreeRoot" 
		   rootText="根节点"
		   baseParams="{menuId:Ext.getCmp('menuTree').currentNodeId}"
		   />;
		   
	  menuTree.on('click',function(node,ev){
		  var url = node.attributes.attributes.action;		  
		      if(!Ext.isEmpty(url)){               
		    		Ext.getDom("x-workspace").src=url;
		       }
	   }); 
		   
		   
		// CENTER显示
	var center = new Ext.Panel({
		region : 'center',
		layout : 'border',
	// 这里是除了top之外的部分
	tbar : [
	{pressed:false,text:'首页',iconCls: 'icon-controller', handler: function(){Ext.getDom("x-workspace").src="productShow.jsp";}},						
	{
	text : '<font size="2.5">欢迎当前用户：&nbsp;&nbsp;'+ userName + ' &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日期：&nbsp;&nbsp;'+ time + '</font>' 
	},'->',
	{pressed:false,text:'GIS',iconCls: 'icon-controller', handler: function(){Ext.getDom("x-workspace").src="frame/admin/welcome.jspx";}},'-',
	{
	text : '<font size="2.5"> 关于系统</font>',
	handler : function() {
				new Ext.Window({
					closeAction : 'close',
					resizable : false,
					bodyStyle : 'padding: 7',
					modal : true,
					iconCls : 'icon-info',
					title : '关于本系统',
					html : '<font size="2.9">本系统采用Spring+MyBatis+Ext3.4架构，独立安全的授权策略，人性化的用户操作界面，给您带来不一样的体验！</font>',
											width : 320,
											height : 250
										}).show();
									},
	iconCls : 'icon-info'
	},'-',{
	text : '<font size="2.5"> 修改密码</font>',
	handler : function() {
					showWindow();
					},
	iconCls : 'icon-edit'
	},'-',{
	text : '<font size="2.5"> 安全退出</font>',
	handler : function() {
					Ext.Msg.confirm('操作提示', '您确定要退出本系统?', function(btn) {
	if ('yes' == btn) {
	window.location = basePath+"base/logout.jspx";
							}
						});
					},
	iconCls : 'icon-delete'
	}],
	hight:85,
	items : [{
	// 左边的树
		region : 'west',
		width : 220,
	 //   layout : 'accordion',		
		title : '导航菜单',
		iconCls : 'icon-nav',
		collapsible : true,
		autoScroll:true,		
		collapsed : false,
		split : true,
		items : [menuTree]
		}, {
		id : 'content-panel',
		region : 'center',
		border : false,
		margins : '2 5 5 0',
		html : "<iframe src='productShow.jsp' name='x-workspace' id='x-workspace' width='100%' height='100%' style='border:0' frameborder='0'></iframe>"
	}]
	});
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [top,center]
	});
	});
    </script>
</body>
</html>
