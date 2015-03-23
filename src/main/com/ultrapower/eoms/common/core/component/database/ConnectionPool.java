package com.ultrapower.eoms.common.core.component.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.ultrapower.eoms.common.constants.Constants;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.util.StringUtils;

public class ConnectionPool {

	/**
	 * 根据连接池名称获得数据连接,连接池名为空时去security.properties 文件中的jdbc.alias
	 * @param poolname 连接池名
	 * @return 数据库连接Connection
	 * @throws SQLException
	 */
	public static synchronized Connection getConn(String poolname) throws SQLException
	{
		String alias = StringUtils.checkNullString(poolname).trim();
		if (alias.equals("")) 
		{
			alias = StringUtils.checkNullString(Constants.DATABASE_ALIAS);
		}
		try {
			if(!alias.equals(""))
			{
				Class.forName("org.logicalcobwebs.proxool.ProxoolDriver");
				Connection conn = DriverManager.getConnection("proxool." + alias);
				return conn;
			}
			else
			{
				System.out.println(TimeUtils.getCurrentDate("yyyy-MM-dd HH:mm:ss")+" alias为空，获取连接失败!");
			}
		} catch (Exception ex) {
			System.out.println(TimeUtils.getCurrentDate("yyyy-MM-dd HH:mm:ss")+" 获取连接失败: "+ex.getMessage());
			ex.printStackTrace();
		}
		return null; 			
		
	}
	/**
	 * 取默认数据连接
	 * @return Connection
	 * @throws SQLException
	 */
	public static synchronized Connection getConn() throws SQLException
	{
		return getConn("");
	}
	
	/** 
	* 释放连接 freeConnection 
	* @param conn 
	*/ 
	public static void free(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	} 	
}
