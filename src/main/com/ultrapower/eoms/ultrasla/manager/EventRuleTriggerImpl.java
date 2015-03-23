package com.ultrapower.eoms.ultrasla.manager;

import java.util.List;
import java.util.Map;

import javax.jms.JMSException;

import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.ultrasla.RecordLog;
import com.ultrapower.eoms.ultrasla.mq.SLAMessageSender;
import com.ultrapower.eoms.ultrasla.service.IEventRuleTrigger;
import com.ultrapower.eoms.ultrasla.util.UltraSLAEvent;
import com.ultrapower.eoms.ultrasla.util.UltraSlaUtil;
import com.ultrapower.eoms.ultrasla.util.ConstantMark.EventType;

public class EventRuleTriggerImpl implements IEventRuleTrigger {
	
	private SLAMessageSender wfSLAMessageSender;
	
	public EventRuleTriggerImpl() {

	}

	public int produce(String eventId, List<String> ruleIdLst, Long dueTime,
			List<String> defaultUser, List<String> defaultDept,
			List<String> defaultRole, Map params) {
		if(eventId==null             	//事件ID为空
				||ruleIdLst==null	 	//没有关联规则
				||ruleIdLst.size()<=0	//没有关联规则
				||dueTime<=0)		 	//处理时限<0
		{
			RecordLog.printLog(UltraSlaUtil.constructLog("非法触发参数，事件ID："+StringUtils.checkNullString(eventId), 13,null,null)
					, RecordLog.LOG_LEVEL_WARN);
			return 13;
		}
		
		//生成事件对象引用
		UltraSLAEvent event = new UltraSLAEvent(); //新建一个SLA事件对象引用
		event.setEventType(EventType.PRODUCE);     //设置事件类型为“产生”
		event.setEventId(eventId);                 //设置事件ID
		event.setRuleIdLst(ruleIdLst);             //设置规则ID
		event.setDuetime(dueTime); 				   //设置业务处理时限
		event.setDefaultUser(defaultUser); 		   //设置默认处理人
		event.setDefaultGroup(defaultDept); 	   //设置默认处理组
		event.setDefaultRole(defaultRole); 		   //设置默认处理角色
		event.setParams(params);				   //设置参数
		
		//发送消息
		return sendMessage(event);
	}

	public int destory(String eventId) {
		if(eventId==null)
		{
			RecordLog.printLog(UltraSlaUtil.constructLog("非法触发参数，事件ID："+StringUtils.checkNullString(eventId), 13,null,null)
					, RecordLog.LOG_LEVEL_WARN);
			return 13;
		}
		
		//生成事件对象引用
		UltraSLAEvent event = new UltraSLAEvent(); //新建一个SLA事件对象引用
		event.setEventType(EventType.DESTROY);     //设置事件类型为“产生”
		event.setEventId(eventId);                 //设置事件ID
		
		//发送消息
		return sendMessage(event);
	}

	public void setWfSLAMessageSender(SLAMessageSender wfSLAMessageSender) {
		this.wfSLAMessageSender = wfSLAMessageSender;
	}
	
	private int sendMessage(UltraSLAEvent event){
		//发送事件消息
		if(wfSLAMessageSender==null){
			RecordLog.printLog(UltraSlaUtil.constructLog("wfSLAMessageSender未初始化，事件ID："+StringUtils.checkNullString(event.getEventId()), 14,null,null)
					, RecordLog.LOG_LEVEL_WARN);
			return 14;
		}else{
			try {
				wfSLAMessageSender.sendMessage(event);
				return 10;
			} catch (JMSException e) {
				e.printStackTrace();
				RecordLog.printLog(UltraSlaUtil.constructLog("JMS异常，事件ID："+StringUtils.checkNullString(event.getEventId()), 11,null,null)
						, RecordLog.LOG_LEVEL_WARN);
				return 11;
			} catch (Exception e) {
				e.printStackTrace();
				RecordLog.printLog(UltraSlaUtil.constructLog("未知异常，事件ID："+StringUtils.checkNullString(event.getEventId()), 12,null,null)
						, RecordLog.LOG_LEVEL_WARN);
				return 12;
			}
		}
	}
}
