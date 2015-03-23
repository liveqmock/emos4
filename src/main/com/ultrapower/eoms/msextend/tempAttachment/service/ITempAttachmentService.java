package com.ultrapower.eoms.msextend.tempAttachment.service;

import java.util.List;

import com.ultrapower.eoms.msextend.tempAttachment.model.TempAttachment;

/**
 * @author yxg
 * @version 创建时间：Jan 16, 2013 11:37:20 AM
 * 类说明：
 */

public interface ITempAttachmentService {
		
	public boolean saveOrUpdateTempAttachment(TempAttachment tempAttachment);
	
	public TempAttachment getTempAttachmentByBaseSchema(String baseSchema);
	
	public TempAttachment getTempAttachmentByBaseSchema(String baseSchema,String belongvalue);
	
	public TempAttachment getTempAttachment(String tempAttachmentId);
	
	/**
	 * 根据流程分类ID获取流程类型
	 * @param wfSortId
	 * @return
	 */
	public List<TempAttachment> getTempAttachmentListBySortId(String wfSortId);
	
	public void delTempAttachment(String delIds);
	
	/**
	 * 根据baseID与baseSchema查询附件RelationCode
	 * @param baseID
	 * @param baseSchema
	 * @return
	 */
	public String getRelationCode(String baseID,String baseSchema,String relateBaseID,String relateBaseSchema);
	
	/**
	 * 根据baseID与baseSchema查询附件组id
	 * @param baseID
	 * @param baseSchema
	 * @return
	 */
	public String getAttchmentGroupId(String baseID,String baseSchema);
	
}
