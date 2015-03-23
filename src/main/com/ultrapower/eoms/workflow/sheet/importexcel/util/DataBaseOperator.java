package com.ultrapower.eoms.workflow.sheet.importexcel.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ultrapower.eoms.common.core.component.data.DataAdapter;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.msextend.incident.model.IncidentConstant;
import com.ultrapower.eoms.msextend.serverQuest.model.ServerQuestConstant;
import com.ultrapower.eoms.msextend.workflow.model.WfRecord;
import com.ultrapower.eoms.msextend.workflow.service.WorkflowService;
import com.ultrapower.eoms.ultrabpp.api.BaseAction;
import com.ultrapower.eoms.workflow.RecordLog;
import com.ultrapower.eoms.workflow.sheet.importexcel.constants.ExcelFieldConstants;

@SuppressWarnings("unchecked")
public class DataBaseOperator {
	public static List<DataRow> eventSourceList = new ArrayList<DataRow>();
	public static List<DataRow> effectDgreeList = new ArrayList<DataRow>();
	public static List<DataRow> urgentDgreeList = new ArrayList<DataRow>();
	public static List<DataRow> propertyList = new ArrayList<DataRow>();
	public static List<DataRow> resouceList = new ArrayList<DataRow>();
	public static List<DataRow> categoryList = new ArrayList<DataRow>();
	public static List<DataRow> incCauseClassList = new ArrayList<DataRow>();
	public static List<DataRow> incPhenoClassList = new ArrayList<DataRow>();
	
	/*static {
		// 获取事件现象分类的值
		QueryAdapter queryAdapter = new QueryAdapter();
		String sql = "select fullname from bs_t_sm_commonTree t where t.type = 'incPhenoClass'";
		DataTable dataTable = queryAdapter.executeQuery(sql);
		if (null != dataTable) {
			incPhenoClassList = dataTable.getRowList();
		} else {
			RecordLog.printLog("无法获取事件现象分类的值！", RecordLog.LOG_LEVEL_ERROR);
		}
		
		// 获取事件原因分类的值
		sql = "select fullname from bs_t_sm_commonTree t where t.type = 'incCauseClass'";
		dataTable = queryAdapter.executeQuery(sql);
		if (null != dataTable) {
			incCauseClassList = dataTable.getRowList();
		} else {
			RecordLog.printLog("无法获取到事件原因分类的值！", RecordLog.LOG_LEVEL_ERROR);
		}
		
		// 获取服务请求服务分类的值
		sql = "select t.serverquestfullname from bs_t_wf_serverquest t";
		dataTable = queryAdapter.executeQuery(sql);
		if (null != dataTable) {
			categoryList = dataTable.getRowList();
		} else {
			RecordLog.printLog("无法获取到服务请求服务分类的值！", RecordLog.LOG_LEVEL_ERROR);
		}
		
		// 获取事件来源的值
		sql = "select diname from BS_T_SM_DICITEM where dtcode ='eventSource'";
		dataTable = queryAdapter.executeQuery(sql);
		if (null != dataTable) {
			eventSourceList = dataTable.getRowList();
		} else {
			RecordLog.printLog("无法获取事件来源的值！", RecordLog.LOG_LEVEL_ERROR);
		}
		
		// 获取事件性质的值
		sql = "select diname from BS_T_SM_DICITEM where dtcode ='property'";
		dataTable = queryAdapter.executeQuery(sql);
		if (null != dataTable) {
			propertyList = dataTable.getRowList();
		} else {
			RecordLog.printLog("无法获取事件性质的值！", RecordLog.LOG_LEVEL_ERROR);
		}
		
		// 获取事件影响度的值
		sql = "select diname from BS_T_SM_DICITEM where dtcode ='Incident_EffectDgree'";
		dataTable = queryAdapter.executeQuery(sql);
		if (null != dataTable) {
			effectDgreeList = dataTable.getRowList();
		} else {
			RecordLog.printLog("无法获取事件影响度的值！", RecordLog.LOG_LEVEL_ERROR);
		}
		
		// 获取事件紧急度的值
		sql = "select diname from BS_T_SM_DICITEM where dtcode ='Incident_UrgentDgree'";
		dataTable = queryAdapter.executeQuery(sql);
		if (null != dataTable) {
			urgentDgreeList = dataTable.getRowList();
		} else {
			RecordLog.printLog("无法获取事件紧急度的值！", RecordLog.LOG_LEVEL_ERROR);
		}
		
		// 获取服务请求来源的值
		sql = "select diname from BS_T_SM_DICITEM where dtcode ='resouce'";
		dataTable = queryAdapter.executeQuery(sql);
		if (null != dataTable) {
			resouceList = dataTable.getRowList();
		} else {
			RecordLog.printLog("无法获取服务请求来源的值！", RecordLog.LOG_LEVEL_ERROR);
		}
	}*/
	
	/**
	 * 调用保存工单方法
	 * @param baseSchema
	 * @param taskID
	 * @param loginName
	 * @param dealDesc
	 * @param fieldMap
	 * @return
	 */
	public String saveSheet(BaseAction baseApiAction, String baseSchema, String taskID, String loginName, String dealDesc) {
		return baseApiAction.doAction("", baseSchema, taskID, loginName, "SAVE", "SAVE", "保存", "", dealDesc, null, null);
	}
	
	/**
	 * 增加处理记录方法
	 * @return
	 */
	public boolean addWfRecord(WorkflowService workflowServiceEx, String baseSchema, String baseID, HashMap rowHashMap) {
		WfRecord record = new WfRecord();
		record.setBaseSchema(baseSchema);
		record.setBaseId(baseID);
		if ("CBD_INCIDENT".equals(baseSchema)) {
			record.setCurrentUser((String) rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.RESOLVEUSER));
			record.setCurrentUserLoginName(((String) rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.RESOLVEUSEROA)).toUpperCase());
			record.setDealDesc("事件工单导入补单");
			record.setDealTime(Long.toString(TimeUtils.formatDateStringToInt(((String) rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.BASEFINISHDATE)).replace('/', '-'))));
		}else if ("CDB_SERVICEREQUEST".equals(baseSchema)) {
			record.setCurrentUser(((String) rowHashMap.get(ExcelFieldConstants.CDB_SERVICEREQUEST.DEALUSER)).toUpperCase());
			record.setCurrentUserLoginName((String) rowHashMap.get(ExcelFieldConstants.CDB_SERVICEREQUEST.DEALUSEROA));
			record.setDealDesc("服务请求导入补单");
			record.setDealTime(Long.toString(TimeUtils.formatDateStringToInt(((String) rowHashMap.get(ExcelFieldConstants.CDB_SERVICEREQUEST.BASEFINISHDATE)).replace('/', '-'))));
		}else {
			return false;
		}
		record.setDealAction("处理完成");
		record.setIsView("1");
		record.setNextDealUser("");
		record.setNextDealUserLoginName("");
		record.setIsSms("0");
		record.setSmsContent("");
		record.setSmsSendFlag("0");
		record.setIsEmail("0");
		record.setEmailSendFlag("0");
		record.setEmailContent("");
		workflowServiceEx.addWfRecord(record);
		return true;
	}
	
	/**
	 * Excel导入补单 - 关单专用方法
	 * 
	 * @author Jason.Forest
	 * @since 2014-12-24 14:53:36
	 * @param baseID
	 *            建单后返回回来的baseID
	 * @param baseSchema
	 *            需要导入的工单的baseSchema
	 * @param loginName
	 * @param taskID
	 * @param fieldMap
	 *            需要导入的工单的字段Map
	 * @param dealDesc
	 *            处理描述
	 */
	public void closeSheet(BaseAction baseApiAction, String baseID, String baseSchema, String loginName, String taskID, String dealDesc, HashMap fieldMap) {
		// 定义变量
		String actionID = "";
		// 关闭工单前参数设置，哪些字段在工单关闭时，被默认填写或修改
		if ("CDB_SERVICEREQUEST".equals(baseSchema)) {
			actionID = ServerQuestConstant.TO_CLOSE_FROM_REQUEST;
		} else if ("CBD_INCIDENT".equals(baseSchema)) {
			actionID = IncidentConstant.TO_FINISH_STEP01;
		} else {
			RecordLog.printLog("更新创建时间及关闭时间失败.", RecordLog.LOG_LEVEL_ERROR);
		}
		baseApiAction.doAction(baseID, baseSchema, taskID, loginName, actionID, "NEXT", "关单", "", dealDesc, fieldMap, null);
	}
	
	/**
	 * 修改创建时间和关闭时间专用方法
	 * @param baseID
	 * @param tableName
	 * @param baseCreateDate
	 * @param baseCloseDate
	 * @return
	 */
	public static boolean updateCreateOrCloseDate(String baseID, String tableName, long baseCreateDate, long baseCloseDate) {
		DataAdapter dataAdapter = new DataAdapter();
		boolean flag = false;
		DataRow dataRow;
		dataRow = new DataRow();
		dataRow.put("baseCreateDate", baseCreateDate);
		dataRow.put("baseCloseDate", baseCloseDate);
		String conditions = "baseid=?";
		Object[] values = { baseID };
		int result = dataAdapter.putDataRow(tableName, dataRow, conditions, values);
		if (result > 0) {
			flag = true;
		} else {
			flag = false;
		}

		tableName = "bs_t_bpp_baseinfor";
		result = dataAdapter.putDataRow(tableName, dataRow, conditions, values);
		if (result > 0) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}

	public static void initDict() {
		// 获取事件现象分类的值
		QueryAdapter queryAdapter = new QueryAdapter();
		String sql = "select fullname from bs_t_sm_commonTree t where t.type = 'incPhenoClass'";
		DataTable dataTable = queryAdapter.executeQuery(sql);
		if (null != dataTable) {
			incPhenoClassList = dataTable.getRowList();
		} else {
			RecordLog.printLog("无法获取事件现象分类的值！", RecordLog.LOG_LEVEL_ERROR);
		}
		
		// 获取事件原因分类的值
		sql = "select fullname from bs_t_sm_commonTree t where t.type = 'incCauseClass'";
		dataTable = queryAdapter.executeQuery(sql);
		if (null != dataTable) {
			incCauseClassList = dataTable.getRowList();
		} else {
			RecordLog.printLog("无法获取到事件原因分类的值！", RecordLog.LOG_LEVEL_ERROR);
		}
		
		// 获取服务请求服务分类的值
		sql = "select t.serverquestfullname from bs_t_wf_serverquest t";
		dataTable = queryAdapter.executeQuery(sql);
		if (null != dataTable) {
			categoryList = dataTable.getRowList();
		} else {
			RecordLog.printLog("无法获取到服务请求服务分类的值！", RecordLog.LOG_LEVEL_ERROR);
		}
		
		// 获取事件来源的值
		sql = "select diname from BS_T_SM_DICITEM where dtcode ='eventSource'";
		dataTable = queryAdapter.executeQuery(sql);
		if (null != dataTable) {
			eventSourceList = dataTable.getRowList();
		} else {
			RecordLog.printLog("无法获取事件来源的值！", RecordLog.LOG_LEVEL_ERROR);
		}
		
		// 获取事件性质的值
		sql = "select diname from BS_T_SM_DICITEM where dtcode ='property'";
		dataTable = queryAdapter.executeQuery(sql);
		if (null != dataTable) {
			propertyList = dataTable.getRowList();
		} else {
			RecordLog.printLog("无法获取事件性质的值！", RecordLog.LOG_LEVEL_ERROR);
		}
		
		// 获取事件影响度的值
		sql = "select diname from BS_T_SM_DICITEM where dtcode ='Incident_EffectDgree'";
		dataTable = queryAdapter.executeQuery(sql);
		if (null != dataTable) {
			effectDgreeList = dataTable.getRowList();
		} else {
			RecordLog.printLog("无法获取事件影响度的值！", RecordLog.LOG_LEVEL_ERROR);
		}
		
		// 获取事件紧急度的值
		sql = "select diname from BS_T_SM_DICITEM where dtcode ='Incident_UrgentDgree'";
		dataTable = queryAdapter.executeQuery(sql);
		if (null != dataTable) {
			urgentDgreeList = dataTable.getRowList();
		} else {
			RecordLog.printLog("无法获取事件紧急度的值！", RecordLog.LOG_LEVEL_ERROR);
		}
		
		// 获取服务请求来源的值
		sql = "select diname from BS_T_SM_DICITEM where dtcode ='resouce'";
		dataTable = queryAdapter.executeQuery(sql);
		if (null != dataTable) {
			resouceList = dataTable.getRowList();
		} else {
			RecordLog.printLog("无法获取服务请求来源的值！", RecordLog.LOG_LEVEL_ERROR);
		}
	}
}
