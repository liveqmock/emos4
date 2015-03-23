package com.ultrapower.eoms.extend.workcalendar.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.ultrapower.eoms.common.core.util.StringUtils;

/**
 * @author RenChenglin
 * @date 2011-12-15 下午01:51:09
 * @version
 */
public class WorkTimeType {
	private String name;
	private String mark;
	private String startTime;
	private String endTime;
	private String timeSpanStr;
	private List<TimeSpan> timeSpan;
	private long startTimeNum = -1;
	private long endTimeNum = -1;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	String[] spans, spanArr, timeArr;
	public WorkTimeType() {
		super();
//		if(!"".equals(StringUtils.checkNullString(timeSpanStr)))
//		{
//			spans = timeSpanStr.split(",");
//			
//		}
	}
	
	public WorkTimeType(String name, String mark, String startTime,
			String endTime, List<TimeSpan> timeSpan) {
		super();
		this.name = name;
		this.mark = mark;
		this.startTime = startTime;
		this.endTime = endTime;
		this.timeSpan = timeSpan;
	}
	
	/**
	 * 获得开始日期的秒数
	 * @param yearStr
	 * @return
	 */
	public long getStartTimeNum(String yearStr) {
		if(startTimeNum==-1)
		{
			if(!"".equals(StringUtils.checkNullString(startTime)) && yearStr!=null && yearStr.matches("[0-9]{4}"))
			{
				String timeStr = yearStr + "-" + startTime;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					startTimeNum = sdf.parse(timeStr).getTime()/1000;
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		return startTimeNum;
	}

	/**
	 * 获得结束日期的秒数
	 * @param yearStr
	 * @return
	 */
	public long getEndTimeNum(String yearStr) {
		if(endTimeNum==-1)
		{
			if(!"".equals(StringUtils.checkNullString(endTime)) && yearStr!=null && yearStr.matches("[0-9]{4}"))
			{
				String timeStr = yearStr + "-" + endTime + " 23:59:59";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					endTimeNum = sdf.parse(timeStr).getTime()/1000;
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		return endTimeNum;
	}
	
	/**
	 * 获得工作时间段的集合
	 * @param yyyyMMdd yyyy-MM-dd字符串
	 * @return
	 */
	public List<TimeSpan> getTimeSpan(String yyyyMMdd) {
		if(!"".equals(StringUtils.checkNullString(timeSpanStr)) && !"".equals(StringUtils.checkNullString(yyyyMMdd)))
		{
			timeSpan = new ArrayList<TimeSpan>();
			String[] spans, spanArr, timeArr;
			spans = timeSpanStr.split(",");
			long startTimeN,endTimeN;
			String timeStr;
			int len = spans.length;
			String span;
			for(int i=0;i<len;i++)
			{
				span = spans[i];
				if(!"".equals(StringUtils.checkNullString(span)))
				{
					try {
						spanArr = span.split(" ");
						timeArr = spanArr[0].split("\\-");
						timeStr = yyyyMMdd + " " + timeArr[0];
						startTimeN = sdf.parse(timeStr).getTime()/1000;
						timeStr = yyyyMMdd + " " + timeArr[1] + ("23:59".equals(timeArr[1]) ? ":59" : ":00");
						endTimeN = sdf2.parse(timeStr).getTime()/1000;
						TimeSpan ts = new TimeSpan();
						ts.setStartTime(startTimeN);
						ts.setEndTime(endTimeN);
						if(spanArr.length==2)
						{
							ts.setMark(spanArr[1]);
						}
						timeSpan.add(ts);
					} catch (ParseException e) {
						e.printStackTrace();
						return null;
					}
				}
			}
		}
		return timeSpan;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public List<TimeSpan> getTimeSpan() {
		return timeSpan;
	}

	public void setTimeSpan(List<TimeSpan> timeSpan) {
		this.timeSpan = timeSpan;
	}

	public String getTimeSpanStr() {
		return timeSpanStr;
	}

	public void setTimeSpanStr(String timeSpanStr) {
		this.timeSpanStr = timeSpanStr;
	}
	
}
