package com.ultrapower.eoms.ultrabpp.model.component.specialfield.rollbacklistfield;


import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import com.ultrapower.eoms.ultrabpp.model.component.specialfield.SpecialFieldTag;
import com.ultrapower.eoms.ultrabpp.utils.FieldTagUtil;

public class RollbackListTag extends SpecialFieldTag<RollbackListField>
{
	private static final long serialVersionUID = 6044378630962794573L;
	protected String label;
	protected String type = "";
	protected String radio;
	
	protected RollbackListField field = null;
	
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
			
			StringBuilder tagHtml = new StringBuilder();
			
			FieldTagUtil temp = new FieldTagUtil("RollbackListField", "bpp_RollbackListField", name, label, 0, 4, theme, 0, "", visiableClass);
			
			tagHtml.append("<div class=\"bpp_RollbackListField_Table\"><table id=\"" + name + "\" cellpadding=\"0\" cellspacing=\"1\" border=\"0\">");
			tagHtml.append("<tr><th style=\"width:40px;\"></th><th>环节描述</th><th style=\"width:300px;\">处理人</th><th style=\"width:100px;\">环节状态</th></tr>");
			tagHtml.append("</table></div>");
			
			String tagString = temp.getTemplateHeader() + tagHtml.toString() + temp.getTemplateFooter();
			
			String scriptString = "<script type=\"text/javascript\">ClientContext.addField(new RollbackListField(\"" + name + "\", \"" + label + "\", " + required + ", \"" + type + "\", \"" + radio + "\"));</script>";
			
			out.println(tagString + scriptString);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
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
	 * @return the radio
	 */
	public String getRadio()
	{
		return radio;
	}

	/**
	 * @param radio the radio to set
	 */
	public void setRadio(String radio)
	{
		this.radio = radio;
	}

	/**
	 * @return the field
	 */
	public RollbackListField getField()
	{
		return field;
	}

	/**
	 * @param field the field to set
	 */
	public void setField(RollbackListField field)
	{
		this.field = field;
	}
}
