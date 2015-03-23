package com.ultrapower.eoms.workflow.sheet.query.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.dispatcher.mapper.ActionMapping;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;
import com.ultrapower.eoms.common.constants.DutyConstants;
import com.ultrapower.eoms.common.core.component.data.DataAdapter;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ultrasm.model.DealGroup;
import com.ultrapower.eoms.ultrasm.model.UserInfo;
import com.ultrapower.eoms.ultrasm.service.DealGroupService;
import com.ultrapower.eoms.workflow.design.control.DealProcessManager;
import com.ultrapower.eoms.workflow.sheet.query.service.IsheetQueryService;
import com.ultrapower.eoms.workflow.sort.web.WfSortTreeImpl;
import com.ultrapower.eoms.workflow.util.WorkflowUtils;
import com.ultrapower.remedy4j.core.RemedySession;
import com.ultrapower.workflow.bizconfig.sort.IWfSortManager;
import com.ultrapower.workflow.bizservice.model.Agency;
import com.ultrapower.workflow.client.WorkFlowServiceClient;
import com.ultrapower.workflow.configuration.sort.model.WfType;
import com.ultrapower.workflow.role.model.RoleUser;
import com.ultrapower.workflow.role.service.IRoleService;

/**
 * 工单查询（Web层action）
 * @version UltraPower-Eoms4.0-0.1
 * @author：liuchuanzu
 * @since jdk1.5
 * @date 2010-05-31 当前版本：v1.0 流程版本管理 web
 * Copyright (c) 2010 神州泰岳服务管理事业部产品组
 */
public class SheetQueryAction extends BaseAction {
	
	/**
	 * 流程分类管理
	 */
	protected WfSortTreeImpl wfSortTreeImpl;
	
	/** 
	 * 流程分类service对象
	 */
	protected IWfSortManager ver;
	
	
	protected DealGroupService dealGroupService;
	
	protected String basestatus; //工单状态

	protected String stepcode; //工单环节
	
	/**
	 * 流程分类ID
	 */
	protected String wfSortId;
	
	/**
	 * 标识，mycreated:我建立的工单，mydealed:我处理过的工单
	 */
	protected String type;
		
	/**
	 * 打开待办工单相应参数
	 * ${baseschema},${entryid},${baseid},${processtype}
	 */
	protected String baseSchema;
	protected String version;
	protected String taskId = "";
	protected String baseId;
	protected String processType;
	protected String infoId;
	protected String entryId;
	
	protected String createUserName;	
	protected String dealUserName;
	
	/**
	 * 查询baseinfo相应参数
	 */
	protected String orderby;
	
	/**
	 * 用户登录名
	 */
	protected UserSession userSessionInfo;
	
	
	protected long createStartTime;//建单时间(开始)
	protected long createEndTime;//建单时间(结束)
	
	protected String dealStartTime;//处理时间（开始）
	protected String dealEndTime;//处理时间（结束）
	
	
	protected long currenttime;
	
	protected String titleFlag;
    
	protected String username;
	protected String groupid;
	protected String roleid;
	
	protected String organizationId;//值班室ID
	protected String listTitle;//列表标题
	protected IRoleService roleService;
	
	protected IsheetQueryService sheetQueryServiceImpl;
	
	protected String queryname;
	protected String customizedPage;//自定义页面
	protected String formjsp;//自定义表单页面
	
	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getGroupid()
	{
		return groupid;
	}

	public void setGroupid(String groupid)
	{
		this.groupid = groupid;
	}

	public String getRoleid()
	{
		return roleid;
	}

	public void setRoleid(String roleid)
	{
		this.roleid = roleid;
	}

	public long getCurrenttime() {
		this.currenttime = TimeUtils.getCurrentTime();
		return currenttime;
	}

	public void setCurrenttime(long currenttime) {
		this.currenttime = currenttime;
	}

	
	/**
	 * 打开待办工单页面
	 * @param typelist
	 */
	public void openWaittingSheet(){
		if (WorkflowUtils.isARflow(baseSchema)) {
			String url = RemedySession.UtilInfor.getModifyUrl(baseSchema,null, baseId,taskId,processType) + "&loginname="+this.getUserSession().getLoginName();;
			try {
				this.getResponse().sendRedirect(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				String url = "../ultrabpp/view.action?baseSchema="+baseSchema+"&baseID="+baseId+"&taskID="+taskId+"&mode=MODIFY";
				if(customizedPage!=null) url+= "&customizedPage="+customizedPage;
				if(formjsp!=null) url+= "&formjsp="+formjsp;
				this.getResponse().sendRedirect(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 新建工单
	 * @param typelist
	 */
	public void createNewSheet(){
		String url = null;
		if (WorkflowUtils.isARflow(baseSchema)) {
			url = RemedySession.UtilInfor.getCreateUrl(baseSchema,null) + "&loginname="+this.getUserSession().getLoginName();
			
			//String url = "http://192.168.20.139:8010/arsys/forms/eoms4/" + baseSchema + "/?mode=CREATE&loginname="+this.getUserSession().getLoginName();
			try {
				this.getResponse().sendRedirect(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				this.getResponse().sendRedirect("../ultrabpp/view.action?mode=NEW&baseSchema="+baseSchema);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 打开流程图
	 */
	public String openFlowMap(){
		WfType wf = null;
		try {
			ver = WorkFlowServiceClient.clientInstance().getSortService();
			wf = ver.getWfTypeByCode(this.baseSchema);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		if (wf != null) {
			String type = "";
			if (wf.getWfType() == 0) {
				type = "free";
			} else if (wf.getWfType() == 1) {
				type = "tpl";
			}
			String url = RemedySession.UtilInfor.getFlowmapUrl(baseId,baseSchema, type, this.version, this.entryId);
			try {
				this.getResponse().sendRedirect(getRequest().getContextPath() + url);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		} else {
			this.getRequest().setAttribute("message", "流程定义不存在！");
			return "result";
		}
	}
	
	/**
	 * 打开已办工单页面
	 */
	public void openDealedSheet(){
		if (WorkflowUtils.isARflow(baseSchema)) {
			String url = RemedySession.UtilInfor.getModifyUrl(baseSchema,null,baseId) + "&loginname="+this.getUserSession().getLoginName();;
			try {
				this.getResponse().sendRedirect(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				this.getResponse().sendRedirect("../ultrabpp/view.action?baseSchema="+baseSchema+"&baseID="+baseId+"&mode=MODIFY");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 待办工单查询
	 */
	public String myWaitingDealSheetQuery(){
		long now = TimeUtils.getCurrentTime();
		
		this.titleFlag = "wf_sheet_mywaitingdeal";
		HashMap map = new HashMap();
		if(baseSchema!=null && !"".equals(baseSchema)){
			map.put("baseSchema", baseSchema);
		}

		//特定环节工单审批列表，暂时只针对变更发布的批量审批
		if(basestatus!=null && !"".equals(basestatus)){
			
			try {
				basestatus = new String(basestatus.getBytes("ISO-8859-1"),"utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			map.put("basestatus", basestatus);
		}

		if(stepcode!=null&& !"".equals(stepcode)){
			map.put("stepcode", stepcode);
			
		}
		
		if(this.wfSortId != null && !"".equals(this.wfSortId)){
			map.put("wfSortId", wfSortId);
			setWfTypeStr(wfSortId, map);
		}
		
		if(this.createStartTime > 0){
			map.put("createstarttime", createStartTime);
		}
		
		if(this.createEndTime > 0){
			map.put("createendtime", createEndTime);
		}
		
		map.put("now", now + "");
		setManagerGroupIdStr(map);
		setChildRoleIdStr(map);
		setManagerChildRoleIdStr(map);
		setAgencyStr(map);
		setDealGroup(map);
		
		this.getRequest().setAttribute("valuemap",map);
		
		if(StringUtils.isNotEmpty(customizedPage)){
			return this.findForward(customizedPage);
		}
		queryname = sheetQueryServiceImpl.getQuerySqlName("SQL_WF_WaitingDealSheet.query", baseSchema);
		return this.findForward("waitingDealSheet");
	}
	
	/**
	 * 值班已办工单查询
	 */
	public String dealedSheetQueryPlan(){
		HashMap map = new HashMap();
		
		if(this.wfSortId != null && !"".equals(this.wfSortId)){
			map.put("wfSortId", wfSortId);
			setWfTypeStr(wfSortId, map);
		}
		
		if(this.dealStartTime != null && !"".equals(this.dealStartTime)){
			map.put("dealstarttime", TimeUtils.formatDateStringToInt(this.dealStartTime,"yyyy-MM-dd HH:mm:ss"));
		}
		
		if(this.dealEndTime != null && !"".equals(this.dealEndTime)){
			map.put("dealendtime", TimeUtils.formatDateStringToInt(this.dealEndTime,"yyyy-MM-dd HH:mm:ss"));
		}
		
		map.put("baseSchema", baseSchema);
		map.put("username", username);
		
		this.getRequest().setAttribute("valuemap",map);
		
		return this.findForward("dealedSheetPlan");
	}
	
	
	/**
	 * 首页工单待办
	 * @return
	 */
	public String myWaitingDealSheetQueryShortcut(){
		
		long now = TimeUtils.getCurrentTime();
		
		HashMap map = new HashMap();
		if(baseSchema!=null && !"".equals(baseSchema)){
			map.put("baseSchema", baseSchema);
		}
		if(this.wfSortId != null && !"".equals(this.wfSortId)){
			map.put("wfSortId", wfSortId);
		}
		
		if(this.createStartTime > 0){
			map.put("createstarttime", createStartTime);
		}
		
		if(this.createEndTime > 0){
			map.put("createendtime", createEndTime);
		}
		map.put("now", now + "");
		setManagerGroupIdStr(map);
		setChildRoleIdStr(map);
		setManagerChildRoleIdStr(map);
		setAgencyStr(map);
		setDealGroup(map);
		
		
		this.getRequest().setAttribute("valuemap",map);
		
		return this.findForward("waitingDealSheetShortcut");
	}
	
	/**
	 * 查询已办工单
	 * @return
	 */
	public String myDealedSheetQuery(){
		HashMap map = new HashMap();
		String target = "dealedSheet";
		if (type != null && !"".equals(type)) {
			if(type.equals("mycreated")){
				target = "myCreatedSheet";
				this.titleFlag = "wf_sheet_mycreatedsheet";
				map.put("create_username",this.getUserSession().getLoginName());
			}else if(type.equals("mydealed")){
				this.titleFlag = "wf_sheet_mydealedsheet";
				map.put("deal_username",this.getUserSession().getLoginName());
			}
		}
		else
		{
			this.titleFlag = "wf_sheet_dealedsheet";
			type = "mydealed";
			//map.put("create_username",this.getUserSession().getLoginName());
		}
		
		if (this.dealUserName != null && !"".equals(this.dealUserName)) {
			map.put("deal_username", this.dealUserName);
		}

		if (this.createUserName != null && !"".equals(this.createUserName)) {
			map.put("create_username", this.createUserName);
		}
		
		
		if(baseSchema!=null && !"".equals(baseSchema)){
			map.put("baseSchema", baseSchema);
		}
		if(this.wfSortId != null && !"".equals(this.wfSortId)){
			map.put("wfSortId", wfSortId);
			setWfTypeStr(wfSortId, map);
		}
		
		if(this.createStartTime > 0){
			map.put("createstarttime", createStartTime);
		}
		
		if(this.createEndTime > 0){
			map.put("createendtime", createEndTime);
		}
		
		this.getRequest().setAttribute("valuemap",map);
		
		if(StringUtils.isNotEmpty(customizedPage)){
			return this.findForward(customizedPage);
		}
		
		if ("mydealed".equals(type)) {
			queryname = sheetQueryServiceImpl.getQuerySqlName("SQL_WF_DealedSheet.query", baseSchema);
		} else {
			queryname = sheetQueryServiceImpl.getQuerySqlName("SQL_WF_DealedSheet.myCreated", baseSchema);
		}
		return this.findForward(target);
	}
	
	
	/**
	 * baseinfo查询，可创建工单
	 * @return
	 */
	public String baseInfoQuery(){
		this.titleFlag = "wf_baseinfoview";
		HashMap map = (HashMap) this.getRequest().getAttribute("valuemap");
		if(map == null){
			map = new HashMap();
		}else{
			this.dateFormat(map);
		}
		
		if(orderby!=null && !"".equals(orderby)){
			orderby = "C700000006";//建单时间排序
		}
		if(this.wfSortId != null && !"".equals(this.wfSortId)){
			map.put("wfSortId", wfSortId);
			setWfTypeStr(wfSortId, map);
		}
		if(baseSchema!=null && !"".equals(baseSchema)){
			map.put("baseSchema", baseSchema);
		}
		map.put("orderby", orderby);
		this.getRequest().setAttribute("valuemap",map);
		
		if(StringUtils.isNotEmpty(customizedPage)){
			return this.findForward(customizedPage);
		}
		queryname = sheetQueryServiceImpl.getQuerySqlName("SQL_WF_BaseViewAll.query", baseSchema);
		return this.findForward("baseViewAll");
	
	}
	
	//逻辑同baseInfoQuery,只为首页取常用功能链接排除掉这些
	public String sheetQuery(){
		baseInfoQuery();
		return this.findForward(customizedPage);
	}
	
	
	/**
	 * netChange查询
	 * @return
	 */
	public String netChangeShutcut(){
		this.titleFlag = "wf_netchange_title";
		HashMap map = (HashMap) this.getRequest().getAttribute("valuemap");
		if(map == null){
			map = new HashMap();
		}else{
			this.dateFormat(map);
		}
		
		if(orderby!=null && !"".equals(orderby)){
			orderby = "C700000006";//建单时间排序
		}
		if(this.wfSortId != null && !"".equals(this.wfSortId)){
			map.put("wfSortId", wfSortId);
		}
		if(baseSchema!=null && !"".equals(baseSchema)){
			map.put("baseSchema", baseSchema);
		}
		map.put("orderby", orderby);
		this.getRequest().setAttribute("valuemap",map);
		return this.findForward("netChangeSheetShortcut");
	}
	
	
	/**
	 * netChange查询
	 * @return
	 */
	public String netChangeQuery(){
		this.titleFlag = "wf_netchange_title";
		HashMap map = (HashMap) this.getRequest().getAttribute("valuemap");
		if(map == null){
			map = new HashMap();
		}else{
			this.dateFormat(map);
		}
		
		if(orderby!=null && !"".equals(orderby)){
			orderby = "C700000006";//建单时间排序
		}
		if(this.wfSortId != null && !"".equals(this.wfSortId)){
			map.put("wfSortId", wfSortId);
		}
		if(baseSchema!=null && !"".equals(baseSchema)){
			map.put("baseSchema", baseSchema);
		}
		map.put("orderby", orderby);
		this.getRequest().setAttribute("valuemap",map);
		return this.findForward("netChangeSheet");
	}
	
	
	/**
	 * lph查询
	 * @return
	 */
	public String lphShutcut(){
		this.titleFlag = "wf_lph_title";
		HashMap map = (HashMap) this.getRequest().getAttribute("valuemap");
		if(map == null){
			map = new HashMap();
		}else{
			this.dateFormat(map);
		}
		
		if(orderby!=null && !"".equals(orderby)){
			orderby = "C700000006";//建单时间排序
		}
		if(this.wfSortId != null && !"".equals(this.wfSortId)){
			map.put("wfSortId", wfSortId);
		}
		if(baseSchema!=null && !"".equals(baseSchema)){
			map.put("baseSchema", baseSchema);
		}
		map.put("orderby", orderby);
		map.put("selectstatus", "未解决");
		this.getRequest().setAttribute("valuemap",map);
		return this.findForward("lphSheetShortcut");
	}
	
	
	/**
	 * 开始时间与结束时间格式的转换
	 * @return
	 */
	public void dateFormat(Map map){
		if(map.get("createstarttime")!=null){
			String cStartTime = map.get("createstarttime").toString();
			map.put("createstarttime",TimeUtils.formatDateStringToInt(cStartTime));
		}
		
		if(map.get("createendtime")!=null){
			String cEndTime = map.get("createendtime").toString();
			map.put("createendtime",TimeUtils.formatDateStringToInt(cEndTime));
		}
	}

	public WfSortTreeImpl getWfSortTreeImpl() {
		return wfSortTreeImpl;
	}

	public void setWfSortTreeImpl(WfSortTreeImpl wfSortTreeImpl) {
		this.wfSortTreeImpl = wfSortTreeImpl;
	}
	
	public String getWfSortId() {
		return wfSortId;
	}



	public void setWfSortId(String wfSortId) {
		this.wfSortId = wfSortId;
	}



	public String getBaseSchema() {
		return baseSchema;
	}



	public void setBaseSchema(String baseSchema) {
		this.baseSchema = baseSchema;
	}



	public long getCreateStartTime() {
		return createStartTime;
	}

	public void setCreateStartTime(long createStartTime) {
		this.createStartTime = createStartTime;
	}

	public long getCreateEndTime() {
		return createEndTime;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getDealUserName() {
		return dealUserName;
	}

	public void setDealUserName(String dealUserName) {
		this.dealUserName = dealUserName;
	}

	public void setCreateEndTime(long createEndTime) {
		this.createEndTime = createEndTime;
	}
	
	
	public String getBasestatus() {
	return basestatus;
	}
	
	public void setBasestatus(String basestatus) {
	this.basestatus = basestatus;
	}
	
	
	public String getStepcode() {
		return stepcode;
	}

	public void setStepcode(String stepcode) {
		this.stepcode = stepcode;
	}


	public String getBaseId() {
		return baseId;
	}



	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}



	public String getProcessType() {
		return processType;
	}



	public void setProcessType(String processType) {
		this.processType = processType;
	}



	public UserSession getUserSessionInfo() {
		return userSessionInfo;
	}



	public void setUserSessionInfo(UserSession userSessionInfo) {
		this.userSessionInfo = userSessionInfo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

	public String getTitleFlag() {
		return titleFlag;
	}

	public void setTitleFlag(String titleFlag) {
		this.titleFlag = titleFlag;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDealStartTime() {
		return dealStartTime;
	}

	public void setDealStartTime(String dealStartTime) {
		this.dealStartTime = dealStartTime;
	}

	public String getDealEndTime() {
		return dealEndTime;
	}

	public void setDealEndTime(String dealEndTime) {
		this.dealEndTime = dealEndTime;
	}
	
	
	
	//原产品方法，国开项目组修改
	public String getProcessLogXML_bak() {
		try {
			DealProcessManager dpManager = new DealProcessManager();
			List<String[]> logList = dpManager.getProcessLogs(baseId, baseSchema); 
//			ServletOutputStream out = getResponse().getOutputStream();
			PrintWriter out = getResponse().getWriter();
			getResponse().setContentType("text/xml;charset=UTF-8");
			out.print("<logs>");
			for(Iterator<String[]> logit = logList.iterator(); logit.hasNext();)
			{
				out.print("<log>");
				String[] log = logit.next();
				out.print("<time>");
				out.print(TimeUtils.formatIntToDateString(Long.parseLong(log[0])));
				out.print("</time>");
				out.print("<user>");
				out.print(log[1]);
				out.print("</user>");
				out.print("<action>");
				out.print(log[2]);
				out.print(" "+log[3]);
				out.print("</action>");
				out.print("</log>");
			}
			out.print("</logs>");
			out.flush();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getProcessLogXML() {
		try {
			DealProcessManager dpManager = new DealProcessManager();
			List<String[]> logList = dpManager.getProcessLogs(baseId, baseSchema); 
			
			String sql = "select currentuser,dealaction,dealtime,nextdealuser from bs_t_wf_record where baseschema = '" + baseSchema + "' and baseid = '" + baseId + "' and isview = 1 order by dealtime";
			QueryAdapter query = new QueryAdapter();
			DataTable dt = query.executeQuery(sql);
			
//			ServletOutputStream out = getResponse().getOutputStream();
			PrintWriter out = getResponse().getWriter();
			getResponse().setContentType("text/xml;charset=UTF-8");
			out.print("<logs>");
			if(dt != null && dt.length() > 0){
				for(Object obj:dt.getRowList())
				{
					DataRow dr = (DataRow)obj;
					out.print("<log>");
					out.print("<time>");
					out.print(TimeUtils.formatIntToDateString(dr.getLong("dealtime")));
					out.print("</time>");
					out.print("<user>");
					out.print(dr.getString("currentuser"));
					out.print("</user>");
					out.print("<action>");
					out.print("        "+dr.getString("dealaction"));
					//out.print("   "+dr.getString("nextdealuser"));
					out.print("</action>");
					out.print("</log>");
				}
			}
			out.print("</logs>");
			out.flush();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	protected void setWfTypeStr(String wfSortId, Map map) {
		UserSession userSession = getUserSession();
		List<WfType> wfTypes = userSession.getWfTypes();
		if (CollectionUtils.isNotEmpty(wfTypes) && StringUtils.isNotBlank(wfSortId)) {
			String wfTypeStr = "";
			for (int i = 0; i < wfTypes.size(); i++) {
				WfType wfType = wfTypes.get(i);
				String sortId = wfType.getSortId();
				String code = wfType.getCode();
				if (wfSortId.equals(sortId)) {
					wfTypeStr += code + ",";
				}
			}
			if (StringUtils.isNotBlank(wfTypeStr)) {
				wfTypeStr = wfTypeStr.substring(0, wfTypeStr.length() - 1);
				map.put("wfTypeStr", wfTypeStr);
			}
		}
	}
	
	protected void setAgencyStr(Map map) {
		UserSession userSession = getUserSession();
		long now = TimeUtils.getCurrentTime();
		List<Agency> agencys = userSession.getAgencys();
		if (CollectionUtils.isNotEmpty(agencys)) {
			String dealIdStr = "";
			for (int i = 0; i < agencys.size(); i++) {
				Agency agency = agencys.get(i);
				String dealId = agency.getDealerId();
				Long bgDate = agency.getBgDate();
				Long edDate = agency.getEdDate();
				if (bgDate != null && edDate != null && now > bgDate && now < edDate) {
					dealIdStr += dealId + ",";
				}
			}
			if (StringUtils.isNotBlank(dealIdStr)) {
				dealIdStr = dealIdStr.substring(0, dealIdStr.length() - 1);
				map.put("dealIdStr", dealIdStr);
			}
		}
	}
	
	/**
	 * 设置同组操作
	 * @param map
	 */
	protected void setDealGroup(Map map) {
		StringBuffer sb = new StringBuffer();
		UserSession userSession = getUserSession();
		String userLoginName = userSession.getLoginName();
		List<DealGroup> dealGroups= dealGroupService.getDealGroupByUser(userLoginName);
		if (CollectionUtils.isNotEmpty(dealGroups)) {
			sb.append("(");
			for (int i = 0; i < dealGroups.size(); i++) {
				DealGroup dealGroup = dealGroups.get(i);
				String entryState = dealGroup.getEntryState();
				List<String> stateList = new ArrayList<String>();
				if (StringUtils.isNotBlank(entryState)) {
					String[] states = entryState.split(";");
					if (!ArrayUtils.isEmpty(states)) {
						for (int j = 0; j < states.length; j++) {
							if (StringUtils.isNotBlank(states[j])) {
								stateList.add(states[j]);
							}
						}
					}
				}
				List<UserInfo> users = dealGroupService.getDealGroupUsers(dealGroup);
				if (CollectionUtils.isNotEmpty(users)) {
					for (int j = 0; j < users.size(); j++) {
						UserInfo user = users.get(j);
						String loginname = user.getLoginname();
						sb.append("(");
						sb.append("(assigneeid='"+loginname+"' or dealerid='"+loginname+"') and (");
						if (CollectionUtils.isNotEmpty(stateList)) {
							for (int k = 0; k < stateList.size(); k++) {
								String state = stateList.get(k);
								sb.append("entrystate='"+state+"'");
								if (k != (stateList.size() -1)) {
									sb.append(" or ");
								}
							}
						} else {
							sb.append(" 1=1 ");
						}
						sb.append(")");
						sb.append(")");
						if (j != (users.size() -1)) {
							sb.append(" or ");
						}
					}
				} 
				if (i != dealGroups.size() - 1) {
					sb.append(" or ");
				}
			}
			sb.append(")");
		}
		String dealGroupSql = sb.toString();
		if (StringUtils.isNotBlank(dealGroupSql)) {
			map.put("dealGroupSql", dealGroupSql);
		} else {
			map.put("dealGroupSql", "");
		}
		log.info("同组操作where条件：" + map.get("dealGroupSql"));
	}
	
	public static void main(String[] args) {
		StringBuffer sb = new StringBuffer();
		List<UserInfo> users = new ArrayList<UserInfo>();
		UserInfo u = new UserInfo();
		u.setLoginname("huangwei");
		users.add(u);
		UserInfo u1 = new UserInfo();
		u1.setLoginname("lihaoyuan");
		users.add(u1);
//		String entryState = "已完成;待归档;";
		List<DealGroup> dealGroups = new ArrayList<DealGroup>();
		DealGroup dg = new DealGroup();
		dg.setEntryState("已完成;");
		dealGroups.add(dg);
//		DealGroup dg1 = new DealGroup();
//		dg1.setEntryState("待归档;");
//		dealGroups.add(dg1);
		
		if (CollectionUtils.isNotEmpty(dealGroups)) {
			sb.append("(");
			for (int i = 0; i < dealGroups.size(); i++) {
				DealGroup dealGroup = dealGroups.get(i);
				String entryState = dealGroup.getEntryState();
				List<String> stateList = new ArrayList<String>();
				if (StringUtils.isNotBlank(entryState)) {
					String[] states = entryState.split(";");
					if (!ArrayUtils.isEmpty(states)) {
						for (int j = 0; j < states.length; j++) {
							if (StringUtils.isNotBlank(states[j])) {
								stateList.add(states[j]);
							}
						}
					}
				}
				if (CollectionUtils.isNotEmpty(users)) {
					for (int j = 0; j < users.size(); j++) {
						UserInfo user = users.get(j);
						String loginname = user.getLoginname();
						sb.append("(");
						sb.append("(assigneeid='"+loginname+"' or dealerid='"+loginname+"') and (");
						if (CollectionUtils.isNotEmpty(stateList)) {
							for (int k = 0; k < stateList.size(); k++) {
								String state = stateList.get(k);
								sb.append("entrystate='"+state+"'");
								if (k != (stateList.size() -1)) {
									sb.append(" or ");
								}
							}
						} else {
							sb.append(" 1=1 ");
						}
						sb.append(")");
						sb.append(")");
						if (j != (users.size() -1)) {
							sb.append(" or ");
						}
					}
				} 
				if (i != dealGroups.size() - 1) {
					sb.append(" or ");
				}
			}
			sb.append(")");
		}
		
		System.out.println(sb.toString());
	}
	
	protected void setManagerChildRoleIdStr(Map map) {
		UserSession userSession = getUserSession();
		String managerChildRoleIds = userSession.getManagerChildRoleIds();
		if (StringUtils.isNotBlank(managerChildRoleIds)) {
			map.put("managerChildRoleIdsStr", managerChildRoleIds);
		}
	}
	
	protected void setManagerGroupIdStr(Map map) {
		UserSession userSession = getUserSession();
		String managerGroupId = userSession.getManagerGroupId();
		if (StringUtils.isNotBlank(managerGroupId)) {
			map.put("managerGroupIdStr", managerGroupId);
		}
	}
	
	protected void setChildRoleIdStr(Map map) {
		UserSession userSession = getUserSession();
		String childRoleIds = userSession.getChildRoleIds();
		if (StringUtils.isNotBlank(childRoleIds)) {
			map.put("childRoleIdsStr", childRoleIds);
		}
		
		//加入获取代理人的原处理人的角色细分
		List<Agency> agencys = userSession.getAgencys();
		if (CollectionUtils.isNotEmpty(agencys)) {
			String dealChildRoleIDStr = "";
			long now = TimeUtils.getCurrentTime();
			for (int i = 0; i < agencys.size(); i++) {
				Agency agency = agencys.get(i);
				String dealId = agency.getDealerId();
				Long bgDate = agency.getBgDate();
				Long edDate = agency.getEdDate();
				if (bgDate != null && edDate != null && now > bgDate && now < edDate) {
					String hql = "from RoleUser where loginName = '" + dealId + "'";
					List<RoleUser> roleUsers = roleService.getRoleUserByHql(hql);
					for(RoleUser rUser : roleUsers)
					{
						dealChildRoleIDStr += "," + rUser.getChildRoleId(); 
					}
				}
			}
			if (StringUtils.isNotBlank(dealChildRoleIDStr)) {
				map.put("childRoleIdsStr", childRoleIds + dealChildRoleIDStr);
			}
		}
	}
	
	public String getListTitle() {
		return listTitle;
	}

	public void setListTitle(String listTitle) {
		this.listTitle = listTitle;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public IsheetQueryService getSheetQueryServiceImpl()
	{
		return sheetQueryServiceImpl;
	}

	public void setSheetQueryServiceImpl(IsheetQueryService sheetQueryServiceImpl)
	{
		this.sheetQueryServiceImpl = sheetQueryServiceImpl;
	}

	public String getQueryname()
	{
		return queryname;
	}

	public void setQueryname(String queryname)
	{
		this.queryname = queryname;
	}

	public DealGroupService getDealGroupService() {
		return dealGroupService;
	}

	public void setDealGroupService(DealGroupService dealGroupService) {
		this.dealGroupService = dealGroupService;
	}

	public String getEntryId() {
		return entryId;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}

	public IRoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}

	public String getCustomizedPage() {
		return customizedPage;
	}

	public void setCustomizedPage(String customizedPage) {
		this.customizedPage = customizedPage;
	}

	public String getFormjsp() {
		return formjsp;
	}

	public void setFormjsp(String formjsp) {
		this.formjsp = formjsp;
	}

}

