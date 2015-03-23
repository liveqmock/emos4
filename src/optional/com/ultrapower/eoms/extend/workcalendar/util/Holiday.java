package com.ultrapower.eoms.extend.workcalendar.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.ultrapower.eoms.common.core.util.StringUtils;

/**
 * 节假日
 * @author RenChenglin
 * @date 2011-12-15 下午01:43:34
 * @version
 */
public class Holiday {
	private String name;
	private String mark;
	private String startTime;
	private String endTime;
	private String swapWorkDay1;//调休日1
	private String swapWorkDay2;//调休日2
	private long startTimeNum = -1;
	private long endTimeNum = -1;
	
	public Holiday() {
		super();
	}

	public Holiday(String name, String mark, String startTime, String endTime,
			String swapWorkDay1, String swapWorkDay2) {
		super();
		this.name = name;
		this.mark = mark;
		this.startTime = startTime;
		this.endTime = endTime;
		this.swapWorkDay1 = swapWorkDay1;
		this.swapWorkDay2 = swapWorkDay2;
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
				String timeStr = yearStr + "-" + endTime;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					endTimeNum = sdf.parse(timeStr).getTime()/1000;
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		return endTimeNum;
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

	public String getSwapWorkDay1() {
		return swapWorkDay1;
	}

	public void setSwapWorkDay1(String swapWorkDay1) {
		this.swapWorkDay1 = swapWorkDay1;
	}

	public String getSwapWorkDay2() {
		return swapWorkDay2;
	}

	public void setSwapWorkDay2(String swapWorkDay2) {
		this.swapWorkDay2 = swapWorkDay2;
	}
	
}
