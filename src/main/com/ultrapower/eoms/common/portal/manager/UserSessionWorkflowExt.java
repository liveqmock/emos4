package com.ultrapower.eoms.common.portal.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.common.portal.service.UserSessionExtService;
import com.ultrapower.eoms.ultrasm.model.DepInfo;
import com.ultrapower.eoms.ultrasm.model.UserInfo;
import com.ultrapower.eoms.ultrasm.service.DepManagerService;
import com.ultrapower.eoms.ultrasm.service.UserManagerService;
import com.ultrapower.workflow.bizservice.model.Agency;
import com.ultrapower.workflow.bizservice.AgencyService;
import com.ultrapower.workflow.role.model.ChildRole;
import com.ultrapower.workflow.role.model.RoleUser;
import com.ultrapower.workflow.role.service.IRoleService;
import com.ultrapower.workflow.bizconfig.sort.IWfSortManager;
import com.ultrapower.workflow.client.WorkFlowServiceClient;
import com.ultrapower.workflow.configuration.sort.model.WfType;

/**
 * UserSession设置流程管理扩展类
 * @author SunHailong
 */
public class UserSessionWorkflowExt implements UserSessionExtService
{
	private DepManagerService depManagerService;
	private UserManagerService userManagerService;
	private AgencyService agencyService;
	private IRoleService roleService;
	
	public UserSession buildExtendUserSession(UserSession userSession)
	{
		if(userSession == null)
		{
			return userSession;
		}
		String loginName = userSession.getLoginName();
		userSession.setAgencys(queryAgencys(loginName));
		userSession.setWfTypes(queryWfTypes());
		userSession.setManagerChildRoleIds(queryManagerChildRoleIds(loginName));
		userSession.setChildRoleIds(queryChileRoleIds(loginName));
		userSession.setManagerGroupId(queryManagerGroupIds(userSession.getGroupId(), loginName));
		return userSession;
	}

	/**
	 * 获取当前用户的代理列表
	 * @param loginName
	 * @return
	 */
	private List<Agency> queryAgencys(String loginName) {
		List<Agency> agencyList = agencyService.getAll();
		List<Agency> agencys = new ArrayList<Agency>();
		if (CollectionUtils.isNotEmpty(agencyList)) {
			for (int i = 0; i < agencyList.size(); i++) {
				Agency agency = agencyList.get(i);
				String agent = agency.getAgentId();
				if (org.apache.commons.lang.StringUtils.isNotBlank(loginName) && loginName.equals(agent)) {
					agencys.add(agency);
				}
			}
		}
		return agencys;
	}
	
	/**
	 * 获取系统中所有的流程类别
	 * @return
	 */
	private List<WfType> queryWfTypes() {
		List<WfType> wfTypes = new ArrayList<WfType>();
		try {
			IWfSortManager ver = WorkFlowServiceClient.clientInstance().getSortService();
			wfTypes = ver.getAllWfType();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wfTypes;
	}
	
	/**
	 * 获取以当前用户为管理员的角色细分id列表
	 * @param loginName
	 * @return
	 */
	private String queryManagerChildRoleIds(String loginName) {
		String childRoleIds = "";
		if (org.apache.commons.lang.StringUtils.isNotBlank(loginName)) {
			String hql = "from ChildRole where charge = '" + loginName + "'";
			List<ChildRole> childRoles = roleService.getChildRoleByHql(hql);
			if (CollectionUtils.isNotEmpty(childRoles)) {
				for (int i = 0; i < childRoles.size(); i++) {
					ChildRole childRole = childRoles.get(i);
					String childRoleId = childRole.getChildRoleId();
					childRoleIds += childRoleId;
					if (i != (childRoles.size() - 1)) {
						childRoleIds += ",";
					}
				}
			}
		}
		return childRoleIds;
	}
	
	/**
	 * 获取当前用户的所有角色细分id列表
	 * @param loginName
	 * @return
	 */
	private String queryChileRoleIds(String loginName) {
		String childRoleIds = "";
		String groupIds = userManagerService.getGroupIdsByLoginName(loginName);
		if (org.apache.commons.lang.StringUtils.isNotBlank(loginName)) {
			String s = "";
			if (StringUtils.isNotBlank(groupIds)) {
				s = " or ('"+groupIds+"' like '%' || depID || '%' and depID is not null)";
			}
			String hql = "from RoleUser where loginName = '" + loginName + "'" + s;
			List<RoleUser> roleUsers = roleService.getRoleUserByHql(hql);
			if (CollectionUtils.isNotEmpty(roleUsers)) {
				for (int i = 0; i < roleUsers.size(); i++) {
					RoleUser roleUser = roleUsers.get(i);
					String childRoleId = roleUser.getChildRoleId();
					childRoleIds += childRoleId;
					if (i != (roleUsers.size() - 1)) {
						childRoleIds += ",";
					}
				}
			}
		}
		return childRoleIds;
	}
	
	/**
	 * 获取以当前用户为管理员的组id列表
	 * @param groupIds
	 * @param loginName
	 * @return
	 */
	private String queryManagerGroupIds(String groupIds, String loginName) {
		String mangerGroupIds = "";
		if (org.apache.commons.lang.StringUtils.isNotBlank(groupIds) && org.apache.commons.lang.StringUtils.isNotBlank(loginName)) {
			String[] arys = groupIds.split(",");
			if (arys != null && arys.length > 0) {
				for (int i = 0; i < arys.length; i++) {
					DepInfo dep = depManagerService.getDepByID(arys[i]);
					if (dep != null) {
						String pid = dep.getPid();
						String depassginee = dep.getDepassginee();
						if (loginName.equals(depassginee)) {
							mangerGroupIds += pid + ",";
						}
					}
				}
			}
		}
		if (org.apache.commons.lang.StringUtils.isNotBlank(mangerGroupIds)) {
			mangerGroupIds = mangerGroupIds.substring(0, mangerGroupIds.length() - 1);
		}
		return mangerGroupIds;
	}

	public void setDepManagerService(DepManagerService depManagerService) {
		this.depManagerService = depManagerService;
	}
	public void setAgencyService(AgencyService agencyService) {
		this.agencyService = agencyService;
	}
	public void setRoleService(IRoleService roleService)
	{
		this.roleService = roleService;
	}

	public UserManagerService getUserManagerService() {
		return userManagerService;
	}

	public void setUserManagerService(UserManagerService userManagerService) {
		this.userManagerService = userManagerService;
	}
}
