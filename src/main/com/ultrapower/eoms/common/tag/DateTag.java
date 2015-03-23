package com.ultrapower.eoms.common.tag;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;

public class DateTag extends TagSupport{
	public long value;
	public String pattern;

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}
	
	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public int doEndTag() 
	{
		
		JspWriter out = pageContext.getOut();
		try 
		{
			String strText="";
			if(value>0)
			{
				if(!"".equals(StringUtils.checkNullString(pattern)))
				{
					strText = TimeUtils.formatIntToDateString(value, pattern);
				}
				else
				{
					strText=TimeUtils.formatIntToDateString(value);
				}
			}
			out.println(strText);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
}
