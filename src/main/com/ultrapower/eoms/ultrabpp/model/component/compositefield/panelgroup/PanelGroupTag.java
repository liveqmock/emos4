package com.ultrapower.eoms.ultrabpp.model.component.compositefield.panelgroup;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import com.ultrapower.eoms.ultrabpp.model.component.compositefield.CompositeField;

import com.ultrapower.eoms.ultrabpp.model.component.compositefield.CompositeFieldTag;

public class PanelGroupTag extends CompositeFieldTag<CompositeField>
{
	private static final long serialVersionUID = -3666365646496453308L;
	protected int cell = 1;
	protected int showtitle = 1;
	protected String type = "tab";
	
	@Override
	public int doStartTag() throws JspException {
		initParameter();
		
		String visiableClass = "";
		if(visiable == 0)
		{
			visiableClass = " display:none;";
		}
		String titleVisiable = "";

		if(showtitle == 0)
		{
			titleVisiable = " display:none;";
		}
		
		JspWriter out = pageContext.getOut();
		StringBuffer html = new StringBuffer();
		
		if(cell > theme.getCell()) cell = theme.getCell();
		int cellpx = cell*(theme.getMainWidth()/theme.getCell());
		
		if(type.equals("tab"))
		{
			html.append("<div id=\""+name+"\" ubftype=\"TabPanelGroup\" class=\"bpp_Field bpp_TabPanelGroup\" style=\"width:"+cellpx+"px; "+visiableClass+"\">");
			html.append("<div class=\"bpp_TabPanelGroup_Header\" style=\""+titleVisiable+"\"><div class=\"bpp_TabPanelGroup_Header_front\"></div><ul></ul></div>");
		}
		else
		{
			html.append("<div id=\""+name+"\" ubftype=\"BoxPanelGroup\" class=\"bpp_Field bpp_BoxPanelGroup\" style=\"width:"+cellpx+"px; "+visiableClass+"\">");
		}
		
		try {
			out.print(html.toString());
			out.print("<script type=\"text/javascript\">ClientContext.addField(new PanelGroupField(\""+name+"\", \""+type+"\", "+showtitle+"));</script>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_BODY_INCLUDE;
	}
	
	@Override
	public int doEndTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try {
			out.print("</div>");
			if(type.equals("tab"))
			{
				//out.print("<script type=\"text/javascript\">PanelField.switchTabPanel(F(\""+name+"\").panelList.get(0) + \"_Header\");</script>");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	/**
	 * @return the cell
	 */
	public int getCell()
	{
		return cell;
	}

	/**
	 * @param cell the cell to set
	 */
	public void setCell(int cell)
	{
		this.cell = cell;
	}

	/**
	 * @return the showtitle
	 */
	public int getShowtitle()
	{
		return showtitle;
	}

	/**
	 * @param showtitle the showtitle to set
	 */
	public void setShowtitle(int showtitle)
	{
		this.showtitle = showtitle;
	}

	/**
	 * @return the type
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type)
	{
		this.type = type;
	}
}
