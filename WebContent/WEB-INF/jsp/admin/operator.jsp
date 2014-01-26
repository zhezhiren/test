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
    <title>系统用户管理</title>	
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
	<link rel="stylesheet" type="text/css" href="css/multiselect.css"/>
    <script type="text/javascript" src="js/ext-3.4.0/ux/DDView.js"></script>
    <script type="text/javascript" src="js/ext-3.4.0/ux/MultiSelect.js"></script>
    <script type="text/javascript" src="js/ext-3.4.0/ux/ItemSelector.js"></script>
	</head>
	<body>
	<script type="text/javascript">
	var pageSize=20;
    var addWindow=null;
    var infoForm=null;
    var currentOperatorId;
    var allRoles;
    var optype;//optype=0为增加,optype=1为修改
    var modifyOperatorId;
    var sm = new Ext.grid.CheckboxSelectionModel();
    
    
    var record1=[
		{name : 'userId',type : 'string'}, 
		{name : 'operatorName',type : 'string'},
		{name : 'status',type : 'string'}, 
		{name : 'email',type : 'string'},
		{name : 'operatorId',type : 'string'},
		{name : 'lastLoginTime',type : 'string'}
		];
				
	var recordHeads = Ext.data.Record.create(record1);
	//定义查询数据的url
    var datastore = new Ext.data.Store( {
		proxy:new Ext.data.HttpProxy(new Ext.data.Connection({timeout:0,url:'operator/searchOperator.json'})),
		reader : new Ext.data.JsonReader( {
			root : 'data.arr',
			totalProperty : 'data.totalProperty'
		}, recordHeads),

		remoteSort : false
	});
	
	datastore.on('beforeload', function() { 
				datastore.baseParams = {
					userId:Ext.getCmp("search_udserId").getValue(),
				    operatorName:Ext.getCmp("search_operatorName").getValue()
				};
			});
	
	//定义页面查询表格
	var datacm = new Ext.grid.ColumnModel({
	
       columns:[sm,
			{
			header:'登录名',
			width:100,
			dataIndex : 'userId'
		}, {
			header : '姓名',
			width:100,
			dataIndex:'operatorName'
		}, {
			header : '状态',
			dataIndex:'status',
			width:100
		}, {
			header : '邮箱',
			width:150,
			dataIndex:'email'
		}, {
			header : '最后登录时间',
			width:150,
			dataIndex:'lastLoginTime'
			//renderer: Ext.util.Format.dateRenderer('Y-m-d H:i:s')
		}],
	    defaults: {
        sortable: false,
        menuDisabled: true
    }

	});
	/*
	datacm.setRenderer(1,userRed);
	
	function userRed(value,metadata,record,rowIndex,colIndex,store){
	 
	  return record.get("USERID")+":"+record.get("OPERATORNAME");
	}
    */
    	
  var pagingBar = new Ext.PagingToolbar({        
        store: datastore,
        displayInfo: true,
        displayMsg : '当前显示第 {0}页 - {1} 条记录，一共 {2} 条',
	    emptyMsg:'没有数据!'
    });
	 	
	 			
     var dataGrid = new Ext.grid.GridPanel({
        store: datastore,
        cm: datacm,
        border : true,
        tbar:[  {text:'增加',iconCls : 'icon-add',handler:onAdd},'-',
		        {text:'修改',iconCls : 'icon-edit',handler:onModify},'-',
		        {text:'删除',iconCls : 'icon-delete',handler:onDelete}],
        region : "center",
        loadMask : true,
        split : true,
        enableColumnMove: false,
        sm:sm,
        stripeRows: true,
        iconCls : 'grid-icon',
       // title:'用户查询结果:' ,
        bbar: pagingBar,
        listeners :{
           rowdblclick:function(grd,rowIndex,ev){
        		onModify();
        	}
        }
    });
    
    //define query  
    var conditionForm=new Ext.FormPanel( {
			buttonAlign:'center',
			id:'conditionForm',
			frame:true,
			height : 70,
			split : true,
			margins:'3 0 0 0',
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
						columnWidth : .25,
						layout : 'form',
						labelWidth:60,
						items : [
								 new Ext.form.TextField( {
									fieldLabel : '登录名',
									id : 'search_udserId',
									width : 120,
									allowBlank : true
								})]
					},
					{
						columnWidth : .25,
						labelWidth:60,
						layout : 'form',
						items : [ new Ext.form.TextField( {
									fieldLabel : '姓名',
									id : 'search_operatorName',
									width : 120,
									allowBlank : true
								})]
					},{
						columnWidth : .25,
						labelWidth:60,
						layout : 'form',
						items : [ new Ext.Button({text:'查询',iconCls : 'icon-search',handler:function(){loadGrid(datastore);}})]
					}]
		
			}]
		});
		
	//var conditionPanel = {region : "north",height : 60,collapsible : true,border : false,items:[conditionForm]};
    
    
	var innerPanel = new Ext.Panel({
		 layout : 'border',
          border : false,
          title:'用户管理',
          iconCls : 'icon-plugin',
			split : true,
			items:[conditionForm,dataGrid]
		});

    var bigTabPanel = new Ext.TabPanel({
               region : 'center',
		        activeTab: 0,
		        border : false,
		        frame:false,
		        plain:true,
		        items:[innerPanel]
    });
    
  /*function loadGrid(store){    				
	//带查询条件的数据分页查询
 //	alert(Ext.getCmp("search_udserId").getValue());    		
		   store.load( {
					params : {
						  userId:Ext.getCmp("search_udserId").getValue(),
				          operatorName:Ext.getCmp("search_operatorName").getValue()
					}
				});
    }*/
    
    var resultArray =null;// = <xframe:write dictTypeId='APF_OPERSTATUS' type='array'/>;
    
    function operStateToString(value){
     return rendererDisplay(resultArray,value);
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
      optype='1';
      currentOperatorId = dataGrid.getSelectionModel().getSelections()[0].get("operatorId");
     
      Ext.Ajax.request( {
									method:'POST', 
									timeout:0,
									url : 'operator/loadOperatorRolesById.json',
									params : {
										operatorId : currentOperatorId
									},
									success : function(response, options) {
										var operator = Ext.util.JSON.decode(response.responseText);
										  									    
										   modifyOperatorId = currentOperatorId;																				
											var add_userId = dataGrid.getSelectionModel().getSelected().get('userId');
											var add_operatorName = dataGrid.getSelectionModel().getSelected().get('operatorName');
										    var status = dataGrid.getSelectionModel().getSelected().get('status');
										    var email  = dataGrid.getSelectionModel().getSelected().get('email');
										    var operatorId = dataGrid.getSelectionModel().getSelected().get('operatorId');
										    var values = {add_userId:add_userId,add_operatorName:add_operatorName,add_status:status,add_email:email,operatorId:operatorId};
										     optype="1";
									        showWindow(); 
									        addWindow.setTitle("修改用户信息");
									        infoForm.getForm().reset();
									     //   Ext.getCmp("add_password").hide();
									    //    Ext.getCmp("add_password_confirm").hide();
											infoForm.getForm().setValues(values);
										 
										if(!Ext.isEmpty(operator.data)){
											var itemselector = Ext.getCmp("itemselector");											
											itemselector.toData = []; 											 
											for(var i=0;i<operator.data.length;i++)
										       itemselector.toData.push([operator.data[i].roleId,operator.data[i].roleName]);    
										        itemselector.reloadToStore();
										        loadFromStore();
									      }
										
									},
									failure : function(response, options) {
										Ext.MessageBox.alert("提示:", "操作失败！"+response.responseText);
										currentOperatorId="";
										modifyOperatorId="";
									}
								});
    }
    
  function onAdd(){
     optype='0';
     showWindow();
     addWindow.setTitle("增加用户信息")
     resetWindow(); 
     Ext.getCmp("add_password").show();
     Ext.getCmp("add_password_confirm").show();   
     modifyOperatorId="";
    }
    
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
           ids.push(selects[i].get("operatorId"));       
          Ext.Ajax.request({
                url: 'operator/deleteOperators.json',               
                params: {
                   ids:ids                    
                    },
                method: 'POST',
                success:  function (response,options) {                                  
                    var responseJson =  Ext.util.JSON.decode(response.responseText);
                  //  Ext.MessageBox.alert('成功', '从服务端获取结果123: ' + response.responseText);                 
                    if(responseJson.success){                                     
				  //     Ext.MessageBox.alert('提示', '该功能名称已经存在，请重新输入一个！ ');
				         datastore.reload();
				  
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
            title:'基本信息:',
			items : [
					{
						columnWidth : .50,
						layout : 'form',
						labelWidth:60,
						items : [
								 {
								   xtype:"textfield",
									fieldLabel : '登录名',
									id:'add_userId',
									width : 180,
									listeners:{blur:checkUserIdUsable},
									allowBlank : false
								},{
								    xtype:"textfield",
									fieldLabel : '姓名',
									id:'add_operatorName',
									width : 180,
									allowBlank : false
								},new Ext.form.TextField({
								fieldLabel : '登录密码',
								name:'add_password',
								id:'add_password',
								width:180,
								inputType: 'password',
								allowBlank:true
							//	listeners:{blur:function(field){
							//	       if(field.getValue()!=Ext.getCmp("add_password_confirm").getValue()){
							//        Ext.getCmp("add_password_confirm").markInvalid();  
							//        Ext.Msg.alert("提示:","两次密码输入不一致！请重新输入！"); }}}
								})
								]
					},{
						columnWidth : .50,
						labelWidth:60,
						layout : 'form',
						items : [					
						new Ext.form.ComboBox({						
							fieldLabel: '状态',
		                    id: 'add_status',
		                    valueField : 'value',				
		                    allowBlank : true,
		                    displayField : 'text',
							mode : 'local',
							width:180,
							triggerAction : 'all',
							editable:true,
							selectOnFocus : false,
							forceSelection : true,
							store :  new Ext.data.SimpleStore( {
													fields : ['value', 'text'],
													data : [['正常','正常'],['不正常','不正常']]
												}) 
						}),{
						 xtype:"textfield",
							fieldLabel : '邮箱',
							id:'add_email',
							vtype:'email',
							width : 180,
							allowBlank : true
						},new Ext.form.TextField({fieldLabel : '确认密码',id:'add_password_confirm',name:'add_password_confirm',width:180,inputType: 'password',allowBlank:true,
						initialPassField: 'add_password',listeners:{blur:function(field){
								       if(field.getValue()!=Ext.getCmp("add_password").getValue()){
								       Ext.getCmp("add_password_confirm").markInvalid();
								       Ext.Msg.alert("提示:","两次密码输入不一致！请重新输入！");
								       return;
								       
								       }}} //  ,vtype: 'password'
						})]
					}
				]
			},{
			layout : 'column',
			 xtype:'fieldset',
            title:'权限信息:',
			items : [
					{
						columnWidth : 1,
						layout : 'form',
						labelSeparator:' ',
						labelWidth:60,
						items : [
						{
				            xtype:"itemselector",
				            name:"itemselector",
				            id:'itemselector',
				            dataFields:["roleId", "roleName"],
				            toData:[],
				            msWidth:250,
				            msHeight:200,
				            valueField:"roleId",
				            displayField:"roleName",
				            imagePath:"foundation/images",
				            toLegend:"已授予的权限",
				            fromLegend:"可获得的权限",
				            fromData:[]
				        }
								 ]
					}
				]
			}]
		});
      
          addWindow = new Ext.Window( { //定义对话框
						width : 700,
						height : 470,
						shadow : true,
						title: '用户信息:',
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
     //   Ext.getCmp("onSaveButton").setDisabled(false);
        addWindow.show();
    }
    
    function resetWindow(){
      if(infoForm!=null){
        infoForm.getForm().reset();
        var itemselector=Ext.getCmp("itemselector");
        itemselector.toData=[];
        itemselector.reloadToStore();
        loadFromStore();
      }
    }

    function checkUserIdUsable(){
        // userExists.json
         Ext.Ajax.request({
                url: 'operator/userExists.json',               
                params: {
                   
                    useId:Ext.getCmp("add_userId").getValue()
                    },
                method: 'POST',
                success:  function (response,options) {                                  
                    var responseJson =  Ext.util.JSON.decode(response.responseText);
                  //  Ext.MessageBox.alert('成功', '从服务端获取结果123: ' + response.responseText);                 
                    if(!responseJson.success){                                     				  
				          Ext.MessageBox.alert('提示', '用户名： '+Ext.getCmp("add_userId").getValue() + ' 已经存在，请重新输入！  ');    
				    
				    }                
                },
                failure: function (response, options) {
                    var responseJson =  Ext.util.JSON.decode(response.responseText);
                    Ext.MessageBox.alert('失败', responseJson.errors);
                }
             }); 
        
  
    }
    
    function loadFromStore(){
     var itemselector=Ext.getCmp("itemselector");
        itemselector.fromData = [];
        for(var i=0;i<allRoles.length;i++)
        itemselector.fromData.push([allRoles[i].roleId,allRoles[i].roleName]);
        itemselector.reloadFromStore();
    }
    
    function onSave(){
      if(infoForm.getForm().isValid()){
        var itemselector=Ext.getCmp("itemselector");
        if(optype=='0'){	        
	         insertOperator();
		 }else if(optype=='1'){		
		    updateOperator();		   
		 }
      }
    }
    function insertOperator(){    
           Ext.Ajax.request({
                url: 'operator/insertOperator.json',               
                params: {
                    password:Ext.getCmp("add_password").getValue(),
                    userId:Ext.getCmp("add_userId").getValue(),
                    operatorName:Ext.getCmp("add_operatorName").getValue(),
                    status:Ext.getCmp("add_status").getValue(),
                    email:Ext.getCmp("add_email").getValue(),                
                    roleIds:(Ext.isEmpty(infoForm.getForm().findField('itemselector').getValue())?null:infoForm.getForm().findField('itemselector').getValue().split(","))                     
                    },
                method: 'POST',
                success:  function (response,options) {                                  
                    var responseJson =  Ext.util.JSON.decode(response.responseText);
                 //   Ext.MessageBox.alert('成功', '从服务端获取结果123: ' + response.responseText);                 
                    if(responseJson.success){                                     				  
				         addWindow.hide();
	                     datastore.reload();
				  
				    }                
                },
                failure: function (response, options) {
                    var responseJson =  Ext.util.JSON.decode(response.responseText);
                    Ext.MessageBox.alert('失败', responseJson.errors);
                }
             }); 
    	
    
    }
    
    function updateOperator(){
              Ext.Ajax.request({
                url: 'operator/updateOperator.json',               
                params: {
                    operatorId:currentOperatorId,
                    password:Ext.getCmp("add_password").getValue(),
                    userId:Ext.getCmp("add_userId").getValue(),
                    operatorName:Ext.getCmp("add_operatorName").getValue(),
                    status:Ext.getCmp("add_status").getValue(),
                    email:Ext.getCmp("add_email").getValue(),                
                    roleIds:(Ext.isEmpty(infoForm.getForm().findField('itemselector').getValue())?null:infoForm.getForm().findField('itemselector').getValue().split(","))                     
                    },
                method: 'POST',
                success:  function (response,options) {                                  
                    var responseJson =  Ext.util.JSON.decode(response.responseText);
                  //  Ext.MessageBox.alert('成功', '从服务端获取结果123: ' + response.responseText);                 
                    if(responseJson.data){                                     
				   //   Ext.MessageBox.alert('提示', '该功能名称已经存在，请重新输入一个！ ');
				        addWindow.hide();
	                    datastore.reload();
				   
				    }                
                },
                failure: function (response, options) {
                    var responseJson =  Ext.util.JSON.decode(response.responseText);
                    Ext.MessageBox.alert('失败', responseJson.errors);
                }
             }); 
    
    }
    
    
    function onSaveOver(mark){
    if(mark){
       addWindow.hide();
	   datastore.reload();
	   }
    }
    
    function loadAllRoles(){
 		Ext.Ajax.request( {
				method:'POST', 
				timeout:0,
				url : 'operator/loadAllRoles.json',
				params : {
					
				},
				success : function(response, options) {
					var operator = Ext.util.JSON.decode(response.responseText);
						var list = operator.data;
						allRoles = list;
						if(operator.success){
				    //		alert(response.responseText);
				  	//   loadGrid(datastore);
				  	     
					}
				},
				failure:function(response, options){
						var responseJson =  Ext.util.JSON.decode(response.responseText);
						Ext.Msg.alert("提示:", "操作失败！", responseJson.errors);
					} 
			});
 	}
 	
 	 function loadGrid(store){
      store.load( {
					params : {
						start : 0,
						limit : pageSize
					}
				});
    }
	
    Ext.onReady(function(){    
         loadAllRoles();
     new Ext.Viewport({
			layout : 'border',
			split : true,
			items:[bigTabPanel]
			});  
		 datastore.load();	 
    });
    </script>
	
	</body>
</html>
