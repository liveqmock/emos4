package com.ultrapower.eoms.extend.workcalendar.service;

import java.util.List;

import com.ultrapower.eoms.extend.workcalendar.model.WorkCalendar;
import com.ultrapower.eoms.extend.workcalendar.model.WorkTime;
import com.ultrapower.eoms.extend.workcalendar.util.Holiday;
import com.ultrapower.eoms.extend.workcalendar.util.WorkTimeType;

/**
 * @author RenChenglin
 * @date 2011-12-15 上午11:18:12
 * @version
 */
public interface WorkCalendarService {
	
	/**
	 * 生成一年的工作日历
	 * @param calType 日历类别
	 * @param calType 日历年份
	 * @param hlds 节假日
	 * @param weekend 周末（数字，如周一为1，周二为2）
	 * @param wttype 工作时间类型
	 * @return
	 */
	public boolean createCalendar(String calType, String calYear, List<Holiday> hlds
			, List<WorkTimeType> wttype, List<Integer> weekend);
	
	/**
	 * 保存
	 * @param wCal
	 * @return
	 */
	public String save(WorkCalendar wCal);
	
	/**
	 * 保存工作日历及对应的工作时间，当是工作日时，保存工作时间，否则不保存工作时间，且删除历史工作时间
	 * @param wCal
	 * @param workTimes
	 * @return
	 */
	public boolean save(WorkCalendar wCal, List<WorkTime> workTimes);
	
	/**
	 * 根据ID删除
	 * @param pid
	 * @return
	 */
	public boolean deleteById(String pid);
	
	/**
	 * 根据日历类型和年份删除
	 * @param calType
	 * @param year 年份
	 * @return
	 */
	public boolean deleteByCalType(String calType, String year);
	
	/**
	 * 根据ID获得
	 * @param pid
	 * @return
	 */
	public WorkCalendar getById(String pid);
	
	/**
	 * 判断是否节假日
	 * @param calType 日历类型
	 * @param dateString 日期字符串，如 yyyy-MM-dd
	 * @return 1:是 0:否 -1:没找到
	 */
	public long isHoliday(String calType, String dateString);
	
	/**
	 * 判断是否节假日
	 * @param calType 日历类型
	 * @param dateSeconds 日期的秒数
	 * @return 1:是 0:否 -1:没找到
	 */
	public long isHoliday(String calType, long dateSeconds);
	
	/**
	 * 日历模版是否存在
	 * @param calType
	 * @param year
	 * @return 1是 0否
	 */
	public long isCalendarExists(String calType, String calYear);
}
