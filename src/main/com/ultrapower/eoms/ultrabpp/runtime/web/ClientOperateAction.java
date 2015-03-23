package com.ultrapower.eoms.ultrabpp.runtime.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import net.sf.json.JSONObject;

import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.common.portal.service.PortalManagerService;
import com.ultrapower.eoms.ultrabpp.runtime.service.ClientCallAdapterService;
import com.ultrapower.eoms.ultrabpp.runtime.service.WorkflowService;
import com.ultrapower.eoms.ultrasm.service.DicManagerService;
import com.ultrapower.workflow.engine.task.model.ProcessTask;

public class ClientOperateAction extends BaseAction
{
	private static final long serialVersionUID = -7846376504222688287L;

	private WorkflowService workflowService;
	
	private String baseID;
	private String baseSchema;
	private String taskID;
	private String actionType;
	private PortalManagerService portalManagerService;
	private DicManagerService dicManagerService;
	
	private String service;
	private String method;
	private String paras;
	
	public String getRollbackList()
	{
		UserSession userInfo = portalManagerService.getUserSession(getUserSession().getLoginName());
		List<ProcessTask>  taskList = workflowService.getRollbackTasks(baseID, baseSchema, userInfo, taskID, actionType);
		
		try
		{
			PrintWriter out = getResponse().getWriter();
			getResponse().setContentType("text/xml;charset=UTF-8");
			out.print("<rollback-list>");
			if(taskList != null)
			{
				for(ProcessTask task : taskList)
				{
					out.print("<task>");
					out.print("<stepid>" + task.getStepId() + "</stepid>");
					out.print("<stepcode>" + StringUtils.checkNullString(task.getStepCode()) + "</stepcode>");
					out.print("<desc>" + StringUtils.checkNullString(task.getTaskName()) + "</desc>");
					String dealer = task.getDealer();
					String dealerdn = StringUtils.checkNullString(task.getDealerDn());
					if(dealer == null || dealer.equals(""))
					{
						dealer = task.getAssignee();
						dealerdn = StringUtils.checkNullString(task.getAssigneeDn());
					}
					if(dealer == null || dealer.equals(""))
					{
						dealer = task.getAssignGroup();
						dealerdn = "";
					}
					if(dealerdn.equals("")) dealerdn = "无";
					out.print("<dealer>" + dealer + "</dealer>");
					out.print("<dealerdn>" + dealerdn + "</dealerdn>");
					out.print("<status>" + task.getProcessState() + "</status>");
					out.print("</task>");
				}
			}
			out.print("</rollback-list>");
			out.close();
			out.flush();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String callService()
	{
		Map<String, String> paraMap = null;
		try
		{
			paraMap = (Map<String, String>)JSONObject.fromObject(paras);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		String returnStr = "";
		
		try
		{
			ClientCallAdapterService callService = (ClientCallAdapterService)WebApplicationManager.getBean(service);
			returnStr = callService.call(service, method, paraMap);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			returnStr = "[]";
		}
		try
		{
			PrintWriter out = getResponse().getWriter();
			getResponse().setContentType("text/html;charset=UTF-8");
			out.print(returnStr);
			out.close();
			out.flush();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @return the baseID
	 */
	public String getBaseID()
	{
		return baseID;
	}

	/**
	 * @param baseID the baseID to set
	 */
	public void setBaseID(String baseID)
	{
		this.baseID = baseID;
	}

	/**
	 * @return the baseSchema
	 */
	public String getBaseSchema()
	{
		return baseSchema;
	}

	/**
	 * @param baseSchema the baseSchema to set
	 */
	public void setBaseSchema(String baseSchema)
	{
		this.baseSchema = baseSchema;
	}

	/**
	 * @return the taskID
	 */
	public String getTaskID()
	{
		return taskID;
	}

	/**
	 * @param taskID the taskID to set
	 */
	public void setTaskID(String taskID)
	{
		this.taskID = taskID;
	}

	/**
	 * @return the actionType
	 */
	public String getActionType()
	{
		return actionType;
	}

	/**
	 * @param actionType the actionType to set
	 */
	public void setActionType(String actionType)
	{
		this.actionType = actionType;
	}

	/**
	 * @return the workflowService
	 */
	public WorkflowService getWorkflowService()
	{
		return workflowService;
	}

	/**
	 * @param workflowService the workflowService to set
	 */
	public void setWorkflowService(WorkflowService workflowService)
	{
		this.workflowService = workflowService;
	}

	/**
	 * @return the portalManagerService
	 */
	public PortalManagerService getPortalManagerService()
	{
		return portalManagerService;
	}

	/**
	 * @param portalManagerService the portalManagerService to set
	 */
	public void setPortalManagerService(PortalManagerService portalManagerService)
	{
		this.portalManagerService = portalManagerService;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	/**
	 * @return the dicManagerService
	 */
	public DicManagerService getDicManagerService()
	{
		return dicManagerService;
	}

	/**
	 * @param dicManagerService the dicManagerService to set
	 */
	public void setDicManagerService(DicManagerService dicManagerService)
	{
		this.dicManagerService = dicManagerService;
	}

	/**
	 * @return the service
	 */
	public String getService()
	{
		return service;
	}

	/**
	 * @param service the service to set
	 */
	public void setService(String service)
	{
		this.service = service;
	}

	/**
	 * @return the paras
	 */
	public String getParas()
	{
		return paras;
	}

	/**
	 * @param paras the paras to set
	 */
	public void setParas(String paras)
	{
		this.paras = paras;
	}

	/**
	 * @return the method
	 */
	public String getMethod()
	{
		return method;
	}

	/**
	 * @param method the method to set
	 */
	public void setMethod(String method)
	{
		this.method = method;
	}
}
