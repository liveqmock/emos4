package com.ultrapower.eoms.msextend.operateform.service;

/**
 *
 * @author yxg
 * @version May 17, 2013 5:45:56 PM
 */

public interface OperateFormService {
	/**
	 * 删除工单
	 * @param baseID
	 * @param baseSchema
	 * @return
	 */
	public boolean deleteForm(String baseID,String baseSchema);
	
	/**
	 * 作废工单
	 * @param baseID
	 * @param baseSchema
	 * @return
	 */
	public boolean doCancelForm(String baseID,String baseSchema,String loginName) throws Exception;
	
	/**
	 * 修改工单处理人
	 * @param baseID
	 * @param baseSchema
	 * @param assigneeid
	 * @param assignee
	 * @param assigneedepid
	 * @param assigneedep
	 * @param assigneecorpid
	 * @param assigneecorp
	 * @param assigneednid
	 * @param assigneedn
	 */
	public boolean modifyForm(String baseID, String baseSchema, String assigneeid,
			String assignee, String assigneedepid, String assigneedep,
			String assigneecorpid, String assigneecorp, String assigneednid,
			String assigneedn);
	
}
