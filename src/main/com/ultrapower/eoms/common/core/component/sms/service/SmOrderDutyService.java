package com.ultrapower.eoms.common.core.component.sms.service;

import java.util.List;

import com.ultrapower.eoms.common.core.component.sms.model.SmsOrderDuty;

/**
 * 短信订阅--值班管理
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-8-30 上午09:43:47
 */
public interface SmOrderDutyService {

	/**
	 * 添加值班短信订阅
	 * @param smsOrderDuty 订阅信息
	 * @return 返回 true 或 false
	 */
	public boolean addOrderInfo(SmsOrderDuty smsOrderDuty);
	
	/**
	 * 删除值班短信订阅信息
	 * @param id 唯一标识
	 */
	public void deleteOrderInfo(String id);
	
	/**
	 * 更新值班短信订阅信息
	 * @param smsOrderDuty 订阅信息
	 * @return 返回 true 或 false
	 */
	public boolean updateOrderInfo(SmsOrderDuty smsOrderDuty);
	
	/**
	 * 获取值班短信订阅列表信息
	 * @return 值班短信订阅的集合
	 */
	public List<SmsOrderDuty> get();
	
	/**
	 * 获取值班短信订阅信息
	 * @param id 唯一标识
	 * @return 值班短信订阅对象
	 */
	public SmsOrderDuty get(String id);
	/**
	 * 根据值班室取得短发发送的提前天数
	 * @param orgId
	 * @return 天数
	 */
	public int getSmsAheaddaynum(String orgId);
}
