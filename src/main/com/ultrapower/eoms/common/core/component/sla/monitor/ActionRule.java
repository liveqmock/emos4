package com.ultrapower.eoms.common.core.component.sla.monitor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.h2.util.DateTimeUtils;

import com.ultrapower.eoms.common.RecordLog;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.component.email.EmailBean;
import com.ultrapower.eoms.common.core.component.rquery.RQuery;
import com.ultrapower.eoms.common.core.component.rquery.util.RConstants;
import com.ultrapower.eoms.common.core.component.rquery.util.SqlReplace;
import com.ultrapower.eoms.common.core.component.sla.model.SqlConditionExp;
import com.ultrapower.eoms.common.core.component.sla.service.SlaMatchDataSourceService;
import com.ultrapower.eoms.common.core.component.sla.util.DataExpDeal;
import com.ultrapower.eoms.common.core.component.sla.util.NoticeParaDeal;
import com.ultrapower.eoms.common.core.component.sms.model.Smsmonitor;
import com.ultrapower.eoms.common.core.component.xml.XmlParser;
import com.ultrapower.eoms.common.core.util.ArrayTransferUtils;
import com.ultrapower.eoms.common.core.util.NumberUtils;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;

/**
 * 动作规则实现
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-9-10 下午03:01:14
 */
public abstract class ActionRule implements IActionRule{
	
	public static HashMap sqlquery=new HashMap();
	public static QueryAdapter queryAdapter = new QueryAdapter();
	private IActionHandle smsHandle = new SmActionHandleImpl();
	private IActionHandle emailHandle = new EmailActionHandleImpl();
	private Smsmonitor sms;
	private EmailBean emailBean;
	/**
	 * 获取查询对象
	 * @param actionid SLA动作ID
	 * @param tplid SLA模板ID
	 * @param type 1 存缓存 0 不存缓存  由于SLA有些是需要当前时间动态变量所以不适合缓存
	 * @return
	 */
	public RQuery getRQuery(String actionid,String tplid,int type)
	{
		type=0;
		if(actionid==null ||"".equals(actionid)||tplid==null ||"".equals(tplid))
		{
			return null;
		}
		if(type==1)
		{
			Object obj=sqlquery.get(actionid);
			if(obj==null)
			{
				RQuery rQuery=new RQuery(this.findSqlName(tplid),this.findSlaRules(actionid));
				rQuery.setPage(1);
				rQuery.setPageSize(200); 
				rQuery.setIsCount(2);//2打出sql 
				sqlquery.put(actionid, rQuery);
				return rQuery;
			}
			else
			{
				return (RQuery)obj;
			}
		}
		else
		{
			RQuery rQuery=new RQuery(this.findSqlName(tplid),this.findSlaRules(actionid));
			rQuery.setPage(1);
			rQuery.setPageSize(200); 
			rQuery.setIsCount(2);//2打出sql 
			sqlquery.put(actionid, rQuery);
			return rQuery;			
		}
	}
	/**
	 * 查询满足条件的数据
	 * @param actionCondition
	 * @param actionid 规则id
	 * @param tplid 模板id
	 * @return
	 */
	protected DataTable getActionBusinessData(String actionid,String tplid)
	{
		RQuery rQuery=getRQuery(actionid,tplid,1);
		DataTable dt=null;
		if(rQuery!=null)
		{
			dt=rQuery.getDataTable();
		}
		return dt;
	}
	/**
	 * 
	 * @param actionCondition
	 * @param dealModel 匹配类型 1：数据源匹配 2：规则匹配
	 * @param actionid 动作id
	 * @param tplid 模版id
	 * @return
	 */
	protected List<DataRow> getActionBusinessData(IActionCondition actionCondition,String dealModel,String actionid,String tplid)
	{
		if(dealModel=="")
			return null;
		List<DataRow> dataRowList = new ArrayList<DataRow>();
		if(dealModel.equals("1")){//数据源匹配
			dataRowList = actionCondition.matchSqlResult(this.findDataSourceSql(tplid), this.findSlaRules(actionid,dealModel));
		}else if(dealModel.equals("2")){//数据规则匹配
			dataRowList = actionCondition.matchRuleResult(this.findDataSourceObj(tplid), this.findSlaRules(actionid,dealModel));
		}else{
			RecordLog.printLog("异常的处理模式,不予处理!", RecordLog.LOG_LEVEL_ERROR);
		}
		return dataRowList;
	}	
	/**
	 * 获取规则条件
	 * @param id
	 * @return
	 */
	protected HashMap<String,String> findSlaRules(String id){
		HashMap<String,String> map = new HashMap<String,String>();
		DataTable dataTable = this.getRuleProperty(id);
		int dataTableLen = 0;
		if(dataTable!=null)
			dataTableLen = dataTable.length();
		DataRow dataRow;
		
		HashMap<String,List<SqlConditionExp>> mapSqlConditionExp = new HashMap<String,List<SqlConditionExp>>();
		List<SqlConditionExp> listdata  = null;
		SqlConditionExp sqlConditionExp;
		
		for(int row=0;row<dataTableLen;row++){
			dataRow = dataTable.getDataRow(row);
			String pid = StringUtils.checkNullString(dataRow.getString("pid"));//规则id
			String fieldid = StringUtils.checkNullString(dataRow.getString("fieldid"));
			String inputtype = StringUtils.checkNullString(dataRow.getString("inputtype"));
			String inputvaluetype = StringUtils.checkNullString(dataRow.getString("inputvaluetype"));
			String operator = StringUtils.checkNullString(dataRow.getString("operator"));
			String value = StringUtils.checkNullString(dataRow.getString("value"));
	
			if(!inputtype.equals("0")){
				//分组的方式进行条件存储
				if(mapSqlConditionExp.get(pid)!=null){
					listdata = (List<SqlConditionExp>) mapSqlConditionExp.get(pid);
					sqlConditionExp = new SqlConditionExp();
					sqlConditionExp.setFieldid(fieldid);
					sqlConditionExp.setInputvaluetype(inputvaluetype);
					sqlConditionExp.setOperator(operator);
					sqlConditionExp.setValue(value);
					listdata.add(sqlConditionExp);
				}else{
					listdata = new ArrayList<SqlConditionExp>();
					sqlConditionExp = new SqlConditionExp();
					sqlConditionExp.setFieldid(fieldid);
					sqlConditionExp.setInputvaluetype(inputvaluetype);
					sqlConditionExp.setOperator(operator);
					sqlConditionExp.setValue(value);
					listdata.add(sqlConditionExp);
					mapSqlConditionExp.put(pid, listdata);
				}
			}else{
				map.put(fieldid, value);//变量域
			}
		}
		
		int mapSqlConditionExpLen = 0;
		if(mapSqlConditionExp!=null)
			mapSqlConditionExpLen = mapSqlConditionExp.size();
		String ruleStr = "";
		if(mapSqlConditionExpLen>0)//存在规则
			ruleStr = DataExpDeal.getWhereExpr(mapSqlConditionExp,"1");
		map.put(SmsPara.slaRuleWhereStr, ruleStr);
		return map;
	}
	
	/**
	 * 获取规则条件
	 * @param id
	 * @param dealModel 匹配类型 1：数据源匹配 2：规则匹配
	 * @return
	 */
	protected String findSlaRules(String id,String dealModel){
		String ruleStr = "";
		DataTable dataTable = this.getRuleProperty(id);
		int dataTableLen = 0;
		if(dataTable!=null)
			dataTableLen = dataTable.length();
		DataRow dataRow;
		HashMap<String,List<SqlConditionExp>> map = new HashMap<String,List<SqlConditionExp>>();
		List<SqlConditionExp> listdata  = null;
		SqlConditionExp sqlConditionExp;
		for(int row=0;row<dataTableLen;row++){
			dataRow = dataTable.getDataRow(row);
			
			String pid = StringUtils.checkNullString(dataRow.getString("pid"));//规则id
			String fieldid = StringUtils.checkNullString(dataRow.getString("fieldid"));
			String inputvaluetype = StringUtils.checkNullString(dataRow.getString("inputvaluetype"));
			String operator = StringUtils.checkNullString(dataRow.getString("operator"));
			String value = StringUtils.checkNullString(dataRow.getString("value"));

			if(dealModel.equals("2"))
				fieldid = "#" + fieldid + "#";
			//分组的方式进行条件存储
			if(map.get(pid)!=null){
				listdata = (List<SqlConditionExp>) map.get(pid);
				sqlConditionExp = new SqlConditionExp();
				sqlConditionExp.setFieldid(fieldid);
				sqlConditionExp.setInputvaluetype(inputvaluetype);
				sqlConditionExp.setOperator(operator);
				sqlConditionExp.setValue(value);
				listdata.add(sqlConditionExp);
			}else{
				listdata = new ArrayList<SqlConditionExp>();
				sqlConditionExp = new SqlConditionExp();
				sqlConditionExp.setFieldid(fieldid);
				sqlConditionExp.setInputvaluetype(inputvaluetype);
				sqlConditionExp.setOperator(operator);
				sqlConditionExp.setValue(value);
				listdata.add(sqlConditionExp);
				map.put(pid, listdata);
			}
		}
		
		int mapLen = 0;
		if(map!=null)
			mapLen = map.size();
		if(mapLen>0)//存在规则
			ruleStr = DataExpDeal.getWhereExpr(map,dealModel);
		return ruleStr;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	protected String findSqlName(String id){
		String p_sql = "select datasource from bs_t_sm_ruletpl where status = 1 and pid = ?";//获取数据配置文件名称
		Object[] values = {id};
		DataTable dataTable = null;
		try{
			dataTable = queryAdapter.executeQuery(p_sql, values,2);
		}catch(Exception e){
			RecordLog.printLog("获取动作规则模版数据源文件名出错,模版pid="+id, RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		int dataTableLen = 0;
		if(dataTable!=null)
			dataTableLen = dataTable.length();
		DataRow dataRow;
		String dataSource = "";
		for(int row=0;row<dataTableLen;row++){
			dataRow = dataTable.getDataRow(row);
			dataSource = StringUtils.checkNullString(dataRow.getString("datasource"));
		}
		return dataSource;
	}
	
	/**
	 * 获取select部分
	 * @param id
	 * @return
	 */
	protected String findDataSourceSql(String id){
		String dataSourceSql = "";
		String p_sql = "select datasource from bs_t_sm_ruletpl where status = 1 and pid = ?";//获取数据配置文件名称
		Object[] values = {id};
		DataTable dataTable = null;
		try{
			dataTable = queryAdapter.executeQuery(p_sql, values,2);
		}catch(Exception e){
			RecordLog.printLog("获取动作规则模版数据源文件名出错,模版pid="+id, RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		int dataTableLen = 0;
		if(dataTable!=null)
			dataTableLen = dataTable.length();
		DataRow dataRow;
		String dataSource = "";
		for(int row=0;row<dataTableLen;row++){
			dataRow = dataTable.getDataRow(row);
			dataSource = StringUtils.checkNullString(dataRow.getString("datasource"));
		}
		
		if(!dataSource.equals(""))
			dataSourceSql = getXmlSql(RConstants.xmlPath + File.separator + "sla",dataSource);
		return dataSourceSql;
	}
	
	/**
	 * 获取源数据集合
	 * @param id
	 * @return
	 */
	protected List<Object> findDataSourceObj(String id){
		List<Object> obj = new ArrayList<Object>();
		String p_sql = new String("select implclass from bs_t_sm_ruletpl where status = 1 and pid = ?");
		Object[] values = {id};
		DataTable dataTable = null;
		try{
			dataTable = queryAdapter.executeQuery(p_sql, values,2);
		}catch(Exception e){
			RecordLog.printLog("获取动作规则模版数据源实现类出错,模版pid="+id, RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		int dataTableLen = 0;
		if(dataTable!=null)
			dataTableLen = dataTable.length();
		DataRow dataRow;
		String impclass = "";
		for(int row=0;row<dataTableLen;row++){
			dataRow = dataTable.getDataRow(row);
			impclass = StringUtils.checkNullString(dataRow.getString("implclass"));
		}
		if(!impclass.equals("")){
			SlaMatchDataSourceService matchDataSourceService = null;
			if(impclass.contains(".")){
				try {
				   matchDataSourceService = (SlaMatchDataSourceService)Class.forName(impclass).newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}else{
				   matchDataSourceService =(SlaMatchDataSourceService)WebApplicationManager.getBean(impclass);
			}
			obj = matchDataSourceService.getDataSource();
		}
		return obj;
	}
	
	
	protected abstract List<Object> getList(long p_threadNo);
	
	/**
	 * 
	 * @param slaxmlFileName
	 * @param slafileName
	 * @return
	 */
	private static String getXmlSql(String slaxmlFileName,String slafileName){
		String sourceDataSql = "";
		if(slafileName == "")
			return "";
		File initfolder=new File(slaxmlFileName);
		File list[]=initfolder.listFiles();  
		int initfolderLen=0;
		if(list!=null)
			initfolderLen=list.length;
		for(int i=0;i<initfolderLen;i++){
			if(list[i].getName().startsWith("SQL_") && list[i].getName().toLowerCase().endsWith(".xml")){
				if(list[i].getName().equals(slafileName)){
					XmlParser xmlParser  = new XmlParser(slaxmlFileName+File.separator+list[i].getName());
					sourceDataSql = xmlParser.getValue("sqlquery#select");
					break;
				}
			}
		}
		return sourceDataSql;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	private DataTable getRuleProperty(String id){
		StringBuffer p_sql = new StringBuffer();
		p_sql.append("select");
		p_sql.append(" slarule.pid,ruleproperty.fieldid,ruleproperty.inputtype,ruleproperty.inputvaluetype,slaruleproperty.operator,slaruleproperty.value value ");
		p_sql.append("from bs_t_sm_slarule         slarule,");//规则配置表
		p_sql.append("  bs_t_sm_slaruleproperty slaruleproperty,");//规则属性数据表
		p_sql.append("  bs_t_sm_ruletplproperty ruleproperty ");//模版属性配置表
		p_sql.append("where slarule.pid = slaruleproperty.ruleid");
		p_sql.append(" and slaruleproperty.propertyid = ruleproperty.pid ");
		p_sql.append(" and ruleproperty.status = 1 ");
		p_sql.append(" and slarule.status = 1 ");
		p_sql.append(" and slarule.actionid = ?");
		Object[] values = {id};
		DataTable dataTable = null;
		try{
			dataTable = queryAdapter.executeQuery(p_sql.toString(), values,2);
		}catch(Exception e){
			RecordLog.printLog("获取动作规则出错,动作id="+id, RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return dataTable;
	}
	
	/**
	 * 
	 * @param datarow
	 * @param p_pid
	 * @param p_actionname
	 * @param p_ruletplid
	 * @param p_isbusinessinformer
	 * @param p_noticeuserid
	 * @param p_noticegroupid
	 * @param p_smcontent
	 * @param p_pri
	 * @param p_slarolemodel
	 * @param p_noticeroleid
	 */
	protected void dealActionByDataRowListOfSM(List<DataRow> datarow,
			String p_pid, String p_actionname, String p_ruletplid,
			String p_isbusinessinformer, String p_noticeuserid,
			String p_noticegroupid, String p_smcontent, String p_pri,
			String p_slarolemodel, String p_noticeroleid,int dealtimes,int steptime) 
	{
		int dataRowLen = 0;
		if(datarow!=null)
			dataRowLen = datarow.size();
		//RecordLog.printLog("短信通知："+p_actionname+",根据动作规则进行业务数据查询,获取的数据："+(dataRowLen)+"条",RecordLog.LOG_LEVEL_INFO);
		DataRow dataRow = null;
		for(int row = 0;row<dataRowLen;row++){
			//RecordLog.printLog("短信通知："+p_actionname+",第"+(row+1)+"条业务数据处理--start",RecordLog.LOG_LEVEL_INFO);
			dataRow = datarow.get(row);
			dealActionParaData(dataRow, p_pid, p_actionname,
					p_isbusinessinformer, p_noticeuserid, p_noticegroupid,
					p_smcontent, p_pri, p_slarolemodel, p_noticeroleid,
					dealtimes,steptime);
			//RecordLog.printLog("短信通知："+p_actionname+",第"+(row+1)+"条业务数据处理--end",RecordLog.LOG_LEVEL_INFO);
		}
	}
	
	/**
	 * 
	 * @param p_dt 满足条件的业务数据集合
	 * @param p_actionid 动作ID
	 * @param p_actionname 动作名称
	 * @param p_ruletplid 模板ID
	 * @param p_isbusinessinformer
	 * @param p_noticeuserid
	 * @param p_noticegroupid
	 * @param p_smcontent
	 * @param p_pri
	 * @param p_slarolemodel
	 * @param p_noticeroleid
	 * @param times 处理次数
	 */
	protected void dealActionByDataTableOfSM(DataTable p_dt, String p_actionid,
			String p_actionname, String p_ruletplid,
			String p_isbusinessinformer, String p_noticeuserid,
			String p_noticegroupid, String p_smcontent, String p_pri,
			String p_slarolemodel, String p_noticeroleid,int times,int steptime) 
	{
		int p_dtLen = 0;
		if(p_dt!=null)
			p_dtLen = p_dt.length();
		DataRow dataRow = null;
		for(int row=0;row<p_dtLen;row++)
		{
			dataRow = p_dt.getDataRow(row);
			dealActionParaData(dataRow, p_actionid, p_actionname,
					p_isbusinessinformer, p_noticeuserid, p_noticegroupid,
					p_smcontent, p_pri, p_slarolemodel, p_noticeroleid,times,steptime);
		}
	}
	
	/**
	 * 
	 * @param datarow
	 * @param pid
	 * @param actionname
	 * @param ruletplid
	 * @param isbusinessinformer
	 * @param noticeuserid
	 * @param noticegroupid
	 * @param copyuserid
	 * @param copygroupid
	 * @param mailsubject
	 * @param mailcontent
	 * @param slarolemodel
	 * @param noticeroleid
	 */
	protected void dealActionByDataRowListOfEmail(List<DataRow> datarow,
			String pid, String actionname, String ruletplid,
			String isbusinessinformer, String noticeuserid,
			String noticegroupid, String copyuserid, String copygroupid,
			String mailsubject, String mailcontent, String slarolemodel,
			String noticeroleid,int times,int steptime)
	{
		int dataRowLen = 0;
		if(datarow!=null)
			dataRowLen = datarow.size();
		//RecordLog.printLog("邮件通知："+actionname+",根据动作规则进行业务数据查询,获取的数据："+(dataRowLen)+"条",RecordLog.LOG_LEVEL_INFO);
		DataRow dataRow = null;
		for(int bRow=0;bRow<dataRowLen;bRow++){
			//RecordLog.printLog("邮件通知："+actionname+",第"+(bRow+1)+"条业务数据处理--start",RecordLog.LOG_LEVEL_INFO);
			dataRow = datarow.get(bRow);
			dealActionParaDataOfEmail(dataRow, pid, actionname,
					isbusinessinformer, noticeuserid, noticegroupid,
					copyuserid, copygroupid, mailsubject, mailcontent,
					slarolemodel, noticeroleid,times,steptime);
			//RecordLog.printLog("邮件通知："+actionname+",第"+(bRow+1)+"条业务数据处理--end",RecordLog.LOG_LEVEL_INFO);
		}
	}
	
	/**
	 * 
	 * @param p_dt
	 * @param p_pid
	 * @param p_actionname
	 * @param p_ruletplid
	 * @param p_isbusinessinformer
	 * @param noticeuserid
	 * @param noticegroupid
	 * @param copyuserid
	 * @param copygroupid
	 * @param mailsubject
	 * @param mailcontent
	 * @param slarolemodel
	 * @param noticeroleid
	 */
	protected void dealActionByDataTableOfEmail(DataTable p_dt, String p_pid,
			String p_actionname, String p_ruletplid,
			String p_isbusinessinformer, String noticeuserid,
			String noticegroupid, String copyuserid, String copygroupid,
			String mailsubject, String mailcontent, String slarolemodel,
			String noticeroleid,int times,int steptime )
	{
		int p_dtLen = 0;
		if(p_dt!=null)
			p_dtLen = p_dt.length();
		DataRow dataRow;
		for(int bRow=0;bRow<p_dtLen;bRow++)
		{
			dataRow = p_dt.getDataRow(bRow);
			dealActionParaDataOfEmail(dataRow, p_pid, p_actionname,
					p_isbusinessinformer, noticeuserid, noticegroupid,
					copyuserid, copygroupid, mailsubject, mailcontent,
					slarolemodel, noticeroleid,times,steptime);
		}
	}
	
	/**
	 * 
	 * @param p_dataRow
	 * @param p_pid
	 * @param p_actionname
	 * @param p_isbusinessinformer
	 * @param noticeuserid
	 * @param noticegroupid
	 * @param copyuserid
	 * @param copygroupid
	 * @param mailsubject
	 * @param mailcontent
	 * @param slarolemodel
	 * @param noticeroleid
	 */
	private void dealActionParaDataOfEmail(DataRow p_dataRow, String p_actionpid,
			String p_actionname, String p_isbusinessinformer,
			String noticeuserid, String noticegroupid, String copyuserid,
			String copygroupid, String mailsubject, String mailcontent,
			String slarolemodel, String noticeroleid,int times,int steptime) 
	{
		String actionid=p_actionpid;
		if(p_dataRow==null)
			return;
		String businessPid = p_dataRow.getString(SmsPara.taskPid);//业务数据pid
		String result=SlaDealRecordDao.isDeal(actionid, businessPid);
		String strAry[]=result.split(";");
		int dealTimes=0;//处理次数
		String dealMaxRuleid="";
		
		if(strAry.length>1)
		{
			dealTimes=NumberUtils.formatToInt(strAry[0]);
			dealMaxRuleid=StringUtils.checkNullString(strAry[1]);
		}
		if(times>0 && dealTimes>=times)
		{
			return;
			//验证该条记录是否已经处理过

		}
		if(times!=1 && steptime>0 )
		{
			long curTimes=getTimes(steptime);
			if(curTimes>0)
			{
				//如果取到当天的次数
				actionid+="_"+TimeUtils.getCurrentDate("yyyyMMdd")+curTimes;
				if(dealMaxRuleid.equals(actionid))//如果是当前次数则不用处理
				{
					return ;
				}
				
			}
			else
			{
				return ;
			}
		}
		//邮件主题
		mailsubject = SqlReplace.stringReplaceAllVar(mailsubject,p_dataRow.getRowHashMap());
		//邮件内容
		mailcontent = SqlReplace.stringReplaceAllVar(mailcontent,p_dataRow.getRowHashMap());
		
		List<String> taskUserEmail = null;
		List<String> taskGroupEmail = null;
		List<String> taskRoleGroupEmail = null;
		if(p_isbusinessinformer.equals("1"))
		{//通知任务通知人/组
			taskUserEmail = NoticeParaDeal.getEmailByUserId(p_dataRow.getString(SmsPara.taskNoticeUserid));
			taskGroupEmail = NoticeParaDeal.getEmailByGroupId(p_dataRow.getString(SmsPara.taskNoticeGroupid));
			taskRoleGroupEmail = NoticeParaDeal.getEmailByRoleGroupId(p_dataRow.getString(SmsPara.taskNoticeRoleGroupid));
		}
		
		//自定义通知人/组
		List<String> customUserEmail = new ArrayList<String>();
		if(!noticeuserid.equals(""))
			customUserEmail = NoticeParaDeal.getEmailByUserId(noticeuserid);
		List<String> customGroupEmail = new ArrayList<String>();
		if(!noticegroupid.equals(""))
			customGroupEmail = NoticeParaDeal.getEmailByGroupId(noticegroupid);
		List<String> copyUserEmail = new ArrayList<String>();
		if(!copyuserid.equals(""))
			copyUserEmail = NoticeParaDeal.getEmailByUserId(copyuserid);
		List<String> copyGroupEmail = new ArrayList<String>();
		if(!copygroupid.equals(""))
			copyGroupEmail = NoticeParaDeal.getEmailByGroupId(copygroupid);
		List<String> customRoleMobile = new ArrayList<String>();
		
		if(slarolemodel.equals("ownerRole")){//默认角色
			customRoleMobile = NoticeParaDeal.getEmailByRole(noticeroleid);
		}else if(slarolemodel.equals("ownerDep")){//所属部门
			if(p_dataRow.getString(SmsPara.belongDepId)!="")
				customRoleMobile = NoticeParaDeal.getEmailByDepOrCompanyId(noticeroleid,p_dataRow.getString(SmsPara.belongDepId));
		}else if(slarolemodel.equals("ownerCompany")){//所属公司
			if(p_dataRow.getString(SmsPara.belongCompanyId)!="")//{
				customRoleMobile = NoticeParaDeal.getEmailByDepOrCompanyId(noticeroleid,p_dataRow.getString(SmsPara.belongCompanyId));
		}else{}
		
		//发送人
		List<String> addressemail = new ArrayList<String>();
		try {
			addressemail = ArrayTransferUtils.copyListNoteTheSame(
					taskUserEmail, taskGroupEmail, taskRoleGroupEmail,
					customUserEmail, customGroupEmail, customRoleMobile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//超送人
		List<String> copyAddressemail = new ArrayList<String>();
		try {
			copyAddressemail = ArrayTransferUtils.copyListNoteTheSame(copyUserEmail, copyGroupEmail);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<String> sendTo = new ArrayList<String>();
		try {
			sendTo = ArrayTransferUtils.copyListNoteTheSame(addressemail, copyAddressemail);
		} catch (Exception e) {
			e.printStackTrace();
		}
		emailBean = new EmailBean();
		emailBean.setMailTo(sendTo);
		emailBean.setSubject(mailsubject);
		emailBean.setMsgContent(mailcontent);
		emailHandle.execute(emailBean);
		
		//发送情况记录,避免重复发送
		boolean flag = SlaDealRecordDao.insert(actionid, businessPid, 1);
		if(flag){
			RecordLog.printLog("邮件通知： 动作="+p_actionname+",业务数据="+businessPid+"。发送结果记录成功!", RecordLog.LOG_LEVEL_INFO);
		}else{
			RecordLog.printLog("邮件通知： 动作="+p_actionname+",业务数据="+businessPid+"。发送结果记录失败!", RecordLog.LOG_LEVEL_ERROR);
		}
	}
	
	/**
	 * 
	 * @param p_dataRow
	 * @param p_pid
	 * @param p_actionname
	 * @param p_isbusinessinformer
	 * @param p_noticeuserid
	 * @param p_noticegroupid
	 * @param p_smcontent
	 * @param p_pri
	 * @param p_slarolemodel
	 * @param p_noticeroleid
	 * @param times 处理次数
	 * @param steptime 间隔时间，当处理次数大于零时的每次重复需要处理SLA的间隔时间 单位为分钟
	 */
	private void dealActionParaData(DataRow p_dataRow, String p_actionpid,
			String p_actionname, String p_isbusinessinformer,
			String p_noticeuserid, String p_noticegroupid, String p_smcontent,
			String p_pri, String p_slarolemodel, String p_noticeroleid,int times,int steptime) 
	{
		String actionid=p_actionpid;
		if(p_dataRow==null)
			return;
		String businessPid = p_dataRow.getString(SmsPara.taskPid);//业务数据pid
		String result=SlaDealRecordDao.isDeal(actionid, businessPid);
		String strAry[]=result.split(";");
		int dealTimes=0;//处理次数
		String dealMaxRuleid="";
		
		if(strAry.length>1)
		{
			dealTimes=NumberUtils.formatToInt(strAry[0]);
			dealMaxRuleid=StringUtils.checkNullString(strAry[1]);
		}
		if(times>0 && dealTimes>=times)
		{
			return;
			//验证该条记录是否已经处理过

		}
		if(times!=1 && steptime>0 )
		{
			long curTimes=getTimes(steptime);
			if(curTimes>0)
			{
				//如果取到当天的次数
				actionid+="_"+TimeUtils.getCurrentDate("yyyyMMdd")+curTimes;
				if(dealMaxRuleid.equals(actionid))//如果是当前次数则不用处理
				{
					return ;
				}
				
			}
			else
			{
				return ;
			}
		}		
		//短信内容
		p_smcontent = SqlReplace.stringReplaceAllVar(p_smcontent,p_dataRow.getRowHashMap());
		
		List<String> taskUserMobile = null;
		List<String> taskGroupMobile = null;
		List<String> taskRoleGroupMobile = null; 
		
		if(p_isbusinessinformer.equals("1")){//通知任务通知人
			taskUserMobile = NoticeParaDeal.getMobileByLoginName(p_dataRow.getString(SmsPara.taskNoticeUserid));
			taskGroupMobile = NoticeParaDeal.getMobileByGroupId(p_dataRow.getString(SmsPara.taskNoticeGroupid));
			taskRoleGroupMobile = NoticeParaDeal.getMolbileByRoleGroup(p_dataRow.getString(SmsPara.taskNoticeRoleGroupid));
		}
		
		//自定义通知人的手机号码
		List<String> customUserMobile = new ArrayList<String>();
		if(!p_noticeuserid.equals(""))
			customUserMobile = NoticeParaDeal.getMobileByUserId(p_noticeuserid);
		List<String> customGroupMobile = new ArrayList<String>();
		if(!p_noticegroupid.equals(""))
		    customGroupMobile = NoticeParaDeal.getMobileByGroupId(p_noticegroupid);
		List<String> customRoleMobile = new ArrayList<String>();
		if(p_slarolemodel.equals("ownerRole")){//默认角色
			customRoleMobile = NoticeParaDeal.getMobileByRoleId(p_noticeroleid);
		}else if(p_slarolemodel.equals("ownerDep")){//所属部门
			if(p_dataRow.getString(SmsPara.belongDepId)!="")
				customRoleMobile = NoticeParaDeal.getMobileByDepOrCompanyId(p_noticeroleid,p_dataRow.getString(SmsPara.belongDepId));
		}else if(p_slarolemodel.equals("ownerCompany")){//所属公司
			if(p_dataRow.getString(SmsPara.belongCompanyId)!="")//{
				customRoleMobile = NoticeParaDeal.getMobileByDepOrCompanyId(p_noticeroleid,p_dataRow.getString(SmsPara.belongCompanyId));
		}else{}
		
		//该业务需要通知人手机号集合
		List<String> goal = new ArrayList<String>();
		try {
			goal = ArrayTransferUtils.copyListNoteTheSame(taskUserMobile,
					taskGroupMobile, taskRoleGroupMobile, customUserMobile,
					customGroupMobile, customRoleMobile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int goalLen = 0;
		if(goal!=null)
			goalLen = goal.size();
		for(int g=0;g<goalLen;g++){
			sms = new Smsmonitor();
			sms.setContent(p_smcontent);
			sms.setGoal(goal.get(g));
			sms.setRelateid(businessPid);
			sms.setPri(Integer.parseInt(p_pri));
			//RecordLog.printLog("短信通知：业务数据="+businessPid+".手机号码="+goal.get(g)+".短信内容="+p_smcontent+".插入短信表--start",RecordLog.LOG_LEVEL_INFO);
			smsHandle.execute(sms);
			//RecordLog.printLog("短信通知：业务数据="+businessPid+".手机号码="+goal.get(g)+".短信内容="+p_smcontent+".插入短信表--end",RecordLog.LOG_LEVEL_INFO);
		}
		boolean flag = SlaDealRecordDao.insert(actionid, businessPid, 1);
		if(flag){
			RecordLog.printLog("短信通知： 动作="+p_actionname+",业务数据="+businessPid+"。发送结果记录成功!", RecordLog.LOG_LEVEL_INFO);
		}else{
			RecordLog.printLog("短信通知： 动作="+p_actionname+",业务数据="+businessPid+"。发送结果记录失败!", RecordLog.LOG_LEVEL_ERROR);
		}
	}
	
	/**
	 * 获取当天的第几次 以每天的零点开始
	 * @return
	 */
	public  long getTimes(int stepTime)
	{
		long result=0;
		long stTime=TimeUtils.formatDateStringToInt(TimeUtils.getCurrentDate("yyyy-MM-dd"),"yyyy-MM-dd");
		long curTime=TimeUtils.formatDateStringToInt(TimeUtils.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
		//long edTime=TimeUtils.formatDateStringToInt(TimeUtils.getCurrentDate("yyyy-MM-dd")+" 23:59:59","yyyy-MM-dd HH:mm:ss");
		result=(curTime-stTime) / (stepTime*60);
		return result;
	}
	 	
	public static void main(String[] args)
	{
		//ActionRule ActionRule=new ActionRule();
		//System.out.println(ActionRule.getTimes(0, 30));
		long a1=11;
		long a2=3;
		System.out.print(a1/a2);
		
		
	} 
}
