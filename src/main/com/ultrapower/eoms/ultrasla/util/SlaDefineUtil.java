package com.ultrapower.eoms.ultrasla.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ultrapower.eoms.common.core.component.xml.XmlParser;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.ultrasla.model.DuetimeRule;
import com.ultrapower.eoms.ultrasla.model.EventRule;
import com.ultrapower.eoms.ultrasla.model.SlaDefine;
import com.ultrapower.eoms.ultrasla.service.IDuetimeRuleService;
import com.ultrapower.eoms.ultrasla.service.IEventRuleService;
import com.ultrapower.eoms.ultrasla.service.ISlaDefineService;

/**
 * @author RenChenglin
 * @date 2011-11-14 下午04:21:42
 * @version
 */
public class SlaDefineUtil {
	
	/**
	 * 构造事件规则
	 * @param ruleXml
	 * @return
	 */
	public static EventRule creEvtruleFromXml(String ruleXml,IEventRuleService ers,String scope)
	{
		if(ruleXml==null||ers==null)
		{
			return null;
		}
		StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		xml.append(ruleXml);
		XmlParser parser = new XmlParser(xml.toString(),-1);
		String ruleid = StringUtils.checkNullString(parser.getElement("id").get(0).getText());
		EventRule rule = null;
		if("".equals(ruleid)){
			//新建的规则
			rule = new EventRule();
			rule.setCreatetime(System.currentTimeMillis()/1000);
			rule.setUpdatetime(System.currentTimeMillis()/1000);
		}else{
			//修改的规则
			rule = ers.getById(ruleid);
			if(rule!=null)
			{
				rule.setUpdatetime(System.currentTimeMillis());
			}
		}
		if(rule!=null){
			rule.setRulename(StringUtils.checkNullString(parser.getElement("name").get(0).getText()));
			rule.setRuletype(StringUtils.checkNullString(parser.getElement("type").get(0).getText()));
			long noticetype = 0;
			String temp = StringUtils.checkNullString(parser.getElement("ovRule").get(0).getText());
			TrigCouple tc = null;
			if("form".equals(scope))
			{
				tc = WfEventTrigManager.getFormTrigByTrigId(temp);
			}
			else if("step".equals(scope))
			{
				tc = WfEventTrigManager.getStepCommonTrig(temp);
			}
			if(tc!=null)
			{
				if(TrigCouple.TimeOffset.BEFORE.equals(tc.getTimeOffset()))
				{
					noticetype = 1;
					rule.setNoticealias(tc.getName());
				}
				else if(TrigCouple.TimeOffset.AFTER.equals(tc.getTimeOffset()))
				{
					noticetype = 2;
					rule.setNoticealias(tc.getName());
				}
			}
			rule.setNoticetype(noticetype);
			temp = StringUtils.checkNullString(parser.getElement("ovtime").get(0).getText());
			rule.setTimespan(Long.parseLong(temp)*60);
			rule.setRuleexpress(StringUtils.checkNullString(parser.getElement("express").get(0).getText()));
			temp = StringUtils.checkNullString(parser.getElement("rulePri").get(0).getText());
			rule.setPriority(Long.parseLong(temp));
			temp = StringUtils.checkNullString(parser.getElement("ntcdft").get(0).getText());
			rule.setIsdefaultnotice(Long.parseLong(temp));
			temp = StringUtils.checkNullString(parser.getElement("dftchktyp").get(0).getText());
			rule.setDefaultchecktype(Long.parseLong(temp));
			rule.setDefaultcheckparam(StringUtils.checkNullString(parser.getElement("dftchkparam").get(0).getText()));
			rule.setNoticehandlerid(StringUtils.checkNullString(parser.getElement("ntcto").get(0).getText()));
			rule.setNoticehandler(StringUtils.checkNullString(parser.getElement("ntc_show").get(0).getText()));
			rule.setDuplicatehandlerid(StringUtils.checkNullString(parser.getElement("dplctto").get(0).getText()));
			rule.setDuplicatehandler(StringUtils.checkNullString(parser.getElement("dplct_show").get(0).getText()));
			rule.setUpgradehandlerid(StringUtils.checkNullString(parser.getElement("upgrdto").get(0).getText()));
			rule.setUpgradehandler(StringUtils.checkNullString(parser.getElement("upgrd_show").get(0).getText()));
			rule.setFixedhandler(StringUtils.checkNullString(parser.getElement("fixedhandler").get(0).getText()));
			rule.setCustnoticecompid(StringUtils.checkNullString(parser.getElement("selfcomp").get(0).getText()));
			temp = StringUtils.checkNullString(parser.getElement("ntctimes").get(0).getText());
			rule.setNoticetimes(Long.parseLong(temp));
			temp = StringUtils.checkNullString(parser.getElement("ntcintval").get(0).getText());
			rule.setNoticeinterval(Long.parseLong(temp)*60);
			rule.setNoticestarttime(StringUtils.checkNullString(parser.getElement("ntcsttime").get(0).getText()));
			rule.setNoticeendtime(StringUtils.checkNullString(parser.getElement("ntcedtime").get(0).getText()));
			temp = StringUtils.checkNullString(parser.getElement("sendaft").get(0).getText());
			rule.setIsnoticeafterwards(Long.parseLong(temp));
			temp = StringUtils.checkNullString(parser.getElement("acttype").get(0).getText());
			rule.setActiontype(Long.parseLong(temp));
			rule.setActionparameter(StringUtils.checkNullString(parser.getElement("actparam").get(0).getText()));
			rule.setCalendartype(StringUtils.checkNullString(parser.getElement("calendartype").get(0).getText()));
			rule.setNoticetopic(StringUtils.checkNullString(parser.getElement("topic").get(0).getText()));
			rule.setNoticecontent(StringUtils.checkNullString(parser.getElement("content").get(0).getText()));
			temp = StringUtils.checkNullString(parser.getElement("paramprotype").get(0).getText());
			rule.setDynamicconchecktype(Long.parseLong(temp));
			rule.setDynamicconcheckparam(StringUtils.checkNullString(parser.getElement("processparam").get(0).getText()));
			temp = StringUtils.checkNullString(parser.getElement("status").get(0).getText());
			rule.setStatus(Long.parseLong(temp));
			temp = StringUtils.checkNullString(parser.getElement("vldsttime").get(0).getText());
			SimpleDateFormat sdf = null;
			if(!"".equals(temp))
			{
				sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date date = sdf.parse(temp);
					rule.setValidstarttime(date.getTime()/1000);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			temp = StringUtils.checkNullString(parser.getElement("vldedtime").get(0).getText());
			if(!"".equals(temp))
			{
				if(sdf==null){
					sdf = new SimpleDateFormat("yyyy-MM-dd");
				}
				try {
					Date date = sdf.parse(temp);
					rule.setValidendtime(date.getTime()/1000);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		return rule;
	}
	
	/**
	 * 构造时限规则
	 * @param ruleXml
	 * @return
	 */
	public static DuetimeRule creDuetimeruleFromXml(String ruleXml, IDuetimeRuleService ers){
		if(ruleXml==null||ers==null)
		{
			return null;
		}
		StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		xml.append(ruleXml);
		XmlParser parser = new XmlParser(xml.toString(),-1);
		String ruleid = StringUtils.checkNullString(parser.getElement("id").get(0).getText());
		DuetimeRule rule = null;
		if("".equals(ruleid)){
			//新建的规则
			rule = new DuetimeRule();
			rule.setCreatetime(System.currentTimeMillis()/1000);
			rule.setUpdatetime(System.currentTimeMillis()/1000);
		}else{
			//修改的规则
			rule = ers.get(ruleid);
			if(rule!=null)
			{
				rule.setUpdatetime(System.currentTimeMillis());
			}
		}
		if(rule!=null){
			rule.setRulename(StringUtils.checkNullString(parser.getElement("name").get(0).getText()));
			rule.setRuletype(StringUtils.checkNullString(parser.getElement("type").get(0).getText()));
			rule.setRuleexpress(StringUtils.checkNullString(parser.getElement("express").get(0).getText()));
			String temp = StringUtils.checkNullString(parser.getElement("rulePri").get(0).getText());
			rule.setPriority(Long.parseLong(temp));
			temp = StringUtils.checkNullString(parser.getElement("acctime").get(0).getText());
			rule.setAccepttime(Long.parseLong(temp)*60);
			temp = StringUtils.checkNullString(parser.getElement("protime").get(0).getText());
			rule.setProcesstime(Long.parseLong(temp)*60);
			temp = StringUtils.checkNullString(parser.getElement("status").get(0).getText());
			rule.setStatus(Long.parseLong(temp));
			temp = StringUtils.checkNullString(parser.getElement("vldsttime").get(0).getText());
			SimpleDateFormat sdf = null;
			if(!"".equals(temp))
			{
				sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date date = sdf.parse(temp);
					rule.setValidstarttime(date.getTime()/1000);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			temp = StringUtils.checkNullString(parser.getElement("vldedtime").get(0).getText());
			if(!"".equals(temp))
			{
				if(sdf==null){
					sdf = new SimpleDateFormat("yyyy-MM-dd");
				}
				try {
					Date date = sdf.parse(temp);
					rule.setValidendtime(date.getTime()/1000);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		return rule;
	}
	
	/**
	 * 创建sla定义
	 * @param slaXml
	 * @return
	 */
	public static SlaDefine creSlaHeadFromXml(String slaXml, ISlaDefineService ers){
		SlaDefine head = null;
		if(slaXml==null||ers==null)
		{
			return null;
		}
		StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		xml.append(slaXml);
		XmlParser parser = new XmlParser(xml.toString(),-1);
		String headid = StringUtils.checkNullString(parser.getElement("id").get(0).getText());
		boolean isnew = "".equals(headid);
		if(isnew)
		{//新建
			head = new SlaDefine();
			head.setCreatetime(System.currentTimeMillis()/1000);
			head.setUpdatetime(System.currentTimeMillis()/1000);
		}else{
			//修改的规则
			head = ers.get(headid);
			if(head!=null)
			{
				head.setUpdatetime(System.currentTimeMillis());
			}
		}
		if(head!=null){
			head.setSlaname(StringUtils.checkNullString(parser.getElement("name").get(0).getText()));
			String temp = StringUtils.checkNullString(parser.getElement("vldsttime").get(0).getText());
			SimpleDateFormat sdf = null;
			if(!"".equals(temp))
			{
				sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date date = sdf.parse(temp);
					head.setValidstarttime(date.getTime()/1000);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			temp = StringUtils.checkNullString(parser.getElement("vldedtime").get(0).getText());
			if(!"".equals(temp))
			{
				if(sdf==null){
					sdf = new SimpleDateFormat("yyyy-MM-dd");
				}
				try {
					Date date = sdf.parse(temp);
					head.setValidendtime(date.getTime()/1000);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			temp = StringUtils.checkNullString(parser.getElement("status").get(0).getText());
			head.setStatus(Long.parseLong(temp));
			head.setRemark(StringUtils.checkNullString(parser.getElement("remark").get(0).getText()));
			head.setFormtimeruleid(StringUtils.checkNullString(parser.getElement("ftrid").get(0).getText()));
			head.setSteptimeruleid(StringUtils.checkNullString(parser.getElement("strid").get(0).getText()));
			head.setFormeventruleid(StringUtils.checkNullString(parser.getElement("ferid").get(0).getText()));
			head.setStepeventruleid(StringUtils.checkNullString(parser.getElement("serid").get(0).getText()));
		}
		return head;
	}
	
	/**
	 * idStr参数的输入形式为“id1:value1,id2:value2,id1:value3”
	 * ，则解析后的的Map格式为[{"id1","value1,value3"},{"id2","value2"}]
	 * @param idStr
	 * @return
	 */
	public static Map<String,String> splitEventRuleId(String idStr){
		if(idStr==null){
			return null;
		}
		Map<String,String> res = null;
		String[] idArr = idStr.split(",");
		int len = idArr.length;
		if(len>0){
			res = new HashMap<String,String>();
			String[] dp_id;
			String temp;
			for(int i=0;i<len;i++){
				dp_id = idArr[i].split(":");
				if(dp_id.length==2){
					temp = res.get(dp_id[0]);
					if(temp==null){
						res.put(dp_id[0], dp_id[1]);
					}else{
						res.put(dp_id[0], temp + "," + dp_id[1]);
					}
				}
			}
		}
		return res;
	}
	
	/**
	 * 构造SLA的XML
	 * @param head
	 * @return
	 */
	public static String constructSlaXml(SlaDefine head){
		StringBuffer xml = new StringBuffer();
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		xml.append("<head>\n");
		if(head!=null){
			xml.append("<id>");
			xml.append(StringUtils.checkNullString(head.getPid()));
			xml.append("</id>\n");
			xml.append("<name>");
			xml.append(StringUtils.checkNullString(head.getSlaname()));
			xml.append("</name>\n");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			xml.append("<vldsttime>");
			if(head.getValidstarttime()!=null){
				xml.append(sdf.format(new Date(head.getValidstarttime()*1000)));
			}
			xml.append("</vldsttime>\n");
			xml.append("<vldedtime>");
			if(head.getValidendtime()!=null){
				xml.append(sdf.format(new Date(head.getValidendtime()*1000)));
			}
			xml.append("</vldedtime>\n");
			xml.append("<status>");
			xml.append(head.getStatus());
			xml.append("</status>\n");
			xml.append("<remark>");
			xml.append(StringUtils.checkNullString(head.getRemark()));
			xml.append("</remark>\n");
			xml.append("<ftrid>");
			xml.append(StringUtils.checkNullString(head.getFormtimeruleid()));
			xml.append("</ftrid>\n");
			xml.append("<strid>");
			xml.append(StringUtils.checkNullString(head.getSteptimeruleid()));
			xml.append("</strid>\n");
			xml.append("<ferid>");
			xml.append(StringUtils.checkNullString(head.getFormeventruleid()));
			xml.append("</ferid>\n");
			xml.append("<serid>");
			xml.append(StringUtils.checkNullString(head.getStepeventruleid()));
			xml.append("</serid>\n");
		}
		xml.append("</head>");
		return xml.toString();
	}
	
	public static String constructTimeRuleXml(DuetimeRule rule){
		StringBuffer xml = new StringBuffer();
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		xml.append("<rule>\n");
		if(rule!=null){
			xml.append("<id>");
			xml.append(StringUtils.checkNullString(rule.getPid()));
			xml.append("</id>\n");
			xml.append("<name>");
			xml.append(StringUtils.checkNullString(rule.getRulename()));
			xml.append("</name>\n");
			xml.append("<type>");
			xml.append(StringUtils.checkNullString(rule.getRuletype()));
			xml.append("</type>\n");
			xml.append("<express>");
			xml.append(StringUtils.checkNullString(rule.getRuleexpress()));
			xml.append("</express>\n");
			xml.append("<rulePri>");
			xml.append(rule.getPriority());
			xml.append("</rulePri>\n");
			xml.append("<acctime>");
			xml.append(rule.getAccepttime()/60);
			xml.append("</acctime>\n");
			xml.append("<protime>");
			xml.append(rule.getProcesstime()/60);
			xml.append("</protime>\n");
		}
		xml.append("</rule>");
		return xml.toString();
	}
	
	public static String constructEventRuleXml(EventRule rule){
		StringBuffer xml = new StringBuffer();
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		xml.append("<rule>\n");
		if(rule!=null){
			xml.append("<id>");
			xml.append(StringUtils.checkNullString(rule.getPid()));
			xml.append("</id>\n");
			xml.append("<name>");
			xml.append(StringUtils.checkNullString(rule.getRulename()));
			xml.append("</name>\n");
			xml.append("<type>");
			xml.append(StringUtils.checkNullString(rule.getRuletype()));
			xml.append("</type>\n");
			xml.append("<ovRule>");
			xml.append(StringUtils.checkNullString(rule.getNoticealias()));
			xml.append("</ovRule>\n");
			xml.append("<ovtime>");
			xml.append(rule.getTimespan()/60);
			xml.append("</ovtime>\n");
			xml.append("<express>");
			xml.append(StringUtils.checkNullString(rule.getRuleexpress()));
			xml.append("</express>\n");
			xml.append("<rulePri>");
			xml.append(rule.getPriority());
			xml.append("</rulePri>\n");
			xml.append("<ntcdft>");
			xml.append(rule.getIsdefaultnotice());
			xml.append("</ntcdft>\n");
			xml.append("<dftchktyp>");
			xml.append(StringUtils.checkNullString(rule.getDefaultchecktype()));
			xml.append("</dftchktyp>\n");
			xml.append("<dftchkparam>");
			xml.append(StringUtils.checkNullString(rule.getDefaultcheckparam()));
			xml.append("</dftchkparam>\n");
			xml.append("<ntc_show>");
			xml.append(StringUtils.checkNullString(rule.getNoticehandler()));
			xml.append("</ntc_show>\n");
			xml.append("<ntcto>");
			xml.append(StringUtils.checkNullString(rule.getNoticehandlerid()));
			xml.append("</ntcto>\n");
			xml.append("<dplct_show>");
			xml.append(StringUtils.checkNullString(rule.getDuplicatehandler()));
			xml.append("</dplct_show>\n");
			xml.append("<dplctto>");
			xml.append(StringUtils.checkNullString(rule.getDuplicatehandlerid()));
			xml.append("</dplctto>\n");
			xml.append("<upgrd_show>");
			xml.append(StringUtils.checkNullString(rule.getUpgradehandler()));
			xml.append("</upgrd_show>\n");
			xml.append("<upgrdto>");
			xml.append(StringUtils.checkNullString(rule.getUpgradehandlerid()));
			xml.append("</upgrdto>\n");
			xml.append("<fixedhandler>");
			xml.append(StringUtils.checkNullString(rule.getFixedhandler()));
			xml.append("</fixedhandler>\n");
			xml.append("<selfcomp>");
			xml.append(StringUtils.checkNullString(rule.getCustnoticecompid()));
			xml.append("</selfcomp>\n");
			xml.append("<acttype>");
			xml.append(rule.getActiontype());
			xml.append("</acttype>\n");
			xml.append("<actparam>");
			xml.append(StringUtils.checkNullString(rule.getActionparameter()));
			xml.append("</actparam>\n");
			xml.append("<calendartype>");
			xml.append(StringUtils.checkNullString(rule.getCalendartype()));
			xml.append("</calendartype>");
			xml.append("<ntctimes>");
			xml.append(rule.getNoticetimes());
			xml.append("</ntctimes>\n");
			xml.append("<ntcintval>");
			xml.append(rule.getNoticeinterval()/60);
			xml.append("</ntcintval>\n");
			xml.append("<ntcsttime>");
			xml.append(StringUtils.checkNullString(rule.getNoticestarttime()));
			xml.append("</ntcsttime>\n");
			xml.append("<ntcedtime>");
			xml.append(StringUtils.checkNullString(rule.getNoticeendtime()));
			xml.append("</ntcedtime>\n");
			xml.append("<sendaft>");
			xml.append(rule.getIsnoticeafterwards());
			xml.append("</sendaft>\n");
			xml.append("<paramprotype>");
			xml.append(rule.getDynamicconchecktype());
			xml.append("</paramprotype>\n");
			xml.append("<processparam>");
			xml.append(StringUtils.checkNullString(rule.getDynamicconcheckparam()));
			xml.append("</processparam>\n");
			xml.append("<topic>");
			xml.append(StringUtils.checkNullString(rule.getNoticetopic()));
			xml.append("</topic>\n");
			xml.append("<content>");
			xml.append(StringUtils.checkNullString(rule.getNoticecontent()));
			xml.append("</content>\n");
		}
		xml.append("</rule>");
		return xml.toString();
	}
}
