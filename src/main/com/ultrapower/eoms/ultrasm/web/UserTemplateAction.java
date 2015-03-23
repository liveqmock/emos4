package com.ultrapower.eoms.ultrasm.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ultrapower.eoms.common.core.util.Internation;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ultrasm.model.UserInfo;
import com.ultrapower.eoms.ultrasm.model.UserTemplate;
import com.ultrapower.eoms.ultrasm.service.DepManagerService;
import com.ultrapower.eoms.ultrasm.service.UserManagerService;
import com.ultrapower.eoms.ultrasm.service.UserTemplateService;
import com.ultrapower.workflow.role.model.ChildRole;
import com.ultrapower.workflow.role.service.IRoleService;

public class UserTemplateAction extends BaseAction
{
	private UserManagerService userManagerService;
	private DepManagerService depManagerService;
	private UserTemplateService userTemplateService;
	private IRoleService roleService;
	private UserTemplate userTemplate;
	private String utid;

	public String userTemplateList()
	{
		return SUCCESS;
	}
	
	public String userTemplateInfo()
	{
		userTemplate = userTemplateService.getUserTemplateById(utid);
		if(userTemplate == null) //新建
		{
			//新建时默认不共享
			userTemplate = new UserTemplate();
			userTemplate.setIsshare((long) 0);
		}
		else //修改
		{
			String typedata = StringUtils.checkNullString(userTemplate.getTypedata());
			if(!"".equals(typedata))
			{
				int space = typedata.indexOf(";");
				String typedata_mark = "";
				String typedata_name = "";
				if(space > 0)
				{
					typedata_mark = typedata.substring(0, space);
					typedata_name = typedata.substring(space + 1);
				}
				else
				{
					typedata_mark = typedata;
				}
				this.getRequest().setAttribute("typedata_mark", typedata_mark);
				this.getRequest().setAttribute("typedata_name", typedata_name);
			}
			
			//获取配置的ID对应的名称
			String userData = userManagerService.getUserNamesById(userTemplate.getUserdata());
			userData = userData.substring(userData.indexOf(";") + 1);
			String depData = depManagerService.getDepNamesById(userTemplate.getDepdata());
			depData = depData.substring(depData.indexOf(";") + 1);
			String userShare = userManagerService.getUserNamesById(userTemplate.getUsershare());
			userShare = userShare.substring(userShare.indexOf(";") + 1);
			String depShare = depManagerService.getDepNamesById(userTemplate.getDepshare());
			depShare = depShare.substring(depShare.indexOf(";") + 1);
			this.getRequest().setAttribute("userData", userData);
			this.getRequest().setAttribute("depData", depData);
			this.getRequest().setAttribute("userShare", userShare);
			this.getRequest().setAttribute("depShare", depShare);
		}
		return SUCCESS;
	}
	
	public String userTemplateType() {
		String utType = StringUtils.checkNullString(this.getRequest().getParameter("utType"));
		String form_name = StringUtils.checkNullString(this.getRequest().getParameter("form_name"));
		String type_mark = StringUtils.checkNullString(this.getRequest().getParameter("type_mark"));
		String type_name = StringUtils.checkNullString(this.getRequest().getParameter("type_name"));
		//String selectData = StringUtils.checkNullString(this.getRequest().getParameter("selectData"));
		this.getRequest().setAttribute("sqlquery", "SQL_SM_UserTemplateType.query." + utType);
		this.getRequest().setAttribute("form_name", form_name);
		this.getRequest().setAttribute("type_mark", type_mark);
		this.getRequest().setAttribute("type_name", type_name);
		//this.getRequest().setAttribute("selectData", selectData);
		return SUCCESS;
	}
	
	public String userTemplateSave()
	{
		String refreshArea = StringUtils.checkNullString(this.getRequest().getParameter("refreshArea"));
		String returnMessage = Internation.language("com_msg_saveErr")+"!";
		if(userTemplate != null)
		{
			UserSession userSession = (UserSession) this.getSession().getAttribute("userSession");
			String userid = "";
			if(userSession != null)
				userid = userSession.getPid();
			long currentTime = TimeUtils.getCurrentTime();
			String utid = StringUtils.checkNullString(userTemplate.getPid());
			boolean result = false;
			if("".equals(utid))
			{
				userTemplate.setCreater(userid);
				userTemplate.setCreatetime(currentTime);
				userTemplate.setLastmodifytime(currentTime);
				result = userTemplateService.addUserTemplate(userTemplate);
			}
			else
			{
				boolean updateUtType = false; // 是否修改了模版类别
				boolean updateUtName = false; // 是否修改了模版名称
				boolean updateData = false; // 是否修改了模版数据
				boolean updateShare = false; // 是否修改了模版共享
				String utname = StringUtils.checkNullString(this.getRequest().getParameter("utname"));
				if(!"false".equals(StringUtils.checkNullString(this.getRequest().getParameter("uttype"))))
					updateUtType = true;
				//如果修改了配置的人和组 则将更新配置数据参数设置为true
				if(!"false".equals(StringUtils.checkNullString(this.getRequest().getParameter("dataid"))))
					updateData = true;
				//如果修改了共享的人和组 并且是否共享参数设置为是 才会将本次修改的共享的人和组更新
				if("1".equals(StringUtils.checkNullString(userTemplate.getIsshare())) && !"false".equals(StringUtils.checkNullString(this.getRequest().getParameter("shareid"))))
					updateShare = true;
				//如果修改了模版名称则需要将名称同步到模版共享关系表中
				if(!updateShare && !utname.equals(StringUtils.checkNullString(userTemplate.getTemplatename())))
					updateUtName = true;
				userTemplate.setLastmodifytime(currentTime);
				result = userTemplateService.updateUserTemplate(userTemplate, updateUtType, updateUtName, updateData, updateShare);
			}
			if(result)
				returnMessage = Internation.language("com_msg_saveSuccess")+"!";
			if("current".equals(refreshArea))
			{
				return this.findRedirectPar("userTemplateInfoSimple.action?utid="+userTemplate.getPid()+"&returnMessage="+returnMessage);
			}
		}
		this.getRequest().setAttribute("returnMessage", returnMessage);
		if("noRefresh".equals(refreshArea))
			this.getRequest().setAttribute("parafresh", "false");
		else
			this.getRequest().setAttribute("parafresh", "true");
		return "refresh";
	}
	
	public String userTemplateDelete()
	{
		String ids = StringUtils.checkNullString(this.getRequest().getParameter("var_selectvalues"));
		if(!"".equals(ids))
		{
			String[] idArray = ids.split(",");
			for(int i=0 ; i<idArray.length ; i++)
			{
				userTemplateService.deleteUserTemplate(idArray[i]);
			}
		}
		return this.findForward("userTemplateList");
	}
	
	public String userTemplateInfoSimple()
	{
		userTemplate = userTemplateService.getUserTemplateById(utid);
		if(userTemplate != null)
		{
			//获取配置的ID对应的名称
			String userData = userManagerService.getUserNamesById(userTemplate.getUserdata());
			userData = userData.substring(userData.indexOf(";") + 1);
			String depData = depManagerService.getDepNamesById(userTemplate.getDepdata());
			depData = depData.substring(depData.indexOf(";") + 1);
			this.getRequest().setAttribute("userData", userData);
			this.getRequest().setAttribute("depData", depData);
			if("0".equals(StringUtils.checkNullString(userTemplate.getIsshare())))
				this.getRequest().setAttribute("parentid", "T_myself");
			else
				this.getRequest().setAttribute("parentid", "T_myshare");
			this.getRequest().setAttribute("refreshArea", "current"); // 刷新当前页面
			this.getRequest().setAttribute("returnMessage", StringUtils.checkNullString(this.getRequest().getParameter("returnMessage")));
		}
		else
		{
			userTemplate = new UserTemplate();
			userTemplate.setOrdernum((long) 1);
			userTemplate.setIsshare((long) 0);
			String utType = StringUtils.checkNullString(this.getRequest().getParameter("utType"));
			String typeMark = StringUtils.checkNullString(this.getRequest().getParameter("typeMark"));
			String selectData = StringUtils.checkNullString(this.getRequest().getParameter("selectData"));
			String haveTemplate = StringUtils.checkNullString(this.getRequest().getParameter("haveTemplate"));
			userTemplate.setUttype(utType);
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
				String userData = StringUtils.checkNullString(map.get("U"));
				String depData = StringUtils.checkNullString(map.get("D"));
				if(!"false".equals(haveTemplate))
				{
					String templateData = userTemplateService.getUserTemplateDataByIds(map.get("T"), "2");
					if(!"".equals(StringUtils.checkNullString(templateData)))
					{
						String[] dataArray = templateData.split(";");
						String[] userid = dataArray[0].split(":");
						if(userid.length > 0 && !"".equals(userid[0]))
							userData = this.mergerStr(userData, userid[0]);
						String[] depid = dataArray[1].split(":");
						if(depid.length > 0 && !"".equals(depid[0]))
							depData = this.mergerStr(depData, depid[0]);
					}
				}
				userTemplate.setUserdata(userData);
				userTemplate.setDepdata(depData);
				userData = userManagerService.getUserNamesById(userData);
				userData = userData.substring(userData.indexOf(";") + 1);
				depData = depManagerService.getDepNamesById(depData);
				depData = depData.substring(depData.indexOf(";") + 1);
				this.getRequest().setAttribute("userData", userData);
				this.getRequest().setAttribute("depData", depData);
			}
			this.getRequest().setAttribute("refreshArea", "noRefresh"); // 关闭窗口不需要刷新父页面
		}
		return SUCCESS;
	}
	
	public void getMergerData() throws IOException
	{
		String dataStr = "";
		String idStr = StringUtils.checkNullString(this.getRequest().getParameter("idStr"));
		String idType = StringUtils.checkNullString(this.getRequest().getParameter("idType"));
		if(!"".equals(idStr))
		{
			String[] subId = idStr.split(";");
			String[] subData;
			HashMap<String, String> map = new HashMap<String, String>();
			for(int i=0 ; i<subId.length ; i++)
			{
				subData = subId[i].split(":");
				if(subData.length >= 2)
					map.put(subData[0], subData[1]);
			}
			String userData = StringUtils.checkNullString(map.get("U"));
			String depData = StringUtils.checkNullString(map.get("D"));
			String templateData = userTemplateService.getUserTemplateDataByIds(map.get("T"), idType);
			if(!"".equals(StringUtils.checkNullString(templateData)))
			{
				String[] dataArray = templateData.split(";");
				String[] userid = dataArray[0].split(":");
				if(userid.length > 0 && !"".equals(userid[0]))
					userData = this.mergerStr(userData, userid[0]);
				String[] depid = dataArray[1].split(":");
				if(depid.length > 0 && !"".equals(depid[0]))
					depData = this.mergerStr(depData, depid[0]);
			}
			//查人员名称
			String userIds = userData;
			//fanying bg 2013-6-20 自定义派发数，同一用户属于多个组时，用户显示不正确，
			//所以在生成用户节点时，在封装好的用户id后追加了部门id，用两个下划线__ 连接，所以在此处需要将追加的部分去掉。
			String[] userIdsTemp = userIds.split(",");
			userIds = "";
			for(String userIdAndDepId:userIdsTemp){
				if(userIdAndDepId.contains("__")){
					userIdAndDepId = userIdAndDepId.substring(0, userIdAndDepId.indexOf("__"));
				}
				userIds += userIdAndDepId+",";
			}
			userIds = userIds.substring(0,userIds.length()-1);
			//fanying ed
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
				if("1".equals(idType))
					userIds += "," + userInfo.getLoginname();
				else if("0".equals(idType))
					userIds += "," + userInfo.getPid() + "-" + userInfo.getLoginname();
				else
					userIds += "," + userInfo.getPid();
				userNames += "," + userInfo.getFullname();
			}
			if(userLen > 0)
			{
				userIds = userIds.substring(1);
				userNames = userNames.substring(1);
			}
			userData = this.getResolveData("U", userIds, userNames);
			
			String depIds = depData;
			String depNames = "";
			String depInfos = StringUtils.checkNullString(depManagerService.getDepNamesById(depIds));
			int dic = depInfos.indexOf(";");
			if(dic > 0)
			{
				depIds = depInfos.substring(0, dic);
				depNames = depInfos.substring(dic + 1);
				depData = this.getResolveData("D", depIds, depNames);
			}
			String roleIds = StringUtils.checkNullString(map.get("R"));
			String roleNames = "";
			if(!"".equals(roleIds))
			{
				String[] idArray = roleIds.split(",");
				ChildRole childRole;
				for(int i=0 ; i<idArray.length ; i++)
				{
					childRole = roleService.getChildRoleById(idArray[i]);
					if(childRole != null)
					{
						roleNames += "," + StringUtils.checkNullString(childRole.getChildRoleName());
					}
				}
				if(!"".equals(roleNames))
					roleNames = roleNames.substring(1);
			}
			String roleData = this.getResolveData("R", roleIds, roleNames); //角色细分
			if(!"".equals(userData))
				dataStr += ";" + userData;
			if(!"".equals(depData))
				dataStr += ";" + depData;
			if(!"".equals(roleData))
				dataStr += ";" + roleData;
			if(!"".equals(dataStr))
				dataStr = dataStr.substring(1);
		}
		PrintWriter out = this.getResponse().getWriter();
		out.print(dataStr);
	}
	
	private String mergerStr(String strOne, String strTwo)
	{
		String resultStr = "";
		if("".equals(StringUtils.checkNullString(strOne)))
			return StringUtils.checkNullString(strTwo);
		if("".equals(StringUtils.checkNullString(strTwo)))
			return StringUtils.checkNullString(strOne);
		String[] oneArray = strOne.split(",");
		String[] twoArray = strTwo.split(",");
		String[] strArray;
		if(oneArray.length > twoArray.length)
		{
			strArray = twoArray;
			resultStr = strOne;
		}
		else
		{
			strArray = oneArray;
			resultStr = strTwo;
		}
		for(int i=0 ; i<strArray.length ; i++)
		{
			//如果以下判断都不满足，则说明strArray[i]这个字符串不存在于resultStr这组字符串
			if(!(resultStr.indexOf(","+strArray[i]+",") > 0							//是否存在于中间
			  || resultStr.indexOf(strArray[i]+",") == 0							//是否在第一个位置
			  || resultStr.indexOf(","+strArray[i]) == resultStr.lastIndexOf(",")	//是否在最后一个位置
			  || resultStr.equals(strArray[i]))										//是否相等
			  )
				resultStr += "," + strArray[i];
		}
		return resultStr;
	}
	
	private String getResolveData(String dataType, String dataIds, String dataNames)
	{
		String dataStr = "";
		if("".equals(StringUtils.checkNullString(dataIds)))
			return dataStr;
		String[] idArray = dataIds.split(",");
		String[] nameArray = dataNames.split(",");
		for(int i=0 ; i<idArray.length ; i++)
			dataStr += ";" + dataType + ":" + idArray[i] + ":" + nameArray[i];
		return "".equals(dataStr) ? "" : dataStr.substring(1);
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
	
	public void setUserManagerService(UserManagerService userManagerService) {
		this.userManagerService = userManagerService;
	}
	public void setDepManagerService(DepManagerService depManagerService) {
		this.depManagerService = depManagerService;
	}
	public void setUserTemplateService(UserTemplateService userTemplateService) {
		this.userTemplateService = userTemplateService;
	}
	public UserTemplate getUserTemplate() {
		return userTemplate;
	}
	public void setUserTemplate(UserTemplate userTemplate) {
		this.userTemplate = userTemplate;
	}
	public void setUtid(String utid) {
		this.utid = utid;
	}

	public IRoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}
}
