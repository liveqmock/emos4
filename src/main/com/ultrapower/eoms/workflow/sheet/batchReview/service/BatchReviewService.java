package com.ultrapower.eoms.workflow.sheet.batchReview.service;

public interface BatchReviewService {

	/**
	 * 根据工单BaseId查询工单所关联的附件Id
	 * @param baseId String 工单baseId
	 * @return chgAttache
	 */
	
	public String getAttIdByBaseId(String baseSchema,String baseId);
	
	/**
	 * 批量审批时进行数据的克隆
	 * @param attaGroupId String  上传附件时生成的关联ID
	 * @param sheetRelationAttId String 每张工单与附件进行关联的ID
	 * @param baseId String 工单ID这里对克隆的文件进行标识,每张工单克隆的文件为工单"baseID_文件真实名称"
	 * @param baseSchema String 工单的类型
	 * 
	 */
	
	public void cloneDataRecord(String attaGroupId,String sheetRelationAttId, String baseId,String baseSchema);
	
	

}
