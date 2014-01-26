<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
 request.getSession().getMaxInactiveInterval();
%>
<%@ taglib uri="/admin/tag/ext.tld" prefix="foundation" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
	<link href="css/main.css" rel="stylesheet" type="text/css" />	
	<link href="css/login.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="foundation/css/global.css" />
    <!-- ext-base.js表示框架的基础库。。。ext-all.js表示框架的核心库 -->
	<link rel="stylesheet" type="text/css" href="js/ext-3.4.0/resources/css/ext-all.css" />
	<link rel="stylesheet" type="text/css" href="css/Spinner.css"/>
	<script type="text/javaScript" src="js/ext-3.4.0/adapter/ext/ext-base.js"></script>
	<script type="text/javaScript" src="js/ext-3.4.0/ext-all.js"></script>
	<script type="text/javascript" src="js/ext-3.4.0/ext-lang-zh_CN.js" charset="utf-8" ></script>
	<script type="text/javaScript" src="js/ext-3.4.0/ux/DatetimePicker.js"></script>
	<script type="text/javascript" src="js/ext-3.4.0/ux/Spinner.js"> </script>
	<script type="text/javascript" src="js/ext-3.4.0/ux/SpinnerField.js"></script> 
  </head>
  
  <body>
 

  <script type="text/javascript">  
     var userId="<%=request.getSession().getAttribute("userName")%>";
     var basePath= "<%=basePath%>";     
     var sessionTimeOut="<%=request.getSession().getMaxInactiveInterval()%>";   
     var task,taskRunner;
     var addWindow=null;
     var infoForm=null;
     var addDutyWindow=null;
     var infoDutyForm=null;     
     var timeOut="";
     var time;

   	 function display()
			{					
			var now=new Date();   //创建Date对象
			var year=now.getFullYear(); //获取年份
			var month=now.getMonth(); //获取月份
			var date=now.getDate();  //获取日期
			var day=now.getDay();  //获取星期
			var hour=now.getHours(); //获取小时
			var minu=now.getMinutes(); //获取分钟
			var sec=now.getSeconds(); //获取秒钟
			month=month+1;
			var arr_week=new Array("星期日","星期一","星期二","星期三","星期四","星期五","星期六");
			var week=arr_week[day];  //获取中文的星期
			time=year+"年"+month+"月"+date+"日 "+week+" "//+hour+":"+minu+":"+sec; //组合系统时间
		 //   document.getElementById("myclock").value=time;
		 //   window.setTimeout("display()",1000);
		    var myTime = setTimeout("display()",1000); 
			}
		//	window.onload=function()
		//	{
		//	 window.setInterval("display()", 1000);
		//	}
	function getTime(){
	   var   task2 = {
				   run:display(),
				   interval:1000//(sessionTimeOut+5)*
				  };
		 var taskRunner2 = new Ext.util.TaskRunner();
		  taskRunner2.start(task2);
	
	}
	
      display();
                  
	function sessionExpireCallBack(){			  
		Ext.Ajax.request({
			method : 'POST',
			url:'base/checkLogin.json',
			params:{
				
			},
			//成功时回调
			success:function(response,options){				
				//获取响应的json字符串
				var objectJson = Ext.util.JSON.decode(response.responseText);				
			//	alert(response.responseText);
			  //  timeOut="1";			    
				if(!objectJson.data){
				    timeOut="1";
					taskRunner.stop(task);
				//	center.doLayout();
			      Ext.MessageBox.show({
			           title: decodeURIComponent("%E5%AE%89%E5%85%A8%E6%8F%90%E7%A4%BA%EF%BC%9A "),
			           msg: decodeURIComponent('%E7%A9%BA%E9%97%B2%E6%97%B6%E9%97%B4%E6%8C%89%E8%B6%85%E8%BF%87%E5%AE%89%E5%85%A8%E6%97%B6%E9%97%B4%EF%BC%8C%E7%B3%BB%E7%BB%9F%E8%87%AA%E5%8A%A8%E8%B7%B3%E8%BD%AC%E5%88%B0%E7%99%BB%E5%BD%95%E9%A1%B5%E9%9D%A2%EF%BC%81 '),
			           buttons: Ext.MessageBox.OK,
			           fn: function(){
			           
			           window.location = basePath+"login.jsp";},
			           icon: Ext.MessageBox.WARNING
			       });					
				}				
			},
			//失败
			failure:function(response, options){
			//	var responseJson =  Ext.util.JSON.decode(response.responseText);
				Ext.Msg.alert("提示:", "操作失败！", responseJson.status);
			} 
		});
	}
			
	 function sessionIsTimeOut(){
        Ext.Ajax.request({
			method : 'POST',
			url:'base/checkLogin.json',
			params:{	},
			//成功时回调
			success:function(response,options){				
				//获取响应的json字符串
				var objectJson = Ext.util.JSON.decode(response.responseText);
			//	alert(response.responseText)
				if(!objectJson.data){
				 window.location = basePath+"login.jsp";									
				}
				else{	             				
				   task = {
					   run:sessionExpireCallBack,
					   interval:(sessionTimeOut+5)*1000
					  };
					  taskRunner = new Ext.util.TaskRunner();
					  taskRunner.start(task);					  
				}
			},
			//失败
			failure:function(response, options){
				//var responseJson =  Ext.util.JSON.decode(response.responseText);
				Ext.Msg.alert("提示:", "操作失败！", responseJson.status);
			} 
		});
   
   }
    sessionIsTimeOut();
   
   //接班弹出窗口
   function showDutyWindow_jieban(){
        var infoDutyForm_jieban = new Ext.FormPanel({
	        labelWidth: 65,
	        border:true,
	        region : "west",
	        frame:true,
	        split : true,
	        title:'接班信息查看',
	        margins:'3 0 0 0',
	        buttonAlign:'left',
	        items: {
	            xtype:'panel',
	            defaults:{autoHeight:true, bodyStyle:'padding:5px'}, 
	            items:[{
	                layout:'form',
	                defaults: {width: 230},
	                defaultType: 'textfield',
	
	                items: [{
	                    fieldLabel: '姓名',
	                    name: '_empName',
	                    id: '_empName',
	                    readOnly:true,
	                    allowBlank : true
	                },{
	                  //  xtype: 'datetimefield',
	                    format:'YmdHis',
	                    fieldLabel: '开始时间',
	                    name: '_startTime',
	                    id: '_startTime',
	                    readOnly:true,
	                    allowBlank : true
	                },{
					//	 xtype: 'datetimefield',
						 name: '_endTime',
						 id:'_endTime',
						 readOnly:true,
						 fieldLabel: '结束时间',
						 width:230 ,
						  
						 allowBlank : true 
						},new Ext.form.TextArea( {
							fieldLabel : '工作内容',
							id:'_jobContent',
							name:'_jobContent',
							width : 335,
							readOnly:true,
							height:80,
							allowBlank : true
						}),new Ext.form.TextArea( {
							fieldLabel : '交接内容',
							id:'_warningContent',
							name:'_warningContent',
							width : 335,
							readOnly:true,
							height:80,
							allowBlank : true
						})
	                ]
	            }]
	        },	       
				listeners:{
				 beforeshow  :function(p){
				  return true;
				 }
			}
	    });
	    
	   var addDutyWindow_jieban = new Ext.Window( { //定义对话框
						width : 360,
						height : 400,
						shadow : true,
						title: '接班管理',
						closeAction : 'hide', //hide表示关闭的时候对话框是隐藏的，并没有真正的销毁，下次要用时在show出来
						modal : true,
				 		closable : true,
						minWidth : 700,
						layout : 'fit', 
		    			minHeight : 470,
			 			buttons : [{
							text : '关闭',
							handler : function (){addDutyWindow_jieban.hide();}
						}],
						items:[infoDutyForm_jieban] //将定义的表单加到对话框里
						});
      
       Ext.Ajax.request({
			method : 'POST',
			url:'duty/onDuty.json',
			params:{},
			//成功时回调
			success:function(response,options){				
				//获取响应的json字符串
				var objectJson = Ext.util.JSON.decode(response.responseText);
				alert(response.responseText);
				if(objectJson.success){
				     var  _empName=objectJson.data.empName;
			         var  _startTime=objectJson.data.startTime;
			         var  _endTime=objectJson.data.endTime;
			         var  _jobContent=objectJson.data.jobContent;
			         var  _warningContent=objectJson.data.warningContent;
				     var  jsonValues={
				          _empName:_empName,
				          _startTime:_startTime,
				          _endTime:_endTime,
				          _warningContent:_warningContent,
				          _jobContent:_jobContent
				     };       
			        infoDutyForm_jieban.getForm().reset(); 			          
					infoDutyForm_jieban.getForm().setValues(jsonValues);				   
				}
			},
			//失败
			failure:function(response, options){			  
				Ext.Msg.alert("提示:", "操作失败！错误码："+responseJson.status);
			} 
		});         
        addDutyWindow_jieban.show();    
   }
   
   //交班弹出窗口
   function showDutyWindow(){
       if(addDutyWindow==null){
	    infoDutyForm =  new Ext.FormPanel({
	        labelWidth: 65,
	        border:true,
	        region : "west",
	        frame:true,
	        split : true,
	        title:'交班信息',
	        margins:'3 0 0 0',
	        buttonAlign:'left',
	        items: {
	            xtype:'panel',
	            defaults:{autoHeight:true, bodyStyle:'padding:5px'}, 
	            items:[{
	                layout:'form',
	                defaults: {width: 230},
	                defaultType: 'textfield',
	
	                items: [{
	                    fieldLabel: '姓名',
	                    name: 'addAndEdit_empName',
	                    id: 'addAndEdit_empName',
	                    allowBlank : false
	                },{
	                    xtype: 'datetimefield',
	                    format:'YmdHis',
	                    fieldLabel: '开始时间',
	                    name: 'addAndEdit_startTime',
	                    id: 'addAndEdit_startTime',
	                    allowBlank : false
	                },{
						 xtype: 'datetimefield',
						 name: 'addAndEdit_endTime',
						 id:'addAndEdit_endTime',
						 format:'YmdHis',
						 fieldLabel: '结束时间',
						 width:230 ,
						 listeners : {  //主要这里添加监听事件     
							    select : function(f, date) {          
							                 DateIsValid_add();  
							              },
							    blur: function() {          
							              DateIsValid_add();
				                      }	
				               }, 
						 allowBlank : false 
						},new Ext.form.TextArea( {
							fieldLabel : '工作内容',
							id:'addAndEdit_jobContent',
							name:'addAndEdit_jobContent',
							width : 335,
							height:80,
							allowBlank : false
						}),new Ext.form.TextArea( {
							fieldLabel : '交接内容',
							id:'addAndEdit_warningContent',
							name:'addAndEdit_warningContent',
							width : 335,
							height:80,
							allowBlank : false
						})
	                ]
	            }]
	        },	       
				listeners:{
				 beforeshow  :function(p){
				      return true;
				 }
			}
	    });
	    
	    		
	//验证开始时间与结束时间	
  function DateIsValid_add(){	
	 var startTime = Ext.isEmpty(Ext.getCmp("addAndEdit_startTime").getValue())?"":Ext.getCmp("addAndEdit_startTime").getValue();	 
	 var endTime = Ext.isEmpty(Ext.getCmp("addAndEdit_endTime").getValue())?"":Ext.getCmp("addAndEdit_endTime").getValue();	 
	 if (startTime!=""&&endTime!=""){	
	    if(Ext.getCmp("addAndEdit_startTime").getValue()>=Ext.getCmp("addAndEdit_endTime").getValue()){
	   
		     Ext.Msg.alert("提示:", "结束时间必须大于开始时间！请重新选择！");
		     Ext.getCmp("addAndEdit_endTime").setValue("");	     	   
	     }
	    }	 
	}	
	
			
	//验证开始时间与结束时间	
  function DateIsValid(){	
	 var startTime = Ext.isEmpty(Ext.getCmp("search_startTime").getValue())?"":Ext.getCmp("search_startTime").getValue().format("Y-m-d");	 
	 var endTime = Ext.isEmpty(Ext.getCmp("search_endTime").getValue())?"":Ext.getCmp("search_endTime").getValue().format("Y-m-d");	 
	 if (startTime!=""&&endTime!=""){	
	    if(Ext.getCmp("search_startTime").getValue().format("Y-m-d")>=Ext.getCmp("search_endTime").getValue().format("Y-m-d")){
	   
		     Ext.Msg.alert("提示:", "结束时间必须大于开始时间！请重新选择！");
		     Ext.getCmp("search_endTime").setValue("");	     	   
	     }
	    }	 
	}
	    //------------加载右边的pannel
	    
	var record_duty=[	    	
        {name : 'id',type : 'string'},
        {name : 'startTime',type : 'string'},
        {name : 'endTime',type : 'string'},
        {name : 'dutyOperatoerId',type : 'string'},
        {name : 'jobContent',type : 'string'},
        {name : 'warningContent',type : 'string'},
        {name : 'empName',type : 'string'},
        {name : 'orgId',type : 'string'}
		];
	//创建record
	var recordHeads_duty = Ext.data.Record.create(record_duty);	
	//定义查询数据的url
    var dataStore_duty = new Ext.data.Store( {
		proxy:new Ext.data.HttpProxy(new Ext.data.Connection({
		     timeout:0,url:'duty/searchDutyAct.json'})),
		reader : new Ext.data.JsonReader( {
			root : 'data.arr',
			totalProperty : 'data.totalProperty'
		}, recordHeads_duty),
		remoteSort : false		
	});
		
	dataStore_duty.on('beforeload', function() {
				dataStore_duty.baseParams = {
					 empName:Ext.getCmp("search_empName").getValue(),
					 startTime:Ext.getCmp("search_startTime").getValue(),
					 endTime:Ext.getCmp("search_endTime").getValue()
				   
				};
			});			
	
	//定义页面查询表格
	var dataCm_duty = new Ext.grid.ColumnModel({	
       columns:[
			{
			header:'姓名',
			width:60,
			dataIndex : 'empName'
		}, {
			header : '开始时间 ',
			width:130,
			dataIndex:'startTime'
		}
		, {
			header : '结束时间 ',
			width:130,
			dataIndex:'endTime'
		}, {
			header : '交班内容 ',
			width:180,
			dataIndex:'warningContent'
		}, {
			header : '工作内容 ',
			width:180,
			dataIndex:'jobContent'
		}
      ],
	    defaults: {
        sortable: false,
        menuDisabled: true
    }
	});
	
	
	
	  //定义dataGrid_log分页栏	
	var dataGrid_duty_pagingBar = new Ext.PagingToolbar({        
        store: dataStore_duty,
        displayInfo: true,
        displayMsg : '当前显示第 {0} - {1} 条记录，一共 {2} 条',
	    emptyMsg:'没有数据!'
    });	
    
    var dataGrid_duty = new Ext.grid.GridPanel({
        store: dataStore_duty,
        cm: dataCm_duty,
        border : true,     
        region : "center",
        loadMask : true,
        split : true,
        enableColumnMove: false,
    //    sm:sm2,
        stripeRows: true,
        iconCls : 'grid-icon',
       // title:'' ,
        bbar: dataGrid_duty_pagingBar,
        listeners :{
           rowdblclick:function(grd,rowIndex,ev){
        	
        	}
        }
    });
    
    //定义查询 Form  
    var conditionForm=new Ext.FormPanel( {
		//	buttonAlign:'center',
			id:'conditionForm',
			frame:true,
			height : 100,						
			collapsible : false,
			region : "north",
			title:'查询条件:',
			border : false,
			bodyBorder:false,
			labelAlign : 'left',
			items : [{
			layout : 'column',
			items : [
					{
						columnWidth : .40,
						layout : 'form',
						labelWidth:60,
						items : [									
								new Ext.form.DateField({  
						            name:"search_startTime", 
						            fieldLabel: '开始时间',
						            id: 'search_startTime',
						            editable:false, //不允许对日期进行编辑  
						            width:150,  
						            format:"Y-m-d", 
						             listeners : {  //主要这里添加监听事件     
									    select : function(f, date) {          
									               DateIsValid();  
									            }     },  
						            emptyText:"请选择开始时间..."  
							        }) ,
							          {
					    xtype: 'textfield',
	                    fieldLabel: '姓名',
	                    name: 'search_empName',
	                    id: 'search_empName',
	                    width:150,
	                    allowBlank : true
	                }
								
								]
					},
					{
						columnWidth : .40,
						labelWidth:60,
						layout : 'form',
						items : [//  search_statusCombobox,
																
									new Ext.form.DateField({  
							            name:"search_endTime", 
							            fieldLabel: '结束时间',
							            id: 'search_endTime',
							            editable:false, //不允许对日期进行编辑  
							            width:150,  
							            format:"Y-m-d", 
							            listeners : {  //主要这里添加监听事件     
										    select : function(f, date) {          
										          DateIsValid();  
										                                    }     }, 
							            emptyText:"请选择结束时间..."  
							        }) 
								//	new Ext.Button({text:'查询',iconCls : 'icon-search',handler:function(){dataStore.load();}})
									]
					},
					{
						columnWidth : .20,
						labelWidth:60,
						layout : 'form',
						items : [ 	new Ext.Button({text:'查询',
											iconCls : 'icon-search',
											handler:function(){					
						                     dataStore_duty.load(); 
						                    }
						               })
									]
					}
					
					
					]
		
			}]
		});
	   
	   	var duty_panel = new Ext.Panel({
			  layout : 'border',
			  region : "center",
	          border : false,
	          title:'交班查询',
			  split : true,
			
			  items:[conditionForm,dataGrid_duty]
				
		});
	dataStore_duty.load(); 
	   
   
	    
	  var innerPanel=new Ext.Panel({
          layout : 'border',
          border : false,
		  split : true,
		//  title:'排班管理',			
		  items:[infoDutyForm,duty_panel]
    });
	    
	      var dutyTabPanel = new Ext.TabPanel({
                region : 'center',
		        activeTab: 0,
		        border : false,
		        plain:true,
		        items:[infoDutyForm,duty_panel]
                });
           
       addDutyWindow = new Ext.Window( { //定义对话框
						width : 700,
						height : 450,
						shadow : true,
						title: '交班管理',
						closeAction : 'hide', //hide表示关闭的时候对话框是隐藏的，并没有真正的销毁，下次要用时在show出来
						modal : true,
				 		closable : true,
						minWidth : 700,
						layout : 'fit', 
		    			minHeight : 470,
			 			buttons : [{
							text :'保存',
							id:'onSaveButton',
							handler: onSaveDuty
						},{
							text : '关闭',
							handler : function (){addDutyWindow.hide();}
						}],
						items:[dutyTabPanel] //将定义的表单加到对话框里
						});
      
       
       }
     
        addDutyWindow.show();
   
   }
	 
	 function onSaveDuty() {
		if(infoDutyForm.getForm().isValid()){               
	         Ext.Ajax.request({
	                url: 'duty/addDutyAct.json',               
	                params: {
		                empName:Ext.getCmp("addAndEdit_empName").getValue(),
						startTime:Ext.getCmp("addAndEdit_startTime").getValue(),
						endTime:Ext.getCmp("addAndEdit_endTime").getValue(),
						jobContent:Ext.getCmp("addAndEdit_jobContent").getValue(),
						warningContent:Ext.getCmp("addAndEdit_warningContent").getValue()						
	                },
	                method: 'POST',
	                success:  function (response, options) {                                  
	                    var responseJson =  Ext.util.JSON.decode(response.responseText);
	                        Ext.MessageBox.alert('成功', '从服务端获取结果123: ' + response.responseText);                 
	                    if(responseJson.success){                                     
					      	Ext.MessageBox.alert('成功', '保存成功！' );                 		     					        
	                        addDutyWindow.hide();
					    } else{
					        Ext.MessageBox.alert('失败', '操作失败！' ); 
					    }               
	                },
	                failure: function (response, options) {
	                   
	                    Ext.MessageBox.alert('失败','操作失败！错误码：'+  response.status);
	                }
	            });     
           }
	  }  
	   
	   
	   //密码修改-窗口
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
							columnWidth : .50,
							layout : 'form',
							labelWidth:60,
							items : [new Ext.form.TextField({
								fieldLabel : '新密码',
								name:'ADD_PASSWORD',
								id:'ADD_PASSWORD',
								width:180,
								inputType: 'password',
								allowBlank:false
							
								})
									
									]
						},{
							columnWidth : .50,
							labelWidth:60,
							layout : 'form',
							items : [																	
									new Ext.form.TextField({fieldLabel : '确认密码',id:'ADD_PASSWORD_CONFIRM',name:'ADD_PASSWORD_CONFIRM',width:180,inputType: 'password',allowBlank:false,
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
      
          addWindow = new Ext.Window( { //定义对话框
						width : 560,
						height : 150,
						shadow : true,
						title: '',
						closeAction : 'hide', //hide表示关闭的时候对话框是隐藏的，并没有真正的销毁，下次要用时在show出来
						modal : true,
				 		closable : true,
						minWidth : 700,
						layout : 'fit', 
		    			minHeight : 470,
			 			buttons : [{
							text :'保存',
							id:'onSaveButton',
							handler:onSave
						},{
							text : '关闭',
							handler : function (){addWindow.hide();}
						}],
						items:[infoForm] //将定义的表单加到对话框里
						});
	        }
	        Ext.getCmp("onSaveButton").setDisabled(false);
	        addWindow.show();
	    }

		//信息修改-保存
	function onSave(){
		    if(infoForm.getForm().isValid()){
		        if(Ext.getCmp("ADD_PASSWORD").getValue()!=Ext.getCmp("ADD_PASSWORD_CONFIRM").getValue()){
		      	    Ext.getCmp("ADD_PASSWORD_CONFIRM").markInvalid();
		      	return;
		      }
		
    	Ext.Ajax.request({
			method : 'POST',
			url:'',
			params:{password:Ext.getCmp("ADD_PASSWORD").getValue(),
			 userId:userId	},
			//成功时回调
			success:function(response,options){				
				//获取响应的json字符串
				var objectJson = Ext.util.JSON.decode(response.responseText);
				
				if(objectJson.success){
				
				   addWindow.hide();
				}
			},
			//失败
			failure:function(response, options){
			  
				Ext.Msg.alert("提示:", "操作失败！", responseJson.status);
			} 
		});
			  
		      }
		  }
		     
   
   Ext.onReady(function(e){
			//路径由你自己的EXT路径决定
			//Ext.BLANK_IMAGE_URL = "../framework/ext2.0/resources/images/default/s.gif";
			Ext.QuickTips.init();
			//TOP显示
			var top = new Ext.Panel({
			//这里可以自己写HTML实现,不需要EXT的代码
			region : 'north',
			height : 60,
			html : '<font size="2.5">当前用户：&nbsp;&nbsp;通过session取到用户ID &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font>'+ new Date().format('Y-m-d g:i:s A')
			   
			});
			var tools = [{
			        id:'gear',
			        handler: function(){
			            Ext.Msg.alert('Message', 'The Settings tool  was  clicked.');
			        }
			    },{
			        id:'close',
			         handler: function(e, target, panel){
			            panel.ownerCt.remove(panel, true);
			        }
			    }];
			    
			    
			  //菜单跳转函数
			  function handlerMenu(item){
					// Ext.Msg.alert("提示","您点击了{0}项",item.text);	
				var tree=Ext.getCmp("menuTree");
				var rootNode = tree.getRootNode();  
				  //------  平台管理	
		        if (item.text=="用户管理"){
		            var url = "frame/admin/operator.jspx";
		        }else if(item.text=="系统菜单管理"){
		            var url = "frame/admin/menu.jspx";
		        }else if(item.text=="角色权限管理"){
		            var url = "frame/admin/role.jspx";
		        }else if(item.text=="角色权限管理"){
		            var url = "frame/admin/role.jspx";
		        }else if(item.text=="应用功能管理"){
		            var url = "frame/admin/applicationModule.jspx";
		        }else if(item.text=="机构员工管理"){
		            var url = "frame/admin/org.jspx";
		        }else if(item.text=="排班管理"){
		            var url = "frame/admin/dutyManage.jspx";
		        }
		        
		        //------  数据采集
		        
		        else if(item.text=="FTP连接配置"){
		            var url = "frame/data/collectFTP.jspx";
		        }else if(item.text=="数据库连接配置"){
		            var url = "frame/data/collectDB.jspx";
		        }else if(item.text=="文件采集任务管理"){
		            var url = "frame/data/fileCollectTaskManage.jspx";
		        }else if(item.text=="数据库采集任务管理"){
		            var url = "frame/data/databaseCollectTaskManage.jspx";
		        }else if(item.text=="采集记录"){
		            var url = "frame/data/collectTaskRecord.jspx";
		        }else if(item.text=="文件管理"){
		            var url = "frame/data/fileManagement.jspx";
		        }else if(item.text=="数据库表采集记录管理"){
		            var url = "";
		        }
		        
		        //----- 天气监测
		        else if(item.text==""){
		            var url = "";
		        }
		        else if(item.text==""){
		            var url = "";
		        }  
		        
		         //----- 预报分析
		        else if(item.text==""){
		            var url = "";
		        }
		        else if(item.text==""){
		            var url = "";
		        }  
		        
		         //----- 预警预报制作
		        else if(item.text==""){
		            var url = "";
		        }
		        else if(item.text==""){
		            var url = "";
		        } 
		         
		           //----- 服务产品制作
		        else if(item.text=="模板管理"){
		            var url = "frame/productprocess/templateManage.jspx";
		        }
		        else if(item.text=="模板属性管理"){
		            var url = "frame/productprocess/templatePropertyManage.jspx";
		        }
		        else if(item.text=="产品分类管理"){
		            var url = "frame/productprocess/productCategoryManage.jspx";
		        }   
		        
		        
		       
		           //----- 预报服务支持
		        else if(item.text=="预报服务流程管理"){
		            var url = "";
		        }
		        else if(item.text=="灾害预警检验结果"){
		            var url = "frame/detection/jl/alertReport.jspx";
		        }  
		         else if(item.text=="短临预报检验结果"){
		            var url = "frame/detection/jl/shortTimeReport.jspx";
		        }
		         else if(item.text=="灾害性天气记录"){
		            var url = "frame/detection/jl/alertFromSourceCheckoutXml.jspx";
		        }
		           //----- 历史资料查询
		        else if(item.text=="历史资料查询"){
		            var url = "frame/data/historyFileLook.jspx";
		        }
		        else if(item.text==""){
		            var url = "";
		        }  
		        
		        
		         
               if(!Ext.isEmpty(url)){               
                Ext.getDom("x-workspace").src=url;
               }
               				
				}  
			   //平台管理菜单 
		      var menu_admin=new Ext.menu.Menu({
					items:[{				//菜单项的集合
						text:"用户管理",		//菜单项的文本，
						//href:"http://jaychaoqun.cnblogs.cn",//指定链接地址
						handler:handlerMenu,
						//hrefTarget:"_blank",		//链接打开的发式，在新窗口打开
						icon:"foundation/images/menu.gif"		//菜单项前面的图标
					},{				//菜单项的集合
						text:"排班管理",		//菜单项的文本，
						//href:"http://jaychaoqun.cnblogs.cn",//指定链接地址
						handler:handlerMenu,
						//hrefTarget:"_blank",		//链接打开的发式，在新窗口打开
						icon:"foundation/images/menu.gif"		//菜单项前面的图标
					},
					{				//菜单项的集合
						text:"系统菜单管理",		//菜单项的文本，
						//href:"http://jaychaoqun.cnblogs.cn",//指定链接地址
						handler:handlerMenu,
						//hrefTarget:"_blank",		//链接打开的发式，在新窗口打开
						icon:"foundation/images/menu.gif"		//菜单项前面的图标
					}
					,
					{				//菜单项的集合
						text:"角色权限管理",		//菜单项的文本，
						//href:"http://jaychaoqun.cnblogs.cn",//指定链接地址
						handler:handlerMenu,
						//hrefTarget:"_blank",		//链接打开的发式，在新窗口打开
						icon:"foundation/images/menu.gif"		//菜单项前面的图标
					}
					,
					{				//菜单项的集合
						text:"应用功能管理",		//菜单项的文本，
						//href:"http://jaychaoqun.cnblogs.cn",//指定链接地址
						handler:handlerMenu,
						//hrefTarget:"_blank",		//链接打开的发式，在新窗口打开
						icon:"foundation/images/menu.gif"		//菜单项前面的图标
					}
					,
					{				//菜单项的集合
						text:"机构员工管理",		//菜单项的文本，
						//href:"http://jaychaoqun.cnblogs.cn",//指定链接地址
						handler:handlerMenu,
						//hrefTarget:"_blank",		//链接打开的发式，在新窗口打开
						icon:"foundation/images/menu.gif"		//菜单项前面的图标
					}
					]
				});
				//数据采集菜单
				 var menu_dataCollect=new Ext.menu.Menu({
					items:[{
						text:"采集任务管理",			//菜单项的文本，
					    icon:"foundation/images/menu.gif",
					    //子菜单
						menu:{items:[{text:"采集连接配置",icon:"foundation/images/menu.gif",menu:{items:[{text:"FTP连接配置",icon:"foundation/images/menu.gif",handler:handlerMenu},{text:"数据库连接配置",icon:"foundation/images/menu.gif",handler:handlerMenu}]}},
						{text:"文件采集任务管理",icon:"foundation/images/menu.gif",handler:handlerMenu},
						{text:"数据库采集任务管理",icon:"foundation/images/menu.gif",handler:handlerMenu},
						{text:"采集记录",icon:"foundation/images/menu.gif",handler:handlerMenu}]}
					},{
						text:"采集数据管理",
						icon:"foundation/images/menu.gif",
						menu:{items:[{text:"文件管理",icon:"foundation/images/menu.gif",handler:handlerMenu},
						{text:"数据库表采集数据管理",icon:"foundation/images/menu.gif",handler:handlerMenu}]}
					}
					
					]
				});
				
				//天气监控菜单
				var menu_weatherMonitor=new Ext.menu.Menu({
					items:[
						{text:"天气监控1",icon:"foundation/images/menu.gif",handler:handlerMenu},
						{text:"天气监控2",icon:"foundation/images/menu.gif",handler:handlerMenu},
						{text:"天气监控3",icon:"foundation/images/menu.gif",handler:handlerMenu}
					
					
					]
				});
				//预报分析菜单
				var menu_AlertAnalyse=new Ext.menu.Menu({
					items:[
						{text:"预报分析1",icon:"foundation/images/menu.gif",handler:handlerMenu},
						{text:"预报分析2",icon:"foundation/images/menu.gif",handler:handlerMenu},
						{text:"预报分析3",icon:"foundation/images/menu.gif",handler:handlerMenu}
					
					
					]
				});	
			//预报预警制作菜单	
			 var menu_AlertMake=new Ext.menu.Menu({
					items:[
						{text:"预报预警制作1",icon:"foundation/images/menu.gif",handler:handlerMenu},
						{text:"预报预警制作2",icon:"foundation/images/menu.gif",handler:handlerMenu},
						{text:"预报预警制作3",icon:"foundation/images/menu.gif",handler:handlerMenu}
					
					
					]
				});	
				//服务产品制作菜单
			  var menu_productMake=new Ext.menu.Menu({
					items:[
						{text:"模板管理",icon:"foundation/images/menu.gif",handler:handlerMenu},
						{text:"模板属性管理",icon:"foundation/images/menu.gif",handler:handlerMenu},
						{text:"产品分类管理",icon:"foundation/images/menu.gif",handler:handlerMenu}
					
					
					]
				});
			  	//预报服务支持菜单
			  var menu_serviceSupport=new Ext.menu.Menu({
					items:[
						{text:"预报服务流程管理",icon:"foundation/images/menu.gif",handler:handlerMenu},
					    {text:"灾害预警检验结果",icon:"foundation/images/menu.gif",handler:handlerMenu},
					    {text:"短临预报检验结果",icon:"foundation/images/menu.gif",handler:handlerMenu},
					    {text:"灾害性天气记录",icon:"foundation/images/menu.gif",handler:handlerMenu}
					]
				});	
				
				
			//历史资料查询	
				var menu_HistoryFileQuery=new Ext.menu.Menu({
					items:[
						{text:"历史资料查询",icon:"foundation/images/menu.gif",handler:handlerMenu}
					
					]
				});
				
				var menu2=new Ext.menu.Menu({
					items:[{
						text:"<b>粗体</b>",
						handler:handlerMenu
					},{
						text:"<i>斜体<i>",
						handler:handlerMenu
					},{
						text:"<u>下划线</u>",
						handler:handlerMenu
					},{
						text:"<font color=red>红色字体</font>",
						handler:handlerMenu
					}]
				});
				
				
				var menu3=new Ext.menu.Menu({
					items:[{
						text:"菜单项1",			//菜单项的文本，
									//子菜单
						menu:{items:[{text:"子菜单1",handler:handlerMenu},{text:"子菜单2",handler:handlerMenu},{text:"子菜单3",handler:handlerMenu}]}
					},{
						text:"菜单项2",
						menu:{items:[{text:"子菜单1"},{text:"子菜单2"},{text:"子菜单3"}]}
					},{
						text:"菜单项3",
						menu:{items:[{text:"子菜单1"},{text:"子菜单2"},{text:"子菜单3"}]}
					}]
				});
				
					
		  var northRegion = new Ext.Panel({
		      region:'north',	       
		      html:"<div id='top'><div id='logo'></div><div id='top_t'> <p class='zi'>欢迎您，"+ userId +"!&nbsp;<a href='javascript:showWindow();'>【修改密码】</a>&nbsp;<a href='#'>【退出系统】</a></p></div></div>",//"<div style='font-size:15pt;'>吉林气象县级综合业务平台    欢迎当前用户：&nbsp;&nbsp;" +userId +"  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日期：&nbsp;&nbsp;"+ time +"</h1>",
			  bbar:[ //{&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;},
			      {text:"&nbsp;&nbsp;"+"平台管理",icon: 'images/main/tu9.png', menu:menu_admin},		//把菜单放在Panel的顶部工具栏
				  {text:"&nbsp;&nbsp;"+"数据采集",icon: 'images/main/tu1.png',menu:menu_dataCollect},
				  {text:"&nbsp;&nbsp;"+"天气监测",icon: 'images/main/tu2.png',menu:menu_weatherMonitor},
				  {text:"&nbsp;&nbsp;"+"预报分析",icon: 'images/main/tu3.png',menu:menu_AlertAnalyse},	  
				  {text:"&nbsp;&nbsp;"+"预警预报制作",icon: 'images/main/tu4.png',menu:menu_AlertMake},	
				  {text:"&nbsp;&nbsp;"+"服务产品制作",icon: 'images/main/tu6.png',menu:menu_productMake},	
				  {text:"&nbsp;&nbsp;"+"预报服务支持",icon: 'images/main/tu7.png',menu:menu_serviceSupport},	
				  {text:"&nbsp;&nbsp;"+"历史资料查询",icon: 'images/main/tu8.png',menu:menu_HistoryFileQuery},					   
				  {xtype:"tbfill"},																			
				  {pressed:false,text:'交班',iconCls: 'icon-edit', handler: function(){showDutyWindow();}},
				  {pressed:false,text:'接班',iconCls: 'icon-delete', handler: function(){showDutyWindow_jieban();}}
			 ]
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
			//  alert (node)
			  if (timeOut=="1"){
			  
			       Ext.MessageBox.show({
			           title: decodeURIComponent("%E5%AE%89%E5%85%A8%E6%8F%90%E7%A4%BA%EF%BC%9A "),
			           msg: decodeURIComponent('%E7%A9%BA%E9%97%B2%E6%97%B6%E9%97%B4%E6%8C%89%E8%B6%85%E8%BF%87%E5%AE%89%E5%85%A8%E6%97%B6%E9%97%B4%EF%BC%8C%E7%B3%BB%E7%BB%9F%E8%87%AA%E5%8A%A8%E8%B7%B3%E8%BD%AC%E5%88%B0%E7%99%BB%E5%BD%95%E9%A1%B5%E9%9D%A2%EF%BC%81 '),
			           buttons: Ext.MessageBox.OK,
			           fn: function(){window.location = basePath+"login.jsp";},
			           icon: Ext.MessageBox.WARNING
			       });
			  
			  }else{
			  
		      var url = node.attributes.attributes.action;
		      
               if(!Ext.isEmpty(url)){               
                Ext.getDom("x-workspace").src=url;
               }
               
               }
           }); 
			   
			   
			//CENTER显示
			var center = new Ext.Panel({
			//这里是除了top之外的部分
		//	tbar : [
			
		/*	{
			text : 'Btn 1',
			iconCls : ''	//这里设置按钮前面的图标
			},'-',{
			text : '<font size="2.5">欢迎当前用户：&nbsp;&nbsp;'+ userId + ' &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日期：&nbsp;&nbsp;'+ time + '</font>' //Ext.get('clock').innerHTML.value
		
			},'->',						
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
			}
			*/
		//	],
			region : 'center',
	 		layout : 'border',
	 	  	hight:85,
			items : [
	/*
			{
			//左边的树
			region : 'west',
			width : 220,
			//layout : 'accordion',
			title : '导航菜单',
			iconCls : 'icon-nav',
	 		collapsible : true,
			split : true,
			items : [menuTree
			
				]
				}, 
			*/	
			
				{
				id : 'content-panel',
				region : 'center',
				border : false,
			 	margins : '2 5 5 0',
				html : "<iframe src='' name='x-workspace' id='x-workspace' width='100%' height='100%' style='border:0' frameborder='0'></iframe>"
			}]
			//items : [left,sub_center]
			});
			
			
			var viewport = new Ext.Viewport({
			layout : 'border',
			items : [northRegion,center]
			});
			});
    
    </script>
  </body>
 </html>
