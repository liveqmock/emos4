package com.ultrapower.eoms.ultrabpp.runtime.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.rquery.RQuery;
import com.ultrapower.eoms.common.core.util.NumberUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.service.PortalManagerService;
import com.ultrapower.eoms.msextend.workflow.WorkFLowUtil;
import com.ultrapower.eoms.ultrabpp.cache.model.ActionModel;
import com.ultrapower.eoms.ultrabpp.cache.model.EditableFieldModel;
import com.ultrapower.eoms.ultrabpp.cache.model.ThemeModel;
import com.ultrapower.eoms.ultrabpp.cache.service.ThemeCacheService;
import com.ultrapower.eoms.ultrabpp.develop.manager.DeployManagerImpl;
import com.ultrapower.eoms.ultrabpp.develop.service.DeployService;
import com.ultrapower.eoms.ultrabpp.runtime.model.WorksheetCommitContext;
import com.ultrapower.eoms.ultrabpp.runtime.model.WorksheetDisplayContext;
import com.ultrapower.eoms.ultrabpp.runtime.service.ProcessService;
import com.ultrapower.eoms.ultrabpp.runtime.service.WorkSheetService;
import com.ultrapower.eoms.ultrabpp.runtime.service.WorkflowRoleService;
import com.ultrapower.eoms.ultrabpp.runtime.service.WorkflowService;
import com.ultrapower.eoms.ultrabpp.utils.ProcessUtil;
import com.ultrapower.workflow.bizconfig.sort.IWfSortManager;
import com.ultrapower.workflow.client.WorkFlowServiceClient;
import com.ultrapower.workflow.configuration.sort.model.WfType;


import edu.emory.mathcs.backport.java.util.Collections;

public class BaseProcessAction extends BaseAction
{
	private static final long serialVersionUID = -408791393341736158L;
	
	protected String mode;
	private String baseID;
	private String baseSchema;
	private String defCode;
	private String taskID;
	private Map<String, String> fieldMap;
	private String actionID;
	private String actionType;
	private String actionText;
	private String closeAfterCommit;
	private Map<String, String> attributeMap;
	private WorksheetDisplayContext displayCxt;
	
	private WorkSheetService workSheetService;
	private WorkflowService workflowService;
	private WorkflowRoleService workflowRoleService;
	private ProcessService processService;
	private PortalManagerService portalManagerService;
	private ThemeCacheService themeCacheService;
	
	private DeployService deployService;
	private String actionName;
	private String stepNo;
	private String baseStatus;
	
	//工单关联属性
	private String cRelateData;//是否复制数据，1：是，2：否
	private String cRelateByConfig;//是否根据特殊逻辑为数据赋值，1：是，2：否 只有在复制数据为1时，此参数才生效
	private String relateType;
	private String fromBaseSchema;
	private String fromBaseID;
	private String fromTaskID;
	private String fromBaseSn;
	private String customizedPage;//自定义的工单主页面
	private String formjsp;//自定义的表单页面
	private Map<String,String> cp = new HashMap<String, String>();//custom parameter自定义参数
	
	public String view()
	{
		//mode = "NEW";
		if(mode == null || mode.equals("")) mode = "NEW";
		displayCxt = new WorksheetDisplayContext();
		displayCxt.setEditType(mode);
		displayCxt.setBaseID(baseID);
		displayCxt.setBaseSchema(baseSchema);
		displayCxt.setTaskID(taskID);
		displayCxt.setUserInfo(portalManagerService.getUserSession(getUserSession().getLoginName()));
		displayCxt.setRelateBaseID(fromBaseID);
		displayCxt.setRelateBaseSchema(fromBaseSchema);
		displayCxt.setRelateBaseSN(fromBaseSn);
		displayCxt.setRelateTaskID(fromTaskID);
		displayCxt.setRelateType(relateType);
		displayCxt.setCopyRelateData(cRelateData);
		displayCxt.setCopyRelateByConfig(cRelateByConfig);
		
		String theme = getRequest().getParameter("theme");
		if(theme != null && !theme.equals(""))
		{
			ThemeModel themeModel = new ThemeModel();
			themeModel.setName(theme);
			displayCxt.setTheme(themeModel);
		}
		
		processService.view(displayCxt);
		/**
		 * 对按钮进行排序
		 */
		List<ActionModel> actionModelList = displayCxt.getFixActionList();
		Collections.sort(actionModelList, new Comparator<ActionModel>() {
			public int compare(ActionModel arg0, ActionModel arg1) {
				Integer orderid = arg0.getOrderId();
				Integer orderid2 = arg1.getOrderId();
				if(orderid == null) orderid = 0;
				if(orderid2 == null) orderid2 = 0;
				return orderid.compareTo(orderid2);
			}
		});
		displayCxt.setFixActionList(actionModelList);
		
		if ("PREVIEWMAIN".equals(mode)) {
			String prevMainJsp = deployService.prevMain(baseSchema);
			displayCxt.setMainPage(prevMainJsp);
		}
		
		if(org.apache.commons.lang3.StringUtils.isEmpty(formjsp)){
			formjsp = "form";
		}
		String url = displayCxt.getTheme().getFolder() + "/"+formjsp;
		
		//二次开发，自定义页面的优先级最高，放在最后执行
		if(org.apache.commons.lang3.StringUtils.isNotEmpty(customizedPage)){
			displayCxt.setMainPage(customizedPage);
		}
		return this.findForward(url);
	}
	
	public String save()
	{
		String returnText = null;
		baseID = getRequest().getParameter("bpp_Sys_BaseID");
		baseSchema = getRequest().getParameter("bpp_Sys_BaseSchema");
		actionID = getRequest().getParameter("bpp_Sys_ActionID");
		actionType = getRequest().getParameter("bpp_Sys_ActionType");
		actionText = getRequest().getParameter("bpp_Sys_ActionText");
		String assignString = getRequest().getParameter("bpp_Sys_AssignString");
		closeAfterCommit = getRequest().getParameter("bpp_Sys_CloseAfterCommit");
		taskID = getRequest().getParameter("bpp_Sys_TaskID");
		Map parameterMap = getRequest().getParameterMap();
		WorksheetCommitContext commitCxt = new WorksheetCommitContext();
		commitCxt.setBaseID(baseID);
		commitCxt.setBaseSchema(baseSchema);
		commitCxt.setActionID(actionID);
		commitCxt.setActionType(actionType);
		commitCxt.setActionText(actionText);
		commitCxt.setAssignString(assignString);
		commitCxt.setCloseAfterCommit(NumberUtils.formatToInt(closeAfterCommit));
		commitCxt.setTaskID(taskID);
		commitCxt.setFieldMap(ProcessUtil.convertMap(parameterMap));
		commitCxt.setEditType(mode);
		commitCxt.setUserInfo(portalManagerService.getUserSession(getUserSession().getLoginName()));
		
		try
		{
			returnText = processService.save(commitCxt);
			returnText = returnText.replace("\n", "\\n");
			WorkFLowUtil.updateCustombaseInfor(commitCxt.getFieldMap());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			returnText = e.getMessage();
			commitCxt.setCloseAfterCommit(0);
		}
		
		try
		{
			PrintWriter out = getResponse().getWriter();
			getResponse().setContentType("text/html;charset=UTF-8");
			String alertMsg = "";
			if(returnText!= null && returnText.length() > 0) alertMsg = "alert('" + returnText + "');";
			if(commitCxt.getCloseAfterCommit() == 1)
			{
				out.print("<script>" + alertMsg + "window.open(\"\",\"_self\");try{window.opener.document.getElementById('form1').submit();}catch(e){}top.opener=null;top.close();</script>");
			}
			else
			{
				String formUrl = this.getRequest().getContextPath() + "/ultrabpp/view.action?baseSchema="+commitCxt.getBaseSchema()+"&baseID="+commitCxt.getBaseID()+"&mode=MODIFY";
				if(formjsp!=null) formUrl += "&formjsp="+formjsp;
				if(customizedPage!=null) formUrl += "&customizedPage="+customizedPage;
				out.print("<script>" + alertMsg + "window.open(\""+formUrl+"\",\"_self\");try{opener.opener.document.getElementById('form1').submit();}catch(e){}</script>");
			}
			out.close();
			out.flush();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public String showStep()
	{
		Map<String, EditableFieldModel> fields = processService.checkAction(baseID, baseSchema, taskID, portalManagerService.getUserSession(getUserSession().getLoginName()), actionID, actionType); 
		displayCxt = new WorksheetDisplayContext();
		displayCxt.setEditableFieldMap(fields);
		String theme = getRequest().getParameter("theme");
		ThemeModel themeModel = themeCacheService.getThemeModel(theme);
		displayCxt.setTheme(themeModel);
		String type = getRequest().getParameter("type");
		String url = "";
		if(type.equals("fix") || !actionID.equals(actionType))
		{
			url = baseSchema + "/action/" + actionID;
		}
		else if(actionID.equals(actionType))
		{
			String filePath = DeployManagerImpl.getPath(baseSchema, "action") + actionID + ".jsp";
			File f = new File(filePath);
			if(f.exists())
			{
				url = baseSchema + "/action/" + actionID;
			}
			else
			{
				url = "common/action/" + actionType;
			}
			System.out.println("自由动作，url为：" + url);
		}
		return this.findForward(url);
	}
	
	public String previewAction() {
		if (StringUtils.isNotBlank(actionType)) {
			try {
				actionType = URLDecoder.decode(actionType,"UTF-8");
				if (actionType.indexOf(":") > 0) {
					String[] split = actionType.split(":");
					baseStatus = split[0];
					actionType = split[1];
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} 
		}
		String actionPage = deployService.prevAction(baseSchema, stepNo, actionName, baseStatus, actionType);
		displayCxt = new WorksheetDisplayContext();
		displayCxt.setEditType(mode);
		displayCxt.setBaseSchema(baseSchema);
		displayCxt.setMainPage(actionPage);
		try {
			IWfSortManager ver = WorkFlowServiceClient.clientInstance().getSortService();
			WfType wfType = ver.getWfTypeByCode(baseSchema);
			String theme = wfType.getTheme();
			ThemeModel themeModel = themeCacheService.getThemeModel(theme);
			displayCxt.setTheme(themeModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return findForward(actionPage);
	}
	
	public String deployMain() {
		deployService.depMain(baseSchema, true);
		this.getRequest().setAttribute("msg", "主表单部署成功!");
		return "refresh";
	}
	
	public String deployAll() {
		deployService.depAll(baseSchema);
		this.getRequest().setAttribute("msg", "整体部署成功!");
		return "refresh";
	}
	
	public String deployAction() {
		if (StringUtils.isNotBlank(actionType)) {
			try {
				actionType = URLDecoder.decode(actionType,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} 
		}
		deployService.depAction(baseSchema, stepNo, actionName, baseStatus, actionType);
		return "custom";
	}
	
	/**
	 * @return the baseID
	 */
	public String getBaseID()
	{
		return baseID;
	}
	/**
	 * @param baseID the baseID to set
	 */
	public void setBaseID(String baseID)
	{
		this.baseID = baseID;
	}
	/**
	 * @return the baseSchema
	 */
	public String getBaseSchema()
	{
		return baseSchema;
	}
	/**
	 * @param baseSchema the baseSchema to set
	 */
	public void setBaseSchema(String baseSchema)
	{
		this.baseSchema = baseSchema;
	}
	/**
	 * @return the defCode
	 */
	public String getDefCode()
	{
		return defCode;
	}
	/**
	 * @param defCode the defCode to set
	 */
	public void setDefCode(String defCode)
	{
		this.defCode = defCode;
	}
	/**
	 * @return the taskID
	 */
	public String getTaskID()
	{
		return taskID;
	}
	/**
	 * @param taskID the taskID to set
	 */
	public void setTaskID(String taskID)
	{
		this.taskID = taskID;
	}
	/**
	 * @return the fieldMap
	 */
	public Map<String, String> getFieldMap()
	{
		return fieldMap;
	}
	/**
	 * @param fieldMap the fieldMap to set
	 */
	public void setFieldMap(Map<String, String> fieldMap)
	{
		this.fieldMap = fieldMap;
	}
	/**
	 * @return the actionID
	 */
	public String getActionID()
	{
		return actionID;
	}
	/**
	 * @param actionID the actionID to set
	 */
	public void setActionID(String actionID)
	{
		this.actionID = actionID;
	}
	/**
	 * @return the actionType
	 */
	public String getActionType()
	{
		return actionType;
	}
	/**
	 * @param actionType the actionType to set
	 */
	public void setActionType(String actionType)
	{
		this.actionType = actionType;
	}
	/**
	 * @return the actionText
	 */
	public String getActionText()
	{
		return actionText;
	}
	/**
	 * @param actionText the actionText to set
	 */
	public void setActionText(String actionText)
	{
		this.actionText = actionText;
	}
	/**
	 * @return the attributeMap
	 */
	public Map<String, String> getAttributeMap()
	{
		return attributeMap;
	}
	/**
	 * @param attributeMap the attributeMap to set
	 */
	public void setAttributeMap(Map<String, String> attributeMap)
	{
		this.attributeMap = attributeMap;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	public ProcessService getProcessService() {
		return processService;
	}

	public void setProcessService(ProcessService processService) {
		this.processService = processService;
	}

	/**
	 * @return the displayCxt
	 */
	public WorksheetDisplayContext getDisplayCxt()
	{
		return displayCxt;
	}

	/**
	 * @param displayCxt the displayCxt to set
	 */
	public void setDisplayCxt(WorksheetDisplayContext displayCxt)
	{
		this.displayCxt = displayCxt;
	}

	/**
	 * @return the mode
	 */
	public String getMode()
	{
		return mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public void setMode(String mode)
	{
		this.mode = mode;
	}

	public WorkSheetService getWorkSheetService() {
		return workSheetService;
	}

	public void setWorkSheetService(WorkSheetService workSheetService) {
		this.workSheetService = workSheetService;
	}

	public WorkflowService getWorkflowService() {
		return workflowService;
	}

	public void setWorkflowService(WorkflowService workflowService) {
		this.workflowService = workflowService;
	}

	public WorkflowRoleService getWorkflowRoleService() {
		return workflowRoleService;
	}

	public void setWorkflowRoleService(WorkflowRoleService workflowRoleService) {
		this.workflowRoleService = workflowRoleService;
	}

	/**
	 * @return the portalManagerService
	 */
	public PortalManagerService getPortalManagerService()
	{
		return portalManagerService;
	}

	/**
	 * @param portalManagerService the portalManagerService to set
	 */
	public void setPortalManagerService(PortalManagerService portalManagerService)
	{
		this.portalManagerService = portalManagerService;
	}

	public DeployService getDeployService() {
		return deployService;
	}

	public void setDeployService(DeployService deployService) {
		this.deployService = deployService;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getStepNo() {
		return stepNo;
	}

	public void setStepNo(String stepNo) {
		this.stepNo = stepNo;
	}

	public String getBaseStatus() {
		return baseStatus;
	}

	public void setBaseStatus(String baseStatus) {
		this.baseStatus = baseStatus;
	}

	/**
	 * @return the closeAfterCommit
	 */
	public String getCloseAfterCommit()
	{
		return closeAfterCommit;
	}

	/**
	 * @param closeAfterCommit the closeAfterCommit to set
	 */
	public void setCloseAfterCommit(String closeAfterCommit)
	{
		this.closeAfterCommit = closeAfterCommit;
	}

	/**
	 * @return the themeCacheService
	 */
	public ThemeCacheService getThemeCacheService()
	{
		return themeCacheService;
	}

	/**
	 * @param themeCacheService the themeCacheService to set
	 */
	public void setThemeCacheService(ThemeCacheService themeCacheService)
	{
		this.themeCacheService = themeCacheService;
	}

	public String getRelateType() {
		return relateType;
	}

	public void setRelateType(String relateType) {
		this.relateType = relateType;
	}

	public String getFromBaseSchema() {
		return fromBaseSchema;
	}

	public void setFromBaseSchema(String fromBaseSchema) {
		this.fromBaseSchema = fromBaseSchema;
	}

	public String getFromBaseID() {
		return fromBaseID;
	}

	public void setFromBaseID(String fromBaseID) {
		this.fromBaseID = fromBaseID;
	}

	public String getFromTaskID() {
		return fromTaskID;
	}

	public void setFromTaskID(String fromTaskID) {
		this.fromTaskID = fromTaskID;
	}

	public String getFromBaseSn() {
		return fromBaseSn;
	}

	public void setFromBaseSn(String fromBaseSn) {
		this.fromBaseSn = fromBaseSn;
	}

	public String getCRelateData()
	{
		return cRelateData;
	}

	public void setCRelateData(String relateData)
	{
		cRelateData = relateData;
	}

	public String getCRelateByConfig()
	{
		return cRelateByConfig;
	}

	public void setCRelateByConfig(String relateByConfig)
	{
		cRelateByConfig = relateByConfig;
	}

	public Map<String, String> getCp() {
		return cp;
	}

	//直接返回json格式的字符串
	public String getCustomParameters() throws UnsupportedEncodingException{
		return URLDecoder.decode(JSONObject.fromObject(cp).toString(), "utf-8");
	}

	public String getCustomizedPage() {
		return customizedPage;
	}

	public void setCustomizedPage(String customizedPage) {
		this.customizedPage = customizedPage;
	}

	public String getFormjsp() {
		return formjsp;
	}

	public void setFormjsp(String formjsp) {
		this.formjsp = formjsp;
	}
	
}
