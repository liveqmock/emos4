package com.ultrapower.eoms.common.core.component.sla.manager;

import java.util.ArrayList;
import java.util.List;
import com.ultrapower.eoms.common.RecordLog;
import com.ultrapower.eoms.common.core.component.sla.model.SlaMailAction;
import com.ultrapower.eoms.common.core.component.sla.model.SlaRule;
import com.ultrapower.eoms.common.core.component.sla.model.SlaRuleTpl;
import com.ultrapower.eoms.common.core.component.sla.model.SlaSmAction;
import com.ultrapower.eoms.common.core.component.sla.service.SlaMailActionService;
import com.ultrapower.eoms.common.core.dao.IDao;

/**
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-8-30 下午04:44:58
 */
public class SlaMailActionImpl implements SlaMailActionService{

	private IDao<SlaMailAction>  slaMailActionDao;
	private IDao<SlaRule> slaRuleDao;
	private IDao<SlaRuleTpl> slaRuleTplDao;
	
	public boolean addSlaMailAction(SlaMailAction slaMailAction) {
		boolean flag = false;
		try{
			slaMailActionDao.save(slaMailAction);
			flag = true;
		}catch(Exception e){
			RecordLog.printLog("添加邮件动作业务出错,"+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
		}
		return flag;
	}

	public boolean deleteSlaMailAction(String id) {//删除前先确认规则是否已经删除
		boolean flag = false;
		try{
			List<SlaRule> slaRuleList = this.getSlaRule(id);
			if(slaRuleList!=null && slaRuleList.size()>0){
				RecordLog.printLog("删除邮件动作业务失败,该邮件业务动作拥有的规则未先删除!", RecordLog.LOG_LEVEL_ERROR);
			}else{
				slaMailActionDao.removeById(id);
				flag = true;
			}
		}catch(Exception e){
			RecordLog.printLog("删除邮件动作业务出错,"+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
		}
		return flag;
	}

	public SlaMailAction get(String id) {
		SlaMailAction slaMailAction = slaMailActionDao.get(id);
		SlaRuleTpl slaRuleTpl = null;
		try{
			if(!slaMailAction.getRuletplid().equals("")){
				slaRuleTpl = slaRuleTplDao.get(slaMailAction.getRuletplid());
				slaMailAction.setRuletplname(slaRuleTpl.getRuletemplatename());
			}
		}catch(Exception e){
			RecordLog.printLog("查询规则模板出错,规则模板id="+slaMailAction.getRuletplid()+","+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
		}
		return slaMailAction;
	}

	public boolean updateSlaMailAction(SlaMailAction slaMailAction) {
		boolean flag = false;
		try{
			slaMailActionDao.saveOrUpdate(slaMailAction);
			flag = true;
		}catch(Exception e){
			RecordLog.printLog("更新邮件动作业务出错,"+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
		}
		return flag;
	}

	private List<SlaRule> getSlaRule(String id){
		List<SlaRule> slaRuleList = new ArrayList<SlaRule>();
		String hql = "from SlaRule where actionid = ?";
		Object[] values = {id};
		try{
			slaRuleList = slaRuleDao.find(hql, values);
		}catch(Exception e){
			RecordLog.printLog("获取邮件动作业务的规则出错,"+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
		}
		return slaRuleList;
	}
	
	public boolean uniqueMark(String actionmark) {
		boolean flag = true;
		String hql = "from SlaMailAction where actionmark = ?";
		Object[] values = {actionmark};
		List<SlaSmAction> slaSmActionList = new ArrayList<SlaSmAction>();
		try{
			slaSmActionList = slaMailActionDao.find(hql, values);
			if(slaSmActionList.size()>0)//说明不唯一
				flag = false;
		}catch(Exception e){
			RecordLog.printLog("获取邮件动作标识是否唯一出错,动作标识="+actionmark+","+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
		}
		return flag;
	}
	
	public void setSlaRuleDao(IDao<SlaRule> slaRuleDao) {
		this.slaRuleDao = slaRuleDao;
	}

	public void setSlaRuleTplDao(IDao<SlaRuleTpl> slaRuleTplDao) {
		this.slaRuleTplDao = slaRuleTplDao;
	}

	public void setSlaMailActionDao(IDao<SlaMailAction> slaMailActionDao) {
		this.slaMailActionDao = slaMailActionDao;
	}


}
