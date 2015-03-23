package com.ultrapower.remedy4j.common;

import java.util.ArrayList;
import java.util.List;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.remedy4j.core.RemedyField;
import com.ultrapower.remedy4j.core.RemedyFieldType;
import com.ultrapower.remedy4j.core.RemedySession;

/**
 * 对Remedy用户操作的类
 * @author BigMouse
 * @version UltraPower-EOMS v4.0
 * @since jdk1.5
 */
public class RemedyUserManagerImpl implements IRemedyUserManager
{
	/**
	 * 向Remedy中添加用户
	 * @param loginName 用户登录名
	 * @param password 用户密码
	 * @param fullName 用户全名
	 * @param licenseType license类型。0：读取；1：固定；2：浮动3：有限读取
	 * @return 是否添加成功
	 */
	public boolean addUser(String loginName, String password, String fullName)
	{
		List<RemedyField> fields = new ArrayList<RemedyField>();
		fields.add(new RemedyField("2", "Creator", RemedyFieldType.CHAR, "Demo"));
		fields.add(new RemedyField("101", "Login Name", RemedyFieldType.CHAR, loginName));
		fields.add(new RemedyField("8", "Full Name", RemedyFieldType.CHAR, fullName));
		fields.add(new RemedyField("102", "Password", RemedyFieldType.CHAR, password));
		fields.add(new RemedyField("109", "License Type", RemedyFieldType.CHAR, "0"));
		RemedySession session = new RemedySession("Demo", RemedySession.UtilInfor.DEMO_PASSWORD);
		session.login();
		String c1 = session.addEntry("User", fields);
		session.logout();
		if(c1 != null && !c1.equals(""))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 修改Remedy用户信息
	 * @param loginName 用户登录名
	 * @param password 用户密码
	 * @param fullName 用户全名
	 * @param licenseType license类型。0：读取；1：固定；2：浮动3：有限读取
	 * @return 是否修改成功
	 */
	public boolean updateUser(String loginName, String password, String fullName)
	{
		List<RemedyField> fields = new ArrayList<RemedyField>();
		//fields.add(new RemedyField("2", "Creator", RemedyFieldType.CHAR, "Demo"));
		//fields.add(new RemedyField("101", "Login Name", RemedyFieldType.CHAR, loginName));
		fields.add(new RemedyField("8", "Full Name", RemedyFieldType.CHAR, fullName));
		fields.add(new RemedyField("102", "Password", RemedyFieldType.CHAR, password));
		
		boolean success = false;
		String userC1 = getUserC1(loginName);
		
		if(userC1 != null)
		{
			RemedySession session = new RemedySession("Demo", RemedySession.UtilInfor.DEMO_PASSWORD);
			success = session.updateEntry("User", userC1, fields);
		}
		
		return success;
	}
	
	/**
	 * 删除Remedy用户信息
	 * @param loginName 用户登录名
	 * @return 是否删除成功
	 */
	public boolean deleteUser(String loginName)
	{
		boolean success = false;
		if(!loginName.equals("Demo"))
		{
			String userC1 = getUserC1(loginName);
			
			if(userC1 != null)
			{
				RemedySession session = new RemedySession("Demo", RemedySession.UtilInfor.DEMO_PASSWORD);
				success = session.deleteEntry("User", userC1);
			}
		}
		
		return success;
	}

	/**
	 * 获取Remedy中用户的C1
	 * @param loginName 用户登录名
	 * @return Remedy中用户的C1
	 */
	private String getUserC1(String loginName)
	{
		String userC1 = null;
		String userTable = RemedySession.UtilInfor.getTableName("User");
		String queryC1 = "select C1 from " + userTable + " where C101 = '" + loginName + "'";
		QueryAdapter con  = new QueryAdapter();
		DataTable rs = con.executeQuery(queryC1, null, 0);
		if(rs.length() > 0)
		{
			DataRow row = rs.getDataRow(0);
			userC1 = row.getString("C1");
		}
		return userC1;
	}
}
