package com.ultrapower.eoms.ultrasm.manager;

import com.ultrapower.eoms.common.constants.ConstantsSynch;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.ultrasm.RecordLog;
import com.ultrapower.eoms.ultrasm.service.SynchDataFromPasm;
import com.ultrapower.pasm.jms.po.MessageType;
import com.ultrapower.pasm.jms.po.PASMMessage;

public class SynchDataMessagePasm {
	SynchDataFromPasm synchDataFromPasm;
	
	public void receiveMessage(PASMMessage message) {
		String opType = StringUtils.checkNullString(message.getOpertaorType());
		RecordLog.printLog("JMS receive PASM of operatorType:"+opType,RecordLog.LOG_LEVEL_INFO);
		synchDataFromPasm = (SynchDataFromPasm) WebApplicationManager.getBean("synchDataFromPasm");
		if(opType.equals(MessageType.USER_ADD)) { // 用户添加
			if(this.isSynchUpPasm("user")) {
				boolean isSynchRela = this.isSynchUpPasm("userdep");
				synchDataFromPasm.synchUserAdd(message.getList(), isSynchRela);
			}
		} else if(opType.equals(MessageType.USER_EDIT)) { // 用户修改
			if(this.isSynchUpPasm("user")) {
				synchDataFromPasm.synchUserEdit(message.getList());
			}
		} else if(opType.equals(MessageType.USER_DELETE)) { // 用户删除
			if(this.isSynchUpPasm("user")) {
				synchDataFromPasm.synchUserDel(message.getList());
			}
		} else if(opType.equals(MessageType.USER_UPDATE_PASS)) { // 修改用户密码
			if(this.isSynchUpPasm("user")) {
				synchDataFromPasm.synchUserPwdEdit(message.getList());
			}
		} else if(opType.equals(MessageType.ORGANISE_ADD)) { // 增加组织机构(用户组)消息
			if(this.isSynchUpPasm("dep")) {
				synchDataFromPasm.synchDepAdd(message.getList());
			}
		} else if(opType.equals(MessageType.ORGANISE_UPD)) { // 修改组织机构(用户组)消息
			if(this.isSynchUpPasm("dep")) {
				synchDataFromPasm.synchDepEdit(message.getList());
			}
		} else if(opType.equals(MessageType.ORGANISE_DEL)) { // 删除组织机构(用户组)消息
			if(this.isSynchUpPasm("dep")) {
				synchDataFromPasm.synchDepDel(message.getList());
			}
		} else if(opType.equals(MessageType.MEMBER_MANAGER)) { // 组成员消息
			if(this.isSynchUpPasm("userdep")) {
				synchDataFromPasm.synchDepUserUpdate(message.getList());
			}
		} else if(opType.equals(MessageType.ROLE_ADD)) { // 添加角色消息
			if(this.isSynchUpPasm("role")) {
				synchDataFromPasm.synchRoleAdd(message.getList());
			}
		} else if(opType.equals(MessageType.ROLE_EDIT)) { // 修改角色信息
			if(this.isSynchUpPasm("role")) {
				synchDataFromPasm.synchRoleEdit(message.getList());
			}
		} else if(opType.equals(MessageType.ROLE_DEL)) { // 删除角色信息
			if(this.isSynchUpPasm("role")) {
				synchDataFromPasm.synchRoleDel(message.getList());
			}
		} else if(opType.equals(MessageType.USER_ROLE_EDIT)) { // 用户角色绑定变更消息
			if(this.isSynchUpPasm("roleorg")) {
				synchDataFromPasm.synchUserRoleRela(message.getList());
			}
		} else {
			RecordLog.printLog("JMS receive PASM Message operatorType is unknown!", RecordLog.LOG_LEVEL_ERROR);
		}
	}
	
	/**
	 * 是否从pasm同步数据到eoms
	 * @param datatype 数据类型 user、dep、userdep、role、roleorg
	 * @return
	 */
	private boolean isSynchUpPasm(String datatype) {
		boolean result = false;
		if(ConstantsSynch.isSynch) {
			if(ConstantsSynch.isSynchFromPasm && ("all".equals(ConstantsSynch.synchFromPasmContent) || ConstantsSynch.synchFromPasmContent.indexOf(datatype) >= 0)) {
				result = true;
			}			
		}
		return result;
	}

	public void setSynchDataFromPasm(SynchDataFromPasm synchDataFromPasm) {
		this.synchDataFromPasm = synchDataFromPasm;
	}
}
