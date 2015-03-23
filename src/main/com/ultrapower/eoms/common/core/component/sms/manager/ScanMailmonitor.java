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
 */
public class ScanMailmonitor{
	private static DataAdapter dataAdapter  = new DataAdapter();
	public static QueryAdapter queryAdapter = new QueryAdapter();
	private static final int initQueryCount = 100;//初始化查询的条数
	/**
	 * 获取待发送邮件信息
	 * @return 邮件信息的集合
	 */
	public List<HashMap<String,String>> getWaitingSendMail(){
		List<HashMap<String,String>> mailList =null;
		
		StringBuffer p_sql = new StringBuffer(1024);
		//查询bs_t_wf_record表中待发送记录
		//发送状态  0:未发送;1:已发送;2:发送失败;3:发送异常(组中存在部分用户发送失败);4:服务异常;5:取消发送(手机号为空)
		p_sql.append(" select * from (");
		p_sql.append(" select r.*,u.LOGINNAME,u.FULLNAME,u.EMAIL,b.BASENAME,b.BASESN,b.BASESUMMARY,'USER' DEALOBJECT ");
		p_sql.append(" from bs_t_wf_record r , bs_t_sm_user u, bs_t_bpp_baseinfor b where r.isemail = '1' and r.emailsendflag = '0' ");
		p_sql.append(" and instr(r.nextdealuserloginname,'U:'||u.loginname||';')>0 ");
		p_sql.append(" and b.baseid=r.baseid ");
		p_sql.append(" union all ");
		p_sql.append(" select r.*,u.LOGINNAME,u.FULLNAME,u.EMAIL,b.BASENAME,b.BASESN,b.BASESUMMARY,'GROUP' DEALOBJECT ");
		p_sql.append(" from bs_t_wf_record r ,bs_t_sm_userdep d , bs_t_sm_user u, bs_t_bpp_baseinfor b where r.isemail = '1' and r.emailsendflag = '0' ");
		p_sql.append(" and instr(r.nextdealuserloginname,'G:'||d.depid||';')>0 and d.userid = u.pid ");
		p_sql.append(" and b.baseid=r.baseid ");
		p_sql.append(" ) order by pid");
		
		Object[] values = {};
		DataTable dataTable = queryAdapter.executeQuery(p_sql.toString(), values, 0, 0, initQueryCount);
		int dataTableLen = 0;
		if(dataTable!=null)
			dataTableLen = dataTable.length();
		DataRow dataRow;
		HashMap<String,String> mailmonitor = new HashMap<String,String>();
		HashMap<String,String> mailmonitorOld = null;
		String ordPID = "";
		if(dataTableLen>0)
			mailList=new ArrayList<HashMap<String,String>>();
		for(int row=0;row<dataTableLen;row++){
			dataRow = dataTable.getDataRow(row);
			if(!"".equals(ordPID) && !StringUtils.checkNullString(dataRow.getString("PID")).equals(ordPID)){
				mailmonitor = new HashMap<String,String>();
			}
			mailmonitor.put("PID", StringUtils.checkNullString(dataRow.getString("PID")));
			mailmonitor.put("BASESCHEMA", StringUtils.checkNullString(dataRow.getString("BASESCHEMA")));
			mailmonitor.put("BASEID", StringUtils.checkNullString(dataRow.getString("BASEID")));
			mailmonitor.put("CURRENTUSER", StringUtils.checkNullString(dataRow.getString("CURRENTUSER")));
			mailmonitor.put("CURRENTUSERLOGINNAME", StringUtils.checkNullString(dataRow.getString("CURRENTUSERLOGINNAME")));
			mailmonitor.put("DEALTIME", StringUtils.checkNullString(dataRow.getString("DEALTIME")));
			mailmonitor.put("DEALDESC", StringUtils.checkNullString(dataRow.getString("DEALDESC")));
			mailmonitor.put("DEALACTION", StringUtils.checkNullString(dataRow.getString("DEALACTION")));
			mailmonitor.put("ISVIEW", StringUtils.checkNullString(dataRow.getString("ISVIEW")));
			mailmonitor.put("NEXTDEALUSER", StringUtils.checkNullString(dataRow.getString("NEXTDEALUSER")));
			mailmonitor.put("NEXTDEALUSERLOGINNAME", StringUtils.checkNullString(dataRow.getString("NEXTDEALUSERLOGINNAME")));
			mailmonitor.put("EMAILCONTENT", StringUtils.checkNullString(dataRow.getString("EMAILCONTENT")));
			mailmonitor.put("EMAILSENDFLAG", StringUtils.checkNullString(dataRow.getString("EMAILSENDFLAG")));
			mailmonitor.put("LOGINNAME", StringUtils.checkNullString(dataRow.getString("LOGINNAME")));
			mailmonitor.put("FULLNAME", StringUtils.checkNullString(dataRow.getString("FULLNAME")));
			mailmonitor.put("EMAIL", StringUtils.checkNullString(dataRow.getString("EMAIL")));
			mailmonitor.put("BASENAME", StringUtils.checkNullString(dataRow.getString("BASENAME")));
			mailmonitor.put("BASESN", StringUtils.checkNullString(dataRow.getString("BASESN")));
			mailmonitor.put("BASESUMMARY", StringUtils.checkNullString(dataRow.getString("BASESUMMARY")));
			//初始化，只会执行一次
			if("".equals(ordPID) || null==mailmonitorOld){
				ordPID = mailmonitor.get("PID");
				mailmonitorOld = mailmonitor;
			}
			//第一次时PID必然相等
			if(ordPID.equals(mailmonitor.get("PID"))){
				if(null==mailmonitor.get("EMAIL") || "".equals(mailmonitor.get("EMAIL"))){
					mailmonitor.put("errorinfo", StringUtils.checkNullString(mailmonitor.get("errorinfo"))+mailmonitor.get("FULLNAME")+"("+mailmonitor.get("LOGINNAME")+")"+":EMAIL为空;");
				}else{
					mailmonitor.put("ALLEMAIL", StringUtils.checkNullString(mailmonitor.get("ALLEMAIL"))+mailmonitor.get("EMAIL")+";");
				}
				//当查询出是GROUP时做替换
				if(null==mailmonitor.get("DEALOBJECT") || "".equals(mailmonitor.get("DEALOBJECT")) || "GROUP".equals(StringUtils.checkNullString(dataRow.getString("DEALOBJECT")))){
					mailmonitor.put("DEALOBJECT", StringUtils.checkNullString(dataRow.getString("DEALOBJECT")).equals("GROUP")?"给您所在的工作组":"给您");
				}
			}else{
				mailList.add(mailmonitorOld);
				ordPID = mailmonitor.get("PID");
				mailmonitorOld = mailmonitor;
				if(null==mailmonitor.get("EMAIL") || "".equals(mailmonitor.get("EMAIL"))){
					mailmonitor.put("errorinfo", mailmonitor.get("FULLNAME")+"("+mailmonitor.get("LOGINNAME")+")"+":EMAIL为空;");
				}else{
					mailmonitor.put("ALLEMAIL", mailmonitor.get("EMAIL")+";");
				}
				mailmonitor.put("DEALOBJECT", StringUtils.checkNullString(dataRow.getString("DEALOBJECT")).equals("GROUP")?"给您所在的工作组":"给您");
			}
		}
		if(null!=mailList){
			mailList.add(mailmonitorOld);
		}
		return mailList;
	}
	
	/**
	 * 修改EMAIL发送状态
	 * @param pid 邮件唯一标识
	 * @param sendflag 发送状态  0:未发送;1:已发送;2:发送失败;3:发送异常(组中存在部分用户发送失败);4:服务异常(会再次尝试发送);5:取消发送(手机号为空);
	 * @return 返回 true or false
	 */
	public boolean updateEmail(String pid,String sendflag){
		boolean flag = false;
		String tblName = "bs_t_wf_record";
		DataRow p_dtrow = new DataRow();
		p_dtrow.put("EMAILSENDFLAG", sendflag);
		String conditions = "PID=?";
		Object[] values = {pid};
		int result = dataAdapter.putDataRow(tblName, p_dtrow, conditions, values);
		if(result>0)
			flag = true;
		return flag;
	} 
	
}
