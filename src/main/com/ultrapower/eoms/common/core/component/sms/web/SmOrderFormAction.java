package com.ultrapower.eoms.common.core.component.sms.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ultrapower.eoms.common.core.component.sla.model.SlaRuleProperty;
import com.ultrapower.eoms.common.core.component.sla.model.SlaRulePropertyShow;
import com.ultrapower.eoms.common.core.component.sms.service.SmOrderFormService;
import com.ultrapower.eoms.ultrasm.service.DicManagerService;
import com.ultrapower.eoms.common.core.component.tree.manager.FormTypeTreeImpl;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.common.core.component.sms.model.SmsOrderForm;
import com.ultrapower.eoms.ultrasm.model.DicItem;

public class SmOrderFormAction extends BaseAction {
	private SmOrderFormService orderFormService;
	private DicManagerService dicManagerService;
	private List<DicItem> allFormAction = new ArrayList<DicItem>();
	private List<SlaRulePropertyShow> slaRuleProLst = new ArrayList<SlaRulePropertyShow>();
	
	public void getOrderTypeTree()
	{
		FormTypeTreeImpl otti = new FormTypeTreeImpl();
		this.renderXML(otti.getTreeXml());
	}

	public String smOrderFormFrame(){
		return SUCCESS;
	}

	public String smOrderFormLoad()
	{
		String saveResult = StringUtils.checkNullString(this.getRequest().getParameter("saveResult"));
		if(!"".equals(saveResult))
		{
			this.getRequest().setAttribute("saveResult", saveResult);
		}
		UserSession us = this.getUserSession();
		String loginName = StringUtils.checkNullString(us.getLoginName());
		String formSchema = StringUtils.checkNullString(this.getRequest().getParameter("formSchema"));
		List<SmsOrderForm> orderFormLst = orderFormService.get(loginName, formSchema);
		if(orderFormLst!=null && orderFormLst.size()>0)
		{
			String startTime = "";
			String endTime = "";
			long isHoliday = 1L;
			long status = 1L;
			StringBuffer actions = new StringBuffer();
			for(int i=0;i<orderFormLst.size();i++)
			{
				if(i==0)
				{
					startTime = StringUtils.checkNullString(orderFormLst.get(i).getStarttime());
					endTime = StringUtils.checkNullString(orderFormLst.get(i).getEndtime());
					isHoliday = orderFormLst.get(i).getIsholiday();
					status = orderFormLst.get(i).getStatus();
				}
				actions.append(orderFormLst.get(i).getFormaction()+"&comm;");
			}
			String actionStr = actions.substring(0, actions.lastIndexOf("&comm;"));
			this.getRequest().setAttribute("startTime", startTime);
			this.getRequest().setAttribute("endTime", endTime);
			this.getRequest().setAttribute("isHoliday", isHoliday);
			this.getRequest().setAttribute("status", status);
			this.getRequest().setAttribute("actionStr", actionStr);
		}
		this.getRequest().setAttribute("formSchema", formSchema);
		this.getRequest().setAttribute("userMobile", StringUtils.checkNullString(us.getMobile()).trim());
		allFormAction = dicManagerService.getDicItemByDicType("formaction");
		return findForward("smOrderForm_right");
	} 
	
	/**
	 * 获取短信订阅规则属性信息
	 */
	public void getRulePropertyInfo()
	{
		StringBuffer rpinfo =  new StringBuffer();
		rpinfo.append("[");
		String formSchema = StringUtils.checkNullString(getRequest().getParameter("formSchema"));
		String modelMark = StringUtils.checkNullString(getRequest().getParameter("modelMark"));
		slaRuleProLst = orderFormService.getSlaRuleProShowList(formSchema, modelMark);
		if(slaRuleProLst!=null && slaRuleProLst.size()>0)
		{
			for(int i=0;i<slaRuleProLst.size();i++)
			{
				SlaRulePropertyShow srps = slaRuleProLst.get(i);
				rpinfo.append("{\"fieldName\":\""+srps.getFieldname()+"\",\"rpid\":\""+srps.getRpid()+"\"");
				rpinfo.append(",\"inputType\":\""+srps.getInputtype()+"\",\"inputDataSourceType\":\""+srps.getInputdatasourcetype()+"\"");
				rpinfo.append(",\"inputValueType\":\""+srps.getInputvaluetype()+"\",\"inData\":\""+srps.getIndata()+"\"");
				if(srps.getSlaRuleProperty()==null)
				{
					rpinfo.append(",\"value\":\"\",\"displayValue\":\"\",\"operator\":\"\"");
				}
				else
				{
					rpinfo.append(",\"value\":\""+StringUtils.checkNullString(srps.getSlaRuleProperty().getValue())+"\"");
					rpinfo.append(",\"displayValue\":\""+StringUtils.checkNullString(srps.getSlaRuleProperty().getDisplayvalue()+"\""));
					rpinfo.append(",\"operator\":\""+StringUtils.checkNullString(srps.getSlaRuleProperty().getOperator()+"\""));
				}
				if(i==slaRuleProLst.size()-1)
				{
					rpinfo.append("}");
				}
				else
				{
					rpinfo.append("},");
				}
			}
		}
		rpinfo.append("]");
		try 
		{
			this.getResponse().getWriter().print(rpinfo.toString());
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 获得工单短信订阅专业维度
	 */
	public void getSmsBaseItem(){
		String loginname = getUserSession().getLoginName();
		String schema = getRequest().getParameter("schema");
		int pageNum = 1;
		try {
			pageNum = Integer.parseInt(getRequest().getParameter("pageNum"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			try {
				getResponse().getWriter().write("[]");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		List<String> items = orderFormService.getSmsBaseItemOrdered(loginname, schema, pageNum, 50);
		StringBuffer str = new StringBuffer();
		str.append("[");
		int len = 0;
		if(items!=null)
			len = items.size();
		for(int i=0;i<len;i++){
			if(i!=0)
				str.append(",");
			str.append("{");
			str.append("\"item\":\"");
			str.append(items.get(i));
			str.append("\"}");
		}
		str.append("]");
		try {
			getResponse().getWriter().write(str.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获得工单短信订阅专业维度
	 */
	public void getSmsBaseItemOfSchema(){
		String schema = getRequest().getParameter("schema");
		int pageNum = 1;
		try {
			pageNum = Integer.parseInt(getRequest().getParameter("pageNum"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			try {
				getResponse().getWriter().write("[]");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		List<String> items = orderFormService.getSmsBaseItemOfSchema(getUserSession().getLoginName(),schema, pageNum, 50);
		StringBuffer str = new StringBuffer();
		str.append("[");
		int len = 0;
		if(items!=null)
			len = items.size();
		for(int i=0;i<len;i++){
			if(i!=0)
				str.append(",");
			str.append("{");
			str.append("\"item\":\"");
			str.append(items.get(i));
			str.append("\"}");
		}
		str.append("]");
		try {
			getResponse().getWriter().write(str.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String smOrderFormSave()
	{
		String actionStr = StringUtils.checkNullString(this.getRequest().getParameter("actionStr"));
		UserSession usersession = this.getUserSession();
		String loginName = StringUtils.checkNullString(usersession.getLoginName());
		String formSchema = StringUtils.checkNullString(this.getRequest().getParameter("formSchema"));
		boolean b = false;
		if("".equals(actionStr))
		{//删除
			b = (orderFormService.deleteOrderInfo(loginName, formSchema) 
					&& orderFormService.deleteRulePropertyInfo(formSchema)
					&& orderFormService.deleteBaseItemOrdered(loginName, formSchema));
		}
		else if(!"".equals(actionStr))
		{//修改
			String userMobile = StringUtils.checkNullString(usersession.getMobile());
			String startTime = StringUtils.checkNullString(this.getRequest().getParameter("startTime"));
			String endTime = StringUtils.checkNullString(this.getRequest().getParameter("endTime"));
			Long isHoliday = Long.valueOf(StringUtils.checkNullString(this.getRequest().getParameter("isHoliday")));
			Long status = Long.valueOf(StringUtils.checkNullString(this.getRequest().getParameter("status")));
			List<SmsOrderForm> smsOrderFormList = new ArrayList<SmsOrderForm>();
			String[] actions = actionStr.split("&comm;");
			long currentTime = TimeUtils.getCurrentTime();
			for(int i=0;i<actions.length;i++)
			{
				SmsOrderForm temp = new SmsOrderForm();
				temp.setFormschema(formSchema);
				temp.setFormaction(actions[i]);
				temp.setLoginname(loginName);
				temp.setUsermobile(userMobile);
				temp.setIsholiday(isHoliday);
				temp.setStarttime(startTime);
				temp.setEndtime(endTime);
				temp.setStatus(status);
				temp.setLastmodifier(StringUtils.checkNullString(usersession.getPid()));
				temp.setLastmodifytime(currentTime);
				smsOrderFormList.add(temp);
			}
			orderFormService.addOrderInfo(loginName, formSchema, smsOrderFormList);
			String slaRuleProStr = StringUtils.checkNullString(this.getRequest().getParameter("slaRuleProStr"));
			if(!"".equals(slaRuleProStr))//如果是故障管理工单，则slaRuleProStr不可能为空
			{
				List<SlaRuleProperty> srplst = null;
				if(!"".equals(slaRuleProStr))
				{
					srplst = new ArrayList<SlaRuleProperty>();
					String[] outArr = slaRuleProStr.split("&semi;");
					for(int i=0;i<outArr.length;i++)
					{
						String[] valueArr = outArr[i].split("&comm;");
						SlaRuleProperty srp = new SlaRuleProperty();
						srp.setRuleid(formSchema);
						srp.setPropertyid(valueArr[0]);
						srp.setValue(valueArr[1]);
						srp.setDisplayvalue(valueArr[2]);
						srp.setOperator(valueArr[3]);
						srplst.add(srp);
					}
				}
				//保存规则属性信息
				orderFormService.saveRulePropertyInfo(srplst, formSchema);
			}
			String selectedItemStr = StringUtils.checkNullString(getRequest().getParameter("selectedItem"));
			if(!"".equals(selectedItemStr)){
				String[] strArr = selectedItemStr.split("&comm;");
				List<String> itemLst = java.util.Arrays.asList(strArr);
				orderFormService.saveSmsBaseItemOrdered(itemLst, loginName, formSchema);
			}
			else
			{
				orderFormService.deleteBaseItemOrdered(loginName, formSchema);
			}
			b = true;
		}
		if(b)
		{
			return this.findRedirectPar("smOrderFormLoad.action?saveResult=success&formSchema="+formSchema);
		}
		else
		{
			return this.findRedirectPar("smOrderFormLoad.action?saveResult=failure&formSchema="+formSchema);
		}
	}
	
	/**
	 * 工单短信订阅启停
	 */
	public void changeSmsOrderStatus(){
		String status = getRequest().getParameter("status");
		String loginname = getUserSession().getLoginName();
		boolean b = orderFormService.changeOrderStatus(loginname, status);
		renderText(String.valueOf(b));
	}
	
	/**
	 * 获得工单短信订阅状态
	 */
	public void getSmsOrderStatus(){
		String loginname = getUserSession().getLoginName();
		renderText(orderFormService.getOrderStatus(loginname));
	}
	
	public void setOrderFormService(SmOrderFormService orderFormService) {
		this.orderFormService = orderFormService;
	}

	public void setDicManagerService(DicManagerService dicManagerService) {
		this.dicManagerService = dicManagerService;
	}

	public List<DicItem> getAllFormAction() {
		return allFormAction;
	}

	public void setAllFormAction(List<DicItem> allFormAction) {
		this.allFormAction = allFormAction;
	}

	public List<SlaRulePropertyShow> getSlaRuleProLst() {
		return slaRuleProLst;
	}

}
