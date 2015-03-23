package com.ultrapower.eoms.common.core.component.sla.util;

import java.util.ArrayList;
import java.util.List;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.util.StringUtils;

/**
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-9-14 下午03:35:52
 */
public class NoticeParaDeal {

	public static QueryAdapter queryAdapter = new QueryAdapter();
	
	
	public static List<String> getMobileByBusinessNoticeUserOfCompany(String businessNoticeUser){
		if(businessNoticeUser == "")
			return null;
		
		StringBuffer sql = new StringBuffer(1024);
		sql.append("select sysuser.mobile");
		sql.append(" from bs_t_sm_roleorg t, bs_t_sm_user sysuser");
		sql.append(" where t.orgid = sysuser.pid");
		sql.append("  and t.roleid in");
		sql.append(" (select t.roleid  from bs_t_sm_roleorg t");
		sql.append("  where t.orgid in (");
		sql.append(" select pid from bs_t_sm_dep");
		sql.append(" where to_number(deptype) < 3");
		sql.append(" start with pid =(select depid from bs_t_sm_user  where pid = ? )");
		sql.append(" connect by prior parentid = pid");
		sql.append(" ) and t.orgtype = 2)");
		sql.append("  and t.orgtype = 1 and sysuser.mobile > 0");
		String[] value = {businessNoticeUser};
		DataTable dataTable = queryAdapter.executeQuery(sql.toString(), value,2);
		int dataTableLen = 0;
		if(dataTable!=null)
			dataTableLen = dataTable.length();
		DataRow dataRow;
		List<String> list = new ArrayList<String>();
		for(int row=0;row<dataTableLen;row++){
			dataRow = dataTable.getDataRow(row);
			String mobile = StringUtils.checkNullString(dataRow.getString("mobile"));
			list.add(mobile);
		}
		return list;
	}
	
	public static List<String> getMobileByBusinessNoticeUser(String businessNoticeUser){
		if(businessNoticeUser == "")
			return null;
		
		StringBuffer sql = new StringBuffer(1024);
		sql.append("select sysuser.mobile");
		sql.append(" from bs_t_sm_roleorg t, bs_t_sm_user sysuser");
		sql.append("  where t.orgid = sysuser.pid");
		sql.append(" and t.roleid in");
		sql.append(" (select t.roleid");
		sql.append(" from bs_t_sm_roleorg t");
		sql.append("  where t.orgid = (select sysuser.depid from bs_t_sm_user sysuser  where sysuser.depid > '0'  and sysuser.pid = ?)");
		sql.append(" and t.orgtype = 2)");
		sql.append(" and t.orgtype = 1 and sysuser.mobile > 0");
		String[] value = {businessNoticeUser};
		DataTable dataTable = queryAdapter.executeQuery(sql.toString(), value,2);
		int dataTableLen = 0;
		if(dataTable!=null)
			dataTableLen = dataTable.length();
		DataRow dataRow;
		List<String> list = new ArrayList<String>();
		for(int row=0;row<dataTableLen;row++){
			dataRow = dataTable.getDataRow(row);
			String mobile = StringUtils.checkNullString(dataRow.getString("mobile"));
			list.add(mobile);
		}
		return list;
	}
	
	/**
	 * 
	 * @param roleid
	 * @param depOrCompanyId
	 * @return
	 */
	public static List<String> getEmailByDepOrCompanyId(final String roleid,final String depOrCompanyId){
		if(roleid == ""||depOrCompanyId == "")
			return null;
		
		String param = "";
		
		String[] roleidArr = roleid.split(",");
		for(int i=0;i<roleidArr.length;i++){
			param += " t.orgid = '"+roleidArr[i]+"' or";
		}
				
		if(param!=""){
			param = param.substring(0, param.length()-2);
			param = " and (" + param + ")";
		}
		
		StringBuffer sql = new StringBuffer(1024);
		sql.append("select sysuser.email");
		sql.append(" from bs_t_sm_businessorganddep  b_orgDep,");
		sql.append(" bs_t_sm_businessorganduser b_OrgUser,");
		sql.append(" bs_t_sm_user sysuser");
		sql.append(" where b_orgDep.Orgid = b_OrgUser.Orgid");
		sql.append(" and sysuser.pid = b_OrgUser.Userid");
		sql.append(" and sysuser.status = 1");
		sql.append(" and sysuser.email > '0'");
		sql.append(" and t.depid = ? ");
		sql.append(param);
		String[] value = {depOrCompanyId};
		DataTable dataTable = queryAdapter.executeQuery(sql.toString(), value,2);
		int dataTableLen = 0;
		if(dataTable!=null)
			dataTableLen = dataTable.length();
		DataRow dataRow;
		List<String> list = new ArrayList<String>();
		for(int row=0;row<dataTableLen;row++){
			dataRow = dataTable.getDataRow(row);
			String mobile = StringUtils.checkNullString(dataRow.getString("email"));
			list.add(mobile);
		}
		return list;
	}
	
	/**
	 * 
	 * @param roleid
	 * @param depOrCompanyId
	 * @return
	 */
	public static List<String> getMobileByDepOrCompanyId(final String roleid,final String depOrCompanyId){
		if(roleid == ""||depOrCompanyId == "")
			return null;
		
		String param = "";
		
		String[] roleidArr = roleid.split(",");
		for(int i=0;i<roleidArr.length;i++){
			param += " t.orgid = '"+roleidArr[i]+"' or";
		}
				
		if(param!=""){
			param = param.substring(0, param.length()-2);
			param = " and (" + param + ")";
		}
		
		StringBuffer sql = new StringBuffer(1024);
		sql.append("select sysuser.mobile");
		sql.append(" from bs_t_sm_businessorganddep  b_orgDep,");
		sql.append(" bs_t_sm_businessorganduser b_OrgUser,");
		sql.append(" bs_t_sm_user sysuser");
		sql.append(" where b_orgDep.Orgid = b_OrgUser.Orgid");
		sql.append(" and sysuser.pid = b_OrgUser.Userid");
		sql.append(" and sysuser.status = 1");
		sql.append(" and sysuser.mobile > 0");
		sql.append(" and t.depid = ?");
		sql.append(param);
		String[] value = {depOrCompanyId};
		DataTable dataTable = queryAdapter.executeQuery(sql.toString(), value,2);
		int dataTableLen = 0;
		if(dataTable!=null)
			dataTableLen = dataTable.length();
		DataRow dataRow;
		List<String> list = new ArrayList<String>();
		for(int row=0;row<dataTableLen;row++){
			dataRow = dataTable.getDataRow(row);
			String mobile = StringUtils.checkNullString(dataRow.getString("mobile"));
			list.add(mobile);
		}
		return list;
	}
	
	/**
	 * 
	 * @param roleid
	 * @return
	 */
	public static List<String> getEmailByRole(final String roleid){
		String param = "";
		
		if(roleid == "")
			return null;
		
		String[] useridArr = roleid.split(",");
		for(int i=0;i<useridArr.length;i++){
			param += " t.orgid = '"+useridArr[i]+"' or";
		}
				
		if(param!=""){
			param = param.substring(0, param.length()-2);
			param = " and (" + param + ")";
		}
		
		List<String> list = new ArrayList<String>();
	   
		StringBuffer sql = new StringBuffer();
		sql.append("select sysuser.email");
		sql.append(" from BS_T_SM_BUSINESSORGANDUSER t, Bs_t_Sm_User sysuser");
		sql.append(" where sysuser.pid = t.userid");
		sql.append(" and sysuser.email > 0");
		sql.append(" and sysuser.status = 1");
		sql.append(param);

		DataTable dataTable = queryAdapter.executeQuery(sql.toString(), null,2);
		int dataTableLen = 0;
		if(dataTable!=null)
			dataTableLen = dataTable.length();
		DataRow dataRow;
		for(int row=0;row<dataTableLen;row++){
			dataRow = dataTable.getDataRow(row);
			String mobile = StringUtils.checkNullString(dataRow.getString("email"));
			list.add(mobile);
		}
		return list;
	}
	
	/**
	 * 角色查询手机号码
	 * @param roleid
	 * @return
	 */
	public static List<String> getMobileByRoleId(final String roleid){
		String param = "";
		
		if(roleid == "")
			return null;
		
		String[] useridArr = roleid.split(",");
		for(int i=0;i<useridArr.length;i++){
			param += " t.orgid = '"+useridArr[i]+"' or";
		}
				
		if(param!=""){
			param = param.substring(0, param.length()-2);
			param = " and (" + param + ")";
		}
		
		List<String> list = new ArrayList<String>();
	   
		StringBuffer sql = new StringBuffer();
		sql.append("select sysuser.mobile");
		sql.append(" from BS_T_SM_BUSINESSORGANDUSER t, Bs_t_Sm_User sysuser");
		sql.append(" where sysuser.pid = t.userid");
		sql.append(" and sysuser.mobile > 0");
		sql.append(" and sysuser.status = 1");
		sql.append(param);

		DataTable dataTable = queryAdapter.executeQuery(sql.toString(), null,2);
		int dataTableLen = 0;
		if(dataTable!=null)
			dataTableLen = dataTable.length();
		DataRow dataRow;
		for(int row=0;row<dataTableLen;row++){
			dataRow = dataTable.getDataRow(row);
			String mobile = StringUtils.checkNullString(dataRow.getString("mobile"));
			list.add(mobile);
		}
		return list;
	}
	
	public static List<String> getMolbileByRoleGroup(final String roleGroup){
		if(roleGroup=="")
			return null;
		List<String> list = new ArrayList<String>(); 
		
		StringBuffer p_sql = new StringBuffer();;
		p_sql.append("select sysuser.mobile from bs_t_sm_user sysuser, bs_t_wf_roleuser roleuser where roleuser.loginname = sysuser.loginname and sysuser.mobile > 0 ");
		
		String sql = "";
		String[] useridArr = roleGroup.split(",");
		for(int i=0;i<useridArr.length;i++){
			sql += " roleuser.childroleid = '"+useridArr[i]+"' or";
		}
		
		if(sql!=""){
			sql = sql.substring(0, sql.length()-2);
			sql = " and (" + sql + ")";
		}
		
		DataTable dataTable = queryAdapter.executeQuery(p_sql.toString() + sql, null,2);
		int dataTableLen = 0;
		if(dataTable!=null)
			dataTableLen = dataTable.length();
		DataRow dataRow;
		for(int row=0;row<dataTableLen;row++){
			dataRow = dataTable.getDataRow(row);
			String mobile = StringUtils.checkNullString(dataRow.getString("mobile"));
			list.add(mobile);
		}
		
		return list;
	}
	
	public static List<String> getMobileByLoginName(final String loginName){

		if(loginName=="")
			return null;
		List<String> list = new ArrayList<String>(); 
		
		StringBuffer p_sql = new StringBuffer();;
		p_sql.append("select mobile from bs_t_sm_user where mobile > 0 ");
		
		String sql = "";
		String[] useridArr = loginName.split(",");
		for(int i=0;i<useridArr.length;i++){
			sql += " loginname = '"+useridArr[i]+"' or";
		}
		
		if(sql!=""){
			sql = sql.substring(0, sql.length()-2);
			sql = " and (" + sql + ")";
		}
		
		DataTable dataTable = queryAdapter.executeQuery(p_sql.toString() + sql, null,2);
		int dataTableLen = 0;
		if(dataTable!=null)
			dataTableLen = dataTable.length();
		DataRow dataRow;
		for(int row=0;row<dataTableLen;row++){
			dataRow = dataTable.getDataRow(row);
			String mobile = StringUtils.checkNullString(dataRow.getString("mobile"));
			list.add(mobile);
		}
		
		return list;
	}
	
	/**
	 * 查询用户手机号码
	 * @param userid eg：97e39d1298150e8012981703b700002,4028940b2aa8255a012aa83ac02d0001,402894aa29eebd730129eed8e9e000d3
	 * @return
	 */
	public static List<String> getMobileByUserId(final String userid){
		if(userid=="")
			return null;
		List<String> list = new ArrayList<String>(); 
		
		StringBuffer p_sql = new StringBuffer();;
		p_sql.append("select mobile from bs_t_sm_user where mobile > 0 ");
		
		String sql = "";
		String[] useridArr = userid.split(",");
		for(int i=0;i<useridArr.length;i++){
			sql += " pid = '"+useridArr[i]+"' or";
		}
		
		if(sql!=""){
			sql = sql.substring(0, sql.length()-2);
			sql = " and (" + sql + ")";
		}
		
		DataTable dataTable = queryAdapter.executeQuery(p_sql.toString() + sql, null,2);
		int dataTableLen = 0;
		if(dataTable!=null)
			dataTableLen = dataTable.length();
		DataRow dataRow;
		for(int row=0;row<dataTableLen;row++){
			dataRow = dataTable.getDataRow(row);
			String mobile = StringUtils.checkNullString(dataRow.getString("mobile"));
			list.add(mobile);
		}
		
		return list;
	}
	
	/**
	 * 查询用户手机号码
	 * @param groupid eg：97e39d1298150e8012981703b700002,4028940b2aa8255a012aa83ac02d0001,402894aa29eebd730129eed8e9e000d3
	 * @return
	 */
	public static List<String> getMobileByGroupId(final String groupid){
		if(groupid == "")
			return null;

		List<String> list = new ArrayList<String>();
		
		StringBuffer p_sql = new StringBuffer();
		p_sql.append("select sysuser.mobile");
		p_sql.append(" from bs_t_sm_user sysuser, bs_t_sm_userdep sysdep");
		p_sql.append(" where sysuser.pid = sysdep.userid");
		p_sql.append(" and sysuser.status = 1");
		p_sql.append(" and sysuser.mobile > 0");
		String sql = "";
		String[] groupidArr = groupid.split(",");
		for(int i=0;i<groupidArr.length;i++){
			sql += " sysdep.depid = '"+groupidArr[i]+"' or";
		}
		   
		if(sql!=""){
			sql = sql.substring(0, sql.length()-2);
			sql = " and (" + sql + ")";
		}
		  
		DataTable dataTable = queryAdapter.executeQuery(p_sql.toString() + sql, null,2);
		int dataTableLen = 0;
		if(dataTable!=null)
			dataTableLen = dataTable.length();
		DataRow dataRow;
		for(int row=0;row<dataTableLen;row++){
			dataRow = dataTable.getDataRow(row);
			String mobile = StringUtils.checkNullString(dataRow.getString("mobile"));
			list.add(mobile);
		}
		 
		return list;
	}
	
	
	public static List<String> getEmailByRoleGroupId(final String roleGroupid){
		if(roleGroupid == "")
			return null;
		List<String> list = new ArrayList<String>(); 
		
		StringBuffer p_sql = new StringBuffer();;
		p_sql.append("select sysuser.email from bs_t_sm_user sysuser, bs_t_wf_roleuser roleuser where roleuser.loginname = sysuser.loginname and sysuser.email > '0' ");
		
		String sql = "";
		String[] useridArr = roleGroupid.split(",");
		for(int i=0;i<useridArr.length;i++){
			sql += " roleuser.childroleid = '"+useridArr[i]+"' or";
		}
		
		if(sql!=""){
			sql = sql.substring(0, sql.length()-2);
			sql = " and (" + sql + ")";
		}
		
		DataTable dataTable = queryAdapter.executeQuery(p_sql.toString() + sql, null,2);
		int dataTableLen = 0;
		if(dataTable!=null)
			dataTableLen = dataTable.length();
		DataRow dataRow;
		for(int row=0;row<dataTableLen;row++){
			dataRow = dataTable.getDataRow(row);
			String mobile = StringUtils.checkNullString(dataRow.getString("email"));
			list.add(mobile);
		}
		
		return list;
	}
	
	/**
	 * 查询用户邮箱地址
	 * @param groupid
	 * @return
	 */
	public static List<String> getEmailByGroupId(final String groupid){
		if(groupid == "")
			return null;

		List<String> list = new ArrayList<String>();
		
		StringBuffer p_sql = new StringBuffer();
		p_sql.append("select sysuser.email");
		p_sql.append(" from bs_t_sm_user sysuser, bs_t_sm_userdep sysdep");
		p_sql.append(" where sysuser.pid = sysdep.userid");
		p_sql.append(" and sysuser.status = 1");
		p_sql.append(" and sysuser.email > '0'");
		String sql = "";
		String[] groupidArr = groupid.split(",");
		for(int i=0;i<groupidArr.length;i++){
			sql += " sysdep.depid = '"+groupidArr[i]+"' or";
		}
		   
		if(sql!=""){
			sql = sql.substring(0, sql.length()-2);
			sql = " and (" + sql + ")";
		}
		  
		DataTable dataTable = queryAdapter.executeQuery(p_sql.toString() + sql, null,2);
		int dataTableLen = 0;
		if(dataTable!=null)
			dataTableLen = dataTable.length();
		DataRow dataRow;
		for(int row=0;row<dataTableLen;row++){
			dataRow = dataTable.getDataRow(row);
			String email = StringUtils.checkNullString(dataRow.getString("email"));
			list.add(email);
		}
		 
		return list;
	}
	
	/**
	 * 查询用户邮箱
	 * @param userid
	 * @return
	 */
	public static List<String> getEmailByUserId(final String userid){
		if(userid=="")
			return null;
		List<String> list = new ArrayList<String>(); 
		
		StringBuffer p_sql = new StringBuffer();;
		p_sql.append("select email from bs_t_sm_user where email > '0' ");
		
		String sql = "";
		String[] useridArr = userid.split(",");
		for(int i=0;i<useridArr.length;i++){
			sql += " pid = '"+useridArr[i]+"' or";
		}
		
		if(sql!=""){
			sql = sql.substring(0, sql.length()-2);
			sql = " and (" + sql + ")";
		}
		
		DataTable dataTable = queryAdapter.executeQuery(p_sql.toString() + sql, null,2);
		int dataTableLen = 0;
		if(dataTable!=null)
			dataTableLen = dataTable.length();
		DataRow dataRow;
		for(int row=0;row<dataTableLen;row++){
			dataRow = dataTable.getDataRow(row);
			String email = StringUtils.checkNullString(dataRow.getString("email"));
			list.add(email);
		}
		return list;
	}
}
