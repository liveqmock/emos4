package com.ultrapower.eoms.ultrasla.mq.test;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import com.ultrapower.eoms.ultrasla.mq.ASLAMessage;
import com.ultrapower.eoms.ultrasla.mq.ASLAMessageCreator;

/**
 * @author RenChenglin
 * @date 2011-11-4 上午10:14:18
 * @version
 */
public class TestMsgCreator extends ASLAMessageCreator {

	@Override
	protected Message createJMSMessage(Session session, ASLAMessage slaMessage)
			throws JMSException {
		if(session==null||slaMessage==null)
			return null;
		Message msg = session.createObjectMessage(slaMessage);
		return msg;
	}

}
