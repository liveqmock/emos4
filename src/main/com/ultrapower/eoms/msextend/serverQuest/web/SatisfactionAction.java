package com.ultrapower.eoms.msextend.serverQuest.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.core.component.data.DataAdapter;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.util.CryptUtils;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.msextend.workflow.model.WfRecord;
import com.ultrapower.eoms.msextend.workflow.service.WorkflowService;
import com.ultrapower.eoms.ultrabpp.runtime.service.ProcessService;
import com.ultrapower.eoms.ultrasm.model.UserInfo;
import com.ultrapower.eoms.ultrasm.service.UserManagerService;
import com.ultrapower.eoms.workflow.RecordLog;

public class SatisfactionAction extends BaseAction {
	private static final long serialVersionUID = 5361111109184213488L;
	private String baseid;
	private String baseschema;
	private String loginname;
	private String suggest;
	private String satisfaction;
	private WorkflowService workflowServiceEx;
	private UserManagerService userManagerService;
	private ProcessService processService;
	
	public String doSatisSurvey(){
//		 用户已经评价过的情况
//		List<WfRecord> wfRecords = workflowServiceEx.getWfRecords(baseschema, baseid);
//		int size = wfRecords.size();
//		WfRecord record;
//		List<String> list = new ArrayList<String>();
//		for (int i = 0; i < size; i++) {
//			record = wfRecords.get(i);
//			list.add(record.getDealAction());
//		}
//		if (!list.isEmpty()) {
//			if (list.contains("用户提交满意度调查")) {
//				RecordLog.printLog("用户已经进行过满意度评价!", RecordLog.LOG_LEVEL_INFO);
//				return null;
//			}
//		}
		
		// 解密参数
		decodeParam();
		
		String tableName = "";
		boolean flag;
		if ("CBD_INCIDENT".equals(baseschema)) {
			tableName = "BS_F_CBD_INCIDENT";// 设置事件工单表名
		} else if ("CDB_SERVICEREQUEST".equals(baseschema)) {
			tableName = "BS_F_CDB_SERVICEREQUEST";// 设置服务请求表名
		} else {
			RecordLog.printLog("baseschema有误!", RecordLog.LOG_LEVEL_ERROR);
			return null;
		}
		
		// 验证baseid是否准确、baseschema是否准确、loginname是否为请求人
		flag = validateParam(tableName);
		if (!flag) {
			RecordLog.printLog("baseid、baseschema或loginname有误!", RecordLog.LOG_LEVEL_ERROR);
			return null;
		}
		
		flag = updateSheet(tableName);
		if (!flag) {
			RecordLog.printLog("更新满意度失败!", RecordLog.LOG_LEVEL_ERROR);
			return null;
		}
		
		addWfRecord();// 增加处理记录
		RecordLog.printLog("添加处理记录完成!", RecordLog.LOG_LEVEL_INFO);
		return "custom";
	}

	
	
	//关单到评价直接的时间是否超过7天
	public void checkTimeOut(){
		String mes="";
		baseid = StringUtils.checkNullString(baseid);
		baseschema = StringUtils.checkNullString(baseschema);
		CryptUtils crypt = CryptUtils.getInstance();
		baseid = crypt.decode(baseid);
		baseschema = crypt.decode(baseschema);
		String tableName = "";
		if("CBD_INCIDENT".equals(baseschema)||"CDB_SERVICEREQUEST".equals(baseschema)){
			if ("CBD_INCIDENT".equals(baseschema)) {
				tableName = "BS_F_CBD_INCIDENT";// 设置事件工单表名
			} else if ("CDB_SERVICEREQUEST".equals(baseschema)) {
				tableName = "BS_F_CDB_SERVICEREQUEST";// 设置服务请求表名
			} 
			String  baseclosedate=findCloseTime(tableName,baseid);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			String  nowtime=df.format(new Date());// new Date()为获取当前系统时间	
			
			int day=TimeUtils.getBetweenDays(baseclosedate,nowtime);
			if(day>=7){
				mes="timeout";
			}
			renderText(mes);
		}else {
			mes="error";
			renderText(mes);
			RecordLog.printLog("baseschema有误!", RecordLog.LOG_LEVEL_ERROR);
		}
	}
	

	// 解密baseid、baseschema、loginname参数
	private void decodeParam() {
		baseid = StringUtils.checkNullString(baseid);
		baseschema = StringUtils.checkNullString(baseschema);
		loginname = StringUtils.checkNullString(loginname);
		CryptUtils crypt = CryptUtils.getInstance();
		baseid = crypt.decode(baseid);
		baseschema = crypt.decode(baseschema);
		loginname = crypt.decode(loginname);
	}

	private String findCloseTime(String tableName,String baseid ){
		QueryAdapter queryAdapter = new QueryAdapter();
		String baseclosedate="";
		String sql = "select   to_char(sec_to_date(baseclosedate),'yyyy-MM-dd HH24:mm:ss')  baseclosedate from " + tableName + " where baseid = '" + baseid + "'";
		DataTable dataTable = queryAdapter.executeQuery(sql);
		List<DataRow> dataRows = new ArrayList<DataRow>();
		DataRow dataRow;
		if (null != dataTable) {
				dataRows = dataTable.getRowList();
			if (null != dataRows && !dataRows.isEmpty()) {
				dataRow = dataRows.get(0);
				if(dataRow.getRowHashMap().get("BASECLOSEDATE")!=null){
					Map map =new HashMap();
					map=dataRow.getRowHashMap();
					baseclosedate=(String)map.get("BASECLOSEDATE");
				}
			}
		} 
		return baseclosedate;
	}
	@SuppressWarnings("unchecked")
	private boolean validateParam(String tableName) {
		boolean flag;
		QueryAdapter queryAdapter = new QueryAdapter();
		String sql = "select * from " + tableName + " where baseid = '" + baseid + "'";
		DataTable dataTable = queryAdapter.executeQuery(sql);
		List<DataRow> dataRows = new ArrayList<DataRow>();
		DataRow dataRow;
		if (null != dataTable) {
			dataRows = dataTable.getRowList();
			if (null != dataRows && !dataRows.isEmpty()) {
				dataRow = dataRows.get(0);
				String baseSchema = (String) dataRow.getRowHashMap().get("BASESCHEMA");
				if (null != baseschema && baseschema.equals(baseSchema)) {
					UserInfo userInfo = userManagerService.getUserByLoginName(loginname);
					if (null != userInfo) {
						flag = true;
					} else {
						RecordLog.printLog("loginname有误!", RecordLog.LOG_LEVEL_ERROR);
						flag = false;
					}
				} else {
					RecordLog.printLog("baseschema有误!", RecordLog.LOG_LEVEL_ERROR);
					flag = false;
				}
			} else {
				flag = false;
			}
		} else {
			RecordLog.printLog("baseid有误!", RecordLog.LOG_LEVEL_ERROR);
			flag = false;
		}
		return flag;
	}

	/**
	 * 修改满意度专用方法
	 * @return
	 */
	private boolean updateSheet(String tableName) {
		DataAdapter dataAdapter = new DataAdapter();
		DataRow dataRow = new DataRow();
		dataRow.put("satisfaction_level", satisfaction);// 满意度更新
		dataRow.put("satisfaction_content", suggest);// 意见更新
		String conditions = "baseid=?";
		Object[] values = { baseid };
		int result = dataAdapter.putDataRow(tableName, dataRow, conditions, values);
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 增加处理记录方法
	 * @return
	 */
	private void addWfRecord() {
		WfRecord record = new WfRecord();
		record.setBaseSchema(baseschema);
		record.setBaseId(baseid);
		record.setDealDesc("用户提交满意度调查");
		record.setCurrentUserLoginName(loginname);
		record.setCurrentUser(userManagerService.getUserNameByLoginName(loginname));
		record.setDealTime(Long.toString(TimeUtils.getCurrentTime()));
		record.setDealAction("用户提交满意度调查");
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
	}
	
	public String getBaseid() {
		return baseid;
	}

	public void setBaseid(String baseid) {
		this.baseid = baseid;
	}

	public String getBaseschema() {
		return baseschema;
	}

	public void setBaseschema(String baseschema) {
		this.baseschema = baseschema;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getSuggest() {
		return suggest;
	}

	public void setSuggest(String suggest) {
		this.suggest = suggest;
	}

	public String getSatisfaction() {
		return satisfaction;
	}

	public void setSatisfaction(String satisfaction) {
		this.satisfaction = satisfaction;
	}

	public WorkflowService getWorkflowServiceEx() {
		return workflowServiceEx;
	}

	public void setWorkflowServiceEx(WorkflowService workflowServiceEx) {
		this.workflowServiceEx = workflowServiceEx;
	}

	public UserManagerService getUserManagerService() {
		return userManagerService;
	}

	public void setUserManagerService(UserManagerService userManagerService) {
		this.userManagerService = userManagerService;
	}

	public ProcessService getProcessService() {
		return processService;
	}

	public void setProcessService(ProcessService processService) {
		this.processService = processService;
	}
}
