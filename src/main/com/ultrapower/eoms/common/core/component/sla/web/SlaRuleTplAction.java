package com.ultrapower.eoms.common.core.component.sla.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ultrapower.eoms.common.core.component.sla.model.SlaRuleTpl;
import com.ultrapower.eoms.common.core.component.sla.model.SlaTplProperty;
import com.ultrapower.eoms.common.core.component.sla.service.SlaRuleTplService;
import com.ultrapower.eoms.common.core.component.sla.util.GetMemuFileOper;
import com.ultrapower.eoms.common.core.component.sla.util.JTableParseRuleTplPropertyUtil;
import com.ultrapower.eoms.common.core.util.Internation;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;


/**
 * SLA规则模板管理
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-9-1 上午11:26:01
 */
public class SlaRuleTplAction extends BaseAction{

	private SlaRuleTplService slaRuleTplService;
	public SlaRuleTpl slaRuleTpl;
	public List<SlaTplProperty> slaTplPropertyList;
	public SlaTplProperty slaTplProperty;
	public String slaTplPropertypid;
	public String slaruletplpid;
	private String xmlData;
	
	public String slaRuleTplList(){
		return SUCCESS;
	}
	
	public String slaRuleTplSave(){
		if(slaruletplpid!=null){
			slaRuleTpl = slaRuleTplService.get(slaruletplpid);
			slaTplPropertyList = slaRuleTplService.getSlaTplProperty(slaruletplpid);
		}
		return SUCCESS;
	}

	public String addRuleTpl(){
		String parafresh = "";
		String returnMessage = "";
		HashMap map = new HashMap();
		if(xmlData!=null && !xmlData.equals(""))
		{
			map = JTableParseRuleTplPropertyUtil.getSlaRuleTplPropertyPropertyData(xmlData);
		}
		List<SlaTplProperty> addSlaTplPropertyList = new ArrayList();//需要增加的数据
	    List<SlaTplProperty> modSlaTplPropertyList = new ArrayList();//需要修改的数据
	    List<String> delSlaTplPropertyPidList = new ArrayList();//需要删除的数据的主键
		if(map!=null)
		{
			addSlaTplPropertyList = (List<SlaTplProperty>) map.get("add");
			modSlaTplPropertyList = (List<SlaTplProperty>) map.get("mod");
			delSlaTplPropertyPidList = (List) map.get("del");
		}
		if(slaRuleTpl!=null){
			UserSession userSession = this.getUserSession();
			if(slaRuleTpl.getPid()!=null){//修改
				slaRuleTpl.setLastmodifier(userSession.getPid());
				slaRuleTpl.setLastmodifytime(TimeUtils.getCurrentTime());
				boolean flag = slaRuleTplService.updateRuleTpl(slaRuleTpl, addSlaTplPropertyList, modSlaTplPropertyList, delSlaTplPropertyPidList);
				if(flag){
					returnMessage = Internation.language("com_msg_saveSuccess")+"!";
					parafresh = "true";
				}else{
					returnMessage = Internation.language("com_msg_saveErr")+"!";
					parafresh = "false";
				}
			}else{//添加
				slaRuleTpl.setCreater(userSession.getPid());
				slaRuleTpl.setCreatetime(TimeUtils.getCurrentTime());
				slaRuleTpl.setLastmodifier(userSession.getPid());
				slaRuleTpl.setLastmodifytime(TimeUtils.getCurrentTime());
				boolean flag = slaRuleTplService.addSlaRuleTpl(slaRuleTpl, addSlaTplPropertyList);
				if(flag){
					returnMessage = Internation.language("com_msg_saveSuccess")+"!";
					parafresh = "true";
				}else{
					returnMessage = Internation.language("com_msg_saveErr")+"!";
					parafresh = "false";
				}
			}
		}else{
			returnMessage = Internation.language("com_msg_saveErr")+"!";
			parafresh = "false";
		}
		this.getRequest().setAttribute("parafresh", parafresh);
		this.getRequest().setAttribute("returnMessage", returnMessage);
		return "refresh";
	}
	
	public String slaRuleTplProperty(){
		if(slaTplPropertypid!=null)
			slaTplProperty = slaRuleTplService.getSlaTplPropertyInfo(slaTplPropertypid);
		return this.findForward("slaRuleTplPropertySave");
	}
	
	public String delSlaRuleTpl(){
		String pids = StringUtils.checkNullString(this.getRequest().getParameter("pids"));
		if(pids!=null && !pids.equals("")){
			String[] pidsArr = pids.split(",");
			for(int i=0;i<pidsArr.length;i++){
				slaRuleTplService.deleteSlaRuleTpl(pidsArr[i]);
			}
		}
		return this.findForward("slaRuleTplList");
	}
	
	public void getDataSourceTree()
	{
		GetMemuFileOper getMemuFileOper = new GetMemuFileOper();
		this.renderXML(getMemuFileOper.getFileNameTree());
	}
	
	public void isTplMarkUnique()
	{
		boolean b = false;
		String mark = StringUtils.checkNullString(getRequest().getParameter("mark"));
		if(!"".equals(mark))
		{
			b = slaRuleTplService.checkTplMarkUnique(mark);
		}
		try {
			this.getResponse().getWriter().print(String.valueOf(b));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<SlaTplProperty> getSlaTplPropertyList() {
		return slaTplPropertyList;
	}

	public void setSlaTplPropertyList(List<SlaTplProperty> slaTplPropertyList) {
		this.slaTplPropertyList = slaTplPropertyList;
	}

	public SlaRuleTpl getSlaRuleTpl() {
		return slaRuleTpl;
	}

	public void setSlaRuleTpl(SlaRuleTpl slaRuleTpl) {
		this.slaRuleTpl = slaRuleTpl;
	}

	public String getSlaruletplpid() {
		return slaruletplpid;
	}

	public void setSlaruletplpid(String slaruletplpid) {
		this.slaruletplpid = slaruletplpid;
	}

	public SlaRuleTplService getSlaRuleTplService() {
		return slaRuleTplService;
	}

	public void setSlaRuleTplService(SlaRuleTplService slaRuleTplService) {
		this.slaRuleTplService = slaRuleTplService;
	}

	public SlaTplProperty getSlaTplProperty() {
		return slaTplProperty;
	}

	public void setSlaTplProperty(SlaTplProperty slaTplProperty) {
		this.slaTplProperty = slaTplProperty;
	}

	public String getXmlData() {
		return xmlData;
	}

	public void setXmlData(String xmlData) {
		this.xmlData = xmlData;
	}
}
