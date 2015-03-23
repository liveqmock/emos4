package com.ultrapower.eoms.workflow.api.base;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.workflow.api.bean.BaseFieldInfo;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ultrapower.eoms.common.core.component.data.DataAdapter;
import com.ultrapower.randomutil.Random15;
import com.ultrapower.randomutil.RandomN;
import com.ultrapower.remedy4j.core.RemedyField;
import com.ultrapower.remedy4j.core.RemedySession;

public class Utils {
	
	private final static Logger log = LoggerFactory.getLogger(Utils.class);
	
	protected Map<String, BaseFieldInfo> baseAllFields = new Hashtable<String, BaseFieldInfo>();
	
	/**
	 * 打印调试信息
	 */
	protected void printFields() {
		log.debug("开始打印baseAllFields信息！");
		String key = null;
		for (Iterator<String> it = baseAllFields.keySet().iterator(); it.hasNext();) {
			key = (String) it.next();
			BaseFieldInfo fieldInfo = (BaseFieldInfo) baseAllFields.get(key);
			log.debug(key + " : " + fieldInfo.getId() + " = " + fieldInfo.getValue());
		}
	}
	
	protected String getGUID() {
		RandomN random = new Random15();
		return random.getRandom(System.currentTimeMillis());
	}
	
	public String getFieldValue(String fieldName) {
		BaseFieldInfo baseField = baseAllFields.get(fieldName);
		if (baseField == null) {
			return "";
		}
		return baseField.getValue();
	}
	
	public BaseFieldInfo getField(String fieldName) {
		BaseFieldInfo baseField = baseAllFields.get(fieldName);
		if (baseField != null) {
			return baseField;
		}
		return null;
	}
	
	protected void setFieldValue(String fieldName,String fieldValue) {
		try {
			((BaseFieldInfo)baseAllFields.get(fieldName)).setValue(fieldValue);			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("设置值出错fieldName="+fieldName+",fieldValue=" + fieldValue, e);
			throw new RuntimeException("设置值出错fieldName="+fieldName+",fieldValue=" + fieldValue);
		}
	}
	
	protected void setFieldFromField(String tarField,String srcField) {
		BaseFieldInfo srcFieldInfo = baseAllFields.get(srcField);
		String srcValue = "";
		if (srcFieldInfo != null) {
			srcValue = ((BaseFieldInfo)srcFieldInfo).getValue();
		}
		BaseFieldInfo tarFieldInfo = baseAllFields.get(tarField);
		if (tarFieldInfo != null) {
			tarFieldInfo.setValue(srcValue);
		}
	}

	protected List<RemedyField> convert() {
		List<RemedyField> remedyList = new ArrayList<RemedyField>();
		for (Iterator<String> it = baseAllFields.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			BaseFieldInfo baseFieldInfo = (BaseFieldInfo) baseAllFields.get(key);
			if (baseFieldInfo != null) {
				int fieldType = baseFieldInfo.getType();
				String fieldID = baseFieldInfo.getId();
				String fieldValue = baseFieldInfo.getValue();
				if (StringUtils.isNotBlank(fieldID)) {
					RemedyField remedyFieldInfo = new RemedyField(fieldID, "", fieldType, fieldValue);
					remedyList.add(remedyFieldInfo);
				}
			}
		}	
		return remedyList;
	}
	
	/**
	 * 新建AR实体
	 * @param baseSchema
	 * @param fieldList
	 * @return
	 */
	protected String remedyEntryInsert(String baseSchema,List<RemedyField> fieldList) {
		RemedySession session = new RemedySession("Demo", RemedySession.UtilInfor.DEMO_PASSWORD);
		session.login();
		String c1 = session.addEntry(baseSchema, fieldList);
		session.logout();
		return c1;
	}

	/**
	 * 修改AR实体
	 * @param baseSchema
	 * @param entryId
	 * @param fields
	 * @return
	 */
	protected boolean remedyEntryUpdate(String baseSchema,String entryId,List<RemedyField> fields) {
		RemedySession session = new RemedySession("Demo", RemedySession.UtilInfor.DEMO_PASSWORD);
		boolean success = session.updateEntry(baseSchema, entryId, fields);
		return success;
	}
	
	/**
	 * 修改与操作有关的信息表的C2字段C2字段
	 * @param baseSchema
	 * @param baseId
	 * @return
	 */
	protected boolean baseUpdataC2(String baseSchema, String baseId) {
		boolean blnRe = true;
		String tblName = RemedySession.UtilInfor.getTableName(baseSchema);
		String strSql;
		try {
			if (StringUtils.isNotBlank(baseId)) {
				DataAdapter dAdapter = new DataAdapter();
				// 更新工单C2信息
				strSql = " update " + tblName + " set C2='Demo'" + " where C1='" + baseId + "'";
				dAdapter.execute(strSql, null);

				// 更新baseinfo
				tblName = RemedySession.UtilInfor.getTableName("WF4:App_Base_Infor");
				strSql = " update " + tblName + " set C2='Demo'";
				strSql += " where C700000000='" + baseId + "' and C700000001='" + baseSchema + "'";
				dAdapter.execute(strSql, null);
			}
		} catch (Exception ex) {
			log.error("更新操作表的c2字段失败", ex);
			blnRe = false;
		}
		return blnRe;
	}
}
