package com.ultrapower.eoms.common.core.component.sms.web;

import com.ultrapower.eoms.common.core.component.sms.model.SmsOrderPlan;
import com.ultrapower.eoms.common.core.component.sms.service.SmOrderPlanService;
import com.ultrapower.eoms.common.core.util.Internation;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;

/**
 * 计划短信订阅业务处理
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-9-1 上午09:54:15
 */
public class SmOrderPlanAction extends BaseAction{

	
	private SmOrderPlanService orderPlanService;
	public String smorderplanPid;
	public SmsOrderPlan smsOrderPlan;
	public String pids;
	
	public String smOrderPlanList(){
		return SUCCESS;
	}
	
	public String smOrderPlanSave(){
		if(smorderplanPid!=null && !smorderplanPid.equals(""))
			smsOrderPlan = orderPlanService.get(smorderplanPid);
		return SUCCESS;
	}

	public String addOrderDuty(){
		String parafresh = "";
		String returnMessage = "";
		if(smsOrderPlan!=null){
			UserSession userSession = this.getUserSession();
			smsOrderPlan.setLoginname(userSession.getLoginName());
			smsOrderPlan.setUsermobile(userSession.getMobile());
			smsOrderPlan.setLastmodifier(userSession.getPid());
			smsOrderPlan.setLastmodifytime(TimeUtils.getCurrentTime());
			boolean flag = false;
			if(smsOrderPlan.getPid()!=null){//修改操作
				flag = orderPlanService.updateOrderInfo(smsOrderPlan);
				if(flag){
					returnMessage = Internation.language("com_msg_saveSuccess")+"!";
					parafresh = "true";
				}else{
					returnMessage = Internation.language("sm_msg_sendFaild")+"!";
					parafresh = "false";
				}
			}else{//添加操作
				flag = orderPlanService.addOrderInfo(smsOrderPlan);
				if(flag){
					returnMessage = Internation.language("com_msg_saveSuccess")+"!";
					parafresh = "true";
				}else{
					returnMessage = Internation.language("sm_msg_sendFaild")+"!";
					parafresh = "false";
				}
			}
		}else{
			returnMessage = Internation.language("sm_msg_sendFaild")+"!";
			parafresh = "false";
		}
		this.getRequest().setAttribute("parafresh", parafresh);
		this.getRequest().setAttribute("returnMessage", returnMessage);
		return "refresh";
	}
	
	public String delSmOrderDuty(){
		if(pids!=null){
			String[] pidArr = pids.split(",");
			for(int p=0;p<pidArr.length;p++){
				orderPlanService.deleteOrderInfo(pidArr[p]);
			}
		}
		return this.findForward("smOrderPlanList");
	}
	
	public void setOrderPlanService(SmOrderPlanService orderPlanService) {
		this.orderPlanService = orderPlanService;
	}

	public String getSmorderplanPid() {
		return smorderplanPid;
	}

	public void setSmorderplanPid(String smorderplanPid) {
		this.smorderplanPid = smorderplanPid;
	}
	
	public String getPids() {
		return pids;
	}

	public void setPids(String pids) {
		this.pids = pids;
	}

	public SmsOrderPlan getSmsOrderPlan() {
		return smsOrderPlan;
	}
	
	public void setSmsOrderPlan(SmsOrderPlan smsOrderPlan) {
		this.smsOrderPlan = smsOrderPlan;
	}
	
	
	
}
