package com.ultrapower.eoms.cdbBusiness;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.ultrasm.model.DepInfo;
import com.ultrapower.eoms.ultrasm.model.UserInfo;
import com.ultrapower.eoms.ultrasm.service.DepManagerService;
import com.ultrapower.eoms.ultrasm.service.UserManagerService;

/**
 * 主要用来获取人员或部门的相关信息
 * @author duly
 *
 */
public class UserOrDeptAction  extends BaseAction {
	private UserManagerService userManagerService;
	private DepManagerService depManagerService;
	private List<UserInfo> userList;
	private List<DepInfo> depList;
	private String ids;
	private String loginName;
	
	/**
	 * 通过部门ID获取本部门用户信息
	 */
	public void getUserListByDeptIds(){
		userList = depManagerService.getUserByDepID(ids,false);
		String text = JSONArray.fromObject(userList).toString();
		this.renderText(text);
	}
	
	/**
	 * 通过登陆名获取用户所有信息；
	 */
	public void getUserInfoByLoginname(){
		UserInfo userInfo = this.userManagerService.getUserByLoginName(loginName);
		this.renderXML(JSONObject.fromObject(userInfo).toString());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	public String getLoginName() {
		return loginName;
	}


	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	
	
	public UserManagerService getUserManagerService() {
		return userManagerService;
	}

	public void setUserManagerService(UserManagerService userManagerService) {
		this.userManagerService = userManagerService;
	}

	public DepManagerService getDepManagerService() {
		return depManagerService;
	}

	public void setDepManagerService(DepManagerService depManagerService) {
		this.depManagerService = depManagerService;
	}






















	public List<UserInfo> getUserList() {
		return userList;
	}






















	public void setUserList(List<UserInfo> userList) {
		this.userList = userList;
	}






















	public List<DepInfo> getDepList() {
		return depList;
	}






















	public void setDepList(List<DepInfo> depList) {
		this.depList = depList;
	}






















	public String getIds() {
		return ids;
	}






















	public void setIds(String ids) {
		this.ids = ids;
	}

}
