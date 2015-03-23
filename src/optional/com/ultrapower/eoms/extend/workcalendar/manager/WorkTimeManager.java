package com.ultrapower.eoms.extend.workcalendar.manager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.extend.workcalendar.model.WorkCalendar;
import com.ultrapower.eoms.extend.workcalendar.model.WorkTime;
import com.ultrapower.eoms.extend.workcalendar.service.WorkTimeService;

/**
 * @author RenChenglin
 * @date 2011-12-19 上午10:13:28
 * @version
 */
@Transactional
public class WorkTimeManager implements WorkTimeService {

	private IDao<WorkTime> workTimeDao;
	private IDao<WorkCalendar> workCalendarDao;
	private int $y = Calendar.YEAR;
	private int $m = Calendar.MONTH;
	private int $d = Calendar.DAY_OF_MONTH;
	
	public String save(WorkTime workT) {
		String pid = null;
		if(workT!=null)
		{
			if(workT.getPid()==null)
			{
				workTimeDao.save(workT);
				pid = workT.getPid();
			}
			else
			{
				workTimeDao.saveOrUpdate(workT);
				pid = workT.getPid();
			}
		}
		return pid;
	}
	
	public int deleteByCalType(String calType, String year) {
		int res = -1;
		if(calType!=null && year!=null)
		{
			String hql = "delete WorkTime where calendartype = ? and datestring like '"+year+"-%'";
			res = workTimeDao.executeUpdate(hql, calType,year);
		}
		return res;
	}

	public int deleteById(String pid) {
		int res = -1;
		if(pid!=null)
		{
			workTimeDao.removeById(pid);
			res = 1;
		}
		return res;
	}

	public List<WorkTime> getByCalTypeAndDate(String calType, String dateString) {
		List<WorkTime> wts = null;
		if(calType!=null && dateString!=null)
		{
			String hql = "from WorkTime where calendartype = ? and datestring = ?";
			wts = workTimeDao.find(hql, calType,dateString);
		}
		return wts;
	}

	public List<WorkTime> getByCalTypeAndDate(String calType, long dateSeconds) {
		List<WorkTime> wts = null;
		if(calType!=null)
		{
			String hql = "from WorkTime where calendartype = ? and dateseconds = ?";
			wts = workTimeDao.find(hql, calType,dateSeconds);
		}
		return wts;
	}

	public WorkTime getById(String pid) {
		WorkTime wt = null;
		if(pid!=null)
		{
			wt = workTimeDao.get(pid);
		}
		return wt;
	}

	public long getCompleteEndTime(String calType, long produceTime,
			long timeLimit) {
		if("".equals(StringUtils.checkNullString(calType)) || produceTime<=0 || timeLimit<=0)
		{
			return -1; //参数不合格
		}
		Map<String,Long> accMap = new HashMap<String,Long>();//用来存储最后时限和累加时间
		accMap.put("endTime", -3L); //最后的时限
		accMap.put("accTime", 0L);  //累加时间
		Calendar produceCa = new GregorianCalendar();//产生任务的日期
		produceCa.setTimeInMillis(produceTime*1000);
		String hql = "from WorkCalendar where calendartype = ? and datestring = ?";
		String dateString = getDayString(produceCa);
		WorkCalendar wc = workCalendarDao.findUnique(hql, calType, dateString);
		if(wc==null)
		{
			return -2; //工作日历有问题
		}
		//从产生任务时间开始，累加任务产生时间所在工作日的工作时间段
		accCalculate(accMap, calType, wc, dateString, timeLimit, produceTime);
		//如果在任务产生日期内没有找到最后时限，也表示这一天的工作时间段加起来小于完成时限，则继续计算
		if(accMap.get("endTime")==-3)
		{
			long avgWD = 8*60*60; //假设每日平均工作时长 8小时
			long restTimeLimit = timeLimit - accMap.get("accTime");
			int supposeDay = (int)(restTimeLimit/avgWD);
			if(supposeDay<=0)
			{//在假设每日平均工作时长下，不足一天即可完成
				//向后累加工作日的工作时间段，直至找到时限为止
				while(accMap.get("endTime")==-3)
				{
					produceCa.add($d, 1);
					produceCa.set(Calendar.HOUR_OF_DAY, 0);
					produceCa.set(Calendar.MINUTE, 0);
					produceCa.set(Calendar.SECOND, 0);
					dateString = getDayString(produceCa);
					wc = workCalendarDao.findUnique(hql, calType, dateString);
					if(wc==null)
					{
						return -2;
					}
					accCalculate(accMap, calType, wc, dateString, timeLimit, produceCa.getTimeInMillis()/1000);
				}
			}
			else
			{//在假设每日平均工作时长下，需要>=1天才能完成
				long tempL = -1;
				long tempR = -1;
				long isholiday = 1;
				while(isholiday==1)
				{
					produceCa.add($d, 1);
					dateString = getDayString(produceCa);
					wc = workCalendarDao.findUnique(hql, calType, dateString);
					if(wc==null)
					{
						return -2;
					}
					isholiday = wc.getIsholiday();
				}
				produceCa.set(Calendar.HOUR_OF_DAY, 0);
				produceCa.set(Calendar.MINUTE, 0);
				produceCa.set(Calendar.SECOND, 0);
				tempL = produceCa.getTimeInMillis()/1000;
				int counter = 1;
				while(counter<supposeDay)
				{
					produceCa.add($d, 1);
					dateString = getDayString(produceCa);
					wc = workCalendarDao.findUnique(hql, calType, dateString);
					if(wc==null)
					{
						return -2;
					}
					if(wc.getIsholiday()!=1)
					{
						counter++;
					}
				}
				produceCa.set(Calendar.HOUR_OF_DAY, 23);
				produceCa.set(Calendar.MINUTE, 59);
				produceCa.set(Calendar.SECOND, 59);
				tempR = produceCa.getTimeInMillis()/1000;
				
				Session s = workTimeDao.getHibernateSession();
				Query q = s.createQuery("select sum(endtime - starttime) from WorkTime where calendartype = ? and dateseconds >= ? and dateseconds <= ?");
				q.setString(0, calType);
				q.setLong(1, tempL);
				q.setLong(2, tempR);
				Object obj = q.uniqueResult();
				if(obj!=null)
				{
					long supposeTime = (Long)obj;
					if(accMap.get("accTime")+supposeTime < timeLimit)
					{//加上假设所需工作日时间还不足以完成任务     顺加法
						accMap.put("accTime", accMap.get("accTime")+supposeTime);
						while(accMap.get("endTime")==-3)
						{
							produceCa.add($d, 1);
							produceCa.set(Calendar.HOUR_OF_DAY, 0);
							produceCa.set(Calendar.MINUTE, 0);
							produceCa.set(Calendar.SECOND, 0);
							dateString = getDayString(produceCa);
							wc = workCalendarDao.findUnique(hql, calType, dateString);
							if(wc==null)
							{
								return -2;
							}
							accCalculate(accMap, calType, wc, dateString, timeLimit, produceCa.getTimeInMillis()/1000);
						}
					}
					else if(accMap.get("accTime")+supposeTime > timeLimit)
					{//加上假设所需工作日时间足以完成任务并有富余时间     逆减法
						accMap.put("accTime", accMap.get("accTime")+supposeTime);
						while(accMap.get("endTime")==-3)
						{
							dateString = getDayString(produceCa);
							wc = workCalendarDao.findUnique(hql, calType, dateString);
							if(wc==null)
							{
								return -2;
							}
							decCalculate(accMap, calType, wc, dateString, timeLimit, produceCa.getTimeInMillis()/1000);
							produceCa.add($d, -1);
						}
					}
					else if(accMap.get("accTime")+supposeTime == timeLimit)
					{//加上假设所需工作日时间刚好完成任务
						String temp_hql = "from WorkTime where calendartype = ? and datestring = ? order by endtime desc";
						List<WorkTime> temp_wts = workTimeDao.find(temp_hql, calType,getDayString(produceCa));
						if(temp_wts!=null && temp_wts.get(0)!=null)
						{
							accMap.put("endTime", temp_wts.get(0).getEndtime());
						}
						else
						{
							accMap.put("endTime", produceCa.getTimeInMillis()/1000);
						}
					}
				}
				else
				{
					return -4; //工作时间设置有问题
				}
			}
		}
		return accMap.get("endTime");
	}
	
	//累加当天工作时间
	private void accCalculate(Map<String,Long> accMap, String calType, WorkCalendar wc, String dateString, long timeLimit, long startTime)
	{
		if(wc.getIsholiday()!=1)
		{
			//升序
			String hql = "from WorkTime where calendartype = ? and datestring = ? order by starttime asc";
			List<WorkTime> wts = workTimeDao.find(hql, calType,dateString);
			if(wts!=null)
			{
				int len = wts.size();
				WorkTime wt;
				long tempSpan;
				for(int i=0;i<len;i++)
				{
					wt = wts.get(i);
					if(wt==null)
					{
						continue;
					}
					
					if(startTime<=wt.getStarttime())
					{
						tempSpan = wt.getEndtime() - wt.getStarttime();
						if(accMap.get("accTime")+tempSpan >= timeLimit)
						{
							accMap.put("endTime", wt.getStarttime() + (timeLimit - accMap.get("accTime")));
							break;
						}
						accMap.put("accTime", accMap.get("accTime")+tempSpan);
					}
					else if(startTime>wt.getStarttime() && startTime<wt.getEndtime())
					{
						tempSpan = wt.getEndtime() - startTime;
						if(accMap.get("accTime")+tempSpan >= timeLimit)
						{
							accMap.put("endTime", startTime + (timeLimit - accMap.get("accTime")));
							break;
						}
						accMap.put("accTime", accMap.get("accTime")+tempSpan);
					}
				}
			}
		}
	}
	
	//累减工作时间
	private void decCalculate(Map<String,Long> accMap, String calType, WorkCalendar wc, String dateString, long timeLimit, long startTime)
	{
		if(wc.getIsholiday()!=1)
		{
			//降序
			String hql = "from WorkTime where calendartype = ? and datestring = ? order by starttime desc";
			List<WorkTime> wts = workTimeDao.find(hql, calType,dateString);
			if(wts!=null)
			{
				int len = wts.size();
				WorkTime wt;
				long tempSpan;
				for(int i=0;i<len;i++)
				{
					wt = wts.get(i);
					if(wt==null)
					{
						continue;
					}
					if(startTime>=wt.getEndtime())
					{
						tempSpan = wt.getEndtime() - wt.getStarttime();
						if(accMap.get("accTime")-tempSpan <= timeLimit)
						{
							accMap.put("endTime", wt.getEndtime() - (accMap.get("accTime")-timeLimit));
							break;
						}
						accMap.put("accTime", accMap.get("accTime")-tempSpan);
					}
					else if(startTime<wt.getEndtime() && startTime>wt.getStarttime())
					{
						tempSpan = startTime - wt.getStarttime();
						if(accMap.get("accTime")-tempSpan <= timeLimit)
						{
							accMap.put("endTime", startTime - (accMap.get("accTime")-timeLimit));
							break;
						}
						accMap.put("accTime", accMap.get("accTime")-tempSpan);
					}
				}
			}
		}
	}
	
	//获得给定日历下的日期字符串 2011-12-29
	private String getDayString(Calendar ca)
	{
		StringBuffer dateStringBf = new StringBuffer();
		dateStringBf.append(String.format("%1$04d",ca.get($y)));
		dateStringBf.append("-");
		dateStringBf.append(String.format("%1$02d",ca.get($m)+1));
		dateStringBf.append("-");
		dateStringBf.append(String.format("%1$02d",ca.get($d)));
		return dateStringBf.toString();
	}
	
	public long getWorkTimeLength(String calType, long startTime, long endTime) {
		if("".equals(StringUtils.checkNullString(calType))
				||startTime<=0
				||endTime<=0
				||startTime>endTime)
		{//非法参数，返回-1
			return -1;
		}
		if(startTime==endTime)
		{//开始时间等于结束时间，返回0
			return 0;
		}
		long wl = 0;
		Calendar startCa = new GregorianCalendar();//开始日期
		startCa.setTimeInMillis(startTime*1000);
		Calendar endCa = new GregorianCalendar();//结束日期
		endCa.setTimeInMillis(endTime*1000);
		if(startCa.get(Calendar.HOUR_OF_DAY)==0 && startCa.get(Calendar.MINUTE)==0 && startCa.get(Calendar.SECOND)==0
				|| endCa.get(Calendar.HOUR_OF_DAY)==23 && endCa.get(Calendar.MINUTE)==59 && endCa.get(Calendar.SECOND)==59)
		{//如果是从某一天00:00:00开始到某一天23:59:59
			Session s = workTimeDao.getHibernateSession();
			Query q = s.createQuery("select sum(endtime - starttime) from WorkTime where calendartype = ? and dateseconds >= ? and dateseconds <= ?");
			q.setString(0, calType);
			q.setLong(1, startTime);
			q.setLong(2, endTime);
			Object obj = q.uniqueResult();
			if(obj!=null)
			{
				wl = (Long)obj;
			}
		}
		else if(startCa.get($y)==endCa.get($y) && startCa.get($m)==endCa.get($m) && startCa.get($d)==endCa.get($d))
		{//如果开始日期和结束日期在同一天
			wl = calculateSameDay(calType, startCa, endCa);
		}
		else
		{//开始日期和结束日期不在同一天
			wl = calculateDifferDay(calType, startCa, endCa);
		}
		return wl;
	}
	//计算在同一天的时长
	private long calculateSameDay(String calType, Calendar startCa, Calendar endCa)
	{
		if(startCa.getTimeInMillis()==endCa.getTimeInMillis())
		{
			return 0;
		}
		long wl = 0; //工作时长
		String hql = "from WorkCalendar where calendartype = ? and datestring = ?";
		String dateString = getDayString(startCa);
		WorkCalendar wc = workCalendarDao.findUnique(hql, calType, dateString);
		long isholiday = 1; //如果没有找到该天，是不做计算的
		if(wc!=null)
		{
			isholiday = wc.getIsholiday();
		}
		//如果是非节假日，则进行计算
		if(isholiday!=1l)
		{
			List<WorkTime> wts = this.getByCalTypeAndDate(calType, dateString);
			if(wts!=null)
			{
				long tl = startCa.getTimeInMillis()/1000; //开始时间
				long tr = endCa.getTimeInMillis()/1000;   //结束时间
				int len = wts.size();
				WorkTime wt;
				for(int i=0;i<len;i++)
				{
					wt = wts.get(i);
					if(wt==null)
					{
						continue;
					}
					if(tl<=wt.getStarttime() && wt.getEndtime()<=tr) //工作时段落在tl和tr之间
					{
						wl += wt.getEndtime() - wt.getStarttime();
					}
					else if(wt.getStarttime()<tl && wt.getEndtime()>tl && wt.getEndtime()<=tr) //工作时段跨tl，不跨tr
					{
						wl += wt.getEndtime() - tl;
					}
					else if(wt.getStarttime()>=tl && wt.getStarttime()<tr && wt.getEndtime()>tr) //工作时段跨tr，不跨tl
					{
						wl += tr - wt.getStarttime();
					}
					else if(wt.getStarttime()<tl && tr<wt.getEndtime()) //工作时段同时跨tl和tr
					{
						wl += tr - tl;
					}
				}
			}
		}
		return wl;
	}
	//计算不在同一天的时长
	private long calculateDifferDay(String calType, Calendar startCa, Calendar endCa)
	{
		long wl = 0; //工作时长
		//计算开始时间所在天的工作时长
		Calendar calML = new GregorianCalendar(startCa.get($y),startCa.get($m),startCa.get($d),23,59,59);
		wl += calculateSameDay(calType, startCa, calML);
		//计算结束时间所在天的工作时长
		Calendar calMR = new GregorianCalendar(endCa.get($y),endCa.get($m),endCa.get($d),0,0,0);
		wl += calculateSameDay(calType, calMR, endCa);
		//计算开始时间所在天和结束时间所在天之间的整数天工作时长
		Session s = workTimeDao.getHibernateSession();
		Query q = s.createQuery("select sum(endtime - starttime) from WorkTime where calendartype = ? and dateseconds > ? and dateseconds < ?");
		q.setString(0, calType);
		q.setLong(1, calML.getTimeInMillis()/1000);
		q.setLong(2, calMR.getTimeInMillis()/1000);
		Object obj = q.uniqueResult();
		if(obj!=null)
		{
			wl += (Long)obj;
		}
		return wl;
	}

	public void setWorkTimeDao(IDao<WorkTime> workTimeDao) {
		this.workTimeDao = workTimeDao;
	}

	public void setWorkCalendarDao(IDao<WorkCalendar> workCalendarDao) {
		this.workCalendarDao = workCalendarDao;
	}
	
	public static void main(String[] args){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			System.out.println(sdf.parse("2011-12-30 14:00:00").getTime()/1000);
			System.out.println(sdf.parse("2011-12-26 14:00:00").getTime()/1000);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
