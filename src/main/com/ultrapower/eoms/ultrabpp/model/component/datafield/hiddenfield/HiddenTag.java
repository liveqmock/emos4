package com.ultrapower.eoms.ultrabpp.model.component.datafield.hiddenfield;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import com.ultrapower.eoms.ultrabpp.model.component.datafield.DataFieldTag;
import com.ultrapower.eoms.ultrabpp.model.component.datafield.timefield.TimeField;
import com.ultrapower.eoms.ultrabpp.utils.FieldTagUtil;

public class HiddenTag extends DataFieldTag<TimeField>
{
	private static final long serialVersionUID = -2359075539959342012L;

	@Override
	public int doEndTag() throws JspException
	{
		JspWriter out = pageContext.getOut();
		try 
		{
			initParameter();
			
			StringBuilder tagHtml = new StringBuilder();

			FieldTagUtil temp = new FieldTagUtil("HiddenField", "bpp_CharField", name, label, row, cell, theme, required, "", "");
			int cellpx = temp.getMaxWidth();
			
			int cellWidth = 0;
			String extendString = "";
//			if(length > 50)
//			{
//				cellpx = cellpx-10;
//				extendString = "<div class=\"bpp_Field_Extend\"></div>";
//			}
			tagHtml.append("<input type=\"hidden\" id=\"" + name + "\" name=\"" + name + "\" style=\"width:" + cellWidth + "px;\" value=\"" + value + "\" />" + extendString);
			
//			String tagString = temp.getTemplateHeader() + tagHtml.toString() + temp.getTemplateFooter();
			String tagString = tagHtml.toString();
			
			String scriptString = "<script type=\"text/javascript\">ClientContext.addField(new HiddenField(\"" + name + "\", \"" + label + "\", " + length + "));</script>";
			
			out.println(tagString + scriptString);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
}
