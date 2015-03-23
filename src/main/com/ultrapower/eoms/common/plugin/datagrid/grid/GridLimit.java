package com.ultrapower.eoms.common.plugin.datagrid.grid;

import javax.servlet.http.HttpServletRequest;

import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.NumberUtils;
import com.ultrapower.eoms.common.plugin.datagrid.core.GridConstants;

public  class GridLimit implements Limit {
	private int page=0;
	private int pageSize=0;
	private int rowStart=0;
	private int rowEnd=0;
	private int totalRows=0;
	private int totalPages=0;
	HttpServletRequest request=null;
	private String sortfiled;
	private int sorttype=1;// 1 正序 0 倒序
	
	public  GridLimit(HttpServletRequest p_request,int type)
	{
		this.request=p_request;
		if(request!=null)
		{
			page=NumberUtils.formatToInt(request.getParameter(GridConstants.HIDDEN_CURRENT_PAGE));
			pageSize=NumberUtils.formatToInt(request.getParameter(GridConstants.HIDDEN_PAGES_SIZE));
			page=page<=0?1:page;
			pageSize=pageSize<=0?GridConstants.DEFAULT_PAGE_SIZE:pageSize;
			sortfiled=StringUtils.checkNullString(request.getParameter(GridConstants.HIDDEN_SORTFIELD));
			sorttype=NumberUtils.formatToInt(request.getParameter(GridConstants.HIDDEN_SORTTYPE));
//			String str=request.getParameter(GridConstants.HIDDEN_ISTRANFER);
			//是否翻页提交  1 是 
			int istranfer=NumberUtils.formatToInt(request.getParameter(GridConstants.HIDDEN_ISTRANFER));
			if(istranfer!=1)
			{
				page=1;//如果不是分页提交的则默认从第一页开始
			}			
			if(type==1)//后台Dao取page属性
			{
		//		rowStart=NumberUtils.formatToInt(request.getAttribute(GridConstants.HIDDEN_PAGES_SIZE));
		//		rowEnd=NumberUtils.formatToInt(request.getAttribute(GridConstants.HIDDEN_PAGES_SIZE));
				totalRows=NumberUtils.formatToInt(request.getParameter(GridConstants.HIDDEN_TOTAL_ROWS));
				totalPages=NumberUtils.formatToInt(request.getParameter(GridConstants.HIDDEN_TOTAL_PAGES));

			}
			else//前台标签取属性
			{
				totalRows=NumberUtils.formatToInt(request.getAttribute(GridConstants.HIDDEN_TOTAL_ROWS));
				totalPages=NumberUtils.formatToInt(request.getAttribute(GridConstants.HIDDEN_TOTAL_PAGES));
				if(totalRows==0)
					page=0;
				
			}
		}
	}
	public int getPage() {
		// TODO Auto-generated method stub
		return page;
	}

	public int getPageSize() {
		// TODO Auto-generated method stub
		return pageSize;
	}

	public int getRowEnd() {
		// TODO Auto-generated method stub
		return rowEnd;
	}

	public int getRowStart() {
		// TODO Auto-generated method stub
		return rowStart;
	}

	public int getTotalRows() {
		// TODO Auto-generated method stub
		return totalRows;
	}

	public void setRowAttributes(int totalRows, int defaultRowsDisplayed) 
	{
		this.totalRows=totalRows;
		if(totalRows>0&& pageSize>0)
		{
			totalPages=totalRows/pageSize;
			if(totalRows%pageSize>0)
			{
				totalPages++;
			}
		}
		else
		{
			totalPages=0;
		}
		this.request.setAttribute(GridConstants.HIDDEN_TOTAL_PAGES, String.valueOf(totalPages));
		this.request.setAttribute(GridConstants.HIDDEN_TOTAL_ROWS, String.valueOf(totalRows));
		
		// TODO Auto-generated method stub
		
	}
	public int getTotalPage() 
	{
		// TODO Auto-generated method stub
		return totalPages;
	}
	public String getSortField()
	{
		return sortfiled;
	}
	public int getSortType()
	{
		return sorttype;
	}
//    public int getTotalRows();
	
	public static void main(String[] args)
	{
	//	ToStringBuilder tostring=new ToStringBuilder(this);
		
	}
}
