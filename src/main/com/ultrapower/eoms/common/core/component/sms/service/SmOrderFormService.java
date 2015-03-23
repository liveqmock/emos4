package com.ultrapower.eoms.common.core.component.sms.service;

import java.util.List;

import com.ultrapower.eoms.common.core.component.sla.model.SlaRuleProperty;
import com.ultrapower.eoms.common.core.component.sla.model.SlaRulePropertyShow;
import com.ultrapower.eoms.common.core.component.sms.model.SmsOrderForm;
import com.ultrapower.eoms.common.core.component.sms.model.SmsBaseItem;
import com.ultrapower.eoms.common.core.component.sms.model.SmsBaseItemCfg;

/**
 * 短信订阅--工单管理
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-8-30 上午09:44:07
 */
public interface SmOrderFormService {

	/**
	 * 添加工单短信订阅信息
	 * @param smsOrderForm 订阅信息
	 * @return 返回 true 或 false
	 */
	public boolean addOrderInfo(SmsOrderForm smsOrderForm);
	
	/**
	 * 添加工单短信订阅信息(先删除,再添加)
	 * @param smsOrderFormList 订阅集合信息
	 */
	public void addOrderInfo(String loginname,String formschema,List<SmsOrderForm> smsOrderFormList);
	
	/**
	 * 获取工单短信订阅集合信息
	 * @param loginname 当前用户登录名
	 * @param formSchema 工单类别
	 * @return 工单短信订阅集合
	 */
	public List<SmsOrderForm> get(String loginname,String formSchema);
	
	/**
	 * 删除工单短信订阅信息
	 * @param id 唯一标识
	 * @return 返回 true 或 false
	 */
	public boolean deleteOrderInfo(String id);
	
	/**
	 * 删除工单短信订阅信息
	 * @param loginname 当前用户登录名
	 * @param formSchema 工单类别
	 * @return 返回 true 或 false
	 */
	public boolean deleteOrderInfo(String loginname,String formSchema);
	
	/**
	 * 根据给定的规则ID和模板标识取得该动作的规则展示列表（包括规则的属性、已经规则属性所对应模板属性输入数据源、输入类型、输入数据值）
	 * @param ruleId 规则ID
	 * @param modelMark 模板标识
	 * @return 规则展示列表
	 */
	public List<SlaRulePropertyShow> getSlaRuleProShowList(String ruleId, String modelMark);
	
	/**
	 * 保存故障工单短信预订的时候需要保存规则属性信息，这些信息存储在SLA规则属性信息表中，记录的规则ID为故障工单的字典值
	 * @param srplst --规则属性集合
	 * @param formSchema --工单类别字典值
	 * @return 返回 true 或 false
	 */
	public boolean saveRulePropertyInfo(List<SlaRuleProperty> srplst, String formSchema);
	
	/**
	 * 根据工单类别删除SLA规则属性信息表中对于该工单类别的规则属性信息
	 * @param formSchema
	 * @return 返回 true 或 false
	 */
	public boolean deleteRulePropertyInfo(String formSchema);
	
	/**
	 * 根据登录名和工单类别获得短信订阅的专业维度
	 * @param loginname
	 * @param schema
	 * @return
	 */
	public List<String> getSmsBaseItemOrdered(String loginname,String schema,int pageNum,int pageSize);
	
	/**
	 * 获取某一工单类别的专业维度，由于专业维度可能比较多，所以进行分页取出，当没有数据时返回NULL
	 * @param BaseSchema
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<String> getSmsBaseItemOfSchema(String loginName,String BaseSchema,int pageNum,int pageSize);
	
	/**
	 * 保存工单短信订阅的维度
	 * @param items
	 * @param loginname
	 * @param schema
	 */
	public void saveSmsBaseItemOrdered(List<String> items,String loginname,String schema);
	
	/**
	 * 删除工单短信订阅的专业信息
	 * @param loginname
	 * @param schema
	 * @return
	 */
	public boolean deleteBaseItemOrdered(String loginname,String schema);
	
	/**
	 * 启停用户工单短信订阅（全部启用/停用）
	 * @param loginname 用户登录名
	 * @param status 状态：1启用 2停用
	 * @return
	 */
	public boolean changeOrderStatus(String loginname,String status);
	
	/**
	 * 获得工单短信订阅状态，都都为停用的时候才为停用，否则为启用
	 * @param loginname
	 * @return
	 */
	public String getOrderStatus(String loginname);
}
