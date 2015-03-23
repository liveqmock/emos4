package com.ultrapower.eoms.workflow.flowmap.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.core.util.WebApplicationManager;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.util.NumberUtils;
import com.ultrapower.eoms.ultrabpp.cache.service.MetaCacheService;
import com.ultrapower.eoms.ultrabpp.model.BaseField;
import com.ultrapower.eoms.workflow.flowmap.model.BaseOwnFieldInfoModel;
import com.ultrapower.eoms.workflow.flowmap.util.RDSelectSql;
import com.ultrapower.eoms.workflow.util.WorkflowUtils;
import com.ultrapower.remedy4j.core.RemedySession;


/**
 * 
 * Copyright (c) 2010 神州泰岳服务管理事业部通用产品
 * All rights reserved.
 * 描述：
 * @author 范莹
 * @date   2010-4-21
 */
public class BaseOwnFieldInfoManage {
	
	/**
	 * 根据工单名称返回工单的特有字段信息列表
	 * @param  baseschema 工单名称
	 * @param  tplId 固定流程版本号
	 * @return List<BaseOwnFieldInfoModel>  工单的特有字段信息列表
	 */
	public List<BaseOwnFieldInfoModel> getList(String  baseschema, String tplId, String cond)
	{
		List<BaseOwnFieldInfoModel>	m_List	= new ArrayList<BaseOwnFieldInfoModel>();
		String tname = RemedySession.UtilInfor.getTableName("WF4:Config_BaseOwnFieldInfo");//Constants.arschemaName2Id.get("WF4:Config_BaseOwnFieldInfo");
		String baseownfieldsqlString = RDSelectSql.Config_BaseOwnFieldInfo.toString();//C650000017 = 1是否打印 C650000017 = 1 and 
		baseownfieldsqlString = baseownfieldsqlString+" from "+tname+"  where  c650000002='"+baseschema+"'";
		if (StringUtils.isNotBlank(tplId)) {
			baseownfieldsqlString += "  and (c650000022='" + tplId + "' or c650000022='' or c650000022 is null)  ";
		}
		baseownfieldsqlString += " order by C650000013";
		//baseownfieldsqlString = baseownfieldsqlString+" from "+tname+"  where  c650000002='"+baseschema+"'  order by C650000013";
		QueryAdapter qAdapter = new QueryAdapter(); 
		DataTable dt = qAdapter.executeQuery(baseownfieldsqlString.toString(), null, 0, 0, 0);
		if (dt != null) {
			for (int i = 0;i<dt.length();i++) 
			{
				DataRow dRow  = dt.getDataRow(i);
				BaseOwnFieldInfoModel m_Model 	= (BaseOwnFieldInfoModel) setModelValue(dRow);
				m_List.add(m_Model);
			} 
		}
		return m_List;
	}
	
	/**
	 * 返回jsp工单字段元数据
	 * @param baseschema
	 * @param tplId
	 * @param cond
	 * @return
	 */
	public List<BaseField> getBppFieldList(String  baseschema, String tplId, String cond)
	{
		MetaCacheService metaCacheService = (MetaCacheService) WebApplicationManager.getBean("metaCacheService");
		Map<String, BaseField> fieldsMap = metaCacheService.getFieldsByBaseSchema(baseschema);
		List<BaseField> fieldsList = new ArrayList<BaseField>();
		if (fieldsMap != null) {
			for (Iterator iterator = fieldsMap.keySet().iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				BaseField baseField = (BaseField)fieldsMap.get(key);
				if (baseField != null && baseField.getVisiable() != null && baseField.getVisiable() == 1) {
					fieldsList.add(baseField);
				}
			}
		}
		Collections.sort(fieldsList);
		return fieldsList;
	}
	
	/**
	 * 根据查询到的工单特有信息数据，封装为对象
	 * @param p_Rs
	 * @return Object
	 */
	private Object setModelValue(DataRow p_Rs)
	{
		BaseOwnFieldInfoModel m_Model 	= new BaseOwnFieldInfoModel(); 

		m_Model.setBaseCategoryName(p_Rs.getString("BaseName"));
		m_Model.setBaseCategorySchama(p_Rs.getString("BaseSchama"));
		m_Model.setBaseFix_field_EditPhase(p_Rs.getString("defEditStep"));
		m_Model.setBaseFix_field_RequiredPhase(p_Rs.getString("BaseFix_field_RequiredPhase"));
		m_Model.setBaseFree_field_EditStep(p_Rs.getString("freeEditStep"));
		m_Model.setBaseFree_field_ShowStep(p_Rs.getString("BaseFree_field_ShowStep"));
		m_Model.setBaseOwnFieldInfoDesc(p_Rs.getString("BaseOwnFieldInfoDesc"));
		m_Model.setBaseOwnFieldInfoID(p_Rs.getString("id"));
		m_Model.setBase_field_DBName(p_Rs.getString("fieldCode"));
		m_Model.setBase_field_ID(p_Rs.getString("fieldId"));
		m_Model.setBase_field_IsRequired(NumberUtils.formatToLong(p_Rs.getString("Base_field_IsRequired")));
		m_Model.setBase_field_Purpose_FlowControl(p_Rs.getString("Base_field_Purpose_FlowControl"));
		m_Model.setBase_field_Purpose_Print(p_Rs.getString("isPrint"));
		m_Model.setBase_field_ShowName(p_Rs.getString("fieldName"));
		m_Model.setBase_field_Type(p_Rs.getString("fieldType"));
		m_Model.setBase_field_TypeValue(p_Rs.getString("fieldTypeValue"));
		m_Model.setEntryMode(NumberUtils.formatToLong(p_Rs.getString("EntryMode")));
		m_Model.setLogIsWrite(NumberUtils.formatToLong(p_Rs.getString("LogIsWrite")));
		m_Model.setPrintOneLine(NumberUtils.formatToLong(p_Rs.getString("PrintOneLine")));
		m_Model.setPrintOrder(NumberUtils.formatToLong((p_Rs.getString("PrintOrder"))));
		m_Model.setBase_field_Is_AllPrint(NumberUtils.formatToLong(p_Rs.getString("Base_field_Is_AllPrint")));
		m_Model.setBase_field_WriteAction(p_Rs.getString("Base_field_WriteAction"));
		m_Model.setVarcharFieldeIsExceed(p_Rs.getLong("fieldIsToLong"));		
		return m_Model;
	}
	
	public List<BaseOwnFieldInfoModel> getModelList(String  modelId) {
		String tname = RemedySession.UtilInfor.getTableName("WF4:App_Base_Model_Field");
		String sql = RDSelectSql.sheetModel.toString();
		sql = sql+" from "+tname+"  where  C650000000='"+modelId+"' order by C650000013";
		QueryAdapter qAdapter = new QueryAdapter(); 
      	DataTable dt = qAdapter.executeQuery(sql.toString(), null, 0, 0, 0);
      	List<BaseOwnFieldInfoModel>	m_List	= new ArrayList<BaseOwnFieldInfoModel>();
      	if (dt != null) {
      		for (int i = 0;i<dt.length();i++) 
    		{
          		DataRow dRow  = dt.getDataRow(i);
          		BaseOwnFieldInfoModel m_Model 	= (BaseOwnFieldInfoModel) setModelVal(dRow);
          		m_List.add(m_Model);
    		} 
		}
		return m_List;
	}
	
	private Object setModelVal(DataRow p_Rs)
	{
		BaseOwnFieldInfoModel m_Model 	= new BaseOwnFieldInfoModel(); 
//		m_Model.setBaseCategoryName(p_Rs.getString("BaseCategoryName"));
//		m_Model.setBaseCategorySchama(p_Rs.getString("BaseCategorySchama"));
//		m_Model.setBaseFix_field_EditPhase(p_Rs.getString("BaseFix_field_EditPhase"));
//		m_Model.setBaseFix_field_RequiredPhase(p_Rs.getString("BaseFix_field_RequiredPhase"));
//		m_Model.setBaseFree_field_EditStep(p_Rs.getString("BaseFree_field_EditStep"));
//		m_Model.setBaseFree_field_ShowStep(p_Rs.getString("BaseFree_field_ShowStep"));
//		m_Model.setBaseOwnFieldInfoDesc(p_Rs.getString("BaseOwnFieldInfoDesc"));
//		m_Model.setBaseOwnFieldInfoID(p_Rs.getString("BaseOwnFieldInfoID"));
		m_Model.setBase_field_DBName(p_Rs.getString("fieldName"));
		m_Model.setBase_field_ID(p_Rs.getString("fieldId"));
//		m_Model.setBase_field_IsRequired(new Long(p_Rs.getString("Base_field_IsRequired")));
//		m_Model.setBase_field_Purpose_FlowControl(p_Rs.getString("Base_field_Purpose_FlowControl"));
//		m_Model.setBase_field_Purpose_Print(p_Rs.getString("Base_field_Purpose_Print"));
		m_Model.setBase_field_ShowName(p_Rs.getString("fieldCnName"));
		m_Model.setBase_field_Type(p_Rs.getString("fieldType"));
		m_Model.setBase_field_TypeValue(p_Rs.getString("fieldTypeValue"));
		m_Model.setBase_field_Value(p_Rs.getString("fieldValue"));
//		m_Model.setEntryMode(new Long(p_Rs.getString("EntryMode")));
//		m_Model.setLogIsWrite(new Long(p_Rs.getString("LogIsWrite")));
		m_Model.setPrintOneLine(new Long(p_Rs.getString("printLine")));
//		m_Model.setPrintOrder(NumberUtils.formatToLong((p_Rs.getString("PrintOrder"))));
		m_Model.setBase_field_Is_AllPrint(new Long(p_Rs.getString("isPrint")));
//		m_Model.setBase_field_WriteAction(p_Rs.getString("Base_field_WriteAction"));
//		m_Model.setVarcharFieldeIsExceed(p_Rs.getLong("VarcharFieldeIsExceed"));		
		return m_Model;
	}
	
	public void delModelById(String modelId) {
		if(StringUtils.isNotBlank(modelId)) {
			QueryAdapter qAdapter = new QueryAdapter(); 
			String tname = RemedySession.UtilInfor.getTableName("WF4:App_Base_Model_Field");
			String sql = "delete from " + tname + " where C650000000='" +modelId+"'";
			qAdapter.executeQuery(sql, null);
			
			tname = RemedySession.UtilInfor.getTableName("WF4:App_Base_Model_Info");
			sql = "delete from " + tname + " where C1='" +modelId+"'";
			qAdapter.executeQuery(sql, null);
		}
	}
	
	public String getAttachId(String modelId) {
		String rtn = null;
		if(StringUtils.isNotBlank(modelId)) {
			QueryAdapter qAdapter = new QueryAdapter(); 
			String tname = RemedySession.UtilInfor.getTableName("WF4:App_Base_Model_Info");
			String sql = RDSelectSql.sheetAttachModel.toString() + " from "+tname+" where c1='"+modelId+"'";
			DataTable dt = qAdapter.executeQuery(sql.toString(), null, 0, 0, 0);
			if(dt != null && dt.length() > 0) {
				DataRow dr = dt.getDataRow(0);
				rtn = dr.getString("attachId");
			}
		}
		return rtn;
	}
}
