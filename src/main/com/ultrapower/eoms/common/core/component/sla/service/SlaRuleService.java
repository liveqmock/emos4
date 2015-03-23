package com.ultrapower.eoms.common.core.component.sla.service;

import java.util.List;

import com.ultrapower.eoms.common.core.component.sla.model.SlaRule;
import com.ultrapower.eoms.common.core.component.sla.model.SlaRuleProperty;
import com.ultrapower.eoms.ultrasm.model.DataInfo;

/**
 * SLA规则服务提供者
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-8-30 下午05:24:53
 */
public interface SlaRuleService {

	/**
	 * 添加SLA规则
	 * @param slaRule SLA规则信息
	 * @return 返回true 或 false
	 */
	public boolean addSlaRule(SlaRule slaRule);
	
	/**
	 * 添加SLA规则
	 * @param slaRule SLA规则信息
	 * @param slaRuleProperty SLA规则属性信息
	 * @return 返回true 或 false
	 */
	public boolean addSlaRule(SlaRule slaRule,List<SlaRuleProperty> slaRuleProperty);
	
	/**
	 * 更新SLA规则
	 * @param slaRule SLA规则信息
	 * @param slaRuleProperty SLA规则属性信息
	 * @return 返回true 或 false
	 */
	public boolean updateSlaRule(SlaRule slaRule,List<SlaRuleProperty> slaRuleProperty);
	
	/**
	 * 删除SLA规则
	 * @param id 唯一标识
	 * @return 返回true 或 false
	 */
	public boolean deleteSlaRule(String id);
	
	/**
	 * 获取SLA规则
	 * @param id 唯一标识
	 * @return SLA规则对象
	 */
	public SlaRule get(String id);
	
	/**
	 * 获取SLA规则属性集合信息
	 * @param id 唯一标识
	 * @return SLA规则属性集合
	 */
	public List<SlaRuleProperty> getSlaRuleProperty(String id);
	
	/**
	 * 执行sql语句，返回value和displayvalue
	 * @param sql sql脚本
	 * @return 获取键值对象集合
	 */
	public List<DataInfo> getInfoBySql(String sql);
	
	/**
	 * 判断标识符是否可用
	 * @param identifier 标识符
	 * @return 返回true 或 false
	 */
	public boolean isIdentifierUnique(String identifier);
}
