package com.ultrapower.eoms.common.core.component.sms.manager;

import java.util.List;
import com.ultrapower.eoms.common.RecordLog;
import com.ultrapower.eoms.common.core.component.sms.model.Smsmonitor;
import com.ultrapower.eoms.common.core.component.sms.model.SmsmonitorLog;
import com.ultrapower.eoms.common.core.component.sms.service.InsideSmsService;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.support.PageLimit;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;

/**
 * 短信内部模块发送调用服务实现类
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version Jul 29, 2010 11:19:46 AM
 */
public class InsideSmsImpl implements InsideSmsService{

	private IDao<Smsmonitor> smDao;
	private IDao<SmsmonitorLog> smLogDao;
	
	public List<Smsmonitor> getsm() {
		PageLimit pageLimit = PageLimit.getInstance();
		List<Smsmonitor> smList = null;
		try{
			smList = smDao.pagedQuery("from Smsmonitor", pageLimit, null);
		}catch(Exception e){
			e.printStackTrace();
		}
		return smList;
	}

	public Smsmonitor getsmById(String pid) {
		return smDao.get(pid);
	}

	public List<Smsmonitor> getsmByWhere(String where) {
		PageLimit pageLimit = PageLimit.getInstance();
		StringBuffer sql = new StringBuffer();
		sql.append("from Smsmonitor where 1=1 ");
		if(!"".equals(StringUtils.checkNullString(where)))
			sql.append(" and "+where);
		List<Smsmonitor> smList = null;
		try{
			smList = smDao.pagedQuery(sql.toString(), pageLimit, null);
		}catch(Exception e){
			e.printStackTrace();
		}
		return smList;
	}

	public List<SmsmonitorLog> getsmLog() {
		PageLimit pageLimit = PageLimit.getInstance();
		List<SmsmonitorLog> smLogList = null;
		try{
			smLogList = smLogDao.pagedQuery("from SmsmonitorLog", pageLimit, null);
		}catch(Exception e){
			e.printStackTrace();
		}
		return smLogList;
	}

	public SmsmonitorLog getsmLogById(String smsmonitorpid) {
		return smLogDao.get(smsmonitorpid);
	}

	public List<SmsmonitorLog> getsmLogByWhere(String where) {
		PageLimit pageLimit = PageLimit.getInstance();
		StringBuffer sql = new StringBuffer();
		sql.append("from SmsmonitorLog where 1=1 ");
		if(!"".equals(StringUtils.checkNullString(where)))
			sql.append(" and "+where);
		List<SmsmonitorLog> smLogList = null;
		try{
			smLogList = smLogDao.pagedQuery(sql.toString(), pageLimit, null);
		}catch(Exception e){
			e.printStackTrace();
		}
		return smLogList;
	}

	public boolean sendsm(String goal, String content, String systemtype,
			long presendtime, String relateid, int pri) {
		boolean flag = false;
		if("".equals(goal))
			return flag;
		Smsmonitor smsmonitor = new Smsmonitor();
		smsmonitor.setGoal(goal);
		smsmonitor.setContent(content);
		smsmonitor.setSendflag(0);
		smsmonitor.setPresendtime(presendtime);
		smsmonitor.setRelateid(relateid);
		smsmonitor.setPri(pri);
		smsmonitor.setSystemtype(systemtype);
		smsmonitor.setInfoinputtime(TimeUtils.getCurrentTime());
		try{
			smDao.save(smsmonitor);
			flag = true;
		}catch(Exception e){
			RecordLog.printLog("模块："+systemtype+",发送信息："+content+",接收手机："+goal+"。短信发送失败,失败原因:"+e.getMessage(),RecordLog.LOG_LEVEL_ERROR);;
		}
		return flag;
	}

	public boolean sendsm(String goal, String content, String systemtype,
			String relateid, int pri) {
		boolean flag = false;
		if("".equals(goal))
			return flag;
		Smsmonitor smsmonitor = new Smsmonitor();
		smsmonitor.setGoal(goal);
		smsmonitor.setContent(content);
		smsmonitor.setSystemtype(systemtype);
		smsmonitor.setSendflag(0);
		smsmonitor.setRelateid(relateid);
		smsmonitor.setPri(pri);
		smsmonitor.setInfoinputtime(TimeUtils.getCurrentTime());
		try{
			smDao.save(smsmonitor);
			flag = true;
		}catch(Exception e){
			RecordLog.printLog("模块："+systemtype+",发送信息："+content+",接收手机："+goal+"。短信发送失败,失败原因:"+e.getMessage(),RecordLog.LOG_LEVEL_ERROR);;
		}
		return flag;
	}

	public boolean sendsm(String goal, String content, String systemtype,
			int pri) {
		boolean flag = false;
		if("".equals(goal))
			return flag;
		Smsmonitor smsmonitor = new Smsmonitor();
		smsmonitor.setGoal(goal);
		smsmonitor.setContent(content);
		smsmonitor.setSystemtype(systemtype);
		smsmonitor.setPri(pri);
		smsmonitor.setSendflag(0);
		smsmonitor.setInfoinputtime(TimeUtils.getCurrentTime());
		try{
			smDao.save(smsmonitor);
			flag = true;
		}catch(Exception e){
			RecordLog.printLog("模块："+systemtype+",发送信息："+content+",接收手机："+goal+"。短信发送失败,失败原因:"+e.getMessage(),RecordLog.LOG_LEVEL_ERROR);;
		}
		return flag;
	}

	public boolean sendsm(String goal, String content, String systemtype,
			long presendtime, int pri) {
		boolean flag = false;
		if("".equals(goal))
			return flag;
		Smsmonitor smsmonitor = new Smsmonitor();
		smsmonitor.setGoal(goal);
		smsmonitor.setContent(content);
		smsmonitor.setSystemtype(systemtype);
		smsmonitor.setPresendtime(presendtime);
		smsmonitor.setPri(pri);
		smsmonitor.setSendflag(0);
		smsmonitor.setInfoinputtime(TimeUtils.getCurrentTime());
		try{
			smDao.save(smsmonitor);
			flag = true;
		}catch(Exception e){
			RecordLog.printLog("模块："+systemtype+",发送信息："+content+",接收手机："+goal+"。短信发送失败,失败原因:"+e.getMessage(),RecordLog.LOG_LEVEL_ERROR);;
		}
		return flag;
	}

	public boolean sendsm(String goal, String content, String systemtype) {
		boolean flag = false;
		if("".equals(goal))
			return flag;
		Smsmonitor smsmonitor = new Smsmonitor();
		smsmonitor.setGoal(goal);
		smsmonitor.setContent(content);
		smsmonitor.setSystemtype(systemtype);
		smsmonitor.setSendflag(0);
		smsmonitor.setInfoinputtime(TimeUtils.getCurrentTime());
		try{
			smDao.save(smsmonitor);
			flag = true;
		}catch(Exception e){
			RecordLog.printLog("模块："+systemtype+",发送信息："+content+",接收手机："+goal+"。短信发送失败,失败原因:"+e.getMessage(),RecordLog.LOG_LEVEL_ERROR);;
			e.printStackTrace();
		}
		return flag;
	}

	public void setSmDao(IDao<Smsmonitor> smDao) {
		this.smDao = smDao;
	}

	public void setSmLogDao(IDao<SmsmonitorLog> smLogDao) {
		this.smLogDao = smLogDao;
	}

	public void separatetimeSmsLog(long time) {
		Object[] values = {time};
		List<Smsmonitor> smsList = smDao.find("from Smsmonitor where sendflag = 1 or sendflag = 2 and infoinputtime <= ?", values);
		int smsListLen = 0;
		if(smsList!=null)
			smsListLen = smsList.size();
		RecordLog.printLog("获取到满足条件需要分离的数据条数:"+smsListLen, RecordLog.LOG_LEVEL_INFO);
		SmsmonitorLog smsInfoLog = null;
		Smsmonitor smsInfo = null;
		for(int i=0;i<smsListLen;i++){
			smsInfo = smsList.get(i);
			smsInfoLog = new SmsmonitorLog();
			smsInfoLog.setContent(StringUtils.checkNullString(smsInfo.getContent()));
			smsInfoLog.setFirstsendtime(smsInfo.getFirstsendtime());
			smsInfoLog.setGatewaycode(StringUtils.checkNullString(smsInfo.getGatewaycode()));
			smsInfoLog.setGatewaycribe(StringUtils.checkNullString(smsInfo.getGatewaycribe()));
			smsInfoLog.setGoal(StringUtils.checkNullString(smsInfo.getGoal()));
			smsInfoLog.setInfoinputtime(smsInfo.getInfoinputtime());
			smsInfoLog.setPresendtime(smsInfo.getPresendtime());
			smsInfoLog.setPri(smsInfo.getPri());
			smsInfoLog.setRelateid(StringUtils.checkNullString(smsInfo.getRelateid()));
			smsInfoLog.setRematk(StringUtils.checkNullString(smsInfo.getRematk()));
			smsInfoLog.setSendflag(smsInfo.getSendflag());
			smsInfoLog.setSendnum(smsInfo.getSendnum());
			smsInfoLog.setSendtime(smsInfo.getSendtime());
			smsInfoLog.setSeparatetime(time);
			smsInfoLog.setSmsmonitorpid(smsInfo.getPid());
			smsInfoLog.setSystemtype(StringUtils.checkNullString(smsInfo.getSystemtype()));
			try{
				RecordLog.printLog("短信记录:"+smsInfo.getPid()+",分离---start", RecordLog.LOG_LEVEL_INFO);
				smLogDao.save(smsInfoLog);
				smDao.remove(smsInfo);
				RecordLog.printLog("短信记录:"+smsInfo.getPid()+",分离---succeed", RecordLog.LOG_LEVEL_INFO);
				RecordLog.printLog("短信记录:"+smsInfo.getPid()+",分离---end", RecordLog.LOG_LEVEL_INFO);
			}catch(Exception e){
				RecordLog.printLog("短信记录:"+smsInfo.getPid()+",分离---failed", RecordLog.LOG_LEVEL_ERROR);
				RecordLog.printLog(e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
				e.printStackTrace();
			}
		}
	}
}
