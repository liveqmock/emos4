package com.ultrapower.eoms.common.core.component.sms.manager;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.RecordLog;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.component.sla.model.SlaRuleProperty;
import com.ultrapower.eoms.common.core.component.sla.model.SlaRulePropertyShow;
import com.ultrapower.eoms.common.core.component.sla.model.SlaRuleTpl;
import com.ultrapower.eoms.common.core.component.sms.model.SmsBaseItem;
import com.ultrapower.eoms.common.core.component.sms.model.SmsBaseItemCfg;
import com.ultrapower.eoms.common.core.component.sms.model.SmsOrderForm;
import com.ultrapower.eoms.common.core.component.sms.service.SmOrderFormService;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.StringUtils;

/**
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-8-30 上午10:15:39
 */
@Transactional
public class SmOrderFormImpl implements SmOrderFormService{

	private IDao<SmsOrderForm> smOrderFormDao;
	private IDao<SmsBaseItem> smsBaseItemDao;
	private IDao<SmsBaseItemCfg> smsBaseItemCfgDao;
	private IDao<SlaRuleTpl> slaRuleTplDao;
	private IDao<SlaRuleProperty> slaRulePropertyDao;
	public boolean addOrderInfo(SmsOrderForm smsOrderForm) {
		boolean flag = false;
		try{
			smOrderFormDao.save(smsOrderForm);
			flag = true;
		}catch(Exception e){
			RecordLog.printLog("工单管理短信订阅:添加订阅信息出错!"+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
		}
		return flag;
	}

	public void addOrderInfo(String loginname,String formschema,List<SmsOrderForm> smsOrderFormList) {
		boolean flag = this.deleteOrderInfo(loginname, formschema);
		if(flag){
			if(smsOrderFormList!=null){
				for(SmsOrderForm smsOrderForm : smsOrderFormList){
					smOrderFormDao.save(smsOrderForm);
				}
			}else{
				RecordLog.printLog("工单管理短信订阅:添加无效,没有任何需要添加的工单订阅信息!", RecordLog.LOG_LEVEL_ERROR);
			}
		}else{
			RecordLog.printLog("工单管理短信订阅:添加失败,添加工单订阅信息前的删除操作失败!", RecordLog.LOG_LEVEL_ERROR);
		}
	}

	public boolean deleteOrderInfo(String id) {
		boolean flag = false;
		try{
			smOrderFormDao.removeById(id);
			flag = true;
		}catch(Exception e){
			RecordLog.printLog("工单管理短信订阅:删除订阅信息出错!pid="+id+","+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
		}
		return flag;
	}

	public boolean deleteOrderInfo(String loginname, String formSchema) {
		boolean flag = false;
		String hql = "delete from SmsOrderForm  where loginname = ? and formschema = ?";
		Object[] values = {loginname,formSchema};
		try{
			smOrderFormDao.executeUpdate(hql, values);
			flag = true;
		}catch(Exception e){
			RecordLog.printLog("工单管理短信订阅:删除订阅信息出错!登录名="+loginname+",工单类别="+formSchema+","+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
		}
		return flag;
	}

	public List<SmsOrderForm> get(String loginname, String formSchema) {
		List<SmsOrderForm>  smsOrderFormList = new ArrayList<SmsOrderForm>();
		String hql = "from SmsOrderForm where loginname = ? and formschema = ?";
		Object[] values = {loginname,formSchema};
		smsOrderFormList = smOrderFormDao.find(hql, values);
		return smsOrderFormList;
	}
	
	public List<SlaRulePropertyShow> getSlaRuleProShowList(String ruleId, String modelMark)
	{
		List<SlaRulePropertyShow> slaRuleProLst = new ArrayList<SlaRulePropertyShow>();
		ruleId = StringUtils.checkNullString(ruleId);
		modelMark = StringUtils.checkNullString(modelMark);
		String modelId = "";
		List<SlaRuleTpl> temp = slaRuleTplDao.find("from SlaRuleTpl where tplmark = ?", new Object[]{modelMark});
		if(temp!=null && temp.size()>0)
		{
			modelId = StringUtils.checkNullString(temp.get(0).getPid());
		}
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
	
	public boolean saveRulePropertyInfo(List<SlaRuleProperty> srplst, String formSchema)
	{
		boolean b = false;
		if(srplst!=null && !"".equals(StringUtils.checkNullString(formSchema)))
		{
			try
			{
				deleteRulePropertyInfo(formSchema);
				for(SlaRuleProperty srp:srplst)
				{
					slaRulePropertyDao.save(srp);
				}
				b = true;
			}catch(Exception e)
			{
				RecordLog.printLog("工单管理短信订阅:保存订阅短信规则出错!"+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
			}
		}
		return b;
	}
	public boolean deleteRulePropertyInfo(String formSchema)
	{
		boolean b = false;
		try
		{
			slaRulePropertyDao.executeUpdate("delete SlaRuleProperty where ruleid = ?", new Object[]{formSchema});
			b = true;
		}catch(Exception e)
		{
			RecordLog.printLog("工单管理短信订阅:删除订阅短信规则出错!"+e.getMessage(), RecordLog.LOG_LEVEL_ERROR);
		}
		return b;
	}
	public void setSmOrderFormDao(IDao<SmsOrderForm> smOrderFormDao) {
		this.smOrderFormDao = smOrderFormDao;
	}

	public void setSlaRuleTplDao(IDao<SlaRuleTpl> slaRuleTplDao) {
		this.slaRuleTplDao = slaRuleTplDao;
	}

	public void setSlaRulePropertyDao(IDao<SlaRuleProperty> slaRulePropertyDao) {
		this.slaRulePropertyDao = slaRulePropertyDao;
	}

	public void setSmsBaseItemDao(IDao<SmsBaseItem> smsBaseItemDao) {
		this.smsBaseItemDao = smsBaseItemDao;
	}

	public void setSmsBaseItemCfgDao(IDao<SmsBaseItemCfg> smsBaseItemCfgDao) {
		this.smsBaseItemCfgDao = smsBaseItemCfgDao;
	}

	public List<String> getSmsBaseItemOfSchema(String loginName,String BaseSchema, int pageNum,
			int pageSize) {
		if(loginName==null||BaseSchema==null)
			return null;
		if(pageNum==0)
			pageNum = 1;
		if(pageSize==0)
			pageSize = 50;
		List<SmsBaseItemCfg> cfglst = this.smsBaseItemCfgDao.find("from SmsBaseItemCfg where lower(baseschema) = ? and status=1", BaseSchema.toLowerCase());
		if(cfglst==null||cfglst.size()<=0)
			return null;
		SmsBaseItemCfg cfg = cfglst.get(0);
		String itemsource = cfg.getItemsource();
		if(itemsource==null)
			return null;
		//此处的SQL形如，其中#中间的为变量：select /*COUNT*/ #fieldName# as value /*COUNT*/ from #tableName# where ...
		QueryAdapter qa = new QueryAdapter();
		StringBuffer sql = new StringBuffer();
		sql.append("select /*COUNT*/ value /*COUNT*/ from (");
		sql.append(" ");
		sql.append(itemsource);
		sql.append(" minus select baseitem as value from bs_t_sm_smbaseitem where loginname=? and baseschema=?) order by value");
		DataTable dt = qa.executeQuery(sql.toString(), new Object[]{loginName,BaseSchema}, pageNum, pageSize, -1);
		int len = 0;
		if(dt!=null)
			len = dt.length();
		List<String> result = new ArrayList<String>();
		for(int i=0;i<len;i++){
			result.add(dt.getDataRow(i).getString("value"));
		}
		return result;
	}

	public List<String> getSmsBaseItemOrdered(String loginname, String schema,int pageNum,int pageSize) {
		if(loginname==null||schema==null)
			return null;
		List<String> result  = new ArrayList<String>();
		String sql = "select /*COUNT*/ baseitem /*COUNT*/ from BS_T_SM_SMBASEITEM where loginname=? and lower(baseschema)=? order by baseitem";
		QueryAdapter qa = new QueryAdapter();
		DataTable dt = qa.executeQuery(sql, new Object[]{loginname,schema.toLowerCase()}, pageNum, pageSize, -1);
		int len = 0;
		if(dt!=null)
			len = dt.length();
		for(int i=0;i<len;i++){
			result.add(dt.getDataRow(i).getString("baseitem"));
		}
		return result;
	}

	public void saveSmsBaseItemOrdered(List<String> items, String loginname,
			String schema) {
		if(items==null||loginname==null||schema==null)
			return;
		String sql = "delete from SmsBaseItem where loginname=? and baseschema=?";
		smsBaseItemDao.executeUpdate(sql, new Object[]{loginname,schema});
		int len = items.size();
		Session session = smsBaseItemDao.getHibernateSession();
		for(int i=0;i<len;i++){
			smsBaseItemDao.save(new SmsBaseItem(null,loginname,schema,items.get(i)));
			if(i%10==0){
				session.flush();
				session.clear();
			}
		}
	}

	public boolean deleteBaseItemOrdered(String loginname, String schema) {
		if(loginname==null||schema==null)
			return false;
		String hql = "delete from SmsBaseItem where loginname=? and baseschema=?";
		try{
			smsBaseItemDao.executeUpdate(hql, new Object[]{loginname,schema});
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean changeOrderStatus(String loginname, String status) {
		if(loginname==null||(!"1".equals(status)&&!"0".equals(status)))
			return false;
		String hql = "update SmsOrderForm set status=? where loginname=?";
		try {
			smOrderFormDao.executeUpdate(hql, Long.valueOf(status),loginname);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public String getOrderStatus(String loginname) {
		if(loginname==null)
			return null;
		QueryAdapter qa = new QueryAdapter();
		String sql = "select orderf.pid from BS_T_SM_SMSORDERFORM orderf where orderf.loginname=?";
		DataTable dt = qa.executeQuery(sql,loginname);
		if(dt==null||dt.length()<=0)
			return "2";
		sql = "select orderf.pid from BS_T_SM_SMSORDERFORM orderf where orderf.loginname=? and orderf.status=1";
		dt = qa.executeQuery(sql, loginname);
		if(dt!=null&&dt.length()>0){
			return "1";
		}else{
			return "0";
		}
	}
	
	
}
