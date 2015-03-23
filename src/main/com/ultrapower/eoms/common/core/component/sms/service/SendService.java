package com.ultrapower.eoms.common.core.component.sms.service;
/**
 * 调用网关发送信息服务
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-7-30 下午02:26:48
 */
public interface SendService {

	/**
	 * 调用WebService发送短信息
	 * @param mobile 手机号码
	 * @param content 短信内容
	 * @return 返回"true" 或 "false"
	 */
	public String SendSm(String pid,String mobile,String content);
}
