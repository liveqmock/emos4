package com.ultrapower.eoms.workflow.managetools.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.List;

import com.ultrapower.eoms.common.core.component.data.DataAdapter;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.plugin.excelutils.ExcelReader;
import com.ultrapower.eoms.workflow.managetools.control.BaseImportXmlImpl;
import com.ultrapower.eoms.workflow.managetools.control.DataReader;
import com.ultrapower.eoms.workflow.managetools.control.TemplateReader;
import com.ultrapower.eoms.workflow.managetools.control.template.ImportTemplate;
import com.ultrapower.eoms.workflow.managetools.service.BaseImportService;
import com.ultrapower.remedy4j.core.RemedySession;

public class FormImportAction extends BaseAction
{
	private static final long serialVersionUID = 8325886686585606470L;
	
	private File excel;
	
	private String startTime;
	private String endTime;
	private String categoryID;
	private String schema;
	
	private String baseTable;
	private String inforTable;
	
	public String importBase()
	{
		ExcelReader reader;
		try
		{
			reader = new ExcelReader(new FileInputStream(excel));
			
			TemplateReader tReader = new TemplateReader();
			ImportTemplate template = tReader.read(reader);
			
			DataReader dReader = new DataReader();
			List<List<String>> dataList = dReader.read(reader, template.getProcessMap().size()+9, template.getFieldList().size());
			
			reader.close();
			
			BaseImportService service = new BaseImportXmlImpl();
			service.importBase(template, dataList);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		return this.findForward("importBase");
	}
	
	public String buildSerialNo()
	{
		baseTable = RemedySession.UtilInfor.getTableName(schema);
		inforTable = RemedySession.UtilInfor.getTableName("WF4:App_Base_Infor");
		
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(TimeUtils.formatStringToDate(startTime.substring(0, startTime.indexOf(" ")) + " 00:00:00"));
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(TimeUtils.formatStringToDate(endTime.substring(0, endTime.indexOf(" ")) + " 00:00:00"));
		
		while(startCal.before(endCal))
		{
			long stLong = startCal.getTimeInMillis()/1000;
			startCal.add(Calendar.DATE, 1);
			long edLong = startCal.getTimeInMillis()/1000;
			String baseSql = "select C1 from " + baseTable + " where C700000006 >= " + stLong + " and C700000006 < " + edLong + " order by C700000006 asc";
			
			QueryAdapter con  = new QueryAdapter();
			DataTable baseList = con.executeQuery(baseSql, null, 0);
			Calendar currentCal = Calendar.getInstance();
			currentCal.setTime(TimeUtils.formatIntToDate(stLong));
			List<DataRow> rowList = baseList.getRowList();
			handleEntry(rowList, currentCal);
		}

		return this.findForward("updateSN");
	}
	
	private boolean handleEntry(List<DataRow> c1s, Calendar timeCal)
	{
		
		int index = 1;
		String tmpYear = String.valueOf(timeCal.get(Calendar.YEAR)).substring(2);
		String tmpMonth = String.valueOf(timeCal.get(Calendar.MONTH) + 1);
		String tmpDay = String.valueOf(timeCal.get(Calendar.DATE));
		if(tmpMonth.length() == 1) tmpMonth = "0" + tmpMonth;
		if(tmpDay.length() == 1) tmpDay = "0" + tmpDay;
		String snBase = "ID-" + categoryID + "-" + tmpYear + tmpMonth + tmpDay + "-";
		
		DataAdapter da = new DataAdapter();
		
		for(int i = 0; i < c1s.size(); i++)
		{
			DataRow row = c1s.get(i);
			String c1 = row.getString("C1");
			
			String baseSN = String.valueOf(index);
			for(int j = 5 - baseSN.length(); j > 0; j--)
			{
				baseSN = "0" + baseSN;
			}
			
			baseSN = snBase + baseSN;
			
			String updateBaseSql = "update " + baseTable + " set C700000003='" + baseSN + "' where C1='" + c1 + "'";
			String updateInforSql = "update " + inforTable + " set C700000003='" + baseSN + "' where C700000000='" + c1 + "'and C700000001='" + schema + "'";
			
			da.execute(updateBaseSql, null);
			da.execute(updateInforSql, null);
		
			index++;
		}
		
		return true;
	}

	/**
	 * @return the excel
	 */
	public File getExcel()
	{
		return excel;
	}

	/**
	 * @param excel the excel to set
	 */
	public void setExcel(File excel)
	{
		this.excel = excel;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime()
	{
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime)
	{
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime()
	{
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime)
	{
		this.endTime = endTime;
	}

	/**
	 * @return the categoryID
	 */
	public String getCategoryID()
	{
		return categoryID;
	}

	/**
	 * @param categoryID the categoryID to set
	 */
	public void setCategoryID(String categoryID)
	{
		this.categoryID = categoryID;
	}

	/**
	 * @return the schema
	 */
	public String getSchema()
	{
		return schema;
	}

	/**
	 * @param schema the schema to set
	 */
	public void setSchema(String schema)
	{
		this.schema = schema;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}
}
