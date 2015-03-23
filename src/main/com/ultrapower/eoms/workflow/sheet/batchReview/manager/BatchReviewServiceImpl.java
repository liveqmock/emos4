package com.ultrapower.eoms.workflow.sheet.batchReview.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.mortbay.log.Log;
import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.constants.Constants;
import com.ultrapower.eoms.common.constants.PropertiesUtils;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.plugin.swfupload.utils.SwfuploadUtil;
import com.ultrapower.eoms.ultrasm.model.Attachment;
import com.ultrapower.eoms.workflow.sheet.batchReview.service.BatchReviewService;


@Transactional
public class BatchReviewServiceImpl implements BatchReviewService {

	private IDao<Attachment> attachmentDao;
	
	
	/**
	 * 根据工单BaseId查询工单所关联的附件Id
	 * @param baseId String 工单baseId
	 * @return chgAttache
	 */
	public String getAttIdByBaseId(String baseSchema,String baseId) {
		String relationAttId = "";
		if("CDB_RELEASE".equals(baseSchema)){
			relationAttId = "RELEASEATTACHE";
		}
		if("CDB_CHANGE".equals(baseSchema)){
			relationAttId = "CHGATTACHE";
		}
		String sql = "SELECT "+relationAttId+" FROM BS_F_"+baseSchema+" where baseid ='"+baseId+"'"; 
		Query query = attachmentDao.createSQLQuery(sql); 
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP); 
		List results = query.list(); 
		Map map = (Map) results.get(0);
		String chgAttache = map.get(relationAttId).toString();
		return chgAttache;
	}
	

	/**
	 * 批量审批时进行数据的克隆
	 * @param attaGroupId String  上传附件时生成的关联ID
	 * @param sheetRelationAttId String 每张工单与附件进行关联的ID
	 * @param baseId String 工单ID,这里对克隆的文件进行标识,每张工单克隆的文件为工单"baseID_文件真实名称"
	 * @param baseSchema String 工单的类型
	 * 
	 */
	
	public void cloneDataRecord(String attaGroupId,String sheetRelationAttId, String baseId, String baseSchema) {
		
		Query  queryAttListByRelCode = attachmentDao.createQuery("from Attachment t where t.relationcode =  :relationcode"); //根据批量评审页面传递的附件关系码，查询附件记录
		queryAttListByRelCode.setParameter("relationcode",attaGroupId);
		List<Attachment> attachmentList = queryAttListByRelCode.list();
		for(Attachment attachment:attachmentList){
			attachment =attachment.clone();
			
			//在数据库中将工单附件内容记录进行复制,使批量审批的每张工单都持有附件,新附件的命名规则为该工单baseId+"_"+原文件名
			attachment.setPid(""); 
			attachment.setRelationcode(sheetRelationAttId); 
			String attachmentOldName = attachment.getRealname();
			attachment.setRealname(baseId+"_"+attachment.getRealname());
			String storeType = PropertiesUtils.getProperty("attachment.storetype");
			attachmentDao.save(attachment); 
			
			//在磁盘中将工单附件内容进行复制,批量审批的每张工单都持有附件,新附件的命名规则为该工单baseId+"_"+原文件名
			StringBuilder inputFilePath = new StringBuilder();
			StringBuilder outFilePath = new StringBuilder();
			inputFilePath.append(SwfuploadUtil.SWFUPLOAD_UPLOAD_PATH).append("/").append(Constants.WORKSHEET_UPLOAD_PATH).append("/").append(baseSchema).append("/").append(attachmentOldName);
			outFilePath.append(SwfuploadUtil.SWFUPLOAD_UPLOAD_PATH).append("/").append(Constants.WORKSHEET_UPLOAD_PATH).append("/").append(baseSchema).append("/").append(attachment.getRealname());
			copyFile(inputFilePath.toString(), outFilePath.toString());
		}
	}
	
	/** 
     * 复制单个文件 
     * @param oldPath String 原文件路径  
     * @param newPath String 复制后路径
     */ 
   public void copyFile(String oldPath, String newPath) { 
       try { 
           int bytesum = 0; 
           int byteread = 0; 
           File oldfile = new File(oldPath); 
           if (oldfile.exists()) { //文件存在时 
               InputStream inStream = new FileInputStream(oldPath); //读入原文件 
               FileOutputStream fs = new FileOutputStream(newPath); 
               byte[] buffer = new byte[1444]; 
               int length; 
               while ( (byteread = inStream.read(buffer)) != -1) { 
                   bytesum += byteread; //字节数 文件大小 
                   fs.write(buffer, 0, byteread); 
               } 
               inStream.close(); 
           } 
       } 
       catch (Exception e) { 
    	   Log.info("复制单个文件操作出错"); 
           e.printStackTrace(); 

       } 

   } 
	
   
	public IDao<Attachment> getAttachmentDao() {
		return attachmentDao;
	}


	public void setAttachmentDao(IDao<Attachment> attachmentDao) {
		this.attachmentDao = attachmentDao;
	}
	

}
