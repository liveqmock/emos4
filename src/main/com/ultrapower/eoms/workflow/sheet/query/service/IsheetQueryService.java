package com.ultrapower.eoms.workflow.sheet.query.service;

public interface IsheetQueryService {

	/**
	 *	值班平台
	 *	功能描述：查询某些人在某时间段内的已办工单数目；
	 *	userName:用户登录名，多人时以","隔开
	 *	createStartTime:开始时间
	 *	createEndTime:结束时间
	 */
	public int getDealedSheetCount(String userName,String createStartTime,String createEndTime);
	
	
	/**
	 *	值班平台
	 *	功能描述：查询某些人在某时间段内的待办工单数目；
	 *	userName:用户登录名，多人时以","隔开
	 *	createStartTime:开始时间
	 *	createEndTime:结束时间
	 */
	public int getWaittingSheetCount(String userName,String createStartTime,String createEndTime);
	
	/**
	 * 获取查询SQL的真是配置名称，根据整体配置文件BaseQueryXMLConfig.xml进行配置
	 * @param queryType 查询类型，为通用的查询SQL名称
	 * @param baseSchema 工单Schema
	 * @return 真是SQL名称
	 */
	public String getQuerySqlName(String queryType, String baseSchema);
}
