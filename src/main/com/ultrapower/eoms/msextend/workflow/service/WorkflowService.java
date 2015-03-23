package com.ultrapower.eoms.msextend.workflow.service;

import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.msextend.workflow.model.WfRecord;

public interface WorkflowService {
	public void addWfRecord(WfRecord record);
	public void updateWfRecord(WfRecord record);
	public WfRecord getWfRecordById(String pid);
	public List<WfRecord> getWfRecords(String baseid,String baseschema);
	/**
	 * 根据配置模型类型,查找配置邮件内容
	 * @return
	 */
	public List getMailContentByType(String modelType);
	
	public List<WfRecord> getWfRecordByCondition(String baseId,String dealAction);
	
	public void updateCustomBaseInfor(Map<String, String> map);
}
 	