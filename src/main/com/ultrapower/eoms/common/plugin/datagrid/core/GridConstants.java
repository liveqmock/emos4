package com.ultrapower.eoms.common.plugin.datagrid.core;

public class GridConstants {
	
    public final static int DEFAULT_PAGE_SIZE = 20;
    
    public final static String HIDDEN_PAGES_SIZE = "var_pagesize";
	public final static String HIDDEN_TOTAL_PAGES = "var_totalpages";
	public final static String HIDDEN_TOTAL_ROWS = "var_totalrows";
	public final static String HIDDEN_CURRENT_PAGE = "var_currentpage";
	public final static String HIDDEN_GO_PAGE = "var_gopage";
	public final static String HIDDEN_CHECKBOX_SELECTVALUES = "var_selectvalues";//checkAll是存放的值
	public final static String HIDDEN_ISTRANFER="var_istranfer";//是否翻页提交
	
	public final static String HIDDEN_SORTFIELD="var_sortfield";//查询排序字段
	public final static String HIDDEN_SORTTYPE="var_sorttype";//排序方式 1 正序 0 倒序

	public final static String HIDDEN_MENUID="id";//此id为节点id 用于查询列表顶侧展示的页面路径
	
	public final static String TQUERY_NAME="tqueryname";//标题上查询字段选择的名字  --不能修改 在datagrid.js中用到了
	public final static String TQUERY_VALUE="tquerytext";//标题上查询字段选择的值  --不能修改 在datagrid.js中用到了
}
