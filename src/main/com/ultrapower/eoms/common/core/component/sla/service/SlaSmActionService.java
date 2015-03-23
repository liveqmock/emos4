package com.ultrapower.eoms.common.core.component.sla.service;

import java.util.List;
import com.ultrapower.eoms.common.core.component.sla.model.SlaSmAction;
import com.ultrapower.eoms.common.core.component.sla.model.SlaRulePropertyShow;

/**
 * SLA关于短信动作的服务提供者
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-8-30 下午04:43:09
 */
public interface SlaSmActionService {

	/**
	 * 添加短信动作业务
	 * @param slaSmAction 短信动作业务信息
	 * @return 返回true 或 false
	 */
	public boolean addSlaSmAction(SlaSmAction slaSmAction);
	
	/**
	 * 更新短信动作业务
	 * @param slaSmAction 短信动作业务信息
	 * @return 返回true 或 false
	 */
	public boolean updateSlaSmAction(SlaSmAction slaSmAction);
	
	/**
	 * 删除短信动作业务
	 * @param id 短信动作业务唯一标识
	 * @return 返回true 或 false
	 */
	public boolean deleteSlaSmAction(String id);
	
	/**
	 * 获取短信动作业务
	 * @param id 短信动作业务唯一标识
	 * @return 短信动作业务对象
	 */
	public SlaSmAction get(String id);
	
	/**
	 * 根据给定的规则ID和模板ID取得该动作的规则展示列表（包括规则的属性、已经规则属性所对应模板属性输入数据源、输入类型、输入数据值）
	 * @param ruleId 规则ID
	 * @param modelId 模板ID
	 * @return 动作的规则展示列表集合
	 */
	public List<SlaRulePropertyShow> getSlaRuleProShowList(String ruleId, String modelId);
	
	/**
	 * 根据动作标识是否唯一
	 * @param actionmark 动作标识
	 * @return 返回true 或 false
	 */
	public boolean uniqueMark(String actionmark);
}
