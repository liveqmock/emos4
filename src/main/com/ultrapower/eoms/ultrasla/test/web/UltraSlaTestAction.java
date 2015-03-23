package com.ultrapower.eoms.ultrasla.test.web;

import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.util.UUIDGenerator;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.ultrasla.test.model.EventTemp;
import com.ultrapower.eoms.ultrasla.test.service.IEventTempService;

/**
 * @author RenChenglin
 * @date 2011-11-7 上午11:00:55
 * @version
 */
public class UltraSlaTestAction extends BaseAction {
	private IEventTempService eventTempService;
	private EventTemp eventTemp;

	public String eventTempList(){
		
		return this.findForward("addEventTemp");
	}
	
	public String addEventTemp(){
		if(eventTemp!=null){
			long currentTime = TimeUtils.getCurrentTime();
			eventTemp.setEventid(UUIDGenerator.getUUIDoffSpace());//设置事件ID
			eventTemp.setCreatetime(currentTime);
			eventTemp.setUpdatetime(currentTime);
			eventTempService.save(eventTemp);
			this.getRequest().setAttribute("parafresh", "true");
			this.getRequest().setAttribute("returnMessage", "保存成功！");
		}
		return this.findRedirect("eventTempList");
	}
	
	public void setEventTempService(IEventTempService eventTempService) {
		this.eventTempService = eventTempService;
	}

	public EventTemp getEventTemp() {
		return eventTemp;
	}

	public void setEventTemp(EventTemp eventTemp) {
		this.eventTemp = eventTemp;
	}
	
}
