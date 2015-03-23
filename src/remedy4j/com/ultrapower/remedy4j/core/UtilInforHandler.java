package com.ultrapower.remedy4j.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.remedy4j.RecordLog;

/**
 * 负责工单相关链接URL以及获取工单真实表名的类
 * @author BigMouse
 * @version UltraPower-EOMS v4.0
 * @since jdk1.5
 */
public class UtilInforHandler
{
	/**
	 * Remedy服务名
	 */
	public String SERVER_NAME;
	
	/**
	 * Remedy服务端口
	 */
	public String SERVER_PORT;
	
	/**
	 * Demo密码
	 */
	public String DEMO_PASSWORD;
	
	/**
	 * 创建工单的URL
	 */
	public String CREATE_URL;
	
	/**
	 * 创建固定流程工单的URL
	 */
	public String CREATE_DEF_URL;
	
	/**
	 * 打开工单的URL
	 */
	public String QUERY_URL;
	
	/**
	 * 打开待办工单的URL
	 */
	public String QUERY_TASK_URL;
	
	/**
	 * 打开流程图的URL
	 */
	public String FLOWMAP_URL;
	
	/**
	 * 退出的URL
	 */
	public String LOGOUT_URL;
	
	/**
	 * Remedy的登录对象池的存储格式。<br />
	 * <b>SINGLE</b>：每次调用一个独立的对象。<b>（默认）</b><br />
	 * <b>THREAD</b>：每个线程一个独立的对象；<br />
	 * <b>GLOBAL</b>：整个系统使用一个对象。
	 */
	public SessionType SESSION_POOL = SessionType.THREAD; //"GLOBAL"：全局对象池；"THREAD"：线程对象池
	
	enum SessionType
	{
		SINGLE,THREAD,GLOBAL
	}
	
	private List<String[]> formTableCache = new ArrayList<String[]>();
	
	/**
	 * 根据工单名（英文schema）获取在数据库中真实的表名， 如果没有则返回null
	 * @param schema 工单名（英文schema）
	 * @return 数据库中真实的表名， 如果没有则返回null
	 */
	public String getTableName(String schema)
	{
		String tableName = null;
		RecordLog.printLog("Check the cache:" + schema, RecordLog.LOG_LEVEL_DEBUG);
		for(String[] formTableArray : formTableCache)
		{
			if(formTableArray[0].equals(schema))
			{
				RecordLog.printLog("Got the table name in cache:" + formTableArray[1], RecordLog.LOG_LEVEL_DEBUG);
				tableName = formTableArray[1];
			}
		}
		
		RecordLog.printLog("No data in cache:" + schema, RecordLog.LOG_LEVEL_DEBUG);
		if(tableName == null)
		{
			QueryAdapter con  = new QueryAdapter();
			String sql = "select SCHEMAID from ARSCHEMA where NAME = '" + schema + "'";
			RecordLog.printLog("Check database:" + sql, RecordLog.LOG_LEVEL_DEBUG);
			DataTable rs = con.executeQuery(sql, null, 0);
			if(rs != null && rs.length() > 0)
			{
				tableName = "T" + rs.getDataRow(0).getString("SCHEMAID");
				RecordLog.printLog("Got the table name in database:" + tableName, RecordLog.LOG_LEVEL_DEBUG);
				formTableCache.add(new String[] {schema, tableName});
			}
		}

		 RecordLog.printLog("Got the table name:" + tableName, RecordLog.LOG_LEVEL_DEBUG);
		return tableName;
	}
	
	/**
	 * 根据数据库中真实的表名获取工单名（英文schema）
	 * @param tableName 数据库中真实的表名
	 * @return 工单名（英文schema）
	 */
	public String getFormSchema(String tableName)
	{
		String schema = null;
		RecordLog.printLog("Check the cache:" + tableName, RecordLog.LOG_LEVEL_DEBUG);
		for(String[] formTableArray : formTableCache)
		{
			if(formTableArray[1].equals(tableName.toUpperCase()))
			{
				RecordLog.printLog("Got the schema in cache:" + formTableArray[0], RecordLog.LOG_LEVEL_DEBUG);
				schema = formTableArray[0];
			}
		}
		
		if(schema == null)
		{
			String schemaID = tableName.toUpperCase().substring(1);
			QueryAdapter con  = new QueryAdapter();
			String sql = "select NAME from ARSCHEMA where SCHEMAID = '"+schemaID+"'";
			RecordLog.printLog("Check database:" + sql, RecordLog.LOG_LEVEL_DEBUG);
			DataTable rs = con.executeQuery(sql, null, 0);
			if(rs != null && rs.length() > 0)
			{
				schema = rs.getDataRow(0).getString("NAME");
				RecordLog.printLog("Got the schema in database:" + schema, RecordLog.LOG_LEVEL_DEBUG);
				formTableCache.add(new String[] {schema, tableName});
			}
		}
		RecordLog.printLog("Got the schema:" + schema, RecordLog.LOG_LEVEL_INFO);
		return schema;
	}
	
	/**
	 * 获取工单所有字段的列表
	 * @param schema 工单名（英文schema）
	 * @return 工单所有字段的列表
	 */
	public Map<String, RemedyField> getStorageFields(String schema)
	{
		QueryAdapter con  = new QueryAdapter();
		String sql = "select schemaid,fieldid,fieldname,datatype from field table_field " +
		"where " +
			"table_field.schemaid="+getTableName(schema)+" and " +
			"table_field.fieldtype=1 and " +
			"table_field.foption != 4 and " +
			"table_field.fieldid > 100000000 " +
		"order by table_field.fieldid";
		RecordLog.printLog("Get fields in database:" + sql, RecordLog.LOG_LEVEL_DEBUG);
		DataTable rs = con.executeQuery(sql, null, 0);
		Map<String, RemedyField> fields = new HashMap<String, RemedyField>();
		for(int i = 0; i < rs.length(); i++)
		{
			DataRow row = rs.getDataRow(i);
			RecordLog.printLog("Set field:" + row.getString("fieldid") + "-" + row.getString("fieldname") + "-" + row.getString("datatype"), RecordLog.LOG_LEVEL_DEBUG);
			RemedyField field = new RemedyField(row.getString("fieldid"), row.getString("fieldname"), Integer.parseInt(row.getString("datatype")));
			fields.put(row.getString("fieldname"), field);
		}
		RecordLog.printLog("Get " + fields.size() + " fields in database:" + schema, RecordLog.LOG_LEVEL_INFO);
		return fields;
	}
	
	/**
	 * 获取工单所有字段的列表，带值的
	 * @param schema 工单名（英文schema）
	 * @param entryID 工单ID（C1字段）
	 * @return 工单所有字段的列表
	 */
	public Map<String, RemedyField> getStorageFieldsWithValue(String schema, String entryID)
	{
		Map<String, RemedyField> fields = getStorageFields(schema);
		String tableName = getTableName(schema);
		QueryAdapter con  = new QueryAdapter();
		String sql = "select * from " + tableName + " " + " where " + " C1='"+entryID + "'";
		RecordLog.printLog("Get fields value in database:" + sql, RecordLog.LOG_LEVEL_DEBUG);
		DataTable rs = con.executeQuery(sql, null, 0);
		if(rs != null && rs.length() > 0)
		{
			DataRow row = rs.getDataRow(0);
			for(RemedyField field : fields.values())
			{
				RecordLog.printLog("Set field:" + field.getId() + "-" + field.getName() + "-" + field.getType() + "=" + row.getString(field.getId()), RecordLog.LOG_LEVEL_DEBUG);
				field.setValue(row.getString(field.getId()));
			}
		}
		RecordLog.printLog("Get " + fields.size() + " fields in database:" + schema + "=" + entryID, RecordLog.LOG_LEVEL_INFO);
		return fields;
	}
	
	/**
	 * 获取创建工单的URL
	 * @param schema 工单名（英文schema）
	 * @param view 工单打开的视图
	 * @return 创建工单的URL
	 */
	public String getCreateUrl(String schema, String view)
	{
		schema = StringUtils.checkNullString(schema);
		view = StringUtils.checkNullString(view);
		String createUrl = CREATE_URL;
		createUrl = createUrl.replaceAll("<REMEDY_SERVER>", SERVER_NAME);
		createUrl = createUrl.replaceAll("<REMEDY_FROM>", schema);
		createUrl = createUrl.replaceAll("<REMEDY_FROMVVIEW>", view);
		RecordLog.printLog("Format create url:" + createUrl, RecordLog.LOG_LEVEL_INFO);
		return createUrl;
	}
	
	/**
	 * 获取创建工单的URL
	 * @param schema 工单名（英文schema）
	 * @param view 工单打开的视图
	 * @param templateID 流程定义名
	 * @return 创建工单的URL
	 */
	public String getCreateUrl(String schema, String view, String templateID)
	{
		schema = StringUtils.checkNullString(schema);
		view = StringUtils.checkNullString(view);
		templateID = StringUtils.checkNullString(templateID);
		String createUrl = CREATE_DEF_URL;
		createUrl = createUrl.replaceAll("<REMEDY_SERVER>", SERVER_NAME);
		createUrl = createUrl.replaceAll("<REMEDY_FROM>", schema);
		createUrl = createUrl.replaceAll("<REMEDY_FROMVVIEW>", view);
		createUrl = createUrl.replaceAll("<BASETPLID>", templateID);
		RecordLog.printLog("Format create def url:" + createUrl, RecordLog.LOG_LEVEL_INFO);
		return createUrl;
	}
	
	/**
	 * 获取创建工单的URL
	 * @param schema 工单名（英文schema）
	 * @param view 工单打开的视图
	 * @param templateID 流程定义名
	 * @param type 固定或自由流程，1为固定，其他为自由
	 * @return 创建工单的URL
	 */
	public String getCreateUrl(String schema, String view, String templateID, String type)
	{
		type = StringUtils.checkNullString(type);
		if(type.equals("1"))
		{
			return getCreateUrl(schema, view, templateID);
		}
		else
		{
			return getCreateUrl(schema, view);
		}
	}
	
	/**
	 * 获取修改工单的URL
	 * @param schema 工单名（英文schema）
	 * @param view 工单打开的视图
	 * @param entryID 工单ID（C1字段）
	 * @return 修改工单的URL
	 */
	public String getModifyUrl(String schema, String view, String entryID)
	{
		schema = StringUtils.checkNullString(schema);
		view = StringUtils.checkNullString(view);
		entryID = StringUtils.checkNullString(entryID);
		String modifyUrl = QUERY_URL;
		modifyUrl = modifyUrl.replaceAll("<REMEDY_SERVER>", SERVER_NAME);
		modifyUrl = modifyUrl.replaceAll("<REMEDY_FROM>", schema);
		modifyUrl = modifyUrl.replaceAll("<REMEDY_FROMVVIEW>", view);
		modifyUrl = modifyUrl.replaceAll("<REMEDY_EID>", entryID);
		RecordLog.printLog("Format modify url:" + modifyUrl, RecordLog.LOG_LEVEL_INFO);
		return modifyUrl;
	}
	
	/**
	 * 获取修改工单的URL
	 * @param schema 工单名（英文schema）
	 * @param view 工单打开的视图
	 * @param entryID 工单ID（C1字段）
	 * @param taskID 任务ID
	 * @param taskType 任务类型，DEAL或AUDITING
	 * @return 修改工单的URL
	 */
	public String getModifyUrl(String schema, String view, String entryID, String taskID, String taskType)
	{
		schema = StringUtils.checkNullString(schema);
		view = StringUtils.checkNullString(view);
		entryID = StringUtils.checkNullString(entryID);
		taskID = StringUtils.checkNullString(taskID);
		taskType = StringUtils.checkNullString(taskType);
		String modifyUrl = QUERY_TASK_URL;
		modifyUrl = modifyUrl.replaceAll("<REMEDY_SERVER>", SERVER_NAME);
		modifyUrl = modifyUrl.replaceAll("<REMEDY_FROM>", schema);
		modifyUrl = modifyUrl.replaceAll("<REMEDY_FROMVVIEW>", view);
		modifyUrl = modifyUrl.replaceAll("<REMEDY_EID>", entryID);
		modifyUrl = modifyUrl.replaceAll("<PROCESSID>", taskID);
		modifyUrl = modifyUrl.replaceAll("<PROCESSTYPE>", taskType);
		RecordLog.printLog("Format modify url with processid:" + modifyUrl, RecordLog.LOG_LEVEL_INFO);
		return modifyUrl;
	}
	
	/**
	 * 获取工单流程图的URL
	 * @param baseID 工单ID（C1字段）
	 * @param schema 工单名（英文schema）
	 * @param type 流程类型。tpl：固定流程；free：自由流程
	 * @param tplID 固定流程的流程定义名
	 * @return 工单流程图的URL
	 */
	public String getFlowmapUrl(String baseID, String schema, String type, String tplID, String entryId)
	{
		baseID = StringUtils.checkNullString(baseID);
		schema = StringUtils.checkNullString(schema);
		type = StringUtils.checkNullString(type);
		tplID = StringUtils.checkNullString(tplID);
		String flowmapUrl = FLOWMAP_URL;
		flowmapUrl = flowmapUrl.replaceAll("<BASEID>", baseID);
		flowmapUrl = flowmapUrl.replaceAll("<BASESCHEMA>", schema);
		flowmapUrl = flowmapUrl.replaceAll("<ENTRYID>", entryId);
		if(type.equals("tpl"))
		{
			flowmapUrl = flowmapUrl.replaceAll("<TYPE>", type);
			flowmapUrl = flowmapUrl.replaceAll("<TPLID>", tplID);
		}
		else
		{
			flowmapUrl = flowmapUrl.replaceAll("<TYPE>", "free");
			flowmapUrl = flowmapUrl.replaceAll("<TPLID>", "");
		}
		return flowmapUrl;
	}
	
	/**
	 * 获取remedy登出的URL
	 * @return remedy登出的URL
	 */
	public String getLogoutUrl()
	{
		String logoutUrl = LOGOUT_URL;
		logoutUrl = logoutUrl.replaceAll("<REMEDY_SERVER>", SERVER_NAME);
		return logoutUrl;
	}
}
