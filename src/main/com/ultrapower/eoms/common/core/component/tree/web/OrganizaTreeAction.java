package com.ultrapower.eoms.common.core.component.tree.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ultrapower.eoms.common.core.component.tree.manager.OrganizaTreeImpl;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.ultrasm.model.UserInfo;
import com.ultrapower.eoms.ultrasm.service.DepManagerService;
import com.ultrapower.eoms.ultrasm.service.UserManagerService;
import com.ultrapower.eoms.ultrasm.service.UserTemplateService;
import com.ultrapower.workflow.role.model.ChildRole;

public class OrganizaTreeAction extends BaseAction
{
	private DepManagerService depManagerService;
	private UserManagerService userManagerService;
	private UserTemplateService userTemplateService;
	private String rearchUserOrDep;
	private String preventTree;
	private String selectId;
	private String treeType;
	
	public String commonTreeArea()
	{
		return SUCCESS;
	}
	
	public String commonUserArea()
	{
		String returnPage = StringUtils.checkNullString(this.getRequest().getParameter("returnPage"));
		String approve = StringUtils.checkNullString(this.getRequest().getParameter("approve"));
		OrganizaTreeImpl organizaTreeImpl = new OrganizaTreeImpl();
		String commonUserData = "";
		if(approve.indexOf("true") == 0)
		{
			commonUserData = organizaTreeImpl.getCommonUserDataApprove(approve);
		}
		else
		{
			treeType = StringUtils.checkNullString(treeType);
			if(organizaTreeImpl.TREETYPE_POSITION.equals(treeType))
			{
				commonUserData = organizaTreeImpl.getCommonUserPosition(id);
				this.getRequest().setAttribute("commonUserData", commonUserData);
				return this.findForward("commonUserAreaPosition");
			}
			else if(organizaTreeImpl.TREETYPE_ROLECHILD.equals(treeType))
			{
				String crName = this.getRequest().getParameter("childRoleName");
				String rolecode = StringUtils.checkNullString(this.getRequest().getParameter("rolecode"));
				if("".equals(rolecode))
					rolecode = id;
				List<ChildRole> crList = organizaTreeImpl.getCommonUserRoleChild(rolecode, crName);
				this.getRequest().setAttribute("rolecode", rolecode);
				this.getRequest().setAttribute("crList", crList);
				this.getRequest().setAttribute("childRoleName", crName);
				return this.findForward("commonUserAreaRoleChild");
			}
			else
			{
				commonUserData = organizaTreeImpl.getCommonUserData(id);
			}
		}
		this.getRequest().setAttribute("commonUserData", commonUserData);
		if(!"".equals(returnPage))
			return this.findForward(returnPage);
		return SUCCESS;
	}
	
	public void getCommonTreeData()
	{
		String approve = StringUtils.checkNullString(this.getRequest().getParameter("approve")); //工单派发树审批环节时传递的参数
		String wfVersion = StringUtils.checkNullString(this.getRequest().getParameter("wfVersion")); //工单类别版本号
		String utType = StringUtils.checkNullString(this.getRequest().getParameter("utType")); // 人员模版类别
		String typeMark = StringUtils.checkNullString(this.getRequest().getParameter("typeMark")); //模版类别标识
		String isSelectType = StringUtils.checkNullString(this.getRequest().getParameter("isSelectType"));
		String useType = StringUtils.checkNullString(this.getRequest().getParameter("useType")); //使用类别
		String depids = StringUtils.checkNullString(this.getRequest().getParameter("depids")); //权限组id
		String action_sign = StringUtils.checkNullString(this.getRequest().getParameter("act"));
		String step_no = StringUtils.checkNullString(this.getRequest().getParameter("step"));
		String fieldID = StringUtils.checkNullString(this.getRequest().getParameter("field"));
		OrganizaTreeImpl organizaTreeImpl = new OrganizaTreeImpl();
		String commonTreeData = "";
		if ("".equals(depids)) {
			if (approve.indexOf("true") == 0) {// 是审批环节
				commonTreeData = organizaTreeImpl.getCommonTreeDataApporve(approve, selectId, rearchUserOrDep);
			} else {
				if (!"".equals(StringUtils.checkNullString(rearchUserOrDep))) {
					if (StringUtils.checkNullString(preventTree).indexOf("UserTemplate") >= 0) // 如果人员模版标签被阻止了，就不能搜索出人员模版信息
						useType = "noTemplate";
					if(organizaTreeImpl.TREETYPE_CONFIG.equals(treeType))
						commonTreeData = organizaTreeImpl.getSubConfigData(rearchUserOrDep ,id,
								selectId, isSelectType, treeType, action_sign,
								step_no, fieldID);
					else
						commonTreeData = organizaTreeImpl.getCommonRearchData(rearchUserOrDep, selectId, isSelectType, treeType, useType, utType, typeMark);
					
				} else {
					if (organizaTreeImpl.TREETYPE_CONFIG.equals(treeType))
						commonTreeData = organizaTreeImpl.getSubConfigData(id,
								selectId, isSelectType, treeType, action_sign,
								step_no, fieldID);
					else
						commonTreeData = organizaTreeImpl.getCommonTreeData(id, selectId, isSelectType, treeType, useType, utType, typeMark, wfVersion);
				}
			}
		} else {
			commonTreeData = organizaTreeImpl.getPriTreeData(id, rearchUserOrDep, selectId, isSelectType, depids);
		}
		System.out.println(commonTreeData);
		this.renderXML(commonTreeData);
	}
	
	/**
	 * 树形区域
	 * @return
	 */
	public String treeArea()
	{
		String isRadio = StringUtils.checkNullString(this.getRequest().getParameter("isRadio"));
		String pageName = "treeAreaOrganiza";
//		if(!"".equals(treeType))
//		{
//			pageName += treeType;
//		}
		this.getRequest().setAttribute("isRadio", isRadio);
		return this.findForward(pageName);
	}
	
	public void getUserTreeId() throws IOException
	{
		String selectData = StringUtils.checkNullString(this.getRequest().getParameter("selectData"));
		String idType = StringUtils.checkNullString(this.getRequest().getParameter("idType"));
		PrintWriter out = this.getResponse().getWriter();
		out.print(getFullSelectData(selectData, idType));
	}
	
	private String getFullSelectData(String selectData, String idType)
	{
		String newIds = "";
		if(!"".equals(selectData))
		{
			String[] subId = selectData.split(";");
			String[] subData;
			HashMap<String, String> map = new HashMap<String, String>();
			for(int i=0 ; i<subId.length ; i++)
			{
				subData = subId[i].split(":");
				if(subData.length == 2)
					map.put(subData[0], subData[1]);
			}
			if(map.get("U") != null)
			{
				String userIds = map.get("U");
				String userNames = "";
				List<UserInfo> userList = null;
				if("0".equals(idType))
				{
					String[] idArray = userIds.split(",");
					List<String> idList = new ArrayList<String> ();
					String[] tmp;
					for(int i=0 ; i<idArray.length ; i++)
					{
						tmp = idArray[i].split("-");
						if(tmp.length > 0)
							idList.add(tmp[0]);
					}
					userList = userManagerService.getUserByID(this.getListByArray(userIds.split(",")));
				}
				else if("1".equals(idType))
					userList = userManagerService.getUserByLoginName(this.getListByArray(userIds.split(",")));
				else
					userList = userManagerService.getUserByID(this.getListByArray(userIds.split(",")));
				int userLen = 0;
				if(userList != null)
					userLen = userList.size();
				UserInfo userInfo;
				userIds = "";
				for(int i=0 ; i<userLen ; i++)
				{
					userInfo = userList.get(i);
					userIds += "," + userInfo.getPid() + "-" + userInfo.getLoginname();
					userNames += "," + userInfo.getFullname();
				}
				if(userLen > 0)
				{
					userIds = userIds.substring(1);
					userNames = userNames.substring(1);
				}
				newIds += ";U:" + userIds + ":" + userNames;
			}
			if(map.get("D") != null)
			{
				String depIds = map.get("D");
				String depInfos = depManagerService.getDepNamesById(depIds);
				String[] subDep = depInfos.split(";");
				if(subDep.length > 1)
				{
					newIds += ";D:" + subDep[0] + ":" + subDep[1];
				}
			}
			if(map.get("T") != null)
			{
				//人员模版
				String utIds = map.get("T");
				String utInfos = userTemplateService.getUserTemplateNamesByIds(utIds);
				String[] subUt = utInfos.split(";");
				if(subUt.length > 1)
				{
					newIds += ";T:" + subUt[0] + ":" + subUt[1];
				}
			}
			if(map.get("R") != null)
			{
				//角色细分是虚拟组
				String depIds = map.get("R");
				String depInfos = depManagerService.getDepNamesById(depIds);
				String[] subDep = depInfos.split(";");
				if(subDep.length > 1)
				{
					newIds += ";R:" + subDep[0] + ":" + subDep[1];
				}
			}
			if(!"".equals(newIds))
				newIds = newIds.substring(1);
		}
		return newIds;
	}
	
	private List<String> getListByArray(String[] idArray)
	{
		List<String> idList = new ArrayList<String> ();
		if(idArray == null)
			return idList;
		for(int i=0 ; i<idArray.length ; i++)
		{
			if(!"".equals(idArray[i]))
				idList.add(idArray[i]);
		}
		return idList;
	}
	
	public String getRearchUserOrDep() {
		return rearchUserOrDep;
	}
	public void setRearchUserOrDep(String rearchUserOrDep) {
		try {
			this.rearchUserOrDep = java.net.URLDecoder.decode(rearchUserOrDep,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	public String getPreventTree() {
		return preventTree;
	}
	public void setPreventTree(String preventTree) {
		this.preventTree = preventTree;
	}
	public void setSelectId(String selectId) {
		this.selectId = selectId;
	}
	public String getSelectId() {
		return selectId;
	}
	public void setTreeType(String treeType) {
		this.treeType = treeType;
	}
	public String getTreeType() {
		return treeType;
	}
	public void setDepManagerService(DepManagerService depManagerService) {
		this.depManagerService = depManagerService;
	}
	public void setUserManagerService(UserManagerService userManagerService) {
		this.userManagerService = userManagerService;
	}
	public void setUserTemplateService(UserTemplateService userTemplateService) {
		this.userTemplateService = userTemplateService;
	}
}
