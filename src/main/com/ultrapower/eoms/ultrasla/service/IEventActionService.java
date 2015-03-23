package com.ultrapower.eoms.ultrasla.service;

import java.util.List;

import com.ultrapower.eoms.ultrasla.model.EventAction;

/**
 * 事件动作服务接口
 * @author Administrator
 */
public interface IEventActionService {

	/**
	 * 保存
	 * @param eventAction
	 */
	public String save(EventAction eventAction);

	/**
	 * 删除
	 * @param eventId
	 * @return
	 */
	public int delete(String eventId);

	/**
	 * 获得匹配的
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public EventAction getValueMatched(int pageNum, int pageSize);

	/**
	 * 通过事件ID修改动作的状态
	 * @param eventId
	 * @param status
	 * @return
	 */
	public int updateStatusByEventId(String eventId, long status);

	/**
	 * 通过ID修改动作的状态
	 * @param id
	 * @param status
	 * @return
	 */
	public int updateStatusById(String id, long status);

	/**
	 * 将指定时限以内的所有需要操作的事件动作全部查询出来
	 * @param startlimit 指定开始时限
	 * @param endlimit 指定结束时限
	 * @return
	 */
	public List<EventAction> getTimeLimitEventAction(long startlimit, long endlimit);
}
