package com.ultrapower.eoms.msextend.operateform.web;

import java.util.Map;

import com.ultrapower.eoms.common.core.util.Internation;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.msextend.operateform.service.OperateFormService;
import com.ultrapower.eoms.ultrabpp.runtime.service.WorkSheetService;
import com.ultrapower.eoms.ultrasm.model.DepInfo;
import com.ultrapower.eoms.ultrasm.model.UserInfo;
import com.ultrapower.eoms.ultrasm.service.DepManagerService;
import com.ultrapower.eoms.ultrasm.service.UserManagerService;

/**
 *
 * @author yxg
 * @version May 17, 2013 5:45:12 PM
 * 删除工单
 */

public class OperateFormAction  extends BaseAction{
	
	private String baseID;
	private String baseSchema;
	private OperateFormService operateFormService;
	private String message;
	private String assigneeLoginName;
	private UserManagerService userManagerService; 
	private DepManagerService depManagerService;

	public String operateForm(){
		
		return SUCCESS;
	}
	
	public String doDeleteForm(){
		boolean flag = operateFormService.deleteForm(baseID, baseSchema);
		renderText(flag+"");
		return null;
	}
	
	public String doCancelForm(){
		boolean flag = false;
		UserSession userSession = getUserSession();
		try{
			operateFormService.doCancelForm(baseID, baseSchema,userSession.getLoginName());
			flag = true;
		}catch(Exception e){
			
		}
		renderText(flag+"");
		return null;
	}
	
	//弹出修改页面
	public String modifyTicketPage(){
		return SUCCESS;
	}

	public String modifyTicket(){
		boolean flag = false;
		try{
			//System.out.println(baseID+baseSchema+assigneeId);
			//根据用户的登录loginname查询相关信息
			UserInfo userInfo = userManagerService.getUserByLoginName(assigneeLoginName);
			String assigneeid = assigneeLoginName;
			String assignee = userInfo.getFullname();
			String assigneedepid = userInfo.getDepid();
			String assigneedep = userInfo.getDepname();
			String assigneecorpid = userInfo.getCompanyId();
			String assigneecorp = userInfo.getCompanyName();
			DepInfo depInfo =  depManagerService.getDepByID(assigneedepid);
			String assigneednid = depInfo.getDepdns();
			String assigneedn = depInfo.getDepfullname();
			flag = operateFormService.modifyForm(baseID, baseSchema,assigneeid,assignee,assigneedepid,assigneedep,assigneecorpid,assigneecorp,assigneednid,assigneedn);
		}catch(Exception e){
			flag = false;
		}
		renderText(flag+"");
		return null;
	}
	
	
	public String getBaseID() {
		return baseID;
	}

	public void setBaseID(String baseID) {
		this.baseID = baseID;
	}

	public String getBaseSchema() {
		return baseSchema;
	}

	public void setBaseSchema(String baseSchema) {
		this.baseSchema = baseSchema;
	}

	public OperateFormService getOperateFormService() {
		return operateFormService;
	}

	public void setOperateFormService(OperateFormService operateFormService) {
		this.operateFormService = operateFormService;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAssigneeLoginName() {
		return assigneeLoginName;
	}

	public void setAssigneeLoginName(String assigneeLoginName) {
		this.assigneeLoginName = assigneeLoginName;
	}

	public UserManagerService getUserManagerService() {
		return userManagerService;
	}

	public void setUserManagerService(UserManagerService userManagerService) {
		this.userManagerService = userManagerService;
	}

	public DepManagerService getDepManagerService() {
		return depManagerService;
	}

	public void setDepManagerService(DepManagerService depManagerService) {
		this.depManagerService = depManagerService;
	}

	

	
}
