package com.ultrapower.eoms.common.core.component.sla.web;

import java.io.IOException;

import com.ultrapower.eoms.common.core.component.sla.model.SlaMailAction;
import com.ultrapower.eoms.common.core.component.sla.service.SlaMailActionService;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;

/**
 * 邮件动作管理
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-9-3 上午10:44:20
 */
public class SlaMailDealAction extends BaseAction{

	private SlaMailActionService slaMailActionService;
	public SlaMailAction slaMailAction;
	public String pid;
	
	public String emailTransSave(){
		String returnMessage= StringUtils.checkNullString(this.getRequest().getParameter("returnMessage"));
		String refreshId= StringUtils.checkNullString(this.getRequest().getParameter("refreshId"));
		String refreshBusiness= StringUtils.checkNullString(this.getRequest().getParameter("refreshBusiness"));
		
		if(returnMessage!=null&&!refreshId.equals("")){
			this.getRequest().setAttribute("returnMessage", returnMessage);
			this.getRequest().setAttribute("refreshId", refreshId);
			this.getRequest().setAttribute("nodeId", refreshId);
		}
		
		if(returnMessage!=null&&!refreshBusiness.equals("")){
			this.getRequest().setAttribute("returnMessage", returnMessage);
			this.getRequest().setAttribute("refreshBusiness", refreshBusiness);
			this.getRequest().setAttribute("nodeId", refreshBusiness);
		}
		
		if(id!=null)
			slaMailAction = slaMailActionService.get(id);
		return SUCCESS;
	}
	
	public String saveSlaMail(){
		String returnMessage = "";
		if(slaMailAction!=null){
			boolean flag = false;
			UserSession user = this.getUserSession();
			slaMailAction.setLastmodifier(user.getPid());
			slaMailAction.setLastmodifytime(TimeUtils.getCurrentTime());
			String noticeroleid = StringUtils.checkNullString(this.getRequest().getParameter("noticeroleid"));
			String noticerolename = StringUtils.checkNullString(this.getRequest().getParameter("noticerolename"));
			slaMailAction.setNoticeroleid(noticeroleid);
			slaMailAction.setNoticerolename(noticerolename);
			if(slaMailAction.getPid()!=null){//修改
				flag = slaMailActionService.updateSlaMailAction(slaMailAction);
				if(flag){
					returnMessage = "true";
				}else{
					returnMessage = "false"; 
				}
			}else{//添加
				slaMailAction.setCreater(user.getPid());
				slaMailAction.setCreatetime(TimeUtils.getCurrentTime());
				flag = slaMailActionService.addSlaMailAction(slaMailAction);
				if(flag){
					returnMessage = "true";
				}else{
					returnMessage = "false";
				}
			}
		}else{
			returnMessage = "false";
		}
		return findRedirectPar("emailTransSave.action?returnMessage="+returnMessage+"&refreshId="+slaMailAction.getActiontype());
	}

	public String delSlaMail(){
		String refreshBusiness= StringUtils.checkNullString(this.getRequest().getParameter("refreshBusiness"));
		String returnMessage = "";
		boolean flag = false;
		if(pid!=null)
			flag = slaMailActionService.deleteSlaMailAction(pid);
		if(flag){
			returnMessage = "true";
		}else{
			returnMessage = "false";
		}
		return findRedirectPar("emailTransSave.action?returnMessage="+returnMessage+"&refreshBusiness="+refreshBusiness);
	}

	/**
	 * 检查动作标识符是否唯一
	 */
	public void actionMarkUnique()
	{
		boolean b = false;
		String actionmark = StringUtils.checkNullString(this.getRequest().getParameter("actionmark"));
		if(!"".equals(actionmark))
		{
			b = slaMailActionService.uniqueMark(actionmark);
		}
		try 
		{
			this.getResponse().getWriter().print(String.valueOf(b));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public SlaMailAction getSlaMailAction() {
		return slaMailAction;
	}

	public void setSlaMailAction(SlaMailAction slaMailAction) {
		this.slaMailAction = slaMailAction;
	}

	public void setSlaMailActionService(SlaMailActionService slaMailActionService) {
		this.slaMailActionService = slaMailActionService;
	}
}
