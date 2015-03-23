package com.ultrapower.eoms.ultrasla.service;

import com.ultrapower.eoms.ultrasla.model.EventParam;

/**
 * 事件参数服务接口
 * @author RenChenglin
 * @date 2011-10-31 下午04:20:28
 * @version
 */
public interface IEventParamService {
	
	/**
	 * 保存
	 * @param eventParam
	 * @return
	 */
	public String save(EventParam eventParam);
	
	/**
	 * 修改
	 * @param eventParam
	 * @return
	 */
	public int update(EventParam eventParam);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public int delete(String id);
	
	/**
	 * 根据事件ID删除
	 * @param eventId
	 * @return
	 */
	public int deleteByEventId(String eventId);
	
	/**
	 * 获得
	 * @param id
	 * @return
	 */
	public EventParam get(String id);
	
	/**
	 * 根据事件ID获得
	 * @param eventId
	 * @return
	 */
	public EventParam getByEventId(String eventId);
	
	/**
	 * 根据动作ID获得
	 * @param actionId
	 * @return
	 */
	public EventParam getByActionId(String actionId);
}
