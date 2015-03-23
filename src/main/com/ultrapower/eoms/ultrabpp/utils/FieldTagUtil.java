package com.ultrapower.eoms.ultrabpp.utils;

import com.ultrapower.eoms.ultrabpp.cache.model.ThemeModel;

public class FieldTagUtil
{
	private String fieldType;
	private String mainClass;
	private String name;
	private String label;
	private int row;
	private int cell;
	private ThemeModel theme;
	private int required;
	private String customClass;
	private String customStyle;
	
	private int maxWidth;
	private int maxHeight;
	
	private String templateHeader;
	private String templateFooter;
	
	public FieldTagUtil(String fieldType, String mainClass, String name, String label, int row, int cell, ThemeModel theme, int required, String customClass, String customStyle)
	{
		this.fieldType = fieldType;
		this.mainClass = mainClass;
		this.name = name;
		this.label = label;
		this.row = row;
		if(cell > theme.getCell()) cell = theme.getCell();
		this.cell = cell;
		this.theme = theme;
		this.required = required;
		this.customClass = customClass;
		this.customStyle = customStyle;
		
		getMaxSize();
		buildFieldTemplateHeader();
		buildFieldTemplateFooter();
	}
	
	private void getMaxSize()
	{
		int cellpx = cell*(theme.getMainWidth()/theme.getCell());
		int rowpx = row*30;
		maxWidth = cellpx-theme.getLabelWidth()-9;
		maxHeight = rowpx-10;
		if(label == null)
		{
			maxWidth += theme.getLabelWidth();
		}
	}
	
	public void buildFieldTemplateHeader()
	{
		int cellpx = cell*(theme.getMainWidth()/theme.getCell());
		int rowpx = row*30;
		String heightStyle = "";
		String tblHeightStyle = "";
		if(rowpx > 0)
		{
			heightStyle = " height:" + rowpx + "px;";
			tblHeightStyle = " height:" + (rowpx-1) + "px;";
		}
		StringBuilder tagHtml = new StringBuilder();
		tagHtml.append("<div id=\"" + name + "_ubfbox\" ubftype=\"" + fieldType + "\" class=\"bpp_Field " + mainClass + " " + customClass + "\" style=\"width:" + cellpx + "px;" + heightStyle + " " + customStyle + "\">");
		tagHtml.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width:" + (cellpx-2) + "px;" + tblHeightStyle + "\"><tr>");
		if(label != null)
		{
			tagHtml.append(buildFieldLabel());
		}
		tagHtml.append("<td valign=\"top\">");
		templateHeader = tagHtml.toString();
	}
	
	public void buildFieldTemplateFooter()
	{
		StringBuilder tagHtml = new StringBuilder();
		tagHtml.append("</td></tr></table></div>");
		templateFooter = tagHtml.toString();
	}
	
	private String buildFieldLabel()
	{
		if(row < 1 && label.length() > 5) row = 2;
		if(row < 1) row = 1;
		String requiredClass = "";
		String requiredTag = "";
		if(required == 1)
		{
			requiredClass = " class=\"bpp_Require\"";
			//requiredTag = "<font>*</font>";
		}
		String css = "";
		if(label.length() > 6 && row == 1) css = "bpp_Field_Label_Large";
		else css = "bpp_Field_Label";
		StringBuilder tagHtml = new StringBuilder();
		tagHtml.append("<td valign=\"top\" class=\"" + css + "\"><div style=\"height:" + (row*30-1) + "px;\"><div><span" + requiredClass + ">" + label + "：" + requiredTag + "</span></div></div></td>");
		return tagHtml.toString();
	}

	/**
	 * @return the maxWidth
	 */
	public int getMaxWidth()
	{
		return maxWidth;
	}

	/**
	 * @param maxWidth the maxWidth to set
	 */
	public void setMaxWidth(int maxWidth)
	{
		this.maxWidth = maxWidth;
	}

	/**
	 * @return the maxHeight
	 */
	public int getMaxHeight()
	{
		return maxHeight;
	}

	/**
	 * @param maxHeight the maxHeight to set
	 */
	public void setMaxHeight(int maxHeight)
	{
		this.maxHeight = maxHeight;
	}

	/**
	 * @return the templateHeader
	 */
	public String getTemplateHeader()
	{
		return templateHeader;
	}

	/**
	 * @param templateHeader the templateHeader to set
	 */
	public void setTemplateHeader(String templateHeader)
	{
		this.templateHeader = templateHeader;
	}

	/**
	 * @return the templateFooter
	 */
	public String getTemplateFooter()
	{
		return templateFooter;
	}

	/**
	 * @param templateFooter the templateFooter to set
	 */
	public void setTemplateFooter(String templateFooter)
	{
		this.templateFooter = templateFooter;
	}
}
