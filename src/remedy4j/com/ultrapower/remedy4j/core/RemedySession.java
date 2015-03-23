package com.ultrapower.remedy4j.core;

import java.util.List;

import com.bmc.arsys.api.ARException;

/**
 * Remedy的操作类
 * @author BigMouse
 * @version UltraPower-EOMS v4.0
 * @since jdk1.5
 */
public class RemedySession
{
	/**
	 * 负责工单相关链接URL以及获取工单真实表名的对象
	 */
	public static UtilInforHandler UtilInfor = new UtilInforHandler();
	
	/**
	 * 负责工单操作的类
	 */
	private RemedyOperation remedyOp = null;
	
	/**
	 * 构造函数
	 */
	public RemedySession() {}
	
	/**
	 * 构造函数
	 * @param serverName 服务名
	 * @param port 端口
	 * @param loginName 用户名
	 * @param password 密码
	 */
	public RemedySession(String loginName, String password)
	{
		remedyOp = new RemedyOperation();
		remedyOp.init(loginName, password);
	}
	
	/**
	 * 连接服务器心跳
	 * @return true为连接成功，false为连接失败
	 */
	public boolean signal()
	{
		boolean signal = false;
		try
		{
			remedyOp.signal();
			signal = true;
		}
		catch (ARException e)
		{
			if(e.getLastStatus().get(0).getMessageNum() == 90l)
			{
				signal = false;
			}
			else
			{
				signal = true;
			}
			e.printStackTrace();
		}
		return signal;
	}
	
	/**
	 * 登入Remedy
	 */
	public void login()
	{
		if(remedyOp == null)
		{
			remedyOp = new RemedyOperation();
		}
		remedyOp.login();
	}
	
	/**
	 * 登入Remedy
	 * @param loginName 登录名
	 * @param password 密码
	 */
	public void login(String loginName, String password)
	{
		if(remedyOp == null)
		{
			remedyOp = new RemedyOperation();
		}
		remedyOp.init(loginName, password);
		remedyOp.login();
	}
	
	/**
	 * 登出Remedy
	 */
	public void logout()
	{
		remedyOp.logout();
	}
	
	/**
	 * 终止AR连接
	 */
	public void terminate()
	{
		remedyOp.terminate();
	}
	
	/**
	 * 向Remedy中指定Form中添加一条数据
	 * @param schema 需要添加数据的Form名（英文schema）
	 * @param fields 需要赋值的字段以及字段的值
	 * @return 新增数据的主键（C1字段），更新失败返回null
	 */
	public String addEntry(String schema, List<RemedyField> fields)
	{
		return remedyOp.addEntry(schema, fields);
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
		return remedyOp.updateEntry(schema, entryID, fields);
	}
	
	/**
	 * 删除Remedy中指定Form的指定记录
	 * @param schema 需要删除记录的Form名（英文schema）
	 * @param entryID 需要删除数据的主键（C1字段）
	 * @return 是否删除成功
	 */
	public boolean deleteEntry(String schema, String entryID)
	{
		return remedyOp.deleteEntry(schema, entryID);
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
		return remedyOp.getAttachment(schema, entryID, attachID, savePath);
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
		return remedyOp.getAttachment(schema, entryID, attachID);
	}
}
