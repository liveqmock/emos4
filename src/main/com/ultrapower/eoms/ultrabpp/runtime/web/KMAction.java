package com.ultrapower.eoms.ultrabpp.runtime.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;

import com.ultrapower.eoms.common.constants.PropertiesUtils;
import com.ultrapower.eoms.common.core.component.data.DataAdapter;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;

public class KMAction extends BaseAction
{
	private String kmbaseSchema;
	private String kmbaseID;
	private String kmcaption;
	private String kmkeyword;
	private String kmcategory;
	private String kmcontent;
//	private String ucasIp;
//	private String ucasPort;
	private String kmUrl;
	private String kmProject;
	private String username;
	private DataTable table;
	private Map<String, String> fieldValue;

	public String kmRedirect() throws UnsupportedEncodingException
	{

		table = null;
		if (kmcaption.contains("@!{"))
		{
			kmcaption = keyFieldReplace(kmcaption);
		}
		if (kmkeyword.contains("@!{"))
		{
			kmkeyword = keyFieldReplace(kmkeyword);
		}
		if (kmcontent.contains("@!{"))
		{
			kmcontent = keyFieldReplace(kmcontent);
		}
		kmUrl = PropertiesUtils.getProperty("km.url");
		kmProject = PropertiesUtils.getProperty("km.projectName");
		UserSession userSession = (UserSession) this.getSession().getAttribute("userSession");
		username = userSession.getLoginName();
		return SUCCESS;
	}

	public String keyFieldReplace(String keyFieldString)
	{
		if (table == null && kmbaseID != null && kmbaseID.length() > 0)
		{
			fieldValue = new HashMap<String, String>();
			QueryAdapter qAdapter = new QueryAdapter();
			String sql = "select * from bs_f_" + kmbaseSchema + " where baseid=?";
			table = qAdapter.executeQuery(sql, kmbaseID);
			DataRow datarow = table.getDataRow(0);
			for (int i = 0; i < datarow.length(); i++)
			{
				String colName = datarow.getKey(i);
				String colValue = datarow.getString(colName);
				fieldValue.put(colName, colValue);
			}
		}
		String finalString = "";
		if (table != null && fieldValue != null)
		{
			while (keyFieldString.indexOf("@!{") > -1)
			{
				finalString += keyFieldString.substring(0, keyFieldString.indexOf("@!{"));
				keyFieldString = keyFieldString.substring(keyFieldString.indexOf("@!{") + 3);
				String fieldKey = keyFieldString.substring(0, keyFieldString.indexOf("}"));
				if (fieldValue.get(fieldKey.toUpperCase()) != null)
				{
					finalString += fieldValue.get(fieldKey.toUpperCase());
				}
				else
				{
					finalString += fieldKey;
				}
				keyFieldString = keyFieldString.substring(keyFieldString.indexOf("}") + 1);
			}
			keyFieldString = finalString;
		}
		return keyFieldString;
	}

	//获取同步至知识库的工单信息
	public void  getTicketInfoToKm()
	{
		Map<String, String> tiketFiledMap = new HashMap<String, String>();
		QueryAdapter qAdapter = new QueryAdapter();
		String sql = "select * from bs_f_" + kmbaseSchema + " where baseid=?";
		table = qAdapter.executeQuery(sql, kmbaseID);
		DataRow datarow = table.getDataRow(0);
		if(datarow != null)
		for (int i = 0; i < datarow.length(); i++)
		{
			String colName = datarow.getKey(i);
			String colValue = datarow.getString(colName);
			tiketFiledMap.put(colName, colValue);
		}
		renderText(JSONArray.fromObject(tiketFiledMap).toString());
	}
	
	public String getKmbaseSchema()
	{
		return kmbaseSchema;
	}

	public void setKmbaseSchema(String kmbaseSchema)
	{
		this.kmbaseSchema = kmbaseSchema;
	}

	public String getKmbaseID()
	{
		return kmbaseID;
	}

	public void setKmbaseID(String kmbaseID)
	{
		this.kmbaseID = kmbaseID;
	}

	public String getKmcaption()
	{
		return kmcaption;
	}

	public void setKmcaption(String kmcaption)
	{
		this.kmcaption = kmcaption;
	}

	public String getKmkeyword()
	{
		return kmkeyword;
	}

	public void setKmkeyword(String kmkeyword)
	{
		this.kmkeyword = kmkeyword;
	}

	public String getKmcategory()
	{
		return kmcategory;
	}

	public void setKmcategory(String kmcategory)
	{
		this.kmcategory = kmcategory;
	}

	public String getKmcontent()
	{
		return kmcontent;
	}

	public void setKmcontent(String kmcontent)
	{
		this.kmcontent = kmcontent;
	}

//	public String getUcasIp()
//	{
//		return ucasIp;
//	}
//
//	public void setUcasIp(String ucasIp)
//	{
//		this.ucasIp = ucasIp;
//	}
//
//	public String getUcasPort()
//	{
//		return ucasPort;
//	}
//
//	public void setUcasPort(String ucasPort)
//	{
//		this.ucasPort = ucasPort;
//	}

	public String getKmUrl() {
		return kmUrl;
	}

	public void setKmUrl(String kmUrl) {
		this.kmUrl = kmUrl;
	}

	public String getKmProject() {
		return kmProject;
	}

	public void setKmProject(String kmProject) {
		this.kmProject = kmProject;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

}
