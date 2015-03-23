package com.ultrapower.eoms.ultrasla.manager;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.ultrasla.model.EventHandlerComp;
import com.ultrapower.eoms.ultrasla.service.IEventHandlerCompService;

/**
 * @author RenChenglin
 * @date 2011-10-31 下午04:16:49
 * @version
 */
@Transactional
public class EventHandlerCompManager implements IEventHandlerCompService {
	
	private IDao<EventHandlerComp> eventHandlerCompDao;
	
	public int delete(String id) {
		if(id==null||eventHandlerCompDao==null)
			return -1;
		try {
			eventHandlerCompDao.removeById(id);
			return 1;
		} catch (RuntimeException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public EventHandlerComp get(String id) {
		if(id==null||eventHandlerCompDao==null)
			return null;
		try {
			return eventHandlerCompDao.get(id);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<EventHandlerComp> getAll() {
		try {
			return eventHandlerCompDao.getAll();
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String save(EventHandlerComp comp) {
		if(comp==null||eventHandlerCompDao==null)
			return null;
		try {
			if(comp.getPid()==null||"".equals(comp.getPid()))
				eventHandlerCompDao.save(comp);
			else
				eventHandlerCompDao.saveOrUpdate(comp);
			return comp.getPid();
		} catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		}
	}

	public int update(EventHandlerComp comp) {
		String temp = save(comp);
		if(temp==null)
			return -1;
		else
			return 1;
	}

	public List<EventHandlerComp> getByType(String type) {
		if(type==null||eventHandlerCompDao==null){
			return null;
		}
		String hql = "from EventHandlerComp where componenttype = ?";
		return eventHandlerCompDao.find(hql, type);
	}
	
	public void setEventHandlerCompDao(IDao<EventHandlerComp> eventHandlerCompDao) {
		this.eventHandlerCompDao = eventHandlerCompDao;
	}
}
