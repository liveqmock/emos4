package com.ultrapower.eoms.msextend.query.web;

import java.io.StringReader;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.ultrabpp.runtime.model.StatusFlowMapModel;
import com.ultrapower.workflow.bizconfig.sort.IWfSortManager;
import com.ultrapower.workflow.bizconfig.version.IWfVersionManager;
import com.ultrapower.workflow.client.WorkFlowServiceClient;
import com.ultrapower.workflow.configuration.sort.model.WfType;
import com.ultrapower.workflow.configuration.version.model.WfVersion;

public class QueryExtendAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<StatusFlowMapModel> statusList;
	private IWfSortManager verSort;
	private IWfVersionManager versionM;
	private String baseSchema;
	
	public String selectStatus(){
		String defCode=null;
		statusList = new ArrayList<StatusFlowMapModel>();
		if(null!=baseSchema && !"".equals(baseSchema) && baseSchema.indexOf(",")>0){
			baseSchema = baseSchema.substring(0, baseSchema.indexOf(","));
		}
		try
		{
			String designXml = "";
			if(defCode == null || defCode.equals("") || defCode.equals("null"))
			{
				verSort = WorkFlowServiceClient.clientInstance().getSortService();
				WfType type = verSort.getWfTypeByCode(baseSchema);
				if(null!=type && type.getWfType() == 1l)
				{
					defCode = type.getWfDefaultVersion();
					versionM = WorkFlowServiceClient.clientInstance().getVersionService();
					WfVersion version = versionM.getByCode(defCode);
					designXml = version.getDesignXml();
				}
			}
			else
			{
				versionM = WorkFlowServiceClient.clientInstance().getVersionService();
				WfVersion version = versionM.getByCode(defCode);
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
		return this.findForward("selectStatus");
	}

	public List<StatusFlowMapModel> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<StatusFlowMapModel> statusList) {
		this.statusList = statusList;
	}

	public IWfSortManager getVerSort() {
		return verSort;
	}

	public void setVerSort(IWfSortManager verSort) {
		this.verSort = verSort;
	}

	public IWfVersionManager getVersionM() {
		return versionM;
	}

	public void setVersionM(IWfVersionManager versionM) {
		this.versionM = versionM;
	}

	public String getBaseSchema() {
		return baseSchema;
	}

	public void setBaseSchema(String baseSchema) {
		this.baseSchema = baseSchema;
	}
	
	
}
