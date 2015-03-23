package com.ultrapower.eoms.workflow.design.control;

import java.util.*;
import java.io.*;

import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.ultrapower.eoms.workflow.design.model.ProcessStatusList;
import com.ultrapower.eoms.workflow.design.model.ProcessStatusModel;





import java.lang.reflect.*;

/**
 * 
 * Copyright (c) 2010 神州泰岳服务管理事业部通用产品
 * All rights reserved.
 * 描述：
 * @author 范莹
 * @date   2010-4-21
 */
public class FlowMapConfig {
	
	private static String xmlString;
	private static ProcessStatusList processStatusList;
	
	static {
		try {//放在static块里只需加载一次。
			String tmpPath = FlowMapConfig.class.getResource("").getPath();
			System.out.println(tmpPath);
			tmpPath = tmpPath.substring(0, tmpPath.length()-44);
			System.out.println(tmpPath);
			File file = new File(tmpPath +  "workflow/design/UltraFlowMapConfig.xml");
			SAXBuilder bu = new SAXBuilder();
			Document doc = bu.build(file);
			Format format = Format.getPrettyFormat();
		    XMLOutputter outp = new XMLOutputter(format);   
		    xmlString = outp.outputString(doc);
		    xmlString = xmlString.replaceAll("\r","").replaceAll("\n","");
		    
		    processStatusList = getProcessStatusModel(doc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getStatusXmlString() {
	   return xmlString;
	}
	/**
	 * 获取xml中配置的环节状态集合对象
	 * 
	 * @return xml中配置的环节状态集合对象
	 * @throws Exception
	 */
	public ProcessStatusList getProcessStatusList() throws Exception
	{
		return  processStatusList;
		
	}

	private static ProcessStatusList getProcessStatusModel(Document doc) {
		Element rootElement = doc.getRootElement();
		ProcessStatusList psList = new ProcessStatusList();

		// 获取环节状态的节点
		Element ptypeElements = rootElement.getChild("ProcessStatus");

		// 获取流程图的整体样式封装到环节状态集合类中

		psList.setStartX(Integer.parseInt(ptypeElements.getAttributeValue("startX")));
		psList.setStartY(Integer.parseInt(ptypeElements.getAttributeValue("startY")));
		psList.setModelWidth(Integer.parseInt(ptypeElements.getAttributeValue("modelWidth")));
		psList.setModelHeight(Integer.parseInt(ptypeElements.getAttributeValue("modelHeight")));
		psList.setArrowLength(Integer.parseInt(ptypeElements.getAttributeValue("arrowLength")));
		psList.setArrowwidth(Integer.parseInt(ptypeElements.getAttributeValue("arrowWidth")));
		psList.setRowHeight(Integer.parseInt(ptypeElements.getAttributeValue("rowHeight")));
		psList.setXSpace(Integer.parseInt(ptypeElements.getAttributeValue("xSpace")));

		// 获取环节状态集合封装到环节状态实体类中并填入到List
		List ptypeList = ptypeElements.getChildren("PStatus");
		List pStatusList = new ArrayList();
		for (Iterator it = ptypeList.iterator(); it.hasNext();)
		{
			Element ptypeElement = (Element) it.next();
			ProcessStatusModel psModel = new ProcessStatusModel();
			psModel.setStatusName(ptypeElement.getAttributeValue("status"));
			psModel.setColor(ptypeElement.getChild("Color").getText());
			psModel.setColor1(ptypeElement.getChild("Color1").getText());
			psModel.setInfoText(ptypeElement.getChild("InfoText").getText());
			psModel.setArrowWay(ptypeElement.getChild("ArrowWay").getText());
			psModel.setArrowType(ptypeElement.getChild("ArrowType").getText());
			psModel.setXSpace(ptypeElement.getChild("XSpace").getText());
			pStatusList.add(psModel);
		}
		psList.setProcessStatusModelList(pStatusList);
		return psList;
	}
}
