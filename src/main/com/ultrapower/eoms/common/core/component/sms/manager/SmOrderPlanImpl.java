package com.ultrapower.eoms.common.core.component.sms.manager;

import java.util.ArrayList;
import java.util.List;

import com.ultrapower.eoms.common.RecordLog;
import com.ultrapower.eoms.common.core.component.sms.model.SmsOrderPlan;
import com.ultrapower.eoms.common.core.component.sms.service.SmOrderPlanService;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.support.PageLimit;

/**
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-8-30 上午10:15:53
 */
public class SmOrderPlanImpl implements SmOrderPlanService{

	private IDao<SmsOrderPlan> smOrderPlanDao;
	
	public boolean addOrderInfo(SmsOrderPlan smsOrderPlan) {
		boolean flag = false;
		try{
			smOrderPlanDao.save(smsOrderPlan);
			flag = true;
		}catch(Exception e){
			RecordLog.printLog("计划管理短信订阅:添加订阅信息出错!"+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
		}
		return flag;
	}

	public void deleteOrderInfo(String id) {
		try{
			smOrderPlanDao.removeById(id);
		}catch(Exception e){
			RecordLog.printLog("计划管理短信订阅:删除订阅信息出错!pid="+id+","+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
		}
	}

	public List<SmsOrderPlan> get() {
		List<SmsOrderPlan> smsOrderPlanList = new ArrayList<SmsOrderPlan>();
		PageLimit pageLimit = PageLimit.getInstance();
		String hql = " from SmsOrderPlan";
		smsOrderPlanList = smOrderPlanDao.pagedQuery(hql, pageLimit);
		return smsOrderPlanList;
	}

	public SmsOrderPlan get(String id) {
		SmsOrderPlan smsOrderPlan = null;
		smsOrderPlan = smOrderPlanDao.get(id);
		return smsOrderPlan;
	}

	public boolean updateOrderInfo(SmsOrderPlan smsOrderPlan) {
		boolean flag = false;
		try{
			smOrderPlanDao.saveOrUpdate(smsOrderPlan);
			flag = true;
		}catch(Exception e){
			RecordLog.printLog("计划管理短信订阅:更新订阅信息出错!pid="+smsOrderPlan.getPid()+","+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
		}
		return flag;
	}

	
	public void setSmOrderPlanDao(IDao<SmsOrderPlan> smOrderPlanDao) {
		this.smOrderPlanDao = smOrderPlanDao;
	}
}
