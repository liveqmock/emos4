package com.ultrapower.eoms.common.core.component.sms.service;

/**
 * 北京移动项目短信接口
 * @author SunHailong
 */
public interface BjMobileSmsService {
	/**
	 * 发送短信
	 * @param goal 发送手机号
	 * @param content 发送内容
	 * @param smtype 短信类型,指的短信来源的业务类型
	 * @param remark 备注字段,用于存储到数据库中做为扩展之用
	 * @return
	 */
	public boolean sendsm(String goal, String content, String smtype, String remark);
	
	/**
	 * 发送短信
	 * @param goal 发送手机号
	 * @param copyto 抄送手机号
	 * @param content 发送内容
	 * @param smtype 短信类型,指的短信来源的业务类型
	 * @param sendtime 发送时间
	 * @param remark 备注字段,用于存储到数据库中做为扩展之用
	 * @return
	 */
	public boolean sendsm(String goal, String copyto, String content, String smtype, long sendtime, String remark);
	
	/**
	 * 发送短信
	 * @param goal 发送手机号
	 * @param copyto 抄送手机号
	 * @param content 发送内容
	 * @param smtype 短信类型,指的短信来源的业务类型
	 * @param sendtime 发送时间
	 * @param relateid 业务关联id
	 * @param baseSchema 工单类别
	 * @param remark 备注字段,用于存储到数据库中做为扩展之用
	 * @return
	 */
	public boolean sendsm(String goal, String copyto, String content, String smtype, long sendtime, String relateid, String baseSchema, String remark);
}
