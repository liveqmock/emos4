package com.ultrapower.eoms.common.external.manager;


import com.ultrapower.eoms.common.RecordLog;
import com.ultrapower.eoms.common.constants.ConstantsSynch;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.pasm.jms.po.MessageType;
import com.ultrapower.pasm.jms.po.PASMMessage;

/**
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2011-1-14 下午03:44:50
 * @descibe 接收pasm广播消息
 */
public class PASMMessageConsumer {

	public PASMMessageConsumer(){
	}
	
	public void receiveMessage(PASMMessage message){
		 String operatorType = StringUtils.checkNullString(message.getOpertaorType());
		 RecordLog.printLog("JMS receive PASM of operatorType:"+operatorType,RecordLog.LOG_LEVEL_INFO);
		 if(ConstantsSynch.isPasmSynchEoms){
			 if(operatorType.equals(MessageType.USER_ADD)){//用户添加
				 EomsInfoUpPasmImpl.addUser(message.getList());
			 }else if(operatorType.equals(MessageType.USER_EDIT)){//用户修改
				 EomsInfoUpPasmImpl.editUser(message.getList());
			 }else if(operatorType.equals(MessageType.USER_DELETE)){//用户删除
				 EomsInfoUpPasmImpl.delUser(message.getList());
			 }else if(operatorType.equals(MessageType.USER_UPDATE_PASS)){//修改用户密码
				 EomsInfoUpPasmImpl.updateUserPass(message.getList());
			 }else if(operatorType.equals(MessageType.ORGANISE_ADD)){//增加组织机构(用户组)消息
				 EomsInfoUpPasmImpl.addOrganise(message.getList());
			 }else if(operatorType.equals(MessageType.ORGANISE_UPD)){//修改组织机构(用户组)消息
				 EomsInfoUpPasmImpl.editOrganise(message.getList());
			 }else if(operatorType.equals(MessageType.ORGANISE_DEL)){//删除组织机构(用户组)消息
				 EomsInfoUpPasmImpl.delOrganise(message.getList());
			 }else if(operatorType.equals(MessageType.ROLE_ADD)){//添加角色消息
				 EomsInfoUpPasmImpl.addRole(message.getList());
			 }else if(operatorType.equals(MessageType.ROLE_EDIT)){//修改角色信息
				 EomsInfoUpPasmImpl.editRole(message.getList());
			 }else if(operatorType.equals(MessageType.ROLE_DEL)){//删除角色信息
				 EomsInfoUpPasmImpl.delRole(message.getList());
			 }else if(operatorType.equals(MessageType.USER_ROLE_EDIT)){//用户角色绑定变更消息
				 EomsInfoUpPasmImpl.editUserRole(message.getList());
			 }else{
				 RecordLog.printLog("JMS receive PASM Message operatorType is unknown!", RecordLog.LOG_LEVEL_ERROR);
			 }
		 }
	}
}
