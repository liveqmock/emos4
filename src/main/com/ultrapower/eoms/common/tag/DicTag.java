package com.ultrapower.eoms.common.tag;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.ultrasm.model.DicItem;
import com.ultrapower.eoms.ultrasm.service.DicManagerService;

public class DicTag extends TagSupport 
{
	private String dictype;//字典类型
	private String value;//字典值
	private String isfullname;//是否显示全名
	private String type;//
	private String name;//
	private String cssClass = "";//
	

	public int doStartTag()
	{
		return EVAL_BODY_INCLUDE;
	}
	public int doEndTag() 
	{
		JspWriter out = pageContext.getOut();
		try 
		{
			DicManagerService dicManagerService  = (DicManagerService)WebApplicationManager.getBean("dicManagerService");
			StringBuffer html = new StringBuffer();
			String diname = "";
			if ("field".equals(type)) {
				diname = dicManagerService.getTextByValue(dictype, value);
				html.append("<input type='text' readonly id='"+name+"_' value='"+diname+"' class='"+cssClass+"'>");
				// 显示Value
				html.append("<input type='hidden' name='"+name+"' id='"+name+"' value='"+value+"'>");
			} else {
				if("true".equals(StringUtils.checkNullString(isfullname))) {
					DicItem di = dicManagerService.getDicItemByValue(dictype, value);
					if(di != null) {
						diname = StringUtils.checkNullString(di.getDicfullname());
					}
				} else {
					diname = dicManagerService.getTextByValue(dictype, value);
				}
				html.append(diname);
			}
			out.print(html.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
	
	public String getDictype() {
		return dictype;
	}
	public void setDictype(String dictype) {
		this.dictype = dictype;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getIsfullname() {
		return isfullname;
	}
	public void setIsfullname(String isfullname) {
		this.isfullname = isfullname;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCssClass() {
		return cssClass;
	}
	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
