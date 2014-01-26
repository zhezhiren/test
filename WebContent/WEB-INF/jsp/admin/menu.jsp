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
    <title>菜单管理</title>	
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

    <link rel="stylesheet" type="text/css" href="foundation/css/global.css" />
    <!-- ext-base.js表示框架的基础库。。。ext-all.js表示框架的核心库 -->
	<link rel="stylesheet" type="text/css" href="js/ext-3.4.0/resources/css/ext-all.css" />
	<script type="text/javaScript" src="js/ext-3.4.0/adapter/ext/ext-base.js"></script>
	<script type="text/javaScript" src="js/ext-3.4.0/ext-all.js"></script>
	<script type="text/javascript" src="js/ext-3.4.0/ext-lang-zh_CN.js" charset="utf-8" ></script>
	<style type="text/css">
    .red {
        color: #FF00000;
    }
	</style>
  </head>

  <body>
  
    <script type="text/javascript">
      
    var pageSize=20;
    var currentNode;
    var optype='1'; //1修改页面选择功能保存  2增加页面选择功能保存
    var selectFuncObj;
    var funcSelectWindow;
    var treeSelectWindow;
    var treeForm;
    var selectTreeObj;
    var currentTreeNode=null;
    var tree;
    var menu;
    var menujson;
    var menuTree;
    var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
            var record1=[
				{name : 'menuId',type : 'string'},
				{name : 'menuLabel',type : 'string'}, 
				{name : 'menuName',type : 'string'},
				{name : 'menuCode',type : 'string'}, 
				{name : 'isleaf',type : 'string'},
				{name : 'menuLevel',type : 'string'}
				];
				
				var recordFunc=[
				{name : 'funcName',type : 'string'}, 
				{name : 'funcGroupName',type : 'string'},
				{name : 'appName',type : 'string'}, 
				{name : 'funcAction',type : 'string'},
				{name : 'appId',type : 'string'},
				{name : 'funcCode',type : 'string'}
				];
				
				
            var menusRecord = Ext.data.Record.create(record1);
            var funcRecord = Ext.data.Record.create(recordFunc);
				
	    var menuListStore = new Ext.data.Store( {
			proxy:new Ext.data.HttpProxy(new Ext.data.Connection({timeout:0,url:'menu/getAllMenu.json'})),

			reader : new Ext.data.JsonReader( {
				root : 'data.arr',
				totalProperty : 'data.totalProperty'
			}, menusRecord),

			remoteSort : false
		});
		
		 var funcListStore = new Ext.data.Store( {
			proxy:new Ext.data.HttpProxy(new Ext.data.Connection({timeout:0,url:'menu/selectFuncList.json'})),

			reader : new Ext.data.JsonReader( {
				root : 'data.arr',
				totalProperty : 'data.totalProperty'
			}, funcRecord),

			remoteSort : false
		});
		
		menuListStore.on('beforeload', function() {
				menuListStore.baseParams = {
					menuCode:Ext.getCmp("menuCode").getValue(),
				    menuLevel:Ext.getCmp("menuLevel").getValue(),
				    menuName:Ext.getCmp("menuName").getValue(),
				    menuLabel:Ext.getCmp("menuLabel").getValue()
				};
			});
			
			funcListStore.on('beforeload', function() {
				funcListStore.baseParams = {
					appName:Ext.getCmp("b_appName").getValue(),
				    funcName:Ext.getCmp("b_funcName").getValue(),
				    funcGroupName:Ext.getCmp("b_funcGroupName").getValue()
				};
			});
		
		
		//菜单查询列表
		var menuCm = new Ext.grid.ColumnModel({ 
       columns:[
			{
			header:'菜单显示名称',
			width:150,
			dataIndex : 'menuLabel'
		}, {
			header : '菜单名称',
			width:150,
			dataIndex:'menuName'
		}, {
			header : '菜单代码',
			width:180,
			dataIndex:'menuCode'
		}, {
			header : '是否叶子菜单',
			dataIndex:'isLeaf',
			renderer:function(value){
		        if(value == "y"){
		        	return "是";
		        }else{
		        	return "否";
		        }
		    }
		}, {
			header : '菜单层次',
			dataIndex:'menuLevel'
		}],
		defaults: {
        sortable: false,
        menuDisabled: true
    }
		});
		
		//菜单详细信息查看内功能名称的查询
		var funcCm = new Ext.grid.ColumnModel({ 
       columns:[
       		sm,
			{
			header:'功能名称',
			width:160,
			dataIndex : 'funcName'
		}, {
			header : '功能组名称',
			width:150,
			dataIndex:'funcGroupName'
		}, {
			header : '应用名称',
			width:120,
			dataIndex:'appName'
		}, {
			header : '功能调用入口',
			width:215,
			dataIndex:'funcAction'
		}],
		defaults: {
        sortable: false,
        menuDisabled: true
    }
		});
		
		 var pagingBarMenu = new Ext.PagingToolbar({        
	        store: menuListStore,
	        displayInfo: true,
	        displayMsg : '当前显示第 {0} - {1} 条记录，一共 {2} 条',
		    emptyMsg:'没有数据!'
	    });
		
    
    var pagingBarFunc = new Ext.PagingToolbar({
        store: funcListStore,
        displayInfo: true,
        displayMsg : '当前显示第 {0} - {1} 条记录，一共 {2} 条',
		emptyMsg:'没有数据!'
    });
    


	
    var menuGrid = new Ext.grid.GridPanel({
        store: menuListStore,
        cm: menuCm,
        border : true,
        split:true,
        region : "center",
        loadMask : true,
        enableColumnMove: false,
        stripeRows: true,
        iconCls : 'grid-icon',
        //title:'菜单查询结果',
        bbar: pagingBarMenu
    });
    
   var funcSearchForm=new Ext.FormPanel( { //定义增加内容的表单
			height : 40,
			collapsible : false,
			region : "north",
			frame:true,
			border : false,
			split:true,
			buttonAlign:'center',
			labelAlign : 'left',
			items : [{
			layout : 'column',
			items : [
					{
						columnWidth : .3,
						layout : 'form',
						labelWidth:60,
						items : [
								 new Ext.form.TextField( {
									fieldLabel : '应用名称',
									id:'b_appName',
									width : 120
								})]
					},{
						columnWidth : .3,
						layout : 'form',
						labelWidth:60,
						items : [
								new Ext.form.TextField( {
									fieldLabel : '功能名称',
									id:'b_funcName',
									width : 120
								})]
					},{
						columnWidth : .3,
						labelWidth:70,
						layout : 'form',
						items : [ new Ext.form.TextField( {
							fieldLabel : '功能组名称',
							id:'b_funcGroupName',
							width : 120
						})]
					},{
						columnWidth : .1,
						labelWidth:70,
						layout : 'form',
						items : [new Ext.Button({text:'查询',handler:loadFuncListGrid,iconCls : 'icon-search'})]
					}
				]
			}]
		});
    
    var funcListGrid = new Ext.grid.GridPanel({
        store: funcListStore,
        cm: funcCm,
        region : "center",
        border : false,
        loadMask : true,
        title:'可设置成菜单的功能列表:',
        sm:sm,
        enableColumnMove: false,
        stripeRows: true,
        iconCls : 'grid-icon',
        bbar: pagingBarFunc,//onFuncSelect
        listeners :{ 
           celldblclick  : function(thisGrid, rowIndex, columnIndex, ev ){
              onFuncSelect();
            }
         }
    });
    
    
	function loadFuncListGrid(){
	  funcListStore.load( {
				params : {
					start : 0,
					limit : pageSize
				}
			});
	}
	
	//显示功能名称列表
	function showFuncWindow(){
	 if(funcSelectWindow==null){
	    loadFuncListGrid();
	    funcSelectWindow = new Ext.Window( { //定义对话框
						width : 700,
						height : 560,
						shadow : true,
						layout:'border',
						title:'功能查询:',
						closeAction : 'hide', 
						modal : true,
						closable : true,
						minWidth : 600,
						minHeight : 540,
						buttons : [{
							text :'选择',
							iconCls : 'icon-tick',
							handler:onFuncSelect
						},{
							text :'清空',
							iconCls : 'icon-rss_delete',
							handler:onFuncClear
						},{
							text : '关闭',
							handler : function (){funcSelectWindow.hide()}
						}],
						items:[funcListGrid,funcSearchForm] //将定义的表单加到对话框里
					});
	  }
	  funcSelectWindow.show();
	}
	
  //父菜单树显示
  function showTreeWindow(){
    if(treeSelectWindow == null){
	    treeform = new Ext.form.FormPanel({
     	region : "north",
		//buttonAlign:'center',
		height : '100%',
		width : '100%',
		id:'treeform',
		collapsible : false,
		baseCls:'backgroundColor:FFFFFF',
		frame:true,
		//title:'菜单选择',
		split:true,
		border : false,
		labelAlign : 'left',
		items : [{
	     	columnWidth : 1.0,
			layout : 'form',
			labelWidth:60,
			items:[
	  		tree = new Ext.tree.TreePanel({
		       id : 'tree',
		       region:"west",
		       split:"true",
		       //title:"菜单管理",
		       autoScroll : true,
		       width:265,
		       height:335,
		       //checkModel: 'cascade',
			   onlyLeafCheckable: false,
		       animate : true,
		       //autoShow :true,
		       containerScroll : true,
		       lines:true,//节点之间连接的横竖线
		       rootVisible:false,//是否显示根节点
		       root: new Ext.tree.AsyncTreeNode({id:'rootId',text:'根节点'}),
		       loader : new Ext.tree.TreeLoader({
		                   dataUrl:'menu/allMenuNoSonAction.json'
		               })
		      })]
	   		}]
	    });
    
    tree.on("click",function(node,event){
			event.preventDefault();
            node.select();
            currentTreeNode = node;
             if(node.text == Ext.getCmp('m_menuName').getValue() || node.text == Ext.getCmp('i_menuName').getValue()){
		         Ext.Msg.alert("提示:","不能选自己做为父菜单，请重新选择！");
		         return;
		     }else if(currentTreeNode.text == menu.data.m_parentMenuLabel){
			   		menuLevel=Ext.getCmp('m_menuLevel').getValue()-1;
			 }
			if(node.getDepth() == 1){
		         if(optype=='1'){
			 		Ext.getCmp('m_parentsId').setValue("");
			 		Ext.getCmp('m_parentMenuLabel').setValue("");
			 	}else{
			 		Ext.getCmp('i_parentsId').setValue("");
			 		Ext.getCmp('i_parentMenuLabel').setValue("");
			 	}
		     }
		    else{
				 if(optype=='1'){
				 	Ext.getCmp('m_parentsId').setValue(currentTreeNode.id);
				 	Ext.getCmp('m_parentMenuLabel').setValue(currentTreeNode.text);
				 }else{
				 	Ext.getCmp('i_parentsId').setValue(currentTreeNode.id);
				 	Ext.getCmp('i_parentMenuLabel').setValue(currentTreeNode.text);
				 }
			 }
			 treeSelectWindow.hide();
			 Ext.getCmp('m_menuLevel').setValue(currentTreeNode.getDepth());
	});
			
    
    treeSelectWindow = new Ext.Window({
     title : '菜单选择',
     width : 280,
     height : 400,
     shadow : true,
     modal:true,
     closeAction : 'hide',
     minWidth : 300,
     minHeight : 100,
     items : treeform,
     buttons : [{
	      	text :'清空',
			iconCls : 'icon-rss_delete',
			handler:onTreeClear
	     },{
	      text : '关闭',
	      handler : function() {
	      treeSelectWindow.hide();
	      }
	     }]
    });
    }
    treeSelectWindow.show();
    treeform.getForm().reset();
}
    var conditionMenuForm=new Ext.FormPanel( {
			region : "north",
			buttonAlign:'center',
			height : 90,
			id:'conditionOrgForm',
			collapsible : false,
			frame:true,
			title:'查询条件',
			split:true,
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
									fieldLabel : '菜单编号',
									id : 'menuCode',
									width : 120,
									allowBlank : true
								}),new Ext.form.ComboBox( {
									fieldLabel: '菜单层次',
				                    name: 'menuLevel',
				                    id: 'menuLevel',
				                    allowBlank : true,
				                    displayField : 'text',
									mode : 'local',
									width:120,
									triggerAction : 'all',
									selectOnFocus : true,
									forceSelection : true,
									valueField : 'value',
									store : new Ext.data.SimpleStore( {
												fields : ['value', 'text'],
												data : [['0','0级'],
														['1', '1级'],
														['2', '2级'],
														['3', '3级'],
														['4', '4级'],
														['5', '5级']]
											})
								})]
					},
					{
						columnWidth : .25,
						labelWidth:60,
						layout : 'form',
						items : [ new Ext.form.TextField( {
							fieldLabel : '显示名称',
							id : 'menuLabel',
							width : 120,
							allowBlank : true
						}),
							new Ext.form.TextField( {
							fieldLabel : '菜单名称',
							id : 'menuName',
							width : 120,
							allowBlank : true
						})]
					},{
						columnWidth : .25,
						labelWidth:60,
						layout : 'form',
						items : [ {html:'<br><br>'},
							new Ext.Button({
							  text:'查询',
							  iconCls : 'icon-search',
							  handler:function(){loadGrid(menuListStore);}
							})]
					}]
		
			}]
		});
		
		
		
		
			var menup = new Ext.menu.Menu({
			 items:[
			 {text: '修改菜单',iconCls : 'icon-edit',handler:modifyMenu},
			 '-',
			 {text: '删除菜单',iconCls : 'icon-delete',handler:deleteMenu}
			 ]
			});
			
			var menuTop2 = new Ext.menu.Menu({
			 items:[
			 {text: '增加子菜单',iconCls : 'icon-add',handler:onAddSubMenu},
			 '-',
			 {text: '修改菜单',iconCls : 'icon-edit',handler:modifyMenu},
			 '-',
			 {text: '删除菜单',iconCls : 'icon-delete',handler:deleteMenu}
			 ]
			});
			
			var menuTop1 = new Ext.menu.Menu({
			 items:[
			 {text: '增加顶级菜单',iconCls : 'icon-add',handler:addTopMenu}
			 ]
			});
          
            menuTree = new Ext.tree.TreePanel({
		       id : 'menuTree',
		       region:"west",
		       split:"true",
		       title:"菜单管理",
		       autoScroll : true,
		       width:240,
		       //checkModel: 'cascade',
			   onlyLeafCheckable: false,
		       animate : true,
		       imgSrc:'foundation/images/',
		       autoShow :true,
		       containerScroll : true,
		       lines:true,//节点之间连接的横竖线
		       rootVisible:false,//是否显示根节点
		       root: new Ext.tree.AsyncTreeNode({id:'rootId',text:'根节点'}),
		       loader : new Ext.tree.TreeLoader({
		                   dataUrl:'menu/allMenuAction.json'
		               })
		      });  
             
             
     var p_menuList = new Ext.Panel({
		 layout : 'border',
          border : false,
			split : true,
			items:[conditionMenuForm,menuGrid],
			listeners:{
			 beforeshow  :function(p){
			  return true;
			 }
			}
		});
		
		var formInfo = new Ext.FormPanel({
        labelWidth: 135,
        border:false,
        frame:true,
        title:'菜单详细信息查看及修改',
        margins:'3 0 0 0',
        buttonAlign:'left',
        items: {
            xtype:'panel',
            defaults:{autoHeight:true, bodyStyle:'padding:10px'}, 
            items:[{
                layout:'form',
                defaults: {width: 230},
                defaultType: 'textfield',

                items: [{
                    fieldLabel: '父菜单显示名称',  
				    xtype:"panel",  
				    layout:"column",  
				    isFormField:true,
				    readOnly:true,
                    items: [
					    new Ext.form.TextField( {
						id : 'm_parentMenuLabel',
						name:'m_parentMenuLabel',
						width : 180,
						disabled:'disabled',
						//readOnly:true,
						allowBlank : true
					}),	
					    new Ext.Button({  									         
				        text:'请选择',
				        id:'m_parentId',
				        width : 50,  									        								  
				        listeners: {  
				            click: function(btn) {  
						    showTreeWindow();  
				           }  
				       }  
				   }),new Ext.form.TextField( {
						id : 'm_parentsId',
						name:'m_parentsId',
						width : 0,
						hidden:true
					})
			         ]  
                },{
                    fieldLabel: '菜单名称',
                    name: 'm_menuName',
                    id: 'm_menuName',
                    allowBlank : false
                },{
                    fieldLabel: '菜单显示名称',
                    name: 'm_menuLabel',
                    id: 'm_menuLabel',
                    allowBlank : false
                }, {
                    fieldLabel: '菜单代码',
                    name: 'm_menuCode',
                    id: 'm_menuCode',
                    allowBlank : false
                }, new Ext.form.ComboBox( {
									fieldLabel: '是否叶子菜单',
				                    name: 'm_isLeaf',
				                    id: 'm_isLeaf',
				                    allowBlank : false,
				                    displayField : 'text',
									mode : 'local',
									triggerAction : 'all',
									selectOnFocus : true,
									forceSelection : true,
									valueField : 'value',
									editable:false,
									store : new Ext.data.SimpleStore( {
												fields : ['value', 'text'],
												data : [['y','是'],['n', '否']]
											})
								}),//m_funcName
								{  
								    fieldLabel: '功能名称',  
								    xtype:"panel",//panel  
								    layout:"column",  
								    isFormField:true,  
								    items: [
								    new Ext.form.TextField( {
									id : 'm_funcName',
									width : 180,
									//disabled:'disabled',
									readOnly:true,
									allowBlank : false
								}),	
										    new Ext.Button({  									         
									        text:'请选择',
									        width : 50,  									        								  
									        listeners: {  
									            click: function(btn) {  
									             optype='1';
											     showFuncWindow();  
									           }  
									       }  
									   })
								         ]  
    								}
								,new Ext.form.TextField({
									fieldLabel : '功能代码',
									name:'m_funcCode',
									id:'m_funcCode',
									disabled:true
								}), new Ext.form.TextField({
									fieldLabel : '功能入口',
									name:'m_funcAtion',
									id:'m_funcAtion',
									disabled:true
								}), new Ext.form.ComboBox( {
									fieldLabel: '页面打开方式',
				                    name: 'OPENMODE_INFO',
				                    id: 'OPENMODE_INFO',
				                    displayField : 'text',
									mode : 'local',
									allowBlank : false,
									triggerAction : 'all',
									selectOnFocus : true,
									forceSelection : true,
									valueField : 'value',
									editable:false,
									value:'comm',
									store : new Ext.data.SimpleStore( {
												fields : ['value', 'text'],
												data : [['comm','普通链接']]
											})
								}), new Ext.form.NumberField({
								    fieldLabel: '显示顺序',
                    name: 'm_desplayOrder',
                    id: 'm_desplayOrder',
                    value:0,
                    allowBlank : false
								}), {
                    fieldLabel: '菜单层次',
                    name: 'm_menuLevel',
                    id: 'm_menuLevel',
                    disabled:'disabled'
                    //readOnly:true
                }, {
                    fieldLabel: '子节点数',
                    name: 'm_subCount',
                     id: 'm_subCount',
                     disabled:'disabled'
                    //readOnly:true
                }]
            }]
        },

        buttons: [
        	{
				text :'保存',
				handler:onEditSave
			},{
				text : '增加子菜单',
				id:'addSubButton',
				handler : onAddSubMenu
			}
        
        ],
			listeners:{
			 beforeshow  :function(p){
			  return true;
			 }
			}
    });
    
    
    
    var inputForm = new Ext.FormPanel({
        labelWidth: 135,
        border:true,
        width: 350,
        frame:true,
        title:'增加新菜单',
        buttonAlign:'left',
        items: {
            xtype:'panel',
            defaults:{autoHeight:true, bodyStyle:'padding:10px'}, 
            items:[{
                layout:'form',
               // defaults: {width: 300},
                defaultType: 'textfield',

                items: [{
                    fieldLabel: '父菜单显示名称',  
				    xtype:"panel",  
				    layout:"column",  
				    isFormField:true,
				    readOnly:true,
                    items: [
					    new Ext.form.TextField( {
						id : 'i_parentMenuLabel',
						name:'i_parentMenuLabel',
						width : 180,
						disabled:'disabled',
						//readOnly:true,
						allowBlank : true
					}),	
					    new Ext.Button({  									         
				        text:'请选择',
				        width : 50,  
				        id:'i_parentId',									        								  
				        listeners: {  
				            click: function(btn) {  
						    showTreeWindow();  
				           }  
				       }  
				   }),new Ext.form.TextField( {
						id : 'i_parentsId',
						name:'i_parentsId',
						width : 0,
						hidden:true
					})
			         ]
                },{
                    fieldLabel: '菜单名称',
                    name: 'i_menuName',
                    id: 'i_menuName',
                    width: 230,
                    allowBlank : false
                },{
                    fieldLabel: '菜单显示名称',
                    name: 'i_menuLabel',
                    id: 'i_menuLabel',
                    width: 230,
                    allowBlank : false
                }, {
                    fieldLabel: '菜单代码',
                    name: 'i_menuCode',
                    width: 230,
                    allowBlank : false,
                    id: 'i_menuCode'
                }, new Ext.form.ComboBox( {
									fieldLabel: '是否叶子菜单',
				                    name: 'i_isLeaf',
				                    id: 'i_isLeaf',
				                    width: 230,
				                    allowBlank : false,
				                    displayField : 'text',
									mode : 'local',
									triggerAction : 'all',
									selectOnFocus : true,
									forceSelection : true,
									valueField : 'value',
									editable:false,
									store : new Ext.data.SimpleStore( {
												fields : ['value', 'text'],
												data : [['y','是'],['n', '否']]
											})
								}),//i_funcName
								{  
								    fieldLabel: '功能名称',  
								    xtype:"panel",//panel  
								    layout:"column",  
								    isFormField:true,
								    width: 300,  
								    items: [
								    new Ext.form.TextField( {
									id : 'i_funcName',
									width : 180,
									//readOnly:true,
									//disabled:'disabled',
									readOnly:true,
									allowBlank : false
									}),	
										    new Ext.Button({  									         
									        text:'请选择',
									        width : 50,  									        								  
									        listeners: {  
									            click: function(btn) {  
									             optype='2';
											     showFuncWindow();  
									           }  
									       }  
									   }),new Ext.form.Label({
									   		id:'i_label',
									   		width : 70,
									   		hideLabel:true,
									   		style:'color:red',
									   		hidden:true,
									   		text:'请选择'
									   })
								         ]  
    								}
								,new Ext.form.TextField({
									fieldLabel : '功能代码',
									name:'i_funcCode',
									id:'i_funcCode',
									width: 230,
									disabled:true
								}),new Ext.form.TextField({
									fieldLabel : '功能入口',
									name:'i_funcAction',
									width: 230,
									disabled:true,
									id:'i_funcAction'
								}), new Ext.form.ComboBox( {
									fieldLabel: '页面打开方式',
				                    name: 'i_openMode',
				                    id: 'i_openMode',
				                    width: 230,
				                    displayField : 'text',
									mode : 'local',
									allowBlank : false,
									triggerAction : 'all',
									selectOnFocus : true,
									forceSelection : true,
									valueField : 'value',
									editable:false,
									value:'comm',
									store : new Ext.data.SimpleStore( {
												fields : ['value', 'text'],
												data : [['comm','普通链接']]
											})
								}), new Ext.form.NumberField({
								   fieldLabel: '显示顺序',
                    name: 'i_displayOrder',
                    id: 'i_displayOrder',
                    width: 230,
                    value:0,
                    allowBlank : false
								})]
            }]
        },

        buttons: [
           {
				text :'保存',
				handler:onAddSave
			},{
				text : '返回',
				handler : onReturn
			}
           ],
			listeners:{
			 beforeshow  :function(p){
			  return true;
			 }
			}
    });
		
		
		var cardPanel = new Ext.Panel({
          border : false,
          layout : 'card',
          region : 'center',
          margins:"3 0 0 0",
          activeItem : 0,
          items:[p_menuList,formInfo,inputForm]
		});

         
            
    var innerPanel=new Ext.Panel({
          layout : 'border',
          border : false,
			split : true,
			title:'菜单管理',
			iconCls : 'icon-plugin',
			items:[menuTree,cardPanel]
    });
    
    var bigTabPanel = new Ext.TabPanel({
               region : 'center',
		        activeTab: 0,
		        border : false,
		        plain:true,
		        frame:false,
		        items:[innerPanel]
    });
    
    function loadGrid(store){
      store.load( {
					params : {
						start : 0,
						limit : pageSize
					}
				});
    }
    
            function modifyMenu(){
            	formInfo.getForm().reset();
			   nodeLeftClick(currentNode);
			}
			
			
			function deleteMenu(){
			Ext.Msg.confirm("提示:","删除菜单会删除该菜单下的所有子菜单,确定删除?",function(btn){
			 if(btn=='yes'){
			 	Ext.Ajax.request({
					method : 'POST',
					url:'menu/deleteMenuById.json',
					params:{
						ids:currentNode.id
					},
					//成功时回调
					success:function(response,success,options){
						//获取响应的json字符串
						var menus = Ext.util.JSON.decode(response.responseTest);
						if(success){
							 Ext.Msg.alert('提示','删除成功！');
							 menuTree.getRootNode().reload();
							  if(tree != undefined && tree != null)
							{
								tree.getRootNode().reload();
							}
						}else{
							Ext.Msg.alert("提示：","删除失败！");
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
			
			function addTopMenu(){
			   inputForm.getForm().reset();
			    selectFuncObj = null;
			    Ext.getCmp('i_parentId').setDisabled(true);
			    //Ext.getCmp('i_isLeaf').setDisabled(true);
			    Ext.getCmp('i_isLeaf').setValue("n");
			    Ext.getCmp("i_parentMenuLabel").setValue("");
			    cardPanel.layout.setActiveItem(inputForm);
			}
			
			
			//功能名称选择按钮
			function onFuncSelect(){
			if(!funcListGrid.getSelectionModel().hasSelection()){
		         Ext.Msg.alert("提示:","请选择对应的功能!");
		         return;
		        }
			 selectFuncObj = funcListGrid.getSelectionModel().getSelected();
			 if(optype=='1'){
			 	Ext.getCmp('m_funcName').setValue(selectFuncObj.get("funcName"));
			 	Ext.getCmp('m_funcAtion').setValue(selectFuncObj.get("funcAction"));
			 	Ext.getCmp('m_funcCode').setValue(selectFuncObj.get("funcCode"));
			 }else{
			 	Ext.getCmp('i_funcName').setValue(selectFuncObj.get("funcName"));
			 	Ext.getCmp('i_funcAction').setValue(selectFuncObj.get("funcAction"));
			 	Ext.getCmp('i_funcCode').setValue(selectFuncObj.get("funcCode"));
			 }
            funcSelectWindow.hide();
			}
			
			
			//功能名称清空按钮
			function onFuncClear(){
			  if(optype=='1'){
			 	Ext.getCmp('m_funcName').setValue('');
			 	Ext.getCmp('m_funcAtion').setValue('');
			 	Ext.getCmp('m_funcCode').setValue('');
			 }else{
			 	Ext.getCmp('i_funcName').setValue('');
			 	Ext.getCmp('i_funcAction').setValue('');
			 	Ext.getCmp('i_funcCode').setValue('');
			 }
			 selectFuncObj = null;
              funcSelectWindow.hide();
			}
			
			
			//父菜单显示名称清空按钮
			function onTreeClear(){
			  if(optype=='1'){
			 	Ext.getCmp("m_parentMenuLabel").setValue("");
			 	Ext.getCmp("m_parentsId").setValue("");
			 }else{
			 	Ext.getCmp("i_parentMenuLabel").setValue("");
			 	Ext.getCmp("i_parentsId").setValue("");
			 }
              treeSelectWindow.hide();
			}
		
		//增加子菜单保存
			function onAddSave(){
			//if(Ext.getCmp("i_funcName").getValue() == ""){
			//	Ext.getCmp("i_label").setHidden(false);
			//}	
			if(inputForm.getForm().isValid()){
			
				Ext.Ajax.request({
					method : 'POST',
					url:'menu/insertMenu.json',
					params:{
						menuName:Ext.getCmp("i_menuName").getValue(),
						menuLabel:Ext.getCmp("i_menuLabel").getValue(),
						menuCode:Ext.getCmp("i_menuCode").getValue(),
						isLeaf:Ext.getCmp("i_isLeaf").getValue(),
						menuAction:Ext.getCmp('i_funcAction').getValue(),
						//displayOrder:Ext.getCmp("i_displayOrder").getValue(),
						//menuLevel:currentNode.getDepth()+1,
						menuLevel:currentNode.getDepth(),
						funcCode:Ext.isEmpty(selectFuncObj)?"":selectFuncObj.get("funcCode"),
					    parentsId:(currentNode.id=='rootId')?"":(currentNode.id),
					    displayOrder:Ext.getCmp("i_displayOrder").getValue(),
					    openMode:Ext.getCmp("i_openMode").getValue(),
					    appId:Ext.isEmpty(selectFuncObj)?"":selectFuncObj.get("appId")
						
					},
					//成功时回调
					success:function(response,options){
						
						//获取响应的json字符串
						var menus = Ext.util.JSON.decode(response.responseText);
						
						var menujson = {i_parentMenuLabel:menus.data.i_parentMenuLabel};
						inputForm.getForm().setValues(menujson);
						
						if(menus.success){
							 var node = new Ext.tree.TreeNode(
						      {
						       text:Ext.getCmp("i_menuLabel").getValue(),
						       leaf:true,
						       id:menus.data,
						       attributes:{isLeaf:Ext.getCmp("i_isLeaf").getValue()}
						       }
						      );
						      currentNode.leaf=false;
						      currentNode.appendChild(node);
						      currentNode.expand(false,false);
						      node.select();
						      nodeLeftClick(node);
						      Ext.Msg.alert('提示','增加菜单成功！');
							  menuTree.getRootNode().reload();
							 if(tree != undefined && tree != null)
							{
								tree.getRootNode().reload();
							}
						}else{
							Ext.Msg.alert("提示：","增加菜单失败！");
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
		
			
		//修改菜单信息保存
		function onEditSave(){
			if(formInfo.getForm().isValid()){
			   var menuLevel;
			
			   if (Ext.getCmp('m_parentMenuLabel').getValue()==menu.data.m_parentMenuLabel){
			       menuLevel=currentNode.getDepth();
			   			   
			   }
			  
			   else if(Ext.getCmp('m_parentMenuLabel').getValue()==""){
			  	if(currentTreeNode != null && currentTreeNode != undefined){
			  		menuLevel=currentTreeNode.getDepth()-1;
			  	}else{
			  		menuLevel=Ext.getCmp('m_menuLevel').getValue();
			  	}
			   }
			   else if (Ext.getCmp('m_parentMenuLabel').getValue()!=menu.data.m_parentMenuLabel) {
			       menuLevel=currentTreeNode.getDepth()-1;
			   }
			   
				Ext.Ajax.request({
					method : 'POST',
					url:'menu/updateMenuById.json',
					params:{
						menuId:currentNode.id,
						menuName:Ext.getCmp("m_menuName").getValue(),
						menuLabel:Ext.getCmp("m_menuLabel").getValue(),
						menuCode:Ext.getCmp("m_menuCode").getValue(),
						isLeaf:Ext.getCmp("m_isLeaf").getValue(),
						menuAction:Ext.getCmp("m_funcAtion").getValue(),
						displayOrder:Ext.getCmp("m_desplayOrder").getValue(),
						//menuLevel:Ext.getCmp("m_parentsId").getValue(),
						menuLevel:menuLevel,
						subCount:Ext.getCmp("m_subCount").getValue(),
						funcCode:Ext.getCmp("m_funcCode").getValue(),
						parentsId:Ext.getCmp('m_parentsId').getValue()
					},
					//成功时回调
					success:function(response,options){
						//获取响应的json字符串
						var menus = Ext.util.JSON.decode(response.responseText);
						
						if(menus.success){
							
							currentNode.setText(Ext.getCmp("m_menuLabel").getValue());
							//Ext.getCmp('m_menuLevel').setValue(menuLevel);
							if(Ext.getCmp("m_isLeaf").getValue()=='y')
		                	Ext.getCmp("addSubButton").setDisabled(true);
							else  Ext.getCmp("addSubButton").setDisabled(false);
							Ext.Msg.alert("提示：","修改成功！");
							menuTree.getRootNode().reload();
							if(tree != undefined && tree != null)
							{
								tree.getRootNode().reload();
							}
						}else{
							Ext.Msg.alert("提示：","修改失败！");
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
    
    //增加子菜单，显示父菜单名称
    function onAddSubMenu(){
	    inputForm.getForm().reset();
	    selectFuncObj = null;
	    Ext.getCmp('i_parentId').setDisabled(true);
	    Ext.getCmp("i_parentMenuLabel").setValue(currentNode.text);
	    cardPanel.layout.setActiveItem(inputForm);
    }
    
    //返回按钮
    function onReturn(){
     cardPanel.layout.setActiveItem(formInfo);
    }
    
    //单击鼠标根据menuId显示菜单详细信息
    function nodeLeftClick(node,ev){
	           currentNode = node;
	           if(node.getDepth()==1){
	            cardPanel.layout.setActiveItem(p_menuList);
	           }else {
	           	Ext.Ajax.request({
					method : 'POST',
					url:'menu/loadMenuById.json',
					params:{
						menuId:node.id
					},
					//成功时回调
					success:function(response,options){
						
						//获取响应的json字符串
						  menu   = Ext.util.JSON.decode(response.responseText);
						 // alert(menu.data);
						var menujson = {m_parentMenuLabel:menu.data.m_parentMenuLabel,
										m_menuName:menu.data.m_menuName,
										m_menuLabel:menu.data.m_menuLabel,
										m_menuCode:menu.data.m_menuCode,
										m_isLeaf:menu.data.m_isLeaf,
										m_funcAtion:menu.data.m_funcAtion,
										m_funcName:menu.data.m_funcName,
										m_desplayOrder:menu.data.m_desplayOrder,
										m_menuLevel:menu.data.m_menuLevel,
										m_subCount:menu.data.m_subCount,
										m_funcCode:menu.data.m_funcCode,
										m_parentsId:menu.data.m_parentsId};
						formInfo.getForm().setValues(menujson);
						if(node.attributes.attributes.isLeaf == 'y')
						Ext.getCmp("addSubButton").setDisabled(true);
						else Ext.getCmp("addSubButton").setDisabled(false);
						cardPanel.layout.setActiveItem(formInfo);
					},
					//失败
					failure:function(response, options){
						var responseJson =  Ext.util.JSON.decode(response.responseText);
						Ext.Msg.alert("提示:", "操作失败！", responseJson.errors);
					} 
				});
	            }
            }
            
    
    Ext.onReady(function(){
    
           menuTree.on("click",nodeLeftClick);
            
            menuTree.on("contextmenu",function(node,ev){
              ev.preventDefault();
              node.select();
              currentNode = node;
              nodeLeftClick(node,ev);
              if(node.getDepth()==1)
              menuTop1.showAt(ev.getXY());
              else if(node.attributes.attributes.isLeaf == 'n')
              menuTop2.showAt(ev.getXY());
              else menup.showAt(ev.getXY());
             });
             
      var viewport = new Ext.Viewport({
			layout : 'border',
			split : true,
			items:[bigTabPanel]
			});
			loadGrid(menuListStore);
			menuTree.getRootNode().expand(false, false);
    });
    </script>
  </body>
  
</html>
