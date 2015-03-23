package com.ultrapower.eoms.common.plugin.commonTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.rquery.RQuery;
import com.ultrapower.eoms.common.core.component.tree.manager.TreeManager;
import com.ultrapower.eoms.common.core.component.tree.model.DtreeBean;
import com.ultrapower.eoms.common.core.web.BaseAction;
@SuppressWarnings({"unchecked","rawtypes"})
public class CommonViewTreeAction extends BaseAction{
	private static final long serialVersionUID = -4249733934309299598L;
	private Map<String, String> opt;
	
	public String commonViewTree(){
		return SUCCESS;
	}

	public void loadXML(){
		List<DtreeBean> dtreeList = new ArrayList<DtreeBean>();
		if(opt.get("pid") == null) opt.put("pid", id);
		if(opt.get("sameAsId") != null && id!=null) opt.put(opt.get("sameAsId"), id);//将给定的key的值设置为id
		
		RQuery rQuery = new RQuery(opt.get("query"),(HashMap) opt);
		DataTable dt = rQuery.getDataTable();
		for(int row=0;row<dt.length();row++){
			DataRow dataRow = dt.getDataRow(row);
			DtreeBean dtreeBean = new DtreeBean();
			dtreeBean.setId(dataRow.getString(opt.get("idColumnName")));
			dtreeBean.setText(dataRow.getString(opt.get("textColumnName")));
			dtreeBean.setUserdata(dataRow.getRowHashMap());
			dtreeList.add(dtreeBean);
		}
		renderText(new TreeManager().createDhtmlxtreeXml(dtreeList, opt.get("pid")));
	}

	public void setOpt(Map<String, String> opt) {
		this.opt = opt;
	}
	public Map<String, String> getOpt() {
		return opt;
	}

	public String getOptJson() {
		return JSONObject.fromObject(opt).toString();
	}

}
