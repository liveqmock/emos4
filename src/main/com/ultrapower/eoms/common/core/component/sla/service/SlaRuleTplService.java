package com.ultrapower.eoms.common.core.component.sla.service;

import java.util.List;

import com.ultrapower.eoms.common.core.component.sla.model.SlaRuleTpl;
import com.ultrapower.eoms.common.core.component.sla.model.SlaTplProperty;

/**
 * SLA规则模板服务提供者
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-8-30 下午03:43:58
 */
public interface SlaRuleTplService {

	/**
	 * 添加规则模板信息
	 * @param slaRuleTpl 规则模板信息
	 * @return 返回true 或 false
	 */
	public boolean addSlaRuleTpl(SlaRuleTpl slaRuleTpl);
	
	/**
	 * 添加规则模板信息
	 * @param slaRuleTpl 规则模板信息
	 * @param slaTplPropertyList 规则模板属性集合信息
	 * @return 返回true 或 false
	 */
	public boolean addSlaRuleTpl(SlaRuleTpl slaRuleTpl,List<SlaTplProperty> slaTplPropertyList);
	
	/**
	 * 更新规则模板信息
	 * @param slaRuleTpl 规则模板信息
	 * @param addSlaTplPropertyList 添加的规则模板属性信息
	 * @param modSlaTplPropertyList 更新的规则模板属性信息
	 * @param delSlaTplPropertyList 删除的规则模板属性信息
	 * @return 返回true 或 false
	 */
	public boolean updateRuleTpl(SlaRuleTpl slaRuleTpl,List<SlaTplProperty> addSlaTplPropertyList,List<SlaTplProperty> modSlaTplPropertyList,List<String> delSlaTplPropertypid);
	
	/**
	 * 获取规则模板信息
	 * @param id 规则模板的唯一标识
	 * @return 规则模板信息对象
	 */
	public SlaRuleTpl get(String id);
	
	/**
	 * 获取规则模板的属性集合信息
	 * @param slaRuleTplPid 规则模板的唯一标识
	 * @return 规则模板的属性集合
	 */
	public List<SlaTplProperty> getSlaTplProperty(String slaRuleTplPid);
	
	/**
	 * 获取规则模板的属性信息
	 * @param slaTplPropertypid 规则模板属性的唯一标识
	 * @return 规则模板的属性信息
	 */
	public SlaTplProperty getSlaTplPropertyInfo(String slaTplPropertypid);
	
	/**
	 * 删除规则模板信息
	 * @param id 规则模板的唯一标识
	 * @return 返回true 或 false
	 */
	public boolean deleteSlaRuleTpl(String id);
	
	/**
	 * 更新规则模板信息
	 * @param slaRuleTpl 规则模板信息
	 * @return 返回true 或 false
	 */
	public boolean updateRuleTpl(SlaRuleTpl slaRuleTpl);
	
	/**
	 * 检查规则模板标识符唯一性
	 * @param mark 标识符
	 * @return 返回true 或 false
	 */
	public boolean checkTplMarkUnique(String mark);
}
