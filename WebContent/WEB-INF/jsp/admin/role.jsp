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
    <title>角色授权管理</title>	
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
    <script type="text/javaScript" src="js/ext-3.4.0/ux/TreeCheckNodeUI.js"></script>
  </head> 
  <body>
   <script type="text/javascript">
    var appData;
	var roleWindow,roleForm;
	var currentRoleId;
	var currentFuncId;
	var appNameData=[];//用于存放应用名称
	//var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false}); //复选框
	//获取FTP配置下拉框数据
    function getAPPNameId(){       
        Ext.Ajax.request({
                url: 'application/getAppNameId.json',               
                params: {},
                method: 'POST',
                success:  function (response, options) {                                  
                    var responseJson =  Ext.util.JSON.decode(response.responseText);                                                                                                  		
				       for (i=0;i<responseJson.length;i++){				      
				            appNameData.push(responseJson[i]);
				       }
				  //     Ext.MessageBox.alert('成功', '从服务端获取结果: ' +appNameData); 
				  			                         
                },
                failure: function (response, options) {
                   var responseJson =  Ext.util.JSON.decode(response.responseText);
						Ext.Msg.alert("提示:", "操作失败");
                }
            });	  
    }
    getAPPNameId();
    
    	
		// 保留期限 renderer	
	  function rendererAPPNameId(value){
		   for(var i=0;i<appNameData.length;i++){
		    if(appNameData[i][0]==value)
		      return appNameData[i][1];
		    }
		
    }
	 var role_tree = new Ext.tree.TreePanel({
       id : 'role_tree',
       region:"center",
       split:"true",
       title:"角色权限",
       autoScroll : true,
       margins:'3 0 0 0',
       checkModel: 'cascade',
	   onlyLeafCheckable: false,
       animate : true,
       imgSrc:'foundation/images/',
       autoShow :true,
       tbar:[{text:'保存授权',iconCls:'icon-save',handler:onSaveAuth}],
       containerScroll : true,
       lines:true,//节点之间连接的横竖线
       rootVisible:false,//是否显示根节点
       root: new Ext.tree.AsyncTreeNode({id:'rootId',text:'根节点'}),
       loader : new Ext.tree.TreeLoader({
                   dataUrl:'role/getRoleDetailTree.json',                 
		          uiProviders:{
		                'checkBox' : Ext.ux.TreeCheckNodeUI
		            }
               })
      });
      
     //保存授权 
    function onSaveAuth(){
      //如果角色ID不为空     
          
       if(!roleGrid.getSelectionModel().hasSelection()){
         Ext.MessageBox.alert('提示', '请选择左边的角色再进行授权！' ); 
         return;
        }
      currentRoleId= roleGrid.getSelectionModel().getSelected().get("roleId"); 
      
     if(!Ext.isEmpty(currentRoleId)){
	      var rootNode = role_tree.getNodeById("root-node");
		     var checkNodes = rootNode.getUI().getCheckedNodes(rootNode);
		     var userCheckNodes = [] ;
		     var functionCodes=[];
		     for(var i=0;i<checkNodes.length;i++){
		     if(checkNodes[i].id.split("~")[1]=='function')	          
		              functionCodes.push(checkNodes[i].id.split("~")[0]);	          		                      
		     }     
		  //   alert(functionCodes); 
		     Ext.Ajax.request({
                url: 'role/onAuthorization.json',               
                params: {
                		roleId:currentRoleId,
                        functionCodes:functionCodes
                },
                method: 'POST',
                success:  function (response, options) {                                  
                  //  if(success)
		          //   Ext.topShow.msg('授权成功:', '已保存到数据库!'); 
		           var responseJson =  Ext.util.JSON.decode(response.responseText);
		            if(responseJson.success)
                    Ext.MessageBox.alert('授权成功:', '已保存到数据库! ' ); 
		           //  Ext.topShow.msg('授权成功:', '已保存到数据库!'); 
		            
		            //带进度条的信息显示 
		           function showMessage(){  
			             Ext.MessageBox.show({  
			                 title:"授权成功:",  
			                 msg:"已保存到数据库!",  
			                 progress:true,  
			                 width:300,  
			                 wait:true,
			                 animEl:"fly" , 
			                 icon:Ext.Msg.INFO,
			                 waitConfig:{interval:500},//0.5s进度条自动加载一定长度  
			                 closable:true  
                         });  
                      setTimeout(function(){Ext.MessageBox.hide()},1800);//2后执行关闭窗口函数  
                     }  
                //    showMessage();   
                       
                },
                failure: function (response, options) {
                    Ext.MessageBox.alert('失败', '请求超时或网络故障,错误编号：' + response.status);
                }
            });
	     }else Ext.Msg.alert("提示:","请选择要授权的角色");
    }
      
	var centerPanel = new Ext.Panel({
     region : "center",
     border : false,
	 split : true,
	
     layout : 'border',
     items:[role_tree]
    });
	//左边完整roleGridPanle定义
    var role_record=[
				{name : 'roleName',type : 'string'}, 
				{name : 'roleType',type : 'string'},
				{name : 'appId',type : 'string'}, 
				{name : 'roleId',type : 'string'}
				];
     var roleRecord = Ext.data.Record.create(role_record);
      var roleListStore = new Ext.data.Store({                           
			proxy:new Ext.data.HttpProxy(
			      new Ext.data.Connection({
			          timeout:0,
			          url:'role/getAllRoles.json'})
			          ),
			reader:new Ext.data.JsonReader({
				root : 'data.arr',
				totalProperty : 'data.totalProperty'
			}, roleRecord),

			remoteSort : false
		});
     var roleCm = new Ext.grid.ColumnModel({ 
       columns:[ 
       		new Ext.grid.RowNumberer(),
			{
			header:'角色编号',
			width:90,
			dataIndex : 'roleId'
		}, {
			header : '角色名称',
			width:120,
			dataIndex:'roleName'
		}, {
			header : '类别',
			width:80,
			dataIndex:'roleType'
	
		}, {
			header : '所属应用',
			width:120,
			renderer:rendererAPPNameId,
			dataIndex:'appId'
		
		}
		
		],
		defaults: {
        sortable: false,
        menuDisabled: true
    }
		});
     var pagingBarRole = new Ext.PagingToolbar({    
	      store : roleListStore,
	      displayInfo : true,
	      displayMsg : '当前显示第 {0} 条到 {1} 条记录，一共 {2} 条',
	      emptyMsg:'没有数据!'
    });
     var roleGrid = new Ext.grid.EditorGridPanel({
        store: roleListStore,
        cm: roleCm,
        border : true,
        margins:'3 0 0 0',
        width:490,
        height:400,
        collapsible : true,
		split : true,
        region : "west",
        sm:new Ext.grid.RowSelectionModel({singleSelect:true}),
        loadMask : true,
        enableColumnMove: false,
        stripeRows: true,
        title:'所有角色',
        tbar:[
	        	{text:'增加',iconCls : 'icon-add',handler:onAddRole},'-',
	        	{text:'修改',iconCls : 'icon-edit',handler:onUpdateRole},'-',
		        {text:'删除',iconCls : 'icon-delete',handler:onDeleteRole},'-'
		        //,{text:'刷新缓存',id:'updateCache',iconCls : 'UST_tp',handler:function(){}}
	    	],
     //  tbar:[{text:'增加',iconCls : 'icon-add'}],
        bbar: pagingBarRole,
        listeners :{                   
           rowclick:function(grd,rowIndex,ev){         
               getFetchFuncs();
           },
           rowdblclick:function(grd,rowIndex,ev){
        	   onUpdateRole();
        	}
         }
           
    });
      //根据角色ID获取角色权限
      function getFetchFuncs() {
          Ext.Ajax.request({
                url: 'role/getRoleFunctions.json',
                headers: {
                    'userHeader': 'userMsg'
                },
                params: {roleId:roleGrid.getSelectionModel().getSelected().get("roleId")},
                method: 'POST',
                success:  function (response, options) {                                  
                    var responseJson =  Ext.util.JSON.decode(response.responseText);
                    //Ext.MessageBox.alert('成功', '从服务端获取结果123: ' + response.responseText);                 
                     var list=responseJson.data;                                      
				     var rootNode = role_tree.getNodeById("root-node");				 
				         rootNode.collapseChildNodes(true);				       
				       var checkNodes = rootNode.getUI().getCheckedNodes(rootNode);	
				      		       
				     for(var i=0;i<checkNodes.length;i++){
						   if(checkNodes[i].id.split("~")[1]=='function'){
						      checkNodes[i].getUI().setChecked(checkNodes[i],false);
						      }
					 }		
					//   alert(response.responseText);			
				      for(var i=0;i<list.length;i++){				      
				       var n1 = role_tree.getNodeById(list[i].FUNCCODE+"~function");			       
				       if(!Ext.isEmpty(n1))
				       n1.getUI().setChecked(n1,true);
				      }                   
                },
                failure: function (response, options) {
                    Ext.MessageBox.alert('失败', '请求超时或网络故障,错误编号：' + response.status);
                }
            });
      }
      
    
    
      //增加角色数据保存
		function onAddRole(){
			optype="0";
	        showRoleWindow(); 
	        roleWindow.setTitle("增加角色信息");
	        roleForm.getForm().reset();
		}
      
      //增加和修改角色数据保存
       function onSaveOrUpdateRole(){
	   if(optype=='0'){//增加
	    onSaveRole();
	   }else if(optype=='1'){//修改
	     onUpdateSaveRole();
	   }
	  }
	  
	  function onUpdateRole(){
      	if(!roleGrid.getSelectionModel().hasSelection()){
         Ext.Msg.alert("提示:","请选择要修改的记录!");
         return;
        }
        if(roleGrid.getSelectionModel().getSelections().length>1){
         Ext.Msg.alert("提示:","请选择一条记录进行修改!");
         return;
      	}
		var e_roleName = roleGrid.getSelectionModel().getSelected().get('roleName');
	    var e_roleType = roleGrid.getSelectionModel().getSelected().get('roleType');
	    var e_appId  = roleGrid.getSelectionModel().getSelected().get('appId');
	    var values = {e_roleName:e_roleName,e_roleType:e_roleType,e_appId:e_appId};
	    optype="1";
        showRoleWindow(); 
        roleWindow.setTitle("修改角色信息");
        roleForm.getForm().reset();
		roleForm.getForm().setValues(values);
	}
      
      
       function showRoleWindow(){
    
        if(roleWindow==null){
           roleForm=new Ext.FormPanel( {
			height : 150,
			collapsible : false,
			frame:true,
			border : false,
			labelAlign : 'left',
			items : [{
			layout : 'column',
			items : [
					{
					
						columnWidth : .50,
						layout : 'form',
						labelWidth:60,
						items : [
								new Ext.form.TextField( {
							fieldLabel : '角色名称',
							id:'e_roleName',
							name:'e_roleName',
							width : 120,
							listeners:{blur:checkRoleNameUsable},
							
							listeners :{"blur":function(value){
						        	//	验证应用名称的唯一性 APPNameExists();
						            if (optype=='0'){						        	  						        	
						        	 checkRoleNameUsable();					        							        	     
						        	 }	
						        	
						        	else if(optype=='1'){
							        	if (Ext.getCmp("e_roleName").getValue()!= roleGrid.getSelectionModel().getSelected().get('roleName')){
							        	
							        	 checkRoleNameUsable();	
							        	}
							        	 
						        	}						        	
						        	
						        }	
						       } ,
							
							allowBlank : false
						}),new Ext.form.ComboBox({
									valueField : 'value',					
				                    allowBlank : false,
				                    displayField : 'text',
									fieldLabel : '角色类别',
									mode : 'local',
									name:'e_roleType',
									id:'e_roleType',								
									width : 120,
									triggerAction : 'all',
									editable:true,
									selectOnFocus : false,
									forceSelection : true,
									store :  new Ext.data.SimpleStore( {
												fields : ['value', 'text'],
												data : [['系统级','系统级'],
														['业务级', '业务级']]
											})
											
								})
								]
					},{
						columnWidth : .50,
						labelWidth:60,
						layout : 'form',
						items : [new Ext.form.ComboBox({
						
						fieldLabel: '所属应用',
	                    id: 'e_appId',
	                    name:'e_appId',
	                    valueField : 'value',					
	                    allowBlank : false,
	                    displayField : 'text',
						mode : 'local',
						width:120,
						triggerAction : 'all',
						editable:true,
						selectOnFocus : false,
						forceSelection : true,
						store :  new Ext.data.SimpleStore( {
												fields : ['value', 'text'],
												data : appNameData
											})
											
							/*							
									fieldLabel: '所属应用',
				                    id: 'add_appId',
				                  //  valueField : 'appId',
									displayField : 'text',									
									store : ds_app_select,
									selectOnFocus : true,
									editable : false,
									resizable :true,
									width : 120,
									allowBlank : false,
									triggerAction : 'all'
						*/
					})]
					}
				]
			}]
		});
    
          roleWindow = new Ext.Window( { 
						width : 500,
						height : 150,
						shadow : true,
						closeAction : 'hide', 
						modal : true,
						title:'角色增加',
						closable : false,
						minWidth : 500,
						minHeight : 150,
						buttons : [{
							text :'保存',
							handler:onSaveOrUpdateRole
						},{
							text : '关闭',
							handler : function (){roleWindow.hide();}
						}],
						items:[roleForm] //将定义的表单加到对话框里
					});
        }
	        roleWindow.show();  
	        roleForm.getForm().reset();
     
    }
    
    
     var innerPanel=new Ext.Panel({
          layout : 'border',
          border : false,
		  split : true,
			title:'角色管理',
			
			//bodyStyle:'padding:200px 0 0 100px',
			iconCls : 'icon-plugin',
			
			items:[roleGrid,centerPanel]
    });
     var bigTabPanel = new Ext.TabPanel({
                region : 'center',
		        activeTab: 0,
		        border : false,
		        plain:true,
		        items:[innerPanel]
                });
                
     //所属应用 Store           
   var ds_app_select = new Ext.data.JsonStore({
        pruneModifiedRecords:true,
		fields:["appId", "appName"]
        }); //列表数据集 
          
   //角色名验证
    function checkRoleNameUsable(){
      var add_roleName = Ext.getCmp("e_roleName").getValue();
      if(add_roleName == "" || add_roleName == null){return;}
      Ext.Ajax.request( {
						method:'POST', 
						timeout:0,
						url : 'role/roleNameExists.json',
						params : {
							roleName : add_roleName
						}, 
						success : function(response, options) {
							var rolejson = Ext.util.JSON.decode(response.responseText);
							
							if(rolejson.data){
							
								Ext.Msg.alert("提示:","角色名: "+add_roleName+" 已经存在,请输入其它角色名");
							}
						},
						failure:function(response, options){
							var responseJson =  Ext.util.JSON.decode(response.responseText);
							Ext.Msg.alert("提示:", "操作失败！", responseJson.errors);
						}
					});
    }
    
    
    //增加角色保存
    function onSaveRole() {
      if (roleForm.getForm().isValid()) {
    
		Ext.Ajax.request({
			method : 'POST',
			url:'role/saveRole.json',
			params:{
				roleName:Ext.getCmp("e_roleName").getValue(),
				roleType:Ext.getCmp("e_roleType").getValue(),
				appId:Ext.getCmp("e_appId").getValue()
			},

			//成功时回调
			success:function(response,options){
				
				//获取响应的json字符串
				var rolejson = Ext.util.JSON.decode(response.responseText);
				
				if(rolejson.success){
					Ext.Msg.alert('提示：','角色增加成功！');
					roleWindow.hide();
					roleListStore.reload();
				}else{
					Ext.Msg.alert('提示','角色增加失败！');
				}
			},
			//失败
			failure:function(response, options){
				var responseJson =  Ext.util.JSON.decode(response.responseText);
				Ext.Msg.alert("提示:", "操作失败！", responseJson.errors);
			} 
		});
		
		}
       }
       
       //修改角色保存
    function onUpdateSaveRole() {
        if (roleForm.getForm().isValid()) {
    
		Ext.Ajax.request({
			method : 'POST',
			url:'role/updateRole.json',
			params:{
				roleId:roleGrid.getSelectionModel().getSelected().get('roleId'),
				roleName:Ext.getCmp("e_roleName").getValue(),
				roleType:Ext.getCmp("e_roleType").getValue(),
				appId:Ext.getCmp("e_appId").getValue()
			},

			//成功时回调
			success:function(response,options){
				
				//获取响应的json字符串
				var rolejson = Ext.util.JSON.decode(response.responseText);
				
				if(rolejson.success){
					Ext.Msg.alert('恭喜','角色修改成功！');
					roleWindow.hide();
					roleListStore.reload();
				}else{
					Ext.Msg.alert('提示','修改失败！');
				}
			},
			//失败
			failure:function(response, options){
				var responseJson =  Ext.util.JSON.decode(response.responseText);
				Ext.Msg.alert("提示:", "操作失败！", responseJson.errors);
			} 
		});
		}
       }
       
   //删除角色列表数据
	function onDeleteRole() {	
		if(!roleGrid.getSelectionModel().hasSelection()){
         Ext.Msg.alert("提示:","请选择要删除的记录!");
         return;
        }
        Ext.Msg.confirm("提示:","确定删除选定的记录?",function(btn){
         if(btn=='yes'){
        	Ext.Ajax.request({
			method : 'POST',
			url:'role/deleteRole.json',
			params:{
				roleIds:roleGrid.getSelectionModel().getSelections()[0].get("roleId")
			},
			//成功时回调
			success:function(response,options){
				//获取响应的json字符串
				var rolejson = Ext.util.JSON.decode(response.responseText);
				if(rolejson.success){
					Ext.Msg.alert('提示','删除成功！');
					roleListStore.reload();
				}else{
					Ext.Msg.alert('提示','删除失败！');
				}
			},
			//失败
			failure:function(response, options){
				var responseJson =  Ext.util.JSON.decode(response.responseText);
				Ext.Msg.alert("提示:", "操作失败！", responseJson.errors);
			}  
		});
         }
        });
        
		}  
          
   
    Ext.onReady(function(){
           
	      new Ext.Viewport({
				layout : 'border',
				split : true,
				items:[bigTabPanel]
				});				
			roleListStore.load();
	});
   </script>
  </body>
</html>
