package com.ultrapower.eoms.ultrasla;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ultrapower.eoms.common.core.component.data.DataAdapter;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.UUIDGenerator;
import com.ultrapower.eoms.common.core.web.ActionContext;

/**
 * 公共日志打印组件
 * @author 朱朝辉(E-mail:zhuzhaohui@ultrapower.com.cn)
 * @version Apr 20, 2010 3:16:33 PM
 */
public class RecordLog{

	private final static Logger loger = LoggerFactory.getLogger(RecordLog.class);
	private static DataAdapter dataAdapter = new DataAdapter();
	public final static int LOG_LEVEL_ERROR = 1;
	public final static int LOG_LEVEL_WARN = 2;
	public final static int LOG_LEVEL_INFO = 3;
	public final static int LOG_LEVEL_DEBUG = 4;
	public final static int LOG_LEVEL_TRACE = 5;
	
	
	/**
	 * 记录操作审计信息
	 * @param moduleId 模块代码
	 * @param operateId 操作代码
	 * @param description 操作描述
	 */
	public static void printOperateAuditLog(String module,String operate,String description) 
	{
		DataRow p_dtrow = new DataRow();
		p_dtrow.put("PID", UUIDGenerator.getUUIDoffSpace());
		if(ActionContext.getUserSession()!=null){
			p_dtrow.put("LOGINNAME", StringUtils.checkNullString(ActionContext.getUserSession().getLoginName()));
			p_dtrow.put("IP", StringUtils.checkNullString(ActionContext.getRequest().getRemoteAddr()));
		}else{
			p_dtrow.put("LOGINNAME", "");
			p_dtrow.put("IP", "");
		}
		p_dtrow.put("MODULE", module);
		p_dtrow.put("OPERTYPE", operate);
		p_dtrow.put("DESCRIPTION", description);
		p_dtrow.put("TIME", System.currentTimeMillis()/1000);
		int resultrowsum = dataAdapter.putDataRow("bs_t_sm_operauditlog", p_dtrow, null, null);
		if(resultrowsum>0){
			loger.debug("记录操作审计信息成功;"+description);
		}else{
			loger.debug("记录操作审计信息失败;"+description);
		}
	}
	
	/**
	 * 记录流量统计日志
	 * @param url 受访Url
	 */
	public static void printFluxStatLog(String url){
		DataRow p_dtrow = new DataRow();
		p_dtrow.put("PID", UUIDGenerator.getUUIDoffSpace());
		if(ActionContext.getUserSession()!=null){
			p_dtrow.put("LOGINNAME", StringUtils.checkNullString(ActionContext.getUserSession().getLoginName()));
			p_dtrow.put("IP", StringUtils.checkNullString(ActionContext.getRequest().getRemoteAddr()));
		}else{
			p_dtrow.put("LOGINNAME", "");
			p_dtrow.put("IP", "");
		}
		p_dtrow.put("URL", url);
		p_dtrow.put("TIME", System.currentTimeMillis()/1000);
		int resultrowsum = dataAdapter.putDataRow("bs_t_sm_fluxstatlog", p_dtrow, null, null);
		if(resultrowsum>0){
			loger.debug("记录流量统计日志成功;"+url);
		}else{
			loger.debug("记录流量统计日志失败;"+url);
		}
	}
	
	/**
	 * 常规信息打印(不带级别,默认一般输出信息,默认级别为3)
	 * @param description 日志描述
	 */
	public static void printLog(String description)
	{
		loger.info(description);
	}
	
	/**
	 * 常规信息打印(带日志级别)
	 * @param description
	 * @param level
	 */
	public static void printLog(String description,int level)
	{

		switch(level)
		{
			case LOG_LEVEL_ERROR:
				loger.error(description);
				break;
			case LOG_LEVEL_WARN:
				loger.warn(description);
				break;
			case LOG_LEVEL_INFO:
				loger.info(description);
				break;
			case LOG_LEVEL_DEBUG:
				loger.debug(description);
				break;
			case LOG_LEVEL_TRACE:
				loger.trace(description);
				break;
			default:
				loger.info(description);
		}
	}
}
