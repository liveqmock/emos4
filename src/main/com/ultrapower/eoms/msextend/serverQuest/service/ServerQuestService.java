package com.ultrapower.eoms.msextend.serverQuest.service;

import java.util.HashMap;
import java.util.List;

import com.ultrapower.eoms.msextend.serverQuest.model.ServerQuestModel;

/**
 * 
 * @author sunkai
 * @version
 *	服务请求
 */
public interface ServerQuestService {
	//得到帮助树的xml
	String  getHelpXml(String id);
	//根据id得到服务请求信息
	ServerQuestModel getServerQuestById(String id);
	
	//根据对象得到父对象
	ServerQuestModel getPranetCriterInfo(ServerQuestModel criterionHelp);
	//根据id删除节点及其子节点
	void delServerQuest(String id);

	List<ServerQuestModel> getHelpInfoByScheme(String baseSchema);
	//保存对象
	void saveServerQuest(ServerQuestModel serverQuestModel);
	
	//根据fullname获得服务目录
	ServerQuestModel getServerQuestByFullname(String fullname);
	
	/**
     * 获取常用服务目录
	 */
	List<ServerQuestModel> getCommonServerQuest(HashMap<String, String> parameter);
	/**
     * 获取常用服务目录PID
	 */
	ServerQuestModel getServerQuestByPID(String serviceCategoryid);
	public String isSendAudit(String fullname);
	
}
