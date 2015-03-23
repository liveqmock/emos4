package com.ultrapower.eoms.ultrasm.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONObject;

import com.ultrapower.eoms.common.constants.PropertiesUtils;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.rquery.RQuery;
import com.ultrapower.eoms.common.core.component.xml.XmlParser;
import com.ultrapower.eoms.common.core.util.Internation;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ultrasm.RecordLog;
import com.ultrapower.eoms.ultrasm.model.DepInfo;
import com.ultrapower.eoms.ultrasm.model.RoleInfo;
import com.ultrapower.eoms.ultrasm.model.UserInfo;
import com.ultrapower.eoms.ultrasm.service.DepManagerService;
import com.ultrapower.eoms.ultrasm.service.RoleManagerService;
import com.ultrapower.eoms.ultrasm.service.UserManagerService;
import com.ultrapower.eoms.ultrasm.util.UltraSmUtil;

public class DepManagerAction extends BaseAction 
{
	private UserManagerService userManagerService;
	private DepManagerService depManagerService;
	private RoleManagerService roleManagerService;
	private List<DepInfo> depList;
	private List<UserInfo> userList;
	private List<RoleInfo> roleList;
	private DepInfo depinfo;
	private String depID;
	private String roleIdXml;
	private String userIdXml;
	private String ser_status;
	private String sqlQuery;
	/**
	 * 获得部门列表
	 */
	public String depList()
	{
		//depList = depManagerService.getDepList(null);
		return SUCCESS;
	}
	
	public String dep_Frame()
	{
		return SUCCESS;
	}
	
	/**
	 * 装载某一个部门
	 */
	public String depLoad()
	{
		depinfo = depManagerService.getDepByID(depID);
		String assgineeName = "";
		if(depinfo != null)
		{
			DepInfo parent = depManagerService.getDepByID(depinfo.getParentid());
			if(parent!=null)
			{
				depinfo.setParentShow(parent.getDepname());
				this.getRequest().setAttribute("PdepType", parent.getDeptype());
			}
			String assginee = depinfo.getDepassginee();
			UserInfo user = userManagerService.getUserByLoginName(assginee, false);
			if(user != null)
				assgineeName = user.getFullname();
			userList = depManagerService.getUserByDepID(depID, true);
			roleList = depManagerService.getRoleByDepID(depID);
		}
		else
		{
			depinfo = new DepInfo();
			depinfo.setParentid("0");
		}
		this.getRequest().setAttribute("assgineeName", assgineeName);
		return this.findForward("depSave");
	}
	
	public String userDepRelationFrame()
	{
		return SUCCESS;
	}
	
	/**
	 * 部门视图为树形的时候，加载部门到框架的右窗口中
	 * @return
	 */
	public String depLoad_right()
	{
		String returnMessage = null;//返回页面信息
		String returnRefreshId = "";//返回需要刷新的节点
		String assgineeName = "";//部门负责人全名
		if(this.getSession().getAttribute("depId_right")!=null)
		{//树形视图下，保存部门后将要执行的语句块
			depID = (String)this.getSession().getAttribute("depId_right");
			this.getSession().removeAttribute("depId_right");
		}
		if(this.getSession().getAttribute("returnMessage_right")!=null)
		{//树形视图下，保存部门和删除部门将要执行的语句块
			returnMessage = (String)this.getSession().getAttribute("returnMessage_right");
			this.getSession().removeAttribute("returnMessage_right");
			this.getRequest().setAttribute("returnMessage", returnMessage);
		}
		if(this.getSession().getAttribute("returnRefreshId")!=null)
		{//树形视图下，删除部门需要执行的语句块
			returnRefreshId = (String)this.getSession().getAttribute("returnRefreshId");
			this.getSession().removeAttribute("returnRefreshId");
			returnRefreshId = depManagerService.getAllParentIdById(returnRefreshId);
		}
		if(depID!=null)
		{//树形视图下，保存部门后将要执行的语句块；树形视图下，点击树节点将要执行的语句块
			depinfo = depManagerService.getDepByID(depID);
		}
		if(depinfo != null)
		{//获取部门信息的父级部门名称以及本部门的用户和角色列表
			DepInfo parent = depManagerService.getDepByID(depinfo.getParentid());
			if(parent!=null)
			{
				depinfo.setParentShow(parent.getDepname());
				this.getRequest().setAttribute("PdepType", parent.getDeptype());
			}
			String assginee = depinfo.getDepassginee();
			UserInfo user = userManagerService.getUserByLoginName(assginee, false);
			if(user != null)
				assgineeName = user.getFullname();
			userList = depManagerService.getUserByDepID(depID, true);
			roleList = depManagerService.getRoleByDepID(depID);
			returnRefreshId = depinfo.getParentid();
			if(this.getSession().getAttribute("returnRefreshId_update")!=null)
			{
				if("0".equals(this.getSession().getAttribute("returnRefreshId_update")))
				{
					returnRefreshId = "0";
				}
				else
				{
					returnRefreshId = depManagerService.getAllParentIdById((String)this.getSession().getAttribute("returnRefreshId_update"));
				}
				this.getSession().removeAttribute("returnRefreshId_update");
			}
		}
		this.getRequest().setAttribute("returnRefreshId", returnRefreshId);
		this.getRequest().setAttribute("assgineeName", assgineeName);
		return this.findForward("depSave_right");
	}
	
	/**
	 * 在部门树形视图下增加部门（同级增加、下级增加）
	 * @return
	 */
	public String addDep_right()
	{
		String add_type = StringUtils.checkNullString(this.getRequest().getParameter("add_type"));
		String current_id = StringUtils.checkNullString(this.getRequest().getParameter("current_id"));
		depinfo = new DepInfo();
		DepInfo current_depinfo = depManagerService.getDepByID(current_id);//当前节点对应的部门信息
		if(add_type.equals("current"))//同级增加
		{
			depinfo.setParentid(current_depinfo.getParentid());
			if(!"0".equals(current_depinfo.getParentid()))
			{
				DepInfo Pdepinfo = depManagerService.getDepByID(current_depinfo.getParentid());
				this.getRequest().setAttribute("PdepType",Pdepinfo.getDeptype());
			}
			if("1".equals(current_depinfo.getDeptype()))
			{//代表顶级部门
				depinfo.setDeptype("1");
				this.getRequest().setAttribute("rootDep","true");
			}
			else
			{//非顶级部门
				depinfo.setParentShow(depManagerService.getDepByID(current_depinfo.getParentid()).getDepname());
			}
		}
		if(add_type.equals("lower"))//下级增加
		{
			depinfo.setParentid(current_id);
			this.getRequest().setAttribute("PdepType", current_depinfo.getDeptype());
			depinfo.setParentShow(current_depinfo.getDepname());
		}
		return findForward("depAdd_right");
	}
	
	/**
	 * 当部门信息视图为树形视图时的保存方法
	 * @return
	 */
	public String depSave_right()
	{
		userIdXml = UltraSmUtil.thansToXml(userIdXml);
		roleIdXml = UltraSmUtil.thansToXml(roleIdXml);
		XmlParser userXml = new XmlParser(userIdXml,0);
		XmlParser roleXml = new XmlParser(roleIdXml,0);
		String userIds = userXml.getAttributeValue("table", "add");
		String delUserIds = userXml.getAttributeValue("table", "del");
		String roleIds = roleXml.getAttributeValue("table", "add");
		String delRoleIds = roleXml.getAttributeValue("table", "del");
		List userIdList = "".equals(userIds)?null:UltraSmUtil.arrayToList(userIds.split(","));
		List delUserIdList = "".equals(delUserIds)?null:UltraSmUtil.arrayToList(delUserIds.split(","));
		List roleIdList = "".equals(roleIds)?null:UltraSmUtil.arrayToList(roleIds.split(","));
		List delRoleIdList = "".equals(delRoleIds)?null:UltraSmUtil.arrayToList(delRoleIds.split(","));
		if(depinfo != null)
		{
			UserSession userSession = (UserSession) this.getSession().getAttribute("userSession");
			String userpid = "";
			if(userSession != null)
				userpid = userSession.getPid();
			long currentTime = TimeUtils.getCurrentTime();
			if("".equals(StringUtils.checkNullString(depinfo.getPid())))
			{
				depinfo.setCreater(userpid);
				depinfo.setCreatetime(currentTime);
			}
			depinfo.setLastmodifier(userpid);
			depinfo.setLastmodifytime(currentTime);
		}
		String src_parentid = StringUtils.checkNullString(this.getRequest().getParameter("src_parentid"));
		if(!"".equals(src_parentid) && !"".equals(StringUtils.checkNullString(depinfo.getPid())))
		{//如果是在树形视图下修改部门的上级部门
			if(!src_parentid.equals(depinfo.getParentid()))
			{
				this.getSession().setAttribute("returnRefreshId_update", depinfo.getParentid());
			}
		}
		String returnMessage = Internation.language("com_msg_saveErr")+"!";
		if(depManagerService.saveDepInfo(depinfo, userIdList, roleIdList, delUserIdList, delRoleIdList))
		{
			returnMessage = Internation.language("com_msg_saveSuccess")+"!";
		}
		this.getSession().setAttribute("depId_right", depinfo.getPid());
		this.getSession().setAttribute("returnMessage_right", returnMessage);
		return findRedirect("depLoad_right");
	}
	
	/**
	 * 删除部门信息
	 * @return
	 */
	public String delDep()
	{
		String redirectType = StringUtils.checkNullString(this.getRequest().getParameter("redirectType"));//如果是从树形视图下删除部门，redirectType为right值
		String ids = StringUtils.checkNullString(this.getRequest().getParameter("depIds"));
		if("right".equals(redirectType))
		{
			DepInfo currentNode = depManagerService.getDepByID(ids);
			this.getSession().setAttribute("returnRefreshId", currentNode != null?currentNode.getParentid():"");//记录返回后页面需要刷新的节点
		}
		List idList = UltraSmUtil.arrayToList(ids.split(","));
		String isAudit = PropertiesUtils.getProperty("eoms.isaudit");//是否添加审计日志
		String depName = "";
		if("true".equals(isAudit))
		{
			depName = UltraSmUtil.getNameById("bs_t_sm_dep", "depname", idList);
		}
		boolean result = depManagerService.deleteDepByID(idList);
		if("true".equals(isAudit) && result && !"".equals(depName))
		{
			RecordLog.printOperateAuditLog("1", "10202", "删除了("+depName+")部门！");
		}
		if(redirectType.equals("right"))
		{
			String returnMessage = null;
			if(result)
			{
				returnMessage = Internation.language("com_msg_deleteSuccess")+"!";
			}
			else
			{
				returnMessage = Internation.language("com_msg_deleteErr")+"!";
			}
			this.getSession().setAttribute("returnMessage_right", returnMessage);
			return findRedirect("depLoad_right");
		}
		else
		{
			return this.findForward("depList");
		}
	}
	
	/**
	 * 部门保存或者修改
	 */
	public String depSave()
	{
		userIdXml = UltraSmUtil.thansToXml(userIdXml);
		roleIdXml = UltraSmUtil.thansToXml(roleIdXml);
		XmlParser userXml = new XmlParser(userIdXml,0);
		XmlParser roleXml = new XmlParser(roleIdXml,0);
		String userIds = userXml.getAttributeValue("table", "add");
		String delUserIds = userXml.getAttributeValue("table", "del");
		String roleIds = roleXml.getAttributeValue("table", "add");
		String delRoleIds = roleXml.getAttributeValue("table", "del");
		List userIdList = "".equals(userIds)?null:UltraSmUtil.arrayToList(userIds.split(","));
		List delUserIdList = "".equals(delUserIds)?null:UltraSmUtil.arrayToList(delUserIds.split(","));
		List roleIdList = "".equals(roleIds)?null:UltraSmUtil.arrayToList(roleIds.split(","));
		List delRoleIdList = "".equals(delRoleIds)?null:UltraSmUtil.arrayToList(delRoleIds.split(","));
		if(depinfo != null)
		{
			UserSession userSession = (UserSession) this.getSession().getAttribute("userSession");
			String userpid = "";
			if(userSession != null)
				userpid = userSession.getPid();
			long currentTime = TimeUtils.getCurrentTime();
			if("".equals(StringUtils.checkNullString(depinfo.getPid())))
			{
				depinfo.setCreater(userpid);
				depinfo.setCreatetime(currentTime);
			}
			depinfo.setLastmodifier(userpid);
			depinfo.setLastmodifytime(currentTime);
		}
		String returnMessage = Internation.language("com_msg_saveErr")+"!";
		if(depManagerService.saveDepInfo(depinfo, userIdList, roleIdList, delUserIdList, delRoleIdList))
		{
			returnMessage = Internation.language("com_msg_saveSuccess")+"!";
		}
		String parafresh = "true";
		this.getRequest().setAttribute("parafresh", parafresh);
		this.getRequest().setAttribute("returnMessage", returnMessage);
		return "refresh";
	}
	
	/**
	 * 改变部门状态
	 */
	public String transferStatus()
	{
		HttpServletRequest request = this.getRequest();
		String tag = StringUtils.checkNullString(request.getParameter("transType"));
		String depIds = StringUtils.checkNullString(request.getParameter("depIds"));
		if(!"".equals(tag) && !"".equals(depIds))
		{
			String[] targets = depIds.split(",");
			List idls = UltraSmUtil.arrayToList(targets);
			if("inuse".equals(tag))
			{	
				depManagerService.setDepEnabled(idls);
			}
			else
			{
				depManagerService.setDepDisable(idls);
			}
		}
		return findForward("depList");
	}
	
	/**
	 * 为部门添加组成员
	 * @return
	 */
	public String cfgDepMembers() 
	{
		return SUCCESS;
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
	
	/**
	 * 该方法用来当在页面上选择了上级部门以后，取欲修改和添加部门的部门全名
	 */
	public void getDepFullName()
	{
		String parentid = StringUtils.checkNullString(this.getRequest().getParameter("parentid"));
		DepInfo parent = depManagerService.getDepByID(parentid);
		String depType = parent.getDeptype();
		String msg = "";
		if(parent!=null)
		{
			msg = parent.getDepfullname() + ',' + parent.getDepdns()+','+depType;
		}
		this.renderText(msg);
	}
	
	/**
	 *   根据depid获取部门信息，返回json串
	 *  
	 */
	public void getDepInfoById(){
		DepInfo depInfo = depManagerService.getDepByID(depID);
		renderText(JSONObject.fromObject(depInfo).toString());
	}
	
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
	 * 以下为属性get/set方法区域
	 */
	public void setDepManagerService(DepManagerService depManagerService) 
	{
		this.depManagerService = depManagerService;
	}
	public List<DepInfo> getDepList() 
	{
		return depList;
	}
	public void setDepID(String depID) 
	{
		this.depID = depID;
	}
	public DepInfo getDepinfo() 
	{
		return depinfo;
	}
	public void setDepinfo(DepInfo depinfo) 
	{
		this.depinfo = depinfo;
	}
	public List<UserInfo> getUserList() 
	{
		return userList;
	}
	public List<RoleInfo> getRoleList() 
	{
		return roleList;
	}
	public void setRoleManagerService(RoleManagerService roleManagerService) 
	{
		this.roleManagerService = roleManagerService;
	}
	public void setRoleIdXml(String roleIdXml) 
	{
		this.roleIdXml = roleIdXml;
	}
	public void setUserIdXml(String userIdXml) 
	{
		this.userIdXml = userIdXml;
	}
	public String getDepID() 
	{
		return depID;
	}
	public String getSer_status() {
		return ser_status;
	}
	public void setSer_status(String ser_status) {
		this.ser_status = ser_status;
	}
	public void setUserManagerService(UserManagerService userManagerService) {
		this.userManagerService = userManagerService;
	}

	public String getSqlQuery() {
		return sqlQuery;
	}

	public void setSqlQuery(String sqlQuery) {
		this.sqlQuery = sqlQuery;
	}
	
}
