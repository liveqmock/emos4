package com.ultrapower.eoms.ultrasla.manager;

import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.ultrasla.model.EventParam;
import com.ultrapower.eoms.ultrasla.service.IEventParamService;

/**
 * @author RenChenglin
 * @date 2011-10-31 下午04:25:28
 * @version
 */
@Transactional
public class EventParamManager implements IEventParamService {

	private IDao<EventParam> eventParamDao;
	
	public int delete(String id) {
		if(id==null||eventParamDao==null)
			return -1;
		try {
			eventParamDao.removeById(id);
			return 1;
		} catch (RuntimeException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public int deleteByEventId(String eventId) {
		if(eventId==null||eventParamDao==null)
			return -1;
		try {
			return eventParamDao.executeUpdate("delete EventParam where eventid=?", eventId);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public EventParam get(String id) {
		if(id==null||eventParamDao==null)
			return null;
		try {
			return eventParamDao.get(id);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		}
	}

	public EventParam getByEventId(String eventId) {
		if(eventId==null||eventParamDao==null)
			return null;
		String hql = "from EventParam where eventid=?";
		try {
			return eventParamDao.findUnique(hql, eventId);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		}
	}

	public EventParam getByActionId(String actionId)
	{
		if(actionId==null||eventParamDao==null)
			return null;
		String hql = "from EventParam where actionid=?";
		try {
			return eventParamDao.findUnique(hql, actionId);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String save(EventParam eventParam) {
		if(eventParam==null||eventParamDao==null)
			return null;
		try {
			if(eventParam.getPid()==null||"".equals(eventParam.getPid()))
				eventParamDao.save(eventParam);
			else
				eventParamDao.saveOrUpdate(eventParam);
			return eventParam.getPid();
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		}
	}

	public int update(EventParam eventParam) {
		String temp = save(eventParam);
		if(temp==null)
			return -1;
		else
			return 1;
	}

	public void setEventParamDao(IDao<EventParam> eventParamDao) {
		this.eventParamDao = eventParamDao;
	}

}
