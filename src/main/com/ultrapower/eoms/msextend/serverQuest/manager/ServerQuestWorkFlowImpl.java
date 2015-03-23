package com.ultrapower.eoms.msextend.serverQuest.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;

import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.msextend.serverQuest.model.ServerQuestModel;
import com.ultrapower.eoms.msextend.serverQuest.service.ServerQuestWorkFlowService;
import com.ultrapower.eoms.msextend.workflow.ClientCallAdapter;
import com.ultrapower.eoms.ultrasm.model.UserInfo;
import com.ultrapower.eoms.ultrasm.service.UserManagerService;

public class ServerQuestWorkFlowImpl extends ClientCallAdapter implements ServerQuestWorkFlowService{

	public final static String SPLITER_AUDIT = "#;";
	public final static String SPLITER_AUDIT_TYPE = "#:";
	IDao<ServerQuestModel> serverQuestModelDao;
	UserManagerService userManagerService;
	public String getNextAssign(Map<String, String> args) {
		String auditSequence = args.get("auditSequence");
		String serverQuestFullName = args.get("serverQuestFullName");
		Map<String,Object> map = getNextAssign(auditSequence,serverQuestFullName);
		return JSONObject.fromObject(map).toString();
	}
	
	/**
	 * 根据服务目录和当前处理人
	 * @param currentAssign
	 * @param serverQuestFullName
	 * @return
	 */
	private Map<String,Object> getNextAssign(String auditSequence, String serverQuestFullName){
		Integer auditSequenceInt = 0;
		if(StringUtils.isNotEmpty(auditSequence)){
			auditSequenceInt = Integer.parseInt(auditSequence);
		}
		Map<String,Object> map = new HashMap<String, Object>();
		ServerQuestModel s = serverQuestModelDao.findUnique("from ServerQuestModel t where t.serverquestfullname = ?", serverQuestFullName);
		if(s != null){
			String auditId = s.getAudit_id();
			if(auditId != null){//审批路径不为空
				String[] assignInfos = auditId.split(SPLITER_AUDIT);
				if(assignInfos.length > auditSequenceInt){//还有后续受理审批人
					String[] assignInfo = assignInfos[auditSequenceInt].split(SPLITER_AUDIT_TYPE);
					String assignType = assignInfo[0];//U人员,G组
					String assignId = assignInfo[1];
					map.put("assignId", assignId);
					map.put("assignType", assignType);
					map.put("assignName", s.getAudit_name().split(SPLITER_AUDIT)[auditSequenceInt]);
					auditSequenceInt++;
					map.put("auditSequence", (auditSequenceInt.toString()));//设置当前处理人序列
//					if(assignType.equals("G")){
//						List<UserInfo> users = userManagerService.getUsersByGroupId(assignId);
//						map.put("users", users);
//					}
				}else if(s.getDealGroupId() != null){//没有下一步审批人,则处理人是dealGroup
					String[] dealGroup = s.getDealGroupId().split("#:");
					map.put("assignId", dealGroup[1]);
					map.put("assignType", dealGroup[0]);
					map.put("isDealPerson", Boolean.toString(true));//是处理人
					map.put("assignName", s.getDealGroupName());
				}
			}else if(s.getDealGroupId() != null){//没有审批路径,则处理人是行员
				String[] dealGroup = s.getDealGroupId().split("#:");
				map.put("assignId", dealGroup[1]);
				map.put("assignType", dealGroup[0]);
				map.put("isDealPerson", Boolean.toString(true));//是处理人
				map.put("assignName", s.getDealGroupName());
			}
		}
		return map;
	}

	public void setServerQuestModelDao(IDao<ServerQuestModel> serverQuestModelDao) {
		this.serverQuestModelDao = serverQuestModelDao;
	}

	public Boolean hasNextAudit(String serverQuestFullName,String auditSequence) {
		ServerQuestModel s = getServiceCategory(serverQuestFullName);
		if(s == null) 
			return false;
		String auditId = s.getAudit_id();
		if(auditId == null) 
			return false;
		if(auditId.split(SPLITER_AUDIT).length > Integer.parseInt(auditSequence))
			return true;
		return false;
	}

	public void setUserManagerService(UserManagerService userManagerService) {
		this.userManagerService = userManagerService;
	}

	public ServerQuestModel getServiceCategory(String serverQuestFullName) {
		ServerQuestModel s = serverQuestModelDao.findUnique("from ServerQuestModel t where t.serverquestfullname = ?", serverQuestFullName);
		return s;
	}

	public String hasAcceptPerson(String serverQuestFullName) {
		ServerQuestModel s = getServiceCategory(serverQuestFullName);
		if(s == null) return "0";
		if(s.getHy() != null) return "1";
		return "0";
	}

}
