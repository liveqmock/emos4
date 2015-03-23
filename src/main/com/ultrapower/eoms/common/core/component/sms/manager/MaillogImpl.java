package com.ultrapower.eoms.common.core.component.sms.manager;

import com.ultrapower.eoms.common.core.component.sms.model.Maillog;
import com.ultrapower.eoms.common.core.component.sms.service.IMaillogService;
import com.ultrapower.eoms.common.core.dao.IDao;

public class MaillogImpl implements IMaillogService{
	private IDao<Maillog> maillogDao;

	@Override
	public void save(Maillog maillog) {
		maillogDao.save(maillog);
	}

	public void setMaillogDao(IDao<Maillog> maillogDao) {
		this.maillogDao = maillogDao;
	}

}
