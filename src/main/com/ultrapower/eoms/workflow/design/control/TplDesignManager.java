package com.ultrapower.eoms.workflow.design.control;

import java.io.File;
import java.io.StringReader;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.ultrapower.eoms.workflow.design.model.BaseMapModel;
import com.ultrapower.eoms.workflow.design.model.LinkModel;
import com.ultrapower.eoms.workflow.design.model.ProcessModel;
import com.ultrapower.eoms.workflow.design.model.StatusModel;
import com.ultrapower.eoms.workflow.design.model.SubFlowModel;

import com.ultrapower.workflow.bizconfig.version.IWfVersionManager;
import com.ultrapower.workflow.client.WorkFlowServiceClient;
import com.ultrapower.workflow.configuration.version.model.WfVersion;

/**
 * 
 * Copyright (c) 2010 神州泰岳服务管理事业部通用产品
 * All rights reserved.
 * 描述：
 * @author 范莹
 * @date   2010-4-21
 */
public class TplDesignManager
{

	private List<ProcessModel> processList = new ArrayList<ProcessModel>();
	private List<LinkModel> linkList = new ArrayList<LinkModel>();
	private List<StatusModel> statusList = new ArrayList<StatusModel>();
	private Map<String, Map> processdatamap = new HashMap<String, Map>();
	private String tplidString;
	private String basecode;
	private BaseMapModel baseModel;
	private List<WfVersion> wfList = new ArrayList<WfVersion>();
	

	public TplDesignManager(String id,String basecode){
		this.tplidString = id;
		this.basecode = basecode;
	}
	public TplDesignManager(String basecode){
		this.basecode = basecode;
	}
	
	/**
	 * 核心方法，用于固定流程流程图文件解析。
	 * 在init方法中完成了一下的初始化工作
	 *  setProcessList();
	 *  setLinkList();
	 *  setStatusList();
	 *  setBaseModel();
	 *  setWfList();
	 */
	public void init(){
		String wfxml = "";
		Element rootElement = null;
		Element rootElementDesign = null;
		
		//调用流程管理接口，保存流程定义文件
	 	IWfVersionManager ver = WorkFlowServiceClient.clientInstance().getVersionService();
	 	try
		{
		WfVersion wfVersion = ver.getByCode(tplidString);
		SAXBuilder bu = new SAXBuilder();
		StringReader m_StringReader = new StringReader(wfVersion.getWorkflowXml());
		Document doc = bu.build(m_StringReader);
		rootElement = doc.getRootElement();
		setProcessdatamap(rootElement);//先于setProcessList方法执行
		
		m_StringReader = new StringReader(wfVersion.getDesignXml());
		Document docdesign = bu.build(m_StringReader);
		rootElementDesign = docdesign.getRootElement();
		setProcessList(rootElementDesign);
		setLinkList(rootElementDesign);
		setStatusList(rootElementDesign);
		setBaseModel(wfVersion);
		setWfList(ver.getSubListByBaseCode(wfVersion.getBaseCode()));
		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}
	}
	
	
	public List<ProcessModel> getProcessList(){
	    return processList;
	}
	
	/**
	 * 封装环节对象
	 * @param rootElement
	 */
	private void setProcessList(Element rootElement){
		//环节
		Element node_processes=rootElement.getChild("processes");
		List<Element> processNodeList = node_processes.getChildren("process");
		for(int i=0;i<processNodeList.size();i++){
			Element node_process = processNodeList.get(i);
			String processidString = node_process.getAttributeValue("id");
			ProcessModel processModel = new ProcessModel();
			processModel.setPId(node_process.getAttributeValue("id"));
			processModel.setPType(node_process.getAttributeValue("type"));
			processModel.setPTitle(node_process.getAttributeValue("title"));
			processModel.setPContent(node_process.getAttributeValue("content"));
			processModel.setPX(node_process.getAttributeValue("x"));
			processModel.setPY(node_process.getAttributeValue("y"));
			processModel.setPStatusId(node_process.getAttributeValue("sid"));
			processModel.setPStatusName(node_process.getAttributeValue("sname"));
			processModel.setPImageUrl(node_process.getAttributeValue("imageurl"));
			processModel.setPModelkey(node_process.getAttributeValue("modelkey"));
			processModel.setPDataMap(processdatamap.get(processidString));
			processList.add(processModel);
		}
	}


	public List<LinkModel> getLinkList() {
		return linkList;
	}

	/**
	 * 封装连线对象
	 * @param rootElement
	 */
	private void setLinkList(Element rootElement) {
		//环节
		Element node_links=rootElement.getChild("links");
		List<Element> linkNodeList = node_links.getChildren("link");
		for(int i=0;i<linkNodeList.size();i++){
			Element node_link = linkNodeList.get(i);
			LinkModel linkModel = new LinkModel();
			linkModel.setLId(node_link.getAttributeValue("id"));
			linkModel.setLType(node_link.getAttributeValue("type"));
			linkModel.setLNum(node_link.getAttributeValue("num"));
			linkModel.setLContent(node_link.getAttributeValue("content"));
			linkModel.setLBprocess(node_link.getAttributeValue("bprocess"));
			linkModel.setLBpoint(node_link.getAttributeValue("bpoint"));
			linkModel.setLEprocess(node_link.getAttributeValue("eprocess"));
			linkModel.setLEpoint(node_link.getAttributeValue("epoint"));
			
			linkList.add(linkModel);
		}
		
	}


	public List<StatusModel> getStatusList() {
		return statusList;
	}

	/**
	 * 封装流程图状态对象
	 * @param rootElement
	 */
	private void setStatusList(Element rootElement) {
		//状态
		Element node_statuses=rootElement.getChild("statuses");
		List<Element> statusNodeList = node_statuses.getChildren("status");
		for(int i=0;i<statusNodeList.size();i++){
			Element node_status = statusNodeList.get(i);
			StatusModel statusModel = new StatusModel();
			statusModel.setSid(node_status.getAttributeValue("id"));
			statusModel.setSindex(node_status.getAttributeValue("index"));
			statusModel.setSname(node_status.getAttributeValue("name"));
			statusModel.setSdesc(node_status.getAttributeValue("desc"));
			statusModel.setSwidth(node_status.getAttributeValue("width"));
			
			statusList.add(statusModel);
		}
		
	}


	private Map<String, Map> getProcessdatamap() {
		return processdatamap;
	}
	
	/**
	 * 封装环节数据对象
	 * @param rootElement
	 */
	private void setProcessdatamap(Element rootElement) {
		//steps
		Element node_steps=rootElement.getChild("steps");
		List<Element> stepsList = node_steps.getChildren("step");
		for (int i = 0; i < stepsList.size(); i++) {
			Element node_step = stepsList.get(i);
			String stepId = node_step.getAttributeValue("id");
			Map attributeMap = new HashMap();
			
			attributeMap.put("desc", node_step.getAttributeValue("desc"));
			
			attributeMap.put("taskPolicy", node_step.getAttributeValue("taskPolicy")!=null?node_step.getAttributeValue("taskPolicy"):"");
			attributeMap.put("assignOver", node_step.getAttributeValue("assignOver")!=null?node_step.getAttributeValue("assignOver"):"");
			attributeMap.put("acceptOver", node_step.getAttributeValue("acceptOver")!=null?node_step.getAttributeValue("acceptOver"):"");
			attributeMap.put("dealOver", node_step.getAttributeValue("dealOver")!=null?node_step.getAttributeValue("dealOver"):"");
			
			
			//actor封装
			Element node_actor = node_step.getChild("actor");
			attributeMap.put("roleID", node_actor.getAttributeValue("roleID"));
			attributeMap.put("roleName", node_actor.getAttributeValue("roleName"));
			attributeMap.put("roleProcessRoleType", node_actor.getAttributeValue("actorType"));
			String actortextString = node_actor.getText();
			String[] actorvalues =actortextString.split("#");
			for (int j = 0; j < actorvalues.length; j++) {
				String[] actorvalueString = actorvalues[j].split(":");
				if(actorvalueString.length>1){
						attributeMap.put(actorvalueString[0], actorvalueString[1]);
				}
			}
			
			//子函数封装
			Element node_subflows = node_step.getChild("subflows");
			Element node_subflowcondition = node_subflows!=null?node_subflows.getChild("condition"):null;
			String[] subflowconditions = node_subflows!=null?node_subflowcondition.getText().split("#"):null;
			List<Element> subflowList = node_subflows!=null?node_subflows.getChildren("subflow"):new ArrayList<Element>();
			if(subflowList.size()>0)
				attributeMap.put("hasSubFlow", "1");
			else attributeMap.put("hasSubFlow", "0");
			List<SubFlowModel> wfList = new ArrayList<SubFlowModel>();
			for (int j = 0; j < subflowList.size(); j++) {
				Element node_subflow = subflowList.get(j);
				SubFlowModel subFlowModel = new SubFlowModel();
				subFlowModel.setCode(node_subflow.getAttributeValue("type"));
				subFlowModel.setName(node_subflow.getAttributeValue("name"));
				for (int k = 0; k < subflowconditions.length; k++) {
					String[] subflowcondition = subflowconditions[k].split(":");
					if(subflowcondition[0].equals(node_subflow.getAttributeValue("type"))&&subflowcondition.length>1)
					{
						subFlowModel.setSubflowCondition(subflowcondition[1]);
					}
				}
				wfList.add(subFlowModel);
			}
			attributeMap.put("subFlowList", wfList);
			
			//前置函数封装
			Element node_prefuncs = node_step.getChild("prefuncs");
			String prefunctString = "";
			List<Element> prefuncsList = node_prefuncs.getChildren("function");
			for (int j = 0; j < prefuncsList.size(); j++) {
				Element functionElement = prefuncsList.get(j);
				if(j==0)prefunctString = functionElement.getText();
				else prefunctString += ","+functionElement.getText();
			}
			attributeMap.put("prefunction", prefunctString);
			
			//后置函数封装
			Element node_postfuncs = node_step.getChild("postfuncs");
			String postfunctString = "";
			List<Element> postfuncsList = node_postfuncs.getChildren("function");
			for (int j = 0; j < postfuncsList.size(); j++) {
				Element functionElement = postfuncsList.get(j);
				if(j==0)postfunctString = functionElement.getText();
				else postfunctString += ","+functionElement.getText();
			}
			attributeMap.put("postfunction", postfunctString);
			
			//属性封装
			Element node_metas = node_step.getChild("metas");
			Map metaMap = new HashMap<String, String>();
			List<Element> metasList = node_metas!=null?node_metas.getChildren("meta"):new ArrayList<Element>();
			for (int j = 0; j < metasList.size(); j++) {
				Element node_meta = metasList.get(j);
				metaMap.put(node_meta.getAttributeValue("name"), node_meta.getText());
			}
			attributeMap.put("metaMap", metaMap);
			
			processdatamap.put(stepId, attributeMap);
		}
		
		//split
		Element node_splits=rootElement.getChild("splits");
		List<Element> splitsList = node_splits.getChildren("split");
		for (int i = 0; i < splitsList.size(); i++) {
			Map attributeMap = new HashMap();
			Element node_split = splitsList.get(i);
			String splitidString = node_split.getAttributeValue("id");
			Element node_condition = node_split.getChild("condition");
			String conditiontypeString = node_condition.getAttributeValue("type");
			String conditionClassString = node_condition.getAttributeValue("conditionClass");
			String conditionvalueString = node_condition.getText();
			attributeMap.put("conditionType", conditiontypeString);
			
			String[] metaStrings = conditionvalueString.split("#");
			Map metaMap = new HashMap<String, String>();
			for (int j = 0; j < metaStrings.length; j++) {
				String[] kv = metaStrings[j].split(":");
				if(kv.length>1)metaMap.put(kv[0], kv[1]);
				else metaMap.put(kv[0], "");
			}
			attributeMap.put("metaMap", metaMap);
			
			if(conditiontypeString.equals("script")){
				attributeMap.put("conditionClass", "");
			}else{
				attributeMap.put("conditionClass", conditionClassString);
			}
			processdatamap.put(splitidString, attributeMap);
		}
		
		//join
		Element node_joins=rootElement.getChild("joins");
		List<Element> joinsList = node_joins.getChildren("join");
		for (int i = 0; i < joinsList.size(); i++) {
			Map attributeMap = new HashMap();
			
			Element node_join = joinsList.get(i); 
			String joinidString = node_join.getAttributeValue("id");
			Element node_condition =  node_join.getChild("condition");
			attributeMap.put("condition", node_condition.getText());
			
			processdatamap.put(joinidString, attributeMap);
		}
	}
	
	public BaseMapModel getBaseModel() {
		return baseModel;
	}
	
	/**
	 * 封装流程图基础信息对象
	 * @param rootElement
	 */
	private void setBaseModel(WfVersion wfVersion) {
		baseModel = new BaseMapModel();
		baseModel.setId(wfVersion.getId());
		baseModel.setBasecode(wfVersion.getBaseCode());
		baseModel.setBasename(wfVersion.getBaseName());
		baseModel.setCode(wfVersion.getCode());
		baseModel.setName(wfVersion.getName());
		baseModel.setActive(new Long(wfVersion.getIsPublish()).toString());
		baseModel.setCreatedate(new Long(wfVersion.getCreateTime()).toString());
		baseModel.setStartdate(new Long(wfVersion.getPublishTime()).toString());
		baseModel.setDesc(wfVersion.getRemark());
		baseModel.setSubflow(wfVersion.getSubflag()+"");
	}
	public List<WfVersion> getWfList() {
		return wfList;
	}
	
	/**
	 * 封装流程图选择的子流程列表
	 * @param rootElement
	 */
	private void setWfList(List<WfVersion> wfList) {
		for (int i = 0; i < wfList.size(); i++) {
			WfVersion wfVersion = wfList.get(i);
			if(wfVersion.getCode().equals(tplidString))wfList.remove(i);
		}
		this.wfList = wfList;
	}
	
	public List<WfVersion> getSubFlowWfListByBaseCode() {
		
		//调用流程管理接口，保存流程定义文件
	 	IWfVersionManager ver = WorkFlowServiceClient.clientInstance().getVersionService();
	 	List sfList = new ArrayList();
		try {
			sfList = ver.getSubListByBaseCode(basecode);
		} catch (RemoteException e) {
			System.out.println(e.toString());
		}
		return sfList;
	}
}






