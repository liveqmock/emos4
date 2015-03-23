package com.ultrapower.eoms.workflow.interfaces.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.List;

import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.workflow.util.PageLimitUtil;
import com.ultrapower.workflow.bizconfig.interfaces.IInterfaceManager;
import com.ultrapower.workflow.client.WorkFlowServiceClient;
import com.ultrapower.workflow.configuration.interfaces.model.WfInterface;
import com.ultrapower.workflow.utils.UUIDGenerator;

/**
 * 流程自定义接口管理（Web层action）
 * @version UltraPower-Eoms4.0-0.1
 * @author：liuchuanzu
 * @since jdk1.5
 * @date 2010-05-31 当前版本：v1.0 流程版本管理 web
 * Copyright (c) 2010 神州泰岳服务管理事业部产品组
 */
public class WfInterfaceManagerAction extends BaseAction {

	/**
	 * 流程接口list
	 */ 
	private List<WfInterface> interfaceList;
	
	/**
	 * 流程接口对象
	 */
	private WfInterface wfinterface;

	/**
	 * 接口ID
	 */ 
	private String interfaceCode;

	/** 
	 * 流程接口service对象
	 */
	private IInterfaceManager ver;
	
	/**
	 * 字符串，主要用来接收删除时接口ID的字符串;
	 */
	private String wfString;

	/**
	 * 获取流程接口列表
	 * @return
	 */
	public String getAllInterfaceList() {
		try {
			ver = WorkFlowServiceClient.clientInstance().getInterfaceService();
			interfaceList = ver.getAllInte();
			interfaceList = PageLimitUtil.pageLimit(interfaceList);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return this.findForward("interfaceList");
	}
	
	/**
	 * 保存流程接口
	 */
	public String saveInterface() {
		try {
			ver = WorkFlowServiceClient.clientInstance().getInterfaceService();
			ver.saveOrUpdateInte(wfinterface);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return "result";
	}
	
	/**
	 * 接口保存时ajax验证标识是否唯一
	 */
	public void wfInterfaceCheckUnique() throws IOException{
		String code = StringUtils.checkNullString(this.getRequest().getParameter("code"));
		boolean result = false;
		ver = WorkFlowServiceClient.clientInstance().getInterfaceService();
		result = ver.getInteByCode(code)==null?true:false;
		PrintWriter out = this.getResponse().getWriter();
		out.print(result);
	}
	
	
	/**
	 * 修改或添加接口
	 */
	public String toEditOrAddInterface() {
		if (interfaceCode == null) {
			wfinterface = new WfInterface();
			wfinterface.setId(UUIDGenerator.getId());
		} else {
			try {
				ver = WorkFlowServiceClient.clientInstance().getInterfaceService();
				wfinterface = ver.getInteByCode(interfaceCode);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return this.findForward("interfaceSave");
	}

	/**
	 * 删除接口
	 */
	public String delInterface() {
		try {
			if(wfString != null){
				ver = WorkFlowServiceClient.clientInstance().getInterfaceService();
				String[] str = wfString.split(",");
				for(String code:str){
					ver.removeInterByCode(code);
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return "getInterfaceList";
	}

	public List<WfInterface> getInterfaceList() {
		return interfaceList;
	}

	public void setInterfaceList(List<WfInterface> interfaceList) {
		this.interfaceList = interfaceList;
	}

	public WfInterface getWfinterface() {
		return wfinterface;
	}

	public void setWfinterface(WfInterface wfinterface) {
		this.wfinterface = wfinterface;
	}



	public String getInterfaceCode() {
		return interfaceCode;
	}

	public void setInterfaceCode(String interfaceCode) {
		this.interfaceCode = interfaceCode;
	}

	public String getWfString() {
		return wfString;
	}

	public void setWfString(String wfString) {
		this.wfString = wfString;
	}
	

}
