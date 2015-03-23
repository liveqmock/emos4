package com.ultrapower.eoms.common.core.component.sla.manager;

import java.util.ArrayList;
import java.util.List;

import com.ultrapower.eoms.common.RecordLog;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.component.sla.model.SlaRule;
import com.ultrapower.eoms.common.core.component.sla.model.SlaRuleProperty;
import com.ultrapower.eoms.common.core.component.sla.model.SlaRulePropertyShow;
import com.ultrapower.eoms.common.core.component.sla.model.SlaRuleTpl;
import com.ultrapower.eoms.common.core.component.sla.model.SlaSmAction;
import com.ultrapower.eoms.common.core.component.sla.service.SlaSmActionService;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.StringUtils;

/**
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-8-30 下午04:44:46
 */
public class SlaSmActionImpl implements SlaSmActionService{

	private IDao<SlaSmAction> slaSmActionDao;
	private IDao<SlaRule> slaRuleDao;
	private IDao<SlaRuleTpl> slaRuleTplDao;
	
	public boolean addSlaSmAction(SlaSmAction slaSmAction) {
		boolean flag = false;
		try{
			slaSmActionDao.save(slaSmAction);
			flag = true;
		}catch(Exception e){
			RecordLog.printLog("添加短信动作业务出错,"+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
		}
		return flag;
	}

	public boolean deleteSlaSmAction(String id) {//删除的时候,需要先判断该短信业务下面的规则是否为空
		boolean flag = false;
		try{
			List<SlaRule> slaRuleList = this.getSlaRule(id);
			if(slaRuleList!=null && slaRuleList.size()>0){
				RecordLog.printLog("删除短信动作业务失败,该短信业务动作拥有的规则未先删除!", RecordLog.LOG_LEVEL_ERROR);
			}else{
				slaSmActionDao.removeById(id);
				flag = true;
			}
		}catch(Exception e){
			RecordLog.printLog("删除短信动作业务出错,"+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
		}
		return flag;
	}

	public SlaSmAction get(String id) {
		SlaSmAction slaSmAction = slaSmActionDao.get(id);
		String ruleId = StringUtils.checkNullString(slaSmAction.getRuletplid());
		SlaRuleTpl slaRuleTpl = slaRuleTplDao.get(ruleId);
		if(slaRuleTpl!=null)
		{
			slaSmAction.setRuletplname(slaRuleTpl.getRuletemplatename());
		}
		return slaSmAction;
	}

	public boolean updateSlaSmAction(SlaSmAction slaSmAction) {
		boolean flag = false;
		try{
			slaSmActionDao.saveOrUpdate(slaSmAction);
			flag = true;
		}catch(Exception e){
			RecordLog.printLog("更新短信动作业务出错,"+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
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
			RecordLog.printLog("获取短信动作业务的规则出错,"+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
		}
		return slaRuleList;
	}
	
	public List<SlaRulePropertyShow> getSlaRuleProShowList(String ruleId, String modelId)
	{
		List<SlaRulePropertyShow> slaRuleProLst = new ArrayList<SlaRulePropertyShow>();
		ruleId = StringUtils.checkNullString(ruleId);
		modelId = StringUtils.checkNullString(modelId);
		if(!"".equals(modelId))
		{
			StringBuffer sql = new StringBuffer();
			QueryAdapter qa = new QueryAdapter();
			DataTable dtab = null;
			if(!"".equals(ruleId))
			{
				sql.append("select rup.pid rpid, rup.fieldname, rup.inputtype, rup.inputdatasourcetype, rup.inputvaluetype, rup.indata, slarup.*");
				sql.append(" from bs_t_sm_ruletplproperty rup,");
				sql.append(" (select * from bs_t_sm_slaruleproperty where ruleid = ?) slarup");
				sql.append(" where slarup.propertyid(+) = rup.pid and rup.ruletplpid = ? and rup.status = 1");
				sql.append(" order by rup.ordernum");
				dtab = qa.executeQuery(sql.toString(), new Object[]{ruleId, modelId}, 0, 0, 2);
			}
			else
			{
				sql.append("select rup.pid rpid, rup.fieldname, rup.inputtype, rup.inputdatasourcetype, rup.inputvaluetype, rup.indata");
				sql.append(" from bs_t_sm_ruletplproperty rup");
				sql.append(" where rup.ruletplpid = ? order by rup.ordernum");
				dtab = qa.executeQuery(sql.toString(), new Object[]{modelId}, 0, 0, 2);
			}
			if(dtab!=null && dtab.length()>0)
			{
				for(int i=0;i<dtab.length();i++)
				{
					DataRow row = dtab.getDataRow(i);
					SlaRulePropertyShow srps = new SlaRulePropertyShow();
					SlaRuleProperty srp = new SlaRuleProperty();
					srps.setRpid(row.getString("rpid"));
					srps.setFieldname(row.getString("fieldname"));
					srps.setInputtype(row.getString("inputtype"));
					srps.setInputdatasourcetype(row.getString("inputdatasourcetype"));
					srps.setInputvaluetype(row.getString("inputvaluetype"));
					srps.setIndata(row.getString("indata"));
					if(!"".equals(ruleId) && !"".equals(StringUtils.checkNullString(row.getString("pid"))))
					{
						srps.setIsown(true);
						srp.setPid(row.getString("pid"));
						srp.setRuleid(row.getString("ruleid"));
						srp.setPropertyid(row.getString("propertyid"));
						srp.setValue(row.getString("value"));
						srp.setDisplayvalue(row.getString("displayvalue"));
						srp.setOperator(row.getString("operator"));
						srps.setSlaRuleProperty(srp);
					}
					slaRuleProLst.add(srps);
				}
			}
		}
		return slaRuleProLst;
	}

	public boolean uniqueMark(String actionmark) {
		boolean flag = true;
		String hql = "from SlaSmAction where actionmark = ?";
		Object[] values = {actionmark};
		List<SlaSmAction> slaSmActionList = new ArrayList<SlaSmAction>();
		try{
			slaSmActionList = slaSmActionDao.find(hql, values);
			if(slaSmActionList.size()>0)//说明不唯一
				flag = false;
		}catch(Exception e){
			RecordLog.printLog("获取短信动作标识是否唯一出错,动作标识="+actionmark+","+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
		}
		return flag;
	}
	
	public void setSlaRuleTplDao(IDao<SlaRuleTpl> slaRuleTplDao) {
		this.slaRuleTplDao = slaRuleTplDao;
	}

	public void setSlaSmActionDao(IDao<SlaSmAction> slaSmActionDao) {
		this.slaSmActionDao = slaSmActionDao;
	}

	public void setSlaRuleDao(IDao<SlaRule> slaRuleDao) {
		this.slaRuleDao = slaRuleDao;
	}
}
