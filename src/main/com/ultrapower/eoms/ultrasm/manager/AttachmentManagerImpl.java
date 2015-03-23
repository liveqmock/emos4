package com.ultrapower.eoms.ultrasm.manager;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.plugin.swfupload.utils.SwfDoloadBean;
import com.ultrapower.eoms.common.plugin.swfupload.utils.SwfuploadUtil;
import com.ultrapower.eoms.ultrasm.RecordLog;
import com.ultrapower.eoms.ultrasm.model.Attachment;
import com.ultrapower.eoms.ultrasm.service.AttachmentManagerService;
import com.ultrapower.eoms.ultrasm.util.UltraSmUtil;

@Transactional
public class AttachmentManagerImpl implements AttachmentManagerService
{
	private IDao<Attachment> attachmentDao;
	
	public boolean addAttachment(Attachment attachment)
	{
		boolean result = false;
		if(attachment == null)
			return result;
		try
		{
			attachmentDao.save(attachment);
			result = true;
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return result;
	}

	public boolean addAttachment(List<Attachment> attachmentList)
	{
		if(attachmentList != null && attachmentList.size() > 0)
		{
			for(int i=0;i<attachmentList.size();i++)
			{
				if(!this.addAttachment(attachmentList.get(i)))
					return false;
			}
		}
		return true;
	}

	public Attachment getAttachmentById(String pid)
	{
		if("".equals(StringUtils.checkNullString(pid)))
			return null;
		return attachmentDao.get(pid);
	}

	public List<Attachment> getAttachmentByRelation(String code) {
		if("".equals(StringUtils.checkNullString(code)))
			return null;
		List<Attachment> attachmentList = null;
		try
		{
			attachmentList = attachmentDao.find(" from Attachment where relationcode = ? order by createtime desc", new Object[] {code});
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return attachmentList;
	}

	public boolean deleteAttachmentById(String attId)
	{
		boolean result = false;
		if("".equals(StringUtils.checkNullString(attId)))
			return result;
		try
		{
			attachmentDao.removeById(attId);
			result = true;
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean deleteAttachmentById(List attIdList)
	{
		boolean result = false;
		attIdList = UltraSmUtil.removeNullData(attIdList);
		if(attIdList != null && attIdList.size() > 0)
		{
			try
			{
				Map map = UltraSmUtil.getSqlParameter(attIdList);
				attachmentDao.executeUpdate(" delete Attachment where pid in (" + map.get("?") + ")", (Object[]) map.get("obj"));
				result = true;
			}
			catch (Exception e)
			{
				RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
				e.printStackTrace();
			}
		}
		else
		{
			result = true;
		}
		return result;
	}
	
	public boolean deleteAttachmentByCode(String code)
	{
		boolean result = false;
		if("".equals(StringUtils.checkNullString(code)))
		{
			return result;
		}
		try
		{
			attachmentDao.executeUpdate(" delete Attachment where relationcode = ? ", new Object[] {code});
			result = true;
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return result;
	}
	
	public List<Attachment> getAttachByUseridAndRelationCode(String userId, String relationCode)
	{
		List<Attachment> attachLst = null;
		userId = StringUtils.checkNullString(userId);
		relationCode = StringUtils.checkNullString(relationCode);
		if(!"".equals(userId) && !"".equals(relationCode))
		{
			String hql = "from Attachment where creater = ? and relationcode = ?";
			attachLst = attachmentDao.find(hql, new Object[]{userId, relationCode});
		}
		return attachLst;
	}
	
	public List<Attachment> queryByHql(String hql) {
		return attachmentDao.find(hql, null);
	}
	
	public void setAttachmentDao(IDao<Attachment> attachmentDao) {
		this.attachmentDao = attachmentDao;
	}
	
	/**
	 * 更新附件表的关联编码
	 * @param oldCode
	 * @param newCode
	 */
	public void updateRelationcode(String oldCode, String newCode) {
		attachmentDao.executeUpdate("update Attachment a set a.relationcode = ? where a.relationcode = ?", new Object[]{newCode, oldCode });
	}

	public Attachment uploadAttachment(InputStream ins,
			String fileName,String path,String relationCode,String creater,String remark) {
		if(ins==null||fileName==null||relationCode==null)
			return null;
		Attachment attachment = new Attachment();
		attachment.setName(fileName);
		attachment.setRealname(SwfuploadUtil.reName(fileName));
		attachment.setRelationcode(relationCode);
		int poi = fileName.lastIndexOf(".");
		if(poi!=-1&&poi<fileName.length()-1)
			attachment.setType(fileName.substring(poi+1));
		path = SwfuploadUtil.pathProcess(path);
		attachment.setPath(path);
		attachment.setCreater(creater);
		attachment.setCreatetime(TimeUtils.getCurrentTime());
		attachment.setRemark(remark);
		try {
			attachment.setAttsize(BigDecimal.valueOf(ins.available()).divide(BigDecimal.valueOf(1024L),1,BigDecimal.ROUND_CEILING).toPlainString()+"KB");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return uploadAttachment(ins,attachment);
	}
	
	public Attachment uploadAttachment(InputStream ins,Attachment attachment){
		if(ins==null||attachment==null||attachment.getName()==null||attachment.getRelationcode()==null)
			return null;
		if(attachment.getRealname()==null)
			attachment.setRealname(SwfuploadUtil.reName(attachment.getName()));
		SwfuploadUtil sfu = new SwfuploadUtil();
		String customPath = sfu.upload(ins, new SwfDoloadBean(attachment.getName(),attachment.getRealname(),attachment.getPath()));
		sfu.close();
		if(customPath!=null){
			attachment.setPath(customPath);
			addAttachment(attachment);
		}else{
			return null;
		}
		return attachment;
	}

	public InputStream downloadAttById(String attId) {
		if(attId==null)
			return null;
		Attachment att = getAttachmentById(attId);
		if(att==null)
			return null;
		return downloadAttByPath(att.getPath(),att.getRealname());
	}

	public InputStream downloadAttByIdLst(List<String> attIdLst) {
		if(attIdLst==null||attIdLst.size()<=0)
			return null;
		List<Attachment> attLst = new ArrayList<Attachment>();
		int len = attIdLst.size();
		for(int i=0;i<len;i++){
			Attachment att = getAttachmentById(attIdLst.get(i));
			if(att!=null)
				attLst.add(att);
		}
		return downloadAtts(attLst,null);
	}

	public InputStream downloadAttByPath(String savePath, String realName) {
		if(savePath==null||realName==null)
			return null;
		SwfuploadUtil sfu = new SwfuploadUtil();
		InputStream ins = sfu.download(new SwfDoloadBean(null,realName,savePath));
		sfu.close();
		return ins;
	}

	public InputStream downloadAttByRelationCode(String relationCode) {
		if(relationCode==null)
			return null;
		List<Attachment> attLst = getAttachmentByRelation(relationCode);
		return downloadAtts(attLst,null);
	}

	public InputStream downloadAtts(List<Attachment> attObjLst,String packageName) {
		if(attObjLst==null)
			return null;
		SwfuploadUtil sfu = new SwfuploadUtil();
		List<SwfDoloadBean> beans = new ArrayList<SwfDoloadBean>();
		int len = attObjLst.size();
		for(int i=0;i<len;i++){
			Attachment att = attObjLst.get(i);
			if(att==null||att.getRealname()==null)
				continue;
			beans.add(new SwfDoloadBean(att.getName(),att.getRealname(),att.getPath()));
		}
		InputStream ins = sfu.batchDownload(beans, packageName);
		sfu.close();
		return ins;
	}
}
