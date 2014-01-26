package com.plj.common.tag;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

@SuppressWarnings("finally")
public class TreeTag extends TagSupport {
	/**
	 * 
	 *  
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean border;
	private boolean rootVisible;
	private boolean split;
	private boolean frame;
	private boolean useArrows;
	private boolean autoScroll=true;
	private boolean autoHeight = false;
	
	private String dataUrl="";
	private String iconCls="";
	private String treeId="";
	private String region="";
	private String width="";
	private String height="";
	private String title="";
	private String rootText="";
	private String rootId="";
	private String layout="";
	private String baseParams="";
	private String tbar="";
	private String bodyStyle="";
	private String margins="";
	 
	
	public int doStartTag(){

		JspWriter out = null;
		try{
		out = pageContext.getOut();
		StringBuffer sb = new StringBuffer(" new Ext.tree.TreePanel({");
		sb.append("border:"+border+",");
		sb.append("rootVisible:"+rootVisible+",");
		sb.append("split:"+split+",");
		sb.append("autoHeight:"+autoHeight+",");
		sb.append("frame:"+frame+",");
		sb.append("autoScroll:"+autoScroll+",");
		sb.append("useArrows:"+useArrows+",");
		if(!margins.equals(""))
			sb.append("margins:'"+margins+"',");
		if(!bodyStyle.equals(""))
			sb.append("bodyStyle:'"+bodyStyle+"',");
		if(!iconCls.equals(""))
			sb.append("iconCls:'"+iconCls+"',");
		if(!treeId.equals(""))
			sb.append("id:'"+treeId+"',");
		if(!region.equals(""))
			sb.append("region:'"+region+"',");
		if(!tbar.equals(""))
			sb.append("tbar:"+tbar+",");
		if(!width.equals(""))
			sb.append("width:"+width+",");
		if(!height.equals(""))
			sb.append("height:"+height+",");
		if(!title.equals(""))
			sb.append("title:'"+title+"',");
//		if(!rootText.equals(""))
//			sb.append("rootText:'"+rootText+"',");
//		if(!rootId.equals(""))
//			sb.append("rootId:'"+rootId+"',");
		if(!layout.equals(""))
			sb.append("layout:'"+layout+"',");
		sb.append("root : new Ext.tree.AsyncTreeNode({text:'"+rootText+"',id:'"+rootId+"'}),");
		sb.append("loader: new Ext.tree.TreeLoader({ ");
		sb.append("dataUrl : '"+dataUrl+"',");//sys.basePath+
		sb.append("listeners :{");
		sb.append("beforeload:function(loader,node,callback){");
		sb.append("if('"+rootId+"'==Ext.getCmp('"+treeId+"').currentNodeId)");
		sb.append("Ext.getCmp('"+treeId+"').currentNodeId = '';");
		if(!baseParams.equals(""))
		sb.append("loader.baseParams = "+baseParams+";");
		sb.append("}");
		sb.append("}");
		sb.append("}),");
		sb.append("listeners : {");
		sb.append("beforeload  :function(node){");
		sb.append("Ext.getCmp('"+treeId+"').currentNodeId = node.id;");
		sb.append("return true; ");
		sb.append("}");
		sb.append("}");
		sb.append("}) ");
		out.print(sb.toString());
		
		}catch(Exception e){
			e.printStackTrace();
		}finally{
		   return SKIP_BODY;
		}
	}

	public void setDataUrl(String dataUrl) {
		this.dataUrl = dataUrl;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public void setBorder(boolean border) {
		this.border = border;
	}

	public void setRootVisible(boolean rootVisible) {
		this.rootVisible = rootVisible;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public void setUseArrows(boolean useArrows) {
		this.useArrows = useArrows;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public void setSplit(boolean split) {
		this.split = split;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setAutoScroll(boolean autoScroll) {
		this.autoScroll = autoScroll;
	}

	public void setRootText(String rootText) {
		this.rootText = rootText;
	}

	public void setRootId(String rootId) {
		this.rootId = rootId;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public void setFrame(boolean frame) {
		this.frame = frame;
	}

	public void setBaseParams(String baseParams) {
		this.baseParams = baseParams;
	}

	public void setTbar(String tbar) {
		this.tbar = tbar;
	}

	public void setBodyStyle(String bodyStyle) {
		this.bodyStyle = bodyStyle;
	}

	public void setMargins(String margins) {
		this.margins = margins;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public void setAutoHeight(boolean autoHeight) {
		this.autoHeight = autoHeight;
	}
}
