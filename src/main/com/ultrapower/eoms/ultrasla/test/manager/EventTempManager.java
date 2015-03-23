package com.ultrapower.eoms.ultrasla.test.manager;

import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.ultrasla.test.model.EventTemp;
import com.ultrapower.eoms.ultrasla.test.service.IEventTempService;

/**
 * @author RenChenglin
 * @date 2011-11-7 上午10:52:26
 * @version
 */
public class EventTempManager implements IEventTempService {
	private IDao<EventTemp> eventTempDao;
	
	public int delete(String pid) {
		if(pid==null){
			return -1;
		}
		eventTempDao.removeById(pid);
		return 1;
	}

	public EventTemp get(String pid) {
		if(pid==null){
			return null;
		}
		return eventTempDao.get(pid);
	}

	public String save(EventTemp eventTemp) {
		if(eventTemp==null){
			return null;
		}
		eventTempDao.saveOrUpdate(eventTemp);
		return eventTemp.getPid();
	}

	public void setEventTempDao(IDao<EventTemp> eventTempDao) {
		this.eventTempDao = eventTempDao;
	}
	
}
