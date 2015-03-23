package com.ultrapower.eoms.common.core.component.sms.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ultrapower.eoms.common.core.component.data.DataAdapter;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.component.sms.model.Smsmonitor;
import com.ultrapower.eoms.common.core.util.StringUtils;

/**
 * 操作短信信息表
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-7-29 下午05:26:11
 */
public class ScanSmsmonitor{
	private static DataAdapter dataAdapter  = new DataAdapter();
	public static QueryAdapter queryAdapter = new QueryAdapter();
	private static final int initQueryCount = 100;//初始化查询的条数
	/**
	 * 获取待发送短信息
	 * @return 短信息的集合
	 */
	public List<HashMap<String,String>> getWaitingSendSm(){
		List<HashMap<String,String>> smList =null;
		
		StringBuffer p_sql = new StringBuffer(1024);
		p_sql.append(" select * from (");
		p_sql.append(" select r.*,u.LOGINNAME,u.FULLNAME,u.MOBILE ");
		p_sql.append(" from bs_t_wf_record r , bs_t_sm_user u where r.issms = '1' and (r.smssendflag = '0') ");
		p_sql.append(" and instr(r.nextdealuserloginname,'U:'||u.loginname||';')>0 ");
		p_sql.append(" union all ");
		p_sql.append(" select r.*,u.LOGINNAME,u.FULLNAME,u.MOBILE ");
		p_sql.append(" from bs_t_wf_record r ,bs_t_sm_userdep d , bs_t_sm_user u where r.issms = '1' and (r.smssendflag = '0') ");
		p_sql.append(" and instr(r.nextdealuserloginname,'G:'||d.depid||';')>0 and d.userid = u.pid ");
		p_sql.append(" ) order by pid");
		
		Object[] values = {};
		DataTable dataTable = queryAdapter.executeQuery(p_sql.toString(), values, 0, 0, initQueryCount);
		int dataTableLen = 0;
		if(dataTable!=null)
			dataTableLen = dataTable.length();
		DataRow dataRow;
		HashMap<String,String> smsmonitor = new HashMap<String,String>();
		HashMap<String,String> smsmonitorOld = null;
		String ordPID = "";
		if(dataTableLen>0)
			smList=new ArrayList<HashMap<String,String>>();
		for(int row=0;row<dataTableLen;row++){
			dataRow = dataTable.getDataRow(row);
			if(!"".equals(ordPID) && !StringUtils.checkNullString(dataRow.getString("PID")).equals(ordPID)){
				smsmonitor = new HashMap<String,String>();
			}
			smsmonitor.put("PID", StringUtils.checkNullString(dataRow.getString("PID")));
			smsmonitor.put("BASESCHEMA", StringUtils.checkNullString(dataRow.getString("BASESCHEMA")));
			smsmonitor.put("BASEID", StringUtils.checkNullString(dataRow.getString("BASEID")));
			smsmonitor.put("CURRENTUSER", StringUtils.checkNullString(dataRow.getString("CURRENTUSER")));
			smsmonitor.put("CURRENTUSERLOGINNAME", StringUtils.checkNullString(dataRow.getString("CURRENTUSERLOGINNAME")));
			smsmonitor.put("DEALTIME", StringUtils.checkNullString(dataRow.getString("DEALTIME")));
			smsmonitor.put("DEALDESC", StringUtils.checkNullString(dataRow.getString("DEALDESC")));
			smsmonitor.put("DEALACTION", StringUtils.checkNullString(dataRow.getString("DEALACTION")));
			smsmonitor.put("ISVIEW", StringUtils.checkNullString(dataRow.getString("ISVIEW")));
			smsmonitor.put("NEXTDEALUSER", StringUtils.checkNullString(dataRow.getString("NEXTDEALUSER")));
			smsmonitor.put("NEXTDEALUSERLOGINNAME", StringUtils.checkNullString(dataRow.getString("NEXTDEALUSERLOGINNAME")));
			smsmonitor.put("SMSCONTENT", StringUtils.checkNullString(dataRow.getString("SMSCONTENT")));
			smsmonitor.put("SMSSENDFLAG", StringUtils.checkNullString(dataRow.getString("SMSSENDFLAG")));
			smsmonitor.put("LOGINNAME", StringUtils.checkNullString(dataRow.getString("LOGINNAME")));
			smsmonitor.put("FULLNAME", StringUtils.checkNullString(dataRow.getString("FULLNAME")));
			smsmonitor.put("MOBILE", StringUtils.checkNullString(dataRow.getString("MOBILE")));
			if("".equals(ordPID) || null==smsmonitorOld){
				ordPID = smsmonitor.get("PID");
				smsmonitorOld = smsmonitor;
			}
			if(ordPID.equals(smsmonitor.get("PID"))){
				if(null==smsmonitor.get("MOBILE") || "".equals(smsmonitor.get("MOBILE"))){
					smsmonitor.put("errorinfo", StringUtils.checkNullString(smsmonitor.get("errorinfo"))+smsmonitor.get("FULLNAME")+"("+smsmonitor.get("LOGINNAME")+")"+":手机号为空;");
				}else{
					smsmonitor.put("ALLMOBILE", StringUtils.checkNullString(smsmonitor.get("ALLMOBILE"))+smsmonitor.get("MOBILE")+";");
				}
			}else{
				smList.add(smsmonitorOld);
				ordPID = smsmonitor.get("PID");
				smsmonitorOld = smsmonitor;
				if(null==smsmonitor.get("MOBILE") || "".equals(smsmonitor.get("MOBILE"))){
					smsmonitor.put("errorinfo", smsmonitor.get("FULLNAME")+"("+smsmonitor.get("LOGINNAME")+")"+":手机号为空;");
				}else{
					smsmonitor.put("ALLMOBILE", smsmonitor.get("MOBILE")+";");
				}
			}
		}
		smList.add(smsmonitorOld);
		return smList;
	}
	
	/**
	 * 修改短信息发送状态
	 * @param pid 短信唯一标识
	 * @param sendflag 发送状态  0:未发送;1:已发送;2:发送失败;3:发送异常(组中存在部分用户发送失败);4:服务异常(会再次尝试发送);5:取消发送(手机号为空);
	 * @return 返回 true or false
	 */
	public boolean updateSm(String pid,String sendflag){
		boolean flag = false;
		String tblName = "bs_t_wf_record";
		DataRow p_dtrow = new DataRow();
		p_dtrow.put("SMSSENDFLAG", sendflag);
		String conditions = "PID=?";
		Object[] values = {pid};
		int result = dataAdapter.putDataRow(tblName, p_dtrow, conditions, values);
		if(result>0)
			flag = true;
		return flag;
	} 
	
}
