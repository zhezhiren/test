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
    <title>组织机构管理</title>	
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
	<link rel="stylesheet" type="text/css" href="css/multiselect.css"/>
    <script type="text/javascript" src="js/ext-3.4.0/ux/DDView.js"></script>
    <script type="text/javascript" src="js/ext-3.4.0/ux/MultiSelect.js"></script>
    <script type="text/javascript" src="js/ext-3.4.0/ux/ItemSelector.js"></script>
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
    var orgWindow,orgForm;
    var empWindow,empForm;
    var optype='1'; //1修改页面选择功能保存  2增加页面选择功能保存
	var eptype="";//0为增加保存 1为修改保存
    var orgId;
    var areaSelectWindow,opSelectWindow;
    var treeWindow,treeform;
    var selectAreaObj,selectOpObj;
    var orgtree;
    var emp;
    var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
    var sms = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
    var sm2 = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
	var allRoles;
          var record1=[
				{name : 'orgCode',type : 'string'}, 
				{name : 'orgName',type : 'string'},
				{name : 'orgType',type : 'string'}, 
				{name : 'status',type : 'string'},
				{name : 'orgLevel',type : 'string'},
				{name : 'area',type : 'string'},
				{name : 'orgId',type : 'string'}
				];
				
				var emps = Ext.data.Record.create([
				{name : 'empCode',type : 'string'}, 
				{name : 'empId',type : 'string'}, 
				{name : 'empName',type : 'string'},
				{name : 'gender',type : 'string'}, 
				{name : 'empStatus',type : 'string'},
				{name : 'orgId',type : 'string'},
				{name : 'orgName',type : 'string'},
				{name : 'ooperatorId',type : 'string'},
				{name : 'uuserId',type : 'string'},
				{name : 'employeeId',type : 'string'}
				]);
				
				var areas = Ext.data.Record.create([
				{name : 'area',type : 'string'}, 
				{name : 'upArea',type : 'string'}, 
				{name : 'areaName',type : 'string'},
				{name : 'nodelvl',type : 'string'}
				]);
            var orgs = Ext.data.Record.create(record1);
				
	    var orgstore = new Ext.data.Store( {
			proxy:new Ext.data.HttpProxy(new Ext.data.Connection({timeout:0,url:''})),

			reader : new Ext.data.JsonReader( {
				root : 'data.arr',
				totalProperty : 'data.totalProperty'
			}, orgs),

			remoteSort : false
		});
		
		 var empsstore = new Ext.data.Store( {
			proxy:new Ext.data.HttpProxy(new Ext.data.Connection({timeout:0,url:'employee/loadEmpByOrgId.json'})),

			reader : new Ext.data.JsonReader( {
				root : 'data.arr',
				totalProperty : 'data.totalProperty'
			}, emps),

			remoteSort : false
		});
		
		var areastore = new Ext.data.Store( {
			proxy:new Ext.data.HttpProxy(new Ext.data.Connection({timeout:0,url:'Organization/loadArea.json'})),

			reader : new Ext.data.JsonReader( {
				root : 'data.arr',
				totalProperty : 'data.totalProperty'
			}, areas),

			remoteSort : false
		});
		
			
	    empsstore.on('beforeload', function() { 
				empsstore.baseParams = {
					orgId:orgId,
					empCode:Ext.getCmp("empCode").getValue(),
					empName:Ext.getCmp("empName").getValue()
				};
			});
			
	    areastore.on('beforeload', function() { 
				areastore.baseParams = {
					area:Ext.getCmp("area").getValue(),
					areaName:Ext.getCmp("areaName").getValue()
				};
			});
			
			
		var empsCm = new Ext.grid.ColumnModel({ 
       columns:[
       		sm,
			{
			header:'员工编号',
			width:100,
			dataIndex : 'empCode'
		}, {
			header : '员工姓名',
			dataIndex:'empName'
		}, {
			header : '用户名',
			dataIndex:'uuserId'
		}, {
			header : '所属机构',
			width:150,
			dataIndex:'orgName'
		}, {
			header : '性别',
			dataIndex:'gender',
			renderer:function(value){
				if(value == "1"){
					return "男";
				}else if(value == "2"){
					return "女";
				}
			},
			width:120
			//renderer: sexRenderer
		}, {
			header : '状态',
			dataIndex:'empStatus',
			renderer:function(value){
				if(value == "noraml"){
					return "正常";
				}else if(value == "delete"){
					return "删除";
				}
			}
			//renderer:function(value){return rendererDisplay(<xframe:write dictTypeId='APF_EMPSTATUS' type='array'/>,value);}
		}],
		defaults: {
        sortable: false,
        menuDisabled: true
    }
		});
		
		
		var areaCm = new Ext.grid.ColumnModel({ 
       columns:[
       		sm2,
			{
			header:'区域代号',
			width:100,
			dataIndex : 'area'
		}, {
			header : '区域名称',
			width:150,
			dataIndex:'areaName'
		},{
			header : '上一级区域',
			dataIndex:'upArea'
		}, {
			header : '级别',
			dataIndex:'nodelvl'
		}],
		defaults: {
        sortable: false,
        menuDisabled: true
    }
		});
		
		var pagingBarEmps = new Ext.PagingToolbar({
	        pageSize: pageSize,
	        store: empsstore,
	        displayInfo: true
    	});
    	
    	var pagingBarAreas = new Ext.PagingToolbar({
	        pageSize: pageSize,
	        store: areastore,
	        displayInfo: true
	    });
	    
	   var empsGrid = new Ext.grid.GridPanel({
        store: empsstore,
        cm: empsCm,
        border : true,
        sm:sm,
        region : "center",
        loadMask : true,
        enableColumnMove: false,
        tbar:[{text:'增加',iconCls : 'icon-add',handler:onAddEmp},'-',{text:'修改',iconCls : 'icon-edit',handler:onUpdateEmp},'-',{text:'删除',iconCls : 'icon-delete',handler:onDeleteEmp}],
        stripeRows: true,
        iconCls : 'grid-icon',
        bbar: pagingBarEmps,
        listeners :{ 
           celldblclick  : function(thisGrid, rowIndex, columnIndex, ev ){
              onUpdateEmp();
            }
         }
    });
    
     var areaGrid = new Ext.grid.GridPanel({
        store: areastore,
        cm: areaCm,
        border : true,
        sm:sm2,
        region : "center",
        loadMask : true,
        enableColumnMove: false,
      //  tbar:[{text:'增加',iconCls : 'icon-add',handler:onAddEmp},'-',{text:'修改',iconCls : 'icon-add',handler:onUpdateEmp},'-',{text:'删除',iconCls : 'icon-delete',handler:onDeleteEmp}],
        stripeRows: true,
        iconCls : 'grid-icon',
        bbar: pagingBarAreas,
        listeners :{ 
           celldblclick  : function(thisGrid, rowIndex, columnIndex, ev ){
              onAreaSelect();
            }
         }
    });
		
		
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
		proxy:new Ext.data.HttpProxy(new Ext.data.Connection({timeout:0,url:'Organization/getOperatorNotEmployee.json'})),
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
			dataIndex : 'userId'
		}, {
			header : '姓名',
			width:120,
			dataIndex:'operatorName'
		}, {
			header : '状态',
			dataIndex:'status',
			width:80
		}, {
			header : '邮箱',
			width:160,
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
					userId:Ext.getCmp("search_udserId").getValue(),
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
						layout : 'form',
						labelWidth:50,
						items : [
								 new Ext.form.TextField( {
									fieldLabel : '登录名',
									id : 'search_udserId',
									width : 120,
									allowBlank : true
								})]
					},
					{
						columnWidth : .4,
						labelWidth:50,
						layout : 'form',
						items : [ new Ext.form.TextField( {
									fieldLabel : '用户名',
									id : 'search_operatorName',
									width : 120,
									allowBlank : true
								})]
					},{
						columnWidth : .2,
						labelWidth:60,
						layout : 'form',
						items : [ new Ext.Button({text:'查询',iconCls : 'icon-search',handler:function(){loadGrid(opstore);}})]
					}]
		
			}]
		});
		
	
		
		 //显示区域列表
	function showOpWindow(){
	 if(opSelectWindow==null){
	    loadOpListGrid();
	    opSelectWindow = new Ext.Window( { //定义对话框
						width : 500,
						height : 450,
						shadow : true,
						layout:'border',
						title:'用户查询',
						closeAction : 'hide', 
						modal : true,
						closable : true,
						minWidth : 500,
						minHeight : 450,
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
		
		
        //组织机构树显示
        orgtree = <foundation:extTree 
	           treeId="orgtree" 
	           dataUrl="Organization/getOrgTree.json" 
	           rootVisible="false" 
	           rootId="orgTreeRoot" 
	           border="true" 
	           iconCls="group-icon" 
	           region="west" 
	           width="210"
	           split="true" 
	           title="机构管理" 
	           rootText="机构树" 
	           margins="3 0 0 0"
           baseParams="{orgId:Ext.getCmp('orgtree').currentNodeId}"/>;
             
           orgtree.on("click",function(node,ev){
           		optype='0';
            	currentNode=node;
            	
            depth = node.getDepth();
            if (depth >= 1){
            		currentNode = node;	
            		orgId =node.id;
            		empsstore.load();
	         }else if(depth == 0){
	         	empsstore.load();
	         	orgstore.load();
	         }
            	
           });
           
     		var menup = new Ext.menu.Menu({
			 items:[
			 {text: '修改机构',iconCls : 'icon-edit',handler:modifyOrg},
			 '-',
			 {text: '删除机构',iconCls : 'icon-delete',handler:onDeleteOrg}
			 ]
			});
			
			var menuTop2 = new Ext.menu.Menu({
			 items:[
			 {text: '增加下一级机构',iconCls : 'icon-add',handler:onAddSubOrg},
			 '-',
			 {text: '修改机构',iconCls : 'icon-edit',handler:modifyOrg},
			 '-',
			 {text: '删除机构',iconCls : 'icon-delete',handler:onDeleteOrg}
			 ]
			});
			
			var menuTop1 = new Ext.menu.Menu({
			 items:[
			 {text: '增加顶级机构',iconCls : 'icon-add',handler:addTopOrg}
			 ]
			});
			
			
		var conditionEmpForm=new Ext.FormPanel( {
			title:'员工查询条件',
			id:'conditionEmpForm',
			region : "north",
			border : false,
			height : 65,
			frame:true,
			labelAlign : 'center',
			buttonAlign:'center',
			items : [{
			layout : 'column',
			items : [
					{
					
						columnWidth : .35,
						layout : 'form',
						labelWidth:60,
						items : [
								 new Ext.form.TextField( {
									fieldLabel : '员工姓名',
									id : 'empName',
								    width : 120,
									allowBlank : true
								})]
					},
					{
						columnWidth : .35,
						labelWidth:60,
						layout : 'form',
						items : [ new Ext.form.TextField( {
							fieldLabel : '员工编号',
							id : 'empCode',
							width : 120,
							allowBlank : true
						})]
					},{
						columnWidth : .3,
						width:60,
						layout : 'form',
						items : [
							new Ext.Button({
								text:'查询',
								iconCls : 'icon-search',
								handler:function(){loadGrid(empsstore);}})]
					}]
		
			}]
		
		});
		
	var formInfo = new Ext.FormPanel({
        labelWidth: 135,
        border:false,
        frame:true,
        title:'组织机构信息修改',
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
					fieldLabel: '父机构显示名称',  
				    xtype:"panel",  
				    layout:"column",  
				    isFormField:true,
				    readOnly:true,
                    items: [
					    new Ext.form.TextField( {
						id : 'm_parentOrgName',
						name:'m_parentOrgName',
						width : 180,
						disabled:'disabled',
						//readOnly:true,
						allowBlank : true
					}),	
					    new Ext.Button({  									         
				        text:'请选择',
				        width : 50,  
				        id:'m_parentsId',									        								  
				        listeners: {  
				            click: function(btn) {  
						    showTreeWindow();  
				           }  
				       }  
				   }),new Ext.form.TextField( {
						id : 'm_parentOrgId',
						name:'m_parentOrgId',
						width : 0,
						hidden:true
					})
			         ]
                },{
                    fieldLabel: '机构名称',
                    name: 'm_orgName',
                    id: 'm_orgName',
                    width: 230,
                    listeners:{blur:function(){
								if(Ext.getCmp('m_orgName').getValue() != org.data.orgName){
									checkUpdateOrgNameUsable();
								}
							}},
                    allowBlank : false
                }, {
                    fieldLabel: '机构代码',
                    name: 'm_orgCode',
                    width: 230,
                    allowBlank : false,
                    listeners:{blur:function(){
								if(Ext.getCmp('m_orgCode').getValue() != org.data.orgCode){
									checkUpdateOrgCodeUsable();
								}
							}},
                    id: 'm_orgCode'
                }, new Ext.form.ComboBox( {
									fieldLabel: '是否叶子机构',
				                    name: 'm_isLeaf',
				                    id: 'm_isLeaf',
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
								    fieldLabel: '所属区域',  
								    xtype:"panel",//panel  
								    layout:"column",  
								    isFormField:true,  
								    items: [
								    new Ext.form.TextField( {
									id : 'm_area',
									width : 0,
									hidden:true,
									//disabled:'disabled',
									readOnly:true,
									allowBlank : false
								}),new Ext.form.TextField( {
									id : 'm_areaName',
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
											    showAreaWindow();  
									           }  
									       }  
									   })
									]
								}
								]
            }]
        },
        buttons: [
        	{
				text :'保存',
				handler:onUpdateSaveOrg
			},{
				text : '增加下级机构',
				id:'addSubButton',
				handler : onAddSubOrg
			}
        
        ],
			listeners:{
			 beforeshow  :function(p){
			  return true;
			 }
			}
    });
    
     //机构树显示(选择上一级机构)
  function showTreeWindow(){
    if(treeWindow == null){
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
		       width:200,
		       height:300,
		       //checkModel: 'cascade',
			   onlyLeafCheckable: false,
		       animate : true,
		       autoShow :true,
		       useRadio:true,
		       containerScroll : true,
		       lines:true,//节点之间连接的横竖线
		       rootVisible:false,//是否显示根节点
		       root: new Ext.tree.AsyncTreeNode({id:'rootId',text:'根节点'}),
		       loader : new Ext.tree.TreeLoader({
		                   dataUrl:'Organization/getOrgTree.json'
		               })
		      })]
	   		}]
	    });
    
    tree.on("click",function(node,event){
			event.preventDefault();
            node.select();
            currentTreeNode = node;
             if(node.text == Ext.getCmp('i_orgName').getValue()||
             	node.text == Ext.getCmp('m_orgName').getValue()){
		         Ext.Msg.alert("提示:","不能选自己做为上一级机构，请重新选择！");
		         return;
		     }
			if(node.getDepth() == 1){
				if(optype == "0"){
					Ext.getCmp('i_parentOrgName').setValue("");
		 			Ext.getCmp('i_parentOrgId').setValue("");
				}else if(optype == "1"){
					Ext.getCmp('m_parentOrgName').setValue("");
		 			Ext.getCmp('m_parentOrgId').setValue("");
				}else{
					Ext.getCmp('e_orgName').setValue("");
			 		Ext.getCmp('e_orgId').setValue("");
				}
		     }
		    else{
		    	if(optype == "0"){
					Ext.getCmp('i_parentOrgName').setValue(node.text);
		 			Ext.getCmp('i_parentOrgId').setValue(currentTreeNode.id);
				}else if(optype == "1"){
					Ext.getCmp('m_parentOrgName').setValue(node.text);
		 			Ext.getCmp('m_parentOrgId').setValue(currentTreeNode.id);
				}else{
					Ext.getCmp('e_orgName').setValue(node.text);
			 		Ext.getCmp('e_orgId').setValue(currentTreeNode.id);
				}
			 }
			 
			 treeWindow.hide();
	});
			
    
    treeWindow = new Ext.Window({
     title : '上一级机构选择',
     width : 210,
     height : 360,
     shadow : true,
     modal:true,
     closeAction : 'hide',
     minWidth : 100,
     minHeight : 100,
     items : treeform,
     buttons : [{
	      	text :'清空',
			iconCls : 'icon-rss_delete',
			handler:onTreeClear
	     },{
	      text : '关闭',
	      handler : function() {
	      treeWindow.hide();
	      }
	     }]
    });
    }
    treeWindow.show();
    treeform.getForm().reset();
}       
    
    
     function loadOpListGrid(){
	  opstore.load( {
				params : {
					start : 0,
					limit : pageSize
				}
			});
	}
    
    function loadAreaListGrid(){
	  areastore.load( {
				params : {
					start : 0,
					limit : pageSize
				}
			});
	}

	var areaSearchForm=new Ext.FormPanel( { //定义增加内容的表单
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
						columnWidth : .4,
						layout : 'form',
						labelWidth:60,
						items : [
								 new Ext.form.TextField( {
									fieldLabel : '区域代号',
									id:'area',
									width : 120
								})]
					},{
						columnWidth : .4,
						layout : 'form',
						labelWidth:60,
						items : [
								new Ext.form.TextField( {
									fieldLabel : '区域名称',
									id:'areaName',
									width : 120
								})]
					},{
						columnWidth : .2,
						labelWidth:60,
						layout : 'form',
						items : [new Ext.Button({text:'查询',handler:loadAreaListGrid,iconCls : 'icon-search'})]
					}
				]
			}]
		});
    
    
    
    //显示区域列表
	function showAreaWindow(){
	 if(areaSelectWindow==null){
	    loadAreaListGrid();
	    areaSelectWindow = new Ext.Window( { //定义对话框
						width : 500,
						height : 500,
						shadow : true,
						layout:'border',
						title:'区域查询',
						closeAction : 'hide', 
						modal : true,
						closable : true,
						minWidth : 500,
						minHeight : 500,
						buttons : [{
							text :'选择',
							iconCls : 'icon-tick',
							handler:onAreaSelect
						},{
							text :'清空',
							iconCls : 'icon-rss_delete',
							handler:onAreaClear
						},{
							text : '关闭',
							handler : function (){areaSelectWindow.hide()}
						}],
						items:[areaGrid,areaSearchForm] //将定义的表单加到对话框里
					});
	  }
	  areaSelectWindow.show();
	}
    
    
    
    var inputForm = new Ext.FormPanel({
        labelWidth: 135,
        border:true,
        width: 350,
        frame:true,
        title:'增加新组织机构',
        buttonAlign:'left',
        items: {
            xtype:'panel',
            defaults:{autoHeight:true, bodyStyle:'padding:10px'}, 
            items:[{
                layout:'form',
               // defaults: {width: 300},
                defaultType: 'textfield',

                items: [{
                    fieldLabel: '上级机构显示名称',  
				    xtype:"panel",  
				    layout:"column",  
				    isFormField:true,
				    readOnly:true,
                    items: [
					    new Ext.form.TextField( {
						id : 'i_parentOrgName',
						name:'i_parentOrgName',
						width : 180,
						disabled:'disabled',
						//readOnly:true,
						allowBlank : true
					}),new Ext.form.TextField( {
						id : 'i_parentOrgId',
						name:'i_parentOrgId',
						width : 0,
						hidden:true
					}),	
					    new Ext.Button({  									         
				        text:'请选择',
				        width : 50,  
				        id:'i_parentsId',									        								  
				        listeners: {  
				            click: function(btn) {  
						    showTreeWindow();  
				           }  
				       }  
				   })
			         ]
                },{
                    fieldLabel: '机构名称',
                    name: 'i_orgName',
                    id: 'i_orgName',
                    width: 230,
                    listeners:{blur:function(){
								if(Ext.getCmp('i_orgName').getValue() != ""){
									checkOrgNameUsable();
								}
					}},
                    allowBlank : false
                }, {
                    fieldLabel: '机构代码',
                    name: 'i_orgCode',
                    width: 230,
                    listeners:{blur:function(){
								if(Ext.getCmp('i_orgCode').getValue() != ""){
									checkOrgCodeUsable();
								}
					}},
                    allowBlank : false,
                    id: 'i_orgCode'
                }, new Ext.form.ComboBox( {
									fieldLabel: '是否叶子机构',
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
									editable:true,
									store : new Ext.data.SimpleStore( {
												fields : ['value', 'text'],
												data : [['y','是'],['n', '否']]
											})
								}),//i_funcName
								{  
								    fieldLabel: '所属区域',  
								    xtype:"panel",//panel  
								    layout:"column",  
								    isFormField:true,  
								    items: [
								    new Ext.form.TextField( {
									id : 'i_area',
									width : 180,
									hidden:true,
									//disabled:'disabled',
									readOnly:true,
									allowBlank : false
								}),new Ext.form.TextField( {
									id : 'i_areaName',
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
											    showAreaWindow();  
									           }  
									       }  
									   })
									]
								}
								]
            }]
        },

        buttons: [
           {
				text :'保存',
				handler:onAddSaveOrg
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
    
    
    
    
    //增加组织机构窗口
		function onAddOrg(){
			optype="0";
	        showOrgWindow();
	        orgWindow.setTitle("增加组织机构");
      		orgForm.getForm().reset();
		}
		
		//增加员工窗口
		function onAddEmp(){
			optype="2";
	        showEmpWindow();
	        empWindow.setTitle("增加员工信息");
      		empForm.getForm().reset();
			Ext.getCmp("e_password").show();
     		Ext.getCmp("e_password_confirm").show();
			loadAllRoles();
			if(currentNode == "undefined" || currentNode == "" || currentNode == null){
				Ext.getCmp('e_orgName').setValue("");
	      		Ext.getCmp('e_orgId').setValue("");
			}else{
				Ext.getCmp('e_orgName').setValue(currentNode.text);
	      		Ext.getCmp('e_orgId').setValue(currentNode.id);
			}
      		
		}
      
	  
	  //增加或修改员工信息
       function onSaveOrUpdateEmp(){
       	if(optype == "2"){
       		onAddSaveEmp();
       	}else if(optype == "3"){
       		onUpdateSaveEmp();
       	}
	  }
	  
	  		//区域选择按钮
			function onAreaSelect(){
			if(!areaGrid.getSelectionModel().hasSelection()){
		         Ext.Msg.alert("提示:","请选择对应的功能!");
		         return;
		        }
			 selectAreaObj = areaGrid.getSelectionModel().getSelected();
			 if(optype == "0"){
       			Ext.getCmp('i_area').setValue(selectAreaObj.get("area"));
		 	 	Ext.getCmp('i_areaName').setValue(selectAreaObj.get("areaName"));
	       	}else if(optype == "1"){
	       		Ext.getCmp('m_area').setValue(selectAreaObj.get("area"));
		 	 	Ext.getCmp('m_areaName').setValue(selectAreaObj.get("areaName"));
	       	}
             areaSelectWindow.hide();
			}
			
			
			//区域清空按钮
			function onAreaClear(){
				if(optype == "0"){
	       			Ext.getCmp('i_area').setValue("");
			 	 	Ext.getCmp('i_areaName').setValue("");
		       	}else if(optype == "1"){
		       		Ext.getCmp('m_area').setValue("");
			 	 	Ext.getCmp('m_areaName').setValue("");
		       	}
			 	selectAreaObj = null;
              	areaSelectWindow.hide();
			}
			
			
			//用户名选择按钮
			function onOpSelect(){
			if(!opGrid.getSelectionModel().hasSelection()){
		         Ext.Msg.alert("提示:","请选择对应的功能!");
		         return;
		        }
			 selectOpObj = opGrid.getSelectionModel().getSelected();
    		 Ext.getCmp('e_operatorId').setValue(selectOpObj.get("operatorId"));
 	 		 Ext.getCmp('e_userId').setValue(selectOpObj.get("userId")); 
 	 		 Ext.getCmp('e_empName').setValue(selectOpObj.get("operatorName"));
             opSelectWindow.hide();
			}
			
			
			//用户名清空按钮
			function onOpClear(){
       			Ext.getCmp('e_operatorId').setValue("");
		 	 	Ext.getCmp('e_userId').setValue("");
			 	selectOpObj = null;
              	opSelectWindow.hide();
			}
			
			
			//上一级机构显示清空按钮
			function onTreeClear(){
				if(optype == "0"){
					Ext.getCmp("i_parentOrgId").setValue("");
			 		Ext.getCmp("i_parentOrgName").setValue("");
				}else if(optype == "1"){
					Ext.getCmp("m_parentOrgId").setValue("");
			 		Ext.getCmp("m_parentOrgName").setValue("");
				}
			 	
              	treeWindow.hide();
			}
	  
	
	 //显示员工的详细信息修改页面
	  function onUpdateEmp(){
      	if(!empsGrid.getSelectionModel().hasSelection()){
         Ext.Msg.alert("提示:","请选择要修改的记录!");
         return;
        }
        if(empsGrid.getSelectionModel().getSelections().length>1){
         Ext.Msg.alert("提示:","请选择一条记录进行修改!");
         return;
      	}
		Ext.Ajax.request({
					method : 'POST',
					//url:'employee/loadEmpById.json',
					url:'operator/loadOperatorByEmpId.json',
					params:{
						empId:empsGrid.getSelectionModel().getSelected().get('employeeId')
					},
					//成功时回调
					success:function(response,options){
						
						//获取响应的json字符串
						  emp   = Ext.util.JSON.decode(response.responseText);
						var e_empName = emp.data.empName;
					    var e_empCode  = emp.data.empCode;
					    var e_orgId  = emp.data.orgId;
					    var e_orgName  = emp.data.orgName;
					    var e_gender  = emp.data.gender;
						var status = emp.data.empStatus;
						var e_userId = emp.data.userId;
						var email = emp.data.email;
						//var e_password = emp.data.password;
						//var e_password_confirm = emp.data.password;
						var operatorId = emp.data.operatorId;
						
						
					    //var e_operatorId  = emp.data.operatorId;
						//var e_empName = empsGrid.getSelectionModel().getSelected().get('empName');
						//var e_empCode = empsGrid.getSelectionModel().getSelected().get('empCode');
						//var e_orgId = empsGrid.getSelectionModel().getSelected().get('orgId');
						//var e_orgName = empsGrid.getSelectionModel().getSelected().get('orgName');
						//var e_gender = empsGrid.getSelectionModel().getSelected().get('gender');
						//var status = empsGrid.getSelectionModel().getSelected().get('empStatus');
						if(emp.data.empStatus == "noraml"){
							status = "正常";
						}else{
							status = "不正常";
						}
					    //var e_userId = empsGrid.getSelectionModel().getSelected().get('uuserId');
						//var e_operatorName = empsGrid.getSelectionModel().getSelected().get('operatorName');
						//var status = empsGrid.getSelectionModel().getSelected().get('status');
						//var email  = empsGrid.getSelectionModel().getSelected().get('email');
						//var operatorId = empsGrid.getSelectionModel().getSelected().get('operatorId');
					    var values = {
									    e_empName:e_empName,
									    e_empCode:e_empCode,
									    e_orgId:e_orgId,
									    e_gender:e_gender,
									    e_orgName:e_orgName,
									    //e_operatorId:e_operatorId,
									    e_userId:e_userId,
										//e_operatorName:e_operatorName,
										e_status:status,
										e_email:email,
										//e_password_confirm:e_password_confirm,
										//e_password:e_password,
										e_operatorId:operatorId
								    };
					  // alert(response.responseText);
					    //optype="3";
				        //showEmpWindow(); 
				       // empWindow.setTitle("修改员工信息");
				       // empForm.getForm().reset();
				        //Ext.getCmp("savebtn").setDisabled(true);
						//empForm.getForm().setValues(values);
						Ext.Ajax.request({
							method : 'POST',
							//url:'employee/loadEmpById.json',
							url:'operator/loadOperatorRolesById.json',
							params:{
								empId:empsGrid.getSelectionModel().getSelected().get('employeeId')
							},
							//成功时回调
							success:function(response,options){
								
								//获取响应的json字符串
								 var rs   = Ext.util.JSON.decode(response.responseText);
								
								optype="3";
								showEmpWindow(); 
								empWindow.setTitle("修改员工信息");
								empForm.getForm().reset();
								//Ext.getCmp("savebtn").setDisabled(true);
								empForm.getForm().setValues(values);
												 
								if(!Ext.isEmpty(emp.data)){
									var itemselector = Ext.getCmp("itemselector");											
									itemselector.toData = []; 											 
									for(var i=0;i<rs.data.length;i++)
									   itemselector.toData.push([rs.data[i].roleId,rs.data[i].roleName]);    
										itemselector.reloadToStore();
										//loadFromStore();
										loadAllRoles();
								  }
							},
							//失败
							failure:function(response, options){
								var responseJson =  Ext.util.JSON.decode(response.responseText);
								Ext.Msg.alert("提示:", "操作失败！", responseJson.errors);
							} 
						});
					},
					//失败
					failure:function(response, options){
						var responseJson =  Ext.util.JSON.decode(response.responseText);
						Ext.Msg.alert("提示:", "操作失败！", responseJson.errors);
					} 
				});
      	
		
	}
      
      function showEmpWindow(){
      if(empWindow==null){
       empForm=new Ext.FormPanel( {
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
							fieldLabel : 'e_operatorId',
							id:'e_operatorId',
							hidden:true,
							width : 0,
							allowBlank : true
						},{
								   xtype:"textfield",
									fieldLabel : '登录名',
									id:'e_userId',
									width : 180,
									listeners:{blur:function(){
										if(Ext.getCmp('e_userId').getValue() != ""){
											checkUserIdUsable();
										}
									}},
									allowBlank : false
								},{
								   xtype:"textfield",
									fieldLabel : '员工编号',
									id:'e_empCode',
									width : 180,
									listeners:{blur:function(){
										if(optype == "2"){
											if(Ext.getCmp('e_empCode').getValue() != ""){
												checkEmpCodeUsable();
											}
										}else if(optype == "3"){
											if(Ext.getCmp('e_empCode').getValue() != "" && 
										  	   Ext.getCmp('e_empCode').getValue() != empsGrid.getSelectionModel().getSelected().get('empCode')){
												checkEmpCodeUsable();
											}
										}
									}},
									allowBlank : false
								},{
									xtype:'combo',
									valueField : 'value',
									displayField : 'text',
									fieldLabel : '性别',
									mode : 'local',
									name:'e_gender',
									id:'e_gender',
									selectOnFocus : false,
									editable : false,
									width : 180,
									allowBlank : true,
									triggerAction : 'all',
									emptyText:'请选择',
									allowBlank : false,
									store :  new Ext.data.SimpleStore( {
												fields : ['value', 'text'],
												data : [['1','男'],
													   ['2','女']]
											})
									},new Ext.form.TextField({
								fieldLabel : '登录密码',
								name:'e_password',
								id:'e_password',
								width:180,
								inputType: 'password',
								allowBlank:true
							//	listeners:{blur:function(field){
							//	       if(field.getValue()!=Ext.getCmp("add_password_confirm").getValue()){
							//        Ext.getCmp("add_password_confirm").markInvalid();  
							//        Ext.Msg.alert("提示:","两次密码输入不一致！请重新输入！"); }}}
								}),{
						 xtype:"textfield",
							fieldLabel : '邮箱',
							id:'e_email',
							vtype:'email',
							width : 180,
							allowBlank : true
						}
								]
					},{
						columnWidth : .50,
						labelWidth:60,
						layout : 'form',
						items : [
						{
						 			fieldLabel: '所属机构',  
								    xtype:"panel",//panel  
								    layout:"column",  
								    isFormField:true,  
								    items: [
								    new Ext.form.TextField( {
									id : 'e_orgId',
									width : 0,
									hidden:true,
									//disabled:'disabled',
									readOnly:true
									//allowBlank : false
								}),new Ext.form.TextField( {
									id : 'e_orgName',
									width : 130,
									emptyText:'请选择',
									//disabled:'disabled',
									readOnly:true,
									allowBlank : false
								}),	
										    new Ext.Button({  									         
									        text:'请选择',
									        width : 50,  									        								  
									        listeners: {  
									            click: function(btn) {  
											    showTreeWindow();  
									           }  
									       }  
									   })
									]},
						{columnWidth : 1,
								layout : 'form',
								labelWidth:60,
								items : [
								{
								    xtype:"textfield",
									fieldLabel : '员工姓名',
									id:'e_empName',
									width : 180,
									allowBlank : false
								}
								]
							},					
						new Ext.form.ComboBox({						
							fieldLabel: '状态',
		                    id: 'e_status',
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
						}),new Ext.form.TextField({fieldLabel : '确认密码',id:'e_password_confirm',name:'e_password_confirm',width:180,inputType: 'password',allowBlank:true,
						initialPassField: 'e_password',listeners:{blur:function(field){
								       if(field.getValue()!=Ext.getCmp("e_password").getValue()){
								       Ext.getCmp("e_password_confirm").markInvalid();
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
      
          empWindow = new Ext.Window( { //定义对话框
						width : 700,
						height : 500,
						shadow : true,
						title: '用户信息:',
						closeAction : 'hide', //hide表示关闭的时候对话框是隐藏的，并没有真正的销毁，下次要用时在show出来
						modal : true,
						closable : true,
						minWidth : 700,
						layout : 'fit',
						minHeight : 500,
						buttons : [{
							text :'保存',
							id:'onSaveButton',
							handler:onSave
							//handler:onSaveOrUpdateEmp
						},{
							text : '关闭',
							handler : function (){empWindow.hide();}
						}],
						items:[empForm] //将定义的表单加到对话框里
					});
        }
     //   Ext.getCmp("onSaveButton").setDisabled(false);
        empWindow.show();
    }
	
	function checkUserIdUsable(){
        // userExists.json
         Ext.Ajax.request({
                url: 'operator/userExists.json',               
                params: {
                   
                    useId:Ext.getCmp("e_userId").getValue()
                    },
                method: 'POST',
                success:  function (response,options) {                                  
                    var responseJson =  Ext.util.JSON.decode(response.responseText);
                  //  Ext.MessageBox.alert('成功', '从服务端获取结果123: ' + response.responseText);                 
                    if(!responseJson.success){                                     				  
				          Ext.MessageBox.alert('提示', '用户名： '+Ext.getCmp("e_userId").getValue() + ' 已经存在，请重新输入！  ');    
				    
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
      if(empForm.getForm().isValid()){
        var itemselector=Ext.getCmp("itemselector");
        if(optype=='2'){	        
	         insertOperator();
		 }else if(optype=='3'){		
		    updateOperator();		   
		 }
      }
    }
    function insertOperator(){    
           Ext.Ajax.request({
                url: 'operator/insertOperator.json',               
                params: {
					empCode:Ext.getCmp("e_empCode").getValue(),
					empName:Ext.getCmp("e_empName").getValue(),
					password:Ext.getCmp("e_password").getValue(),
                    userId:Ext.getCmp("e_userId").getValue(),
					orgId:Ext.getCmp("e_orgId").getValue(),
					gender:Ext.getCmp("e_gender").getValue(),
                    //operatorName:Ext.getCmp("e_operatorName").getValue(),
                    empStatus:Ext.getCmp("e_status").getValue(),
                    email:Ext.getCmp("e_email").getValue(),                
                    roleIds:(Ext.isEmpty(empForm.getForm().findField('itemselector').getValue())?null:empForm.getForm().findField('itemselector').getValue().split(","))                     
                    },
                method: 'POST',
                success:  function (response,options) {                                  
                    var responseJson =  Ext.util.JSON.decode(response.responseText);
                 //   Ext.MessageBox.alert('成功', '从服务端获取结果123: ' + response.responseText);                 
                    if(responseJson.success){                                     				  
				         empWindow.hide();
	                     empsstore.reload();
				  
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
                    operatorId:Ext.getCmp("e_operatorId").getValue(),
					empId:empsGrid.getSelectionModel().getSelected().get('employeeId'),
					empCode:Ext.getCmp("e_empCode").getValue(),
					empName:Ext.getCmp("e_empName").getValue(),
                    password:Ext.getCmp("e_password").getValue(),
                    userId:Ext.getCmp("e_userId").getValue(),
					orgId:Ext.getCmp("e_orgId").getValue(),
					gender:Ext.getCmp("e_gender").getValue(),
                    //operatorName:Ext.getCmp("e_operatorName").getValue(),
                    empStatus:Ext.getCmp("e_status").getValue(),
                    email:Ext.getCmp("e_email").getValue(),                
                    roleIds:(Ext.isEmpty(empForm.getForm().findField('itemselector').getValue())?null:empForm.getForm().findField('itemselector').getValue().split(","))                     
                    },
                method: 'POST',
                success:  function (response,options) {                                  
                    var responseJson =  Ext.util.JSON.decode(response.responseText);
 				   //alert(Ext.getCmp("e_orgId").getValue());
                  //  Ext.MessageBox.alert('成功', '从服务端获取结果123: ' + response.responseText);                 
                    if(responseJson.data){                                     
				   //   Ext.MessageBox.alert('提示', '该功能名称已经存在，请重新输入一个！ ');
				        empWindow.hide();
	                    empsstore.reload();
				   
				    }                
                },
                failure: function (response, options) {
                    var responseJson =  Ext.util.JSON.decode(response.responseText);
                    Ext.MessageBox.alert('失败', responseJson.errors);
                }
             }); 
    
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
					loadFromStore();
				  	     
					}
				},
				failure:function(response, options){
						var responseJson =  Ext.util.JSON.decode(response.responseText);
						Ext.Msg.alert("提示:", "操作失败！", responseJson.errors);
					} 
			});
 	}
	
	
      /* function showEmpWindow(){
      if(empWindow==null){
        empForm=new Ext.FormPanel( {
			//collapsible : true,
			frame:true,
			border:false,
			labelAlign : 'right',
			items : [{
			layout : 'column',
			 //xtype:'fieldset',
            //title:'基本信息:',
			items : [
					{columnWidth : .50,
						layout : 'form',
						labelWidth:60,
						items : [
								{
						 			fieldLabel: '用户名',  
								    xtype:"panel",//panel  
								    layout:"column",  
								    isFormField:true,  
								    items: [
								    new Ext.form.TextField( {
									id : 'e_operatorId',
									width : 0,
									hidden:true,
									//disabled:'disabled',
									readOnly:true
									//allowBlank : false
								}),new Ext.form.TextField( {
									id : 'e_userId',
									width : 100,
								    emptyText:'请选择',
									//disabled:'disabled',
									readOnly:true,
									allowBlank : false
								}),	
										    new Ext.Button({  									         
									        text:'请选择',
									        width : 50,  									        								  
									        listeners: {  
									            click: function(btn) {  
											    showOpWindow();  
									           }  
									       }  
									   })
									]},{
						 			fieldLabel: '所属机构',  
								    xtype:"panel",//panel  
								    layout:"column",  
								    isFormField:true,  
								    items: [
								    new Ext.form.TextField( {
									id : 'e_orgId',
									width : 0,
									hidden:true,
									//disabled:'disabled',
									readOnly:true
									//allowBlank : false
								}),new Ext.form.TextField( {
									id : 'e_orgName',
									width : 100,
									emptyText:'请选择',
									//disabled:'disabled',
									readOnly:true,
									allowBlank : false
								}),	
										    new Ext.Button({  									         
									        text:'请选择',
									        width : 50,  									        								  
									        listeners: {  
									            click: function(btn) {  
											    showTreeWindow();  
									           }  
									       }  
									   })
									]}
									
									
								]
							},{
						columnWidth : .50,
						layout : 'form',
						labelWidth:60,
						items : [
								 {
								   xtype:"textfield",
									fieldLabel : '员工编号',
									id:'e_empCode',
									width : 150,
									listeners:{blur:function(){
										if(optype == "2"){
											if(Ext.getCmp('e_empCode').getValue() != ""){
												checkEmpCodeUsable();
											}
										}else if(optype == "3"){
											if(Ext.getCmp('e_empCode').getValue() != "" && 
										  	   Ext.getCmp('e_empCode').getValue() != empsGrid.getSelectionModel().getSelected().get('empCode')){
												checkEmpCodeUsable();
											}
										}
									}},
									allowBlank : false
								},{
									xtype:'combo',
									valueField : 'value',
									displayField : 'text',
									fieldLabel : '性别',
									mode : 'local',
									name:'e_gender',
									id:'e_gender',
									selectOnFocus : false,
									editable : false,
									width : 150,
									allowBlank : true,
									triggerAction : 'all',
									emptyText:'请选择',
									allowBlank : false,
									store :  new Ext.data.SimpleStore( {
												fields : ['value', 'text'],
												data : [['1','男'],
													   ['2','女']]
											})
									}]
											
					},{columnWidth : 1,
								layout : 'form',
								labelWidth:60,
								items : [
								{
								    xtype:"textfield",
									fieldLabel : '员工姓名',
									id:'e_empName',
									width : 150,
									allowBlank : false
								}
								]
							}]
				}]
		});
		
		empWindow = new Ext.Window( { //定义对话框
						width : 480,
						height : 170,
						shadow : true,
						title: '增加员工',
						closeAction : 'hide', //hide表示关闭的时候对话框是隐藏的，并没有真正的销毁，下次要用时在show出来
						modal : true,
						closable : true,
						minWidth : 480,
						layout : 'fit',
						minHeight : 155,
						buttons : [{
							text :'保存',
							id:'onSaveButtonEmp',
							handler:onSaveOrUpdateEmp
						},{
							text : '关闭',
							handler : function (){empWindow.hide();}
						}],
						items:[empForm] //将定义的表单加到对话框里
					});
		
      }
     
      empWindow.show();
    }*/
    
    
    //检验新增时组织机构名称是否存在
    function checkOrgNameUsable(){
         Ext.Ajax.request({
                url: 'Organization/orgNameExists.json',               
                params: {
                    	orgName:Ext.getCmp("i_orgName").getValue()
                    },
                method: 'POST',
                success:  function (response,options) {                                  
                    var responseJson =  Ext.util.JSON.decode(response.responseText);

                    if(responseJson.data >= "1"){                                     				  
				          Ext.MessageBox.alert('提示', '机构代码:'+Ext.getCmp("i_orgName").getValue()+'  已经存在，请重新输入！  ');    
				    }                
                },
                failure: function (response, options) {
                    var responseJson =  Ext.util.JSON.decode(response.responseText);
                    Ext.MessageBox.alert('失败', responseJson.errors);
                }
             });
          }
          
          
    //检验修改时组织机构名称是否存在
    function checkUpdateOrgNameUsable(){
         Ext.Ajax.request({
                url: 'Organization/orgNameExists.json',               
                params: {
                    	orgName:Ext.getCmp("m_orgName").getValue()
                    },
                method: 'POST',
                success:  function (response,options) {                                  
                    var responseJson =  Ext.util.JSON.decode(response.responseText);

                    if(responseJson.data >= "1"){                                     				  
				          Ext.MessageBox.alert('提示', '机构代码:'+Ext.getCmp("m_orgName").getValue()+'  已经存在，请重新输入！  ');    
				    }                
                },
                failure: function (response, options) {
                    var responseJson =  Ext.util.JSON.decode(response.responseText);
                    Ext.MessageBox.alert('失败', responseJson.errors);
                }
             });
          }
    
    
    //检验新增时组织机构代码是否存在
    function checkOrgCodeUsable(){
         Ext.Ajax.request({
                url: 'Organization/orgCodeExists.json',               
                params: {
                    	orgCode:Ext.getCmp("i_orgCode").getValue()
                    },
                method: 'POST',
                success:  function (response,options) {                                  
                    var responseJson =  Ext.util.JSON.decode(response.responseText);

                    if(responseJson.data >= "1"){                                     				  
				          Ext.MessageBox.alert('提示', '机构代码:'+Ext.getCmp("i_orgCode").getValue()+'  已经存在，请重新输入！  ');    
				    }                
                },
                failure: function (response, options) {
                    var responseJson =  Ext.util.JSON.decode(response.responseText);
                    Ext.MessageBox.alert('失败', responseJson.errors);
                }
             });
          }
             
    
    //检验修改时组织机构代码是否存在
    function checkUpdateOrgCodeUsable(){
         Ext.Ajax.request({
                url: 'Organization/orgCodeExists.json',               
                params: {
                    	orgCode:Ext.getCmp("m_orgCode").getValue()
                    },
                method: 'POST',
                success:  function (response,options) {                                  
                    var responseJson =  Ext.util.JSON.decode(response.responseText);

                    if(responseJson.data >= "1"){                                     				  
				          Ext.MessageBox.alert('提示', '机构代码:'+Ext.getCmp("m_orgCode").getValue()+'  已经存在，请重新输入！  ');    
				    }                
                },
                failure: function (response, options) {
                    var responseJson =  Ext.util.JSON.decode(response.responseText);
                    Ext.MessageBox.alert('失败', responseJson.errors);
                }
             });
           }
             
        //检验员工编号是否存在     
        function checkEmpCodeUsable(){
         Ext.Ajax.request({
                url: 'Organization/empCodeExists.json',               
                params: {
                    	empCode:Ext.getCmp("e_empCode").getValue()
                    },
                method: 'POST',
                success:  function (response,options) {                                  
                    var responseJson =  Ext.util.JSON.decode(response.responseText);

                    if(responseJson.data >= "1"){                                     				  
				          Ext.MessageBox.alert('提示', '员工编号:'+Ext.getCmp("e_empCode").getValue()+'  已经存在，请重新输入！  ');    
				    }                
                },
                failure: function (response, options) {
                    var responseJson =  Ext.util.JSON.decode(response.responseText);
                    Ext.MessageBox.alert('失败', responseJson.errors);
                }
             });
           }
              
			
			function addTopOrg(){
			   inputForm.getForm().reset();
			    selectFuncObj = null;
			    Ext.getCmp('i_parentsId').setDisabled(true);
			    Ext.getCmp('i_isLeaf').setDisabled(true);
			    Ext.getCmp('i_isLeaf').setValue("n");
			    Ext.getCmp("i_parentOrgName").setValue("");
			    cardPanel.layout.setActiveItem(inputForm);
			}
			
			 //增加下级机构，显示上级机构名称
		    function onAddSubOrg(){
		    	optype = "0";
			    inputForm.getForm().reset();
			    selectFuncObj = null;
			    Ext.getCmp('i_parentsId').setDisabled(true);

			    Ext.getCmp('i_isLeaf').setDisabled(false);
			    Ext.getCmp('i_isLeaf').setValue('y');

			    Ext.getCmp("i_parentOrgName").setValue(currentNode.text);
			    Ext.getCmp("i_parentOrgId").setValue(currentNode.id);
			    cardPanel.layout.setActiveItem(inputForm);
		    }
		    
		    //返回按钮
		    function onReturn(){
		     cardPanel.layout.setActiveItem(formInfo);
		    }
		    
		    
		    //获得员工修改数据
		    /*function onUpdateEmp(){
		     	optype = "3";
		     	showEmpWindow(); 
        		empWindow.setTitle("修改员工信息");
			    Ext.Ajax.request({
					method : 'POST',
					url:'employee/loadEmpById.json',
					params:{
						empId:empsGrid.getSelectionModel().getSelected().get('employeeId')
					},
					//成功时回调
					success:function(response,options){
						
						//获取响应的json字符串
						  emp   = Ext.util.JSON.decode(response.responseText);
						  alert(emp.data.empName);
						var empjson = {e_empName:emp.data.empName,
										e_empCode:emp.data.empCode,
										e_orgId:emp.data.orgId,
										e_orgName:emp.data.orgName,
										e_gender:emp.data.gender,
										e_operatorId:emp.data.operatorId,
										e_userId:emp.data.userId};
										
						empForm.getForm().setValues(empjson);
						cardPanel.layout.setActiveItem(empForm);
						empForm.getForm().reset();
					},
					//失败
					failure:function(response, options){
						var responseJson =  Ext.util.JSON.decode(response.responseText);
						Ext.Msg.alert("提示:", "操作失败！", responseJson.errors);
					} 
				});
			}*/
		    
		    
		    //获得组织机构修改数据
		     function modifyOrg(){
		     	optype = "1";
            	formInfo.getForm().reset();
			    Ext.Ajax.request({
					method : 'POST',
					url:'Organization/loadOrgInfoById.json',
					params:{
						orgId:currentNode.id
					},
					//成功时回调
					success:function(response,options){
						
						//获取响应的json字符串
						  org   = Ext.util.JSON.decode(response.responseText);
						var orgjson = {m_parentOrgName:org.data.parentOrgName,
										m_orgName:org.data.orgName,
										m_parentOrgId:org.data.infoParentOrgId,
										m_orgCode:org.data.orgCode,
										m_isLeaf:org.data.isLeaf,
										m_area:org.data.infoArea,
										m_areaName:org.data.displayArea};
						formInfo.getForm().setValues(orgjson);
						if(currentNode.attributes.attributes.isLeaf == 'y')
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
			
			
			//删除组织机构信息
			function onDeleteOrg(){
			Ext.Msg.confirm("提示:","删除此机构将删除此机构下所有子机构，确定要删除?",function(btn){
			 if(btn=='yes'){
			 	Ext.Ajax.request({
					method : 'POST',
					url:'Organization/deleteOrg.json',
					params:{
						ids:currentNode.id
					},
					//成功时回调
					success:function(response,options){
						//获取响应的json字符串
						var dbjson = Ext.util.JSON.decode(response.responseText);

						if(dbjson.success){
							 Ext.Msg.alert('提示','删除成功！');
							 orgtree.getRootNode().reload();
							 if(org != undefined && org != null){
								org.getRootNode().reload();
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
			
			
			//增加组织机构信息保存
			function onAddSaveOrg(){
			if(inputForm.getForm().isValid()){
				Ext.Ajax.request({
					method : 'POST',
					url:'Organization/insertOrg.json',
					params:{
						orgCode:Ext.getCmp("i_orgCode").getValue(),
						orgName:Ext.getCmp("i_orgName").getValue(),
						parentOrgId:Ext.getCmp("i_parentOrgId").getValue(),
						isLeaf:Ext.getCmp("i_isLeaf").getValue(),
						area:Ext.getCmp("i_area").getValue()
					},
					//成功时回调
					success:function(response,options){
						
						//获取响应的json字符串
						var orgjson = Ext.util.JSON.decode(response.responseText);

						if(orgjson.success){
						      Ext.Msg.alert('恭喜','增加组织机构成功！');
							  orgtree.getRootNode().reload();
							  if(org != undefined && org != null){
								org.getRootNode().reload();
							  }
						}else{
							Ext.Msg.alert("提示：","增加组织机构失败！");
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
			
			
		//修改组织机构信息保存
		function onUpdateSaveOrg(){
		if(formInfo.getForm().isValid()){
				Ext.Ajax.request({
					method : 'POST',
					url:'Organization/updateOrgById.json',
					params:{
						orgId:currentNode.id,
						orgCode:Ext.getCmp("m_orgCode").getValue(),
						orgName:Ext.getCmp("m_orgName").getValue(),
						parentOrgId:Ext.getCmp("m_parentOrgId").getValue(),
						isLeaf:Ext.getCmp("m_isLeaf").getValue(),
						area:Ext.getCmp("m_area").getValue()
					},
					//成功时回调
					success:function(response,options){
						
						//获取响应的json字符串
						var orgjson = Ext.util.JSON.decode(response.responseText);
						
						if(Ext.getCmp("m_isLeaf").getValue()=='y')
	                	Ext.getCmp("addSubButton").setDisabled(true);
						else  Ext.getCmp("addSubButton").setDisabled(false);
						
						if(orgjson.success){
							Ext.Msg.alert('恭喜','修改成功！');
							orgtree.getRootNode().reload();
							if(org != undefined && org != null){
								org.getRootNode().reload();
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
			
			
			//删除员工信息
			function onDeleteEmp(){
			   if(!empsGrid.getSelectionModel().hasSelection()){
		         Ext.Msg.alert("提示:","请选择要删除的记录!");
		         return;
		        }
				Ext.Msg.confirm("提示:","确定要删除此员工信息?",function(btn){
			 if(btn=='yes'){
			 	var ids = new Array();
			   	var selects = empsGrid.getSelectionModel().getSelections();
			   	for(var i=0;i<selects.length;i++)
			   	ids.push(selects[i].get("employeeId"));
			 	Ext.Ajax.request({
					method : 'POST',
					url:'employee/deleteEmp.json',
					params:{
						ids:ids
					},
					//成功时回调
					success:function(response,options){
						//获取响应的json字符串
						var empjson = Ext.util.JSON.decode(response.responseText);

						if(empjson.success){
							 Ext.Msg.alert('提示','删除成功！');
							 empsstore.reload();
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
			
			
			//增加员工信息保存
		function onAddSaveEmp(){
			if(empForm.getForm().isValid()){
				Ext.Ajax.request({
					method : 'POST',
					url:'employee/insertEmp.json',
					params:{
						empCode:Ext.getCmp("e_empCode").getValue(),
						empName:Ext.getCmp("e_empName").getValue(),
						orgId:Ext.getCmp("e_orgId").getValue(),
						gender:Ext.getCmp("e_gender").getValue(),
						operatorId:Ext.getCmp("e_operatorId").getValue()
					},
					//成功时回调
					success:function(response,options){
						
						//获取响应的json字符串
						var empjson = Ext.util.JSON.decode(response.responseText);
						
						if(empjson.success){
						      Ext.Msg.alert('提示','增加员工成功！');
						      empWindow.hide();
							  empsstore.reload();
							  orgtree.getRootNode().reload();
						}else{
							Ext.Msg.alert("提示：","增加员工失败！");
						}
					},
					//失败
					failure:function(response, options){
						
						Ext.Msg.alert("提示:", "操作失败！错误码：", response.status);
					} 
				});
				}
			}
			
			
		//修改员工信息保存
		function onUpdateSaveEmp(){
		if(empForm.getForm().isValid()){
				Ext.Ajax.request({
					method : 'POST',
					url:'employee/updateEmp.json',
					params:{
						employeeId:empsGrid.getSelectionModel().getSelected().get('employeeId'),
						employeeStatus:empsGrid.getSelectionModel().getSelected().get('empStatus'),
						employeeCode:Ext.getCmp("e_empCode").getValue(),
						employeeName:Ext.getCmp("e_empName").getValue(),
						orgId:Ext.getCmp("e_orgId").getValue(),
						gender:Ext.getCmp("e_gender").getValue(),
						operatorId:Ext.getCmp("e_operatorId").getValue()
					},
					//成功时回调
					success:function(response,options){
						
						//获取响应的json字符串
						var orgjson = Ext.util.JSON.decode(response.responseText);
						
						if(orgjson.success){
							Ext.Msg.alert('提示：','修改成功！');
							empWindow.hide();
							empsstore.reload();
							orgtree.getRootNode().reload();
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
	
	
			
			 //单击鼠标根据menuId显示菜单详细信息
    function nodeLeftClick(node,ev){
           
            var   depth = node.getDepth();
          
            if (depth == 1 ) {
            	return;
            }else{
	            cardPanel.layout.setActiveItem(p_empList);
            }
     }
     
      
      var p_empList = new Ext.Panel({
		 layout : 'border',
          border : false,
			split : true,
			items:[conditionEmpForm,empsGrid],
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
          items:[p_empList,formInfo,inputForm]
		});    
     
      var innerPanel=new Ext.Panel({
          layout : 'border',
          border : false,
			split : true,
			title:'组织机构管理',
			iconCls : 'icon-plugin',
			items:[orgtree,cardPanel]
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
    
      
     Ext.onReady(function(){
      
      orgtree.on("click",nodeLeftClick);
            
      orgtree.on("contextmenu",function(node,ev){
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
			loadGrid(empsstore);
			orgtree.getRootNode().expand(false, false);	
    });
         
    </script>
  </body>
  
</html>
