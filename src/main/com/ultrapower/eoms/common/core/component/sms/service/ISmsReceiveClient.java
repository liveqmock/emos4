package com.ultrapower.eoms.common.core.component.sms.service;

public interface ISmsReceiveClient {

	public String receive(String eomsid,String mobile,String smscontext) throws Exception;
	
}
