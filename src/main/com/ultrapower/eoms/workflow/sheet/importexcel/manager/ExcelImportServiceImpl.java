package com.ultrapower.eoms.workflow.sheet.importexcel.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.msextend.workflow.service.WorkflowService;
import com.ultrapower.eoms.ultrabpp.api.BaseAction;
import com.ultrapower.eoms.ultrasm.model.ImpTableConfig;
import com.ultrapower.eoms.ultrasm.model.UserInfo;
import com.ultrapower.eoms.ultrasm.service.IExcelExtend;
import com.ultrapower.eoms.ultrasm.service.UserManagerService;
import com.ultrapower.eoms.workflow.RecordLog;
import com.ultrapower.eoms.workflow.sheet.importexcel.constants.ExcelFieldConstants;
import com.ultrapower.eoms.workflow.sheet.importexcel.util.DataBaseOperator;
import com.ultrapower.eoms.workflow.sheet.importexcel.util.DataValidateUtils;

public class ExcelImportServiceImpl implements IExcelExtend {
	private BaseAction baseApiAction;
	private WorkflowService workflowServiceEx;
	private UserManagerService userManagerService;
	private UserInfo userInfo;
	private String tableName;// 用于接收表名
	private String baseSchema;// 用于接收通过表名赋的值：baseSchema
	private String dealDesc;// 用于接收通过表名赋的值：dealDesc

	@Override
	public void extendExpRun(List<String> errorInfoList,
			ImpTableConfig impTableConfig) {
	}

	@SuppressWarnings("unchecked")
	@Override
	public void extendImpRun(List<String> errorInfoList, ImpTableConfig impTableConfig, DataTable excelDt) {
		// 第一部分：初始化数据
		tableName = impTableConfig.getTableName();// 获取表名，并为baseSchema、dealDesc赋值
		if ("bs_t_sm_import_servicequest".equals(tableName)) {
			tableName = "BS_F_CDB_SERVICEREQUEST";
			baseSchema = "CDB_SERVICEREQUEST";
			dealDesc = "服务请求Excel导入补单";
		} else if ("bs_t_sm_import_incident".equals(tableName)) {
			tableName = "BS_F_CBD_INCIDENT";
			baseSchema = "CBD_INCIDENT";
			dealDesc = "事件工单Excel导入补单";
		} else {
			// 导入失败信息
			RecordLog.printLog("表" + tableName + "对应的" + baseSchema + "流程Excel导入补单失败.", RecordLog.LOG_LEVEL_ERROR);
		}
		
		// 第二部分：定义变量及数据验证
		int listSize = 0;
		// xml中有配置startrow，通过impTableConfig.getStartRow()，实际代表表头在第几行(从0开始计数)，真正的数据行数需加2
		// 例：startrow=0，代表表头在第1行，数据从第2行开始；startrow=1，代表表头在第2行，数据从第3行开始。
		// startrow从0开始计数，实际代表第0+1行，需加1，数据在下一行，需再加1
		int datarow = impTableConfig.getStartRow() + 2;// startrow=0,dataRow=2;startrow=1,dataRow=3;startrow=2,dataRow=4.
		if (null != excelDt) {
			listSize = excelDt.length();// 数据的行数
		}
		HashMap rowHashMap;// 用于接收datarow中的hashmap
		String error = "";// 用于接收错误串的信息
		// 为进行验证的数据字典项进行数据初始化
		DataBaseOperator.initDict();		
		// 循环数据
		for (int i = 0; i < listSize; i++) {
			rowHashMap = excelDt.getDataRow(i).getRowHashMap();// 循环每一行DataTable数据，将其数据取出并判断，以便保存建单
			// 进行数据验证
			error = DataValidateUtils.validate(userManagerService, baseSchema, rowHashMap);
			if (!"".equals(error)) {
				// 验证失败走这里
				// 第一行数据的写法(0+3),第二行数据的写法(1+3),依次类推……
				error = "第" + (i + datarow) + "行数据：" + error;
				errorInfoList.add(error);
			}
		}
		// 第三部分：定义变量及数据保存
		String baseID = "";// 用于接收保存工单返回回来的值：baseID
		String taskID = "";		
		HashMap fieldMap;// 用于保存doaction所需的字段		
		String baseCreatorLoginname = "";// 通用字段：建单人OA号
		String baseCreatorFullname = "";// 通用字段：建单人姓名
		String baseSummary = "";// 通用字段：标题
		long baseCreateDate;//通用字段：用于接收
		long baseCloseDate;
		DataBaseOperator dataBaseOperator = new DataBaseOperator();
		if (null==errorInfoList ||( null != errorInfoList && errorInfoList.isEmpty())) {
			for (int i = 0; i < listSize; i++) {
				rowHashMap = excelDt.getDataRow(i).getRowHashMap();// 循环每一行DataTable数据，将其数据取出并判断，以便保存建单
				fieldMap = new HashMap();
				baseCreatorFullname = rowHashMap.get(ExcelFieldConstants.BASECREATORFULLNAME).toString();
				baseCreatorLoginname = rowHashMap.get(ExcelFieldConstants.BASECREATORLOGINNAME).toString().toUpperCase();
				if ("CBD_INCIDENT".equals(baseSchema)) {
					baseSummary = rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.BASESUMMARY).toString();
				} else if ("CDB_SERVICEREQUEST".equals(baseSchema)) {
					baseSummary = rowHashMap.get(ExcelFieldConstants.CDB_SERVICEREQUEST.BASESUMMARY).toString();
				} else {
					// 导入失败信息
					// 第一行数据的写法(0+3),第二行数据的写法(1+3),依次类推……
					RecordLog.printLog("第" + (i + datarow) + "行数据：添加标题失败.", RecordLog.LOG_LEVEL_ERROR);
				}
				baseCreateDate = TimeUtils.formatDateStringToInt(((String) rowHashMap.get(ExcelFieldConstants.BASECREATEDATE)).replace('/', '-'));
				baseCloseDate = TimeUtils.formatDateStringToInt(((String) rowHashMap.get(ExcelFieldConstants.BASECLOSEDATE)).replace('/', '-'));
				
				fieldMap.putAll(fillFieldToMap(baseCreatorLoginname,baseCreatorFullname,baseSummary,rowHashMap));// 字段个性化设置并将其他字段全部填入到doAction所需的fieldMap中
				baseID = dataBaseOperator.saveSheet(baseApiAction, baseSchema, taskID, baseCreatorLoginname, dealDesc);// 保存工单
				
				boolean flag = dataBaseOperator.addWfRecord(workflowServiceEx, baseSchema, baseID, rowHashMap);// 添加处理记录
				if (!flag) {
					// 第一行数据的写法(0+3),第二行数据的写法(1+3),依次类推……
					RecordLog.printLog("第" + (i + datarow) + "行数据：添加流程记录失败.", RecordLog.LOG_LEVEL_ERROR);
				}
				
				dataBaseOperator.closeSheet(baseApiAction, baseID, baseSchema, baseCreatorLoginname, "", dealDesc, fieldMap);// 关闭工单
				flag = DataBaseOperator.updateCreateOrCloseDate(baseID, tableName, baseCreateDate, baseCloseDate);
				if (!flag) {
					// 第一行数据的写法(0+3),第二行数据的写法(1+3),依次类推……
					RecordLog.printLog("第" + (i + datarow) + "行数据：更新创建时间及关闭时间失败.", RecordLog.LOG_LEVEL_ERROR);
				}
			}
		}
	}

	/**
	 * 字段个性化设置并将其他字段全部填入到doAction所需的fieldMap中
	 * @param baseCreatorLoginname
	 * @param baseCreatorFullname
	 * @param baseSummary
	 * @param baseSchema
	 * @param rowHashMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map fillFieldToMap(String loginName, String fullName, String baseSummary, HashMap rowHashMap) {
		Map map = new HashMap();
		map.putAll(fillCommonFieldToMap(loginName, fullName, baseSummary));
		map.putAll(fillOthersFieldToMap(rowHashMap));
		return map;
	}

	/**
	 * 向map中添加工单需要的共用字段：建单人、建单人OA号、建单人部门、建单人部门ID、标题
	 * 
	 * @param loginName
	 *            建单人OA号
	 * @param fullName
	 *            建单人姓名
	 * @param baseSummary
	 *            标题字段
	 * @return 返回一个map用于将数据putAll到fieldMap中
	 */
	@SuppressWarnings("unchecked")
	private Map fillCommonFieldToMap(String loginName, String fullName, String baseSummary) {
		Map map = new HashMap();
		map.put("BASECREATORLOGINNAME", loginName.toUpperCase());
		map.put("BASECREATORFULLNAME", fullName);
		userInfo = userManagerService.getUserByLoginName(loginName);
		if (null != userInfo) {
			map.put("BASECREATORDEPID", userInfo.getDepid());
			map.put("BASECREATORDEP", userInfo.getDepname());
		}
		map.put("BaseSummary", "(补单)" + baseSummary);// 标题
		return map;
	}
	
	/**
	 * 向map中添加工单需要的其他字段
	 * @param rowHashMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map fillOthersFieldToMap(HashMap rowHashMap) {
		Map map = new HashMap();
		if ("CBD_INCIDENT".equals(baseSchema)) {
			// 联系人相关信息
			String contactOA = rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.CONTACTOA).toString().toUpperCase();
			String contactPerson = rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.CONTACTPERSON).toString();
			String contactStation = (String) rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.CONTACTSTATION);
			String contactTel = (String) rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.CONTACTTEL);
			map.putAll(fillContactFieldToMap(contactOA,contactPerson,contactStation,contactTel));
			
			// 直接入库
			map.put("OccurrenceTime", rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.OCCURRENCETIME).toString().replace('/', '-'));
			map.put("RecoveryTime", rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.RECOVERYTIME).toString().replace('/', '-'));
			map.put("IcidentSource", rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.ICIDENTSOURCE));
			map.put("IncidentDes", rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.INCIDENTDES));
			map.put("IncidentIdentification",rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.INCIDENTIDENTIFICATION));
			map.put("IncidentPropertie", rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.INCIDENTPROPERTIE));
			map.put("ServiceInterruptionTime",rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.SERVICEINTERRUPTIONTIME));
			map.put("IncidentSolutionType",rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.INCIDENTSOLUTIONTYPE));
			map.put("IncidentReason", rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.INCIDENTREASON));
			map.put("ProcedureProcessing", rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.PROCEDUREPROCESSING));
			map.put("Solution", rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.SOLUTION));
			map.put("closeCode", rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.CLOSECODE));
			map.put("Incident_UrgentDgree",rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.INCIDENT_URGENTDGREE));
			map.put("Incident_EffectDgree",rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.INCIDENT_EFFECTDGREE));
			
			// 获取到优先级的值，然后入库
			Map<String, String> priorityMap = initpriorityMap();
			map.put("Priority", priorityMap.get(rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.INCIDENT_EFFECTDGREE)+","+rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.INCIDENT_URGENTDGREE)));// map判断
			
			// 分类相关拼接后入库
			map.put("IncidentPhenoClass", StringUtils.joinClassStr(
					(String) rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.INCIDENTPHENOCLASS1),
					(String) rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.INCIDENTPHENOCLASS2),
					(String) rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.INCIDENTPHENOCLASS3),
					(String) rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.INCIDENTPHENOCLASS4)));
			map.put("IncidentSourceClass", StringUtils.joinClassStr(
					(String) rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.INCIDENTSOURCECLASS1),
					(String) rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.INCIDENTSOURCECLASS2),
					(String) rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.INCIDENTSOURCECLASS3),
					(String) rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.INCIDENTSOURCECLASS4)));
		} else if ("CDB_SERVICEREQUEST".equals(baseSchema)) {
			// 请求人相关信息
			String requestUserOA = rowHashMap.get(ExcelFieldConstants.CDB_SERVICEREQUEST.REQUESTUSEROA).toString().toUpperCase();
			String requestUser = rowHashMap.get(ExcelFieldConstants.CDB_SERVICEREQUEST.REQUESTUSER).toString();
			String requestUserSite = (String) rowHashMap.get(ExcelFieldConstants.CDB_SERVICEREQUEST.REQUESTUSERSITE);
			String requestUserPhone = (String) rowHashMap.get(ExcelFieldConstants.CDB_SERVICEREQUEST.REQUESTUSERPHONE);
			map.putAll(fillContactFieldToMap(requestUserOA, requestUser, requestUserSite, requestUserPhone));

			// 直接入库
			map.put("requireDealTime", rowHashMap.get(ExcelFieldConstants.CDB_SERVICEREQUEST.REQUIREDEALTIME).toString().replace('/', '-'));
			map.put("dataResource", rowHashMap.get(ExcelFieldConstants.CDB_SERVICEREQUEST.DATARESOURCE));
			map.put("requestDesc", rowHashMap.get(ExcelFieldConstants.CDB_SERVICEREQUEST.REQUESTDESC));
			map.put("deal_process_solution",rowHashMap.get(ExcelFieldConstants.CDB_SERVICEREQUEST.DEAL_PROCESS_SOLUTION));
			map.put("close_code", rowHashMap.get(ExcelFieldConstants.CDB_SERVICEREQUEST.CLOSE_CODE));
			
			// 分类相关拼接后入库
			map.put("service_category", StringUtils.joinClassStr(
					(String) rowHashMap.get(ExcelFieldConstants.CDB_SERVICEREQUEST.SERVICE_CATEGORY1),
					(String) rowHashMap.get(ExcelFieldConstants.CDB_SERVICEREQUEST.SERVICE_CATEGORY2),
					(String) rowHashMap.get(ExcelFieldConstants.CDB_SERVICEREQUEST.SERVICE_CATEGORY3),
					(String) rowHashMap.get(ExcelFieldConstants.CDB_SERVICEREQUEST.SERVICE_CATEGORY4)));
		}
		return map;
	}

	/**
	 * 向map中添加工单需要的其他字段 - 联系人相关字段
	 * @param loginname
	 * @param fullname
	 * @param station
	 * @param phone
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map fillContactFieldToMap(String loginname, String fullname, String station, String phone) {
		Map map = new HashMap();
		if ("CBD_INCIDENT".equals(baseSchema)) {
			map.put("ContactPerson", fullname);
			map.put("ContactOA", loginname);
			map.put("ContactStation", station);
			map.put("ContactTel", phone);
			userInfo = userManagerService.getUserByLoginName(loginname);
			if (null != userInfo) {
				if (null == station || "".equals(station)) {
					map.put("ContactStation", userInfo.getStation());// 联系人工位
				}
				if (null == phone || "".equals(phone)) {
					map.put("ContactTel", userInfo.getPhone());// 联系人电话
				}
				map.put("ContactPhone", userInfo.getMobile());// 联系人手机
				map.put("ContactPersonId", userInfo.getLoginname());// 联系人ID
				map.put("ContactPosition", userInfo.getPosition());// 联系人职位
				map.put("IsVIP", userInfo.getIsVip());// VIP用户
				map.put("ContactDepartment", userInfo.getDepname());// 联系人部门
				map.put("ContactPersonSite", userInfo.getCompanyName());// 联系人单位
			}
			return map;
		} else if ("CDB_SERVICEREQUEST".equals(baseSchema)) {
			map.put("requestUser", fullname);
			map.put("requestUserOA", loginname);
			map.put("requestUserSite",station);
			map.put("requestUserPhone",phone);
			userInfo = userManagerService.getUserByLoginName(loginname);
			if (null != userInfo) {
				if (null == station || "".equals(station)) {
					map.put("requestUserSite", userInfo.getStation());
				}
				if (null == phone || "".equals(phone)) {
					map.put("requestUserPhone", userInfo.getPhone());
				}
				map.put("requestUserID", userInfo.getLoginname());
				map.put("requestUserDept", userInfo.getCompanyName());
				map.put("requestUserGroup", userInfo.getDepname());
				map.put("requestUserMobile", userInfo.getMobile());
				map.put("requestUserPosition", userInfo.getPosition());
				map.put("ISVIP", userInfo.getIsVip());
			}
			return map;
		}
		return null;
	}

	/**
	 * 优先级判断逻辑，紧急程度与优先级配置关系map存放规则数据初始化：key:影响度,紧急程度，value：优先级
	 * @return
	 */
	private Map<String, String> initpriorityMap() {
		Map<String, String> priorityMap = new HashMap<String, String>();
		priorityMap.put("1,1","1");
		priorityMap.put("2,1","2");
		priorityMap.put("3,1","3");
		priorityMap.put("1,2","2");
		priorityMap.put("2,2","2");
		priorityMap.put("3,2","3");
		priorityMap.put("1,3","3");
		priorityMap.put("2,3","3");
		priorityMap.put("3,3","4");
		return priorityMap;
	}

	public UserManagerService getUserManagerService() {
		return userManagerService;
	}

	public void setUserManagerService(UserManagerService userManagerService) {
		this.userManagerService = userManagerService;
	}
	
	public UserInfo getUserInfo() {
		return userInfo;
	}
	
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public void setBaseApiAction(BaseAction baseApiAction) {
		this.baseApiAction = baseApiAction;
	}

	public BaseAction getBaseApiAction() {
		return baseApiAction;
	}

	public void setWorkflowServiceEx(WorkflowService workflowServiceEx) {
		this.workflowServiceEx = workflowServiceEx;
	}

	public WorkflowService getWorkflowServiceEx() {
		return workflowServiceEx;
	}
}