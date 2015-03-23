package com.ultrapower.eoms.ultrasla.util;

import com.ultrapower.eoms.ultrasla.exception.UnInitializationException;
import com.ultrapower.eoms.ultrasla.mq.ASLAMessage;
import com.ultrapower.eoms.ultrasla.mq.ASLAMessageListener;
import com.ultrapower.eoms.ultrasla.service.IEventRuleParser;

/**
 * @author RenChenglin
 * @date 2011-11-1 下午04:47:06
 * @version
 */
public class WFSLAMessageListener extends ASLAMessageListener {
	private IEventRuleParser eventRuleParser;
	
	@Override
	protected void processMessage(ASLAMessage message) throws UnInitializationException {
		if(message!=null && message instanceof UltraSLAEvent){
			if(eventRuleParser==null){
				throw new UnInitializationException("未初始化异常：'eventRuleParser' in com.ultrapower.eoms.ultrasla.util.WFSLAMessageListener");
			}
			eventRuleParser.parse((UltraSLAEvent)message);
		}
	}

	public void setEventRuleParser(IEventRuleParser eventRuleParser) {
		this.eventRuleParser = eventRuleParser;
	}

}
