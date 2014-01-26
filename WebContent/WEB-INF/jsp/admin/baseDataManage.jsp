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
    <title>基础数据管理</title>	
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
    var addWindow=null;
    var infoForm=null;
    var currentOperatorId;
    var allRoles;
    var optype;//optype=0为增加,optype=1为修改
    var modifyOperatorId;
    var sm = new Ext.grid.CheckboxSelectionModel();
    var updateJson=null;
    var point1Id;
    var point2Id;
    var point3Id;
    var point4Id;
    var point5Id;
    
    var measure1Id;
    var measure2Id;
    var measure3Id;
    var measure4Id;
    var measure5Id;
    
    
    var record1=[
    
   
		{name : 'id',type : 'string'}, 
		{name : 'areaCode',type : 'string'},
		{name : 'name',type : 'string'}, 
		{name : 'type',type : 'string'},
		{name : 'pointLongitude',type : 'string'},
		{name : 'pointLatitude',type : 'string'},
		{name : 'pointHeight',type : 'string'}
		
	
		];
				
	var recordHeads = Ext.data.Record.create(record1);
	//定义查询数据的url
    var datastore = new Ext.data.Store( {
		proxy:new Ext.data.HttpProxy(new Ext.data.Connection({timeout:0,url:'hidden/searchHiddenDangers.json'})),
		reader : new Ext.data.JsonReader( {
			root : 'data.arr',
			totalProperty : 'data.totalProperty'
		}, recordHeads),

		remoteSort : false
	});
	
   /*	datastore.on('beforeload', function() { 
				datastore.baseParams = {
					type:Ext.getCmp("search_type").getValue(),
				    name:Ext.getCmp("search_point").getValue()
				};
			});
	*/
	//定义页面查询表格
	var datacm = new Ext.grid.ColumnModel({
	
       columns:[sm,
				{
			header:'序号',
			width:100,
			hidden:true,
			dataIndex : 'id'
		},{
			header:'灾害类型',
			width:100,
			dataIndex : 'type'
		}, {
			header : '地点',
			width:250,
			dataIndex:'name'
		}, {
			header : '所在区域',
			hidden:true,
			dataIndex:'areaCode',
			width:120
		}, {
			header : '经度',
			dataIndex:'pointLongitude',
			width:120
		}, {
			header : '纬度',
			dataIndex:'pointLatitude',
			width:120
		}, {
			header : '高度',
			dataIndex:'pointHeight',
			width:120
		}
		
		],
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
								
								
									 new Ext.form.ComboBox({						
							fieldLabel: '灾害类型',
		                    id: 'search_type',
		                    valueField : 'value',				
		                    allowBlank : true,
		                    displayField : 'text',
							mode : 'local',
							width:150,
							triggerAction : 'all',
							editable:true,
							selectOnFocus : false,
							forceSelection : true,
							                   
							store :  new Ext.data.SimpleStore( {
													fields : ['value', 'text'],
													data : [['POINT','泥石流'],['POLYGON','山洪沟']]
												}) 
						})
								
								]
					},
					{
						columnWidth : .25,
						labelWidth:60,
						layout : 'form',
						items : [ new Ext.form.TextField( {
									fieldLabel : '地点',
									id : 'search_point',
									width : 150,
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
          title:'基础数据管理',
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
    
  function loadGrid(store){    				
	//带查询条件的数据分页查询
 //	alert(Ext.getCmp("search_udserId").getValue());    		
		   store.load( {
					params : {
						  type:Ext.getCmp("search_type").getValue(),
				          name:Ext.getCmp("search_point").getValue()
					}
				});
    }
    
    var resultArray =null;// = <xframe:write dictTypeId='APF_OPERSTATUS' type='array'/>;
    
    function operStateToString(value){
     return rendererDisplay(resultArray,value);
    }
    
 
    
  function onAdd(){
     optype='0';
     showWindow();
     addWindow.setTitle("增加基础数据")
      
     infoForm.getForm().reset(); 
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
                url: '',               
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
						labelWidth:90,
						items : [
								 new Ext.form.ComboBox({						
							fieldLabel: '灾害类型',
		                    id: 'add_infoType',
		                    valueField : 'value',				
		                    allowBlank : true,
		                    displayField : 'text',
							mode : 'local',
							width:240,
							triggerAction : 'all',
							editable:true,
							selectOnFocus : false,
							forceSelection : true,
							listeners : {  //主要这里添加监听事件     
										    select : function(combo, record, index) { 
										          if(Ext.getCmp("add_infoType").getValue()=="POINT"){
										           Ext.getCmp("jingweidu4").hide();
										           Ext.getCmp("jingweidu5").hide();
										           Ext.getCmp("jingweidu3").hide();
										           Ext.getCmp("jingweidu2").hide();
										          } else{
										            Ext.getCmp("jingweidu4").show();
										           Ext.getCmp("jingweidu5").show();
										           Ext.getCmp("jingweidu3").show();
										           Ext.getCmp("jingweidu2").show();
										          }
										                                    }     },
							store :  new Ext.data.SimpleStore( {
													fields : ['value', 'text'],
													data : [['POINT','泥石流'],['POLYGON','山洪沟']]
												}) 
						}),new Ext.form.TextField({
								fieldLabel : '所在区域',
								name:'add_areaCode',
								id:'add_areaCode',
								width:240,							
								allowBlank:true
							
								}),
							{
		                    fieldLabel: '经纬度(高度)4',  
						    xtype:"panel",  
						    layout:"column",  
						    isFormField:true,
						    id:"jingweidu4",
		                    items: [
							    new Ext.form.TextField( {
								id : 'jingdu4',
								emptyText :"经度",
								width : 80,
								allowBlank : true
							}),new Ext.form.TextField( {
								id : 'weidu4',
								emptyText :"纬度",
								width : 80,
								hidden:false
							}),	new Ext.form.TextField( {
								id : 'gaodu4',
								emptyText :"高度",
								width : 80,
								hidden:false
							})
							    
			         ]
                },{
		                    fieldLabel: '经纬度(高度)5',  
						    xtype:"panel",  
						    layout:"column",  
						    isFormField:true,
						    id:"jingweidu5",
		                    items: [
							    new Ext.form.TextField( {
								id : 'jingdu5',
								emptyText :"经度",
								width : 80,
								allowBlank : true
							}),new Ext.form.TextField( {
								id : 'weidu5',
								emptyText :"纬度",
								width : 80,
								hidden:false
							}),	new Ext.form.TextField( {
								id : 'gaodu5',
								emptyText :"高度",
								width : 80,
								hidden:false
							})
							    
			         ]
                }	
								
								
								]
					},{
						columnWidth : .50,
						labelWidth:90,
						layout : 'form',
						items : [													
								{
								    xtype:"textfield",
									fieldLabel : '地点',
									id:'add_infoName',
									width : 240,
									allowBlank : false
								},						
						   {
		                    fieldLabel: '经纬度(高度)1',  
						    xtype:"panel",  
						    layout:"column",  
						    isFormField:true,
						    id:"jingweidu1",
		                    items: [
							    new Ext.form.TextField( {
								id : 'jingdu1',
								
								emptyText :"经度",
								width : 80,
								allowBlank : true
							}),new Ext.form.TextField( {
								id : 'weidu1',
								
								emptyText :"纬度",
								width : 80,
								hidden:false
							}),	new Ext.form.TextField( {
								id : 'gaodu1',
								name:'gaodu1',
								emptyText :"高度",
								width : 80,
								hidden:false
							})
							    
			         ]
                },{
		                    fieldLabel: '经纬度(高度)2',  
						    xtype:"panel",  
						    layout:"column",  
						    isFormField:true,
						    id:"jingweidu2",
		                    items: [
							    new Ext.form.TextField( {
								id : 'jingdu2',
								emptyText :"经度",
								width : 80,
								allowBlank : true
							}),new Ext.form.TextField( {
								id : 'weidu2',
								emptyText :"纬度",
								width : 80,
								hidden:false
							}),	new Ext.form.TextField( {
								id : 'gaodu2',
								emptyText :"高度",
								width : 80,
								hidden:false
							})
							    
			         ]
                },{
		                    fieldLabel: '经纬度(高度)3',  
						    xtype:"panel",  
						    layout:"column",  
						    isFormField:true,
						    id:"jingweidu3",
		                    items: [
							    new Ext.form.TextField( {
								id : 'jingdu3',
								emptyText :"经度",
								width : 80,
								allowBlank : true
							}),new Ext.form.TextField( {
								id : 'weidu3',
								emptyText :"纬度",
								width : 80,
								hidden:false
							}),	new Ext.form.TextField( {
								id : 'gaodu3',
								emptyText :"高度",
								width : 80,
								hidden:false
							})
							    
			         ]
                }
						
						
						]
					}
				]
			},{
			layout : 'column',
			 xtype:'fieldset',
            title:'阀值:',
			items : [
					{
						columnWidth :.5,
						layout : 'form',						
						labelWidth:90,
						items : [
					
					        new Ext.form.TextField( {
								id : 'shichang1',
								fieldLabel: '时长1',
								width : 240,
								allowBlank : true
							}),  new Ext.form.TextField( {
								id : 'shichang2',
								fieldLabel: '时长2',
								width : 240,
								allowBlank : true
							}),  new Ext.form.TextField( {
								id : 'shichang3',
								fieldLabel: '时长3',
								width : 240,
								allowBlank : true
							}),  new Ext.form.TextField( {
								id : 'shichang4',
								fieldLabel: '时长4',
								width : 240,
								allowBlank : true
							}),  new Ext.form.TextField( {
								id : 'shichang5',
								fieldLabel: '时长5',
								width : 240,
								allowBlank : true
							})
					
					
								 ]
					},	{
						columnWidth :.5,
						layout : 'form',						
						labelWidth:90,
						items : [
					         {
		                    fieldLabel: '临界值1(毫升)',  
						    xtype:"panel",  
						    layout:"column",  
						    isFormField:true,
						    id:"linjiezhi1",
		                    items: [
							    new Ext.form.TextField( {
								id : 'linjiezhi1_1',
								emptyText :"一级临界值",
								width : 60,
								allowBlank : true
							}),new Ext.form.TextField( {
								id : 'linjiezhi1_2',
								emptyText :"二级临界值",
								width : 60,
								hidden:false
							}),	new Ext.form.TextField( {
								id : 'linjiezhi1_3',
								emptyText :"三级临界值",
								width : 60,
								hidden:false
							}),	new Ext.form.TextField( {
								id : 'linjiezhi1_4',
								emptyText :"四级临界值",
								width : 60,
								hidden:false
							})
							    
			         ]
                },{
		                    fieldLabel: '临界值2(毫升)',  
						    xtype:"panel",  
						    layout:"column",  
						    isFormField:true,
						    id:"linjiezhi2",
		                    items: [
							    new Ext.form.TextField( {
								id : 'linjiezhi2_1',
								emptyText :"一级临界值",
								width : 60,
								allowBlank : true
							}),new Ext.form.TextField( {
								id : 'linjiezhi2_2',
								emptyText :"二级临界值",
								width : 60,
								hidden:false
							}),	new Ext.form.TextField( {
								id : 'linjiezhi2_3',
								emptyText :"三级临界值",
								width : 60,
								hidden:false
							}),	new Ext.form.TextField( {
								id : 'linjiezhi2_4',
								emptyText :"四级临界值",
								width : 60,
								hidden:false
							})
							    
			         ]
                },{
		                    fieldLabel: '临界值3(毫升)',  
						    xtype:"panel",  
						    layout:"column",  
						    isFormField:true,
						    id:"linjiezhi3",
		                    items: [
							    new Ext.form.TextField( {
								id : 'linjiezhi3_1',
								emptyText :"一级临界值",
								width : 60,
								allowBlank : true
							}),new Ext.form.TextField( {
								id : 'linjiezhi3_2',
								emptyText :"二级临界值",
								width : 60,
								hidden:false
							}),	new Ext.form.TextField( {
								id : 'linjiezhi3_3',
								emptyText :"三级临界值",
								width : 60,
								hidden:false
							}),	new Ext.form.TextField( {
								id : 'linjiezhi3_4',
								emptyText :"四级临界值",
								width : 60,
								hidden:false
							})
							    
			         ]
                },{
		                    fieldLabel: '临界值4(毫升)',  
						    xtype:"panel",  
						    layout:"column",  
						    isFormField:true,
						    id:"linjiezhi4",
		                    items: [
							    new Ext.form.TextField( {
								id : 'linjiezhi4_1',
								emptyText :"一级临界值",
								width : 60,
								allowBlank : true
							}),new Ext.form.TextField( {
								id : 'linjiezhi4_2',
								emptyText :"二级临界值",
								width : 60,
								hidden:false
							}),	new Ext.form.TextField( {
								id : 'linjiezhi4_3',
								emptyText :"三级临界值",
								width : 60,
								hidden:false
							}),	new Ext.form.TextField( {
								id : 'linjiezhi4_4',
								emptyText :"四级临界值",
								width : 60,
								hidden:false
							})
							    
			             ]
                      }	,{
		                    fieldLabel: '临界值5(毫升)',  
						    xtype:"panel",  
						    layout:"column",  
						    isFormField:true,
						    id:"linjiezhi5",
		                    items: [
							    new Ext.form.TextField( {
								id : 'linjiezhi5_1',
								emptyText :"一级临界值",
								width : 60,
								allowBlank : true
							}),new Ext.form.TextField( {
								id : 'linjiezhi5_2',
								emptyText :"二级临界值",
								width : 60,
								hidden:false
							}),	new Ext.form.TextField( {
								id : 'linjiezhi5_3',
								emptyText :"三级临界值",
								width : 60,
								hidden:false
							}),	new Ext.form.TextField( {
								id : 'linjiezhi5_4',
								emptyText :"四级临界值",
								width : 60,
								hidden:false
							})
							    
			             ]
                      }	
                      
                      				         										
					 ]
					}
					
					
				]
			}]
		});
      
          addWindow = new Ext.Window( { //定义对话框
						width : 800,
						height : 425,
						shadow : true,
						title: '用户信息:',
						closeAction : 'hide', //hide表示关闭的时候对话框是隐藏的，并没有真正的销毁，下次要用时在show出来
						modal : true,
						closable : true,
						minWidth : 800,
						layout : 'fit',
						minHeight : 425,
						buttons : [{
							text :'保存',
							id:'onSaveButton',
							handler:onSaveOrUpdate
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
     
    }
    
    function onSaveOrUpdate(){
         if(optype=="0"){
             onSave();
         
         }else if(optype=="1"){
           onUpdate()
         
         }
    
    
    }
    
    function onSave(){
      if(infoForm.getForm().isValid()){
          var jsonParams={
           //  infoId:null,	
	         infoName:Ext.getCmp('add_infoName').getValue(),	
			 areaCode:Ext.getCmp('add_areaCode').getValue(),	
			// areaName:null,	
			 infoType:Ext.getCmp('add_infoType').getValue(),
			 	
			// point1Id:null,	
			 point1Longitude:Ext.getCmp('jingdu1').getValue(),	
	         point1Latitude:Ext.getCmp('weidu1').getValue(),	
	         point1Height:Ext.getCmp('gaodu1').getValue(),
	         
	      //   point2Id:null,	
			 point2Longitude:Ext.isEmpty(Ext.getCmp('jingdu2').getValue())?'':Ext.getCmp('jingdu2').getValue(),	
	         point2Latitude:Ext.isEmpty(Ext.getCmp('weidu2').getValue())?'':Ext.getCmp('weidu2').getValue(),	
	         point2Height:Ext.isEmpty(Ext.getCmp('gaodu2').getValue())?'':Ext.getCmp('gaodu2').getValue(),
	         
	      //   point3Id:null,	
			 point3Longitude:Ext.isEmpty(Ext.getCmp('jingdu3').getValue())?'':Ext.getCmp('jingdu3').getValue(),	
	         point3Latitude:Ext.isEmpty(Ext.getCmp('weidu3').getValue())?'':Ext.getCmp('weidu3').getValue(),	
	         point3Height:Ext.isEmpty(Ext.getCmp('gaodu3').getValue())?'':Ext.getCmp('gaodu3').getValue(),
	         
	       //  point4Id:null,	
			 point4Longitude:Ext.isEmpty(Ext.getCmp('jingdu4').getValue())?'':Ext.getCmp('jingdu4').getValue(),	
	         point4Latitude:Ext.isEmpty(Ext.getCmp('weidu4').getValue())?'':Ext.getCmp('weidu4').getValue(),	
	         point4Height:Ext.isEmpty(Ext.getCmp('gaodu4').getValue())?'':Ext.getCmp('gaodu4').getValue(),
	         
	      //   point5Id:null,	
			point5Longitude:Ext.isEmpty(Ext.getCmp('jingdu5').getValue())?'':Ext.getCmp('jingdu5').getValue(),	
	         point5Latitude:Ext.isEmpty(Ext.getCmp('weidu5').getValue())?'':Ext.getCmp('weidu5').getValue(),	
	         point5Height:Ext.isEmpty(Ext.getCmp('gaodu5').getValue())?'':Ext.getCmp('gaodu5').getValue(),
	         
	         
		  //  measure1Id:null,	
			measure1Hour:Ext.getCmp('shichang1').getValue(),	
			measure1Level1:Ext.getCmp('linjiezhi1_1').getValue(),	
			measure1Level2:Ext.getCmp('linjiezhi1_2').getValue(),	
			measure1Level3:Ext.getCmp('linjiezhi1_3').getValue(),	
			measure1Level4:Ext.getCmp('linjiezhi1_4').getValue(),
			
	     //   measure2Id:null,	
			measure2Hour:Ext.getCmp('shichang2').getValue(),	
			measure2Level1:Ext.getCmp('linjiezhi2_1').getValue(),	
			measure2Level2:Ext.getCmp('linjiezhi2_2').getValue(),	
			measure2Level3:Ext.getCmp('linjiezhi2_3').getValue(),	
			measure2Level4:Ext.getCmp('linjiezhi2_4').getValue(),
			
		//	measure3Id:null,	
			measure3Hour:Ext.getCmp('shichang3').getValue(),	
			measure3Level1:Ext.getCmp('linjiezhi3_1').getValue(),	
			measure3Level2:Ext.getCmp('linjiezhi3_2').getValue(),	
			measure3Level3:Ext.getCmp('linjiezhi3_3').getValue(),	
			measure3Level4:Ext.getCmp('linjiezhi3_4').getValue(),
			
		//	measure4Id:null,	
			measure4Hour:Ext.getCmp('shichang4').getValue(),	
			measure4Level1:Ext.getCmp('linjiezhi4_1').getValue(),	
			measure4Level2:Ext.getCmp('linjiezhi4_2').getValue(),	
			measure4Level3:Ext.getCmp('linjiezhi4_3').getValue(),	
			measure4Level4:Ext.getCmp('linjiezhi4_4').getValue(),
			
		//	measure5Id:null,	
			measure5Hour:Ext.getCmp('shichang5').getValue(),	
			measure5Level1:Ext.getCmp('linjiezhi5_1').getValue(),	
			measure5Level2:Ext.getCmp('linjiezhi5_2').getValue(),	
			measure5Level3:Ext.getCmp('linjiezhi5_3').getValue(),	
			measure5Level4:Ext.getCmp('linjiezhi5_4').getValue()
          
          };
          
          
           Ext.Ajax.request({
                url: 'hidden/save.json',               
                params: jsonParams,
                method: 'POST',
                success:  function (response,options) {                                  
                    var responseJson =  Ext.util.JSON.decode(response.responseText);
               //     Ext.MessageBox.alert('成功', '从服务端获取结果: ' + response.responseText);                 
                    if(responseJson.success){                                     
				  //     Ext.MessageBox.alert('提示', '该功能名称已经存在，请重新输入一个！ ');
				         datastore.reload();
				         addWindow.hide();
				    }                
                },
                failure: function (response, options) {
                 
                    Ext.MessageBox.alert('失败', '操作失败，错误码：'+response.status);
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
      optype='1';
     
      Ext.Ajax.request({
                url: 'hidden/getHiddenDangerInfo.json',               
                params: {
                   id:dataGrid.getSelectionModel().getSelected().get("id")                    
                    },
                method: 'POST',
                success:  function (response,options) {                                  
                    var responseJson =  Ext.util.JSON.decode(response.responseText);
                    //    alert(response.responseText);  
                        
                        
                        
                          
                        
                                      
                    if(responseJson.success){                                     
				     var   values={
				         //    infoId:dataGrid.getSelectionModel().getSelected().get("id"),	
					         add_infoName:responseJson.data.infoName,	
							 add_areaCode:responseJson.data.areaName,	
							// areaName:null,	
							 add_infoType:responseJson.data.infoType,
							 	
							// point1Id:null,	
							 jingdu1:responseJson.data.point1Longitude,	
					         weidu1:responseJson.data.point1Latitude,	
					         gaodu1:responseJson.data.point1Height,
					         
					          jingdu2:responseJson.data.point2Longitude,	
					         weidu2:responseJson.data.point2Latitude,	
					         gaodu2:responseJson.data.point2Height,
					         
					          jingdu3:responseJson.data.point3Longitude,	
					         weidu3:responseJson.data.point3Latitude,	
					         gaodu3:responseJson.data.point3Height,
					         
					          jingdu4:responseJson.data.point4Longitude,	
					         weidu4:responseJson.data.point4Latitude,	
					         gaodu4:responseJson.data.point4Height,
					         
					         jingdu5:responseJson.data.point5Longitude,	
					         weidu5:responseJson.data.point5Latitude,	
					         gaodu5:responseJson.data.point5Height,
					         
					      //   point2Id:null,	
							
					         
					      //   point3Id:null,	
							
					         
					       //  point4Id:null,	
							
					         
					      //   point5Id:null,	
							
					         
					         
						  //  measure1Id:null,	
							shichang1:responseJson.data.measure1Hour,	
						    linjiezhi1_1:responseJson.data.measure1Level1,	
							linjiezhi1_2:responseJson.data.measure1Level2,	
							linjiezhi1_3:responseJson.data.measure1Level3,	
							linjiezhi1_4:responseJson.data.measure1Level4,
							
							
							shichang2:responseJson.data.measure2Hour,	
						    linjiezhi2_1:responseJson.data.measure2Level1,	
							linjiezhi2_2:responseJson.data.measure2Level2,	
							linjiezhi2_3:responseJson.data.measure2Level3,	
							linjiezhi2_4:responseJson.data.measure2Level4,
							
							shichang3:responseJson.data.measure3Hour,	
						    linjiezhi3_1:responseJson.data.measure3Level1,	
							linjiezhi3_2:responseJson.data.measure3Level2,	
							linjiezhi3_3:responseJson.data.measure3Level3,	
							linjiezhi3_4:responseJson.data.measure3Level4,
							
							shichang4:responseJson.data.measure4Hour,	
						    linjiezhi4_1:responseJson.data.measure4Level1,	
							linjiezhi4_2:responseJson.data.measure4Level2,	
							linjiezhi4_3:responseJson.data.measure4Level3,	
							linjiezhi4_4:responseJson.data.measure4Level4,
							
							shichang5:responseJson.data.measure5Hour,	
						    linjiezhi5_1:responseJson.data.measure5Level1,	
							linjiezhi5_2:responseJson.data.measure5Level2,	
							linjiezhi5_3:responseJson.data.measure5Level3,	
							linjiezhi5_4:responseJson.data.measure5Level4
							
					     //   measure2Id:null,	
							
						//	measure3Id:null,	
							
							
						//	measure4Id:null,	
							
							
						//	measure5Id:null,	
							
				          
				          };
				          point1Id=responseJson.data.point1Id;
					      point2Id=responseJson.data.point2Id;
					      point3Id=responseJson.data.point3Id;
					      point4Id=responseJson.data.point4Id;
					      point5Id=responseJson.data.point5Id;
					    
					      measure1Id=responseJson.data.measure1Id;
					      measure2Id=responseJson.data.measure2Id;
					      measure3Id=responseJson.data.measure3Id;
					      measure4Id=responseJson.data.measure4Id;
					      measure5Id=responseJson.data.measure5Id; 
				          
				         showWindow(); 
				         if(responseJson.data.infoType=="POINT"){
					           Ext.getCmp("jingweidu4").hide();
					           Ext.getCmp("jingweidu5").hide();
					           Ext.getCmp("jingweidu3").hide();
					           Ext.getCmp("jingweidu2").hide();
					          } else if (responseJson.data.infoType!="POINT"){
					            Ext.getCmp("jingweidu4").show();
					           Ext.getCmp("jingweidu5").show();
					           Ext.getCmp("jingweidu3").show();
					           Ext.getCmp("jingweidu2").show();
					          }
				        addWindow.setTitle("修改基础数据信息");
				        infoForm.getForm().reset();
				     //   Ext.getCmp("add_password").hide();
				    //    Ext.getCmp("add_password_confirm").hide();
						infoForm.getForm().setValues(values);
				  
				    }                
                },
                failure: function (response, options) {
                   
                    Ext.MessageBox.alert('失败', '操作失败！错误码：'+response.status);
                }
             });
       
								
    }
    
    function onUpdate(){
           if(infoForm.getForm().isValid()){
              
           Ext.Ajax.request({
                url: 'hidden/update.json',               
                params: {
			             infoId:dataGrid.getSelectionModel().getSelected().get("id"),	
				         infoName:Ext.getCmp('add_infoName').getValue(),	
						 areaCode:Ext.getCmp('add_areaCode').getValue(),	
						// areaName:null,	
						 infoType:Ext.getCmp('add_infoType').getValue(),
						 	
						 point1Id:point1Id,	
						 point1Longitude:Ext.getCmp('jingdu1').getValue(),	
				         point1Latitude:Ext.getCmp('weidu1').getValue(),	
				         point1Height:Ext.getCmp('gaodu1').getValue(),
				         
				         point2Id:point2Id,	
						 point2Longitude:Ext.isEmpty(Ext.getCmp('jingdu2').getValue())?'':Ext.getCmp('jingdu2').getValue(),	
				         point2Latitude:Ext.isEmpty(Ext.getCmp('weidu2').getValue())?'':Ext.getCmp('weidu2').getValue(),	
				         point2Height:Ext.isEmpty(Ext.getCmp('gaodu2').getValue())?'':Ext.getCmp('gaodu2').getValue(),
				         
				         point3Id: point3Id,	
						 point3Longitude:Ext.isEmpty(Ext.getCmp('jingdu3').getValue())?'':Ext.getCmp('jingdu3').getValue(),	
				         point3Latitude:Ext.isEmpty(Ext.getCmp('weidu3').getValue())?'':Ext.getCmp('weidu3').getValue(),	
				         point3Height:Ext.isEmpty(Ext.getCmp('gaodu3').getValue())?'':Ext.getCmp('gaodu3').getValue(),
				         
				         point4Id:point4Id,	
						 point4Longitude:Ext.isEmpty(Ext.getCmp('jingdu4').getValue())?'':Ext.getCmp('jingdu4').getValue(),	
				         point4Latitude:Ext.isEmpty(Ext.getCmp('weidu4').getValue())?'':Ext.getCmp('weidu4').getValue(),	
				         point4Height:Ext.isEmpty(Ext.getCmp('gaodu4').getValue())?'':Ext.getCmp('gaodu4').getValue(),
				         
				         point5Id:point5Id,	
						point5Longitude:Ext.isEmpty(Ext.getCmp('jingdu5').getValue())?'':Ext.getCmp('jingdu5').getValue(),	
				         point5Latitude:Ext.isEmpty(Ext.getCmp('weidu5').getValue())?'':Ext.getCmp('weidu5').getValue(),	
				         point5Height:Ext.isEmpty(Ext.getCmp('gaodu5').getValue())?'':Ext.getCmp('gaodu5').getValue(),
				         
				         
					    measure1Id:measure1Id,	
						measure1Hour:Ext.getCmp('shichang1').getValue(),	
						measure1Level1:Ext.getCmp('linjiezhi1_1').getValue(),	
						measure1Level2:Ext.getCmp('linjiezhi1_2').getValue(),	
						measure1Level3:Ext.getCmp('linjiezhi1_3').getValue(),	
						measure1Level4:Ext.getCmp('linjiezhi1_4').getValue(),
						
				        measure2Id:measure2Id,	
						measure2Hour:Ext.getCmp('shichang2').getValue(),	
						measure2Level1:Ext.getCmp('linjiezhi2_1').getValue(),	
						measure2Level2:Ext.getCmp('linjiezhi2_2').getValue(),	
						measure2Level3:Ext.getCmp('linjiezhi2_3').getValue(),	
						measure2Level4:Ext.getCmp('linjiezhi2_4').getValue(),
						
						measure3Id:measure3Id,	
						measure3Hour:Ext.getCmp('shichang3').getValue(),	
						measure3Level1:Ext.getCmp('linjiezhi3_1').getValue(),	
						measure3Level2:Ext.getCmp('linjiezhi3_2').getValue(),	
						measure3Level3:Ext.getCmp('linjiezhi3_3').getValue(),	
						measure3Level4:Ext.getCmp('linjiezhi3_4').getValue(),
						
						measure4Id:measure4Id,	
						measure4Hour:Ext.getCmp('shichang4').getValue(),	
						measure4Level1:Ext.getCmp('linjiezhi4_1').getValue(),	
						measure4Level2:Ext.getCmp('linjiezhi4_2').getValue(),	
						measure4Level3:Ext.getCmp('linjiezhi4_3').getValue(),	
						measure4Level4:Ext.getCmp('linjiezhi4_4').getValue(),
						
						measure5Id:measure5Id,	
						measure5Hour:Ext.getCmp('shichang5').getValue(),	
						measure5Level1:Ext.getCmp('linjiezhi5_1').getValue(),	
						measure5Level2:Ext.getCmp('linjiezhi5_2').getValue(),	
						measure5Level3:Ext.getCmp('linjiezhi5_3').getValue(),	
						measure5Level4:Ext.getCmp('linjiezhi5_4').getValue()
			                
                
                },
                method: 'POST',
                success:  function (response,options) {                                  
                    var responseJson =  Ext.util.JSON.decode(response.responseText);
                    Ext.MessageBox.alert('成功', '从服务端获取结果: ' + response.responseText);                 
                    if(responseJson.success){                                     
				  //     Ext.MessageBox.alert('提示', '该功能名称已经存在，请重新输入一个！ ');
				         datastore.reload();
				         addWindow.hide();
				    }                
                },
                failure: function (response, options) {
                 
                    Ext.MessageBox.alert('失败', '操作失败，错误码：'+response.status);
                }
             });
       
          
          
      }
              
    
    }
    
    
    function onSaveOver(mark){
    if(mark){
       addWindow.hide();
	   datastore.reload();
	   }
    }
    
  
	
    Ext.onReady(function(){    
       
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
