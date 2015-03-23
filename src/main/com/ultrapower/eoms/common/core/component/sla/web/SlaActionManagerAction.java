package com.ultrapower.eoms.common.core.component.sla.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.ultrapower.eoms.common.core.component.sla.model.SlaRule;
import com.ultrapower.eoms.common.core.component.sla.model.SlaRuleProperty;
import com.ultrapower.eoms.common.core.component.sla.model.SlaRulePropertyShow;
import com.ultrapower.eoms.common.core.component.sla.model.SlaSmAction;
import com.ultrapower.eoms.common.core.component.sla.model.SlaTplProperty;
import com.ultrapower.eoms.common.core.component.sla.service.SlaRuleService;
import com.ultrapower.eoms.common.core.component.sla.service.SlaRuleTplService;
import com.ultrapower.eoms.common.core.component.sla.service.SlaSmActionService;
import com.ultrapower.eoms.common.core.component.tree.manager.SlaActionTreeImpl;
import com.ultrapower.eoms.common.core.component.tree.manager.RuleModelTreeImpl;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ultrasm.model.DataInfo;
import com.ultrapower.eoms.ultrasm.model.DicItem;
import com.ultrapower.eoms.ultrasm.service.DicManagerService;

/**
 * SLA动作管理
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-9-2 上午11:03:32
 */
public class SlaActionManagerAction extends BaseAction
{

	private SlaSmActionService slaSmActionService;
	private SlaRuleService slaRuleService;
	private SlaRuleTplService slaRuleTplService;
	private DicManagerService dicManagerService;
	private SlaSmAction slaSmAction;
	private SlaRule slaRule;
	private List<SlaRulePropertyShow> slaRuleProLst = new ArrayList<SlaRulePropertyShow>();
	private List<DataInfo> dataList;
	
	public String slaActionManageFrame()
	{
		return SUCCESS;
	}
	
	public String slaActionOperationList(){
		return SUCCESS;
	}
	/**
	 * 动作树
	 */
	public void getActionTree()
	{
		if(!"".equals(StringUtils.checkNullString(id)))
		{
			SlaActionTreeImpl sat = new SlaActionTreeImpl();
			if("0".equals(id))
			{//调用SLA动作类型树，目前只有“短信和邮件”两个动作类型
				this.renderXML(sat.getActionTypeTreeXmlStr(id));
			}
			else
			{//调用SLA业务和其规则构成的树
				this.renderXML(sat.getTransTreeXmlStr(id));
			}
		}
	}
	
	/**
	 * 规则模板树
	 */
	public void getRuleModelTree()
	{
		RuleModelTreeImpl ruletree = new RuleModelTreeImpl();
		this.renderXML(ruletree.getTreeXml());
	}
	
	/**
	 * 加载业务
	 * @return
	 */
	public String smTransLoad()
	{
		String curid = StringUtils.checkNullString(this.getRequest().getParameter("id"));
		if(!"".equals(curid))
		{
			SlaSmAction curAction = slaSmActionService.get(curid);
			this.slaSmAction = curAction;
		}
		if(!"".equals(StringUtils.checkNullString(getRequest().getParameter("saveResult"))) && !"".equals(StringUtils.checkNullString(getRequest().getParameter("refreshId")))
			&& !"".equals(StringUtils.checkNullString(getRequest().getParameter("serviceType"))))
		{
			getRequest().setAttribute("saveResult", getRequest().getParameter("saveResult"));
			getRequest().setAttribute("refreshId", getRequest().getParameter("refreshId"));
			getRequest().setAttribute("nodeId", getRequest().getParameter("refreshId"));
			getRequest().setAttribute("serviceType", getRequest().getParameter("serviceType"));
		}
		return findForward("smTransSave");
	}
	
	/**
	 * 保存业务
	 * @return
	 */
	public String smTransSave()
	{
		boolean b = false;
		String saveResult = "failure";
		String refreshId = "";
		String noticeroleid = StringUtils.checkNullString(this.getRequest().getParameter("noticeroleid"));
		String noticerolename = StringUtils.checkNullString(this.getRequest().getParameter("noticerolename"));
		slaSmAction.setNoticeroleid(noticeroleid);
		slaSmAction.setNoticerolename(noticerolename);
		if(slaSmAction!=null)
		{
			refreshId = slaSmAction.getActiontype();
			UserSession session = this.getUserSession();
			String userId = session.getPid();
			long currentTime = TimeUtils.getCurrentTime();
			if("".equals(StringUtils.checkNullString(slaSmAction.getPid())))
			{
				slaSmAction.setCreater(userId);
				slaSmAction.setCreatetime(currentTime);
				slaSmAction.setLastmodifier(userId);
				slaSmAction.setLastmodifytime(currentTime);
				b = slaSmActionService.addSlaSmAction(slaSmAction);
			}
			else
			{
				slaSmAction.setLastmodifier(userId);
				slaSmAction.setLastmodifytime(currentTime);
				b = slaSmActionService.updateSlaSmAction(slaSmAction);
			}
		}
		if(b)
		{
			saveResult = "success";
		}
		return findRedirectPar("smTransLoad.action?saveResult="+saveResult+"&refreshId="+refreshId+"&serviceType=save");
	}
	
	/**
	 * 删除业务
	 * @return
	 */
	public String delSmTrans()
	{
		String id = StringUtils.checkNullString(this.getRequest().getParameter("id"));
		boolean b = false;
		String saveResult = "failure";
		String refreshId = "";
		if(!"".equals(id))
		{
			SlaSmAction curAction = slaSmActionService.get(id);
			refreshId = curAction.getActiontype();
			b = slaSmActionService.deleteSlaSmAction(id);
		}
		if(b)
		{
			saveResult = "success";
		}
		return findRedirectPar("smTransLoad.action?saveResult="+saveResult+"&refreshId="+refreshId+"&serviceType=delete");
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
			b = slaSmActionService.uniqueMark(actionmark);
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
	
	/**
	 * 加载规则
	 * @return
	 */
	public String loadRule()
	{
		String modelId = StringUtils.checkNullString(this.getRequest().getParameter("modelId")); 
		String ruleId = StringUtils.checkNullString(this.getRequest().getParameter("ruleId")); 
		if(!"".equals(ruleId))
		{
			slaRule = slaRuleService.get(ruleId);
		}
		this.getRequest().setAttribute("modelIdPar", modelId);
		slaRuleProLst = slaSmActionService.getSlaRuleProShowList(ruleId, modelId);
		return findForward("ruleSave");
	}
	
	/**
	 * 保存规则
	 * @return
	 */
	public String saveRule()
	{
		boolean b = false;
		String refreshId = "0";
		String saveResult = "failure";
		String modelId = StringUtils.checkNullString(this.getRequest().getParameter("modelIdPar"));
		String actionType = StringUtils.checkNullString(this.getRequest().getParameter("actionType"));
		if(slaRule!=null)
		{
			refreshId = slaRule.getActionid();
			UserSession session = this.getUserSession();
			String userId = session.getPid();
			long currentTime = TimeUtils.getCurrentTime();
			String slaRuleProStr = StringUtils.checkNullString(this.getRequest().getParameter("slaRuleProStr"));
			List<SlaRuleProperty> srplst = null;
			if(!"".equals(slaRuleProStr))
			{
				srplst = new ArrayList<SlaRuleProperty>();
				String[] outArr = slaRuleProStr.split("&semi;");
				for(int i=0;i<outArr.length;i++)
				{
					String[] valueArr = outArr[i].split("&comm;");
					SlaRuleProperty srp = new SlaRuleProperty();
					srp.setPropertyid(valueArr[0]);
					srp.setValue(valueArr[1]);
					srp.setDisplayvalue(valueArr[2]);
					srp.setOperator(valueArr[3]);
					srplst.add(srp);
				}
			}
			if("".equals(StringUtils.checkNullString(slaRule.getPid())))
			{//新建
				slaRule.setCreater(userId);
				slaRule.setCreatetime(currentTime);
				slaRule.setLastmodifier(userId);
				slaRule.setLastmodifytime(currentTime);
				b = slaRuleService.addSlaRule(slaRule, srplst);
			}
			else
			{
				slaRule.setLastmodifier(userId);
				slaRule.setLastmodifytime(currentTime);
				b = slaRuleService.updateSlaRule(slaRule, srplst);
			}
			if(b)
			{
				saveResult = "success";
			}
		}
		String redirectPar = "loadRule.action?transId="+refreshId+"&modelId="+modelId+"&serviceType=save&refreshId="+refreshId+"&saveResult="+saveResult+"&actionType="+actionType;
		return this.findRedirectPar(redirectPar);
	}
	
	/**
	 * 删除规则
	 * @return
	 */
	public String deleteRule()
	{
		String ruleId = StringUtils.checkNullString(this.getRequest().getParameter("ruleId"));
		String modelId = StringUtils.checkNullString(this.getRequest().getParameter("modelId"));
		String actionType = StringUtils.checkNullString(this.getRequest().getParameter("actionType"));
		String refreshId = "0";
		String saveResult = "failure";
		if(!"".equals(ruleId))
		{
			SlaRule temp = slaRuleService.get(ruleId);
			refreshId = temp.getActionid();
			if(slaRuleService.deleteSlaRule(ruleId))
			{
				saveResult = "success";
			}
		}
		String redirectPar = "loadRule.action?transId="+refreshId+"&modelId="+modelId+"&serviceType=delete&refreshId="+refreshId+"&saveResult="+saveResult+"&actionType="+actionType;
		return this.findRedirectPar(redirectPar);
	}
	
	/**
	 * 获得数据
	 * @param slaSmActionService
	 */
	public String getValue()
	{
		String type = StringUtils.checkNullString(this.getRequest().getParameter("type"));
		String rpid = StringUtils.checkNullString(this.getRequest().getParameter("rpid"));
		dataList = new ArrayList<DataInfo>();
		if("1".equals(type))//type=1时,为系统变量配置
		{
			List<DicItem> diList = dicManagerService.getDicItemByDicType("SystemVar");
			if(diList != null && diList.size() > 0)
			{
				DicItem di = null;
				DataInfo data = null;
				for(int i=0;i<diList.size();i++)
				{
					di = diList.get(i);
					data = new DataInfo();
					data.setId(di.getDivalue());
					data.setValue(di.getDiname());
					dataList.add(data);
				}
			}
		}
		else if("2".equals(type) && !"".equals(rpid))//type=2时,为数据字典取值
		{
			SlaTplProperty stp = slaRuleTplService.getSlaTplPropertyInfo(rpid);
			List<DicItem> diList = dicManagerService.getDicItemByDicType(stp.getIndata());
			if(diList != null && diList.size() > 0)
			{
				DicItem di = null;
				DataInfo data = null;
				for(int i=0;i<diList.size();i++)
				{
					di = diList.get(i);
					data = new DataInfo();
					data.setId(di.getDivalue());
					data.setValue(di.getDiname());
					dataList.add(data);
				}
			}
		}
		else if("3".equals(type) && !"".equals(rpid))//type=3时,为SQL配置取值
		{
			SlaTplProperty stp = slaRuleTplService.getSlaTplPropertyInfo(rpid);
			dataList = slaRuleService.getInfoBySql(stp.getIndata());
		}
		else if("6".equals(type) && !"".equals(rpid))//手工配置
		{
			SlaTplProperty stp = slaRuleTplService.getSlaTplPropertyInfo(rpid);
			String value = StringUtils.checkNullString(stp.getIndata());
			if(!"".equals(value))
			{
				value = value.replaceAll("；", ";");//将全角的分号替换成半角的分号
				value = value.replaceAll("：", ":");//将全角的冒号替换成半角的冒号
				String[] values = value.split(";");
				DataInfo data = null;
				for(int k=0;k<values.length;k++)
				{
					if(!"".equals(values[k]))
					{
						String[] dics = values[k].split(":");
						if(dics.length > 1)
						{
							data = new DataInfo();
							data.setId(dics[0]);
							data.setValue(dics[1]);
							dataList.add(data);
						}
					}
				}
			}
		}
		this.getRequest().setAttribute("input_name", this.getRequest().getParameter("input_name"));
		this.getRequest().setAttribute("input_id", this.getRequest().getParameter("input_id"));
		return this.findForward("dataSelect");
	}
	
	/**
	 * 验证规则标识符唯一性
	 */
	public void isRuleIdferUnique()
	{
		String identifier = StringUtils.checkNullString(this.getRequest().getParameter("identifier"));
		boolean b = slaRuleService.isIdentifierUnique(identifier);
		try 
		{
			this.getResponse().getWriter().print(String.valueOf(b));
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void setSlaSmActionService(SlaSmActionService slaSmActionService) 
	{
		this.slaSmActionService = slaSmActionService;
	}

	public void setSlaRuleService(SlaRuleService slaRuleService) 
	{
		this.slaRuleService = slaRuleService;
	}
	
	public void setDicManagerService(DicManagerService dicManagerService) 
	{
		this.dicManagerService = dicManagerService;
	}
	
	public void setSlaRuleTplService(SlaRuleTplService slaRuleTplService) 
	{
		this.slaRuleTplService = slaRuleTplService;
	}
	public SlaSmAction getSlaSmAction() 
	{
		return slaSmAction;
	}

	public void setSlaSmAction(SlaSmAction slaSmAction) 
	{
		this.slaSmAction = slaSmAction;
	}

	public SlaSmActionService getSlaSmActionService() 
	{
		return slaSmActionService;
	}
	public SlaRule getSlaRule() 
	{
		return slaRule;
	}
	public void setSlaRule(SlaRule slaRule) 
	{
		this.slaRule = slaRule;
	}
	public List<SlaRulePropertyShow> getSlaRuleProLst() 
	{
		return slaRuleProLst;
	}
	public void setSlaRuleProLst(List<SlaRulePropertyShow> slaRuleProLst) 
	{
		this.slaRuleProLst = slaRuleProLst;
	}
	public List<DataInfo> getDataList() 
	{
		return dataList;
	}
	public void setDataList(List<DataInfo> dataList) 
	{
		this.dataList = dataList;
	}
	
}
