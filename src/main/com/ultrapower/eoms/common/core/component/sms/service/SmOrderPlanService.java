package com.ultrapower.eoms.common.core.component.sms.service;

import java.util.List;

import com.ultrapower.eoms.common.core.component.sms.model.SmsOrderPlan;

/**
 * 短信订阅--作业计划
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-8-30 上午09:44:23
 */
public interface SmOrderPlanService {

	/**
	 * 添加计划短信订阅
	 * @param smsOrderPlan 订阅信息
	 * @return 返回 true 或 false
	 */
	public boolean addOrderInfo(SmsOrderPlan smsOrderPlan);
	
	/**
	 * 删除计划短信订阅
	 * @param id 唯一标识
	 */
	public void deleteOrderInfo(String id);
	
	/**
	 * 更新计划短信订阅信息
	 * @param smsOrderPlan 订阅信息
	 * @return 返回 true 或 false
	 */
	public boolean updateOrderInfo(SmsOrderPlan smsOrderPlan);
	
	/**
	 * 获取计划短信订阅列表信息
	 * @return 计划短信订阅集合
	 */
	public List<SmsOrderPlan> get();
	
	/**
	 * 获取计划短信订阅信息
	 * @param id 唯一标识
	 * @return 计划短信订阅对象
	 */
	public SmsOrderPlan get(String id);
}
