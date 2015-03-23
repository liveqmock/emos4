package com.ultrapower.eoms.extend.workcalendar.web;

import java.util.List;

import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.extend.workcalendar.model.WorkCalendar;
import com.ultrapower.eoms.extend.workcalendar.model.WorkTime;
import com.ultrapower.eoms.extend.workcalendar.service.WorkCalendarService;
import com.ultrapower.eoms.extend.workcalendar.service.WorkTimeService;
import com.ultrapower.eoms.extend.workcalendar.util.Holiday;
import com.ultrapower.eoms.extend.workcalendar.util.WorkTimeType;
import com.ultrapower.eoms.ultrasm.model.DicItem;
import com.ultrapower.eoms.ultrasm.service.DicManagerService;

/**
 * @author RenChenglin
 * @date 2011-12-15 下午03:21:43
 * @version
 */
public class WorkCalendarAction extends BaseAction {
	private String calType;//日历类型
	private String calYear;//日历年份
	private List<DicItem> holidays;//有哪些节假日
	private List<DicItem> weekDays;//星期
	private List<Integer> weekend;//周末
	private List<WorkTimeType> workTimes;//工作时间
	private List<Holiday> holidaySets;//节假日设置
	private WorkCalendar workCalendar;//某一工作天
	private WorkTime workTime;//某一工作时间
	private List<WorkTime> dayWorkTimes;//某一工作天的工作时间段
	private DicManagerService dicManagerService;
	private WorkCalendarService workCalendarService;
	private WorkTimeService workTimeService;
	
	public String workCalendarList()
	{
		return SUCCESS;
	}
	
	public String workCalendarLoad()
	{
		workCalendar = workCalendarService.getById(id);
		if(workCalendar!=null)
		{
			dayWorkTimes = workTimeService.getByCalTypeAndDate(workCalendar.getCalendartype(), workCalendar.getDatestring());
		}
		return findForward("workCalendarSave");
	}
	
	public String workCalendarSave()
	{
		String msg = "保存失败！";
		if(workCalendarService.save(workCalendar, dayWorkTimes))
		{
			msg = "保存成功！";
		}
		getRequest().setAttribute("returnMessage", msg);
		getRequest().setAttribute("parafresh", "true");
		return "refresh";
	}
	
	public String workCalendarCreate()
	{
		if(calType==null)
		{
			if(dicManagerService!=null) 
			{
				holidays = dicManagerService.getDicItemByDicType("holidayType");
				weekDays = dicManagerService.getDicItemByDicType("weekDays");
			}
			return SUCCESS;
		}
		else
		{
			boolean result = workCalendarService.createCalendar(calType, calYear, holidaySets, workTimes, weekend);
			String msg = "保存失败！";
			if(result)
			{
				msg = "保存成功！";
			}
			getRequest().setAttribute("returnMessage", msg);
			getRequest().setAttribute("parafresh", "true");
			return "refresh";
		}
	}

	public void isCalendarExists()
	{
		long res = workCalendarService.isCalendarExists(calType, calYear);
		renderText(String.valueOf(res));
	}
	
	public String workTimeList()
	{
		return SUCCESS;
	}
	
	public String workTimeLoad()
	{
		return SUCCESS;
	}
	
	public String workTimeSave()
	{
		return SUCCESS;
	}
	
	public String getCalType() {
		return calType;
	}

	public void setCalType(String calType) {
		this.calType = calType;
	}

	public String getCalYear() {
		return calYear;
	}

	public void setCalYear(String calYear) {
		this.calYear = calYear;
	}

	public List<DicItem> getHolidays() {
		return holidays;
	}

	public void setHolidays(List<DicItem> holidays) {
		this.holidays = holidays;
	}

	public void setDicManagerService(DicManagerService dicManagerService) {
		this.dicManagerService = dicManagerService;
	}

	public List<DicItem> getWeekDays() {
		return weekDays;
	}

	public void setWeekDays(List<DicItem> weekDays) {
		this.weekDays = weekDays;
	}

	public List<Integer> getWeekend() {
		return weekend;
	}

	public void setWeekend(List<Integer> weekend) {
		this.weekend = weekend;
	}

	public void setWorkTimes(List<WorkTimeType> workTimes) {
		this.workTimes = workTimes;
	}

	public List<WorkTimeType> getWorkTimes() {
		return workTimes;
	}

	public List<Holiday> getHolidaySets() {
		return holidaySets;
	}

	public void setHolidaySets(List<Holiday> holidaySets) {
		this.holidaySets = holidaySets;
	}

	public void setWorkCalendarService(WorkCalendarService workCalendarService) {
		this.workCalendarService = workCalendarService;
	}

	public WorkCalendar getWorkCalendar() {
		return workCalendar;
	}

	public void setWorkCalendar(WorkCalendar workCalendar) {
		this.workCalendar = workCalendar;
	}

	public WorkTime getWorkTime() {
		return workTime;
	}

	public void setWorkTime(WorkTime workTime) {
		this.workTime = workTime;
	}

	public void setWorkTimeService(WorkTimeService workTimeService) {
		this.workTimeService = workTimeService;
	}

	public List<WorkTime> getDayWorkTimes() {
		return dayWorkTimes;
	}

	public void setDayWorkTimes(List<WorkTime> dayWorkTimes) {
		this.dayWorkTimes = dayWorkTimes;
	}
	
}
