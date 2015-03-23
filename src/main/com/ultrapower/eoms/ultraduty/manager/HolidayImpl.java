package com.ultrapower.eoms.ultraduty.manager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ultrapower.eoms.common.constants.DutyUtil;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.support.PageLimit;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.ultraduty.model.DutyHoliday;
import com.ultrapower.eoms.ultraduty.service.HolidayService;

@Transactional
public class HolidayImpl implements HolidayService {
	IDao<DutyHoliday> theDutyHolidayDao;
	
	private static final String checkHolidayIsExistSQL = "from DutyHoliday dh where dh.dateinfo = ? and dh.hideflag <> 1 ";
	
	// 查询启用节假日Map
	private static final String HOLIDAYMAPSQL = "from DutyHoliday dh where dh.hideflag <> 1 and holidayFlag = 0 ";
	private static final String FINDSPECIALHOLIDAYBYDATE = "from DutyHoliday dh where dh.hideflag <> 1 and dh.dateinfo = ?";

	private static final String FINDHOLIDAYBYDATE = "from DutyHoliday dh where dh.hideflag <> 1 and dh.dateinfo = ?";
	private static final String FINDCOMMONHOLIDAYBYDATE = "from DutyHoliday dh where dh.hideflag <> 1 and dh.dateinfo = ?";
	// 查询启用节假日Map
	public void addHoliday(DutyHoliday dutyHoliday) {
		theDutyHolidayDao.save(dutyHoliday);
	}

	public void delHoliday(String holidayId) {
		if (DutyUtil.isNull(holidayId)) {
			String[] pids = holidayId.split(",");
			for (String pid : pids) {
				// 删除节假日信息
				theDutyHolidayDao.removeById(pid);;
			}
		}
	}
	/**
	 * 根据日期得到节假日
	 * @param date yyyy-MM-dd
	 * @return
	 */
	public DutyHoliday getHolidayByDate(String date,String organizationId) {
		DutyHoliday holiday = null;
		List<DutyHoliday> holidayList = theDutyHolidayDao.find(FINDSPECIALHOLIDAYBYDATE, date);
		if(holidayList!=null && holidayList.size()>0){
			holiday = holidayList.get(0);
		}else{
			holidayList = theDutyHolidayDao.find(FINDCOMMONHOLIDAYBYDATE, date);
			if(holidayList!=null && holidayList.size()>0){
				holiday = holidayList.get(0);
			}
		}
		return holiday;
	}
	/**
	 * 根据日期得到当天的日期类型
	 * @param date
	 * @return 1：工作日，2：节假日，3：重保日
	 */
	public int getDateTypeByDate(String date,String organizationId) {
		int i = 1;
		DutyHoliday dh = this.getHolidayByDate(date,organizationId);
		if (dh != null && DutyUtil.isNull(dh.getPid())) {//节假日表存在该日期的数据
			//datetype 1为特殊日期（重保日），2为非变更日
			if(com.ultrapower.eoms.common.core.util.StringUtils.isNotBlank(dh.getDatetype()) && dh.getDatetype().equals("1")){
				i=3;
			}else{
				i = dh.getHolidayFlag().intValue()==0?2:1;
			}
		} else {
			// 判断是不是周末
			Calendar dateCal = Calendar.getInstance();
			dateCal.setTime(TimeUtils.formatStrToDate(date));
			int day = dateCal.get(Calendar.DAY_OF_WEEK);
			if (day == Calendar.SATURDAY || day == Calendar.SUNDAY) {
				i = 2;
			}
		}
		return i;
	}
	public DutyHoliday getHolidayByid(String holidayId) {
		DutyHoliday holiday = theDutyHolidayDao.get(holidayId);
		return holiday;
	}
	
	/**
	 * 根据日期得到节假日
	 * @param date
	 * @return
	 */
	public DutyHoliday getHolidayByDate(String date) {
		DutyHoliday holiday = new DutyHoliday();
		List<DutyHoliday> holidayList = theDutyHolidayDao.find(FINDHOLIDAYBYDATE, date);
		if(holidayList!=null && holidayList.size()>0){
			holiday = holidayList.get(0);
		}
		return holiday;
	}

	public List<DutyHoliday> listHoliday(DutyHoliday dutyHoliday) {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateHoliday(DutyHoliday oldHoliday, DutyHoliday newHoliday) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 以1日为单位保存节假日信息
	 * @param dutyHoliday
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public void addHoliday(DutyHoliday dutyHoliday, String startTime,
			String endTime) {
		
		Date startDay = TimeUtils.formatStrToDate(startTime);
		Date endDay = TimeUtils.formatStrToDate(endTime);
		while (startDay.compareTo(endDay) <= 0) {
			DutyHoliday holiday = new DutyHoliday();
			holiday.setHolidayname(dutyHoliday.getHolidayname());
			holiday.setNote(dutyHoliday.getNote());
			holiday.setHideflag(dutyHoliday.getHideflag());
			holiday.setHolidayFlag(dutyHoliday.getHolidayFlag());
			holiday.setDatetype(dutyHoliday.getDatetype());
			String dateTemp = TimeUtils.formatShortDateToString(startDay);
			String[] ymd = dateTemp.split("\\-");
			holiday.setDateinfo(dateTemp);
			holiday.setYears(ymd[0]);
			holiday.setMonths(ymd[1]);
			holiday.setDays(ymd[2]);
			this.addHoliday(holiday);
			
			// 日期加1
			startDay = TimeUtils.getModifyDate(startDay, 1);
		}
	}
	
   /** 
    * 更新节假日基本信息
    * 
    * @param newHoliday
    * @param date
    */
	public void updateHoliday(DutyHoliday dutyHoliday) {
		dutyHoliday.setDateinfo(dutyHoliday.getDateinfo());
		String[] ymd = dutyHoliday.getDateinfo().split("\\-");
		dutyHoliday.setMonths(ymd[0]);
		dutyHoliday.setDays(ymd[1]);
		theDutyHolidayDao.saveOrUpdate(dutyHoliday);
	}
	
	/**
     * 根据条件查询集合对象
     * 
     * @param holidayName
     * @param startTime
     * @param endTime
     * @return List<DutyHoliday>
	 */
	public List<DutyHoliday> listHoliday(String holidayName, String startTime,
			String endTime,Long holidayFlag) {
		List<DutyHoliday> holidayList = new ArrayList<DutyHoliday>();
		PageLimit pageLimit = PageLimit.getInstance();
		List<Object> paramList = new ArrayList<Object>();
		// 节假日名称不为空
		StringBuffer sbf = new StringBuffer();
		sbf.append(" from DutyHoliday dh where 1 = 1");
		if (DutyUtil.isNull(holidayName)) {
			sbf.append(" and dh.holidayname like ? ");
			paramList.add("%" + holidayName.trim() + "%");
		}
		if (holidayFlag != null) {
			sbf.append(" and dh.holidayFlag = ? ");
			paramList.add(holidayFlag);
		}
		
		if (DutyUtil.isNull(startTime)) {
			sbf.append(" and dh.dateinfo >= ? ");
			paramList.add(startTime);
		}
		
		if (DutyUtil.isNull(endTime)) {
			sbf.append(" and dh.dateinfo <= ? ");
			paramList.add(endTime);
		}
		sbf.append(" order by dh.holidayname, dh.dateinfo");
		
		holidayList = theDutyHolidayDao.pagedQuery(sbf.toString(), pageLimit, DutyUtil.getObjects(paramList));
		return holidayList;
	}
	
	
	
	/**
     * 根据条件查询集合对象
     * 
     * @param holidayName
     * @param startTime
     * @param endTime
     * @return List<DutyHoliday>
	 */
	public List<DutyHoliday> listHoliday2(String holidayName, String startTime,
			String endTime,Long holidayFlag) {
		List<DutyHoliday> holidayList = new ArrayList<DutyHoliday>();
		// 节假日名称不为空
		StringBuffer sbf = new StringBuffer();
		sbf.append(" from DutyHoliday dh where 1 = 1");
		if (DutyUtil.isNull(holidayName)) {
			sbf.append(" and dh.holidayname like %" + holidayName.trim() + "% ");
		}
		if (holidayFlag != null) {
			sbf.append(" and dh.holidayFlag = " + holidayFlag + " ");
		}
		
		if (DutyUtil.isNull(startTime)) {
			sbf.append(" and dh.dateinfo >= '" + startTime + "' ");
		}
		
		if (DutyUtil.isNull(endTime)) {
			sbf.append(" and dh.dateinfo <= '" + endTime + "' ");
		}
		sbf.append(" order by dh.holidayname, dh.dateinfo");
		
		holidayList = theDutyHolidayDao.find(sbf.toString());
		return holidayList;
	}
	
   /**
    * 检验某时间段内本节日是否已经被添加
    * 
    * @param holidayName
    * @param startTime
    * @param endTime
    * @return
    */
	public String checkHolidayIsExist(String holidayName, String startTime,
			String endTime) {
		String returnMessages = "true";
		Date endDay = TimeUtils.formatStrToDate(endTime);
		Date startDay = TimeUtils.formatStrToDate(startTime);
		while (startDay.compareTo(endDay) <= 0) {
			String dateTemp = TimeUtils.formatShortDateToString(startDay);
			List<DutyHoliday> dhList = theDutyHolidayDao.find(checkHolidayIsExistSQL, dateTemp);
			
			if (dhList != null) {
				if (dhList.size() > 0) {
					returnMessages = "false";
					break;
				}
			}
			// 日期加1
			startDay = TimeUtils.getModifyDate(startDay, 1);
		}
		return returnMessages;
	}
	
   /**
    * 查询启用节假日集合
    * 
    * @return Map<MM-dd, 名称> 
    */
	public Map<String, String> getHolidayMap() {
		Map<String, String> holidayMap = new LinkedHashMap<String,String>();
		List<DutyHoliday> list = theDutyHolidayDao.find(HOLIDAYMAPSQL);
		for (DutyHoliday dh : list) {
			holidayMap.put(dh.getDateinfo(), dh.getHolidayname());
		}
		return holidayMap;
	}
	
	/* (non-Javadoc)
	 * @see com.ultrapower.eoms.ultraduty.service.HolidayService#getHolidayMap(java.lang.String, java.lang.String)
	 */
	public Map<String, String> getHolidayMap(String stime, String etime) {
		Map<String, String> holidayMap = new LinkedHashMap<String,String>();
		String sql = "from DutyHoliday dy where dy.dateinfo >= ? and dy.dateinfo <= ?";
		List<DutyHoliday> list = theDutyHolidayDao.find(sql, new Object[]{stime,etime});
		for (DutyHoliday dh : list) {
			String month = String.format("%02d", Integer.parseInt(dh.getMonths()));
			String day = String.format("%02d", Integer.parseInt(dh.getDays()));
			holidayMap.put(month + "-" + day, dh.getHolidayname());
		}
		return holidayMap;
	}
	
	/**
	 * 根据月开始结束日期得到所有节假日和非节假日
	 * @param monthFirstDay
	 * @param monthLastDay
	 * @return
	 */
	public String getHolidays(String monthFirstDay,String monthLastDay){
		
		String jsonArray = "";//构建json数组
		try {
			String sql = "from DutyHoliday dy where dy.dateinfo >= ? and dy.dateinfo <= ? and dy.hideflag = '0'";
			List<DutyHoliday> list = theDutyHolidayDao.find(sql, monthFirstDay,monthLastDay);
			String jsonString = "";//json字符串
			if(list != null){
				for(DutyHoliday dutyHoliday : list){
					jsonString += ",{'buildDate':'"+dutyHoliday.getDateinfo()+"','isHoliday':'" +dutyHoliday.getHolidayFlag() + "','holidayName':'" +dutyHoliday.getHolidayname() + "'}";
				}
			}
			if(StringUtils.hasLength(jsonString)){
				jsonArray = "["+ jsonString.substring(1)+"]";
			}else{
				jsonArray="[{'buildDate':null,'isHoliday':'null','holidayName':'null'}]"; 
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return jsonArray;
	}
	
	/**
	 * 根据开始结束时间得到工作日天数
	 * @param startTIme
	 * @param endTime
	 * @return
	 */
	public int getWorkDaysByStartTimeAndEndTime(String startTIme,String endTime){
		
		int workDays = 0;
		Date startDate = TimeUtils.formatStrToDate(startTIme);
		Date endDate = TimeUtils.formatStrToDate(endTime);
		
		while (startDate.compareTo(endDate) <= 0) {
			boolean isWeekend = TimeUtils.isWeekend(startDate);
			String dateTemp = TimeUtils.formatShortDateToString(startDate);
			List<DutyHoliday> dhList = theDutyHolidayDao.find(checkHolidayIsExistSQL, dateTemp);
			if(isWeekend == false){
				if (dhList != null) {
					if (dhList.size() > 0) {
						Long isHoliday = dhList.get(0).getHolidayFlag();
						if(isHoliday == 1){
							workDays += 1;
						}
					}else {
						workDays += 1;
					}
				}else{
					workDays += 1;
				}
			}else{
				if (dhList != null) {
					if (dhList.size() > 0) {
						Long isHoliday = dhList.get(0).getHolidayFlag();
						if(isHoliday == 1){
							workDays += 1;
						}
					}
				}
			}
			startDate = TimeUtils.getModifyDate(startDate, 1);
		}
		return workDays;
	}
	
	/**
	 * 根据开始日期、工作天数，得到结束日期
	 * @return
	 */
	public String getEndDateByStartDateAndWorkDays(String startTime,int workDays){
		
		String endTime = "";
		Date endDate = null;
		Date startDate = TimeUtils.formatStrToDate(startTime);
		for(int i=1;i<workDays+1;i++){
			endDate = TimeUtils.getModifyDate(startDate, 1);
			boolean isWeekend = TimeUtils.isWeekend(endDate);
			String dateTemp = TimeUtils.formatShortDateToString(endDate);
			List<DutyHoliday> dhList = theDutyHolidayDao.find(checkHolidayIsExistSQL, dateTemp);
			if(isWeekend == false){
				if (dhList != null) {
					if (dhList.size() > 0) {
						Long isHoliday = dhList.get(0).getHolidayFlag();
						if(isHoliday == 0){
							endDate = TimeUtils.getModifyDate(endDate, 1);
						}
					}
				}
			}else{
				if (dhList != null) {
					if (dhList.size() > 0) {
						Long isHoliday = dhList.get(0).getHolidayFlag();
						if(isHoliday == 0){
							endDate = TimeUtils.getModifyDate(endDate, 1);
						}
					}else{
						endDate = TimeUtils.getModifyDate(endDate, 1);
					}
				}else{
					endDate = TimeUtils.getModifyDate(endDate, 1);
				}
			}
			
		}
		endTime = TimeUtils.formatShortDateToString(endDate);
		return endTime;
	}
	
	/**
	 * 根据日期得到是否是节假日
	 * @param date
	 * @return
	 */
	public Boolean isOrNotHoliday(String date){
		
		List<DutyHoliday> dhList = theDutyHolidayDao.find(FINDHOLIDAYBYDATE, date);
		if(dhList!=null){
			if(dhList.size()>0){
				DutyHoliday dutyHoliday = dhList.get(0);
				if(dutyHoliday!=null){
					int holidayFlag = dutyHoliday.getHideflag();
					if(holidayFlag == 0){
						return true;
					}
				}
			}
		}
		return false;
	}
	

	public IDao<DutyHoliday> getTheDutyHolidayDao() {
		return theDutyHolidayDao;
	}

	public void setTheDutyHolidayDao(IDao<DutyHoliday> theDutyHolidayDao) {
		this.theDutyHolidayDao = theDutyHolidayDao;
	}

	public static String getCheckHolidayIsExistSQL() {
		return checkHolidayIsExistSQL;
	}

	public static String getHOLIDAYMAPSQL() {
		return HOLIDAYMAPSQL;
	}

	public static String getFINDSPECIALHOLIDAYBYDATE() {
		return FINDSPECIALHOLIDAYBYDATE;
	}

	public static String getFINDHOLIDAYBYDATE() {
		return FINDHOLIDAYBYDATE;
	}

	public static String getFINDCOMMONHOLIDAYBYDATE() {
		return FINDCOMMONHOLIDAYBYDATE;
	}

}
