package com.ultrapower.eoms.ultrabpp.model.component.displayfield.labelfield;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.commons.lang.StringUtils;

import com.ultrapower.eoms.ultrabpp.model.component.displayfield.DisplayFieldTag;
import com.ultrapower.eoms.ultrabpp.utils.FieldTagUtil;

public class LabelTag extends DisplayFieldTag<LabelField>
{
	private static final long serialVersionUID = -893641234374622792L;
	protected String label;
	protected int row = 1;
	protected String css = "";
	
	@Override
	public int doEndTag() throws JspException
	{
		JspWriter out = pageContext.getOut();
		try
		{
			initParameter();
			
			String visiableClass = "";
			if(visiable == 0)
			{
				visiableClass = " display:none;";
			}
			
			FieldTagUtil temp = new FieldTagUtil("LabelField", "bpp_LabelField", name, null, row, cell, theme, 0, "", visiableClass);
			
			String cssHtml = "";
			if(StringUtils.isNotBlank(css))
			{
				if(css.indexOf("class=") > -1)
				{
					cssHtml = "class=\"" + css.replace("class=", "") + "\"";
				}
				else
				{
					cssHtml = "class=\"" + css.replace("style=", "") + "\"";
				}
			}
			
			String tagString = temp.getTemplateHeader() + "<div><span id=\"name\" " + cssHtml + ">" + label + "</span></div>" + temp.getTemplateFooter();
			
			String scriptString = "<script type=\"text/javascript\">ClientContext.addField(new LabelField(\"" + name + "\", \"" + label + "\"));</script>";
			
			out.println(tagString + scriptString);
		}
		catch (IOException e)
		{
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
	 * @return the css
	 */
	public String getCss()
	{
		return css;
	}

	/**
	 * @param css the css to set
	 */
	public void setCss(String css)
	{
		this.css = css;
	}

}
