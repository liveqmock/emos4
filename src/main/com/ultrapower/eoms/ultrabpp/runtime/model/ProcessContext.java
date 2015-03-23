package com.ultrapower.eoms.ultrabpp.runtime.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ultrabpp.runtime.service.ExtendFunction;
import com.ultrapower.workflow.engine.task.model.ProcessTask;

public abstract class ProcessContext
{
	protected String editType;
	protected long flagActive;
	protected ProcessTask<?> currentTask;
	protected UserSession userInfo;
	protected Map<String, String> fieldMap = new HashMap<String, String>();
	protected Map<String, String> attributeMap = new HashMap<String, String>();
	protected List<ExtendFunction> extendFuncList = new ArrayList<ExtendFunction>();
	protected String baseID;
	protected String baseSchema;
	protected String baseName;
	protected String defCode;
	protected String taskID;
	protected String mainPage = "main";
	protected String relateType;
	protected String relateBaseID;
	protected String relateBaseSchema;
	protected String relateBaseSN;
	protected String relateTaskID;
	protected String copyRelateData;
	protected String copyRelateByConfig;
	/**
	 * @return the currentTask
	 */
	public ProcessTask<?> getCurrentTask()
	{
		return currentTask;
	}
	/**
	 * @param currentTask the currentTask to set
	 */
	public void setCurrentTask(ProcessTask<?> currentTask)
	{
		this.currentTask = currentTask;
	}
	/**
	 * @return the userInfor
	 */
	public UserSession getUserInfo()
	{
		return userInfo;
	}
	/**
	 * @param userInfor the userInfor to set
	 */
	public void setUserInfo(UserSession userInfo)
	{
		this.userInfo = userInfo;
	}
	/**
	 * @return the fieldMap
	 */
	public Map<String, String> getFieldMap()
	{
		return fieldMap;
	}
	/**
	 * @param fieldMap the fieldMap to set
	 */
	public void setFieldMap(Map<String, String> fieldMap)
	{
		this.fieldMap = fieldMap;
	}
	/**
	 * @return the attributeMap
	 */
	public Map<String, String> getAttributeMap()
	{
		return attributeMap;
	}
	/**
	 * @param attributeMap the attributeMap to set
	 */
	public void setAttributeMap(Map<String, String> attributeMap)
	{
		this.attributeMap = attributeMap;
	}
	/**
	 * @return the extendFuncList
	 */
	public List<ExtendFunction> getExtendFuncList()
	{
		return extendFuncList;
	}
	/**
	 * @param extendFuncList the extendFuncList to set
	 */
	public void setExtendFuncList(List<ExtendFunction> extendFuncList)
	{
		this.extendFuncList = extendFuncList;
	}
	/**
	 * @return the editType
	 */
	public String getEditType()
	{
		return editType;
	}
	/**
	 * @param editType the editType to set
	 */
	public void setEditType(String editType)
	{
		this.editType = editType;
	}
	public String getTaskID() {
		return taskID;
	}
	public void setTaskID(String taskID) {
		this.taskID = taskID;
	}
	public String getBaseID() {
		return baseID;
	}
	public void setBaseID(String baseID) {
		this.baseID = baseID;
	}
	public String getBaseSchema() {
		return baseSchema;
	}
	public void setBaseSchema(String baseSchema) {
		this.baseSchema = baseSchema;
	}
	public String getDefCode() {
		return defCode;
	}
	public void setDefCode(String defCode) {
		this.defCode = defCode;
	}
	public String getBaseName() {
		return baseName;
	}
	public void setBaseName(String baseName) {
		this.baseName = baseName;
	}
	public String getMainPage() {
		return mainPage;
	}
	public void setMainPage(String mainPage) {
		this.mainPage = mainPage;
	}
	public String getRelateType() {
		return relateType;
	}
	public void setRelateType(String relateType) {
		this.relateType = relateType;
	}
	public String getRelateBaseID() {
		return relateBaseID;
	}
	public void setRelateBaseID(String relateBaseID) {
		this.relateBaseID = relateBaseID;
	}
	public String getRelateBaseSchema() {
		return relateBaseSchema;
	}
	public void setRelateBaseSchema(String relateBaseSchema) {
		this.relateBaseSchema = relateBaseSchema;
	}
	public String getCopyRelateData() {
		return copyRelateData;
	}
	public void setCopyRelateData(String copyRelateData) {
		this.copyRelateData = copyRelateData;
	}
	public String getRelateTaskID() {
		return relateTaskID;
	}
	public void setRelateTaskID(String relateTaskID) {
		this.relateTaskID = relateTaskID;
	}
	public String getRelateBaseSN() {
		return relateBaseSN;
	}
	public void setRelateBaseSN(String relateBaseSN) {
		this.relateBaseSN = relateBaseSN;
	}
	/**
	 * @return the flagActive
	 */
	public long getFlagActive()
	{
		return flagActive;
	}
	/**
	 * @param flagActive the flagActive to set
	 */
	public void setFlagActive(long flagActive)
	{
		this.flagActive = flagActive;
	}
	public String getCopyRelateByConfig()
	{
		return copyRelateByConfig;
	}
	public void setCopyRelateByConfig(String copyRelateByConfig)
	{
		this.copyRelateByConfig = copyRelateByConfig;
	}
	
}
