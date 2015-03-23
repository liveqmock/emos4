package com.ultrapower.eoms.ultrasla.test.service;

import com.ultrapower.eoms.ultrasla.test.model.EventTemp;

/**
 * @author RenChenglin
 * @date 2011-11-7 上午10:52:03
 * @version
 */
public interface IEventTempService {
	
	public String save(EventTemp eventTemp);
	
	public int delete(String pid);
	
	public EventTemp get(String pid);
}
