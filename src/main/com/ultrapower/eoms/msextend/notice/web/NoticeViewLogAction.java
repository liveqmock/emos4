package com.ultrapower.eoms.msextend.notice.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.msextend.notice.model.NoticeInfo;
import com.ultrapower.eoms.msextend.notice.service.INoticeService;
import com.ultrapower.eoms.msextend.notice.service.INoticeViewLogService;
import com.ultrapower.eoms.msextend.notice.service.INoticeViewLogTreeService;
import com.ultrapower.accredit.util.GetUserUtil;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.common.sheettag.common.PageInfo;
import com.ultrapower.eoms.ultrasm.model.DepInfo;
import com.ultrapower.eoms.ultrasm.model.UserInfo;
import com.ultrapower.eoms.ultrasm.service.DepManagerService;
import com.ultrapower.eoms.ultrasm.service.UserManagerService;


@SuppressWarnings("serial")
public class NoticeViewLogAction extends BaseAction {
	
	private INoticeViewLogTreeService noticeViewLogTreeService;
	private INoticeService noticeManagerService;
	private INoticeViewLogService noticeViewLogService;
	private List<NoticeInfo> noticeList;
	private UserManagerService userManagerService;
	private DepManagerService depManagerService;
	private DepInfo depInfo;
	private PageInfo pageInfo;
	private String page;
	
	

	/**
	 * 所有配置
	 * @return
	 */
	public String listNoticeViewLog(){
		UserSession userSession = this.getUserSession();
		String userId = userSession.getPid();
		Map map = new HashMap();
		map.put("userid", userId);
		map.put("noticetype", "1");
		this.getRequest().setAttribute("valuemap",map);
		return SUCCESS;
	}
	
	/**
	 * 流程平台首页公告显示
	 * @return
	 */
	public String noticeView(){
		UserSession userSession = this.getUserSession();
		String userId = userSession.getPid();
		String groupId = userSession.getGroupId(); //组ID,多个组以,号隔开
		String depId = userSession.getDepId(); //部门ID
		Map<String, String> map = new HashMap<String, String>();
		String personDgsIncludeParent = getDgsIncludeParent(groupId,depId);  //得到人的部门和组以及父级部门和组,以,号进行分隔
		map.put("groupid", groupId);
		map.put("userid", userId);
		map.put("noticetype", "1");
		map.put("noticeStatus", "1");
		map.put("noticescopename", personDgsIncludeParent);
		this.getRequest().setAttribute("valuemap",map);
		return SUCCESS;
	}
	
	/**
	 * 得到人的部门和组以及父级部门和组,以,号进行分隔
	 * @param groupId
	 * @param depId
	 * @return
	 */
	public String getDgsIncludeParent(String groupId, String depId) {
		String personDetpAndGroup = ""; //存储人的部门和组,以,号进行分隔
		StringBuilder personDgsIncludeParent = new StringBuilder(); //存储人的部门和组以及父级部门和组,以,号进行分隔
		if(!"".equals(groupId)&&groupId !=null)
		{
			if(!"".equals(depId)&&depId !=null)
			{
				personDetpAndGroup = groupId+","+depId;
			}
			else
			{
				personDetpAndGroup = groupId;
			}
		}
		else if("".equals(groupId)|| groupId ==null)
		{
			personDetpAndGroup = groupId;
		}
		
		String pdagArray[]=personDetpAndGroup.split(",");   
		for(String stemp:pdagArray){  
			depInfo = depManagerService.getDepByID(stemp);
			String depFullname = depInfo.getDepfullname();
			depFullname =getParentDeptsAndGroups(depFullname);
			personDgsIncludeParent = personDgsIncludeParent.append(depFullname).append(",");
		}  
		
		return personDgsIncludeParent.toString().substring(0,personDgsIncludeParent.length()-1);
	}

	/**
	 * 通过部门或组的fullname,查找所包含的上级部门,返回字符串格式为"...上级部门,当前部门,"
	 * @param depFullname
	 * @return conditionSql
	 */
	private String getParentDeptsAndGroups(String depFullname) {
		StringBuilder parentDeptsAndGroup = new StringBuilder();  //存储单个父级部门或组
		StringBuilder  parentDeptsAndGroups = new StringBuilder(); //存储人所在的父级部门和组,以,号进行分隔
		String perInCdArray[] = depFullname.split("\\.");
		for (int i = 0; i < perInCdArray.length; i++) {
			parentDeptsAndGroup = parentDeptsAndGroup.append(perInCdArray[i]).append(".");
			parentDeptsAndGroups = parentDeptsAndGroups.append(parentDeptsAndGroup.toString()).append(",");
		}
		return parentDeptsAndGroups.toString().replace(".,", ",");
	}

	
	/**
	 * 值班平台公告显示
	 * @return
	 */
	public String dutynoticeView(){
		UserInfo userInfo = this.getUserModelSession();
		if(userInfo == null){
			String userName = GetUserUtil.getUsername();
			userInfo = userManagerService.getUserByLoginName(userName);
			this.getSession().setAttribute("userModelSession", userInfo);
		}
		Long count = noticeViewLogService.getCount(userInfo.getPid());
		pageInfo = new PageInfo(count, 1);
		if (page != null && !"".equals(page)) {
			pageInfo.setPage(new Integer(page).intValue());
		}
		 noticeList = noticeViewLogService.dutynoticeView(userInfo.getPid(),pageInfo);
		return SUCCESS;
	}
	
	/**
	 * 弹出框
	 * @return
	 */
	public void popView(){
		UserSession userSession = this.getUserSession();
		String userId = userSession.getPid();
		
		Map map = new HashMap();
		map.put("userid", userId);
		map.put("noticeStatus", "1");
		map.put("isview", "0");
		map.put("noticetype", "1");
		this.getRequest().setAttribute("valuemap",map);
		
		String str = noticeViewLogService.getNoticePopView(this.getUserSession().getPid());
		renderHtml(str);
	}
	
	/**
	 * 不再显示弹出框
	 */
	public void unView(){
		UserSession userSession = this.getUserSession();
		String userId = userSession.getPid();
		noticeViewLogService.allToRead(userId);
	}
	
	
	public String noticeViewLogFrame()
	{
		return SUCCESS;
	}
	
	public String noticeViewLogLeft(){
		return SUCCESS;
	}
	
	public void getViewLogTree()
	{
		String dtcode = this.getRequest().getParameter("dtcode");
		this.renderXML(noticeViewLogTreeService.getViewLogTreeXml(id, dtcode));
	}

	public INoticeViewLogTreeService getNoticeViewLogTreeService() {
		return noticeViewLogTreeService;
	}

	public void setNoticeViewLogTreeService(
			INoticeViewLogTreeService noticeViewLogTreeService) {
		this.noticeViewLogTreeService = noticeViewLogTreeService;
	}

	public INoticeViewLogService getNoticeViewLogService() {
		return noticeViewLogService;
	}

	public void setNoticeViewLogService(INoticeViewLogService noticeViewLogService) {
		this.noticeViewLogService = noticeViewLogService;
	}
	
	public List<NoticeInfo> getNoticeList() {
		return noticeList;
	}

	public void setNoticeList(List<NoticeInfo> noticeList) {
		this.noticeList = noticeList;
	}
	
	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public UserManagerService getUserManagerService() {
		return userManagerService;
	}

	public void setUserManagerService(UserManagerService userManagerService) {
		this.userManagerService = userManagerService;
	}

	public DepInfo getDepInfo() {
		return depInfo;
	}

	public void setDepInfo(DepInfo depInfo) {
		this.depInfo = depInfo;
	}

	public INoticeService getNoticeManagerService() {
		return noticeManagerService;
	}


	public void setNoticeManagerService(INoticeService noticeManagerService) {
		this.noticeManagerService = noticeManagerService;
	}

	public DepManagerService getDepManagerService() {
		return depManagerService;
	}

	public void setDepManagerService(DepManagerService depManagerService) {
		this.depManagerService = depManagerService;
	}

}
