package com.ultrapower.eoms.ultrasm.manager;

import java.util.ArrayList;
import java.util.List;

import com.ultrapower.accredit.api.SecurityService;
import com.ultrapower.accredit.common.value.Organise;
import com.ultrapower.accredit.common.value.User;
import com.ultrapower.accredit.rmiclient.RmiClientApplication;
import com.ultrapower.eoms.common.constants.ConstantsSynch;
import com.ultrapower.eoms.common.constants.PropertiesUtils;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.ultrasm.RecordLog;
import com.ultrapower.eoms.ultrasm.model.DepInfo;
import com.ultrapower.eoms.ultrasm.model.UserInfo;
import com.ultrapower.eoms.ultrasm.service.BatchSynchDataService;
import com.ultrapower.eoms.ultrasm.service.DepManagerService;
import com.ultrapower.eoms.ultrasm.service.SynchDataFromPasm;
import com.ultrapower.eoms.ultrasm.service.SynchDataToPasm;
import com.ultrapower.eoms.ultrasm.util.UltraSmUtil;

public class BatchSynchDataImpl implements BatchSynchDataService {
	private SynchDataFromPasm synchDataFromPasm;
	private SynchDataToPasm synchDataToPasm;
	private DepManagerService depManagerService;

	/**
	 * pasm批量同步用户（pasm-eoms）
	 * 单独同步用户信息
	 * @return
	 */
	public boolean pasmBatchSynchUserToEoms(String preventUser) {
		boolean result = false;
		if(!this.isSynchPasm("up", "user")) {
			return result;
		}
		SecurityService service = RmiClientApplication.getInstance().getSecurityService();
		if(service != null) {
			try {
				List<User> userList = service.getAllUsers();
				List<User> synchUserList = null;
				if("".equals(StringUtils.checkNullString(preventUser))) {
					synchUserList = userList;
				} else {
					List<String> loginList = UltraSmUtil.arrayToList(preventUser.split(","));
					int userLen = 0;
					if(userList != null) {
						userLen = userList.size();
					}
					synchUserList = new ArrayList<User> ();
					User user;
					for(int i=0 ; i<userLen ; i++) {
						user = userList.get(i);
						if(user == null) {
							continue;
						}
						if(loginList.indexOf(StringUtils.checkNullString(user.getUserAccount())) < 0) {
							synchUserList.add(user);
						}
					}
				}
				System.out.println("PASM用户信息到EOMS的批量同步开始！");
				result = synchDataFromPasm.synchUserAdd(synchUserList, true);
				System.out.println("PASM用户信息到EOMS的批量同步结束！");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			RecordLog.printLog("link pasm failure, synch to pasm failure!", RecordLog.LOG_LEVEL_ERROR);
		}
		return result;
	}
	
	/**
	 * pasm批量同步部门（pasm-eoms）
	 * 同步部门信息以及部门下的成员关系
	 * @return
	 */
	public boolean pasmBatchSynchDeptToEoms() {
		boolean result = false;
		if(!this.isSynchPasm("up", "dep")) {
			return result;
		}
		SecurityService service = RmiClientApplication.getInstance().getSecurityService();
		if(service != null) {
			try {
				List<Organise> organiseList = service.getTopOrganises();
				System.out.println("组织机构数据同步开始");
				this.synchAllOrganise(service, organiseList);
				System.out.println("组织机构数据同步结束");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			RecordLog.printLog("link pasm failure, synch to pasm failure!", RecordLog.LOG_LEVEL_ERROR);
		}
		return result;
	}
	
	private void synchAllOrganise(SecurityService service, List<Organise> organiseList) {
		if(service == null) {
			return ;
		}
		int orgLen = 0;
		if(organiseList != null) {
			orgLen = organiseList.size();
		}
		List<Organise> synchOrgList;
		List<Organise> subOrgList;
		Organise organise;
		for(int i=0 ; i<orgLen ; i++) {
			organise = organiseList.get(i);
			if(organise == null) {
				continue;
			}
			synchOrgList = new ArrayList<Organise> ();
			synchOrgList.add(organise);
			synchDataFromPasm.synchDepAdd(synchOrgList);
			synchDataFromPasm.synchDepUserUpdate(organise.getLeaguers());
			System.out.println(organise.getGroup_dnname());
			try {
				subOrgList = service.getOrganizations(organise.getDept_id(), StringUtils.checkNullString(PropertiesUtils.getProperty("iam.security.resource.appname")));
				this.synchAllOrganise(service, this.getSubOrgList(subOrgList, organise.getDept_id()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private List<Organise> getSubOrgList(List<Organise> subOrgList, String depId) {
		if("".equals(StringUtils.checkNullString(depId))) {
			return null;
		}
		int subOrgLen = 0;
		if(subOrgList != null) {
			subOrgLen = subOrgList.size();
		}
		Organise organise;
		for(int i=subOrgLen-1 ; i>=0 ; i--) {
			organise = subOrgList.get(i);
			if(organise == null) {
				subOrgList.remove(i);
				continue;
			}
			if(!depId.equals(StringUtils.checkNullString(organise.getSuper_id()))) {
				subOrgList.remove(i);
				continue;
			}
		}
		return subOrgList;
	}
	
	/**
	 * pasm批量同步用户（eoms-pasm）
	 * @param userList eoms的用户列表
	 * @return
	 */
	public boolean pasmBatchSynchUserToPasm(List<UserInfo> userList) {
		boolean result = true;
		if(!this.isSynchPasm("down", "user")) {
			RecordLog.printLog("please lookup the property 'synch.eoms_pasm' of the file 'security.properties', batch synch user to pasm failure!", RecordLog.LOG_LEVEL_INFO);
			return false;
		}
		int userLen = 0;
		if(userList != null) {
			userLen = userList.size();
		}
		UserInfo userInfo;
		int n;
		for(int i=0 ; i<userLen ; i++) {
			if(i % 100 == 0 || i == userLen - 1) {
				RecordLog.printLog("synch user to pasm (" + i + ") records!", RecordLog.LOG_LEVEL_INFO);
			}
			userInfo = userList.get(i);
			if(userInfo == null) {
				continue;
			}
			n = synchDataToPasm.synchUserAdd(userInfo);
			if(n > 0) {
				result = false;
				RecordLog.printLog("synch the user (" + userInfo.getLoginname() + ") to pasm failure!", RecordLog.LOG_LEVEL_ERROR);
			}
		}
		if(result) {
			RecordLog.printLog("batch synch user to pasm success!", RecordLog.LOG_LEVEL_INFO);
		}
		return result;
	}
	
	/**
	 * pasm批量同步部门（eoms-pasm）
	 * 同步部门信息以及部门下的成员关系
	 * @param depList eoms的部门列表
	 * @return
	 */
	public boolean pasmBatchSynchDeptToPasm(List<DepInfo> depList) {
		boolean result = true;
		if(!this.isSynchPasm("down", "dep")) {
			RecordLog.printLog("please lookup the property 'synch.eoms_pasm' of the file 'security.properties', batch synch group to pasm failure!", RecordLog.LOG_LEVEL_INFO);
			return false;
		}
		int depLen = 0;
		if(depList != null) {
			depLen = depList.size();
		}
		DepInfo depInfo;
		int n;
		for(int i=0 ; i<depLen ; i++) {
			if(i % 100 == 0 || i == depLen - 1) {
				RecordLog.printLog("synch group to pasm (" + i + ") records!", RecordLog.LOG_LEVEL_INFO);
			}
			depInfo = depList.get(i);
			if(depInfo == null) {
				continue;
			}
			n = synchDataToPasm.synchDepAdd(depInfo);
			if(n > 0) {
				result = false;
				RecordLog.printLog("synch the group (" + depInfo.getDepfullname() + ") to pasm failure!", RecordLog.LOG_LEVEL_ERROR);
			} else {
				if(this.isSynchPasm("down", "userdep")) {
					List<String> depId = new ArrayList<String> ();
					depId.add(depInfo.getPid());
					List<String> userId = depManagerService.getUserIdByDepID(depInfo.getPid());
					n = synchDataToPasm.synchDepUserAdd(depId, userId);
					if(n > 0) {
						RecordLog.printLog("synch the groupuser (" + depInfo.getDepfullname() + ") to pasm failure!", RecordLog.LOG_LEVEL_ERROR);
					}
				}
			}
		}
		if(result) {
			RecordLog.printLog("batch synch group to pasm success!", RecordLog.LOG_LEVEL_INFO);
		}
		return result;
	}
	
	/**
	 * 是否从pasm同步数据到eoms
	 * @param synchType 同步类型 down:eoms同步到pasm;up:pasm同步到eoms
	 * @param datatype 数据类型 user、dep、userdep、role、roleorg
	 * @return
	 */
	private boolean isSynchPasm(String synchType, String datatype) {
		boolean result = false;
		if("up".equals(synchType)) {
			if(ConstantsSynch.isSynchFromPasm && ("all".equals(ConstantsSynch.synchFromPasmContent) || ConstantsSynch.synchFromPasmContent.indexOf(datatype) >= 0)) {
				result = true;
			}
		} else if("down".equals(synchType)) {
			if(ConstantsSynch.isSynchToPasm && ("all".equals(ConstantsSynch.synchToPasmContent) || ConstantsSynch.synchToPasmContent.indexOf(datatype) >= 0)) {
				result = true;
			}
		}
		return result;
	}
	
	public void setSynchDataFromPasm(SynchDataFromPasm synchDataFromPasm) {
		this.synchDataFromPasm = synchDataFromPasm;
	}
	public void setSynchDataToPasm(SynchDataToPasm synchDataToPasm) {
		this.synchDataToPasm = synchDataToPasm;
	}
	public void setDepManagerService(DepManagerService depManagerService) {
		this.depManagerService = depManagerService;
	}
}
