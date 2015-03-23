package com.ultrapower.eoms.ultrabpp.runtime.manage;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.ultrabpp.utils.ProcessUtil;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.core.dao.HibernateMapDaoImpl;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.NumberUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ultrabpp.cache.model.EditableFieldModel;
import com.ultrapower.eoms.ultrabpp.cache.service.MetaCacheService;
import com.ultrapower.eoms.ultrabpp.model.BaseField;
import com.ultrapower.eoms.ultrabpp.runtime.service.WorkSheetService;
import com.ultrapower.workflow.bizconfig.sort.IWfSortManager;
import com.ultrapower.workflow.client.WorkFlowServiceClient;
import com.ultrapower.workflow.configuration.sort.model.WfType;
import com.ultrapower.workflow.engine.core.model.EngineModel;
import com.ultrapower.workflow.engine.task.model.ProcessTask;

@Transactional
public class WorkSheetManagerImpl implements WorkSheetService {
	
	private static Logger log = LoggerFactory.getLogger(WorkSheetManagerImpl.class);
	
	protected IDao jdbcDao;
	protected HibernateMapDaoImpl mapDao;
	private MetaCacheService metaCacheService;

	public Map<String, String> getDataMap(String baseID, String baseSchema) {
		Map<String, String> rtn = new HashMap<String, String>();
		String sql = "select * from BS_F_" + baseSchema + " where baseId='"+baseID+"'";
		Map data = jdbcDao.getMap(sql, null);
		Map<String, BaseField> metaMap = metaCacheService.getAllFieldsByBaseSchema(baseSchema);
		if (data != null && metaMap != null) {
			for (Iterator iter = data.keySet().iterator(); iter.hasNext();) {
				String dbKey = (String) iter.next();
				Object dbObj = data.get(dbKey);
				String dbValue = "";
				if (dbObj != null) {
					dbValue = dbObj.toString();
				}
				
				boolean exist = false;
				if (StringUtils.isNotBlank(dbKey)) {
					for (Iterator iterator = metaMap.keySet().iterator(); iterator.hasNext();) {
						String code = (String) iterator.next();
						if (dbKey.equalsIgnoreCase(code)) {
							rtn.put(code, dbValue);
							exist = true;
							break;
						}
					}
				}
				
				if (!exist) {
					rtn.put(dbKey, dbValue);
				}
			}
		}
		return rtn;
	}

	public void save(String baseID, String baseSchema, boolean isAddNew,
			Map<String, String> fieldMap, ProcessTask task, String actionNo, UserSession userInfo, EngineModel engineModel) {
		
		StringBuffer prefix = new StringBuffer();
		StringBuffer update = new StringBuffer();
		StringBuffer placeHolder = new StringBuffer();
		String stepNo = task.getStepNo();
		String baseStatus = task.getEntryState();
		long flagActive = task.getFlagActive();
		String tableName = "BS_F_" + baseSchema;
		Map<String, EditableFieldModel> stepFields = null;
		Map<String, EditableFieldModel> actionFields = null;
		if (flagActive == 1) {
			if (StringUtils.isNotBlank(stepNo)) {
				stepFields = metaCacheService.getStepFields(baseSchema, stepNo);
				actionFields = metaCacheService.getActionFields(baseSchema,stepNo,actionNo);
			} else {
				stepFields = metaCacheService.getStatusFields(baseSchema, task.getEntryState());
				actionFields = metaCacheService.getFreeActionFields(baseSchema, baseStatus, actionNo);
			}
		}
		Map<String, Object> baseInfoMap = new HashMap<String, Object>();
		baseInfoMap.put("BASEID", baseID);
		Map<String, Object> baseInfoExtMap = new HashMap<String, Object>();
		List paramList = new ArrayList();
		log.info("环节编辑字段：");
		if (stepFields != null) {
			for (Iterator iterator = stepFields.keySet().iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				if (StringUtils.isNotBlank(key)) {
					BaseField baseField = stepFields.get(key).getBaseField();
					String val = fieldMap.get(key);
					if (val != null) {
						Map<String, Object> saveMap = baseField.getSaveSql(fieldMap);
						if (saveMap != null) {
							for (Iterator iter = saveMap.keySet().iterator(); iter
									.hasNext();) {
								String  fieldName = (String ) iter.next();
								Object objVal = saveMap.get(fieldName);
								prefix.append(fieldName + ",");
								paramList.add(objVal);
								update.append(fieldName + "=?,");
								placeHolder.append("?,");
								baseInfoExtMap.put(fieldName, objVal);
								log.info(fieldName + "=" + objVal);
							}
						}
					}
				}
			}
		}
		log.info("动作编辑字段：");
		if (actionFields != null) {
			for (Iterator iterator = actionFields.keySet().iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				if (StringUtils.isNotBlank(key)) {
					BaseField baseField = actionFields.get(key).getBaseField();
					String val = fieldMap.get(key);
					if (val != null) {//有可能为“”
						Map<String, Object> saveMap = baseField.getSaveSql(fieldMap);
						if (saveMap != null) {
							for (Iterator iter = saveMap.keySet().iterator(); iter
									.hasNext();) {
								String  fieldName = (String ) iter.next();
								Object objVal = saveMap.get(fieldName);
								prefix.append(fieldName + ",");
								paramList.add(objVal);
								update.append(fieldName + "=?,");
								placeHolder.append("?,");
								baseInfoExtMap.put(fieldName, objVal);
								log.info(fieldName + "=" + objVal);
							}
						}
					}
				}
			}
		}
		
		String preSql = "";
		if (prefix.toString().length() > 0) {
			preSql = ","+prefix.toString().substring(0, prefix.toString().length() - 1);
		}
		String upSql = "";
		if (update.toString().length() > 0) {
			upSql = update.toString().substring(0, update.toString().length() - 1);
		}
		String ph = "";
		if (placeHolder.toString().length() > 0) {
			ph = ","+placeHolder.toString().substring(0, placeHolder.toString().length() - 1);
		}
		String baseSn = engineModel.getBaseSn();
		String baseEntryId = engineModel.getTopEntryId();
		long baseSendDate = NumberUtils.formatToLong(fieldMap.get("BASESENDDATE"));
		long baseAcceptDate = NumberUtils.formatToLong(fieldMap.get("BASEACCEPTDATE"));
		long baseFinishDate = NumberUtils.formatToLong(fieldMap.get("BASEFINISHDATE"));
		long baseCreateTime = engineModel.getBaseCreateTime();
		long baseCloseTime = 0;
		if ("2".equalsIgnoreCase(engineModel.getEntryStateFlag())) {
			baseCloseTime = engineModel.getBaseCloseTime();
		}
		//工单处理受理时限添加
		long baseAcceptOutTime = NumberUtils.formatToLong(fieldMap.get("BASEACCEPTOUTTIME"));
		long baseDealOutTime = NumberUtils.formatToLong(fieldMap.get("BASEDEALOUTTIME"));
		
		baseStatus = engineModel.getEntryState();
		if (isAddNew) {
			String baseName = "";
			String baseTplID = "";
			long workflowFlag = 0;
			String catagoryId = "";
			String baseCreatorFullName = userInfo.getFullName();
			String baseCreatorLoginName = userInfo.getLoginName();
			String baseCreatorConnectWay = userInfo.getPhone();
			String baseCreatorCorp = userInfo.getCompanyName();
			String baseCreatorCorpID = userInfo.getCompanyId();
			String baseCreatorDep = userInfo.getDepName();
			String baseCreatorDepID = userInfo.getDepId();
			String baseCreatorDN = userInfo.getDepFullName();
			String baseCreatorDNID = userInfo.getDepDns();
			IWfSortManager ver = WorkFlowServiceClient.clientInstance().getSortService();
			WfType wfType;
			try {
				wfType = ver.getWfTypeByCode(baseSchema);
				baseName = wfType.getName();
				baseTplID = wfType.getWfDefaultVersion();
				workflowFlag = wfType.getWfType();
				catagoryId = wfType.getSortId();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			
			//新建
			String sysCols = "baseID,baseSchema,baseName,baseSn,baseEntryID," +
					"baseCreateDate,baseCloseDate," +
					"baseSendDate,baseAcceptDate,baseFinishDate," +
					"baseStatus,baseTplID,baseWorkflowFlag,baseCatagoryID," +
					"baseCreatorFullName,baseCreatorLoginName,baseCreatorConnectWay," +
					"baseCreatorCorp,baseCreatorCorpID," +
					"baseCreatorDep,baseCreatorDepID," +
					"baseCreatorDN,baseCreatorDNID";
			String sql = "insert into " + tableName + " ("+sysCols+preSql+") values (" +
					"?,?,?,?,?," +
					"?,?," +
					"?,?,?," +
					"?,?,?,?," +
					"?,?,?," +
					"?,?," +
					"?,?," +
					"?,?" +
					ph+")";
			Object[] params = {
					baseID, baseSchema, baseName, baseSn, baseEntryId,
					baseCreateTime, baseCloseTime,
					baseSendDate, baseAcceptDate, baseFinishDate,
					baseStatus, baseTplID, workflowFlag, catagoryId,
					baseCreatorFullName, baseCreatorLoginName, baseCreatorConnectWay,
					baseCreatorCorp, baseCreatorCorpID,
					baseCreatorDep, baseCreatorDepID,
					baseCreatorDN, baseCreatorDNID
			};
			
			baseInfoMap.put("BASESCHEMA", baseSchema);
			baseInfoMap.put("BASENAME", baseName);
			baseInfoMap.put("BASESN", baseSn);
			baseInfoMap.put("BASEENTRYID", baseEntryId);
			baseInfoMap.put("BASECREATEDATE", baseCreateTime);
			baseInfoMap.put("BASETPLID", baseTplID);
			baseInfoMap.put("BASEWORKFLOWFLAG", workflowFlag);
			baseInfoMap.put("BASECATAGORYID", catagoryId);
			baseInfoMap.put("BASECREATORFULLNAME", baseCreatorFullName);
			baseInfoMap.put("BASECREATORLOGINNAME", baseCreatorLoginName);
			baseInfoMap.put("BASECREATORCONNECTWAY", baseCreatorConnectWay);
			baseInfoMap.put("BASECREATORCORP", baseCreatorCorp);
			baseInfoMap.put("BASECREATORCORPID", baseCreatorCorpID);
			baseInfoMap.put("BASECREATORDEP", baseCreatorDep);
			baseInfoMap.put("BASECREATORDEPID", baseCreatorDepID);
			baseInfoMap.put("BASECREATORDN", baseCreatorDN);
			baseInfoMap.put("BASECREATORDNID", baseCreatorDNID);
			Object[] tmpParam = params;
			Object[] tmp = paramList.toArray();
			if (!ArrayUtils.isEmpty(tmp)) {
				tmpParam = ArrayUtils.addAll(params, tmp);
			}
			jdbcDao.executeUpdate(sql, tmpParam);
			log.info("新建工单，sql=" + sql);
		} else {
			Object[] obj = new Object[5];
			obj[0] = baseStatus;
			obj[1] = baseCloseTime;
			obj[2] = baseSendDate;
			obj[3] = baseAcceptDate;
			obj[4] = baseFinishDate;
			//修改
			if (StringUtils.isNotBlank(upSql)) {
				Object[] tmp = paramList.toArray();
				upSql+=", baseStatus=?,baseCloseDate=?,baseSendDate=?,baseAcceptDate=?,baseFinishDate=?";
				String sql = "update " + tableName + " set " + upSql + " WHERE baseId='" + baseID + "' ";
				jdbcDao.executeUpdate(sql, ArrayUtils.addAll(tmp, obj));
				log.info("流转工单，sql=" + sql);
			} else {
				upSql+="baseStatus=?,baseCloseDate=?,baseSendDate=?,baseAcceptDate=?,baseFinishDate=?";
				String sql = "update " + tableName + " set " + upSql + " WHERE baseId='" + baseID + "' ";
				jdbcDao.executeUpdate(sql, obj);
				log.info("流转工单，sql=" + sql);
			}
			mapDao.setEntityName("BPP_BASEINFOR");
			baseInfoMap = (Map<String, Object>) mapDao.get(baseID);
		}
		for (Iterator iterator = baseInfoExtMap.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			if (StringUtils.isNotBlank(key)) {
				baseInfoMap.put(key.toUpperCase(), baseInfoExtMap.get(key));
			}
		}
		baseInfoMap.put("BASESTATUS", baseStatus);
		baseInfoMap.put("BASECLOSEDATE", baseCloseTime);
		baseInfoMap.put("BASESENDDATE", baseSendDate);
		baseInfoMap.put("BASEACCEPTDATE", baseAcceptDate);
		baseInfoMap.put("BASEFINISHDATE", baseFinishDate);
		baseInfoMap.put("BASEDEALOUTTIME", baseDealOutTime);
		baseInfoMap.put("BASEACCEPTOUTTIME", baseAcceptOutTime);
		this.saveBaseInfo(baseInfoMap);
	}
	
	private void saveBaseInfo(Map<String, Object> baseInfoMap) {
		mapDao.setEntityName("BPP_BASEINFOR");
		mapDao.saveOrUpdate(baseInfoMap);
	}
	

	public MetaCacheService getMetaCacheService() {
		return metaCacheService;
	}

	public void setMetaCacheService(MetaCacheService metaCacheService) {
		this.metaCacheService = metaCacheService;
	}

	public IDao getJdbcDao() {
		return jdbcDao;
	}

	public void setJdbcDao(IDao jdbcDao) {
		this.jdbcDao = jdbcDao;
	}

	public HibernateMapDaoImpl getMapDao() {
		return mapDao;
	}

	public void setMapDao(HibernateMapDaoImpl mapDao) {
		this.mapDao = mapDao;
	}
}
