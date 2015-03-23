package com.ultrapower.eoms.workflow.sheet.attachment.web;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.ultrapower.eoms.common.constants.PropertiesUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.util.UUIDGenerator;
import com.ultrapower.eoms.common.core.util.ftpclient.IFtpClientServices;
import com.ultrapower.eoms.common.plugin.swfupload.core.AttachmentManagerAction;
import com.ultrapower.eoms.common.plugin.swfupload.utils.SwfuploadUtil;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ultrasm.model.Attachment;
import com.ultrapower.eoms.ultrasm.service.UserManagerService;
import com.ultrapower.eoms.workflow.flowmap.control.BaseOwnFieldInfoManage;
import com.ultrapower.eoms.workflow.sheet.attachment.model.WfAttachment;
import com.ultrapower.eoms.workflow.sheet.attachment.service.WfAttachmentService;

public class WfAttachmentAction extends AttachmentManagerAction {
	
	private WfAttachmentService wfAttachmentImpl;
	private String attachId;
	private String modelId;
	private String srcSheetId;    //附件拷贝 源附件的sheetId
	private String srcProcessId;  //附件拷贝 源附件的processId
	private String srcFieldId;    //附件拷贝 源附件的fieldId
	private String tarSheetId;    //附件拷贝 目标附件的sheetId
	private String tarProcessId;  //附件拷贝 目标附件的processId
	private String tarFieldId;    //附件拷贝 目标附件的fieldId
	private String tarBaseSchema; //附件拷贝 目标附件的工单schema
	private String reloadId;      //附件拷贝，返回关键ID（例如，用作刷新工单上的frame）
	private int copyResult;       //附件拷贝，返回拷贝结果
	private UserManagerService userManagerService;
	
	@Override
	public String upload(){
		String relateCode = getAttachmentGroupId();
		if (StringUtils.isNotBlank(relateCode)) {
			String[] ary = relateCode.split("_@!_");
			WfAttachment att = new WfAttachment();
			String sheetId = str(ary[0]);
			String processId = str(ary[1]);
			String fieldId = str(ary[2]);
			att.setSheetId(sheetId);
			att.setProcessId(processId);
			att.setFieldId(fieldId);
			setAttachmentGroupId(StringUtils.isNotBlank(processId) ? processId : sheetId);
			String repositoryPath = null;
			repositoryPath = store();
			// 保存附件的相关信息到数据库
			UserSession userSession = this.getUserSession();
			String userid = (getAttachmentUser()==null || "".equals(getAttachmentUser()))?userSession.getPid():getAttachmentUser();
			long createtime = TimeUtils.getCurrentTime();

			Attachment attachment = new Attachment();
			attachment.setCreater(userid);
			attachment.setLastmodifier(userid);
			attachment.setCreatetime(createtime);
			attachment.setName(getFileFileName());
			attachment.setRealname(getFileNewName());
//			attachment.setPath(getSavePath());
			attachment.setPath(repositoryPath);
			attachment.setRelationcode(getAttachmentGroupId());
			attachment.setAttsize(""+BigDecimal.valueOf(getFile().length()).divide(BigDecimal.valueOf(1024L),1,BigDecimal.ROUND_CEILING)+"KB");
			int poi = attachment.getRealname().lastIndexOf(".");
			if(poi!=-1 && poi!=attachment.getRealname().length()-1)
				attachment.setType(attachment.getRealname().substring(poi+1).toLowerCase());
			// 入库操作
			getAttachmentManagerService().addAttachment(attachment);
			// 返回记录id
			setAttachmentId(getAttachmentGroupId());
			
			att.setAttachId(attachment.getPid());
			wfAttachmentImpl.save(att);
		}
		return this.SUCCESS;
	}

	/**
	 * 查询下载列表
	 */
	public String queryDownList() {
		setItems(new ArrayList<Map<String, String>>());
		String attaGroupId = getAttachmentGroupId();
		if (StringUtils.isNotBlank(attaGroupId)) {
			String[] ary = attaGroupId.split("_@!_");
			String sheetId = ary[0];
			String processId = ary[1];
			String fieldId = ary[2];
			String queryType = str(ary[3]);
			if ("sheetId".equals(queryType)) {
				queryBySheetId(sheetId);
			} else if ("processId".equals(queryType)) {
				queryByProcessId(processId);
			} else if ("fieldId".equals(queryType)) {
				queryByFieldId(sheetId, fieldId);
			}
		}
		//item将会以JSON数组的格式返回：[{"fileFileName":"xxx","fileNewName":"yyy","savePath":"www","attachmentId":"ididid"},{...},{...}]
		return SUCCESS;
	}

	/**
	 * 按工单id查询
	 * @param sheetId
	 */
	private void queryBySheetId(String sheetId) {
		List<WfAttachment> queryBySheetId = wfAttachmentImpl.queryBySheetId(sheetId);
		
		//2011-1-14 fanying 优化sql，去掉in语法结构，改用or关键字
		String con = " relationcode='"+sheetId+"' ";
		if (CollectionUtils.isNotEmpty(queryBySheetId)) {
			for (int i = 0; i < queryBySheetId.size(); i++) {
				WfAttachment wfAttachment = queryBySheetId.get(i);
				String pId = wfAttachment.getProcessId();
				//con += ",'" + pId + "'";
				con += " or relationcode='" + pId + "' ";
			}
		}
		//String hql = "from Attachment where  relationcode in ("+con+")";
		String hql = "from Attachment where "+con;
		
		List<Attachment> attachments = getAttachmentManagerService().queryByHql(hql);
		String jsoncallback = getRequest().getParameter("jsoncallback");
		if (attachments != null && !attachments.isEmpty())
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(jsoncallback==null||"".equals(jsoncallback)){
				for (Attachment attachment : attachments)
				{
					Map<String, String> item = new HashMap<String, String>();
					item.put("fileFileName",attachment.getName());
					item.put("fileNewName", attachment.getRealname());
					item.put("savePath", attachment.getPath());
					item.put("attachmentId", attachment.getPid());
					item.put("attSize", attachment.getAttsize());
					item.put("uploadTime", sdf.format(new Date(attachment.getCreatetime()*1000)));
					item.put("attUserPid", attachment.getCreater());
					item.put("attUser", userManagerService.getUserNameByID(attachment.getCreater()));
					item.put("remark", attachment.getRemark()==null?"":attachment.getRemark());
					// 可下载附件项
					getItems().add(item);
				}
			}else{
				StringBuffer json = new StringBuffer();
				json.append("[");
				int len = attachments.size();
				for (int i=0;i<len;i++){
					Attachment att = attachments.get(i);
					if(i!=0)
						json.append(",");
					json.append("{\"fileFileName\":\"");
					json.append(att.getName());
					json.append("\",\"fileNewName\":\"");
					json.append(att.getRealname());
					json.append("\",\"savePath\":\"");
					json.append(att.getPath());
					json.append("\",\"attachmentId\":\"");
					json.append(att.getPid());
					json.append("\",\"attSize\":\"");
					json.append(att.getAttsize());
					json.append("\",\"uploadTime\":\"");
					json.append(sdf.format(new Date(att.getCreatetime()*1000)));
					json.append("\",\"attUserPid\":\"");
					json.append(att.getCreater());
					json.append("\",\"attUser\":\"");
					json.append(userManagerService.getUserNameByID(att.getCreater()));
					json.append("\",\"remark\":\"");
					json.append(att.getRemark()==null?"":att.getRemark());
					json.append("\"}");
				}
				json.append("]");
				try {
					getResponse().getWriter().write(jsoncallback+"("+json.toString()+");");
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}else{
			if(jsoncallback!=null&&!"".equals(jsoncallback)){
				try {
					getResponse().getWriter().write(jsoncallback+"([]);");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 按环节id查询
	 * @param processId
	 */
	public void queryByProcessId(String processId) {
		List<Attachment> attachments = getAttachmentManagerService().getAttachmentByRelation(processId);
		String jsoncallback = getRequest().getParameter("jsoncallback");
		if (attachments != null && !attachments.isEmpty())
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(jsoncallback==null||"".equals(jsoncallback)){
				for (Attachment attachment : attachments)
				{
					Map<String, String> item = new HashMap<String, String>();
					item.put("fileFileName",attachment.getName());
					item.put("fileNewName", attachment.getRealname());
					item.put("savePath", attachment.getPath());
					item.put("attachmentId", attachment.getPid());
					item.put("attSize", attachment.getAttsize());
					item.put("uploadTime", sdf.format(new Date(attachment.getCreatetime()*1000)));
					item.put("attUserPid", attachment.getCreater());
					item.put("attUser", userManagerService.getUserNameByID(attachment.getCreater()));
					item.put("remark", attachment.getRemark()==null?"":attachment.getRemark());
					// 可下载附件项
					getItems().add(item);
				}
			}else{
				StringBuffer json = new StringBuffer();
				json.append("[");
				int len = attachments.size();
				for (int i=0;i<len;i++){
					Attachment att = attachments.get(i);
					if(i!=0)
						json.append(",");
					json.append("{\"fileFileName\":\"");
					json.append(att.getName());
					json.append("\",\"fileNewName\":\"");
					json.append(att.getRealname());
					json.append("\",\"savePath\":\"");
					json.append(att.getPath());
					json.append("\",\"attachmentId\":\"");
					json.append(att.getPid());
					json.append("\",\"attSize\":\"");
					json.append(att.getAttsize());
					json.append("\",\"uploadTime\":\"");
					json.append(sdf.format(new Date(att.getCreatetime()*1000)));
					json.append("\",\"attUserPid\":\"");
					json.append(att.getCreater());
					json.append("\",\"attUser\":\"");
					json.append(userManagerService.getUserNameByID(att.getCreater()));
					json.append("\",\"remark\":\"");
					json.append(att.getRemark()==null?"":att.getRemark());
					json.append("\"}");
				}
				json.append("]");
				try {
					getResponse().getWriter().write(jsoncallback+"("+json.toString()+");");
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}else{
			if(jsoncallback!=null&&!"".equals(jsoncallback)){
				try {
					getResponse().getWriter().write(jsoncallback+"([]);");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 按字段id查询
	 * @param sheetId
	 * @param processId
	 * @param fieldId
	 */
	public void queryByFieldId(String sheetId, String fieldId) {
		String jsoncallback = getRequest().getParameter("jsoncallback");
		if (StringUtils.isNotBlank(fieldId) && !"<FIELD>".equals(fieldId)) {
			List<WfAttachment> queryBySheetId = wfAttachmentImpl.queryBySheetId(sheetId);
			List<String> attidList = new ArrayList<String>();
			String con = "";
			if (CollectionUtils.isNotEmpty(queryBySheetId)) {
				for (int i = 0; i < queryBySheetId.size(); i++) {
					WfAttachment wfAttachment = queryBySheetId.get(i);
					String attaId = wfAttachment.getAttachId();
					String fId = wfAttachment.getFieldId();
					if (fieldId.equals(fId)) {
						attidList.add(attaId);
					}
				}
			}
			if (CollectionUtils.isNotEmpty(attidList)) {
				for (int i = 0; i < attidList.size(); i++) {
					String attId = attidList.get(i);
					con += "'" + attId + "'";
					if (i != (attidList.size() - 1)) {
						con += ",";
					}
				}
			}
			if (StringUtils.isNotBlank(con)) {
				String hql = "from Attachment where  pid in ("+con+")";
				List<Attachment> attachments = getAttachmentManagerService().queryByHql(hql);
				if (attachments != null && !attachments.isEmpty())
				{
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					if(jsoncallback==null||"".equals(jsoncallback)){
						for(Attachment attachment : attachments)
						{
							Map<String, String> item = new HashMap<String, String>();
							item.put("fileFileName",attachment.getName());
							item.put("fileNewName", attachment.getRealname());
							item.put("savePath", attachment.getPath());
							item.put("attachmentId", attachment.getPid());
							item.put("attSize", attachment.getAttsize());
							item.put("uploadTime", sdf.format(new Date(attachment.getCreatetime()*1000)));
							item.put("attUserPid", attachment.getCreater());
							item.put("attUser", userManagerService.getUserNameByID(attachment.getCreater()));
							item.put("remark", attachment.getRemark()==null?"":attachment.getRemark());
							// 可下载附件项
							getItems().add(item);
						}
					}else{
						StringBuffer json = new StringBuffer();
						json.append("[");
						int len = attachments.size();
						for (int i=0;i<len;i++){
							Attachment att = attachments.get(i);
							if(i!=0)
								json.append(",");
							json.append("{\"fileFileName\":\"");
							json.append(att.getName());
							json.append("\",\"fileNewName\":\"");
							json.append(att.getRealname());
							json.append("\",\"savePath\":\"");
							json.append(att.getPath());
							json.append("\",\"attachmentId\":\"");
							json.append(att.getPid());
							json.append("\",\"attSize\":\"");
							json.append(att.getAttsize());
							json.append("\",\"uploadTime\":\"");
							json.append(sdf.format(new Date(att.getCreatetime()*1000)));
							json.append("\",\"attUserPid\":\"");
							json.append(att.getCreater());
							json.append("\",\"attUser\":\"");
							json.append(userManagerService.getUserNameByID(att.getCreater()));
							json.append("\",\"remark\":\"");
							json.append(att.getRemark()==null?"":att.getRemark());
							json.append("\"}");
						}
						json.append("]");
						try {
							getResponse().getWriter().write(jsoncallback+"("+json.toString()+");");
							
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					
				}else{
					if(jsoncallback!=null&&!"".equals(jsoncallback)){
						try {
							getResponse().getWriter().write(jsoncallback+"([]);");
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}else{
				if(jsoncallback!=null&&!"".equals(jsoncallback)){
					try {
						getResponse().getWriter().write(jsoncallback+"([]);");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}else{
			if(jsoncallback!=null&&!"".equals(jsoncallback)){
				try {
					getResponse().getWriter().write(jsoncallback+"([]);");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 处理模板附件
	 * @return
	 */
	public String dealModelAttach() {
		if(StringUtils.isNotBlank(modelId)) {
			BaseOwnFieldInfoManage man = new BaseOwnFieldInfoManage();
			String attachId = man.getAttachId(modelId);
			if(StringUtils.isNotBlank(attachId)) {
				List<Attachment> attaches = getAttachmentManagerService().getAttachmentByRelation(attachId);
				if(CollectionUtils.isNotEmpty(attaches)) {
					for (int i = 0; i < attaches.size(); i++) {
						Attachment attachment = attaches.get(i);
						Attachment newAtta = attachment.clone();
						String oldName = newAtta.getName();
						String oldRealName = newAtta.getRealname();
						String path = newAtta.getPath();
						String suffix = StringUtils.substring(oldName, oldName.lastIndexOf("."));
						String currentUUID = UUIDGenerator.getUUIDoffSpace();
						String dbName = currentUUID + suffix;
						newAtta.setPid(null);
						newAtta.setRealname(dbName);
						newAtta.setRelationcode(this.attachId);
						getAttachmentManagerService().addAttachment(newAtta);
						//拷贝附件
						String storeType = PropertiesUtils.getProperty("attachment.storetype");
						if(SwfuploadUtil.STORE_TYPE_FTP.equals(storeType)){
							IFtpClientServices ftpClient = SwfuploadUtil.getFtpClient();
							try {
								String fullPath = SwfuploadUtil.getFullPathOfAttach(path);
								if(fullPath.startsWith("/"))
									fullPath = fullPath.substring(1);
								InputStream ins = ftpClient.downloadFileAsStream(fullPath, oldRealName);
								ftpClient.uploadFile(fullPath, dbName, ins);
							} catch (Exception e) {
								e.printStackTrace();
							}
							SwfuploadUtil.releaseFtp(ftpClient);
						}else if(SwfuploadUtil.STORE_TYPE_LOCAL.equals(storeType)){
							File oldFile = null;
							if(SwfuploadUtil.isAbsolutePath(path))
							{
								File oldFileP = new File(path);
								if(!oldFileP.exists())
									oldFileP.mkdirs();
								oldFile = new File(path, oldRealName);
							}
							else
							{
								oldFile = new File(this.getSession().getServletContext().getRealPath(path),oldRealName);
							}
							File newFile = new File(this.getSession().getServletContext().getRealPath(path),dbName);
							try {
								FileUtils.copyFile(oldFile, newFile);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						
						WfAttachment wfAtta = new WfAttachment();
						wfAtta.setAttachId(newAtta.getPid());
						wfAtta.setProcessId(this.attachId);
						wfAtta.setSheetId(this.attachId);
						wfAttachmentImpl.save(wfAtta);
					}
				}
			}
		}
		return null;
	}
	
	@Override
	public void deleteAttachment()
	{
		boolean b = false;
		String attachmentId = this.getRequest().getParameter("attachmentId");
		if(attachmentId!=null)
		{
			Attachment attach = getAttachmentManagerService().getAttachmentById(attachmentId);
			if(attach!=null)
			{
				wfAttachmentImpl.delete(attach.getPid());
				setSavePath(attach.getPath());
				setFileNewName(attach.getRealname());
				physicalDel(getSavePath(), getFileNewName());
				getAttachmentManagerService().deleteAttachmentById(attachmentId);
				b = true;
			}
		}
		String jsoncallback = getRequest().getParameter("jsoncallback");
		try {
			if(jsoncallback==null||"".equals(jsoncallback))
				getResponse().getWriter().print(String.valueOf(b));
			else
				getResponse().getWriter().print(jsoncallback+"([{\"result\":\""+String.valueOf(b)+"\"}]);");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 拷贝附件
	 * @return
	 */
	public String copyWfAttachment()
	{
		copyResult = wfAttachmentImpl.copyWfAttachment(srcSheetId, srcProcessId, srcFieldId
				, tarSheetId, tarProcessId, tarFieldId, tarBaseSchema, getUserSession().getPid());
		return SUCCESS;
	}
	
	/**
	 * 拷贝附件(AJAX访问)
	 * @return
	 */
	public void copyWfAttachmentAjax()
	{
		copyResult = wfAttachmentImpl.copyWfAttachment(srcSheetId, srcProcessId, srcFieldId
				, tarSheetId, tarProcessId, tarFieldId, tarBaseSchema, getUserSession().getPid());
		this.renderText("{\"reloadId\":\""+(this.reloadId==null?"":reloadId)+"\",\"copyResult\":"+this.copyResult+"}");
	}
	
	public WfAttachmentService getWfAttachmentImpl() {
		return wfAttachmentImpl;
	}

	public void setWfAttachmentImpl(WfAttachmentService wfAttachmentImpl) {
		this.wfAttachmentImpl = wfAttachmentImpl;
	}
	
	public static String str(String s) {
		String rtn = "";
		if (StringUtils.isNotBlank(s)) {
			rtn = s;
		}
		return rtn;
	}

	public String getAttachId() {
		return attachId;
	}

	public void setAttachId(String attachId) {
		this.attachId = attachId;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	
	public String getReloadId() {
		return reloadId;
	}

	public void setReloadId(String reloadId) {
		this.reloadId = reloadId;
	}

	public int getCopyResult() {
		return copyResult;
	}

	public void setCopyResult(int copyResult) {
		this.copyResult = copyResult;
	}
	
	public String getSrcSheetId() {
		return srcSheetId;
	}

	public void setSrcSheetId(String srcSheetId) {
		this.srcSheetId = srcSheetId;
	}

	public String getSrcProcessId() {
		return srcProcessId;
	}

	public void setSrcProcessId(String srcProcessId) {
		this.srcProcessId = srcProcessId;
	}

	public String getSrcFieldId() {
		return srcFieldId;
	}

	public void setSrcFieldId(String srcFieldId) {
		this.srcFieldId = srcFieldId;
	}

	public String getTarSheetId() {
		return tarSheetId;
	}

	public void setTarSheetId(String tarSheetId) {
		this.tarSheetId = tarSheetId;
	}

	public String getTarProcessId() {
		return tarProcessId;
	}

	public void setTarProcessId(String tarProcessId) {
		this.tarProcessId = tarProcessId;
	}

	public String getTarFieldId() {
		return tarFieldId;
	}

	public void setTarFieldId(String tarFieldId) {
		this.tarFieldId = tarFieldId;
	}

	public String getTarBaseSchema() {
		return tarBaseSchema;
	}

	public void setTarBaseSchema(String tarBaseSchema) {
		this.tarBaseSchema = tarBaseSchema;
	}

	public void setUserManagerService(UserManagerService userManagerService) {
		this.userManagerService = userManagerService;
	}
} 
