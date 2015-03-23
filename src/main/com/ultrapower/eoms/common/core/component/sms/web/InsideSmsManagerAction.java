package com.ultrapower.eoms.common.core.component.sms.web;

import java.util.ArrayList;
import java.util.List;
import com.ultrapower.eoms.common.core.component.sms.service.InsideSmsService;
import com.ultrapower.eoms.common.core.util.Internation;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ultrasm.service.UserManagerService;
import com.ultrapower.eoms.ultrasm.util.UltraSmUtil;

/**
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-7-29 下午03:30:41
 */
public class InsideSmsManagerAction extends BaseAction{

	private InsideSmsService insidesm;
	private UserManagerService userManagerService;
	public String getManualPage(){
		String message = StringUtils.checkNullString(this.getRequest().getParameter("returnMsg"));
		if(!"".equals(message))
		{
			if("1".equals(message))
			{
				message = Internation.language("sm_msg_sendSuccess")+"!";
			}
			else
			{
				message = Internation.language("sm_msg_sendFaild")+"!";
			}
			this.getRequest().setAttribute("message", message);
		}
		return findForward("manualMsgSend");
		
	}
	
	/**
	 * 手动发送短信
	 */
	public String manualSmSend(){
		String recepterId = StringUtils.checkNullString(this.getRequest().getParameter("smRecepterId"));
		String recepGroupId = StringUtils.checkNullString(this.getRequest().getParameter("smReceptGroupId"));
		String sendType = StringUtils.checkNullString(this.getRequest().getParameter("sendType"));
		String sendTime = StringUtils.checkNullString(this.getRequest().getParameter("sendTime"));
		String smContent = StringUtils.checkNullString(this.getRequest().getParameter("smContent"));
		UserSession usersession = this.getUserSession();
		String message = "2";
		if("".equals(recepterId) && "".equals(recepGroupId))
		{//如果没有接受者
			return this.findRedirectPar("getManualPage.action?message="+message);
		}
		if("2".equals(sendType) && "".equals(sendTime))
		{//如果是定时发送却没有发送时间
			return this.findRedirectPar("getManualPage.action?message="+message);
		}
		List<String> allmobile = new ArrayList<String>(); //所有手机号码集合
		if(!"".equals(recepterId))
		{//通过人员ID找到的手机号码
			List userIdlst = UltraSmUtil.arrayToList(recepterId.split(","));
			List<String> mobileList_user = UltraSmUtil.removeNullData(userManagerService.getMobileListByUserId(userIdlst));
			for(int i=0;i<mobileList_user.size();i++)
			{
				if(!"".equals(mobileList_user.get(i).trim()))
				{
					allmobile.add(mobileList_user.get(i));
				}
			}
		}
		if(!"".equals(recepGroupId))
		{//通过部门ID找到该组成员的手机号码
			List<String> mobileList_dep = UltraSmUtil.removeNullData(userManagerService.getMobileListByDepId(UltraSmUtil.arrayToList(recepGroupId.split(","))));
			for(int i=0;i<mobileList_dep.size();i++)
			{
				if(!"".equals(mobileList_dep.get(i).trim()))
				{
					allmobile.add(mobileList_dep.get(i));
				}
			}
		}
		int allmobileLen = 0;
		if(allmobile!=null)
			allmobileLen = allmobile.size();
		if(allmobileLen!=0){
			if("1".equals(sendType))
			{//即时发送
				for(int i=0;i<allmobile.size();i++)
				{
					insidesm.sendsm(allmobile.get(i),smContent,Internation.language("sm_lb_manualSend")+":"+usersession.getLoginName());
				}
				message = "1";
			}
			if("2".equals(sendType))
			{//定时发送
				for(int i=0;i<allmobile.size();i++)
				{
					insidesm.sendsm(allmobile.get(i),smContent,Internation.language("sm_lb_manualSend")+":"+usersession.getLoginName(),TimeUtils.getCurrentTime(),0);
				}
				message = "1";
			}
		}else{
			message = "2";
		}
		String redirectPar = "getManualPage.action?returnMsg="+message;
		return this.findRedirectPar(redirectPar);
	}
	
	
	public void setUserManagerService(UserManagerService userManagerService) {
		this.userManagerService = userManagerService;
	}

	public void setInsidesm(InsideSmsService insidesm) {
		this.insidesm = insidesm;
	}
}
