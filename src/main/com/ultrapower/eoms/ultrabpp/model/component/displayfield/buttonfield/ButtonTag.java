package com.ultrapower.eoms.ultrabpp.model.component.displayfield.buttonfield;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import com.ultrapower.eoms.ultrabpp.model.component.displayfield.DisplayFieldTag;
import com.ultrapower.eoms.ultrabpp.utils.FieldTagUtil;

public class ButtonTag extends DisplayFieldTag<ButtonField>
{
	private static final long serialVersionUID = -1324496578541708035L;
	
	protected String code;
	protected String label;
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
			
			FieldTagUtil temp = new FieldTagUtil("ButtonField", "bpp_BtnField", name, label, row, cell, theme, 0, "", visiableClass);
			
			StringBuilder tagString = new StringBuilder();
			if(code != null && !code.equals("<div>"))
			{
				String[] btnArr = code.split(",");
				for(String btn : btnArr)
				{
					String[] btnInfor = btn.split(":");
					String btnCode = btnInfor[0];
					String btnText = btnInfor[1];
					tagString.append("<input type=\"button\" id=\""+name+"_"+btnCode+"\" name=\""+name+"_"+btnCode+"\" value=\""+btnText+"\" />");
					tagString.append("<script type=\"text/javascript\">ClientContext.addField(new ButtonField(\"" + name + "\", \"" + code + "\"));</script>");
				}
			}
			tagString.append("</div>");
			
			
			out.println(temp.getTemplateHeader() + tagString + temp.getTemplateFooter());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	/**
	 * @return the code
	 */
	public String getCode()
	{
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code)
	{
		this.code = code;
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
