package com.ultrapower.eoms.common.plugin.autosearch;

import java.io.IOException;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * input自动列出选项标签
 * 
 * @author user
 * 
 */
@SuppressWarnings("serial")
public class AutoInputTag extends TagSupport {
	private String id;
	private String name;
	private String value = "";
	private String style = "";
	private String width = "400";
	private String sqlQuery;
	private String hiddenId = "";
	private String hiddenName = "";
	private String hiddenValue = "";
	private String showFormat = "{value}";

	public int doEndTag() throws JspTagException {
		try {
			String ctx = this.pageContext.getServletContext().getContextPath();
			String actionUrl = ctx + "/autosearch/autoSearch.action?sqlQuery="
					+ sqlQuery;
			JspWriter out = pageContext.getOut();
			StringBuffer html = new StringBuffer();
			html.append("<div id='autoInput_" + id + "'/>\n");
			if (hiddenId != null && !hiddenId.trim().equals(""))
				html.append("<input type='hidden' id='" + hiddenId + "' name='"
						+ hiddenName + "' value='" + hiddenValue + "'/>\n");
			html.append("<script language='javascript'>\n");
			html.append("var combox =  new ListCombox");
			html.append("({renderTo : 'autoInput_" + id + "',width:" + width
					+ ",style:'" + style + "',showFormat:'"+showFormat+"',");
			html.append("'AjaxUrl':'" + actionUrl
					+ "','queryParam':'paramKey',");
			html.append("'IdField':'" + hiddenId + "','NameField':'" + id
					+ "','NameFieldName':'" + name + "'});\n");
			html.append(" combox.setValue('" + value + "');\n");
			html.append("</script>");
			out.write(html.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSqlQuery() {
		return sqlQuery;
	}

	public void setSqlQuery(String sqlQuery) {
		this.sqlQuery = sqlQuery;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getHiddenId() {
		return hiddenId;
	}

	public void setHiddenId(String hiddenId) {
		this.hiddenId = hiddenId;
	}

	public String getHiddenName() {
		return hiddenName;
	}

	public void setHiddenName(String hiddenName) {
		this.hiddenName = hiddenName;
	}

	public String getHiddenValue() {
		return hiddenValue;
	}

	public void setHiddenValue(String hiddenValue) {
		this.hiddenValue = hiddenValue;
	}

	public String getShowFormat() {
		return showFormat;
	}

	public void setShowFormat(String showFormat) {
		this.showFormat = showFormat;
	}

}
