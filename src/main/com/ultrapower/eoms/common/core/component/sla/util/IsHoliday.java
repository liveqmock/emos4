package com.ultrapower.eoms.common.core.component.sla.util;

import java.util.Date;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.util.TimeUtils;

/**
 * 节假日判断
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-9-14 上午11:01:21
 */
public class IsHoliday {

	public static QueryAdapter queryAdapter = new QueryAdapter();
	
	public static boolean nowIsHoliday(){
		boolean flag = false;
		String p_sql = "select * from bs_t_sm_holiday t where t.dateinfo = ? and t.holidayflag = 0 and t.hideflag = 0";
		String date = TimeUtils.formatShortDateToString(new Date());
		Object[] values = {date};
		DataTable dataTable = queryAdapter.executeQuery(p_sql, values,2);
		int dataTableLen = 0;
		if(dataTable!=null)
			dataTableLen = dataTable.length();
		if(dataTableLen>0)
			flag = true;
		return flag;
	}
}
