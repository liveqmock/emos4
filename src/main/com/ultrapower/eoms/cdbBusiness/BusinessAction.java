package com.ultrapower.eoms.cdbBusiness;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONArray;

import com.ultrapower.eoms.common.core.component.data.DataAdapter;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.component.rquery.RQuery;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.msextend.notice.web.NoticeViewLogAction;
import com.ultrapower.eoms.msextend.sla.SlaExtUtil;
import com.ultrapower.eoms.ultrasm.model.ChgBatchInfo;
import com.ultrapower.eoms.ultrasm.model.UserInfo;
import com.ultrapower.wfinterface.core.manager.DefaultWFInterfaceConfig;
import com.ultrapower.wfinterface.core.service.IWFInterfaceConfig;

/**
 * 国家开发银行零碎的小功能ACTION
 * @author duly
 *
 */
public class BusinessAction  extends BaseAction {
	private DataAdapter dataAdapter;
	private QueryAdapter query;
	private String busSystem;
	private String changeSort;
	private String ids;
	private Map<String,String> userMap;
	private String configType;//变更类型  1：标准变更 2：一般变更3：重大变更
	private String sqlName;//SQL语句或SQLXML文件配置名
	private HashMap conMap;//条件 MAP
	private NoticeViewLogAction noticeViewLogAction;
	private Map<String,String> batchConMap;  
	private Long intoStepTimeL;
	
	//获取当前登录人的部门和组以及所有上级部门的全称
	public void getDgsIncludeParent() throws Exception{
		renderText(noticeViewLogAction.getDgsIncludeParent(getUserSession().getGroupId(), getUserSession().getDepId()));
	}

	
	/**
	 * 通过对象的SQL语句或SQLXML文件名称获取查询的JSON 通用方法
	 */
	public void getJsonBySQL(){
		if(StringUtils.isNotBlank(sqlName)){
			DataTable dt = null;
			if(sqlName.indexOf("select") != -1){
				query = new QueryAdapter();
				dt = query.executeQuery(sqlName);
			}else{
				RQuery rquery = new RQuery(sqlName,conMap);
				System.out.println(rquery.getSqlString());
				dt = rquery.getDataTable();
			}
			if(dt != null && dt.length() > 0){
				List<Map> list = new ArrayList<Map>();
				for(Object obj:dt.getRowList()){
					DataRow dr = (DataRow)obj;
					list.add(dr.getRowHashMap());
				}
				String jsonStr = JSONArray.fromObject(list).toString();
				this.renderText(jsonStr);
			}
		}
	}

	
/*******************************	变更工单是否评审配置start	**********************************/	
	public String changeIsAuditConfig(){
		return SUCCESS;
	}
	
	//删除变更分类与业务系统的关联
	public void delConfig(){
		if(ids != null && !"".equals(ids)){
			String sql = "delete from bs_t_wf_changeIsAudit where pid in ('" + ids.replaceAll(",","','") + "')";
			dataAdapter = new DataAdapter();
			dataAdapter.execute(sql,null);
		}
		this.renderText("success");
	}

	//添加 变更分类 与 业务系统 的关联
	public void addConfig(){
		if(busSystem != null && !"".equals(busSystem) && changeSort != null && !changeSort.equals("")){
			String[] bus = busSystem.split(",");
			String[] sort = changeSort.split(",");
			String querySql = "select * from bs_t_wf_changeIsAudit where changesort = ? and bussystem = ?";
			String sql = "insert into bs_t_wf_changeIsAudit(pid,changesort,bussystem) values(?,?,?) ";
			dataAdapter = new DataAdapter();
			query = new QueryAdapter();
			boolean bl = true;
			for(String bs:bus){
				for(String st:sort){
					if(bs != null && !bs.equals("") && st != null && !st.equals("")){
						String[] qValue = {st,bs};
						DataTable dt = query.executeQuery(querySql,qValue);
						if(dt == null || dt.length() == 0){
							String[] values=  {UUID.randomUUID().toString(),st,bs};
							dataAdapter.execute(sql, values);
							bl = false;
						}
					}
				}
			}
			if(bl){
				this.renderText("已存在，不能重复添加！");
			}else{
				this.renderText("success");
			}
		}
	}	
/*******************************	变更工单是否评审配置 end	**********************************/






/*******************************	变更工单处理人配置 start	**********************************/		
	public String changeDealUserConfig(){
		return SUCCESS;
	}
	
	//新增或修改
	public String addChangeDealUserConfig(){
		if(userMap == null){
			userMap = new HashMap<String, String>();
		}
		if(ids != null && !ids.equals("")){
			String querySql = "select * from bs_t_wf_changeDealUserConfig where pid = '" + ids + "'";
			query = new QueryAdapter();
			DataTable dt = query.executeQuery(querySql);
			if(dt != null && dt.length() == 1){
				userMap = dt.getDataRow(0).getRowHashMap();
			}
		}
		return SUCCESS;
	}
	

	
	//AJAX验证  本业务系统 是否已经配置
	public void checkChangeDealUserConfig_AJAX(){
		String changeType = this.getRequest().getParameter("changeType");
		String querySql = "select * from bs_t_wf_changeDealUserConfig where busSystem = '" + busSystem + "' and changesort = '" + changeSort + "' and changetype = '" + changeType + "'";
		query = new QueryAdapter();
		DataTable dt = query.executeQuery(querySql);
		if(dt != null && dt.length() > 0){
			this.renderText("true");
		}else{
			this.renderText("false");
		}
	}
	
	//添加 变更分类 的各处理人    业务分类与业务系统的批量增加 业务系统可为空
	public String addDealUserConfig(){
		changeSort = userMap.get("changeSort");
		String busSystem = userMap.containsKey("busSystem")?userMap.get("busSystem"):"";//业务系统
		String type = userMap.get("changeType");
		if(changeSort != null && !changeSort.equals("")){
			List<Map<String,String>> conditionList = new ArrayList<Map<String,String>>();
			String[] sort = changeSort.split(",");
			String[] bus = busSystem.split(",");
			for(String s:sort){
				Map<String,String> valuemap = new HashMap<String, String>();
				valuemap.put("changesort",s);
				if(busSystem.equals("")){
					conditionList.add(valuemap);
				}else{
					for(String sys:bus){
						valuemap.put("bussystem",sys);
						conditionList.add(valuemap);
					}
				}
			}
			
			for(Map<String,String> map:conditionList){
				String st = map.get("changesort");
				String sys = map.get("bussystem");
				userMap.put("changeSort",st);
				userMap.put("busSystem",sys);
				
				String querySql = "select * from bs_t_wf_changeDealUserConfig where changesort = '" + st + "' and changetype = '" + type + "'";
				if(sys != null && !sys.equals("")){
					querySql += " and busSystem ='" + sys + "'";
				}else{
					querySql += " and busSystem is null";				
				}
				
				if(StringUtils.isNotBlank(ids)){
					querySql = "select * from bs_t_wf_changeDealUserConfig where pid = '" + ids + "'";
				}
				//String sql = "insert into bs_t_wf_changeDealUserConfig(pid,changesort,changetype,configName,editUser,editUserId,auditUsers,auditUserIds,excuteUser,excuteUserId,testUser,testUserId,configDesc) values(?,?,?) ";
				
				dataAdapter = new DataAdapter();
				query = new QueryAdapter();
			
				if(st != null && !st.equals("")){
					String key = "";
					DataTable dt = query.executeQuery(querySql);
					if(dt != null && dt.length() == 1){
						key = dt.getDataRow(0).getString("pid");
					}
					
					DataTable table = new DataTable("bs_t_wf_changeDealUserConfig");
					String[] keys = { "pid" };
					table.setPrimaryKey(keys);
					
					DataRow dr = new DataRow();
					if(key == ""){
						key = UUID.randomUUID().toString();
						userMap.put("pid",key);
						dr.setOptype(1);
						for(String k:userMap.keySet()){
							dr.put(k,userMap.get(k));
						}
						table.putDataRow(dr);
						int i = dataAdapter.executeAdd(table);
						System.out.println("i:" + i);
					}else{
						userMap.put("pid",key);
						dr.setOptype(2);
						for(String k:userMap.keySet()){
							dr.put(k,userMap.get(k));
						}
						table.putDataRow(dr);
						dataAdapter.executeUpdate(table);
					}
				}
			}
			this.getRequest().setAttribute("result","success");
		}
		return this.findForward("addChangeDealUserConfig");
	}
	
	
	//删除变更分类与业务系统的关联
	public void delDealUserConfig(){
		if(ids != null && !"".equals(ids)){
			String sql = "delete from bs_t_wf_changeDealUserConfig where pid in ('" + ids.replaceAll(",","','") + "')";
			dataAdapter = new DataAdapter();
			dataAdapter.execute(sql,null);
		}
		this.renderText("success");
	}
/*******************************	变更工单处理人配置 end	**********************************/	
	

/*******************************	发布工单处理人配置 start	**********************************/		
	public String releaseDealUserConfig(){
		return SUCCESS;
	}
	
	//新增或修改
	public String addReleaseDealUserConfig(){
		if(userMap == null){
			userMap = new HashMap<String, String>();
		}
		if(ids != null && !ids.equals("")){
			String querySql = "select * from bs_t_wf_releaseDealUserConfig where pid = '" + ids + "'";
			query = new QueryAdapter();
			DataTable dt = query.executeQuery(querySql);
			if(dt != null && dt.length() == 1){
				userMap = dt.getDataRow(0).getRowHashMap();
			}
		}
		return SUCCESS;
	}
	
	//AJAX验证  本业务系统 是否已经配置
	public void checkReleaseDealUserConfig_AJAX(){
		String querySql = "select * from bs_t_wf_releaseDealUserConfig where busSystem = '" + busSystem + "'";
		query = new QueryAdapter();
		DataTable dt = query.executeQuery(querySql);
		if(dt != null && dt.length() > 0){
			this.renderText("true");
		}else{
			this.renderText("false");
		}
	}
	
	//添加 变更分类 的各处理人    业务分类与业务系统的批量增加 业务系统可为空
	public String saveReleaseDealUserConfig(){
		String busSystem = userMap.containsKey("busSystem")?userMap.get("busSystem"):"";//业务系统
			if(busSystem != null && !busSystem.equals("")){
				String querySql = "select * from bs_t_wf_releaseDealUserConfig where busSystem = '" + busSystem + "'";
				if(StringUtils.isNotBlank(ids)){
					querySql = "select * from bs_t_wf_releaseDealUserConfig where pid = '" + ids + "'";
				}
				String commonTreeSql ="select a.*  from bs_t_sm_commontree a where a.type='busSystem' and a.name= ?";
				dataAdapter = new DataAdapter();
				query = new QueryAdapter();
				String key = "";
				String key1="";
				DataTable dt = query.executeQuery(querySql);
				DataTable dt1 = query.executeQuery(commonTreeSql,busSystem);
				if(dt != null && dt.length() == 1){
					key = dt.getDataRow(0).getString("pid");
				}
				if(dt1 != null && dt1.length() == 1){
					key1=dt1.getDataRow(0).getString("PROPFIELD_03");
				}
				
				DataTable table = new DataTable("bs_t_wf_releaseDealUserConfig");
				String[] keys = { "pid" };
				table.setPrimaryKey(keys);
				
				DataRow dr = new DataRow();
				if(key == ""){
					key = UUID.randomUUID().toString();
					userMap.put("pid",key);
					userMap.put("BUSSYSTEMCODE",key1);
					dr.setOptype(1);
					for(String k:userMap.keySet()){
						dr.put(k,userMap.get(k));
					}
					table.putDataRow(dr);
					int i = dataAdapter.executeAdd(table);
					System.out.println("i:" + i);
				}else{
					userMap.put("pid",key);
					userMap.put("BUSSYSTEMCODE",key1);
					dr.setOptype(2);
					for(String k:userMap.keySet()){
						dr.put(k,userMap.get(k));
					}
					table.putDataRow(dr);
					dataAdapter.executeUpdate(table);
				}
				this.getRequest().setAttribute("result","success");
			}
			return this.findForward("addReleaseDealUserConfig");
	}
	
	//删除变更分类与业务系统的关联
	public void delReleaseDealUserConfig(){
		if(ids != null && !"".equals(ids)){
			String sql = "delete from bs_t_wf_releaseDealUserConfig where pid in ('" + ids.replaceAll(",","','") + "')";
			dataAdapter = new DataAdapter();
			dataAdapter.execute(sql,null);
		}
		this.renderText("success");
	}
/*******************************	发布工单处理人配置 end	**********************************/	
		
	
	
/********************************变更批次信息配置 START   ******************************************/
	/**
	 * 变更批次信息配置 
	 * add by zhangliang
	 * @return
	 */
	public String changeBatchInfoConfig(){
		return SUCCESS;
	}

	//删除变更批次信息配置 
	public void delChgBatchConfig(){
		if(ids != null && !"".equals(ids)){
			String sql = "delete from BS_T_SM_BATCHCONF where pid in ('" + ids.replaceAll(",","','") + "')";
			dataAdapter = new DataAdapter();
			dataAdapter.execute(sql,null);
		}
		this.renderText("success");
	}
	
	//新增或修改
	public String addChgBatchConfig(){
		if(batchConMap == null){
			batchConMap = new HashMap<String, String>();
		}
		if(ids != null && !ids.equals("")){
			String querySql = "select * from BS_T_SM_BATCHCONF where pid = '" + ids + "'";
			query = new QueryAdapter();
			DataTable dt = query.executeQuery(querySql);
			if(dt != null && dt.length() == 1){
				batchConMap = dt.getDataRow(0).getRowHashMap();
			}
		}
		return SUCCESS;
	}
		
		
	public String saveChgBatchConfig(){
	    String operator = getUserSession().getFullName();
	    String operate_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		String	querySql = "";
		String key = "";
		String old_exec_flag = "";
		String z_log = "";
			if(StringUtils.isNotBlank(ids)){
					querySql = "select * from BS_T_SM_BATCHCONF where pid = '" + ids + "'";
					query = new QueryAdapter();
					
					DataTable dt = query.executeQuery(querySql);
					if(dt != null && dt.length() == 1){
						key = dt.getDataRow(0).getString("pid");
						old_exec_flag = dt.getDataRow(0).getString("exec_flag");
						z_log = dt.getDataRow(0).getString("z_log");
					}
			}
			dataAdapter = new DataAdapter();
			DataTable table = new DataTable("BS_T_SM_BATCHCONF");
			String[] keys = { "pid" };
			table.setPrimaryKey(keys);
			
			DataRow dr = new DataRow();
			if(key == ""){
				key = UUID.randomUUID().toString();
				batchConMap.put("pid",key);
				dr.setOptype(1);
				for(String k:batchConMap.keySet()){
					dr.put(k,batchConMap.get(k));
				}
				table.putDataRow(dr);
				int i = dataAdapter.executeAdd(table);
			}else{
				String new_exec_flag = batchConMap.get("exec_flag");
				batchConMap.put("pid",key);
				//记录操作开关
				if(!old_exec_flag.equals(new_exec_flag)){
					z_log+= operate_time + " " + operator + " " + "将批次开关从[" + old_exec_flag + "]改为[" + new_exec_flag +"]\r\n";
					batchConMap.put("z_log", z_log);
				}
				dr.setOptype(2);
				for(String k:batchConMap.keySet()){
					dr.put(k,batchConMap.get(k));
				}
				table.putDataRow(dr);
				dataAdapter.executeUpdate(table);
			}
			this.getRequest().setAttribute("result","success");
		return this.findForward("addChgBatchConfig");
	}
		
	//根据传的日期，查询最晚参与评审时间（评审会时间前推 2个工作日）
	public void getWorkDayJson(){
		//String resultDate = SlaExtUtil.getLastRevTimeWorkDay(intoStepTimeL);
		String resultDate = SlaExtUtil.getLastAcceptTimeWorkDay(intoStepTimeL);
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put("res", resultDate);
		String jsonStr = JSONArray.fromObject(jsonMap).toString();
		this.renderText(jsonStr);
	}
	
		
	/********************************变更批次信息配置 END   ******************************************/
	
	
	/********************************查询条件赋值功能Start***************************************************/
	private String ticket_type;
	private String jsonData;
	public String getTicket_type() {
		return ticket_type;
	}


	public void setTicket_type(String ticket_type) {
		this.ticket_type = ticket_type;
	}

	
	public String getJsonData() {
		return jsonData;
	}


	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}


	//存储查询条件
	public void saveConditions() throws UnsupportedEncodingException{
		//将传来的数据先转换成json格式
		Map map = this.getRequest().getParameterMap();
		String jsonStr = java.net.URLDecoder.decode(JSONArray.fromObject(map).toString(),"UTF-8");
		try {
			//数据入库
				String	querySql = "";
				String key = "";
				Map<String,String> queryConditionsMap = new HashMap<String, String>();
				String user_id = getUserSession().getPid();
				String condition_info="";
				querySql = "select * from BS_T_SM_QUERYCONDITIONS where user_id = '" + user_id + "' and ticket_type = '" + ticket_type + "'";
				query = new QueryAdapter();
				DataTable dt = query.executeQuery(querySql);
				if(dt != null && dt.length() == 1){
					key = dt.getDataRow(0).getString("pid");
				}
				dataAdapter = new DataAdapter();
				DataTable table = new DataTable("BS_T_SM_QUERYCONDITIONS");
				String[] keys = { "pid" };
				table.setPrimaryKey(keys);
				
				DataRow dr = new DataRow();
				if(key == ""){
					key = UUID.randomUUID().toString();
					condition_info=jsonStr;
					queryConditionsMap.put("pid",key);
					queryConditionsMap.put("user_id",user_id);
					queryConditionsMap.put("condition_info",condition_info);
					queryConditionsMap.put("ticket_type",ticket_type);
					dr.setOptype(1);
					for(String k:queryConditionsMap.keySet()){
						dr.put(k,queryConditionsMap.get(k));
					}
					table.putDataRow(dr);
					int i = dataAdapter.executeAdd(table);
				}else{
					condition_info=jsonStr;
					queryConditionsMap.put("pid",key);
					queryConditionsMap.put("condition_info",condition_info);
					dr.setOptype(2);
					for(String k:queryConditionsMap.keySet()){
						dr.put(k,queryConditionsMap.get(k));
					}
					table.putDataRow(dr);
					dataAdapter.executeUpdate(table);
				}
				this.getRequest().setAttribute("result","success");
				renderText("T");
		} catch (Exception e) {
			renderText("F");
			e.printStackTrace();
		}
			
	}
	
	public void setConditions(){
		String condition_info = "";
		if(StringUtils.isNotBlank(sqlName)){
			query = new QueryAdapter();
			DataTable dt = query.executeQuery(sqlName);
			if(dt != null && dt.length() == 1){
				condition_info = dt.getDataRow(0).getString("condition_info");
			}
			this.renderText(condition_info);
		}
	}
	
	
	/********************************查询条件赋值功能End***************************************************/
	
	/***********************************批次信息自动补全Start***********************************************/
	private String sqlQuery;// 配置的查询sql
	
	public String getSqlQuery() {
		return sqlQuery;
	}


	public void setSqlQuery(String sqlQuery) {
		this.sqlQuery = sqlQuery;
	}
	/**
	 * 批次信息自动补全
	 * @throws IOException
	 */
	public void autoSearchExOutHtml() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		String paramKey = java.net.URLDecoder.decode(ServletActionContext.getRequest().getParameter("q"),"UTF-8");
		if (paramKey == null || paramKey.trim().equals("") || sqlQuery == null
				|| sqlQuery.trim().equals("")) {
			return ;
		} else {
			paramKey = paramKey.toLowerCase();
		}
		HashMap<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("paramKey", paramKey);
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		RQuery rquery = new RQuery(sqlQuery, conditionMap);
		rquery.setPageSize(20);
		rquery.setPage(1);
		DataTable dt = rquery.getDataTable();
		for(Object o :dt.getRowList()){
			DataRow dr = (DataRow)o;
			Map rowmap = dr.getRowHashMap();
			Iterator i = rowmap.keySet().iterator();
			Map<String,Object> map = new HashMap<String, Object>();
			while(i.hasNext()){
				String key = (String) i.next();
				map.put(key.toLowerCase(), rowmap.get(key));
			}
			result.add(map);
		}
		
		for(int i=0;i<result.size();i++){
			StringBuilder sbHtml = new StringBuilder();
			Map map=new HashMap();
			map=result.get(i);
			sbHtml.append(map.get("info"));
			sbHtml.append("|");
			sbHtml.append(map.get("batch_no"));
			response.getWriter().println(sbHtml.toString());
		}
	}
	/***********************************批次信息自动补全End***********************************************/
	
	
	public HashMap getConMap() {
		return conMap;
	}

	public void setConMap(HashMap conMap) {
		this.conMap = conMap;
	}

	public String getSqlName() {
		return sqlName;
	}

	public void setSqlName(String sqlName) {
		this.sqlName = sqlName;
	}

	public String getConfigType() {
		return configType;
	}

	public void setConfigType(String configType) {
		this.configType = configType;
	}
	
	public String releaseChgList(){
		return SUCCESS;
	}
	
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}


	
	public Map<String, String> getUserMap() {
			return userMap;
	}

	public void setUserMap(Map<String, String> userMap) {
		this.userMap = userMap;
	}

	
	public String getBusSystem() {
		return busSystem;
	}

	public void setBusSystem(String busSystem) {
		this.busSystem = busSystem;
	}

	public String getChangeSort() {
		return changeSort;
	}

	public void setChangeSort(String changeSort) {
		this.changeSort = changeSort;
	}

	public void setNoticeViewLogAction(NoticeViewLogAction noticeViewLogAction) {
		this.noticeViewLogAction = noticeViewLogAction;
	}

	public Map<String, String> getBatchConMap() {
		return batchConMap;
	}

	public void setBatchConMap(Map<String, String> batchConMap) {
		this.batchConMap = batchConMap;
	}

	public Long getIntoStepTimeL() {
		return intoStepTimeL;
	}

	public void setIntoStepTimeL(Long intoStepTimeL) {
		this.intoStepTimeL = intoStepTimeL;
	}

}
