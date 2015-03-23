package com.ultrapower.eoms.ultrasla.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ultrapower.eoms.common.core.component.rquery.util.SqlReplace;
import com.ultrapower.eoms.common.core.util.NumberUtils;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.ultrasla.RecordLog;
import com.ultrapower.eoms.ultrasla.model.EventAction;
import com.ultrapower.eoms.ultrasla.model.EventHandlerComp;
import com.ultrapower.eoms.ultrasla.model.EventParam;
import com.ultrapower.eoms.ultrasla.model.EventRule;
import com.ultrapower.eoms.ultrasla.service.ICustomOrganizationService;
import com.ultrapower.eoms.ultrasla.service.IEventActionProcessor;
import com.ultrapower.eoms.ultrasla.service.IEventActionService;
import com.ultrapower.eoms.ultrasla.service.IEventHandlerCompService;
import com.ultrapower.eoms.ultrasla.service.IEventParamService;
import com.ultrapower.eoms.ultrasla.service.IEventRuleService;
import com.ultrapower.eoms.ultrasla.service.INoticeService;
import com.ultrapower.eoms.ultrasla.service.IOrganizationService;
import com.ultrapower.eoms.ultrasla.service.ISelfDefineNotice;
import com.ultrapower.eoms.ultrasla.util.ConstantMark;
import com.ultrapower.eoms.ultrasla.util.EventStatusInfo;
import com.ultrapower.eoms.ultrasla.util.ParamResolve;
import com.ultrapower.eoms.ultrasla.util.Receiver;
import com.ultrapower.eoms.ultrasla.util.UltraSLAMessage;
import com.ultrapower.eoms.ultrasla.util.UltraSlaUtil;

public class EventActionProcessorImpl implements IEventActionProcessor {

	private IEventActionService eventActionService;
	private IEventRuleService eventRuleService;
	private IEventParamService eventParamService;
	private IOrganizationService slaOrganizationService;
	private IEventHandlerCompService eventHandlerCompService;
	private INoticeService noticeService;
	
	public void process(List<EventAction> eventActionList, long currentTime) {
		int eventLen = 0;
		if(eventActionList != null) {
			eventLen = eventActionList.size();
		}
		EventAction eventAction; // 事件动作对象
		EventParam eventParam; // 事件参数
		EventRule eventRule = null; // 事件规则对象
		String ruleid = ""; // 事件规则id
		String defhanid; // 默认通知对象
		long actiontype; // 动作类型
		String actionparam; // 动作参数
		String topic; // 通知主题
		String content; //通知内容
		UltraSLAMessage message; // 消息信息对象（主题、内容）
		HashMap<String, String> paramMap; // 参数Map
		HashMap<ConstantMark.RecType, List<Receiver>> recMap; // 接收者Map
		String eventid; // 事件id
		long sendNum; // 已通知次数
		boolean isSend; // 是否发送
		String actionid; //动作id
		EventStatusInfo eventStatusInfo; // 动作事件需要修改的状态等相关信息对象
		for(int i=0 ; i<eventLen ; i++) {
			eventAction = eventActionList.get(i);
			if(eventAction == null) {
				continue;
			}
			isSend = true;
			actionid = StringUtils.checkNullString(eventAction.getPid());
			
			// 如果和前一条动作关联的规则一样，则不需要重新获取该规则
			if(eventAction.getRuleid() != null && !ruleid.equals(eventAction.getRuleid())) {
				ruleid = eventAction.getRuleid();
				eventRule = eventRuleService.getById(ruleid); // 根据规则id获取事件规则对象
			}
			if(eventRule == null) {
				RecordLog.printLog("EventAction:"+actionid+", EventRule is null!", RecordLog.LOG_LEVEL_ERROR);
				continue;
			}
			
			// 封装动作事件需要修改的状态等相关信息对象
			sendNum = NumberUtils.formatToLong(eventAction.getNoticedtimes()); // 获取已通知次数
			eventStatusInfo = this.getEventStatus(eventRule, actionid, sendNum, currentTime); // 获取事件状态更新对象
			if(NumberUtils.formatToFloat(eventStatusInfo.getNoticenum()) == sendNum) { // 当通知次数没变时，则为当前未在设置的接收通知时间范围内，则不发送
				isSend = false;
			}
			
			// 修改事件动作状态
			eventAction.setActionstatus(eventStatusInfo.getActionstatus()); // 修改状态
			eventAction.setNoticedtimes(eventStatusInfo.getNoticenum()); // 修改通知次数
			if(eventStatusInfo.getActiontime() > 0)
				eventAction.setNextnoticetime(eventStatusInfo.getActiontime()); // 修改下次通知时间
			eventActionService.save(eventAction);
			
			if(!isSend) { // 发送标识为false 则不执行发送动作，直接到下个循环
				continue;
			}
			
			// 获取SLA事件参数paramMap
			eventid = StringUtils.checkNullString(eventAction.getEventid());
			if("".equals(eventid)) {
				RecordLog.printLog("EventAction:"+actionid+", eventid is null!", RecordLog.LOG_LEVEL_ERROR);
				continue;
			}
			eventParam = eventParamService.getByActionId(actionid); // 根据事件id获取事件参数
			if(eventParam != null) {
				paramMap = ParamResolve.xmlThansToMap(eventParam.getActionparamxml()); // 将xml格式的参数转换为map
			} else {
				paramMap = new HashMap<String, String> ();
			}
			
			actiontype = NumberUtils.formatToLong(eventRule.getActiontype());
			if(actiontype == ConstantMark.SEND_TYPE_SELFNOTICE) { // 如果是自定义执行动作，则直接调用自定义动作接口
				actionparam = StringUtils.checkNullString(eventRule.getActionparameter()).trim();
				this.selfDefineAction(paramMap, eventid, actionparam);
				continue; // 已调用了执行动作，所以对此动作事件下边代码无需执行
			}
			
			// 解析通知主题、内容
			topic = this.paramAutoThans(StringUtils.checkNullString(eventRule.getNoticetopic()), paramMap);
			content = this.paramAutoThans(StringUtils.checkNullString(eventRule.getNoticecontent()), paramMap);
			message = new UltraSLAMessage(topic, content);
			if(eventStatusInfo.getNoticenum() > 1) {
				message.setRemark("第" + eventStatusInfo.getNoticenum() + "次");
			}
			
			// 获取接收者信息列表
			defhanid = StringUtils.checkNullString(eventAction.getDefaulthanlderid()); // 获取默认通知对象
			recMap = this.getReceiverMap(eventRule, paramMap, defhanid); // 获取接收者信息集合Map
			
			// 批量执行动作
			paramMap.put("ACTIONID", actionid); // 事件id做为业务id传递过去
			paramMap.put("FIXED_CAL_TYPE", eventRule.getCalendartype()); // 节假日业务类别做为参数放到参数Map中
			this.doAction(recMap, message, actiontype, paramMap); // 执行通知动作
		}
	}
	
	/**
	 * 获取事件状态更新对象
	 * @param eventRule 事件规则
	 * @param actionid 事件动作id
	 * @param currentTime 当前时间
	 * @return
	 */
	private EventStatusInfo getEventStatus(EventRule eventRule, String actionid, long sendNum, long currentTime) {
		EventStatusInfo eventStatusInfo = new EventStatusInfo();
		eventStatusInfo.setActionid(actionid); // 事件动作id
		String starttime = StringUtils.checkNullString(eventRule.getNoticestarttime()); // 通知开始时间
		String endtime = StringUtils.checkNullString(eventRule.getNoticeendtime()); // 通知结束时间
		String curDay = TimeUtils.formatIntToDateString("yyyy-MM-dd", currentTime); // 当前日期 yyyy-MM-dd格式
		String curTime = TimeUtils.formatIntToDateString("HH:mm", currentTime); // 当前时间 00:00格式

		long resend = NumberUtils.formatToLong(eventRule.getIsnoticeafterwards()); // 是否补发 当前时间不在指定接收通知时间范围内时使用
		long noticeNum = NumberUtils.formatToLong(eventRule.getNoticetimes()); // 通知次数
		long noticeStep = NumberUtils.formatToLong(eventRule.getNoticeinterval()); // 每次通知间隔
		if(starttime.compareTo(endtime) < 0) { // 当天的时间段
			if(curTime.compareTo(starttime) >= 0 && curTime.compareTo(endtime) <= 0) { // 当前时间在时间段内
				if(noticeNum < 1 || noticeNum <= sendNum + 1) { // 通知次数大于已通知次数时
					eventStatusInfo.setNoticenum(noticeNum);
					eventStatusInfo.setActionstatus(ConstantMark.EVENT_ACTION_STATUS_COMPLETED);
					eventStatusInfo.setActiontime(0);
				} else {
					eventStatusInfo.setNoticenum(sendNum + 1);
					eventStatusInfo.setActionstatus(ConstantMark.EVENT_ACTION_STATUS_ACTIVE);
					eventStatusInfo.setActiontime(currentTime + noticeStep + ConstantMark.EVENT_ACTION_LIMIT_END); // 下一次通知时间为当前通知时间+通知间隔+预留程序处理的时间
				}
			} else { // 不在时间段内则此时不通知信息
				eventStatusInfo.setNoticenum(sendNum); // 已通知次数不变
				if(resend == 1) { // 如果补发 则将状态改为补发活动状态 否则 改为补发销毁状态
					eventStatusInfo.setActionstatus(ConstantMark.EVENT_ACTION_STATUS_REACTIVE);
					//跨天时间段 和 当天时间段 唯一区别就是 “事件补发的通知时间” 代码如下：
					if(curTime.compareTo(starttime) <= 0) { // 比开始时间小时，则当前天的开始时间通知即可
						eventStatusInfo.setActiontime(TimeUtils.formatDateStringToInt(curDay + " " + starttime + ":00", "yyyy-MM-dd HH:mm:ss")); // 设置通知时间为当前天的开始接收时间
					} else { // 当当前时间比结束时间大时，则第二天的开始时间通知即可
						eventStatusInfo.setActiontime(TimeUtils.formatDateStringToInt(curDay + " " + starttime + ":00", "yyyy-MM-dd HH:mm:ss") + 24 * 60 * 60); // 设置通知时间为第二天的开始接收时间
					}
				} else {
					eventStatusInfo.setActionstatus(ConstantMark.EVENT_ACTION_STATUS_REDESTROY);
					eventStatusInfo.setActiontime(0);
				}
			}
		} else { // 跨天的时间段
			if(curTime.compareTo(starttime) >= 0 || curTime.compareTo(endtime) <= 0) { // 当前时间在跨天的时间段内
				if(noticeNum < 1 || noticeNum <= sendNum + 1) { // 通知次数大于已通知次数时
					eventStatusInfo.setNoticenum(noticeNum);
					eventStatusInfo.setActionstatus(ConstantMark.EVENT_ACTION_STATUS_COMPLETED);
					eventStatusInfo.setActiontime(0);
				} else {
					eventStatusInfo.setNoticenum(sendNum + 1);
					eventStatusInfo.setActionstatus(ConstantMark.EVENT_ACTION_STATUS_ACTIVE);
					eventStatusInfo.setActiontime(currentTime + noticeStep + ConstantMark.EVENT_ACTION_LIMIT_END); // 下一次通知时间为当前通知时间+通知间隔+预留程序处理的时间
				}
			} else { // 不在时间段内则此时不发送信息
				eventStatusInfo.setNoticenum(sendNum); // 已通知次数不变
				if(resend == 1) {
					eventStatusInfo.setActionstatus(ConstantMark.EVENT_ACTION_STATUS_REACTIVE);
					//跨天时间段 和 当天时间段 唯一区别就是 “事件补发的通知时间” 代码如下：
					eventStatusInfo.setActiontime(TimeUtils.formatDateStringToInt(curDay + " " + starttime + ":00", "yyyy-MM-dd HH:mm:ss")); // 设置通知时间为当天的开始接收时间
				} else {
					eventStatusInfo.setActionstatus(ConstantMark.EVENT_ACTION_STATUS_REDESTROY);
					eventStatusInfo.setActiontime(0);
				}
			}
		}
		return eventStatusInfo;
	}
	
	/**
	 * 参数自动转换
	 * @param content 转换内容
	 * @param paramMap 转换参数
	 * @return
	 */
	private String paramAutoThans(String content, final HashMap<String, String> paramMap) {
		return SqlReplace.stringReplaceAllVar(content, paramMap);
	}
	
	/**
	 * 获取接收者总列表Map
	 * @param eventRule 事件规则对象
	 * @param dynamicParamMap 动态参数Map
	 * @param defhanid 默认通知对象
	 * @return
	 */
	private HashMap<ConstantMark.RecType, List<Receiver>> getReceiverMap(EventRule eventRule, final HashMap<String, String> dynamicParamMap, String defhanid) {
		HashMap<ConstantMark.RecType, List<Receiver>> recMap = new HashMap<ConstantMark.RecType, List<Receiver>> ();
		if(eventRule == null) {
			return recMap;
		}
		// 指定通知的接收者
		// 通知对象
		String noticeInfo = eventRule.getNoticehandlerid();
		recMap.put(ConstantMark.RecType.NOTICE, this.getReceiverByIdInfo(noticeInfo, 2));
		// 抄送对象
		String duplicateInfo = eventRule.getDuplicatehandlerid();
		recMap.put(ConstantMark.RecType.DUPLICATE, this.getReceiverByIdInfo(duplicateInfo, 2));
		// 升级对象
		String upgradeInfo = eventRule.getUpgradehandlerid();
		recMap.put(ConstantMark.RecType.UPGRADE, this.getReceiverByIdInfo(upgradeInfo, 2));
		// 固定通知对象
		String fixedInfo = eventRule.getFixedhandler();
		recMap.put(ConstantMark.RecType.FIXED, this.getReceiverByFixed(fixedInfo));
		// 自定义通知的组
		String compids = eventRule.getCustnoticecompid();
		recMap.put(ConstantMark.RecType.COMP, this.getReceiverByComp(compids, dynamicParamMap));
		
		// 通知的默认人或组
		long isnotice = NumberUtils.formatToLong(eventRule.getIsdefaultnotice());
		boolean isNull = false; // 是否不能正常获取到默认的接收者
		if(isnotice == 1) { // 1.通知默认人标识
			long checktype = NumberUtils.formatToLong(eventRule.getDefaultchecktype());
			String checkvalue = StringUtils.checkNullString(eventRule.getDefaultcheckparam()).trim();
			if(checktype == 0) { // 通知传递过来的对象
				recMap.put(ConstantMark.RecType.DEFAULT, this.getReceiverByIdInfo(defhanid, 1));
			} else if("".equals(checkvalue)) {
				isNull = true;
			} else { // 通知动态获取的
				recMap.put(ConstantMark.RecType.DEFAULT, this.getDynamicReceiver(checktype, checkvalue, dynamicParamMap));
			}
		} else {
			isNull = true;
		}
		if(isNull) {
			recMap.put(ConstantMark.RecType.DEFAULT, null);
		}
		return recMap;
	}
	
	/**
	 * 根据用户、组、角色id获取接收者信息列表
	 * @param idInfo 用户、组、角色id集合
	 * @return
	 */
	private List<Receiver> getReceiverByIdInfo(String idInfo, int idtype) {
		List<Receiver> recList = null;
		if("".equals(StringUtils.checkNullString(idInfo))) {
			return recList;
		}
		// idInfo 格式： U:id1,id2;D:id1,id2;R:id1,id2
		String[] idInfoArray = idInfo.split(";");
		String type; // 角色类型 U,D,R
		String ids; // 角色id
		String[] idArray;
		int space; // 分割位置
		List<Receiver> subrecList;
		for(int i=0 ; i<idInfoArray.length ; i++) {
			space = idInfoArray[i].indexOf(":");
			if(space <= 0) {
				continue;
			}
			type = idInfoArray[i].substring(0, space); // 角色类型 U,D,R
			ids = idInfoArray[i].substring(space + 1); // 角色id
			idArray = ids.split(",");
			for(int k=0 ; k<idArray.length ; k++) {
				if("".equals(idArray[k])) {
					continue;
				}
				subrecList = slaOrganizationService.getReceiver(idArray[k], this.getOrgType(type), idtype); // 获取消息接收者
				if(recList == null) {
					recList = subrecList;
				} else {
					if(subrecList != null) {
						recList.addAll(subrecList);
					}
				}
			}
		}
		return recList;
	}
	
	/**
	 * 根据固定数据获取接收者信息列表
	 * @param fixedInfo
	 * @return
	 */
	private List<Receiver> getReceiverByFixed(String fixedInfo) {
		List<Receiver> recList = null;
		if("".equals(StringUtils.checkNullString(fixedInfo))) {
			return recList;
		}
		recList = new ArrayList<Receiver> (); // 请勿删，以下会使用此List
		String[] subFixed = fixedInfo.split(";");
		String[] fixeds;
		for(int i=0 ; i<subFixed.length ; i++) {
			if("".equals(subFixed[i])) {
				continue;
			}
			fixeds = subFixed[i].split(":");
			if(fixeds.length == 2) {
				recList.addAll(this.getReceiverByData(fixeds[1], fixeds[0]));
			}
		}
		return recList;
	}
	
	/**
	 * 根据固定数据获取接收者信息列表
	 * @param datas 以,分割的数据
	 * @param type 类型 mobile mail
	 * @return
	 */
	private List<Receiver> getReceiverByData(String datas, String type) {
		List<Receiver> recList = new ArrayList<Receiver> (); // 请不要设置为null，要返回的结果是非null
		if("".equals(StringUtils.checkNullString(datas))) {
			return recList;
		}
		String[] dataArray = datas.split(",");
		Receiver receiver;
		for(int i=0 ; i<dataArray.length ; i++) {
			receiver = new Receiver();
			if("mobile".equals(type)) {
				receiver.setMobile(dataArray[i]);
			} else if("mail".equals(type)) {
				receiver.setEmail(dataArray[i]);
			}
			recList.add(receiver);
		}
		return recList;
	}
	
	/**
	 * 根据自定义组件id获取接收者信息列表
	 * @param compids
	 * @return
	 */
	private List<Receiver> getReceiverByComp(String compids, final HashMap<String, String> dynamicParamMap) {
		List<Receiver> recList = null;
		if("".equals(StringUtils.checkNullString(compids))) {
			return recList;
		}
		String[] compidArray = compids.split(",");
		long comptype; // 组件执行类型
		String compvalue; // 组件执行值
		EventHandlerComp eventHandlerComp;
		List<Receiver> subrecList;
		for(int i=0 ; i<compidArray.length ; i++) {
			eventHandlerComp = eventHandlerCompService.get(compidArray[i]); // 根据组件id获取事件主体组件配置
			if(eventHandlerComp == null) {
				continue;
			}
			comptype = NumberUtils.formatToLong(eventHandlerComp.getCompexetype());
			compvalue = StringUtils.checkNullString(eventHandlerComp.getCompexeparam()).trim();
			subrecList = this.getDynamicReceiver(comptype, compvalue, dynamicParamMap); // 获取动态的接收者
			if(recList == null) {
				recList = subrecList;
			} else {
				if(subrecList != null) {
					recList.addAll(subrecList);
				}
			}
		}
		return recList;
	}
	
	/**
	 * 获取动态的接收者
	 * @param type 获取类型
	 * @param value 获取值
	 * @param dynamicParamMap //参数Map
	 * @return
	 */
	private List<Receiver> getDynamicReceiver(long type, String value, final HashMap<String, String> dynamicParamMap) {
		List<Receiver> recList = null;
		if("".equals(StringUtils.checkNullString(value))) {
			return recList;
		}
		if(type == 1) { // SQL方式获取接收者
			ReceiverSqlImpl receiverSqlImpl = new ReceiverSqlImpl();
			recList = receiverSqlImpl.getReceiverFromSql(dynamicParamMap, value);
		} else if(type == 2) { // 实现类方式获取接收者
			ICustomOrganizationService customOrganizationService = (ICustomOrganizationService) WebApplicationManager.getBean(value); // 获取实例customOrganizationService
			if(customOrganizationService == null) { // 如果没在spring中配置，则取文件
				customOrganizationService = (ICustomOrganizationService) UltraSlaUtil.getClassInstance(value);
			}
			if(customOrganizationService != null) {
				recList = customOrganizationService.getReceiverFromImpl(dynamicParamMap);
			}
		}
		return recList;
	}
	
	/**
	 * 获取组织机构类型
	 * @param type 角色类型
	 * @return
	 */
	private ConstantMark.OrgType getOrgType(String type) {
		if(type == null) {
			// 非空验证
		} else if("U".equals(type)) {
			return ConstantMark.OrgType.USER;
		} else if("D".equals(type)) {
			return ConstantMark.OrgType.GROUP;
		} else if("R".equals(type)) {
			return ConstantMark.OrgType.ROLE;
		}
		return ConstantMark.OrgType.USER;
	}
	
	/**
	 * 执行通知动作入口
	 * @param recMap 接收者集合
	 * @param message 接收信息对象
	 * @param actiontype 动作类型
	 */
	private void doAction(final HashMap<ConstantMark.RecType, List<Receiver>> recMap
			, UltraSLAMessage message, long actiontype, HashMap<String, String> paramMap) {
		noticeService.notice(recMap, message, actiontype, paramMap);
	}
	
	/**
	 * 自定义执行动作入口
	 * @param paramMap 事件参数
	 * @param eventid 事件id
	 * @param actionparam 动作参数
	 */
	private void selfDefineAction(final HashMap<String, String> paramMap, String eventid, String actionparam) {
		if(!"".equals(StringUtils.checkNullString(actionparam))) {
			ISelfDefineNotice selfDefineNotice = (ISelfDefineNotice) WebApplicationManager.getBean(actionparam);
			if(selfDefineNotice == null) { // 如果没在spring中配置，则取文件
				selfDefineNotice = (ISelfDefineNotice) UltraSlaUtil.getClassInstance(actionparam);
			}
			if(selfDefineNotice != null) {
				selfDefineNotice.doTask(paramMap, eventid); // 调用自定义通知处理
			}
		}
	}
	
	public void setEventActionService(IEventActionService eventActionService) {
		this.eventActionService = eventActionService;
	}
	public void setEventRuleService(IEventRuleService eventRuleService) {
		this.eventRuleService = eventRuleService;
	}
	public void setEventParamService(IEventParamService eventParamService) {
		this.eventParamService = eventParamService;
	}
	public void setSlaOrganizationService(IOrganizationService slaOrganizationService) {
		this.slaOrganizationService = slaOrganizationService;
	}
	public void setEventHandlerCompService(IEventHandlerCompService eventHandlerCompService) {
		this.eventHandlerCompService = eventHandlerCompService;
	}
	public void setNoticeService(INoticeService noticeService) {
		this.noticeService = noticeService;
	}
}
