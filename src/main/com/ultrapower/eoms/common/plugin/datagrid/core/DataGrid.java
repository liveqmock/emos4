package com.ultrapower.eoms.common.plugin.datagrid.core;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.jdom.Element;

import com.ultrapower.eoms.common.core.component.rquery.startup.StartUp;
import com.ultrapower.eoms.common.core.util.Internation;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.plugin.datagrid.grid.Limit;
import com.ultrapower.eoms.common.plugin.datagrid.util.RequestUtils;

public class DataGrid {

	private String name;
	private PageContext pageContext;
	private String leftToolAre;//工具条左侧区域
	private String conditionAre;//查询条件区域
	private String tablerow;//行数据
	private String gridtitle;//
	private String title;//标题
	private String ititle;//国际化的标题
	private String action;
	private String tquery;
	HttpServletRequest request;
	private String sqlName;
	private String formid;
	private String pagetype;
	
	public String getFormid() {
		return formid;
	}

	public void setFormid(String formid) {
		this.formid = formid;
	}

	public String getTquery() {
		return tquery;
	}

	public void setTquery(String tquery) {
		this.tquery = tquery;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public DataGrid(PageContext p_pageContext,String sqlName)
	{
		this.sqlName=sqlName;
		pageContext=p_pageContext;
	}

	/**
	 * 输出DataGrid视图
	 */
	public void dataView()
	{

		JspWriter out = pageContext.getOut();
		request=(HttpServletRequest)pageContext.getRequest();
		//System.out.println(request.getParameter("workSheetTitle2"));
		//System.out.println(request.getParameter("workSheetTitle"));
		String sorfield=StringUtils.checkNullString(request.getParameter(GridConstants.HIDDEN_SORTFIELD));
		String sorttype=StringUtils.checkNullString(request.getParameter(GridConstants.HIDDEN_SORTTYPE));
		String id = StringUtils.checkNullString(request.getParameter(GridConstants.HIDDEN_MENUID));
		if(sorttype.equals(""))
		{
			sorttype="0";//0升序 
		}
		Limit limit=RequestUtils.getWebLimit(request);
		int totalpage=0;
		if(limit!=null)
			totalpage=limit.getTotalPage();
		try 
		{
			//DataGridFnc.getJSFunction(out);
			String formid=StringUtils.checkNullString(this.getFormid());
			if(formid.equals(""))
			{
				formid="form1";
			}
			if(action!=null && !action.equals(""))
				out.println("<form id='"+formid+"' method='post' action='"+action+"'>");
			else
				out.println("<form id='"+formid+"' method='post'>");

			out.println("<div class='content'>");
			getTitle(out);
			
			this.getConditionAre(out);
			getToolBoor(out,limit);
			out.println("	<div class='scroll_div' id='center'>"); //<!--滚动条div-->

			out.println("	<table id='tab' class='tableborder'>");
			if(gridtitle!=null)
				out.println(this.gridtitle);
			if(tablerow!=null)
				out.println(tablerow);
			out.println("	</table>");
			//out.println("<div style=\"padding-left:30px;padding-top:30px;\"/>"); 

			//out.println("</div>");//out.println("<div class='scroll_div' id='center'>");
		
			
			out.println("	</div>");

			out.println("<div >");
			out.println("<input id='"+GridConstants.HIDDEN_TOTAL_PAGES+"' name='"+GridConstants.HIDDEN_TOTAL_PAGES+"' type='hidden' value='"+totalpage+"'/> ");
			out.println("<input id='"+GridConstants.HIDDEN_CHECKBOX_SELECTVALUES+"' name='"+GridConstants.HIDDEN_CHECKBOX_SELECTVALUES+"' type='hidden' value=''/> ");
			out.println("<input id='"+GridConstants.HIDDEN_ISTRANFER+"' name='"+GridConstants.HIDDEN_ISTRANFER+"' type='hidden' value=''/> ");
			out.println("<input id='"+GridConstants.HIDDEN_SORTFIELD+"' name='"+GridConstants.HIDDEN_SORTFIELD+"' type='hidden' value='"+sorfield+"'/> ");
			out.println("<input id='"+GridConstants.HIDDEN_SORTTYPE+"' name='"+GridConstants.HIDDEN_SORTTYPE+"' type='hidden' value='"+sorttype+"'/> ");
			out.println("<input id='"+GridConstants.HIDDEN_MENUID+"' name='"+GridConstants.HIDDEN_MENUID+"' type='hidden' value='"+id+"'/> ");
			out.println("</div>");
			out.println("</div>");
			out.println("</form>");
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getItitle() {
		return ititle;
	}

	public void setItitle(String ititle) {
		this.ititle = ititle;
	}

	private void getTitle(JspWriter out) throws IOException
	{
		
		if(title!=null && !"".equals(title))
		{
			out.println("<div class='title_right'>");
			out.println("	<div class='title_left'>");
			out.println("		<span class='title_bg'>");
			out.println("			<span class='title_icon2'>"+title+"</span>");
			out.println("			</span>");
			getTQueryHtml(out);
			out.println("		<span class='title_xieline'>");
			out.println("		</span>");
			out.println("	</div>");
			out.println("</div>");
		}
		else if(!"".equals(StringUtils.checkNullString(ititle)))
		{
			out.println("<div class='title_right'>");
			out.println("	<div class='title_left'>");
			out.println("		<span class='title_bg'>");
			out.println("			<span class='title_icon2'>");
			out.println(Internation.language(ititle));
			out.println("			</span>");
			out.println("		</span>");
			getTQueryHtml(out);
			out.println("		<span class='title_xieline'>");
			out.println("		</span>");
			out.println("	</div>");
			out.println("</div>");
		}
	}
	
	private void getTQueryHtml(JspWriter out)  throws IOException
	{
		
		out.println("<span style=\"float:right;\">");
		if(this.tquery!=null &&"true".equals(tquery))
		{
			String tName=StringUtils.checkNullString(request.getParameter(GridConstants.TQUERY_NAME));
			//String value=StringUtils.checkNullString(request.getParameter(tName));
			List lstCondition=this.getConditionElement();
			int lstLen=0;
			if(lstCondition!=null)
				lstLen=lstCondition.size();
			
			String value=StringUtils.checkNullString(request.getParameter(GridConstants.TQUERY_VALUE));
			
			out.println("<select name='"+GridConstants.TQUERY_NAME+"'> style=\"margin-top: 0px;\"");
			Element element;
			String istquery;
			for(int i=0;i<lstLen;i++)
			{
				element=(Element)lstCondition.get(i);
				String name=StringUtils.checkNullString(element.getAttributeValue("name"));
				istquery=StringUtils.checkNullString(element.getAttributeValue("tquery"));
				if(istquery.equals("true"))
				{
					String displayName=StringUtils.checkNullString(element.getAttributeValue("displayname"));
					String text=Internation.language(displayName);
					if(!text.equals(""))
						displayName=text;
					
					
					if(tName.equals(name))
					{
						out.println("<option value='"+name+"' selected > ");	
					}
					else
					{
						out.println("<option value='"+name+"'> ");
					}
					out.println(text);
					out.println("</option>");
				}
			}
			out.println("</select>");
			out.println("<input name='"+GridConstants.TQUERY_VALUE+"' style='width:200px' value='"+value+"' />" +
					"<input type='submit' value='搜索' onclick='tquerysubmit();' class='searchButton' onmouseover=\"this.className='searchButton_hover'\" onmouseout=\"this.className='searchButton'\"  />"
					+ "<input name='searchB' type='button' value='高级搜索' class='searchadv_button'  onclick='showsearch()' onmouseover=\"this.className='searchadv_button_hover'\" onmouseout=\"this.className='searchadv_button'\"/>"
					);
		}				
		out.println("</span>");
	}
	private List getConditionElement()
	{
		Element sqlqueryElement=null;
		if(StartUp.sqlmapElement!=null)
		{
			Object obj=StartUp.sqlmapElement.get(sqlName);
			if(obj!=null)
				sqlqueryElement=(Element)obj;
		}
		
		List lstCondition=null;
		if(sqlqueryElement!=null)
		{
			lstCondition=sqlqueryElement.getChildren("condition");
			Element ele=(Element)lstCondition.get(0);
			lstCondition=ele.getChildren();
				
		}
		return lstCondition;
	}
	
	private void getConditionAre(JspWriter out) throws IOException
	{
		if(conditionAre!=null && !conditionAre.equals(""))
		{
			if(name!=null)
			{
				out.println("<div id='"+name+"_serachdiv' class='serachdiv' style='display:none;'>");
			}
			else
			{
				out.println("<div id='serachdiv' class='serachdiv' style='display:none;'>");
			}
			out.println("	<div class='type_condition'><span>请输入查询条件</span></div>");
			out.println(conditionAre);
			out.println("</div>");
		}

	}
	/**
	 * 工具条
	 * @param out
	 * @param limit
	 * @throws IOException
	 */
	private void getToolBoor(JspWriter out,Limit limit)throws IOException
	{
		int number=0;
		if(leftToolAre==null)//如果没有lefttoobar工具栏标签
			return ;
		out.println("<div class='page_div_bg'>");//工具栏开始
		out.println("	<div class='page_div'>");//工具栏左侧开始
		out.println(leftToolAre);

		//out.println("</div >");
		out.println("	</div>");//工具栏左侧结束
	
		//工具栏右侧翻页开始
		if("simple".equals(StringUtils.checkNullString(pagetype)))
		{
			out.println("<span class='pagenumber'>");
			out.println("<ul>");
			out.println("<li class='firstpage' onclick=\"return tranferPage('frist')\"  onmouseover=\"this.className='firstpage_hover'\" onmouseout=\"this.className='firstpage'\" title='第一页'></li>");
			out.println("<li class='prepage' onclick=\"return tranferPage('previous')\" onmouseover=\"this.className='prepage_hover'\" onmouseout=\"this.className='prepage'\" title='上一页'></li>");
		    number=0;
		    int currentpage=0;
		    int pagesize=0;
		    int totalrows=0;
		    if(limit!=null)
			{
				number=limit.getTotalPage();
				currentpage=limit.getPage();
				pagesize=limit.getPageSize();
				totalrows=limit.getTotalRows();
			}
		    
			out.println("	<select id='"+GridConstants.HIDDEN_CURRENT_PAGE+"' name='"+GridConstants.HIDDEN_CURRENT_PAGE+"' onchange=\"return tranferPage('go')\" style=\"display:none\">  \n" );
			for(int i=1;i<(number+1);i++)
			{
				if(currentpage==i)
					out.println("<option  selected value='"+i+"'>"+i+"</option>");
				else
					out.println("<option value='"+i+"'>"+i+"</option>");
			}
			out.println("	</select>\n");
			out.println("<li style='font-size: 12.5px;line-height: 28px;'>"+currentpage+"/"+number+"页</li>");
			out.println("<li class='nextpage' onclick=\"return tranferPage('next')\" onmouseover=\"this.className='nextpage_hover'\" onmouseout=\"this.className='nextpage'\" title='下一页'></li>");
			out.println("<li class='lastpage' onclick=\"return tranferPage('last')\" onmouseover=\"this.className='lastpage_hover'\" onmouseout=\"this.className='lastpage'\" title='最后一页'></li>");
			out.println("<li><div class='page_split_line'></div></li>");
		    out.println("<li style='font-size: 12.5px;line-height: 28px;'>共"+totalrows+"条</li>");
		    out.println("</ul>");
			out.println("</span>"); //out.println("<span class='pagenumber'>");
		}
		else
		{
			out.println("<span class='pagenumber'>");
			out.println("<ul>");
			//out.println("<li>");
			out.println("	<li class='firstpage' onclick=\"return tranferPage('frist')\"  onmouseover=\"this.className='firstpage_hover'\" onmouseout=\"this.className='firstpage'\" title='第一页'></li>");
			out.println("	<li class='prepage' onclick=\"return tranferPage('previous')\" onmouseover=\"this.className='prepage_hover'\" onmouseout=\"this.className='prepage'\" title='上一页'></li>");
			//out.println("</li>");
		    number=0;
		    //Limit limit=RequestUtils.getWebLimit((HttpServletRequest)pageContext.getRequest()); 
		    int currentpage=0;
		    int pagesize=0;
		    int totalrows=0;
		    if(limit!=null)
			{
				number=limit.getTotalPage();
				currentpage=limit.getPage();
				pagesize=limit.getPageSize();
				totalrows=limit.getTotalRows();
			}
			out.println("<li> \n");
			out.println("	<select class='paddingHack' id='"+GridConstants.HIDDEN_CURRENT_PAGE+"' name='"+GridConstants.HIDDEN_CURRENT_PAGE+"' onchange=\"return tranferPage('go')\" >  \n" );
			for(int i=1;i<(number+1);i++)
			{
				if(currentpage==i)
					out.println("<option  selected value='"+i+"'>"+i+"</option>");
				else
					out.println("<option value='"+i+"'>"+i+"</option>");
			}
			out.println("	</select>\n");
			out.println("</li>");
			out.println("<li style='font-family:arial;font-size: 13px;line-height: 28px;'>/"+number+"页</li>");
			//out.println("<li>");
			out.println("	<li class='nextpage' onclick=\"return tranferPage('next')\" onmouseover=\"this.className='nextpage_hover'\" onmouseout=\"this.className='nextpage'\" title='下一页'></li>");
			out.println("	<li class='lastpage' onclick=\"return tranferPage('last')\" onmouseover=\"this.className='lastpage_hover'\" onmouseout=\"this.className='lastpage'\" title='最后一页'></li>");
			//out.println("</li>");
			out.println("<li><div class='page_split_line'></div></li>");
			out.println("<li>每页</li>");
			out.println("<li>");
			out.println("<select class='paddingHack' id='"+GridConstants.HIDDEN_PAGES_SIZE+"' name='"+GridConstants.HIDDEN_PAGES_SIZE+"' onchange=\"return tranferPage('setsize')\">   ");
			if(pagesize==5)
				out.println("	<option  value='5' selected>5</option> ");
			else
				out.println("	<option value='5'>5</option> ");
			if(pagesize==10)
				out.println("	<option value='10' selected>10</option> ");
			else
				out.println("	<option value='10'>10</option> ");
			if(pagesize==20)
				out.println("	<option value='20' selected>20</option> ");
			else
				out.println("	<option value='20'>20</option> ");
			if(pagesize==50)
				out.println("	<option value='50' selected>50</option> ");
			else
				out.println("	<option value='50'>50</option> ");		
			out.println("</select>");
			out.println("</li>");
			out.println("<li style='font-family:arial;font-size: 12.5px;line-height: 28px;'>条</li>");
		    out.println("<li><div class='page_split_line'></div></li>");
		    out.println("<li style='font-size: 12.5px;line-height: 28px;'>共"+totalrows+"条数据</li>");
		    out.println("</ul>");
			out.println("</span>"); //out.println("<span class='pagenumber'>");
		}
		//工具栏右侧翻页结束
		out.println("</div>");//工具栏结束
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPagetype() {
		return pagetype;
	}

	public void setPagetype(String pagetype) {
		this.pagetype = pagetype;
	}
	
}
