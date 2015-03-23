package com.ultrapower.eoms.msextend.serverQuest.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.ultrapower.eoms.common.core.component.data.DataAdapter;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.component.tree.manager.TreeManager;
import com.ultrapower.eoms.common.core.component.tree.model.DtreeBean;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.util.UUIDGenerator;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.msextend.serverQuest.manager.CreatTreeImpl;
import com.ultrapower.eoms.msextend.serverQuest.model.ServerQuestModel;
import com.ultrapower.eoms.msextend.serverQuest.service.ServerQuestService;
import com.ultrapower.eoms.ultrasm.model.MenuInfo;
import com.ultrapower.eoms.ultrasm.service.MenuManagerService;

public class ServerQuestAction extends BaseAction{
	private static final long serialVersionUID = -5312087180184412618L;
	private static final String MARKNEW = "sheet_CDB_WF";//新meanutree节点标示
	private static final String MARKOLD = "sheet_CDB_WF";//旧meanutree节点标示

	private ServerQuestService serverQuestService;
	private MenuManagerService menuManagerService;
	private ServerQuestModel serverQuestModel;
	private String id;
	private String serverQuestId;
	private ServerQuestModel parentServerQuestModel;
	private String message;
	
	private String baseSchema;
	private String levertype;
	private String dilever;
	private String parentid;
	
	private String attamentid;
	
	private List<String> aduitIdList;
	private List aduitidNameList;
	private String aduitid1;
	private String aduitName1;
	
	private String fullname;
	private String forwho;//0对内1对外
	
	private String serverquestfullname;
	
	public void isSendAudit(){
		renderText(serverQuestService.isSendAudit(fullname));
	}
	
	/**
	 * 获得常用服务请求,返回JSON
	 */
	public void getCommonServerQuest(){
		HashMap<String, String> parameter = new HashMap<String, String>();
		parameter.put("isCommon", "1");
		if(StringUtils.equals(forwho, "1")) parameter.put("forwho", "对外");
		parameter.put("releaseScope", WebApplicationManager.getUserSession().getGroupDepNames());
		renderText(JSONArray.fromObject(serverQuestService.getCommonServerQuest(parameter)).toString());
	}
	
	/**
	 * 根据fullname获得服务目录,返回json
	 */
	public void getServerQuestByFullname(){
		ServerQuestModel s = serverQuestService.getServerQuestByFullname(fullname);
		renderText(JSONObject.fromObject(s).toString());
	}
	
	/**/
	public void serverQuestInfo(){
		if(levertype!=null&&!"".equals(levertype)){
			Map<String,String> cdnMap=getparentCdnOrCdns(parentid);
			int maxcdn=Integer.parseInt(getMaxCdn(parentid))+1;
			int lever1=Integer.parseInt(cdnMap.get("dilever")==null?"1":cdnMap.get("dilever"));
			serverQuestModel.setServerquestdn(maxcdn+"");
			if("0".equals(parentid)){
				lever1=1;
			}else{
				lever1=lever1+1;
			}
			
			if("lower".equals(levertype)){//下级增加
				serverQuestModel.setServerquestdns(cdnMap.get("SERVERQUESTDNS")+"."+maxcdn);
				serverQuestModel.setServerquestfullname((cdnMap.get("SERVERQUESTFULLNAME")+"."+serverQuestModel.getServerquestname()));
			}else{
				if("0".equals(parentid)){
					serverQuestModel.setServerquestdns(maxcdn+"");
					serverQuestModel.setServerquestfullname(serverQuestModel.getServerquestname()+"");
				}else{
					String cdns=cdnMap.get("SERVERQUESTDNS");
					if(cdns.indexOf(".")!=-1){
						if(lever1!=3){
							cdns=cdns.substring(0, cdns.lastIndexOf("."));
						}
						serverQuestModel.setServerquestdns(cdns+"."+maxcdn);
					}else{
						if(lever1==2){
							serverQuestModel.setServerquestdns(cdns+"."+maxcdn);
						}else{
							serverQuestModel.setServerquestdns(maxcdn+"");
						}
					}
					String dicfullname=cdnMap.get("SERVERQUESTFULLNAME");
					if(dicfullname.indexOf(".")!=-1){
						if(lever1!=3){
							dicfullname=dicfullname.substring(0, dicfullname.lastIndexOf("."));
						}
						serverQuestModel.setServerquestfullname(dicfullname+"."+serverQuestModel.getServerquestname());
						
					}else{
						if(lever1==2){
							serverQuestModel.setServerquestfullname(dicfullname+"."+serverQuestModel.getServerquestname());
						}else{
						 serverQuestModel.setServerquestfullname(serverQuestModel.getServerquestname());
						}
					}
				}
			}
			serverQuestModel.setDilever(lever1);
		}else{
			String fullname=serverQuestModel.getServerquestfullname();
			if(fullname.indexOf(".")!=-1){
				fullname=fullname.substring(0, fullname.lastIndexOf("."));
				serverQuestModel.setServerquestfullname(fullname+"."+serverQuestModel.getServerquestname());
			}else{
				serverQuestModel.setServerquestfullname(serverQuestModel.getServerquestname());
			}
		}
		serverQuestModel.setCreater(this.getUserSession().getLoginName());
		serverQuestModel.setCreatetime(TimeUtils.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
		//将菜单信息同时更新到meantree里面。
		
		if(serverQuestModel!=null&&serverQuestModel.getPid()!=null&&!"".equals(serverQuestModel.getPid())){///更新
			
			 MenuInfo newMenu_tree_newP = menuManagerService.getMenuByMark(MARKNEW);
			 if("0".equals(serverQuestModel.getParentid())&&newMenu_tree_newP!=null){
				 MenuInfo oldMenu_tree = menuManagerService.getMenuByMark(MARKOLD+"_"+serverQuestModel.getServerquestdns());
				 MenuInfo newMenu_tree = menuManagerService.getMenuByMark(MARKNEW+"_"+serverQuestModel.getServerquestdns());
				if(oldMenu_tree!=null&&oldMenu_tree.getPid()!=null){//第一次更新
					oldMenu_tree.setOrdernum(serverQuestModel.getOrdernum().longValue());//排序
					oldMenu_tree.setNodemark(MARKNEW+"_"+serverQuestModel.getServerquestdns());//新建的节点标识：sheet_CDB_SERVICEREQUEST_1.1
					oldMenu_tree.setNodename(serverQuestModel.getServerquestname());//服务目录名称
					oldMenu_tree.setStatus(Long.valueOf(serverQuestModel.getStatus()));
					oldMenu_tree.setParentid(newMenu_tree_newP.getPid());
					menuManagerService.updateMenuInfo(oldMenu_tree);
				}
				if(newMenu_tree!=null&&newMenu_tree.getPid()!=null){//再次更新
					newMenu_tree.setOrdernum(serverQuestModel.getOrdernum().longValue());//排序
					newMenu_tree.setNodename(serverQuestModel.getServerquestname());//服务目录名称
					newMenu_tree.setStatus(Long.valueOf(serverQuestModel.getStatus()));
					menuManagerService.updateMenuInfo(newMenu_tree);
				}
				if(oldMenu_tree==null&&newMenu_tree==null){//如果目录树中不存在则增加
					addMenutree();
				}
			}else{
				 MenuInfo newMenu_tree = menuManagerService.getMenuByMark(serverQuestModel.getServerquestdns());
				 if(newMenu_tree!=null&&newMenu_tree.getPid()!=null){//更新子节点
						newMenu_tree.setOrdernum(serverQuestModel.getOrdernum().longValue());//排序
						newMenu_tree.setNodename(serverQuestModel.getServerquestname());//服务目录名称
						newMenu_tree.setStatus(Long.valueOf(serverQuestModel.getStatus()));
						menuManagerService.updateMenuInfo(newMenu_tree);
					}else{ //如果目录树中不存在则增加
						addMenutree();
					}
			}
			if(StringUtils.isEmpty(serverQuestModel.getParentid())){
				serverQuestModel.setParentid("0");
			}
			serverQuestService.saveServerQuest(serverQuestModel);
		}else{//新增
			serverQuestModel.setParentid(parentid);
			serverQuestService.saveServerQuest(serverQuestModel);
			addMenutree();
		}
		
		if(serverQuestModel.getParentid().equals("0") || StringUtils.isEmpty(serverQuestModel.getParentid())){
			updateFullName(serverQuestModel.getPid(), serverQuestModel.getServerquestname());
		}else{
			parentServerQuestModel=serverQuestService.getServerQuestById(serverQuestModel.getParentid());
			updateFullName(serverQuestModel.getPid(), parentServerQuestModel.getServerquestfullname()+"."+serverQuestModel.getServerquestname());
		}
	}
	
	public void addMenutree(){
		 MenuInfo newMenu=new MenuInfo();
		 newMenu.setOrdernum(serverQuestModel.getOrdernum().longValue());//排序
		 newMenu.setNodeurl("sheet/baseInfoQuery.action?baseSchema=CDB_SERVICEREQUEST&&valuemap.requestType="+serverQuestModel.getPid());
		 newMenu.setNodename(serverQuestModel.getServerquestname());//服务目录名称
		 newMenu.setStatus(Long.valueOf(serverQuestModel.getStatus()));
		 MenuInfo newMenu_tree = menuManagerService.getMenuByMark(MARKNEW);
		if("0".equals(serverQuestModel.getParentid())){
			if(newMenu_tree!=null&&newMenu_tree.getPid()!=null){
				 newMenu.setParentid(newMenu_tree.getPid());//父节点ID
				 newMenu.setNodemark(MARKNEW+"_"+serverQuestModel.getServerquestdns());//新建的节点标识：sheet_CDB_SERVICEREQUEST_1.1
				 menuManagerService.addMenuInfo(newMenu);
			}	
		}else{
			 ServerQuestModel sqm=new ServerQuestModel();
			 sqm=serverQuestService.getServerQuestById(serverQuestModel.getParentid());
			 newMenu.setNodemark(serverQuestModel.getServerquestdns());//新建的节点标识：sheet_CDB_SERVICEREQUEST_1.1
			 MenuInfo newMenu_tree_P = menuManagerService.getMenuByMark(sqm.getServerquestdns());
			 if("0".equals(sqm.getParentid())){
				 newMenu_tree_P= menuManagerService.getMenuByMark(MARKNEW+"_"+sqm.getServerquestdns());
			 }
			 if(newMenu_tree_P!=null&&newMenu_tree_P.getPid()!=null){
				 newMenu.setParentid(newMenu_tree_P.getPid());//父节点ID
				 menuManagerService.addMenuInfo(newMenu);
			 }
		}
		
	}
	
	public String  getMaxCdn(String parentid){
		QueryAdapter queryAdapter = new QueryAdapter();
		StringBuffer sql = new StringBuffer();
		sql.append("select Max(to_number(t.SERVERQUESTDN)) MAXDICDN");
		sql.append("  from BS_T_WF_SERVERQUEST t");
		sql.append(" where  t.parentid = '"+parentid+"'");
		
		DataTable datatable = queryAdapter.executeQuery(sql.toString(), null);
		int datatableLen = 0;
		if(datatable!=null)
			datatableLen = datatable.length();
		DataRow dataRow;
		String maxdic="0";
		for(int row=0;row<datatableLen;row++){
			dataRow = datatable.getDataRow(row);
			maxdic= dataRow.getString("MAXDICDN");
		}
		return maxdic==""?"0":maxdic;
	}
	
	public Map<String,String > getparentCdnOrCdns(String parentid){
		Map<String,String > map = new HashMap<String,String >();
		if(parentid!=null&&"0".equals(parentid)){
			String cdn= getMaxCdn(parentid);
			map.put("SERVERQUESTDN", cdn);
			map.put("SERVERQUESTDNS", cdn);
			map.put("dilever","1");
			return map;
		}
		QueryAdapter queryAdapter = new QueryAdapter();
		StringBuffer sql = new StringBuffer();
		sql.append("select t.pid,t.parentid,t.dilever,t.serverquestdn,t.serverquestdns,t.SERVERQUESTFULLNAME   ");
		sql.append("  from BS_T_WF_SERVERQUEST t");
		sql.append(" where  t.pid = '"+parentid+"'");
		DataTable datatable = queryAdapter.executeQuery(sql.toString(), null);
		int datatableLen = 0;
		if(datatable!=null)
			datatableLen = datatable.length();
		DataRow dataRow;
		
		for(int row=0;row<datatableLen;row++){
			dataRow = datatable.getDataRow(row);
			map.put("SERVERQUESTDN", dataRow.getString("serverquestdn"));
			map.put("SERVERQUESTDNS", dataRow.getString("serverquestdns"));
			map.put("SERVERQUESTFULLNAME", dataRow.getString("SERVERQUESTFULLNAME"));
			map.put("dilever", dataRow.getString("dilever"));
		}
		return map;
	}
	
	public String addserverQuestInfo(){
		ServerQuestModel serverQuestModel1=serverQuestService.getServerQuestById(id);
		if("lower".equals(levertype)){
			dilever=(serverQuestModel1.getDilever()+1)+"";
			parentid=serverQuestModel1.getPid();
			parentServerQuestModel=serverQuestService.getServerQuestById(serverQuestModel1.getPid());
		}else{
			dilever=serverQuestModel1.getDilever()+"";
			parentid=serverQuestModel1.getParentid();
			parentServerQuestModel=serverQuestService.getServerQuestById(serverQuestModel1.getParentid());
		}
		if(levertype==null||"".equals(levertype)){
			serverQuestModel=serverQuestModel1;
			
			String audit_id=serverQuestModel.getAudit_id();
			String[]  audit_ids=null;
			if(audit_id!=null){
				audit_ids=audit_id.split("#;");
				aduitIdList=new ArrayList();
				for (int i = 0; i < audit_ids.length; i++) {
					aduitid1=audit_ids[0];
					aduitIdList.add(audit_ids[i]);
				}
			}
			String audit_name=serverQuestModel.getAudit_name();
			String[]  audit_names=null;
			if(audit_name!=null){
				audit_names=audit_name.split("#;");
				aduitidNameList=new ArrayList();
				for (int i = 0; i < audit_names.length; i++) {
					aduitName1=audit_names[0];
					Map map=new HashMap();
					String ids="";
					try {
						ids=aduitIdList.get(i);
					} catch (Exception e) {
						ids="00";
					}
					map.put(ids, audit_names[i]);
					aduitidNameList.add(map);
				}
			}
		}else{
			attamentid=UUIDGenerator.getUUIDoffSpace();
		}
		
		if(serverQuestModel == null){
			serverQuestModel = new ServerQuestModel();
			serverQuestModel.setParent(parentServerQuestModel);
		}
		
		return SUCCESS;
	}
	
	
	public void delServerQuest(){
		serverQuestModel=serverQuestService.getServerQuestById(id);
		parentServerQuestModel=serverQuestService.getServerQuestById(serverQuestModel.getParentid());
		//删除菜单树
		if("0".equals(serverQuestModel.getParentid())){
			 Boolean be= menuManagerService.deleteMenuByMark(MARKNEW+"_"+serverQuestModel.getServerquestdns());
			 if(!be){
				 menuManagerService.deleteMenuByMark(MARKOLD+"_"+serverQuestModel.getServerquestdns());
			 }
		}else{
			 menuManagerService.deleteMenuByMark(serverQuestModel.getServerquestdns());
		}
		serverQuestService.delServerQuest(id);
		message=parentServerQuestModel.getPid();
		if(message==null){
			message="0";
		}
		
		this.renderText(message);
	}
	
	public String serverQuestInfoList(){
		return SUCCESS;
	}
	
	/**
	 * 更新本节点下所有的节点fullname
	 */
	private void updateFullName(String id,String fullname){
		DataAdapter dataAdapter = new DataAdapter();
		if(fullname!=null){
			dataAdapter.execute("update Bs_t_Wf_Serverquest t set t.serverquestfullname = ? where pid = ?",new Object[]{fullname,id});
		}
		String sql = "select pid,serverquestname,serverquestfullname from Bs_t_Wf_Serverquest where parentid = '" + id + "'";
		String updateSql = "update Bs_t_Wf_Serverquest t set t.serverquestfullname = ? where pid = ?";
		QueryAdapter query = new QueryAdapter();
		DataTable dt = query.executeQuery(sql);
		for(Object obj:dt.getRowList()){
			DataRow dr = (DataRow)obj;
			String tmpId = dr.getString("pid");
			String tmpName = dr.getString("serverquestname");
			String tmpFullName = fullname + "." + tmpName; 
			Object[] values = {tmpFullName,tmpId};
			dataAdapter.execute(updateSql, values);
			this.updateFullName(tmpId,tmpFullName);
		}
	}
	
	/*
	 * 查询服务分类ID
	 * */
	public void  getServerquestPID(){
		String PID="";
		QueryAdapter query = new QueryAdapter();
		if(StringUtils.isNotBlank(serverquestfullname)){
			String querySql = "select * from BS_T_WF_SERVERQUEST where  serverquestfullname = '" + serverquestfullname+"'" ;
			DataTable dt = query.executeQuery(querySql);
			if(dt!=null&&dt.length()>0){
				 PID =  dt.getDataRow(0).getString("PID");
			}
			
		}
		renderText(PID);
	} 
	

	public void getXML(){
		String xml="";
		//xml=serverQuestService.getHelpXml(id);
		//this.renderXML(xml);
		CreatTreeImpl creatTreeImpl = new CreatTreeImpl();
		this.renderXML(creatTreeImpl.getChildXml(id));
	}
	
	//获取可用的服务请求信息，status = 1
	public void getEnableXML(){
		CreatTreeImpl creatTreeImpl = new CreatTreeImpl();
		this.renderXML(creatTreeImpl.getChildXmlByStatus(id,"1"));
	}
	
	//获取服务目录树的XML
	public void getServiceCategoryTreeXML(){
		CreatTreeImpl creatTreeImpl = new CreatTreeImpl();
		HashMap<String, String> parameter = new HashMap<String, String>();
		if(forwho!=null && forwho.equals("1"))
			parameter.put("forwho", "对外");
		parameter.put("releaseScope", getUserSession().getGroupDepNames());
		renderXML(creatTreeImpl.getServiceCategoryTreeXML(parameter));
	}
	
	public String   getServerQuestInfo(){
		serverQuestModel=serverQuestService.getServerQuestById(id);
		parentServerQuestModel=serverQuestService.getPranetCriterInfo(serverQuestModel);	
		return SUCCESS;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public String getBaseSchema() {
		return baseSchema;
	}

	public void setBaseSchema(String baseSchema) {
		this.baseSchema = baseSchema;
	}

	public ServerQuestService getServerQuestService() {
		return serverQuestService;
	}

	public void setServerQuestService(ServerQuestService serverQuestService) {
		this.serverQuestService = serverQuestService;
	}

	public ServerQuestModel getServerQuestModel() {
		return serverQuestModel;
	}

	public void setServerQuestModel(ServerQuestModel serverQuestModel) {
		this.serverQuestModel = serverQuestModel;
	}

	public ServerQuestModel getParentServerQuestModel() {
		return parentServerQuestModel;
	}

	public void setParentServerQuestModel(ServerQuestModel parentServerQuestModel) {
		this.parentServerQuestModel = parentServerQuestModel;
	}



	public String getDilever() {
		return dilever;
	}



	public void setDilever(String dilever) {
		this.dilever = dilever;
	}

	public String getLevertype() {
		return levertype;
	}

	public void setLevertype(String levertype) {
		this.levertype = levertype;
	}


	public String getParentid() {
		return parentid;
	}


	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public String getAttamentid() {
		return attamentid;
	}
	public void setAttamentid(String attamentid) {
		this.attamentid = attamentid;
	}
	public MenuManagerService getMenuManagerService() {
		return menuManagerService;
	}
	public void setMenuManagerService(MenuManagerService menuManagerService) {
		this.menuManagerService = menuManagerService;
	}
	public List<String> getAduitIdList() {
		return aduitIdList;
	}
	public String getAduitid1() {
		return aduitid1;
	}
	public void setAduitid1(String aduitid1) {
		this.aduitid1 = aduitid1;
	}
	public String getAduitName1() {
		return aduitName1;
	}
	public void setAduitName1(String aduitName1) {
		this.aduitName1 = aduitName1;
	}
	public List getAduitidNameList() {
		return aduitidNameList;
	}
	public void setAduitidNameList(List aduitidNameList) {
		this.aduitidNameList = aduitidNameList;
	}
	public void setAduitIdList(List<String> aduitIdList) {
		this.aduitIdList = aduitIdList;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getServerQuestId() {
		return serverQuestId;
	}

	public void setServerQuestId(String serverQuestId) {
		this.serverQuestId = serverQuestId;
	}

	public String getForwho() {
		return forwho;
	}

	public void setForwho(String forwho) {
		this.forwho = forwho;
	}
	
	public String getServerquestfullname() {
		return serverquestfullname;
	}

	public void setServerquestfullname(String serverquestfullname) {
		this.serverquestfullname = serverquestfullname;
	}

}
