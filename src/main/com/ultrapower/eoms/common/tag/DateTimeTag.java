package com.ultrapower.eoms.common.tag;

import java.io.IOException;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.ultrapower.eoms.common.core.util.NumberUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;

public class DateTimeTag extends TagSupport {
	private String id = "";
	private String name="";
	private String value="";
	private String format = "yyyy-MM-dd HH:mm:ss";
	private String cssClass="";
	private String type="";

	public int doEndTag() throws JspTagException {
		try {
			JspWriter out = pageContext.getOut();
			
			StringBuffer html = new StringBuffer();
			
			if (StringUtils.isBlank(id)) {
				id = name;
			}

			String strText="";
			long valLong = NumberUtils.formatToLong(value);
			if(valLong>0) {
				strText=TimeUtils.formatIntToDateString(valLong);
			}
			
			if ("display".equals(type)) {
				html.append("<input type=\"text\" name=\"" + name + "\" id=\"" + id + "\" value=\""+strText+"\" class='"+cssClass+"' readonly=\"true\"/>");
			} else if("text".equals(type)) {
				html.append(strText);
			} else {
				html.append("<input type=\"text\" name=\"" + name + "\" id=\"" + id + "\" value=\""+strText+"\" class='"+cssClass+"' onClick=\"WdatePicker({dateFmt:'"+format+"',autoPickDate:true});\" readonly=\"true\">");
			}
			out.write(html.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
