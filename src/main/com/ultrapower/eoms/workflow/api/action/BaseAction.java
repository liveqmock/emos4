package com.ultrapower.eoms.workflow.api.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.ultrapower.eoms.common.plugin.swfupload.utils.SwfuploadUtil;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ultrapower.eoms.common.constants.Constants;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.util.NumberUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.ultrasm.model.Attachment;
import com.ultrapower.eoms.ultrasm.service.AttachmentManagerService;
import com.ultrapower.eoms.workflow.api.base.InitBaseInfo;
import com.ultrapower.eoms.workflow.api.bean.BaseAttachmentInfo;
import com.ultrapower.eoms.workflow.api.bean.BaseFieldInfo;
import com.ultrapower.eoms.workflow.api.bean.DealObject;
import com.ultrapower.eoms.workflow.sheet.attachment.model.WfAttachment;
import com.ultrapower.eoms.workflow.sheet.attachment.service.WfAttachmentService;



public abstract class BaseAction extends InitBaseInfo {
	private final static Logger log = LoggerFactory.getLogger(BaseAction.class);
	private String actionType;
	private String actionName;
	private String baseId;
	private String baseSchema;
	
	public void initBaseField(String _baseSchema, String _baseid)
	{
		this.baseSchema = _baseSchema;
		this.baseId = _baseid;
		loadBaseFields(baseSchema);
		if(baseId != null && !baseId.equals(""))
		{
			initBaseFieldValues(baseSchema, baseId);
		}
	}
	
	/**
	 * 执行动作
	 * @param baseSchema 工单标识
	 * @param baseId 工单id
	 * @param userLoginName 当前用户登录名
	 * @param dealDesc 处理描述
	 * @param actionText 动作名称，如果为空自由流动作会有默认值
	 * @param dealObjs 处理对象列表
	 * @param bizFields 业务字段列表
	 * @param attachs 附件列表
	 * @return 是否执行成功
	 */
	public boolean doAction(String baseSchema, String baseId, String userLoginName, String dealDesc, String actionText, List<DealObject> dealObjs, List<BaseFieldInfo> bizFields, List<BaseAttachmentInfo> attachs) {
		return this.doAction(baseSchema, baseId, null, userLoginName, dealDesc, actionText, dealObjs, bizFields, attachs);
	}
	
	public boolean doAction(String baseSchema, String baseId, String taskId, String userLoginName, String dealDesc, String actionText, List<DealObject> dealObjs, List<BaseFieldInfo> bizFields, List<BaseAttachmentInfo> attachs) {
		this.baseSchema = baseSchema;
		this.baseId = baseId;
		if ((this.initBase(baseSchema, baseId, taskId, userLoginName, dealObjs, bizFields, attachs))) {
			try {
				//设置传入的业务字段(先给业务字段赋值，因为后面的逻辑会用到业务字段进行判断)
				if (CollectionUtils.isNotEmpty(bizFields)) {
					for (int i = 0; i < bizFields.size(); i++) {
						BaseFieldInfo bizField = bizFields.get(i);
						String fieldName = bizField.getName();
						baseAllFields.put(fieldName, bizField);
					}
				}
				
				//设置动作的特有字段
				if(!setActionField(baseSchema, baseId, userLoginName, actionText, dealObjs, bizFields, attachs)) {
					return false;
				}
				
				this.actionType = getFieldValue("tmp_BaseActionBtn_Char");
				this.actionName = getFieldValue("tmp_BaseActionBtn_Fix_Char");
				
				//设置处理描述
				setFieldValue("tmp_DealDesc", StringUtils.isNotBlank(dealDesc) ? dealDesc : "");
				
				//拼装分支判断用到的条件信息
				setConditionField();
				
				//输出调试信息
				printFields();
				
				//处理时间字段
				dealTimeField();
				
				dealTypeValueField();
				
				//处理附件
				if (!setAttachment(attachs)) {
					return false;
				}
				
				//更新C2，并新增或更新AR实体
				if (!baseUpdataC2(baseSchema, baseId)) {
					return false;
				}
				
				boolean isSucc = false;
				if (StringUtils.isNotBlank(baseId)) {
					this.baseId = baseId;
					isSucc = remedyEntryUpdate(baseSchema, baseId, convert());
				} else {
					this.baseId = remedyEntryInsert(baseSchema, convert());
					if (StringUtils.isNotBlank(this.baseId)) {
						isSucc = true;
					}
				}
				if (isSucc) {
					log.info(actionName + "动作执行成功！");
					return true;
				} else {
					log.error(actionName + "动作执行失败！");
					return false;
				}
			} catch (Exception e) {
				log.error("执行动作失败", e);
				return false;
			}
		}
		return false;
	}
	
	protected boolean setAttachment(List<BaseAttachmentInfo> attachs) {
		boolean rtn = true;
		if (CollectionUtils.isNotEmpty(attachs)) {
			String attachId = getFieldValue("BaseAttachGUID");
			String userLoginName = getFieldValue("tmp_UserLoginName");
			String processId = getFieldValue("tmp_wfattach_processId");
			String baseSchema = getFieldValue("tmp_BaseCategorySchama").replace(":", "_");
			String date = TimeUtils.getCurrentDate("yyyy-MM-dd");
			String ctxPath = SwfuploadUtil.SWFUPLOAD_UPLOAD_PATH;
			String sp = "//";
			for (int i = 0; i < attachs.size(); i++) {
				BaseAttachmentInfo atta = attachs.get(i);
				String path = atta.getPath();
				if (StringUtils.isNotBlank(path)) {
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
							log.error("处理附件出错！", e);
							e.printStackTrace();
							return false;
						}
					}
				}
			}
		}
		return rtn;
	}

	/**
	 * 拼装固定流程分支上的条件信息
	 */
	protected void setConditionField() {
		StringBuffer sb = new StringBuffer();
		Set<String> keySet = baseAllFields.keySet();
		Iterator<String> iter = keySet.iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			BaseFieldInfo baseFieldInfo = baseAllFields.get(key);
			boolean isCon = baseFieldInfo.isConditionField();
			String name = baseFieldInfo.getName();
			String value = baseFieldInfo.getValue();
			int type = baseFieldInfo.getType();
			if (isCon) {
				sb.append(name + "@," + value +"@;");
			}
		}
		setFieldValue("tmp_PreAddInput", sb.toString());
	}
	
	/**
	 * 根据动作类型和处理对象列表拼装处理人规则串
	 * @param dealObjs
	 * @param actionType
	 * @return
	 */
	protected String getAssignStr(List<DealObject> dealObjs, String actionType) {
		if (CollectionUtils.isNotEmpty(dealObjs) && StringUtils.isNotBlank(actionType)) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < dealObjs.size(); i++) {
				DealObject dealObject = dealObjs.get(i);
				String assigneeId = dealObject.getAssigneeId();
				String groupId = dealObject.getGroupId();
				String roleId = dealObject.getRoleId();
				String dealType = dealObject.getDealType();
				String actorStr = dealObject.getActorStr();
				long acceptOverTime = dealObject.getAcceptOverTime();
				long assignOverTime = dealObject.getAssignOverTime();
				long dealOverTime = dealObject.getDealOverTime();
				if (StringUtils.isNotBlank(actorStr)) {
					sb.append(actorStr);
				} else if (StringUtils.isNotBlank(assigneeId)) {
					sb.append("U#:"+assigneeId+"#:"+actionType+"#:"+dealType+"#:"+acceptOverTime+"#:"+assignOverTime+"#:"+dealOverTime+"#:#:#:#:#;");
				} else if (StringUtils.isNotBlank(groupId)) {
					sb.append("G#:"+groupId+"#:"+actionType+"#:"+dealType+"#:"+acceptOverTime+"#:"+assignOverTime+"#:"+dealOverTime+"#:#:#:#:#;");
				} else if (StringUtils.isNotBlank(roleId)) {
					sb.append("R#:"+roleId+"#:"+actionType+"#:"+dealType+"#:"+acceptOverTime+"#:"+assignOverTime+"#:"+dealOverTime+"#:#:#:#:#;");
				}
			}
			return sb.toString();
		}
		return "";
	}
	
	protected boolean setTargetId(String sql, String actionType, List<DealObject> dealObjs) {
		boolean flag = false;
		QueryAdapter qAdapter = new QueryAdapter();
		DataTable dt = qAdapter.executeQuery(sql, null);
		if (dt != null && dt.length() > 0) {
			if (CollectionUtils.isEmpty(dealObjs)) {
				log.info("没有要传入拼装targetIds的DealObject对象列表，默认取第一个！");
				DataRow dr = dt.getDataRow(0);
				String stepId = dr.getString("stepId");
				String pNo = dr.getString("phaseNo");
				setFieldValue("tmp_AssigneesInfo", "#:#:"+actionType+"#:#:#:#:#:"+pNo + ":" + stepId+"#:#:#:#;");
				flag = true;
			} else {
				String targetId = "";
				for (int i = 0; i < dt.length(); i++) {
					DataRow dr = dt.getDataRow(i);
					String assigneeId = dr.getString("assigneeId");
					String assigneeGroupId = dr.getString("assigngroupId");
					String pId = dr.getString("processId");
					String stepId = dr.getString("stepId");
					String pNo = dr.getString("phaseNo");
					for (int j = 0; j < dealObjs.size(); j++) {
						DealObject dealObj = dealObjs.get(j);
						String dealAssignee = dealObj.getAssigneeId();
						String groupId = dealObj.getGroupId();
						String roleId = dealObj.getRoleId();
						String dealPid = dealObj.getProcessId();
						if ((StringUtils.isNotBlank(dealAssignee) && dealAssignee.equals(assigneeId))
								|| (StringUtils.isNotBlank(groupId) && (groupId.equals(assigneeGroupId)))
								|| (StringUtils.isNotBlank(roleId) && (roleId.equals(assigneeGroupId)))
								|| (StringUtils.isNotBlank(dealPid) && dealPid.equals(pId))) {
							targetId += pNo + ":" + stepId + ";";
						} 
					}
				}
				if (StringUtils.isBlank(targetId)) {
					log.error("没有匹配到拼装targetIds所需要的信息！");
				} else {
					setFieldValue("tmp_AssigneesInfo", "#:#:"+actionType+"#:#:#:#:#:"+targetId+"#:#:#:#;");
					flag = true;
				}
			}
		} 
		return flag;
	}
	
	/**
	 * 设置动作的特有字段
	 * @param baseSchema 工单标识
	 * @param baseId 工单id
	 * @param userLoginName 用户登录名
	 * @param actionText 动作名称，如果为空自由流动作会有默认值
	 * @param dealObjs 处理对象列表
	 * @param bizFields 业务字段列表
	 * @param attachs 附件列表
	 */
	protected abstract boolean setActionField(String baseSchema, String baseId, String userLoginName, String actionText, List<DealObject> dealObjs, List<BaseFieldInfo> bizFields, List<BaseAttachmentInfo> attachs);

	public String getBaseId() {
		return baseId;
	}
	
	/**
	 * 处理时间类型字段
	 */
	private void dealTimeField() {
		Set<String> keySet = baseAllFields.keySet();
		Iterator<String> iter = keySet.iterator();
		List<String> dels = new ArrayList<String>();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			BaseFieldInfo field = baseAllFields.get(key);
			int type = field.getType();
			String value = field.getValue();
			if (type == 7) {
				if (StringUtils.isNotBlank(value)) {
					if (value.indexOf(":") > 0) {
						long formatDateStringToInt = TimeUtils.formatDateStringToInt(value, "yyyy-MM-dd HH:mm:ss");
						field.setValue(formatDateStringToInt + "");
					}
					value = field.getValue();
					if (NumberUtils.formatToInt(value) == 0) {
						dels.add(key);
					}
				} else {
					dels.add(key);
				}
			}
		}
		if (CollectionUtils.isNotEmpty(dels)) {
			for (int i = 0; i < dels.size(); i++) {
				baseAllFields.remove(dels.get(i));
			}
		}
	}
	
	/**
	 * 处理未赋值的type=6的字典类型值字段(数据库里存的是整形)
	 */
	private void dealTypeValueField() {
		Set<String> keySet = baseAllFields.keySet();
		List<String> dels = new ArrayList<String>();
		Iterator<String> iter = keySet.iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			BaseFieldInfo field = baseAllFields.get(key);
			int type = field.getType();
			String value = field.getValue();
			if (type == 6 || type == 2) {
				if (StringUtils.isBlank(value)) {
					dels.add(key);
				}
			}
		}
		if (CollectionUtils.isNotEmpty(dels)) {
			for (int i = 0; i < dels.size(); i++) {
				baseAllFields.remove(dels.get(i));
			}
		}
	}
}
