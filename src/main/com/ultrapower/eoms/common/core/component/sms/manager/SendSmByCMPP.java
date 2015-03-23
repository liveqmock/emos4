package com.ultrapower.eoms.common.core.component.sms.manager;

import com.ultrapower.eoms.common.core.component.sms.service.SendService;

/**
 * 华为网关协议
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-8-2 下午02:37:24
 */
public class SendSmByCMPP implements SendService{

	public String SendSm(String pid, String mobile, String content) {
		return "true";
	}
}
