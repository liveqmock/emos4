package com.ultrapower.eoms.ultrabpp.api;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.ultrapower.eoms.common.constants.Constants;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.common.plugin.swfupload.utils.SwfuploadUtil;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.common.portal.service.PortalManagerService;
import com.ultrapower.eoms.ultrabpp.cache.model.ActionModel;
import com.ultrapower.eoms.ultrabpp.runtime.model.WorksheetCommitContext;
import com.ultrapower.eoms.ultrabpp.runtime.service.ProcessService;
import com.ultrapower.eoms.ultrabpp.runtime.service.WorkSheetService;
import com.ultrapower.eoms.ultrabpp.runtime.service.WorkflowService;
import com.ultrapower.eoms.ultrabpp.utils.ProcessUtil;
import com.ultrapower.eoms.ultrasm.model.Attachment;
import com.ultrapower.eoms.ultrasm.service.AttachmentManagerService;
import com.ultrapower.eoms.workflow.api.bean.BaseAttachmentInfo;
import com.ultrapower.eoms.workflow.api.bean.BaseFieldInfo;
import com.ultrapower.eoms.workflow.api.bean.DealObject;
import com.ultrapower.eoms.workflow.sheet.attachment.model.WfAttachment;
import com.ultrapower.eoms.workflow.sheet.attachment.service.WfAttachmentService;
import com.ultrapower.randomutil.Random15;
import com.ultrapower.randomutil.RandomN;
import com.ultrapower.workflow.engine.task.model.ProcessTask;
import com.ultrapower.workflow.utils.WfEngineConstants;

public class BaseAction {
	
	private ProcessService processService;
	private PortalManagerService portalManagerService;
	private WorkflowService workflowService;
	private WorkSheetService workSheetService;
	
	public String doAction(String baseID, String baseSchema, String taskID, String loginName, String actionID, String actionType, String actionText, String assignStr, String desc, Map parameterMap, List<BaseAttachmentInfo> attachs) {
		if (parameterMap == null) {
			parameterMap = new HashMap();
		}
		String mode = "MODIFY";
		if (StringUtils.isBlank(baseID)) {
			mode = "NEW";
			if (CollectionUtils.isNotEmpty(attachs)) {
				for (int i = 0; i < attachs.size(); i++) {
					BaseAttachmentInfo atta = attachs.get(i);
					String attachId = atta.getAttachFieldId();
					if (StringUtils.isNotBlank(attachId)) {
						parameterMap.put(attachId, getGUID());
					}
				}
			}
		} else {
			if (CollectionUtils.isNotEmpty(attachs)) {
				Map<String, String> dataMap = workSheetService.getDataMap(baseID, baseSchema);
				for (int i = 0; i < attachs.size(); i++) {
					BaseAttachmentInfo atta = attachs.get(i);
					String attachId = atta.getAttachFieldId();
					String val = dataMap.get(attachId);
					if (StringUtils.isNotBlank(val)) {
						parameterMap.put(attachId, val);
					} else {
						parameterMap.put(attachId, getGUID());
					}
				}
			}
		}
		UserSession userSession = ((PortalManagerService)WebApplicationManager.getBean("portalManagerService")).getUserSession(loginName);
		ProcessTask<?> currentTask = ((WorkflowService)WebApplicationManager.getBean("workflowService")).getCurrentProcess(baseID, baseSchema, userSession, taskID);
		WorksheetCommitContext commitCxt = new WorksheetCommitContext();
		commitCxt.setBaseID(baseID);
		commitCxt.setBaseSchema(baseSchema);
		commitCxt.setActionType(actionType);
		commitCxt.setActionText(actionText);
		commitCxt.setCloseAfterCommit(1);
		commitCxt.setTaskID(taskID);
		commitCxt.setEditType(mode);
		commitCxt.setCurrentTask(currentTask);
		if (WfEngineConstants.ACTION_NEXT.equals(actionType)) {
			if (StringUtils.isBlank(actionID)) {
				List<ActionModel>[] availableActions = ((WorkflowService)WebApplicationManager.getBean("workflowService")).getAvailableActions(baseID, baseSchema, currentTask);
				if (!ArrayUtils.isEmpty(availableActions)) {
					List<ActionModel> fixActions = availableActions[0];
					if (CollectionUtils.isNotEmpty(fixActions) && fixActions.size() == 1) {
						commitCxt.setActionID(fixActions.get(0).getActionName());
					} else {
						throw new RuntimeException("当前环节可执行的固定动作不唯一，必须指定固定动作标识（actionNo）！");
					}
				}
			} else {
				commitCxt.setActionID(actionID);
			}
		} else {
			commitCxt.setActionID(actionType);
		}
		commitCxt.setUserInfo(userSession);
		if (StringUtils.isNotBlank(desc)) {
			parameterMap.put("DESC", desc);
		}
		if (StringUtils.isNotBlank(assignStr)) {
			parameterMap.put("BPP_SYS_ASSIGNSTRING", assignStr);
		}
		commitCxt.setFieldMap(parameterMap);
		String msg = ((ProcessService)WebApplicationManager.getBean("processService")).save(commitCxt);
		if (CollectionUtils.isNotEmpty(attachs)) {
			setAttachment(loginName, taskID, baseSchema, attachs, parameterMap);
		}
		baseID = commitCxt.getBaseID();
		return baseID;
	}
	
	protected void setAttachment(String userLoginName, String processId, String baseSchema, List<BaseAttachmentInfo> attachs, Map parameterMap) {
		if (CollectionUtils.isNotEmpty(attachs)) {
			String date = TimeUtils.getCurrentDate("yyyy-MM-dd");
			String ctxPath = SwfuploadUtil.SWFUPLOAD_UPLOAD_PATH;
			String sp = "//";
			for (int i = 0; i < attachs.size(); i++) {
				BaseAttachmentInfo atta = attachs.get(i);
				String path = atta.getPath();
				if (StringUtils.isNotBlank(path)) {
					String attachId = ProcessUtil.getStringFromMap(atta.getAttachFieldId(), parameterMap);
					String attaName = atta.getName();
					String attaDbName = getGUID();
					String realName = attaDbName;
					File srcFile = new File(path);
					if (srcFile != null && srcFile.exists() && srcFile.isFile()) {
						String relaPath = Constants.WORKSHEET_UPLOAD_PATH + sp + baseSchema + sp + date ;
						String dirPath = ctxPath + sp + relaPath + sp;
						String destPath = dirPath + realName;
						try {
							File destFile = new File(destPath);
							FileUtils.touch(destFile);
							destFile.createNewFile();
							FileUtils.copyFile(srcFile, destFile);
							
							AttachmentManagerService attaService = (AttachmentManagerService) WebApplicationManager.getBean("attachmentManagerService");
							Attachment att = new Attachment();
							att.setName(attaName);
							att.setRealname(realName);
							att.setRelationcode(StringUtils.isNotBlank(processId) ? processId : attachId);
							att.setPath(relaPath);
							att.setCreater(userLoginName);
							att.setCreatetime(TimeUtils.getCurrentTime());
							attaService.addAttachment(att);
							
							WfAttachmentService wfAttachmentImpl = (WfAttachmentService) WebApplicationManager.getBean("wfAttachmentImpl");
							WfAttachment wfAtt = new WfAttachment();
							wfAtt.setAttachId(att.getPid());
							wfAtt.setSheetId(attachId);
							wfAtt.setProcessId(processId);
							wfAttachmentImpl.save(wfAtt);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	protected String getGUID() {
		RandomN random = new Random15();
		return random.getRandom(System.currentTimeMillis());
	}

	public ProcessService getProcessService() {
		return processService;
	}

	public void setProcessService(ProcessService processService) {
		this.processService = processService;
	}

	public PortalManagerService getPortalManagerService() {
		return portalManagerService;
	}

	public void setPortalManagerService(PortalManagerService portalManagerService) {
		this.portalManagerService = portalManagerService;
	}

	public WorkflowService getWorkflowService() {
		return workflowService;
	}

	public void setWorkflowService(WorkflowService workflowService) {
		this.workflowService = workflowService;
	}

	public WorkSheetService getWorkSheetService() {
		return workSheetService;
	}

	public void setWorkSheetService(WorkSheetService workSheetService) {
		this.workSheetService = workSheetService;
	}
}
