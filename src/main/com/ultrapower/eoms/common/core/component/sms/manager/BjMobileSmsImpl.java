package com.ultrapower.eoms.common.core.component.sms.manager;

import com.ultrapower.eoms.common.RecordLog;
import com.ultrapower.eoms.common.core.component.sms.model.SmMonitor;
import com.ultrapower.eoms.common.core.component.sms.service.BjMobileSmsService;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;

public class BjMobileSmsImpl implements BjMobileSmsService {
	private IDao<SmMonitor> smmonitorDao;
	
	public boolean sendsm(String goal, String content, String smtype, String remark) {
		return this.sendsm(goal, "", content, smtype, TimeUtils.getCurrentTime(), remark);
	}
	
	public boolean sendsm(String goal, String copyto, String content, String smtype, long sendtime, String remark) {
		return this.sendsm(goal, copyto, content, smtype, sendtime, "", "", remark);
	}
	
	public boolean sendsm(String goal, String copyto, String content, String smtype, long sendtime, String relateid, String baseSchema, String remark) {
		boolean result = false;
		if("".equals(StringUtils.checkNullString(goal))) {
			return result;
		}
		SmMonitor smmonitor = new SmMonitor();
		smmonitor.setContent(content);
		smmonitor.setGoal(goal);
		smmonitor.setCopyto(copyto);
		smmonitor.setSmtype(smtype);
		smmonitor.setScanstatus("0"); // 未扫描
		smmonitor.setSendflag("0"); // 未发送
		smmonitor.setInputtime(TimeUtils.getCurrentTime());
		smmonitor.setSendtime(0L); // 发送时间
		smmonitor.setPresendtime(sendtime); // 预计发送时间
		smmonitor.setRelateid(relateid); // 工单id
		smmonitor.setBaseschema(baseSchema); // 工单类别
		smmonitor.setRemark(remark); // 备注字段
		try {
			smmonitorDao.save(smmonitor);
		} catch (Exception e) {
			RecordLog.printLog("模块："+smtype+",发送信息："+content+",接收手机："+goal+"。短信发送失败,失败原因:"+e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return result;
	}

	public void setSmmonitorDao(IDao<SmMonitor> smmonitorDao) {
		this.smmonitorDao = smmonitorDao;
	}
}
