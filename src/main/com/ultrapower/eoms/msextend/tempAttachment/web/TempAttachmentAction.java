package com.ultrapower.eoms.msextend.tempAttachment.web;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.core.util.Internation;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.msextend.tempAttachment.model.TempAttachment;
import com.ultrapower.eoms.msextend.tempAttachment.service.ITempAttachmentService;
import com.ultrapower.eoms.ultrasm.model.Attachment;
import com.ultrapower.eoms.ultrasm.model.UserInfo;
import com.ultrapower.eoms.ultrasm.service.AttachmentManagerService;
import com.ultrapower.eoms.ultrasm.service.UserManagerService;
import com.ultrapower.eoms.workflow.sort.web.WfSortTreeImpl;
import com.ultrapower.eoms.workflow.util.PageLimitUtil;
import com.ultrapower.workflow.bizconfig.sort.IWfSortManager;
import com.ultrapower.workflow.client.WorkFlowServiceClient;
import com.ultrapower.workflow.configuration.sort.model.WfSort;
import com.ultrapower.workflow.configuration.sort.model.WfType;

public class TempAttachmentAction extends BaseAction {

	private ITempAttachmentService tempAttachmentService;
	
	private String wfSortId;
	
	private String pid;
	
	private String delIds;
	
	private String message;
	
	/**
	 * 附件关联ID
	 */
	private String attchmentGroupId;
	
	 /**
     * 流程类型list
     */
    private List<WfType> wfTypeList;
    
    /**
     * 流程分类service对象
     */
    private IWfSortManager ver;
    
    private WfSortTreeImpl wfSortTreeImpl;
    
    private TempAttachment tempAttachment;
    
    private List<TempAttachment> tempAttachmentList;
    
    private AttachmentManagerService attachmentManagerService;
    
    private UserManagerService userManagerService;
    
    List<Attachment> attachments;
    
    private String baseSchema;
	private String baseID;
	private String relateBaseSchema;
	private String relateBaseID;
	private String belongvalue;
	/**
	 * 进入流程分类管理主页面
	 */
	public String tempAttaFrame() {
		return SUCCESS;
	}
	
	public String tempAttaMain() {
		return SUCCESS;
	}

	public String tempAttaLeftTree() {
		return SUCCESS;
	}
	
	public String listTempAttachment(){
		
		ver = WorkFlowServiceClient.clientInstance().getSortService();
		tempAttachmentList = new ArrayList<TempAttachment>();
		List<WfSort> list = wfSortTreeImpl.getAllChildSort(wfSortId);
		
		this.tempAttachmentList.addAll(tempAttachmentService.getTempAttachmentListBySortId(wfSortId));

		for (WfSort sort : list)
		{
			this.tempAttachmentList.addAll(tempAttachmentService.getTempAttachmentListBySortId(sort.getId()));
		}
		tempAttachmentList = PageLimitUtil.pageLimit(tempAttachmentList);
		return SUCCESS;
	}
	
	public String createTempAttachment(){
		tempAttachment = new TempAttachment();
		
		UserSession userSession = this.getUserSession();
		tempAttachment.setCreater(userSession.getFullName());
		tempAttachment.setCreateTime(TimeUtils.getCurrentTime());
		
		ver = WorkFlowServiceClient.clientInstance().getSortService();
		wfTypeList = new ArrayList<WfType>();
		List<WfSort> list = wfSortTreeImpl.getAllChildSort(wfSortId);
		try
		{
		    this.wfTypeList.addAll(ver.getWfTypeBySortId(wfSortId));
		} catch (RemoteException e)
		{
		    e.printStackTrace();
		}

		for (WfSort sort : list)
		{
		    try
		    {
			this.wfTypeList.addAll(ver.getWfTypeBySortId(sort.getId()));
		    } catch (RemoteException e)
		    {
			e.printStackTrace();
		    }
		}
		//wfTypeList = PageLimitUtil.pageLimit(wfTypeList);
		return SUCCESS;
	}
	
	public String saveTempAttachment() throws RemoteException{
		ver = WorkFlowServiceClient.clientInstance().getSortService();
		if(tempAttachment.getPid() == null || "".equals(tempAttachment.getPid())){
			
			WfType wfType = ver.getWfTypeByCode(tempAttachment.getBaseSchema());
			tempAttachment.setSortid(wfType.getSortId());
			
			TempAttachment tempAtta =  tempAttachmentService.getTempAttachmentByBaseSchema(tempAttachment.getBaseSchema(),tempAttachment.getBelongvalue());
			
			if(tempAtta != null){
				return findRedirectPar("createTempAttachment.action?wfSortId="+wfSortId+"&message=exist");
			}
			
			tempAttachment.setPid(null);
		}
		
		UserSession userSession = this.getUserSession();
		tempAttachment.setLastModifier(userSession.getFullName());
		tempAttachment.setLastModifyTime(TimeUtils.getCurrentTime());
		
		String returnMessage = Internation.language("com_msg_saveErr")+"!";
		if(tempAttachmentService.saveOrUpdateTempAttachment(tempAttachment))
			returnMessage = Internation.language("com_msg_saveSuccess")+"!";
		String parafresh = "true";
		this.getRequest().setAttribute("parafresh", parafresh);
		this.getRequest().setAttribute("returnMessage", returnMessage);
		return "refresh";
	}
	
	
	public String editTempAttachment(){
		tempAttachment = tempAttachmentService.getTempAttachment(pid);
		return SUCCESS;
	}
	
	/**
	 * 删除通知
	 * @return
	 */
	public String delTempAttachment(){
		tempAttachmentService.delTempAttachment(delIds);
		return SUCCESS;
	}
	
	/**
	 * 根据attachGroupId获得下载列表(action)
	 * @return
	 */
	public String queryTempAttachmentDownList()
	{
		TempAttachment tempAtta = null;
//		if (null!=belongvalue&&!"".equals(belongvalue)) {
		tempAtta =tempAttachmentService.getTempAttachmentByBaseSchema(baseSchema,belongvalue);
//		}
//		if(null == tempAtta){
//			tempAtta =tempAttachmentService.getTempAttachmentByBaseSchema(baseSchema);
//		}
		if(tempAtta != null){
			attachments = attachmentManagerService.getAttachmentByRelation(tempAtta.getAttachmentId());
		}
		return SUCCESS;
	}
	
	/**
	 * 根据工单id查找工单上传的附件
	 * @return
	 */
	public String downFormAttach(){
		String getRelationCode = tempAttachmentService.getRelationCode(baseID,baseSchema,relateBaseID,relateBaseSchema);
		String[] sp = getRelationCode.split(",");
		for(int i=0;i<sp.length;i++){
			List<Attachment> attachs = attachmentManagerService.getAttachmentByRelation(sp[i]);
			if(attachs != null){
				if(attachments == null){
					attachments = new ArrayList<Attachment>();
				}
				attachments.addAll(attachs);
			}
		}
		// 转换用户id为用户全名
		if(attachments != null){
			for(int i=0;i<attachments.size();i++){
				Attachment attach = attachments.get(i);
				String userFullName = userManagerService.getUserNameByID(attach.getCreater());
				attach.setCreater(userFullName);
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 根据工单id查找工单上传的附件
	 * @return
	 */
	public String uploadAttatch(){
		attchmentGroupId = tempAttachmentService.getAttchmentGroupId(baseID,baseSchema);
		return SUCCESS;
	}

	public ITempAttachmentService getTempAttachmentService() {
		return tempAttachmentService;
	}

	public void setTempAttachmentService(ITempAttachmentService tempAttachmentService) {
		this.tempAttachmentService = tempAttachmentService;
	}

	public String getWfSortId() {
		return wfSortId;
	}

	public void setWfSortId(String wfSortId) {
		this.wfSortId = wfSortId;
	}

	public List<WfType> getWfTypeList() {
		return wfTypeList;
	}

	public void setWfTypeList(List<WfType> wfTypeList) {
		this.wfTypeList = wfTypeList;
	}

	public IWfSortManager getVer() {
		return ver;
	}

	public void setVer(IWfSortManager ver) {
		this.ver = ver;
	}

	public WfSortTreeImpl getWfSortTreeImpl() {
		return wfSortTreeImpl;
	}

	public void setWfSortTreeImpl(WfSortTreeImpl wfSortTreeImpl) {
		this.wfSortTreeImpl = wfSortTreeImpl;
	}

	public TempAttachment getTempAttachment() {
		return tempAttachment;
	}

	public void setTempAttachment(TempAttachment tempAttachment) {
		this.tempAttachment = tempAttachment;
	}

	public List<TempAttachment> getTempAttachmentList() {
		return tempAttachmentList;
	}

	public void setTempAttachmentList(List<TempAttachment> tempAttachmentList) {
		this.tempAttachmentList = tempAttachmentList;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getDelIds() {
		return delIds;
	}

	public void setDelIds(String delIds) {
		this.delIds = delIds;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public AttachmentManagerService getAttachmentManagerService() {
		return attachmentManagerService;
	}

	public void setAttachmentManagerService(
			AttachmentManagerService attachmentManagerService) {
		this.attachmentManagerService = attachmentManagerService;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public String getBaseSchema() {
		return baseSchema;
	}

	public void setBaseSchema(String baseSchema) {
		this.baseSchema = baseSchema;
	}

	public String getBaseID() {
		return baseID;
	}
	public void setBaseID(String baseID) {
		this.baseID = baseID;
	}

	public String getRelateBaseSchema() {
		return relateBaseSchema;
	}

	public void setRelateBaseSchema(String relateBaseSchema) {
		this.relateBaseSchema = relateBaseSchema;
	}

	public String getRelateBaseID() {
		return relateBaseID;
	}

	public void setRelateBaseID(String relateBaseID) {
		this.relateBaseID = relateBaseID;
	}

	public String getAttchmentGroupId() {
		return attchmentGroupId;
	}

	public void setAttchmentGroupId(String attchmentGroupId) {
		this.attchmentGroupId = attchmentGroupId;
	}

	public UserManagerService getUserManagerService() {
		return userManagerService;
	}

	public void setUserManagerService(UserManagerService userManagerService) {
		this.userManagerService = userManagerService;
	}

	public String getBelongvalue() {
		return belongvalue;
	}

	public void setBelongvalue(String belongvalue) {
		this.belongvalue = belongvalue;
	}
	
}
