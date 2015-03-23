package com.ultrapower.eoms.workflow.flowmap.control;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.component.rquery.RQuery;
import com.ultrapower.eoms.common.core.util.NumberUtils;
import com.ultrapower.eoms.workflow.flowmap.model.BaseFieldBean;
import com.ultrapower.eoms.workflow.flowmap.model.BaseModel;
import com.ultrapower.eoms.workflow.flowmap.model.BaseOwnFieldInfoModel;
import com.ultrapower.eoms.workflow.flowmap.util.RDSelectSql;
import com.ultrapower.eoms.workflow.util.WorkflowUtils;
import com.ultrapower.remedy4j.core.RemedySession;


/**
 * 
 * Copyright (c) 2010 神州泰岳服务管理事业部通用产品
 * All rights reserved.
 * 描述：remedy工单基础信息管理类
 * @author 范莹
 * @date   2010-4-21
 */
public class BaseManage {

	/**
	 * 通过工单参数获得工单基本信息对象BaseModel
	 * @param baseid       工单编号
	 * @param baseschema   工单名称 
	 * @param selectrow	   选择的工单特有的field字段查询条件
	 * @param condition	   查询条件
	 * @return BaseModel   返回工单对象
	 */
	public BaseModel getOneModel(String baseid,String baseschema,String selectrow,String condition)
	{
		BaseModel	m_BaseModel	= new BaseModel();
		if (WorkflowUtils.isARflow(baseschema)) {
			String tname = RemedySession.UtilInfor.getTableName(baseschema);//Constants.arschemaName2Id.get(baseschema);
			try
			{
				//获得查询SQL
				String m_sql			=  RDSelectSql.BaseOn_BaseIDSelectBase.toString();
				m_sql = m_sql +selectrow+ " from "+tname+" Base where C1='"+baseid+"' and "+(StringUtils.isNotBlank(condition)?condition:" 1=1 ");
				System.out.println(m_sql.toString());
				QueryAdapter qAdapter = new QueryAdapter();
				DataTable dTable = qAdapter.executeQuery(m_sql.toString(), null, 0);
				DataRow dRow = dTable != null && dTable.length()>0?dTable.getDataRow(0):null;
				if (dRow!=null) 
				{
					m_BaseModel = setModelValue(dRow);
				}
				
			}
			catch (Exception ex) {
				ex.printStackTrace();
			} 
		} else {//jsp表单
			String con = StringUtils.isNotBlank(condition)?condition:" 1=1 ";
			String sql = "select * from BS_F_" + baseschema + " where baseid='"+baseid+"' and " + con;
			QueryAdapter qAdapter = new QueryAdapter();
			try {
				DataTable dTable = qAdapter.executeQuery(sql.toString(), null, 0);
				DataRow dRow = dTable != null && dTable.length()>0?dTable.getDataRow(0):null;
				if (dRow!=null) {
					m_BaseModel = setModelValue(dRow);
				} /*else {
					sql = "select * from BS_T_WF_BASEINFOR where baseid='"+baseid+"' and " + con;
					dTable = qAdapter.executeQuery(sql.toString(), null, 0);
					dRow = dTable != null && dTable.length()>0?dTable.getDataRow(0):null;
					if (dRow!=null) {
						try {
							m_BaseModel = setModelValue(dRow);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}*/
			} catch (Exception e) {
			}
		}
		return m_BaseModel;
	}
	
	public BaseModel getOneModel(String baseid,String baseschema,List<BaseOwnFieldInfoModel> m_BaseOwnFieldInfoList,String condition) {
		BaseModel	m_BaseModel	= new BaseModel();
		long m_BFCount 	= m_BaseOwnFieldInfoList.size();
		String m_str_Select_BaseOwnField = "";
		for (int i=0;i<m_BFCount;i++) {//拼装查询条件
			BaseOwnFieldInfoModel m_BaseOwnFieldInfoModel = (BaseOwnFieldInfoModel)m_BaseOwnFieldInfoList.get(i);
			String m_Base_field_DBName		= m_BaseOwnFieldInfoModel.getBase_field_DBName();
			String m_Base_field_ID			= m_BaseOwnFieldInfoModel.getBase_field_ID();
			if (StringUtils.isNotBlank(m_Base_field_DBName) && StringUtils.isNotBlank(m_Base_field_ID)) {
				m_str_Select_BaseOwnField 		+= "," + " Base.C" + m_Base_field_ID + " as " + m_Base_field_DBName;
			}
		}
		m_BaseModel = getOneModel(baseid, baseschema, m_str_Select_BaseOwnField, condition);
		return m_BaseModel;
	}
	
	/**
	 * 
	 * 通过工单参数获得符合要求的工单列表
	 * @param baseid       工单编号
	 * @param baseschema   工单名称 
	 * @param selectrow	   选择的工单特有的field字段查询条件
	 * @param condition	   查询条件
	 * @return List<BaseModel>  返回工单列表
	 */
	public List<BaseModel> getList(String baseid,String baseschema,String selectrow,String condition)
	{
		String tname = RemedySession.UtilInfor.getTableName(baseschema);//Constants.arschemaName2Id.get(baseschema);
		List<BaseModel>	m_BaseModelList	= new ArrayList<BaseModel>();
		try
		{
			//获得查询SQL
	      	StringBuffer m_sql =  RDSelectSql.BaseOn_BaseIDSelectBase;
	      	m_sql.append(selectrow).append(" from "+tname)
	      											.append(" as Base where C1="+baseid).append(" and ").append(condition.length()>0?condition:" 1=1");
			System.out.println(m_sql.toString());
	      	QueryAdapter qAdapter = new QueryAdapter();
			DataTable dTable = qAdapter.executeQuery(m_sql.toString(), null, 0);
			for(int i=0;i<dTable.length();i++) {
				m_BaseModelList.add(setModelValue(dTable.getDataRow(i)));
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return m_BaseModelList;
	}

	/**
	 * 将从数据库中查询到的数据封装为工单对象
	 * @param  rs
	 * @return BaseModel
	 * @throws SQLException
	 */
	private BaseModel setModelValue(DataRow rs) throws SQLException
	{
		String name = "";
		int cols = rs.length();
		Map<String,BaseFieldBean>	m_BaseFieldHashtable	= new LinkedHashMap<String,BaseFieldBean>();
		for (int i = 0; i <= cols; i++) {
			
			name = rs.getKey(i);
			String value= rs.getString(name);
			
			BaseFieldBean m_BaseFieldBean = new BaseFieldBean();
			m_BaseFieldBean.setM_BaseFieldDBName(name);
			m_BaseFieldBean.setM_BaseFieldValue(value);
			m_BaseFieldHashtable.put(name,m_BaseFieldBean);
		}
		BaseModel m_BaseModel = new BaseModel();
		m_BaseModel.setBaseName(rs.getString("BASENAME"));
		m_BaseModel.setBaseSN(rs.getString("BASESN"));
		m_BaseModel.setBaseSchema(rs.getString("BASESCHEMA"));
		m_BaseModel.setM_BaseFieldHashtable(m_BaseFieldHashtable);
		return m_BaseModel;
	}
	
	public BaseModel getOneBaseModel(String baseid,String baseschema)
	{
		BaseModel baseModel	= new BaseModel();
		HashMap params = new HashMap();
		params.put("baseid", baseid);
		params.put("baseschema", baseschema);
		RQuery rquery = new RQuery("SQL_API_BASEINFO.query",params);
		DataTable dt = rquery.getDataTable();
		if(dt != null && dt.length()>0){
			for (int i = 0; i < dt.length(); i++) {
				DataRow dr = dt.getDataRow(i);
				String BaseID = dr.getString("BaseID");
				String BaseSchema = dr.getString("BaseSchema");
				String BaseName = dr.getString("BaseName");
				String BaseSN = dr.getString("BaseSN");
				String BaseCreatorFullName = dr.getString("BaseCreatorFullName");
				String BaseCreatorLoginName = dr.getString("BaseCreatorLoginName");
				String BaseCreateDate = dr.getString("BaseCreateDate");
				String BaseSendDate = dr.getString("BaseSendDate");
				String BaseFinishDate = dr.getString("BaseFinishDate");
				String BaseCloseDate = dr.getString("BaseCloseDate");
				String BaseStatus = dr.getString("BaseStatus");
				String BaseSummary = dr.getString("BaseSummary");
				String BaseItems = dr.getString("BaseItems");
				String BasePriority = dr.getString("BasePriority");
				String BaseAcceptOutTime = dr.getString("BaseAcceptOutTime");
				String BaseDealOutTime = dr.getString("BaseDealOutTime");
				String BaseDescrption = dr.getString("BaseDescrption");
				String BaseResult = dr.getString("BaseResult");
				String BaseCloseSatisfy = dr.getString("BaseCloseSatisfy");
				String BaseTplID = dr.getString("BaseTplID");
				String BaseIsArchive = dr.getString("BaseIsArchive");
				String BaseIsTrueClose = dr.getString("BaseIsTrueClose");
				String BaseWorkFlowFlag = dr.getString("BaseWorkFlowFlag");
				String BaseFlagCreated = dr.getString("BaseFlagCreated");
				String BaseFlagSended = dr.getString("BaseFlagSended");
				String BaseFlagFinished = dr.getString("BaseFlagFinished");
				String BaseFlagCloseed = dr.getString("BaseFlagCloseed");
				String BaseStatusCode = dr.getString("BaseStatusCode");
				String BaseAcceptOutFlag = dr.getString("BaseAcceptOutFlag");
				String BaseDealOutFlag = dr.getString("BaseDealOutFlag");
				String BaseFieldLogName = dr.getString("BaseFieldLogName");
				String BaseFlagIsMotherCreated = dr.getString("BaseFlagIsMotherCreated");
				String BaseFlowDrawDesc = dr.getString("BaseFlowDrawDesc");
				String BaseFlagIsCreateChild = dr.getString("BaseFlagIsCreateChild");
				String BaseOpenDateTime = dr.getString("BaseOpenDateTime");
				String BaseAtteptTime = dr.getString("BaseAtteptTime");
				String BaseIsHasAcceptTime = dr.getString("BaseIsHasAcceptTime");
				String BaseCreatorConnectWay = dr.getString("BaseCreatorConnectWay");
				String BaseCreatorCorp = dr.getString("BaseCreatorCorp");
				String BaseCreatorCorpID = dr.getString("BaseCreatorCorpID");
				String BaseCreatorDep = dr.getString("BaseCreatorDep");
				String BaseCreatorDepID = dr.getString("BaseCreatorDepID");
				String BaseCreatorDN = dr.getString("BaseCreatorDN");
				String BaseCreatorDNID = dr.getString("BaseCreatorDNID");
				String tmp_Begin_PhaseNo = dr.getString("tmp_Begin_PhaseNo");
				String tmp_Begin_ProcessID = dr.getString("tmp_Begin_ProcessID");
				String BaseEntryID = dr.getString("BaseEntryID");
				String BaseAttachGUID = dr.getString("BaseAttachGUID");
				baseModel.setBaseID(BaseID);
				baseModel.setBaseSchema(BaseSchema);
				baseModel.setBaseName(BaseName);
				baseModel.setBaseSN(BaseSN);
				baseModel.setBaseCreatorFullName(BaseCreatorFullName);
				baseModel.setBaseCreatorLoginName(BaseCreatorLoginName);
				baseModel.setBaseCreateDate(NumberUtils.formatToLong(BaseCreateDate));
				baseModel.setBaseSendDate(NumberUtils.formatToLong(BaseSendDate));
				baseModel.setBaseFinishDate(NumberUtils.formatToLong(BaseFinishDate));
				baseModel.setBaseCloseDate(NumberUtils.formatToLong(BaseCloseDate));
				baseModel.setBaseStatus(BaseStatus);
				baseModel.setBaseSummary(BaseSummary);
				baseModel.setBaseItems(BaseItems);
				baseModel.setBasePriority(BasePriority);
				baseModel.setBaseAcceptOutTime(NumberUtils.formatToLong(BaseAcceptOutTime));
				baseModel.setBaseDealOutTime(NumberUtils.formatToLong(BaseDealOutTime));
				baseModel.setBaseDescrption(BaseDescrption);
				baseModel.setBaseResult(BaseResult);
				baseModel.setBaseCloseSatisfy(BaseCloseSatisfy);
				baseModel.setBaseTplID(BaseTplID);
				baseModel.setBaseIsArchive(NumberUtils.formatToInt(BaseIsArchive));
				baseModel.setBaseIsTrueClose(NumberUtils.formatToInt(BaseIsTrueClose));
				baseModel.setBaseWorkFlowFlag(NumberUtils.formatToInt(BaseWorkFlowFlag));
				baseModel.setBaseFlagCreated(NumberUtils.formatToLong(BaseFlagCreated));
				baseModel.setBaseFlagSended(NumberUtils.formatToLong(BaseFlagSended));
				baseModel.setBaseFlagFinished(NumberUtils.formatToLong(BaseFlagFinished));
				baseModel.setBaseFlagCloseed(NumberUtils.formatToLong(BaseFlagCloseed));
				baseModel.setBaseStatusCode(BaseStatusCode);
				baseModel.setBaseAcceptOutFlag(NumberUtils.formatToLong(BaseAcceptOutFlag));
				baseModel.setBaseDealOutFlag(NumberUtils.formatToLong(BaseDealOutFlag));
				baseModel.setBaseFieldLogName(BaseFieldLogName);
				baseModel.setBaseFlagIsMotherCreated(NumberUtils.formatToInt(BaseFlagIsMotherCreated));
				baseModel.setBaseFlowDrawDesc(BaseFlowDrawDesc);
				baseModel.setBaseFlagIsCreateChild(NumberUtils.formatToInt(BaseFlagIsCreateChild));
				baseModel.setBaseOpenDateTime(NumberUtils.formatToInt(BaseOpenDateTime));
				baseModel.setBaseAcceptDate(NumberUtils.formatToLong(BaseAtteptTime));
				baseModel.setBaseCreatorConnectWay(BaseCreatorConnectWay);
				baseModel.setBaseCreatorCorp(BaseCreatorCorp);
				baseModel.setBaseCreatorCorpID(BaseCreatorCorpID);
				baseModel.setBaseCreatorDep(BaseCreatorDep);
				baseModel.setBaseCreatorDepID(BaseCreatorDepID);	
				baseModel.setBaseCreatorDN(BaseCreatorDN);
				baseModel.setBaseCreatorDNID(BaseCreatorDNID);
				baseModel.setTmp_Begin_PhaseNo(tmp_Begin_PhaseNo);
				baseModel.setTmp_Begin_ProcessID(tmp_Begin_ProcessID);
				baseModel.setBaseAttachGUID(BaseAttachGUID);
				baseModel.setBaseEntryID(BaseEntryID);
			}
		}
		return baseModel;
	}

}
