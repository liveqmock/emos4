package com.ultrapower.eoms.workflow.design.servlet;

import java.rmi.RemoteException;

import org.apache.commons.lang.StringUtils;

import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.workflow.sheet.role.web.SheetRoleAction;
import com.ultrapower.eoms.workflow.version.web.WfVersionManagerAction;
import com.ultrapower.workflow.bizconfig.sort.IWfSortManager;
import com.ultrapower.workflow.bizconfig.version.IWfVersionManager;
import com.ultrapower.workflow.client.WorkFlowServiceClient;
import com.ultrapower.workflow.configuration.version.model.WfVersion;

/**
 * 流程定义保存方法
 * @version UltraPower-Eoms4.0-0.1
 * @author：liuchuanzu
 * @since jdk1.5
 * @date 2010-06-31 当前版本：v1.0 web
 * Copyright (c) 2010 神州泰岳服务管理事业部产品组
 */
public class DesignAction extends BaseAction {
	private WfVersionManagerAction wfVersionManagerAction;
	private SheetRoleAction sheetRoleAction;
	
	private String wfxml;
	private String wfdesignxml;
	private String wfbase;
	private String mode;
	private String wfpath;
	private String roleCodes;
	private String roleNames;
	private String processNos;
	private boolean asNew;//是否保存为新版本

	//调用流程管理接口，保存流程定义文件
 	IWfVersionManager ver;
 	IWfSortManager versort;
 	
	public String saveWfDesign(){		
//		String wfxml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + this.getRequest().getParameter("wfxml");
//		String wfdesignxml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + this.getRequest().getParameter("wfdesignxml");
//		String wfbase = this.getRequest().getParameter("wfbase");
//		String wfmode=this.getRequest().getParameter("mode");
//		String wfpath=this.getRequest().getParameter("wfpath");
		
		
		ver = WorkFlowServiceClient.clientInstance().getVersionService();
		versort = WorkFlowServiceClient.clientInstance().getSortService();
		
		WfVersion wfVersion = new WfVersion();
		String[] baseinfoStrings = new String[0];
		baseinfoStrings = wfbase.split("#&#");
		
		
		//2011-1-21 fanying 版本"另存为功能",在oldbasecode中保存最初的basecode值，在定制器中不对basecode进行判断，判断操作在后台完成
		String oldbasecode = "";
		
		for (int i = 0; i < baseinfoStrings.length; i++) {
			String[] keyAndValue = baseinfoStrings[i].split(":");
			String keyString = keyAndValue[0];
			String valueString  = "";
			if(keyAndValue.length>1)valueString = keyAndValue[1];
			if(keyAndValue.length>2)valueString = keyAndValue[1]+":"+keyAndValue[2];
			if(keyString.equals("id"))wfVersion.setId(valueString);
			if(keyString.equals("name"))wfVersion.setName(valueString);
			if(keyString.equals("code"))wfVersion.setCode(valueString);
			if(keyString.equals("basecode"))wfVersion.setBaseCode(valueString);
			
			if(keyString.equals("oldbasecode"))
					oldbasecode = valueString;
				
			if(keyString.equals("basename"))wfVersion.setBaseName(valueString);
			if(keyString.equals("name"))wfVersion.setName(valueString);
			if(keyString.equals("code"))wfVersion.setCode(valueString);
			if(keyString.equals("desc"))wfVersion.setRemark(valueString);
			if(keyString.equals("active")&&!valueString.equals(""))wfVersion.setIsPublish(new Long(valueString));
			if(keyString.equals("startdate")&&!valueString.equals(""))wfVersion.setPublishTime(new Long(valueString));
			if(keyString.equals("createdate")&&!valueString.equals(""))wfVersion.setCreateTime(new Long(valueString));
			if(keyString.equals("isSub")&&!valueString.equals(""))wfVersion.setSubflag(new Long(valueString));
		}
		
		//2011-1-21 fanying 
		if(!oldbasecode.equals(wfVersion.getBaseCode()))wfVersion.setId("");
			
		String wfid = wfVersion.getId(); 
		WfVersion dbVer = null;
		if (StringUtils.isNotBlank(wfid)) {
			try {
				dbVer = ver.getById(wfid);
				if (dbVer != null) {
					long entryCount = dbVer.getEntryCount();
					long todayEntryCount = dbVer.getTodayEntryCount();
					String verCode = dbVer.getCode();
					long lastEntryTime = dbVer.getLastEntryTime();
					wfVersion.setEntryCount(entryCount);
					wfVersion.setTodayEntryCount(todayEntryCount);
					wfVersion.setCode(verCode);
					wfVersion.setLastEntryTime(lastEntryTime);
					if (asNew) {
						wfVersion.setId(null);
						wfVersion.setCreateTime(System.currentTimeMillis() / 1000);
						String createdate = TimeUtils.getCurrentDate("yyyy-MM-dd hh:mm:ss");
						String code = wfVersion.getBaseCode().replace(":","_")+ "-"+ createdate.replace(":", "").replace(" ", "").replace("-", "");
						wfVersion.setCode(code.toUpperCase());
					}
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		} else {
			wfVersion.setId(null);
			wfVersion.setCreateTime(System.currentTimeMillis() / 1000);
			wfVersion.setIsPublish(0);
			String createdate = TimeUtils.getCurrentDate("yyyy-MM-dd hh:mm:ss");
			String code = wfVersion.getBaseCode().replace(":","_")+ "-"+ createdate.replace(":", "").replace(" ", "").replace("-", "");
			wfVersion.setCode(code.toUpperCase());
		}
		
		wfdesignxml = wfdesignxml.replaceAll("xmlns=\"design.ascript.transfer:Model2Xml\"","");
		wfxml = wfxml.replaceAll("xmlns=\"design.ascript.transfer:Model2Xml\"","");
	    
		wfVersion.setDesignXml(wfdesignxml);
		wfVersion.setWorkflowXml(wfxml);

		try {
			wfVersion.setBaseName(versort.getWfTypeByCode(wfVersion.getBaseCode()).getName());
			ver.saveWfVersion(wfVersion, asNew);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
//		try {
//			if(wfVersion!=null && wfVersion.getBaseCode()!=null){
//				if(wfVersion.getCode() == null || "".equals(wfVersion.getCode()) || entryCount > 0){
//					String createdate = TimeUtils.getCurrentDate("yyyy-MM-dd hh:mm:ss");
//					String code = wfVersion.getBaseCode().replace(":","_")+ "-"+ createdate.replace(":", "").replace(" ", "").replace("-", "");
//					wfVersion.setCode(code.toUpperCase());
//					log.info("新添流程版本号："+wfVersion.getCode());
//				}
//				wfVersion.setBaseName(versort.getWfTypeByCode(wfVersion.getBaseCode()).getName());
//				ver.saveWfVersion(wfVersion);
//			}
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}
		System.out.println("保存完毕");
		
		//添加流程管理中，流程版本对应的角色目录树
		wfVersionManagerAction.addWfRoleMenu(wfVersion);
		
		//获取流程角色，添加流程角色
		sheetRoleAction.editWfRole(roleCodes, roleNames,processNos,wfVersion);
		return "result";
	}
	
	
	
	public WfVersionManagerAction getWfVersionManagerAction() {
		return wfVersionManagerAction;
	}
	
	
	public void setWfVersionManagerAction(
			WfVersionManagerAction wfVersionManagerAction) {
		this.wfVersionManagerAction = wfVersionManagerAction;
	}



	public String getWfxml() {
		return wfxml;
	}



	public void setWfxml(String wfxml) {
		this.wfxml = wfxml;
	}



	public String getRoleCodes() {
		return roleCodes;
	}



	public void setRoleCodes(String roleCodes) {
		this.roleCodes = roleCodes;
	}



	public String getRoleNames() {
		return roleNames;
	}



	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}



	public String getWfdesignxml() {
		return wfdesignxml;
	}



	public void setWfdesignxml(String wfdesignxml) {
		this.wfdesignxml = wfdesignxml;
	}



	public String getWfbase() {
		return wfbase;
	}



	public void setWfbase(String wfbase) {
		this.wfbase = wfbase;
	}



	public String getMode() {
		return mode;
	}



	public void setMode(String mode) {
		this.mode = mode;
	}



	public String getWfpath() {
		return wfpath;
	}



	public void setWfpath(String wfpath) {
		this.wfpath = wfpath;
	}



	public SheetRoleAction getSheetRoleAction() {
		return sheetRoleAction;
	}



	public void setSheetRoleAction(SheetRoleAction sheetRoleAction) {
		this.sheetRoleAction = sheetRoleAction;
	}



	public String getProcessNos() {
		return processNos;
	}



	public void setProcessNos(String processNos) {
		this.processNos = processNos;
	}



	public boolean isAsNew() {
		return asNew;
	}



	public void setAsNew(boolean asNew) {
		this.asNew = asNew;
	}
	
}

