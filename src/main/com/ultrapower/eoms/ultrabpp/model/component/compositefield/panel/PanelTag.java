package com.ultrapower.eoms.ultrabpp.model.component.compositefield.panel;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import com.ultrapower.eoms.ultrabpp.model.component.compositefield.CompositeFieldTag;
import com.ultrapower.eoms.ultrabpp.model.component.compositefield.panelgroup.PanelGroupTag;

public class PanelTag extends CompositeFieldTag<PanelField>
{
	private static final long serialVersionUID = 6145101278935973422L;
	protected String label;

	@Override
	public int doStartTag() throws JspException
	{
		initParameter();
		PanelGroupTag pgTag = (PanelGroupTag)this.getParent();
		StringBuffer html = new StringBuffer();
		String style = "";
		if (visiable == 0)
		{
			style = "style=\"display:none;\"";
		}
		
		String type = pgTag.getType();
		String groupID = pgTag.getName();
		int showtitle = pgTag.getShowtitle();
		if(type.equals("tab"))
		{
			html.append("<div id=\""+name+"\" ubftype=\"TabPanel\" class=\"bpp_TabPanelGroup_Body\" "+style+">");
		}
		else
		{
			html.append("<div id=\""+name+"_Item\" class=\"bpp_BoxPanelGroup_Item\">");
			if(showtitle == 1)
			{
				html.append("<div id=\""+name+"_Header\" class=\"bpp_BoxPanelGroup_Header\"><div>" + label + "：</div></div>");
			}
			html.append("<div id=\""+name+"\" ubftype=\"BoxPanel\" class=\"bpp_BoxPanelGroup_Body\" "+style+">");
		}
		
		try
		{
			JspWriter out = pageContext.getOut();
			out.print(html.toString());
			out.print("<script type=\"text/javascript\">ClientContext.addField(new PanelField(\""+name+"\", \""+label+"\", \""+type+"\", \""+groupID+"\", "+showtitle+"));</script>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_BODY_INCLUDE;
	}
	
	@Override
	public int doEndTag() throws JspException
	{
		JspWriter out = pageContext.getOut();
		try {
			out.print("</div>");
			PanelGroupTag pgTag = (PanelGroupTag)this.getParent();
			String type = pgTag.getType();
			if(!type.equals("tab"))
			{
				out.print("</div>");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	/**
	 * @return the label
	 */
	public String getLabel()
	{
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label)
	{
		this.label = label;
	}
}
