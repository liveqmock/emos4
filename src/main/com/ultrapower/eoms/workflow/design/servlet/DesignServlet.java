package com.ultrapower.eoms.workflow.design.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
public class DesignServlet extends HttpServlet
{
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
//		String wfxml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + request.getParameter("wfxml");
//		String wfdesignxml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + request.getParameter("wfdesignxml");
//		String wfbase = request.getParameter("wfbase");
//		String wfmode=request.getParameter("mode");
//		String wfpath=request.getParameter("wfpath");
//		
//		
//		//调用流程管理接口，保存流程定义文件
//	 	IWfVersionManager ver = WorkFlowServiceClient.clientInstance().getVersionService();
//		
//
//		WfVersion wfVersion = new WfVersion();
//		String[] baseinfoStrings = new String[0];
//		baseinfoStrings = wfbase.split("#&#");
//		
//		for (int i = 0; i < baseinfoStrings.length; i++) {
//			String[] keyAndValue = baseinfoStrings[i].split(":");
//			String keyString = keyAndValue[0];
//			String valueString  = "";
//			if(keyAndValue.length>1)valueString = keyAndValue[1];
//			if(keyAndValue.length>2)valueString = keyAndValue[1]+":"+keyAndValue[2];
//			if(keyString.equals("id"))wfVersion.setId(valueString);
//			if(keyString.equals("name"))wfVersion.setName(valueString);
//			if(keyString.equals("code"))wfVersion.setCode(valueString);
//			if(keyString.equals("basecode"))wfVersion.setBaseCode(valueString);
//			if(keyString.equals("basename"))wfVersion.setBaseName(valueString);
//			if(keyString.equals("name"))wfVersion.setName(valueString);
//			if(keyString.equals("code"))wfVersion.setCode(valueString);
//			if(keyString.equals("desc"))wfVersion.setRemark(valueString);
//			if(keyString.equals("active"))wfVersion.setIsPublish(new Long(valueString));
//			if(keyString.equals("startdate")&&!valueString.equals(""))wfVersion.setPublishTime(new Long(valueString));
//			if(keyString.equals("createdate")&&!valueString.equals(""))wfVersion.setCreateTime(new Long(valueString));
//			if(keyString.equals("isSub"))wfVersion.setSubflag(new Long(valueString));
//		}
//		String wfid = wfVersion.getId(); 
//		if(wfid.equals(""))
//		{
//			wfVersion.setCreateTime(System.currentTimeMillis() / 1000);
//		}
//		if(wfVersion.getIsPublish()==1 && wfVersion.getPublishTime() == 0){
//			wfVersion.setPublishTime(System.currentTimeMillis() / 1000);
//		}
//		if(wfVersion.getIsPublish()==0)wfVersion.setPublishTime(0);
//		wfVersion.setDesignXml(wfdesignxml);
//		wfVersion.setWorkflowXml(wfxml);
//		ver.saveWfVersion(wfVersion);
//		System.out.println("保存完毕");
//		response.sendRedirect("../result.jsp");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doPost(request, response);
	}
}
