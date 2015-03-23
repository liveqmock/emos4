package com.ultrapower.eoms.ultrabpp.runtime.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;










import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.ultrapower.eoms.common.core.component.data.DataAdapter;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.common.portal.service.PortalManagerService;
import com.ultrapower.eoms.ultrabpp.cache.service.ExtendFunctionCacheService;
import com.ultrapower.eoms.ultrabpp.runtime.manage.RelationSheetServiceImpl;
import com.ultrapower.eoms.ultrabpp.runtime.model.WorksheetCommitContext;
import com.ultrapower.workflow.configuration.sort.model.WfType;
import com.ultrapower.workflow.relate.model.RelateModel;

public class RelateSheetAction extends BaseAction {

	private String baseIdlist;
	private String taskId;
	private String relationBaseID;
	private PortalManagerService portalManagerService;
	private String relatebaseSchema;
	private String relateType;
	private String curStatus;
	private String baseid;
	
	public String getBaseid() {
		return baseid;
	}

	public void setBaseid(String baseid) {
		this.baseid = baseid;
	}

	public String getCurStatus() {
		return curStatus;
	}

	public void setCurStatus(String curStatus) {
		this.curStatus = curStatus;
	}

	public String getRelateType() {
		return relateType;
	}

	public void setRelateType(String relateType) {
		this.relateType = relateType;
	}

	public String getRelatebaseSchema() {
		return relatebaseSchema;
	}

	public void setRelatebaseSchema(String relatebaseSchema) {
		this.relatebaseSchema = relatebaseSchema;
	}

	public String getBaseIdlist() {
		return baseIdlist;
	}

	public void setBaseIdlist(String baseIdlist) {
		this.baseIdlist = baseIdlist;
	}

	IDao<RelateModel> relaDao;
	
	public IDao<RelateModel> getRelaDao() {
		return relaDao;
	}

	public void setRelaDao(IDao<RelateModel> relaDao) {
		this.relaDao = relaDao;
	}

	public String getRelationBaseID() {
		return relationBaseID;
	}

	public void setRelationBaseID(String relationBaseID) {
		this.relationBaseID = relationBaseID;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public PortalManagerService getPortalManagerService() {
		return portalManagerService;
	}

	public void setPortalManagerService(PortalManagerService portalManagerService) {
		this.portalManagerService = portalManagerService;
	}

	public void    addRsheet() {
		String baseId = "";
		String mes="";
		String failebasesn="";
		String[] list = baseIdlist.split(";");
		for (int i = 0; i < list.length; i++) {
			baseId = list[i];
			String str = addRelation(baseId, relationBaseID, taskId);
			if(!str.equals("关联成功")){
				failebasesn=getBaseSn(baseId,relatebaseSchema);
			}
			mes+=str;
		}
		if(mes.contains("工单不存在")||mes.contains("工单存在关联")){
			mes=failebasesn+"关联失败";
		}else{
			mes="关联成功";
		}
		renderText(mes);
	}
	
	/*
	 * 检查工单是否存在关联
	 */
	private boolean  checkRelation(String baseID, String relationBaseID){
		RelateModel relateModel = relaDao.findUnique("from RelateModel where baseId=? and relateBaseId=?", new String[]{baseID,relationBaseID});
		if(relateModel==null){
			relateModel = relaDao.findUnique("from RelateModel where baseId=? and relateBaseId=?", new String[]{relationBaseID,baseID});
		}
		if(relateModel==null) return true;
		else return false;
	}
	
	private String addRelation(String baseID, String relationBaseID,String relationTaskID)
	{
		UserSession  userinfo =portalManagerService.getUserSession(getUserSession().getLoginName());//获取登录用户信息

		String loginName=userinfo.getLoginName();
		String fullName=userinfo.getFullName();
		QueryAdapter qAdapter = new QueryAdapter();
		String sql = "select * from bs_t_bpp_baseinfor t where t.baseid='"+baseID+"' or t.baseid='"+relationBaseID+"'"; //查询需要关联的工单是否都存在
		DataTable dataTable = qAdapter.executeQuery(sql);
		if(!checkRelation(baseID,relationBaseID)){
			return "工单存在关联";
		}
		if(dataTable!=null&&dataTable.length()==2){
			RelateModel model = new RelateModel();
			List<DataRow> dataRows = dataTable.getRowList();
			for(DataRow dataRow : dataRows) //给关联表字段赋值
			{
				String baseid = dataRow.getString("baseid");
				String basesn = dataRow.getString("basesn");
				String baseschema = dataRow.getString("baseschema");
				String basename = dataRow.getString("basename");
				if(baseID.equals(baseid)){
					model.setBaseId(baseid);
					model.setBaseSchema(baseschema);
					model.setBaseName(basename);
					model.setBaseSN(basesn);
				}else{
					model.setRelateBaseId(baseid);
					model.setRelateBaseSn(basesn);
					model.setRelateBaseSchema(baseschema);
					model.setRelateBaseName(basename);
					model.setRelateTaskId(relationTaskID);
					model.setRelateUserLoginName(loginName);
					model.setRelateUserName(fullName);
				}
				model.setRelateType(0);
				model.setRelateTime(TimeUtils.getCurrentTime());
				//model.setRelateUserLoginName(actorId);
			}
			relaDao.save(model);
			return "关联成功";
		}else{
			return "工单不存在";
		}
	}
   public String getBaseSn(String baseID,String baseschema){
	   String basesn="";
	   QueryAdapter qAdapter = new QueryAdapter();
	   String tablename ="bs_f_"+baseschema;
	   String sql = "select * from "+tablename+" t where t.baseid='"+baseID+"'";
	   DataTable dataTable = qAdapter.executeQuery(sql);
	   if(dataTable!=null){
			RelateModel model = new RelateModel();
			List<DataRow> dataRows = dataTable.getRowList();
			basesn =dataRows.get(0).getString("basesn");
			if(basesn==null){
				basesn="";
			}
	   }
	   return basesn;
   }
   

   
     public  String relateSheets(){
	   HttpServletRequest request = ServletActionContext. getRequest();
	   String sqlname="";
	   //根据需要关联的类型去设置相应的sqlname
		if(relatebaseSchema.equals("CDB_CHANGE")){  
			sqlname="SQL_WF_RelateChangSheet.query";
		}else if(relatebaseSchema.equals("CDB_RELEASE")){
			sqlname="SQL_WF_RelateReleaseSheet.query";
		}else if(relatebaseSchema.equals("CDB_SERVICEREQUEST")){
			sqlname="SQL_WF_RelateServiceRequestSheet.query";
		}else if(relatebaseSchema.equals("CBD_INCIDENT")){
			sqlname="SQL_WF_RelateIncidentSheet.query";
		}
		request.setAttribute("sqlname", sqlname);
		request.setAttribute("relationBaseID", relationBaseID);
		request.setAttribute("taskId", taskId);
//	   request.setAttribute("relateType", relateType);
//	   List a =new ArrayList();
//	   for(int i=0;i<relateSchemas.size();i++){
//		   a=(List) relateSchemas.get(i);
//	   }
//	   relateSchemas.clear();
//	   relateSchemas.add(a);
//	   request.setAttribute("relateSchemas", relateSchemas);
//	   request.setAttribute("baseId", baseId);
//		request.setAttribute("taskId", taskId);
	   return "relatesheet";
   }
    
 	public void  deleteSheet() {
 		String mes="";
 		 DataAdapter dAdapter = new DataAdapter();
 		String  Sql="delete  from bs_t_wf_relation where (baseid=? and relatebaseid=?) or (baseid=? and relatebaseid=?)";
	 
 		String[] objs = {baseid,relationBaseID,relationBaseID,baseid};
 		int i = dAdapter.execute(Sql,objs);
 		if(i>-1){
 			mes="删除成功";
 		}else{
 			mes="删除失败";
 		}
 		renderText(mes);
	}
}
