package com.ultrapower.eoms.ultrabpp.runtime.service;

import java.util.List;
import java.util.Map;

import com.ultrapower.workflow.role.model.ChildRole;
import com.ultrapower.workflow.role.model.Dimension;

public interface WorkflowRoleService {
	/**
	 * 
	 * @param baseSchema
	 * @return
	 */
	public String[] getDimensions(String baseSchema);

	/**
	 * 
	 * @param baseSchema
	 * @param phaseNo
	 * @param args
	 * @return
	 */
	public String[] matchRole(String baseSchema, String defName,
			String phaseNo, Map<String, String> args);

	/**
	 * 通用角色api，获取维度列表
	 * @param baseSchema 业务标识
	 * @param roleCode 角色标识
	 * @return 维度列表
	 */
	public List<Dimension> getDimensions(String baseSchema, String roleCode);

	/**
	 * 通用角色api，匹配角色细分
	 * @param baseSchema 业务标识
	 * @param roleCode 角色标识
	 * @param args 匹配角色细分用到的业务字段
	 * @return 匹配到的角色细分列表
	 */
	public List<ChildRole> matchRole(String baseSchema, String roleCode,
			Map<String, String> args);
}
