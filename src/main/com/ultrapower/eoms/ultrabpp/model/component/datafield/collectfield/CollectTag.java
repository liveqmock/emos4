package com.ultrapower.eoms.ultrabpp.model.component.datafield.collectfield;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.util.NumberUtils;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.ultrabpp.model.component.datafield.DataFieldTag;
import com.ultrapower.eoms.ultrabpp.utils.FieldTagUtil;
import com.ultrapower.eoms.ultrasm.model.DicItem;
import com.ultrapower.eoms.ultrasm.service.DicManagerService;

public class CollectTag extends DataFieldTag<CollectField>
{
	
	private String type;
	private String resource;
	private String paras;
	private String showtype;
	
	Map<String, String> dicValues;

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
			
			if(showtype == null || showtype.equals("")) showtype = "radio";
			
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
				roAttr = " disabled=\"disabled\"";
				editEvent = "ClientContext.addField(new CollectField(\"" + name + "\", \"" + label + "\", " + required + ", \"" + showtype + "\",  \"" + type + "\", \"\", \"\", \"\", \"\"));";
			}
			else
			{
				if(type.equals("collect"))
				{
					editEvent = "ClientContext.addField(new CollectField(\"" + name + "\", \"" + label + "\", " + required + ", \"" + showtype + "\", \"" + type + "\", \"" + resource + "\", \"0\", \"1\", \"" + paras + "\"));";
					//getDicData("collect", resource, "1", "1", paras);
				}
				else if(type.equals("sysdic"))
				{
					editEvent = "ClientContext.addField(new CollectField(\"" + name + "\", \"" + label + "\", " + required + ", \"" + showtype + "\", \"" + type + "\", \"" + resource + "\", \"0\", \"1\", \"" + paras + "\"));";
					//getDicData("sysdic", resource, "1", "1", paras);
				}
				else if(type.equals("table"))
				{
					String res = resource.split(":")[0];
					String key = resource.split(":")[1];
					String val = resource.split(":")[2];
					editEvent = "ClientContext.addField(new CollectField(\"" + name + "\", \"" + label + "\", " + required + ", \"" + showtype + "\", \"" + type + "\", \"" + res + "\", \"" + key + "\", \"" + val + "\", \"" + paras + "\"));";
					//getDicData("table", res, key, val, paras);
				}
			}
			dicValues = CollectField.getDicData(type, resource, paras);
			
			StringBuilder tagHtml = new StringBuilder();
			
			FieldTagUtil temp = new FieldTagUtil("CollectField", "bpp_CollectField", name, label, row, cell, theme, required, roClass, visiableClass);
			int cellpx = temp.getMaxWidth();
			int rowpx = temp.getMaxHeight();
			
			//tagHtml.append("<input id=\"" + name + "\" name=\"" + name + "\"" + roAttr + " style=\"width:" + cellpx + "px; height:" + rowpx + ";\" value=\"" + value + "\" onkeydown=\"return false;\" onpaste=\"return false;\" />");
			
			int i = 0;
			String valueArr = "," + value + ",";
			tagHtml.append("<ul>");
			for(String map_key : dicValues.keySet())
			{
				String map_value = dicValues.get(map_key);
				String selected = "";
				if(valueArr.indexOf("," + map_value + ",") > -1)
				{
					selected = "checked=\"true\"";
				}
				String showTypeStr = "radio";
				if(showtype.equals("checkbox")) showTypeStr = "checkbox";
				String appendMrgin = "";
				if(i < dicValues.size())
				{
					appendMrgin = " margin-right:2px;";
				}
				tagHtml.append("<li style=\"float:left; white-space:nowrap;" + appendMrgin + "\">");
				tagHtml.append("<table><tr><td><input id=\"" + name + "_" + i + "\" name=\"" + name + "_displayInput\" type=\"" + showTypeStr + "\" " + roAttr + " value=\"" + map_key + "\" " + selected + " /></td><td><label style=\"white-space:nowrap;\" for=\"" + name + "_" + i + "\">" + map_value + "</label></td></tr></table>");
				tagHtml.append("</li>");
				i++;
			}
			tagHtml.append("</ul>");
			tagHtml.append("<input id=\"" + name + "\" name=\"" + name + "\" type=\"hidden\" value=\"" + value + "\">");
			
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
	/**
	 * @return the showtype
	 */
	public String getShowtype()
	{
		return showtype;
	}
	/**
	 * @param showtype the showtype to set
	 */
	public void setShowtype(String showtype)
	{
		this.showtype = showtype;
	}
}
