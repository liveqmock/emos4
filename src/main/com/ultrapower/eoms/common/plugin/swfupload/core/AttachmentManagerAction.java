package com.ultrapower.eoms.common.plugin.swfupload.core;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.ultrapower.eoms.common.constants.PropertiesUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.plugin.swfupload.utils.SwfDoloadBean;
import com.ultrapower.eoms.common.plugin.swfupload.utils.SwfuploadUtil;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ultrasm.model.Attachment;
import com.ultrapower.eoms.ultrasm.model.DicItem;
import com.ultrapower.eoms.ultrasm.service.AttachmentManagerService;
import com.ultrapower.eoms.ultrasm.service.DicManagerService;
import com.ultrapower.eoms.ultrasm.service.UserManagerService;

public class AttachmentManagerAction extends BaseAction
{
	private AttachmentManagerService attachmentManagerService;
	private UserManagerService userManagerService;
	private ArrayList<String> downablelist = new ArrayList<String>(); // 批量下載文件列表
	private DicManagerService dicManagerService;
	// upload file parameters
	private String fileFileName;
	private String fileNewName;
	private String fileTypes;
	private File file;
	private String fileContentType;
	private String fileTypesDescription;//文件描述
	private String savePath;
	private String createDirByDate;
	// biz parameters
	private String attachmentUser; //附件人，由谁定制的附件（如果为空，则从session中去）
	private String attachmentGroupId;
	private String attachmentId;
	private List<Map<String, String>> items;
	private String sessionId; // current sessionId
	//返回值格式：1-文件名；2-文件真实名；3-存储路径；4-文件大小；5-文件类型；6-关系ID；
	private String returnValue;
	private String downloadIds;
	

	/**
	 * 上传附件(action)
	 */
	public String upload()
	{
		String customPath = store();
		if(customPath!=null){
			// 保存附件的相关信息到数据库
			UserSession userSession = this.getUserSession();
			String userid = (attachmentUser==null || "".equals(attachmentUser))?userSession.getPid():attachmentUser;
			long createtime = TimeUtils.getCurrentTime();
			Attachment attachment = new Attachment();
			attachment.setCreater(userid);
			attachment.setLastmodifier(userid);
			attachment.setCreatetime(createtime);
			attachment.setName(getFileFileName());
			attachment.setRealname(getFileNewName());
			String fileType = getFileFileName().substring(getFileFileName().lastIndexOf(".")+1);
			attachment.setType(fileType);
			attachment.setPath(customPath);
			attachment.setRelationcode(getAttachmentGroupId());
			String fileSize = ""+BigDecimal.valueOf(file.length()).divide(BigDecimal.valueOf(1024L),1,BigDecimal.ROUND_CEILING)+"KB";
			attachment.setAttsize(fileSize);
			// 入库操作
			attachmentManagerService.addAttachment(attachment);
			// 返回记录id
			attachmentId = getAttachmentGroupId();
			returnValue = parseReturnValue(customPath,fileSize,attachmentId,fileType);
		}
		return SUCCESS;
	} 
	
	/**
	 * 保存附件
	 * @return
	 * @throws Exception
	 */
	protected String store(){
		SwfuploadUtil sfu = new SwfuploadUtil();
		setFileNewName(SwfuploadUtil.reName(getFileFileName()));
		String customPath = null;
		try {
			customPath = sfu.upload(new FileInputStream(file), new SwfDoloadBean(getFileFileName(),getFileNewName(),getSavePath()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} finally {
			sfu.close();
		}
		return customPath;
	}
	
	/**
	 * 下载附件，批量和单个附件下载(action)
	 */
	public void downloadAttachment()
	{
		String ids = this.getDownloadIds();
		if(ids==null||"".equals(ids.trim())){
			SwfuploadUtil sfu = new SwfuploadUtil();
			InputStream tempIns = sfu.download(new SwfDoloadBean(null,this.getFileNewName(),this.getSavePath()));
			sfu.close();
			downing(tempIns);
		}else{
			String[] idArr = ids.split(",");
			List<SwfDoloadBean> beans = new ArrayList<SwfDoloadBean>();
			int len = idArr.length;
			for(int i=0;i<len;i++){
				Attachment attach = attachmentManagerService.getAttachmentById(idArr[i]);
				if(attach==null||attach.getRealname()==null)
					continue;
				beans.add(new SwfDoloadBean(attach.getName(),attach.getRealname(),attach.getPath()));
			}
			int beanSize = beans.size();
			if(beanSize==1){
				SwfuploadUtil sfu = new SwfuploadUtil();
				InputStream ins = sfu.download(beans.get(0));
				sfu.close();
				setFileFileName(beans.get(0).getFileName());
				setFileNewName(beans.get(0).getSaveName());
				setSavePath(beans.get(0).getSavePath());
				downing(ins);
			}else if(beanSize>1){
				SwfuploadUtil sfu = new SwfuploadUtil();
				InputStream ins = sfu.batchDownload(beans, null);
				setFileFileName(SwfuploadUtil.SWFUPLOAD_BATCH_FILENAME);
				downing(ins);
			}
		}
	}
	
	/**
	 * 真正的下载实现，输出流文件
	 * @param fileInputStream
	 */
	protected void downing(InputStream fileInputStream)
	{
		if(fileInputStream==null){
			try {
				getResponse().getOutputStream().write("Sorry, file(s) can't be found!".getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/octet-stream");
		String fileName = getFileFileName();
		try
		{
//			fileName = URLDecoder.decode(fileName, "UTF-8");
//			fileName = new String(fileName.getBytes(), "iso8859-1");
			
			fileName = URLEncoder.encode(fileName, "utf-8");
			//处理空格变加号 
			fileName = fileName.replace("+", "%20");
		}
		catch (UnsupportedEncodingException e)
		{
			log.error(e.getMessage(), e);
		}
		
		response.setHeader("Content-disposition", "attachment;filename="
		        + fileName);
		BufferedInputStream bis = new BufferedInputStream(fileInputStream);
		BufferedOutputStream bos = null;
		try
		{
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length)))
			{
				bos.write(buff, 0, bytesRead);
			}

		}
		catch (Exception e)
		{
			if("ClientAbortException".equals(e.getClass().getSimpleName()))
			{
				System.out.println("----->Socket异常，可能原因是客户端中断了附件下载。");
			}
			else
				log.error(e.getMessage(), e);
		}
		finally
		{
			try
			{
				if (bos != null)
				{
					bos.close();
				}
			}
			catch (IOException e)
			{
				log.error(e.getMessage(), e);
			}

			try
			{
				if (bis != null)
				{
					bis.close();
				}
			}
			catch (IOException e)
			{
				log.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * 根据附件ID删除附件（action）
	 */
	public void deleteAttachment()
	{
		String jsoncallback = getRequest().getParameter("jsoncallback");
		boolean b = false;
		String attachmentId = this.getRequest().getParameter("attachmentId");
		if(attachmentId!=null)
		{
			Attachment attach = attachmentManagerService.getAttachmentById(attachmentId);
			if(attach!=null)
			{
				setSavePath(SwfuploadUtil.pathProcess(attach.getPath()));
				setFileNewName(attach.getRealname());
				String storeType = PropertiesUtils.getProperty("attachment.storetype");
				physicalDel(getSavePath(), getFileNewName());
				attachmentManagerService.deleteAttachmentById(attachmentId);
				b = true;
			}
		}
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
	 * 物理删除附件
	 * @param att
	 */
	protected void physicalDel(String savePath,String fileName){
		SwfuploadUtil sfu = new SwfuploadUtil();
		sfu.deleteAttachment(new SwfDoloadBean(null,fileName,savePath));
	}
	
	/**
	 * 根据attachGroupId获得下载列表(action)
	 * @return
	 */
	public String queryDownList()
	{
		items = new ArrayList<Map<String, String>>();
		List<Attachment> attachments = attachmentManagerService
		        .getAttachmentByRelation(getAttachmentGroupId());
		String jsoncallback = getRequest().getParameter("ajax");
		if (attachments != null && !attachments.isEmpty())
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
				items.add(item);
			}
		}
		
		if(jsoncallback != null && !"".equals(jsoncallback))
		{
			String json = "[]";
			if(items.size() > 0)
			{
				JSONArray jsonObj = JSONArray.fromObject(items);
				json = jsonObj.toString();
			}
			try
			{
				PrintWriter out = getResponse().getWriter();
				getResponse().setContentType("text/html;charset=UTF-8");
				out.print(json);
				out.close();
				out.flush();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			return null;
		}
		else
		{
			return SUCCESS;
		}
	}
	
	/**
	 * 根据attachGroupId获得下载列表(action),返回json
	 */
	public void getAttachmentByRelation(){
		List<Attachment> attachments = attachmentManagerService.getAttachmentByRelation(attachmentGroupId);
		renderText(JSONArray.fromObject(attachments).toString());
	}
	
	/**
	 * 获得根据附件类型字典类型code获得附件类型列表(action)
	 * @return
	 */
	public String queryAttachType(){
		String jsoncallback = getRequest().getParameter("jsoncallback");
		items = new ArrayList<Map<String, String>>();
		String dicCode = this.getRequest().getParameter("dicCode");
		List<DicItem> itemlst = dicManagerService.getDicItemByDicType(dicCode);
		if (itemlst != null && !itemlst.isEmpty())
		{
			if(jsoncallback==null||"".equals(jsoncallback)){
				for (DicItem dic : itemlst)
				{
					Map<String, String> item = new HashMap<String, String>();
					item.put("typeValue",dic.getDiname()==null?"":dic.getDiname().trim());
					item.put("rule", dic.getRemark()==null?"":dic.getRemark().trim());
					items.add(item);
				}
			}else{
				int len = itemlst.size();
				StringBuffer json = new StringBuffer();
				json.append("[");
				for(int i=0;i<len;i++){
					if(i!=0)
						json.append(",");
					DicItem dic = itemlst.get(i);
					json.append("{");
					json.append("\"typeValue\":\"");
					json.append(dic.getDiname()==null?"":dic.getDiname().trim());
					json.append("\",\"rule\":\"");
					json.append(dic.getRemark()==null?"":dic.getRemark().trim());
					json.append("\"");
					json.append("}");
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
		return SUCCESS;
	}
	
	/**
	 * 解析返回数据
	 * @param repositoryPath
	 * @param fileSize
	 * @param relationId
	 * @param fileType
	 * @return
	 */
	public String parseReturnValue(String repositoryPath,String fileSize,String relationId,String fileType)
	{
		String formatStr = this.returnValue;
		if(formatStr==null || "".equals(formatStr))
			return "";
		String temp[] = formatStr.split(",");
		StringBuffer result = new StringBuffer();
		for(int i=0;i<temp.length;i++)
		{
			String tag = temp[i];
			result.append(",");
			if("1".equals(tag))
			{
				result.append(getFileFileName());
			}
			else if("2".equals(tag))
			{
				result.append(getFileNewName());
			}
			else if("3".equals(tag))
			{
				result.append(repositoryPath);
			}
			else if("4".equals(tag))
			{
				result.append(fileSize);
			}
			else if("5".equals(tag))
			{
				result.append(fileType);
			}
			else if("6".equals(tag))
			{
				result.append(relationId);
			}
		}
		if(result.length()>0)
			return result.substring(1);
		else
			return result.toString();
	}
	
	/**
	 * 取得附件数量
	 * @return
	 */
	public String getAttNum() {
		List<Attachment> attList = attachmentManagerService.getAttachmentByRelation(attachmentGroupId);
		if (attList == null) {
			this.renderText("0");
		} else {
			this.renderText(String.valueOf(attList.size()));
		}
		return null;
	}
	
	/*
	 * 以下为属性的GET/SET方法
	 */
	public void setAttachmentManagerService(
	        AttachmentManagerService attachmentManagerService)
	{
		this.attachmentManagerService = attachmentManagerService;
	}

	public void setUserManagerService(UserManagerService userManagerService) {
		this.userManagerService = userManagerService;
	}

	public String getFileFileName()
	{
		return fileFileName;
	}

	public void setFileFileName(String fileFileName)
	{
		if(fileFileName!=null)
			this.fileFileName = (fileFileName.replace("@join@", "&")).replace("@jing@", "#");
	}

	public String getFileNewName()
	{
		return fileNewName;
	}

	public void setFileNewName(String fileNewName)
	{
		if(fileNewName!=null)
			this.fileNewName = (fileNewName.replace("@join@", "&")).replace("@jing@", "#");
	}

	public String getFileTypes()
	{
		return fileTypes;
	}

	public void setFileTypes(String fileTypes)
	{
		this.fileTypes = fileTypes;
	}

	public File getFile()
	{
		return file;
	}

	public void setFile(File file)
	{
		this.file = file;
	}

	public String getFileContentType()
	{
		return fileContentType;
	}

	public void setFileContentType(String fileContentType)
	{
		this.fileContentType = fileContentType;
	}

	public String getSavePath()
	{
		return savePath;
	}

	public void setSavePath(String savePath)
	{
		if(savePath!=null)
			this.savePath = (savePath.replace("@join@", "&")).replace("@jing@", "#");
	}

	public String getAttachmentGroupId() 
	{
		return attachmentGroupId;
	}

	public void setAttachmentGroupId(String attachmentGroupId)
	{
		this.attachmentGroupId = attachmentGroupId;
	}

	public String getAttachmentId()
	{
		return attachmentId;
	}

	public void setAttachmentId(String attachmentId)
	{
		this.attachmentId = attachmentId;
	}

	public String getSessionId()
	{
		return sessionId;
	}

	public void setSessionId(String sessionId)
	{
		this.sessionId = sessionId;
	}

	public List<Map<String, String>> getItems()
	{
		if (items == null)
			items = new ArrayList<Map<String, String>>();
		return items;
	}

	public void setItems(List<Map<String, String>> items)
	{
		this.items = items;
	}

	public ArrayList<String> getDownablelist()
	{
		return downablelist;
	}

	public void setDownablelist(ArrayList<String> downablelist)
	{
		this.downablelist = downablelist;
	}

	public String getAttachmentUser() {
		return attachmentUser;
	}

	public void setAttachmentUser(String attachmentUser) {
		this.attachmentUser = attachmentUser;
	}
	
	public String getFileTypesDescription() {
		return fileTypesDescription;
	}

	public void setFileTypesDescription(String fileTypesDescription) {
		this.fileTypesDescription = fileTypesDescription;
	}

	public String getCreateDirByDate() {
		return createDirByDate;
	}

	public void setCreateDirByDate(String createDirByDate) {
		this.createDirByDate = createDirByDate;
	}

	public String getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}
	
	public AttachmentManagerService getAttachmentManagerService() {
		return attachmentManagerService;
	}

	public void setDicManagerService(DicManagerService dicManagerService) {
		this.dicManagerService = dicManagerService;
	}

	public String getDownloadIds() {
		return downloadIds;
	}

	public void setDownloadIds(String downloadIds) {
		this.downloadIds = downloadIds;
	}
	
}