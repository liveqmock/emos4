
package com.ultrapower.eoms.workflow.jsp.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.metadata.ClassMetadata;

import com.ultrapower.eoms.common.core.util.NumberUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.workflow.engine.core.model.ActionInfo;
import com.ultrapower.workflow.engine.core.model.DataField;
import com.ultrapower.workflow.engine.core.model.FieldDataType;

public class ProcessUtil{

	 public static String getStringFromMap(String key, Map inputs) {
		String rtn = "";
		if (inputs != null) {
			Object obj = inputs.get(key);
			if (obj != null) {
				rtn = obj.toString();
			}
		}
		return rtn;
	 } 
	 
	 public static Map cloneMap(Map src) {
		 Map dest = new HashMap();
		 if (src != null) {
			Iterator iter = src.keySet().iterator();
			while (iter.hasNext()) {
				Object obj = (Object) iter.next();
				dest.put(obj, src.get(obj));
			}
		}
		 return dest;
	 }
	 
	/**
	 * Description:转换从页面接收到的数据
	 * @author chenliang
	 * Create Date：2008-6-24
	 * @param map
	 * @return
	 */
	public static Map convertMap(Map map) {
		Map retMap = new HashMap();
		if (map == null) {
			map = new HashMap();
		}
		Iterator iter = map.keySet().iterator();
		while (iter.hasNext()) {
			String key = iter.next().toString();
			String value = "";
			Object valObj = map.get(key);
			if (valObj instanceof String[]) {
				String[] arrValue = (String[]) valObj;
				for (int i = 0; i < arrValue.length; i++) {
					if (StringUtils.isNotBlank(value)) {
						value = value + ";";
					}
					value = value + arrValue[i];
				}
			} else {
				value = String.valueOf(valObj);
			}
			if (StringUtils.isNotBlank(value)) {
				retMap.put(key.toUpperCase(), value);
			} else {
				retMap.put(key.toUpperCase(), "");
			}
		}
		return retMap;
	}
	
	/**
	 * Description:转换从页面接收的数据，同时处理long型字段
	 * @author chenliang
	 * Create Date：2008-6-24
	 * @param dateCfg
	 * @param map
	 * @return
	 */
	public static Map convertMapFormat(String baseSchema, Map map) {
		Map retMap = convertMap(map);
		formatLong(baseSchema,retMap);
		return retMap;
	}

	private static void formatLong(String baseSchema, Map map) {
		if (map == null) {
			map = new HashMap();
		}
		org.hibernate.SessionFactory asf = (org.hibernate.SessionFactory) WebApplicationManager
				.getBean("sessionFactory");
		ClassMetadata classmeta = asf.getClassMetadata(baseSchema);
		if (classmeta != null) {
			String[] properties = classmeta.getPropertyNames();
			for (int i = 0; i < properties.length; i++) {
				String type = classmeta.getPropertyType(properties[i]).getName();
				String col = properties[i];
				if ("long".equalsIgnoreCase(type) && map.containsKey(col)) {
					Object obj = map.get(col);
					if (obj != null) {
						String strLong = obj.toString();
						if (strLong.indexOf(":") > 0) {
							long formatDateStringToInt = TimeUtils.formatDateStringToInt(strLong);
							map.put(col, formatDateStringToInt);
						} else {
							map.put(col, NumberUtils.formatToLong(strLong));
						}
					}
				}
			}
		}
	}
	
	public static Map<String, DataField> putAll(Map<String, DataField> inputData, Map inputs) {
		if (inputData != null && inputs != null) {
			Iterator<String> iter = inputs.keySet().iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				Object obj = inputs.get(key);
				if (obj != null) {
					inputData.put(key, new DataField(key, FieldDataType.String, obj.toString()));
				}
			}
		}
		return inputData;
	}
	
	
	public static List<ActionInfo> formatActionInfo(String assingStr) {
		//U#:lihaoyuan#:ASSIGN#:2#:acceptDate#:assignDate#:dealDate#:tartetId#:stepCode#:HUANG-20100721094851#:这里写派发说明
		//字符串规则：派发类型(U:人,G:组)@:派发角色ID(人:登录名,组:组ID)@:处理类型(1:流转,2:协办,3:抄送,4:完成,5:归档,6:提审)@:组处理模式(1:共享,2:独占,3:管理者)@:派发说明@;
		List<ActionInfo> acInfoList = new ArrayList<ActionInfo>();
		if(StringUtils.isNotBlank(assingStr)) {
			String[] assAry = assingStr.split("#;");
			if (!ArrayUtils.isEmpty(assAry)) {
				for(String assignee : assAry) {
					parseAssignStr(acInfoList, assignee);
				}
			}
		}
		return acInfoList;
	}

	public static void parseAssignStr(List<ActionInfo> acInfoList, String assignee) {
		String[] assigneeArr = StringUtils.splitByWholeSeparatorPreserveAllTokens(assignee, "#:");
		if (!ArrayUtils.isEmpty(assigneeArr)) {
			String acType = assigneeArr[0];
			String actorType = "USER";
			if (acType.equals("G")) {
				actorType = "GROUP";
			} else if (acType.equals("R")) {
				actorType = "ROLE";
			}
			ActionInfo acInfo = new ActionInfo();
			acInfo.setActorId(assigneeArr[1]);
			acInfo.setActorType(actorType);
			String dealType = assigneeArr[3];
			acInfo.setDealType(getDealType(dealType));
			
			acInfo.setActionType(assigneeArr[2]);
			acInfo.setAcceptOverTimeDate(NumberUtils.formatToLong(assigneeArr[4]));
			acInfo.setAssignOverTimeDate(NumberUtils.formatToLong(assigneeArr[5]));
			acInfo.setDealOverTimeDate(NumberUtils.formatToLong(assigneeArr[6]));
			acInfo.setStepCode(assigneeArr[8]);
			acInfo.setActionDesc(assigneeArr[10]);
			if (assigneeArr.length == 11) {//子流程定义名城名，有就赋值，没有就为空。临时修改
				acInfo.setSubName(assigneeArr[9]);
			} 
			
			if(StringUtils.isNotBlank(assigneeArr[7])) {//targetIds 不为空
				String[] sps = assigneeArr[7].split(";");
				if (sps != null) {
					for (int i = 0; i < sps.length; i++) {
						String str = sps[i];
						if (StringUtils.isNotBlank(str)) {
							String[] codeAndId = str.split(":");
							if (codeAndId != null) {
								ActionInfo ac = acInfo.clone();
								ac.setStepCode(codeAndId[0]);
								ac.setStepId(codeAndId[1]);
								acInfoList.add(ac);
							}
						}
					}
				}
			} else {
				acInfoList.add(acInfo);
			}
		}
	}
	
	public static String getDealType(String dealStr) {
		String dealType = "SHARE";
		if(StringUtils.isNotBlank(dealStr)) {
			if("1".equals(dealStr)) {
				dealType = "SHARE";
			} else if("2".equals(dealStr)) {
				dealType = "ONLY";
			} else if("3".equals(dealStr)) {
				dealType = "MANAGEMANT";
			}
		}
		return dealType;
	}
}
