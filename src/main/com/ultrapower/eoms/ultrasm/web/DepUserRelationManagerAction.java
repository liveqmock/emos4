package com.ultrapower.eoms.ultrasm.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.ultrasm.service.DepManagerService;
import com.ultrapower.eoms.ultrasm.model.UserInfo;
import com.ultrapower.eoms.ultrasm.util.UltraSmUtil;
import com.ultrapower.eoms.common.core.util.Internation;
import com.ultrapower.eoms.common.core.util.StringUtils;

public class DepUserRelationManagerAction extends BaseAction 
{
	private DepManagerService depManagerService;
	private List<UserInfo> userList;
	
	/**
	 * 根据部门ID返回相应的人员列表
	 * @return
	 */
	public String depUserList()
	{
		String depid = StringUtils.checkNullString(this.getRequest().getParameter("depid"));
		this.getRequest().setAttribute("depid", depid);
		return this.findForward("userDepRelation_right");
	}
	
	/**
	 * 当点击定义部门人员关系的时候，用以返回不属于该组的成员列表
	 * @return
	 */
	public String addDepUser()
	{
		this.getRequest().setAttribute("depid", StringUtils.checkNullString(this.getRequest().getParameter("depid")));
		Map map = new HashMap();
		map.put("depid", StringUtils.checkNullString(this.getRequest().getParameter("depid")));
		this.getRequest().setAttribute("valuemap", map);
		return findForward("defUserDepRelation");
	}
	
	/**
	 * 当在定义部门人员关系页面中点击保存的时候，用以保存为部门添加的人员
	 * @return
	 */
	public String saveDepUser()
	{
		String depid = StringUtils.checkNullString(this.getRequest().getParameter("depid"));
		String userids = StringUtils.checkNullString(this.getRequest().getParameter("var_selectvalues"));
		List userIdList = UltraSmUtil.arrayToList(userids.split(","));
		boolean temp = depManagerService.addDepUser(depid, userIdList);
		String parafresh = "true";
		String returnMessage = Internation.language("com_msg_saveErr")+"!";
		if(temp)
		{
			returnMessage = Internation.language("com_msg_saveSuccess")+"!";
		}
		this.getRequest().setAttribute("parafresh", parafresh);
		this.getRequest().setAttribute("returnMessage", returnMessage);
		return "refresh";
	}
	
	/**
	 * 用以删除部门和成员之间的关系
	 * @return
	 */
	public String delDepUser()
	{
		String depid = StringUtils.checkNullString(this.getRequest().getParameter("depid"));
		String userid = StringUtils.checkNullString(this.getRequest().getParameter("userid"));
		depManagerService.deleteDepUser(depid, UltraSmUtil.arrayToList(userid.split(",")));
		return findRedirectPar("depUserList.action?depid="+depid);
	}
	
	/*
	 * 以下为属性的get/set方法区域
	 */
	public void setDepManagerService(DepManagerService depManagerService) 
	{
		this.depManagerService = depManagerService;
	}
	
	public List<UserInfo> getUserList() 
	{
		return userList;
	}
}
