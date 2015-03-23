package com.ultrapower.remedy4j.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bmc.arsys.api.ARException;
import com.bmc.arsys.api.ARServerUser;
import com.bmc.arsys.api.DataType;
import com.bmc.arsys.api.Entry;
import com.bmc.arsys.api.SignalType;
import com.bmc.arsys.api.Value;
import com.ultrapower.remedy4j.RecordLog;
import com.ultrapower.remedy4j.core.UtilInforHandler.SessionType;

/**
 * 对Remedy进行数据操作的类
 * @author BigMouse
 * @version UltraPower-EOMS v4.0
 * @since jdk1.5
 */
class RemedyOperation
{
	private ARServerUserObj server;
	
	/**
	 * 构造函数
	 */
	public RemedyOperation() {}
	
	/**
	 * 初始化Remedy登入参数
	 * @param loginName 登录名
	 * @param password 密码
	 */
	public void init(String loginName, String password)
	{
		RecordLog.printLog("Ready to operate:" + RemedySession.UtilInfor.SERVER_NAME + ":" + RemedySession.UtilInfor.SERVER_PORT + "=" + loginName, RecordLog.LOG_LEVEL_DEBUG);
		server = RemedySessionPool.getSession();
		server.getServer().setServer(RemedySession.UtilInfor.SERVER_NAME);
		server.getServer().setPort(Integer.parseInt(RemedySession.UtilInfor.SERVER_PORT));
		server.getServer().setUser(loginName);
		server.getServer().setPassword(password);
	}
	
	/**
	 * 连接服务器心跳
	 * @throws ARException
	 */
	public void signal() throws ARException
	{
		Map<SignalType, String> signalMap = new HashMap<SignalType, String>();
		signalMap.put(SignalType.APPLICATION_SIGNAL, null);
		server.getServer().signalServer(signalMap);
	}
	
	/**
	 * 登入Remedy
	 */
	public void login()
	{
		try
		{
			if(RemedySession.UtilInfor.SESSION_POOL.equals(SessionType.SINGLE) || !RemedySessionPool.SessionGlobalThreadLoginStatus)
			{
				server.login();
				if(!RemedySession.UtilInfor.SESSION_POOL.equals(SessionType.SINGLE))
				{
					RemedySessionPool.SessionGlobalThreadLoginStatus = true;
				}
				RecordLog.printLog("Login remedy successfully!", RecordLog.LOG_LEVEL_INFO);
			}
		}
		catch (Exception e)
		{
			RecordLog.printLog("Failed to login AR Server!" + e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
	}
	
	/**
	 * 登出Remedy
	 */
	public void logout()
	{
		if(RemedySession.UtilInfor.SESSION_POOL.equals(SessionType.SINGLE))
		{
			server.logout();
			RemedySessionPool.removeSession();
			RecordLog.printLog("Logout AR Server successfully!", RecordLog.LOG_LEVEL_INFO);
		}
	}
	
	/**
	 * 终止AR连接
	 */
	public void terminate()
	{
		RemedySessionPool.terminate();
	}
	
	private void putFields2Entry(List<RemedyField> fields, Entry entry)
	{
		for(RemedyField field : fields)
		{
			if(field.getValue() != null)
			{
				RecordLog.printLog("Put fields into entry:" + field.getId() + ":" + field.getType() + "=" + field.getValue(), RecordLog.LOG_LEVEL_DEBUG);
				entry.put(Integer.parseInt(field.getId()), new Value(field.getValue(), DataType.toDataType(field.getType())));
			}
		}
	}
	
	/**
	 * 向Remedy中指定Form中添加一条数据
	 * @param schema 需要添加数据的Form名（英文schema）
	 * @param fields 需要赋值的字段以及字段的值
	 * @return 新增数据的主键（C1字段），更新失败返回null
	 */
	public String addEntry(String schema, List<RemedyField> fields)
	{
		Entry entry = new Entry();
		RecordLog.printLog("Start to add entry!", RecordLog.LOG_LEVEL_DEBUG);
		putFields2Entry(fields, entry);
		
		String entryID = null;
		
		try
		{
			entryID = server.getServer().createEntry(schema, entry);
			RecordLog.printLog("Add entry to remedy successfully!", RecordLog.LOG_LEVEL_INFO);
		}
		catch (Exception e)
		{
			RecordLog.printLog("failed to add entry to remedy!", RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return entryID;
	}
	
	/**
	 * 更新Remedy中指定Form的指定记录的数据
	 * @param schema 需要更新数据的Form名（英文schema）
	 * @param entryID 需要更新数据的主键（C1字段）
	 * @param fields 需要更新的字段以及字段的值
	 * @return 是否更新成功
	 */
	public boolean updateEntry(String schema, String entryID, List<RemedyField> fields)
	{
		RecordLog.printLog("Start to update entry!", RecordLog.LOG_LEVEL_DEBUG);
		Entry entry = null;
		try
		{
			entry = server.getServer().getEntry(schema, entryID, null);
			RecordLog.printLog("Get entry from remedy successfully!", RecordLog.LOG_LEVEL_DEBUG);
		}
		catch (Exception e)
		{
			RecordLog.printLog("Failed to get entry!", RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
			return false;
		}
		
		putFields2Entry(fields, entry);
		try
		{
			server.getServer().setEntry(schema, entryID, entry, null, 0);
			RecordLog.printLog("Update entry to remedy successfully!", RecordLog.LOG_LEVEL_INFO);
		}
		catch (Exception e)
		{
			RecordLog.printLog("Failed to update entry!", RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	/**
	 * 删除Remedy中指定Form的指定记录
	 * @param schema 需要删除记录的Form名（英文schema）
	 * @param entryID 需要删除数据的主键（C1字段）
	 * @return 是否删除成功
	 */
	public boolean deleteEntry(String schema, String entryID)
	{
		RecordLog.printLog("Start to delete entry!", RecordLog.LOG_LEVEL_DEBUG);
		try
		{
			server.getServer().deleteEntry(schema, entryID, 0);
			RecordLog.printLog("Delete entry from remedy successfully!", RecordLog.LOG_LEVEL_INFO);
		}
		catch (Exception e)
		{
			RecordLog.printLog("Failed to delete entry from remedy!", RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 获取Remedy中的指定工单的指定附件，直接保存到指定目录
	 * @param schema 需要获取附件的工单名（英文schema）
	 * @param entryID 需要获取附件的工单主键（C1字段）
	 * @param attachID 需要获取的附件主键
	 * @param savePath 获取到的附件保存到的目录
	 * @return 是否获取成功
	 */
	public boolean getAttachment(String schema, String entryID, String attachID, String savePath)
	{
		return true;
	}
	
	/**
	 * 获取Remedy中的指定工单的指定附件，返回数据流
	 * @param schema 需要获取附件的工单名（英文schema）
	 * @param entryID 需要获取附件的工单主键（C1字段）
	 * @param attachID 需要获取的附件主键
	 * @return 附件的数据流
	 */
	public byte[] getAttachment(String schema, String entryID, String attachID)
	{
		return null;
	}
}
