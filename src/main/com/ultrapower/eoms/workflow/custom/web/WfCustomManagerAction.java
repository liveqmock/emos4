package com.ultrapower.eoms.workflow.custom.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.workflow.api.DefActionTest;
import com.ultrapower.eoms.workflow.api.FreeActionTest;
import com.ultrapower.eoms.workflow.util.PageLimitUtil;
import com.ultrapower.workflow.bizconfig.custom.IModelManager;
import com.ultrapower.workflow.bizconfig.interfaces.IInterfaceManager;
import com.ultrapower.workflow.client.WorkFlowServiceClient;
import com.ultrapower.workflow.configuration.custom.model.WfModel;
import com.ultrapower.workflow.configuration.custom.model.WfModelProperties;
import com.ultrapower.workflow.configuration.interfaces.model.WfInterface;

/**
 * Copyright (c) 2010 神州泰岳服务管理事业部产品组
 * 
 * @author：liuchuanzu
 * @date 2010-05-31 当前版本：v1.0 流程版本管理 web
 */
public class WfCustomManagerAction extends BaseAction {

	// 流程自定义模型list
	private List<WfModel> modelList;
	
	// 流程自定义模型对象
	private WfModel wfModel;

	// 模型CODE
	private String modelCode;

	// 流程自定义模型属性list
	private List<WfModelProperties> propList;
	
	//接口集合
	private List<WfInterface> interfaceList;
	
	// 流程自定义模型属性string
	private String propString;

	// 流程自定义模型service对象
	private IModelManager ver;
	
	//接口管理对象
	private IInterfaceManager iver;

	/**
	 * 获取流程自定义模型列表
	 * @return
	 */
	public String getAllModelList() {
		try {
//			FreeActionTest.main(null);
			ver  = WorkFlowServiceClient.clientInstance().getModelService();
			modelList = ver.getAllModel();
			modelList = PageLimitUtil.pageLimit(modelList);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return this.findForward("modelList");
	}
	
	

	/**
	 * 接口保存时ajax验证标识是否唯一
	 */
	public void wfModelCheckUnique() throws IOException{
		String code = StringUtils.checkNullString(this.getRequest().getParameter("code"));
		boolean result = false;
		ver = WorkFlowServiceClient.clientInstance().getModelService();
		result = ver.getModelByCode(code) == null ? true : false;
		PrintWriter out = this.getResponse().getWriter();
		out.print(result);
	}
	
	/**
	 * 保存流程自定义模型
	 */
	public String saveModel() {
		try {
			ver  = WorkFlowServiceClient.clientInstance().getModelService();
			ver.saveOrUpdateModel(wfModel);
			if(propString!=null){
				String[] obj = propString.split("\\$\\$");
				for(String value:obj){
					String[] temp = new String(value).split("\\#\\#");
					String[] values = new String[9];
					for(int i=0;i<temp.length;i++){
						if("null".equals(temp[i])){
							values[i] = "";
						}
						else{
							values[i] = temp[i];
						}
					}
					String stuts =values[0];
					if(stuts!=null&&stuts.equals("del")){//删除
						ver.delModelPropById(values[1]);
					}else if(stuts!=null && !"".equals(stuts)){// 保存
						WfModelProperties prop = new WfModelProperties();
							if(values[1]!=null && !values[1].equals("")){
								prop.setId(values[1]);
							}
							prop.setCode(values[2]);
							prop.setName(values[3]);
							prop.setType(values[4]);
							prop.setDict(values[5]);
							System.out.println(values[6]);
							prop.setIsNull(values[6]==null||"".equals(values[6].trim())?-1:Integer.parseInt(values[6]));
							prop.setDefaulvalue(values[7]);
							prop.setOrderBy(values[8]==null||"".equals(values[8].trim())?-1:Integer.parseInt(values[8]));
							prop.setModelCode(wfModel.getCode());
							prop.setModelName(wfModel.getName());
							ver.saveOrUpdateModelProp(prop);
					}
				}
			}
		} catch (RemoteException e) {
			this.getRequest().setAttribute("message","保存失败");
		}
		return "result";
	}
	
	
	/**
	 * 修改或添加自定义模型
	 * @return
	 */
	public String toEditOrAddModel(){
		if(modelCode == null){
			wfModel = new WfModel();
		}else{
			try {
				ver  = WorkFlowServiceClient.clientInstance().getModelService();
				wfModel = ver.getModelById(modelCode);
				if(wfModel!=null){
					propList = new ArrayList<WfModelProperties>();
					propList = ver.getModelPropsByCode(wfModel.getCode());
				}else{
					wfModel = new WfModel();
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		
		try {
			iver = WorkFlowServiceClient.clientInstance().getInterfaceService();
			interfaceList = iver.getAllInte();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return this.findForward("modelEdit");
	}

	/**
	 * 删除自定义模型
	 * @return
	 */
	public String delModel() {
		try {
			if(propString != null){
				ver  = WorkFlowServiceClient.clientInstance().getModelService();
				String[] str = propString.split(",");
				for(String id:str){
					ver.delModelById(id);
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return "getModelList";
	}
	

	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public List<WfModelProperties> getPropList() {
		return propList;
	}

	public void setPropList(List<WfModelProperties> propList) {
		this.propList = propList;
	}

	public IModelManager getVer() {
		return ver;
	}

	public void setVer(IModelManager ver) {
		this.ver = ver;
	}

	public void setModelList(List<WfModel> modelList) {
		this.modelList = modelList;
	}

	public List<WfModel> getModelList() {
		return modelList;
	}

	public WfModel getWfModel() {
		return wfModel;
	}

	public void setWfModel(WfModel wfModel) {
		this.wfModel = wfModel;
	}

	public String getPropString() {
		return propString;
	}

	public void setPropString(String propString) {
		this.propString = propString;
	}
	
	public static void main(String[] args) {
		String sts = " # ## ";
//		String[] str = sts.split("$");
//		for(String st:str){
//			String[] values = st.split("#");
//			System.out.println(st+" : "+values.length);
//		}
		System.out.println(sts.split("#").length);
	}

	public List<WfInterface> getInterfaceList() {
		return interfaceList;
	}

	public void setInterfaceList(List<WfInterface> interfaceList) {
		this.interfaceList = interfaceList;
	}

}
