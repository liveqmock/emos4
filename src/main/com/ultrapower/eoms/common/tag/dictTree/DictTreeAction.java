package com.ultrapower.eoms.common.tag.dictTree;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.derby.tools.sysinfo;

import com.ultrapower.eoms.common.core.component.data.DataAdapter;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.component.rquery.RQuery;
import com.ultrapower.eoms.common.core.component.tree.manager.TreeManager;
import com.ultrapower.eoms.common.core.component.tree.model.DtreeBean;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.ultrasm.model.DicItem;
import com.ultrapower.eoms.ultrasm.service.DicManagerService;
import com.ultrapower.remedy4j.core.RemedySession;


/**
 * 主要用于下拉框树的功能
 * @author liuchuanzu
 * 2012-08-23
 */
public class DictTreeAction extends BaseAction {
	private DicManagerService dicManagerService;
	private TreeManager itreeManager;
	private String id;//根节点ID，转为为0
	private String dicttype;//词典类型
	private String checkLevelFlag;//第几级的值可以进行选择,-1:所有均可选择，0:根节点选择,-2:叶子节点
	private String levelFlag;
	private String sqlStr;//select id,value,text,fulltext,pid from table; ##:=
	private String sqlName;//XML配置SQL语句
	QueryAdapter queryAdapter = new QueryAdapter();
	DataAdapter dataAdapter = new DataAdapter();
	private String name;
	private String queryStr;//查询语句
	private String pflied;
	private String defaultValue;//设置默认值  主要用来传递到SQL语句中拼条件


	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getPflied() {
		return pflied;
	}

	public void setPflied(String pflied) {
		this.pflied = pflied;
	}

	public void loadxml() {
		if(StringUtils.isNotBlank(queryStr)){
			try {
//				queryStr = new String(queryStr.getBytes(),"utf-8");
				queryStr = URLDecoder.decode(queryStr,"utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			System.out.println(queryStr);
			//unix下不需要上面转义
			queryStr = queryStr.replaceAll("%2B","+");
		}
		if(pflied != null && pflied.equals("null")){
			pflied = "";
		}
		if(StringUtils.isNotBlank(pflied)){
			try {
				pflied = URLDecoder.decode(pflied,"utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			System.out.println(pflied);
			//unix下不需要上面转义
			pflied = pflied.replaceAll("%2B","+");
		}
		itreeManager = new TreeManager();
		DtreeBean dtree = new DtreeBean();
		dtree.setId(id);
		if(StringUtils.isNotBlank(dicttype) && !dicttype.equals("null")){//数据字典
			List<DicItem> list= dicManagerService.getDicItemByDicType(dicttype);
			getDataByDict(dtree,list);
		}else if((StringUtils.isNotBlank(sqlStr)&& !sqlStr.equals("null")) || (StringUtils.isNotBlank(sqlName)&& !sqlName.equals("null"))){
			DataTable dt = null; 
			if(StringUtils.isNotBlank(sqlStr)&& !sqlStr.equals("null")){//SQL语句
				sqlStr = sqlStr.replaceAll("##","=");
				sqlStr = sqlStr.replaceAll("#t","'");
				
				int start = sqlStr.indexOf("{");
				int end = sqlStr.indexOf("}");
				if(start != -1 && end != -1 && end > start){
					String baseSchema = sqlStr.substring(start + 1, end);
					String table = RemedySession.UtilInfor.getTableName(baseSchema);
					sqlStr = sqlStr.substring(0,start) + table + sqlStr.substring(end + 1);
				}
				System.out.println("sqlStr:" + sqlStr);

				Object[] obj = {pflied};
				if(StringUtils.isNotBlank(pflied)){
					dt = queryAdapter.executeQuery(sqlStr.toString(),obj);				
				}else{
					dt = queryAdapter.executeQuery(sqlStr.toString());
				}
			}else if(StringUtils.isNotBlank(sqlName)&& !sqlName.equals("null")){//XML配置SQL
				HashMap<String,String> valuemap  = new HashMap<String, String>();
				valuemap.put("dictvalue",pflied);
				valuemap.put("defaultValue",defaultValue);
				RQuery query = new RQuery(sqlName,valuemap);
				dt = query.getDataTable();
			}
			
			
			List<Map<String,String>> list = new ArrayList<Map<String,String>>();
			if(dt != null){
				for(Object obj:dt.getRowList()){
					Map<String,String> map = new HashMap<String, String>();
					DataRow dr = (DataRow)obj;
					map.put("id",dr.getString(0).replaceAll("&","/"));
					map.put("value",dr.getString(1));
					map.put("name",dr.getString(2).replaceAll("&","/"));
					map.put("fullname",dr.getString(3));
					map.put("pid",dr.getString(4));
					list.add(map);
				}
				getDataBySql(dtree,list);
			}
		}
		String xml = itreeManager.createDhtmlxtreeXml(dtree.getChildList(), id);
		System.out.println(xml);
		renderXML(xml);
	}

	/**
	 * 从数据字典中获取数据
	 */
	private void getDataByDict(DtreeBean dtree,List<DicItem> dlist){		
		List<DtreeBean> child = new ArrayList<DtreeBean>();
		List<DicItem> tempList = new ArrayList<DicItem>();
		if(StringUtils.isNotBlank(queryStr) && !queryStr.equals("null")){
			String[] strs;
			if(queryStr.indexOf(",") != -1){
				strs =queryStr.split("\\,");
			}else if(queryStr.indexOf("，") != -1){
				strs =queryStr.split("\\，");
			}else{
				strs = new String[]{queryStr};
			}
			Map<String,DicItem> tempMap = new HashMap<String, DicItem>();//去重
			for(DicItem dt:dlist){
				for(String str:strs){
					if(dt.getDicfullname().indexOf(str.trim()) != -1 || dt.getDiname().indexOf(str.trim()) != -1 || dt.getDivalue().indexOf(str.trim()) != -1){
						dt.setParentid("0");
						tempMap.put(dt.getPid(),dt);
					}
				}
			}
			tempList = (List<DicItem>) tempMap.values();
		}else{
			tempList.addAll(dlist);
		}
		String pid = dtree.getId();
		int i=0;
		for(DicItem dt:tempList){
			if(dt.getParentid().equals(pid)){
				i++;
				DtreeBean dbean = new DtreeBean();
				dbean.setParentId(pid);
				dbean.setText(dt.getDiname());
				dbean.setId(dt.getPid());
				HashMap<String,String> data = new HashMap<String, String>();
				data.put("fullname",dt.getDiname());
				data.put("dictvalue",dt.getDivalue());
				dbean.setUserdata(data);
				//判断是否有子节点

				int nums = 0;
				for(DicItem dt2:dlist){
					if(dt2.getParentid().equals(dt.getPid())){
						nums = 1;
						break;
					}
				}
				dbean.setChild(nums+"");
//				getDataByDict(dbean,dlist);
				child.add(dbean);
			}
		}
		dtree.setChildList(child);
	}
	
	
	/**
	 * 从SQL中获取数据
	 */
	private void getDataBySql(DtreeBean dtree,List<Map<String,String>> dlist){
		List<DtreeBean> child = new ArrayList<DtreeBean>();
		List<Map<String,String>> tempList = new ArrayList<Map<String,String>>();
		if(StringUtils.isNotBlank(queryStr) && !queryStr.equals("null")){
			String[] strs;
			if(queryStr.indexOf(",") != -1){
				strs =queryStr.split("\\,");
			}else if(queryStr.indexOf("，") != -1){
				strs =queryStr.split("，");
			}else{
				strs = new String[]{queryStr};
			}
			Map<String,Map<String,String>> tempMap = new HashMap<String, Map<String,String>>();//去重
			for(Map<String,String> dt:dlist){
				for(String str:strs){
					if(dt.get("fullname").indexOf(str.trim()) != -1 || dt.get("name").indexOf(str.trim()) != -1 || dt.get("value").indexOf(str.trim()) != -1){
						dt.put("pid","0");
						tempMap.put(dt.get("id"),dt);
					}
				}
			}
			for(Map<String,String> map:tempMap.values()){
				tempList.add(map);
			}
		}else{
			tempList.addAll(dlist);
		}
		
		
		
		String pid = dtree.getId();
		for(Map<String,String> dt:tempList){
			if(dt.get("pid").equals(pid)){
				DtreeBean dbean = new DtreeBean();
				dbean.setParentId(pid);
				dbean.setText(dt.get("name"));
				dbean.setId(dt.get("id"));
				HashMap<String,String> data = new HashMap<String, String>();
				data.put("fullname",dt.get("fullname"));
				data.put("dictvalue",dt.get("value"));
				dbean.setUserdata(data);

				int nums = 0;
				for(Map<String,String> dt2:dlist){
					if(dt2.get("pid").equals(dt.get("id"))){
						nums = 1;
						break;
					}
				}
				dbean.setChild(nums+"");
				
				child.add(dbean);
			}
		}
		dtree.setChildList(child);
	}

	
	public String getQueryStr() {
		return queryStr;
	}

	public void setQueryStr(String queryStr) {
		this.queryStr = queryStr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getCheckLevelFlag() {
		return checkLevelFlag;
	}

	public void setCheckLevelFlag(String checkLevelFlag) {
		this.checkLevelFlag = checkLevelFlag;
	}

	public String getLevelFlag() {
		return levelFlag;
	}

	public void setLevelFlag(String levelFlag) {
		this.levelFlag = levelFlag;
	}

	public TreeManager getItreeManager() {
		return itreeManager;
	}

	public void setItreeManager(TreeManager itreeManager) {
		this.itreeManager = itreeManager;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public DicManagerService getDicManagerService() {
		return dicManagerService;
	}

	public void setDicManagerService(DicManagerService dicManagerService) {
		this.dicManagerService = dicManagerService;
	}

	public String getSqlStr() {
		return sqlStr;
	}

	public void setSqlStr(String sqlStr) {
		this.sqlStr = sqlStr;
	}

	public String getDicttype() {
		return dicttype;
	}

	public void setDicttype(String dicttype) {
		this.dicttype = dicttype;
	}

	public String getSqlName() {
		return sqlName;
	}

	public void setSqlName(String sqlName) {
		this.sqlName = sqlName;
	}
}
