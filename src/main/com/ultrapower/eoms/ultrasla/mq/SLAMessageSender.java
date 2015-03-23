package com.ultrapower.eoms.ultrasla.mq;

import javax.jms.Destination;
import javax.jms.JMSException;

import org.springframework.jms.core.JmsTemplate;

/**
 * @author RenChenglin
 * @date 2011-10-19 下午01:29:46
 * @version
 */
public class SLAMessageSender {
	private JmsTemplate jmsTemplate;
	private ASLAMessageCreator messageCreator;
	private Destination messageDest;
	
	public SLAMessageSender(){
		//
	}

	public void sendMessage(ASLAMessage message) throws JMSException,Exception{
		if(message==null)
			throw new RuntimeException("消息实体为空!");
		messageCreator.setSlaMessage(message);
		jmsTemplate.send(messageDest, messageCreator);
	}
	
	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public ASLAMessageCreator getMessageCreator() {
		return messageCreator;
	}

	public void setMessageCreator(ASLAMessageCreator messageCreator) {
		this.messageCreator = messageCreator;
	}

	public Destination getMessageDest() {
		return messageDest;
	}

	public void setMessageDest(Destination messageDest) {
		this.messageDest = messageDest;
	}

}
