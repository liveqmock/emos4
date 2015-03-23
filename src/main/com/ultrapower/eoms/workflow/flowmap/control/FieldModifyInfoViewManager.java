package com.ultrapower.eoms.workflow.flowmap.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.util.NumberUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.common.core.util.WebUtils;
import com.ultrapower.eoms.ultrabpp.model.BaseField;
import com.ultrapower.eoms.ultrasm.model.Attachment;
import com.ultrapower.eoms.ultrasm.model.DicItem;
import com.ultrapower.eoms.ultrasm.service.AttachmentManagerService;
import com.ultrapower.eoms.ultrasm.service.DicManagerService;
import com.ultrapower.eoms.workflow.flowmap.model.BaseFieldBean;
import com.ultrapower.eoms.workflow.flowmap.model.BaseModel;
import com.ultrapower.eoms.workflow.flowmap.model.BaseOwnFieldInfoModel;
import com.ultrapower.eoms.workflow.flowmap.model.ModifyFieldView;
import com.ultrapower.eoms.workflow.flowmap.model.ModifyLogModel;
import com.ultrapower.eoms.workflow.flowmap.model.TplDealProcessModel;
import com.ultrapower.eoms.workflow.flowmap.util.Constants;
import com.ultrapower.eoms.workflow.util.WorkflowUtils;
import com.ultrapower.remedy4j.core.RemedySession;



/**
 * 
 * Copyright (c) 2010 神州泰岳服务管理事业部通用产品
 * All rights reserved.
 * 描述：用户输入信息的封装管理类
 * @author 范莹
 * @date   2010-4-21
 */ 
public class FieldModifyInfoViewManager {
	
	/**
	 * 根据编号，工单名称，环节信息，封装用户输入信息列表（步骤表单）
	 * @param baseID
	 * @param schema
	 * @param pInfo
	 * @return
	 */
//	public List drawSubFieldInfo(String baseID,String schema,TplDealProcessModel pInfo){
//		
//		List templite = new ArrayList();
//		String tname = RemedySession.UtilInfor.getTableName(schema);//Constants.arschemaName2Id.get(schema);
//		
//		List<BaseOwnFieldInfoModel> m_BaseOwnFieldInfoList = (new BaseOwnFieldInfoManage()).getList(schema);
//		int m_BFCount = m_BaseOwnFieldInfoList.size();
//		StringBuffer sqlStringBuffer = m_BFCount>0?new StringBuffer(","):new StringBuffer("");
//		for(int i=0;i<m_BFCount;i++)
//		{		
//			BaseOwnFieldInfoModel m_BaseOwnFieldInfoModel = (BaseOwnFieldInfoModel)m_BaseOwnFieldInfoList.get(i);
//			String m_Base_field_DBName			= m_BaseOwnFieldInfoModel.getBase_field_DBName();
//			String m_Base_field_IdString 		= m_BaseOwnFieldInfoModel.getBase_field_ID();
//			if(i==0){
//				 sqlStringBuffer.append(" BaseSub.C"+m_Base_field_IdString+" as "+m_Base_field_DBName);
//			}
//			else sqlStringBuffer.append(" , BaseSub.C"+m_Base_field_IdString+" as "+m_Base_field_DBName);
//		}
//		String conditionString = " C700038041='"+pInfo.getProcessID()+"' ";
//		BaseModel m_BaseSubModel = (new BaseSubManage()).getOneModel(baseID,schema,sqlStringBuffer.toString(),conditionString);
//		
//      	
//		//int m_BaseWorkFlowFlag		= Integer.parseInt(baseModel.getOneBaseFieldBean("BaseWorkFlowFlag").getM_BaseFieldValue());
//
//		Hashtable<String,BaseFieldBean>  baseFieldHashtable = m_BaseSubModel.getM_BaseFieldHashtable();
//		//读取工单特有信息
//		for(int i=0;i<m_BaseOwnFieldInfoList.size();i++)
//		{
//			BaseOwnFieldInfoModel m_BaseOwnFieldInfoModel = (BaseOwnFieldInfoModel)m_BaseOwnFieldInfoList.get(i);
//			long m_PrintOneLine				= m_BaseOwnFieldInfoModel.getPrintOneLine();
//			String m_Base_field_ShowName	= m_BaseOwnFieldInfoModel.getBase_field_ShowName();
//			String m_Base_field_Type		= m_BaseOwnFieldInfoModel.getBase_field_Type();
//			String m_Base_field_DBName		= m_BaseOwnFieldInfoModel.getBase_field_DBName();
//			
//			String m_Base_field_Value		= "";
//			if (baseFieldHashtable.size()>0)
//			{
//				if (baseFieldHashtable.containsKey(m_Base_field_DBName.toUpperCase())==true)
//				{
//					m_Base_field_Value = baseFieldHashtable.get(m_Base_field_DBName.toUpperCase()).getM_BaseFieldValue();
//				}
//			}
//			
//			ModifyFieldView modifyFieldView = new ModifyFieldView();
//			modifyFieldView.setName(m_Base_field_ShowName);
//			String m_str_field_ShowValue = "";
//			
//			if (m_Base_field_Type.equals("999") == false)
//			{
//				m_str_field_ShowValue = m_Base_field_Value;
//			}
//
//			modifyFieldView.setValue(m_str_field_ShowValue);
//			modifyFieldView.setPrintOneLine(m_PrintOneLine+"");
//			templite.add(modifyFieldView);
//		}
//		
//		return templite;
//	}
	/**
	 * 根据编号，工单名称，环节信息，封装用户输入信息列表（步骤表单）:采用modifylog进行步骤输入信息保存
	 * @param baseID
	 * @param schema
	 * @param pInfo
	 * @return
	 */
	public List drawSubFieldInfo(List<BaseOwnFieldInfoModel> m_BaseOwnFieldInfoList, String baseID,String schema,String processId, String taskId, String tplId){
		List templite = new ArrayList();
		
		//查询该环节所有的modifylog信息
		String conditionString =" C700021013='"+processId+"' ";
		if (!WorkflowUtils.isARflow(schema)) {
			conditionString =" taskId='"+taskId+"' ";
		}
		ModifyLogModel m_BaseSubModel = (new BaseSubManage()).getModifyLogModel(baseID, schema, conditionString);
		Map<String,BaseFieldBean>  baseFieldHashtable = m_BaseSubModel.getM_FieldHashtable();
		if(baseFieldHashtable==null) {
			baseFieldHashtable = new LinkedHashMap<String, BaseFieldBean>();			
		}
		
		int m_BFCount = m_BaseOwnFieldInfoList.size();
		for(int i=0;i<m_BFCount;i++) {		
			BaseOwnFieldInfoModel m_BaseOwnFieldInfoModel = (BaseOwnFieldInfoModel)m_BaseOwnFieldInfoList.get(i);
			String m_Base_field_DBName			= m_BaseOwnFieldInfoModel.getBase_field_DBName();
			long m_PrintOneLine				= m_BaseOwnFieldInfoModel.getPrintOneLine();
			String m_Base_field_ShowName	= m_BaseOwnFieldInfoModel.getBase_field_ShowName();
			String m_Base_field_Type		= m_BaseOwnFieldInfoModel.getBase_field_Type();
			String base_field_Purpose_Print = m_BaseOwnFieldInfoModel.getBase_field_Purpose_Print();
			String base_field_TypeValue = m_BaseOwnFieldInfoModel.getBase_field_TypeValue();
			if ("1".equals(base_field_Purpose_Print)) {
				for (Iterator iter = baseFieldHashtable.keySet().iterator(); iter.hasNext();) {
					String key = (String) iter.next();
					BaseFieldBean baseFieldBean = baseFieldHashtable.get(key);
					String dbName = baseFieldBean.getM_BaseFieldDBName();
					boolean isFind = false;
					if (dbName.equals(m_Base_field_DBName)) {
						String m_Base_field_Value = baseFieldBean.getM_BaseFieldValue();
						ModifyFieldView modifyFieldView = new ModifyFieldView();
						modifyFieldView.setDbName(m_Base_field_DBName);
						modifyFieldView.setName(m_Base_field_ShowName);
						String m_str_field_ShowValue = m_Base_field_Value;
						if ("4".equals(m_Base_field_Type)) {
							isFind = true;
						}
						if ("6".equals(m_Base_field_Type)) {//字典类型值
							String str_field_TypeValue_Array[] = base_field_TypeValue.split(";");
							for (int tmp_i = 0; tmp_i < str_field_TypeValue_Array.length ; tmp_i++) {
								String str_field_TypeValue_Tmp[] = str_field_TypeValue_Array[tmp_i].split("=");
								String field_TypeValue_DB_Value="";
								String field_TypeValue_DB_TureValue="";
								if(str_field_TypeValue_Tmp.length>1) {
									field_TypeValue_DB_Value			= str_field_TypeValue_Tmp[0];
									field_TypeValue_DB_TureValue		= str_field_TypeValue_Tmp[1];
									if (field_TypeValue_DB_Value.equals(m_Base_field_Value)) {
										m_str_field_ShowValue = field_TypeValue_DB_TureValue;
										break;
									}
								}
							}	  
							isFind = true;
						} else if ("8".equals(m_Base_field_Type)) {//字典值
							if (StringUtils.isNotBlank(base_field_TypeValue)) {
								DicManagerService dicManagerService  = (DicManagerService)WebApplicationManager.getBean("dicManagerService");
								DicItem di = dicManagerService.getDicItemByValue(base_field_TypeValue, m_Base_field_Value);
								if (di != null) {
									m_str_field_ShowValue = di.getDicfullname();
								}
							}
							isFind = true;
						} else if ("7".equals(m_Base_field_Type)) {//处理时间字段 
							m_str_field_ShowValue = m_Base_field_Value;
							if (StringUtils.isNotBlank(m_Base_field_Value) && m_Base_field_Value.indexOf(":") < 0) {
								m_str_field_ShowValue = TimeUtils.formatIntToDateString(NumberUtils.formatToInt(m_Base_field_Value));
							}
							isFind = true;
						} else if ("999".equals(m_Base_field_Type)) {
							if (!WorkflowUtils.isARflow(schema)) {
								StringBuffer sb = new StringBuffer();
								AttachmentManagerService attachService = (AttachmentManagerService)WebApplicationManager.getBean("attachmentManagerService");
								List<Attachment> attachs = attachService.getAttachmentByRelation(m_Base_field_Value);
								if (CollectionUtils.isNotEmpty(attachs)) {
									for (int j = 0; j < attachs.size(); j++) {
										Attachment atta = attachs.get(j);
										String path = WebUtils.getContextPath() + "/attachment/download.action?";
										String fileRealName = atta.getRealname();
										String fileName = atta.getName().replace(" ", "");
										String filePath = atta.getPath();
										sb.append("<a href=\""+path+"fileFileName="+fileName+"&fileNewName="+fileRealName+"&savePath="+filePath+"\")>");
										sb.append(atta.getName() + "；");
										sb.append("</a>");
									}
								}
								m_str_field_ShowValue = sb.toString();
							}
							isFind = true;
						}

						if (isFind) {
							modifyFieldView.setValue(m_str_field_ShowValue);
							modifyFieldView.setPrintOneLine(baseFieldBean.getColSpan()+"");
							templite.add(modifyFieldView);
							break;
						}
					}
				}
			}
		}
		return templite;
	}
	
	public List drawSubFieldInfoBpp(String baseID,String schema,String processId, String taskId, String tplId){
		List templite = new ArrayList();
		
		//查询该环节所有的modifylog信息
		String conditionString =" ";
		if (!WorkflowUtils.isARflow(schema)) {
			conditionString =" taskId='"+taskId+"' ";
		}
		ModifyLogModel m_BaseSubModel = (new BaseSubManage()).getModifyLogModel(baseID, schema, conditionString);
		Map<String,BaseFieldBean>  baseFieldHashtable = m_BaseSubModel.getM_FieldHashtable();
		if(baseFieldHashtable==null) {
			baseFieldHashtable = new Hashtable<String, BaseFieldBean>();			
		}
		
		for (Iterator iterator = baseFieldHashtable.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			BaseFieldBean baseFieldBean = baseFieldHashtable.get(key);
			int colSpan = baseFieldBean.getColSpan();
//			if (colSpan > 4) {
//				colSpan = 1;
//			} else {
//				colSpan = 0;
//			}
			if (baseFieldBean != null) {
				ModifyFieldView modifyFieldView = new ModifyFieldView();
				modifyFieldView.setDbName(baseFieldBean.getM_BaseFieldDBName());
				modifyFieldView.setName(baseFieldBean.getFieldLabel());
				modifyFieldView.setValue(baseFieldBean.getM_BaseFieldValue());
				modifyFieldView.setPrintOneLine(colSpan + "");
				templite.add(modifyFieldView);
			}
		}
		return templite;
	}

	/**
	 * 根据编号，工单名称，封装用户输入信息列表（主表单）
	 * @param baseid
	 * @param baseschema
	 * @return
	 */
	public List drawBaseFieldInfo(String baseid,String baseschema, String tplId) {
		
		List<BaseField> baseFields= (new BaseOwnFieldInfoManage()).getBppFieldList(baseschema, tplId, null);
		BaseModel m_BaseModel = (new BaseManage()).getOneModel(baseid, baseschema, "", "");
		List templite = new ArrayList();
		//读取工单特有信息
		for(int i=0;i<baseFields.size();i++)
		{
			BaseField baseField = (BaseField)baseFields.get(i);
			String m_Base_field_ShowName = baseField.getLabel();
			String m_Base_field_DBName = baseField.getFieldName();
			
			String m_Base_field_Value = "";
			BaseFieldBean oneBaseFieldBean = m_BaseModel.getOneBaseFieldBean(m_Base_field_DBName);
			if (oneBaseFieldBean != null) {
				m_Base_field_Value = baseField.getDisplayValue(oneBaseFieldBean.getM_BaseFieldValue());
				ModifyFieldView modifyFieldView = new ModifyFieldView();
				modifyFieldView.setName(m_Base_field_ShowName);
				modifyFieldView.setValue(m_Base_field_Value);
				modifyFieldView.setPrintOneLine(oneBaseFieldBean.getColSpan() + "");
				templite.add(modifyFieldView);
			}
		}
		return templite;
	}
	/**
	 * 通过环节信息得到与环节关联的步骤表单（自由流程适用）
	 * @param pInfo
	 * @return
	 */
	public String[] getSchemaByStepId(TplDealProcessModel pInfo){
		String baseschema = "";
		String baseid = "";
		
		int flagPredefined = pInfo.getFlagPredefined();
		
		if(flagPredefined == 0){
			String[] subforms = Constants.appBaseSubForm;
			HashMap<String, String[]> subformMap = new HashMap<String, String[]>(); 
			for(int i=0;i<subforms.length;i++){
				String[] stepidvalue = {};
				String tname = RemedySession.UtilInfor.getTableName(subforms[i]);//Constants.arschemaName2Id.get(subforms[i]);
				if(tname == null || tname.equals("") || tname.equals("T"))
				{
					continue;
				}
				QueryAdapter qAdapter  = new QueryAdapter();
				String sqlString = "select C700020001 as BaseId,C700038041 as Pro_processId from "+tname;
				DataTable dTable = qAdapter.executeQuery(sqlString, null, 0);
				for (int j = 0; j < dTable.length(); j++) {
					DataRow dRow = dTable.getDataRow(j);
					String processidString = dRow.getString("Pro_processId");
					String baseidString = dRow.getString("BaseId");
					if(processidString.equals(pInfo.getProcessID())){
						baseschema = subforms[i];
						baseid=baseidString;
					}
				}
			}
		}else{
			
		}
		String[] idandschema = {baseid,baseschema};
		return idandschema;
	}

}
