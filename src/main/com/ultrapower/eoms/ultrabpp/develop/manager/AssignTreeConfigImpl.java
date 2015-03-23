package com.ultrapower.eoms.ultrabpp.develop.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.RecordLog;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.ultrabpp.cache.model.AssignTreeOrganizeModel;
import com.ultrapower.eoms.ultrabpp.develop.service.AssignTreeConfigService;
import com.ultrapower.eoms.ultrasm.model.UserDep;
import com.ultrapower.workflow.utils.UUIDGenerator;

public class AssignTreeConfigImpl implements AssignTreeConfigService
{
	private IDao hibernateDao;
	
	/**
	 * 保存子节点
	 * @return
	 */
	public void saveChildren(String org,String configIDTemp){
		// 需要查询所有子节点的部门id
		List<String> selectDepIdList = new ArrayList<String>();
		// 不需要查询子节点的部门id
		List<String> notSelectDepIdList = new ArrayList<String>();
		// 所有选择的部门id<父id,pid>
		Map<String,String> depIdMap = new HashMap<String,String>();
		List<String> depIdList = new ArrayList<String>();
		if(org ==null || "".equals(org)){
			return;
		}
		String[] organizeInfoArray = org.split(";");
		for (String organizeInfo : organizeInfoArray)
		{
			if(organizeInfo.length() == 0){
				continue;
			}
			String[] info = organizeInfo.split(":");
			String orgType = info[0];
			String orgID = info[1];
			String parentOrgID = info[2];
			if("D".equals(orgType)){
				depIdList.add(orgID);
			}else if("U".equals(orgType)){
				// 如果为人员，则直接保存
				this.saveOrganizeModel(configIDTemp,orgType,orgID,parentOrgID);
			}
			depIdMap.put(parentOrgID, orgID);
		}
		
		// 如果没有找到以该节点为父节点的子节点，则表示该节点为叶子节点
		for(String depId:depIdList){
			String childrenId = depIdMap.get(depId);
			if("".equals(StringUtils.checkNullString(childrenId))){
				selectDepIdList.add(depId);
			}else{
				notSelectDepIdList.add(depId);
			}
		}
		// 查询所有叶子节点的子节点
		String depInfoListSql = this.getAllChildrenByParentId(selectDepIdList);
		String depInfoListSql2 = this.getDepInfoListByDepId(notSelectDepIdList);
		String userInfoListSql = this.getAllUserByDepParentId(selectDepIdList);
		
		List<AssignTreeOrganizeModel> orgList = new ArrayList<AssignTreeOrganizeModel>();
		List<AssignTreeOrganizeModel> depInfoList = this.getOrganizeModel(depInfoListSql,configIDTemp,"D");
		List<AssignTreeOrganizeModel> depInfoList2 = this.getOrganizeModel(depInfoListSql2,configIDTemp,"D");
		List<AssignTreeOrganizeModel> userInfoList = this.getOrganizeModel(userInfoListSql,configIDTemp,"U");
		
		// 加入不需要查询子节点的部门id
		if(depInfoList != null){
			orgList.addAll(depInfoList);
		}
		if(depInfoList2 != null){
			orgList.addAll(depInfoList2);
		}
		if(userInfoList != null){
			orgList.addAll(userInfoList);
		}
		for(int i=0;i<orgList.size();i++){
			hibernateDao.save(orgList.get(i));
		}
	}
	
	/**
	 * 根据部门id获取所有的子部门
	 * @param
	 * @return
	 */
	public String getAllChildrenByParentId(List<String> depIdList){
		
		if(depIdList == null || depIdList.size() ==0){
			return null;
		}
		StringBuffer buffer = new StringBuffer();
		StringBuffer sqlBuffer = new StringBuffer();
		for(int i=0;i<depIdList.size();i++){
			String depId = depIdList.get(i);
			if(!"".equals(depId)){
				if(i==0){
					buffer.append(" d.PID='");
				}else{
					buffer.append(" or d.PID='");
				}
				buffer.append(depId);
				buffer.append("'");
			}
		}
		if(buffer.length() == 0){
			return "";
		}
		sqlBuffer.append("SELECT d.pid as orgID,d.parentid as parentOrgID FROM BS_T_SM_DEP d  WHERE 1 = 1 START WITH (");
		sqlBuffer.append(buffer);
		sqlBuffer.append(") CONNECT BY d.PARENTID = PRIOR d.PID");
		return sqlBuffer.toString();
	}
	
	/**
	 * 根据部门id获取所有的子部门
	 * @param
	 * @return
	 */
	public String getDepInfoListByDepId(List<String> depIdList){
		
		if(depIdList == null || depIdList.size() ==0){
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		StringBuffer sqlBuffer = new StringBuffer();
		for(int i=0;i<depIdList.size();i++){
			String depId = depIdList.get(i);
			if(!"".equals(depId)){
				if(i==0){
					buffer.append(" d.PID='");
				}else{
					buffer.append(" or d.PID='");
				}
				buffer.append(depId);
				buffer.append("'");
			}
		}
		if(buffer.length() == 0){
			return "";
		}
		sqlBuffer.append("SELECT d.pid as orgID,d.parentid as parentOrgID FROM BS_T_SM_DEP d  WHERE 1 = 1 and (");
		sqlBuffer.append(buffer);
		sqlBuffer.append(") ");
		return sqlBuffer.toString();
	}
	
	/**
	 * 查询该组下所有人员sql
	 * @param
	 * @return
	 */
	public String getAllUserByDepParentId(List<String> depIdList){
		
		if(depIdList == null || depIdList.size() ==0){
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		StringBuffer sqlBuffer = new StringBuffer();
		for(int i=0;i<depIdList.size();i++){
			String depId = depIdList.get(i);
			if(!"".equals(depId)){
				if(i==0){
					buffer.append(" d.pid='");
				}else{
					buffer.append(" or d.pid='");
				}
				buffer.append(depId);
				buffer.append("'");
			}
		}
		if(buffer.length() == 0){
			return "";
		}
		sqlBuffer.append("SELECT ud.USERID as orgID,ud.depid as parentOrgID FROM bs_t_sm_userdep ud WHERE ud.depid in (");
		sqlBuffer.append("SELECT d.pid FROM BS_T_SM_DEP d  WHERE 1 = 1 START WITH (");
		sqlBuffer.append(buffer);
		sqlBuffer.append(") CONNECT BY d.PARENTID = PRIOR d.PID)");
		return sqlBuffer.toString();
	}
	
	public List<AssignTreeOrganizeModel> getOrganizeModel(String sql,String configIDTemp,String orgType){
		if("".equals(StringUtils.checkNullString(sql))
				|| "".equals(StringUtils.checkNullString(configIDTemp))
						|| "".equals(StringUtils.checkNullString(orgType))){
			return null;
		}
		List<AssignTreeOrganizeModel> assignTreeOrganizeModelList = null;
		QueryAdapter con  = new QueryAdapter();
		DataTable rs = con.executeQuery(sql, null, 2);
		DataRow dataRow = null;
		if(rs.length() > 0)
		{
			assignTreeOrganizeModelList =new ArrayList<AssignTreeOrganizeModel>();
			for(int i=0;i<rs.length();i++){
				dataRow = rs.getDataRow(i);
				String orgID = dataRow.getString("orgID");
				String parentOrgID = dataRow.getString("parentOrgID");
				if("".equals(orgID) || "".equals(parentOrgID)){
					continue;
				}
				AssignTreeOrganizeModel organizeModel = new AssignTreeOrganizeModel();
				organizeModel.setId(UUIDGenerator.getId());
				organizeModel.setConfigid(configIDTemp);
				organizeModel.setOrganizetype(orgType.equals("U") ? 2 : 1);
				organizeModel.setOrganizeid(orgID);
				organizeModel.setParentorgid(parentOrgID);
				assignTreeOrganizeModelList.add(organizeModel);
			}
		}
		return assignTreeOrganizeModelList;
	}
	
	
	public void saveOrganizeModel(String configIDTemp,String orgType,String orgID,String parentOrgID){
		AssignTreeOrganizeModel organizeModel = new AssignTreeOrganizeModel();
		organizeModel.setId(UUIDGenerator.getId());
		organizeModel.setConfigid(configIDTemp);
		organizeModel.setOrganizetype(orgType.equals("U") ? 2 : 1);
		organizeModel.setOrganizeid(orgID);
		organizeModel.setParentorgid(parentOrgID);
		hibernateDao.save(organizeModel);
	}
	
	/**
	 * @param userDepList
	 */
	public boolean addOragnizeModel(List<UserDep> userDepList){
		boolean flag = false;
		try{
			if(userDepList == null || userDepList.size() ==0){
				return flag;
			}
			StringBuffer buffer = new StringBuffer();
			StringBuffer sqlBuffer = new StringBuffer();
			for(int i=0;i<userDepList.size();i++){
				UserDep userDep = userDepList.get(i);
				if(userDep == null){
					continue;
				}
				String depId = userDep.getDepid();
				if(!"".equals(depId)){
					if(i==0){
						buffer.append(" ud.PARENTORGID='");
					}else{
						buffer.append(" or ud.PARENTORGID='");
					}
					buffer.append(depId);
					buffer.append("'");
				}
			}
			if(buffer.length() == 0){
				return flag;
			}
			sqlBuffer.append("SELECT ud.ORGANIZEID as ORGID,ud.PARENTORGID AS PARENTORGID,UD.CONFIGID as CONFIGID FROM BS_T_BPP_ASSINGTREEORGANIZE ud WHERE 1=1 and (");
			sqlBuffer.append(buffer);
			sqlBuffer.append(") and ORGANIZETYPE = 2");
			
			// 查询配置表中的数据
			//Map<部门id,Map<派发树配置表ID,Map<人员id,人员id>>>
			Map<String,Map<String,Map<String,String>>> map = new HashMap<String,Map<String,Map<String,String>>>();
			QueryAdapter con  = new QueryAdapter();
			DataTable rs = con.executeQuery(sqlBuffer.toString(), null, 2);
			DataRow dataRow = null;
			if(rs.length() > 0)
			{
				for(int i=0;i<rs.length();i++){
					dataRow = rs.getDataRow(i);
					String depId = dataRow.getString("PARENTORGID");
					String userId = dataRow.getString("ORGID");
					String configId = dataRow.getString("CONFIGID");
					Map<String,Map<String,String>> configMap = map.get(depId);
					if(configMap == null){
						configMap = new HashMap<String,Map<String,String>>();
						Map<String,String> userIdMap = new HashMap<String,String>();
						userIdMap.put(userId,userId);
						configMap.put(configId, userIdMap);
						map.put(depId, configMap);
					}else{
						Map<String,String> userIdMap = configMap.get(configId);
						if(userIdMap == null){
							userIdMap = new HashMap<String,String>();
							userIdMap.put(userId, userId);
						}else{
							userIdMap.put(userId, userId);
						}
					}
				}
			}
			
			for(int i=0;i<userDepList.size();i++){
				UserDep userDep = userDepList.get(i);
				String depId = userDep.getDepid();
				String userId = userDep.getUserid();
				Map<String,Map<String,String>> configMap = map.get(depId);
				if(configMap == null){
					continue;
				}else{
					for(Map.Entry<String, Map<String,String>> config:configMap.entrySet()){
						String configid = config.getKey();
						Map<String,String> userIdMap = config.getValue();
						if(userIdMap.get(userId) == null){
							// 如果派发树配置表id所对应的组不存在该人员，则加入
							saveOrganizeModel(configid,"U",userId,depId);
						}
					}
				}
			}
			flag = true;
		}catch(Exception e){
			RecordLog.printLog("同步自定义派发树失败！");
			e.printStackTrace();
		}
		return flag;
	}
	
	public IDao getHibernateDao()
	{
		return hibernateDao;
	}

	public void setHibernateDao(IDao hibernateDao)
	{
		this.hibernateDao = hibernateDao;
	}

}
