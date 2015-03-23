package com.ultrapower.eoms.common.core.component.sms.manager;

import java.util.ArrayList;
import java.util.List;

import com.ultrapower.eoms.common.RecordLog;
import com.ultrapower.eoms.common.core.component.sms.model.SmsOrderDuty;
import com.ultrapower.eoms.common.core.component.sms.service.SmOrderDutyService;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.support.PageLimit;

/**
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-8-30 上午10:15:24
 */
public class SmOrderDutyImpl implements SmOrderDutyService{

	private IDao<SmsOrderDuty>  smOrderDutyDao;
	public boolean addOrderInfo(SmsOrderDuty smsOrderDuty) {
		boolean flag = false;
		try{
			smOrderDutyDao.save(smsOrderDuty);
			flag = true;
		}catch(Exception e){
			RecordLog.printLog("值班管理短信订阅:添加订阅信息出错!"+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
		}
		return flag;
	}

	public void deleteOrderInfo(String id) {
		try{
			smOrderDutyDao.removeById(id);
		}catch(Exception e){
			RecordLog.printLog("值班管理短信订阅:删除订阅信息出错!pid="+id+","+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
		}
	}

	public List<SmsOrderDuty> get() {
		List<SmsOrderDuty> smsOrderDutyList = new ArrayList<SmsOrderDuty>();
		PageLimit pageLimit = PageLimit.getInstance();
		String hql = "from SmsOrderDuty";
		smsOrderDutyList = smOrderDutyDao.pagedQuery(hql, pageLimit);
		return smsOrderDutyList;
	}

	public SmsOrderDuty get(String id) {
		SmsOrderDuty smsOrderDuty  = null;
		smsOrderDuty = smOrderDutyDao.get(id);
		return smsOrderDuty;
	}

	public boolean updateOrderInfo(SmsOrderDuty smsOrderDuty) {
		boolean flag = false;
		try{
			smOrderDutyDao.saveOrUpdate(smsOrderDuty);
			flag = true;
		}catch(Exception e){
			RecordLog.printLog("值班管理短信订阅:更新订阅信息出错!pid="+smsOrderDuty.getPid()+","+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
		}
		return flag;
	}

	
	public void setSmOrderDutyDao(IDao<SmsOrderDuty> smOrderDutyDao) {
		this.smOrderDutyDao = smOrderDutyDao;
	}

	/* (non-Javadoc)
	 * @see com.ultrapower.eoms.common.core.component.sms.service.SmOrderDutyService#getSmsAheaddaynum(java.lang.String)
	 */
	public int getSmsAheaddaynum(String orgId) {
		int aheaddaynum = 0;
		List<SmsOrderDuty> list = smOrderDutyDao.find("from SmsOrderDuty where dutyroomid = ?", orgId);
		if(list != null && !list.isEmpty())
			aheaddaynum = list.get(0).getAheaddaynum().intValue();
		return aheaddaynum;
	}
}
