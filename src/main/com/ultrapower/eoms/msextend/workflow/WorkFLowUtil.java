 package com.ultrapower.eoms.msextend.workflow;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;

import org.apache.commons.lang3.StringUtils;
import org.mortbay.log.Log;

import com.rits.cloning.Cloner;
import com.ultrapower.eoms.common.constants.PropertiesUtils;
import com.ultrapower.eoms.common.core.util.CryptUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.msextend.workflow.model.Assign;
import com.ultrapower.eoms.msextend.workflow.model.WfRecord;
import com.ultrapower.eoms.msextend.workflow.service.WorkflowService;
import com.ultrapower.eoms.ultrabpp.cache.model.ActionModel;
import com.ultrapower.eoms.ultrabpp.runtime.model.WorksheetCommitContext;
import com.ultrapower.eoms.ultrabpp.runtime.model.WorksheetDisplayContext;
import com.ultrapower.eoms.ultrasm.service.DepManagerService;
import com.ultrapower.eoms.ultrasm.service.UserManagerService;
import com.ultrapower.workflow.engine.task.model.ProcessTask;
import com.ultrapower.workflow.utils.ApplicationContextUtils;

/**
 * 工作流相关工具类
 * @author hhy
 *
 */
public class WorkFLowUtil {
	
	private static Set<Integer> params01;
	static WorkflowService workflowServiceEx;
	static UserManagerService userManagerService;
	static DepManagerService depManagerService;
	static{
		workflowServiceEx = (WorkflowService) ApplicationContextUtils.getBean("workflowServiceEx");
		userManagerService = (UserManagerService) ApplicationContextUtils.getBean("userManagerService");
		depManagerService = (DepManagerService) ApplicationContextUtils.getBean("depManagerService");
		
		params01 = new HashSet<Integer>();
		params01.add(0);
		params01.add(1);
	}
	
	/**
	 * 添加流程处理记录
	 * @param isView 0 不在页面显示,1 在页面显示
	 * @param dealDescField 处理意见的字段名
	 * @param isEmail 0 不发送邮件提醒,1 发送邮件提醒
	 * @param emailContent 传null根据邮件模板确定内容
	 * @param isSms 0 不发送短信提醒,1 发送短信提醒
	 * @param smsContent 穿null根据短信模板确定内容
	 * @param remark  邮件发送处理标识,对特殊情况进行处理,建单环节提交给处理人标识码为:createToRequestor
	 */
	public static void addWfRecord(WorksheetCommitContext commitCxt,Integer isView,String dealDescField,
			Integer isEmail,String emailContent,
			Integer isSms,String smsContent,String remark){
		
		if(!(isView != null && params01.contains(isView) 
				&& isEmail != null && params01.contains(isEmail) 
				&& isSms != null && params01.contains(isSms)))
			throw new InvalidParameterException("isView,isEmail,isSms只能取值为0,1");
		
		WfRecord record = new WfRecord();
		List<Assign> assigns = getAssigns(commitCxt.getAssignString());
		StringBuffer nextDealUser = new StringBuffer();
		StringBuffer nextDealUserLoginName = new StringBuffer();
		
		record.setBaseId(commitCxt.getBaseID());
		//根据标识字段进行插入不同的BaseShema
		if(remark.contains("createTo")){
			record.setBaseSchema(commitCxt.getBaseSchema()+"_CREATE_RECORDCONTENT");
		}else if(remark.contains("finishTo")){
			record.setBaseSchema(commitCxt.getBaseSchema()+"_CLOSE_RECORDCONTENT");
		}else{
		record.setBaseSchema(commitCxt.getBaseSchema());
		}
		record.setCurrentUser(commitCxt.getUserInfo().getFullName());
		record.setCurrentUserLoginName(commitCxt.getUserInfo().getLoginName());
		record.setDealAction(commitCxt.getActionText());
		record.setDealDesc(getFieldValue(commitCxt, dealDescField));
		record.setDealTime(Long.toString(TimeUtils.getCurrentTime()));
		
		if(isEmail.equals(1)){
			record.setIsEmail("1");
			if(emailContent == null){
//				record.setEmailContent("emailContentTest");
//				TODO hehaiyuan 邮件模板
			}else{
				record.setEmailContent(emailContent);
			}
		}else{
			record.setIsEmail("0");
		}
		record.setEmailSendFlag("0");
		
		if(isSms.equals(1)){
			record.setIsSms("1");
			if(smsContent == null){
//				record.setSmsContent("smsContent");
//				TODO hehaiyuan 短信模板
			}else{
				record.setEmailContent(smsContent);
			}
		}else{
			record.setIsSms("0");
		}
		record.setSmsSendFlag("0");
		
		record.setIsView(isView.toString());
		if("".equals(remark)||remark==null){
		if(assigns!=null){
			for(Assign a : assigns){
				nextDealUser.append(";").append(a.getActorName());
				nextDealUserLoginName.append(";").append(a.getActorType()).append(":").append(a.getActorID());
			}
			record.setNextDealUser(nextDealUser.deleteCharAt(0).append(";").toString());
			record.setNextDealUserLoginName(nextDealUserLoginName.deleteCharAt(0).append(";").toString());
		}
		}
		else if("createToRequestor".equals(remark)){
			String requstorId = "";
			requstorId = getRequstorId(commitCxt.getBaseSchema()); //获取不同流程的请求人ID
			record.setNextDealUser(nextDealUser.append(commitCxt.getFieldMap().get(requstorId.substring(0, requstorId.length()-2))).append(";").toString());
			record.setNextDealUserLoginName(nextDealUserLoginName.append("U:").append(commitCxt.getFieldMap().get(requstorId)).append(";").toString());
		}
		else if("finishToRequestor".equals(remark)){
			String requstorId = "";
			requstorId = getRequstorId(commitCxt.getBaseSchema()); //获取不同流程的请求人ID
			record.setNextDealUser(nextDealUser.append(commitCxt.getFieldMap().get(requstorId.substring(0, requstorId.length()-2))).append(";").toString());
			record.setNextDealUserLoginName(nextDealUserLoginName.append("U:").append(commitCxt.getFieldMap().get(requstorId)).append(";").toString());
		}
		else if("finishToCreator".equals(remark)){
			record.setNextDealUser(nextDealUser.append(commitCxt.getUserInfo().getFullName()).append(";").toString());
			record.setNextDealUserLoginName(nextDealUserLoginName.append("U:").append(commitCxt.getUserInfo().getLoginName()).append(";").toString());
		}
		else if("finishToHelpDesk".equals(remark)){
			String serviceAccount = PropertiesUtils.getPropertyMail("service.desk.loginname");
			record.setNextDealUser(nextDealUser.append(getFullNameByLoginName(serviceAccount)).append(";").toString());
			record.setNextDealUserLoginName(nextDealUserLoginName.append("U:").append(serviceAccount).append(";").toString());
		}
		
		workflowServiceEx.addWfRecord(record);
	}
	
	public static String getRequstorId(String baseSchema) {
		String requstorId ="";
		if("CBD_INCIDENT".equals(baseSchema)) requstorId="ContactPersonId";
		if("CDB_SERVICEREQUEST".equals(baseSchema)) requstorId="requestUserID";
		if("CDB_CHANGE".equals(baseSchema)) requstorId="requestUserId";
		return requstorId;
	}

	/**
	 * 添加流程处理记录,默认使用邮件,短信模板,通知字段名notify
	 * @param isView 0 不在页面显示,1 在页面显示
	 * @param notifyField 通知的字段名(数据源     sms:短信,email:邮件)
	 * @param dealDescField 处理意见的字段名
	 * @param emailContent 邮件发送内容
	 * @param remark 邮件发送处理标识,对特殊情况进行处理,建单环节提交给处理人标识码为:createToRequestor
	 */
	public static void addWfRecord(WorksheetCommitContext commitCxt,Integer isView,String notifyField,String dealDescField,String emailContent,String remark){
		String notify = ","+getFieldValue(commitCxt, notifyField)+",";
		Integer isEmail = 0;
		Integer isSms = 0;
		if(notify.indexOf("短信") > -1){
			isSms = 1;
		}
		if(notify.indexOf("邮件") > -1){
			isEmail = 1;
		}
		//特殊情况下直接发送
		if(!"".equals(remark)){
			isSms = 0;
			isEmail = 1;
			
		}
		//替换邮件发送的内容
		if(!"".equals(emailContent)&&emailContent!=null){
			emailContent = replaceContent(commitCxt,emailContent,remark);
		}
		
		addWfRecord(commitCxt, isView, dealDescField, isEmail, emailContent, isSms, null,remark);
	}
	
	
	/**
	 * 获取邮件模板配置内容 
	 * @param modelType
	 * @return
	 */
	public static String getMailContentByType(String modelType){
		String mailContent = "";
		if("".equals(modelType)||modelType==null){
			modelType = "DEFAULT";
		}
		List list = workflowServiceEx.getMailContentByType(modelType);
		if(list!=null&&list.size()>0){
		mailContent = (String) list.get(0);
		}else{
			mailContent = "no model config, please contact the administrator";
		}
		
		return mailContent;
		
	}
	
	
	/**
	 * 替换邮件内容
	 * @param commitCxt
	 * @param mailContent
	 * @return
	 */
	private static String replaceContent(WorksheetCommitContext commitCxt,String mailContent,String remark) {
		String resolveUser = "";
		String creatUser = commitCxt.getFieldMap().get("BASECREATORFULLNAME");
		String sLoginName = commitCxt.getFieldMap().get(WorkFLowUtil.getRequstorId(commitCxt.getBaseSchema()));//不同流程请求人ID
		
		List<WfRecord> recordList = workflowServiceEx.getWfRecordByCondition(commitCxt.getBaseID(), "处理完成");
		if(recordList!=null&&recordList.size()>0){
			resolveUser = recordList.get(0).getCurrentUser();
		}
		Cloner cloner=new Cloner();

			Map<String, String> fieldMap = cloner.deepClone(commitCxt.getFieldMap());
			 if("finishToCreator".equals(remark)){
				 sLoginName = commitCxt.getUserInfo().getLoginName();
			 }
			 if("finishToHelpDesk".equals(remark)){
				 sLoginName = PropertiesUtils.getPropertyMail("service.desk.loginname");
			 }
			 
			fieldMap.put("CreatUser", creatUser);
			fieldMap.put("LoginName", CryptUtils.getInstance().encode(sLoginName));
			fieldMap.put("BaseID", CryptUtils.getInstance().encode(commitCxt.getBaseID()));
			fieldMap.put("BaseSchema", CryptUtils.getInstance().encode(commitCxt.getBaseSchema()));
			fieldMap.put("ResolveUser", resolveUser);
			for(Object obj:fieldMap.entrySet()) {
				Entry entry = (Entry)obj;
				String key = (String) entry.getKey();
				String value = (String) entry.getValue();
				if(value!=null)
				mailContent = mailContent.replaceAll("#"+key+"#",Matcher.quoteReplacement(value));
			} 
		return mailContent;
	}
 
	/**
	 * 创建动作
	 */
	public static ActionModel createActionModel(String actionName, String actionType, String label, Integer isFree, Integer hasNext, Integer isClose, String desc) {
		ActionModel action = new ActionModel();
		action.setActionName(actionName);
		action.setActionType(actionType);
		action.setHasNext(hasNext);
		action.setIsClose(isClose);
		action.setIsFree(isFree);
		action.setLabel(label);
		action.setDescription(desc);
		return action;
	}
	
	/**
	 * 获取当前环节号
	 * @return
	 */
	public static String getCurrentTaskStepNo(WorksheetDisplayContext displayCxt){
		ProcessTask<?> task = displayCxt.getCurrentTask();
		return task == null ? "" : task.getStepNo();
	}
	/**
	 * 获取当前环节号
	 * @return
	 */
	public static String getCurrentTaskStepNo(WorksheetCommitContext commitCxt){
		ProcessTask<?> task = commitCxt.getCurrentTask();
		return task == null ? "" : task.getStepNo();
	}
	
	/**
	 * 获取字段值
	 * @param fieldName 字段名
	 * @return
	 */
	public static String getFieldValue(WorksheetDisplayContext displayCxt,String fieldName){
		return displayCxt.getFieldMap().get(fieldName);
	}
	/**
	 * 获取字段值
	 * @param fieldName 字段名
	 * @return
	 */
	public static String getFieldValue(WorksheetCommitContext commitCxt,String fieldName){
		return commitCxt.getFieldMap().get(fieldName);
	}
	
	/**
	 * 移除流程动作
	 */
	public static void removeAction(WorksheetDisplayContext displayCxt,String... actions){
//		List<ActionModel> list = displayCxt.getFixActionList();
//		ActionModel action = null;
//		for(ActionModel a : list){
//			if(a.getActionName().equals(actionName))
//				action = a;
//		}
//		if(action!=null)
//			list.remove(action);
		if(actions.length < 1) return;
		List<ActionModel> listFixAction = displayCxt.getFixActionList();
		List<ActionModel> listFreeAction = displayCxt.getFreeActionList();
		
		Set<String> actionSet = new HashSet<String>();
		for(String action : actions){
			actionSet.add(action);
		}
		
		List<ActionModel> listTemp = new ArrayList<ActionModel>();
		for(ActionModel action : listFixAction){
			if(actionSet.contains(action.getActionName()))
				listTemp.add(action);
		}
		listFixAction.removeAll(listTemp);
		listTemp = new ArrayList<ActionModel>();
		for(ActionModel action : listFreeAction){
			if(actionSet.contains(action.getActionName()))
				listTemp.add(action);
		}
		listFreeAction.removeAll(listTemp);
	}
	
	/**
	 * 只留下给定的动作
	 */
	public static void leaveActions(WorksheetDisplayContext displayCxt,String... actions){
		if(actions.length < 1) return;
		List<ActionModel> listFixAction = displayCxt.getFixActionList();
		List<ActionModel> listFreeAction = displayCxt.getFreeActionList();
		
		Set<String> actionSet = new HashSet<String>();
		for(String action : actions){
			actionSet.add(action);
		}
		
		List<ActionModel> listTemp = new ArrayList<ActionModel>();
		for(ActionModel action : listFixAction){
			if(actionSet.contains(action.getActionName())) continue;
			listTemp.add(action);
		}
		listFixAction.removeAll(listTemp);
		listTemp = new ArrayList<ActionModel>();
		for(ActionModel action : listFreeAction){
			if(actionSet.contains(action.getActionName())) continue;
			listTemp.add(action);
		}
		listFreeAction.removeAll(listTemp);
	}
	
	/**
	 * 是否是新建工单
	 */
	public static Boolean isNew(WorksheetDisplayContext displayCxt){
		return displayCxt.getEditType().equals("NEW");
	}
	
	/**
	 * 取全局变量
	 */
	public static String getAttribute(WorksheetDisplayContext displayCxt,String key){
		return displayCxt.getAttributeMap().get(key);
	}
	/**
	 * 取全局变量
	 */
	public static String getAttribute(WorksheetCommitContext commitCxt,String key){
		return commitCxt.getAttributeMap().get(key);
	}
	
	/**
	 * 根据传递的assignString获得处理人信息列表
	 */
	public static List<Assign> getAssigns(String assignString){
		if(StringUtils.isEmpty(assignString)) return null;
		String[] users = assignString.split("#;");
		List<Assign> assigns = new ArrayList<Assign>();
		for(String userStr : users){
			String[] user = userStr.split("#:",-1);
			if(user.length < 11) throw new RuntimeException("处理人规则串不正确");
			Assign assign = new Assign();
			assign.setActorType(user[0]);
			assign.setActorID(user[1]);
			assign.setActionCode(user[2]);
			assign.setDealType(user[3]);
			assign.setAcceptOutTime(user[4]);
			assign.setAssignOutTime(user[5]);
			assign.setDealOutTime(user[6]);
			assign.setRollbackStepID(user[7]);
			assign.setTargetPhaseNo(user[8]);
			assign.setSubflowVersionID(user[9]);
			assign.setDesc(user[10]);
			assigns.add(assign);
			if(assign.getActorType().equals("U")){
				assign.setActorName(userManagerService.getUserByLoginName(assign.getActorID()).getFullname());
			}else if(assign.getActorType().equals("G")){
				assign.setActorName(depManagerService.getDepNameByID(assign.getActorID()));
			}
		}
		
		return assigns;
	}

	public static ActionModel getAction(WorksheetDisplayContext displayCxt,String action){
		List<ActionModel> listFixAction = displayCxt.getFixActionList();
		List<ActionModel> listFreeAction = displayCxt.getFreeActionList();
		for(ActionModel ac : listFixAction){
			if(action.equals(ac.getActionName())) return ac;
		}
		for(ActionModel ac : listFreeAction){
			if(action.equals(ac.getActionName())) return ac;
		}
		return null;
	}
	
	public static void updateCustombaseInfor (Map<String, String> map){
	  if(map!=null){
	    workflowServiceEx.updateCustomBaseInfor(map);
	  }
	}
	public static  String getEmailByLoginName(String loginName){
		return userManagerService.getUserByLoginName(loginName).getEmail();
	}
	
	public static  String getFullNameByLoginName(String loginName){
		return userManagerService.getUserByLoginName(loginName).getFullname();
	}
	
}
