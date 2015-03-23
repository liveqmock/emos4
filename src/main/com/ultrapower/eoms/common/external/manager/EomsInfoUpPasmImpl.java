package com.ultrapower.eoms.common.external.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ultrapower.accredit.common.value.Organise;
import com.ultrapower.accredit.common.value.Role;
import com.ultrapower.accredit.common.value.User;
import com.ultrapower.accredit.common.value.UserRole;
import com.ultrapower.accredit.util.GetUserUtil;
import com.ultrapower.eoms.common.RecordLog;
import com.ultrapower.eoms.common.core.util.CryptUtils;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.ultrasm.model.DepInfo;
import com.ultrapower.eoms.ultrasm.model.RoleInfo;
import com.ultrapower.eoms.ultrasm.model.UserInfo;
import com.ultrapower.eoms.ultrasm.service.DepManagerService;
import com.ultrapower.eoms.ultrasm.service.RoleManagerService;
import com.ultrapower.eoms.ultrasm.service.UserManagerService;

/**
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2011-1-14 下午04:09:35
 * @descibe 处理pasm广播信息
 */
public class EomsInfoUpPasmImpl {

	
	public static CryptUtils crypt = CryptUtils.getInstance();
	public static UserManagerService userService = (UserManagerService)WebApplicationManager.getBean("userManagerService");
	public static DepManagerService depManagerService = (DepManagerService)WebApplicationManager.getBean("depManagerService");
	public static RoleManagerService roleManagerService = (RoleManagerService)WebApplicationManager.getBean("roleManagerService");
	public static String loginname = GetUserUtil.getUsername();
	
	/**
	 * 添加用户消息
	 * @param userList
	 */
	public static void addUser(List<User> userList){
		int userListLen = 0;
		if(userList!=null)
			userListLen = userList.size();
		UserInfo userInfo;
		User user;
		for(int u = 0;u<userListLen;u++){
			RecordLog.printLog("JMS receive PASM opertype:添加用户消息:第"+(u+1)+"个用户添加--start", RecordLog.LOG_LEVEL_INFO);
			userInfo = new UserInfo();
			user = userList.get(u);
			userInfo.setLoginname(user.getUserAccount());//登录名
			userInfo.setFullname(user.getUserName());//用户中文名
			userInfo.setPwd(crypt.encode(user.getPass()));//用户密码
			userInfo.setPosition(user.getDuty_id());//用户职位
			userInfo.setType(user.getUserType_id());//用户类型
			userInfo.setMobile(user.getMobile());//用户手机号码
			userInfo.setPhone(user.getTelephone());//用户电话
			userInfo.setFax(user.getFax());//用户传真
			userInfo.setEmail(user.getEmail());//用户email
			int status = user.getUserStatus()==0?1:0;
			userInfo.setStatus((long)status);//状态 
			int ordernum =  user.getUserOrderby();
			userInfo.setOrdernum((long)ordernum);//排序值
			userInfo.setImage(user.getPortralURI());//图片存放路径
			userInfo.setDepid(user.getDeptID());//行政部门id
			userInfo.setDepname(user.getDeptName());//行政部门名称
			//userInfo.setGroupid(groupid);//所属组id
			//userInfo.setGroupname(groupname);//所属组名称
			//userInfo.setPtdepid(ptdepid);//兼职部门id
			//userInfo.setPtdepname(ptdepname);//兼职部门名称
			userInfo.setIpaddress(String.valueOf(user.getAdrIP()));//ip地址
			//userInfo.setMsn(user.get);//msn
			//userInfo.setQq(qq);//qq
			userInfo.setRemark(user.getNote());//备注
			//userInfo.setSystemskin(user.getStyle());//用户登录系统的样式
			userInfo.setCreater(loginname);//创建人
			userInfo.setCreatetime(user.getRegisterDate()/1000);//创建时间
			userInfo.setLastmodifier(loginname);//修改人
			userInfo.setLastmodifytime(user.getRegisterDate()/1000);//修改时间
			String userid = StringUtils.checkNullString(userService.addUserInfo(userInfo));
			RecordLog.printLog("JMS receive PASM opertype:添加用户消息:第"+(u+1)+"个用户添加--end", RecordLog.LOG_LEVEL_INFO);
			if(userid!=""){
				RecordLog.printLog("JMS receive PASM opertype:添加用户消息:用户="+user.getUserAccount()+",--成功！", RecordLog.LOG_LEVEL_INFO);
			}else{
				RecordLog.printLog("JMS receive PASM opertype:添加用户消息:用户="+user.getUserAccount()+",--失败！", RecordLog.LOG_LEVEL_ERROR);
			}
		}
	}
	
	/**
	 * 修改用户消息
	 * @param userList
	 */
	public static void editUser(List<User> userList){
		int userListLen = 0;
		if(userList!=null)
			userListLen = userList.size();
		UserInfo userInfo;
		User user;
		String loginname = GetUserUtil.getUsername();
		for(int u = 0;u<userListLen;u++){
			RecordLog.printLog("JMS receive PASM opertype:修改用户消息:第"+(u+1)+"个用户修改--start", RecordLog.LOG_LEVEL_INFO);
			user = userList.get(u);
			userInfo = userService.getUserByLoginName(user.getUserAccount());
			if(userInfo==null)
				continue;
			
			userInfo.setFullname(user.getUserName());//用户中文名
			userInfo.setPwd(crypt.encode(user.getPass()));//用户密码
			userInfo.setPosition(user.getDuty_id());//用户职位
			userInfo.setType(user.getUserType_id());//用户类型
			userInfo.setMobile(user.getMobile());//用户手机号码
			userInfo.setPhone(user.getTelephone());//用户电话
			userInfo.setFax(user.getFax());//用户传真
			userInfo.setEmail(user.getEmail());//用户email
			int status = user.getUserStatus()==0?1:0;
			userInfo.setStatus((long)status);//状态 
			int ordernum =  user.getUserOrderby();
			userInfo.setOrdernum((long)ordernum);//排序值
			userInfo.setImage(user.getPortralURI());//图片存放路径
			userInfo.setDepid(user.getDeptID());//行政部门id
			userInfo.setDepname(user.getDeptName());//行政部门名称
			//userInfo.setGroupid(groupid);//所属组id
			//userInfo.setGroupname(groupname);//所属组名称
			//userInfo.setPtdepid(ptdepid);//兼职部门id
			//userInfo.setPtdepname(ptdepname);//兼职部门名称
			userInfo.setIpaddress(String.valueOf(user.getAdrIP()));//ip地址
			//userInfo.setMsn(user.get);//msn
			//userInfo.setQq(qq);//qq
			userInfo.setRemark(user.getNote());//备注
			//userInfo.setSystemskin(user.getStyle());//用户登录系统的样式
			userInfo.setLastmodifier(loginname);//修改人
			userInfo.setLastmodifytime(user.getRegisterDate()/1000);//修改时间
			String userid = StringUtils.checkNullString(userService.updateUserInfo(userInfo));
			RecordLog.printLog("JMS receive PASM opertype:修改用户消息:第"+(u+1)+"个用户修改--end", RecordLog.LOG_LEVEL_INFO);
			if(userid!=""){
				RecordLog.printLog("JMS receive PASM opertype:修改用户消息:用户="+user.getUserAccount()+",--成功！", RecordLog.LOG_LEVEL_INFO);
			}else{
				RecordLog.printLog("JMS receive PASM opertype:修改用户消息:用户="+user.getUserAccount()+",--失败！", RecordLog.LOG_LEVEL_ERROR);
			}
		}
	}
	
	/**
	 * 删除用户消息
	 * @param userAccountList 用户账号列表
	 */
	public static void delUser(List<String> userAccountList){
		int userAccountListLen = 0;
		if(userAccountList!=null)
			userAccountListLen = userAccountList.size();
		String account = "";
		UserInfo userInfo;
		for(int u = 0;u<userAccountListLen;u++){
			RecordLog.printLog("JMS receive PASM opertype:删除用户消息:第"+(u+1)+"个用户删除--start", RecordLog.LOG_LEVEL_INFO);
			account = userAccountList.get(u);
			userInfo = userService.getUserByLoginName(account);
			if(userInfo==null)
				continue;
			userInfo.setStatus((long)0);
			userInfo.setLastmodifier(loginname);
			userInfo.setLastmodifytime(TimeUtils.getCurrentTime());
			String userid = StringUtils.checkNullString(userService.updateUserInfo(userInfo));
			RecordLog.printLog("JMS receive PASM opertype:删除用户消息:第"+(u+1)+"个用户删除--end", RecordLog.LOG_LEVEL_INFO);
			if(userid!=""){
				RecordLog.printLog("JMS receive PASM opertype:删除用户消息:用户="+account+",--成功！", RecordLog.LOG_LEVEL_INFO);
			}else{
				RecordLog.printLog("JMS receive PASM opertype:删除用户消息:用户="+account+",--失败！", RecordLog.LOG_LEVEL_ERROR);
			}
		}
	}
	
	/**
	 * 修改用户密码消息
	 * @param map Map中的 key是用户账号；value是新密码
	 */
	public static void updateUserPass(List<Map> mapList){
		int mapListLen = 0;
		if(mapList!=null)
			mapListLen = mapList.size();
		for(int m = 0;m<mapListLen;m++){
			HashMap<String,String> map = (HashMap<String, String>)mapList.get(m);
			String userAccount = "";
			String newPass = "";
			UserInfo userInfo;
			for(Map.Entry<String, String> entry : map.entrySet()){
				userAccount = entry.getKey().toString();
				newPass = entry.getValue().toString();
				userInfo = userService.getUserByLoginName(userAccount);
				if(userInfo==null)
					continue;
				
				userInfo.setPwd(crypt.encode(newPass));
				userInfo.setLastmodifier(loginname);
				userInfo.setLastmodifytime(TimeUtils.getCurrentTime());
				RecordLog.printLog("JMS receive PASM opertype:修改用户密码消息:用户--"+userAccount+"--start", RecordLog.LOG_LEVEL_INFO);
				String userid = StringUtils.checkNullString(userService.updateUserInfo(userInfo));
				RecordLog.printLog("JMS receive PASM opertype:修改用户密码消息:用户--"+userAccount+"--end", RecordLog.LOG_LEVEL_INFO);
				if(userid!=""){
					RecordLog.printLog("JMS receive PASM opertype:修改用户密码消息:用户="+userAccount+",--成功！", RecordLog.LOG_LEVEL_INFO);
				}else{
					RecordLog.printLog("JMS receive PASM opertype:修改用户密码消息:用户="+userAccount+",--失败！", RecordLog.LOG_LEVEL_ERROR);
				}
			}
		}
	}
	
	/**
	 * 增加组织机构（用户组）消息
	 * @param organiseList
	 */
	public static void addOrganise(List<Organise> organiseList){
		int organiseListLen = 0;
		if(organiseList!=null)
			organiseListLen = organiseList.size();
		DepInfo depInfo;
		Organise organise;
		for(int d = 0;d<organiseListLen;d++){
			RecordLog.printLog("JMS receive PASM opertype:增加组织机构(用户组)消息:第"+(d+1)+"个组织(用户组)添加--start", RecordLog.LOG_LEVEL_INFO);
			depInfo = new DepInfo();
			organise = organiseList.get(d);
			depInfo.setPid(organise.getDept_id());
			depInfo.setDepname(organise.getDept_name());
			//depInfo.setDepdn(depdn);
			depInfo.setDepdns(organise.getGroup_dnid());
			//depInfo.setDepemail(depemail);
			depInfo.setDepfax(organise.getOrg_fax());
			depInfo.setDepfullname(organise.getGroup_dnname());
			//depInfo.setDepimage(depimage);
			depInfo.setDepphone(organise.getOrg_phone());
			depInfo.setDeptype("4");//默认类型设置成 “组”
			depInfo.setOrdernum((long)organise.getOrg_orderBy());
			depInfo.setParentid(organise.getSuper_id().equals("1")?"0":organise.getSuper_id());
			depInfo.setStatus((long)(organise.getState()==0?1:0));
			depInfo.setRemark(organise.getNode());
			depInfo.setCreater(loginname);
			depInfo.setCreatetime(TimeUtils.getCurrentTime());
			depInfo.setLastmodifier(loginname);
			depInfo.setLastmodifytime(TimeUtils.getCurrentTime());
			String depid = StringUtils.checkNullString(depManagerService.addDepInfo(depInfo));
			RecordLog.printLog("JMS receive PASM opertype:增加组织机构(用户组)消息:第"+(d+1)+"个组织(用户组)添加--end", RecordLog.LOG_LEVEL_INFO);
			if(depid!=""){
				RecordLog.printLog("JMS receive PASM opertype:增加组织机构(用户组)消息:组织(用户组)="+organise.getDept_name()+",--成功！", RecordLog.LOG_LEVEL_INFO);
			}else{
				RecordLog.printLog("JMS receive PASM opertype:增加组织机构(用户组)消息:组织(用户组)="+organise.getDept_name()+",--失败！", RecordLog.LOG_LEVEL_ERROR);
			}
		}
	}
	
	/**
	 * 修改组织机构（用户组）消息
	 * @param organiseList
	 */
	public static void editOrganise(List<Organise> organiseList){
		int organiseListLen = 0;
		if(organiseList!=null)
			organiseListLen = organiseList.size();
		DepInfo depInfo;
		Organise organise;
		for(int d = 0;d<organiseListLen;d++){
			RecordLog.printLog("JMS receive PASM opertype:修改组织机构(用户组)消息:第"+(d+1)+"个组织(用户组)修改--start", RecordLog.LOG_LEVEL_INFO);
			organise = organiseList.get(d);
			depInfo = depManagerService.getDepByID(organise.getDept_id());
			if(depInfo==null)
				continue;
			
			depInfo.setDepname(organise.getDept_name());
			//depInfo.setDepdn(depdn);
			depInfo.setDepdns(organise.getGroup_dnid());
			//depInfo.setDepemail(depemail);
			depInfo.setDepfax(organise.getOrg_fax());
			depInfo.setDepfullname(organise.getGroup_dnname());
			//depInfo.setDepimage(depimage);
			depInfo.setDepphone(organise.getOrg_phone());
			depInfo.setOrdernum((long)organise.getOrg_orderBy());
			depInfo.setParentid(organise.getSuper_id().equals("1")?"0":organise.getSuper_id());
			depInfo.setStatus((long)(organise.getState()==0?1:0));
			depInfo.setRemark(organise.getNode());
			depInfo.setLastmodifier(loginname);
			depInfo.setLastmodifytime(TimeUtils.getCurrentTime());
			
			String depid = StringUtils.checkNullString(depManagerService.updateDepInfo(depInfo));
			RecordLog.printLog("JMS receive PASM opertype:修改组织机构(用户组)消息:第"+(d+1)+"个组织(用户组)修改--end", RecordLog.LOG_LEVEL_INFO);
			if(depid!=""){
				RecordLog.printLog("JMS receive PASM opertype:修改组织机构(用户组)消息:组织(用户组)="+organise.getDept_name()+",--成功！", RecordLog.LOG_LEVEL_INFO);
			}else{
				RecordLog.printLog("JMS receive PASM opertype:修改组织机构(用户组)消息:组织(用户组)="+organise.getDept_name()+",--失败！", RecordLog.LOG_LEVEL_ERROR);
			}
		}
	}
	
	/**
	 * 删除组织机构（用户组）消息
	 * @param organiseIdList 组织机构ＩＤ列表
	 */
	public static void delOrganise(List<String> organiseIdList){
		int organiseIdListLen = 0;
		if(organiseIdList!=null)
			organiseIdListLen = organiseIdList.size();
		String organiseId = "";
		DepInfo depInfo;
		for(int i = 0;i<organiseIdListLen;i++){
			RecordLog.printLog("JMS receive PASM opertype:删除组织机构(用户组)消息:第"+(i+1)+"个组织(用户组)删除--start", RecordLog.LOG_LEVEL_INFO);
			organiseId = organiseIdList.get(i);
			depInfo = depManagerService.getDepByID(organiseId);
			if(depInfo==null)
				continue;
			
			depInfo.setStatus((long)0);
			depInfo.setLastmodifier(loginname);
			depInfo.setLastmodifytime(TimeUtils.getCurrentTime());
			
			String depid = StringUtils.checkNullString(depManagerService.updateDepInfo(depInfo));
			RecordLog.printLog("JMS receive PASM opertype:删除组织机构(用户组)消息:第"+(i+1)+"个组织(用户组)删除--end", RecordLog.LOG_LEVEL_INFO);
			if(depid!=""){
				RecordLog.printLog("JMS receive PASM opertype:删除组织机构(用户组)消息:组织(用户组)="+depInfo.getDepname()+",--成功！", RecordLog.LOG_LEVEL_INFO);
			}else{
				RecordLog.printLog("JMS receive PASM opertype:删除组织机构(用户组)消息:组织(用户组)="+depInfo.getDepname()+",--失败！", RecordLog.LOG_LEVEL_ERROR);
			}
		}
	}
	
	/**
	 * 添加角色消息
	 * @param roleList
	 */
	public static void addRole(List<Role> roleList){
		int roleListLen = 0;
		if(roleList!=null)
			roleListLen = roleList.size();
		Role role;
		RoleInfo roleInfo;
		for(int r = 0;r<roleListLen;r++){
			roleInfo = new RoleInfo();
			role = roleList.get(r);
			RecordLog.printLog("JMS receive PASM opertype:添加角色消息:第"+(r+1)+"个角色添加--start", RecordLog.LOG_LEVEL_INFO);
			roleInfo.setPid(role.getId());
			roleInfo.setRolename(role.getName());
			roleInfo.setRoledn("");
			roleInfo.setRoledns("");
			roleInfo.setParentid(role.getSuper_id().equals("")?"0":role.getSuper_id());
			roleInfo.setDefinetype("");
			roleInfo.setCreater(loginname);
			roleInfo.setCreatetime(TimeUtils.getCurrentTime());
			roleInfo.setLastmodifier(loginname);
			roleInfo.setLastmodifytime(TimeUtils.getCurrentTime());
			roleInfo.setRemark(role.getNote());
			String roleid = StringUtils.checkNullString(roleManagerService.addRoleInfo(roleInfo));
			RecordLog.printLog("JMS receive PASM opertype:添加角色消息:第"+(r+1)+"个角色添加--end", RecordLog.LOG_LEVEL_INFO);
			if(roleid!=""){
				RecordLog.printLog("JMS receive PASM opertype:添加角色消息:角色="+role.getName()+",--成功！", RecordLog.LOG_LEVEL_INFO);
			}else{
				RecordLog.printLog("JMS receive PASM opertype:添加角色消息:角色="+role.getName()+",--失败！", RecordLog.LOG_LEVEL_ERROR);
			}
		}
	}
	
	/**
	 * 修改角色消息
	 * @param roleList
	 */
	public static void editRole(List<Role> roleList){
		int roleListLen = 0;
		if(roleList!=null)
			roleListLen = roleList.size();
		Role role;
		RoleInfo roleInfo;
		for(int r = 0;r<roleListLen;r++){
			role = roleList.get(r);
			roleInfo = roleManagerService.getRoleByID(role.getId());
			
			if(roleInfo==null)
				continue;
			
			RecordLog.printLog("JMS receive PASM opertype:修改角色消息:第"+(r+1)+"个角色修改--start", RecordLog.LOG_LEVEL_INFO);
			roleInfo.setRolename(role.getName());
			roleInfo.setParentid(role.getSuper_id().equals("")?"0":role.getSuper_id());
			roleInfo.setLastmodifier(loginname);
			roleInfo.setLastmodifytime(TimeUtils.getCurrentTime());
			roleInfo.setRemark(role.getNote());
			
			boolean flag = roleManagerService.updateRoleInfo(roleInfo);
			RecordLog.printLog("JMS receive PASM opertype:修改角色消息:第"+(r+1)+"个角色修改--end", RecordLog.LOG_LEVEL_INFO);
			if(flag){
				RecordLog.printLog("JMS receive PASM opertype:修改角色消息:角色="+role.getName()+",--成功！", RecordLog.LOG_LEVEL_INFO);
			}else{
				RecordLog.printLog("JMS receive PASM opertype:修改角色消息:角色="+role.getName()+",--失败！", RecordLog.LOG_LEVEL_ERROR);
			}
		}
	}
	
	/**
	 * 删除角色消息
	 * @param roleIdList 被删除的角色ID列表
	 */
	public static void delRole(List<String> roleIdList){
		int roleIdListLen = 0;
		if(roleIdList!=null)
			roleIdListLen = roleIdList.size();
		String roleid = "";
		RoleInfo roleInfo;
		for(int r = 0;r<roleIdListLen;r++){
			roleid = roleIdList.get(r);
			roleInfo = roleManagerService.getRoleByID(roleid);
			RecordLog.printLog("JMS receive PASM opertype:删除角色消息:第"+(r+1)+"个角色删除--start", RecordLog.LOG_LEVEL_INFO);
			if(roleInfo==null)
				continue;
			
			boolean flag = roleManagerService.deleteRoleByID(roleid);
			RecordLog.printLog("JMS receive PASM opertype:删除角色消息:第"+(r+1)+"个角色删除--end", RecordLog.LOG_LEVEL_INFO);
			if(flag){
				RecordLog.printLog("JMS receive PASM opertype:删除角色消息:角色="+roleInfo.getRolename()+",--成功！", RecordLog.LOG_LEVEL_INFO);
			}else{
				RecordLog.printLog("JMS receive PASM opertype:删除角色消息:角色="+roleInfo.getRolename()+",--失败！", RecordLog.LOG_LEVEL_ERROR);
			}
			
		}
	}
	
	/**
	 * 用户角色绑定变更消息
	 * @param userRoleList 用户与角色绑定对象列表，表示一个用户重新绑定后的所有角色
	 */
	public static void editUserRole(List<UserRole> userRoleList){
		int userRoleListLen = 0;
		if(userRoleList!=null)
			userRoleListLen = userRoleList.size();
		UserRole userRole;
		String userid = "";
		List<String> roleidList = new ArrayList<String>();
		for(int ur = 0;ur<userRoleListLen;ur++){
			userRole = userRoleList.get(ur);
			userid = StringUtils.checkNullString(userRole.getUserId());
			roleidList.add(StringUtils.checkNullString(userRole.getRoleId()));
		}
		if(userid!="" && roleidList.size()>0){
			RecordLog.printLog("JMS receive PASM opertype:用户角色绑定变更消息:删除用户="+userid+"绑定的角色--start", RecordLog.LOG_LEVEL_INFO);
			boolean flag = roleManagerService.deleteUserRole(userid, roleidList);
			RecordLog.printLog("JMS receive PASM opertype:用户角色绑定变更消息:删除用户="+userid+"绑定的角色--end", RecordLog.LOG_LEVEL_INFO);
			if(flag){
				RecordLog.printLog("JMS receive PASM opertype:用户角色绑定变更消息:删除用户="+userid+"绑定的角色--成功", RecordLog.LOG_LEVEL_INFO);
				RecordLog.printLog("JMS receive PASM opertype:用户角色绑定变更消息:添加用户="+userid+"绑定的新角色--start", RecordLog.LOG_LEVEL_INFO);
				boolean flag1 = roleManagerService.addUserRole(userid, roleidList);
				RecordLog.printLog("JMS receive PASM opertype:用户角色绑定变更消息:添加用户="+userid+"绑定的新角色--end", RecordLog.LOG_LEVEL_INFO);
				if(flag1){
					RecordLog.printLog("JMS receive PASM opertype:用户角色绑定变更消息:添加用户="+userid+"绑定的新角色--成功", RecordLog.LOG_LEVEL_INFO);
				}else{
					RecordLog.printLog("JMS receive PASM opertype:用户角色绑定变更消息:添加用户="+userid+"绑定的新角色--失败", RecordLog.LOG_LEVEL_INFO);
				}
			}else{
				RecordLog.printLog("JMS receive PASM opertype:用户角色绑定变更消息:删除用户="+userid+"绑定的角色--失败", RecordLog.LOG_LEVEL_INFO);
			}
		}
	}
}
