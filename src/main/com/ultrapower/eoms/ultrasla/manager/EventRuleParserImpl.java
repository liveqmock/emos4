package com.ultrapower.eoms.ultrasla.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.ultrasla.exception.UnInitializationException;
import com.ultrapower.eoms.ultrasla.model.EventAction;
import com.ultrapower.eoms.ultrasla.model.EventParam;
import com.ultrapower.eoms.ultrasla.model.EventRule;
import com.ultrapower.eoms.ultrasla.service.ICustomParamService;
import com.ultrapower.eoms.ultrasla.service.IEventActionService;
import com.ultrapower.eoms.ultrasla.service.IEventParamService;
import com.ultrapower.eoms.ultrasla.service.IEventRuleParser;
import com.ultrapower.eoms.ultrasla.service.IEventRuleService;
import com.ultrapower.eoms.ultrasla.util.ConstantMark;
import com.ultrapower.eoms.ultrasla.util.ParamResolve;
import com.ultrapower.eoms.ultrasla.util.UltraSLAEvent;
import com.ultrapower.eoms.ultrasla.util.UltraSlaUtil;
import com.ultrapower.eoms.ultrasla.util.ConstantMark.EventType;

public class EventRuleParserImpl implements IEventRuleParser {
	private IEventActionService eventActionService;
	private IEventRuleService eventRuleService;
	private IEventParamService eventParamService;
	
	public EventRuleParserImpl() {
		
	}

	public void parse(UltraSLAEvent slaEvent) {
		if(slaEvent==null)
			return;
		EventType eventType = slaEvent.getEventType();
		if(eventType==null)
			return;
		switch(eventType){
		case PRODUCE:
			try {
					produceAction(slaEvent);
				} catch (UnInitializationException e1) {
					e1.printStackTrace();
				}
			break;
		case DESTROY:
			try {
					destoryAction(slaEvent);
				} catch (UnInitializationException e) {
					e.printStackTrace();
				}
			break;
		default:;
		}
	}

	public void saveToEventAction(EventAction eventAction) throws UnInitializationException{
		if(eventAction==null)
			return;
		if(eventActionService==null)
			throw new UnInitializationException("未初始化异常：'eventActionService' in com.ultrapower.eoms.ultrasla.manager.EventRuleParserImpl");
		eventActionService.save(eventAction);
	}
	
	public void saveToEventParam(EventParam eventParam) throws UnInitializationException{
		if(eventParam==null)
			return;
		if(eventParamService==null)
			throw new UnInitializationException("未初始化异常：'eventParamService' in com.ultrapower.eoms.ultrasla.manager.EventRuleParserImpl");
		eventParamService.save(eventParam);
	}
	
	//产生动作
	private void produceAction(UltraSLAEvent slaEvent) throws UnInitializationException{
		if(eventRuleService==null)
			throw new UnInitializationException("未初始化异常：'eventRuleService' in com.ultrapower.eoms.ultrasla.manager.EventRuleParserImpl");
		List<String> ridlst= slaEvent.getRuleIdLst();
		int len = 0;
		if(ridlst!=null)
			len = ridlst.size();
		if(len<=0)
			return;
		long currentTime = System.currentTimeMillis()/1000;
		long checktype = -1;
		String checkparam = null;
		String checkkey;
		Map primitiveParam = slaEvent.getParams();//取得原始传递的参数
		boolean paramChk = primitiveParam==null?false:true;
		Map dynamicParamMap = null;
		Map<String,Map> cacheParam = new HashMap<String,Map>(len);
		for(int i=0;i<len;i++){
			String rid = ridlst.get(i);
			if(rid==null){
				continue;
			}
			EventRule rule = eventRuleService.getById(rid);
			if(rule==null									  //没有该rule
					||rule.getStatus()==0                     //该rule的状态为停用
					||currentTime<rule.getValidstarttime()    //该rule不在有效期内
					||currentTime>rule.getValidendtime())     //该rule不在有效期内
			{
				continue;
			}
			
			if(paramChk)	//当原始参数不为空的时候进行保存
			{
				checktype = rule.getDynamicconchecktype()==null?-1:rule.getDynamicconchecktype().longValue();
				checkparam = rule.getDynamicconcheckparam();
				if((checktype==1 || checktype==2) && checkparam!=null && (primitiveParam instanceof HashMap))
				{
					checkkey = checkparam + "@" + checktype;
					if(cacheParam.containsKey(checkkey))
					{
						dynamicParamMap = cacheParam.get(checkkey);
					}
					else
					{
						dynamicParamMap = getDynamicParamMap(checktype, checkparam
								, (HashMap<String,String>)primitiveParam); // 获取动态参数Map
						cacheParam.put(checkkey, dynamicParamMap);//缓存参数数据
					}
				}
				else
				{
					dynamicParamMap = primitiveParam;
				}
			}
			else
			{
				dynamicParamMap = primitiveParam;
			}
			
			if(eventRuleService.checkExpress(dynamicParamMap, rule.getRuleexpress()))
			{
				EventAction action = createEventAction(rule,slaEvent);
				if(action!=null)
				{
					saveToEventAction(action);
					if(dynamicParamMap!=null)
					{
						String actionparamxml = ParamResolve.mapThansToXml(dynamicParamMap);       //获得xml文件
						EventParam eventParam  =  new EventParam();
						eventParam.setActionid(action.getPid());
						eventParam.setEventid(slaEvent.getEventId());
						eventParam.setActionparamxml(actionparamxml);
						eventParam.setCreatetime(TimeUtils.getCurrentTime());
						saveToEventParam(eventParam);
					}
				}
			}
		}
		cacheParam = null;
	}
	
	//销毁动作
	private void destoryAction(UltraSLAEvent slaEvent) throws UnInitializationException {
		String eventId = slaEvent.getEventId();
		if(eventId==null)
			return;
		if(eventActionService==null)
			throw new UnInitializationException("未初始化异常：'eventActionService' in com.ultrapower.eoms.ultrasla.manager.EventRuleParserImpl");
		if(eventParamService==null)
			throw new UnInitializationException("未初始化异常：'eventParamService' in com.ultrapower.eoms.ultrasla.manager.EventRuleParserImpl");
		//删除事件动作
		eventActionService.updateStatusByEventId(eventId, 0);
		//删除事件动作参数
		eventParamService.deleteByEventId(eventId);
	}
	
	//创建EventAction对象引用
	private EventAction createEventAction(EventRule rule,UltraSLAEvent slaEvent){
		EventAction action = null;
		action = new EventAction();
		action.setEventid(slaEvent.getEventId());
		action.setRuleid(rule.getPid());
		action.setDuetime(slaEvent.getDuetime());
		long noticeType = rule.getNoticetype();
		long timeSpan = rule.getTimespan();
		if(noticeType==ConstantMark.ACTION_NOTICE_TYPE_FORKNOWN){
			action.setNoticetime(slaEvent.getDuetime() - timeSpan);
			action.setNextnoticetime(slaEvent.getDuetime() - timeSpan);
		}else if(noticeType==ConstantMark.ACTION_NOTICE_TYPE_AFTERKNOWN){
			action.setNoticetime(slaEvent.getDuetime() + timeSpan);
			action.setNextnoticetime(slaEvent.getDuetime() + timeSpan);
		}
		action.setNoticedtimes(0L);
		action.setStatus(1L);
		action.setActionstatus(1L);
		action.setDefaulthanlderid(createDefaultHandler(slaEvent.getDefaultUser()
				,slaEvent.getDefaultGroup(),slaEvent.getDefaultRole()));
		action.setCreatetime(TimeUtils.getCurrentTime());
		return action;
	}
	
	//构造当前处理人拼接串，形如 U:id1,id2;D:id1,id2;R:id1,id2
	private String createDefaultHandler(List<String> defU,List<String> defD,List<String> defR){
		if(defU==null&&defD==null&&defR==null)
			return null;
		StringBuffer idbf = new StringBuffer();
		int len = 0;
		if(defU!=null){
			len = defU.size();
			if(len>0)
				idbf.append("U:");
		}
		for(int i=0;i<len;i++){
			String id = defU.get(i);
			if(id==null||"".equals(id))
				continue;
			if(i>0)
				idbf.append(",");
			idbf.append(id);
			
		}
		len = 0;
		int bflen = idbf.length();
		if(defD!=null){
			len = defD.size();
			if(len>0){
				if(bflen>0)
					idbf.append(";");
				idbf.append("D:");
			}
		}
		for(int i=0;i<len;i++){
			String id = defD.get(i);
			if(id==null||"".equals(id))
				continue;
			if(i>0)
				idbf.append(",");
			idbf.append(id);
		}
		len = 0;
		bflen = idbf.length();
		if(defR!=null){
			len = defR.size();
			if(len>0){
				if(bflen>0)
					idbf.append(";");
				idbf.append("R:");
			}
		}
		for(int i=0;i<len;i++){
			String id = defR.get(i);
			if(id==null||"".equals(id))
				continue;
			if(i>0)
				idbf.append(",");
			idbf.append(id);
		}
		return idbf.toString();
	}
	
	/**
	 * 获取动态参数Map
	 * @param checktype 参数获取类型
	 * @param checkparam 参数获取值
	 * @param paramMap 参数Map
	 * @return
	 */
	private HashMap<String, String> getDynamicParamMap(long checktype, String checkparam, final HashMap<String, String> paramMap) {
		HashMap<String, String> dynamicParamMap = null;
		if(!"".equals(StringUtils.checkNullString(checkparam))) { // 当配置的参数不为空时才按照正常方式去获取
			if(checktype == 1) { // SQL方式获取参数
				ParamValSqlImpl paramValSqlImpl = new ParamValSqlImpl();
				dynamicParamMap = paramValSqlImpl.getParamValFromSql(paramMap, checkparam);
			} else if (checktype == 2) { // 实现类方式获取参数
				ICustomParamService customParamService = (ICustomParamService) WebApplicationManager.getBean(checkparam); // 获取实例customParamService
				if(customParamService == null) { // 如果没在spring中配置，则取文件
					customParamService = (ICustomParamService) UltraSlaUtil.getClassInstance(checkparam);
				}
				if(customParamService != null) {
					dynamicParamMap = customParamService.getParamValFromImpl(paramMap);
				}
			}
		}
		if(dynamicParamMap == null) { // 如果dynamicParamMap为空则直接赋值为paramMap
			dynamicParamMap = paramMap;
		} else { // 否则合并两个Map 并且以paramMap为主
			Set keys = paramMap.keySet();
			Object[] keyObj = keys.toArray();
			for(int i=0;i<keyObj.length;i++) {
				dynamicParamMap.put((String) keyObj[i], paramMap.get(keyObj[i]));
			}
		}
		return dynamicParamMap;
	}
	
	//初始化eventActionService
	public void setEventActionService(IEventActionService eventActionService) {
		this.eventActionService = eventActionService;
	}

	//初始化eventRuleService
	public void setEventRuleService(IEventRuleService eventRuleService) {
		this.eventRuleService = eventRuleService;
	}
	
	//初始化eventParamService
	public void setEventParamService(IEventParamService eventParamService) {
		this.eventParamService = eventParamService;
	}

	public static void main(String[] args){
		List<String> u = new ArrayList<String>();
		u.add("001");
		u.add("002");
		List<String> d = new ArrayList<String>();
		d.add("003");
		d.add("004");
		List<String> r = new ArrayList<String>();
		r.add("005");
		r.add("006");
		EventRuleParserImpl imp = new EventRuleParserImpl();
		System.out.println(imp.createDefaultHandler(u, null, null));
	}
	
}
