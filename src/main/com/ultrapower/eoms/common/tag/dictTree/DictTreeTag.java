package com.ultrapower.eoms.common.tag.dictTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.component.rquery.RQuery;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.remedy4j.core.RemedySession;


/**
 * 本类主要是用于字典树，弹出DIV展现，并提供搜索功能
 * @author liuchuanzu 20120816
 *
 */
@SuppressWarnings("serial")
public class DictTreeTag extends TagSupport {

	private String viewFlag = "";//是否只显示内容;true:只显示内容；false:显示输入框
	private String id = "";
	private String name = "";
	private String style = "";
	private String cssClass = "";
	private String readonly = "false";
	private String dicttype;//词典类型
	private int levelFlag = -1;//显示前几级
	private String value;//原始值
	private String checkboxes;//1：单选；其它的为多选
	private String onchange;
	private String valueOf;//主要用来获取字典表中的字段值，如value,name,fullname等等；默认的是value
	private int checkLevelFlag = -1;//第几级的值可以进行选择,-1:所有均可选择，0:根节点选择,-2:叶子节点
	private String dictFlag = "";//数据字典标识，与字典值中的标识对应
	private String pid;//根节点ID，转为为0
	private String sqlStr;//select id,value,text,fulltext,pid from table; ##:=
	private String sqlName;//XML配置SQL语句
	private String pflied;//级联父字段


	public String getPflied() {
		return pflied;
	}

	public void setPflied(String pflied) {
		this.pflied = pflied;
	}

	public String getSqlName() {
		return sqlName;
	}

	public void setSqlName(String sqlName) {
		this.sqlName = sqlName;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getSqlStr() {
		return sqlStr;
	}

	public void setSqlStr(String sqlStr) {
		this.sqlStr = sqlStr;
	}

	public String getDictFlag() {
		return dictFlag;
	}

	public void setDictFlag(String dictFlag) {
		this.dictFlag = dictFlag;
	}

	public String getValueOf() {
		return valueOf;
	}

	public void setValueOf(String valueOf) {
		this.valueOf = valueOf;
	}

	public int getCheckLevelFlag() {
		return checkLevelFlag;
	}

	public void setCheckLevelFlag(int checkLevelFlag) {
		this.checkLevelFlag = checkLevelFlag;
	}

	public int doStartTag() {
		try {
//			String ids = (String) ServletActionContext.getValueStack(
//					ServletActionContext.getRequest()).findValue(name,String.class);
			JspWriter out = pageContext.getOut();
			String view = "";
//			if(name.equals("basestatus") || name.equals("belongOrg")){
//				System.out.println(id);
//			}
			
			if(StringUtils.isBlank(cssClass)){
				cssClass = "textInput";
			}
			if(StringUtils.isNotBlank(name)){
				id = name;
			}
			
			if(StringUtils.isNotBlank(viewFlag) || !viewFlag.equals("true")){
				viewFlag = "false";
			}
			

			if(checkboxes == null || checkboxes.equals("")){
				checkboxes = "1";
			}
			
			if(readonly == null || readonly.equals("")){
				readonly = "false";
			}
			
			if(org.apache.commons.lang.StringUtils.isBlank(valueOf)){
				valueOf = "value";
			}
			
			
			
			if(org.apache.commons.lang.StringUtils.isBlank(pid)){
				pid = "0";
			}
			
			
			
			StringBuffer html = new StringBuffer();
			
			
			if(value != null && !value.equals("")){
				QueryAdapter queryAdapter = new QueryAdapter();
				String sql = "";
				
				//数据字典获取数据
				if(dicttype != null && !dicttype.equals("") && !dicttype.equals("null")){
					sql = "select pid,divalue,diname,dicfullname,parentid from bs_t_sm_dicitem t where (t.dtcode = '" + dicttype + "')";
				}
				
				//SQL语句获取数据
				if(sqlStr != null && !sqlStr.equals("") && !sqlStr.equals("null")){
					sqlStr = sqlStr.replaceAll("##","=");
					sqlStr = sqlStr.replaceAll("#t","'");
					

					int start = sqlStr.indexOf("{");
					int end = sqlStr.indexOf("}");
					if(start != -1 && end != -1 && end > start){
						String baseSchema = sqlStr.substring(start + 1, end);
						String table = RemedySession.UtilInfor.getTableName(baseSchema);
						sqlStr = sqlStr.substring(0,start) + table + sqlStr.substring(end + 1);
					}
					
					
					sql = sqlStr;
				}
				
				DataTable dt = null;
				if(StringUtils.isNotBlank(sql)){
					dt = queryAdapter.executeQuery(sql);
				}
				
				//XML配置SQL语句
				if(StringUtils.isNotBlank(sqlName)){
					RQuery query = new RQuery(sqlName,null);
					dt = query.getDataTable();
				}
				
				List<Map<String,String>> list = new ArrayList<Map<String,String>>();
				String[] values = value.split("\\,");
				StringBuffer viewsb = new StringBuffer();
				for(Object obj:dt.getRowList()){
					DataRow dr = (DataRow)obj;
					for(String vl:values){
						if(valueOf.equals("value") && dr.getString(1).equals(vl)){
							if(viewsb.toString().equals("")){
								viewsb.append(dr.getString(2));
							}else{
								viewsb.append("," + dr.getString(2));
							}
						}
						
						if(valueOf.equals("fullname") && dr.getString(3).equals(vl)){
							if(viewsb.toString().equals("")){
								viewsb.append(dr.getString(3));
							}else{
								viewsb.append("," + dr.getString(3));
							}
						}
					}
					
				}
				view = viewsb.toString();
//				System.out.println("view:" + view);
			}else{
				value = "";
			}
			if(view == null || view.equals("") || view.equals("null")){
				view = "";
			}
			
			
			
			if(org.apache.commons.lang.StringUtils.isBlank(dictFlag)){
				dictFlag = "";
			}
			
			if(org.apache.commons.lang.StringUtils.isBlank(pflied)){
				pflied = "";
			}
			
			//html.append("<%@ include file='/common/plugin/dictTree/jsp/dict.jsp' %>");
			html.append("<input type=\"hidden\" name=\"" + name + "\" id=\"" + id + "\" value=\"" + value + "\"");
			
			
			//数据传递中的特殊字符格式转换
			if(sqlStr != null && !sqlStr.equals("") && !sqlStr.equals("null")){
				sqlStr = sqlStr.replaceAll("=","##");
				sqlStr = sqlStr.replaceAll("'","#t");
			}
			
			if(onchange != null && !onchange.equals("")){
				html.append(" onChange=\""+onchange+"\"");
			}
			html.append(" />");
			String url = "/dict/dictTree.action?id=" + id + "&&dictType=" + dicttype + "&&checkboxes=" + checkboxes + "&&levelFlag=" + levelFlag + "&&checkLevelFlag=" + checkLevelFlag + "&&valueOf=" + valueOf + "&&dictFlag=" + dictFlag + "&&pid=" + pid + "&&sqlStr=" + sqlStr + "&&sqlName=" + sqlName + "&&pflied=" + pflied;
			if(viewFlag.equals("true")){
				html.append("<input type=\"text\" class=\"" + cssClass + "\" style=\"" + style + "\" id=\"view_" + id + "\" value=\"" + view + "\" title=\"" + view + "\" readonly=\"true\"/>");
			}else{
				html.append("<input type=\"text\" class=\"" + cssClass + "\" style=\"" + style + "\" onclick=\"initDivs(this,'" + url + "');\" onkeyup=\"toQuery();\"  id=\"view_" + id + "\" value=\"" + view + "\" title=\"" + view + "\" readonly=\"" + readonly + "\" />");
			}
			out.write(html.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}


	public String getDicttype() {
		return dicttype;
	}

	public void setDicttype(String dicttype) {
		this.dicttype = dicttype;
	}

	public int getLevelFlag() {
		return levelFlag;
	}

	public void setLevelFlag(int levelFlag) {
		this.levelFlag = levelFlag;
	}

	public String getReadonly() {
		return readonly;
	}

	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}

	public int doEndTag() throws JspTagException {
		return EVAL_PAGE;
	}

	public String getSubTree() {
		return "";
	}

	public String getViewFlag() {
		return viewFlag;
	}

	public void setViewFlag(String viewFlag) {
		this.viewFlag = viewFlag;
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

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getCheckboxes() {
		return checkboxes;
	}

	public void setCheckboxes(String checkboxes) {
		this.checkboxes = checkboxes;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}


	public String getOnchange() {
		return onchange;
	}

	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}


}
