package com.ultrapower.eoms.ultrasm.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.ultrapower.eoms.common.constants.Constants;
import com.ultrapower.eoms.common.constants.PropertiesUtils;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.rquery.RQuery;
import com.ultrapower.eoms.common.core.component.rquery.core.QueryCondition;
import com.ultrapower.eoms.common.core.component.xml.XmlParser;
import com.ultrapower.eoms.common.core.util.Internation;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.plugin.swfupload.utils.SwfDoloadBean;
import com.ultrapower.eoms.common.plugin.swfupload.utils.SwfuploadUtil;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ultrasm.RecordLog;
import com.ultrapower.eoms.ultrasm.model.Attachment;
import com.ultrapower.eoms.ultrasm.model.RoleInfo;
import com.ultrapower.eoms.ultrasm.model.RoleOrgShow;
import com.ultrapower.eoms.ultrasm.model.UserInfo;
import com.ultrapower.eoms.ultrasm.service.AttachmentManagerService;
import com.ultrapower.eoms.ultrasm.service.DepManagerService;
import com.ultrapower.eoms.ultrasm.service.DicManagerService;
import com.ultrapower.eoms.ultrasm.service.PwdManageService;
import com.ultrapower.eoms.ultrasm.service.RoleManagerService;
import com.ultrapower.eoms.ultrasm.service.UserManagerService;
import com.ultrapower.eoms.ultrasm.util.ResolvePwdManageCfg;
import com.ultrapower.eoms.ultrasm.util.UltraSmUtil;

public class UserManagerAction  extends BaseAction
{
	private List<UserInfo> userList;
	private UserManagerService userManagerService;
	private DepManagerService depManagerService;
	private RoleManagerService roleManagerService;
	private PwdManageService pwdManageService;
	private DicManagerService dicManagerService;
	private AttachmentManagerService attachmentManagerService;
	private UserInfo userInfo;
	private List<RoleInfo> roleList;
	private List<RoleOrgShow> roShowList;
	private String userID;
	private String ser_status;
	private String roleIdXml;
	private String groupIds;
	private String groupFullname;
	private String ptdepIds;

	private String sqlname;
	private Map<String,String> conditonMap;
	private String conditions;
	
	private String pid;
	
	/**
	 * 该方法用以得到所有处于启用状态的用户集合
	 * @return String
	 */
	public String userList()
	{
		return SUCCESS;
	}  
	
	public String getMyInfo()
	{
		UserSession userSession = this.getUserSession();
		if(userSession != null)
			userID = StringUtils.checkNullString(userSession.getPid());
		userInfo = userManagerService.getUserByID(userID);
		String image = "";
		if(userInfo != null)
		{
			//roleList = userManagerService.getRoleByUserID(userID);
			roShowList = userManagerService.getRoleOrgByUserID(userID);
			UserInfo parent = userManagerService.getUserByID(userInfo.getCreater());
			if(parent!=null)
			{
				userInfo.setCreaterShow(parent.getFullname());
			}
			List<Attachment> attachmentList = attachmentManagerService.getAttachmentByRelation(userInfo.getImage());
			if(attachmentList != null && attachmentList.size() > 0)
			{
				image = StringUtils.checkNullString(attachmentList.get(attachmentList.size()-1).getRealname());
			}
		}
		this.getRequest().setAttribute("pwdmanage", Constants.PWD_MANAGE);
		this.getRequest().setAttribute("image", image);
		
		this.getRequest().setAttribute("systemVar", "1");
		return findForward("userSave");
	}
	
	public String addGroupUser()
	{
		String depid = StringUtils.checkNullString(this.getRequest().getParameter("depid"));
		if(!"".equals(depid))
		{
			userInfo = new UserInfo();
			userInfo.setDepid(depid);
			userInfo.setDepname(depManagerService.getDepNameByID(depid));
			this.getRequest().setAttribute("systemVar", "2");
		}
		return this.findForward("userSave");
	}
	
	/**
	 * 该方法用以在客户端点击用户登录名时得到编辑对象
	 * @return String
	 */
	public String userLoad()
	{
		userInfo = userManagerService.getUserByID(userID);
		String image = "";
		String isCanSave = "";
		if(userInfo != null)
		{
			UserSession userSession = this.getUserSession();
			if(userSession != null)
			{
				String loginname = userInfo.getLoginname();
				if("Demo".equals(loginname))
				{
					String sessionLoginname = userSession.getLoginName();
					if(!"Demo".equals(sessionLoginname))
					{
						isCanSave = "false";
					}
				}
			}
			roShowList = userManagerService.getRoleOrgByUserID(userID);
			UserInfo parent = userManagerService.getUserByID(userInfo.getCreater());
			if(parent!=null)
			{
				userInfo.setCreaterShow(parent.getFullname());
			}
			List<Attachment> attachmentList = attachmentManagerService.getAttachmentByRelation(userInfo.getImage());
			if(attachmentList != null && attachmentList.size() > 0)
			{
				image = StringUtils.checkNullString(attachmentList.get(attachmentList.size()-1).getRealname());
			}
		}
		this.getRequest().setAttribute("pwdmanage", Constants.PWD_MANAGE);
		this.getRequest().setAttribute("isCanSave", isCanSave);
		this.getRequest().setAttribute("image", image);
		this.getRequest().setAttribute("systemVar", StringUtils.checkNullString(this.getRequest().getParameter("systemVar")));
		return findForward("userSave");
	}
	
	/**
	 * 该方法用来保存和添加用户
	 * @return String
	 */
	@SuppressWarnings("deprecation")
	public String userSave()
	{
		List groupIdList = null;
		List delGroupIdList = null;
		List ptdepIdList = null;
		List delPtdepIdList = null;
		if(groupIds != null && !";".equals(groupIds))
		{
			String[] groupId = groupIds.split(";");
			groupIdList = "".equals(groupId[0])?null:UltraSmUtil.arrayToList(groupId[0].split(","));
			if(groupId.length > 1)
			{
				delGroupIdList = "".equals(groupId[1])?null:UltraSmUtil.arrayToList(groupId[1].split(","));
			}
		}
		if(ptdepIds != null && !";".equals(ptdepIds))
		{
			String[] ptdepId = ptdepIds.split(";");
			ptdepIdList = "".equals(ptdepId[0])?null:UltraSmUtil.arrayToList(ptdepId[0].split(","));
			if(ptdepId.length > 1)
			{
				delPtdepIdList = "".equals(ptdepId[1])?null:UltraSmUtil.arrayToList(ptdepId[1].split(","));
			}
		}
		roleIdXml = StringUtils.checkNullString(UltraSmUtil.thansToXml(roleIdXml));
		XmlParser xml = new XmlParser(roleIdXml,0);
		String roleIds = StringUtils.checkNullString(xml.getAttributeValue("table", "add"));
		String delRoleIds = StringUtils.checkNullString(xml.getAttributeValue("table", "del"));
		
		List roleIdList = "".equals(roleIds)?null:UltraSmUtil.arrayToList(roleIds.split(","));
		List delRoleIdList = "".equals(delRoleIds)?null:UltraSmUtil.arrayToList(delRoleIds.split(","));
		String userId = StringUtils.checkNullString(userInfo.getPid());
		if(userInfo != null)
		{
			UserSession userSession = (UserSession) this.getSession().getAttribute("userSession");
			String userpid = "";
			if(userSession != null)
				userpid = userSession.getPid();
			long currentTime = TimeUtils.getCurrentTime();
			if("".equals(userId))
			{
				userInfo.setCreater(userpid);
				userInfo.setCreatetime(currentTime);
				userInfo.setLastlogintime((long) 0);
			}
			userInfo.setLastmodifier(userpid);
			userInfo.setLastmodifytime(currentTime);
		}
		String returnMessage = Internation.language("com_msg_saveErr")+"!";
		String updatePwd = StringUtils.checkNullString(this.getRequest().getParameter("cryptPwd"));
		if("".equals(updatePwd))
		{
			updatePwd = "true";
		}
		if(userManagerService.saveUserInfo(userInfo, groupIdList, ptdepIdList, roleIdList, delGroupIdList, delPtdepIdList, delRoleIdList,updatePwd))
		{
			String userImage = StringUtils.checkNullString(userInfo.getImage());
			if(!"".equals(userImage))
			{
				List<Attachment> attList = attachmentManagerService.getAttachmentByRelation(userImage);
				if(attList != null && attList.size() > 1)
				{
					int nDel = attList.size()-1;
					userImage = attList.get(nDel).getRealname();
					attachmentManagerService.deleteAttachmentById(attList.get(nDel).getPid());
				}
//				String imagePath = this.getRequest().getRealPath("common/userimage");
//				String imagePath = getSession().getServletContext().getRealPath("common/userimage");
//				new File(imagePath+"/"+userImage).delete();
				SwfuploadUtil sfu = new SwfuploadUtil();
				sfu.deleteAttachment(new SwfDoloadBean(null,userImage,"sys_userimage"));
				sfu.close();
			}
			returnMessage = Internation.language("com_msg_saveSuccess")+"!";
		}
		String parafresh = "true";
		this.getRequest().setAttribute("parafresh", parafresh);
		this.getRequest().setAttribute("returnMessage", returnMessage);
		String systemVar = StringUtils.checkNullString(this.getRequest().getParameter("systemVar"));
		if("1".equals(systemVar))
			return this.findRedirect("getMyInfo");
		return "refresh";
	}
	
	public void getImage() throws IOException
	{
		
		String imageGroupId = StringUtils.checkNullString(this.getRequest().getParameter("imageGroupId"));
		String isExistImage = StringUtils.checkNullString(this.getRequest().getParameter("isExistImage"));
		List<Attachment> attachmentList = attachmentManagerService.getAttachmentByRelation(imageGroupId);
		String imageName = "";
		int attLen = 0;
		if(attachmentList != null)
			attLen = attachmentList.size();
		if(attLen > 0)
		{
			if(!"".equals(isExistImage))
				attLen--;
			for(int i=1;i<attLen;i++)
			{
				imageName = StringUtils.checkNullString(attachmentList.get(i).getRealname());
				attachmentManagerService.deleteAttachmentById(attachmentList.get(i).getPid());
//				String imagePath = this.getRequest().getRealPath("common/userimage");
//				String imagePath = SwfuploadUtil.getFullPathOfAttach("common/userimage");
//				new File(imagePath+"/"+imageName).delete();
				SwfuploadUtil sfu = new SwfuploadUtil();
				sfu.deleteAttachment(new SwfDoloadBean(null,imageName,"sys_userimage"));
				sfu.close();
			}
			imageName = StringUtils.checkNullString(attachmentList.get(0).getRealname()) + ";" + StringUtils.checkNullString(attachmentList.get(0).getRelationcode());
		}
		//PrintWriter out = this.getResponse().getWriter();
		//out.print(String.valueOf(imageName));
		this.renderText(imageName);
	}
	
	/**
	 * 获得用户头像流
	 */
	public void getUserImgStream(){
		String realName = getRequest().getParameter("realName");
		if("".equals(realName))
			return;
//		InputStream ins = SwfuploadUtil.getFileStream("sys_userimage", realName);
		SwfuploadUtil sfu = new SwfuploadUtil();
		InputStream ins = sfu.download(new SwfDoloadBean(null,realName,"sys_userimage"));
		if(ins==null)
			return;
		byte[] b = new byte[1024*5];
		OutputStream os = null;
		try {
			os = getResponse().getOutputStream();
			while(true){
				int i = ins.read(b);
				if(i==-1)
					break;
				os.write(b, 0, i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(os!=null)
					os.close();
				if(ins!=null)
					ins.close();
				sfu.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 该方法提供启用和停用用户的功能
	 * @return String
	 */
	public String transferStatus()
	{
		String ids = StringUtils.checkNullString(this.getRequest().getParameter("ids"));
		String transType = StringUtils.checkNullString(this.getRequest().getParameter("transType"));
		if(!"".equals(ids) && !"".equals(transType))
		{
			String[] targets = ids.split(",");
			List idls = UltraSmUtil.arrayToList(targets);
			if("inuse".equals(transType))
			{	
				userManagerService.setUserEnabled(idls);
			}
			else
			{
				userManagerService.setUserDisable(idls);
			}
		}
		return this.findForward("userList");
	}
	
	/**
	 * 该方法用来检查添加用户登录名的唯一性
	 * @return String
	 * @throws IOException 
	 */
	public void checkUnique() throws IOException
	{
		String loginName = StringUtils.checkNullString(this.getRequest().getParameter("loginName"));
		String result = String.valueOf(userManagerService.isUnique(loginName));
		PrintWriter out = this.getResponse().getWriter();
		out.print(result);
//		if(result)
//			this.renderText("true");
//		else
//			this.renderText("false");
	}
	
	public void pwdSarbanes() throws IOException
	{
		String loginName = StringUtils.checkNullString(this.getRequest().getParameter("loginName"));
		String result = "";
		if(Constants.PWD_MANAGE)
		{
			result = pwdManageService.isEnablePwd(loginName, this.getRequest().getParameter("pwd"));
		}
		PrintWriter out = this.getResponse().getWriter();
		out.print(result);
	}
	
	/**
	 * 获取角色列表信息
	 * @return
	 */
	public String selectRole()
	{
		UserSession userSession = (UserSession) this.getSession().getAttribute("userSession");
		String userId = "";
		if(userSession != null)
			userId = userSession.getPid();
		List<RoleInfo> lst = roleManagerService.getRoleByUserID(userId);
		StringBuffer roleids = new StringBuffer();
		Map map = new HashMap();
		if(lst != null)
		{
			for(RoleInfo role:lst)
				roleids.append(role.getRoledns()+",");
		}
		if(roleids.lastIndexOf(",")!=-1)
		{
			map.put("roledns", roleids);
			this.getRequest().setAttribute("valuemap", map);
		}
		else
		{
			map.put("roledns", "");
			this.getRequest().setAttribute("valuemap", map);
		}
		return SUCCESS;
	}
	
	public String userDel()
	{
		String ids = StringUtils.checkNullString(this.getRequest().getParameter("ids"));
		List idList = UltraSmUtil.arrayToList(ids.split(","));
		String isAudit = PropertiesUtils.getProperty("eoms.isaudit");//是否添加审计日志
		String userName = "";
		if("true".equals(isAudit))
		{
			userName = UltraSmUtil.getNameById("bs_t_sm_user", "fullname", idList);
		}
		boolean result = userManagerService.deleteUserByID(idList);
		if("true".equals(isAudit) && result && !"".equals(userName))
		{
			RecordLog.printOperateAuditLog("1", "10102", "删除了("+userName+")人员！");
		}
		String src = StringUtils.checkNullString(this.getRequest().getParameter("src"));
		if(!"".equals(src))
		{
			this.getRequest().setAttribute("depid", this.getRequest().getParameter("depid"));
			return this.findForward(src);
		}
		return this.findForward("userList");
	}
	
	/**
	 * 根据组id获得组内用户,返回json
	 */
	public void getUsersByGroupId(){
		renderText(JSONArray.fromObject(depManagerService.getUserByDepID(groupIds, false)).toString());
	}
	
	
	/**
	 * 根据组fullname获得组内用户,返回json
	 */
	
	public void getUsersByGroupFullname(){
		renderText(JSONArray.fromObject(userManagerService.getUsersByGroupFullname(groupFullname)).toString());
	}
	
	
	/**
	 * 根据登录名获得用户,返回json
	 */
	public void getUserByLoginname(){
		UserInfo user = userManagerService.getUserByLoginName(userID);
		user.setPosition(dicManagerService.getTextByValue("userposition", user.getPosition()));
		renderText(JSONObject.fromObject(user).toString());
	}
	
	/**
	 * 根据用户id获得用户loginName
	 */
	
	public void getLoginNameByPid(){
		String loginName = "";
		loginName = userManagerService.getLoginNameByPid(pid);
		renderText(loginName);
		
	}
	
	public void synchUserToRemedy()
	{
		userManagerService.synchUserToRemedy();
	}
	
	public void getPwdManageCfg()
	{
		if(Constants.PWD_MANAGE)
			ResolvePwdManageCfg.getPwdManageCfg();
	}
	
	public String userLoginInfoList()
	{
		this.getRequest().setAttribute("current", TimeUtils.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
		return "common";
	}
	
	public String visitsQueryList()
	{
		this.getRequest().setAttribute("current", TimeUtils.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
		return "common";
	}
	
	public String configSearchCondition()
	{
		UserSession userSession = (UserSession)this.getSession().getAttribute("userSession");
		conditions = userSession.getSearchCondition().get(this.sqlname);
		QueryCondition queryCondition = new QueryCondition(this.sqlname);
		conditonMap = queryCondition.getConditionConfig();
		
		return "common";
	}
	public String saveSearchCondition()
	{
		UserSession userSession = (UserSession)this.getSession().getAttribute("userSession");
		this.userManagerService.saveSearchConditionConfig(userSession.getLoginName(),sqlname, conditions);
		userSession.getSearchCondition().put(sqlname,conditions);
		return "close";
	}
	
	
	//add by zhangliang
	private String sqlQuery;// 配置的查询sql
	
	public String getSqlQuery() {
		return sqlQuery;
	}

	public void setSqlQuery(String sqlQuery) {
		this.sqlQuery = sqlQuery;
	}
	private String paramKey;
	
	public String getParamKey() {
		return paramKey;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}
	/**
	 * 人员自动补全
	 * @throws IOException
	 */
	public void autoSearchExOutHtml() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
//		String paramKey = new String(ServletActionContext.getRequest().getParameter("q").getBytes("ISO-8859-1"), "UTF-8");
		String paramKey = java.net.URLDecoder.decode(ServletActionContext.getRequest().getParameter("q"),"UTF-8");
		if (paramKey == null || paramKey.trim().equals("") || sqlQuery == null
				|| sqlQuery.trim().equals("")) {
			return ;
		} else {
			paramKey = paramKey.toLowerCase();
		}
		HashMap<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("paramKey", paramKey);
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		RQuery rquery = new RQuery(sqlQuery, conditionMap);
		rquery.setPageSize(20);
		rquery.setPage(1);
		DataTable dt = rquery.getDataTable();
		for(Object o :dt.getRowList()){
			DataRow dr = (DataRow)o;
			Map rowmap = dr.getRowHashMap();
			Iterator i = rowmap.keySet().iterator();
			Map<String,Object> map = new HashMap<String, Object>();
			while(i.hasNext()){
				String key = (String) i.next();
				map.put(key.toLowerCase(), rowmap.get(key));
			}
			result.add(map);
		}
		
		for(int i=0;i<result.size();i++){
			StringBuilder sbHtml = new StringBuilder();
			Map map=new HashMap();
			map=result.get(i);
			sbHtml.append(map.get("value"));
			sbHtml.append("|");
			sbHtml.append(map.get("key"));
			sbHtml.append("|");
			sbHtml.append(map.get("depfullname"));
			response.getWriter().println(sbHtml.toString());
		}
	}
	/*
	 * 以下方法为属性的get/set方法
	 */
	public UserInfo getUserInfo() 
	{
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) 
	{
		this.userInfo = userInfo;
	}
	public void setUserID(String userID) 
	{
		this.userID = userID;
	}
	public List<UserInfo> getUserList() 
	{
		return userList;
	}
	public void setUserManagerService(UserManagerService userManagerService) 
	{
		this.userManagerService = userManagerService;
	}
	public void setDepManagerService(DepManagerService depManagerService) {
		this.depManagerService = depManagerService;
	}
	public void setAttachmentManagerService(AttachmentManagerService attachmentManagerService) {
		this.attachmentManagerService = attachmentManagerService;
	}
	
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public void setPwdManageService(PwdManageService pwdManageService) {
		this.pwdManageService = pwdManageService;
	}
	public String getUserID() 
	{
		return userID;
	}
	public void setSer_status(String ser_status) 
	{
		this.ser_status = ser_status;
	}
	public String getSer_status() 
	{
		return ser_status;
	}
	public List<RoleInfo> getRoleList() 
	{
		return roleList;
	}
	public List<RoleOrgShow> getRoShowList() {
		return roShowList;
	}
	public void setRoleManagerService(RoleManagerService roleManagerService) 
	{
		this.roleManagerService = roleManagerService;
	}
	public void setRoleIdXml(String roleIdXml) 
	{
		this.roleIdXml = roleIdXml;
	}
	public void setGroupIds(String groupIds) 
	{
		this.groupIds = groupIds;
	}
	
	public String getGroupFullname() {
		return groupFullname;
	}

	public void setGroupFullname(String groupFullname) {
		this.groupFullname = groupFullname;
	}

	public void setPtdepIds(String ptdepIds) 
	{
		this.ptdepIds = ptdepIds;
	}

	public String getSqlname()
	{
		return sqlname;
	}

	public void setSqlname(String sqlname)
	{
		this.sqlname = sqlname;
	}

	public Map<String, String> getConditonMap()
	{
		return conditonMap;
	}

	public void setConditonMap(Map<String, String> conditonMap)
	{
		this.conditonMap = conditonMap;
	}

	public String getConditions()
	{
		return conditions;
	}

	public void setConditions(String conditions)
	{
		this.conditions = conditions;
	}

	public void setDicManagerService(DicManagerService dicManagerService) {
		this.dicManagerService = dicManagerService;
	}
	
}
