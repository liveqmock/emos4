package com.ultrapower.eoms.ultrasla.service;

import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.ultrasla.util.ConstantMark;
import com.ultrapower.eoms.ultrasla.util.Receiver;
import com.ultrapower.eoms.ultrasla.util.UltraSLAMessage;

/**
 * 消息通知接口
 * @author Administrator
 */
public interface INoticeService {

	/**
	 * 消息通知
	 * @param receiver 接收对象
	 * @param message 消息对象
	 * @param noticetype 接收类别 1.短信 2.邮件 3.短信+邮件 9.自定义动作类型
	 * @param param 参数
	 */
	public void notice(Map<ConstantMark.RecType,List<Receiver>> receiver, UltraSLAMessage message
			, long noticetype, Map<String,String> param);
}
