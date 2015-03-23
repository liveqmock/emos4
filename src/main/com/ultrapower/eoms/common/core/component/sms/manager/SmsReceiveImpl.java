package com.ultrapower.eoms.common.core.component.sms.manager;

import com.ultrapower.eoms.common.core.component.sms.model.SmsReceive;
import com.ultrapower.eoms.common.core.component.sms.service.SmsReceiveService;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.TimeUtils;

public class SmsReceiveImpl implements SmsReceiveService {

	private IDao<SmsReceive>  smsReceiveDao;
	public boolean saveSmsReceive(SmsReceive smsReceive) {
		// TODO Auto-generated method stub
		smsReceive.setCreatetime(TimeUtils.getCurrentTime());
		this.getSmsReceiveDao().save(smsReceive);
		
		return true;
	}
	
	
	public IDao<SmsReceive> getSmsReceiveDao() {
		return smsReceiveDao;
	}
	public void setSmsReceiveDao(IDao<SmsReceive> smsReceiveDao) {
		this.smsReceiveDao = smsReceiveDao;
	}
	


}
