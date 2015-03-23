package com.ultrapower.eoms.workflow.visioanalyze;

import java.util.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import org.dom4j.io.SAXReader;


import com.ultrapower.eoms.workflow.design.model.BaseMapModel;
import com.ultrapower.eoms.workflow.design.model.FunctionModel;
import com.ultrapower.eoms.workflow.design.model.LinkModel;
import com.ultrapower.eoms.workflow.design.model.ProcessModel;
import com.ultrapower.eoms.workflow.design.model.StatusModel;
import com.ultrapower.eoms.workflow.design.model.SubFlowModel;


public class AnalyzeHandler
{
	
	private Map<String, MasterModel> masterMap;
	private Map<String, ShapeModel> shapeMap;
	private List<ConnectModel> connectList;
	
	private BaseMapModel baseObj;
	private List<StatusModel> statusList;
	private ProcessModel startModel;
	private List<ProcessModel> stepList  = new ArrayList<ProcessModel>();
	private List<ProcessModel> splitList = new ArrayList<ProcessModel>();
	private List<ProcessModel> joinList  = new ArrayList<ProcessModel>();
	private List<ProcessModel> endList   = new ArrayList<ProcessModel>();
	private List<LinkModel> linkList  = new ArrayList<LinkModel>();
	private Map<String, ProcessModel> processMap = new HashMap<String, ProcessModel>();
	
	private int pxX = 0;
	private int pxY = 0;
	
	private String wfdesignxml = "";
	private String wfxml = "";
	private String roleCodes = "";
	private String roleNames = "";
	
	public static void main(String[] args)
	{
		AnalyzeHandler ah = new AnalyzeHandler();
		ah.testAnalyze();
	}
	
	public void testAnalyze()
	{
		try
		{
			FileInputStream file = new FileInputStream ("E:\\test1.vdx");
			Map xmlMap = new HashMap();   
	        xmlMap.put("wfd","http://schemas.microsoft.com/visio/2003/core");
			SAXReader saxReader = new SAXReader();
			saxReader.getDocumentFactory().setXPathNamespaceURIs(xmlMap);
			InputStreamReader reader  = new InputStreamReader(file,"utf-8");
			Document doc = saxReader.read(reader);
			analyze(doc);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return;
		}
	}

	public void doAnalyze(HttpServletRequest request)
	{
		try
		{
			InputStream fileIS = null;
			
			DiskFileUpload fu = new DiskFileUpload();
			// 设置允许用户上传文件大小,单位:字节
			fu.setSizeMax(100000000);
			// 设置最多只允许在内存中存储的数据,单位:字节
			fu.setSizeThreshold(4096);
			List fileItems = fu.parseRequest(request);
			// 依次处理每个上传的文件
			Iterator iter = fileItems.iterator();
			while (iter.hasNext())
			{
				FileItem item = (FileItem) iter.next();
				if (!item.isFormField())
				{
					fileIS = item.getInputStream();
				}
			}
			
			//FileInputStream file = new FileInputStream ("E:\\电路调度工单_1.vdx");
			Map xmlMap = new HashMap();   
            xmlMap.put("wfd","http://schemas.microsoft.com/visio/2003/core");
			SAXReader saxReader = new SAXReader();
			saxReader.getDocumentFactory().setXPathNamespaceURIs(xmlMap);
			InputStreamReader reader  = new InputStreamReader(fileIS,"utf-8");
			Document doc = saxReader.read(reader);
			analyze(doc);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return;
		}
	}
	
	public void analyze(Document doc)
	{
		//处理XML
		try
		{
			//处理Master标签
			List<Element> eleMasterList = doc.selectNodes("/wfd:VisioDocument/wfd:Masters/wfd:Master");
			masterAnalyze(eleMasterList);
			
			//处理Shape标签
			List<Element> eleShapeList = doc.selectNodes("/wfd:VisioDocument/wfd:Pages/wfd:Page/wfd:Shapes/wfd:Shape");
			shapeAnalyze(eleShapeList);
			
			//处理Connects标签
			List<Element> eleConnectList = doc.selectNodes("/wfd:VisioDocument/wfd:Pages/wfd:Page/wfd:Connects/wfd:Connect");
			connectAnalyze(eleConnectList);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return;
		}
		
		//开始处理数据
		List<ShapeModel> statusShapeList = new ArrayList<ShapeModel>();
		MasterModel statusMasterModel = null;
		ShapeModel startShapeModel = null;
		List<ShapeModel> endShapeList = new ArrayList<ShapeModel>();
		MasterModel startMasterModel = null;
		MasterModel endMasterModel = null;
		List<ShapeModel> stepShapeList = new ArrayList<ShapeModel>();
		MasterModel stepMasterModel = null;
		List<ShapeModel> splitShapeList = new ArrayList<ShapeModel>();
		MasterModel splitMasterModel = null;
		List<ShapeModel> joinShapeList = new ArrayList<ShapeModel>();
		MasterModel joinMasterModel = null;
		List<ShapeModel> linkShapeList = new ArrayList<ShapeModel>();
		MasterModel linkMasterModel = null;
		
		baseObj = new BaseMapModel();
		//对数据进行分类
		for(ShapeModel shapeModel : shapeMap.values())
		{
			MasterModel masterModel = masterMap.get(shapeModel.getMaster());
			if(masterModel==null)continue;
			if(masterModel.getName().equals("流程模板")||masterModel.getName().equals("流程模具")|| masterModel.getName().equals("CFF 容器"))
			{
				setBase(shapeModel, masterModel);
			}
			else if(masterModel.getName().equals("状态"))
			{
				statusShapeList.add(shapeModel); 
				if(statusMasterModel == null) statusMasterModel = masterModel;
			}
			else if(masterModel.getName().equals("开始"))
			{
				 startShapeModel = shapeModel;
				if(startMasterModel == null) startMasterModel = masterModel;
			}
			else if(masterModel.getName().equals("结束"))
			{
				endShapeList.add(shapeModel);
				if(endMasterModel == null) endMasterModel = masterModel;
			}
			else if(masterModel.getName().equals("环节"))
			{
				stepShapeList.add(shapeModel);
				if(stepMasterModel == null) stepMasterModel = masterModel;
			}
			else if(masterModel.getName().equals("分支"))
			{
				splitShapeList.add(shapeModel);
				if(splitMasterModel == null) splitMasterModel = masterModel;
			}
			else if(masterModel.getName().equals("合并"))
			{
				joinShapeList.add(shapeModel);
				if(joinMasterModel == null) joinMasterModel = masterModel;
			}
			else if(masterModel.getName().equals("连线"))
			{
				linkShapeList.add(shapeModel);
				if(linkMasterModel == null) linkMasterModel = masterModel;
			}
		}
		System.out.print("XML解析完毕");
		System.out.print("开始流程定制文件对象封装");
		setStatus(statusShapeList, statusMasterModel);
		setStart(startShapeModel,startMasterModel);
		setStep(stepShapeList,stepMasterModel);
		setSplit(splitShapeList, splitMasterModel);
		setJoin(joinShapeList, joinMasterModel);
		setEnd(endShapeList,endMasterModel);
		setLink(linkShapeList, linkMasterModel);
		System.out.print("结束流程定制文件对象封装");
		wfdesignxml = getWfdesignxml();
		wfxml = getWfxml();
		
		System.out.println(wfdesignxml);
		System.out.println(wfxml);
	}
	
	public void masterAnalyze(List<Element> eleMasterList)
	{
		masterMap = new HashMap<String, MasterModel>();
		
		for(Element eleMaster : eleMasterList)
		{
			MasterModel masterModel = new MasterModel();
			masterModel.setId(eleMaster.attributeValue("ID"));
			masterModel.setNameU(eleMaster.attributeValue("NameU"));
			masterModel.setName(eleMaster.attributeValue("Name"));
			
			List<Element> elePropList = eleMaster.selectNodes("wfd:Shapes/wfd:Shape/wfd:Prop");
			
			Map<String, PropModel> propModelMap = new HashMap<String, PropModel>();
			for(Element eleProp : elePropList)
			{
				PropModel propModel = new PropModel();
				propModel.setId(eleProp.attributeValue("ID"));
				propModel.setNameU(eleProp.attributeValue("NameU"));
				propModel.setLabel(eleProp.elementText("Label"));
				propModel.setValue(eleProp.elementText("Value"));
				propModelMap.put(propModel.getLabel(), propModel);
			}
			masterModel.setPropModelMap(propModelMap);
			masterMap.put(masterModel.getId(), masterModel);
		}
	}
	
	public void shapeAnalyze(List<Element> eleShapeList)
	{
		shapeMap = new HashMap<String, ShapeModel>();
		
		for(Element eleShape : eleShapeList)
		{
			ShapeModel shapeModel = new ShapeModel();
			shapeModel.setId(eleShape.attributeValue("ID"));
			shapeModel.setNameU(eleShape.attributeValue("NameU"));
			shapeModel.setName(eleShape.attributeValue("Name"));
			shapeModel.setMaster(eleShape.attributeValue("Master"));
			
			Element xForm = (Element)eleShape.selectSingleNode("wfd:XForm");
			shapeModel.setX(xForm.elementText("PinX"));
			Element elePinX = (Element)xForm.selectSingleNode("wfd:PinX");
			String attF = elePinX.attributeValue("F");
			if(attF != null && attF.indexOf("!") > 0)
			{
				shapeModel.setFormat(attF.substring(0, attF.indexOf("!")));
				
			}
			shapeModel.setY(xForm.elementText("PinY"));
			shapeModel.setWidth(xForm.elementText("Width"));
			shapeModel.setHeight(xForm.elementText("Height"));
			
			List<Element> elePropList = eleShape.selectNodes("wfd:Prop");
			if(elePropList.size() > 0)
			{
				Map<String, PropModel> propModelMap = new HashMap<String, PropModel>();
				for(Element eleProp : elePropList)
				{
					PropModel propModel = new PropModel();
					propModel.setId(eleProp.attributeValue("ID"));
					propModel.setNameU(eleProp.attributeValue("NameU"));
					propModel.setLabel(eleProp.elementText("Label"));
					propModel.setValue(eleProp.elementText("Value"));
					propModelMap.put(propModel.getLabel(), propModel);
				}
				shapeModel.setPropModelMap(propModelMap);
			}else{
				shapeModel.setPropModelMap(new HashMap<String, PropModel>());
			}
			
			String masterName = masterMap.get(shapeModel.getMaster())!=null?masterMap.get(shapeModel.getMaster()).getName():"";
			System.out.println("masterName:"+masterName);
			if(masterName.equals("开始") || masterName.equals("环节") || masterName.equals("分支")|| masterName.equals("合并") || masterName.equals("结束"))
			{
				List<Element> eleConnectionList = eleShape.selectNodes("wfd:Connection");
				if(eleConnectionList.size() > 0)
				{
					String[][] connArray = new String[4][2];
					double maxX = 0.0;
					double maxY = 0.0;
					for(int iConn = 0; iConn < eleConnectionList.size(); iConn++)
					{
						String x = eleConnectionList.get(iConn).elementText("X");
						String y = eleConnectionList.get(iConn).elementText("Y");
						connArray[iConn][0] = x;
						connArray[iConn][1] = y;
						double dx = Double.valueOf(x);
						double dy = Double.valueOf(y);
						if(maxX < dx) maxX = dx;
						if(maxY < dy) maxY = dy;
					}
					
					StringBuilder connectionPoint = new StringBuilder();
					
					for(String[] conn : connArray)
					{
						double dx = Double.valueOf(conn[0]);
						double dy = Double.valueOf(conn[1]);
						if(dx < maxX && dy == 0)
						{
							connectionPoint.append(",-1");
						}
						else if(dx < maxX && dy == maxY)
						{
							connectionPoint.append(",1");
						}
						else if(dx == 0 && dy < maxY)
						{
							connectionPoint.append(",-2");
						}
						else if(dx == maxX && dy < maxY)
						{
							connectionPoint.append(",2");
						}
					}
					
					shapeModel.setConnectionPoint(connectionPoint.toString().substring(1));
				}
			}
			
			if(masterName.equals("状态")&&shapeModel.getPropModelMap().size()==0){
				List<Element> childShapeList = eleShape.selectNodes("wfd:Shapes/wfd:Shape");
				for (int i = 0; i < childShapeList.size(); i++) {
					Element childShape = childShapeList.get(i);
					List<Element> proElement = childShape.selectNodes("wfd:Prop");
					if(proElement!=null&&proElement.size()>0){
							Map<String, PropModel> propModelMap = new HashMap<String, PropModel>();
						    PropModel propModel = new PropModel();
							propModel.setId(proElement.get(0).attributeValue("ID"));
							propModel.setNameU(proElement.get(0).attributeValue("NameU"));
							propModel.setLabel(proElement.get(0).elementText("Label"));
							propModel.setValue(proElement.get(0).elementText("Value"));
							propModelMap.put(propModel.getLabel(), propModel);
							shapeModel.setPropModelMap(propModelMap);
					}
				}
			}
			shapeMap.put(shapeModel.getId(), shapeModel);
		}
	}
	
	public void connectAnalyze(List<Element> eleConnectList)
	{
		connectList = new ArrayList<ConnectModel>();
		
		for(Element eleConnect : eleConnectList)
		{
			ConnectModel connectModel = new ConnectModel();
			connectModel.setFromSheet(eleConnect.attributeValue("FromSheet"));
			connectModel.setFromCell(eleConnect.attributeValue("FromCell"));
			connectModel.setToSheet(eleConnect.attributeValue("ToSheet"));
			connectModel.setToCell(eleConnect.attributeValue("ToCell"));
			connectModel.setToPart(eleConnect.attributeValue("ToPart"));
			connectList.add(connectModel);
		}
	}
	
	public void setBase(ShapeModel shapeModel,MasterModel masterModel)
	{
		if(!masterModel.getName().equals("CFF 容器")){
			baseObj.setName(masterModel.getPropModelMap().get("版本名称").getValue());
			baseObj.setSubflow(masterModel.getPropModelMap().get("是否子流程").getValue().equals("是") ? "1" : "0");
			baseObj.setDesc(masterModel.getPropModelMap().get("备注").getValue());
		}else{
			baseObj.setName("");
			baseObj.setSubflow("");
			baseObj.setDesc("");
		}
		
		if(shapeModel.getPropModelMap().get("版本名称") != null) baseObj.setName(shapeModel.getPropModelMap().get("版本名称").getValue());
		if(shapeModel.getPropModelMap().get("是否子流程") != null) baseObj.setSubflow(shapeModel.getPropModelMap().get("是否子流程").getValue().equals("是") ? "1" : "0");
		if(shapeModel.getPropModelMap().get("备注") != null) baseObj.setDesc(shapeModel.getPropModelMap().get("备注").getValue());
		
		pxX = new Long(Math.round(Double.valueOf(shapeModel.getX())*25.4)).intValue()*5+100;
		pxY = new Long(Math.round(Double.valueOf(shapeModel.getY())*25.4)).intValue()*5-100;
		
		if(masterModel.getName().equals("CFF 容器")){
			pxX = new Long(Math.round((Double.valueOf(shapeModel.getX())-Double.valueOf(shapeModel.getWidth())/2)*25.4)).intValue()*5+100;
			pxY = new Long(Math.round(Double.valueOf(shapeModel.getY())*25.4)).intValue()*5*2-100;
		}
	}
	public void setStatus(List<ShapeModel> statusShapeList,MasterModel masterModel)
	{
		
		//排列状态顺序
		List<ShapeModel> newShapeModelList = new ArrayList<ShapeModel>();
		double minX = 0.0;
		double minXStatusWidth = 0.0;
		while(statusShapeList.size() > 0)
		{
			int index = 0;
			for(int i = 0; i < statusShapeList.size(); i++)
			{
				ShapeModel shapeModel = statusShapeList.get(i);
				double x = Double.valueOf(shapeModel.getX());
				double width = Double.valueOf(shapeModel.getWidth());
				if(minX == 0.0)
				{
					minX = x;
					minXStatusWidth = width;
				}
				else if(x < minX)
				{
					index = i;
					minX = x;
					minXStatusWidth = width;
				}
			}
			newShapeModelList.add(statusShapeList.get(index));
			statusShapeList.remove(index);
		}
		statusList =new  ArrayList<StatusModel>();
		for (int i = 0; i < newShapeModelList.size(); i++) {
			ShapeModel statusShape = newShapeModelList.get(i);
			StatusModel statusModel = new StatusModel();
			statusModel.setSid("s_"+statusShape.getId());
			statusModel.setSname(statusShape.getPropModelMap().get("状态名称").getValue());
			statusModel.setSindex(i+"");
			statusModel.setSdesc("");
			statusModel.setSwidth(Math.round(Double.valueOf(statusShape.getWidth())*25.4*5)+"");
			statusList.add(statusModel);
		}
		
		if(minX>0){
			pxX = new Long(Math.round((minX-minXStatusWidth/2)*25.4)).intValue()*5;
		}
	    }
		public void setStart(ShapeModel startShapeModel,MasterModel masterModel)
		{
			startModel = new ProcessModel();
			startModel.setPId("p_BEGIN");
			startModel.setPType("BEGIN");
			String statusId = "";
			String statusName = "";
			String[] statuStrings = getStatusIdAndName(startShapeModel);
			statusId = statuStrings[0];
			statusName = statuStrings[1];
			startModel.setPStatusId(statusId);
			startModel.setPStatusName(statusName);
			//if(pxY!=0){
				startModel.setPX((new Long(Math.round(Double.valueOf(startShapeModel.getX())*25.4))*5-pxX)+"");
				startModel.setPY((pxY-new Long(Math.round(Double.valueOf(startShapeModel.getY())*25.4))*5)+"");
				//}
			//else{
			//	startModel.setPX((new Long(Math.round(Double.valueOf(startShapeModel.getX())*25.4))*5)+"");
			//	startModel.setPY((new Long(Math.round(Double.valueOf(startShapeModel.getY())*25.4))*5)+"");	
			//}
			
			processMap.put(startShapeModel.getId(), startModel);
		}
		public void setStep(List<ShapeModel> stepShapeList,MasterModel masterModel){
			for (int i = 0; i < stepShapeList.size(); i++) {
				ShapeModel stepShapeModel = stepShapeList.get(i);
				ProcessModel stepModel = new ProcessModel();
				stepModel.setPId("dp_"+stepShapeModel.getId());
				stepModel.setPType("STEP");
				
				String statusId = "";
				String statusName = "";
				String[] statuStrings = getStatusIdAndName(stepShapeModel);
				statusId = statuStrings[0];
				statusName = statuStrings[1];
				
				stepModel.setPStatusId(statusId);
				stepModel.setPStatusName(statusName);
				
				//if(pxY!=0){
					stepModel.setPX((new Long(Math.round(Double.valueOf(stepShapeModel.getX())*25.4))*5-pxX)+"");
					stepModel.setPY((pxY-new Long(Math.round(Double.valueOf(stepShapeModel.getY())*25.4))*5)+"");
				//	}else{
				//	stepModel.setPX((new Long(Math.round(Double.valueOf(stepShapeModel.getX())*25.4))*5)+"");
				//	stepModel.setPY((new Long(Math.round(Double.valueOf(stepShapeModel.getY())*25.4))*5)+"");}
				
				stepModel.setPModelkey(stepShapeModel.getPropModelMap().get("模型标识")!=null?stepShapeModel.getPropModelMap().get("模型标识").getValue():masterModel.getPropModelMap().get("模型标识").getValue());
				
				
				Map<String,Object> pDataMap =new HashMap<String,Object>();
				pDataMap.put("desc",stepShapeModel.getPropModelMap().get("环节简述")!=null?stepShapeModel.getPropModelMap().get("环节简述").getValue():masterModel.getPropModelMap().get("环节简述").getValue());
				pDataMap.put("roleID",stepShapeModel.getPropModelMap().get("操作角色ID")!=null?stepShapeModel.getPropModelMap().get("操作角色ID").getValue():masterModel.getPropModelMap().get("操作角色ID").getValue());
				pDataMap.put("roleName", stepShapeModel.getPropModelMap().get("操作角色")!=null?stepShapeModel.getPropModelMap().get("操作角色").getValue():masterModel.getPropModelMap().get("操作角色").getValue());
				String roleProcessRoleType = stepShapeModel.getPropModelMap().get("操作角色类型")!=null?stepShapeModel.getPropModelMap().get("操作角色类型").getValue():masterModel.getPropModelMap().get("操作角色类型").getValue();
				pDataMap.put("roleProcessRoleType",roleProcessRoleType.equals("固定的执行角色")?0:
													roleProcessRoleType.equals("流程中已经定制的执行角色")?1:
														roleProcessRoleType.equals("流程流转中关联的上下文")?2:
															roleProcessRoleType.equals("流程流转角色通过规则生成")?3:5);
				//roleProcessRoleType == 0
				pDataMap.put("assignee", stepShapeModel.getPropModelMap().get("操作人")!=null?stepShapeModel.getPropModelMap().get("操作人").getValue():masterModel.getPropModelMap().get("操作人").getValue());
				pDataMap.put("assigneeID", stepShapeModel.getPropModelMap().get("操作人ID")!=null?stepShapeModel.getPropModelMap().get("操作人ID").getValue():masterModel.getPropModelMap().get("操作人ID").getValue());
				pDataMap.put("group", stepShapeModel.getPropModelMap().get("操作组")!=null?stepShapeModel.getPropModelMap().get("操作组").getValue():masterModel.getPropModelMap().get("操作组").getValue());
				pDataMap.put("groupID", stepShapeModel.getPropModelMap().get("操作组ID")!=null?stepShapeModel.getPropModelMap().get("操作组ID").getValue():masterModel.getPropModelMap().get("操作组ID").getValue());
				//roleProcessRoleType == 1
				pDataMap.put("roleKey", stepShapeModel.getPropModelMap().get("关键字")!=null?stepShapeModel.getPropModelMap().get("关键字").getValue():masterModel.getPropModelMap().get("关键字").getValue());
				//roleProcessRoleType == 2
				pDataMap.put("assigneeIDFieldID", stepShapeModel.getPropModelMap().get("操作人ID字段ID")!=null?stepShapeModel.getPropModelMap().get("操作人ID字段ID").getValue():masterModel.getPropModelMap().get("操作人ID字段ID").getValue());
				pDataMap.put("groupIDFieldID", stepShapeModel.getPropModelMap().get("操作组ID字段ID")!=null?stepShapeModel.getPropModelMap().get("操作组ID字段ID").getValue():masterModel.getPropModelMap().get("操作组ID字段ID").getValue());
				//roleProcessRoleType == 3
				pDataMap.put("conditionsDesc", stepShapeModel.getPropModelMap().get("角色细分规则")!=null?stepShapeModel.getPropModelMap().get("角色细分规则").getValue():masterModel.getPropModelMap().get("角色细分规则").getValue());
				
				String taskPolicy = stepShapeModel.getPropModelMap().get("任务策略")!=null?stepShapeModel.getPropModelMap().get("任务策略").getValue():masterModel.getPropModelMap().get("任务策略").getValue();
				pDataMap.put("taskPolicy",taskPolicy.equals("共享")?"SHARE":taskPolicy.equals("独占")?"ONLY":"MANAGEMENT");
				pDataMap.put("assignOver", stepShapeModel.getPropModelMap().get("派单时限")!=null?stepShapeModel.getPropModelMap().get("派单时限").getValue():masterModel.getPropModelMap().get("派单时限").getValue());
				pDataMap.put("acceptOver", stepShapeModel.getPropModelMap().get("受理时限")!=null?stepShapeModel.getPropModelMap().get("受理时限").getValue():masterModel.getPropModelMap().get("受理时限").getValue());
				pDataMap.put("dealOver", stepShapeModel.getPropModelMap().get("处理时限")!=null?stepShapeModel.getPropModelMap().get("处理时限").getValue():masterModel.getPropModelMap().get("处理时限").getValue());
				
				pDataMap.put("hasSubFlow", stepShapeModel.getPropModelMap().get("启动内部流程")!=null?(stepShapeModel.getPropModelMap().get("启动内部流程").getValue().equals("是")?1:0):masterModel.getPropModelMap().get("启动内部流程").getValue().equals("是")?1:0);
				//子流程维护
				Map<String,SubFlowModel> subFlowMap = new HashMap<String,SubFlowModel>();
				String subFlowDesc = stepShapeModel.getPropModelMap().get("子流程描述")!=null?stepShapeModel.getPropModelMap().get("子流程描述").getValue():masterModel.getPropModelMap().get("子流程描述").getValue();
				String[] subFlows = subFlowDesc.split("#");
				for (int j = 0; j < subFlows.length; j++) {
					String[] subFlow = subFlows[j].split(":");
					if(subFlow.length>1){
						SubFlowModel subFlowModel = new SubFlowModel();
						subFlowModel.setCode(subFlow[0]);
						subFlowModel.setName(subFlow[1]);
						subFlowModel.setSubflowCondition(subFlow[2]);
						subFlowMap.put(subFlow[0], subFlowModel);
					}
				}
				pDataMap.put("subFlowMap", subFlowMap);
				
				//自定义模型属性
				Map<String,String> metaMap =new HashMap<String,String>();
				metaMap.put("ACTIONNAME", stepShapeModel.getPropModelMap().get("动作名称")!=null?stepShapeModel.getPropModelMap().get("动作名称").getValue():masterModel.getPropModelMap().get("动作名称").getValue());
				//metaMap.put("actionPageName", stepShapeModel.getPropModelMap().get("动作页面")!=null?stepShapeModel.getPropModelMap().get("动作页面").getValue():masterModel.getPropModelMap().get("动作页面").getValue());
				//metaMap.put("actionPageID", stepShapeModel.getPropModelMap().get("动作页面ID")!=null?stepShapeModel.getPropModelMap().get("动作页面ID").getValue():masterModel.getPropModelMap().get("动作页面ID").getValue());
				metaMap.put("CUSTOMACTIONS", stepShapeModel.getPropModelMap().get("客户化动作")!=null?stepShapeModel.getPropModelMap().get("客户化动作").getValue():masterModel.getPropModelMap().get("客户化动作").getValue());
				String flagTypeString = stepShapeModel.getPropModelMap().get("处理类型")!=null?stepShapeModel.getPropModelMap().get("处理类型").getValue():masterModel.getPropModelMap().get("处理类型").getValue();
				metaMap.put("FLAGASSIGNTYPE", (flagTypeString.equals("主办")?0:
					flagTypeString.equals("协办")?1:
						flagTypeString.equals("抄送")?2:
							flagTypeString.equals("审批")?3:
								flagTypeString.equals("质检")?4:
									flagTypeString.equals("会审")?5:20)
						
						+"");
				metaMap.put("FLAGASSIGN", stepShapeModel.getPropModelMap().get("允许派单")!=null?(stepShapeModel.getPropModelMap().get("允许派单").getValue().equals("是")?"1":"0"):masterModel.getPropModelMap().get("允许派单").getValue().equals("是")?"1":"0");
				//metaMap.put("flag03Assist", stepShapeModel.getPropModelMap().get("允许协办")!=null?(stepShapeModel.getPropModelMap().get("允许协办").getValue().equals("是")?"1":"0"):masterModel.getPropModelMap().get("允许协办").getValue().equals("是")?"1":"0");
				metaMap.put("FLAGCOPY", stepShapeModel.getPropModelMap().get("允许抄送")!=null?(stepShapeModel.getPropModelMap().get("允许抄送").getValue().equals("是")?"1":"0"):masterModel.getPropModelMap().get("允许抄送").getValue().equals("是")?"1":"0");
				metaMap.put("FLAGTRANSFER", stepShapeModel.getPropModelMap().get("允许转单")!=null?(stepShapeModel.getPropModelMap().get("允许转单").getValue().equals("是")?"1":"0"):masterModel.getPropModelMap().get("允许转单").getValue().equals("是")?"1":"0");
				metaMap.put("FLAGRECALL", stepShapeModel.getPropModelMap().get("允许追单")!=null?(stepShapeModel.getPropModelMap().get("允许追单").getValue().equals("是")?"1":"0"):masterModel.getPropModelMap().get("允许追单").getValue().equals("是")?"1":"0");
				metaMap.put("FLAGTURNUP", stepShapeModel.getPropModelMap().get("允许驳回")!=null?(stepShapeModel.getPropModelMap().get("允许驳回").getValue().equals("是")?"1":"0"):masterModel.getPropModelMap().get("允许驳回").getValue().equals("是")?"1":"0");
				metaMap.put("FLAGTOAUDITING", stepShapeModel.getPropModelMap().get("允许审批")!=null?(stepShapeModel.getPropModelMap().get("允许审批").getValue().equals("是")?"1":"0"):masterModel.getPropModelMap().get("允许审批").getValue().equals("是")?"1":"0");
				metaMap.put("FLAGTOASSISTAUDITING", stepShapeModel.getPropModelMap().get("允许会审")!=null?(stepShapeModel.getPropModelMap().get("允许会审").getValue().equals("是")?"1":"0"):masterModel.getPropModelMap().get("允许会审").getValue().equals("是")?"1":"0");
				//metaMap.put("flag35IsCanCreateBase", stepShapeModel.getPropModelMap().get("其他流程")!=null?(stepShapeModel.getPropModelMap().get("其他流程").getValue().equals("是")?"1":"0"):masterModel.getPropModelMap().get("其他流程").getValue().equals("是")?"1":"0");
				pDataMap.put("metaMap", metaMap);
				
				//前置函数维护
				Map<String,FunctionModel> prefunctionMap =new HashMap<String, FunctionModel>();
				String prefunctionDesc = stepShapeModel.getPropModelMap().get("前置函数描述")!=null?stepShapeModel.getPropModelMap().get("前置函数描述").getValue():masterModel.getPropModelMap().get("前置函数描述").getValue();
				String[] prefunctions =  prefunctionDesc.split("#");
				for (int j = 0; j < prefunctions.length; j++) {
					String[] prefunction = prefunctions[j].split(":");
					if(prefunction.length>1){
						FunctionModel functionModel= new FunctionModel();
						functionModel.setFId(prefunction[0]);
						functionModel.setFName(prefunction[1]);
						prefunctionMap.put(prefunction[0], functionModel);
					}
				}
				pDataMap.put("prefunctionMap", prefunctionMap);
				
				//后置函数维护
				Map<String,FunctionModel> postfunctionMap =new HashMap<String, FunctionModel>();
				String postfunctionDesc = stepShapeModel.getPropModelMap().get("后置函数描述")!=null?stepShapeModel.getPropModelMap().get("后置函数描述").getValue():masterModel.getPropModelMap().get("后置函数描述").getValue();
				String[] postfunctions =  postfunctionDesc.split("#");
				for (int j = 0; j < postfunctions.length; j++) {
					String[] postfunction = postfunctions[j].split(":");
					if(postfunction.length>1){
						FunctionModel functionModel= new FunctionModel();
						functionModel.setFId(postfunction[0]);
						functionModel.setFName(postfunction[1]);
						prefunctionMap.put(postfunction[0], functionModel);
					}
				}
				pDataMap.put("postfunctionMap", postfunctionMap);
				stepModel.setPDataMap(pDataMap);
				stepList.add(stepModel);
				processMap.put(stepShapeModel.getId(), stepModel);
			}
		}
		public void setSplit(List<ShapeModel>splitShapeList, MasterModel masterModel){
			for (int i = 0; i < splitShapeList.size(); i++) {
				ShapeModel splitShapeModel = splitShapeList.get(i);
				ProcessModel splitModel = new ProcessModel();
				splitModel.setPId("dp_"+splitShapeModel.getId());
				splitModel.setPType("SPLIT");
				String statusId = "";
				String statusName = "";

				String[] statuStrings = getStatusIdAndName(splitShapeModel);
				statusId = statuStrings[0];
				statusName = statuStrings[1];
				splitModel.setPStatusId(statusId);
				
				splitModel.setPStatusName(statusName);
				
				//if(pxY!=0){
					splitModel.setPX((new Long(Math.round(Double.valueOf(splitShapeModel.getX())*25.4))*5-pxX)+"");
					splitModel.setPY((pxY-new Long(Math.round(Double.valueOf(splitShapeModel.getY())*25.4))*5)+"");
				//	}else {
				//	splitModel.setPX((new Long(Math.round(Double.valueOf(splitShapeModel.getX())*25.4))*5)+"");
				//	splitModel.setPY((new Long(Math.round(Double.valueOf(splitShapeModel.getY())*25.4))*5)+"");
				//	}
				
				
				Map<String,Object> pDataMap = new HashMap<String, Object>();
				String conditionType = splitShapeModel.getPropModelMap().get("条件类型")!=null?splitShapeModel.getPropModelMap().get("条件类型").getValue():masterModel.getPropModelMap().get("条件类型").getValue();
				pDataMap.put("conditionType",conditionType.equals("脚本")?"script":"class");
				pDataMap.put("conditionClass",splitShapeModel.getPropModelMap().get("实现类名称")!=null?splitShapeModel.getPropModelMap().get("实现类名称").getValue():masterModel.getPropModelMap().get("实现类名称").getValue());
				
				splitModel.setPDataMap(pDataMap);
				splitList.add(splitModel);
				processMap.put(splitShapeModel.getId(), splitModel);
			}
		}
		public void setJoin(List<ShapeModel> joinShapeList, MasterModel masterModel){
			for (int i = 0; i < joinShapeList.size(); i++) {
				ShapeModel joinShapeModel = joinShapeList.get(i);
				ProcessModel joinModel = new ProcessModel();
				joinModel.setPId("dp_"+joinShapeModel.getId());
				joinModel.setPType("JOIN");
				String statusId = "";
				String statusName = "";

				String[] statuStrings = getStatusIdAndName(joinShapeModel);
				statusId = statuStrings[0];
				statusName = statuStrings[1];
				
				joinModel.setPStatusId(statusId);
				joinModel.setPStatusName(statusName);
				//if(pxY!=0){
					joinModel.setPX((new Long(Math.round(Double.valueOf(joinShapeModel.getX())*25.4))*5-pxX)+"");
					joinModel.setPY((pxY-new Long(Math.round(Double.valueOf(joinShapeModel.getY())*25.4))*5)+"");
				//}else {
				//	joinModel.setPX((new Long(Math.round(Double.valueOf(joinShapeModel.getX())*25.4))*5)+"");
				//	joinModel.setPY((new Long(Math.round(Double.valueOf(joinShapeModel.getY())*25.4))*5)+"");	
				//}
				
				Map<String,Object> pDataMap =new HashMap<String, Object>();
				pDataMap.put("condition", joinShapeModel.getPropModelMap().get("合并条件")!=null?joinShapeModel.getPropModelMap().get("合并条件").getValue():masterModel.getPropModelMap().get("合并条件").getValue());
				joinModel.setPDataMap(pDataMap);
				joinList.add(joinModel);
				processMap.put(joinShapeModel.getId(), joinModel);
			}
		}
		public void setLink(List<ShapeModel> linkShapeList, MasterModel masterModel){
			for(int i = 0; i < linkShapeList.size(); i++)
			{
				    ShapeModel shapeModel = linkShapeList.get(i);
				
				    ConnectModel beginX = null;
					ConnectModel endX = null;
					
					for(ConnectModel connectModel : connectList)
					{
						if(connectModel.getFromSheet().equals(shapeModel.getId()) && connectModel.getFromCell().equals("BeginX"))
						{
							beginX = connectModel;
						}
						else if(connectModel.getFromSheet().equals(shapeModel.getId()) && connectModel.getFromCell().equals("EndX"))
						{
							endX = connectModel;
						}
						if(beginX != null && endX != null) break;
					}
					
					if(beginX != null && endX != null)
					{				
						ShapeModel beginModel = shapeMap.get(beginX.getToSheet());
						ShapeModel endModel = shapeMap.get(endX.getToSheet());
						LinkModel linkModel = new LinkModel();
						linkModel.setLId("l_"+shapeModel.getId());
						System.out.println("endModel:"+beginX.getToSheet());
						String beginPoint = beginModel.getConnectionPoint().split(",")[Integer.parseInt(beginX.getToPart().substring(2))];
						
						String endPoint = endModel.getConnectionPoint().split(",")[Integer.parseInt(endX.getToPart().substring(2))];
						linkModel.setLBpoint(beginPoint);
						linkModel.setLEpoint(endPoint);
						linkList.add(linkModel);
						
						ProcessModel processModel = processMap.get(beginModel.getId());
						ProcessModel endProcessModel = processMap.get(endModel.getId());
						linkModel.setLBprocess(processModel.getPId());
						linkModel.setLEprocess(endProcessModel.getPId());
						if(processModel!=null){
							List<ProcessModel> nextProcessModeList = processModel.getNextProcessModelList();
							if(nextProcessModeList==null)nextProcessModeList = new ArrayList<ProcessModel>();
							nextProcessModeList.add(endProcessModel);
							processModel.setNextProcessModelList(nextProcessModeList);
						}
						if(processModel.getPType().equals("SPLIT")){
							String condition = shapeModel.getPropModelMap().get("流转条件")!=null?shapeModel.getPropModelMap().get("流转条件").getValue():masterModel.getPropModelMap().get("流转条件").getValue();
							Map<String,String> conditionMap = processModel.getPDataMap().get("conditonMap")!=null?(Map<String, String>)processModel.getPDataMap().get("conditonMap"):new HashMap<String, String>();
							conditionMap.put(endProcessModel.getPId(), condition);
							processModel.getPDataMap().put("conditonMap", conditionMap);
						}
						if(endProcessModel.getPType().equals("JOIN")){
							String ids = processModel.getPDataMap().get("ids")!=null?processModel.getPDataMap().get("ids").toString():"";
							if(ids.length()>0){
								ids += ","+processModel.getPId();
							}else{
								ids = processModel.getPId();
							}
							processModel.getPDataMap().put("ids", ids);
						}
					}
				
			}
		}
		public void setEnd(List<ShapeModel> endShapeList,MasterModel endMasterModel){
			for (int i = 0; i < endShapeList.size(); i++) {
				ShapeModel endShapeModel = endShapeList.get(i);
				ProcessModel endModel = new ProcessModel();
				endModel.setPId("dp_"+endShapeModel.getId());
				endModel.setPType("END");
				String statusId = "";
				String statusName = "";
				String[] statuStrings = getStatusIdAndName(endShapeModel);
				statusId = statuStrings[0];
				statusName = statuStrings[1];
				endModel.setPStatusId(statusId);
				endModel.setPStatusName(statusName);
				
				//if(pxY!=0){
					endModel.setPX((new Long(Math.round(Double.valueOf(endShapeModel.getX())*25.4))*5-pxX)+"");
					endModel.setPY((pxY-new Long(Math.round(Double.valueOf(endShapeModel.getY())*25.4))*5)+"");
				//}else {
				//	endModel.setPX((new Long(Math.round(Double.valueOf(endShapeModel.getX())*25.4))*5)+"");
				//	endModel.setPY((new Long(Math.round(Double.valueOf(endShapeModel.getY())*25.4))*5)+"");
				//}
				endList.add(endModel);
				processMap.put(endShapeModel.getId(), endModel);
			}
		}
		public String getWfdesignxml(){
			Document doc = DocumentHelper.createDocument();
			Element designElement = doc.addElement("design");
			Element processesElement = designElement.addElement("processes");
			Iterator it = processMap.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry entry  = (Map.Entry)it.next();	
				ProcessModel valueModel = (ProcessModel)entry.getValue();
				Element processElement = processesElement.addElement("process");
				processElement.addAttribute("id", valueModel.getPId());
				processElement.addAttribute("type", valueModel.getPType());
				processElement.addAttribute("title", valueModel.getPTitle());
				processElement.addAttribute("content", valueModel.getPTitle());
				processElement.addAttribute("x", valueModel.getPX());
				processElement.addAttribute("y", valueModel.getPY());
				processElement.addAttribute("sid", valueModel.getPStatusId());
				processElement.addAttribute("sname", valueModel.getPStatusName());
				processElement.addAttribute("modelkey", valueModel.getPModelkey());
				processElement.addAttribute("imageurl", "");
			}
			Element linksElement = designElement.addElement("links");
			for (int i = 0; i < linkList.size(); i++) {
				LinkModel linkModel = linkList.get(i);
				Element linkElement = linksElement.addElement("link");
				linkElement.addAttribute("id", linkModel.getLId());
				linkElement.addAttribute("bprocess", linkModel.getLBprocess());
				linkElement.addAttribute("eprocess", linkModel.getLEprocess());
				linkElement.addAttribute("bpoint", linkModel.getLBpoint());
				linkElement.addAttribute("epoint", linkModel.getLEpoint());
			}
			
			Element statusesElement = designElement.addElement("statuses");
			for (int i = 0; i < statusList.size(); i++) {
				StatusModel statusModel = statusList.get(i);
				Element statusElement = statusesElement.addElement("statuses");
				statusElement.addAttribute("id", statusModel.getSid());
				statusElement.addAttribute("index", i+"");
				statusElement.addAttribute("name", statusModel.getSname());
				statusElement.addAttribute("width", statusModel.getSwidth());
				statusElement.addAttribute("desc", statusModel.getSdesc());
				
			}
			return doc.asXML();
		}
		public String getWfxml(){
			Document doc = DocumentHelper.createDocument();
			Element workflowElement = doc.addElement("workflow");
			//begin
			Element startElement = workflowElement.addElement("start");
			for (int i = 0; i < startModel.getNextProcessModelList().size(); i++) {
				ProcessModel processModel = startModel.getNextProcessModelList().get(i);
				startElement.addAttribute("to", processModel.getPId());
				startElement.addAttribute("statusId", processModel.getPStatusId());
			}
			//steps
			Element stepsElement = workflowElement.addElement("steps");
			for (int i = 0; i < stepList.size(); i++) {
				Element stepElement = stepsElement.addElement("step");
				ProcessModel stepModel = stepList.get(i);
				stepElement.addAttribute("id", stepModel.getPId());
				stepElement.addAttribute("statusId", stepModel.getPStatusId());
				
				String desc = stepModel.getPDataMap().get("desc")!=null?stepModel.getPDataMap().get("desc").toString():"";
				String taskPolicy = stepModel.getPDataMap().get("taskPolicy")!=null?stepModel.getPDataMap().get("taskPolicy").toString():"";
				String hasSubFlow = stepModel.getPDataMap().get("hasSubFlow")!=null?stepModel.getPDataMap().get("hasSubFlow").toString():"";
				Map<String,SubFlowModel> subFlowMap = (Map<String,SubFlowModel>)stepModel.getPDataMap().get("subFlowMap");
				int subflowSize = subFlowMap.keySet().size();
				String assignOver = stepModel.getPDataMap().get("assignOver")!=null?stepModel.getPDataMap().get("assignOver").toString():"";
				String acceptOver = stepModel.getPDataMap().get("acceptOver")!=null?stepModel.getPDataMap().get("acceptOver").toString():"";
				String dealOver = stepModel.getPDataMap().get("dealOver")!=null?stepModel.getPDataMap().get("dealOver").toString():"";
				Map<String,FunctionModel> prefunctionMap = (Map<String,FunctionModel>)stepModel.getPDataMap().get("prefunctionMap");
				Map<String,FunctionModel> postfunctionMap = (Map<String,FunctionModel>)stepModel.getPDataMap().get("postfunctionMap");
				String roleID = stepModel.getPDataMap().get("roleID")!=null?stepModel.getPDataMap().get("roleID").toString():"";
				String roleName = stepModel.getPDataMap().get("roleName")!=null?stepModel.getPDataMap().get("roleName").toString():"";
				String roleProcessRoleType = stepModel.getPDataMap().get("roleProcessRoleType")!=null?stepModel.getPDataMap().get("roleProcessRoleType").toString():"";
				StringBuffer actorString = new StringBuffer();
				if(roleProcessRoleType.equals("0")){
					String assignee = stepModel.getPDataMap().get("assignee")!=null?stepModel.getPDataMap().get("assignee").toString():"";
					String assigneeID = stepModel.getPDataMap().get("assigneeID")!=null?stepModel.getPDataMap().get("assigneeID").toString():"";
					String group = stepModel.getPDataMap().get("group")!=null?stepModel.getPDataMap().get("group").toString():"";
					String groupID = stepModel.getPDataMap().get("groupID")!=null?stepModel.getPDataMap().get("groupID").toString():"";
					actorString.append("assignee:"+assignee).append("#assigneeID:"+assigneeID).append("#group:"+group).append("#groupID:"+groupID);
				}else if(roleProcessRoleType.equals("1")){
					String roleKey = stepModel.getPDataMap().get("roleKey")!=null?stepModel.getPDataMap().get("roleKey").toString():"";
					actorString.append("roleKey:"+roleKey);
				}else if(roleProcessRoleType.equals("2")){
					String assigneeIDFieldID = stepModel.getPDataMap().get("assigneeIDFieldID")!=null?stepModel.getPDataMap().get("assigneeIDFieldID").toString():"";
					String groupIDFieldID = stepModel.getPDataMap().get("groupIDFieldID")!=null?stepModel.getPDataMap().get("groupIDFieldID").toString():"";
					actorString.append("assigneeIDFieldID:"+assigneeIDFieldID).append("#groupIDFieldID:"+groupIDFieldID);
				}else if(roleProcessRoleType.equals("3")){
					String conditionsDesc = stepModel.getPDataMap().get("conditionsDesc")!=null?stepModel.getPDataMap().get("conditionsDesc").toString():"";
					actorString.append("conditionsDesc:"+conditionsDesc);
				}
				Map<String, Object> metasMap = (Map<String, Object>)stepModel.getPDataMap().get("metaMap");
				
				
				stepElement.addAttribute("desc", desc);
				stepElement.addAttribute("taskPolicy", taskPolicy);
				stepElement.addAttribute("auto", (subflowSize>0&&hasSubFlow.equals("1"))?"true":"false");
				stepElement.addAttribute("assignOver", assignOver);
				stepElement.addAttribute("acceptOver", acceptOver);
				stepElement.addAttribute("dealOver", dealOver);
				
				//前置函数
				Element prefuncsElement = stepElement.addElement("prefuncs");
				Iterator iterator = prefunctionMap.entrySet().iterator();
				int index = 0;
				while (iterator.hasNext()) {
					
					Map.Entry entry = (Map.Entry)iterator.next();
					FunctionModel functionModel = (FunctionModel)entry.getValue();
					Element funcElement = prefuncsElement.addElement("func");
					funcElement.addAttribute("id", index+"");
					funcElement.addText(functionModel.getFId());
					
					index++;
				}
				
				//actor
				Element actorElement = stepElement.addElement("actor");
				actorElement.addAttribute("id", "actor");
				actorElement.addAttribute("roleID", roleID); roleCodes += roleCodes.length()>0?"#"+roleID:roleID;
				actorElement.addAttribute("roleName", roleName);roleNames += roleNames.length()>0?"#"+roleName:roleName;
				actorElement.addAttribute("actorType", roleProcessRoleType);
				actorElement.addText(actorString.toString());
				
				//actions
				Element actionsElement = stepElement.addElement("actions");
				for (int j = 0; j < stepModel.getNextProcessModelList().size(); j++) {
					ProcessModel nextProcessModel = stepModel.getNextProcessModelList().get(j);
					Element actionElement = actionsElement.addElement("action");
					String processid = nextProcessModel.getPType().equals("STEP")?"stepId":
												nextProcessModel.getPType().equals("SPLIT")?"splitId":
													nextProcessModel.getPType().equals("JOIN")?"joinId":
														nextProcessModel.getPType().equals("END")?"joinId":"";
					actionElement.addAttribute("id", j+"");
					if(!"".equals(processid))
					actionElement.addAttribute(processid, nextProcessModel.getPId());
				}
				
				//metas
				Element metasElement = stepElement.addElement("metas");
				iterator = metasMap.entrySet().iterator();
				index = 0;
				while (iterator.hasNext()) {
					Map.Entry entry = (Map.Entry)iterator.next();
					String entryKey = (String)entry.getKey();
					String entryValue = (String)entry.getValue();
					Element metaElement = metasElement.addElement("meta");
					metaElement.addAttribute("id", index+"");
					metaElement.addAttribute("name", entryKey);
					metaElement.addText(entryValue);
					index++;
				}
				
				//subflow
				Element subflowsElement = stepElement.addElement("subflows");
				StringBuffer subflowCondition = new  StringBuffer();
				iterator = subFlowMap.entrySet().iterator();
				index = 0;
				while (iterator.hasNext()) {
					Map.Entry entry = (Map.Entry)iterator.next();
					String entryKey = (String)entry.getKey();
					SubFlowModel entryValue = (SubFlowModel)entry.getValue();
					Element subflowElement = subflowsElement.addElement("subflow");
					subflowElement.addAttribute("id", index+"");
					subflowElement.addAttribute("name", entryValue.getName());
					subflowElement.addAttribute("type", entryKey);
					if(index==0)subflowCondition.append(entryKey+":"+entryValue.getSubflowCondition());
					else subflowCondition.append("#"+entryKey+":"+entryValue.getSubflowCondition());
					index++;
				}
				Element conditionElement = subflowsElement.addElement("condition");
				conditionElement.addText(subflowCondition.toString());
				
				//后置函数
				Element postfuncsElement = stepElement.addElement("postfuncs");
				iterator = prefunctionMap.entrySet().iterator();
				index = 0;
				while (iterator.hasNext()) {
					
					Map.Entry entry = (Map.Entry)iterator.next();
					FunctionModel functionModel = (FunctionModel)entry.getValue();
					Element funcElement = prefuncsElement.addElement("func");
					funcElement.addAttribute("id", index+"");
					funcElement.addText(functionModel.getFId());
					index++;
				}
				
			}
			//splits
			Element splitsElement =  workflowElement.addElement("splits");
			for (int i = 0; i < splitList.size(); i++) {
				ProcessModel splitModel = splitList.get(i);
				String conditionType = splitModel.getPDataMap().get("conditionType")!=null?splitModel.getPDataMap().get("conditionType").toString():"";
				String conditionClass = splitModel.getPDataMap().get("conditionClass")!=null?splitModel.getPDataMap().get("conditionClass").toString():"";
				 
				StringBuffer metaString = new StringBuffer();
				Map<String,String> conditionMap = splitModel.getPDataMap().get("conditonMap")!=null?(Map<String, String>)splitModel.getPDataMap().get("conditonMap"):new HashMap<String, String>();
				Element splitElement = splitsElement.addElement("split");
				splitElement.addAttribute("id", splitModel.getPId());
				splitElement.addAttribute("statusId", splitModel.getPStatusId());
				splitElement.addAttribute("statusId", splitModel.getPStatusId());
				Element conditionElement = splitElement.addElement("condition");
				conditionElement.addAttribute("type", conditionType);
				
				if(conditionType.equals("script")){
					Element tosElement = splitElement.addElement("tos");
					for (int j = 0; j < splitModel.getNextProcessModelList().size(); j++) {
						ProcessModel nextProcessModel = splitModel.getNextProcessModelList().get(j);
						String processid = nextProcessModel.getPType().equals("STEP")?"stepId":
							nextProcessModel.getPType().equals("SPLIT")?"splitId":
								nextProcessModel.getPType().equals("JOIN")?"joinId":
									nextProcessModel.getPType().equals("END")?"joinId":"";
						Element toElement = tosElement.addElement("to");
						toElement.addAttribute("id", j+"");
						toElement.addAttribute(processid, nextProcessModel.getPId());
						if(j==0)metaString.append(nextProcessModel.getPId()+":"+conditionMap.get(nextProcessModel.getPId()));
						else metaString.append("#"+nextProcessModel.getPId()+":"+conditionMap.get(nextProcessModel.getPId()));
					}
					conditionElement.addText(metaString.toString());
				}else conditionElement.addText(conditionClass);
			}
			
			//joins
			Element joinsElement =  workflowElement.addElement("joins");
			for (int i = 0; i < joinList.size(); i++) {
				ProcessModel joinModel = joinList.get(i);
				String condition = joinModel.getPDataMap().get("condition")!=null?joinModel.getPDataMap().get("condition").toString():"";
				String ids = joinModel.getPDataMap().get("ids")!=null?joinModel.getPDataMap().get("ids").toString():"";
				
				Element joinElement = joinsElement.addElement("join");
				joinElement.addAttribute("id", joinModel.getPId());
				joinElement.addAttribute("value", ids);
				
				Element conditionElement = joinElement.addElement("condition");
				conditionElement.addText(condition);
				
				Element toElement = joinElement.addElement("to");
				toElement.addAttribute("id", i+"");
				
				for (int j = 0; j < joinModel.getNextProcessModelList().size(); j++) {
					ProcessModel nextProcessModel = joinModel.getNextProcessModelList().get(j);
					String processid = nextProcessModel.getPType().equals("STEP")?"stepId":
						nextProcessModel.getPType().equals("SPLIT")?"splitId":
							nextProcessModel.getPType().equals("JOIN")?"joinId":
								nextProcessModel.getPType().equals("END")?"joinId":"";
					toElement.addAttribute(processid, nextProcessModel.getPId());
				}
			}
			
			//ends
			Element endsElement = workflowElement.addElement("ends");
			for (int i = 0; i < endList.size(); i++) {
				ProcessModel endModel = endList.get(i);
				Element endElement = endsElement.addElement("end");
				endElement.addAttribute("id", endModel.getPId());
				endElement.addAttribute("statusId", endModel.getPStatusId());
			}
			
			//status
			Element statusesElement = workflowElement.addElement("statuses");
			for (int i = 0; i < statusList.size(); i++) {
				StatusModel statusModel = statusList.get(i);
				Element statusElement = statusesElement.addElement("status");
				statusElement.addAttribute("id", statusModel.getSid());
				statusElement.addAttribute("name", statusModel.getSname());
			}
			
			return doc.asXML();
		}
		
		public String[] getStatusIdAndName(ShapeModel shapeModel){
			String statusId = "";
			String statusName = "";
			for(ShapeModel statusModel : shapeMap.values())
			{
				
				if(masterMap.get(statusModel.getMaster())!=null&&masterMap.get(statusModel.getMaster()).getName().equals("状态")&&masterMap.get(statusModel.getMaster()).propModelMap.size()>0
						&&((shapeModel.getFormat()!=null&&statusModel.getId().equals(shapeModel.getFormat().substring(shapeModel.getFormat().indexOf(".")+1)))
						|| statusModel.getNameU() != null && statusModel.getNameU().equals(shapeModel.getFormat())))
				{
					statusId = "s_"+statusModel.getId();
					statusName = statusModel.getPropModelMap().get("状态名称").getValue();
					break;
				}
			}
  		   if(shapeModel.getFormat()==null&&statusId.equals("")&&statusName.equals("")){ 
				for (int i = 0; i < statusList.size(); i++) {
					String statusnameString = shapeModel.getPropModelMap().get("")!=null?shapeModel.getPropModelMap().get("").getValue():"";
					StatusModel statusModel = statusList.get(i);
					if(statusModel.getSname().equals(statusnameString.replace("\n", ""))){
						statusId = statusModel.getSid();
						statusName = statusModel.getSname();
					};
				}
			}
			String[] statuStrings = new String[2];
			statuStrings[0]=statusId;
			statuStrings[1]=statusName;
			return statuStrings;
		}
}
