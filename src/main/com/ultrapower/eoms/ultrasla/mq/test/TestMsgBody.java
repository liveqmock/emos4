package com.ultrapower.eoms.ultrasla.mq.test;

import com.ultrapower.eoms.ultrasla.mq.ASLAMessage;

/**
 * @author RenChenglin
 * @date 2011-11-4 上午10:09:43
 * @version
 */
public class TestMsgBody extends ASLAMessage {
	
	private String information;
	
	public TestMsgBody(){
		information = "A test info produced at " + System.currentTimeMillis() + "(ms)";
	}
	
	@Override
	public String toString(){
		return information;
	}
}
