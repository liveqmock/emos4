package com.ultrapower.eoms.workflow.event.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.ultrasla.model.EventRule;
import com.ultrapower.eoms.ultrasla.service.IEventRuleService;
import com.ultrapower.randomutil.Random15;
import com.ultrapower.randomutil.RandomN;
import com.ultrapower.workflow.event.model.EventDefine;
import com.ultrapower.workflow.event.service.EventDefineService;

public class EventDefineAction extends BaseAction {

	private EventDefine eventDefine;
	private IEventRuleService eventRuleService;
	private EventDefineService eventDefineService;
	private String delIds;
	private List<EventDefine> eventDefines;

	public String eventDefineList() {
		return SUCCESS;
	}

	public String addEventDefine() {
		return SUCCESS;
	}

	public String editEventDefine() {
		String edid = this.getRequest().getParameter("edid");
		eventDefine = eventDefineService.getByPId(edid);
		if(eventDefine != null) {
			String operationtype = eventDefine.getOperationtype();
			String operationname = eventDefine.getOperationname();
			if(operationtype != null && !"".equals(operationtype)) {
				if("SLA".equals(operationtype)) {
					operationname = "";
					String operationnameid = eventDefine.getOperationname();
					String[] ruleIdArray = operationnameid.split(",");
					EventRule eventRule;
					String ruleName;
					for(int i=0 ; i<ruleIdArray.length ; i++) {
						eventRule = eventRuleService.getById(ruleIdArray[i]);
						if(eventRule != null) {
							ruleName = eventRule.getRulename();
							if(ruleName!=null){
								operationname += "," + ruleName;
							}
						}
					}
					if(!"".equals(operationname))
						operationname = operationname.substring(1);
					this.getRequest().setAttribute("operationnameid", operationnameid);
				} else {
					eventDefine.setOperationname("");
				}
				this.getRequest().setAttribute("operationname", operationname);
			}
		}
		return this.findForward("addEventDefine");
	}

	public String delEventDefine() {
		if (StringUtils.isNotBlank(delIds)) {
			String[] ids = delIds.split(",");
			if (!ArrayUtils.isEmpty(ids)) {
				for (int i = 0; i < ids.length; i++) {
					String edid = ids[i];
					if (StringUtils.isNotBlank(edid)) {
						eventDefineService.deleteById(edid);
					}
				}
			}
		}
		return this.findForward("eventDefineList");
	}

	public String saveEventDefine() {
		if (eventDefine != null) {
			eventDefine.setUpdatetime(TimeUtils.getCurrentTime());
			if (eventDefine.getPid() == null || "".equals(eventDefine.getPid())) {
				eventDefine.setCreatetime(TimeUtils.getCurrentTime());
				RandomN random = new Random15();
				String sjid = random.getRandom(System.currentTimeMillis());
				String baseschema = eventDefine.getBaseschema().replace(":", "_");
				eventDefine.setEventdefid("WFE-"+baseschema+"-"+eventDefine.getEventtype()+"-"+sjid);
				eventDefineService.addEventDefine(eventDefine);
			} else {
				eventDefineService.saveOrUpdate(eventDefine);
			}
		}
		
		this.getRequest().setAttribute("parafresh", true);
		this.getRequest().setAttribute("returnMessage", "保存成功!");
		return "refresh";
	}

	// 选择规则
	public String selectEventRule() {
		String type = this.getRequest().getParameter("ruleType");
		if (type != null) {
			Map valueMap = new HashMap();
			valueMap.put("ruletype", type);
			this.getRequest().setAttribute("valuemap", valueMap);
		}
		return SUCCESS;
	}

	public EventDefine getEventDefine() {
		return eventDefine;
	}
	public void setEventDefine(EventDefine eventDefine) {
		this.eventDefine = eventDefine;
	}
	public EventDefineService getEventDefineService() {
		return eventDefineService;
	}
	public void setEventDefineService(EventDefineService eventDefineService) {
		this.eventDefineService = eventDefineService;
	}
	public String getDelIds() {
		return delIds;
	}
	public void setDelIds(String delIds) {
		this.delIds = delIds;
	}
	public List<EventDefine> getEventDefines() {
		return eventDefines;
	}
	public void setEventDefines(List<EventDefine> eventDefines) {
		this.eventDefines = eventDefines;
	}
	public void setEventRuleService(IEventRuleService eventRuleService) {
		this.eventRuleService = eventRuleService;
	}
}
