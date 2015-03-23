package com.ultrapower.eoms.common.tag.select.tag;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import com.ultrapower.eoms.common.tag.select.core.Select;

/**
 * 下拉框标签
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version Jun 17, 2010 2:07:43 PM
 */
public class SelectTag extends TagSupport{

	private static final long serialVersionUID = 7434941476661297398L;
	private String name = "";//下拉框名称
	private String dataDicTypeCode = "";//数据字典类型编码
	private String diValue = "";//数据字典值
	private String style = "select";
	private HashMap customData = null;//自定义下拉框数据 为Man类型
	private String customSql = "";//自定义Sql脚本数据
	private String customStr = "";
	private String onChangeFun = "";//事件函数
	private String value = "";
	private String childrenname = "";//下一级节点的name
	private String isnull = "";//是否有缺省值  true:有缺省值
	private String childrenUrl = "";//取子集数据的URL地址
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDataDicTypeCode() {
		return dataDicTypeCode;
	}
	public void setDataDicTypeCode(String dataDicTypeCode) {
		this.dataDicTypeCode = dataDicTypeCode;
	}
	public String getDiValue() {
		return diValue;
	}
	public void setDiValue(String diValue) {
		this.diValue = diValue;
	}
	public HashMap getCustomData() {
		return customData;
	}
	public void setCustomData(HashMap customData) {
		this.customData = customData;
	}
	public String getCustomSql() {
		return customSql;
	}
	public void setCustomSql(String customSql) {
		this.customSql = customSql;
	}
	public String getCustomStr() {
		return customStr;
	}
	public void setCustomStr(String customStr) {
		this.customStr = customStr;
	}
	public String getOnChangeFun() {
		return onChangeFun;
	}
	public void setOnChangeFun(String onChangeFun) {
		this.onChangeFun = onChangeFun;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getChildrenname() {
		return childrenname;
	}
	public void setChildrenname(String childrenname) {
		this.childrenname = childrenname;
	}
	public String getIsnull() {
		return isnull;
	}
	public void setIsnull(String isnull) {
		this.isnull = isnull;
	}
	public String getChildrenUrl() {
		return childrenUrl;
	}
	public void setChildrenUrl(String childrenUrl) {
		this.childrenUrl = childrenUrl;
	}
	public int doStartTag() throws JspException{
		return EVAL_BODY_INCLUDE;
	}
	
	public int doEndTag() throws JspException{
		JspWriter out = pageContext.getOut();
		try{
			Select select = new Select(name,style,childrenname,dataDicTypeCode,diValue,onChangeFun,value,customData,customSql,customStr,isnull,childrenUrl);
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			out.write(select.getSelect(request));
		}catch(IOException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
}
