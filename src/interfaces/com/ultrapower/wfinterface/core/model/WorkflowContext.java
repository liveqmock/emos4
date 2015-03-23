package com.ultrapower.wfinterface.core.model;

import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.core.component.data.DataRow;

public class WorkflowContext
{
	private String operateCode;
	private String operateText;
	private String operateActionId;
	private String creator;//建单人登陆名
	private String baseID;
	private String baseSchema;
	private String taskID;
	private String dealer;
	private String desc;
	private Actor actor;
	private Map<String, WorkflowField> inputFields;
	private List<com.ultrapower.eoms.ultrasm.model.Attachment> attachments;
	private DataRow sheetInfo;
	private boolean actionResult;
	public String getBaseID()
	{
		return baseID;
	}
	public void setBaseID(String baseID)
	{
		this.baseID = baseID;
	}
	public String getBaseSchema()
	{
		return baseSchema;
	}
	public void setBaseSchema(String baseSchema)
	{
		this.baseSchema = baseSchema;
	}
	public String getTaskID()
	{
		return taskID;
	}
	public void setTaskID(String taskID)
	{
		this.taskID = taskID;
	}
	public String getDealer()
	{
		return dealer;
	}
	public void setDealer(String dealer)
	{
		this.dealer = dealer;
	}

	public Actor getActor() {
		return actor;
	}
	public void setActor(Actor actor) {
		this.actor = actor;
	}
	public Map<String, WorkflowField> getInputFields()
	{
		return inputFields;
	}
	public void setInputFields(Map<String, WorkflowField> inputFields)
	{
		this.inputFields = inputFields;
	}
	public List<com.ultrapower.eoms.ultrasm.model.Attachment> getAttachments()
	{
		return attachments;
	}
	public void setAttachments(List<com.ultrapower.eoms.ultrasm.model.Attachment> attachments)
	{
		this.attachments = attachments;
	}
	public String getOperateCode()
	{
		return operateCode;
	}
	public void setOperateCode(String operateCode)
	{
		this.operateCode = operateCode;
	}
	public String getOperateText()
	{
		return operateText;
	}
	public void setOperateText(String operateText)
	{
		this.operateText = operateText;
	}
	public String getDesc()
	{
		return desc;
	}
	public void setDesc(String desc)
	{
		this.desc = desc;
	}
	public boolean isActionResult()
	{
		return actionResult;
	}
	public void setActionResult(boolean actionResult)
	{
		this.actionResult = actionResult;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getOperateActionId() {
		return operateActionId;
	}
	public void setOperateActionId(String operateActionId) {
		this.operateActionId = operateActionId;
	}
	public DataRow getSheetInfo() {
		return sheetInfo;
	}
	public void setSheetInfo(DataRow sheetInfo) {
		this.sheetInfo = sheetInfo;
	}
	
	
}
