package com.ultrapower.eoms.ultrabpp.cache.service;

import java.util.Map;

import com.ultrapower.eoms.ultrabpp.cache.model.ActionModel;
import com.ultrapower.eoms.ultrabpp.cache.model.EditableFieldModel;
import com.ultrapower.eoms.ultrabpp.cache.model.ThemeModel;
import com.ultrapower.eoms.ultrabpp.model.BaseField;

/**
 * 缓存操作接口
 * @author BigMouse
 *
 */
public interface MetaCacheService 
{
	/**
	 * 刷新缓存(整体)
	 * @param baseSchema
	 */
	public void reflushCache(String baseSchema); 
	/**
	 * 刷新缓存（局部）根据工单类型 刷新工单的主表单字段缓存
	 */
	public void reflushCacheByWorksheet(String baseSchema);
	/**
	 * 刷新缓存（局部） 根据工单类型，环节标识 刷新工单环节配置缓存
	 */
	public void reflushCacheByStep(String baseSchema,String stepNo);
	/**
	 * 刷新缓存（局部） 根据工单类型，环节标识，动作名称， 刷新动作配置信息缓存
	 */
	public void reflushCacheByAction(String baseSchema,String stepNo,String actionName);
	/**
	 * 刷新缓存（局部） 根据工单类型，刷新状态动作配置信息缓存
	 */
	public void reflushCacheByStatusAction(String baseSchema);
	/**
	 * 缓存 自由流程动作
	 */
	public void reflushFreeAction();
	/**
	 * 缓存 字段类型配置
	 */
	public void reflushFieldInfo();
	
	
	/**
	 * 获取主表单对应的所有字段
	 */
	public Map<String,BaseField> getFieldsByBaseSchema(String baseSchema);
	/**
	 *获取工单所有字段,包括主表单字段，动作页面字段
	 */
	public Map<String,BaseField> getAllFieldsByBaseSchema(String baseSchema);
	/**
	 * 获取当前环节的可编辑字段
	 */
	public Map<String,EditableFieldModel> getStepFields(String baseSchema,String stepNo);
	/**
	 * 当前动作的可编辑字段
	 */
	public Map<String,EditableFieldModel> getActionFields(String baseSchema,String stepNo,String actionName);
	
	/**
	 * 自由流程工单编辑字段
	 */
	public Map<String,EditableFieldModel> getStatusFields(String baseSchema,String baseStatus);
	/**
	 * 自由流程动作编辑字段
	 */
	public Map<String,EditableFieldModel> getFreeActionFields(String baseSchema,String baseStatus,String actionType);
	/**
	 * 获取固定的动作列表Map
	 */
	public Map<String,ActionModel> getFixActionMap(String baseSchema,String stepNo);
	/**
	 * 获取自由动作列表Map
	 */
	public Map<String,ActionModel> getFreeActionMap();
	/**
	 * 获取主表单字段与容器关系的json格式字符串
	 */
	public String getLayOutJsonString(String baseSchema);
}
