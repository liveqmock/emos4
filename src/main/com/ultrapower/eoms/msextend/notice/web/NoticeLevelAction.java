package com.ultrapower.eoms.msextend.notice.web;

import com.ultrapower.eoms.msextend.notice.model.NoticeLevelManage;
import com.ultrapower.eoms.msextend.notice.service.INoticeLevelService;
import com.ultrapower.eoms.msextend.notice.service.INoticeLevelTreeService;

import com.ultrapower.eoms.common.core.util.Internation;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;


@SuppressWarnings("serial")
public class NoticeLevelAction extends BaseAction {
	
	private NoticeLevelManage noticeLevelManage;
	private String pid;
	private String noticeLevelStr;
	private String selectUser;
	
	private String delIds;
	
	private INoticeLevelService noticeLevelService;
	// 字典表
	private INoticeLevelTreeService noticeLevelTreeService;
	
	
	public String createNoticeLevel(){
		return SUCCESS;
	}
	
	/**
	 * 保存公告级别信息
	 * @return
	 */
	public String saveNoticeLevel(){
		if(noticeLevelManage.getPid() == null || "".equals(noticeLevelManage.getPid())){
			UserSession userSession = this.getUserSession();
			String userId = userSession.getPid();
			String userFullName = userSession.getFullName();
			noticeLevelManage.setCreatorId(userId);
			noticeLevelManage.setCreatorName(userFullName);
		}
		
		String returnMessage = Internation.language("com_msg_saveErr")+"!";
		if(noticeLevelService.saveOrUpdateNoticeLevel(noticeLevelManage,selectUser))
			returnMessage = Internation.language("com_msg_saveSuccess")+"!";
		String parafresh = "true";
		this.getRequest().setAttribute("parafresh", parafresh);
		this.getRequest().setAttribute("returnMessage", returnMessage);
		return "refresh";
	}
	
	/**
	 * 删除通知
	 * @return
	 */
	public String delNoticeLevel(){
		noticeLevelService.delNoticeLevel(delIds);
		return SUCCESS;
	}
	
	/**
	 * 所有配置
	 * @return
	 */
	public String listNoticeLevel(){
		long currentTime = TimeUtils.getCurrentTime();
		this.getRequest().setAttribute("currentTime", currentTime);
		return SUCCESS;
	}
	
	/**
	 * 公告级别配置
	 * @return
	 */
	public String noticeLevelFrame()
	{
		return SUCCESS;
	}
	
	public String noticeLevelLeft(){
		return SUCCESS;
	}
	
	public void getLevelTree()
	{
		String dtcode = this.getRequest().getParameter("dtcode");
		this.renderXML(noticeLevelTreeService.getLevelTreeXml(id, dtcode));
	}

	public NoticeLevelManage getNoticeLevelManage() {
		return noticeLevelManage;
	}

	public void setNoticeLevelManage(NoticeLevelManage noticeLevelManage) {
		this.noticeLevelManage = noticeLevelManage;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public INoticeLevelService getNoticeLevelService() {
		return noticeLevelService;
	}

	public void setNoticeLevelService(INoticeLevelService noticeLevelService) {
		this.noticeLevelService = noticeLevelService;
	}

	public INoticeLevelTreeService getNoticeLevelTreeService() {
		return noticeLevelTreeService;
	}

	public void setNoticeLevelTreeService(
			INoticeLevelTreeService noticeLevelTreeService) {
		this.noticeLevelTreeService = noticeLevelTreeService;
	}

	public String getNoticeLevelStr() {
		return noticeLevelStr;
	}

	public void setNoticeLevelStr(String noticeLevelStr) {
		this.noticeLevelStr = noticeLevelStr;
	}

	public String getSelectUser() {
		return selectUser;
	}

	public void setSelectUser(String selectUser) {
		this.selectUser = selectUser;
	}

	public String getDelIds() {
		return delIds;
	}

	public void setDelIds(String delIds) {
		this.delIds = delIds;
	}
}
