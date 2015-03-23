package com.ultrapower.eoms.common.external.manager;

import java.util.ArrayList;
import java.util.List;
import com.ultrapower.accredit.api.SecurityService;
import com.ultrapower.accredit.common.value.GroupUser;
import com.ultrapower.accredit.common.value.Organise;
import com.ultrapower.accredit.common.value.Role;
import com.ultrapower.accredit.common.value.User;
import com.ultrapower.accredit.rmiclient.RmiClientApplication;
import com.ultrapower.eoms.common.RecordLog;
import com.ultrapower.eoms.common.constants.ConstantsSynch;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.external.service.EomsInfoDownPasm;
import com.ultrapower.eoms.ultrasm.model.DepInfo;
import com.ultrapower.eoms.ultrasm.model.RoleInfo;
import com.ultrapower.eoms.ultrasm.model.UserDep;
import com.ultrapower.eoms.ultrasm.model.UserInfo;

/**
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-12-29 上午11:29:29
 * @descibe 应用系统信息下行同步到pasm系统服务实现类
 */
public class EomsInfoDownPasmImpl implements EomsInfoDownPasm {
	
	
	public boolean synchAddUserInfo(UserInfo userInfo, List<String> depId) {
		boolean flag = false;
		if(ConstantsSynch.isSynch){
			RecordLog.printLog("userinfo add_synch to pasm is start", RecordLog.LOG_LEVEL_INFO);
			SecurityService service = RmiClientApplication.getInstance().getSecurityService();
			if(service!=null){
				if(userInfo!=null && StringUtils.checkNullString(userInfo.getPid())!=""){
					User user = new User();
					user.setUserAccount(StringUtils.checkNullString(userInfo.getLoginname()));//登录名
					user.setUserName(StringUtils.checkNullString(userInfo.getFullname()));//全名
					user.setPass(StringUtils.checkNullString(userInfo.getPwd()));//密码
					user.setMobile(StringUtils.checkNullString(userInfo.getMobile()));//手机
					user.setTelephone(StringUtils.checkNullString(userInfo.getPhone()));//固定电话
					user.setEmail(StringUtils.checkNullString(userInfo.getEmail()));//E-mail
					user.setFax(StringUtils.checkNullString(userInfo.getFax()));//传真
					user.setUserStatus(userInfo.getStatus()==1?0:1);//状态
					user.setUserOrderby(userInfo.getOrdernum().intValue());//排序值
					user.setNote(StringUtils.checkNullString(userInfo.getRemark()));//备注
					user.setPortralURI(StringUtils.checkNullString(userInfo.getImage()));//头像
					user.setDuty_id(StringUtils.checkNullString(userInfo.getPosition()));//用户职位
					user.setUserType_id("0");//用户类型
					//user.setUserCardNum();//员工编号
					user.setMemo(StringUtils.checkNullString(userInfo.getPid()));
					user.setSysUser(0);//是否为系统用户(0:普通用户/1:系统内置用户(root))
					user.setLastModifyOperateDate(System.currentTimeMillis());
					user.setDeptID(StringUtils.checkNullString(userInfo.getDepid()));
					user.setDeptName(StringUtils.checkNullString(userInfo.getDepname()));
					//０:成功/１:失败/２:失败，有重复账号
					int result = service.addUser(user);
					if(result==0){
						RecordLog.printLog("userinfo add_synch succeed,pasm addUser server succeed!", RecordLog.LOG_LEVEL_ERROR);
						int depIdLen = 0;
						if(depId!=null)
							depIdLen = depId.size();
						List<GroupUser> groupUserList = new ArrayList<GroupUser>();
						GroupUser groupUser;
						for(int i=0;i<depIdLen;i++){
							groupUser = new GroupUser();
							groupUser.setDept_id(StringUtils.checkNullString(depId.get(i)));
							groupUser.setUserAccount(StringUtils.checkNullString(userInfo.getLoginname()));
							groupUser.setType(0);
							groupUserList.add(groupUser);
						}
						if(depIdLen==0){
							flag = true;
						}else{
							try{
								int result1 = service.addGroupUser(groupUserList);
								if(result1==0){
									flag = true;
								}else{
									RecordLog.printLog("groupuser add_synch failure,pasm addGroupUser server wrong!", RecordLog.LOG_LEVEL_ERROR);
								}
							} catch (Exception e) {
								RecordLog.printLog("groupuser add_synch failure,pasm addGroupUser server error!"+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
								e.printStackTrace();
							}
						}
					}else if(result==2){
						RecordLog.printLog("userinfo add_synch failure,pasm is exit same accounts!", RecordLog.LOG_LEVEL_ERROR);
					}else{
						RecordLog.printLog("userinfo add_synch failure,pasm addUser server wrong!", RecordLog.LOG_LEVEL_ERROR);
					}			
				}else{
					RecordLog.printLog("userinfo add_synch failure,userinfo is null!", RecordLog.LOG_LEVEL_ERROR);
				}
			}else{
				RecordLog.printLog("userinfo add_synch failure,pasm server wrong!", RecordLog.LOG_LEVEL_ERROR);
			}
			RecordLog.printLog("userinfo add_synch to pasm is end", RecordLog.LOG_LEVEL_INFO);
		}else{
			flag = true;
		}
		return flag;
	}
	
	public boolean synchUpdateUserInfo(UserInfo userInfo,List<String> delDepId,List<String> addDepId) {
		boolean flag = false;
		if(ConstantsSynch.isSynch){
			RecordLog.printLog("userinfo update_synch to pasm is start", RecordLog.LOG_LEVEL_INFO);
			SecurityService service = RmiClientApplication.getInstance().getSecurityService();
			if(service!=null){
				if(userInfo!=null && userInfo.getPid()!=null){
					try {
						User user = service.getUserByAccount(StringUtils.checkNullString(userInfo.getLoginname()));
						user.setUserAccount(StringUtils.checkNullString(userInfo.getLoginname()));//登录名
						user.setUserName(StringUtils.checkNullString(userInfo.getFullname()));//全名
						//user.setPass(StringUtils.checkNullString(userInfo.getPwd()));//密码
						user.setMobile(StringUtils.checkNullString(userInfo.getMobile()));//手机
						user.setTelephone(StringUtils.checkNullString(userInfo.getPhone()));//固定电话
						user.setEmail(StringUtils.checkNullString(userInfo.getEmail()));//E-mail
						user.setFax(StringUtils.checkNullString(userInfo.getFax()));//传真
						user.setUserStatus(userInfo.getStatus()==1?0:1);//状态
						user.setUserOrderby(userInfo.getOrdernum().intValue());//排序值
						user.setNote(StringUtils.checkNullString(userInfo.getRemark()));//备注
						user.setPortralURI(StringUtils.checkNullString(userInfo.getImage()));//头像
						user.setDuty_id(StringUtils.checkNullString(userInfo.getPosition()));//用户职位
						user.setUserType_id(StringUtils.checkNullString("0"));//用户类型
						//user.setUserCardNum();//员工编号
						user.setMemo(StringUtils.checkNullString(userInfo.getPid()));
						user.setDeptID(StringUtils.checkNullString(userInfo.getDepid()));//部门id
						user.setLastModifyOperateDate(System.currentTimeMillis());
						user.setDeptName(StringUtils.checkNullString(userInfo.getDepname()));//部门名称
						user.setLastDate(userInfo.getLastlogintime()*1000);
						int updatepass = service.clientChangePassWord(StringUtils.checkNullString(userInfo.getLoginname()), StringUtils.checkNullString(userInfo.getPwd()));
						if(updatepass>0){
							int result = service.updateUser(user);
							if(result==0){
								//删除组用户关系
								int delDepIdLen = 0;
								if(delDepId!=null)
									delDepIdLen = delDepId.size();
								List<GroupUser> groupUserList_del = new ArrayList<GroupUser>();
								GroupUser groupUser_del;
								for(int d=0;d<delDepIdLen;d++){
									groupUser_del = new GroupUser();
									groupUser_del.setType(0);
									groupUser_del.setDept_id(StringUtils.checkNullString(delDepId.get(d)));
									groupUser_del.setUserAccount(StringUtils.checkNullString(userInfo.getLoginname()));
									groupUserList_del.add(groupUser_del);
								}
								service.removeGroupUser(groupUserList_del);
								
								//添加组用户关系
								int addDepIdLen = 0;
								if(addDepId!=null)
									addDepIdLen = addDepId.size();
								List<GroupUser> groupUserList_add = new ArrayList<GroupUser>();
								GroupUser groupUser_add;
								for(int a=0;a<addDepIdLen;a++){
									groupUser_add = new GroupUser();
									groupUser_add.setType(0);
									groupUser_add.setDept_id(StringUtils.checkNullString(addDepId.get(a)));
									groupUser_add.setUserAccount(StringUtils.checkNullString(userInfo.getLoginname()));
									groupUserList_add.add(groupUser_add);
								}
								if(addDepIdLen==0){
									flag = true;
								}else{
									result = service.addGroupUser(groupUserList_add);
									if(result==0)
										flag = true;
									else
										RecordLog.printLog("userinfo update_synch failure,pasm addGroupUser server wrong!", RecordLog.LOG_LEVEL_ERROR);
								}
							}else{
								RecordLog.printLog("userinfo update_synch failure,pasm updateUser server wrong!", RecordLog.LOG_LEVEL_ERROR);
							}
						}else{
							RecordLog.printLog("userinfo update_synch failure,pasm clientChangePassWord server wrong!", RecordLog.LOG_LEVEL_ERROR);
						}
					} catch (Exception e) {
						RecordLog.printLog("Get pasm UserInfo by Account failure,pasm updateUser server error!"+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
						e.printStackTrace();
					}
				}else{
					RecordLog.printLog("userinfo update_synch failure,userinfo is null!", RecordLog.LOG_LEVEL_ERROR);
				}
			}else{
				RecordLog.printLog("userinfo update_synch failure,pasm server wrong!", RecordLog.LOG_LEVEL_ERROR);
			}
			RecordLog.printLog("userinfo update_synch to pasm is end", RecordLog.LOG_LEVEL_INFO);
		}else{
			flag = true;
		}
		return flag;
	}
	
	public boolean synchDelUserInfo(UserInfo userInfo, List<String> depId) {
		boolean flag = false;
		if(ConstantsSynch.isSynch){
			RecordLog.printLog("userinfo del_synch to pasm is start", RecordLog.LOG_LEVEL_INFO);
			SecurityService service = RmiClientApplication.getInstance().getSecurityService();
			if(service!=null){
				if(userInfo!=null && StringUtils.checkNullString(userInfo.getPid())!=""){
					int result = service.removeUser(StringUtils.checkNullString(userInfo.getLoginname()));
					if(result==0){
						RecordLog.printLog("userinfo del_synch succeed!", RecordLog.LOG_LEVEL_ERROR);
						int depIdLen = 0;
						if(depId!=null)
							depIdLen = depId.size();
						List<GroupUser> groupUserList = new ArrayList<GroupUser>();
						GroupUser groupUser;
						for(int i=0;i<depIdLen;i++){
							groupUser = new GroupUser();
							groupUser.setDept_id(StringUtils.checkNullString(depId.get(i)));
							groupUser.setUserAccount(StringUtils.checkNullString(userInfo.getLoginname()));
							groupUser.setType(0);
							groupUserList.add(groupUser);
						}
						if(depIdLen==0){
							flag = true;
						}else{
							try {
								int result1 = service.removeGroupUser(groupUserList);
								if(result1==0){
									flag = true;
								}else{
									RecordLog.printLog("groupuser del_synch failure,pasm removeGroupUser server wrong!", RecordLog.LOG_LEVEL_ERROR);
								}
							} catch (Exception e) {
								RecordLog.printLog("groupuser del_synch failure,pasm removeGroupUser server error!"+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
								e.printStackTrace();
							}
						}
					}else{
						RecordLog.printLog("userinfo del_synch failure,pasm removeUser server wrong!", RecordLog.LOG_LEVEL_ERROR);
					}
				}else{
					RecordLog.printLog("userinfo del_synch failure,userinfo is null!", RecordLog.LOG_LEVEL_ERROR);
				}
			}else{
				RecordLog.printLog("userinfo del_synch failure,pasm server wrong!", RecordLog.LOG_LEVEL_ERROR);
			}
			RecordLog.printLog("userinfo del_synch to pasm is end", RecordLog.LOG_LEVEL_INFO);
		}else{
			flag = true;
		}
		return flag;
	}
	
	public boolean synchAddDepInfo(DepInfo depInfo, List<String> userInfo) {
		boolean flag = false;
		if(ConstantsSynch.isSynch){
			RecordLog.printLog("depinfo add_synch to pasm is start", RecordLog.LOG_LEVEL_INFO);
			SecurityService service = RmiClientApplication.getInstance().getSecurityService();
			if(service!=null){
				if(depInfo!=null && StringUtils.checkNullString(depInfo.getPid())!=""){
					Organise organise = new Organise();
					organise.setDept_id(StringUtils.checkNullString(depInfo.getPid()));//部门id
					organise.setDept_name(StringUtils.checkNullString(depInfo.getDepname()));//部门名称
					organise.setNode(StringUtils.checkNullString(depInfo.getRemark()));
					organise.setOrg_fax(StringUtils.checkNullString(depInfo.getDepfax()));
					organise.setOrg_phone(StringUtils.checkNullString(depInfo.getDepphone()));
					organise.setOrg_orderBy(depInfo.getOrdernum().intValue());
					organise.setState(depInfo.getStatus()==1?0:1);
					organise.setOrg_status(depInfo.getStatus()==1?0:1);
					organise.setSuper_id(StringUtils.checkNullString(depInfo.getParentid().equals("0")?"1":depInfo.getParentid()));
					organise.setType(depInfo.getDeptype()!=""?0:0);
					organise.setSys_group(0);
					organise.setGroup_dnid(StringUtils.checkNullString(depInfo.getDepdns()));
					organise.setGroup_dnname(StringUtils.checkNullString(depInfo.getDepfullname()));
					
					int userInfoLen = 0;
					if(userInfo!=null)
						userInfoLen = userInfo.size();
					List<GroupUser> leaguers = new ArrayList<GroupUser>();
					GroupUser groupUser;
					for(int i=0;i<userInfoLen;i++){
						groupUser = new GroupUser();
						groupUser.setDept_id(StringUtils.checkNullString(depInfo.getPid()));
						groupUser.setUserAccount(StringUtils.checkNullString(userInfo.get(i)));
						groupUser.setType(0);
						leaguers.add(groupUser);
					}
					organise.setLeaguers(leaguers);
					flag = service.addOrganise(organise);
					if(!flag)
						RecordLog.printLog("depinfo add_synch failure,pasm addOrganise server wrong!", RecordLog.LOG_LEVEL_ERROR);
				}else{
					RecordLog.printLog("depinfo add_synch failure,depinfo is null!", RecordLog.LOG_LEVEL_ERROR);
				}
			}else{
				RecordLog.printLog("depinfo add_synch failure,pasm server wrong!", RecordLog.LOG_LEVEL_ERROR);
			}
			RecordLog.printLog("depinfo add_synch to pasm is end", RecordLog.LOG_LEVEL_INFO);
		}else{
			flag = true;
		}
		return flag;
	}
	
	public boolean synchUpdateDepInfo(DepInfo depInfo, List<String> userInfo) {
		boolean flag = false;
		if(ConstantsSynch.isSynch){
			RecordLog.printLog("depinfo update_synch to pasm is start", RecordLog.LOG_LEVEL_INFO);
			SecurityService service = RmiClientApplication.getInstance().getSecurityService();
			if(service!=null){
				if(depInfo!=null && StringUtils.checkNullString(depInfo.getPid())!=""){
					Organise organise = null;
					try {
						organise = service.getOrganise(StringUtils.checkNullString(depInfo.getPid()));
					} catch (Exception e) {
						RecordLog.printLog("depinfo update_synch failure,pasm getOrganise server error!"+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
						e.printStackTrace();
					}
					if(organise!=null){
						organise.setDept_id(StringUtils.checkNullString(depInfo.getPid()));//部门id
						organise.setDept_name(StringUtils.checkNullString(depInfo.getDepname()));//部门名称
						organise.setNode(StringUtils.checkNullString(depInfo.getRemark()));
						organise.setOrg_fax(StringUtils.checkNullString(depInfo.getDepfax()));
						organise.setOrg_orderBy(depInfo.getOrdernum().intValue());
						organise.setState(depInfo.getStatus()==1?0:1);
						organise.setOrg_status(depInfo.getStatus()==1?0:1);
						organise.setSuper_id(StringUtils.checkNullString(depInfo.getParentid().equals("0")?"1":depInfo.getParentid()));
						organise.setType(depInfo.getDeptype()!=""?0:0);
						organise.setSys_group(0);
						organise.setGroup_dnid(StringUtils.checkNullString(depInfo.getDepdns()));
						organise.setGroup_dnname(StringUtils.checkNullString(depInfo.getDepfullname()));
						int userInfoLen = 0;
						if(userInfo!=null)
							userInfoLen = userInfo.size();
						List<GroupUser> leaguers = new ArrayList<GroupUser>();
						GroupUser groupUser;
						for(int i=0;i<userInfoLen;i++){
							groupUser = new GroupUser();
							groupUser.setDept_id(StringUtils.checkNullString(depInfo.getPid()));
							groupUser.setUserAccount(StringUtils.checkNullString(userInfo.get(i)));
							groupUser.setType(0);
							leaguers.add(groupUser);
						}
						organise.setLeaguers(leaguers);
						flag = service.updateOrganise(organise);
						if(!flag)
							RecordLog.printLog("depinfo update_synch failure,pasm updateOrganise server wrong!", RecordLog.LOG_LEVEL_ERROR);
					}else{
						RecordLog.printLog("depinfo update_synch failure,depInfo is not exit of pasm!", RecordLog.LOG_LEVEL_ERROR);
					}
				}else{
					RecordLog.printLog("depinfo update_synch failure,depinfo is null!", RecordLog.LOG_LEVEL_ERROR);
				}
			}else{
				RecordLog.printLog("depinfo update_synch failure,pasm server wrong!", RecordLog.LOG_LEVEL_ERROR);
			}
			RecordLog.printLog("depinfo update_synch to pasm is end", RecordLog.LOG_LEVEL_INFO);
		}else{
			flag = true;
		}
		return flag;
	}
	
	public boolean synchDelDepInfo(DepInfo depInfo, List<String> userInfo) {
		boolean flag = false;
		if(ConstantsSynch.isSynch){
			RecordLog.printLog("depinfo del_synch to pasm is start", RecordLog.LOG_LEVEL_INFO);
			SecurityService service = RmiClientApplication.getInstance().getSecurityService();
			if(service!=null){
				if(depInfo!=null && StringUtils.checkNullString(depInfo.getPid())!=""){
					Organise organise = null;
					try {
						organise = service.getOrganise(depInfo.getPid());
					} catch (Exception e) {
						RecordLog.printLog("depinfo del_synch failure,pasm getOrganise server error!"+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
						e.printStackTrace();
					}
					if(organise!=null){
						flag = service.removeOrganise(organise);
						if(flag){
							int userInfoLen = 0;
							if(userInfo!=null)
								userInfoLen = userInfo.size();
							List<GroupUser> leaguers = new ArrayList<GroupUser>();
							GroupUser groupUser;
							for(int i=0;i<userInfoLen;i++){
								groupUser = new GroupUser();
								groupUser.setDept_id(StringUtils.checkNullString(organise.getDept_id()));
								groupUser.setUserAccount(StringUtils.checkNullString(userInfo.get(i)));
								groupUser.setType(0);
								leaguers.add(groupUser);
							}
							int result = 0;
							try {
								result = service.removeGroupUser(leaguers);
							} catch (Exception e) {
								RecordLog.printLog("depinfo del_synch failure,pasm removeGroupUser server error!"+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
								e.printStackTrace();
							}
							if(result!=0){
								flag = false;
								RecordLog.printLog("depinfo del_synch failure,pasm removeGroupUser server wrong!", RecordLog.LOG_LEVEL_ERROR);
							}
						}else{
							RecordLog.printLog("depinfo del_synch failure,pasm removeGroupUser server failure!", RecordLog.LOG_LEVEL_ERROR);
						}
					}else{
						RecordLog.printLog("depinfo del_synch failure,depInfo is not exit of pasm!", RecordLog.LOG_LEVEL_ERROR);
					}
				}else{
					RecordLog.printLog("depinfo del_synch failure,depinfo is null!", RecordLog.LOG_LEVEL_ERROR);
				}
			}else{
				RecordLog.printLog("depinfo del_synch failure,pasm server wrong!", RecordLog.LOG_LEVEL_ERROR);
			}
			RecordLog.printLog("depinfo del_synch to pasm is end", RecordLog.LOG_LEVEL_INFO);
		}else{
			flag = true;
		}
		return flag;
	}

	public boolean synchAddRoleInfo(RoleInfo roleInfo) {
		boolean flag = false;
		if(ConstantsSynch.isSynch){
			RecordLog.printLog("roleinfo add_synch to pasm is start", RecordLog.LOG_LEVEL_INFO);
			SecurityService service = RmiClientApplication.getInstance().getSecurityService();
			if(service!=null){
				if(roleInfo!=null){
					Role role = new Role();
					role.setId(StringUtils.checkNullString(roleInfo.getPid()));
					role.setName(StringUtils.checkNullString(roleInfo.getRolename()));
					role.setState(0);
					role.setSuper_id(StringUtils.checkNullString(roleInfo.getParentid()));
					role.setNote(StringUtils.checkNullString(roleInfo.getRemark()));
					int result = service.addRole(role);
					//result 0:成功 1:失败
					if(result==0){
						flag = true;
					}else{
						RecordLog.printLog("roleinfo add_synch to pasm failure,pasm addRole server wrong!", RecordLog.LOG_LEVEL_ERROR);
					}
				}else{
					RecordLog.printLog("roleinfo add_synch to pasm failure,roleInfo is null!", RecordLog.LOG_LEVEL_ERROR);
				}
			}else{
				RecordLog.printLog("roleinfo add_synch to pasm failure,pasm server wrong!", RecordLog.LOG_LEVEL_ERROR);
			}
			RecordLog.printLog("roleinfo add_synch to pasm is end", RecordLog.LOG_LEVEL_INFO);
		}else{
			flag = true;
		}
		return flag;
	}
	
	public boolean synchUpdateRoleInfo(RoleInfo roleInfo) {
		boolean flag = false;
		if(ConstantsSynch.isSynch){
			RecordLog.printLog("roleinfo update_synch to pasm is start", RecordLog.LOG_LEVEL_INFO);
			SecurityService service = RmiClientApplication.getInstance().getSecurityService();
			if(service!=null){
				if(roleInfo!=null){
					Role role = service.getRole(roleInfo.getPid());
					if(role!=null){
						role.setName(StringUtils.checkNullString(roleInfo.getRolename()));
						role.setNote(StringUtils.checkNullString(roleInfo.getRemark()));
						//0:成功 / 1:失败 / 2: 重名
						int result = service.updateRole(role);
						if(result==0){
							flag = true;
						}else if(result==2){
							RecordLog.printLog("roleinfo update_synch failure,sameness level is not repeat!", RecordLog.LOG_LEVEL_ERROR);
						}else{
							RecordLog.printLog("roleinfo update_synch failure,pasm updateRole server wrong!", RecordLog.LOG_LEVEL_ERROR);
						}
					}else{
						RecordLog.printLog("roleinfo update_synch failure,this role is not exit of pasm!", RecordLog.LOG_LEVEL_ERROR);
					}
				}else{
					RecordLog.printLog("roleinfo update_synch failure,roleInfo is null!", RecordLog.LOG_LEVEL_ERROR);
				}
			}else{
				RecordLog.printLog("roleinfo update_synch failure,pasm server wrong!", RecordLog.LOG_LEVEL_ERROR);
			}
			RecordLog.printLog("roleinfo update_synch to pasm is end", RecordLog.LOG_LEVEL_INFO);
		}else{
			flag = true;
		}
		return flag;
	}
	
	public boolean synchDelRoleInfo(String roleid) {
		boolean flag = false;
		if(ConstantsSynch.isSynch){
			RecordLog.printLog("roleinfo del_synch to pasm is start", RecordLog.LOG_LEVEL_INFO);
			SecurityService service = RmiClientApplication.getInstance().getSecurityService();
			if(service!=null){
				if(StringUtils.checkNullString(roleid)!=""){
					int result = 0;
					try {
						//0:成功 / 1:失败 / 2:角色被账号使用 / 3:角色下有子角色存在 
						result = service.removeRole(StringUtils.checkNullString(roleid));
					} catch (Exception e) {
						RecordLog.printLog("roleinfo del_synch failure,pasm removeRole server is error!"+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
						e.printStackTrace();
					}
					if(result==0){
						flag = true;
					}else if(result==2){
						RecordLog.printLog("roleinfo del_synch failure,this role accounts is useing!", RecordLog.LOG_LEVEL_ERROR);
					}else if(result==3){
						RecordLog.printLog("roleinfo del_synch failure,role's son is exist!", RecordLog.LOG_LEVEL_ERROR);
					}else{
						RecordLog.printLog("roleinfo del_synch failure,pasm removeRole server wrong!", RecordLog.LOG_LEVEL_ERROR);
					}
				}else{
					RecordLog.printLog("roleinfo del_synch failure,roleid is null!", RecordLog.LOG_LEVEL_ERROR);
				}
			}else{
				RecordLog.printLog("roleinfo del_synch failure,pasm server wrong!", RecordLog.LOG_LEVEL_ERROR);
			}
			RecordLog.printLog("roleinfo del_synch to pasm is end", RecordLog.LOG_LEVEL_INFO);
		}else{
			flag = true;
		}
		return flag;
	}
	
	public boolean synchAddUserRole(String loginname, List<String> roleId) {
		boolean flag = false;
		if(ConstantsSynch.isSynch){
			RecordLog.printLog("UserRole Add_synch to pasm is start", RecordLog.LOG_LEVEL_INFO);
			SecurityService service = RmiClientApplication.getInstance().getSecurityService();
			if(service!=null){
				if(StringUtils.checkNullString(loginname)!=""&&roleId!=null){
					int roleIdLen = 0;
					if(roleId!=null)
						roleIdLen = roleId.size();
					if(roleIdLen>0){
						try {
							int result = service.addUserRole(loginname, roleId);
							if(result>0){
								flag = true;
							}else{
								RecordLog.printLog("UserRole Add_synch failure,pasm addUserRole server is wrong!", RecordLog.LOG_LEVEL_ERROR);
							}
						} catch (Exception e) {
							RecordLog.printLog("UserRole Add_synch failure,pasm addUserRole server is error!"+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
							e.printStackTrace();
						}
					}else{
						RecordLog.printLog("UserRole Add_synch failure,roleId length is 0!", RecordLog.LOG_LEVEL_ERROR);
					}
				}else{
					RecordLog.printLog("UserRole Add_synch failure,loginname is null or roleId is null!", RecordLog.LOG_LEVEL_ERROR);
				}
			}else{
				RecordLog.printLog("UserRole Add_synch failure,pasm server wrong!", RecordLog.LOG_LEVEL_ERROR);
			}
		}else{
			flag = true;
		}
		return flag;
	}
	
	public boolean synchAddGroupuser(List<UserDep> depList) {
		boolean flag = false;
		if(ConstantsSynch.isSynch){
			RecordLog.printLog("groupuser add_synch to pasm is start", RecordLog.LOG_LEVEL_INFO);
			SecurityService service = RmiClientApplication.getInstance().getSecurityService();
			if(service!=null){
				int delListLen = 0;
				if(depList!=null)
					delListLen = depList.size();
				List<GroupUser> leaguers = new ArrayList<GroupUser>();
				GroupUser groupUser;
				UserDep userDep;
				for(int i=0;i<delListLen;i++){
					groupUser = new GroupUser();
					userDep = depList.get(i);
					groupUser.setUserAccount(StringUtils.checkNullString(userDep.getLoginname()));
					groupUser.setDept_id(StringUtils.checkNullString(userDep.getDepid()));
					groupUser.setType(0);
					leaguers.add(groupUser);
				}
				int result = 0;
				try {
					result = service.addGroupUser(leaguers);
				} catch (Exception e) {
					RecordLog.printLog("groupuser add_synch failure,pasm addGroupUser server error!"+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
					e.printStackTrace();
				}
				if(result==0){
					flag = true;
				}else{
					RecordLog.printLog("groupuser add_synch failure,pasm addGroupUser server wrong!", RecordLog.LOG_LEVEL_ERROR);
				}
			}else{
				RecordLog.printLog("groupuser add_synch failure,pasm server wrong!", RecordLog.LOG_LEVEL_ERROR);
			}
			RecordLog.printLog("groupuser add_synch to pasm is end", RecordLog.LOG_LEVEL_INFO);
		}else{
			flag = true;
		}
		return flag;
	}

	public boolean synchDelGroupuser(List<UserDep> depList) {
		boolean flag = false;
		if(ConstantsSynch.isSynch){
			RecordLog.printLog("groupuser del_synch to pasm is start", RecordLog.LOG_LEVEL_INFO);
			SecurityService service = RmiClientApplication.getInstance().getSecurityService();
			if(service!=null){
				int depListLen = 0;
				if(depList!=null)
					depListLen = depList.size();
				List<GroupUser> leaguers = new ArrayList<GroupUser>();
				GroupUser groupUser;
				UserDep userDep;
				for(int i=0;i<depListLen;i++){
					groupUser = new GroupUser();
					userDep = depList.get(i);
					groupUser.setDept_id(StringUtils.checkNullString(userDep.getDepid()));
					groupUser.setType(0);
					groupUser.setUserAccount(StringUtils.checkNullString(userDep.getLoginname()));
					leaguers.add(groupUser);
				}
				int result = 0;
				try {
					result = service.removeGroupUser(leaguers);
				} catch (Exception e) {
					RecordLog.printLog("groupuser del_synch failure,pasm removeGroupUser server error!"+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
					e.printStackTrace();
				}
				if(result==0){
					flag = true;
				}else{
					RecordLog.printLog("groupuser del_synch failure,pasm removeGroupUser server wrong!", RecordLog.LOG_LEVEL_ERROR);
				}
			}else{
				RecordLog.printLog("groupuser del_synch failure,pasm server wrong!", RecordLog.LOG_LEVEL_ERROR);
			}
		}else{
			flag = true;
		}
		return flag;
	}

	public boolean batchSynchAddUserInfo(List<UserInfo> userInfoList) {
		boolean flag = false;
		if(ConstantsSynch.isSynch){
			RecordLog.printLog("userInfo batchAdd_synch to pasm is start", RecordLog.LOG_LEVEL_INFO);
			SecurityService service = RmiClientApplication.getInstance().getSecurityService();
			if(service!=null){
				int userInfoLen = 0;
				if(userInfoList!=null)
					userInfoLen = userInfoList.size();
				UserInfo userInfo = null;
				User user = null;
				for(int i=0;i<userInfoLen;i++){
					user = new User();
					userInfo = userInfoList.get(i);
					user.setUserAccount(StringUtils.checkNullString(userInfo.getLoginname()));//登录名
					user.setUserName(StringUtils.checkNullString(userInfo.getFullname()));//全名
					user.setPass(StringUtils.checkNullString(userInfo.getPwd()));//密码
					user.setMobile(StringUtils.checkNullString(userInfo.getMobile()));//手机
					user.setTelephone(StringUtils.checkNullString(userInfo.getPhone()));//固定电话
					user.setEmail(StringUtils.checkNullString(userInfo.getEmail()));//E-mail
					user.setFax(StringUtils.checkNullString(userInfo.getFax()));//传真
					user.setUserStatus(userInfo.getStatus()==1?0:1);//状态
					user.setUserOrderby(userInfo.getOrdernum().intValue());//排序值
					user.setNote(StringUtils.checkNullString(userInfo.getRemark()));//备注
					user.setPortralURI(StringUtils.checkNullString(userInfo.getImage()));//头像
					user.setDuty_id(StringUtils.checkNullString(userInfo.getPosition()));//用户职位
					user.setUserType_id("0");//用户类型
					user.setLastModifyOperateDate(System.currentTimeMillis());
					//user.setUserCardNum();//员工编号
					user.setMemo(StringUtils.checkNullString(userInfo.getPid()));
					user.setSysUser(0);//是否为系统用户(0:普通用户/1:系统内置用户(root))
					user.setDeptID(StringUtils.checkNullString(userInfo.getDepid()));
					user.setDeptName(StringUtils.checkNullString(userInfo.getDepname()));
					//０:成功/１:失败/２:失败，有重复账号
					RecordLog.printLog("userInfo batchAdd_synch to pasm,synch: "+userInfo.getLoginname()+"，ID="+userInfo.getPid()+".--begin", RecordLog.LOG_LEVEL_INFO);
					int result = service.addUser(user);
					if(result==0){
						flag = true;
						RecordLog.printLog("userInfo batchAdd_synch to pasm,synch: "+userInfo.getLoginname()+"，ID="+userInfo.getPid()+".--succced", RecordLog.LOG_LEVEL_INFO);
					}else{
						RecordLog.printLog("userInfo batchAdd_synch to pasm,synch: "+userInfo.getLoginname()+"，ID="+userInfo.getPid()+".--failure", RecordLog.LOG_LEVEL_INFO);
					}
					RecordLog.printLog("userInfo batchAdd_synch to pasm,synch: "+userInfo.getLoginname()+"，ID="+userInfo.getPid()+".--end", RecordLog.LOG_LEVEL_INFO);
				}
				
			}else{
				RecordLog.printLog("userInfo batchAdd_synch failure,pasm server wrong!", RecordLog.LOG_LEVEL_ERROR);
			}
		}else{
			flag = true;
		}
		return flag;
	}
	
	public boolean batchSynchAddDepInfo(List<DepInfo> depInfoList) {
		boolean flag = false;
		if(ConstantsSynch.isSynch){
			RecordLog.printLog("depInfo batchAdd_synch to pasm is start", RecordLog.LOG_LEVEL_INFO);
			SecurityService service = RmiClientApplication.getInstance().getSecurityService();
			if(service!=null){
				int depInfoLen = 0;
				if(depInfoList!=null)
					depInfoLen = depInfoList.size();
				DepInfo depInfo = null;
				Organise organise = null;
				for(int i=0;i<depInfoLen;i++){
					organise = new Organise();
					depInfo = depInfoList.get(i);
					organise.setDept_id(StringUtils.checkNullString(depInfo.getPid()));//部门id
					organise.setDept_name(StringUtils.checkNullString(depInfo.getDepname()));//部门名称
					organise.setNode(StringUtils.checkNullString(depInfo.getRemark()));
					organise.setOrg_fax(StringUtils.checkNullString(depInfo.getDepfax()));
					organise.setOrg_orderBy(depInfo.getOrdernum().intValue());
					organise.setState(depInfo.getStatus()==1?0:1);
					organise.setOrg_status(depInfo.getStatus()==1?0:1);
					organise.setSuper_id(StringUtils.checkNullString(depInfo.getParentid().equals("0")?"1":depInfo.getParentid()));
					organise.setType(depInfo.getDeptype()!=""?0:0);
					organise.setSys_group(0);
					organise.setGroup_dnid(StringUtils.checkNullString(depInfo.getDepdns()));
					organise.setGroup_dnname(StringUtils.checkNullString(depInfo.getDepfullname()));
					RecordLog.printLog("depInfo batchAdd_synch to pasm,synch: "+depInfo.getDepname()+"，ID="+depInfo.getPid()+".--begin", RecordLog.LOG_LEVEL_INFO);
					flag = service.addOrganise(organise);
					if(flag){
						RecordLog.printLog("depInfo batchAdd_synch to pasm,synch "+depInfo.getDepname()+"，ID="+depInfo.getPid()+".--succced", RecordLog.LOG_LEVEL_INFO);
					}else{
						RecordLog.printLog("depInfo batchAdd_synch to pasm,synch "+depInfo.getDepname()+"，ID="+depInfo.getPid()+".--failure", RecordLog.LOG_LEVEL_ERROR);
					}
					RecordLog.printLog("depInfo batchAdd_synch to pasm,synch: "+depInfo.getDepname()+"，ID="+depInfo.getPid()+".--end", RecordLog.LOG_LEVEL_INFO);
				}
			}else{
				RecordLog.printLog("depInfo batchAdd_synch failure,pasm server wrong!", RecordLog.LOG_LEVEL_ERROR);
			}
		}else{
			flag = true;
		}
		return flag;
	}

	public boolean batchSynchAddGroupUser(List<UserDep> userDepList) {
		boolean flag = false;
		if(ConstantsSynch.isSynch){
			RecordLog.printLog("UserDep batchAdd_synch to pasm is start", RecordLog.LOG_LEVEL_INFO);
			SecurityService service = RmiClientApplication.getInstance().getSecurityService();
			if(service!=null){
				int userDepListLen = 0;
				if(userDepList!=null)
					userDepListLen = userDepList.size();
				UserDep userDep = null;
				List<GroupUser> leaguers = new ArrayList<GroupUser>();
				GroupUser groupUser;
				for(int i=0;i<userDepListLen;i++){
					groupUser = new GroupUser();
					userDep = userDepList.get(i);
					groupUser.setDept_id(StringUtils.checkNullString(userDep.getDepid()));
					groupUser.setUserAccount(StringUtils.checkNullString(userDep.getLoginname()));
					groupUser.setType(0);
					leaguers.add(groupUser);
				}
				int result = 0;
				try {
					result = service.addGroupUser(leaguers);
				} catch (Exception e) {
					RecordLog.printLog("UserDep batchAdd_synch failure,pasm addGroupUser server error!"+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
					e.printStackTrace();
				}
				if(result==0){
					flag = true;
				}else{
					RecordLog.printLog("UserDep batchAdd_synch to pasm,pasm addGroupUser server wrong!", RecordLog.LOG_LEVEL_INFO);
				}
			}else{
				RecordLog.printLog("UserDep batchAdd_synch failure,pasm server wrong!", RecordLog.LOG_LEVEL_ERROR);
			}
		}else{
			flag = true;
		}
		return flag;
	}

	public boolean batchSynchAddRoleInfo(List<RoleInfo> roleInfoList) {
		boolean flag = false;
		if(ConstantsSynch.isSynch){
			RecordLog.printLog("Role batchAdd_synch to pasm is start", RecordLog.LOG_LEVEL_INFO);
			SecurityService service = RmiClientApplication.getInstance().getSecurityService();
			if(service!=null){
				int roleInfoListLen = 0;
				if(roleInfoList!=null)
					roleInfoListLen = roleInfoList.size();
				Role role = null;
				RoleInfo roleInfo = null;
				for(int i=0;i<roleInfoListLen;i++){
					role = new Role();
					roleInfo = roleInfoList.get(i);
					role.setId(StringUtils.checkNullString(roleInfo.getPid()));
					role.setName(StringUtils.checkNullString(roleInfo.getRolename()));
					role.setState(0);
					role.setSuper_id(StringUtils.checkNullString(roleInfo.getParentid()).equals("0")?"":StringUtils.checkNullString(roleInfo.getParentid()));
					role.setNote(StringUtils.checkNullString(roleInfo.getRemark()));
					int result = service.addRole(role);
					if(result==0){
						flag = true;
					}else{
						RecordLog.printLog("Role batchAdd_synch to pasm failure,RoleId="+roleInfo.getPid()+";RoleName="+roleInfo.getRolename(), RecordLog.LOG_LEVEL_ERROR);
					}
				}
			}else{
				RecordLog.printLog("Role batchAdd_synch failure,pasm server wrong!", RecordLog.LOG_LEVEL_ERROR);
			}
		}else{
			flag = true;
		}
		return flag;
	}
}
