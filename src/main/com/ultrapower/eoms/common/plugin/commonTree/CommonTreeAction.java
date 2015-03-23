package com.ultrapower.eoms.common.plugin.commonTree;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.relaxng.datatype.Datatype;

import net.sf.json.JSONArray;

import com.ultrapower.eoms.common.core.component.data.DataAdapter;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.component.tree.manager.TreeManager;
import com.ultrapower.eoms.common.core.component.tree.model.DtreeBean;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;

public class CommonTreeAction extends BaseAction{
	private String id;
	private String pid;
	private String pageURL;//业务类的URl
	private String type;//业务分类
	private int level = -1;//确定最多几级，根据业务需要而定
	private String sql = "select id,name,fullname,pid,tlevel from bs_t_sm_commonTree where pid = ? and type = ?";
	private QueryAdapter query;
	private TreeManager treeManager;
	private Map<String,String> treeMap;
	private String fullname;
	
	private String item;   //要查询的数据项
	private String itemValue;  //查询的数据项的值
	

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String commonTree(){
		return SUCCESS;
	}
	
	//树展示
	public void loadXML(){
		if(StringUtils.isBlank(id)){
			id = "0";
		}
		List<DtreeBean> dtreeList = new ArrayList<DtreeBean>();
		if(StringUtils.isNotBlank(type)){//业务分类不可为空
			query = new QueryAdapter();
			DataTable dt = query.executeQuery(sql,id,type);
			for(int row=0;row<dt.length();row++){
				DataRow dataRow = dt.getDataRow(row);
				DtreeBean dtreeBean = new DtreeBean();
				dtreeBean.setId(dataRow.getString("id"));
				dtreeBean.setUserdata(dataRow.getRowHashMap());
				dtreeBean.setText(dataRow.getString("name"));
				dtreeList.add(dtreeBean);
			}
		}
		treeManager = new TreeManager();		
		this.renderXML(treeManager.createDhtmlxtreeXml(dtreeList,id));
	}
	
	/**
	 * 树节点修改
	 * @return
	 */
	public String editTreeNode(){
		if(StringUtils.isNotBlank(id)){
			query = new QueryAdapter();
			String querySql = "select * from bs_t_sm_commonTree where id = ?";
			DataTable dt = query.executeQuery(querySql,id);
			if(dt != null && dt.length() == 1){
				treeMap = dt.getDataRow(0).getRowHashMap();
			}
		}
		if(treeMap == null){
			treeMap = new HashMap<String, String>();
			treeMap.put("TYPE",type);
			treeMap.put("PID",pid);
		}
		return SUCCESS;
	}
	
	/**
	 * 树节点保存 及相关功能保存
	 */
	public String saveTreeNode(){
		if(StringUtils.isNotBlank(treeMap.get("pid"))){
			query = new QueryAdapter();
			String querySql = "select id,pid,name,fullname,tlevel from bs_t_sm_commonTree where id = ?";
			DataTable dt = query.executeQuery(querySql,treeMap.get("pid"));
			if(dt != null && dt.length() == 1){
				treeMap.put("fullname",dt.getDataRow(0).getString("FULLNAME") + "." + treeMap.get("name"));
				treeMap.put("tlevel",(Integer.parseInt(dt.getDataRow(0).getString("TLEVEL")) + 1) + "");
			}
		}
		
		if(StringUtils.isBlank(treeMap.get("pid")) || treeMap.get("pid").equals("0")){
			treeMap.put("pid","0");
			treeMap.put("tlevel","1");
			treeMap.put("fullname",treeMap.get("name"));
		}
		
		DataTable table = new DataTable("bs_t_sm_commonTree");
		String[] keys = { "id" };
		table.setPrimaryKey(keys);
		DataRow dr = new DataRow();
		DataAdapter dataAdapter = new DataAdapter();
		if(StringUtils.isBlank(treeMap.get("id"))){
			id = UUID.randomUUID().toString();
			treeMap.put("id",id);
			dr.setOptype(1);
			for(String k:treeMap.keySet()){
				dr.put(k,treeMap.get(k));
			}
			table.putDataRow(dr);
			dataAdapter.executeAdd(table);
			this.getRequest().setAttribute("msg","true");
		}else{
			dr.setOptype(2);
			for(String k:treeMap.keySet()){
				dr.put(k,treeMap.get(k));
			}
			table.putDataRow(dr);
			dataAdapter.executeUpdate(table);
			this.getRequest().setAttribute("msg","true");
			
			updateFullName(treeMap.get("id"),treeMap.get("fullname"));
		}
		return this.findForward("editTreeNode");
	}
	
	
	/**
	 * 更新本节点下所有的节点fullname
	 */
	private void updateFullName(String id,String fullname){
		DataAdapter dataAdapter = new DataAdapter();
		String sql = "select id,name,fullname from bs_t_sm_commontree where pid = '" + id + "'";
		String updateSql = "update bs_t_sm_commontree t set t.fullname = ? where id = ?";
		query = new QueryAdapter();
		DataTable dt = query.executeQuery(sql);
		for(Object obj:dt.getRowList()){
			DataRow dr = (DataRow)obj;
			String tmpId = dr.getString("ID");
			String tmpName = dr.getString("NAME");
			String tmpFullName = fullname + "." + tmpName; 
			Object[] values = {tmpFullName,tmpId};
			dataAdapter.execute(updateSql, values);
			this.updateFullName(tmpId,tmpFullName);
		}
	}
	
	/**
	 * 树节点删除
	 */
	public String delTreeNode(){
		if(StringUtils.isNotBlank(id)){
			query = new QueryAdapter();
			String querySql = "select id,pid,name,fullname,tlevel from bs_t_sm_commonTree where id = ?";
			DataTable dt = query.executeQuery(querySql,id);
			if(dt != null && dt.length() == 1){
				treeMap = new HashMap<String, String>();
				treeMap.put("pid",dt.getDataRow(0).getString("PID"));
			}
			
			String delSql = "delete from bs_t_sm_commonTree where id in (select id from bs_t_sm_commonTree where 1=1 start with id = ? connect by pid = prior id)";
			DataAdapter ada = new DataAdapter();
			String[] objs = {id};
			int i = ada.execute(delSql,objs);
			System.out.println("i:" + i);
			this.getRequest().setAttribute("msg","true");
		}
		return this.findForward("editTreeNode");
	}
	
	/**
	 * 通过指定的条件查询相关树配置信息AJAX  返回JSON
	 * type:类型
	 * where:搜索条件
	 */
	private Map<String,String> conditionMap;
	public void getTreeData() throws UnsupportedEncodingException{
		if(StringUtils.isNotBlank(type)){
			String querySql = "select * from bs_t_sm_commonTree where type = ?";
			if(conditionMap != null){
				String conStr = "";
				for(String key:conditionMap.keySet()){
					String value = java.net.URLDecoder.decode(conditionMap.get(key), "UTF-8");
					if(StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)){
						String[] values = value.split(",");
						for(String vl:values){
							if(StringUtils.isNotBlank(vl)){
								if(!conStr.equals("")){
									conStr += " or ";
								}
								conStr += key + " = '" +  vl + "' ";
							}
						}
					}
				}
				querySql += " and (" + conStr + ")";
			}
			query = new QueryAdapter();
			DataTable dt = query.executeQuery(querySql,type);
			List<Map> list = new ArrayList<Map>();
			for(Object obj:dt.getRowList()){
				DataRow dr = (DataRow)obj;
				list.add(dr.getRowHashMap());
			}
			String jsonStr = JSONArray.fromObject(list).toString();
			System.out.println(jsonStr);
			this.renderText(jsonStr);
		}
	}
	
	
	/**
	 * @param item 数据项名称
	 * @fullname bs_t_sm_commonTree保存的数据项的全名
	 * @return  itemId 数据项的ID
	 */
	
	public void  getItemIdByName(){
		String itemId="";
		query = new QueryAdapter();
		if(StringUtils.isNotBlank(fullname)){
			String querySql = "select * from bs_t_sm_commonTree where  type='"+type+"' and fullname = '" + fullname+"'" ;
			DataTable dt = query.executeQuery(querySql);
			if(dt!=null&&dt.length()>0){
				itemId =  dt.getDataRow(0).getString(item);
			}
		}
		renderText(itemId);
	}  
	
	
	
	/**
	 * @param item  字段项
	 * @param type  类型
	 * @param itemValue 字段项的值
	 * @return
	 */
	
	public void isExistItem(){
	query = new QueryAdapter();
	if(!"".equals(itemValue)&&itemValue!=null){
		String querySql = "select count(*) count from bs_t_sm_commonTree where type= '"+type+"' and "+item+"='"+itemValue+"'";
		DataTable dt = query.executeQuery(querySql);
		if(dt.getDataRow(0).getString("count").equals("0")){
			renderText ("nonExist");
		}
		else{
			renderText("exist");
		}
	}
	}
	
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getPid() {
		return pid;
	}


	public void setPid(String pid) {
		this.pid = pid;
	}


	public String getPageURL() {
		return pageURL;
	}


	public void setPageURL(String pageURL) {
		this.pageURL = pageURL;
	}


	public Map<String, String> getTreeMap() {
		return treeMap;
	}


	public void setTreeMap(Map<String, String> treeMap) {
		this.treeMap = treeMap;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Map<String, String> getConditionMap() {
		return conditionMap;
	}

	public void setConditionMap(Map<String, String> conditionMap) {
		this.conditionMap = conditionMap;
	}
	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getItemValue() {
		return itemValue;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

}
