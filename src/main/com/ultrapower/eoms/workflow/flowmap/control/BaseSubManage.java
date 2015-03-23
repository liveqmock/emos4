package com.ultrapower.eoms.workflow.flowmap.control;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.util.NumberUtils;
import com.ultrapower.eoms.workflow.flowmap.model.BaseFieldBean;
import com.ultrapower.eoms.workflow.flowmap.model.BaseModel;
import com.ultrapower.eoms.workflow.flowmap.model.ModifyLogModel;
import com.ultrapower.eoms.workflow.flowmap.util.RDSelectSql;
import com.ultrapower.eoms.workflow.util.WorkflowUtils;
import com.ultrapower.remedy4j.core.RemedySession;

/**
 * 
 * Copyright (c) 2010 神州泰岳服务管理事业部通用产品
 * All rights reserved.
 * 描述：步骤表单的对象查询
 * @author 范莹
 * @date   2010-4-21
 */
public class BaseSubManage {
	
	/**
	 * 
	 * 通过工单参数获得步骤表单对象BaseModel
	 * @param baseid       工单编号
	 * @param baseschema   工单名称 
	 * @param selectrow	   选择的工单特有的field字段查询条件
	 * @param condition	   查询条件
	 * @return BaseModel   返回工单对象
	 */
	public BaseModel getOneModel(String baseid,String baseschema,String selectrow,String condition)
	{
		BaseModel	m_BaseModel	= new BaseModel();
		String tname = RemedySession.UtilInfor.getTableName(baseschema);//Constants.arschemaName2Id.get(baseschema);
		try
		{
			//获得查询SQL
	      	String m_sql			=  RDSelectSql.BaseSubOn_BaseSubIDSelectBaseSub.toString();
	      	m_sql = m_sql +selectrow+ " from "+tname+" BaseSub where C700020001="+baseid+" and "+(condition.length()>0?condition:" 1=1");
			System.out.println(m_sql.toString());
			QueryAdapter qAdapter = new QueryAdapter();
			DataTable dTable = qAdapter.executeQuery(m_sql.toString(), null, 0);
			DataRow dRow = dTable.length()>0?dTable.getDataRow(0):null;
			if (dRow!=null) 
			{
				m_BaseModel = setModelValue(dRow);
			}

		}
		catch (Exception ex) {
			ex.printStackTrace();
		} 
		return m_BaseModel;
	}
	
	/**
	 * 
	 * 通过工单参数获得步骤表单对象列表
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
	 * 将从数据库中查询到的数据封装为对象
	 * @param  rs
	 * @return BaseModel
	 * @throws SQLException
	 */
	private BaseModel setModelValue(DataRow rs) throws SQLException
	{
		String name = "";
		int cols = rs.length();
		Hashtable<String,BaseFieldBean>	m_BaseFieldHashtable	= new Hashtable<String,BaseFieldBean>();
		for (int i = 1; i <= cols; i++) {
			
			name = rs.getKey(i);
			String value= rs.getString(name);
			
			BaseFieldBean m_BaseFieldBean = new BaseFieldBean();
			m_BaseFieldBean.setM_BaseFieldDBName(name);
			m_BaseFieldBean.setM_BaseFieldValue(value);
			m_BaseFieldHashtable.put(name,m_BaseFieldBean);
		}
		BaseModel m_BaseModel = new BaseModel();
		m_BaseModel.setM_BaseFieldHashtable(m_BaseFieldHashtable);
		return m_BaseModel;
	}
	/**
	 * 将从数据库中查询到的数据封装为对象
	 * @param  rs
	 * @return ModifyLogModel
	 * @throws SQLException
	 */
	private ModifyLogModel setModifyLogModelValue(DataRow rs) throws SQLException
	{
		String name = "";
		int cols = rs.length();
		Hashtable<String,BaseFieldBean>	m_BaseFieldHashtable	= new Hashtable<String,BaseFieldBean>();
		for (int i = 1; i <= cols; i++) {
			
			name = rs.getKey(i);
			String value= rs.getString(name);
			
			BaseFieldBean m_BaseFieldBean = new BaseFieldBean();
			m_BaseFieldBean.setM_BaseFieldDBName(name);
			m_BaseFieldBean.setM_BaseFieldValue(value);
			m_BaseFieldHashtable.put(name,m_BaseFieldBean);
		}
		ModifyLogModel m_BaseModel = new ModifyLogModel();
		m_BaseModel.setM_FieldHashtable(m_BaseFieldHashtable);
		return m_BaseModel;
	}
	/**
	 * 2010-08-26修改
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	
	private ModifyLogModel setFiledModifyLog(DataTable dTable) throws SQLException
	{
		
		int cols = dTable.length();
		Map<String,BaseFieldBean>	m_BaseFieldHashtable = new LinkedHashMap<String,BaseFieldBean>();
		for (int i = 0; i < cols; i++) {
			DataRow dRow = dTable.getDataRow(i);
			String NewValue = dRow.getString("fieldValue");
			String FieldDBName = dRow.getString("fieldCode");
			String fieldLabel = dRow.getString("fieldLabel");
			String colSpan = dRow.getString("colSpan");
			
			
			BaseFieldBean m_BaseFieldBean = new BaseFieldBean();
			m_BaseFieldBean.setM_BaseFieldDBName(FieldDBName);
			m_BaseFieldBean.setM_BaseFieldValue(NewValue);
			m_BaseFieldBean.setFieldLabel(fieldLabel);
			m_BaseFieldBean.setColSpan(NumberUtils.formatToInt(colSpan));
			m_BaseFieldHashtable.put(FieldDBName,m_BaseFieldBean);
		}
		ModifyLogModel m_BaseModel = new ModifyLogModel();
		m_BaseModel.setM_FieldHashtable(m_BaseFieldHashtable);
		return m_BaseModel;
	}
	
	/**
	 * 通过工单参数获得工单步骤表单对象,ModifyLogModel
	 * 2010-08-26修改
	 **/
	public ModifyLogModel getModifyLogModel(String baseid,String schema,String condition){
		ModifyLogModel	m_LogModel	= new ModifyLogModel();
		if (WorkflowUtils.isARflow(schema)) {
			String tname = RemedySession.UtilInfor.getTableName(schema+"_FieldModifyLog");
			try
			{
				//获得查询SQL
				String m_sql			=  "";
				m_sql = "select C700021010 as fieldValue,C700021004 as FieldID,C700021006 as fieldCode from "+tname+" ModifyLog where C700021001="+baseid+" and "+(condition.length()>0?condition:" 1=1") + " order by C700021005";
				System.out.println(m_sql.toString());
				QueryAdapter qAdapter = new QueryAdapter();
				DataTable dTable = qAdapter.executeQuery(m_sql.toString(), null, 0);
				DataRow dRow =dTable!=null?( dTable.length()>0?dTable.getDataRow(0):null):null;
				if (dRow!=null) 
				{
					m_LogModel = setFiledModifyLog(dTable);
				}
				
			}
			catch (Exception ex) {
				ex.printStackTrace();
			} 
		} else {
			try {
				String sql = "select * from BS_F_"+schema+"_FML where baseid='"+baseid+"' and "+(condition.length()>0?condition:" 1=1") + " and fieldType !='HiddenField' order by orderNum,modifydate";
				QueryAdapter qAdapter = new QueryAdapter();
				DataTable dTable = qAdapter.executeQuery(sql.toString(), null, 0);
				DataRow dRow =dTable!=null?( dTable.length()>0?dTable.getDataRow(0):null):null;
				if (dRow!=null) 
				{
					m_LogModel = setFiledModifyLog(dTable);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return m_LogModel;
	}
}
