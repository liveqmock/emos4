package com.ultrapower.eoms.ultrasla.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ultrapower.eoms.common.core.component.data.DataAdapter;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.rquery.RQuery;
import com.ultrapower.eoms.common.core.util.NumberUtils;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.ultrasla.RecordLog;
import com.ultrapower.eoms.ultrasla.model.EventDealParam;
import com.ultrapower.eoms.ultrasla.service.IEventRuleTrigger;
import com.ultrapower.eoms.ultrasla.service.IEventScanTrigger;

public class EventScanTriggerImpl implements IEventScanTrigger {
	
	private IEventRuleTrigger eventRuleTrigger;

	public void eventScanDeal(String sqlName) {
		// 事件轮询 获取临时表中的事件
		List<EventDealParam> dealParamList = this.eventScan(sqlName);
		// 事件处理
		this.eventDeal(dealParamList);
		// 批量更新临时表事件的状态
		this.updateStatus(dealParamList);
	}
	
	private void updateStatus(List<EventDealParam> dealParamList) {
		int dealParamLen = 0;
		DataTable table = null;
		if(dealParamList != null) {
			dealParamLen = dealParamList.size();
			table = new DataTable("bs_t_wf_eventinstance");
		}
		EventDealParam eventDealParam;
		String id;
		DataRow row;
		for(int i=0 ; i<dealParamLen ; i++) {
			eventDealParam = dealParamList.get(i);
			id = eventDealParam.getId();
			// 执行修改状态操作
			row = new DataRow();
			row.put("pid", id);
			row.put("logrecord", eventDealParam.getLogRecord());
			row.put("eventstatus", "1");
			table.putDataRow(row);
		}
		if(table != null) {
			String[] primaryKey={"pid"}; // 关键字字段 根据此字段更新
			table.setPrimaryKey(primaryKey);
			DataAdapter dataAdapter=new DataAdapter();
			dataAdapter.executeUpdate(table); // 执行更新操作
		}
	}
	
	/**
	 * 事件轮询
	 * @return
	 */
	private List<EventDealParam> eventScan(String sqlName) {
		List<EventDealParam> dealParamList = null;
		RQuery rquery = new RQuery(sqlName, null);
		DataTable table = rquery.getDataTable(); // 获取SQL数据集
		int tableLen = 0;
		if(table != null) {
			tableLen = table.length();
			dealParamList = new ArrayList<EventDealParam> ();
		}
		DataRow row;
		int rowLen;
		int defaultType; // 默认处理对象类型
		String defaultObj; // 默认处理对象
		HashMap<String, String> paramMap; // 参数集合
		EventDealParam eventDealParam;
		String logRecord;
		for(int i=0; i<tableLen ; i++) {
			row = table.getDataRow(i);
			rowLen = row.length();
			paramMap = new HashMap<String, String> ();
			eventDealParam = new EventDealParam();
			eventDealParam.setId(row.getString("id"));
			eventDealParam.setEventid(row.getString("eventid")); // 事件id
			eventDealParam.setRuleids(row.getString("ruleids")); // 规则id
			eventDealParam.setDuetime(row.getLong("duetime")); // 处理时限
			defaultType = row.getInt("defaulttype");
			defaultObj = row.getString("defaultid");
			if(defaultType == 0) {
				eventDealParam.setDefaultUser(defaultObj); // 默认处理人
			} else {
				int groupIntId = NumberUtils.formatToInt(defaultObj);
				if(groupIntId > 0) {
					defaultObj = String.format("%1$015d", groupIntId);
				}
				eventDealParam.setDefaultGroup(defaultObj); // 默认处理组
			}
			eventDealParam.setHandletype(row.getString("handletype")); // 类型 产生：NEW、销毁：DESTROY
			logRecord = row.getString("logrecord");
			eventDealParam.setLogRecord(this.dataCheck(logRecord, eventDealParam)); // 日志记录
			// 从第8列以后都作为参数 这是此sql的规则
			for(int j=8 ; j<rowLen ; j++) {
				if(!"".equals(row.getString(j))) {
					paramMap.put(row.getKey(j), row.getString(j));
				}
			}
			eventDealParam.setParamMap(paramMap);
			dealParamList.add(eventDealParam);
		}
		return dealParamList;
	}
	
	/**
	 * 数据校验，即对eventDealParam对象中部分数据进行校验
	 * @param logRecord
	 * @param eventDealParam
	 * @return 返回校验日志
	 */
	private String dataCheck(String logRecord, EventDealParam eventDealParam) {
		StringBuffer logResult = new StringBuffer();
		if(!"".equals(StringUtils.checkNullString(logRecord))) {
			logResult.append(logRecord);
		}
		logResult.append("已扫描：");
		if("".equals(StringUtils.checkNullString(eventDealParam.getEventid()))) {
			logResult.append(" 事件id为空");
		}
		if("".equals(StringUtils.checkNullString(eventDealParam.getHandletype()))) {
			logResult.append(" 产生销毁类型为空");
		} else if("NEW".equals(eventDealParam.getHandletype())) {
			if("".equals(StringUtils.checkNullString(eventDealParam.getRuleids()))) {
				logResult.append(" 规则id为空");
			}
			if(eventDealParam.getDuetime() == 0) {
				logResult.append(" 处理时限为空");
			}
		} else if("DESTROY".equals(eventDealParam.getHandletype())) {
			; // 不操作
		} else {
			logResult.append(" 产生销毁类型有误");
		}
		logResult.append("；");
		return logResult.length() > 100 ? logResult.toString().substring(0, 99) : logResult.toString();
	}
	
	/**
	 * 事件处理
	 * @param dealParamList
	 */
	private void eventDeal(List<EventDealParam> dealParamList) {
		int dealParamLen = 0;
		if(dealParamList != null) {
			dealParamLen = dealParamList.size();
		}
		EventDealParam eventDealParam;
		String eventid;
		String handeltype;
		for(int i=0 ; i<dealParamLen ; i++) {
			eventDealParam = dealParamList.get(i);
			if(eventDealParam == null) {
				continue;
			}
			eventid = eventDealParam.getEventid();
			handeltype = eventDealParam.getHandletype();
			if(handeltype == null) {
				continue;
			}
			int result = 0;
			if("NEW".equals(handeltype)) { // 类型为NEW为产生
				result = eventRuleTrigger.produce(
						eventid, 
						this.strToList(eventDealParam.getRuleids()),
						eventDealParam.getDuetime(),
						this.strToList(eventDealParam.getDefaultUser()),
						this.strToList(eventDealParam.getDefaultGroup()),
						this.strToList(eventDealParam.getDefaultRole()),
						eventDealParam.getParamMap());
			} else if("DESTROY".equals(handeltype)) { // 类型为DESTROY为销毁
				result = eventRuleTrigger.destory(eventid);
			} else {
				// 无需处理
			}
			if(result > 10) {
				RecordLog.printLog("the return result is " + result, RecordLog.LOG_LEVEL_WARN);
			}
		}
	}
	
	/**
	 * 将以,分割的字符串分割成到List中
	 * @param str
	 * @return
	 */
	private List<String> strToList(String str) {
		List<String> strList = new ArrayList<String> ();
		if(str == null) {
			return strList;
		}
		String[] strArray = str.split(",");
		for(int i=0 ; i<strArray.length ; i++) {
			if(!"".equals(strArray[i])) {
				strList.add(strArray[i]);
			}
		}
		return strList;
	}

	public void setEventRuleTrigger(IEventRuleTrigger eventRuleTrigger) {
		this.eventRuleTrigger = eventRuleTrigger;
	}
}
