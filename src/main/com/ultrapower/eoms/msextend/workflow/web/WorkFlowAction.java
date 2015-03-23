package com.ultrapower.eoms.msextend.workflow.web;

import java.util.List;

import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.msextend.workflow.model.WfRecord;
import com.ultrapower.eoms.msextend.workflow.service.WorkflowService;

/**
 *
 */
public class WorkFlowAction extends BaseAction{
	private static final long serialVersionUID = -5739331382435479495L;
	private String baseid;//流程关键字
	private String baseschema;
	private String recordViewFieldName;//处理记录的视图字段名
	private List<WfRecord> wfRecords; 
	private WorkflowService workflowServiceEx;
	
	public String showWfRecords(){
		wfRecords = workflowServiceEx.getWfRecords(baseschema,baseid);
		return SUCCESS;
	}

	public void setBaseid(String baseid) {
		this.baseid = baseid;
	}

	public List<WfRecord> getWfRecords() {
		return wfRecords;
	}

	public void setWorkflowServiceEx(WorkflowService workflowServiceEx) {
		this.workflowServiceEx = workflowServiceEx;
	}

	public void setBaseschema(String baseschema) {
		this.baseschema = baseschema;
	}

	public String getRecordViewFieldName() {
		return recordViewFieldName;
	}

	public void setRecordViewFieldName(String recordViewFieldName) {
		this.recordViewFieldName = recordViewFieldName;
	}
	
}
