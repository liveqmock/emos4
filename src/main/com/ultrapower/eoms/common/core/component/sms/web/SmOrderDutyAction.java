package com.ultrapower.eoms.common.core.component.sms.web;

import com.ultrapower.eoms.common.core.component.sms.model.SmsOrderDuty;
import com.ultrapower.eoms.common.core.component.sms.service.SmOrderDutyService;
import com.ultrapower.eoms.common.core.util.Internation;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;

/**
 * 值班短信订阅业务处理
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-8-31 上午11:07:57
 */
public class SmOrderDutyAction extends BaseAction{

	private SmOrderDutyService orderDutyService;
	public String smorderdutyPid;
	public String pids;
	public SmsOrderDuty smsOrderDuty;
	
	public String smOrderDutyList(){
		return SUCCESS;
	}
	
	public String smOrderDutySave(){
		if(smorderdutyPid!=null && !smorderdutyPid.equals(""))
			smsOrderDuty = orderDutyService.get(smorderdutyPid);
		return SUCCESS;
	}
	
	public String addOrderDuty(){
		String parafresh = "";
		String returnMessage = "";
		if(smsOrderDuty!=null){
			UserSession userSession = this.getUserSession();
			smsOrderDuty.setLoginname(userSession.getLoginName());
			smsOrderDuty.setUsermobile(userSession.getMobile());
			smsOrderDuty.setLastmodifier(userSession.getPid());
			smsOrderDuty.setLastmodifytime(TimeUtils.getCurrentTime());
			boolean flag = false;
			if(smsOrderDuty.getPid()!=null){//修改操作
				flag = orderDutyService.updateOrderInfo(smsOrderDuty);
				if(flag){
					returnMessage = Internation.language("com_msg_saveSuccess")+"!";
					parafresh = "true";
				}else{
					returnMessage = Internation.language("sm_msg_sendFaild")+"!";
					parafresh = "false";
				}
			}else{//添加操作
				flag = orderDutyService.addOrderInfo(smsOrderDuty);
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
				orderDutyService.deleteOrderInfo(pidArr[p]);
			}
		}
		return this.findForward("smOrderDutyList");
	}
	
	
	
	public SmsOrderDuty getSmsOrderDuty() {
		return smsOrderDuty;
	}

	public void setSmsOrderDuty(SmsOrderDuty smsOrderDuty) {
		this.smsOrderDuty = smsOrderDuty;
	}

	public String getSmorderdutyPid() {
		return smorderdutyPid;
	}

	public void setSmorderdutyPid(String smorderdutyPid) {
		this.smorderdutyPid = smorderdutyPid;
	}

	public String getPids() {
		return pids;
	}

	public void setPids(String pids) {
		this.pids = pids;
	}

	public void setOrderDutyService(SmOrderDutyService orderDutyService) {
		this.orderDutyService = orderDutyService;
	}


}
