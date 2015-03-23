package com.ultrapower.wfinterface.core.extend;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.msextend.incident.web.IncidentManageExtendFunction;
import com.ultrapower.eoms.ultrasm.model.Attachment;
import com.ultrapower.eoms.ultrasm.model.UserInfo;
import com.ultrapower.eoms.ultrasm.service.UserManagerService;
import com.ultrapower.wfinterface.core.model.Actor;
import com.ultrapower.wfinterface.core.model.WorkflowContext;
import com.ultrapower.wfinterface.core.model.WorkflowField;
import com.ultrapower.wfinterface.core.service.AbsWorkflowService;

public class IncidentServiceImpl extends AbsWorkflowService
{
	
	/**
	 * 接口XML解析之前
	 * @return
	 * @throws Exception
	 */
	@Override
	protected boolean init() throws Exception {
		return true;		
	}
	
	
	/**
	 * 接口XML解析之后，执行接口建单之前
	 * @return
	 * @throws Exception
	 */
	@Override
	protected boolean init2() throws Exception {	
		return true;		
	}
	
	@Override
	protected boolean dataException(String errorString, Exception ex) {
		return true;
	}

	@Override
	protected boolean postHandle() throws Exception {
		return true;
	}
	
	@Override
	protected boolean dataHandle(WorkflowContext wfContext,List<Attachment> attachmentsList) throws Exception {
		WorkflowField dealUser = wfContext.getInputFields().get("requestOA");
		wfContext.setCreator(dealUser.getValue());//设置建单人
		setContactInfo(wfContext);
		setActor(wfContext);//设置下一步处理人
		setConnBaseid(wfContext);//设置接口建单标识字段
		setPriority(wfContext);//设置优先级
		setAttachmentRef(wfContext,attachmentsList);//设置附件的关联id
		return true;
	}
	
	private void setAttachmentRef(WorkflowContext wfContext, List<Attachment> attachmentsList) {
		String relationCode = UUID.randomUUID().toString();
		if(attachmentsList!=null && attachmentsList.size()>0){
			relationCode = attachmentsList.get(0).getRelationcode();
		}
		WorkflowField field = new WorkflowField();
		field.setDataType(4);
		field.setDbColName("AttachmentsList");
		field.setFieldName("AttachmentsList");
		field.setFieldText("附件列表");
		field.setValue(relationCode);
		wfContext.getInputFields().put("AttachmentsList", field);
	}


	//设置优先级
	private void setPriority(WorkflowContext wfContext) {
		WorkflowField field = new WorkflowField();
		field.setDataType(4);
		field.setDbColName("Priority");
		field.setFieldName("Priority");
		field.setFieldText("优先级");
		field.setValue(IncidentManageExtendFunction.getPriority(wfContext.getInputFields().get("Incident_UrgentDgree").getValue(), wfContext.getInputFields().get("Incident_EffectDgree").getValue()));
		wfContext.getInputFields().put("Priority", field);
	}


	//设置接口建单标识字段
	private void setConnBaseid(WorkflowContext wfContext) {
		WorkflowField field = new WorkflowField();
		field.setDataType(4);
		field.setDbColName("connBaseid");
		field.setFieldName("connBaseid");
		field.setFieldText("关联单号");
		field.setValue(UUID.randomUUID().toString());
		wfContext.getInputFields().put("connBaseid", field);
	}


	/**
	 * 设置联系人信息
	 */
	private void setContactInfo(WorkflowContext wfContext) {
		String contactLoginName = wfContext.getInputFields().get("ContactPersonId").getValue();
		UserManagerService userManager = (UserManagerService) WebApplicationManager.getBean("userManagerService");
		UserInfo contact = userManager.getUserByLoginName(contactLoginName);
		if(contact==null) throw new RuntimeException("无此联系人:"+contactLoginName);
		
		WorkflowField fieldContactPerson = new WorkflowField();
		fieldContactPerson.setDataType(4);
		fieldContactPerson.setDbColName("ContactPerson");
		fieldContactPerson.setFieldName("ContactPerson");
		fieldContactPerson.setFieldText("联系人");
		fieldContactPerson.setValue(contact.getFullname());
		wfContext.getInputFields().put("ContactPerson", fieldContactPerson);
		
//		WorkflowField fieldContactPersonId = new WorkflowField();
//		fieldContactPerson.setDataType(4);
//		fieldContactPerson.setDbColName("ContactPersonId");
//		fieldContactPerson.setFieldName("ContactPersonId");
//		fieldContactPerson.setFieldText("联系人ID");
//		fieldContactPerson.setValue(contact.getLoginname());
//		wfContext.getInputFields().put("ContactPersonId", fieldContactPersonId);
		
		WorkflowField fieldContactPersonSite = new WorkflowField();
		fieldContactPersonSite.setDataType(4);
		fieldContactPersonSite.setDbColName("ContactPersonSite");
		fieldContactPersonSite.setFieldName("ContactPersonSite");
		fieldContactPersonSite.setFieldText("联系人单位");
		fieldContactPersonSite.setValue(contact.getCdbUnitName());
		wfContext.getInputFields().put("ContactPersonSite", fieldContactPersonSite);
		
		WorkflowField fieldContactDepartment = new WorkflowField();
		fieldContactDepartment.setDataType(4);
		fieldContactDepartment.setDbColName("ContactDepartment");
		fieldContactDepartment.setFieldName("ContactDepartment");
		fieldContactDepartment.setFieldText("联系人部门");
		fieldContactDepartment.setValue(contact.getCdbDepName());
		wfContext.getInputFields().put("ContactDepartment", fieldContactDepartment );
		
		WorkflowField fieldContactOA = new WorkflowField();
		fieldContactOA.setDataType(4);
		fieldContactOA.setDbColName("ContactOA");
		fieldContactOA.setFieldName("ContactOA");
		fieldContactOA.setFieldText("联系人OA号");
		fieldContactOA.setValue(contact.getOaNumber());
		wfContext.getInputFields().put("ContactOA", fieldContactOA );
		
		WorkflowField fieldContactPhone = new WorkflowField();
		fieldContactPhone.setDataType(4);
		fieldContactPhone.setDbColName("ContactPhone");
		fieldContactPhone.setFieldName("ContactPhone");
		fieldContactPhone.setFieldText("联系人手机");
		fieldContactPhone.setValue(contact.getMobile());
		wfContext.getInputFields().put("ContactPhone", fieldContactPhone );
		
		WorkflowField fieldContactPosition = new WorkflowField();
		fieldContactPosition.setDataType(4);
		fieldContactPosition.setDbColName("ContactPosition");
		fieldContactPosition.setFieldName("ContactPosition");
		fieldContactPosition.setFieldText("联系人职位");
		fieldContactPosition.setValue(contact.getPositionText());
		wfContext.getInputFields().put("ContactPosition", fieldContactPosition );
		
		WorkflowField fieldContactStation = new WorkflowField();
		fieldContactStation.setDataType(4);
		fieldContactStation.setDbColName("ContactStation");
		fieldContactStation.setFieldName("ContactStation");
		fieldContactStation.setFieldText("联系人工位");
		fieldContactStation.setValue(contact.getStation());
		wfContext.getInputFields().put("ContactStation", fieldContactStation );
		
		WorkflowField fieldContactTel = new WorkflowField();
		fieldContactTel.setDataType(4);
		fieldContactTel.setDbColName("ContactTel");
		fieldContactTel.setFieldName("ContactTel");
		fieldContactTel.setFieldText("联系人电话");
		fieldContactTel.setValue(contact.getPhone());
		wfContext.getInputFields().put("ContactTel", fieldContactTel );
		
		WorkflowField fieldIsVIP = new WorkflowField();
		fieldIsVIP.setDataType(4);
		fieldIsVIP.setDbColName("IsVIP");
		fieldIsVIP.setFieldName("IsVIP");
		fieldIsVIP.setFieldText("VIP等级");
		String isVipValue = "";
		if(contact.getIsVip()!=null){
			String isVip = contact.getIsVip().toString();
			if(StringUtils.equals(isVip, "1")) isVipValue = "一级VIP";
			if(StringUtils.equals(isVip, "2")) isVipValue = "二级VIP";
		}
		fieldIsVIP.setValue(isVipValue);
		wfContext.getInputFields().put("IsVIP", fieldIsVIP );
		
		WorkflowField fieldviplevel = new WorkflowField();
		fieldviplevel.setDataType(4);
		fieldviplevel.setDbColName("viplevel");
		fieldviplevel.setFieldName("viplevel");
		fieldviplevel.setFieldText("viplevel");
		String viplevelValue = "";
		if(contact.getIsVip()!=null){
			String isVip = contact.getIsVip().toString();
			if(!StringUtils.equals(isVip, "0")) viplevelValue = isVip;
		}
		fieldviplevel.setValue(viplevelValue);
		wfContext.getInputFields().put("viplevel", fieldviplevel );
		
	}

	/**
	 * 设置下一步处理人，根据现象分类ID在bs_t_sm_commontree表中查询该现象分类的“负责人/团队(PROPFIELD_01)”
	 */
	private void setActor(WorkflowContext wfContext){
		String phenoClassID = wfContext.getInputFields().get("IncidentPhenoClassID").getValue();
		String phenoClassName = wfContext.getInputFields().get("IncidentPhenoClass").getValue();
		String sql = "select propfield_01 from bs_t_sm_commontree where id = ?";
		QueryAdapter query = new QueryAdapter();
		DataTable dataTable = query.executeQuery(sql, phenoClassID);
		String actorText = "%s#:%s#:NEXT#:2#:0#:0#:0#:#:#:#:#;";
		if(dataTable!=null && dataTable.length()>0){
			DataRow dataRow = dataTable.getDataRow(0);
			String responsible[] = dataRow.getString("propfield_01").split(":");
			String responsibleType = "U";
			if(responsible[0].equals("D")) responsibleType = "G";
			actorText = String.format(actorText, responsibleType,responsible[1]);
		}else{
			throw new RuntimeException("现象分类["+phenoClassName+"]未配置负责人/团队");
		}
		Actor actor = new Actor();
		actor.setActorText(actorText);
		wfContext.setActor(actor);
	}
	
	
//	protected String connBusSystemName(String connBusSystem) {
//		QueryAdapter query = new QueryAdapter();
//		String sqlQuery = "select  BUSSYSTEM from bs_t_wf_releasedealuserconfig where bussystemcode='" + connBusSystem + "'";
//		DataTable busSystem = query.executeQuery(sqlQuery);
//		String busSystemName = "";
//		if (busSystem != null && busSystem.length() > 0) {
//			busSystemName = busSystem.getDataRow(0).getString("bussystem");
//		}
//		return busSystemName;
//	}
}
