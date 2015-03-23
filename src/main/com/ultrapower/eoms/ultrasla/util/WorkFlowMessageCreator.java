package com.ultrapower.eoms.ultrasla.util;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import com.ultrapower.eoms.ultrasla.mq.ASLAMessage;
import com.ultrapower.eoms.ultrasla.mq.ASLAMessageCreator;
import com.ultrapower.eoms.ultrasla.util.UltraSLAEvent;


/**
 * @author RenChenglin
 * @date 2011-10-19 下午02:46:09
 * @version
 */
public class WorkFlowMessageCreator extends ASLAMessageCreator {

	public WorkFlowMessageCreator(){}
	
	@Override
	protected Message createJMSMessage(Session session, ASLAMessage slaMessage) throws JMSException {
		if(session==null||slaMessage==null)
			return null;
		UltraSLAEvent wfe = null;
		if(slaMessage instanceof UltraSLAEvent)
			wfe = (UltraSLAEvent)slaMessage;
		if(wfe==null)
			return null;
		return session.createObjectMessage(wfe);
	}

}
