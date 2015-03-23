package com.ultrapower.eoms.msextend.tempAttachment.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.msextend.tempAttachment.model.TempAttachment;
import com.ultrapower.eoms.msextend.tempAttachment.service.ITempAttachmentService;

/**
 * @author yxg
 * @version 创建时间：Jan 16, 2013 11:37:20 AM
 * 类说明：
 */

public class TempAttachmentImpl implements ITempAttachmentService{
	
	private IDao<TempAttachment>  tempAttachmentDao;
	
	/**
	 * 根据流程分类ID获取流程类型
	 * @param wfSortId
	 * @return
	 */
	public List<TempAttachment> getTempAttachmentListBySortId(String wfSortId){
		String hql = "from TempAttachment t where t.sortid = ? ";
		List<TempAttachment> results = tempAttachmentDao.find(hql, wfSortId);
		return results;
	}
	
	/**
	 * 根据baseschema取得记录
	 */
	public TempAttachment getTempAttachmentByBaseSchema(String baseSchema){
		TempAttachment tempAttachment = null;
		String hql = "from TempAttachment t where t.baseSchema = ? ";
		List<TempAttachment> results = tempAttachmentDao.find(hql, baseSchema);
		if(results != null && results.size() > 0){
			//如果有多条记录，取第一条，删除其他
			tempAttachment = results.get(0);
			if(results.size() > 1){
				TempAttachment rtemp;
				for(int i=1;i<results.size();i++){
					rtemp = results.get(i);
					tempAttachmentDao.remove(rtemp);
				}
			}
		}
		return tempAttachment;
	}
	
	public boolean saveOrUpdateTempAttachment(TempAttachment tempAttachment) {
		tempAttachmentDao.saveOrUpdate(tempAttachment);
		return true;
	}

	public TempAttachment getTempAttachment(String tempAttachmentId) {
		TempAttachment tempAttachment = tempAttachmentDao.get(tempAttachmentId);
		return tempAttachment;
	}

	public void delTempAttachment(String delIds) {
		if (delIds != null && !"".equals(delIds)) {
			String[] ids = delIds.split(",");
			if (ids != null && ids.length > 0) {
				for (int i = 0; i < ids.length; i++) {
					tempAttachmentDao.removeById(ids[i]);
				}
			}
		}
	}
	
	/**
	 * 根据baseID与baseSchema查询附件RelationCode
	 * @param baseID
	 * @param baseSchema
	 * @return
	 */
	public String getRelationCode(String baseID,String baseSchema,String relateBaseID,String relateBaseSchema){
		String attachRelationCode = "";
		// 查询关联的工单信息
		Map<String,List<String>> resultMap = this.getWFRelation(baseID, baseSchema, relateBaseID, relateBaseSchema);
		if(resultMap == null){
			return "";
		}
		for(Map.Entry<String, List<String>> map:resultMap.entrySet()){
			String mapBaseSchema = StringUtils.checkNullString(map.getKey());
			List baseIDList = map.getValue();
			if("".equals(mapBaseSchema) || baseIDList == null || baseIDList.size() == 0){
				continue;
			}
			StringBuffer baseIDBuffer = new StringBuffer();
			for(int i=0;i<baseIDList.size();i++){
				if(i==0){
					baseIDBuffer.append("and (");
				}else{
					baseIDBuffer.append(" or ");
				}
				baseIDBuffer.append("baseid='");
				baseIDBuffer.append(baseIDList.get(i));
				baseIDBuffer.append("'");
			}
			baseIDBuffer.append(")");
			// 查询工单上存储附件的字段
			String fieldName = this.getFieldNameByBaseSchema(mapBaseSchema);
			if("".equals(StringUtils.checkNullString(fieldName))){
				return attachRelationCode;
			}
			String baseTable = "BS_F_" + mapBaseSchema;
			String sql = "select "+fieldName+" from "+ baseTable + " where 1=1 "+baseIDBuffer.toString();
			
			String spFieldName[] = fieldName.split(",");
			QueryAdapter queryAdapter = new QueryAdapter();
			DataTable dataTable = queryAdapter.executeQuery(sql);
			if(dataTable != null && dataTable.length() > 0){
				for(int m=0;m<dataTable.length();m++){
					DataRow dataRow = dataTable.getDataRow(m);
					for(int i=0;i<spFieldName.length;i++){
						String fieldNameStr = spFieldName[i];
						if(!"".equals(StringUtils.checkNullString(fieldNameStr))){
							String re = StringUtils.checkNullString(dataRow.getString(fieldNameStr));
							if(!"".equals(re)){
								if("".equals(attachRelationCode)){
									attachRelationCode = re;
								}else{
									attachRelationCode = attachRelationCode + "," + re;
								}
							}
						}
					}
				}
			}
		}
		return attachRelationCode;
	}
	
	/**
	 * 关联查询
	 * @param baseID
	 * @param baseSchema
	 * @param relateBaseID
	 * @param relateBaseSchema
	 * @return
	 */
	private Map<String,List<String>> getWFRelation(String baseID,String baseSchema,String relateBaseID,String relateBaseSchema){
		Map<String,List<String>> resultMap = null;
		if("".equals(StringUtils.checkNullString(baseID)) && "".equals(StringUtils.checkNullString(relateBaseID))){
			return null;
		}
		StringBuffer buffer = new StringBuffer("select BASEID,BASESCHEMA,RELATEBASEID,RELATEBASESCHEMA from BS_T_WF_RELATION r where 1=1 ");
		if(!"".equals(StringUtils.checkNullString(baseID))){
			String[] spBaseID = StringUtils.checkNullString(baseID).split(",");
			for(int i=0;i<spBaseID.length;i++){
				if(i != 0){
					buffer.append(" or ");
				}else{
					buffer.append(" and (");
				}
				buffer.append(" r.baseid = '");
				buffer.append(spBaseID[i]);
				buffer.append("'");
				if(i == spBaseID.length -1){
					buffer.append(")");
				}
			}
		}
		if(!"".equals(StringUtils.checkNullString(baseSchema))){
			String[] spBaseSchema = StringUtils.checkNullString(baseSchema).split(",");
			for(int i=0;i<spBaseSchema.length;i++){
				if(i != 0){
					buffer.append(" or ");
				}else{
					buffer.append(" and (");
				}
				buffer.append(" r.BASESCHEMA = '");
				buffer.append(spBaseSchema[i]);
				buffer.append("'");
				if(i == spBaseSchema.length -1){
					buffer.append(")");
				}
			}
		}
		if(!"".equals(StringUtils.checkNullString(relateBaseID))){
			String[] spRelateBaseID = StringUtils.checkNullString(relateBaseID).split(",");
			for(int i=0;i<spRelateBaseID.length;i++){
				if(i != 0){
					buffer.append(" or ");
				}else{
					buffer.append(" and (");
				}
				buffer.append(" r.RELATEBASEID = '");
				buffer.append(spRelateBaseID[i]);
				buffer.append("'");
				if(i == spRelateBaseID.length -1){
					buffer.append(")");
				}
			}
		}
		if(!"".equals(StringUtils.checkNullString(relateBaseSchema))){
			String[] spRelateBaseSchema = StringUtils.checkNullString(relateBaseSchema).split(",");
			for(int i=0;i<spRelateBaseSchema.length;i++){
				if(i != 0){
					buffer.append(" or ");
				}else{
					buffer.append(" and (");
				}
				buffer.append(" r.RELATEBASESCHEMA = '");
				buffer.append(spRelateBaseSchema[i]);
				buffer.append("'");
				if(i == spRelateBaseSchema.length -1){
					buffer.append(")");
				}
			}
		}
		
		// 查询关联的工单
		QueryAdapter queryAdapter = new QueryAdapter();
		DataTable dataTable = queryAdapter.executeQuery(buffer.toString());
		if(dataTable != null && dataTable.length() > 0){
			resultMap = new HashMap<String,List<String>>();
			for(int i=0;i<dataTable.length();i++){
				DataRow dataRow = dataTable.getDataRow(i);
				String baseIDStr = StringUtils.checkNullString(dataRow.getString("BASEID"));
				String baseSchemaStr = StringUtils.checkNullString(dataRow.getString("BASESCHEMA"));
				String relateBaseIDStr = StringUtils.checkNullString(dataRow.getString("RELATEBASEID"));
				String relateBaseSchemaStr = StringUtils.checkNullString(dataRow.getString("RELATEBASESCHEMA"));
				if("".equals(relateBaseSchemaStr) || "".equals(relateBaseIDStr)){
					continue;
				}
				if(!"".equals(StringUtils.checkNullString(relateBaseID))){
					List resultList = resultMap.get(baseSchemaStr);
					if(resultList == null){
						resultList = new ArrayList<String>();
						resultList.add(baseIDStr);
						resultMap.put(baseSchemaStr, resultList);
					}else{
						resultList.add(baseIDStr);
					}
				}else{
					List resultList = resultMap.get(relateBaseSchemaStr);
					if(resultList == null){
						resultList = new ArrayList<String>();
						resultList.add(relateBaseIDStr);
						resultMap.put(relateBaseSchemaStr, resultList);
					}else{
						resultList.add(relateBaseIDStr);
					}
				}
			}
		}
		return resultMap;
	}
	
	/**
	 * 获取该工单附件字段
	 * @param baseSchema
	 * @return
	 */
	public String getFieldNameByBaseSchema(String baseSchema){
		String fieldName = "";
		String sql = "select FIELDNAME from bs_t_bpp_f_attachment t WHERE t.baseschema = ?";
		QueryAdapter queryAdapter = new QueryAdapter();
		DataTable dataTable = queryAdapter.executeQuery(sql, new Object[] {baseSchema});
		for(int i=0;i<dataTable.length();i++){
			DataRow dataRow = dataTable.getDataRow(i);
			if(i==0){
				fieldName = dataRow.getString("FIELDNAME");
			}else{
				fieldName = fieldName + "," + dataRow.getString("FIELDNAME");
			}
		}
		return fieldName;
	}
	
	/**
	 * 根据baseID与baseSchema查询附件组id
	 * @param baseID
	 * @param baseSchema
	 * @return
	 */
	public String getAttchmentGroupId(String baseID,String baseSchema){
		String attchmentGroupId = "";
		// 查询工单上存储附件的字段
		String fieldName = this.getFieldNameByBaseSchema(baseSchema);
		if("".equals(StringUtils.checkNullString(fieldName))){
			return attchmentGroupId;
		}
		String baseTable = "BS_F_" + baseSchema;
		String sql = "select "+fieldName+" from "+ baseTable + " where 1=1 and baseID = '"+baseID+"'";
		
		String spFieldName[] = fieldName.split(",");
		QueryAdapter queryAdapter = new QueryAdapter();
		DataTable dataTable = queryAdapter.executeQuery(sql);
		if(dataTable != null && dataTable.length() > 0){
			DataRow dataRow = dataTable.getDataRow(0);
			if(spFieldName != null && spFieldName.length > 0){
				String fieldNameStr = spFieldName[0];
				if(!"".equals(StringUtils.checkNullString(fieldNameStr))){
					String re = StringUtils.checkNullString(dataRow.getString(fieldNameStr));
					if(!"".equals(re)){
						attchmentGroupId = re;
					}
				}
			}
		}
		return attchmentGroupId;
	}

	public IDao<TempAttachment> getTempAttachmentDao() {
		return tempAttachmentDao;
	}

	public void setTempAttachmentDao(IDao<TempAttachment> tempAttachmentDao) {
		this.tempAttachmentDao = tempAttachmentDao;
	}

	public TempAttachment getTempAttachmentByBaseSchema(String baseSchema,
			String belongvalue) {
		TempAttachment tempAttachment = null;
		String hql = "from TempAttachment t where t.baseSchema = ? and t.belongvalue is null ";
		String [] value = new String[]{baseSchema};
		if(null!=belongvalue&&!"".equals(belongvalue)){
			 hql = "from TempAttachment t where t.baseSchema = ? and t.belongvalue = ? ";
			 value = new String[]{baseSchema,belongvalue};
		}
		List<TempAttachment> results = tempAttachmentDao.find(hql, value);
		if(results != null && results.size() > 0){
			//如果有多条记录，取第一条，删除其他
			tempAttachment = results.get(0);
			if(results.size() > 1){
				TempAttachment rtemp;
				for(int i=1;i<results.size();i++){
					rtemp = results.get(i);
					tempAttachmentDao.remove(rtemp);
				}
			}
		}
		return tempAttachment;
	}
	
}
