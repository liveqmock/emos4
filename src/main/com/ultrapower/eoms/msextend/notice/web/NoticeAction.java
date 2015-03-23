package com.ultrapower.eoms.msextend.notice.web;

import java.util.HashMap;
import java.util.Map;

import com.ultrapower.eoms.common.core.util.Internation;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.msextend.notice.model.NoticeInfo;
import com.ultrapower.eoms.msextend.notice.service.INoticeService;


@SuppressWarnings("serial")
public class NoticeAction extends BaseAction {
	
	private NoticeInfo noticeInfo;
	
	private INoticeService noticeManagerService;
	
	//标记页面操作的通知的uuid
	private String noticeId ;
	
	private String delIds;
	
	public String createNotice(){
		return SUCCESS;
	}
	
	/**
	 * 保存公告
	 * @return
	 */
	public String saveNotice(){
		if(noticeInfo.getPid() == null || "".equals(noticeInfo.getPid())){
			noticeInfo.setPid(null);
			UserSession userSession = this.getUserSession();
			String userId = userSession.getPid();
			String userFullName = userSession.getFullName();
			noticeInfo.setCreatorId(userId);
			noticeInfo.setCreatorName(userFullName);
		}
		
		String returnMessage = Internation.language("com_msg_saveErr")+"!";
		if(noticeManagerService.saveOrUpdateNotice(noticeInfo))
			returnMessage = Internation.language("com_msg_saveSuccess")+"!";
		String parafresh = "true";
		this.getRequest().setAttribute("parafresh", parafresh);
		this.getRequest().setAttribute("returnMessage", returnMessage);
		return "refresh";
	}
	
	/**
	 * 修改一条公告
	 * @return
	 */
	public String editNotice(){
		noticeInfo = noticeManagerService.getNotice(noticeId);
		return SUCCESS;
	}
	
	/**
	 * 查看公告
	 * @return
	 */
	public String viewNotice(){
		noticeInfo = noticeManagerService.getNotice(noticeId,this.getUserSession().getPid());
		return SUCCESS;
	}
	
	/**
	 * 所有公告
	 * @return
	 */
	public String listNotice(){
		long currentTime = TimeUtils.getCurrentTime();
		this.getRequest().setAttribute("currentTime", currentTime);
		return SUCCESS;
	}
	
	/**
	 * 已删除的公告
	 * @return
	 */
	public String listDelNotice(){
		long currentTime = TimeUtils.getCurrentTime();
		this.getRequest().setAttribute("currentTime", currentTime);
		return SUCCESS;
	}
	
	/**
	 * 自己的公告
	 * @return
	 */
	public String myListNotice(){
		long currentTime = TimeUtils.getCurrentTime();
		this.getRequest().setAttribute("currentTime", currentTime);
		
		UserSession userSession = this.getUserSession();
		String userId = userSession.getPid();
		
		Map map = new HashMap();
		map.put("creatorid", userId);
		this.getRequest().setAttribute("valuemap",map);
		return SUCCESS;
	}
	
	/**
	 * 删除通知
	 * @return
	 */
	public String delNotice(){
		noticeManagerService.delNotice(delIds);
		return SUCCESS;
	}

	public NoticeInfo getNoticeInfo() {
		return noticeInfo;
	}

	public void setNoticeInfo(NoticeInfo noticeInfo) {
		this.noticeInfo = noticeInfo;
	}

	public INoticeService getNoticeManagerService() {
		return noticeManagerService;
	}

	public void setNoticeManagerService(INoticeService noticeManagerService) {
		this.noticeManagerService = noticeManagerService;
	}

	public String getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}

	public String getDelIds() {
		return delIds;
	}

	public void setDelIds(String delIds) {
		this.delIds = delIds;
	}
}
