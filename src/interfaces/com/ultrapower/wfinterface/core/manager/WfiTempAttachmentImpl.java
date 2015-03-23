package com.ultrapower.wfinterface.core.manager;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.constants.PropertiesUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.util.ftpclient.FtpClicentApache;
import com.ultrapower.eoms.common.core.util.ftpclient.FtpUrlParser;
import com.ultrapower.eoms.common.core.util.ftpclient.IFtpClientServices;
import com.ultrapower.eoms.ultrasm.model.Attachment;
import com.ultrapower.eoms.ultrasm.service.AttachmentManagerService;
import com.ultrapower.wfinterface.core.service.WfiTempAttachmentService;

@Transactional
public class WfiTempAttachmentImpl implements WfiTempAttachmentService {

	private AttachmentManagerService attachmentManagerService;
	//接口附件的存储位置
	private String ifcAttFileDic = PropertiesUtils.getProperty("attachment.path");

	/**
	 * 根据Id取得接口附件数据
	 * @param pid
	 * @return
	 */
	public Attachment getAttachment(String pid){
		Attachment Attachment = attachmentManagerService.getAttachmentById(pid);
		return Attachment;
	}
	

	/**
	 * 保存接口数据
	 * 
	 * @param Attachment
	 * @return
	 */ 
	public String saveAttachment(Attachment attachment) throws Exception{

		if (!downloadAttachment(attachment)){
			throw new Exception("附件下载失败!");
		}
		//保存数据
		attachmentManagerService.addAttachment(attachment);

		return "数据保存成功！";
	}

	public boolean downloadAttachment(Attachment attachment) throws Exception{
		boolean result = false;
		String attachurl = attachment.getRemark();//备注保存的是附件的下载地址
		
		if (attachurl.toLowerCase().startsWith("ftp://")){
			String realName = UUID.randomUUID().toString();
			String path = "interface"+File.separator+TimeUtils.getCurrentDate("yyyy-MM-dd");//附件存储的文件夹
			String fullPath = ifcAttFileDic+File.separator+path;
			File localPathDic = new File(fullPath);
			if(!localPathDic.exists()||!localPathDic.isDirectory()){
				localPathDic.mkdirs();
			}
			result = downloadFtpAttachment(attachurl, fullPath+File.separator+realName);
			attachment.setAttsize(BigDecimal.valueOf(new File(fullPath).length()).divide(BigDecimal.valueOf(1024L),1,BigDecimal.ROUND_CEILING).toPlainString()+"KB");
			attachment.setCreatetime(TimeUtils.getCurrentTime());
			attachment.setPath(path);
			attachment.setRealname(realName);
		} else if ( attachurl.startsWith("http://")){
			throw new Exception("目前暂不支持HTTP协议");
		} else {
			throw new Exception("错误的协议");
		}
		return result;
	}


	/**
	 * @param attachurl 获取附件完整的Url
	 * @param filename  存为本地的文件路径全名
	 * @return 是否成功true成功false失败
	 * @throws Exception
	 */
	private boolean downloadFtpAttachment(String attachurl,	String localpath) throws Exception {
		boolean result = false;
		FtpUrlParser parser = new FtpUrlParser(attachurl);
		parser.parse();
		IFtpClientServices ftpclient=new FtpClicentApache();
		try {
			ftpclient.connectServer(parser.getHost(), parser.getPort(), parser.getUsername(), parser.getPassword());
			result = ftpclient.downloadFile(parser.getUrlPath(), localpath);
		} finally {
			ftpclient.closeServer();
		}
		return result;
	}

	/**
	 * 保存接口附件数据列表
	 * 
	 * @param list
	 * @return
	 */
	public String saveAttachment(List<Attachment> attachmentList) throws Exception{
		if (attachmentList == null || attachmentList.size() <= 0){
			return "";
		}

		//下载附件并设置附件的其他信息
		if (!downloadAttachment(attachmentList)){
			throw new Exception("附件下载失败!");
		}
		
		//保存数据
		attachmentManagerService.addAttachment(attachmentList);
		
		return "数据保存成功！";
	}

	public boolean downloadAttachment(List<Attachment> list) throws Exception{
		if (list == null || list.size() <= 0){
			return true;
		}

		boolean result = false;
		String releationCode = UUID.randomUUID().toString();
		for(Attachment attach : list){
			attach.setRelationcode(releationCode);//设置附件的关联code
			result = downloadAttachment(attach);
			if (!result){
				break;
			}
		}
		return result;
	}

	/**
	 * 根据Id删除接口附件数据
	 * @param pid
	 * @return
	 */
	public void deleteAttachment(String pid){
//		Attachment Attachment = getAttachment(pid);
//		if (Attachment!=null){
//			//删除实际文件
//			String filename = Attachment.getLocalfilepath();
//			FileOperUtil.delFile(filename);
//
//			//将数据标记为删除
//			Attachment.setStatus(0);
//			AttachmentDao.saveOrUpdate(Attachment);
//		}
//		//AttachmentDao.removeById(pid);//update state
	}

	/**
	 * 根据数据Id删除接口附件数据
	 * @param dataid
	 * @return
	 */
	public void deleteAttachmentList(String id){
		attachmentManagerService.deleteAttachmentById(id);
	}

	/**
	 * 根据数据ID取得接口附件数据列表
	 * @param dataid
	 * @return
	 */
	public List<Attachment> getAttachmentList(String code){
		return attachmentManagerService.getAttachmentByRelation(code);
	}


	public void setAttachmentManagerService(AttachmentManagerService attachmentManagerService) {
		this.attachmentManagerService = attachmentManagerService;
	}


//	private String getAttachmentFilePath(String filename) {
//		String filepath = "";
//		String fileext = filename.substring(filename.lastIndexOf("."));
//		if (Constants.ATTACHMENT_TMP_DIR.endsWith("/") || Constants.ATTACHMENT_TMP_DIR.endsWith("\\")){
//			filepath = Constants.ATTACHMENT_TMP_DIR + UUIDGenerator.getUUIDoffSpace() + fileext;
//		} else {
//			filepath = Constants.ATTACHMENT_TMP_DIR + "/" + UUIDGenerator.getUUIDoffSpace() + fileext;
//		}
//		return filepath;
//	}
	
}
