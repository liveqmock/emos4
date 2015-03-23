package com.ultrapower.eoms.common.plugin.datagrid.tag;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.TagSupport;

import com.ultrapower.eoms.common.constants.Constants;
import com.ultrapower.eoms.common.core.component.cache.manager.BaseCacheManager;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.rquery.RQuery;
import com.ultrapower.eoms.common.core.component.rquery.core.QueryCondition;
import com.ultrapower.eoms.common.core.component.rquery.core.SqlResult;
import com.ultrapower.eoms.common.core.component.rquery.util.SqlReplace;
import com.ultrapower.eoms.common.core.util.Internation;
import com.ultrapower.eoms.common.core.util.NumberUtils;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.web.ActionContext;
import com.ultrapower.eoms.common.plugin.datagrid.core.DataGrid;
import com.ultrapower.eoms.common.plugin.datagrid.core.GridConstants;
import com.ultrapower.eoms.common.plugin.datagrid.grid.Limit;
import com.ultrapower.eoms.common.plugin.datagrid.util.RequestUtils;
import com.ultrapower.eoms.common.portal.model.UserSession;

public class DataGrideTag extends TagSupport {

	
	Limit limit=null;
	String var; 
	Collection items;
	int index=0;
	
	private String name;
	private String title;
	private String ititle;
	private DataGrid dataGrid;
	private String leftToolAre;//工具条左侧区域
	private String conditionAre;//查询条件区域
	private String tablerow;//行数据
	private String gridtitle;//
	private String action;
	
	private String sqlname;
	private DataTable datatable;
	
	private String mempage;//内存分页 true为内存分页
	private String cachemode;//缓存模式 sql:缓存sql ; data:缓存数据 all全缓存
	private String currentpage;
	private String pagesize;
	private String tquery;
	HashMap hashMap=null;//参数变量
	private String formid;
	private String pagetype = "";
	
	public String getFormid() {
		return formid;
	}

	public void setFormid(String formid) {
		this.formid = formid;
	}

	public void setInmap(HashMap hmap)
	{
		hashMap=hmap;
	}
	
	public HashMap getInmap()
	{
		return hashMap;
	}
	
	public String getSqlname() 
	{
		if(sqlname==null)
			sqlname="";
		return sqlname;
	}
	public void setSqlname(String sqlname) {
		this.sqlname = sqlname;
	}
	public String getAction() {
		if(action==null)
			action="";
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public void setGridtitle(String gridtitle)
	{
		this.gridtitle=gridtitle;
	}
	public void setTablerow(String tablerow)
	{
		this.tablerow=tablerow;
	}
	public void setLeftToolAre(String leftToolAre)
	{
		this.leftToolAre=leftToolAre;
	}
	public void setConditionAre(String conditionAre)
	{
		this.conditionAre=conditionAre;
		
	}

	public int doStartTag() {
		//this.title="";
		this.leftToolAre=null;
		this.conditionAre=null;
		this.gridtitle="";
		this.tablerow="";
		int pagesize=NumberUtils.formatToInt(this.getPagesize());
		int curpage=NumberUtils.formatToInt(this.currentpage);
		
		
		if(!this.getSqlname().equals("")&& items==null)
		{
			if(hashMap==null)
			{
				hashMap=new HashMap();
			}
			Limit limit=RequestUtils.getPageLimit((HttpServletRequest)pageContext.getRequest());
			String orderby=SqlReplace.getOrderby(limit.getSortField(),limit.getSortType());
			if(!"".equals(orderby))
			{
				hashMap.put("orderby", orderby);
			}
			HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
/*			String tName=StringUtils.checkNullString(request.getParameter(GridConstants.TQUERY_NAME));
			String tValue=StringUtils.checkNullString(request.getParameter(GridConstants.TQUERY_VALUE));
			//String tValue=StringUtils.checkNullString(request.getParameter(tName));
			if(!tName.equals("")&& !tValue.equals(""))
			{
				//if(hashMap==null)
				//	hashMap=new HashMap();
				hashMap.put(tName, tValue);
			}
*/			RQuery rQuery=new RQuery(this.sqlname,hashMap);
			if(pagesize<1 && curpage<1)
			{
				rQuery.setPage(limit.getPage()<=0?1:limit.getPage());
				rQuery.setPageSize(limit.getPageSize());
			}
			else
			{
				rQuery.setPage(curpage);
				rQuery.setPageSize(pagesize);				
			}
			rQuery.setIsCount(2);
			this.datatable=rQuery.getDataTable();
			limit.setRowAttributes(rQuery.getQueryResultSetCount(), 0);
			if(this.getCachemode().toLowerCase().equals("sql"))
			{
				SqlResult sqlResult=rQuery.getSqlResult();
				if(sqlResult!=null)
				{
					String cacheid="";
					UserSession usersession=ActionContext.getUserSession();
					if(usersession!=null)
					{
						cacheid=sqlname+"_"+usersession.getLoginName();
					}
					else
					{
						cacheid=sqlname+"_"+this.pageContext.getSession().getId();
					}
					BaseCacheManager.put(Constants.QUERYSQL,cacheid , sqlResult);
				}
			}
		}
		if(this.mempage!=null && "true".equals(this.mempage))
		{
			//内存排序
			Limit limit=RequestUtils.getPageLimit((HttpServletRequest)pageContext.getRequest());
			if(items!=null)
				limit.setRowAttributes(items.size() , 0);
			else if(datatable!=null)
				limit.setRowAttributes(datatable.length() , 0);
		}
		dataGrid=new DataGrid(pageContext,this.sqlname);
		
		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() 
	{
		dataGrid.setName(name);
		dataGrid.setAction(action);
		dataGrid.setTitle(title);
		dataGrid.setItitle(ititle);
		dataGrid.setLeftToolAre(leftToolAre);
		dataGrid.setTquery(this.getTquery());
		dataGrid.setFormid(this.getFormid());
		dataGrid.setPagetype(pagetype);
		if(!this.getSqlname().equals(""))
		{
			if(conditionAre!=null)
			{
			//conditionAre=StringUtils.checkNullString(conditionAre);
//			if(!conditionAre.equals(""))
//			{
				QueryCondition queryCondition=new QueryCondition(this.getSqlname()); 
				UserSession userSession = (UserSession)pageContext.getSession().getAttribute("userSession");
				Map<String,String> searchCondition = userSession.getSearchCondition();
				conditionAre=queryCondition.getCondition((HttpServletRequest)pageContext.getRequest(),hashMap,searchCondition.get(this.getSqlname()))+conditionAre;
//			}
			}
		}
		dataGrid.setConditionAre(conditionAre);
		dataGrid.setGridtitle(gridtitle);
		dataGrid.setTablerow(tablerow);
		dataGrid.dataView();
		return EVAL_PAGE;
	}
	
	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public Collection getItems() {
		return items;
	}

	public void setItems(Collection items) {
		this.items = items;
	}
	public String getTitle() {
		if(title==null)
			title="";
		return title;
	}
	public void setTitle(String title) {
/*		String strText=Internation.language(title);
		if(strText==null || "".equals(strText))
		{
			strText=title;
		}
		this.title = strText;*/
		this.title =title;
	}
	public String getItitle() {
		if(ititle == null)
			ititle = "";
		return ititle;
	}
	public void setItitle(String ititle) {
		this.ititle = ititle;
	}
	public void setDatatable(DataTable dt)
	{
		datatable=dt;
	}
	
	public DataTable getDatatable()
	{
		return datatable;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMempage() {
		return mempage;
	}
	public void setMempage(String mempage) 
	{
		this.mempage = mempage;
	}
	public String getCachemode() 
	{
		if(cachemode==null)
			cachemode="";
		return cachemode;
	}
	public void setCachemode(String cachemode) {
		this.cachemode = cachemode;
	}
	public String getCurrentpage() {
		return currentpage;
	}
	public void setCurrentpage(String currentpage) {
		this.currentpage = currentpage;
	}
	public String getPagesize() {
		return pagesize;
	}
	public void setPagesize(String pagesize) {
		this.pagesize = pagesize;
	}
	public String getTquery() {
		return tquery;
	}
	public void setTquery(String tquery) {
		this.tquery = tquery;
	}
	public String getPagetype() {
		return pagetype;
	}
	public void setPagetype(String pagetype) {
		this.pagetype = pagetype;
	}
}
