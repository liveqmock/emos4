package com.ultrapower.eoms.cdbBusiness.service;

import static com.ultrapower.eoms.msextend.incident.model.IncidentConstant.TO_CLOSE;
import static com.ultrapower.eoms.msextend.serverQuest.model.ServerQuestConstant.TO_CLOSE_FROM_DEAL;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.rquery.RQuery;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ultrabpp.api.BaseAction;
import com.ultrapower.eoms.ultrasm.manager.DicManagerImpl;
import com.ultrapower.eoms.ultrasm.model.DicType;

public class CloseSheet {
	
	private DicManagerImpl dicManagerService;
	
	public void closeSheet(){
		closeServiceRequest();
		colseIncident();
	}
	
	/**
	 * 关闭事件，默认关单人使用建单人
	 */
	private void colseIncident() {
		DicType dicType = dicManagerService.getDicTypeByCode("incident_autoclose_time");
		HashMap<String, String> parameter = new HashMap<String, String>();
		parameter.put("timetoclose", Float.toString(Float.parseFloat(dicType.getRemark())*3600));
		RQuery query = new RQuery("SQL_CDB_Incident_autoclose.query",parameter);
		DataTable dataTable = query.getDataTable();
		@SuppressWarnings("unchecked")
		List<DataRow> list = dataTable.getRowList();
		if(list != null)
		for(DataRow row : list){
			Map<String, String> fieldMap = new HashMap<String, String>();
			fieldMap.put("satisfaction_level", "非常满意");
			fieldMap.put("satisfaction_content", "非常满意");
			fieldMap.put("closeCode", "已解决");
			fieldMap.put("Description", "已解决");
			UserSession userSession = new UserSession();
			String loginName = row.getString("creator");
			userSession.setLoginName(loginName);
			userSession.setFullName(row.getString("creatorname"));
			new BaseAction().doAction(row.getString("baseid"), "CBD_INCIDENT", 
					row.getString("taskid"), loginName, 
					TO_CLOSE, "NEXT", "关单", "", "", fieldMap, null);		
		}
	}

	//关闭服务请求
	private void closeServiceRequest(){
		DicType dicType = dicManagerService.getDicTypeByCode("servicerequest_autoclose_time");
		HashMap<String, String> parameter = new HashMap<String, String>();
		parameter.put("timetoclose", Float.toString(Float.parseFloat(dicType.getRemark())*3600));
		RQuery query = new RQuery("SQL_CDB_ServiceRequest_autoclose.query",parameter);
		DataTable dataTable = query.getDataTable();
		@SuppressWarnings("unchecked")
		List<DataRow> list = dataTable.getRowList();
		if(list != null)
		for(DataRow row : list){
			Map<String, String> fieldMap = new HashMap<String, String>();
			fieldMap.put("satisfaction_level", "满意");
			fieldMap.put("close_code", "已解决");
			fieldMap.put("satisfaction_content", "自动关单");
			UserSession userSession = new UserSession();
			userSession.setLoginName(row.getString("submit_request_person"));
			userSession.setFullName(row.getString("submit_request_person_name"));
			new BaseAction().doAction(row.getString("baseid"), "CDB_SERVICEREQUEST", 
					row.getString("taskid"), row.getString("submit_request_person"), 
					TO_CLOSE_FROM_DEAL, "NEXT", "关单", "", "", fieldMap, null);		
		}
	}

	public void setDicManagerService(DicManagerImpl dicManagerService) {
		this.dicManagerService = dicManagerService;
	}
	
}
