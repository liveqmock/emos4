package com.ultrapower.eoms.ultrasla.manager;

import java.util.ArrayList;
import java.util.List;

import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.ultrasla.service.IOrganizationService;
import com.ultrapower.eoms.ultrasla.util.Receiver;
import com.ultrapower.eoms.ultrasla.util.ConstantMark.OrgType;
import com.ultrapower.eoms.ultrasm.model.UserInfo;
import com.ultrapower.eoms.ultrasm.service.DepManagerService;
import com.ultrapower.eoms.ultrasm.service.RoleManagerService;
import com.ultrapower.eoms.ultrasm.service.UserManagerService;

public class OrganizationImpl implements IOrganizationService {
	
	private UserManagerService userManagerService;
	private DepManagerService depManagerService;
	private RoleManagerService roleManagerService;

	public List<Receiver> getReceiver(String id, OrgType type, int idtype) {
		List<Receiver> recList = null; // 接受者列表信息
		List<UserInfo> userList = null; // 用户列表信息
		if(type == null) {
			// 不执行代码
		} else if(type.equals(OrgType.USER)) { // 通过用户id获取用户信息
			userList = new ArrayList<UserInfo> ();
			if(idtype == 1) { // idtype为1时 为用户登录名
				userList.add(userManagerService.getUserByLoginName(id, false));
			} else {
				userList.add(userManagerService.getUserByID(id));
			}
		} else if(type.equals(OrgType.GROUP)) { // 通过部门id获取用户列表信息
			userList = depManagerService.getUserByDepID(id, false);
		} else if(type.equals(OrgType.ROLE)) { // 通过角色id获取用户列表信息
			userList = roleManagerService.getUserByRoleID(id);
		}
		int userLen = 0;
		if(userList != null) {
			userLen = userList.size();
			recList = new ArrayList<Receiver> ();
		}
		UserInfo user;
		Receiver receiver;
		// 以下为封装接收者列表信息代码
		for(int i=0 ; i<userLen ; i++) {
			user = userList.get(i);
			if(user == null) {
				continue;
			}
			receiver = new Receiver();
			receiver.setId(StringUtils.checkNullString(user.getPid()));
			receiver.setLoginName(StringUtils.checkNullString(user.getLoginname()));
			receiver.setFullName(StringUtils.checkNullString(user.getFullname()));
			receiver.setMobile(StringUtils.checkNullString(user.getMobile()));
			receiver.setEmail(StringUtils.checkNullString(user.getEmail()));
			recList.add(receiver);
		}
		return recList;
	}

	public void setUserManagerService(UserManagerService userManagerService) {
		this.userManagerService = userManagerService;
	}

	public void setDepManagerService(DepManagerService depManagerService) {
		this.depManagerService = depManagerService;
	}

	public void setRoleManagerService(RoleManagerService roleManagerService) {
		this.roleManagerService = roleManagerService;
	}
}
