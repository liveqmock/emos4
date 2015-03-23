package com.ultrapower.eoms.workflow.managetools.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.core.component.data.DataAdapter;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.workflow.sheet.query.service.IsheetQueryService;
import com.ultrapower.remedy4j.core.RemedySession;

public class FormDeleteAction extends BaseAction
{
	private String baseID;
	private String baseSchema;
	private String baseIDs;
	private String wfSortId;
	
	private String titleFlag;
	private String orderby;
	private String queryname;
	private String baseStatus;
	
	private IsheetQueryService sheetQueryServiceImpl;
	
	public String delete()
	{
		doDelete(baseID, baseSchema);
		
		return SUCCESS;
	}
	
	public String statusDelete()
	{
		QueryAdapter adp=new QueryAdapter();
		String baseFormTableName = RemedySession.UtilInfor.getTableName(baseSchema);
		DataTable topEntryIDTable = adp.executeQuery("select C1 from " + baseFormTableName + " where C700000010='" + baseStatus + "'", new Object[]{});
		List<DataRow> c1List = topEntryIDTable.getRowList();
		for(DataRow row : c1List)
		{
			String c1 = row.getString(0);
			try
			{
				doDelete(c1, baseSchema);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
		}
		return this.findForward("statusDelete");
	}
	
	public String importDelete()
	{
		QueryAdapter adp=new QueryAdapter();
		String baseFormTableName = RemedySession.UtilInfor.getTableName(baseSchema);
		DataTable topEntryIDTable = adp.executeQuery("select C1 from " + baseFormTableName + " where C8='IMPORT'", new Object[]{});
		List<DataRow> c1List = topEntryIDTable.getRowList();
		for(DataRow row : c1List)
		{
			String c1 = row.getString(0);
			try
			{
				doDelete(c1, baseSchema);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return this.findForward("importDelete");
	}
	
	public String multiDelete()
	{ 
		String[] baseIDArr = baseIDs.split(",");//000000000455;WF4:EL_UVS_BULT,00000002;WF4:EL_NOP_CP
		int baseIDArrLen = 0;
		if(baseIDArr!=null)
			baseIDArrLen = baseIDArr.length;
		String baseIdSchema = null;
		for(int i = 0;i<baseIDArrLen;i++)
		{
			baseIdSchema = StringUtils.checkNullString(baseIDArr[i]);
			
			String[] idAndName = baseIdSchema.split(";");
			doDelete(idAndName[0], idAndName[1]);
		}
		return this.findRedirect("query");
	}
	
	public String query()
	{
		this.titleFlag = "wf_baseinfoview";
		HashMap map = (HashMap) this.getRequest().getAttribute("valuemap");
		if(map == null)
		{
			map = new HashMap();
		}
		else
		{
			this.dateFormat(map);
		}
		
		if(orderby!=null && !"".equals(orderby))
		{
			orderby = "C700000006";//建单时间排序
		}
		if(baseSchema!=null && !"".equals(baseSchema))
		{
			map.put("baseSchema", baseSchema);
		}
		map.put("orderby", orderby);
		this.getRequest().setAttribute("valuemap",map);
		queryname = sheetQueryServiceImpl.getQuerySqlName("SQL_WF_BaseViewAll.query", baseSchema);
		return this.findForward("DeleteViewAll");
	}
	
	/**
	 * 开始时间与结束时间格式的转换
	 * @return
	 */
	public void dateFormat(Map map){
		if(map.get("createstarttime")!=null){
			String cStartTime = map.get("createstarttime").toString();
			map.put("createstarttime",TimeUtils.formatDateStringToInt(cStartTime));
		}
		
		if(map.get("createendtime")!=null){
			String cEndTime = map.get("createendtime").toString();
			map.put("createendtime",TimeUtils.formatDateStringToInt(cEndTime));
		}
	}

	private void doDelete(String baseid, String schema)
	{
		//BaseInfor信息
		String baseInforSchema = "WF4:App_Base_Infor";
		String baseInforTableName = RemedySession.UtilInfor.getTableName(baseInforSchema);
		System.out.println("获取WF4:App_Base_Infor真实表名：" + baseInforTableName);
		//主表单信息
		String baseFormSchema = schema;
		String baseFormTableName = RemedySession.UtilInfor.getTableName(baseFormSchema);
		System.out.println("获取" + schema + "真实表名：" + baseFormTableName);
		//字段修改表信息
		String baseModifySchema = schema + "_FieldModifyLog";
		String baseModifyTableName = RemedySession.UtilInfor.getTableName(baseModifySchema);
		System.out.println("获取" + baseModifySchema + "真实表名：" + baseModifyTableName);
		
		//流程引擎表
		String dealProcessTableName = "BS_T_WF_DEALPROCESS";
		String dealProcessLogTableName = "BS_T_WF_DEALPROCESSLOG";
		String entryTableName = "BS_T_WF_ENTRY";
		String currentTaskTableName = "BS_T_WF_CURRENTTASK";
		String currentStepTableName = "BS_T_WF_CURRENTSTEPS";
		String historyTaskTableName = "BS_T_WF_HISTORYTASK";
		String historyStepTableName = "BS_T_WF_HISTORYSTEPS";
		String baseNoticeTableName = "BS_T_WF_NOTICE";
		
		String inforID = "";
		//获取entryID
		List<String> entryIDs = new ArrayList<String>();
		StringBuilder entryIDStr = new StringBuilder();
		QueryAdapter adp=new QueryAdapter();
		DataTable topEntryIDTable = adp.executeQuery("select C710000001, C1 from " + baseInforTableName + " where C700000000='" + baseid + "' and C700000001='" + schema + "'", new Object[]{});
		String tmpTopEntryID = StringUtils.checkNullString(topEntryIDTable.getDataRow(0).getString(0));
		String tmpTopInforID = StringUtils.checkNullString(topEntryIDTable.getDataRow(0).getString(1));
		entryIDs.add(tmpTopEntryID);
		entryIDStr.append("'" + tmpTopEntryID + "'");
		inforID = tmpTopInforID;
		
		DataTable subEntryIDTable = adp.executeQuery("select ID from " + entryTableName + " where TOPENTRYID='" + entryIDs.get(0) + "'", new Object[]{});
		List<DataRow> subEntryRowList = subEntryIDTable.getRowList();
		for(DataRow row : subEntryRowList)
		{
			String tmpSubEntryID = StringUtils.checkNullString(row.getString("ID"));
			entryIDs.add(tmpSubEntryID);
			entryIDStr.append(",'" + tmpSubEntryID + "'");
		}
		System.out.println("获取到EntryID:" + entryIDStr);
		
		DataAdapter da = new DataAdapter();
		System.out.println("开始删除" + dealProcessTableName + "表数据");
		da.execute("delete from " + dealProcessTableName + " where BASEID='" + baseid + "' and BASESCHEMA='" + schema + "'", new Object[]{});
		System.out.println("开始删除" + dealProcessLogTableName + "表数据");
		da.execute("delete from " + dealProcessLogTableName + " where BASEID='" + baseid + "' and BASESCHEMA='" + schema + "'", new Object[]{});
		System.out.println("开始删除" + currentTaskTableName + "表数据");
		da.execute("delete from " + currentTaskTableName + " where BASEID='" + baseid + "' and BASESCHEMA='" + schema + "'", new Object[]{});
		System.out.println("开始删除" + historyTaskTableName + "表数据");
		da.execute("delete from " + historyTaskTableName + " where BASEID='" + baseid + "' and BASESCHEMA='" + schema + "'", new Object[]{});
		System.out.println("开始删除" + baseNoticeTableName + "表数据");
		da.execute("delete from " + baseNoticeTableName + " where BASEID='" + baseid + "' and BASESCHEMA='" + schema + "'", new Object[]{});
		System.out.println("开始删除" + currentStepTableName + "表数据");
		da.execute("delete from " + currentStepTableName + " where ENTRYID in (" + entryIDStr + ")", new Object[]{});
		System.out.println("开始删除" + historyStepTableName + "表数据");
		da.execute("delete from " + historyStepTableName + " where ENTRYID in (" + entryIDStr + ")", new Object[]{});
		System.out.println("开始删除" + entryTableName + "表数据");
		da.execute("delete from " + entryTableName + " where ID in (" + entryIDStr + ")", new Object[]{});
		
		RemedySession session = new RemedySession("Demo", RemedySession.UtilInfor.DEMO_PASSWORD);
		
		session.login();
		System.out.println("开始删除" + baseModifySchema + "表数据");
		DataTable modifyIDTable = adp.executeQuery("select C1 from " + baseModifyTableName + " where C700021001='" + baseid + "' and C700021002='" + schema + "'", new Object[]{});
		List<DataRow> modifyIDList = modifyIDTable.getRowList();
		for(DataRow row : modifyIDList)
		{
			String tmpModifyID = StringUtils.checkNullString(row.getString("C1"));
			session.deleteEntry(baseModifySchema, tmpModifyID);
		}
		System.out.println("开始删除" + baseInforSchema + "表数据");
		session.deleteEntry(baseInforSchema, inforID);
		System.out.println("开始删除" + baseFormSchema + "表数据");
		session.deleteEntry(baseFormSchema, baseid);
		session.logout();
	}

	public String getBaseID()
	{
		return baseID;
	}

	public void setBaseID(String baseID)
	{
		this.baseID = baseID;
	}

	public String getBaseSchema()
	{
		return baseSchema;
	}

	public void setBaseSchema(String baseSchema)
	{
		this.baseSchema = baseSchema;
	}

	public String getWfSortId()
	{
		return wfSortId;
	}

	public void setWfSortId(String wfSortId)
	{
		this.wfSortId = wfSortId;
	}

	public String getTitleFlag()
	{
		return titleFlag;
	}

	public void setTitleFlag(String titleFlag)
	{
		this.titleFlag = titleFlag;
	}

	public String getOrderby()
	{
		return orderby;
	}

	public void setOrderby(String orderby)
	{
		this.orderby = orderby;
	}

	public String getQueryname()
	{
		return queryname;
	}

	public void setQueryname(String queryname)
	{
		this.queryname = queryname;
	}

	public IsheetQueryService getSheetQueryServiceImpl()
	{
		return sheetQueryServiceImpl;
	}

	public void setSheetQueryServiceImpl(IsheetQueryService sheetQueryServiceImpl)
	{
		this.sheetQueryServiceImpl = sheetQueryServiceImpl;
	}

	public String getBaseIDs()
	{
		return baseIDs;
	}

	public void setBaseIDs(String baseIDs)
	{
		this.baseIDs = baseIDs;
	}

	/**
	 * @return the baseStatus
	 */
	public String getBaseStatus()
	{
		return baseStatus;
	}

	/**
	 * @param baseStatus the baseStatus to set
	 */
	public void setBaseStatus(String baseStatus)
	{
		this.baseStatus = baseStatus;
	}
}

