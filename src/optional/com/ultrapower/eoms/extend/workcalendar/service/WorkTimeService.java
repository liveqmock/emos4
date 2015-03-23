package com.ultrapower.eoms.extend.workcalendar.service;

import java.util.List;

import com.ultrapower.eoms.extend.workcalendar.model.WorkTime;

/**
 * @author RenChenglin
 * @date 2011-12-15 上午11:18:33
 * @version
 */
public interface WorkTimeService {

	/**
	 * 保存
	 * @param workT
	 * @return
	 */
	public String save(WorkTime workT);
	
	/**
	 * 根据ID删除
	 * @param pid
	 * @return
	 */
	public int deleteById(String pid);
	
	/**
	 * 根据日历类型和年份删除
	 * @param calType 日历类型
	 * @param year 年份
	 * @return
	 */
	public int deleteByCalType(String calType, String year);
	
	/**
	 * 根据ID获得
	 * @param pid
	 * @return
	 */
	public WorkTime getById(String pid);
	
	/**
	 * 根据日历类型和日期获得
	 * @param calType 日历类型
	 * @param dateString 时间字符串 yyyy-MM-dd
	 * @return
	 */
	public List<WorkTime> getByCalTypeAndDate(String calType, String dateString);
	
	/**
	 * 根据日历类型和日期获得
	 * @param calType 日历类型
	 * @param dateSeconds 时间秒数（秒）
	 * @return
	 */
	public List<WorkTime> getByCalTypeAndDate(String calType, long dateSeconds);
	
	/**
	 * 获得完成时限
	 * @param calType 日历类型
	 * @param produceTime 产生时间（秒）
	 * @param timeLimit 时限（秒）
	 * @return 完成时限（秒） -1：非法参数；-2：工作日历有问题；-3：未能找到结果；-4：工作时间设置有问题
	 */
	public long getCompleteEndTime(String calType, long produceTime, long timeLimit);
	
	/**
	 * 获得工作时长
	 * @param calType 日历类型
	 * @param startTime 开始时间（秒）
	 * @param endTime 结束时间（秒）
	 * @return 工作时长（秒）
	 */
	public long getWorkTimeLength(String calType, long startTime, long endTime);
}
