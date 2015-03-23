package com.ultrapower.eoms.ultraduty.service;

import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.ultraduty.model.DutyHoliday;

/**
 * Copyright (c) 2010 神州泰岳服务管理事业部产品组
 * @author： yhg
 * @date： 2010-5-7 下午04:09:57
 * 当前版本：
 * 摘要:
 */
public interface HolidayService {
	
	/**
	 * 根据日期得到当天的日期类型
	 * @param date yyyy-MM-dd
	 * @return 1：工作日，2：节假日，3：重保日
	 */
	public int getDateTypeByDate(String date,String depNo);
	
	/** 保存方法
	    * 
	    * @param dutyHoliday
	    * @pdOid 49a77667-28d5-4200-9686-cb5d0269bda9 */
	   public void addHoliday(DutyHoliday dutyHoliday);
	   
	   /** 根据ID获得对象
	    * 
	    * @param holidayId
	    * @pdOid 230f6b72-c836-4dd8-88f4-5bc4797bfff4 */
	   public DutyHoliday getHolidayByid(String holidayId);
	   
	   /** 根据ID更新对象
	    * 
	    * @param oldHoliday 
	    * @param newHoliday
	    * @pdOid 8cf2d1de-5dc3-43e1-a888-571c57566faf */
	   public void updateHoliday(DutyHoliday oldHoliday, DutyHoliday newHoliday);
	   
	   /** 根据ID删除对象
	    * 
	    * @param holidayId
	    * @pdOid 5fec8d72-469c-4b24-8fec-b752f1df91a4 */
	   public void delHoliday(String holidayId);
	   
	   /** 查询集合对象
	    * 
	    * @param dutyHoliday
	    * @pdOid 3d41ade5-a92d-4a86-824d-1b77836142d9 */
	   public List<DutyHoliday> listHoliday(DutyHoliday dutyHoliday);

		/** 
	    * 保存新增节假日
	    * @param dutyHoliday
	    * @param startTime
	    * @param endTime
	    */
	   public void addHoliday(DutyHoliday dutyHoliday, String startTime, String endTime);
	   
	   /** 
	    * 根据条件查询集合对象
	    * 
	    * @param holidayName
	    * @param startTime
	    * @param endTime
	    * @return List<DutyHoliday>
	    */
	   public List<DutyHoliday> listHoliday(String holidayName, String startTime, String endTime,Long holidayFlag);
	   
	   
	   public List<DutyHoliday> listHoliday2(String holidayName, String startTime, String endTime,Long holidayFlag);
	   /** 
	    * 更新节假日基本信息
	    * 
	    * @param newHoliday
	    */
	   public void updateHoliday(DutyHoliday dutyHoliday);
	   

	   /**
	    * 检验某时间段内本节日是否已经被添加
	    * 
	    * @param holidayName
	    * @param startTime
	    * @param endTime
	    * @return
	    */
	   public String checkHolidayIsExist(String holidayName, String startTime, String endTime);
	   
	   /**
	    * 查询启用节假日集合
	    * 
	    * @return Map<MM-dd, 名称> 
	    */
	   public Map<String, String> getHolidayMap();
	   /**
	    * 根据时间段查询节假日
	    * @author yhg
	    * @param stime
	    * @param etime
	    * @return
	    */
	   public Map<String,String> getHolidayMap(String stime,String etime);
	   
	   /**
		 * 根据月开始结束日期得到所有节假日和非节假日
		 * @param monthFirstDay
		 * @param monthLastDay
		 * @return
		 */
		public String getHolidays(String monthFirstDay,String monthLastDay);
		
		/**
		 * 根据日期得到节假日
		 * @param date
		 * @return
		 */
		public DutyHoliday getHolidayByDate(String date);
		
		/**
		 * 根据开始结束时间得到工作日天数
		 * @param startTIme
		 * @param endTime
		 * @return
		 */
		public int getWorkDaysByStartTimeAndEndTime(String startTIme,String endTime);
		
		/**
		 * 根据开始日期、工作天数，得到结束日期
		 * @return
		 */
		public String getEndDateByStartDateAndWorkDays(String startTime,int workDays);
		
		/**
		 * 根据日期得到是否是节假日
		 * @param date
		 * @return
		 */
		public Boolean isOrNotHoliday(String date);
}
