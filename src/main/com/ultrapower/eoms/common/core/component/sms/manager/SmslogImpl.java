package com.ultrapower.eoms.common.core.component.sms.manager;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ultrapower.eoms.common.core.component.sms.model.Smslog;
import com.ultrapower.eoms.common.core.component.sms.service.ISmslogService;
import com.ultrapower.eoms.common.core.dao.IDao;

@Service
public class SmslogImpl implements ISmslogService {

	private IDao<Smslog> smslogDao;

	@Override
	public void save(Smslog smslog) {
		smslogDao.save(smslog);
	}

	public void setSmslogDao(IDao<Smslog> smslogDao) {
		this.smslogDao = smslogDao;
	}
	
}
