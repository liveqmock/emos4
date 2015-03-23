package com.ultrapower.eoms.common.core.component.sla.util;

import com.ultrapower.eoms.common.core.util.TimeUtils;

/**
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-9-14 下午03:11:05
 */
public class TimeDeal {

	public static boolean nowIsScanTime(final String startTime,final String endTime){
		boolean flag = false;
		if(startTime == "" || endTime == "")
			return flag;
		
		String[] startTimeArr = startTime.split(":");
		long begin = Long.parseLong(startTimeArr[0])*3600+Long.parseLong(startTimeArr[1])*60;
		
		String[] nowTimeArr = TimeUtils.getCurrentDate("HH:mm").split(":");
		long now = Long.parseLong(nowTimeArr[0])*3600+Long.parseLong(nowTimeArr[1])*60;
		
		String[] endTimeArr = endTime.split(":");
		long end = Long.parseLong(endTimeArr[0])*3600+Long.parseLong(endTimeArr[1])*60;
		
		if(begin <= now && now <= end)
			flag = true;
		return flag;
	}
}
