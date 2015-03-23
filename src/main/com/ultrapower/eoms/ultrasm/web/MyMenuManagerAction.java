package com.ultrapower.eoms.ultrasm.web;

import com.ultrapower.eoms.common.core.util.Internation;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.util.UUIDGenerator;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ultrasm.RecordLog;
import com.ultrapower.eoms.ultrasm.model.MenuInfo;
import com.ultrapower.eoms.ultrasm.model.MyMenu;
import com.ultrapower.eoms.ultrasm.service.MenuManagerService;
import com.ultrapower.eoms.ultrasm.service.MyMenuManagerService;

/**
 * 我的菜单模块管理
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version Jul 14, 2010 11:36:25 AM
 */
public class MyMenuManagerAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	private MenuManagerService menuManagerService;
	private MyMenuManagerService mymenuManagerService;
	private String menuid;
	private String myMenuId;
	private MenuInfo menuInfo;
	private MyMenu myMenu;
	private String myMenuParentid;
	private String myMenuNodename;
	
	/**
	 * 返回我的菜单配置页面
	 * @return
	 */
	public String myMenuManager()
	{
		return this.findForward("myMenuFrame");
	}
	
	/**
	 * 获取菜单节点信息：菜单管理右侧窗口展示 
	 * @return
	 */
	public String myMenu()
	{
		myMenu = mymenuManagerService.getMyMenuByID(myMenuId);
		if(myMenu != null)
		{
			myMenuNodename = "";
			if(!"0".equals(myMenu.getParentid()))
			{
				MyMenu parentMenu = mymenuManagerService.getMyMenuByID(myMenu.getParentid());
				if(parentMenu != null)
					myMenuNodename = parentMenu.getNodename();
			}
		}
		if(!StringUtils.checkNullString(this.getRequest().getParameter("message")).equals(""))
		{
			String message = this.getRequest().getParameter("message");
			String parentId = this.getRequest().getParameter("parentId");
			this.getRequest().setAttribute("message", message);
			this.getRequest().setAttribute("parentId", parentId);
		}
		return this.findForward("myMenu_right");
	}
	
	public String myMenuSave()
	{
		if(myMenu != null)
		{
			UserSession userSession = this.getUserSession();
			String userpid = "";
			if(userSession != null)
				userpid = userSession.getPid();
			long currentTime = TimeUtils.getCurrentTime();
			//设置最后修改人和最后修改时间
			myMenu.setUserid(userpid);
			myMenu.setMenuid(UUIDGenerator.getUUIDoffSpace());
			myMenu.setLastmodifier(userpid);
			myMenu.setLastmodifytime(currentTime);
			if("".equals(StringUtils.checkNullString(myMenu.getPid())))//如果不存在节点ID则为添加，否则为修改
			{
				//添加需设置创建人和创建时间
				myMenu.setCreater(userpid);
				myMenu.setCreatetime(currentTime);
				myMenuId = mymenuManagerService.addMymenu(myMenu);
			}
			else
			{
				myMenuId = mymenuManagerService.updateMymenu(myMenu);
			}
		}
		this.getRequest().setAttribute("message","success");
		String parentId = StringUtils.checkNullString(this.getRequest().getParameter("myMenu.parentid"));
		this.getRequest().setAttribute("parentId", parentId);
		return this.findForward("myMenu_right");
	}
	
	public String myMenuDel()
	{
		UserSession userSession = this.getUserSession();
		String userId = StringUtils.checkNullString(userSession.getPid());
		myMenu = mymenuManagerService.getMyMenuByID(myMenuId);
		mymenuManagerService.deleteMyMenuByID(myMenuId);
		return this.findRedirectPar("myMenu.action?message=success&parentId="+this.getRequest().getParameter("parentId"));
	}
	
	public String myMenuAdd(){
		menuInfo = menuManagerService.getMenuByID(menuid);
		return SUCCESS;
	}
	
	public String myMenuFolderAdd(){
		return this.findForward("myMenuFolderAdd");
	}
	
	public String addMyMenu(){
		if(menuInfo!=null){
			UserSession userSession = (UserSession) this.getSession().getAttribute("userSession");
			myMenu = new MyMenu();
			myMenu.setUserid(userSession.getPid());
			myMenu.setMenuid(menuInfo.getPid());
			myMenu.setNodename(menuInfo.getNodename());
			if(!"".equals(StringUtils.checkNullString(myMenuParentid))){
				myMenu.setParentid(myMenuParentid);
			}else{
				myMenu.setParentid("0");
			}
			if(!"".equals(StringUtils.checkNullString(menuInfo.getNodeurl()))){
				myMenu.setNodetype("2");
			}else{
				myMenu.setNodetype("1");
			}
			myMenu.setNodeurl(menuInfo.getNodeurl());
			myMenu.setStatus(menuInfo.getStatus());
			myMenu.setNodemark(menuInfo.getNodemark());
			myMenu.setCreater(userSession.getPid());
			myMenu.setCreatetime(TimeUtils.getCurrentTime());
			myMenu.setLastmodifier(userSession.getPid());
			myMenu.setLastmodifytime(TimeUtils.getCurrentTime());
			mymenuManagerService.addMymenu(myMenu);
		}
		
		this.getRequest().setAttribute("parafresh", true);
		this.getRequest().setAttribute("returnMessage",  Internation.language("com_msg_saveSuccess")+"!");
		return "refreshParent";
	}

	public String addMyMenuFolder(){
		UserSession userSession = (UserSession) this.getSession().getAttribute("userSession");
		myMenu = new MyMenu();
		myMenu.setUserid(userSession.getPid());
		myMenu.setMenuid(UUIDGenerator.getUUIDoffSpace());
		myMenu.setNodename(myMenuNodename);
		if(!"".equals(StringUtils.checkNullString(myMenuParentid))){
			myMenu.setParentid(myMenuParentid);
		}else{
			myMenu.setParentid("0");
		}
		myMenu.setNodetype("1");
		myMenu.setStatus(new Long(1));
		myMenu.setCreater(userSession.getPid());
		myMenu.setCreatetime(TimeUtils.getCurrentTime());
		myMenu.setLastmodifier(userSession.getPid());
		myMenu.setLastmodifytime(TimeUtils.getCurrentTime());
		mymenuManagerService.addMymenu(myMenu);
		
		this.getRequest().setAttribute("parafresh", true);
		this.getRequest().setAttribute("returnMessage",  Internation.language("com_msg_saveSuccess")+"!");
		return "refreshParent";
	}
	
	public void setMenuManagerService(MenuManagerService menuManagerService) {
		this.menuManagerService = menuManagerService;
	}

	public void setMymenuManagerService(MyMenuManagerService mymenuManagerService) {
		this.mymenuManagerService = mymenuManagerService;
	}

	public String getMyMenuId() {
		return myMenuId;
	}

	public void setMyMenuId(String myMenuId) {
		this.myMenuId = myMenuId;
	}

	public String getMenuid() {
		return menuid;
	}

	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}

	public MenuInfo getMenuInfo() {
		return menuInfo;
	}

	public void setMenuInfo(MenuInfo menuInfo) {
		this.menuInfo = menuInfo;
	}

	public MyMenu getMyMenu() {
		return myMenu;
	}

	public void setMyMenu(MyMenu myMenu) {
		this.myMenu = myMenu;
	}

	public String getMyMenuParentid() {
		return myMenuParentid;
	}

	public void setMyMenuParentid(String myMenuParentid) {
		this.myMenuParentid = myMenuParentid;
	}

	public String getMyMenuNodename() {
		return myMenuNodename;
	}

	public void setMyMenuNodename(String myMenuNodename) {
		this.myMenuNodename = myMenuNodename;
	}
	
}
