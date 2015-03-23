package com.ultrapower.eoms.common.core.component.sla.monitor;

import com.ultrapower.eoms.common.RecordLog;
import com.ultrapower.eoms.common.core.component.data.DataAdapter;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.sms.model.Smsmonitor;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.util.UUIDGenerator;

/**
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-9-14 下午05:03:22
 * @descibe 
 */
public class SmActionHandleImpl implements IActionHandle{

	private static DataAdapter dataAdapter = new DataAdapter();
	private static String systemtype = "SLA短信通知";
	
	
	public void execute(Object obj) {
		if(obj!=null){
			if(obj instanceof Smsmonitor){
				Smsmonitor sms = (Smsmonitor)obj;
				DataRow p_dtrow = new DataRow();
				p_dtrow.put("PID", UUIDGenerator.getUUIDoffSpace());
				p_dtrow.put("CONTENT", sms.getContent());
				p_dtrow.put("SYSTEMTYPE", systemtype);
				p_dtrow.put("GOAL", sms.getGoal());
				p_dtrow.put("SENDFLAG", 0);
				p_dtrow.put("RELATEID", sms.getRelateid());
				p_dtrow.put("PRI", sms.getPri());
				p_dtrow.put("INFOINPUTTIME", TimeUtils.getCurrentTime());
				int result = 0;
				try{
					result = dataAdapter.putDataRow("bs_t_sm_smsmonitor", p_dtrow, "", null);
				}catch(Exception e){
					RecordLog.printLog("调用短信接口,信息发送。短信息插入短信检控表--失败", RecordLog.LOG_LEVEL_ERROR);
					e.printStackTrace();
				}
				if(result>0)
					RecordLog.printLog("调用短信接口,信息发送。短信息插入短信检控表--成功", RecordLog.LOG_LEVEL_INFO);
			}else{
				RecordLog.printLog("调用短信接口,信息发送。非法的短信息对象", RecordLog.LOG_LEVEL_ERROR);
			}
		}
	}
}
