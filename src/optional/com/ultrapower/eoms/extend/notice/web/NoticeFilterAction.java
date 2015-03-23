package com.ultrapower.eoms.extend.notice.web;

import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.extend.notice.manager.NoticeFilterManager;
import com.ultrapower.eoms.extend.notice.model.NoticeFilter;


public class NoticeFilterAction extends BaseAction {
	private NoticeFilter noticeFilter;
	private NoticeFilterManager noticeFilterService;
	private String loginName;
	
	/**
	 * 
	 * @return
	 */
	public String noticeFilterList(){
		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 */
	public String noticeFilter() {
		return SUCCESS;
	}
	/**
	 * @return 返回信息页面
	 */
	public String noticeFilterInfo(){
		String saveResult = StringUtils.checkNullString(this.getRequest().getParameter("saveResult"));
		String baseSchema = StringUtils.checkNullString(this.getRequest().getParameter("baseSchema"));
		UserSession userSession = this.getUserSession();
		if(userSession != null) {
			loginName = userSession.getLoginName();
		}
		noticeFilter = noticeFilterService.getNoticeFilter(loginName, baseSchema);
		if(noticeFilter == null) {
			noticeFilter = new NoticeFilter();
			noticeFilter.setBusinesstype("form");
			noticeFilter.setBusinessmark(baseSchema);
			noticeFilter.setUsertype("1");
			noticeFilter.setUsermark(loginName);
		}
		this.getRequest().setAttribute("saveResult", saveResult);
		return SUCCESS;
	}
	/**
	 * 
	 * @return 保存信息页面
	 */
	public String noticeFilterSave(){
		String saveResult = "true";
		if(noticeFilter!=null){		
			noticeFilter.setUpdatetime(TimeUtils.getCurrentTime());
			if (noticeFilter.getPid() == null || "".equals(noticeFilter.getPid())) {
				noticeFilter.setCreatetime(TimeUtils.getCurrentTime());
			}
			String nfid = noticeFilterService.save(noticeFilter);
			if("".equals(StringUtils.checkNullString(nfid)))
				saveResult = "false";
		}
		String baseSchema = "";
		if(noticeFilter != null) {
			loginName = noticeFilter.getUsermark();
			baseSchema = noticeFilter.getBusinessmark();
		}
		return this.findRedirectPar("noticeFilterInfo.action?saveResult="+saveResult+"&loginName="+loginName+"&baseSchema="+baseSchema);
	}
	
	public String noticeFilterDel(){
		return "";
	}
	
	public NoticeFilter getNoticeFilter() {
		return noticeFilter;
	}
	public void setNoticeFilter(NoticeFilter noticeFilter) {
		this.noticeFilter = noticeFilter;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public NoticeFilterManager getNoticeFilterService() {
		return noticeFilterService;
	}

	public void setNoticeFilterService(NoticeFilterManager noticeFilterService) {
		this.noticeFilterService = noticeFilterService;
	}

}
