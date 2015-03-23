package com.ultrapower.eoms.common.tag;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.jstl.fmt.LocalizationContext;
import javax.servlet.jsp.tagext.TagSupport;

public class ActorTag extends TagSupport{
	
	private String hiddenId = "";
	private String textId = "";
	private String hidDefValue = "";
	private String textDefValue = "";
	private boolean require = false;
	private String cssClass = "";
	private String msg = "";
	private LocalizationContext resourceBundle;
	
	public int doEndTag() {
		
		JspWriter out = pageContext.getOut();
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("<input type=\"text\" name=\""+textId+"\" id=\""+textId+"\" class=\""+cssClass+"\" readonly=\"readonly\" value=\""+textDefValue+"\"/>");
			sb.append("<input type=\"hidden\" name=\"" + hiddenId + "\" id=\"" + hiddenId + "\" value=\""+hidDefValue+"\">");
			if (require) {
				String ms1 = resourceBundle.getResourceBundle().getString(msg);
				sb.append("<validation id=\""+textId+"V\" dataType=\"Require\" msg=\""+ms1+"\"></validation>");
			}
			out.println(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}


	public String getHiddenId() {
		return hiddenId;
	}


	public void setHiddenId(String hiddenId) {
		this.hiddenId = hiddenId;
	}


	public String getTextId() {
		return textId;
	}


	public void setTextId(String textId) {
		this.textId = textId;
	}


	public boolean isRequire() {
		return require;
	}


	public void setRequire(boolean require) {
		this.require = require;
	}


	public String getCssClass() {
		return cssClass;
	}


	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}


	public LocalizationContext getResourceBundle() {
		return resourceBundle;
	}


	public void setResourceBundle(LocalizationContext resourceBundle) {
		this.resourceBundle = resourceBundle;
	}


	public String getHidDefValue() {
		return hidDefValue;
	}


	public void setHidDefValue(String hidDefValue) {
		this.hidDefValue = hidDefValue;
	}


	public String getTextDefValue() {
		return textDefValue;
	}


	public void setTextDefValue(String textDefValue) {
		this.textDefValue = textDefValue;
	}
}
