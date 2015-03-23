package com.ultrapower.eoms.common.tag.select.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.ultrapower.eoms.common.RecordLog;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.ultrasm.model.DicItem;
import com.ultrapower.eoms.ultrasm.service.DicManagerService;

/**
 * 下拉框、级联菜单底层控制
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version Jun 22, 2010 3:49:17 PM
 */
public class Select {
	
	private String name = "";
	private String style = "select";
	private String childrenname = "";
	private String dataDicTypeCode = "";
	private String diValue = "";
	private String onChangeFun = "";
	private HashMap customData = null;
	private String customSql = "";
	private String customStr = "";
	private String value;
	private String isnull = "true";
	private String childrenUrl = "/dicManager/getChildNode.action";
	public Select(String name){
		this.name = name;
	}
	
	/**
	 * 标签底层类初始
	 * @param name 下拉框名称
	 * @param style 显示样式
	 * @param childrenname 下一级节点的name
	 * @param dataDicTypeCode 数据字典类型编码
	 * @param onChangeFun onChange事件函数
	 * @param value 默认值
	 * @param customData 自定义下拉框数据 为Man类型
	 * @param customSql 自定义sql脚本数据
	 */
	public Select(String name,String style,String childrenname,String dataDicTypeCode,String diValue, String onChangeFun,String value, HashMap customData,String customSql,String customStr,String isnull,String childrenUrl){
		this.name = name;
		if(!StringUtils.checkNullString(style).equals(""))
			this.style = style;
		this.childrenname = childrenname;
		this.dataDicTypeCode = dataDicTypeCode;
		this.diValue = diValue;
		this.onChangeFun = onChangeFun;
		this.value = value;
		this.customData = customData;
		this.customSql = customSql;
		this.customStr = customStr;
		if(!StringUtils.checkNullString(isnull).equals(""))
			this.isnull = isnull;
		if(!"".equals(StringUtils.checkNullString(childrenUrl)))
			this.childrenUrl = childrenUrl;
	}
	
	/**
	 * 获取输出的页面下拉标签字符串
	 * @return
	 */
	public String getSelect(HttpServletRequest request){
		StringBuffer htmlStr = new StringBuffer(1024);
		htmlStr.append("<select id=\""+name+"\" name=\""+name+"\" class=\""+style+"\"");
		if(!StringUtils.checkNullString(childrenname).equals("") && request!=null)
			onChangeFun = "insertSelectData('"+request.getContextPath()+"/"+childrenUrl+"','"+name+"','"+dataDicTypeCode+"','"+childrenname+"');";
		htmlStr.append(" onchange=\""+onChangeFun+"\"");
		htmlStr.append(">");
		if(customData!=null){//自定义下拉框数据值
			if("true".equals(StringUtils.checkNullString(isnull)))
				htmlStr.append("<option value=\"\"></option>");
			Iterator<String> iterator = customData.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				String text = (String) customData.get(key);
				if(value.equals(key))
					htmlStr.append("<option value=\""+key+"\" selected>"+text+"</option>");
				else
					htmlStr.append("<option value=\""+key+"\">"+text+"</option>");
			}
		}else if(!StringUtils.checkNullString(customStr).equals("")){
			if("true".equals(StringUtils.checkNullString(isnull)))
				htmlStr.append("<option value=\"\"></option>");
			String[] cStrArr = customStr.split(";");//1,高;2,中;3,低
			for(int i=0;i<cStrArr.length;i++){
				String[] arr = cStrArr[i].split(",");
				if(arr.length == 2){
					if(value.equals(arr[0]))
						htmlStr.append("<option value=\""+arr[0]+"\" selected>"+arr[1]+"</option>");
					else
						htmlStr.append("<option value=\""+arr[0]+"\">"+arr[1]+"</option>");
				}
			}
		}else if(!StringUtils.checkNullString(customSql).equals("")){//自定义脚本值
			QueryAdapter queryAdapter = new QueryAdapter();
			DataTable dataTable = null;
			try{
				dataTable = queryAdapter.executeQuery(customSql, null, 0, 0, 2);
			}catch(Exception e){
				RecordLog.printLog(e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
			}
			int dataTableLen = 0;
			if(dataTable!=null)
				dataTableLen = dataTable.length();
			DataRow dataRow;
			if("true".equals(StringUtils.checkNullString(isnull)))
				htmlStr.append("<option value=\"\"></option>");
			for(int row=0;row<dataTableLen;row++){
				dataRow = dataTable.getDataRow(row);
				String key = dataRow.getString(0);
				String text = dataRow.getString(1);
				if(value.equals(key)){
					htmlStr.append("<option value=\""+key+"\" selected>"+text+"</option>");
				}else{
					htmlStr.append("<option value=\""+key+"\">"+text+"</option>");
				}
			}
		}else if(!StringUtils.checkNullString(dataDicTypeCode).equals("")){//数据字典查询的下拉框数据
			DicManagerService dicManagerService  = (DicManagerService)WebApplicationManager.getBean("dicManagerService");
			List<DicItem> dicItsmList = null;
			if("".equals(StringUtils.checkNullString(diValue))) {
				dicItsmList = dicManagerService.getRootItsmByDicType(dataDicTypeCode);				
			} else {
				dicItsmList = dicManagerService.getDicItem(diValue, dataDicTypeCode);
			}
			int dicItsmListLen = 0;
			if(dicItsmList!=null)
				dicItsmListLen = dicItsmList.size();
			if("true".equals(StringUtils.checkNullString(isnull))){
				if("".equals(value))
				{
					htmlStr.append("<option value=\"\" selected></option>");
				}else{
					htmlStr.append("<option value=\"\"></option>");
				}
			}
			for(int row=0;row<dicItsmListLen;row++){
				DicItem dicItem = dicItsmList.get(row);
				if(value!=null&&value.equals(dicItem.getDivalue())){
					htmlStr.append("<option value=\""+dicItem.getDivalue()+"\" selected>"+dicItem.getDiname()+"</option>");
				}else if(value==null&&dicItem.getIsdefault().equals("1")){
					htmlStr.append("<option value=\""+dicItem.getDivalue()+"\" selected>"+dicItem.getDiname()+"</option>");
				}else{
					htmlStr.append("<option value=\""+dicItem.getDivalue()+"\">"+dicItem.getDiname()+"</option>");
				}
			}
		}
		htmlStr.append("</select>");
		//RecordLog.printLog(htmlStr.toString(), RecordLog.LOG_LEVEL_INFO);
		return htmlStr.toString();
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
	public String getChildrenname() {
		return childrenname;
	}
	public void setChildrenname(String childrenname) {
		this.childrenname = childrenname;
	}
	public String getDataDicTypeCode() {
		return dataDicTypeCode;
	}
	public void setDataDicTypeCode(String dataDicTypeCode) {
		this.dataDicTypeCode = dataDicTypeCode;
	}
	public String getOnChangeFun() {
		return onChangeFun;
	}
	public void setOnChangeFun(String onChangeFun) {
		this.onChangeFun = onChangeFun;
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
	public String getDiValue() {
		return diValue;
	}
	public void setDiValue(String diValue) {
		this.diValue = diValue;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
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
}
