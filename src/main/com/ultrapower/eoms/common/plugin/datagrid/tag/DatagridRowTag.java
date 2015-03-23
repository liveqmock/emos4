package com.ultrapower.eoms.common.plugin.datagrid.tag;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.plugin.datagrid.grid.Limit;
import com.ultrapower.eoms.common.plugin.datagrid.util.RequestUtils;
public class DatagridRowTag extends BodyTagSupport{
	String var; 
	Iterator iterator;
	DataTable dataTable;
	int index=0;
	int rowindex=0;//序号
	int endIndex=0;
	List lstItem;
	public void setVar(String var) {
	    this.var = var;
	}
	/**
	 * 获取数据结果集
	 * @param items
	 */
	public void setItems(Collection items) 
	{
		if(items!=null)
		{
			this.iterator =items.iterator();//  items.iterator();
			if(items instanceof List)
			{
				lstItem=(List)items;
			}
		}
	}
	public void setDataTable(DataTable dt)
	{
		dataTable=dt;
	}
	public int doStartTag() throws JspException 
	{
		
        Tag t = findAncestorWithClass(this, DataGrideTag.class);    
        DataGrideTag parent = (DataGrideTag) t;
        this.setVar(parent.getVar());
        this.setItems(parent.getItems());
        this.dataTable=parent.getDatatable();
        String memsort=StringUtils.checkNullString(parent.getMempage()).trim();
        Limit limit=RequestUtils.getPageLimit((HttpServletRequest)pageContext.getRequest());
        rowindex=(limit.getPage()-1)*limit.getPageSize();
        rowindex=rowindex<0?0:rowindex;
        if(memsort.equals("true"))
        {
        	//内存分页
        	this.index = (limit.getPage()-1)*limit.getPageSize();
        	index=index<0?0:index;
        	endIndex=limit.getPage()*limit.getPageSize();
        	//limit.getTotalRows();
        }
        else
        {
        	this.index = 0;
        }
//		try {
//		    
////			JspWriter out = pageContext.getOut();
////			StringBuffer stringBuffer=new StringBuffer();
//			//stringBuffer.append("<table border=\"1px\" align=\"center\" class=\"tableborder\">");
//			//out.print(stringBuffer.toString());
//			} catch (Exception e) {
//				System.out.println(e);
//			}
        this.process();
			return EVAL_PAGE;
//	    if (this.process()) {
//	        return EVAL_BODY_INCLUDE;
//	    } else {
//	        return EVAL_PAGE;
//	    }
	}
	
	// ----标签结束时调用此方法-------
	public int doEndTag() { 
		try {
			
			Object result = getBodyContent().getString();
			//System.out.println(result);
			if(result!=null)
			{
				if(this.index>0)
				{
					Tag t = findAncestorWithClass(this, DataGrideTag.class);
					DataGrideTag parent = (DataGrideTag) t;
					parent.setTablerow(result.toString());
				}
			}
		//	t.s
		    this.index = 0;
//			JspWriter out = pageContext.getOut();
//			StringBuffer stringBuffer=new StringBuffer();
//			stringBuffer.append("</table>");
//			stringBuffer.append("</form>");
//			out.print(stringBuffer.toString());
			} catch (Exception e) {
				e.printStackTrace();
			} 
		return EVAL_PAGE;
	}	

	public int doAfterBody() 
	{
		
	    if (this.process()) {
	        return EVAL_BODY_AGAIN;
	    } else {
	        return EVAL_PAGE;
	    }
	}

	private boolean process() {
		if(this.lstItem!=null)
		{
			if(index<lstItem.size())
		    {
		        String row = this.index % 2 != 0 ? "odd" : "even";
		        pageContext.setAttribute(var + "_index", this.index);
		        pageContext.setAttribute(var + "_row", row);
		        Object item = lstItem.get(index);
		       // pageContext.setAttribute("b001", "ddddddddd");
		        pageContext.setAttribute("rowindex", rowindex+1);
		        pageContext.setAttribute(var, item);
		        this.index++;
		        rowindex++;
				//如果大于限制的行数则返回false
				if(this.endIndex>0 && index>endIndex)
				{
					return false;
				}
				else
				{
					return true;
				}
		    } 
		    else 
		    {
		        return false;
		    }			
		}
		else if(this.iterator!=null)
		{
		    if (this.iterator.hasNext()) 
		    {
		        String row = this.index % 2 != 0 ? "odd" : "even";
		        pageContext.setAttribute(var + "_index", this.index);
		        pageContext.setAttribute(var + "_row", row);
		        Object item = this.iterator.next();
		       // pageContext.setAttribute("b001", "ddddddddd");
		        pageContext.setAttribute("rowindex", rowindex+1);
		        pageContext.setAttribute(var, item);
		        this.index++;
		        rowindex++;
				//如果大于限制的行数则返回false
				if(this.endIndex>0 && index>endIndex)
				{
					return false;
				}
				else
				{
					return true;
				}
		    } 
		    else 
		    {
		        return false;
		    }
		}
		else
		{
			String key;
			String value;
			if(dataTable!=null)
			{	
				if(index<dataTable.length())
				{
			        String rowStyle = this.index % 2 != 0 ? "odd" : "even";
			        pageContext.setAttribute(var + "_index", this.index);
			        pageContext.setAttribute(var + "_row", rowStyle);
			        pageContext.setAttribute("rowindex", rowindex+1);
			        
					DataRow dr=dataTable.getDataRow(index);
					for(int col=0;col<dr.length();col++)
					{
						key=dr.getKey(col).toLowerCase();
						value=dr.getString(key);
						//key=var+"."+key;
						pageContext.setAttribute(key,value);
					}
					index++;
					rowindex++;
					//如果大于限制的行数则返回false
					if(this.endIndex>0 && index>endIndex)
					{
						return false;
					}
					else
					{
						return true;
					}
				}
				else
				{
					return false;
				}
				
			}	
			return false;
		}
	}
	  
}
