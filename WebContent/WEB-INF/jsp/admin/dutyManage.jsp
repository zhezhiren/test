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
    <title>排班管理</title>	
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
	<link rel="stylesheet" type="text/css" href="css/Spinner.css"/>
	<script type="text/javaScript" src="js/ext-3.4.0/adapter/ext/ext-base.js"></script>
	<script type="text/javaScript" src="js/ext-3.4.0/ext-all.js"></script> 
	<script type="text/javascript" src="js/ext-3.4.0/ext-lang-zh_CN.js" charset="utf-8" ></script>
    <script type="text/javaScript" src="js/ext-3.4.0/ux/TreeCheckNodeUI.js"></script>
    <script type="text/javaScript" src="js/ext-3.4.0/ux/DatetimePicker.js"></script>
	<script type="text/javascript" src="js/ext-3.4.0/ux/Spinner.js"> </script>
	<script type="text/javascript" src="js/ext-3.4.0/ux/SpinnerField.js"></script> 
  </head> 
  <body>
   <script type="text/javascript">
    var appData;
    var addWindow=null;
    var infoForm=null;
    var addDutyWindow=null;
    var infoDutyForm=null;
    var infoDutyForm_edit=null;
	var roleWindow,roleForm;
	var currentRoleId;
	var currentFuncId;
	var appNameData=[];//用于存放应用名称
	var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false}); //复选框
    var sms = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
    var opSelectWindow=null;
    var optpye;
   
   
   
   var recordop=[
		{name : 'userId',type : 'string'}, 
		{name : 'operatorName',type : 'string'},
		{name : 'status',type : 'string'}, 
		{name : 'lastLogin',type : 'string'},
		{name : 'email',type : 'string'},
		{name : 'operatorId',type : 'string'}
		];
				
	var recordHeads = Ext.data.Record.create(recordop);
	//定义查询数据的url
    var opstore = new Ext.data.Store( {
		proxy:new Ext.data.HttpProxy(new Ext.data.Connection({timeout:0,url:'duty/getUserList.json'})),
		reader : new Ext.data.JsonReader( {
			root : 'data.arr',
			totalProperty : 'data.totalProperty'
		}, recordHeads),

		remoteSort : false
	});
	
	//定义页面查询表格
	var opcm = new Ext.grid.ColumnModel({
	
       columns:[
       		sms,
			{
			header:'登录名',
			width:120,
			hidden:true,
			dataIndex : 'userId'
		}, {
			header : '姓名',
			width:120,
			dataIndex:'operatorName'
		}, {
			header : '状态',
			hidden:true,
			dataIndex:'operatorId',
			width:80
		}, {
			header : '邮箱',
			width:160,
			hidden:true,
			dataIndex:'email'
		}],
	    defaults: {
        sortable: false,
        menuDisabled: true
    }

	});
    	
  var pagingBar = new Ext.PagingToolbar({        
        store: opstore,
        displayInfo: true,
        displayMsg : '当前显示第 {0} - {1} 条记录，一共 {2} 条',
	    emptyMsg:'没有数据!'
    });
    
    opstore.on('beforeload', function() { 
				opstore.baseParams = {
					
				    operatorName:Ext.getCmp("search_operatorName").getValue()
				};
			});
    
	 			
     var opGrid = new Ext.grid.GridPanel({
        store: opstore,
        cm: opcm,
        border : true,
        region : "center",
        loadMask : true,
        split : true,
        enableColumnMove: false,
        sm:sms,
        stripeRows: true,
        iconCls : 'grid-icon',
       // title:'用户查询结果:' ,
        bbar: pagingBar,
        listeners :{ 
           celldblclick  : function(thisGrid, rowIndex, columnIndex, ev ){
              onOpSelect();
            }
         }
    });
    
    //define query  
    var opSearchForm=new Ext.FormPanel( {
			buttonAlign:'center',
			id:'opSearchForm',
			frame:true,
			height : 65,
			split : true,
			margins:'3 0 0 0',
			collapsible : false,
			region : "north",
			title:'查询条件:',
			border : true,
			bodyBorder:false,
			labelAlign : 'left',
			items : [{
			layout : 'column',
			items : [
					
					{
						columnWidth : .4,
						labelWidth:50,
						layout : 'form',
						items : [ new Ext.form.TextField( {
									fieldLabel : '姓名',
									id : 'search_operatorName',
									width : 120,
									allowBlank : true
								})]
					},{
						columnWidth : .2,
						labelWidth:60,
						layout : 'form',
						items : [ new Ext.Button({text:'查询',iconCls : 'icon-search',handler:function(){opstore.load();}})]
					}]
		
			}]
		});
		
		
		 //显示区域列表
	function showOpWindow(){
	 if(opSelectWindow==null){
	      opstore.load( {
				params : {
					start : 0,
					limit : 20
				}
			});
	    opSelectWindow = new Ext.Window( { //定义对话框
						width : 500,
						height : 500,
						shadow : true,
						layout:'border',
						title:'用户查询',
						closeAction : 'hide', 
						modal : true,
						closable : true,
						minWidth : 500,
						minHeight : 500,
						buttons : [{
							text :'选择',
							iconCls : 'icon-tick',
							handler:onOpSelect
						},{
							text :'清空',
							iconCls : 'icon-rss_delete',
							handler:onOpClear
						},{
							text : '关闭',
							handler : function (){opSelectWindow.hide()}
						}],
						items:[opGrid,opSearchForm] //将定义的表单加到对话框里
					});
	  }
	  opSelectWindow.show();
	}
   
             //用户名选择按钮
			function onOpSelect(){
			if(!opGrid.getSelectionModel().hasSelection()){
		         Ext.Msg.alert("提示:","请选择姓名!");
		         return;
		        }
			 selectOpObj = opGrid.getSelectionModel().getSelected();
			 if(optype=="0"){
    		 Ext.getCmp('addAndEdit_empName').setValue(selectOpObj.get("operatorName"));
 	 		 Ext.getCmp('addAndEdit_userId').setValue(selectOpObj.get("operatorId"));
 	 		 }
 	 		  if(optype=="1"){
    		 Ext.getCmp('addAndEdit_empName_leader').setValue(selectOpObj.get("operatorName"));
 	 		 Ext.getCmp('addAndEdit_userId_leader').setValue(selectOpObj.get("operatorId"));
 	 		 }
 	 		 if(optype=="3"){
    		 Ext.getCmp('Edit_empName').setValue(selectOpObj.get("operatorName"));
 	 		 Ext.getCmp('Edit_userId').setValue(selectOpObj.get("operatorId"));
 	 		 }
 	 		  if(optype=="4"){
    		 Ext.getCmp('Edit_empName_leader').setValue(selectOpObj.get("operatorName"));
 	 		 Ext.getCmp('Edit_userId_leader').setValue(selectOpObj.get("operatorId"));
 	 		 }
 	 		 
 	 		 
             opSelectWindow.hide();
			}
			
			
			//用户名清空按钮
			function onOpClear(){
       			Ext.getCmp('addAndEdit_empName').setValue("");
		  	    Ext.getCmp('addAndEdit_userId').setValue("");
			 	selectOpObj = null;
              	opSelectWindow.hide();
			}
   
   

    
    infoDutyForm =  new Ext.FormPanel({
	        labelWidth: 65,
	        border:true,
	        region : "west",
	        frame:true,
	        width:390,
	        split : true,
	        title:'排班信息',
	        margins:'3 0 0 0',
	        buttonAlign:'left',
	        items: {
	            xtype:'panel',
	            defaults:{autoHeight:true, bodyStyle:'padding:5px'}, 
	            items:[{
	                layout:'form',
	             //   defaults: {width: 230},
	            //    defaultType: 'textfield',
	
	                items: [
	                
	                
	                 {
		                    fieldLabel: '业务班',  
						    xtype:"panel",  
						    layout:"column",  
						    isFormField:true,
						    id:"name",
		                    items: [
							     new Ext.form.TextField( {
								id : 'addAndEdit_userId',
								emptyText :"请选择",
								width : 0,
								hidden:true,
								readOnly:true,
								allowBlank : true
							}),new Ext.form.TextField( {
								id : 'addAndEdit_empName',
								emptyText :"请选择业务员",
								width : 150,
								readOnly:true,
								allowBlank : false
							}),new Ext.Button({text:'请选择',
						                    id:'dutyButton2',
						                    width : 80,
										//	iconCls : 'icon-search',
											handler:function(){	
											    optype="0";
											 	showOpWindow();			
						                      
						                    }
						               })
							    
			         ]
                },
	                
	                
	                {
		                    fieldLabel: '带班领导',  
						    xtype:"panel",  
						    layout:"column",  
						    isFormField:true,
						    id:"name_leader",
		                    items: [
							     new Ext.form.TextField( {
								id : 'addAndEdit_userId_leader',
								emptyText :"请选择",
								width : 0,
								hidden:true,
								readOnly:true,
								allowBlank : true
							}),new Ext.form.TextField( {
								id : 'addAndEdit_empName_leader',
								emptyText :"请选择带班人",
								width : 150,
								readOnly:true,
								allowBlank : false
							}),new Ext.Button({text:'请选择',
						                   
						                    width : 80,
										//	iconCls : 'icon-search',
											handler:function(){
											     optype="1";	
											 	showOpWindow();			
						                      
						                    }
						               })
							    
			         ]
                },
	                
	                new Ext.form.DateField({  
							            
					            fieldLabel: '排班日期',
					            id: 'addAndEdit_endTime',
					            editable:false, //不允许对日期进行编辑  
					            width:230,  
					            format:"Y-m-d",
					            allowBlank : false, 
					            listeners : {  //主要这里添加监听事件     
								    select : function(f, date) {          
								     //     Ext.getCmp("addAndEdit_startTime").setValue( Ext.getCmp("addAndEdit_startTime").getValue()); 
								    
								               var dt = Ext.getCmp('addAndEdit_endTime').getValue();									
								    //          dt.setDate(dt.getDate()+1);
								    //          alert(dt);
								                      }     }, 
					            emptyText:"请选择日期..."  
							        }) 
	                
	                 ,new Ext.Button({text:'排班',
						                    id:'dutyButton',
						                    width : 85,
										//	iconCls : 'icon-search',
											handler:function(){	
											 	addDuty();			
						                      
						                    }
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
	    
	 function addDuty(){ 
	//   alert(Ext.getCmp("addAndEdit_userId").getValue());
	    if(infoDutyForm.getForm().isValid()){          
            Ext.Ajax.request({
                url: 'duty/addDutyPlan.json',               
                params: {
                        dutyLeaderId:Ext.getCmp("addAndEdit_userId_leader").getValue(),                
                        dutyLeaderName:Ext.getCmp("addAndEdit_empName_leader").getValue(),
                        
                        dutyOperatorId:Ext.getCmp("addAndEdit_userId").getValue(),                
                        dutyOperatorName:Ext.getCmp("addAndEdit_empName").getValue(),
						endTime:Ext.getCmp("addAndEdit_endTime").getValue()//.format('Y-m-d H-i-s')
					//	endTime:Ext.getCmp("addAndEdit_endTime").getValue(),//.format('Y-m-d H-i-s')
					//	jobContent:Ext.getCmp("addAndEdit_jobContent").getValue()                
                },
                method: 'POST',
                success:  function (response, options) {                                  
                    var responseJson =  Ext.util.JSON.decode(response.responseText);                                                                                                  		
				      
				    //   Ext.MessageBox.alert('成功', '从服务端获取结果: '+response.responseText ); 
				      if (responseJson.success){
				          Ext.MessageBox.alert('成功', '排班成功！');
				          
				            var dt = Ext.getCmp('addAndEdit_endTime').getValue();									
							//	 dt.setDate(dt.getDate()+1);
							Ext.getCmp("addAndEdit_userId_leader").setValue("");               
	                        Ext.getCmp("addAndEdit_empName_leader").setValue("");
	                        
	                        Ext.getCmp("addAndEdit_userId").setValue("");                
	                        Ext.getCmp("addAndEdit_empName").setValue("");
	                     //  infoDutyForm.getForm().reset();
						//	alert(dt.setDate(dt.getDate()+1)); 
		               //      Ext.getCmp('addAndEdit_endTime').setValue(dt.setDate(dt.getDate()+1));
				        // dt.setDate(dt.getDate()+1);
				         Ext.getCmp('addAndEdit_endTime').setValue(dt.getDate()+1);
				         dataStore_duty.load();
				      }else {
				      
				         Ext.MessageBox.alert('失败', '排班失败！');
				      }
				       
				  			                         
                },
                failure: function (response, options) {
                  
						 Ext.MessageBox.alert('失败', '请求超时或网络故障,错误编号：' + response.status);
                }
            });	
        }   
              
    }
	 
	    
	/*    		
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
	
	
	*/
     var record_duty=[
	    		
        {name : 'id',type : 'string'},
        {name : 'startTime',type : 'string'},
        {name : 'endTime',type : 'string'},
        {name : 'dutyOperatorId',type : 'string'},
        {name : 'jobContent',type : 'string'},       
        {name : 'dutyOperatorName',type : 'string'},
        {name : 'dutyLeaderName',type : 'string'},
        {name : 'dutyLeaderId',type : 'string'},
        {name : 'orgId',type : 'string'}
		];
	//创建record
	var recordHeads_duty = Ext.data.Record.create(record_duty);
	
	//定义查询数据的url
    var dataStore_duty = new Ext.data.Store( {
		proxy:new Ext.data.HttpProxy(new Ext.data.Connection({
		     timeout:0,url:'duty/searchDutyPlan.json'})),
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
	
       columns:[sm,{
			header:'序号',
			width:80,
			hidden:true,
			dataIndex : 'id'   
		},{
			
			width:80,
			hidden:true,
			dataIndex : 'dutyOperatorId'  
		},{
			
			width:80,
			hidden:true,
			dataIndex : 'dutyLeaderId'  
		}, {
			header : '日期 ',
			width:150,
			dataIndex:'endTime'
		},
			{
			header:'业务班',
			width:80,
			dataIndex : 'dutyOperatorName'
		}
		, {
			header : '带班领导 ',
			width:80,
			
			dataIndex:'dutyLeaderName'
		}
      ],
	    defaults: {
        sortable: false,
        menuDisabled: true
    }
	});
	
	
	
	  //定义dataGrid_log分页栏	
	var dataGrid_pagingBar = new Ext.PagingToolbar({        
        store: dataStore_duty,
        displayInfo: true,
        displayMsg : '当前显示第 {0} - {1} 条记录，一共 {2} 条',
	    emptyMsg:'没有数据!'
    });	
    
    var dataGrid = new Ext.grid.GridPanel({
        store: dataStore_duty,
        cm: dataCm_duty,
        border : true,     
        region : "center",
        loadMask : true,
        split : true,
        enableColumnMove: false,
        sm:sm,
        stripeRows: true,
        iconCls : 'grid-icon',
        tbar:[  
		        {text:'修改',iconCls : 'icon-edit',handler:onModify},'-',
		        {text:'删除',iconCls : 'icon-delete',handler:onDelete}],
        bbar: dataGrid_pagingBar,
        listeners :{
           rowdblclick:function(grd,rowIndex,ev){
        	    onModify();
        	}
        }
    });
    
    //定义查询 Form  
    var conditionForm=new Ext.FormPanel( {
		//	buttonAlign:'center',
			id:'conditionForm',
			frame:true,
			height : 100,
			split : true,
		//	margins:'3 0 0 0',
	  /*
	  	collapsible : false,
			frame:true,
			region : "north",
			border:false,
			height : 210,
			labelAlign : 'left',
	  
	  */					
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
				                    fieldLabel: '业务员',
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
		
	   function onDelete(){
      if(!dataGrid.getSelectionModel().hasSelection()){
         Ext.Msg.alert("提示:","请选择要删除的记录!");
         return;
        }
        Ext.Msg.confirm("提示:","确定删除选定的记录?",function(btn){
         if(btn=='yes'){
         var ids = new Array();
         var selects = dataGrid.getSelectionModel().getSelections();
         for(var i=0;i<selects.length;i++)
           ids.push(selects[i].get("id"));       
          Ext.Ajax.request({
                url: 'duty/deleteDutyPlan.json',               
                params: {
                   ids:ids                    
                    },
                method: 'POST',
                success:  function (response,options) {                                  
                    var responseJson =  Ext.util.JSON.decode(response.responseText);
                    Ext.MessageBox.alert('成功', '从服务端获取结果 ' + response.responseText);                 
                    if(responseJson.success){                                     
				       Ext.MessageBox.alert('提示', '删除成功！ ');
				         dataStore_duty.reload();
				  
				    }else{
				    
				        Ext.MessageBox.alert('提示', '删除操作失败！ ');
				    }                
                },
                failure: function (response, options) {
                   Ext.MessageBox.alert('失败','操作失败！错误码：'+  response.status);
                }
             });
      
         }
        });
    }	
    
     //排班修改窗口
   function showDutyWindow(){
        if(addDutyWindow==null){
          infoDutyForm_edit =  new Ext.FormPanel({
	        labelWidth: 65,
	        border:true,
	        region : "center",
	        frame:true,
	        width:390,
	        split : true,
	     //   title:'排班信息',
	        margins:'3 0 0 0',
	        buttonAlign:'left',
	        items: {
	            xtype:'panel',
	            defaults:{autoHeight:true, bodyStyle:'padding:5px'}, 
	            items:[{
	                layout:'form',
	              //  defaults: {width: 230},
	                defaultType: 'textfield',
	
	                items: [
	                
	                 {
		                    fieldLabel: '业务班',  
						    xtype:"panel",  
						    layout:"column",  
						    isFormField:true,
						    id:"Edit_name",
		                    items: [
							     new Ext.form.TextField( {
								id : 'Edit_userId',
								emptyText :"请选择",
								width : 0,
								hidden:true,
								readOnly:true,
								allowBlank : true
							}),new Ext.form.TextField( {
								id : 'Edit_empName',
								emptyText :"请选择业务员",
								width : 150,
								readOnly:true, 
								allowBlank : false
							}),new Ext.Button({text:'请选择',
						                    
						                    width : 80,
										//	iconCls : 'icon-search',
											handler:function(){	
											    optype="3";
											 	showOpWindow();			
						                      
						                    }
						               })
							    
			         ]
                },
	                
	                
	                {
		                    fieldLabel: '带班领导',  
						    xtype:"panel",  
						    layout:"column",  
						    isFormField:true,
						    id:"Edit_name_leader",
		                    items: [
							     new Ext.form.TextField( {
								id : 'Edit_userId_leader',
								emptyText :"请选择",
								width : 0,
								hidden:true,
								readOnly:true,
								allowBlank : true
							}),new Ext.form.TextField( {
								id : 'Edit_empName_leader',
								emptyText :"请选择带班人",
								width : 150,
								readOnly:true,
								allowBlank : false
							}),new Ext.Button({text:'请选择',
						                   
						                    width : 80,
										//	iconCls : 'icon-search',
											handler:function(){
											     optype="4";	
											 	showOpWindow();			
						                      
						                    }
						               })
							    
			         ]
                },
	                
	                new Ext.form.DateField({  
							            
					            fieldLabel: '排班日期',
					            id: 'Edit_endTime',
					            editable:false, //不允许对日期进行编辑  
					            width:230,  
					            format:"Y-m-d",
					            allowBlank : false, 
					            listeners : {  //主要这里添加监听事件     
								    select : function(f, date) {          
								     //     Ext.getCmp("addAndEdit_startTime").setValue( Ext.getCmp("addAndEdit_startTime").getValue()); 
								    
								               var dt = Ext.getCmp('addAndEdit_endTime').getValue();									
								    //          dt.setDate(dt.getDate()+1);
								    //          alert(dt);
								                      }     }, 
					            emptyText:"请选择日期..."  
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
        
        
        
        
        
        
          addDutyWindow = new Ext.Window( { //定义对话框
						width : 380,
						height : 200,
						shadow : true,
						title: '交班管理',
						closeAction : 'hide', //hide表示关闭的时候对话框是隐藏的，并没有真正的销毁，下次要用时在show出来
						modal : true,
				 		closable : true,
						minWidth : 380,
						layout : 'fit', 
		    			minHeight : 200,
			 			buttons : [{
							text :'保存',
							id:'onSaveButton',
							handler: onSaveDuty
						},{
							text : '关闭',
							handler : function (){addDutyWindow.hide();}
						}],
						items:[infoDutyForm_edit] //将定义的表单加到对话框里
						});
		}				
			  addDutyWindow.show();			
						
   
   }
   
   
    function onSaveDuty() {
    
      //  alert(dataGrid.getSelectionModel().getSelected().get('id'));
		if(infoDutyForm_edit.getForm().isValid()){               
	         Ext.Ajax.request({
	                url: 'duty/updateDutyPlan.json',               
	                params: {
	                    id:dataGrid.getSelectionModel().getSelected().get('id'),
	                    dutyOperatorId:Ext.getCmp("Edit_userId").getValue(),                    
		                dutyOperatorName:Ext.getCmp("Edit_empName").getValue(),
						dutyLeaderId:Ext.getCmp("Edit_userId_leader").getValue(),                    
		                dutyLeaderName:Ext.getCmp("Edit_empName_leader").getValue(),
						endTime:Ext.getCmp("Edit_endTime").getValue()
						
	                },
	                method: 'POST',
	                success:  function (response, options) {                                  
	                    var responseJson =  Ext.util.JSON.decode(response.responseText);
	                      //  Ext.MessageBox.alert('成功', '从服务端获取结果: ' + response.responseText);                 
	                    if(responseJson.success){                                     
					      	 Ext.MessageBox.alert('成功', '修改成功！ ' );                 		     					        
	                          
	                          addDutyWindow.hide();
	                          dataStore_duty.reload();
					    } else{
					        
					         Ext.MessageBox.alert('失败', '修改失败！ '+response.responseText ); 
					    
					    }  
					                
	                },
	                failure: function (response, options) {
	                   
	                    Ext.MessageBox.alert('失败','操作失败！错误码：'+  response.status);
	                }
	            });     
           }
	  }  
		  
    function onModify(){
      
     if(!dataGrid.getSelectionModel().hasSelection()){
         Ext.Msg.alert("提示:","请选择要修改的记录!");
         return;
      }
      if(dataGrid.getSelectionModel().getSelections().length>1){
         Ext.Msg.alert("提示:","请选择一条记录进行修改!");
         return;
      }
      
    
     
      showDutyWindow();
      
         var  Edit_empName=dataGrid.getSelectionModel().getSelected().get('dutyOperatorName');
         var  Edit_userId=dataGrid.getSelectionModel().getSelected().get('dutyOperatorId');
         var  Edit_userId_leader=dataGrid.getSelectionModel().getSelected().get('dutyLeaderId');
         var  Edit_empName_leader=dataGrid.getSelectionModel().getSelected().get('dutyLeaderName');
         var  Edit_endTime=dataGrid.getSelectionModel().getSelected().get('endTime');

      var jsonValues={
           Edit_empName:Edit_empName,
           Edit_userId:Edit_userId,
           Edit_userId_leader:Edit_userId_leader,
           Edit_empName_leader:Edit_empName_leader,
           Edit_endTime:Edit_endTime
          
      };
      
        
        addDutyWindow.setTitle("修改排班信息");
        infoDutyForm_edit.getForm().reset(); 
          
		infoDutyForm_edit.getForm().setValues(jsonValues);
     
     
    }
	   
	   	var duty_panel = new Ext.Panel({
			  layout : 'border',
			  region : "center",
	          border : false,
	          title:'排班查询',
			  split : true,	
			  
			  	  
			  items:[conditionForm,dataGrid]
				
		});
   
     var innerPanel=new Ext.Panel({
          layout : 'border',
          border : false,
		  split : true,
		  title:'排班管理',
			
			//bodyStyle:'padding:200px 0 0 100px',
			iconCls : 'icon-plugin',
			
			items:[infoDutyForm,duty_panel]
    });
     var bigTabPanel = new Ext.TabPanel({
                region : 'center',
		        activeTab: 0,
		        border : false,
		        plain:true,
		        items:[innerPanel]
                });
                
 
          
   
    Ext.onReady(function(){
           
	      new Ext.Viewport({
				layout : 'border',
				split : true,
				items:[bigTabPanel]
				});				
		dataStore_duty.load();	
	});
   </script>
  </body>
</html>
