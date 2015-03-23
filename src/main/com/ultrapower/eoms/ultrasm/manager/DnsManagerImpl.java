package com.ultrapower.eoms.ultrasm.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.core.component.data.DataAdapter;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.ultrasm.RecordLog;
import com.ultrapower.eoms.ultrasm.service.DnsManagerService;
import com.ultrapower.eoms.ultrasm.util.UltraSmUtil;

@Transactional
public class DnsManagerImpl implements DnsManagerService
{
	public String getCurrentDn(String tableName, String dnFieldName, String prevId)
	{
		String sql = "select max(" + dnFieldName + ") from " + tableName + " where parentId = ?";
		int dn = 0;
		try
		{
			QueryAdapter queryAdapter = new QueryAdapter();
			DataTable table = queryAdapter.executeQuery(sql,new Object[] {prevId}, 0, 0, 2);
			DataRow row = null;
			if(table != null && table.length() > 0)
			{
				String data = "[0-9][0-9][0-9]";
				for(int i=0;i<table.length();i++)
				{
					row = table.getDataRow(i);
					if(row.getString(0).matches(data))
					{
						dn = Integer.valueOf(row.getString(0));
					}
				}
			}
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		dn++;
		return String.format("%1$03d", dn);
	}

	public String getCurrentDns(String tableName, String dnsFieldName, String dnFieldName, String prevId)
	{
		if(prevId != null && "0".equals(prevId))
		{
			return this.getCurrentDn(tableName, dnFieldName, prevId);
		}
		String sql = "select " + dnsFieldName + " from " + tableName + " where pid = ?";
		String dns = "";
		try
		{
			QueryAdapter queryAdapter = new QueryAdapter();
			DataTable table = queryAdapter.executeQuery(sql,new Object[] {prevId}, 0, 0, 2);
			if(table != null && table.length() > 0)
			{
				dns = table.getDataRow(0).getString(0);
			}
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return dns+"."+this.getCurrentDn(tableName, dnFieldName, prevId);
	}

	public String getCurrentDn(String tableName, String dnFieldName, String prevId, String relaFieldName, String relaValue)
	{
		String sql = "select max(" + dnFieldName + ") from " + tableName + " where parentId = ? and " + relaFieldName + " = ? ";
		int dn = 0;
		try
		{
			QueryAdapter queryAdapter = new QueryAdapter();
			DataTable table = queryAdapter.executeQuery(sql,new Object[] {prevId, relaValue}, 0, 0, 2);
			DataRow row = null;
			if(table != null && table.length() > 0)
			{
				String data = "[0-9][0-9][0-9]";
				for(int i=0;i<table.length();i++)
				{
					row = table.getDataRow(i);
					if(row.getString(0).matches(data))
					{
						dn = Integer.valueOf(row.getString(0));
					}
				}
			}
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		dn++;
		return String.format("%1$03d", dn);
	}
	
	public String getCurrentDns(String tableName, String dnsFieldName, String dnFieldName, String prevId, String relaFieldName, String relaValue)
	{
		if(prevId != null && "0".equals(prevId))
			return this.getCurrentDn(tableName, dnFieldName, prevId, relaFieldName, relaValue);
		String sql = "select " + dnsFieldName + " from " + tableName + " where pid = ?";
		String dns = "";
		try
		{
			QueryAdapter queryAdapter = new QueryAdapter();
			DataTable table = queryAdapter.executeQuery(sql,new Object[] {prevId}, 0, 0, 2);
			if(table != null && table.length() > 0)
			{
				dns = table.getDataRow(0).getString(0);
			}
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return dns+"."+this.getCurrentDn(tableName, dnFieldName, prevId);
	}
	
	public boolean updateSubDns(String tableName, String dnsFieldName, String oldDns, String newDns)
	{
		boolean result = false;
		String sql = "update " + tableName
		           +   " set " + dnsFieldName + " = ? || substr(" + dnsFieldName + "," + (oldDns.length()+1) + ")"
		           + " where " + dnsFieldName + " like '" + oldDns + ".%'";
		try
		{
			DataAdapter DataAdapter = new DataAdapter();
			DataAdapter.execute(sql,new Object[] {newDns});
			result = true;
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean updateSubFullName(String tableName, String dnsFieldName, String fullNameName, String oldDns, String oldFullName, String newFullName)
	{
		boolean result = false;
		String sql = "update " + tableName
		           +   " set " + fullNameName + " = ? || substr(" + fullNameName + "," + (oldFullName.length()+1) + ")"
		           + " where " + dnsFieldName + " like '" + oldDns + ".%'";
		try
		{
			DataAdapter DataAdapter = new DataAdapter();
			DataAdapter.execute(sql,new Object[] {newFullName});
			result = true;
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean updateSubFullName(String tableName, String dnsFieldName, String fullNameName, String oldDns, String oldFullName, String newFullName, String relaFieldName, String relaValue)
	{
		boolean result = false;
		String sql = "update " + tableName
		           +   " set " + fullNameName + " = ? || substr(" + fullNameName + "," + (oldFullName.length()+1) + ")"
		           + " where " + dnsFieldName + " like '" + oldDns + ".%'"
		           +   " and " + relaFieldName + " = ? ";
		try
		{
			DataAdapter DataAdapter = new DataAdapter();
			DataAdapter.execute(sql,new Object[] {newFullName, relaValue});
			result = true;
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean updateSubDnsAndFullName(String tableName, String dnsFieldName, String fullNameName, String oldDns, String oldFullName, String newDns, String newFullName)
	{
		boolean result = false;
		String sql = "update " + tableName
		           +   " set " + dnsFieldName + " = ? || substr(" + dnsFieldName + "," + (oldDns.length()+1) + ")"
		           +   " ,   " + fullNameName + " = ? || substr(" + fullNameName + "," + (oldFullName.length()+1) + ")"
		           + " where " + dnsFieldName + " like '" + oldDns + ".%'";
		try
		{
			DataAdapter DataAdapter = new DataAdapter();
			Object[] obj = {newDns, newFullName};
			DataAdapter.execute(sql, obj);
			result = true;
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return result;
	}
	
	public List getSubIdList(String tableName, String pid)
	{
		if("".equals(StringUtils.checkNullString(pid)))
		{
			return null;
		}
		String sql = " select pid from " + tableName + " start with pid = ? connect by parentid = prior pid";
		List idList = new ArrayList();
		try
		{
			QueryAdapter queryAdapter = new QueryAdapter();
			DataTable table = queryAdapter.executeQuery(sql, new Object[] {pid});
			if(table != null && table.length() > 0)
			{
				for(int i=0;i<table.length();i++)
				{
					String temp = table.getDataRow(i).getString(0);
					if(!"".equals(StringUtils.checkNullString(temp)))
					{
						idList.add(temp);
					}
				}
			}
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return idList;
	}
	
	public List getParentIdListByDns(String tableName, String dnsFieldName, String dns)
	{
		if("".equals(StringUtils.checkNullString(dns)))
		{
			return null;
		}
		List idList = new ArrayList();
		try
		{
			String dnss = "";
			for(int i=0;i<dns.length();i+=4) //每一级dn由3位数字组成，dns由各级dn通过小圆点“.”连接而成
			{
				if(!"".equals(dnss))
				{
					dnss += ",";
				}
				dnss += dns.substring(0, i+3);
			}
			Map map = UltraSmUtil.getSqlParameter(dnss);
			String sql = " select pid from " + tableName + " where " + dnsFieldName + " in (" + map.get("?") + ") order by " + dnsFieldName;
			QueryAdapter queryAdapter = new QueryAdapter();
			DataTable table = queryAdapter.executeQuery(sql, (Object[]) map.get("obj"));
			if(table != null && table.length() > 0)
			{
				for(int i=0;i<table.length();i++)
				{
					String temp = table.getDataRow(i).getString(0);
					if(!"".equals(StringUtils.checkNullString(temp)))
					{
						idList.add(temp);
					}
				}
			}
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return idList;
	}
}
