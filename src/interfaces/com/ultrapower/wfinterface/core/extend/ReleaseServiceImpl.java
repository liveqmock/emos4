package com.ultrapower.wfinterface.core.extend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.ultrapower.eoms.common.core.component.data.DataAdapter;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.ultrasm.model.Attachment;
import com.ultrapower.eoms.ultrasm.model.UserInfo;
import com.ultrapower.eoms.ultrasm.service.UserManagerService;
import com.ultrapower.wfinterface.core.model.Actor;
import com.ultrapower.wfinterface.core.model.WfiTempAttachment;
import com.ultrapower.wfinterface.core.model.WorkflowContext;
import com.ultrapower.wfinterface.core.model.WorkflowField;
import com.ultrapower.wfinterface.core.service.AbsWorkflowService;

public class ReleaseServiceImpl extends AbsWorkflowService
{
	private WorkflowContext context;
	private List<DataRow> dataRow;
	private Map<String,String> fieldMap;
	
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
		if(this.workflowContexts != null){
			WorkflowContext tmpContext = new WorkflowContext();
			for(WorkflowContext context : this.workflowContexts){
				String chgName = context.getInputFields().get("chgName").getValue();
				String chgNum = context.getInputFields().get("chgNum").getValue();
				String chgType = context.getInputFields().get("chgType").getValue();
				
				String connBaseid = context.getInputFields().get("connBaseId").getValue();
				if(StringUtils.isNotBlank(connBaseid)){
					tmpContext = context;
				}
			}
			this.workflowContexts.clear();
			this.workflowContexts.add(tmpContext);
		}
		return true;		
	}
	
	@Override
	protected boolean dataException(String errorString, Exception ex) {
		return true;
	}

	@Override
	protected boolean postHandle() throws Exception {
		DataRow sheetInfo = context.getSheetInfo();
		if(sheetInfo != null){
			String baseid = sheetInfo.getString("baseid");
			if(StringUtils.isNotBlank(baseid)){
				DataAdapter ada = new DataAdapter();
				DataTable dt = new DataTable("BS_T_WF_RELEASEINTERFACECHG");
				String[] key = {"pid"};
				dt.setPrimaryKey(key);
				for(DataRow dr:dataRow){
					dr.put("pid",UUID.randomUUID().toString());
					dr.put("baseid",baseid);
					dr.setOptype(1);
					dt.putDataRow(dr);
				}
				ada.execute(dt);
			}
		}
		return true;
	}
	
	@Override
	protected boolean dataHandle(WorkflowContext wfContext,List<Attachment> attachmentsList) throws Exception {
		this.context = wfContext;
		dataRow = new ArrayList<DataRow>();
		fieldMap = new HashMap<String, String>();
		fieldMap.put("pid","pid");
		fieldMap.put("baseid","baseid");
		fieldMap.put("chgType","chgType");
		fieldMap.put("chgName","chgName");
		fieldMap.put("chgNum","chgNum");
		fieldMap.put("chgContent","chgContent");
		fieldMap.put("chgModule","chgModule");
		
		/**
		 * 从BS_T_WFIDATAIN表的OpDetail信息获取网管告警的信息，需要执行客户化信息转换
		 * 此些信息为从网管对接接口中，直接取得的信息，不加任何客户化修饰，直接赋值到ITSM对应字段中
		 */
		
		//告警opDetail Info数据（dr中体现所有告警传入的参数，数据从InterSwitchAlarm.xml中匹配的信息）
		DataTable dt = this.parameterTable;
		DataRow dr = new DataRow();
		for(Object obj:dt.getRowList()){
			DataRow row = (DataRow)obj;
			String connBaseid = row.getString("connBaseid");
			if(StringUtils.isBlank(connBaseid)){
				dataRow.add(row);
			}else{
				for(Object key:row.getRowHashMap().keySet()){
					if(!fieldMap.containsKey(key)){
						dr.put(key.toString(),row.getRowHashMap().get(key));
					}
				}
			}
		}
		for(String key:fieldMap.keySet()){
			wfContext.getInputFields().remove(key);
		}
		this.parameterTable.getRowList().clear();
		this.parameterTable.getRowList().add(dr);
		
		dr = this.parameterTable.getDataRow(0);
		
		
		/**
		 * 通过应用系统查看相应的处理人
		 */
		
		String actorText = "#:NEXT#:1#:0#:0#:0#:#:dp_22#:#:#;";
		QueryAdapter query = new  QueryAdapter();
		String connBusSystem = dr.getString("connBusSystem");
		String connBusSystemName = connBusSystemName(connBusSystem);
		if(StringUtils.isNotBlank(connBusSystem))
		{
			//根据配置文件取得表名（根据InterSwitchAlarm.xml文件中的baseSchema）
			String sqlQuery = "select edituserid from bs_t_wf_releasedealuserconfig where bussystemcode='" + connBusSystem + "'";
			DataTable actor = query.executeQuery(sqlQuery);
			if(actor!=null && actor.length()>0)
			{
				String actorId = actor.getDataRow(0).getString("edituserid");
				if(StringUtils.isNotBlank(actorId)){
					actorText = actorId + actorText;
				}else{
					throw new Exception("发布工单建单失败，原因：应用系统在ITSM系统中未找到相应的处理人");	
				}
			}else{
				throw new Exception("发布工单建单失败，原因：应用系统在ITSM系统中不存在 或 未找到相应的处理人");			
			}
		}
		else
		{
			throw new Exception("发布工单建单失败，原因：应用系统名称为空");
		}
		
		//自动匹配变更批次
		DataTable reSheetDt;
		String batchInfoStr = "";
		String batchInfoId = "";
		List<DataRow> dataRowList = null;
		reSheetDt = getCbiFolLrtAll(TimeUtils.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
		if(reSheetDt!=null){
		  dataRowList = reSheetDt.getRowList();
		}
		
		if(dataRowList!=null&&dataRowList.size()>0){
		 batchInfoStr = dataRowList.get(0).getString("BATCH_NO")+"["+dataRowList.get(0).getString("REVIEWTIME")+"]";
		 batchInfoId = dataRowList.get(0).getString("pid");
		}
		
		wfContext.getInputFields().put("batch_info", new WorkflowField("batch_info", "batch_info", "批次信息", 4,batchInfoStr, false));
		wfContext.getInputFields().put("batch_info_id", new WorkflowField("batch_info_id", "batch_info_id", "批次信息ID", 4,batchInfoId, false));
		
		//设置处理人
		Actor actor = new Actor();
		actor.setActorText(actorText);
		wfContext.setActor(actor);
		wfContext.setCreator(dr.getString("requestOA")); 
		UserManagerService userManagerService = (UserManagerService) WebApplicationManager.getBean("userManagerService");
		
		String useracount = dr.getString("requestOA");
		if(StringUtils.isBlank(useracount)){
			throw new Exception("建单人OA号不能不为空 ！");
		}
		UserInfo user = userManagerService.getUserByLoginName(useracount);
		
		if(user == null){
			//user = userManagerService.getUserByLoginName(PropertiesUtils.getProperty("newAlarmCreateUser"));
			throw new Exception("OA号是：" + useracount + "  的人员在ITSM系统中不存在。");
		}
		if(user != null){
			wfContext.setDealer(user.getLoginname());
			wfContext.getInputFields().put("requestUser", new WorkflowField("requestUser", "requestUser", "请求人", 4,user.getFullname(), false));
			wfContext.getInputFields().put("requestDept", new WorkflowField("requestDept", "requestDept","请求人部门", 4,user.getDepname(), false));
			wfContext.getInputFields().put("requestGroup", new WorkflowField("requestGroup", "requestGroup","请求人单位", 4,user.getCdbUnitName(), false));
			wfContext.getInputFields().put("requestOA", new WorkflowField("requestOA", "requestOA", "工号", 4,user.getLoginname(), false));
			wfContext.getInputFields().put("requestEmail", new WorkflowField("requestEmail", "requestEmail", "请求人邮件", 4,user.getEmail(), false));
			wfContext.getInputFields().put("requestMobile", new WorkflowField("requestMobile", "requestMobile", "请求人手机", 4,user.getMobile(), false));
			wfContext.getInputFields().put("connBusSystem", new WorkflowField("connBusSystem", "connBusSystem", "关联系统名称", 4,connBusSystemName, false));
		}
		
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
		field.setDbColName("releaseAttache");
		field.setFieldName("releaseAttache");
		field.setFieldText("附件列表");
		field.setValue(relationCode);
		wfContext.getInputFields().put("releaseAttache", field);
	}
	
	protected String connBusSystemName(String connBusSystem) {
		QueryAdapter query = new QueryAdapter();
		String sqlQuery = "select  BUSSYSTEM from bs_t_wf_releasedealuserconfig where bussystemcode='" + connBusSystem + "'";
		DataTable busSystem = query.executeQuery(sqlQuery);
		String busSystemName = "";
		if (busSystem != null && busSystem.length() > 0) {
			busSystemName = busSystem.getDataRow(0).getString("bussystem");
		}
		return busSystemName;
	}
	
	
	/**
	 * 根据传入的时间查询大于最后受理时间的批次信息
	 * @param timeStr  字符串形式的时间,如果此时间为null,默认为当前时间
	 * @return 
	 */
	
	public 	DataTable getCbiFolLrtAll(String timeStr){
		QueryAdapter query = new QueryAdapter();
		Long timeL ; 
		if("".equals(timeStr)||timeStr == null){
			timeL = TimeUtils.getCurrentTime();
		}else{
			timeL = TimeUtils.formatDateStringToInt(timeStr);
		}
		String querySql = "select * from BS_T_SM_BATCHCONF where 1=1 and date_to_sec(LASTACCEPTTIME)>"+timeL +" and active = 1 order by LASTACCEPTTIME ";
		DataTable dt= query.executeQuery(querySql);
		return dt;
	}
	
	
}
