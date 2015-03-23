package com.ultrapower.eoms.workflow.sheet.attachment.service;

import java.util.List;

import com.ultrapower.eoms.ultrasm.model.Attachment;
import com.ultrapower.eoms.workflow.sheet.attachment.model.WfAttachment;

public interface WfAttachmentService {

	public void save(WfAttachment att);
	
	public List<WfAttachment> queryBySheetId(String sheetId);
	
	public void delete(String attachId);
	
	/**
	 * 根据工单ID、环节ID、类型ID获得附件集合
	 * @param sheetId
	 * @param processId
	 * @param fieldId
	 * @return
	 */
	public List<WfAttachment> getWfAttachment(String sheetId,String processId,String fieldId);
	
	/**
	 * 存储流程附件相关信息
	 * @param attachment --附件基本信息（非NULL）
	 * @param wfAttachment --工单和附件的关系信息（非NULL）
	 */
	public void saveWfAttach(Attachment attachment,WfAttachment wfAttachment);
	
	/**
	 * 根据工单的相关信息查询出相关的所有附件实体
	 * @param sheetId --该附件所属工单的GUID（必填参数）
	 * @param processId --该附件所属的环节ID，表示该附件在哪个环节上传的，如果是新建，则为NULL（非必填参数）
	 * @param fieldId --标识该附件的特定工单字段的ID，主要用来区分一张工单里面多个附件列表和记录各个附件列表里面附件的参数个数的特殊字段（非必填参数）
	 * @return
	 */
	public List<Attachment> getAttachmentByWfInfo(String sheetId,String processId,String fieldId);
	
	/**
	 * 将源(src)工单的附件拷贝至目标(tar)工单下
	 * @param srcSheetId --源附件所属工单的GUID（必填）
	 * @param srcProcessId --源附件所属的环节ID
	 * @param srcFieldId --源附件所属工单标识字段ID
	 * @param tarSheetId --目标附件所属工单的GUID（必须）
	 * @param tarProcessId --目标附件所属的环节ID
	 * @param tarFieldId --目标附件所属工单的标识字段ID
	 * @param tarBaseSchema --目标附件所属的工单的BaseSchema（必须）
	 * @param createrId --创建者PID
	 * @return -1:异常 number:拷贝了多少个附件
	 */
	public int copyWfAttachment(String srcSheetId, String srcProcessId, String srcFieldId
			, String tarSheetId, String tarProcessId, String tarFieldId, String tarBaseSchema, String createrId);
}
