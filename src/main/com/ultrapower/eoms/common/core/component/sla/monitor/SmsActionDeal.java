package com.ultrapower.eoms.common.core.component.sla.monitor;

import java.util.ArrayList;
import java.util.List;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.ultrapower.eoms.common.RecordLog;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.component.sla.util.IsHoliday;
import com.ultrapower.eoms.common.core.component.sla.util.TimeDeal;
import com.ultrapower.eoms.common.core.util.StringUtils;

/**
 * SLA--短信业务
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-9-10 下午04:55:31
 */
public class SmsActionDeal extends ActionRule
{

	private static QueryAdapter queryAdapter = new QueryAdapter();
	private long threadNO = 1;//线程号
	
	public void execute(long p_threadNO)
	{
		threadNO=p_threadNO;
		List<Object> objList = getList(threadNO);
		analySmActionData(objList);
	}
	
	
	protected void analySmActionData(List<Object> p_objList){
		int objListLen = 0;
		if(p_objList!=null)
			objListLen = p_objList.size();
		DataRow dataRow;
		for(int row=0;row<objListLen;row++)
		{
			dataRow = (DataRow) p_objList.get(row);
			dealSmActionData(dataRow);
		}
	}

	protected void dealSmActionData(DataRow dataRow)
	{
		if(dataRow==null)
			return;
		String actionpid = dataRow.getString("pid");
		String actionname = dataRow.getString("actionname");
		String ruletplid = dataRow.getString("ruletplid");
		String dealmode = dataRow.getString("dealmode");
		String isbusinessinformer = dataRow.getString("isbusinessinformer");
		String noticeuserid = dataRow.getString("noticeuserid");
		String noticegroupid = dataRow.getString("noticegroupid");
		String smcontent = dataRow.getString("smcontent");
		String pri = dataRow.getString("pri");
		String slarolemodel = dataRow.getString("slarolemodel");
		String noticeroleid = dataRow.getString("noticeroleid");
		int times= dataRow.getInt("dealtime");//处理次数
		int steptime= dataRow.getInt("steptime");//间隔时间(分钟）
		if(dealmode.equals("1"))
		{   //数据源模式
			DataTable dt = super.getActionBusinessData(actionpid, ruletplid);//根据动作ID和规则ID获取查询的SQL并查询出业务数据
			super.dealActionByDataTableOfSM(dt, actionpid, actionname,
					ruletplid, isbusinessinformer, noticeuserid, noticegroupid,
					smcontent, pri, slarolemodel, noticeroleid,times,steptime);
		}
		else if(dealmode.equals("2"))
		{//规则匹配
			IActionCondition actionCondition = new ActionConditionImpl();
			List<DataRow> businessdataRow = super.getActionBusinessData(actionCondition, "2", actionpid, ruletplid);
			super.dealActionByDataRowListOfSM(businessdataRow, actionpid,
					actionname, ruletplid, isbusinessinformer, noticeuserid,
					noticegroupid, smcontent, pri, slarolemodel, noticeroleid,
					times,steptime);
		}else{
			return;
		}
	}
	
	/**
	 * 扫描短信通知表,并进行节假日、时间(开始时间、结束时间)过滤
	 */
	protected List<Object> getList(long p_threadNo) 
	{
		List<Object> dataList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select action.pid,action.ruletplid,action.actionname,action.dealmode,action.isholiday,action.starttime");
		sql.append(",action.endtime,action.isbusinessinformer,action.noticeuserid,action.noticegroupid,action.smcontent,action.pri,action.slarolemodel,action.noticeroleid");
		sql.append(",dealtime,steptime"); 
		sql.append(" from bs_t_sm_slasmaction action ");
		sql.append(" where action.status = 1 and action.threadno = ? ");
		sql.append(" order by action.pri desc");
		 
		Object[] values = {p_threadNo};  
		DataTable dataTable = queryAdapter.executeQuery(sql.toString(), values, 0, 0, 0);
		int dataTableLen = 0;
		if(dataTable!=null)
			dataTableLen = dataTable.length();
		DataRow dataRow;
		for(int row=0;row<dataTableLen;row++)
		{
			dataRow = dataTable.getDataRow(row);
			//String actionname = StringUtils.checkNullString(dataRow.getString("actionname"));//动作名称
			String isholiday = StringUtils.checkNullString(dataRow.getString("isholiday"));//节假日除外
			String starttime = StringUtils.checkNullString(dataRow.getString("starttime"));//开始时间
			String endtime = StringUtils.checkNullString(dataRow.getString("endtime"));//结束时间
			if(isholiday.equals("1") && IsHoliday.nowIsHoliday())
			{//节假日不接收短信通知
				//RecordLog.printLog("短信通知："+actionname+",今天是假日,不进行短信通知处理!,第："+(row+1)+"条被过滤",RecordLog.LOG_LEVEL_ERROR);
				continue;
			}
			if(!TimeDeal.nowIsScanTime(starttime,endtime))
			{
				//RecordLog.printLog("短信通知："+actionname+",当前时间不满足处理的开始、结束时间,不进行短信通知处理!,第："+(row+1)+"条被过滤",RecordLog.LOG_LEVEL_ERROR);
				continue;
			}
			dataList.add(dataRow);
		}
		return dataList;
	}
}
