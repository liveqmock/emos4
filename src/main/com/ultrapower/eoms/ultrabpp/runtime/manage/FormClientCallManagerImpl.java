package com.ultrapower.eoms.ultrabpp.runtime.manage;

import java.io.StringReader;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.util.NumberUtils;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.common.portal.service.PortalManagerService;
import com.ultrapower.eoms.ultrabpp.model.component.datafield.selectfield.SelectMenu;
import com.ultrapower.eoms.ultrabpp.runtime.model.ClientCallDataModel;
import com.ultrapower.eoms.ultrabpp.runtime.model.MenuModel;
import com.ultrapower.eoms.ultrabpp.runtime.model.StatusFlowMapModel;
import com.ultrapower.eoms.ultrabpp.runtime.service.WorkflowService;
import com.ultrapower.eoms.ultrasm.model.DicItem;
import com.ultrapower.eoms.ultrasm.service.DicManagerService;
import com.ultrapower.workflow.bizconfig.sort.IWfSortManager;
import com.ultrapower.workflow.bizconfig.version.IWfVersionManager;
import com.ultrapower.workflow.client.WorkFlowServiceClient;
import com.ultrapower.workflow.configuration.sort.model.WfType;
import com.ultrapower.workflow.configuration.version.model.WfVersion;
import com.ultrapower.workflow.engine.task.model.ProcessTask;

public class FormClientCallManagerImpl extends AbsClientCallAdapter
{
	private DicManagerService dicManagerService;
	private PortalManagerService portalManagerService;
	private WorkflowService workflowService;
	private IWfVersionManager ver;
	private IWfSortManager verSort;

	@Override
	public ClientCallDataModel<?> call(String methodName, Map<String, String> parameters)
	{
		if(methodName.equals("getDicData") && parameters.get("dicType").equals("bean")){
			SelectMenu sm =  (SelectMenu) WebApplicationManager.getBean(parameters.get("dicResName"));
			try {
				return (ClientCallDataModel<?>) sm.getMenus();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}else if(methodName.equals("getDicData")){
			List<MenuModel> datas = getDicData(
					parameters.get("dicType")
					,parameters.get("dicResName")
					,parameters.get("dicKeyName")
					,parameters.get("dicValueName")
					,parameters.get("dicParas"));
			ClientCallDataModel<MenuModel> model = new ClientCallDataModel<MenuModel>();
			model.setData(datas);
			return model;
		}
		else if(methodName.equals("getRollbackList"))
		{
			List<Map<String, String>> datas = getRollbackList(
					parameters.get("baseID")
					,parameters.get("baseSchema")
					,parameters.get("taskID")
					,parameters.get("actionType")
					,parameters.get("loginName"));
			ClientCallDataModel<Map<String, String>> model = new ClientCallDataModel<Map<String, String>>();
			model.setData(datas);
			return model;
		}
		else if(methodName.equals("getStatusFlowMap"))
		{
			List<StatusFlowMapModel> datas = getStatusFlowMap(
					parameters.get("baseSchema")
					,parameters.get("defCode"));
			ClientCallDataModel<StatusFlowMapModel> model = new ClientCallDataModel<StatusFlowMapModel>();
			model.setData(datas);
			return model;
		}
		else if(methodName.equals("getEndpointProcess"))
		{
			List<Map<String, String>> datas = getEndpointProcess(
					parameters.get("baseID")
					,parameters.get("baseSchema"));
			ClientCallDataModel<Map<String, String>> model = new ClientCallDataModel<Map<String, String>>();
			model.setData(datas);
			return model;
		}else{
			return null;
		}
	}
	
	/**
	 * 获取字典数据
	 * @param dicType 字典类型
	 * @param dicResName 数据源
	 * @param dicKeyName 数据key
	 * @param dicValueName 数据value
	 * @param dicParas 参数
	 * @return
	 */
	private List<MenuModel> getDicData(String dicType, String dicResName, String dicKeyName, String dicValueName, String dicParas)
	{
		List<MenuModel> dicList = new ArrayList<MenuModel>();
		if(dicType.equals("table"))
		{
			try
			{
				String cells = "";
				if(dicKeyName.equals(dicValueName)) cells = dicValueName;
				else cells = dicKeyName + ", " + dicValueName;
				StringBuilder querySql = new StringBuilder("select " + cells + " from " + dicResName);
				if(dicParas != null && !dicParas.equals(""))
				{
					querySql.append(" where " + dicParas);
				}
				querySql.append(" order by " + dicValueName + " asc");
				QueryAdapter qa = new QueryAdapter();
				DataTable dicData = qa.executeQuery(querySql.toString());
				if(dicData != null)
				{
					List<DataRow> dicRows = dicData.getRowList();
					for(DataRow dicRow : dicRows)
					{
						String key = dicRow.getString(dicKeyName);
						if(key != null && !key.equals(""))
						{
							String value = StringUtils.checkNullString(dicRow.getString(dicValueName));
							
							if(key.indexOf(".") > 0)
							{
								buildMenuTree(dicList, key, value, 0);
							}
							else
							{
								boolean hasItem = false;
								for(MenuModel lvm : dicList)
								{
									if(lvm.getValue().equals(key))
									{
										dicList.add(lvm);
										hasItem = true;
										break;
									}
								}
								if(!hasItem)
								{
									MenuModel menu = new MenuModel();
									menu.setValue(key);
									menu.setText(value);
									menu.setDnIDs(key);
									menu.setDns(value);
									dicList.add(menu);
								}
							}
						}
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else if(dicType.equals("collect"))
		{
			String[] items = dicResName.split(",");
			for(String item : items)
			{
				String[] itemarr = item.split(":");
				String key = itemarr[NumberUtils.formatToInt(dicKeyName)];
				String value = itemarr[NumberUtils.formatToInt(dicValueName)];
				MenuModel menu = new MenuModel();
				menu.setValue(key);
				menu.setText(value);
				menu.setDnIDs(key);
				menu.setDns(value);
				dicList.add(menu);
			}
		}
		else if(dicType.equals("sysdic"))
		{
			List<DicItem> items = dicManagerService.getDicItemByFullName(dicResName, dicParas);
			
			for(DicItem item : items)
			{
				String key = item.getDivalue();
				String value = item.getDiname();
				MenuModel menu = new MenuModel();
				menu.setValue(value);
				menu.setText(value);
				menu.setDnIDs(value);
				menu.setDns(value);
				dicList.add(menu);
			}
		}
		
		return dicList;
	}
	
	private void buildMenuTree(List<MenuModel> menuTree, String key, String value, int level)
	{
		String[] keys = key.split("\\.");
		if(keys.length == level) return;
		String[] values = value.split("\\.");
		String lv = keys[level];
		MenuModel lvM = null;
		boolean hasItem = false;
		for(MenuModel lvm : menuTree)
		{
			if(lvm.getValue().equals(lv))
			{
				hasItem = true;
				lvM = lvm;
				break;
			}
		}
		if(!hasItem)
		{
			MenuModel menu = new MenuModel();
			String keydn = "";
			String valuedn = "";
			for(int i = 0; i < level+1; i++) keydn += "." + keys[i];
			for(int i = 0; i < level+1; i++) valuedn += "." + values[i];
			menu.setValue(keys[level]);
			menu.setText(values[level]);
			menu.setDnIDs(keydn.substring(1));
			menu.setDns(valuedn.substring(1));
			menuTree.add(menu);
			lvM = menu;
		}
		buildMenuTree(lvM.getSubMenus(), key, value, level+1);
	}
	
	/**
	 * 获取回滚列表
	 * @param baseID 工单ID
	 * @param baseSchema 工单类型
	 * @param taskID 当前任务ID
	 * @param actionType 动作类型
	 * @param loginName 当前人登录名
	 * @return
	 */
	private List<Map<String, String>> getRollbackList(String baseID, String baseSchema, String taskID, String actionType, String loginName)
	{
		UserSession userInfo = portalManagerService.getUserSession(loginName);
		List<ProcessTask> taskList = workflowService.getRollbackTasks(baseID, baseSchema, userInfo, taskID, actionType);
		
		List<Map<String, String>> rollbackList = new ArrayList<Map<String,String>>();
		if(taskList != null)
		{
			for(ProcessTask task : taskList)
			{
				Map<String, String> rollbackItem = new HashMap<String, String>();
				rollbackItem.put("stepid", task.getStepId());
				rollbackItem.put("stepcode", StringUtils.checkNullString(task.getStepCode()));
				rollbackItem.put("desc", StringUtils.checkNullString(task.getTaskName()));
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
				rollbackItem.put("dealer", dealer);
				rollbackItem.put("dealerdn", dealerdn);
				rollbackItem.put("status", task.getProcessState());
				
				rollbackList.add(rollbackItem);
			}
		}
		return rollbackList;
	}
	
	private List<StatusFlowMapModel> getStatusFlowMap(String baseSchema, String defCode)
	{
		List<StatusFlowMapModel> statusList = new ArrayList<StatusFlowMapModel>();
		try
		{
			String designXml = "";
			if(defCode == null || defCode.equals("") || defCode.equals("null"))
			{
				verSort = WorkFlowServiceClient.clientInstance().getSortService();
				WfType type = verSort.getWfTypeByCode(baseSchema);
				if(type.getWfType() == 1l)
				{
					defCode = type.getWfDefaultVersion();
					ver = WorkFlowServiceClient.clientInstance().getVersionService();
					WfVersion version = ver.getByCode(defCode);
					designXml = version.getDesignXml();
				}
			}
			else
			{
				ver = WorkFlowServiceClient.clientInstance().getVersionService();
				WfVersion version = ver.getByCode(defCode);
				designXml = version.getDesignXml();
			}
			
			if(designXml != null && !designXml.equals(""))
			{
				SAXReader reader = new SAXReader();
				Document doc = null;
				try
				{
					doc = reader.read(new StringReader(designXml.replaceAll("\r", "").replaceAll("\n", "")));
				}
				catch (DocumentException e)
				{
					e.printStackTrace();
				}
				if(doc != null)
				{
					List<Node> nodes = doc.selectNodes("/design/statuses/status");
					for(Node node : nodes)
					{
						StatusFlowMapModel model = new StatusFlowMapModel();
						model.setCode(node.valueOf("@id"));
						model.setName(node.valueOf("@name"));
						model.setDesc(node.valueOf("@desc"));
						statusList.add(model);
					}
				}
			}
		}
		catch (RemoteException e)
		{
			e.printStackTrace();
		}
		return statusList;
	}
	
	private List<Map<String, String>> getEndpointProcess(String baseID, String baseSchema)
	{
		List<Map<String, String>> pList = new ArrayList<Map<String,String>>();
		
		QueryAdapter qa = new QueryAdapter();
		String querySql = "select phaseno, processstatus, flagactive, actortype, assigneeid, assignee, assigneedep, assigneecorp assigneedn, assigngroup, flagassigntype, dealerid, dealer, dealerdep, dealercorp, dealerdn, stdate, bgdate, eddate, dpdesc from bs_t_wf_dealprocess t where t.flagendduplicated = 1 and t.flagendphase = 1 and baseid=? and baseschema=?";
		DataTable table = qa.executeQuery(querySql, baseID, baseSchema);
		
		List<DataRow> rows = table.getRowList();
		
		for(DataRow row : rows)
		{
			Map<String, String> data = new HashMap<String, String>();
			data.put("phaseno", row.getString("PHASENO"));
			data.put("flagactive", row.getString("FLAGACTIVE"));
			pList.add(data);
		}
		
		return pList;
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
}
