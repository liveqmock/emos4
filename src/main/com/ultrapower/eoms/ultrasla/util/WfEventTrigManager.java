package com.ultrapower.eoms.ultrasla.util;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Element;

import com.ultrapower.eoms.common.core.component.xml.XmlParser;
import com.ultrapower.eoms.common.core.util.StringUtils;

/**
 * @author RenChenglin
 * @date 2011-11-23 下午12:27:17
 * @version
 */
public class WfEventTrigManager {
	public static TrigStepConfig trigStepConfig; //环节触发配置
	public static TrigFormConfig trigFormConfig; //工单触发配置
	
	static 
	{
		parseTrigConfig();
	}
	
	/**
	 * 解析流程事件触发规则配置
	 */
	public static void parseTrigConfig()
	{
		URL url = WfEventTrigManager.class.getResource("/workflow-event-trig.xml");
		File configFile = null;
		try {
			configFile = new File(url.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		if(configFile==null)
		{
			return;
		}
		XmlParser parser = new XmlParser(configFile.getAbsolutePath());
		//解析环节”通用“触发配置
		trigStepConfig = parseStepTrig(parser);
		//解析工单触发配置
		trigFormConfig = parseFormTrig(parser);
	}
	
	/**
	 * 获得环节通用触发配置
	 * @return
	 */
	public static List<TrigCouple> getStepCommonTrig()
	{
		List<TrigCouple> tcLst = null;
		if(trigStepConfig!=null)
		{
			Map<String,TrigCouple> trigs = trigStepConfig.getCommonTrigs();
			if(trigs!=null)
			{
				tcLst = new ArrayList<TrigCouple>(trigs.size());
				Iterator<String> it = trigs.keySet().iterator();
				TrigCouple tc;
				while(it.hasNext())
				{
					tc = trigs.get(it.next());
					if(tc!=null)
					{
						tcLst.add(tc);
					}
				}
			}
		}
		return tcLst;
	}
	
	/**
	 * 根据工单类别获得工单的触发规则
	 * @param baseSchema
	 * @return
	 */
	public static List<TrigCouple> getFormTrig(String baseSchema)
	{
		List<TrigCouple> tcLst = null;
		if(baseSchema!=null && trigFormConfig!=null)
		{
			Map<String,TrigCouple> trigs = trigFormConfig.getTrigs();
			Map<String,TrigFormRef> trigFroms = trigFormConfig.getTrigFroms();
			if(trigs!=null && trigFroms!=null)
			{
				TrigFormRef tfr = trigFroms.get(baseSchema);
				if(tfr!=null)
				{
					List<String> trigIds = tfr.getTrigRef();
					int len = 0;
					if(trigIds!=null)
					{
						len = trigIds.size();
						if(len>0)
						{
							tcLst = new ArrayList<TrigCouple>(len);
						}
					}
					String id;
					TrigCouple tc;
					for(int i=0;i<len;i++)
					{
						id = trigIds.get(i);
						if(id!=null)
						{
							tc = trigs.get(id);
							if(tc!=null)
							{
								tcLst.add(tc);
							}
						}
					}
				}
			}
		}
		return tcLst;
	}
	
	/**
	 * 根据通用环节触发规则ID获得触发规则
	 * @param id
	 * @return
	 */
	public static TrigCouple getStepCommonTrig(String id)
	{
		TrigCouple tc = null;
		if(id!=null && trigStepConfig!=null)
		{
			Map<String,TrigCouple> commonTrigs = trigStepConfig.getCommonTrigs();
			if(commonTrigs!=null)
			{
				tc = commonTrigs.get(id);
			}
		}
		return tc;
	}
	
	/**
	 * 根据工单触发规则ID获得触发规则
	 * @param id
	 * @return
	 */
	public static TrigCouple getFormTrigByTrigId(String id)
	{
		TrigCouple tc = null;
		if(id!=null && trigFormConfig!=null)
		{
			Map<String,TrigCouple> trigs = trigFormConfig.getTrigs();
			if(trigs!=null)
			{
				tc = trigs.get(id);
			}
		}
		return tc;
	}
	
	private static TrigStepConfig parseStepTrig(XmlParser parser)
	{
		TrigStepConfig config = null;
		List<Element> stepTrigConfig = parser.getElement("step-common-trig");
		if(stepTrigConfig!=null&&stepTrigConfig.size()>0)
		{
			Element tempE = stepTrigConfig.get(0);
			if(tempE!=null)
			{
				config = new TrigStepConfig();
				Map<String,TrigCouple> commonTrigs = null;
				List<Element> trigs = tempE.getChildren("trig");
				int len = 0;
				if(trigs!=null)
				{
					len = trigs.size();
					if(len>0)
					{
						commonTrigs = new HashMap<String,TrigCouple>(len);
					}
				}
				Element trig;
				for(int i=0;i<len;i++)
				{
					trig = trigs.get(i);
					TrigCouple tc = parseTrigCouple(trig);
					if(tc!=null && !"".equals(tc.getId()))
					{
						commonTrigs.put(tc.getId(), tc);
					}
				}
				config.setCommonTrigs(commonTrigs);
			}
		}
		return config;
	}
	
	private static TrigCouple parseTrigCouple(Element trig)
	{
		TrigCouple tc = null;
		if(trig!=null)
		{
			tc = new TrigCouple();
			tc.setId(StringUtils.checkNullString(trig.getAttributeValue("id")));
			tc.setName(StringUtils.checkNullString(trig.getAttributeValue("name")));
			String timeOffset = StringUtils.checkNullString(trig.getAttributeValue("timeOffset")).toLowerCase();
			if("before".equals(timeOffset))
			{
				tc.setTimeOffset(TrigCouple.TimeOffset.BEFORE);
			}
			else if("after".equals(timeOffset))
			{
				tc.setTimeOffset(TrigCouple.TimeOffset.AFTER);
			}
			String duetimeField = StringUtils.checkNullString(trig.getAttributeValue("duetimeField"));
			tc.setDuetimeField(duetimeField);
			Element trigItemCfg = trig.getChild("trig-new");
			tc.setTrigNew(parseTrigItem(trigItemCfg));
			trigItemCfg = trig.getChild("trig-destroy");
			tc.setTrigDestroy(parseTrigItem(trigItemCfg));
		}
		return tc;
	}
	
	private static TrigItem parseTrigItem(Element trigItemCfg)
	{
		TrigItem item = null;
		if(trigItemCfg!=null)
		{
			item = new TrigItem();
			String eventType = StringUtils.checkNullString(trigItemCfg.getAttributeValue("eventType")).toLowerCase();
			if("form".equals(eventType))
			{
				item.setEventType(TrigItem.EventType.FORM);
			}
			else if("step".equals(eventType))
			{
				item.setEventType(TrigItem.EventType.STEP);
			}
			else if("action".equals(eventType))
			{
				item.setEventType(TrigItem.EventType.ACTION);
			}
			String eventAction = StringUtils.checkNullString(trigItemCfg.getAttributeValue("eventAction")).toLowerCase();
			if("in".equals(eventAction))
			{
				item.setEventAction(TrigItem.EventAction.IN);
			}
			else if("out".equals(eventAction))
			{
				item.setEventAction(TrigItem.EventAction.OUT);
			}
			else if("do".equals(eventAction))
			{
				item.setEventAction(TrigItem.EventAction.DO);
			}
			item.setEventCondition(StringUtils.checkNullString(trigItemCfg.getAttributeValue("eventCondition")));
		}
		return item;
	}
	
	private static TrigFormConfig parseFormTrig(XmlParser parser)
	{
		TrigFormConfig config = null;
		List<Element> formTrigConfig = parser.getElement("form-trig");
		if(formTrigConfig!=null && formTrigConfig.size()>0)
		{
			Element tempE = formTrigConfig.get(0);
			if(tempE!=null)
			{
				config = new TrigFormConfig();
				Map<String,TrigCouple> formTrigs = null;
				List<Element> trigs = tempE.getChildren("trig");
				int len = 0;
				if(trigs!=null)
				{
					len = trigs.size();
					if(len>0)
					{
						formTrigs = new HashMap<String,TrigCouple>(len);
					}
				}
				Element trig;
				for(int i=0;i<len;i++)
				{
					trig = trigs.get(i);
					TrigCouple tc = parseTrigCouple(trig);
					if(tc!=null && !"".equals(tc.getId()))
					{
						formTrigs.put(tc.getId(), tc);
					}
				}
				config.setTrigs(formTrigs);
				
				List<Element> formTrigRefs = tempE.getChildren("form-trig-ref");
				Map<String,TrigFormRef> trigFromRefs = null;
				len = 0;
				if(formTrigRefs!=null)
				{
					len = formTrigRefs.size();
					if(len>0)
					{
						trigFromRefs = new HashMap<String,TrigFormRef>(len);
					}
				}
				Element trigRef;
				for(int i=0;i<len;i++)
				{
					trigRef = formTrigRefs.get(i);
					TrigFormRef tfr = parseTrigFormRef(trigRef);
					if(tfr!=null && !"".equals(tfr.getBaseSchema()))
					{
						trigFromRefs.put(tfr.getBaseSchema(), tfr);
					}
				}
				config.setTrigFroms(trigFromRefs);
			}
		}
		return config;
	}
	
	private static TrigFormRef parseTrigFormRef(Element trigRef)
	{
		TrigFormRef tfr = null;
		if(trigRef!=null)
		{
			tfr = new TrigFormRef();
			tfr.setBaseSchema(StringUtils.checkNullString(trigRef.getAttributeValue("formSchema")));
			tfr.setName(StringUtils.checkNullString(trigRef.getAttributeValue("name")));
			List<Element> refs = trigRef.getChildren("ref-trig");
			List<String> refLst = null;
			int len = 0;
			if(refs!=null)
			{
				len = refs.size();
				if(len>0)
				{
					refLst = new ArrayList<String>(len);
				}
			}
			Element ref;
			String refVal;
			for(int i=0;i<len;i++)
			{
				ref = refs.get(i);
				if(ref!=null)
				{
					refVal = ref.getAttributeValue("value");
					if(!"".equals(StringUtils.checkNullString(refVal)))
					{
						refLst.add(refVal);
					}
				}
			}
			tfr.setTrigRef(refLst);
		}
		return tfr;
	}
	
	public static void main(String[] args)
	{
		System.out.println("解析完毕！");
		System.out.println(trigStepConfig);
		System.out.println(trigFormConfig);
	}
	
}
