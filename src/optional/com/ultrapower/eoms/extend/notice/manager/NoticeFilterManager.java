package com.ultrapower.eoms.extend.notice.manager;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.NumberUtils;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.extend.notice.model.NoticeFilter;
import com.ultrapower.eoms.extend.notice.service.NoticeFilterService;
import com.ultrapower.eoms.extend.notice.util.Constant;
import com.ultrapower.eoms.extend.workcalendar.service.WorkCalendarService;

/**
 * @author RenChenglin
 * @date 2011-12-12 下午04:05:04
 * @version
 */
@Transactional
public class NoticeFilterManager implements NoticeFilterService {
	WorkCalendarService workCalendarService;
	IDao<NoticeFilter> noticeFilterDao;

	public NoticeFilter getNoticeFilter(String loginName, String baseSchema) {
		return this.getNoticeFilter("form", baseSchema, "1", loginName);
	}
	
	public NoticeFilter getNoticeFilter(String businessType, String businessMark, String userType, String userMark) {
		NoticeFilter nf = null;
		if("".equals(StringUtils.checkNullString(businessType)) || "".equals(StringUtils.checkNullString(businessMark))) {
			return nf;
		}
		if("".equals(StringUtils.checkNullString(userType)) || "".equals(StringUtils.checkNullString(userMark))) {
			return nf;
		}
		String hql = "from NoticeFilter where usertype=? and usermark=? and businesstype=? and businessmark=?";
		List<NoticeFilter> nfList = null;
		try {
			nfList = noticeFilterDao.find(hql, userType, userMark, businessType, businessMark);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(nfList != null && nfList.size() > 0){
			nf = nfList.get(0);
		}
		return nf;
	}
	
	public boolean deleteById(String pid) {
		boolean result = false;
		if(pid != null) {
			try {
				noticeFilterDao.removeById(pid);
				result = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public NoticeFilter getById(String pid) {
		NoticeFilter filter = null;
		if(pid != null) {
			filter = noticeFilterDao.get(pid);
		}
		return filter;
	}

	public String save(NoticeFilter filter) {
		String pid = "";
		if(filter != null) {
			if("".equals(StringUtils.checkNullString(filter.getPid()))) {
				noticeFilterDao.save(filter);
			} else {
				noticeFilterDao.saveOrUpdate(filter);
			}
			pid = filter.getPid();
		}
		return pid;
	}

	private NoticeFilter getNoticeFilterJdbc(String businessType, String businessMark, String userType, String userMark) {
		NoticeFilter noticeFilter = null;
		QueryAdapter queryAdapter = new QueryAdapter();
		DataTable table = null;
		try {
			table = queryAdapter.executeQuery("select workdaystarttime, workdayendtime, holidaystatus, holidaystarttime, holidayendtime, isresend from bs_t_com_noticefilter where businesstype = ? and businessmark = ? and usertype = ? and usermark = ?", businessType, businessMark, userType, userMark);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		int tableLen = 0;
		if(table != null) {
			tableLen = table.length();
		}
		if(tableLen > 0) {
			DataRow row = table.getDataRow(0);
			noticeFilter = new NoticeFilter();
			noticeFilter.setWorkdaystarttime(row.getString("workdaystarttime"));
			noticeFilter.setWorkdayendtime(row.getString("workdayendtime"));
			noticeFilter.setHolidaystatus(row.getLong("holidaystatus"));
			noticeFilter.setHolidaystarttime(row.getString("holidaystarttime"));
			noticeFilter.setHolidayendtime(row.getString("holidayendtime"));
			noticeFilter.setIsresend(row.getLong("isresend"));
		}
		return noticeFilter;
	}
	
	private long isHolidayJdbc(String calType, String dateString) {
		long isHoliday = 0;
		QueryAdapter queryAdapter = new QueryAdapter();
		DataTable table = null;
		try {
			table = queryAdapter.executeQuery("select isholiday from bs_t_com_workcalendar where calendartype = ? and datestring = ?", calType, dateString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int tableLen = 0;
		if(table != null) {
			tableLen = table.length();
		}
		if(tableLen > 0) {
			isHoliday = table.getDataRow(0).getLong("isholiday");
		}
		return isHoliday;
	}
	
	public int doNoticeFilter(String baseSchema, String userlogin, Map<String, String> paramMap) {
		return this.doNoticeFilter("form", baseSchema, "1", userlogin, paramMap);
	}

	public int doNoticeFilter(String businessType, String businessMark, String userType, String userMark, Map<String, String> paramMap) {
		int result = 1; // 接收通知
		NoticeFilter noticeFilter = this.getNoticeFilterJdbc(businessType, businessMark, userType, userMark);
		if(noticeFilter == null) { // 如果获取通知过滤对象为空，则返回1，未定义默认通过过滤
			return result;
		}
		String calType = Constant.Calendar_Common_Type;
		if(paramMap != null) {
			if(!"".equals(StringUtils.checkNullString(paramMap.get("CalendarType")))) {
				calType = paramMap.get("FIXED_CAL_TYPE");
			}
		}
		long curTime = TimeUtils.getCurrentTime();
		String strDate = TimeUtils.formatIntToDateString("yyyy-MM-dd", curTime);
		long isHoliday = 0;
		if(Constant.FINDWAY == 0) {
			isHoliday = workCalendarService.isHoliday(calType, strDate);			
		} else if(Constant.FINDWAY == 1) {
			isHoliday = this.isHolidayJdbc(calType, strDate);
		}
		if(isHoliday == 1) { // 是节假日
			long holidayStatus = NumberUtils.formatToLong(noticeFilter.getHolidaystatus());
			if(holidayStatus == 1) { // 节假日启用
				String holidayStart = StringUtils.checkNullString(noticeFilter.getHolidaystarttime()); // 节假日设定开始时间
				String holidayEnd = StringUtils.checkNullString(noticeFilter.getHolidayendtime()); // 节假日设定结束时间
				if(!this.isBetweenTimes(curTime, holidayStart, holidayEnd)) {
					result = 0; // 当前时间不接收通知
				}
			}
		} else { // 不是节假日
			String workdayStart = StringUtils.checkNullString(noticeFilter.getWorkdaystarttime()); // 工作日设定开始时间
			String workdayEnd = StringUtils.checkNullString(noticeFilter.getWorkdayendtime()); // 工作日设定结束时间
			if(!this.isBetweenTimes(curTime, workdayStart, workdayEnd)) {
				result = 0; // 当前时间不接收通知
			}
		}
		return result;
	}
	
	/**
	 * 判断指定时间是否在一个时间段范围内
	 * @param curTime 指定时间（秒）
	 * @param timeStart 时间段开始时间 格式: 00:00
	 * @param timeEnd 时间段结束时间 格式: 23:59
	 * @return
	 */
	private boolean isBetweenTimes(long curTime, String timeStart, String timeEnd) {
		boolean isBetween = false;
		if("".equals(timeStart) || "".equals(timeEnd)) {
			return true;
		}
		String strTime = TimeUtils.formatIntToDateString("HH:mm", curTime);
		if(timeStart.compareTo(timeEnd) < 0) { // 时间段开始时间 小于 时间段结束时间
			if(strTime.compareTo(timeStart) >= 0 && strTime.compareTo(timeEnd) <= 0) {
				isBetween = true;
			}
		} else {
			if(strTime.compareTo(timeStart) >= 0 || strTime.compareTo(timeEnd) <= 0) {
				isBetween = true;
			}
		}
		return isBetween;
	}
	
	public long getDelayTime(String baseSchema, String userlogin, Map<String, String> paramMap) {
		return this.getDelayTime("form", baseSchema, "1", userlogin, paramMap);
	}

	public long getDelayTime(String businessType, String businessMark, String userType, String userMark, Map<String, String> paramMap) {
		long curTime = TimeUtils.getCurrentTime();
		long delayTime = curTime;
		NoticeFilter noticeFilter = this.getNoticeFilterJdbc(businessType, businessMark, userType, userMark);
		if(noticeFilter == null) {
			return delayTime;
		}
		String calType = Constant.Calendar_Common_Type;
		if(paramMap != null) {
			if(!"".equals(StringUtils.checkNullString(paramMap.get("CalendarType")))) {
				calType = paramMap.get("FIXED_CAL_TYPE");
			}
		}
		long holidayStatus = NumberUtils.formatToLong(noticeFilter.getHolidaystatus());
		String strDate = TimeUtils.formatIntToDateString("yyyy-MM-dd", curTime);
		long isHoliday = 0;
		if(Constant.FINDWAY == 0) {
			isHoliday = workCalendarService.isHoliday(calType, strDate);			
		} else if(Constant.FINDWAY == 1) {
			isHoliday = this.isHolidayJdbc(calType, strDate);
		}
		if(isHoliday == 1) { // 是节假日
			if(holidayStatus == 1) { // 节假日启用
				String holidayStart = StringUtils.checkNullString(noticeFilter.getHolidaystarttime()); // 节假日设定开始时间
				String holidayEnd = StringUtils.checkNullString(noticeFilter.getHolidayendtime()); // 节假日设定结束时间
				delayTime = this.getNoticeTime(curTime, holidayStart, holidayEnd);
			} else { // 节假日不启用
				delayTime = -1;
			}
		} else { // 不是节假日
			String workdayStart = StringUtils.checkNullString(noticeFilter.getWorkdaystarttime()); // 工作日设定开始时间
			String workdayEnd = StringUtils.checkNullString(noticeFilter.getWorkdayendtime()); // 工作日设定结束时间
			delayTime = this.getNoticeTime(curTime, workdayStart, workdayEnd);
		}
		if(delayTime < 0) { // 需要跨天计算
			delayTime = getActualNoticeTime(curTime, noticeFilter);
		}
		return delayTime;
	}
	
	/**
	 * 获取通知时间
	 * @param curTime 指定时间（秒）
	 * @param timeStart 时间段开始时间 格式: 00:00
	 * @param timeEnd 时间段结束时间 格式: 23:59
	 * @return
	 */
	private long getNoticeTime(long curTime, String timeStart, String timeEnd) {
		long noticeTime = curTime;
		if("".equals(timeStart) || "".equals(timeEnd)) {
			return noticeTime;
		}
		String strDate = TimeUtils.formatIntToDateString("yyyy-MM-dd", curTime);
		String strTime = TimeUtils.formatIntToDateString("HH:mm", curTime);
		if(timeStart.compareTo(timeEnd) < 0) { // 时间段开始时间 小于 时间段结束时间
			if(strTime.compareTo(timeStart) >= 0 && strTime.compareTo(timeEnd) <= 0) { // 在指定时间段内通知时间为指定时间
				noticeTime = curTime;
			} else if(strTime.compareTo(timeStart) < 0) { // 小于时间段开始时间，通知时间为时间段开始时间
				noticeTime = TimeUtils.formatDateStringToInt(strDate + " " + timeStart + ":00");
			} else { // 大于时间段结束时间，则需要推迟计算
				noticeTime = -1;
			}
		} else { // 时间段开始时间 大于等于 时间段结束时间
			if(strTime.compareTo(timeStart) >= 0 || strTime.compareTo(timeEnd) <= 0) { // 在指定时间段内通知时间为指定时间
				noticeTime = curTime;
			} else { // 在时间段内，通知时间为时间段开始时间
				noticeTime = TimeUtils.formatDateStringToInt(strDate + " " + timeStart + ":00");
			}
		}
		return noticeTime;
	}
	
	/**
	 * 获取实际通知时间
	 * @param curTime 指定的时间
	 * @param noticeFilter 通知过滤对象
	 * @return
	 */
	private long getActualNoticeTime(long curTime, NoticeFilter noticeFilter) {
		long noticeTime = curTime;
		if(noticeFilter == null) {
			return noticeTime;
		}
		String timeStart;
		String timeEnd;
		String strDate;
		long holidayStatus = NumberUtils.formatToLong(noticeFilter.getHolidaystatus());
		long isHoliday = 0;
		while (true) {
			curTime += 86400; // 24 * 60 * 60 累加1天 用来获取明天是否是节假日
			strDate = TimeUtils.formatIntToDateString("yyyy-MM-dd", curTime);
			if(Constant.FINDWAY == 0) {
				isHoliday = workCalendarService.isHoliday(Constant.Calendar_Common_Type, strDate);			
			} else if(Constant.FINDWAY == 1) {
				isHoliday = this.isHolidayJdbc(Constant.Calendar_Common_Type, strDate);
			}
			if(isHoliday == 1) { // 是节假日
				if(holidayStatus == 1) { // 节假日启用，则获取节假日指定的开始时间作为接收短信的初始时间
					timeStart = StringUtils.checkNullString(noticeFilter.getHolidaystarttime());
					timeEnd = StringUtils.checkNullString(noticeFilter.getHolidayendtime());
					if(timeStart.compareTo(timeEnd) < 0) {
						noticeTime = TimeUtils.formatDateStringToInt(strDate + " " + timeStart + ":00");
					} else {
						noticeTime = TimeUtils.formatDateStringToInt(strDate + " 00:00:00");
					}
					break;
				}
			} else { // 不是节假日
				timeStart = StringUtils.checkNullString(noticeFilter.getWorkdaystarttime());
				timeEnd = StringUtils.checkNullString(noticeFilter.getWorkdayendtime());
				if(timeStart.compareTo(timeEnd) < 0) {
					noticeTime = TimeUtils.formatDateStringToInt(strDate + " " + timeStart + ":00");
				} else {
					noticeTime = TimeUtils.formatDateStringToInt(strDate + " 00:00:00");
				}
				break;
			}
		}
		return noticeTime;
	}

	public void setWorkCalendarService(WorkCalendarService workCalendarService) {
		this.workCalendarService = workCalendarService;
	}
	public void setNoticeFilterDao(IDao<NoticeFilter> noticeFilterDao) {
		this.noticeFilterDao = noticeFilterDao;
	}
}
