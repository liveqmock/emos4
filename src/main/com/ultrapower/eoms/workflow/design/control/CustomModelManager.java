package com.ultrapower.eoms.workflow.design.control;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import com.ultrapower.eoms.common.core.util.WebApplicationManager;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.ultrapower.eoms.ultrabpp.cache.model.ActionMeta;
import com.ultrapower.eoms.ultrabpp.cache.model.ActionModel;
import com.ultrapower.eoms.ultrabpp.cache.model.StepMeta;
import com.ultrapower.eoms.ultrabpp.cache.model.StepModel;
import com.ultrapower.eoms.ultrabpp.cache.model.WorksheetMeta;
import com.ultrapower.eoms.ultrabpp.develop.service.TemplateService;
import com.ultrapower.eoms.workflow.design.model.CustomModel;
import com.ultrapower.eoms.workflow.design.model.FunctionModel;
import com.ultrapower.eoms.workflow.design.model.ModelMeta;
import com.ultrapower.workflow.bizconfig.custom.IModelManager;
import com.ultrapower.workflow.bizconfig.interfaces.IInterfaceManager;
import com.ultrapower.workflow.client.WorkFlowServiceClient;
import com.ultrapower.workflow.configuration.custom.model.WfModel;
import com.ultrapower.workflow.configuration.custom.model.WfModelProperties;
import com.ultrapower.workflow.configuration.interfaces.model.WfInterface;

/**
 * 
 * Copyright (c) 2010 神州泰岳服务管理事业部通用产品 All rights reserved. 描述：
 * 
 * @author 范莹
 * @date 2010-4-21
 */
public class CustomModelManager {

	private List<CustomModel> modelList;
	private TemplateService templateService;
	private ServletContext servletContext;

	public String getCustomModelString(String type) throws RemoteException {
		IModelManager modelManager = WorkFlowServiceClient.clientInstance().getModelService();
		IInterfaceManager interfaceManager = WorkFlowServiceClient.clientInstance().getInterfaceService();
		List<WfModel> wfmoList = modelManager.getWfModelByWftype(type);
		List<CustomModel> modelList = new ArrayList<CustomModel>();
		Element root = new Element("CustomModels");
		Document doc = new Document(root);

		for (int i = 0; i < wfmoList.size(); i++) {
			Element modelElement = new Element("CustomModel");
			WfModel wfModel = wfmoList.get(i);
			CustomModel model = new CustomModel();

			// 封装属性
			List<ModelMeta> metaList = new ArrayList<ModelMeta>();
			List<WfModelProperties> wfproperties = modelManager.getModelPropsByCode(wfModel.getCode());
			Element metas = new Element("metas");
			for (int j = 0; j < wfproperties.size(); j++) {
				WfModelProperties wfproperty = wfproperties.get(j);
				Element meta = new Element("meta");
				meta.addContent(new Element("metakey").setText(wfproperty.getCode()));
				meta.addContent(new Element("metaname").setText(wfproperty.getName()));
				meta.addContent(new Element("metatype").setText(wfproperty.getType()));
				meta.addContent(new Element("metadict").setText(wfproperty.getDict()));
				meta.addContent(new Element("mandatory").setText(wfproperty.getIsNull() + ""));
				metas.addContent(meta);
				ModelMeta metaModel = new ModelMeta();
				metaModel.setMetakey(wfproperty.getCode());
				metaModel.setMetaname(wfproperty.getName());
				metaModel.setMetatype(wfproperty.getType());
				metaModel.setMetadict(wfproperty.getDict());
				metaModel.setMandatory(wfproperty.getIsNull() + "");
				metaList.add(metaModel);
			}
			modelElement.addContent(metas);
			model.setMetaList(metaList);

			// 前置函数封装
			List<FunctionModel> preList = new ArrayList<FunctionModel>();
			Element prefunctionsElement = new Element("prefunctions");
			String prefunctionString = wfModel.getPreFunction();
			if (prefunctionString != null && prefunctionString.length() > 0) {
				String[] prefunctions = prefunctionString.split(",");
				for (int j = 0; j < prefunctions.length; j++) {
					WfInterface wfInterface = interfaceManager.getInteByCode(prefunctions[j]);
					Element prefunction = new Element("prefunction");
					prefunction.addContent(new Element("functionId").setText(wfInterface.getCode()));
					prefunction.addContent(new Element("functionName").setText(wfInterface.getName()));
					prefunctionsElement.addContent(prefunction);
					FunctionModel preFunctionModel = new FunctionModel();
					preFunctionModel.setFId(wfInterface.getCode());
					preFunctionModel.setFName(wfInterface.getName());
					preList.add(preFunctionModel);
				}
			}
			modelElement.addContent(prefunctionsElement);
			model.setPrefunction(preList);

			// 后置函数封装
			List<FunctionModel> postList = new ArrayList<FunctionModel>();
			Element postfunctionsElement = new Element("postfunctions");
			String postfunctionString = wfModel.getPostFunction();
			if (postfunctionString != null && postfunctionString.length() > 0) {
				String[] postfunctions = postfunctionString.split(",");
				for (int j = 0; j < postfunctions.length; j++) {
					WfInterface wfInterface = interfaceManager.getInteByCode(postfunctions[j]);
					Element postfunction = new Element("postfunction");
					postfunction.addContent(new Element("functionId").setText(wfInterface.getCode()));
					postfunction.addContent(new Element("functionName").setText(wfInterface.getName()));
					postfunctionsElement.addContent(postfunction);
					FunctionModel postFunctionModel = new FunctionModel();
					postFunctionModel.setFId(wfInterface.getCode());
					postFunctionModel.setFName(wfInterface.getName());
					postList.add(postFunctionModel);
				}
			}
			modelElement.addContent(postfunctionsElement);
			model.setPostfunction(postList);

			// 接口封装
			List<FunctionModel> interfunctionList = new ArrayList<FunctionModel>();
			Element interfunctionsElement = new Element("interfunctions");
			List<WfInterface> wfinterfaceList = interfaceManager.getAllInte();// interfaceManager.getInterfaceListByType(type);
			for (int j = 0; j < wfinterfaceList.size(); j++) {
				WfInterface wfInterface = wfinterfaceList.get(j);
				Element interfunction = new Element("interfunction");
				interfunction.addContent(new Element("functionId").setText(wfInterface.getCode()));
				interfunction.addContent(new Element("functionName").setText(wfInterface.getName()));
				interfunctionsElement.addContent(interfunction);
				FunctionModel interFunctionModel = new FunctionModel();
				interFunctionModel.setFId(wfInterface.getCode());
				interFunctionModel.setFName(wfInterface.getName());
				interfunctionList.add(interFunctionModel);
			}
			modelElement.addContent(interfunctionsElement);
			model.setInterfunction(interfunctionList);

			// 模型基本信息封装
			modelElement.addContent(new Element("modelkey").setText(wfModel.getCode()));
			modelElement.addContent(new Element("modelname").setText(wfModel.getName()));
			modelElement.addContent(new Element("imageurl").setText(wfModel.getPhotoPath()));
			root.addContent(modelElement);

			model.setModelkey(wfModel.getCode());
			model.setModelname(wfModel.getName());
			model.setModeltype(wfModel.getType());
			model.setImageurl(wfModel.getPhotoPath());
			model.setModeldesc(wfModel.getRemark());
			modelList.add(model);
		}
		this.modelList = modelList;
		Format format = Format.getPrettyFormat();// 
		XMLOutputter outp = new XMLOutputter(format);

		return outp.outputString(doc);
	}
	
	
	public String getCustomModelString(String type,String baseSchema) throws RemoteException {
		IModelManager modelManager = WorkFlowServiceClient.clientInstance().getModelService();
		IInterfaceManager interfaceManager = WorkFlowServiceClient.clientInstance().getInterfaceService();
		List<WfModel> wfmoList = modelManager.getWfModelByWftype(type);
		List<CustomModel> modelList = new ArrayList<CustomModel>();
		Element root = new Element("CustomModels");
		Document doc = new Document(root);

		for (int i = 0; i < wfmoList.size(); i++) {
			Element modelElement = new Element("CustomModel");
			WfModel wfModel = wfmoList.get(i);
			CustomModel model = new CustomModel();

			// 封装属性
			List<ModelMeta> metaList = new ArrayList<ModelMeta>();
			List<WfModelProperties> wfproperties = modelManager.getModelPropsByCode(wfModel.getCode());
			Element metas = new Element("metas");
			for (int j = 0; j < wfproperties.size(); j++) {
				WfModelProperties wfproperty = wfproperties.get(j);
				Element meta = new Element("meta");
				meta.addContent(new Element("metakey").setText(wfproperty.getCode()));
				meta.addContent(new Element("metaname").setText(wfproperty.getName()));
				meta.addContent(new Element("metatype").setText(wfproperty.getType()));
				meta.addContent(new Element("metadict").setText(wfproperty.getDict()));
				meta.addContent(new Element("mandatory").setText(wfproperty.getIsNull() + ""));
				metas.addContent(meta);
				ModelMeta metaModel = new ModelMeta();
				metaModel.setMetakey(wfproperty.getCode());
				metaModel.setMetaname(wfproperty.getName());
				metaModel.setMetatype(wfproperty.getType());
				metaModel.setMetadict(wfproperty.getDict());
				metaModel.setMandatory(wfproperty.getIsNull() + "");
				metaList.add(metaModel);
			}
			modelElement.addContent(metas);
			model.setMetaList(metaList);

			// 前置函数封装
			List<FunctionModel> preList = new ArrayList<FunctionModel>();
			Element prefunctionsElement = new Element("prefunctions");
			String prefunctionString = wfModel.getPreFunction();
			if (prefunctionString != null && prefunctionString.length() > 0) {
				String[] prefunctions = prefunctionString.split(",");
				for (int j = 0; j < prefunctions.length; j++) {
					WfInterface wfInterface = interfaceManager.getInteByCode(prefunctions[j]);
					Element prefunction = new Element("prefunction");
					prefunction.addContent(new Element("functionId").setText(wfInterface.getCode()));
					prefunction.addContent(new Element("functionName").setText(wfInterface.getName()));
					prefunctionsElement.addContent(prefunction);
					FunctionModel preFunctionModel = new FunctionModel();
					preFunctionModel.setFId(wfInterface.getCode());
					preFunctionModel.setFName(wfInterface.getName());
					preList.add(preFunctionModel);
				}
			}
			modelElement.addContent(prefunctionsElement);
			model.setPrefunction(preList);

			// 后置函数封装
			List<FunctionModel> postList = new ArrayList<FunctionModel>();
			Element postfunctionsElement = new Element("postfunctions");
			String postfunctionString = wfModel.getPostFunction();
			if (postfunctionString != null && postfunctionString.length() > 0) {
				String[] postfunctions = postfunctionString.split(",");
				for (int j = 0; j < postfunctions.length; j++) {
					WfInterface wfInterface = interfaceManager.getInteByCode(postfunctions[j]);
					Element postfunction = new Element("postfunction");
					postfunction.addContent(new Element("functionId").setText(wfInterface.getCode()));
					postfunction.addContent(new Element("functionName").setText(wfInterface.getName()));
					postfunctionsElement.addContent(postfunction);
					FunctionModel postFunctionModel = new FunctionModel();
					postFunctionModel.setFId(wfInterface.getCode());
					postFunctionModel.setFName(wfInterface.getName());
					postList.add(postFunctionModel);
				}
			}
			modelElement.addContent(postfunctionsElement);
			model.setPostfunction(postList);

			// 接口封装
			List<FunctionModel> interfunctionList = new ArrayList<FunctionModel>();
			Element interfunctionsElement = new Element("interfunctions");
			List<WfInterface> wfinterfaceList = interfaceManager.getAllInte();// interfaceManager.getInterfaceListByType(type);
			for (int j = 0; j < wfinterfaceList.size(); j++) {
				WfInterface wfInterface = wfinterfaceList.get(j);
				Element interfunction = new Element("interfunction");
				interfunction.addContent(new Element("functionId").setText(wfInterface.getCode()));
				interfunction.addContent(new Element("functionName").setText(wfInterface.getName()));
				interfunctionsElement.addContent(interfunction);
				FunctionModel interFunctionModel = new FunctionModel();
				interFunctionModel.setFId(wfInterface.getCode());
				interFunctionModel.setFName(wfInterface.getName());
				interfunctionList.add(interFunctionModel);
			}
			modelElement.addContent(interfunctionsElement);
			model.setInterfunction(interfunctionList);

			//fany 修改 添加环节和动作关联关系
			templateService = (TemplateService)WebApplicationManager.getBean("templateService");
			WorksheetMeta worksheetMeta = templateService.getWorksheetMeta(baseSchema);
			List<StepMeta> stepList = worksheetMeta.getSteps();
			
			Element stepsElement = new Element("steps");
			
			for(StepMeta stepMeta:stepList){
			    StepModel stepModel = stepMeta.getStepModel();
			    Element step = new Element("step");
			    step.addContent(new Element("stepNo").setText(stepModel.getStepNo()));
			    step.addContent(new Element("desc").setText(stepModel.getDescription()));
			    step.addContent(new Element("roleName").setText(stepModel.getRoleName()));
			    step.addContent(new Element("roleID").setText(stepModel.getRoleID()));
			    step.addContent(new Element("roleProcessRoleType").setText(stepModel.getRoleProcessRoleType()));
			    step.addContent(new Element("assignee").setText(stepModel.getAssignee()));
			    step.addContent(new Element("assigneeID").setText(stepModel.getAssigneeID()));
			    step.addContent(new Element("groupName").setText(stepModel.getGroupName()));
			    step.addContent(new Element("groupID").setText(stepModel.getGroupID()));
			    step.addContent(new Element("roleKey").setText(stepModel.getRoleKey()));
			    step.addContent(new Element("contextKey").setText(stepModel.getContextKey()));
			    step.addContent(new Element("actionType").setText(stepModel.getActionType()));
			    step.addContent(new Element("taskPolicy").setText(stepModel.getTaskPolicy()));
			    step.addContent(new Element("acceptOver").setText(stepModel.getAcceptOver()));
			    step.addContent(new Element("dealOver").setText(stepModel.getDealOver()));
			    step.addContent(new Element("hasSubFlow").setText(stepModel.getHasSubFlow()+""));
			    //封装环节动作
			    List<ActionMeta> actionList = stepMeta.getActions();
			    Element actionsElement = new Element("actions");
			    for(ActionMeta actionMeta:actionList){
				
				ActionModel actionModel=actionMeta.getActionModel();
				String actionName=actionModel.getActionName();
				String actionType=actionModel.getActionType();
				Integer isFree=actionModel.getIsFree();
				String label=actionModel.getLabel();
				
				//只有动作类型是NEXT且不是自由动作的才能进行封装
				if("NEXT".equals(actionType)&&isFree==0){
				    Element action = new Element("action");
				    action.addContent(new Element("actionName").setText(actionName));
				    action.addContent(new Element("label").setText(label));
				    actionsElement.addContent(action);
				}
			    }
			    step.addContent(actionsElement);
			    stepsElement.addContent(step);
			}
			
			modelElement.addContent(stepsElement);
			
			// 模型基本信息封装
			modelElement.addContent(new Element("modelkey").setText(wfModel.getCode()));
			modelElement.addContent(new Element("modelname").setText(wfModel.getName()));
			modelElement.addContent(new Element("imageurl").setText(wfModel.getPhotoPath()));
			root.addContent(modelElement);

			model.setModelkey(wfModel.getCode());  
			model.setModelname(wfModel.getName());
			model.setModeltype(wfModel.getType());
			model.setImageurl(wfModel.getPhotoPath());
			model.setModeldesc(wfModel.getRemark());
			modelList.add(model);
		}
		this.modelList = modelList;
		Format format = Format.getPrettyFormat();// 
		XMLOutputter outp = new XMLOutputter(format);

		return outp.outputString(doc);
	}
	

	public List<CustomModel> getModelList() { 
		return modelList;
	}

	public void setModelList(List<CustomModel> modelList) {
		this.modelList = modelList;
	}


	public TemplateService getTemplateService()
	{
	    return templateService;
	}


	public void setTemplateService(TemplateService templateService)
	{
	    this.templateService = templateService;
	}


	public ServletContext getServletContext()
	{
	    return servletContext;
	}


	public void setServletContext(ServletContext servletContext)
	{
	    this.servletContext = servletContext;
	}
	
}
