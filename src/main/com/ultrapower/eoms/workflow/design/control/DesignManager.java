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
public class DesignManager
{

	private String tplidString;
	private String basecode;
	private BaseMapModel baseModel;
	private List<WfVersion> wfList = new ArrayList<WfVersion>();
	private WfVersion wfVersion = null;

	public DesignManager(String id,String basecode){
		this.tplidString = id;
		this.basecode = basecode;
	}
	public DesignManager(String basecode){
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
	 	try {
			wfVersion = ver.getByCode(tplidString);
		    setBaseModel(wfVersion);
			setWfList(ver.getSubListByBaseCode(wfVersion.getBaseCode()));
		} catch(Throwable e) {
			e.printStackTrace();
		}
	}
	

	
	public BaseMapModel getBaseMapModel() {
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
		baseModel.setSubflow(wfVersion.getSubflag() + "");
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
			if(wfVersion.getCode().equals(tplidString)) {
				wfList.remove(i);
				break;
			}
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
	public WfVersion getWfVersion() {
		return wfVersion;
	}
	public void setWfVersion(WfVersion wfVersion) {
		this.wfVersion = wfVersion;
	}
} 






