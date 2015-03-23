package com.ultrapower.eoms.ultrabpp.model.component.datafield.timefield;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.ultrabpp.model.component.datafield.DataFieldTag;
import com.ultrapower.eoms.ultrabpp.utils.FieldTagUtil;

public class TimeTag extends DataFieldTag<TimeField>
{
	private String format;
	private static final long serialVersionUID = 6821606524448319079L;
	
	@Override
	public int doEndTag() throws JspException
	{
		initParameter();
		
		JspWriter out = pageContext.getOut();
		try 
		{
			String visiableClass = "";
			String roClass = "";
			String roAttr = "";
			String editEvent = "";
			
			if(format == null || format.equals(""))
			{
				if(field != null)
				{
					format = field.getTimeFormat();
				}
				else
				{
					format = "yyyy-MM-dd HH:mm:ss";
				}
			}
			
			if(visiable == 0)
			{
				visiableClass = " display:none;";
			}
			if(editable == 0)
			{
				roClass = " bpp_Field_RO";
				roAttr = " readonly=\"true\"";
			}
			
			if(!value.equals("") && Long.parseLong(value) > 0l)
			{
				value = TimeUtils.formatIntToDateString(format, Long.parseLong(value));
			}
			else
			{
				value = "";
			}
			
			StringBuilder tagHtml = new StringBuilder();

			FieldTagUtil temp = new FieldTagUtil("TimeField", "bpp_TimeField", name, label, row, cell, theme, required, roClass, visiableClass);
			int cellpx = temp.getMaxWidth();
			int rowpx = temp.getMaxHeight();
			
			tagHtml.append("<input id=\"" + name + "\" name=\"" + name + "\"" + roAttr + " style=\"width:" + cellpx + "px; height:" + rowpx + "\" value=\"" + value + "\" onkeydown=\"return false\" onpaste=\"return false\" " + editEvent + " />");
			
			String tagString = temp.getTemplateHeader() + tagHtml.toString() + temp.getTemplateFooter();
			
			String scriptString = "<script type=\"text/javascript\">ClientContext.addField(new TimeField(\"" + name + "\", \"" + label + "\", " + required + ", \"" + format + "\"));</script>";
			
			out.println(tagString + scriptString);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	/**
	 * @return the format
	 */
	public String getFormat()
	{
		return format;
	}

	/**
	 * @param format the format to set
	 */
	public void setFormat(String format)
	{
		this.format = format;
	}
}
