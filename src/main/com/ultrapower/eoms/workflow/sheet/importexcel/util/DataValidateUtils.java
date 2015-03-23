package com.ultrapower.eoms.workflow.sheet.importexcel.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.ultrasm.service.UserManagerService;
import com.ultrapower.eoms.workflow.sheet.importexcel.constants.ExcelFieldConstants;

/**
 * 数据验证专用方法
 * 
 * @author 飞翔
 * 
 */
public class DataValidateUtils {
	/**
	 * 数据验证专用方法：含有用户信息验证及数据字典项验证
	 * @param userManagerService 用于获取用户的基本信息
	 * @param baseSchema
	 * @param dataRow
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String validate(UserManagerService userManagerService, String baseSchema, HashMap rowHashMap) {
		String returnStr = "";
		returnStr += userValidate(userManagerService, baseSchema, rowHashMap);
		returnStr += dictValidate(baseSchema, rowHashMap);
		return returnStr;
	}

	/**
	 * 验证人员信息专用方法
	 * 
	 * @param userManagerService
	 * @param baseSchema
	 * @param rowHashMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static String userValidate(UserManagerService userManagerService, String baseSchema, HashMap rowHashMap) {
		String returnStr = "";
		boolean flag;
		String baseCreatorLoginname = rowHashMap.get(ExcelFieldConstants.BASECREATORLOGINNAME).toString().toUpperCase();
		String baseCreatorFullname = rowHashMap.get(ExcelFieldConstants.BASECREATORFULLNAME).toString();
		// 验证人员具体信息
		flag = samePersonValidate(userManagerService, baseCreatorLoginname, baseCreatorFullname);
		if (flag) {
			returnStr += "";
		} else {
			returnStr += "建单人填写有误. ";
		}
		if ("CBD_INCIDENT".equals(baseSchema)) {
			// 事件工单
			String contactOA = rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.CONTACTOA).toString().toUpperCase();
			String contactPerson = rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.CONTACTPERSON).toString();
			String resolveUserOA = ((String) rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.RESOLVEUSEROA)).toUpperCase();
			String resolveUser = (String) rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.RESOLVEUSER);
			
			flag = samePersonValidate(userManagerService, contactOA, contactPerson);
			if (flag) {
				returnStr += "";
			} else {
				returnStr += "联系人填写有误. ";
			}
			if ("" != resolveUserOA && "" != resolveUser) {
				flag = samePersonValidate(userManagerService, resolveUserOA, resolveUser);
				if (flag) {
					returnStr += "";
				} else {
					returnStr += "解决人填写有误. ";
				}
			} else {
				returnStr += "未进行解决人基本信息验证. ";
			}
		} else if ("CDB_SERVICEREQUEST".equals(baseSchema)) {
			// 服务请求
			String requestUserOA = rowHashMap.get(ExcelFieldConstants.CDB_SERVICEREQUEST.REQUESTUSEROA).toString().toUpperCase();
			String requestUser = rowHashMap.get(ExcelFieldConstants.CDB_SERVICEREQUEST.REQUESTUSER).toString();
			String dealUserOA = ((String) rowHashMap.get(ExcelFieldConstants.CDB_SERVICEREQUEST.DEALUSEROA)).toUpperCase();
			String dealUser = (String) rowHashMap.get(ExcelFieldConstants.CDB_SERVICEREQUEST.DEALUSER);
			
			flag = samePersonValidate(userManagerService, requestUserOA, requestUser);
			if (flag) {
				returnStr += "";
			} else {
				returnStr += "联系人填写有误. ";
			}
			if ("" != dealUserOA && "" != dealUser) {
				flag = samePersonValidate(userManagerService, dealUserOA, dealUser);
				if (flag) {
					returnStr += "";
				} else {
					returnStr += "处理人填写有误. ";
				}
			} else {
				returnStr += "未进行处理人基本信息验证. ";
			}
		} else {
			returnStr += "未进行人员基本信息验证. ";
		}
		return returnStr;
	}

	/**
	 * 用于验证用户提交的OA号与用户名是否是同一个人，如果是同一个人， 验证成功，返回true，如果不是同一个人，验证失败，返回false
	 * 
	 * @param userManagerService
	 * @param loginname
	 * @param fullname
	 * @return
	 */
	private static boolean samePersonValidate(UserManagerService userManagerService, String loginname, String fullname) {
		String tmp_fullname = userManagerService.getUserNameByLoginName(loginname.toUpperCase());// 根据用户登录名获取用户名称
		if (null != tmp_fullname && tmp_fullname != "") {
			if (tmp_fullname.equals(fullname)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	/**
	 * 验证字典项信息专用方法
	 * @param baseSchema
	 * @param rowHashMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static String dictValidate(String baseSchema, HashMap rowHashMap) {
		String returnStr = "";
		String closeCode;
		String source;
		if ("CBD_INCIDENT".equals(baseSchema)) {
			String phenoClass1 = (String) rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.INCIDENTPHENOCLASS1);
			String phenoClass2 = (String) rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.INCIDENTPHENOCLASS2);
			String phenoClass3 = (String) rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.INCIDENTPHENOCLASS3);
			String phenoClass4 = (String) rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.INCIDENTPHENOCLASS4);
			String sourceClass1 = (String) rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.INCIDENTSOURCECLASS1);
			String sourceClass2 = (String) rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.INCIDENTSOURCECLASS2);
			String sourceClass3 = (String) rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.INCIDENTSOURCECLASS3);
			String sourceClass4 = (String) rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.INCIDENTSOURCECLASS4);
			String solutionType = (String) rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.INCIDENTSOLUTIONTYPE);
			String identification = (String) rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.INCIDENTIDENTIFICATION);
			String effectdgree = (String) rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.INCIDENT_EFFECTDGREE);
			String urgentdgree = (String) rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.INCIDENT_URGENTDGREE);
			String propertie = (String) rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.INCIDENTPROPERTIE);
			closeCode = (String) rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.CLOSECODE);
			source = (String) rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.ICIDENTSOURCE);
			// 通过查询bs_t_sm_commonTree，验证事件原因、现象分类的值
			returnStr += commonTreeValidate(sourceClass1, sourceClass2, sourceClass3, sourceClass4, phenoClass1, phenoClass2, phenoClass3, phenoClass4);
			// 通过查询bs_t_sm_dicitem，验证事件来源、事件影响度、事件紧急度、事件性质的值
			returnStr += dicItemValidate("CBD_INCIDENT", source, effectdgree, urgentdgree, propertie);
			// 判断写死参数的字段：结束代码、解决方案类型、事件标识
			returnStr += fixedFieldValidate("CBD_INCIDENT", closeCode, solutionType, identification);
			try {
				String priorityStr = (String) rowHashMap.get(ExcelFieldConstants.CBD_INCIDENT.PRIORITY);
				int priority = Integer.parseInt(priorityStr.trim());
				if (priority > 4 && priority < 1) {
					returnStr += "优先级验证有误. ";
				} else {
					returnStr += "";
				}
			} catch (Exception e) {
				e.printStackTrace();
				e.getMessage();
				returnStr += "优先级验证有误. ";
			}
		} else if ("CDB_SERVICEREQUEST".equals(baseSchema)) {
			String category1 = (String) rowHashMap.get(ExcelFieldConstants.CDB_SERVICEREQUEST.SERVICE_CATEGORY1);
			String category2 = (String) rowHashMap.get(ExcelFieldConstants.CDB_SERVICEREQUEST.SERVICE_CATEGORY2);
			String category3 = (String) rowHashMap.get(ExcelFieldConstants.CDB_SERVICEREQUEST.SERVICE_CATEGORY3);
			String category4 = (String) rowHashMap.get(ExcelFieldConstants.CDB_SERVICEREQUEST.SERVICE_CATEGORY4);
			closeCode = (String) rowHashMap.get(ExcelFieldConstants.CDB_SERVICEREQUEST.CLOSE_CODE);
			source = (String) rowHashMap.get(ExcelFieldConstants.CDB_SERVICEREQUEST.DATARESOURCE);
			// 通过查询bs_t_sm_dicitem，验证服务请求来源、事件影响度、事件紧急度、事件性质的值
			returnStr += dicItemValidate("CDB_SERVICEREQUEST", source, "", "", "");
			// 判断写死参数的字段：结束代码
			returnStr += fixedFieldValidate("CDB_SERVICEREQUEST", closeCode, "", "");
			// 通过查询bs_t_wf_serverquest，验证服务分类的值
			returnStr += questClassValidate(category1, category2, category3, category4);
		} else {
			returnStr += "未进行字典项信息验证. ";
		}
		return returnStr;
	}

	/**
	 * 通过查询bs_t_sm_commonTree，验证事件原因、现象分类的值
	 * @param rowHashMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static String commonTreeValidate(String sourceClass1, String sourceClass2, String sourceClass3, String sourceClass4, String phenoClass1, String phenoClass2, String phenoClass3, String phenoClass4) {
		String returnStr = "";
		String phenoClass = "";
		String sourceClass = "";
		// 分类拼接判断逻辑
		sourceClass = StringUtils.joinClassStr(sourceClass1,sourceClass2,sourceClass3,sourceClass4);
		phenoClass = StringUtils.joinClassStr(phenoClass1,phenoClass2,phenoClass3,phenoClass4);
		List list;
		if (null != DataBaseOperator.incCauseClassList && !DataBaseOperator.incCauseClassList.isEmpty()) {
			list = getDicitemOrCommonTreeList(DataBaseOperator.incCauseClassList, "FULLNAME");
			if (null != list && !list.isEmpty()) {
				if (list.contains(sourceClass)) {
					returnStr += "";
				} else {
					returnStr += "事件原因分类验证有误. ";
				}
			} else {
				returnStr += "事件原因分类验证有误. ";
			}
		} else {
			returnStr += "事件原因分类验证有误. ";
		}
		
		if (null != DataBaseOperator.incPhenoClassList && !DataBaseOperator.incPhenoClassList.isEmpty()) {
			list = getDicitemOrCommonTreeList(DataBaseOperator.incPhenoClassList, "FULLNAME");
			if (null != list && !list.isEmpty()) {
				if (list.contains(phenoClass)) {
					returnStr += "";
				} else {
					returnStr += "事件现象分类验证有误. ";
				}
			} else {
				returnStr += "事件现象分类验证有误. ";
			}
		} else {
			returnStr += "事件现象分类验证有误. ";
		}
		return returnStr;
	}
	
	/**
	 * 传入指定参数，将此参数对应的字典项取出
	 * @param dinameList
	 * @param type
	 * @param name
	 * @param string
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static List getDicitemOrCommonTreeList(List<DataRow> dinameList, String name) {
		int listSize = dinameList.size();
		DataRow dataRow;
		List list = new ArrayList();
		for (int i = 0; i < listSize; i++) {
			dataRow = dinameList.get(i);
			list.add(dataRow.getString(name));
		}
		return list;
	}

	/**
	 * 通过查询bs_t_sm_dicitem，验证事件来源、事件影响度、事件紧急度、事件性质、服务请求来源的值
	 * @param rowHashMap
	 * @param baseSchema
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static String dicItemValidate(String baseSchema, String source, String effectdgree, String urgentdgree, String propertie) {
		String returnStr = "";
		List list;
		if ("CBD_INCIDENT".equals(baseSchema)) {
			if (null != DataBaseOperator.eventSourceList && !DataBaseOperator.eventSourceList.isEmpty()) {
				list = getDicitemOrCommonTreeList(DataBaseOperator.eventSourceList, "DINAME");
				if (null != list && !list.isEmpty()) {
					if (list.contains(source)) {
						returnStr += "";
					} else {
						returnStr += "事件来源验证有误. ";
					}
				} else {
					returnStr += "事件来源验证有误. ";
				}
			} else {
				returnStr += "事件来源验证有误. ";
			}
			
			if (null != DataBaseOperator.effectDgreeList && !DataBaseOperator.effectDgreeList.isEmpty()) {
				list = getDicitemOrCommonTreeList(DataBaseOperator.effectDgreeList, "DINAME");
				if (null != list && !list.isEmpty()) {
					if (list.contains(effectdgree)) {
						returnStr += "";
					} else {
						returnStr += "事件影响度验证有误. ";
					}
				} else {
					returnStr += "事件影响度验证有误. ";
				}
			} else {
				returnStr += "事件影响度验证有误. ";
			}
			
			if (null != DataBaseOperator.urgentDgreeList && !DataBaseOperator.urgentDgreeList.isEmpty()) {
				list = getDicitemOrCommonTreeList(DataBaseOperator.urgentDgreeList, "DINAME");
				if (null != list && !list.isEmpty()) {
					if (list.contains(urgentdgree)) {
						returnStr += "";
					} else {
						returnStr += "事件紧急度验证有误. ";
					}
				} else {
					returnStr += "事件紧急度验证有误. ";
				}
			} else {
				returnStr += "事件紧急度验证有误. ";
			}
			
			if (null != DataBaseOperator.propertyList && !DataBaseOperator.propertyList.isEmpty()) {
				list = getDicitemOrCommonTreeList(DataBaseOperator.propertyList, "DINAME");
				if (null != list && !list.isEmpty()) {
					if (list.contains(propertie)) {
						returnStr += "";
					} else {
						returnStr += "事件性质验证有误. ";
					}
				} else {
					returnStr += "事件性质验证有误. ";
				}
			} else {
				returnStr += "事件性质验证有误. ";
			}
		} else if ("CDB_SERVICEREQUEST".equals(baseSchema)) {
			if (null != DataBaseOperator.resouceList && !DataBaseOperator.resouceList.isEmpty()) {
				list = getDicitemOrCommonTreeList(DataBaseOperator.resouceList, "DINAME");
				if (null != list && !list.isEmpty()) {
					if (list.contains(source)) {
						returnStr += "";
					} else {
						returnStr += "服务请求来源验证有误. ";
					}
				} else {
					returnStr += "服务请求来源验证有误. ";
				}
			} else {
				returnStr += "服务请求来源验证有误. ";
			}
		} else {
			returnStr += "未进行通用字典项信息验证. ";
		}
		return returnStr;
	}

	/**
	 * 判断写死参数的字段：结束代码、解决方案类型、事件标识、服务请示结束代码
	 * @param rowHashMap
	 * @return
	 */
	private static String fixedFieldValidate(String baseSchema, String closeCode, String solutionType, String identification) {
		String returnStr = "";
		if ("CBD_INCIDENT".equals(baseSchema)) {
			if (!("已解决".equals(closeCode) || "临时解决".equals(closeCode) || "误报".equals(closeCode))) {
				returnStr += "结束代码填写有误. ";
			} else {
				returnStr += "";
			}
			if (!("最终方案".equals(solutionType) || "临时方案".equals(solutionType))) {
				returnStr += "解决方案类型填写有误. ";
			} else {
				returnStr += "";
			}
			if (!("内部事件".equals(identification) || "外部事件".equals(identification))) {
				returnStr += "事件标识填写有误. ";
			} else {
				returnStr += "";
			}
		} else if ("CDB_SERVICEREQUEST".equals(baseSchema)) {
			if (!("已解决".equals(closeCode) || "临时解决".equals(closeCode) || "审批不通过".equals(closeCode) || "处理取消".equals(closeCode))) {
				returnStr += "结束代码填写有误. ";
			} else {
				returnStr += "";
			}
		} else {
			returnStr += "未进行固定参数字段字典项信息验证. ";
		}
		return returnStr;
	}

	/**
	 * 服务请求分类数据验证专用方法
	 * @param rowHashMap
	 * @param baseSchema
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static String questClassValidate(String category1, String category2, String category3, String category4) {
		String returnStr = "";
		List list;
		String serviceCategory = "";
		// 分类拼接判断逻辑
		serviceCategory = StringUtils.joinClassStr(category1, category2, category3, category4);
		if (null != DataBaseOperator.categoryList && !DataBaseOperator.categoryList.isEmpty()) {
			list = getDicitemOrCommonTreeList(DataBaseOperator.categoryList, "SERVERQUESTFULLNAME");
			if (null != list && !list.isEmpty()) {
				if (list.contains(serviceCategory)) {
					returnStr += "";
				} else {
					returnStr += "服务分类验证有误. ";
				}
			} else {
				returnStr += "服务分类验证有误. ";
			}
		} else {
			returnStr += "服务分类验证有误. ";
		}
		return returnStr;
	}
}
