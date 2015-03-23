package com.ultrapower.eoms.ultrasla.service;

import java.util.List;

import com.ultrapower.eoms.ultrasla.model.EventHandlerComp;

/**
 * 自定义事件主体组件服务接口
 * @author RenChenglin
 * @date 2011-10-31 下午04:06:37
 * @version
 */
public interface IEventHandlerCompService {
	
	/**
	 * 保存
	 * @param comp
	 * @return
	 */
	public String save(EventHandlerComp comp);
	
	/**
	 * 修改
	 * @param comp
	 * @return
	 */
	public int update(EventHandlerComp comp);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public int delete(String id);
	
	/**
	 * 根据ID获得
	 * @param id
	 * @return
	 */
	public EventHandlerComp get(String id);
	
	/**
	 * 获得所有组件
	 * @return
	 */
	public List<EventHandlerComp> getAll();
	
	/**
	 * 通过组件类型获得组件
	 * @param type
	 * @return
	 */
	public List<EventHandlerComp> getByType(String type);
}
