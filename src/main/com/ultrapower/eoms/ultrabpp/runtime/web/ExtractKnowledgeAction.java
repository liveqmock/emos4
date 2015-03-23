package com.ultrapower.eoms.ultrabpp.runtime.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.constants.PropertiesUtils;
import com.ultrapower.eoms.common.core.component.data.DataAdapter;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.web.BaseAction;

public class ExtractKnowledgeAction extends BaseAction{
	private String baseId;
	private String baseSn;
	private List<Map<String, String>> knowledgeInfoList;
	private String kmURL;
	private String kmProName;
	private String kmId;
	
	public String showKnowledge(){
		String tempURL = PropertiesUtils.getProperty("km.url");
		kmURL = getRequest().getServerName()+tempURL.substring(tempURL.indexOf(":"));
		kmProName = PropertiesUtils.getProperty("km.projectName");
		knowledgeInfoList = new ArrayList<Map<String, String>>();
		String kmkeyword = "";
		String kmcaption = "";
		String kmcategory = "";
		String kmcreator = "";
		String kmID = "";
		String sql = "select k9.c100001002 caption,k9.c100001014 keyword,k9.c100001005 category,k9.c100001003 creator,k9.c100001001 as kmId from k9,k18 where k9.c100001001=k18.c100001002 and (k18.c100001001='"
				+ baseId + "' or k18.c100001001='"+baseSn+"') and k18.c100001005 is null";
		QueryAdapter qAdapter = new QueryAdapter("kmsource");
		DataTable dTable = qAdapter.executeQuery(sql, null, 0);
		if(dTable != null){
			int tabLen = dTable.length();
			if(tabLen > 0){
				for(int i=0;i<tabLen;i++){
					Map<String,String> knowledge = new HashMap<String,String>();
					kmkeyword = dTable.getDataRow(i).getString("keyword");
					kmcaption = dTable.getDataRow(i).getString("caption");
					kmcategory = dTable.getDataRow(i).getString("category");
					kmcreator = dTable.getDataRow(i).getString("creator");
					kmID = dTable.getDataRow(i).getString("kmId");
					knowledge.put("kmkeyword", kmkeyword);
					knowledge.put("kmcaption", kmcaption);
					knowledge.put("kmcategory", kmcategory);
					knowledge.put("kmcreator", kmcreator);
					knowledge.put("kmId", kmID);
					knowledgeInfoList.add(knowledge);
				}
			}
		}
		return "success";
	}
	
	/*
	 * 取消知识关联
	 */
	public void cancelRelate(){
		String[] kmIds = kmId.split(",");
		if(kmIds.length > 0){
			String sql = "update k18 set c100001005='0' where ";
			for(int i=0;i<kmIds.length;i++){
				sql += " c100001002 = '"+kmIds[i]+"' or";
			}
			if(sql!=""){
				sql = sql.substring(0, sql.length()-2);
			}
			DataAdapter dataAdapter  = new DataAdapter("kmsource");
			dataAdapter.execute(sql,null);
		}
		this.renderText("true");
	}
	
	
	public String getBaseId() {
		return baseId;
	}
	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}


	public List<Map<String, String>> getKnowledgeInfoList() {
		return knowledgeInfoList;
	}


	public void setKnowledgeInfoList(List<Map<String, String>> knowledgeInfoList) {
		this.knowledgeInfoList = knowledgeInfoList;
	}

	public String getKmURL() {
		return kmURL;
	}

	public void setKmURL(String kmURL) {
		this.kmURL = kmURL;
	}

	public String getKmProName() {
		return kmProName;
	}

	public void setKmProName(String kmProName) {
		this.kmProName = kmProName;
	}

	public String getKmId() {
		return kmId;
	}

	public void setKmId(String kmId) {
		this.kmId = kmId;
	}

	public String getBaseSn() {
		return baseSn;
	}

	public void setBaseSn(String baseSn) {
		this.baseSn = baseSn;
	}
	
	
}
