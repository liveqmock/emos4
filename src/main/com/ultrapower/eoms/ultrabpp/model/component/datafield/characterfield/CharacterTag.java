package com.ultrapower.eoms.ultrabpp.model.component.datafield.characterfield;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import com.ultrapower.eoms.ultrabpp.model.component.datafield.DataFieldTag;
import com.ultrapower.eoms.ultrabpp.utils.FieldTagUtil;

public class CharacterTag extends DataFieldTag<CharacterField>
{
	private static final long serialVersionUID = 7717162993614035157L;

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
			
			if(visiable == 0)
			{
				visiableClass = " display:none;";
			}
			if ("Desc".equalsIgnoreCase(name)) {
				editable = 1;
			}
			if(editable == 0)
			{
				roClass = " bpp_Field_RO";
				roAttr = " readonly=\"true\"";
			}
			
			StringBuilder tagHtml = new StringBuilder();
			
			FieldTagUtil temp = new FieldTagUtil("CharField", "bpp_CharField", name, label, row, cell, theme, required, roClass, visiableClass);
			int cellpx = temp.getMaxWidth();
			int rowpx = temp.getMaxHeight();
			
			String extendString = "";
			
			if(length > 50 || length == 0)
			{
				cellpx = cellpx-23;
				extendString = "<div id=\"" + name + "_extend\" class=\"bpp_Field_Extend\"></div>";
			}
			
			value = value.replaceAll("<script", "<ｓｃｒｉｐｔ").replaceAll("</script>", "</ｓｃｒｉｐｔ>");
			value = value.replaceAll("<SCRIPT", "<ｓｃｒｉｐｔ").replaceAll("</SCRIPT>", "</ｓｃｒｉｐｔ>");
			value = value.replaceAll("<Script", "<ｓｃｒｉｐｔ").replaceAll("</Script>", "</ｓｃｒｉｐｔ>");
			if(row > 1)
			{
				tagHtml.append("<textarea id=\"" + name + "\" name=\"" + name + "\"" + roAttr + " style=\"width:" + (cellpx+1) + "px; height:" + rowpx + "px;\">" + value + "</textarea>" + extendString);
			}
			else
			{
				tagHtml.append("<input id=\"" + name + "\" name=\"" + name + "\"" + roAttr + " style=\"width:" + cellpx + "px;\" height:" + rowpx + "px; value=\"" + value + "\" />" + extendString);
			}
			String tagString = temp.getTemplateHeader() + tagHtml.toString() + temp.getTemplateFooter();
			
			String scriptString = "<script type=\"text/javascript\">ClientContext.addField(new CharacterField(\"" + name + "\", \"" + label + "\", " + required + ", " + length + "));</script>";
			
			out.println(tagString + scriptString);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
}
