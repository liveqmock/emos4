package com.ultrapower.eoms.ultrasla.manager;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.ultrasla.model.EventAction;
import com.ultrapower.eoms.ultrasla.service.IEventActionService;
import com.ultrapower.eoms.ultrasla.util.ConstantMark;

@Transactional
public class EventActionManager implements IEventActionService {
	private IDao<EventAction> eventActionDao;

	public EventActionManager() {

	}

	public String save(EventAction eventAction) {
		if(eventAction==null||eventActionDao==null)
			return null;
		try {
			if(eventAction.getPid()==null||"".equals(eventAction.getPid()))
				eventActionDao.save(eventAction);
			else
				eventActionDao.saveOrUpdate(eventAction);
			return eventAction.getPid();
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		}
	}

	public int delete(String eventId) {
		if(eventId==null||eventActionDao==null)
			return -1;
		try {
			String hql = "delete EventAction where eventid=?";
			return eventActionDao.executeUpdate(hql, eventId);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public EventAction getValueMatched(int pageNum, int pageSize) {
		return null;
	}

	public int updateStatusByEventId(String eventId, long status) {
		if(eventId==null)
			return -1;
		String hql = "update EventAction set status = ? where eventid = ?";
		try {
			return eventActionDao.executeUpdate(hql, status, eventId);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public int updateStatusById(String id, long status) {
		if(id==null)
			return -1;
		String hql = "update EventAction set status = ? where pid = ?";
		try {
			return eventActionDao.executeUpdate(hql, status, id);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public List<EventAction> getTimeLimitEventAction(long startlimit, long endlimit) {
		List<EventAction> eventActionList = null;
		try {
			eventActionList = eventActionDao.find("from EventAction where status = ? and (actionstatus = ? or actionstatus = ?) and nextnoticetime > ? and nextnoticetime < ? order by ruleid, nextnoticetime, actionstatus",
							new Object[] {ConstantMark.EVENT_ACTION_STATUS_NEW, ConstantMark.EVENT_ACTION_STATUS_ACTIVE, ConstantMark.EVENT_ACTION_STATUS_REACTIVE, startlimit, endlimit});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return eventActionList;
	}
	
	public void setEventActionDao(IDao<EventAction> eventActionDao) {
		this.eventActionDao = eventActionDao;
	}
}
