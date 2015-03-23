package com.ultrapower.eoms.ultrasla.web;

import com.bmc.thirdparty.org.apache.commons.lang.ArrayUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.ultrasla.model.EventHandlerComp;
import com.ultrapower.eoms.ultrasla.service.IEventHandlerCompService;
import org.apache.commons.lang.StringUtils;

public class EventHandlerCompAction extends BaseAction {

	private EventHandlerComp eventHandlerComp;
	private IEventHandlerCompService eventHandlerCompService;
	private String delIds;
	
	public String eventHandlerCompList(){		
		
		return SUCCESS;
	}
	public String selEventHandlerComp(){		
		
		return SUCCESS;
	}
	public String addEventHandlerComp(){				
		
		return  findForward("eventHandlerCompSave");
	}
	public String saveEventHandlerComp(){
		if(eventHandlerComp!=null){			
			eventHandlerComp.setUpdatetime(TimeUtils.getCurrentTime());
			if("".equals(eventHandlerComp.getPid())||eventHandlerComp.getPid()==null){
				eventHandlerComp.setCreatetime(TimeUtils.getCurrentTime());
			}
		}		
	    String flag = eventHandlerCompService.save(eventHandlerComp);
	    this.getRequest().setAttribute("parafresh", true);
	    if(flag==null){
	    	this.getRequest().setAttribute("returnMessage", "保存失败!");	    	
	    }else{
	    	this.getRequest().setAttribute("returnMessage", "保存成功!");	    	
	    }
		return "refresh";
	}
	public String delEventHandlerComp(){
		if(StringUtils.isNotBlank(delIds)){
			String[] ids = delIds.split(",");
			if(!ArrayUtils.isEmpty(ids)){
				for(int i = 0; i < ids.length; i++){
					String edid = ids[i];
					if (StringUtils.isNotBlank(edid)) {
						eventHandlerCompService.delete(edid);
				}
			}
		}
		}
		return this.findForward("eventHandlerCompList");
		}
	public String editEventHandlerComp(){
		String id = this.getRequest().getParameter("id");
		eventHandlerComp = eventHandlerCompService.get(id);		
		return this.findForward("eventHandlerCompSave");		
	}
	public EventHandlerComp getEventHandlerComp() {
		return eventHandlerComp;
	}
	public void setEventHandlerComp(EventHandlerComp eventHandlerComp) {
		this.eventHandlerComp = eventHandlerComp;
	}
	public IEventHandlerCompService getEventHandlerCompService() {
		return eventHandlerCompService;
	}
	public void setEventHandlerCompService(IEventHandlerCompService eventHandlerCompService) {
		this.eventHandlerCompService = eventHandlerCompService;
	}
	public String getDelIds() {
		return delIds;
	}
	public void setDelIds(String delIds) {
		this.delIds = delIds;
	}
}
