package com.ultrapower.eoms.ultrasla.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.ultrasla.model.DuetimeRule;
import com.ultrapower.eoms.ultrasla.model.EventHandlerComp;
import com.ultrapower.eoms.ultrasla.model.EventRule;
import com.ultrapower.eoms.ultrasla.model.SlaDefine;
import com.ultrapower.eoms.ultrasla.service.IDuetimeRuleService;
import com.ultrapower.eoms.ultrasla.service.IEventHandlerCompService;
import com.ultrapower.eoms.ultrasla.service.IEventRuleService;
import com.ultrapower.eoms.ultrasla.service.ISlaDefineService;
import com.ultrapower.eoms.ultrasla.util.SlaDefineUtil;
import com.ultrapower.eoms.ultrasla.util.TrigCouple;
import com.ultrapower.eoms.ultrasla.util.TrigItem;
import com.ultrapower.eoms.ultrasla.util.WfEventTrigManager;
import com.ultrapower.randomutil.Random15;
import com.ultrapower.randomutil.RandomN;
import com.ultrapower.workflow.event.model.EventDefine;
import com.ultrapower.workflow.event.service.EventDefineService;

/**
 * @author RenChenglin
 * @date 2011-11-14 下午03:47:15
 * @version
 */
public class SlaDefineAction extends BaseAction {
	private String ruleXml;
	private String slaXml;
	private String baseSchema;
	private String deledTimeR;
	private String deledEventR;
	private String scope;
	private IEventRuleService eventRuleService;
	private IDuetimeRuleService duetimeRuleService;
	private ISlaDefineService slaDefineService;
	private EventDefineService eventDefineService;
	private IEventHandlerCompService eventHandlerCompService;
	
	/**
	 * 获得事件定义ID
	 * @return
	 */
	private String getEventDefID(String baseSchema
			,String eventType,RandomN random,StringBuffer sb)
	{
		sb.delete(0, sb.length());
		sb.append("WFE-");
		sb.append(StringUtils.checkNullString(baseSchema.replace(":", "_")));
		sb.append("-");
		sb.append(eventType);
		sb.append("-");
		sb.append(random.getRandom(System.currentTimeMillis()));
		return sb.toString();
	}
	
	/**
	 * 进入SLA协议定义框架页面
	 * @return
	 */
	public String slaDefineOuterFrame(){
		return SUCCESS;
	}
	
	/**
	 * 加载SLA协议定义页面
	 * @return
	 */
	public String slaDefine(){
		if(baseSchema==null){
			baseSchema = "WF4:Demo";
		}
		return SUCCESS;
	}
	
	/**
	 * 加载SLA定义基本信息
	 */
	public void loadSlaHead(){
		SlaDefine head = slaDefineService.getBySchema(baseSchema);
		String xml = SlaDefineUtil.constructSlaXml(head);
		renderXML(xml);
	}
	
	/**
	 * 加载事件规则
	 */
	public void loadEventRule(){
		EventRule rule = eventRuleService.getById(id);
		String xml = SlaDefineUtil.constructEventRuleXml(rule);
		renderXML(xml);
	}
	
	/**
	 * 加载时限规则
	 */
	public void loadTimeRule(){
		DuetimeRule rule = duetimeRuleService.get(id);
		String xml = SlaDefineUtil.constructTimeRuleXml(rule);
		renderXML(xml);
	}
	
	/**
	 * 保存事件规则
	 */
	public void saveEventRule(){
		String ruleid = "";
		if(ruleXml!=null)
		{
			EventRule rule = SlaDefineUtil.creEvtruleFromXml(ruleXml,eventRuleService,scope);
			ruleid = StringUtils.checkNullString(eventRuleService.save(rule));
		}
		renderText(ruleid);
	}

	/**
	 * 保存时限过则
	 */
	public void saveTimeRule(){
		String ruleid = "";
		if(ruleXml!=null)
		{
			DuetimeRule rule = SlaDefineUtil.creDuetimeruleFromXml(ruleXml,duetimeRuleService);
			ruleid = StringUtils.checkNullString(duetimeRuleService.save(rule));
		}
		renderText(ruleid);
	}
	
	/**
	 * 获得触发规则
	 */
	public void getTrigRule(){
		StringBuffer trigCon = new StringBuffer();
		trigCon.append("[");
		List<TrigCouple> tcList = null;
		if("form".equals(scope) && !"".equals(StringUtils.checkNullString(baseSchema)))
		{
			tcList = WfEventTrigManager.getFormTrig(baseSchema);
		}
		if("step".equals(scope))
		{
			tcList = WfEventTrigManager.getStepCommonTrig();
		}
		int len = 0;
		if(tcList!=null)
		{
			len = tcList.size();
		}
		TrigCouple tc;
		for(int i=0;i<len;i++)
		{
			tc = tcList.get(i);
			if(i!=0)
			{
				trigCon.append(",");
			}
			trigCon.append("{\"id\":\"");
			trigCon.append(tc.getId());
			trigCon.append("\",\"name\":\"");
			try {
				trigCon.append(URLEncoder.encode(StringUtils.checkNullString(tc.getName()), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				trigCon.delete(0, trigCon.length());
				trigCon.append("[");
				break;
			}
			trigCon.append("\"}");
		}
		trigCon.append("]");
		renderText(trigCon.toString());
	}
	
	/**
	 * 保存SLA定义基本信息及其触发规则关联
	 */
	public void saveSlaHead(){
		String result = "-1";
		if(slaXml!=null)
		{
			String trigGroupId;
			StringBuffer sbf = new StringBuffer();
			RandomN random = new Random15();
			SlaDefine head = SlaDefineUtil.creSlaHeadFromXml(slaXml,slaDefineService);
			head.setBaseschema(baseSchema);//设置SCHEMA
			String headid = StringUtils.checkNullString(head.getPid());//主键ID
			if("".equals(headid))
			{//新建的时候
				
				slaDefineService.deleteBySchema(baseSchema);//新建的时候删除以前历史垃圾SLA定义
				eventDefineService.deleteBySchema(baseSchema,"SLA");//新建的时候删除以前历史垃圾事件定义
				
				//如果工单触发事件ID不为空，则建立工单触发事件
				if(!"".equals(StringUtils.checkNullString(head.getFormeventruleid())))
				{
					//evtRuleIds的格式为“触发规则id1:事件规则id1,触发规则id2:事件规则id2,...”
					String evtRuleIds = StringUtils.checkNullString(head.getFormeventruleid());
					//“触发规则ID”为KEY，“事件规则id1,事件规则id2,...”为VALUE
					Map<String,String> idmap = SlaDefineUtil.splitEventRuleId(evtRuleIds);
					if(idmap!=null)
					{
						Set<String> sets = idmap.keySet();
						Iterator<String> it = sets.iterator();
						while(it.hasNext())
						{
							String trigConId = it.next();
							TrigCouple tc = WfEventTrigManager.getFormTrigByTrigId(trigConId);
							if(tc==null||tc.getTrigDestroy()==null||tc.getTrigNew()==null)
							{
								continue;
							}
							trigGroupId = random.getRandom(System.currentTimeMillis());//新建销毁触发组ID
							//新建工单事件规则
							EventDefine edf_NEW_form = new EventDefine();//工单产生事件
							TrigItem temp = tc.getTrigNew();
							edf_NEW_form.setHandletypegroupid(trigGroupId);
							edf_NEW_form.setBaseschema(baseSchema);
							edf_NEW_form.setEventtype(temp.getEventType()==null?"FORM":temp.getEventType().toString());
							edf_NEW_form.setEventaction(temp.getEventAction()==null?"":temp.getEventAction().toString());
							edf_NEW_form.setEventcondition(temp.getEventCondition());
							edf_NEW_form.setEventdefid(getEventDefID(baseSchema, edf_NEW_form.getEventtype(), random, sbf));
							edf_NEW_form.setEventsubject("FORM");
							edf_NEW_form.setHandletype("NEW");
							edf_NEW_form.setOperationtype("SLA");
							edf_NEW_form.setEventconditionid(trigConId);
							edf_NEW_form.setOperationname(idmap.get(trigConId));
							edf_NEW_form.setDuetimefield(tc.getDuetimeField());
							edf_NEW_form.setCreatetime(System.currentTimeMillis()/1000);
							edf_NEW_form.setUpdatetime(System.currentTimeMillis()/1000);
							
							EventDefine edf_DESTROY_form = new EventDefine();//工单销毁时间
							temp = tc.getTrigDestroy();
							edf_DESTROY_form.setHandletypegroupid(trigGroupId);
							edf_DESTROY_form.setBaseschema(baseSchema);
							edf_DESTROY_form.setEventtype(temp.getEventType()==null?"FORM":temp.getEventType().toString());
							edf_DESTROY_form.setEventaction(temp.getEventAction()==null?"":temp.getEventAction().toString());
							edf_DESTROY_form.setEventcondition(temp.getEventCondition());
							edf_DESTROY_form.setEventdefid(getEventDefID(baseSchema, edf_DESTROY_form.getEventtype(), random, sbf));
							edf_DESTROY_form.setEventtype("FORM");
							edf_DESTROY_form.setEventsubject("FORM");
							edf_DESTROY_form.setHandletype("DESTROY");
							edf_DESTROY_form.setOperationtype("SLA");
							edf_DESTROY_form.setEventconditionid(trigConId);
							edf_DESTROY_form.setOperationname(idmap.get(trigConId));
							edf_DESTROY_form.setDuetimefield(tc.getDuetimeField());
							edf_DESTROY_form.setCreatetime(System.currentTimeMillis()/1000);
							edf_DESTROY_form.setUpdatetime(System.currentTimeMillis()/1000);
							
							//保存到数据库
							eventDefineService.addEventDefine(edf_NEW_form);
							eventDefineService.addEventDefine(edf_DESTROY_form);
						}
					}
				}
				
				//如果环节触发事件ID不为空，则建立环节触发事件
				if(!"".equals(StringUtils.checkNullString(head.getStepeventruleid())))
				{
					//stepEvtIds的格式为“环节1@触发规则id1:事件规则id1,环节2@触发规则id2:事件规则id2,...”
					String stepEvtIds = StringUtils.checkNullString(head.getStepeventruleid());
					//“环节号@触发规则ID”为KEY，“事件规则ID1,事件规则ID2,...”为VALUE
					Map<String,String> idmap = SlaDefineUtil.splitEventRuleId(stepEvtIds);
					//如果解析的环节和触发规则ID的MPA不为空，则构造环节触发事件
					if(idmap!=null)
					{
						Set<String> keys = idmap.keySet();
						if(keys!=null)
						{
							Iterator<String> it = keys.iterator();
							while(it.hasNext()){
								String sbj = it.next();//环节号@触发规则ID
								String[] sbjArr = sbj.split("@");//sbjArr[0]环节号；sbjArr[1]触发规则ID
								if(sbjArr.length!=2)
								{
									continue;
								}
								TrigCouple tc = WfEventTrigManager.getStepCommonTrig(sbjArr[1]);
								if(tc==null||tc.getTrigDestroy()==null||tc.getTrigNew()==null)
								{
									continue;
								}
								trigGroupId = random.getRandom(System.currentTimeMillis());//新建销毁触发组ID
								EventDefine edf_NEW_step = new EventDefine();//环节产生事件
								TrigItem temp = tc.getTrigNew();
								edf_NEW_step.setHandletypegroupid(trigGroupId);
								edf_NEW_step.setBaseschema(baseSchema);
								edf_NEW_step.setEventtype(temp.getEventType()==null?"STEP":temp.getEventType().toString());
								edf_NEW_step.setEventaction(temp.getEventAction()==null?"IN":temp.getEventAction().toString());
								edf_NEW_step.setEventcondition(temp.getEventCondition());
								edf_NEW_step.setEventconditionid(sbjArr[1]);
								edf_NEW_step.setEventdefid(getEventDefID(baseSchema, edf_NEW_step.getEventtype(), random, sbf));
								edf_NEW_step.setEventsubject(sbjArr[0]);
								edf_NEW_step.setHandletype("NEW");
								edf_NEW_step.setOperationtype("SLA");
								edf_NEW_step.setOperationname(idmap.get(sbj));//设置触发规则
								edf_NEW_step.setDuetimefield(tc.getDuetimeField());
								edf_NEW_step.setCreatetime(System.currentTimeMillis()/1000);
								edf_NEW_step.setUpdatetime(System.currentTimeMillis()/1000);
								
								EventDefine edf_DESTROY_step = new EventDefine();//环节销毁事件
								temp = tc.getTrigDestroy();
								edf_DESTROY_step.setHandletypegroupid(trigGroupId);
								edf_DESTROY_step.setBaseschema(baseSchema);
								edf_DESTROY_step.setEventtype(temp.getEventType()==null?"STEP":temp.getEventType().toString());
								edf_DESTROY_step.setEventaction(temp.getEventAction()==null?"OUT":temp.getEventAction().toString());
								edf_DESTROY_step.setEventcondition(temp.getEventCondition());
								edf_DESTROY_step.setEventconditionid(sbjArr[1]);
								edf_DESTROY_step.setEventdefid(getEventDefID(baseSchema, edf_DESTROY_step.getEventtype(), random, sbf));
								edf_DESTROY_step.setEventsubject(sbjArr[0]);
								edf_DESTROY_step.setHandletype("DESTROY");
								edf_DESTROY_step.setOperationtype("SLA");
								edf_DESTROY_step.setOperationname(idmap.get(sbj));
								edf_DESTROY_step.setDuetimefield(tc.getDuetimeField());
								edf_DESTROY_step.setCreatetime(System.currentTimeMillis()/1000);
								edf_DESTROY_step.setUpdatetime(System.currentTimeMillis()/1000);
								
								eventDefineService.addEventDefine(edf_NEW_step);
								eventDefineService.addEventDefine(edf_DESTROY_step);
							}
						}
					}
				}
			}
			else
			{//修改的时候
				
				//如果工单事件规则ID不为空，则修改工单事件定义
				if(!"".equals(StringUtils.checkNullString(head.getFormeventruleid())))
				{
					Map<String,String> idmap = SlaDefineUtil.splitEventRuleId(head.getFormeventruleid());//key为触发规则ID，value为触发规则的ID，多个ID用逗号分隔
					if(idmap!=null)
					{
						//查询出被标记删除掉的工单事件定义，并删除
						List<EventDefine> defInDb = eventDefineService.getByEventSubject(baseSchema, "FORM","SLA");
						if(defInDb!=null&&defInDb.size()>0)
						{
							int tlen = defInDb.size();
							String tvalue;
							EventDefine ted;
							for(int j=0;j<tlen;j++){
								ted = defInDb.get(j);
								tvalue = idmap.get(ted.getEventconditionid());//获得触发规则ID
								if("".equals(StringUtils.checkNullString(tvalue)))
								{
									eventDefineService.deleteById(ted.getPid());
								}
							}
							defInDb = null;
						}
						
						Set<String> sets = idmap.keySet();
						Iterator<String> it = sets.iterator();
						while(it.hasNext())
						{
							String trigConId = it.next();
							TrigCouple tc = WfEventTrigManager.getFormTrigByTrigId(trigConId);
							if(tc==null||tc.getTrigNew()==null||tc.getTrigDestroy()==null)
							{
								continue;
							}
							//修改工单事件规则
							List<EventDefine> edf_NEW_formList = eventDefineService.getFormEventDefine(baseSchema, trigConId, "NEW","SLA");//工单产生事件
							TrigItem temp = tc.getTrigNew();
							trigGroupId = random.getRandom(System.currentTimeMillis());//新建销毁触发组ID
							if(edf_NEW_formList==null)
							{//修改的时候添加的触发规则
								EventDefine edf_NEW_form = new EventDefine();
								edf_NEW_form.setHandletypegroupid(trigGroupId);
								edf_NEW_form.setBaseschema(baseSchema);
								edf_NEW_form.setEventtype(temp.getEventType()==null?"FORM":temp.getEventType().toString());
								edf_NEW_form.setEventaction(temp.getEventAction()==null?"":temp.getEventAction().toString());
								edf_NEW_form.setEventcondition(temp.getEventCondition());
								edf_NEW_form.setEventconditionid(trigConId);
								edf_NEW_form.setEventdefid(getEventDefID(baseSchema, edf_NEW_form.getEventtype(), random, sbf));
								edf_NEW_form.setEventsubject("FORM");
								edf_NEW_form.setHandletype("NEW");
								edf_NEW_form.setOperationtype("SLA");
								edf_NEW_form.setOperationname(idmap.get(trigConId));
								edf_NEW_form.setDuetimefield(tc.getDuetimeField());
								edf_NEW_form.setCreatetime(System.currentTimeMillis()/1000);
								edf_NEW_form.setUpdatetime(System.currentTimeMillis()/1000);
								
								eventDefineService.addEventDefine(edf_NEW_form);
							}
							else
							{
								for(EventDefine edf_NEW_form : edf_NEW_formList)
								{
									edf_NEW_form.setEventtype(temp.getEventType()==null?"FORM":temp.getEventType().toString());
									edf_NEW_form.setEventaction(temp.getEventAction()==null?"":temp.getEventAction().toString());
									edf_NEW_form.setEventcondition(temp.getEventCondition());
									edf_NEW_form.setEventconditionid(trigConId);
									edf_NEW_form.setOperationname(idmap.get(trigConId));
									edf_NEW_form.setDuetimefield(tc.getDuetimeField());
									edf_NEW_form.setUpdatetime(System.currentTimeMillis()/1000);
									
									eventDefineService.saveOrUpdate(edf_NEW_form);
								}
							}
							
							List<EventDefine> edf_DESTROY_formList = eventDefineService.getFormEventDefine(baseSchema, trigConId, "DESTROY","SLA");//工单销毁事件
							temp = tc.getTrigDestroy();
							if(edf_DESTROY_formList==null)
							{
								EventDefine edf_DESTROY_form = new EventDefine();
								edf_DESTROY_form.setHandletypegroupid(trigGroupId);
								edf_DESTROY_form.setBaseschema(baseSchema);
								edf_DESTROY_form.setEventtype(temp.getEventType()==null?"FORM":temp.getEventType().toString());
								edf_DESTROY_form.setEventaction(temp.getEventAction()==null?"":temp.getEventAction().toString());
								edf_DESTROY_form.setEventcondition(temp.getEventCondition());
								edf_DESTROY_form.setEventconditionid(trigConId);
								edf_DESTROY_form.setEventdefid(getEventDefID(baseSchema, edf_DESTROY_form.getEventtype(), random, sbf));
								edf_DESTROY_form.setEventsubject("FORM");
								edf_DESTROY_form.setHandletype("DESTROY");
								edf_DESTROY_form.setOperationtype("SLA");
								edf_DESTROY_form.setOperationname(idmap.get(trigConId));
								edf_DESTROY_form.setDuetimefield(tc.getDuetimeField());
								edf_DESTROY_form.setCreatetime(System.currentTimeMillis()/1000);
								edf_DESTROY_form.setUpdatetime(System.currentTimeMillis()/1000);
								
								eventDefineService.addEventDefine(edf_DESTROY_form);
							}
							else
							{
								for(EventDefine edf_DESTROY_form : edf_DESTROY_formList)
								{
									edf_DESTROY_form.setEventtype(temp.getEventType()==null?"FORM":temp.getEventType().toString());
									edf_DESTROY_form.setEventaction(temp.getEventAction()==null?"":temp.getEventAction().toString());
									edf_DESTROY_form.setEventcondition(temp.getEventCondition());
									edf_DESTROY_form.setEventconditionid(trigConId);
									edf_DESTROY_form.setOperationname(idmap.get(trigConId));
									edf_DESTROY_form.setDuetimefield(tc.getDuetimeField());
									edf_DESTROY_form.setUpdatetime(System.currentTimeMillis()/1000);
									
									eventDefineService.saveOrUpdate(edf_DESTROY_form);
								}
							}
						}
					}
					else
					{
						//如果idmap解析有误，则删除所有工单事件定义
						eventDefineService.delEventDefByEventSubject(baseSchema,"FORM","SLA");
					}
				}
				else
				{
					//如果工单事件ID为空，则删除所有工单事件定义
					eventDefineService.delEventDefByEventSubject(baseSchema,"FORM","SLA");
				}
				
				//如果环节事件规则ID不为空，则修改环节事件定义
				if(!"".equals(StringUtils.checkNullString(head.getStepeventruleid())))
				{
					Map<String,String> idmap = SlaDefineUtil.splitEventRuleId(head.getStepeventruleid());//key为环节号@触发规则ID，value为触发规则的ID，多个ID用逗号分隔
					if(idmap!=null)
					{
						//查询出被标记删除掉的环节事件定义，并删除
//						List<EventDefine> defInDb = eventDefineService.getByEventType(baseSchema, "STEP","SLA");
						List<EventDefine> defInDb = eventDefineService.getStepSubjectEvent(baseSchema, "SLA");
						if(defInDb!=null&&defInDb.size()>0)
						{
							int tlen = defInDb.size();
							String tvalue;
							EventDefine ted;
							for(int j=0;j<tlen;j++){
								ted = defInDb.get(j);
								tvalue = idmap.get(StringUtils.checkNullString(ted.getEventsubject())
										+"@"+StringUtils.checkNullString(ted.getEventconditionid()));
								if("".equals(StringUtils.checkNullString(tvalue)))
								{
									eventDefineService.deleteById(ted.getPid());
								}
							}
							defInDb = null;
						}
						Set<String> keys = idmap.keySet();
						if(keys!=null)
						{
							Iterator<String> it = keys.iterator();
							while(it.hasNext())
							{
								String sbj = it.next();
								String[] sbjArr = sbj.split("@");//sbjArr[0]是环节号，sbjArr[1]是触发规则ID
								if(sbjArr.length!=2)
								{
									continue;
								}
								TrigCouple tc = WfEventTrigManager.getStepCommonTrig(sbjArr[1]);
								if(tc==null||tc.getTrigDestroy()==null||tc.getTrigNew()==null)
								{
									continue;
								}
								
								EventDefine edf_NEW_step = eventDefineService.getStepEventDefine(baseSchema, sbjArr[0], sbjArr[1], "NEW","SLA");//环节产生事件
								TrigItem temp = tc.getTrigNew();
								trigGroupId = random.getRandom(System.currentTimeMillis());//新建销毁触发组ID
								if(edf_NEW_step==null)
								{
									//修改时候新增的
									edf_NEW_step = new EventDefine();
									edf_NEW_step.setHandletypegroupid(trigGroupId);
									edf_NEW_step.setBaseschema(baseSchema);
									edf_NEW_step.setEventtype(temp.getEventType()==null?"STEP":temp.getEventType().toString());
									edf_NEW_step.setEventaction(temp.getEventAction()==null?"":temp.getEventAction().toString());
									edf_NEW_step.setEventcondition(temp.getEventCondition());
									edf_NEW_step.setEventconditionid(sbjArr[1]);
									edf_NEW_step.setEventdefid(getEventDefID(baseSchema, edf_NEW_step.getEventtype(), random, sbf));
									edf_NEW_step.setEventsubject(sbjArr[0]);
									edf_NEW_step.setHandletype("NEW");
									edf_NEW_step.setOperationtype("SLA");
									edf_NEW_step.setOperationname(idmap.get(sbj));
									edf_NEW_step.setDuetimefield(tc.getDuetimeField());
									edf_NEW_step.setUpdatetime(System.currentTimeMillis()/1000);
									edf_NEW_step.setCreatetime(System.currentTimeMillis()/1000);
									eventDefineService.addEventDefine(edf_NEW_step);
								}
								else
								{
									edf_NEW_step.setEventtype(temp.getEventType()==null?"STEP":temp.getEventType().toString());
									edf_NEW_step.setEventaction(temp.getEventAction()==null?"":temp.getEventAction().toString());
									edf_NEW_step.setEventcondition(temp.getEventCondition());
									edf_NEW_step.setOperationname(idmap.get(sbj));
									edf_NEW_step.setDuetimefield(tc.getDuetimeField());
									edf_NEW_step.setUpdatetime(System.currentTimeMillis()/1000);
									eventDefineService.saveOrUpdate(edf_NEW_step);
								}
								
								EventDefine edf_DESTROY_step = eventDefineService.getStepEventDefine(baseSchema, sbjArr[0], sbjArr[1], "DESTROY","SLA");//环节销毁事件
								temp = tc.getTrigDestroy();
								if(edf_DESTROY_step==null)
								{
									//修改的时候新增的
									edf_DESTROY_step = new EventDefine();
									edf_DESTROY_step.setHandletypegroupid(trigGroupId);
									edf_DESTROY_step.setBaseschema(baseSchema);
									edf_DESTROY_step.setEventtype(temp.getEventType()==null?"STEP":temp.getEventType().toString());
									edf_DESTROY_step.setEventaction(temp.getEventAction()==null?"":temp.getEventAction().toString());
									edf_DESTROY_step.setEventcondition(temp.getEventCondition());
									edf_DESTROY_step.setEventconditionid(sbjArr[1]);
									edf_DESTROY_step.setEventdefid(getEventDefID(baseSchema, edf_DESTROY_step.getEventtype(), random, sbf));
									edf_DESTROY_step.setEventsubject(sbjArr[0]);
									edf_DESTROY_step.setHandletype("DESTROY");
									edf_DESTROY_step.setOperationtype("SLA");
									edf_DESTROY_step.setOperationname(idmap.get(sbj));
									edf_DESTROY_step.setDuetimefield(tc.getDuetimeField());
									edf_DESTROY_step.setCreatetime(System.currentTimeMillis()/1000);
									edf_DESTROY_step.setUpdatetime(System.currentTimeMillis()/1000);
									eventDefineService.addEventDefine(edf_DESTROY_step);
								}
								else
								{
									edf_DESTROY_step.setEventtype(temp.getEventType()==null?"STEP":temp.getEventType().toString());
									edf_DESTROY_step.setEventaction(temp.getEventAction()==null?"":temp.getEventAction().toString());
									edf_DESTROY_step.setEventcondition(temp.getEventCondition());
									edf_DESTROY_step.setOperationname(idmap.get(sbj));
									edf_DESTROY_step.setDuetimefield(tc.getDuetimeField());
									edf_DESTROY_step.setUpdatetime(System.currentTimeMillis()/1000);
									eventDefineService.saveOrUpdate(edf_DESTROY_step);
								}
							}
						}
					}
					else
					{
						//如果环节事件ID不能被正确解析为idmap，删除环节事件定义
//						eventDefineService.delEventDefByEventType(baseSchema, "STEP","SLA");
						eventDefineService.delEventDefOfStepSubject(baseSchema, "SLA");
					}
				}
				else
				{
					//若果环节事件ID为空，则删除所有环节事件
//					eventDefineService.delEventDefByEventType(baseSchema, "STEP","SLA");
					eventDefineService.delEventDefOfStepSubject(baseSchema, "SLA");
				}
			}
			//删除标记删除的时限规则
			if(!"".equals(StringUtils.checkNullString(deledTimeR))){
				String[] delid = deledTimeR.split(",");
				int len = delid.length;
				for(int i=0;i<len;i++){
					duetimeRuleService.delete(delid[i]);
				}
			}
			//删除标记删除的事件规则
			if(!"".equals(StringUtils.checkNullString(deledEventR))){
				String[] delid = deledEventR.split(",");
				int len = delid.length;
				for(int i=0;i<len;i++){
					eventRuleService.delete(delid[i]);
				}
			}
			headid = slaDefineService.save(head);
			if(headid!=null){
				result = "1";
			}
		}
		renderText(result);
	}
	
	/**
	 * 读取自定义通知组件
	 */
	public void readSelfNoticeComp(){
		String type = StringUtils.checkNullString(getRequest().getParameter("type"));//组件类型
		List<EventHandlerComp> comp = null;
		if("".equals(type)){
			comp = eventHandlerCompService.getAll();
		}else{
			comp = eventHandlerCompService.getByType(type);
		}
		StringBuffer jsonArr = new StringBuffer();
		jsonArr.append("[");
		if(comp!=null){
			int len = comp.size();
			EventHandlerComp temp;
			for(int i=0;i<len;i++){
				temp = comp.get(i);
				if(i!=0){
					jsonArr.append(",");
				}
				jsonArr.append("{\"pid\":\"");
				jsonArr.append(temp.getPid());
				jsonArr.append("\",\"name\":\"");
				try {
					jsonArr.append(URLEncoder.encode(temp.getComponentname(),"UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					jsonArr.delete(0, jsonArr.length());
					jsonArr.append("[");
					break;
				}
				jsonArr.append("\"}");
			}
		}
		jsonArr.append("]");
		renderText(jsonArr.toString());
	}
	
	/**
	 * 刷新触发规则配置
	 */
	public void refreshTrigConfig(){
		try {
			WfEventTrigManager.parseTrigConfig();
			renderText("1");
		} catch (RuntimeException e) {
			e.printStackTrace();
			renderText("-1");
		}
	}
	
	public String getRuleXml() {
		return ruleXml;
	}

	public void setRuleXml(String ruleXml) {
		this.ruleXml = ruleXml;
	}

	public String getSlaXml() {
		return slaXml;
	}

	public void setSlaXml(String slaXml) {
		this.slaXml = slaXml;
	}

	public String getBaseSchema() {
		return baseSchema;
	}

	public void setBaseSchema(String baseSchema) {
		this.baseSchema = baseSchema;
	}

	public String getDeledTimeR() {
		return deledTimeR;
	}

	public void setDeledTimeR(String deledTimeR) {
		this.deledTimeR = deledTimeR;
	}

	public String getDeledEventR() {
		return deledEventR;
	}

	public void setDeledEventR(String deledEventR) {
		this.deledEventR = deledEventR;
	}
	
	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
	
	public void setEventRuleService(IEventRuleService eventRuleService) {
		this.eventRuleService = eventRuleService;
	}

	public void setDuetimeRuleService(IDuetimeRuleService duetimeRuleService) {
		this.duetimeRuleService = duetimeRuleService;
	}

	public void setSlaDefineService(ISlaDefineService slaDefineService) {
		this.slaDefineService = slaDefineService;
	}

	public void setEventDefineService(EventDefineService eventDefineService) {
		this.eventDefineService = eventDefineService;
	}

	public void setEventHandlerCompService(
			IEventHandlerCompService eventHandlerCompService) {
		this.eventHandlerCompService = eventHandlerCompService;
	}
	
}
