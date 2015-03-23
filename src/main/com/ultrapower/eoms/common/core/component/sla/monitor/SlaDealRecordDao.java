package com.ultrapower.eoms.common.core.component.sla.monitor;

import com.ultrapower.eoms.common.core.component.data.DataAdapter;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.util.UUIDGenerator;

/**
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-9-16 上午09:47:52
 */
public class SlaDealRecordDao {

	private static QueryAdapter queryAdapter = new QueryAdapter();
	private static DataAdapter dataAdapter = new DataAdapter();
	
	/**
	 * 插入处理记录
	 * @param actionid
	 * @param taskid
	 * @param num
	 * @return
	 */
	public static boolean insert(String actionid,String taskid,int num){
		boolean flag = false;
		DataRow p_dtrow = new DataRow();
		p_dtrow.put("PID", UUIDGenerator.getUUIDoffSpace());
		p_dtrow.put("RULEID", actionid);
		p_dtrow.put("TASKID", taskid);
		p_dtrow.put("DEALNUM", num);
		p_dtrow.put("DEALTIME", TimeUtils.getCurrentTime());
		int result = dataAdapter.putDataRow("bs_t_sm_sladealrecord", p_dtrow, "", null);
		if(result==1)
			flag = true;
		return flag;
	}
	
	/**
	 * 查询是否已经处理,返回已经处理的次数和最后一次的处理时间
	 * @param actionid
	 * @param taskpid
	 * @return
	 */
	public static String  isDeal(String actionid,String taskpid){
		StringBuffer result=new StringBuffer();
		String qty;
		String dealtime;
		String sql = "select count(*) qty,max(ruleid) ruleid  from bs_t_sm_sladealrecord  where ruleid like ? and taskid = ?";
		Object[] values = {actionid+"%",taskpid};
		DataTable dataTable = queryAdapter.executeQuery(sql, values);
		if(dataTable!=null)
		{
			DataRow dr=dataTable.getDataRow(0);
			if(dr!=null)
			{
				qty=dr.getString("qty");
				dealtime=dr.getString("ruleid");
				result.append(qty);
				result.append(";");
				result.append(dealtime);
				
			}
		}
		return result.toString();
	}
}
