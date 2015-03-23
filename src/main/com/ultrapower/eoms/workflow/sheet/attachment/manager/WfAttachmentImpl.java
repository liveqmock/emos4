package com.ultrapower.eoms.workflow.sheet.attachment.manager;

import java.io.InputStream;
import java.util.List;

import com.ultrapower.eoms.common.constants.Constants;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.util.UUIDGenerator;
import com.ultrapower.eoms.common.plugin.swfupload.utils.SwfDoloadBean;
import com.ultrapower.eoms.common.plugin.swfupload.utils.SwfuploadUtil;
import com.ultrapower.eoms.ultrasm.model.Attachment;
import com.ultrapower.eoms.workflow.sheet.attachment.model.WfAttachment;
import com.ultrapower.eoms.workflow.sheet.attachment.service.WfAttachmentService;

public class WfAttachmentImpl implements WfAttachmentService {
	
	private IDao<WfAttachment> wfAttDao;
	private IDao<Attachment> attachmentDao;

	public void save(WfAttachment att) {
		wfAttDao.save(att);
	}

	public IDao<WfAttachment> getWfAttDao() {
		return wfAttDao;
	}

	public void setWfAttDao(IDao<WfAttachment> wfAttDao) {
		this.wfAttDao = wfAttDao;
	}

	public List<WfAttachment> queryBySheetId(String sheetId) {
		String hql = "from WfAttachment where sheetId = '" + sheetId + "'";
		List find = wfAttDao.find(hql, null);
		return find;
	}
	
	public List<WfAttachment> getWfAttachment(String sheetId,String processId,String fieldId){
		if(sheetId==null)
			return null;
		String hql = null;
		Object[] obj = null;
		if(processId==null && fieldId==null){
			hql = "from WfAttachment where sheetId = ?";
			obj = new Object[]{sheetId};
		}
		else if(processId==null && fieldId !=null ){
			hql = "from WfAttachment where sheetId = ? and fieldId = ?";
			obj = new Object[]{sheetId,fieldId};
		}
		else if(processId!=null  && fieldId == null){
			hql = "from WfAttachment where sheetId = ? and processId= ?";
			obj = new Object[]{sheetId,processId};
		}
		else{
			hql = "from WfAttachment where sheetId = ? and processId= ? and fieldId=?";
			obj = new Object[]{sheetId,processId,fieldId};
		}
		List<WfAttachment> wfAttLst = wfAttDao.find(hql, obj);
		return wfAttLst;
	}
	
	public void delete(String attachId) {
		String hql = "delete from WfAttachment where attachId = '"+attachId+"'";
		wfAttDao.executeUpdate(hql, null);
	}
	public void saveWfAttach(Attachment attachment,WfAttachment wfAttachment) {
		if(attachment==null || wfAttachment==null)
			return;
		attachment.setRelationcode(wfAttachment.getProcessId()==null?wfAttachment.getSheetId():wfAttachment.getProcessId());
		if(attachment.getPid()==null)
			attachmentDao.save(attachment);
		else
			attachmentDao.saveOrUpdate(attachment);
		wfAttachment.setAttachId(attachment.getPid());
		save(wfAttachment);
	}
	
	public List<Attachment> getAttachmentByWfInfo(String sheetId,String processId,String fieldId){
		List<WfAttachment> wfAttLst = getWfAttachment(sheetId, processId, fieldId);
		if(wfAttLst==null||wfAttLst.size()<=0)
			return null;
		List<Attachment> attachLst;
		int len = wfAttLst.size();
		StringBuffer con = new StringBuffer();
		con.append("from Attachment where");
		for(int i=0;i<len;i++){
			if(i==0){
				con.append(" pid='");
				con.append(wfAttLst.get(i).getAttachId());
				con.append("'");
			}
			else{
				con.append(" or pid='");
				con.append(wfAttLst.get(i).getAttachId());
				con.append("'");
			}	
		}
		attachLst = attachmentDao.find(con.toString(), null);
		return attachLst;
	}

	public int copyWfAttachment(String srcSheetId, String srcProcessId,
			String srcFieldId, String tarSheetId, String tarProcessId,
			String tarFieldId, String tarBaseSchema, String createrId) {
		int copyCounter = 0;
		if(!"".equals(StringUtils.checkNullString(srcSheetId))
				&& !"".equals(StringUtils.checkNullString(tarSheetId))
				&& !"".equals(StringUtils.checkNullString(tarBaseSchema)))
		{
			List<WfAttachment> wfattlst = getWfAttachment(srcSheetId, srcProcessId, srcFieldId);
			String savePath = null; //附件的保存路径
			long createtime = System.currentTimeMillis()/1000; //拷贝时间
			SwfuploadUtil sfu = new SwfuploadUtil();
			int len = 0;
			if(wfattlst!=null)
			{
				len = wfattlst.size();
				if(len>0)
				{
					String prefix = Constants.WORKSHEET_UPLOAD_PATH;
					if(prefix != null && !prefix.trim().equals("")) {
						savePath = prefix + "/" + tarBaseSchema + "/" + TimeUtils.getCurrentDate("yyyy-MM-dd");
					} else {
						savePath = "common" + "/workflow_attachment/" + tarBaseSchema + "/" + TimeUtils.getCurrentDate("yyyy-MM-dd");
					}
				}
			}
			WfAttachment wfatt;
			String temp_attachid;
			for(int i=0;i<len;i++)
			{
				wfatt = wfattlst.get(i);
				temp_attachid = wfatt.getAttachId();//获得附件ID
				Attachment att = attachmentDao.get(temp_attachid);
				if(att!=null)
				{
					temp_attachid = depCopyAttachment(att, savePath, createtime, createrId, tarSheetId, sfu);
					if(temp_attachid!=null)
					{
						WfAttachment newWfAtt = new WfAttachment();
						newWfAtt.setAttachId(temp_attachid);
						newWfAtt.setFieldId(tarFieldId);
						newWfAtt.setProcessId(tarProcessId==null?tarSheetId:tarProcessId);
						newWfAtt.setSheetId(tarSheetId);
						save(newWfAtt);
						copyCounter++;
					}
				}
			}
		}
		return copyCounter;
	}
	//拷贝附件文件
	private String depCopyAttachment(Attachment att,String newPath,long current
			,String createrId,String sheetId,SwfuploadUtil sfu)
	{
		String newAttId = null;
		try {
			InputStream srcIns = sfu.download(new SwfDoloadBean(null,att.getRealname(),att.getPath()));
			if(srcIns!=null)
			{
				Attachment newAtt = att.clone();
				newAtt.setPid(null);
				newAtt.setPath(newPath);
				newAtt.setRealname(UUIDGenerator.getUUIDoffSpace()+"."+newAtt.getType());
				newAtt.setCreater(createrId);
				newAtt.setCreatetime(current);
				newAtt.setLastmodifytime(current);
				newAtt.setRelationcode(sheetId);
				newAtt.setRemark(att.getRemark());
				attachmentDao.save(newAtt);
				sfu.upload(srcIns, new SwfDoloadBean(null,newAtt.getRealname(),newPath));
				newAttId = newAtt.getPid();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newAttId;
	}

	public void setAttachmentDao(IDao<Attachment> attachmentDao) {
		this.attachmentDao = attachmentDao;
	}

}
