package com.ultrapower.eoms.ultrasla.mq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.MessageCreator;

/**
 * @author RenChenglin
 * @date 2011-10-19 下午02:05:44
 * @version
 */
public abstract class ASLAMessageCreator implements MessageCreator {
	
	private ASLAMessage slaMessage;
	
	public Message createMessage(Session session) throws JMSException {
		return createJMSMessage(session,slaMessage);
	}
	
	protected void setSlaMessage(ASLAMessage slaMessage){
		this.slaMessage = slaMessage;
	}
	
	protected abstract Message createJMSMessage(Session session,ASLAMessage slaMessage) throws JMSException;
}
