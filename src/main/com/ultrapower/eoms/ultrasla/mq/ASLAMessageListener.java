package com.ultrapower.eoms.ultrasla.mq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import com.ultrapower.eoms.ultrasla.exception.UnInitializationException;

/**
 * @author RenChenglin
 * @date 2011-11-1 下午04:31:29
 * @version
 */
public abstract class ASLAMessageListener implements MessageListener {

	public void onMessage(Message arg0) {
		if(arg0!=null && arg0 instanceof ObjectMessage){
			ObjectMessage omsg = (ObjectMessage)arg0;
			try {
				Object obj = omsg.getObject();
				ASLAMessage message = (ASLAMessage)obj;
				processMessage(message);
			} catch (JMSException e) {
				e.printStackTrace();
			} catch (UnInitializationException e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 处理消息
	 * @param message
	 */
	protected abstract void processMessage(ASLAMessage message) throws UnInitializationException;
}
