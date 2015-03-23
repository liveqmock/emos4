package com.ultrapower.eoms.common.plugin.autosearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import org.springframework.util.Assert;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.rquery.RQuery;
import com.ultrapower.eoms.common.core.util.CharsetUtil;
import com.ultrapower.eoms.common.core.web.BaseAction;

@SuppressWarnings({"serial","rawtypes"})
public class AutoInputAction extends BaseAction {
	private String paramKey; // 输入的关键字
	private Map<String,String> opt = new HashMap<String, String>();//附加的查询参数
	
	private String sqlQuery;// 配置的查询sql

	public String getParamKey() {
		return paramKey;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	public String getSqlQuery() {
		return sqlQuery;
	}

	public void setSqlQuery(String sqlQuery) {
		this.sqlQuery = sqlQuery;
	}

	/**
	 * 自动检索
	 */
	public void autoSearch() {
		if (paramKey == null || paramKey.trim().equals("") || sqlQuery == null
				|| sqlQuery.trim().equals("")) {
			return;
		} else {
			paramKey = paramKey.toLowerCase();
		}
		HashMap<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("paramKey", paramKey);
		RQuery rquery = new RQuery(sqlQuery, conditionMap);
		rquery.setPageSize(20);
		rquery.setPage(1);
		DataTable dt = rquery.getDataTable();
		List<KeyValue> resultList = new ArrayList<KeyValue>();
		if (dt != null && dt.length() > 0) {
			for (Object obj : dt.getRowList()) {
				DataRow dr = (DataRow) obj;
				String key = dr.getString("key");
				String value = dr.getString("value");
				KeyValue model = new KeyValue(key, value);
				resultList.add(model);
			}
		}
		JSON obj = JSONSerializer.toJSON(resultList);
		String output = obj.toString();
		this.renderText(output);
	}
	/**
	 * 自动搜索升级版
	 */
	public void autoSearchEx() {
		Assert.notNull(paramKey,"自动查询关键字为空");
		CharsetUtil.urlDecode(opt);
		opt.put("paramKey", paramKey.toLowerCase());
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		RQuery rquery = new RQuery(sqlQuery, (HashMap) opt);
		rquery.setPageSize(20);
		rquery.setPage(1);
		DataTable dt = rquery.getDataTable();
		System.out.println(rquery.getSqlString());
		for(Object o :dt.getRowList()){
			DataRow dr = (DataRow)o;
			Map rowmap = dr.getRowHashMap();
			Iterator i = rowmap.keySet().iterator();
			Map<String,Object> map = new HashMap<String, Object>();
			while(i.hasNext()){
				String key = (String) i.next();
				map.put(key.toLowerCase(), rowmap.get(key));
			}
			result.add(map);
		}
		renderText(JSONArray.fromObject(result).toString());
	}

	public Map<String, String> getOpt() {
		return opt;
	}

}
