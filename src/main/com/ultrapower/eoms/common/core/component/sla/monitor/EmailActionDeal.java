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
import com.ultrapower.eoms.common.core.util.StringUtils;

/**
 * SLA--邮件业务
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-9-15 上午10:43:09
 */
public class EmailActionDeal extends ActionRule{

	private static QueryAdapter queryAdapter = new QueryAdapter();
	private long threadNO = 1;//线程号
	
	
	public void execute(final long p_threadNo) 
	{
		threadNO=p_threadNo;
		List<Object> objList = getList(threadNO);
		analySmActionData(objList);
	}
	
	protected void analySmActionData(List<Object> p_objList){
		int objListLen = 0;
		if(p_objList!=null)
			objListLen = p_objList.size();
		DataRow dataRow;
		for(int row=0;row<objListLen;row++){
			dataRow = (DataRow)p_objList.get(row);
			dealSmActionData(dataRow);
		}
	}
	
	protected void dealSmActionData(DataRow dataRow){
		
		if(dataRow==null)
			return;
		
		String pid = dataRow.getString("pid");
		String actionname = dataRow.getString("actionname");
		String ruletplid = dataRow.getString("ruletplid");
		String dealmode = dataRow.getString("dealmode");
		String isbusinessinformer = dataRow.getString("isbusinessinformer");
		String noticeuserid = dataRow.getString("noticeuserid");
		String noticegroupid = dataRow.getString("noticegroupid");
		String copyuserid = dataRow.getString("copyuserid");
		String copygroupid = dataRow.getString("copygroupid");
		String mailsubject = dataRow.getString("mailsubject");
		String mailcontent = dataRow.getString("mailcontent");
		String slarolemodel = dataRow.getString("slarolemodel");
		String noticeroleid = dataRow.getString("noticeroleid");
		int times= dataRow.getInt("dealtime");//处理次数
		int steptime= dataRow.getInt("steptime");//间隔时间(分钟）
		if(dealmode.equals("1")){//数据源
			DataTable dt = super.getActionBusinessData(pid, ruletplid);
			super.dealActionByDataTableOfEmail(dt,pid,actionname,ruletplid,isbusinessinformer,noticeuserid,noticegroupid,copyuserid,copygroupid,mailsubject,mailcontent,slarolemodel,noticeroleid,times,steptime);
		}else if(dealmode.equals("2")){//规则匹配
			IActionCondition actionCondition = new ActionConditionImpl();
			List<DataRow> businessdataRow = super.getActionBusinessData(actionCondition, "2", pid, ruletplid);
			super.dealActionByDataRowListOfEmail(businessdataRow,pid,actionname,ruletplid,isbusinessinformer,noticeuserid,noticegroupid,copyuserid,copygroupid,mailsubject,mailcontent,slarolemodel,noticeroleid,times,steptime);
		}else{
			return;
		}
	}

	/**
	 * 查询邮件动作表,并进行节假日过滤
	 */
	protected List<Object> getList(long p_threadNo) {
		List<Object> listData = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select action.pid, action.ruletplid, action.actionname, action.dealmode, action.pri,");
		sql.append(" action.isholiday, action.isbusinessinformer, action.noticeuserid, action.noticegroupid,");
		sql.append(" action.copyuserid, action.copygroupid, action.mailsubject, action.mailcontent,action.slarolemodel,action.noticeroleid");
		sql.append(" from bs_t_sm_slamailaction action where action.status = 1 and action.threadno = ? order by action.pri desc");
		
		Object[] values = {p_threadNo};
		DataTable dataTable = queryAdapter.executeQuery(sql.toString(), values, 0, 0, 0);
	    int dataTableLen = 0;
	    if(dataTable!=null)
	       dataTableLen = dataTable.length();
	    DataRow dataRow;
	    for(int row=0;row<dataTableLen;row++){
	    	dataRow = dataTable.getDataRow(row);
	    	String actionname = StringUtils.checkNullString(dataRow.getString("actionname"));//动作名称
			String isholiday = StringUtils.checkNullString(dataRow.getString("isholiday"));//节假日除外
			if(isholiday.equals("1") && IsHoliday.nowIsHoliday()){//节假日不接收邮件通知
				RecordLog.printLog("邮件通知："+actionname+",今天是假日,不进行邮件通知处理!,第："+(row+1)+"条被过滤",RecordLog.LOG_LEVEL_ERROR);
				continue;
			}
			listData.add(dataRow);
	    }
		return listData;
	}
}
