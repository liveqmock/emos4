package com.ultrapower.wfinterface.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.ultrabpp.api.BaseAction;
import com.ultrapower.eoms.ultrasm.model.BsTCmbcAlarmnote;
import com.ultrapower.eoms.workflow.api.bean.BaseAttachmentInfo;
import com.ultrapower.eoms.workflow.api.bean.DealObject;
import com.ultrapower.wfinterface.RecordLog;

/**
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2012-7-10 下午04:20:37
 * @descibe
 */
public class SheetImplService {

	/**提供字段如下（11个）：
	 * 服务 告警ID、事件标题、事件描述、应用系统分类1
	 * 告警一级分类、告警二级分类、告警三级分类、地 点、发生时间
	 * 实际开始时间、联系部门
	 */
	
	public String createForm(String alarmID, String titile, String desc,
			String sysType, String alarmOneType, String alarmTwoType,
			String alarmThreeType, String local, String happenTime,
			String trustTime, String dep) {
		
			if(happenTime != "")
				happenTime = String.valueOf(TimeUtils.formatDateStringToInt(happenTime));
		
			if(trustTime != "")
				trustTime = String.valueOf(TimeUtils.formatDateStringToInt(trustTime));
			
			Map  tthFields = new HashMap();
			tthFields.put("alarmID",alarmID);
			tthFields.put("BaseSummary",titile);
			tthFields.put("description",desc);
			tthFields.put("alarmSystem",sysType);
			tthFields.put("classificationOne",alarmOneType);
			tthFields.put("classificationTwo",alarmTwoType);
			tthFields.put("classificationThree",alarmThreeType);
			tthFields.put("alarmLocal",local);
			tthFields.put("eventTime",happenTime);
			tthFields.put("alarmStartTime",trustTime);
			tthFields.put("requestPartment",dep);		
			
			String result="";
			List<DealObject> dealObj = new ArrayList<DealObject>();
			
			String baseSchema = "UBP_EVENT_MANAG";
			String baseId = null;
			String taskID = "";
			String dealDesc = "NMS接口派发工单";
			String loginName = "Demo";
			String actionID = "";
			String actionType = "SAVE";
			String actionText = "";
			String assignStr = "";
			List<BaseAttachmentInfo> attachs = null;
			
			BaseAction action = null;
			BsTCmbcAlarmnote bsTCmbcAlarmnote = null;
			action = new BaseAction();
			String faultlog = "";
			try {
			//新建
			   result= action.doAction(baseId, baseSchema, taskID, loginName, actionID, actionType, actionText, assignStr, dealDesc, tthFields, attachs);
			   System.out.println("==============================================================result="+result);
			} catch (Exception e) {
				faultlog = e.getMessage();
				e.printStackTrace();
			}finally{
				bsTCmbcAlarmnote = new BsTCmbcAlarmnote();
				bsTCmbcAlarmnote.setAlarmid(alarmID);
				bsTCmbcAlarmnote.setAlarmtitle(titile);
				bsTCmbcAlarmnote.setAlarmdesc(desc);
				bsTCmbcAlarmnote.setAlarmsystype(sysType);
				bsTCmbcAlarmnote.setAlarmonetype(alarmOneType);
				bsTCmbcAlarmnote.setAlarmtwotype(alarmTwoType);
				bsTCmbcAlarmnote.setAlarmthreetype(alarmThreeType);
				bsTCmbcAlarmnote.setAlarmlocal(local);
				bsTCmbcAlarmnote.setAlarmhappentime(Long.parseLong(happenTime));
				bsTCmbcAlarmnote.setAlarmstarttime(Long.parseLong(trustTime));
				bsTCmbcAlarmnote.setDep(dep);
				if("".equals(result) || result == null)
				{
					bsTCmbcAlarmnote.setIscreatesheet("0");
					bsTCmbcAlarmnote.setFaultlog(faultlog);
				}else{
					bsTCmbcAlarmnote.setIscreatesheet("1");
				}
				RecordLog.pringAlarmLog(bsTCmbcAlarmnote);
			}
			return result;
	}
}
