package com.ultrapower.eoms.ultrabpp.model.component.displayfield.viewfield;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import com.ultrapower.eoms.ultrabpp.model.component.displayfield.DisplayFieldTag;
import com.ultrapower.eoms.ultrabpp.utils.FieldTagUtil;

public class ViewTag extends DisplayFieldTag<ViewField> {

	private static final long serialVersionUID = 1L;

	private String url = "";
	private String type = "";
	private int row = 1;
	private String label = "";
	
	@Override
	public int doStartTag() throws JspException {
		try {
			initParameter();
			
			String visiableClass = "";
			if(visiable == 0)
			{
				visiableClass = " display:none;";
			}
			
			JspWriter out = pageContext.getOut();
			StringBuffer tagHtml = new StringBuffer();
			
			if(label != null && label.equals("")) label = null;
			
			FieldTagUtil temp = new FieldTagUtil("ViewField", "bpp_View", name, label, row, cell, theme, 0, "", visiableClass);
			int cellpx = temp.getMaxWidth() + 5;
			int rowpx = temp.getMaxHeight();
			String extendUrl = "";
			//if(url.indexOf("?") > -1) extendUrl = "&fid="+name;
			//else extendUrl = "?fid="+name;
			
			if ("frame".equals(type))
			{
				tagHtml.append("<iframe id=\""+name+"\" src=\""+url+extendUrl+"\" height=\""+rowpx+"px\" width=\""+cellpx+"px\" MARGINWIDTH=\"0\" MARGINHEIGHT=\"0\" HSPACE=\"0\" VSPACE=\"0\" FRAMEBORDER=\"0\" Border=\"0\" noresize scrolling=\"auto\" ></iframe>");
			}
			
			String tagString = temp.getTemplateHeader() + tagHtml.toString() + temp.getTemplateFooter();
			
			String scriptString = "<script type=\"text/javascript\">ClientContext.addField(new ViewField(\"" + name + "\", \"" + label + "\"));</script>";
			
			out.println(tagString + scriptString);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return EVAL_PAGE;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
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


	/**
	 * @return the row
	 */
	public int getRow()
	{
		return row;
	}


	/**
	 * @param row the row to set
	 */
	public void setRow(int row)
	{
		this.row = row;
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
