package com.ultrapower.wfinterface.core.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.ultrabpp.api.BaseAction;
import com.ultrapower.eoms.ultrasm.model.Attachment;
import com.ultrapower.eoms.workflow.api.bean.BaseAttachmentInfo;
import com.ultrapower.eoms.workflow.api.bean.BaseFieldInfo;
import com.ultrapower.eoms.workflow.api.bean.DealObject;
import com.ultrapower.wfinterface.RecordLog;
import com.ultrapower.wfinterface.core.model.Actor;
import com.ultrapower.wfinterface.core.model.Actor.ActorType;
import com.ultrapower.wfinterface.core.model.WfiDatain;
import com.ultrapower.wfinterface.core.model.WorkflowContext;
import com.ultrapower.wfinterface.core.model.WorkflowField;

/**
 * 
 * @see IWorkflowService
 * @author BigMouse
 *
 */
public abstract class AbsWorkflowService implements IWorkflowService
{
	protected IWFInterfaceConfig wfInterfaceConfig;
	protected WfiDatainService wfiDatainService;
	
	private String serviceClassName = getClass().getName();
	protected WfiDatain serviceContext;
	protected List<WorkflowContext> workflowContexts;
	protected DataTable parameterTable;
	protected List<Attachment> attachmentsList;
	protected String actionResult;
	protected BaseAction apiAction;
	protected DataRow currentParameterRow;
	
	private WorkflowContext currentWFContext;

	public String call(WfiDatain serviceContext) throws Exception
	{
		return this.call(serviceContext,null);
	}
	/**
	 * 返回null标识处理成功，否则返回错误信息
	 * @param serviceContext
	 * @return
	 */
	public String call(WfiDatain serviceContext,DataTable paraTable) throws Exception
	{
		parameterTable=paraTable;
		try
		{
			wfInterfaceConfig = (IWFInterfaceConfig)WebApplicationManager.getBean("wfInterfaceConfig");
			wfiDatainService = (WfiDatainService)WebApplicationManager.getBean("wfiDatainService");
		}
		catch (Exception e)
		{
			return "系统错误！";
		}
		
		this.serviceContext = serviceContext;
		String result = null;
		
		result = mainHandle();
		
		return result;
	}
	
	public String extendCall(DataTable fields, List<Attachment> attachments) throws Exception
	{
		try
		{
			wfInterfaceConfig = (IWFInterfaceConfig)WebApplicationManager.getBean("wfInterfaceConfig");
			wfiDatainService = (WfiDatainService)WebApplicationManager.getBean("wfiDatainService");
		}
		catch (Exception e)
		{
			return "系统错误！";
		}
		
		parameterTable = fields;
		attachmentsList = attachments;
		
		coreHandle();
		return actionResult;
	}
	
	/**
	 * 真正的操作类，抛出的异常只有更新状态是产生，这是，回滚所有操作
	 * @return
	 * @throws Exception
	 */
	private String mainHandle() throws Exception
	{
		wfInterfaceConfig.reflushConfiguration(null);
		
		//处理附件XML转换成对象
		attachmentsList = wfInterfaceConfig.formatAttachXml2AttachObj(serviceContext.getAttachref());
		
		//保存接口数据
		try
		{
			serviceContext.setOpstate(2);
			serviceContext.setMethodname(serviceClassName);
			wfiDatainService.saveWfiDatainAndAttachments(serviceContext, attachmentsList);
		}
		catch (Exception e)
		{
			//updateDataInStatus(4,"保存接口信息失败");
			String errorString = "保存接口信息失败！";
			return reportException(errorString, e);
		}
		
		//转换XML到Table
		try
		{
			if(parameterTable==null)
			{
				parameterTable = wfInterfaceConfig.formatXml2Table(serviceContext.getOpdetail(), serviceClassName);
				RecordLog.printLog("将opDeatilXml转换成Table", RecordLog.LOG_LEVEL_DEBUG);
			}
		}
		catch (Exception e)
		{
			String errorString = "转换opDeatilXml失败！";
			
			//更新接口数据为处理异常
			updateDataInStatus(4,"转换XML到Table处理异常");
			
			return reportException(errorString, e);
		}
		
		try
		{
			coreHandle();
		}
		catch (Exception e)
		{
			updateDataInStatus(4,"coreHandle 中程序处理出错: "+e.getMessage()); 
			return reportException("处理逻辑异常！", e);
			//更新接口数据为处理异常
/*			String msg=e.getMessage();
			int len =msg.indexOf("NetOptimizeID");
			String NetOptimizeID=msg.substring(len+1);
			
			
			if(NetOptimizeID==null || "".equals(NetOptimizeID))
			{
				updateDataInStatus(4,"coreHandle 中程序处理出错: "+msg);//建了网络优化工单的特殊处理
				return reportException("处理逻辑异常！", e);
			}
			else
			{
				updateDataInStatus(5,"coreHandle 中程序处理出错: "+msg);//建了网络优化工单的特殊处理
				return null;
			}*/
		}
		
		
		if(this.workflowContexts.get(0)!=null)
			//保存接口数据
			updateDataInStatus(5,"处理成功,工单id： "+this.workflowContexts.get(0).getBaseID());
		else
		{
			updateDataInStatus(4,"处理完成,workflowContexts.get(0).getBaseID()为空： ");
		}
		return null;
	}

	protected void coreHandle() throws Exception
	{
		//客户化扩展init()
		if(!init())
		{
		
			RecordLog.printLog("init逻辑设置不进行进一步操作，操作完成", RecordLog.LOG_LEVEL_DEBUG);
			return;
		}
		
		//转换Table到WFObj
		List<Map<String, WorkflowField>> fieldsList = wfInterfaceConfig.formatTable2WFObj(parameterTable, serviceClassName);
		RecordLog.printLog("将Table转换成WFObj", RecordLog.LOG_LEVEL_DEBUG);
		
		workflowContexts = new ArrayList<WorkflowContext>();
		
		for(Map<String, WorkflowField> fields:  fieldsList)
		{
			WorkflowContext wfContext = new WorkflowContext();
			wfContext.setOperateCode(wfInterfaceConfig.getMethodMap().get(serviceClassName).getOperateCode());
			wfContext.setOperateText(wfInterfaceConfig.getMethodMap().get(serviceClassName).getOperateText());
			wfContext.setBaseSchema(wfInterfaceConfig.getMethodMap().get(serviceClassName).getBaseSchema());
			wfContext.setOperateActionId(wfInterfaceConfig.getMethodMap().get(serviceClassName).getOperateActonid());
			wfContext.setCreator(wfInterfaceConfig.getMethodMap().get(serviceClassName).getCreator());
			wfContext.setInputFields(fields);
			wfContext.setAttachments(attachmentsList);
			workflowContexts.add(wfContext);
		}
		
		if(!init2())
		{
			RecordLog.printLog("init逻辑设置不进行进一步操作，操作完成", RecordLog.LOG_LEVEL_DEBUG);
			return;
		}
		
		
		int i = 0;
		
		QueryAdapter query = new QueryAdapter();
		for(WorkflowContext wfContext : workflowContexts)
		{
			currentWFContext = wfContext;
			currentParameterRow = parameterTable.getDataRow(i);
			
			//调用工单API
			apiAction = null;
			//客户化扩展dataHandle()
			if(!dataHandle(wfContext, attachmentsList))
			{
				RecordLog.printLog("dataHandle逻辑设置不进行进一步操作，操作完成", RecordLog.LOG_LEVEL_DEBUG);
				continue;
			}
			actionResult = doAction(wfContext, attachmentsList);
			wfContext.setBaseID(actionResult);
			String sheetSql = "select * from bs_f_" + wfContext.getBaseSchema() + " where baseid = '" + actionResult + "'";
			DataTable dt = query.executeQuery(sheetSql);
			if(dt != null && dt.length() == 1){
				wfContext.setSheetInfo(dt.getDataRow(0));
			}
			
			//客户化扩展postHandle()
			if(!postHandle())
			{
				RecordLog.printLog("postHandle逻辑设置不进行进一步操作，操作完成", RecordLog.LOG_LEVEL_DEBUG);
				continue;
			}
			i++;
		}
	}
	
	/**
	 * 更新DataIn数据
	 * @param updateType 处理状态1:未处理  2:处理中 3:已处理但未连接上ar服务 4:已处理但处理失败(ar服务错误除外) 5 已完成
	 * @return 是否更新成功
	 * @throws Exception
	 */
	protected void updateDataInStatus(int updateType,String dealdesc) throws Exception
	{
		//保存接口数据
		try
		{
			StringBuffer dealsc=new StringBuffer();
			if(serviceContext.getDealdesc()!=null)
				dealsc.append(serviceContext.getDealdesc());
			dealsc.append("; ");
			dealsc.append(updateType);
			dealsc.append(dealdesc);
			serviceContext.setDealdesc(dealsc.toString());
			if(updateType>-1)
			{
				serviceContext.setOpstate(updateType);
			}
			wfiDatainService.saveWfiDatain(serviceContext);
		}
		catch (Exception e)
		{
			String errorString = "保存接口信息失败！";
			reportException(errorString, e);
			throw e;
		}
	}
	
	protected String doAction(WorkflowContext wfContext, List<Attachment> attachmentsList) throws Exception
	{
		initBaseAction(wfContext);
		
		//封装Actor对象
		List<DealObject> dealObjs = new ArrayList<DealObject>();
		Actor actor = wfContext.getActor();
		if(actor != null){
			DealObject dealObj = new DealObject();
			if(actor.getActorType().equals(ActorType.USER)) dealObj.setAssigneeId(actor.getActorName());
			if(actor.getActorType().equals(ActorType.GROUP)) dealObj.setGroupId(actor.getActorName());
			if(actor.getActorType().equals(ActorType.ROLE)) dealObj.setRoleId(actor.getActorName());
			dealObj.setDealType(String.valueOf(actor.getDealType()));
			dealObj.setAcceptOverTime(actor.getAcceptOverTime());
			dealObj.setDealOverTime(actor.getDealOverTime());
			dealObj.setDealDesc(wfContext.getDesc());
			dealObj.setProcessId(actor.getProcessID());
			dealObj.setActorStr(actor.getActorText());
			dealObjs.add(dealObj);
		}
		
		//封装Field对象
		List<BaseFieldInfo> fields = new ArrayList<BaseFieldInfo>();
		Map<String, WorkflowField> wfFields = wfContext.getInputFields();
		Map<String,String> inputFileds = new HashMap<String, String>();
		if(wfFields != null)
		{
			for(WorkflowField field : wfFields.values())
			{
				BaseFieldInfo fieldInfo = new BaseFieldInfo(field.getFieldName(), field.getDbColName(), field.getValue(), field.getDataType(), field.isCondition());
				fields.add(fieldInfo);
				inputFileds.put(field.getDbColName(),field.getValue());
			}
		}
		
		//封装附件对象
		List<BaseAttachmentInfo> attachs = new ArrayList<BaseAttachmentInfo>();
		if(attachmentsList != null)
		{
			for(Attachment attach : attachmentsList)
			{
				BaseAttachmentInfo attachInfo = new BaseAttachmentInfo(attach.getPath(), attach.getName());
				attachs.add(attachInfo);
			}
		}
		
		//调用API
		String newBaseID = null;
		
		if(!isAlive())
		{
			connectException();
			updateDataInStatus(3,"isAlive调用失败");
		}
		else
		{
			//(, , wfContext.getDealer(), , wfContext.getOperateText(), dealObjs, fields, attachs)
			newBaseID = apiAction.doAction(wfContext.getBaseID(),wfContext.getBaseSchema(),wfContext.getTaskID(),wfContext.getCreator(),wfContext.getOperateActionId(),wfContext.getOperateCode(),wfContext.getOperateText(),wfContext.getActor().getActorText(),wfContext.getDesc(), inputFileds, attachs);
			if(newBaseID != null && !newBaseID.equals(""))
			{
				currentWFContext.setBaseID(newBaseID);
				wfContext.setActionResult(true);
			}
			else
			{
				wfContext.setActionResult(false);
				throw new Exception("调用 apiAction.doAction为false");
			}
		}
		return newBaseID;
	}

	/**
	 * 获取工单API对象
	 * @param wfContext
	 * @return
	 */
	protected BaseAction initBaseAction(WorkflowContext wfContext)
	{
		if(apiAction == null)
		{
			apiAction = new BaseAction();
		}
		return apiAction;
	}
	
	/**
	 * 获取工单原有字段的值
	 * @param fieldName
	 * @param baseID
	 * @param baseSchema
	 * @return
	protected String getFormFieldValue(String fieldName)
	{
		apiAction.initBaseField(currentWFContext.getBaseSchema(), currentWFContext.getBaseID());
		return apiAction.getFieldValue(fieldName);
	}
	 */

	/**
	 * 处理数据异常的类
	 * @param errorString
	 * @param e
	 * @return
	 */
	private String reportException(String errorString, Exception e)
	{
		RecordLog.printLog(errorString, RecordLog.LOG_LEVEL_ERROR);
		e.printStackTrace();
		dataException(errorString, e);
		return errorString + e.getMessage();
	}
	
	protected void connectException()
	{
		
	}
	
	protected boolean isAlive()
	{
		// TODO: 完成连接监测
		return true;
	}
	
	
	/**
	 * 接口XML解析之前
	 * @return
	 * @throws Exception
	 */
	protected abstract boolean init() throws Exception;
	
	/**
	 * 接口XML解析之后，执行接口建单之前
	 * @return
	 * @throws Exception
	 */
	protected abstract boolean init2() throws Exception;
	
	protected abstract boolean dataHandle(WorkflowContext wfContext, List<Attachment> attachmentsList) throws Exception;
	
	protected abstract boolean postHandle() throws Exception;
	
	protected abstract boolean dataException(String errorString, Exception ex);

	public IWFInterfaceConfig getWfInterfaceConfig()
	{
		return wfInterfaceConfig;
	}

	public void setWfInterfaceConfig(IWFInterfaceConfig wfInterfaceConfig)
	{
		this.wfInterfaceConfig = wfInterfaceConfig;
	}

	public WfiDatainService getwfiDatainService()
	{
		return wfiDatainService;
	}

	public void setwfiDatainService(WfiDatainService wfiDatainService)
	{
		this.wfiDatainService = wfiDatainService;
	}

	public String getActionResult()
	{
		return actionResult;
	}

	public List<WorkflowContext> getWorkflowContexts()
	{
		return workflowContexts;
	}

	public DataTable getParameterTable() {
		return parameterTable;
	}

	public void setParameterTable(DataTable parameterTable) {
		this.parameterTable = parameterTable;
	}

	public String getServiceClassName() {
		return serviceClassName;
	}

	public void setServiceClassName(String serviceClassName) {
		this.serviceClassName = serviceClassName;
	}
}
