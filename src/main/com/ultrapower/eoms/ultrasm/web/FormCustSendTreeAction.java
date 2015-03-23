package com.ultrapower.eoms.ultrasm.web;

import java.util.ArrayList;
import java.util.List;

import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.ActionContext;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ultrasm.model.CustomOrganize;
import com.ultrapower.eoms.ultrasm.model.FormCustSendTree;
import com.ultrapower.eoms.ultrasm.model.FormCustSenderDelPara;
import com.ultrapower.eoms.ultrasm.model.FormSenderTreeView;
import com.ultrapower.eoms.ultrasm.service.FormCustSendTreeService;
import com.ultrapower.eoms.ultrasm.util.UltraSmUtil;


/**
 * 工单派发树
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-9-26 上午09:37:05
 */
public class FormCustSendTreeAction extends BaseAction {

	private FormCustSendTreeService formCustSendTreeService;
	private FormCustSendTree formCustSendTree;
	private List<FormSenderTreeView> senderTreeView;
	public String formSendTreeList(){
		return SUCCESS;
	}
	
	public String formSendTreeFrame(){
		return SUCCESS;
	}
	
	public String del(){
		String pids = StringUtils.checkNullString(this.getRequest().getParameter("pids"));
		if(!pids.equals("")){
			String[] pidsArr = pids.split(",");
			formCustSendTreeService.delCustomTree(UltraSmUtil.arrayToList(pidsArr));
		}
		return findForward("formSendTreeList");
	}
	
	public String transStatus(){
		String type = StringUtils.checkNullString(this.getRequest().getParameter("type"));
		String pids = StringUtils.checkNullString(this.getRequest().getParameter("pids"));
		if(!type.equals("")){
			if(!pids.equals("")){
				if(type.equals("inuse"))//启用
					formCustSendTreeService.transStatus(UltraSmUtil.arrayToList(pids.split(",")), 1);
				if(type.equals("outuse"))//停用
					formCustSendTreeService.transStatus(UltraSmUtil.arrayToList(pids.split(",")), 0);
			}
		}
		return findForward("formSendTreeList");
	}
	
	/**
	 * 根据工单类别和当前登录人加载对应派发树
	 * @return
	 */
	public String loadFromCustSendTree()
	{
		String formSchema = StringUtils.checkNullString(this.getRequest().getParameter("formSchema"));
		UserSession session = ActionContext.getUserSession();
		if(!"".equals(formSchema))
		{
			formCustSendTree = formCustSendTreeService.getFormCustSendTree(formSchema,StringUtils.checkNullString(session.getLoginName()));
			if(formCustSendTree!=null)
			{
				senderTreeView = formCustSendTreeService.getFormSenderTreeView(formCustSendTree.getPid());
				if(senderTreeView==null || senderTreeView.size()<=0)
				{
					formCustSendTreeService.delCustomTree(formCustSendTree);
					formCustSendTree = null;
				}
			}
		}
		this.getRequest().setAttribute("formSchema", formSchema);
		return findForward("formSendTree_right");
	}

	/**
	 * 保存工单派发树
	 * @return
	 */
	public String saveFromCustSendTree()
	{
		UserSession session = ActionContext.getUserSession();
		if(formCustSendTree!=null)
		{
			String customOrganizeInfo = StringUtils.checkNullString(getRequest().getParameter("customOrganizeInfo"));
			List<CustomOrganize> customOrganizeList = null;
			if(!"".equals(customOrganizeInfo))
			{
				String[] temp = customOrganizeInfo.split(";");
				customOrganizeList = new ArrayList<CustomOrganize>();
				for(String str:temp)
				{
					String type = str.split(":")[0];
					String orgid = str.split(":")[1];
					CustomOrganize co = new CustomOrganize();
					co.setOrganizepid(orgid);
					if(!"".equals(StringUtils.checkNullString(formCustSendTree.getPid())))
					{
						co.setCustominfopid(formCustSendTree.getPid());
					}
					if(type.equals("U") || type.equals("2")) //人员
					{
						co.setOrganizetype(2L);
					}
					else if(type.equals("D") || type.equals("1")) //部门
					{
						co.setOrganizetype(1L);
					}
					customOrganizeList.add(co);
				}
			}
			if("".equals(StringUtils.checkNullString(formCustSendTree.getPid())))
			{//新建
				long currentTime = TimeUtils.getCurrentTime();
				formCustSendTree.setCreater(session.getPid());
				formCustSendTree.setCreatetime(currentTime);
				formCustSendTree.setLastmodifier(session.getPid());
				formCustSendTree.setLastmodifytime(currentTime);
				formCustSendTree.setLoginname(session.getLoginName());
				formCustSendTreeService.addCustomTree(formCustSendTree, customOrganizeList);
			}
			else
			{//修改
				formCustSendTree.setLastmodifier(session.getPid());
				formCustSendTree.setLastmodifytime(TimeUtils.getCurrentTime());
				formCustSendTree.setLoginname(session.getLoginName());
				//formCustSendTreeService.updateCustomTree(formCustSendTree, customOrganizeList);
				formCustSendTreeService.updateCustOrgTree(formCustSendTree.getPid(), customOrganizeList);
			}
		}
		String baseSchema = formCustSendTree==null?"":formCustSendTree.getBaseschema();
		return this.findRedirectPar("loadFromCustSendTree.action?formSchema="+baseSchema);
	}
	
	/**
	 * 删除工单配发树
	 * @return
	 */
	public String delFromCustSendTree()
	{
		String custSenderPid = StringUtils.checkNullString(getRequest().getParameter("custSenderPid"));
		String depids = StringUtils.checkNullString(getRequest().getParameter("depids")).trim();
		String userids = StringUtils.checkNullString(getRequest().getParameter("userids")).trim();
		if(!"".equals(custSenderPid))
		{
			List<FormCustSenderDelPara> delList = new ArrayList<FormCustSenderDelPara>();
			if(!"".equals(depids))
			{
				String[] depidArr = depids.split(",");
				FormCustSenderDelPara temp;
				for(String str:depidArr)
				{
					temp = new FormCustSenderDelPara();
					temp.setCustSenderPid(custSenderPid);
					temp.setOrginzedpid(str);
					temp.setOrgtype("1");
					delList.add(temp);
				}
			}
			if(!"".equals(userids))
			{
				String[] useridArr = userids.split(",");
				FormCustSenderDelPara temp;
				for(String str:useridArr)
				{
					temp = new FormCustSenderDelPara();
					temp.setCustSenderPid(custSenderPid);
					temp.setOrginzedpid(str);
					temp.setOrgtype("2");
					delList.add(temp);
				}
			}
			formCustSendTreeService.delCustOrganized(delList);
		}
		String formSchema = StringUtils.checkNullString(getRequest().getParameter("formSchema"));
		return this.findRedirectPar("loadFromCustSendTree.action?formSchema="+formSchema);
	}
	
	public void setFormCustSendTreeService(
			FormCustSendTreeService formCustSendTreeService) {
		this.formCustSendTreeService = formCustSendTreeService;
	}

	public FormCustSendTree getFormCustSendTree() {
		return formCustSendTree;
	}

	public void setFormCustSendTree(FormCustSendTree formCustSendTree) {
		this.formCustSendTree = formCustSendTree;
	}

	public List<FormSenderTreeView> getSenderTreeView() {
		return senderTreeView;
	}
	
}
