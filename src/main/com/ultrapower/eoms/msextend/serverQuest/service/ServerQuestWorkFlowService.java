package com.ultrapower.eoms.msextend.serverQuest.service;

import java.util.Map;

import com.ultrapower.eoms.msextend.serverQuest.model.ServerQuestModel;

/**
 * 服务请求工作流相关接口
 * @author hhy
 *
 */
public interface ServerQuestWorkFlowService {
	/**
	 * 根据当前处理人和服务目录PID获取下一步处理人信息
	 * @param args 参数
	 * <ul>
	 * 	<li>currentAssign 审批序列，1受理人，2...审批人 </li>
	 * 	<li>serverQuestFullName 服务目录</li>
	 * </ul>
	 * @return
	 */
	public String getNextAssign(Map<String, String> args);
	
	/**
	 * 是否有下一审批人
	 * @param auditSequence 当前的审批序列
	 * @return
	 */
	public Boolean hasNextAudit(String serverQuestFullName,String auditSequence);
	
	/**
	 * 根据服务目录全名获得服务目录
	 * @param serverQuestFullName 服务目录
	 * @return
	 */
	public ServerQuestModel getServiceCategory(String serverQuestFullName);

}
