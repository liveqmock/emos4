package com.ultrapower.eoms.common.core.component.sms.service;

import com.ultrapower.eoms.common.core.component.sms.model.SmsReceive;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;

public abstract class AbsSmsReceiveClient implements ISmsReceiveClient {

	protected SmsReceiveService smsReceiveService;
	protected InsideSmsService insidesm;
	
	
	/**
	 * 短信接收服务
	 * 返回 空对象是为处理成功,否则返回错误信息
	 */ 
	public String receive(String eomsid,String mobile,String smscontext) throws Exception
	{
		String result=null;
		smsReceiveService = (SmsReceiveService)WebApplicationManager.getBean("smsReceiveService");
		insidesm=(InsideSmsService)WebApplicationManager.getBean("insidesm");
		SmsReceive smsReceive=new SmsReceive();
		smsReceive.setEomsid(eomsid);
		smsReceive.setSmscontext(smscontext);
		smsReceive.setPhone(mobile);
		int opstate=4;
		try
		{
			if(dataHandle(eomsid,mobile,smscontext))
			{
				opstate=5;
				insidesm.sendsm(mobile, "短信操作处理成功!", "smcommon");
			}
			else
			{
				insidesm.sendsm(mobile, "短信操作处理失败!", "smcommon");
			}
			
		}catch(Exception ex)
		{
			insidesm.sendsm(mobile, ex.getMessage(), "smcommon");
			ex.printStackTrace();
		}
		smsReceive.setOpstate(opstate);//处理状态1:未处理  2:处理中 3:已处理但未连接上ar服务 4:已处理但处理失败(ar服务错误除外的错误) 5 已完成
		smsReceiveService.saveSmsReceive(smsReceive);
		return result;
	}
	
	protected abstract boolean dataHandle(String eomsid,String mobile,String smscontext) throws Exception;

}
