package com.ultrapower.eoms.ultrabpp.cache.service;

import java.util.List;

import com.ultrapower.eoms.ultrabpp.runtime.service.ExtendFunction;
import com.ultrapower.workflow.configuration.sort.model.WfType;

/**
 * 扩展逻辑缓存服务
 * @author BigMouse
 *
 */
public interface ExtendFunctionCacheService
{
	/**
	 * 获取工单打开时的扩展逻辑
	 * @param baseSchema 工单类型
	 * @return 工单打开时的扩展逻辑
	 */
	public List<ExtendFunction> getFormOpenFuncList(String baseSchema);
	
	/**
	 * 获取工单提交时的扩展逻辑
	 * @param baseSchema 工单类型
	 * @return 工单提交时的扩展逻辑
	 */
	public List<ExtendFunction> getFormCommitFuncList(String baseSchema);
	
	/**
	 * 获取工单客户端扩展逻辑
	 * @param baseSchema 工单类型
	 * @return 工单客户端扩展逻辑
	 */
	public List<String> getClientFuncList(String baseSchema);
	
	/**
	 * 获取自定义参数
	 * @param baseSchema  工单类型
	 * @return 自定义参数
	 */
	public List<String> getAttributeList(String baseSchema);
	
	/**
	 * 刷新缓存
	 * @param baseSchema 工单类型
	 */
	public void reflush(String baseSchema);
	/**
	 * 根据路径刷新客户化配置文件缓存
	 */
	public void reflushCache(String path,String baseSchema);
	/**
	 * 获取关联类型
	 * @param baseSchema
	 * @return
	 */
	public String getRelateType(String baseSchema);
	
	/**
	 * 获取关联流程类别列表
	 * @param baseSchema
	 * @return
	 */
	public List<WfType> getRelateSchemas(String baseSchema);
}
