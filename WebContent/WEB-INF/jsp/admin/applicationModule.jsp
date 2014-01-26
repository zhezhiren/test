<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="/admin/tag/ext.tld" prefix="foundation" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">    
    <title>应用功能管理</title>    
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
  </head>
  
  <body>
  <script type="text/javascript">
    var pageSize=20;
    var currentNode;
    var currentNodeRoot;
    var currentAppName=""; 
    var rootappid="";
    var clickAppId="";
    var optype=""; //0为增加 1为修改
    var dialog=null;
    var addWindow;
    var infoWindow;
    var appWindow;
    var subWindow;
    var funcForm;
    var appcForm;
    var subfuncForm;
    var ISMENUData= [['y','是'],['n','否']];
    var clickFuncCode;
    var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
    var sm2 = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
    var sm3 = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
    //define ac_function table
            var record1=[
				{name : 'appId',type : 'string'}, 
				{name : 'appName',type : 'string'},
				{name : 'appType',type : 'string'}, 
				{name : 'isOpen',type : 'string'},
				{name : 'ipAddr',type : 'string'},
				{name : 'ipPort',type : 'string'},
				{name : 'appDesc',type : 'string'}
				];
				
        var apps = Ext.data.Record.create(record1);
		//定义查询数据的url		
	    var appstore = new Ext.data.Store( {
			proxy:new Ext.data.HttpProxy(new Ext.data.Connection({timeout:0,url:'application/searchApplication.json'})),
			reader : new Ext.data.JsonReader( {
				root : 'data.arr',
				totalProperty : 'data.totalProperty'
			}, apps),

			remoteSort : false
		});
		//带查询条件的数据分页查询
		appstore.on('beforeload', function() {
				appstore.baseParams = {
					APPNAME:Ext.getCmp("search_appName").getValue(),
					APPTYPE:Ext.getCmp("search_appType").getValue()
				
				};
			});
		//定义页面查询表格
		var appCm = new Ext.grid.ColumnModel({ 
       columns:[
       		sm,
			{
			header:'功能编码',
			width:80,
			dataIndex : 'appId'
		}, {
			header : '功能名称',
			width:120,
			dataIndex:'appName'
		}, {
			header : '功能类型',
			width:70,
			dataIndex:'appType'
			//renderer:function(value){return rendererDisplay(<xframe:write dictTypeId='APF_APPTYPE' type='array'/>,value);}
		}, {
			header : '是否开通',
			width:60,
			dataIndex:'isOpen'
		
		},{
			header : 'IP',
			width:80,
			dataIndex:'ipAddr'
		},{
			header : '端口',
			width:60,
			dataIndex:'ipPort'
		}, {
			header : '功能应用描叙',
			width:120,
			dataIndex:'appDesc'
		}],
		defaults: {
        sortable: false,
        menuDisabled: true
    }
		});
		//分页向导UI 
		var pagingBarOrg = new Ext.PagingToolbar({    
	          store: appstore,
	          displayInfo : true,
		      displayMsg : '当前显示第 {0} 条到 {1} 条记录，一共 {2} 条',
		      emptyMsg:'没有数据!'
    });
 
    //define query  
    var conditionOrgForm=new Ext.FormPanel( {
			region : "north",
			buttonAlign:'center',
			height : 80,
			id:'conditionOrgForm',
			collapsible : true,
			frame:true,
			split : true,
			border : false,
			labelAlign : 'left',
			items : [{
			layout : 'column',
			items : [
					{
					
						columnWidth : .25,
						layout : 'form',
						labelWidth:60,
						items : [
								 new Ext.form.TextField( {
									fieldLabel : '应用名称',
									id : 'search_appName',
									width : 120,
									allowBlank : true
								})]
					},
					{
						columnWidth : .25,
						labelWidth:60,
						layout : 'form',
						items : [new Ext.form.ComboBox({
						    fieldLabel:'应用类别',
						    width:120,
						    id:'search_appType',
						    allowBlank : true,
		                    displayField : 'text',
							mode : 'local',							
							triggerAction : 'all',
							editable:false,
							selectOnFocus : false,
							forceSelection : true,
							//valueField : 'value',
							store : new Ext.data.SimpleStore( {
										fields : ['value', 'text'],
										data : [['0','本地'],['1', '远程']]
									})
						   }) 
						 ]//<xframe:dataComboBox fieldLabel="应用类别" width="120" comboId="APPTYPE" dictTypeId="APF_APPTYPE" value="0" />
					},{
						columnWidth : .20,
						labelWidth:60,
						layout : 'form',
						items : [ new Ext.Button({
					    text:'查询',iconCls : 'icon-search',handler:function(){
					 //   alert(Ext.getCmp("search_appType").getValue());
					    loadGrid(appstore);}
					   })]
					}]
		
			}]
		});
		
	 var appGrid_pagingBarOrg = new Ext.PagingToolbar({       
	         store: appstore,
	         displayInfo : true,
			 displayMsg : '当前显示第 {0} 条到 {1} 条记录，一共 {2} 条',
			 emptyMsg:'没有数据!'
    });
    
    //query apps table ui
    var appGrid = new Ext.grid.GridPanel({
        store: appstore,
        cm: appCm,
        border : true,
        tbar:[{text:'增加',iconCls : 'icon-add',handler:onAddAppc},'-',{text:'修改',iconCls : 'icon-edit',handler:onEditAppc},'-',{text:'删除',iconCls : 'icon-delete',handler:onDeleteApp}],
        region : "center",
        loadMask : true,
        sm:sm,
        enableColumnMove: false,
        stripeRows: true,
        iconCls : 'grid-icon',
        bbar: appGrid_pagingBarOrg,
        listeners :{
           rowdblclick:function(grd,rowIndex,ev){
        		onEditAppc();
        	}
        }	
    });
    
		var p_app = new Ext.Panel({
		 layout : 'border',
          border : false,
          title:'应用查询结果：',          
		  split : true,
		  items:[appGrid],//conditionOrgForm,
		  listeners :{
				activate : function(p){
				 p_app.doLayout(true);
				}
			}
		});
		
		
	function onAddAppc(){
		optype="0";
        showappWindow(); 
        appWindow.setTitle("增加功能类");
        appcForm.getForm().reset();
		}
		
    function onSaveAppc() {
	    if(appcForm.getForm().isValid()){
             Ext.Ajax.request({
                url: 'application/insertApplication.json',                
                params: {
	                appName:Ext.getCmp("addAndEdit_appName").getValue(),
					ipAddr:Ext.getCmp("addAndEdit_ipAddr").getValue(),
					ipPort:Ext.getCmp("addAndEdit_ipPort").getValue(),
					appDesc:Ext.getCmp("addAndEdit_appDesc").getValue(),
					appType:Ext.getCmp("addAndEdit_appType").getValue(),
					isOpen:Ext.getCmp("addAndEdit_isOpen").getValue()
				//	appId:'' 页面不需要传ID                             
                },
                method: 'POST',
                success:  function (response, options) {                                  
                    var responseJson =  Ext.util.JSON.decode(response.responseText);
                    Ext.MessageBox.alert('保存成功', '从服务端获取结果: ' + response.responseText);                 
                    if(responseJson.success){                                     
				       appWindow.hide();
                       appstore.reload();
                       apptree.getRootNode().reload();
				    }                
                },
                failure: function (response, options) {
                    Ext.MessageBox.alert('失败', '请求超时或网络故障,错误编号：' + response.status);
                }
            });
          }
       }
   function onSaveOrUpdateAppc(){
	   if(optype=='0'){//增加
	    onSaveAppc();
	   }else if(optype=='1'){//修改
	     onUpdateSaveAppc();
	   }
	   apptree.getRootNode().reload();
	  }
	  
	function onEditAppc() {
		if(!appGrid.getSelectionModel().hasSelection()){
	         Ext.Msg.alert("提示:","请选择要修改的记录!");
	         return;
	    }
			var _APPNAME = appGrid.getSelectionModel().getSelected().get('appName');
		    var _APPTYPE = appGrid.getSelectionModel().getSelected().get('appType');
		    var _ISOPEN  = appGrid.getSelectionModel().getSelected().get('isOpen');
		    var _APPDESC = appGrid.getSelectionModel().getSelected().get('appDesc');
		    var _IPADDR  = appGrid.getSelectionModel().getSelected().get('ipAddr');
		    var _IPPORT  = appGrid.getSelectionModel().getSelected().get('ipPort');
		    var values = {
		    addAndEdit_appName:_APPNAME,
		    addAndEdit_appType:_APPTYPE,
		    addAndEdit_isOpen:_ISOPEN,
		    addAndEdit_appDesc:_APPDESC,
		    addAndEdit_ipAddr:_IPADDR,
		    addAndEdit_ipPort:_IPPORT};
		    optype="1";
	        showappWindow(); 
	        appWindow.setTitle("修改应用");
	        appcForm.getForm().reset();
			appcForm.getForm().setValues(values);
	}
	
	function onUpdateSaveAppc() {
		if(appcForm.getForm().isValid()){               
	         Ext.Ajax.request({
	                url: 'application/updateApplication.json',               
	                params: {
		                appName:Ext.getCmp("addAndEdit_appName").getValue(),
						ipAddr:Ext.getCmp("addAndEdit_ipAddr").getValue(),
						ipPort:Ext.getCmp("addAndEdit_ipPort").getValue(),
						appDesc:Ext.getCmp("addAndEdit_appDesc").getValue(),
						appType:Ext.getCmp("addAndEdit_appType").getValue(),
						isOpen:Ext.getCmp("addAndEdit_isOpen").getValue(),
						appId:appGrid.getSelectionModel().getSelected().get("appId")                              
	                },
	                method: 'POST',
	                success:  function (response, options) {                                  
	                    var responseJson =  Ext.util.JSON.decode(response.responseText);
	                 //       Ext.MessageBox.alert('成功', '从服务端获取结果123: ' + response.responseText);                 
	                    if(responseJson.success){                                     
					        appWindow.hide();				     					        
	                        appstore.reload();
	                        apptree.getRootNode().reload();
					    }                
	                },
	                failure: function (response, options) {
	                    var responseJson =  Ext.util.JSON.decode(response.responseText);
	                    Ext.MessageBox.alert('失败',  responseJson.errors);
	                }
	            });     
           }
	  }
	
	function onDeleteApp() {
		if(!appGrid.getSelectionModel().hasSelection()){
         Ext.Msg.alert("提示:","请选择要删除的记录!");
         return;
        }
    	Ext.Msg.confirm("提示:","确定删除选定的功能数据?",function(btn){
			 if(btn=='yes'){			 
				   var ids = new Array();
				   var selects = appGrid.getSelectionModel().getSelections();
				   for(var i=0;i<selects.length;i++)
				   ids.push(selects[i].get("appId"));				  		   
			Ext.Ajax.request({
                url: 'application/deleteByIds.json',               
                params: {
	                appIds:ids                              
                },
                method: 'POST',
                success:  function (response, options) {                                  
                    var responseJson =  Ext.util.JSON.decode(response.responseText);
                //    Ext.MessageBox.alert('成功', '从服务端获取结果123: ' + response.responseText);                 
                    if(responseJson.success){                                     
				        apptree.getRootNode().reload();
                        appstore.reload();
				    }                
                },
                failure: function (response, options) {
                    var responseJson =  Ext.util.JSON.decode(response.responseText);
                    Ext.MessageBox.alert('失败', responseJson.errors);
                }
             });
			   
			   
			 }
			});
		}
		
		function showappWindow(){
	  	if(appWindow==null){
		appcForm=new Ext.FormPanel( { //定义修改内容的表单
			//region: 'center',
        	labelAlign:'left',
        	buttonAlign:'center',
        	width:510,
        	height:170,
        	bodyStyle:'padding:0 0 0 0',
        	frame:true,
        	labelWidth:55,
        	monitorValid:true, 
			items : [
			  {layout:'column',border:false,labelSeparator:':',
			  defaults:{layout: 'form',border:false,columnWidth:.5, width:30},
			  items : [
					{columnWidth:1.00,items: [{xtype:'textfield',
					fieldLabel: '应用名称',name:'addAndEdit_appName',
					id:'addAndEdit_appName', 
					width:362,allowBlank : false,						
					listeners :{"blur":function(value){
						        	//	验证应用名称的唯一性 APPNameExists();
						            if (optype=='0'){						        	  						        	
						        	     appNameExists();						        							        	     
						        	 }	
						        	
						        	else if(optype=='1'){
							        	if (Ext.getCmp("addAndEdit_appName").getValue()!= appGrid.getSelectionModel().getSelected().get('appName')){
							        	
							        	     appNameExists();
							        	}
							        	 
						        	}						        	
						        	
						        }	
						       } 
					
					
					}]},
					
                	{items:[new Ext.form.ComboBox({
						    fieldLabel:'应用类别',
						    width:120,
						    id:'addAndEdit_appType',
						    allowBlank : true,
		                    displayField : 'text',
							mode : 'local',							
							triggerAction : 'all',
							editable:false,
							selectOnFocus : false,
							forceSelection : true,
							//valueField : 'value',
							store : new Ext.data.SimpleStore( {
										fields : ['value', 'text'],
										data : [['0','本地'],['1', '远程']]
									})
						   }) ]
					},
                	{items:[new Ext.form.ComboBox({
						    fieldLabel:'是否开通',
						    width:120,
						    id:'addAndEdit_isOpen',
						    allowBlank : true,
		                    displayField : 'text',
							mode : 'local',							
							triggerAction : 'all',
							editable:false,
							selectOnFocus : false,
							forceSelection : true,
							//valueField : 'value',
							store : new Ext.data.SimpleStore( {
										fields : ['value', 'text'],
										data : [['y','是'],['n', '否']]
									})
						   })]
					},
					{items: [{xtype:'textfield',fieldLabel: 'IP',name:'addAndEdit_ipAddr',id:'addAndEdit_ipAddr', width:120}]},
					{items: [{xtype:'textfield',fieldLabel: '端口',name:'addAndEdit_ipPort',id:'addAndEdit_ipPort', width:120}]},
					{columnWidth:1.00,items: [{xtype:'textfield',fieldLabel: '应用描叙',name:'addAndEdit_appDesc',id:'addAndEdit_appDesc', width:362}]
                	}
				]
			}]
		});
		
		 appWindow = new Ext.Window( { //定义对话框
						width : 510,
						height : 200,
						layout:'fit',
						shadow : true,
						closeAction : 'hide', //hide表示关闭的时候对话框是隐藏的，并没有真正的销毁，下次要用时在show出来
						modal : true,
						closable : true,
						minWidth : 510,
						minHeight : 200,
						buttons : [{
							text :'保存',
							handler:onSaveOrUpdateAppc
						},{
							text : '关闭',
							handler : function (){appWindow.hide()}
						}],
						items:[appcForm] //将定义的表单加到对话框里
					});
	        }
        appWindow.show(); 
		}
	  function  appNameExists(){     
         Ext.Ajax.request({
                url: 'application/appNameExits.json',               
                params: {
                    appName:Ext.getCmp("addAndEdit_appName").getValue()},
                method: 'POST',
                success:  function (response,options) {                                  
                    var responseJson =  Ext.util.JSON.decode(response.responseText);
                  //  Ext.MessageBox.alert('成功', '从服务端获取结果123: ' + response.responseText);                 
                    if(responseJson.data){                                     
				        Ext.MessageBox.alert('提示', '应用名称: '+Ext.getCmp("addAndEdit_appName").getValue()+' 已经存在，请重新输入一个！ ');
				   }                
                },
                failure: function (response, options) {
                    var responseJson =  Ext.util.JSON.decode(response.responseText);
                    Ext.MessageBox.alert('失败', responseJson.errors);
                }
             }); 		    
        }
    	
		
		
		//主组别修改完成
		
		//渲染功能组查询	
		var recordGroupApp=[
				{name : 'functionGroupId',type : 'string'}, 
				{name : 'functionGroupName',type : 'string'}
				];
				
        var appGroups = Ext.data.Record.create(recordGroupApp);
		//定义查询数据的url		
	    var appGroupstore = new Ext.data.Store( {
			proxy:new Ext.data.HttpProxy(
			     new Ext.data.Connection({
			         timeout:0,url:'application/getFunctionGroupList.json'})),
			reader : new Ext.data.JsonReader( {
				root : 'data.arr',
				totalProperty : 'data.totalProperty'
			}, appGroups),
			remoteSort : false
		});
		//带查询条件的数据分页查询
		appGroupstore.on('beforeload', function() {
				appGroupstore.baseParams = {
				    appId:rootappid,		//主应用id
				    parentId:clickAppId	//父id
					
				};
			});
		//定义页面查询表格
		var appGroupCm = new Ext.grid.ColumnModel({ 
       columns:[sm2,
			{
			header:'功能代码',
			width:80,
			dataIndex : 'functionGroupId'
		}, {
			header : '功能组名称',
			width:150,
			dataIndex:'functionGroupName',
			editor : new Ext.form.TextField({
		     maxLength : 20
	         })
		}],
		defaults: {
        sortable: false,
        menuDisabled: true
    }
		});
		//分页向导UI 
	var appGroupGrid_pagingBarappGroup = new Ext.PagingToolbar({       
        store: appGroupstore,
        displayInfo: true,
        displayMsg : '当前显示第 {0} - {1} 条记录，一共 {2} 条',
	    emptyMsg:'没有数据!'
    });
	 			
     var appGroupGrid = new Ext.grid.EditorGridPanel({
        store: appGroupstore,
        cm: appGroupCm,
        border : false,
        tbar:[{text:'增加',iconCls : 'icon-add',handler:onAddGroup},'-',{text:'修改',iconCls : 'icon-edit',handler:onEditGroup},'-',{text:'删除',iconCls : 'icon-delete',handler:onDeleteGroup}],
        region : "center",
        loadMask : true,
        sm:sm2,
        enableColumnMove: false,
        stripeRows: true,
        iconCls : 'grid-icon',
        title:'功能组查询结果',
        bbar: appGroupGrid_pagingBarappGroup     
    });
    
    function onAddGroup(){
    	    if(addWindow==null){//为了性能，延时加载，如果为空则新创建，不为空的话就直接显示出来
            addForm=new Ext.FormPanel( { //定义增加内容的表单
			height : 100,
			collapsible : false,
			frame:true,
			border : false,
			labelAlign : 'center',
			items : [{
			layout : 'column',
			items : [
					{
						columnWidth : .80,
						layout : 'form',
						labelWidth:80,
						items : [
								 new Ext.form.TextField( {
									fieldLabel : '功能组名称',
									id:'add_funcGroupName',
									width : 110,									
									allowBlank : false,
									
						         listeners :{"blur":function(value){
						        	//	验证功能名称的唯一性 funcNameExists();
						            if (optype=='0'){						        	  						        	
						        	     funcGroupNameExists();						        							        	     
						        	 }	
						        	
						        	else if(optype=='1'){
							        	if (Ext.getCmp("add_funcGroupName").getValue()!= appGroupGrid.getSelectionModel().getSelected().get('functionGroupName')){
							        	
							        	     funcGroupNameExists();
							        	}
							        	 else if (Ext.getCmp("add_funcGroupName").getValue()==appGroupGrid.getSelectionModel().getSelected().get('functionGroupName')){
							        	 //   alert("不验证");
							        	 }
						        	}						        	
						        	
						        }	
						       } 	
						         
						         
							})]
					}
				]
			}]
		});
    
          addWindow = new Ext.Window( { //定义对话框
						width : 300,
						title:'增加功能组',
						height : 150,
						shadow : true,
						closeAction : 'hide', //hide表示关闭的时候对话框是隐藏的，并没有真正的销毁，下次要用时在show出来
						modal : true,
						closable : true,
						minWidth : 500,
						minHeight : 150,
						buttons : [{
							text :'保存',
							handler:onSaveOrUpdateGroup
						},{
							text : '关闭',
							handler : function (){addWindow.hide()}
						}],
						items:[addForm] //将定义的表单加到对话框里
					});
        } 
        addForm.getForm().reset();
        addWindow.show(); 
    
    }
    
    function  funcGroupNameExists(){     
         Ext.Ajax.request({
                url: 'application/funcGroupNameExists.json',               
                params: {
                    funcGroupName:Ext.getCmp("add_funcGroupName").getValue()},
                method: 'POST',
                success:  function (response,options) {                                  
                    var responseJson =  Ext.util.JSON.decode(response.responseText);
                  //  Ext.MessageBox.alert('成功', '从服务端获取结果123: ' + response.responseText);                 
                    if(responseJson.data){                                     
				        Ext.MessageBox.alert('提示', '该功能组名称已经存在，请重新输入一个！ ');
				    }                
                },
                failure: function (response, options) {
                    var responseJson =  Ext.util.JSON.decode(response.responseText);
                    Ext.MessageBox.alert('失败', responseJson.errors);
                }
             }); 		    
        }
    
  function onEditGroup(){
     //没有选择数据时进行提示
     if(!appGroupGrid.getSelectionModel().hasSelection()){
	         Ext.Msg.alert("提示:","请选择要修改的记录!");
	         return;
	    }
	  //选择数据超过两条进行修改时进行提示  
	 if(appGroupGrid.getSelectionModel().getSelections().length>1){
	         Ext.Msg.alert("提示:","请选择一条记录进行修改！");
	         return;
	    }
			var _functionGroupId = appGroupGrid.getSelectionModel().getSelected().get('functionGroupId');
		    var _functionGroupName = appGroupGrid.getSelectionModel().getSelected().get('functionGroupName');
		    
		    var values = {
		        add_funcGroupName:_functionGroupName
		    };
		    optype="1";
	        onAddGroup(); 
	        addWindow.setTitle("修改功能组");
	        addForm.getForm().reset();
			addForm.getForm().setValues(values);
     
     }
     
   function  onSaveOrUpdateGroup(){
       if (optype == 1){
         onUpdateGroup();       
       }
       else {
          onSaveGroup();
       }          
   }
    
    function onDeleteGroup() {
	    if(!appGroupGrid.getSelectionModel().hasSelection()){
	         Ext.Msg.alert("提示:","请选择要删除的记录!");
	         return;
	        }
	     Ext.Msg.confirm("提示:","删除功能组会删除该功能组下的所有子功能组和功能,确定删除?",function(btn){
			  if(btn=='yes'){
				   var ids = new Array();				   
				   var selects = appGroupGrid.getSelectionModel().getSelections();
				   for(var i=0;i<selects.length;i++)
				       ids.push(selects[i].get("functionGroupId"));			  			   
			  Ext.Ajax.request({
	               url: 'application/deleteFunctionGroup.json',               
	               params: {
		                ids:ids                              
	                },
	               method: 'POST',
	               success:  function (response,options) {                                  
	                    var responseJson =  Ext.util.JSON.decode(response.responseText);
	                 //       Ext.MessageBox.alert('成功', '从服务端获取结果: ' + response.responseText);                 
	                    if(responseJson.success){                                     					        
	                        appGroupstore.reload();	
	                        apptree.getRootNode().reload();                      
					    }                
	                },
	               failure: function (response,options) {
	                   
	                    Ext.MessageBox.alert('失败', responseJson.status);
	               }
	           }); 				   				   
			}
		});    
     }
    
    function onSaveGroup(){     
    	if(addForm.getForm().isValid()){    
         Ext.Ajax.request({
                url: 'application/insertFunctionGroup.json',                
                params: {
	                functionGroupName:Ext.getCmp("add_funcGroupName").getValue(),
         		    appId:rootappid
         		//  parentGroup:clickAppId                             
                },
                method: 'POST',
                success:  function (response, options) {                                  
                    var responseJson =  Ext.util.JSON.decode(response.responseText);
                 //       Ext.MessageBox.alert('成功', '从服务端获取结果: ' + response.responseText);                 
                    if(responseJson.success){                                     
				     addWindow.hide();
           			 apptree.getRootNode().reload();
            		 appGroupstore.reload();
				    }                
                },
                failure: function (response, options) {
                    Ext.MessageBox.alert('操作失败', '请求超时或网络故障,错误编号：' + response.status);
                }
           });         
       }
    }
    
    function onUpdateGroup(){ 
          
          Ext.Ajax.request({
                url: 'application/updateFunctionGroup.json',               
                params: {
                    functionGroupName:Ext.getCmp("add_funcGroupName").getValue(),
                    functionGroupId:appGroupGrid.getSelectionModel().getSelected().get('functionGroupId')},
                method: 'POST',
                success:  function (response,options) {                                  
                    var responseJson =  Ext.util.JSON.decode(response.responseText);
                 //   Ext.MessageBox.alert('返回',  response.responseText);                 
                    if(responseJson.success){                                     
				     //   apptree.getRootNode().reload();
				          addWindow.hide();
                        appGroupstore.reload();                        
                      //  currentNode.findChild("id",Groupgrid.get("functionGroupId")).setText(Groupgrid.get("functionGroupName"));
				    }                
                },
                failure: function (response, options) {
                 Ext.MessageBox.alert('操作失败', '请求超时或网络故障,错误编号：' + response.status);
                }
             }); 	
         
       
    } 
    
 	//--define appGroup end-- 
	
	//--定义功能列表渲染元素--
	var recordFuncApp=[
		{name : 'funccode',type : 'string'}, 
		{name : 'funcname',type : 'string'},
		{name : 'funcaction',type : 'string'},
		{name : 'funcdesc',type : 'string'}
		];
				
    var appFuncs = Ext.data.Record.create(recordFuncApp);
		//定义查询数据的url		
	var appFuncStore = new Ext.data.Store( {
			proxy:new Ext.data.HttpProxy(
			new Ext.data.Connection({
			timeout:0,url:'application/getappFuncData.json'})),
			reader : new Ext.data.JsonReader( {
				root : 'data.arr',
				totalProperty : 'data.totalProperty'
			}, appFuncs),
			remoteSort : false,
			pruneModifiedRecords:true
		});
		//带查询条件的数据分页查询
		appFuncStore.on('beforeload', function() {
				appFuncStore.baseParams = {
					funcGroupId:clickAppId
				};
			});
		//定义页面查询表格
		var appFuncCm = new Ext.grid.ColumnModel({ 
       columns:[
       		sm3,
			{
			header:'功能名称',
			width:280,
			dataIndex : 'funcname'
		}, {
			header : '功能编码',
			width:100,
			dataIndex:'funccode'
		},{
			header : '调用入口',
			width:330,
			dataIndex:'funcaction'
		},{
			header : '功能描叙',
			width:280,
			dataIndex:'funcdesc'
		}
		],
		defaults: {
        sortable: false,
        menuDisabled: true
    }
		});
		//分页向导UI 
		var appFuncGrid_pagingBarappFunc = new Ext.PagingToolbar({
       
        store: appFuncStore,
        displayInfo: true,
        displayMsg : '当前显示第 {0} - {1} 条记录，一共 {2} 条',
	    emptyMsg:'没有数据!'
    });
	 			
     var appFuncGrid = new Ext.grid.GridPanel({
        store: appFuncStore,
        cm: appFuncCm,
        border : false,
        tbar:[{text:'增加',iconCls : 'icon-add',handler:onAddFunc},'-',{text:'修改',iconCls : 'icon-edit',handler:onEditFunc},'-',{text:'删除',iconCls : 'icon-delete',handler:onDeleteFunc}],
        region : "center",
     // height:300,
        loadMask : true,
        sm:sm3,
        enableColumnMove: false,
        split : true,  
        stripeRows: true,
        iconCls : 'grid-icon',
        title:'功能列表',
        bbar: appFuncGrid_pagingBarappFunc,
        listeners :{
        	rowclick:function(grd,rowIndex,ev){
          //	clickFuncCode = grd.getSelectionModel().getSelected().get("funccode");
        	// alert(clickFuncCode); appFuncGrid.getSelectionModel().getSelected().get("funccode").getValue()
        //	subFuncGrid.setTitle(grd.getSelectionModel().getSelected().get("funcname")+"--功能附加列表");
             //  loadGrid(SubFunctore);
        	},
        	rowdblclick:function(grd,rowIndex,ev){
        		onEditFunc();
        	}
        }
    });
	
	function onAddFunc() {
	    optype="0";
        showWindow(); 
        infoWindow.setTitle("增加功能");
        funcForm.getForm().reset();
	
	}
	
	function onSaveFunc() {
	    if(funcForm.getForm().isValid()){
            Ext.Ajax.request({
                url: 'application/insertFuncData.json',               
                params: {
	                
			         funcName:Ext.getCmp("addAndEdit_funcName").getValue(),
			         funcAction:Ext.getCmp("addAndEdit_funcAction").getValue(),
			         funcDesc:Ext.getCmp("addAndEdit_funcDesc").getValue(),
			         funcGroupId:clickAppId                             
                },
                method: 'POST',
                success:  function (response, options) {                                  
                    var responseJson =  Ext.util.JSON.decode(response.responseText);
               //     Ext.MessageBox.alert('成功', '从服务端获取结果123: ' + response.responseText);                 
                    if(responseJson.success){                                     
				         infoWindow.hide();
                         appFuncStore.reload();
				    }                
                },
                failure: function (response, options) {
                    Ext.MessageBox.alert('操作失败', '请求超时或网络故障,错误编号：' + response.status);
                }
             });          
         }	
	 }
	
	function onSaveOrUpdateFunc(){
	   if(optype=='0'){//增加
	      onSaveFunc();
	   }else if(optype=='1'){//修改
	       onUpdateSaveFunc();
	   }
	}
	
	function onEditFunc() {
	if(!appFuncGrid.getSelectionModel().hasSelection()){
         Ext.Msg.alert("提示:","请选择要修改的记录!");
         return;
        }
		var FUNCNAME = appFuncGrid.getSelectionModel().getSelected().get('funcname');
	    var FUNCACTION = appFuncGrid.getSelectionModel().getSelected().get('funcaction');
	    var FUNCDESC   = appFuncGrid.getSelectionModel().getSelected().get('funcdesc');
	    var values = {
		    addAndEdit_funcName:FUNCNAME,
		    addAndEdit_funcAction:FUNCACTION,
		    addAndEdit_funcDesc:FUNCDESC};
		    optype="1";
	        showWindow(); 
	        infoWindow.setTitle("修改功能");
	        funcForm.getForm().reset();
			funcForm.getForm().setValues(values);
	}
	
  function onUpdateSaveFunc() {
	  if(funcForm.getForm().isValid()){       
          Ext.Ajax.request({
                url: 'application/updateFuncData.json',               
                params: {	                
			         funcName:Ext.getCmp("addAndEdit_funcName").getValue(),
			         funcAction:Ext.getCmp("addAndEdit_funcAction").getValue(),
			         funcDesc:Ext.getCmp("addAndEdit_funcDesc").getValue(),
			         funcCode:appFuncGrid.getSelectionModel().getSelected().get("funccode")			                                    
                },
                method: 'POST',
                success:  function (response, options) {                                  
                    var responseJson =  Ext.util.JSON.decode(response.responseText);
                 //   Ext.MessageBox.alert('成功', '从服务端获取结果: ' + response.responseText);                 
                    if(responseJson.success){                                     
				         infoWindow.hide();
                         appFuncStore.reload();
				    }                
                },
                failure: function (response, options) {
                    Ext.MessageBox.alert('操作失败', '请求超时或网络故障,错误编号：' + response.status);
                }
             });           
        }
	}
	
	function showWindow(){
	   if(infoWindow==null){	      
		    funcForm=new Ext.FormPanel( { //定义修改内容的表单
			//region: 'center',
        	labelAlign:'left',
        	buttonAlign:'center',
        	width:510,
        	height:170,
        	bodyStyle:'padding:0 0 0 0',
        	frame:true,
        	labelWidth:55,
        	monitorValid:true, 
			items : [
			  {layout:'column',border:false,labelSeparator:':',
			  defaults:{layout: 'form',border:false,columnWidth:.5, width:30},
			  items : [
					{columnWidth:1.00,items: [{xtype:'textfield',
					fieldLabel: '功能名称',
					name:'addAndEdit_funcName',
					id:'addAndEdit_funcName', 
					width:355,
					allowBlank : false,
					listeners :{"blur":function(value){
						        	//	验证功能名称的唯一性 funcNameExists();
						          if (optype=='0'){						        	  						        	
						        	     funcNameExists();						        							        	     
						        	}	
						        	
						        	else if(optype=='1'){
							        	if (Ext.getCmp("addAndEdit_funcName").getValue()!= appFuncGrid.getSelectionModel().getSelected().get('funcname')){
							        	
							        	     funcNameExists();
							        	}
							        	 else if (Ext.getCmp("addAndEdit_funcName").getValue()==appFuncGrid.getSelectionModel().getSelected().get('funcname')){
							        	 //   alert("不验证");
							        	 }
						        	}						        	
						        	
						        }	
						       } 					
					}]},					
                	{columnWidth:1.00,items: [{xtype:'textfield',fieldLabel: '调用入口',name:'addAndEdit_funcAction',id:'addAndEdit_funcAction', width:355,allowBlank : true}]
                	},
					{columnWidth:1.00,items: [{xtype:'textfield',fieldLabel: '功能描叙',name:'addAndEdit_funcDesc',id:'addAndEdit_funcDesc', width:355}]
                	}
				]
			}]
		});
		
		    infoWindow = new Ext.Window( { //定义对话框
						width : 510,
						//title:'修改功能',
						height : 190,
						layout:'fit',
						shadow : true,
						closeAction : 'hide', //hide表示关闭的时候对话框是隐藏的，并没有真正的销毁，下次要用时在show出来
						modal : true,
						closable : true,
						minWidth : 510,
						minHeight : 190,
						buttons : [{
							text :'保存',
							handler:onSaveOrUpdateFunc
						},{
							text : '关闭',
							handler : function (){infoWindow.hide()}
						}],
						items:[funcForm] //将定义的表单加到对话框里
					});
	        }
        infoWindow.show(); 
	}
	
	
	function  funcNameExists(){     
         Ext.Ajax.request({
                url: 'application/funcNameExists.json',               
                params: {
                    funcName:Ext.getCmp("addAndEdit_funcName").getValue()},
                method: 'POST',
                success:  function (response,options) {                                  
                    var responseJson =  Ext.util.JSON.decode(response.responseText);
                  //  Ext.MessageBox.alert('成功', '从服务端获取结果123: ' + response.responseText);                 
                    if(responseJson.data){                                     
				       Ext.MessageBox.alert('提示', '该功能名称已经存在，请重新输入一个！ ');
				    }                
                },
                failure: function (response, options) {
                   Ext.MessageBox.alert('操作失败', '请求超时或网络故障,错误编号：' + response.status);
                }
             }); 		    
        }

	function onDeleteFunc() {
	    if(!appFuncGrid.getSelectionModel().hasSelection()){
         Ext.Msg.alert("提示:","请选择要删除的记录!");
         return;
        }
    Ext.Msg.confirm("提示:","确定删除选定的功能数据?",function(btn){
			 if(btn=='yes'){
			   var ids = new Array();
			   var selects = appFuncGrid.getSelectionModel().getSelections();
			   for(var i=0;i<selects.length;i++)
			   ids.push(selects[i].get("funccode"));			   
			   	Ext.Ajax.request({
                url: 'application/deleteFuncData.json',               
                params: {
	                ids:ids                              
                },
                method: 'POST',
                success:  function (response, options) {                                  
                    var responseJson =  Ext.util.JSON.decode(response.responseText);
               //     Ext.MessageBox.alert('成功', '从服务端获取结果123: ' + response.responseText);                 
                    if(responseJson.success){                                     
				       appFuncStore.reload();
				    }                
                },
                failure: function (response, options) {
                   Ext.MessageBox.alert('操作失败', '请求超时或网络故障,错误编号：' + response.status);
                }
             });
			 }
			});
	
	}
	//--定义功能列表渲染完成--		
	
	//--定义功能方法和数据的授权数据--
	
	var recordSubFunc=[
		{name : 'SUBFUNCID',type : 'string'}, 
		{name : 'SUBTYPE',type : 'string'},
		{name : 'SUBEXPRESSION',type : 'string'},
		{name : 'SUBDESC',type : 'string'}
		];
				
        var SubFuncs = Ext.data.Record.create(recordSubFunc);
		//定义查询数据的url		
	    var SubFunctore = new Ext.data.Store( {
			proxy:new Ext.data.HttpProxy(new Ext.data.Connection({timeout:0,url:''})),
			reader : new Ext.data.JsonReader( {
				root : 'data',
				totalProperty : 'totalProperty'
			}, SubFuncs),
			remoteSort : false
		});
		//带查询条件的数据分页查询
		SubFunctore.on('beforeload', function() {
				SubFunctore.baseParams = {
					funccode:clickFuncCode
				};
			});
		//定义页面查询表格
		var subFuncCm = new Ext.grid.ColumnModel({ 
       columns:[
       		new Ext.grid.CheckboxSelectionModel({singleSelect:false}),
			{
			header:'编号',
			width:60,
			dataIndex : 'SUBFUNCID'
		}, {
			header : '类型',
			width:90,
			dataIndex:'SUBTYPE'
		//	renderer:function(value){return rendererDisplay(<xframe:write dictTypeId='FUNC_TYPE' type='array'/>,value);}
		},{
			header : '默认表达式',
			width:200,
			dataIndex:'SUBEXPRESSION'
		},{
			header : '功能描叙',
			width:200,
			dataIndex:'SUBDESC'
		}
		],
		defaults: {
        sortable: false,
        menuDisabled: true
    }
		});
		//分页向导UI 
	var pagingBarappsubFunc = new Ext.PagingToolbar({      
        store: SubFunctore,
        displayInfo: true,
        displayMsg : '当前显示第 {0} - {1} 条记录，一共 {2} 条',
	    emptyMsg:'没有数据!'
    });
	 			
     var subFuncGrid = new Ext.grid.GridPanel({
        store: SubFunctore,
        cm: subFuncCm,
        border : false,
        region : 'center',
        split : true,
     //   tbar:[{text:'增加',iconCls : 'icon-add',handler:onAddsubFunc},'-',{text:'修改',iconCls : 'icon-edit',handler:onEditsubFunc},'-',{text:'删除',iconCls : 'icon-delete',handler:onDeletesubFunc}],
        region : "center",
        loadMask : true,
        sm:new Ext.grid.CheckboxSelectionModel({singleSelect:false}),
        enableColumnMove: false,
        stripeRows: true,
        iconCls : 'grid-icon',
        title:'功能附加列表',
        bbar: pagingBarappsubFunc,
        listeners :{
           rowdblclick:function(grd,rowIndex,ev){
        		onEditsubFunc();
        	}
        }	
        
    });
	
	function onAddsubFunc() {
		if (clickFuncCode == '' || clickFuncCode == null) {
			Ext.Msg.alert("提示:","请先选择功能列表中功能项!");
			return;
		}
	    optype="0";
        subshowWindow(); 
        subWindow.setTitle("增加附属属性");
        subfuncForm.getForm().reset();
	
	}
	
	function onSavesubFunc() {
	    if(subfuncForm.getForm().isValid()){
         var jsonObj = {FUNCCODE:clickFuncCode,SUBTYPE:Ext.getCmp("SUBTYPE").getValue(),SUBEXPRESSION:Ext.getCmp("SUBEXPRESSION").getValue(),SUBDESC:Ext.getCmp("SUBDESC").getValue()};
         pageCall.invokeNamedSqlForInsertWithId("rights.insertsubFuncData",{SUBFUNCID:'ac_subfunctiondata.subfuncid'},jsonObj,function(returnId){
            subWindow.hide();
            loadGrid(SubFunctore);
         });
       }
	
	}
	
	function onSaveOrUpdatesubFunc(){
	   if(optype=='0'){//增加
	    onSavesubFunc();
	   }else if(optype=='1'){//修改
	     onUpdateSavesubFunc();
	   }
	}
	
	function onEditsubFunc() {
	if(!subFuncGrid.getSelectionModel().hasSelection()){
         Ext.Msg.alert("提示:","请选择要修改的记录!");
         return;
        }
        var SUBFUNCID = subFuncGrid.getSelectionModel().getSelected().get('SUBFUNCID');
		var SUBTYPE = subFuncGrid.getSelectionModel().getSelected().get('SUBTYPE');
	    var SUBEXPRESSION = subFuncGrid.getSelectionModel().getSelected().get('SUBEXPRESSION');
	    var SUBDESC   = subFuncGrid.getSelectionModel().getSelected().get('SUBDESC');
	    var values = {SUBFUNCID:SUBFUNCID,SUBTYPE:SUBTYPE,SUBEXPRESSION:SUBEXPRESSION,SUBDESC:SUBDESC};
	    optype="1";
        subshowWindow(); 
       	subWindow.setTitle("修改附属属性");
        subfuncForm.getForm().reset();
		subfuncForm.getForm().setValues(values);
	}
	
	function onUpdateSavesubFunc() {
	if(subfuncForm.getForm().isValid()){
         var jsonObj = {SUBFUNCID:subFuncGrid.getSelectionModel().getSelected().get('SUBFUNCID'),SUBTYPE:Ext.getCmp("SUBTYPE").getValue(),SUBEXPRESSION:Ext.getCmp("SUBEXPRESSION").getValue(),SUBDESC:Ext.getCmp("SUBDESC").getValue()};
         pageCall.invokeNamedSqlForUpdate("rights.updatesubFuncData",jsonObj,function(returnId){if(returnId==1) subWindow.hide();loadGrid(SubFunctore);});
       }
	}
	
	function subshowWindow(){
	  if(subWindow==null){
		subfuncForm=new Ext.FormPanel( { //定义修改内容的表单
			//region: 'center',
        	labelAlign:'left',
        	buttonAlign:'center',
        	width:510,
        	height:170,
        	bodyStyle:'padding:0 0 0 0',
        	frame:true,
        	labelWidth:55,
        	monitorValid:true, 
			items : [
			  {layout:'column',border:false,labelSeparator:':',
			  defaults:{layout: 'form',border:false,columnWidth:.5, width:30},
			  items : [
					{items:[]//<xframe:dataComboBox fieldLabel="属性类型" width="120" comboId="SUBTYPE" dictTypeId="FUNC_TYPE" value="0" />
					},
                	{columnWidth:1.00,items: [{xtype:'textfield',fieldLabel: '表达式',name:'SUBEXPRESSION',id:'SUBEXPRESSION', width:355}]
                	},
					{columnWidth:1.00,items: [{xtype:'textfield',fieldLabel: '属性描叙',name:'SUBDESC',id:'SUBDESC', width:355}]
                	}
				]
			}]
		});
		
		    subWindow = new Ext.Window( { //定义对话框
						width : 510,
						//title:'修改功能',
						height : 190,
						layout:'fit',
						shadow : true,
						closeAction : 'hide', //hide表示关闭的时候对话框是隐藏的，并没有真正的销毁，下次要用时在show出来
						modal : true,
						closable : true,
						minWidth : 510,
						minHeight : 190,
						buttons : [{
							text :'保存',
							handler:onSaveOrUpdatesubFunc
						},{
							text : '关闭',
							handler : function (){subWindow.hide()}
						}],
						items:[subfuncForm] //将定义的表单加到对话框里
					});
	        }
        subWindow.show(); 
	}

	function onDeletesubFunc() {
	    if(!subFuncGrid.getSelectionModel().hasSelection()){
         Ext.Msg.alert("提示:","请选择要删除的记录!");
         return;
        }
    Ext.Msg.confirm("提示:","确定删除选定的功能数据?",function(btn){
			 if(btn=='yes'){
			   var arr = new Array();
			   var selects = subFuncGrid.getSelectionModel().getSelections();
			   for(var i=0;i<selects.length;i++)
			   arr.push({SUBFUNCID:selects[i].get("SUBFUNCID")});
			   pageCall.invokeNamedSqlForBatchDelete("rights.deletesubFuncData",arr,
			   function(a){
                     SubFunctore.reload();
			   });
			 }
			});
	
	}
	
	//--定义功能方法和数据的授权数据完成
	
	//--定义功能组别的布局面板
	var groupPanel = new Ext.Panel({
	  layout : 'border',
      border : false,
      title:'功能组查询',
	  split : true,
	  items:[appGroupGrid],
	  listeners :{
	  activate : function(p){
				 groupPanel.doLayout(true);
				}						
			}
	  });
	//--定义功能列表的布局面板
	var funcPanel = new Ext.Panel({
	  layout : 'border',
      border : false,
      title:'功能列表',
	  items:[appFuncGrid],//,subFuncGrid
	  listeners :{
	  activate : function(p){
				 funcPanel.doLayout(true);
				}		
			}
	  });	
	
		var TabPanel = new Ext.TabPanel({
	           region : 'center',
	           activeTab: 0,
	           border : true,
			   items:[groupPanel,funcPanel]
			});	
		
	   var cardPanel = new Ext.Panel({
	       region : 'center',
	       layout : 'card',
		   activeItem : 0,
	       border : false,
	       margins:"3 0 0 0",
		   items:[p_app,TabPanel] //便于激活其中一个
		});
	//--整体布局完成--		
			
			//生成一级树	
        var apptree = <foundation:extTree
            treeId="apptree" 
            dataUrl="application/getApplicationTree.json" 
            rootId="appTreeRoot"
            border="true" 
            iconCls="group-icon" 
            region="west" 
            width="210" 
            split="true" 
            title="应用功能管理" 
            rootText="应用功能管理" 
            rootVisible = "true" 
            margins="3 0 0 0" 
            baseParams="{ appId:Ext.getCmp('apptree').currentNodeId}"
            />;
            
          apptree.on("click",
            function(node,ev){
            currentNode = node;
            rootappid = "";
          
            clickAppId = node.id;      //当前ID 一级栏目id = id+'r'
            if (clickAppId.substr(clickAppId.length-1,1) == 'r') {
            	clickAppId = clickAppId.substr(0,clickAppId.length-1);
            }
            currentAppName = node.text;
            depth = node.getDepth();
            if (depth == 0 ) {
            	cardPanel.layout.setActiveItem(p_app);
            	loadGrid(appstore);
            }else if (depth >= 1){
            	//加载功能组列表数据
            	if (depth == 1 ) {
            		rootappid = node.id;
            		currentNodeRoot = node;	
            		rootappid = rootappid.substr(0,rootappid.length-1);
            		clickAppId = "";
            		cardPanel.layout.setActiveItem(TabPanel);
            		TabPanel.unhideTabStripItem(groupPanel);
            		TabPanel.hideTabStripItem(funcPanel);
            		TabPanel.setActiveTab(groupPanel);
            		loadGrid(appGroupstore);
            		  
            		}
            	if (depth >= 2 ) {
            	 	rootappid  = node.attributes.attributes.appid;
            	 	clickAppid = node.attributes.attributes.parentid;
            	 	cardPanel.layout.setActiveItem(TabPanel);
            	 	TabPanel.unhideTabStripItem(funcPanel);
            	 	TabPanel.hideTabStripItem(groupPanel);
            	 	TabPanel.setActiveTab(funcPanel);
            		loadGrid(appGroupstore);
            	 	loadGrid(appFuncStore);           	
            	 	 
            	 	}
            }
            });
             
           
    var innerPanel=new Ext.Panel({
          layout : 'border',
          border : false,
		  split : true,
		  title:'应用功能管理',
		  iconCls : 'icon-plugin',
		  items:[apptree,cardPanel]
    });
    
    var bigTabPanel = new Ext.TabPanel({
               region : 'center',
		        activeTab: 0,
		        border : false,
		        frame:false,
		        items:[innerPanel]
    });
    
    function loadGrid(store){
      store.load( {
					params : {
					
					}
				});
    }
	
    Ext.onReady(function(){
      var viewport = new Ext.Viewport({
			layout : 'border',
			split : true,
			items:[bigTabPanel]
			});
	 apptree.getRootNode().expand(false, false);	
	 loadGrid(appstore);	
    });
    
    </script>
  </body>
</html>
