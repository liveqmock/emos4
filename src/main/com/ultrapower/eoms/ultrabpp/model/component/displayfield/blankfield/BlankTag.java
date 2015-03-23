package com.ultrapower.eoms.ultrabpp.model.component.displayfield.blankfield;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import com.ultrapower.eoms.ultrabpp.model.component.displayfield.DisplayFieldTag;
import com.ultrapower.eoms.ultrabpp.utils.FieldTagUtil;

public class BlankTag extends DisplayFieldTag<BlankField>
{
	private static final long serialVersionUID = 6573141587520285103L;
	protected int row = 1;
	
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
			
			FieldTagUtil temp = new FieldTagUtil("BlankField", "bpp_BlankField", name, null, row, cell, theme, 0, "", visiableClass);
			
			String tagString = temp.getTemplateHeader() + temp.getTemplateFooter();
			
			out.println(tagString);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
}
