package com.ultrapower.eoms.ultrasla.mq.test;

import com.ultrapower.eoms.ultrasla.exception.UnInitializationException;
import com.ultrapower.eoms.ultrasla.mq.ASLAMessage;
import com.ultrapower.eoms.ultrasla.mq.ASLAMessageListener;

/**
 * @author RenChenglin
 * @date 2011-11-4 上午10:19:19
 * @version
 */
public class TestMsgListener extends ASLAMessageListener {

	@Override
	protected void processMessage(ASLAMessage message)
			throws UnInitializationException {
		System.out.println(message);
	}

}
