package com.ultrapower.eoms.ultrasla.init;

import com.ultrapower.eoms.common.constants.PropertiesUtils;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.ultrasla.util.ConstantMark;

/**
 * 初始化SLA配置信息
 * @author SunHailong
 */
public class InitSlaConfigInfo {
	/**
	 * 初始化配置信息
	 */
	public static void initConfig() {
		ConstantMark.EVENT_SCAN_SWITCH = StringUtils.checkNullString(PropertiesUtils.getProperty("sla.event.scan")).equals("true") ? true : false;
		ConstantMark.EVENT_ROLL_SWITCH = StringUtils.checkNullString(PropertiesUtils.getProperty("sla.event.roll")).equals("true") ? true : false;
		ConstantMark.EVENT_SCAN_SQLNAME = StringUtils.checkNullString(PropertiesUtils.getProperty("sla.event.sqlname"));
		if("true".equals(StringUtils.checkNullString(PropertiesUtils.getProperty("sla.action.noticeFilter")).toLowerCase())) {
			ConstantMark.OPEN_NOTICE_FILTER = true;
		}
	}
	
}
