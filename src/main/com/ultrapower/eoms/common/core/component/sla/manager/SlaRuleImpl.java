package com.ultrapower.eoms.common.core.component.sla.manager;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.RecordLog;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.component.sla.model.SlaRule;
import com.ultrapower.eoms.common.core.component.sla.model.SlaRuleProperty;
import com.ultrapower.eoms.common.core.component.sla.service.SlaRuleService;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.ultrasm.model.DataInfo;

/**
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-8-31 上午10:08:45
 * @descibe 
 */
@Transactional
public class SlaRuleImpl implements SlaRuleService{

	private IDao<SlaRule> slaRuleDao;
	private IDao<SlaRuleProperty> slaRulePropertyDao;
	public boolean addSlaRule(SlaRule slaRule) {
		boolean flag = false;
		try{
			slaRuleDao.save(slaRule);
			flag = true;
		}catch(Exception e){
			RecordLog.printLog("添加SLA规则出错,"+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
		}
		return flag;
	}

	public boolean addSlaRule(SlaRule slaRule,
			List<SlaRuleProperty> slaRulePropertyList) {
		boolean flag = false;
		try{
			slaRuleDao.save(slaRule);
			if(slaRulePropertyList!=null){
				for(SlaRuleProperty slaRuleProperty : slaRulePropertyList){
					slaRuleProperty.setRuleid(slaRule.getPid());
					slaRulePropertyDao.save(slaRuleProperty);
				}
			}
			flag = true;
		}catch(Exception e){
			RecordLog.printLog("添加SLA规则出错,"+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
		}
		return flag;
	}

	public boolean deleteSlaRule(String id) {
		boolean flag = false;
		try{
			slaRuleDao.removeById(id);
			delSlaRuleProperty(id);
			flag = true;
		}catch(Exception e){
			RecordLog.printLog("删除SLA规则出错,"+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
		}
		return flag;
	}

	public SlaRule get(String id) {
		return slaRuleDao.get(id);
	}

	public List<SlaRuleProperty> getSlaRuleProperty(String id) {
		List<SlaRuleProperty> slaRulePropertyList = new ArrayList<SlaRuleProperty>();
		String hql = "from SlaRuleProperty where ruleid = ?";
		Object[] values = {id};
		try{
			slaRulePropertyList = slaRulePropertyDao.find(hql, values);
		}catch(Exception e){
			RecordLog.printLog("获取SLA规则属性出错,规则id="+id+","+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
		}
		return slaRulePropertyList;
	}

	public boolean updateSlaRule(SlaRule slaRule,
			List<SlaRuleProperty> slaRulePropertyList) {//先删除该规则拥有的属性,再添加
		boolean flag = false;
		try{
			//更新sla规则
			slaRuleDao.saveOrUpdate(slaRule);
			
			//更新属性,先删除再添加
			this.delSlaRuleProperty(slaRule.getPid());
			if(slaRulePropertyList!=null){
				for(SlaRuleProperty slaRuleProperty : slaRulePropertyList){
					slaRuleProperty.setRuleid(slaRule.getPid());
					slaRulePropertyDao.save(slaRuleProperty);
				}
			}
			flag = true;
		}catch(Exception e){
			RecordLog.printLog("更新SLA规则出错,id="+slaRule.getPid()+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
		}
		return flag;
	}
	
	private boolean delSlaRuleProperty(String slaRuleId){
		boolean flag = false;
		String hql = "delete from SlaRuleProperty  where ruleid = ?";
		Object[] values = {slaRuleId};
		try{
			slaRulePropertyDao.executeUpdate(hql, values);
			flag = true;
		}catch(Exception e){
			RecordLog.printLog("删除SLA规则属性出错,规则id="+slaRuleId+","+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
		}
		return flag;
	}
	
	public List<DataInfo> getInfoBySql(String sql)
	{
		List<DataInfo> dataList = new ArrayList();
		if("".equals(StringUtils.checkNullString(sql)))
		{
			return dataList;
		}
		try
		{
			QueryAdapter queryAdapter = new QueryAdapter();
			DataTable table = queryAdapter.executeQuery(sql, null, 0, 0, 2);
			if(table != null && table.length() > 0)
			{
				DataRow row = null;
				DataInfo data = null;
				for(int i=0;i<table.length();i++)
				{
					row = table.getDataRow(i);
					data = new DataInfo();
					data.setId(row.getString(0));
					data.setValue(row.getString(1));
					dataList.add(data);
				}
			}
		}
		catch  (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return dataList;
	}
	
	public boolean isIdentifierUnique(String identifier)
	{
		boolean b = false;
		if(!"".equals(StringUtils.checkNullString(identifier)))
		{
			String hql = "from SlaRule where ruleidentifier = ?";
			List lst = slaRuleDao.find(hql, new Object[]{identifier});
			if(lst==null || lst.size()==0)
			{
				b = true;
			}
		}
		return b;
	}
	
	public void setSlaRulePropertyDao(IDao<SlaRuleProperty> slaRulePropertyDao) {
		this.slaRulePropertyDao = slaRulePropertyDao;
	}

	public void setSlaRuleDao(IDao<SlaRule> slaRuleDao) {
		this.slaRuleDao = slaRuleDao;
	}
}
