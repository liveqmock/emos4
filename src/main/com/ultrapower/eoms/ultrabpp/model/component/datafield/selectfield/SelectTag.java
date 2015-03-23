package com.ultrapower.eoms.ultrabpp.model.component.datafield.selectfield;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import com.ultrapower.eoms.ultrabpp.model.component.datafield.DataFieldTag;
import com.ultrapower.eoms.ultrabpp.utils.FieldTagUtil;

public class SelectTag extends DataFieldTag<SelectField>
{
	private String type;
	private String resource;
	private String paras;
	private static final long serialVersionUID = 2688417341202553752L;
	
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
			
			if(type == null) type = "";
			
			if(type == null || type.equals(""))
			{
				type = "collect";
			}
			if(resource == null) resource = "";
			
			if(visiable == 0)
			{
				visiableClass = " display:none;";
			}
			if(editable == 0)
			{
				roClass = " bpp_Field_RO";
				roAttr = " readonly=\"true\"";
				editEvent = "ClientContext.addField(new SelectField(\"" + name + "\", \"" + label + "\", " + required + ", \"" + type + "\", \"\", \"\", \"\", \"\"));";
			}
			else
			{
				if(type.equals("collect") || type.equals("bean"))
				{
					editEvent = "ClientContext.addField(new SelectField(\"" + name + "\", \"" + label + "\", " + required + ", \"" + type + "\", \"" + resource + "\", \"1\", \"1\", \"" + paras + "\"));";
				}
				else if(type.equals("sysdic"))
				{
					editEvent = "ClientContext.addField(new SelectField(\"" + name + "\", \"" + label + "\", " + required + ", \"" + type + "\", \"" + resource + "\", \"1\", \"1\", \"" + paras + "\"));";
				}
				else if(type.equals("table"))
				{
					String res = resource.split(":")[0];
					String key = resource.split(":")[1];
					String val = resource.split(":")[2];
					editEvent = "ClientContext.addField(new SelectField(\"" + name + "\", \"" + label + "\", " + required + ", \"" + type + "\", \"" + res + "\", \"" + key + "\", \"" + val + "\", \"" + paras + "\"));";
				}
			}
			
			StringBuilder tagHtml = new StringBuilder();
			
			FieldTagUtil temp = new FieldTagUtil("SelectField", "bpp_SelectField", name, label, row, cell, theme, required, roClass, visiableClass);
			int cellpx = temp.getMaxWidth();
			int rowpx = temp.getMaxHeight();
			
			tagHtml.append("<input id=\"" + name + "\" name=\"" + name + "\"" + roAttr + " style=\"width:" + (cellpx-22) + "px; height:" + rowpx + ";\" value=\"" + value + "\" onkeydown=\"return false;\" onpaste=\"return false;\" />");
			tagHtml.append("<div id=\"" + name + "_extend\" class=\"bpp_Field_SelectExtend\"></div>");
			
			String tagString = temp.getTemplateHeader() + tagHtml.toString() + temp.getTemplateFooter();
			
			String scriptString = "<script type=\"text/javascript\">" + editEvent + "</script>";
			
			out.println(tagString + scriptString);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return EVAL_PAGE;
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
	 * @return the resource
	 */
	public String getResource()
	{
		return resource;
	}
	/**
	 * @param resource the resource to set
	 */
	public void setResource(String resource)
	{
		this.resource = resource;
	}

	/**
	 * @return the paras
	 */
	public String getParas()
	{
		return paras;
	}

	/**
	 * @param paras the paras to set
	 */
	public void setParas(String paras)
	{
		this.paras = paras;
	}

}
