package com.ultrapower.eoms.common.core.component.sms.service;

import java.util.List;
import com.ultrapower.eoms.common.core.component.sms.model.Smsmonitor;
import com.ultrapower.eoms.common.core.component.sms.model.SmsmonitorLog;

/**
 * 短信内部模块发送调用服务
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version Jul 29, 2010 10:50:28 AM
 */
public interface InsideSmsService {

	/**
	 * 发送短信
	 * @param goal 手机号码 eg:13401162542
	 * @param content 短信内容
	 * @param systemtype 系统类型 eg:系统管理
	 * @param presendtime 定时发送时间  eg:1292159563(2010-12-12 21:12:43)
	 * @param relateid 业务关联ID
	 * @param pri 优先级 四个级别 3(高) 2(中) 0(一般) 1(低)  默认为0
	 * @return 返回true 或 false
	 */
	public boolean sendsm(String goal,String content,String systemtype,long presendtime,String relateid,int pri);
	
	/**
	 * 发送短信
	 * @param goal 手机号码 eg:13401162542
	 * @param content 短信内容
	 * @param systemtype 系统类型 eg:系统管理
	 * @param relateid 业务关联ID
	 * @param pri 优先级 四个级别 3(高) 2(中) 0(一般) 1(低)  默认为0
	 * @return 返回true 或 false
	 */
	public boolean sendsm(String goal,String content,String systemtype,String relateid,int pri);
	
	/**
	 * 发送短信
	 * @param goal 手机号码 eg:13401162542
	 * @param content 短信内容
	 * @param systemtype  系统类型 eg:系统管理
	 * @param pri 优先级 四个级别 3(高) 2(中) 0(一般) 1(低)  默认为0
	 * @return 返回true 或 false
	 */
	public boolean sendsm(String goal,String content,String systemtype,int pri);
	
	/**
	 * 发送短信
	 * @param goal 手机号码 eg:13401162542
	 * @param content 短信内容
	 * @param systemtype 系统类型 eg:系统管理
	 * @param presendtime 定时发送时间  eg:1292159563(2010-12-12 21:12:43)
	 * @param pri 优先级 四个级别 3(高) 2(中) 0(一般) 1(低)  默认为0
	 * @return 返回true 或 false
	 */
	public boolean sendsm(String goal,String content,String systemtype,long presendtime,int pri);
	
	/**
	 * 发送短信
	 * @param goal 手机号码 eg:13401162542
	 * @param content 短信内容
	 * @param systemtype 系统类型 eg:系统管理
	 * @return 返回true 或 false
	 */
	public boolean sendsm(String goal,String content,String systemtype);
	
	/**
	 * 查询短信发送情况
	 * @param pid 短信唯一码
	 * @return 返回短信信息实体对象
	 */
	public Smsmonitor getsmById(String pid);
	
	/**
	 * 查询短信发送情况
	 * @return 返回短信信息实体对象集合
	 */
	public List<Smsmonitor> getsm();
	
	/**
	 * 查询短信发送情况
	 * @param where 查询条件
	 * @return 返回短信信息实体对象集合
	 */
	public List<Smsmonitor> getsmByWhere(String where);
	
	/**
	 * 查询短信日志情况
	 * @param smsmonitorpid
	 * @return 短信日志唯一码
	 */
	public SmsmonitorLog getsmLogById(String smsmonitorpid);
	
	/**
	 * 查询短信日志情况
	 * @return 返回短信信息日志实体对象集合
	 */
	public List<SmsmonitorLog> getsmLog();
	
	/**
	 * 查询短信日志情况
	 * @param where 查询条件
	 * @return 返回短信信息日志实体对象集合
	 */
	public List<SmsmonitorLog> getsmLogByWhere(String where);

	/**
	 * 短信日志分离
	 * @param time 分离的时间点(将该时间点以前的短信数据转移至短信日志表中)
	 */
	public void separatetimeSmsLog(long time);
	
}
