package com.ultrapower.eoms.workflow.sheet.query.manager;


import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.rquery.RQuery;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.workflow.sheet.query.service.IsheetQueryService;



public class SheetQueryServiceImpl implements IsheetQueryService
{
	private static Map<String, Map<String, String>> queryXmlMap;
	
	/**
	 *	值班平台
	 *	功能描述：查询某些人在某时间段内的已办和待办工单数目；
	 */
	public int getDealedSheetCount(String userName,String createStartTime,String createEndTime)
	{
		int count = 0;
		String tableName = "SQL_WF_DealedSheet_PlanStats.query";
		HashMap<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("username",userName);
		conditionMap.put("dealstarttime",createStartTime);
		conditionMap.put("dealendtime",createEndTime);
		
		RQuery rquery = new RQuery(tableName,conditionMap);
		DataTable dt = rquery.getDataTable();
		if(dt!=null && dt.length()>0){
			DataRow dr = dt.getDataRow(0);
			count = dr.getString(0)!=null?Integer.parseInt(dr.getString(0)):0;
		}
		return count;
	}
	
	
	/**
	 *	值班平台
	 *	功能描述：查询某些人在某时间段内的待办工单数目；
	 */
	public int getWaittingSheetCount(String userName,String createStartTime,String createEndTime)
	{
		int count = 0;
		String tableName = "SQL_WF_WaitingDealSheet_PlanStats.query";
		HashMap<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("username",userName);
		conditionMap.put("createstarttime",createStartTime);
		conditionMap.put("createendtime",createEndTime);
		
		RQuery rquery = new RQuery(tableName,conditionMap);
		DataTable dt = rquery.getDataTable();
		if(dt!=null && dt.length()>0){
			DataRow dr = dt.getDataRow(0);
			count = dr.getString(0)!=null?Integer.parseInt(dr.getString(0)):0;
		}
		return count;
	}
	
	/**
	 * 获取查询SQL的真是配置名称，根据整体配置文件BaseQueryXMLConfig.xml进行配置
	 * @param queryType 查询类型，为通用的查询SQL名称
	 * @param baseSchema 工单Schema
	 * @return 真是SQL名称
	 */
	public String getQuerySqlName(String queryType, String baseSchema)
	{
		if(queryXmlMap == null || queryXmlMap.size() == 0)
		{
			reflushQueryConfig();
		}
		
		String xmlKey = StringUtils.checkNullString(baseSchema).equals("") ? "*" : baseSchema;
		Map<String, String> xmlMap = queryXmlMap.get(StringUtils.checkNullString(queryType));
		if(xmlMap != null)
		{
			String xmlName = xmlMap.get(baseSchema);
			return StringUtils.checkNullString(xmlName).equals("") ? queryType : xmlName;
		}
		else
		{
			return queryType;
		}
	}
	
	public void reflushQueryConfig()
	{
		String tmpPath = this.getClass().getResource("").getPath();
		System.out.println(tmpPath);
		tmpPath = tmpPath.substring(0, tmpPath.length()-49);
		System.out.println(tmpPath);
		File file = new File(tmpPath +  "workflow/query/BaseQueryXMLConfig.xml");
		try
		{
			SAXBuilder bu = new SAXBuilder();
			Document doc = bu.build(file);
			Element rootElement = doc.getRootElement();
	
			List<Element> queryXmlList = rootElement.getChildren("sqls");
			Map<String, Map<String, String>> xmlMap = new HashMap<String, Map<String, String>>();
			for(Element ele_query : queryXmlList)
			{
				List<Element> sqlXmlList = ele_query.getChildren("sql");
				Map<String, String> sqlMap = new HashMap<String, String>();
				for(Element ele_sql : sqlXmlList)
				{
					sqlMap.put(ele_sql.getAttributeValue("schema"), ele_sql.getText());
				}
				xmlMap.put(ele_query.getAttributeValue("name"), sqlMap);
			}
			
			queryXmlMap = xmlMap;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
