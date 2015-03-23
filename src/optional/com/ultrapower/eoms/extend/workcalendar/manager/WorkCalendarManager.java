package com.ultrapower.eoms.extend.workcalendar.manager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.extend.workcalendar.model.WorkCalendar;
import com.ultrapower.eoms.extend.workcalendar.model.WorkTime;
import com.ultrapower.eoms.extend.workcalendar.service.WorkCalendarService;
import com.ultrapower.eoms.extend.workcalendar.util.Holiday;
import com.ultrapower.eoms.extend.workcalendar.util.TimeSpan;
import com.ultrapower.eoms.extend.workcalendar.util.WorkTimeType;

/**
 * @author RenChenglin
 * @date 2011-12-19 上午10:13:09
 * @version
 */
@Transactional
public class WorkCalendarManager implements WorkCalendarService {

	private IDao<WorkCalendar> workCalendarDao;
	private IDao<WorkTime> workTimeDao;
	
	public boolean createCalendar(String calType, String calYear, List<Holiday> hlds,
			List<WorkTimeType> wttype, List<Integer> weekend) {
		boolean res = false;
		if(calType!=null&&calYear!=null&&calYear.matches("[0-9]{4}"))
		{
			//workTime覆盖历史记录
			String hql = "delete WorkTime where calendartype = ? and datestring like '"+calYear+"-%'"; 
			workTimeDao.executeUpdate(hql, calType);
			//workCalendar覆盖历史记录
			hql = "delete WorkCalendar where calendartype = ? and datestring like '"+calYear+"-%'";
			workCalendarDao.executeUpdate(hql, calType);
			//处理周末，清空NULL值 
			if(weekend!=null)
			{
				int len = weekend.size();
				for(int i=len-1;i>=0;i--)
				{
					if(weekend.get(i)==null)
					{
						weekend.remove(i);
					}
				}
			}
			//处理节假日，清空NULL及不符合节假日
			if(hlds!=null)
			{
				int len = hlds.size();
				Holiday ho;
				for(int i=len-1;i>=0;i--)
				{
					ho = hlds.get(i);
					if(ho==null
						||"".equals(StringUtils.checkNullString(ho.getStartTime()))
						||"".equals(StringUtils.checkNullString(ho.getEndTime()))
					   )
					{
						hlds.remove(i);
					}
				}
			}
			//处理工作时间，清空NULL及不符合工作时间
			if(wttype!=null)
			{
				int len = wttype.size();
				WorkTimeType wtt;
				for(int i=len-1;i>=0;i--)
				{
					wtt = wttype.get(i);
					if(wtt==null
							||"".equals(StringUtils.checkNullString(wtt.getStartTime()))
							||"".equals(StringUtils.checkNullString(wtt.getEndTime()))
							||"".equals(StringUtils.checkNullString(wtt.getTimeSpanStr())))
					{
						wttype.remove(i);
					}
				}
			}
			int yearNum = Integer.parseInt(calYear);
			Calendar ca = new GregorianCalendar(yearNum,0,1);
			long currentTime = System.currentTimeMillis()/1000;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String holiday;
			int counter = 0;
			Session hibS = workCalendarDao.getHibernateSession();
			while(ca.get(Calendar.YEAR)==yearNum)
			{
				WorkCalendar wc = new WorkCalendar();
				wc.setCalendartype(calType);
				wc.setDatestring(sdf.format(ca.getTime()));
				wc.setDateseconds(ca.getTimeInMillis()/1000);
				wc.setCreatetime(currentTime);
				wc.setUpdatetime(currentTime);
				//是否周末
				if(isWeekend(weekend, ca))
				{
					//是否调休工作日
					if(isSwapWorkDay(hlds,ca))
					{
						wc.setIsholiday(0L);//周末调休工作日
					}
					else
					{
						wc.setIsholiday(1L);//周末愉快
						holiday = isHoliday(hlds, ca);
						if(holiday!=null)
						{
							wc.setHolidaytype(holiday);//周末节假日连放
						}
					}
				}
				else
				{
					holiday = isHoliday(hlds, ca);
					if(holiday!=null)
					{
						wc.setIsholiday(1L);//节日愉快
						wc.setHolidaytype(holiday);
					}
					else
					{
						wc.setIsholiday(0L);//正常工作日
					}
				}
				//保存日历
				workCalendarDao.save(wc);
				//非节假日保存工作时间
				if(wc.getIsholiday()!=1L)
				{
					saveWorkTime(wttype, wc, calYear);
				}
				counter++;
				//保存50个的时候，向数据库中flush一次
				if(counter%20==0)
				{
					
					hibS.flush(); // 将一级缓存中的数据同步到数据库中
					hibS.clear(); // 清空一级缓存中的数据，这样不至于造成内存溢出
				}
				ca.add(Calendar.DAY_OF_YEAR, 1);
			}
			res = true;
		}
		return res;
	}

	//判断是否是周末
	private boolean isWeekend(List<Integer> weekend, Calendar currentCa)
	{
		if(weekend==null||currentCa==null)
		{
			return false;
		}
		boolean res = false;
		int len = 0;
		if(weekend!=null)
		{
			len = weekend.size();
		}
		Integer dayOfWeek;
		for(int i=0;i<len;i++)
		{
			dayOfWeek = weekend.get(i);
			if(dayOfWeek!=null)
			{
				if(dayOfWeek.intValue()==currentCa.get(Calendar.DAY_OF_WEEK))
				{
					res = true;
					break;
				}
			}
		}
		return res;
	}
	
	//判断是否调休工作日
	private boolean isSwapWorkDay(List<Holiday> hlds,Calendar currentCa)
	{
		if(hlds==null||currentCa==null)
		{
			return false;
		}
		boolean res = false;
		String curDayStr = null;
		int len = 0;
		if(hlds!=null)
		{
			len = hlds.size();
			if(len>0)
			{
				StringBuffer curDay = new StringBuffer(String.format("%1$02d", currentCa.get(Calendar.MONTH)+1));
				curDay.append("-");
				curDay.append(String.format("%1$02d", currentCa.get(Calendar.DAY_OF_MONTH)));
				curDayStr = curDay.toString();
			}
		}
		Holiday ho;
		for(int i=0;i<len;i++)
		{
			ho = hlds.get(i);
			if(ho!=null)
			{
				if(curDayStr.equals(ho.getSwapWorkDay1()) || curDayStr.equals(ho.getSwapWorkDay2()))
				{
					res = true;
					break;
				}
			}
		}
		return res;
	}
	
	//判断是否节假日
	private String isHoliday(List<Holiday> hlds,Calendar currentCa)
	{
		if(hlds==null || currentCa==null)
		{
			return null;
		}
		String res = null;
		int len = 0;
		SimpleDateFormat sdf = null;
		long comparedTime = -1;
		if(hlds!=null)
		{
			len = hlds.size();
			if(len>0)
			{
				sdf = new SimpleDateFormat("yyyy-MM-dd");
				StringBuffer curDay = new StringBuffer(String.format("%1$04d", currentCa.get(Calendar.YEAR)));
				curDay.append("-");
				curDay.append(String.format("%1$02d", currentCa.get(Calendar.MONTH)+1));
				curDay.append("-");
				curDay.append(String.format("%1$02d", currentCa.get(Calendar.DAY_OF_MONTH)));
				try {
					comparedTime = sdf.parse(curDay.toString()).getTime()/1000;
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
			}
		}
		Holiday ho;
		String startTime;
		String endTime;
		String yearStr = String.format("%1$04d", currentCa.get(Calendar.YEAR)); 
		for(int i=0;i<len;i++)
		{
			ho = hlds.get(i);
			if(ho!=null)
			{
				startTime = ho.getStartTime();
				endTime = ho.getEndTime();
				if(startTime!=null&&endTime!=null)
				{
					if(ho.getStartTimeNum(yearStr)<=comparedTime && comparedTime<=ho.getEndTimeNum(yearStr))
					{
						res = StringUtils.checkNullString(ho.getMark());
						break;
					}
				}
			}
		}
		return res;
	}
	
	//保存工作时间
	private void saveWorkTime(List<WorkTimeType> wttype, WorkCalendar wc, String yearStr)
	{
		if(wttype!=null)
		{
			int len = wttype.size();
			WorkTimeType wtt;
			for(int i=0;i<len;i++)
			{
				wtt = wttype.get(i);
				if(wtt==null)
				{
					continue;
				}
				if(wtt.getStartTimeNum(yearStr)<=wc.getDateseconds() && wc.getDateseconds()<=wtt.getEndTimeNum(yearStr))
				{
					List<TimeSpan> timeSpan = wtt.getTimeSpan(wc.getDatestring());
					if(timeSpan!=null)
					{
						int len2 = timeSpan.size();
						TimeSpan ts;
						for(int j=0;j<len2;j++)
						{
							ts = timeSpan.get(j);
							if(ts!=null)
							{
								WorkTime wt = new WorkTime();
								wt.setCalendartype(wc.getCalendartype());
								wt.setDatestring(wc.getDatestring());
								wt.setDateseconds(wc.getDateseconds());
								wt.setTimetype(ts.getMark());
								wt.setStarttime(ts.getStartTime());
								wt.setEndtime(ts.getEndTime());
								wt.setCreatetime(wc.getCreatetime());
								wt.setUpdatetime(wc.getUpdatetime());
								workTimeDao.save(wt);
							}
						}
					}
					break;
				}
			}
		}
	}
	
	public boolean deleteByCalType(String calType, String year) {
		boolean res = false;
		if(calType!=null && year!=null)
		{
			String hql = "delete WorkTime where calendartype = ? and datestring like '"+year+"-%'";
			workTimeDao.executeUpdate(hql, calType,year);
			hql = "delete WorkCalendar where calendartype = ? and datestring like '"+year+"-%'";
			workCalendarDao.executeUpdate(hql, calType,year);
			res = true;
		}
		return res;
	}

	public boolean deleteById(String pid) {
		boolean res = false;
		if(pid!=null)
		{
			workCalendarDao.removeById(pid);
		}
		return res;
	}

	public WorkCalendar getById(String pid) {
		WorkCalendar wc = null;
		if(pid!=null)
		{
			wc = workCalendarDao.get(pid);
		}
		return wc;
	}

	public long isHoliday(String calType, String dateString) {
		long res = -1;
		if(calType!=null && dateString!=null)
		{
			String hql = "from WorkCalendar where calendartype = ? and datestring = ?";
			WorkCalendar wc = workCalendarDao.findUnique(hql, calType,dateString);
			if(wc!=null){
				res = wc.getIsholiday();
			}
		}
		return res;
	}

	public long isHoliday(String calType, long dateSeconds) {
		long res = -1;
		if(calType!=null)
		{
			String hql = "from WorkCalendar where calendartype = ? and dateseconds = ?";
			WorkCalendar wc = workCalendarDao.findUnique(hql, calType,dateSeconds);
			if(wc!=null){
				res = wc.getIsholiday();
			}
		}
		return res;
	}

	public String save(WorkCalendar cal) {
		String pid = null;
		if(cal!=null)
		{
			if(cal.getPid()==null)
			{
				workCalendarDao.save(cal);
				pid = cal.getPid();
			}
			else
			{
				workCalendarDao.saveOrUpdate(cal);
				pid = cal.getPid();
			}
		}
		return pid;
	}

	public boolean save(WorkCalendar wCal, List<WorkTime> workTimes)
	{
		boolean res = false;
		if(wCal!=null)
		{
			String calId = save(wCal);
			if(calId!=null)
			{
				//删除历史工作时间
				workTimeDao.executeUpdate("delete WorkTime where calendartype=? and datestring=?"
						, StringUtils.checkNullString(wCal.getCalendartype())
						, StringUtils.checkNullString(wCal.getDatestring()));
				//如果不是节假日，则保存工作时间
				if(wCal.getIsholiday()!=1L && workTimes!=null)
				{
					int len = workTimes.size();
					WorkTime wt;
					for(int i=0;i<len;i++)
					{
						wt = workTimes.get(i);
						if(wt!=null && wt.getStarttime()!=null && wt.getEndtime()!=null)
						{
							wt.setCalendartype(wCal.getCalendartype());
							wt.setDateseconds(wCal.getDateseconds());
							wt.setDatestring(wCal.getDatestring());
							if("".equals(StringUtils.checkNullString(wt.getPid())))
							{
								wt.setCreatetime(wCal.getUpdatetime());
								wt.setUpdatetime(wCal.getUpdatetime());
							}
							else
							{
								wt.setUpdatetime(wCal.getUpdatetime());
							}
							workTimeDao.save(wt);
						}
					}
				}
				res = true;
			}
			else
			{
				res = true;
			}
		}
		return res;
	}
	
	public long isCalendarExists(String calType, String calYear) {
		long res = 0;
		if(calType!=null && calYear!=null && calYear.matches("[0-9]{4}"))
		{
			Session s = workCalendarDao.getHibernateSession();
			Query q = s.createQuery("select count(*) from WorkCalendar where calendartype='"+calType+"' and datestring like '"+calYear+"-%'");
			Object obj = q.uniqueResult();
			if(obj!=null && (Long)obj>0)
			{
				res = 1;
			}
		}
		return res;
	}
	
	public void setWorkCalendarDao(IDao<WorkCalendar> workCalendarDao) {
		this.workCalendarDao = workCalendarDao;
	}

	public void setWorkTimeDao(IDao<WorkTime> workTimeDao) {
		this.workTimeDao = workTimeDao;
	}

	public static void main(String[] args){
		Calendar ca = new GregorianCalendar(2011,11,17);
		System.out.println(ca.get(Calendar.DAY_OF_WEEK));
	}

}
