package com.ultrapower.eoms.common.plugin.swfupload.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.ultrapower.eoms.common.core.util.UUIDGenerator;
import com.ultrapower.eoms.common.plugin.swfupload.utils.SwfuploadUtil;
import com.ultrapower.eoms.common.portal.model.UserSession;

@SuppressWarnings("serial")
public class AttachmentUploadTag extends TagSupport
{
	private String uploadAction;
	private String queryAction;
	private String downloadAction;
	private String uploadDestination;
	private String deleteAction;
	
	private String attchmentGroupId; // 附件组id
	private String uploadBtnIsVisible = "true"; // 是否显示上传按钮
	private String downTableVisible = "true"; // 是否显示下载列表
	private String uploadable = "true"; // 是否可上傳
	private String isMultiUpload = "true";//是否批量上传
	private String isMultiDownload = "true";//是否批量下载
	private String isAutoUpload = "false";//是否自动上传
	private String uploadTableVisible = "true";//是否显示上传文件table
	private String progressIsVisible = "true";//是否显示上传进度条
	private String operationParams;//附件的操作权限参数（权限顺序为：编辑,删除,下载 1表示有此权限，0表示无此权限 例如：1,0,1表示可编辑,不可删除,可下载，默认为不可编辑，可删除，可下载）
	private String createDirByDate = "false";// 是否根据日期创建新的文件夹（默认创建）
	private String attachmentUser;
	// 上传文件类型及文件类型描述
	private String fileTypes = "*.*";
	private String fileTypesDescription = "project reference";
	// 按钮imageurl、flash url
	private String imageUrl;
	private String imagePath;
	private String flashUrl;
	//返回值格式：1-文件名；2-文件真实名；3-存储路径；4-文件大小；5-文件类型；6-关系ID；
	//比如您要返回文件名、存储路径、文件大小，您应该在标签中添加属性为returnValue="1,3,4"
	//返回值也将以这个顺序返回，如"卡农.mp3,D:\music\,3072KB"
	private String returnValue;
	//自定义附件类型--字典Code
	//这里的附件类型不是附件的后缀名类型，而是业务分类；如果要限制某业务分类必须上传哪种后缀的附件，可在该类型对应的字典的remark字段中添加形如'@T*.doc;*.docx'的备注
	private String typeDicCode;
	//是否只能删除自己上传的附件，默认为都可以删除，前提是有删除权限
	private String delSelfOnly = "false";
	
	//附件整合在线编辑（北京科瀚软件SOAOffice  word）
	private String editFileType = "doc,docx";//可编辑的文件类型
	private String editAction;
	private String mode = "2";//编辑模式 1可编辑2只读
	private String downable;
	private String isEdit;
	
	@SuppressWarnings("static-access")
    public int doStartTag()
	{
		Object obj = pageContext.getSession().getAttribute("userSession");
		UserSession us = null;
		if(obj!=null && obj instanceof UserSession)
		{
			us = (UserSession)obj;
		}
		try
		{
			// 标签显示开始
			StringBuffer uploadHtmlHodler = new StringBuffer();
			// 生成一个div
			uploadHtmlHodler.append("<div id='");
			uploadHtmlHodler.append(this.getId());
			uploadHtmlHodler.append(SwfuploadUtil.SWFUPLOAD_DIV);
			uploadHtmlHodler.append("' ></div>\n");
			// script
			uploadHtmlHodler.append("<script type=\"text/javascript\">\n");
			if(us!=null){
				uploadHtmlHodler.append("var Sys_CurrentLoginUserPid='");
				uploadHtmlHodler.append(us.getPid());
				uploadHtmlHodler.append("';\n");
			}
			uploadHtmlHodler.append("var ");
			uploadHtmlHodler.append(this.getId());
			uploadHtmlHodler.append(" = new UltraSWFUpload('");
			uploadHtmlHodler.append(this.getId());
			uploadHtmlHodler.append("','");
			uploadHtmlHodler.append(this.getUploadAction());
			uploadHtmlHodler.append("','");
			uploadHtmlHodler.append(this.getQueryAction());
			uploadHtmlHodler.append("','");
			uploadHtmlHodler.append(this.getDownloadAction());
			uploadHtmlHodler.append("','");
			uploadHtmlHodler.append(this.getAttchmentGroupId());
			uploadHtmlHodler.append("','");
			uploadHtmlHodler.append(this.getUploadDestination());
			uploadHtmlHodler.append("',");
			uploadHtmlHodler.append(this.getUploadable());
			uploadHtmlHodler.append(",");
			uploadHtmlHodler.append(this.getUploadTableVisible());
			uploadHtmlHodler.append(",");
			uploadHtmlHodler.append(this.getDownTableVisible());
			uploadHtmlHodler.append(",");
			uploadHtmlHodler.append(this.getUploadBtnIsVisible());
			uploadHtmlHodler.append(",'");
			uploadHtmlHodler.append(this.getImageUrl());
			uploadHtmlHodler.append("','");
			uploadHtmlHodler.append(this.getFlashUrl());
			uploadHtmlHodler.append("','");
			uploadHtmlHodler.append(this.getFileTypes());
			uploadHtmlHodler.append("','");
			uploadHtmlHodler.append(this.getFileTypesDescription());
			uploadHtmlHodler.append("',");
			uploadHtmlHodler.append(this.getIsMultiUpload());
			uploadHtmlHodler.append(",");
			uploadHtmlHodler.append(this.getIsMultiDownload());
			uploadHtmlHodler.append(",");
			uploadHtmlHodler.append(this.getIsAutoUpload());
			uploadHtmlHodler.append(",");
			uploadHtmlHodler.append(this.getProgressIsVisible());
			uploadHtmlHodler.append(",'");
			uploadHtmlHodler.append(this.getImagePath());
			uploadHtmlHodler.append("','");
			uploadHtmlHodler.append(this.getOperationParams());
			uploadHtmlHodler.append("','");
			uploadHtmlHodler.append(this.getEditFileType());
			uploadHtmlHodler.append("','");
			uploadHtmlHodler.append(this.getDeleteAction());
			uploadHtmlHodler.append("',");
			uploadHtmlHodler.append(this.getCreateDirByDate());
			uploadHtmlHodler.append(",'");
			uploadHtmlHodler.append(this.getAttachmentUser());
			uploadHtmlHodler.append("','");
			uploadHtmlHodler.append(this.getReturnValue());
			uploadHtmlHodler.append("',");
			uploadHtmlHodler.append(this.getDelSelfOnly());
			uploadHtmlHodler.append(",'");
			uploadHtmlHodler.append(this.getTypeDicCode());
			uploadHtmlHodler.append("','");
			uploadHtmlHodler.append(this.getServicePath());
			uploadHtmlHodler.append("/attachment/queryAttachType.action");
			uploadHtmlHodler.append("','");
			uploadHtmlHodler.append(this.getEditAction());
			uploadHtmlHodler.append("','");
			uploadHtmlHodler.append(this.getMode());
			uploadHtmlHodler.append("');\n");
			uploadHtmlHodler.append("</script>\n");
			pageContext.getOut().write(uploadHtmlHodler.toString());
//			System.out.println(uploadHtmlHodler.toString());
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return this.SKIP_BODY;
	}

	private String getServicePath()
	{
//		StringBuffer pathBuffer = new StringBuffer(this.pageContext.getRequest().getScheme());
		StringBuffer pathBuffer = new StringBuffer();
//		pathBuffer.append("://");
//		pathBuffer.append(this.pageContext.getRequest().getServerName());
//		pathBuffer.append(":");
//		pathBuffer.append(this.pageContext.getRequest().getServerPort());
		pathBuffer.append(((HttpServletRequest)this.pageContext.getRequest()).getContextPath());
		return pathBuffer.toString();
	}
	
	public String getUploadAction()
	{
		if(this.uploadAction==null)
			uploadAction = getServicePath() + "/attachment/upload.action";
		return uploadAction;
	}

	public void setUploadAction(String uploadAction)
	{
		this.uploadAction = this.getServicePath()+ "/"+uploadAction;
	}

	public String getDownloadAction()
	{
		if(this.downloadAction==null)
			downloadAction = this.getServicePath() + "/attachment/download.action";
		return downloadAction;
	}

	public void setDownloadAction(String downloadAction)
	{
		this.downloadAction = this.getServicePath() + "/"+downloadAction;
	}
	
	
	
	public String getEditAction() {
		if(this.editAction==null)
			this.editAction = this.getServicePath() + "/oledit/loadDocument.action";
		return editAction;
		
	}

	public void setEditAction(String editAction) {
		this.editAction = this.getServicePath() + "/"+ editAction;
	}

	public String getUploadDestination()
	{
		if(this.uploadDestination==null)
			uploadDestination = "";
		return uploadDestination;
	}

	public void setUploadDestination(String uploadDestination)
	{
		String temp = SwfuploadUtil.pathProcess(uploadDestination);
		this.uploadDestination = temp;
	}

	public String getUploadBtnIsVisible()
	{
		if("false".equalsIgnoreCase(uploadable) || "true".equalsIgnoreCase(isAutoUpload))
		{
			uploadBtnIsVisible = "false";
		}
		return uploadBtnIsVisible;
	}

	public void setUploadBtnIsVisible(String uploadBtnIsVisible)
	{
		if ("true".equalsIgnoreCase(uploadBtnIsVisible)
		        || "false".equalsIgnoreCase(uploadBtnIsVisible))
		{
			uploadBtnIsVisible = uploadBtnIsVisible.toLowerCase();
		}
		else
			uploadBtnIsVisible = "true";
		this.uploadBtnIsVisible = uploadBtnIsVisible;
	}

	public String getAttchmentGroupId()
	{
		if (StringUtils.isBlank(attchmentGroupId))
		{
			attchmentGroupId = UUIDGenerator.getUUID();
		}
		return attchmentGroupId;
	}

	public void setAttchmentGroupId(String attchmentGroupId)
	{
		this.attchmentGroupId = attchmentGroupId;
	}

	public String getDownTableVisible()
	{
		return downTableVisible;
	}
	
	public void setDownTableVisible(String downTableVisible)
	{
		if ("true".equalsIgnoreCase(downTableVisible)
		        || "false".equalsIgnoreCase(downTableVisible))
		{
			downTableVisible = downTableVisible.toLowerCase();
		}
		else
			downTableVisible = "true";
		this.downTableVisible = downTableVisible;
	}

	public String getFileTypes()
	{
		return fileTypes;
	}

	public void setFileTypes(String fileTypes)
	{
		this.fileTypes = fileTypes;
	}

	public String getFileTypesDescription()
	{
		return fileTypesDescription;
	}

	public void setFileTypesDescription(String fileTypesDescription)
	{
		this.fileTypesDescription = fileTypesDescription;
	}

	public String getImageUrl()
	{
		if (StringUtils.isBlank(imageUrl))
		{
			//imageUrl = "http://www.swfupload.org/button_sprite.png";
			imageUrl = "";
		}
		return imageUrl;
	}

	public void setImageUrl(String imageUrl)
	{
		this.imageUrl = imageUrl;
	}

	public String getFlashUrl()
	{
		if (StringUtils.isBlank(flashUrl))
		{
			//flashUrl = "http://www.swfupload.org/swfupload.swf";
			flashUrl=this.getServicePath()+"/common/plugin/swfupload/js/swfupload.swf";
		}
		return flashUrl;
	}

	public void setFlashUrl(String flashUrl)
	{
		this.flashUrl = flashUrl;
	}

	public String getQueryAction()
	{
		if(this.queryAction==null)
			queryAction = this.getServicePath() + "/attachment/queryDownList.action";
		return queryAction;
	}

	public void setQueryAction(String queryAction)
	{
		this.queryAction = getServicePath()+"/"+queryAction;
	}

	public String getIsMultiUpload()
    {
    	if("false".equalsIgnoreCase(uploadable))
    		isMultiUpload = "false";
		return isMultiUpload;
    }

	public void setIsMultiUpload(String isMultiUpload)
    {
		if ("true".equalsIgnoreCase(isMultiUpload)
		        || "false".equalsIgnoreCase(isMultiUpload))
		{
			isMultiUpload = isMultiUpload.toLowerCase();
		}
		else
			isMultiUpload = "true";
		this.isMultiUpload = isMultiUpload;
    }

	public String getUploadable()
    {
    	return uploadable;
    }

	public void setUploadable(String uploadable)
    {
		if ("true".equalsIgnoreCase(uploadable)
		        || "false".equalsIgnoreCase(uploadable))
		{
			uploadable = uploadable.toLowerCase();
		}
		else
			uploadable = "true";
		this.uploadable = uploadable;
    }

	public String getIsMultiDownload()
    {
		if(getOperationParams().matches(".,.,0"))
    		isMultiDownload = "false";
		return isMultiDownload;
    }

	public void setIsMultiDownload(String isMultiDownload)
    {
		if ("true".equalsIgnoreCase(isMultiDownload)
		        || "false".equalsIgnoreCase(isMultiDownload))
		{
			isMultiDownload = isMultiDownload.toLowerCase();
		}
		else
			isMultiDownload = "true";
    	this.isMultiDownload = isMultiDownload;
    }

	public String getIsAutoUpload()
    {
    	return isAutoUpload;
    }

	public void setIsAutoUpload(String isAutoUpload)
    {
		if ("true".equalsIgnoreCase(isAutoUpload)
		        || "false".equalsIgnoreCase(isAutoUpload))
		{
			isAutoUpload = isAutoUpload.toLowerCase();
		}
		else
			isAutoUpload = "false";
		this.isAutoUpload = isAutoUpload;
    }

	public String getUploadTableVisible()
    {
    	if("false".equals(this.uploadable))
    		uploadTableVisible = "false";
		return uploadTableVisible;
    }

	public void setUploadTableVisible(String uploadTableVisible)
    {
		if ("true".equalsIgnoreCase(uploadTableVisible)
		        || "false".equalsIgnoreCase(uploadTableVisible))
		{
			uploadTableVisible = uploadTableVisible.toLowerCase();
		}
		else
			isAutoUpload = "false";
		this.uploadTableVisible = uploadTableVisible;
    }

	public String getProgressIsVisible()
    {
    	return progressIsVisible;
    }

	public void setProgressIsVisible(String progressIsVisible)
    {
		if ("true".equalsIgnoreCase(progressIsVisible)
		        || "false".equalsIgnoreCase(progressIsVisible))
		{
			progressIsVisible = progressIsVisible.toLowerCase();
		}
		else
			progressIsVisible = "true";
		this.progressIsVisible = progressIsVisible;
    }

	public String getImagePath() {
		if(this.imagePath==null)
			imagePath = this.getServicePath()+"/common/style/blue/";
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getDeleteAction() {
		if(this.deleteAction==null)
			deleteAction = this.getServicePath() + "/attachment/deleteAttachment.action";
		return deleteAction;
	}

	public void setDeleteAction(String deleteAction) {
		this.deleteAction = this.getServicePath() + "/"+deleteAction;
	}

	public String getAttachmentUser() {
		if(StringUtils.isBlank(attachmentUser))
		{
			attachmentUser = "";
		}
		return attachmentUser;
	}

	public void setAttachmentUser(String attachmentUser) {
		this.attachmentUser = attachmentUser;
	}

	public String getCreateDirByDate() {
		return createDirByDate;
	}

	public void setCreateDirByDate(String createDirByDate) {
		if ("true".equalsIgnoreCase(createDirByDate)
		        || "false".equalsIgnoreCase(createDirByDate))
		{
			createDirByDate = createDirByDate.toLowerCase();
		}
		else
			createDirByDate = "false";
		this.createDirByDate = createDirByDate;
	}

	public String getReturnValue() {
		if(this.returnValue==null)
			return "";
		return returnValue;
	}

	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getOperationParams() {
		if(operationParams==null){
			String delable = "false".equals(getIsEdit())?"0":"1";
			String downable = "false".equals(getDownable())?"0":"1";
			return "0,"+delable+","+downable;
		}
		return operationParams;
	}

	public void setOperationParams(String operationParams) {
		this.operationParams = operationParams;
	}

	public String getEditFileType() {
		return editFileType;
	}

	public void setEditFileType(String editFileType) {
		this.editFileType = editFileType;
	}

	public String getTypeDicCode() {
		if(this.typeDicCode==null)
			return "";
		return typeDicCode;
	}

	public void setTypeDicCode(String typeDicCode) {
		this.typeDicCode = typeDicCode;
	}
	
	public String getDownable() {
		return downable;
	}

	public void setDownable(String downable) {
		if ("true".equalsIgnoreCase(downable)
		        || "false".equalsIgnoreCase(downable))
		{
			downable = downable.toLowerCase();
		}
		else
			downable = "false";
		this.downable = downable;
	}

	public String getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(String isEdit) {
		if ("true".equalsIgnoreCase(isEdit)
		        || "false".equalsIgnoreCase(isEdit))
		{
			isEdit = isEdit.toLowerCase();
		}
		else
			isEdit = "false";
		this.isEdit = isEdit;
	}

	public String getDelSelfOnly() {
		return delSelfOnly;
	}

	public void setDelSelfOnly(String delSelfOnly) {
		if ("true".equalsIgnoreCase(delSelfOnly)
		        || "false".equalsIgnoreCase(delSelfOnly))
		{
			delSelfOnly = delSelfOnly.toLowerCase();
		}
		else
			delSelfOnly = "false";
		this.delSelfOnly = delSelfOnly;
	}
	
}
