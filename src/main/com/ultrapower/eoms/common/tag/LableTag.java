package com.ultrapower.eoms.common.tag;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.ultrapower.eoms.common.core.util.Internation;

public class LableTag extends TagSupport 
{
	
	private String name;//名称

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int doStartTag()
	{
		return EVAL_BODY_INCLUDE;
	}
	public int doEndTag() 
	{
		JspWriter out = pageContext.getOut();
		try 
		{
			String strText=Internation.language(name);
			if(strText==null || "".equals(strText))
			{
				strText=name;
			}
			out.print(strText);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
	

}
