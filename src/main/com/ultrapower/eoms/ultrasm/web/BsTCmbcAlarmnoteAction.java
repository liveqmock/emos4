package com.ultrapower.eoms.ultrasm.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.util.platform.PID;

import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.ultrasm.model.BsTCmbcAlarmnote;
import com.ultrapower.eoms.ultrasm.service.BsTCmbcAlarmnoteService;
import com.ultrapower.eoms.workflow.api.bean.BaseAttachmentInfo;
import com.ultrapower.eoms.workflow.api.bean.DealObject;
import com.ultrapower.wfinterface.RecordLog;
import com.ultrapower.wfinterface.services.SheetImplService;

public class BsTCmbcAlarmnoteAction extends BaseAction {

	private BsTCmbcAlarmnoteService bsTCmbcAlarmnoteService;
	private BsTCmbcAlarmnote bsTCmbcAlarmnote;
	private String pid;
	private String statueIds;

	
	/**
	 * 返回工单维护列表页面
	 * 
	 * @return
	 */
	public String bsTCmbcAlarmnoteList() {

		return SUCCESS;
	}

	/**
	 * 查看页
	 * 
	 * @return
	 */
	public String viewBsTCmbcAlarmnote() {
		bsTCmbcAlarmnote = bsTCmbcAlarmnoteService.getBsTCmbcAlarmnote(pid);
		return SUCCESS;
	}
   
	/**
	 * 改变标志
	 */
	public boolean saveOrUpdateBsTCmbcAlarmnote(BsTCmbcAlarmnote bsTCmbcAlarmnote)
	{
		boolean falg ;
		if(bsTCmbcAlarmnote==null)
			return false;
		else 
			falg = bsTCmbcAlarmnoteService.updateBsTCmbcAlarmnote(bsTCmbcAlarmnote);
		return falg;
	}
	/**
	 * 
	 * 重新建单
	 */
	public String createAlarm() {
		String result = "";
		boolean falg = false;
		String[] len = statueIds.split(",");
		for (int j = 0; j < len.length; j++) {
			String[] lens = len[j].split(";");
			String ids = lens[0];
			bsTCmbcAlarmnote = bsTCmbcAlarmnoteService.getBsTCmbcAlarmnote(ids);
			if(bsTCmbcAlarmnote!=null)
			{
				String alarmid = bsTCmbcAlarmnote.getAlarmid();
				String alarmtitle = bsTCmbcAlarmnote.getAlarmtitle();
				String alarmdesc = bsTCmbcAlarmnote.getAlarmdesc();
				String alarmsystype = bsTCmbcAlarmnote.getAlarmsystype();
				String alarmonetype = bsTCmbcAlarmnote.getAlarmonetype();
				String alarmtwotype = bsTCmbcAlarmnote.getAlarmtwotype();
				String alarmthreetype = bsTCmbcAlarmnote.getAlarmthreetype();
				String alarmlocal = bsTCmbcAlarmnote.getAlarmlocal();
				Long alarmhappentime = bsTCmbcAlarmnote.getAlarmhappentime();
				String alarmhappentimes = alarmhappentime+"";
				Long alarmstarttime = bsTCmbcAlarmnote.getAlarmstarttime();
				String alarmstarttimes = alarmstarttime+"";
				String dep = bsTCmbcAlarmnote.getDep();
				result = this.createForm(alarmid, alarmtitle, alarmdesc,
						alarmsystype, alarmonetype, alarmtwotype, alarmthreetype,
						alarmlocal, alarmhappentimes, alarmstarttimes, dep);
			}
			if(!"".equals(result))//建单成功
			{
				this.saveOrUpdateBsTCmbcAlarmnote(bsTCmbcAlarmnote);
			}
		}

		return this.findForward("bsTCmbcAlarmnoteList");
	}

	
	private String createForm(String alarmID, String titile, String desc,
			String sysType, String alarmOneType, String alarmTwoType,
			String alarmThreeType, String local, String happenTime,
			String trustTime, String dep) {
		
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
			String dealDesc = "接口派发工单";
			String loginName = "Demo";
			String actionID = "";
			String actionType = "SAVE";
			String actionText = "";
			String assignStr = "";
			List<BaseAttachmentInfo> attachs = null;
			
			com.ultrapower.eoms.ultrabpp.api.BaseAction action = null;
			BsTCmbcAlarmnote bsTCmbcAlarmnote = null;
			action = new com.ultrapower.eoms.ultrabpp.api.BaseAction();
			String faultlog = "";
			try {
			//新建
			   result= action.doAction(baseId, baseSchema, taskID, loginName, actionID, actionType, actionText, assignStr, dealDesc, tthFields, attachs);
			   System.out.println("==============================================================result="+result);
			} catch (Exception e) {
				faultlog = e.getMessage();
				e.printStackTrace();
			}
			return result;
	}
	
	
	public String delTCmbcAlarmnote(){
		
		String[] len = statueIds.split(",");
		for (int j = 0; j < len.length; j++) {
			String[] lens = len[j].split(";");
			String ids = lens[0];
			if(!"".equals(ids))
			{
				bsTCmbcAlarmnoteService.remove(ids);
			}
			
		}
		
	   return this.findForward("bsTCmbcAlarmnoteList");
	}
	public BsTCmbcAlarmnoteService getBsTCmbcAlarmnoteService() {
		return bsTCmbcAlarmnoteService;
	}

	public void setBsTCmbcAlarmnoteService(
			BsTCmbcAlarmnoteService bsTCmbcAlarmnoteService) {
		this.bsTCmbcAlarmnoteService = bsTCmbcAlarmnoteService;
	}

	public BsTCmbcAlarmnote getBsTCmbcAlarmnote() {
		return bsTCmbcAlarmnote;
	}

	public void setBsTCmbcAlarmnote(BsTCmbcAlarmnote bsTCmbcAlarmnote) {
		this.bsTCmbcAlarmnote = bsTCmbcAlarmnote;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getStatueIds() {
		return statueIds;
	}

	public void setStatueIds(String statueIds) {
		this.statueIds = statueIds;
	}

}
