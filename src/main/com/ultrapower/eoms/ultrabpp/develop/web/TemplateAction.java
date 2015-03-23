package com.ultrapower.eoms.ultrabpp.develop.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.core.io.Resource;

import com.ultrapower.eoms.common.core.component.cache.manager.BaseCacheManager;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.ultrabpp.cache.model.ActionFieldRelationModel;
import com.ultrapower.eoms.ultrabpp.cache.model.ActionModel;
import com.ultrapower.eoms.ultrabpp.cache.model.AssignTreeConfigModel;
import com.ultrapower.eoms.ultrabpp.cache.model.AssignTreeOrganizeModel;
import com.ultrapower.eoms.ultrabpp.cache.model.FieldConfig;
import com.ultrapower.eoms.ultrabpp.cache.model.FreeActionFieldRelationModel;
import com.ultrapower.eoms.ultrabpp.cache.model.StepFieldRelationModel;
import com.ultrapower.eoms.ultrabpp.cache.model.StepModel;
import com.ultrapower.eoms.ultrabpp.develop.model.TreeNode;
import com.ultrapower.eoms.ultrabpp.develop.service.DeployService;
import com.ultrapower.eoms.ultrabpp.develop.service.TemplateService;
import com.ultrapower.eoms.ultrabpp.model.BaseField;
import com.ultrapower.eoms.ultrabpp.model.component.compositefield.panel.PanelField;
import com.ultrapower.eoms.ultrabpp.model.component.compositefield.panelgroup.PanelGroupField;
import com.ultrapower.eoms.ultrabpp.model.component.datafield.DataField;
import com.ultrapower.eoms.ultrabpp.model.component.datafield.characterfield.CharacterField;
import com.ultrapower.eoms.ultrabpp.model.component.datafield.collectfield.CollectField;
import com.ultrapower.eoms.ultrabpp.model.component.datafield.hiddenfield.HiddenField;
import com.ultrapower.eoms.ultrabpp.model.component.datafield.selectfield.SelectField;
import com.ultrapower.eoms.ultrabpp.model.component.datafield.timefield.TimeField;
import com.ultrapower.eoms.ultrabpp.model.component.displayfield.blankfield.BlankField;
import com.ultrapower.eoms.ultrabpp.model.component.displayfield.buttonfield.ButtonField;
import com.ultrapower.eoms.ultrabpp.model.component.displayfield.labelfield.LabelField;
import com.ultrapower.eoms.ultrabpp.model.component.displayfield.viewfield.ViewField;
import com.ultrapower.eoms.ultrabpp.model.component.specialfield.assigntreefield.AssignTreeField;
import com.ultrapower.eoms.ultrabpp.model.component.specialfield.attachmentfield.AttachmentField;
import com.ultrapower.eoms.ultrabpp.model.component.specialfield.rollbacklistfield.RollbackListField;
import com.ultrapower.eoms.ultrasm.service.RoleManagerService;
import com.ultrapower.eoms.workflow.sort.service.WfSortMenuService;
import com.ultrapower.workflow.bizconfig.sort.IWfSortManager;
import com.ultrapower.workflow.client.WorkFlowServiceClient;
import com.ultrapower.workflow.configuration.sort.model.WfType;
import com.ultrapower.workflow.utils.UUIDGenerator;

/**
 * @author fying
 * @version
 * @since
 * @see TemplateDoc
 */
public class TemplateAction extends BaseAction
{
	private final String FIELDINFO_CACHEKEY = "bppFieldInfoMap";
	private RoleManagerService roleManagerService;
	private WfSortMenuService wfSortMenuFree;
	private WfSortMenuService wfSortMenuFix;

	private TemplateService templateService;
	private IDao<WfType> typeDao;
	private IDao hibernateDao;
	private IDao jdbcDao;

	/**
	 * 自由字段列表展示
	 */
	private String isFree;
	/**
	 * 工单类型
	 */
	public String baseSchema;
	/**
	 * 字段类型
	 */
	private String fieldType;
	/**
	 * 字段ID
	 */
	private String fieldID;
	private String stepNo;
	private String actionID;
	private String actionName;
	/**
	 * 弹出 editFreeActionfieldrel.jsp 需要的一个临时 标志
	 */
	private String action;
	private String flag;
	/**
	 * 跳转 struts配置文件命名冲突 actionName
	 */
	public String actionN;
	private String idString;
	private String actionField;
	private String delFieldID;
	private String delFieldType;
	private String msg;
	private String delvalue;

	private List<StepModel> stepList;
	private List<BaseField> baseFieldList;
	private List<BaseField> actionFieldList;
	
	public String interfaceXMLName;
	/**
	 * 自由流程 主表单页面中的 提供给动作关联的组件列表
	 */
	private List<BaseField> actionFieldFreeList;
	private List<StepFieldRelationModel> stepFieldRelationList;
	private List<ActionModel> actionList;
	private List<ActionFieldRelationModel> actionFieldRelationList;
	private List<PanelField> panelFieldList;
	private List<PanelGroupField> panelGroupFieldList;
	private List<FreeActionFieldRelationModel> freeActionfieldrelList;
	private List<ActionModel> freeActionList;
	private List<FieldConfig> fieldConfigList;
	// 自由动作部署时 页面显示列表
	private List<Object[]> statusAndFreeActionList;

	private PanelField panelField;
	private PanelGroupField panelGroupField;

	private CharacterField characterField;
	private HiddenField hiddenField;
	private SelectField selectField;
	private CollectField collectField;
	private TimeField timeField;

	private BlankField blankField;
	private ButtonField buttonField;
	private LabelField labelField;
	private ViewField viewField;

	private AssignTreeField assignTreeField;
	private AttachmentField attachmentField;
	private RollbackListField rollbackListField;

	private BaseField baseField;

	private StepModel stepModel;
	private ActionModel actionModel;
	private FreeActionFieldRelationModel freeActionfieldrel;

	// 表单部署和预览
	private DeployService deployService;
	private boolean isForce;

	private StepFieldRelationModel stepFieldRelationModel;
	private ActionFieldRelationModel actionFieldRelationModel;

	private String optionsString;
	private String optionsStringOther;

	//派发树配置列表
	private List<AssignTreeConfigModel> configList;
	private Map<String, String> configMap;
	private String configOptions;
	private String stepDesc;
	private String actionLabel;
	private String fieldName;
	private String fieldLabel;
	private String organizeInfo;
	private String configID;

	/**
	 * 自由类型工单 状态动作配置页面 数据增加或者修改 其中 freeActionList为 自由类型工单动作类型 下拉选的初始化
	 */
	public String editFreeActionfieldrel()
	{

		if (id == null)
		{
			baseField = (BaseField) hibernateDao.findUnique("from " + fieldType + " where baseSchema=? and id=?", new String[] { baseSchema, fieldID });
			freeActionfieldrel = new FreeActionFieldRelationModel();
			freeActionfieldrel.setBaseSchema(baseSchema);
			freeActionfieldrel.setFieldType(fieldType);
			freeActionfieldrel.setFieldID(fieldID);
			freeActionfieldrel.setVisiable(1);
		}
		else
		{
			freeActionfieldrel = (FreeActionFieldRelationModel) hibernateDao.findUnique("from FreeActionFieldRelationModel where id=?", new String[] { id });
			baseField = (BaseField) hibernateDao.findUnique("from " + freeActionfieldrel.getFieldType() + " where baseSchema=? and id=?", new String[] { freeActionfieldrel.getBaseSchema(), freeActionfieldrel.getFieldID() });
		}

		freeActionList = templateService.getFreeActionList();
		ActionModel actionModel = new ActionModel();
		actionModel.setActionType("");
		actionModel.setLabel("请选择动作页面需要配置的字段");
		freeActionList.add(0, actionModel);
		return "developfree";
	}

	/**
	 * dingfangwen 工单类型为自由类型的时候，字段配置列表
	 */
	public String freeActionfieldrelList()
	{
		List<BaseField> baseFieldListTemp = templateService.mainFieldList(baseSchema);

		List<BaseField> baseListDel = new ArrayList<BaseField>();
		for (BaseField temp : baseFieldListTemp)
		{
			if (temp != null)
			{
				temp.setId(temp.getId() + "*" + temp.getFieldType());
				if (temp.getFieldType() == "PanelField" || temp.getFieldType() == "PanelGroupField")
				{
					baseListDel.add(temp);
				}
			}
		}
		// dingfangwen 去掉 下拉选中 baseField.fieldType == "PanelField" or
		// "PanelGroupField"的 部分
		for (BaseField tempDel : baseListDel)
		{
			baseFieldListTemp.remove(tempDel);
		}

		BaseField baseField1 = new PanelGroupField();
		baseField1.setId("");
		baseField1.setLabel("请选择主表单字段");

		baseFieldListTemp.add(0, baseField1);
		baseFieldList = baseFieldListTemp;

		// 自由工单list界面 第二个list的初始化
		List<BaseField> baseActionFieldListTemp = templateService.actionFieldFreeList(baseSchema);
		for (BaseField temp : baseActionFieldListTemp)
		{
			if (temp != null)
			{
				temp.setId(temp.getId() + "*" + temp.getFieldType());
			}
		}
		BaseField baseField2 = new PanelGroupField();
		baseField2.setId("");
		baseField2.setLabel("请选择动作字段");
		baseActionFieldListTemp.add(0, baseField2);

		actionFieldFreeList = baseActionFieldListTemp;
		// dingfangwen 自由工单list界面 主list的初始化
		freeActionfieldrelList = templateService.getFreeActionfieldrelList(baseSchema);

		return "developfree";
	}

	/**
	 * dingfangwen 自由工单动作修改保存
	 */
	public String saveFreeActionfieldrel()
	{

		if (freeActionfieldrel != null)
		{
			String status = freeActionfieldrel.getBaseStatus();
			if (status.contains(","))
			{
				String[] statusArray = status.split(",");
				hibernateDao.executeUpdate("delete from FreeActionFieldRelationModel where baseSchema=? and fieldID=? and fieldType=?", new String[] { freeActionfieldrel.getBaseSchema(), freeActionfieldrel.getFieldID(), freeActionfieldrel.getFieldType() });
				for (String baseStatus : statusArray)
				{
					FreeActionFieldRelationModel tempModel = new FreeActionFieldRelationModel();
					tempModel.setId(UUIDGenerator.getId());
					tempModel.setActionType(freeActionfieldrel.getActionType());
					tempModel.setBaseSchema(freeActionfieldrel.getBaseSchema());
					tempModel.setBaseStatus(baseStatus);
					tempModel.setFieldID(freeActionfieldrel.getFieldID());
					tempModel.setFieldType(freeActionfieldrel.getFieldType());
					tempModel.setRequired(freeActionfieldrel.getRequired());
					tempModel.setVisiable(freeActionfieldrel.getVisiable());
					hibernateDao.save(tempModel);
				}
			}
			else
			{
				if (freeActionfieldrel.getId() == null || "".equals(freeActionfieldrel.getId()))
				{
					freeActionfieldrel.setId(UUIDGenerator.getId());
				}
				hibernateDao.saveOrUpdate(freeActionfieldrel);
			}
		}
		return "refresh";
	}

	/**
	 * dingfangwen 自由工单动作 删除
	 */

	public String delFreeActionfieldrel()
	{
		freeActionfieldrel = (FreeActionFieldRelationModel) hibernateDao.findUnique("from FreeActionFieldRelationModel where id=? and baseSchema=?", new String[] { id, baseSchema });

		hibernateDao.remove(freeActionfieldrel);

		return this.findRedirectPar("freeActionfieldrelList.action?baseSchema=" + baseSchema);
	}

	/***************************************************************************
	 * dingfangwen 固定工单动作动作 超链接 弹出的 list的窗口数据
	 */

	public String deployMain()
	{
		try
		{

			IWfSortManager sortService = WorkFlowServiceClient.clientInstance().getSortService();
			WfType wfType = sortService.getWfTypeByCode(baseSchema);

			if (wfType.getPanelGroupID() == null || wfType.getPanelGroupID().length() == 0)
			{
				List<PanelGroupField> panelGroupFieldList = (List<PanelGroupField>) hibernateDao.find("from PanelGroupField where baseSchema=? and parentID='main'", baseSchema);
				if (panelGroupFieldList == null || panelGroupFieldList.size() == 0)
				{
					PanelGroupField panelGroup = new PanelGroupField();
					String id = UUIDGenerator.getId();
					panelGroup.setId(id);
					panelGroup.setBaseSchema(baseSchema);
					panelGroup.setColspan(8);
					panelGroup.setFieldName("PG_Main");
					panelGroup.setParentID("main");
					panelGroup.setOrderNum(0);
					panelGroup.setLabel("工单主体部分");
					panelGroup.setVisiable(1);
					panelGroup.setTitleVisible(1);
					panelGroup.setType("tab");
					panelGroup.setHasDeploy(0);
					panelGroup.setOperate("add");
					hibernateDao.save(panelGroup);
					wfType.setPanelGroupID(id);
					sortService.saveWfType(wfType);

				}
				else
				{
					wfType.setPanelGroupID(panelGroupFieldList.get(0).getId());
					sortService.saveWfType(wfType);
				}
			}

		}
		catch (RemoteException e)
		{
			e.printStackTrace();
		}

		baseFieldList = templateService.mainFieldList(baseSchema);
		actionFieldFreeList = templateService.actionFieldFreeList(baseSchema);
		fieldConfigList = templateService.getFieldInfoList();

		makeSelectObjectString();

		return "developform";
	}
	
	//生成接口需要的字段XML，刨除interfaceXMLName中已有的字段，默认datatype是4文本，非必填
	public void generateFiledXMLForInterface() throws IOException, DocumentException{
		baseFieldList = templateService.mainFieldList(baseSchema);
		Document document = DocumentHelper.createDocument();
		Element fields = document.addElement("fields");
		document.setRootElement(fields);
		
		Document existInterfaceXml = null;//已有的接口字段xml文件
		if(org.apache.commons.lang3.StringUtils.isNotEmpty(interfaceXMLName)){
			String realPath = WebApplicationManager.servletContext.getRealPath("/") + "/WEB-INF/classes/wfinterface/services/"+interfaceXMLName+".xml";
			Resource resource2 = WebApplicationManager.webApplicationContext.getResource("file:"+realPath);
			SAXReader saxReader;
			if(resource2.exists()){
				saxReader = new SAXReader();
				existInterfaceXml = saxReader.read(resource2.getFile());
			}
			
		}
		
		for(BaseField baseField : baseFieldList){
			String fieldType = baseField.getFieldType();
			//容器组，容器，视图，附件不需要
			if(org.apache.commons.lang3.StringUtils.equals("PanelGroupField", fieldType) ||
			   org.apache.commons.lang3.StringUtils.equals("PanelField", fieldType)||
			   org.apache.commons.lang3.StringUtils.equals("ViewField", fieldType)||
			   org.apache.commons.lang3.StringUtils.equals("AttachmentField", fieldType)){
				continue;
			}
			DataField dataField = (DataField) baseField;
			if(existInterfaceXml != null){
				Node field = existInterfaceXml.selectSingleNode(String.format("//field[@name='%s']", dataField.getFieldName()));
				if(field != null){//xml中没有此字段
					continue;
				}
			}
			
			Element field = fields.addElement("field");
			field.addAttribute("name", baseField.getFieldName());
			field.addAttribute("text", baseField.getLabel());
			Element xmlColname = field.addElement("xml-colname");
			xmlColname.setText(baseField.getFieldName());
			Element dbColname = field.addElement("db-colname");
			dbColname.setText(baseField.getFieldName());
			Element datatype = field.addElement("datatype");
			datatype.setText("4");
			Element length = field.addElement("length");
			length.setText(dataField.getLength().toString());
			Element isnull = field.addElement("isnull");
			isnull.setText("true");
			Element defaultvalue = field.addElement("defaultvalue");
			defaultvalue.setText("");
		}
		OutputFormat format = OutputFormat.createPrettyPrint();
		StringWriter stringWriter = new StringWriter();
		XMLWriter writer = new XMLWriter(stringWriter,format);
		writer.write(fields); 
		renderText(stringWriter.toString()+"\n"+getCSVString(fields));
		writer.close();
		stringWriter.close();
	}
	
	//根据给定的xml文档，将其转换成CSV文件格式
	private String getCSVString(Element fields){
		StringBuffer csv = new StringBuffer();
		for(Object o : fields.selectNodes("//field")){
			Element e = (Element)o;
			csv.append("\n").append(e.attributeValue("text"))
			.append(",").append(e.attributeValue("name"))
			.append(",").append(e.elementText("length"));
		}
		return csv.toString();
	}

	private void makeSelectObjectString()
	{

		StringBuffer optionStringBuffer = new StringBuffer();
		optionStringBuffer.append("<select id='mainFieldSelect' name='mainFieldSelect' onchange='addMainField(this.value);'><option value='0'>请选择添加主表单组件</option>");
		for (int i = 0; i < fieldConfigList.size(); i++)
		{
			FieldConfig fieldConfig = fieldConfigList.get(i);
			if (i == 0)
			{
				optionStringBuffer.append("<optgroup label='" + fieldConfig.getExtendsLabel() + "'/>");
				optionStringBuffer.append("<option value='" + fieldConfig.getName() + "'>" + fieldConfig.getLabel() + "</option>");
			}
			else
			{
				FieldConfig fieldConfigLast = fieldConfigList.get(i - 1);
				if (!fieldConfigLast.getExtendsLabel().equals(fieldConfig.getExtendsLabel()))
				{
					optionStringBuffer.append("<optgroup label='" + fieldConfig.getExtendsLabel() + "'/>");
				}
				optionStringBuffer.append("<option value='" + fieldConfig.getName() + "'>" + fieldConfig.getLabel() + "</option>");
			}
		}
		optionStringBuffer.append("</select>");
		optionsString = optionStringBuffer.toString();

		optionStringBuffer = new StringBuffer();
		optionStringBuffer.append("<select id='actionFieldSelect' name='actionFieldSelect' onchange='addActionField(this.value);'><option value='0'>请选择添加动作组件</option>");
		for (int i = 0; i < fieldConfigList.size(); i++)
		{
			FieldConfig fieldConfig = fieldConfigList.get(i);
			if (fieldConfig.getExtendsName().equals("CompositeField"))
				continue;
			if (i == 0)
			{
				optionStringBuffer.append("<optgroup label='" + fieldConfig.getExtendsLabel() + "'/>");
				optionStringBuffer.append("<option value='" + fieldConfig.getName() + "'>" + fieldConfig.getLabel() + "</option>");
			}
			else
			{
				FieldConfig fieldConfigLast = fieldConfigList.get(i - 1);
				if (!fieldConfigLast.getExtendsLabel().equals(fieldConfig.getExtendsLabel()))
				{
					optionStringBuffer.append("<optgroup label='" + fieldConfig.getExtendsLabel() + "'/>");
				}
				optionStringBuffer.append("<option value='" + fieldConfig.getName() + "'>" + fieldConfig.getLabel() + "</option>");
			}
		}
		optionStringBuffer.append("</select>");
		optionsStringOther = optionStringBuffer.toString();

	}

	public String deployStep()
	{
		return "developfix";
	}

	public String deployAction()
	{
		actionFieldRelationList = templateService.actionFieldList(baseSchema, stepNo, actionName);
		return "developfix";
	}

	// 自由动作字段配置页面 进行部署操作 弹出部署页面
	public String deployFreeActionField()
	{
		statusAndFreeActionList = hibernateDao.find("select baseStatus,actionType from FreeActionFieldRelationModel where baseSchema=? and actionType is not null group by baseStatus,actionType order by actionType", new String[] { baseSchema });
		return "developfree";
	}

	public String stepList()
	{
		stepList = hibernateDao.find("from StepModel where baseSchema=? order by stepNo", baseSchema);// templateService.getStepList(baseSchema);
		return "developfix";
	}

	public String stepFieldList()
	{
		stepFieldRelationList = templateService.stepFieldList(baseSchema, stepNo);
		return "developfix";
	}

	public String actionList()
	{
		actionList = hibernateDao.find("from ActionModel where baseSchema=? and stepNo=? order by orderId", new String[] { baseSchema, stepNo });
		return "developfix";
	}

	public String editAction()
	{
		if (actionID == null)
		{
			actionModel = new ActionModel();
			actionModel.setBaseSchema(baseSchema);
			actionModel.setStepNo(stepNo);
			actionModel.setIsClose(1);
			actionModel.setIsFree(0);
			actionModel.setHasNext(1);
		}
		else
		{
			actionModel = (ActionModel) hibernateDao.findUnique("from ActionModel where stepNo=? and baseSchema=? and id=?", new String[] { stepNo, baseSchema, actionID });
		}
		return "developfix";
	}

	public String editStep()
	{
		if (stepNo == null)
		{
			stepModel = new StepModel();
			stepModel.setBaseSchema(baseSchema);
			stepModel.setDealOver("0");
			stepModel.setAcceptOver("0");
			stepModel.setHasSubFlow(0);
		}
		else
		{
			stepModel = (StepModel) hibernateDao.findUnique("from StepModel where stepNo=? and baseSchema=?", new String[] { stepNo, baseSchema });
		}
		return "developfix";
	}

	public String saveStep()
	{

		if (stepModel != null)
		{
			if (stepModel.getId() == null || "".equals(stepModel.getId()))
			{
				stepModel.setId(UUIDGenerator.getId());
				String menuPid = wfSortMenuFix.saveMenu_step(stepModel.getBaseSchema(), stepModel.getId(), stepModel.getStepNo());

				// 更新目录树
				List<String> roleIdList = roleManagerService.getRoleIdByUserID(this.getUserSession().getPid());
				roleManagerService.getRoleIdByUserID(this.getUserSession().getPid());
				List<String> menuIDList = new ArrayList<String>();
				menuIDList.add(menuPid);
				this.roleManagerService.addRoleMenu(roleIdList, menuIDList);
				stepModel.setOperate("add");
			}
			else
			{
				wfSortMenuFix.updateMenu_nodename(baseSchema, stepModel.getId(), stepModel.getStepNo());
				if ("1".equals(stepModel.getHasDeploy()))
				{
					stepModel.setOperate("update");
				}

				// 如果环节的标识被更改了，那么所有与环节关联的动作都要进行环节标识更改，想对应的字段也需要更改。
				StepModel stepModelOld = (StepModel) hibernateDao.findUnique("from StepModel where baseSchema=? and id=?", new String[] { stepModel.getBaseSchema(), stepModel.getId() });
				if (!stepModel.getStepNo().equals(stepModelOld.getStepNo()))
				{
					List<ActionModel> actionModelList = hibernateDao.find("from ActionModel where baseSchema=? and stepNo=? order by orderId", new String[] { stepModelOld.getBaseSchema(), stepModelOld.getStepNo() });
					for (ActionModel actionModel : actionModelList)
					{
						actionModel.setStepNo(stepModel.getStepNo());
						hibernateDao.saveOrUpdate(actionModel);
					}

					List<ActionFieldRelationModel> afrList = hibernateDao.find("from ActionFieldRelationModel where baseSchema=? and stepNo=?", new String[] { stepModelOld.getBaseSchema(), stepModelOld.getStepNo() });
					for (ActionFieldRelationModel afRelationModel : afrList)
					{
						afRelationModel.setStepNo(stepModel.getStepNo());
						hibernateDao.saveOrUpdate(afRelationModel);
					}
				}

			}
			stepModel.setHasDeploy(0);
			hibernateDao.saveOrUpdate(stepModel);
		}
		return "refresh";
	}

	/**
	 * 保存动作信息
	 * 
	 * @return
	 */
	public String saveAction()
	{

		if (actionModel != null)
		{
			if (actionModel.getId() == null || "".equals(actionModel.getId()))
			{
				actionModel.setId(UUIDGenerator.getId());
				actionModel.setOperate("add");

			}
			else
			{
				if ("1".equals(actionModel.getHasDeploy()))
				{
					actionModel.setOperate("update");
				}
			}

			// 与动作关联的字段中存储的是ActionName，所以如果动作名称修改时，需要同步修改动作字段关联表的ActionName字段
			ActionModel actionModelOld = (ActionModel) hibernateDao.findUnique("from ActionModel where baseSchema=? and id=?", new String[] { actionModel.getBaseSchema(), actionModel.getId() });
			if (actionModelOld != null)
			{
				if (!actionModelOld.getActionName().equals(actionModel.getActionName()))
				{
					List<ActionFieldRelationModel> afrList = hibernateDao.find("from ActionFieldRelationModel where baseSchema=? and stepNo=? and actionName=?", new String[] { actionModelOld.getBaseSchema(), actionModelOld.getStepNo(), actionModelOld.getActionName() });
					for (ActionFieldRelationModel afRelationModel : afrList)
					{
						afRelationModel.setActionName(actionModel.getActionName());
						hibernateDao.saveOrUpdate(afRelationModel);
					}
				}
			}

			actionModel.setHasDeploy(0);
			hibernateDao.saveOrUpdate(actionModel);
		}
		return "refresh";
	}

	public String configWorksheetField()
	{
		baseFieldList = templateService.mainFieldList(baseSchema);
		stepFieldRelationList = templateService.stepFieldList(baseSchema, stepNo);

		StringBuffer idStringBuffer = new StringBuffer();
		List<BaseField> tempList = new ArrayList<BaseField>();

		for (StepFieldRelationModel stepFieldRelationModel : stepFieldRelationList)
		{
			for (BaseField baseField : baseFieldList)
			{
				if (stepFieldRelationModel.getFieldID().equals(baseField.getId()) && stepFieldRelationModel.getFieldType().equals(baseField.getFieldType()))
				{
					stepFieldRelationModel.getBaseField().setId(stepFieldRelationModel.getBaseField().getId() + "#" + stepFieldRelationModel.getFieldType());
					stepFieldRelationModel.getBaseField().setLabel(stepFieldRelationModel.getBaseField().getLabel() + "(" + stepFieldRelationModel.getBaseField().getFieldName() + ")");
					tempList.add(baseField);

				}
			}
		}
		baseFieldList.removeAll(tempList);

		for (BaseField baseField : baseFieldList)
		{
			baseField.setId(baseField.getId() + "#" + baseField.getFieldType());
			baseField.setLabel(baseField.getLabel() + "(" + baseField.getFieldName() + ")");
		}
		return "developfix";
	}

	public String configActionField()
	{
		actionFieldList = templateService.actionFieldFreeList(baseSchema);
		actionFieldRelationList = templateService.actionFieldList(baseSchema, stepNo, actionName);

		StringBuffer idStringBuffer = new StringBuffer();
		List<BaseField> tempList = new ArrayList<BaseField>();

		for (ActionFieldRelationModel actionFieldRelationModel : actionFieldRelationList)
		{
			for (BaseField baseField : actionFieldList)
			{
				if (actionFieldRelationModel.getFieldID().equals(baseField.getId()) && actionFieldRelationModel.getFieldType().equals(baseField.getFieldType()))
				{
					actionFieldRelationModel.getBaseField().setId(actionFieldRelationModel.getBaseField().getId() + "#" + actionFieldRelationModel.getFieldType());
					actionFieldRelationModel.getBaseField().setLabel(actionFieldRelationModel.getBaseField().getLabel() + "(" + actionFieldRelationModel.getBaseField().getFieldName() + ")");
					tempList.add(baseField);

				}
			}
		}
		actionFieldList.removeAll(tempList);

		for (BaseField baseField : actionFieldList)
		{
			baseField.setId(baseField.getId() + "#" + baseField.getFieldType());
			baseField.setLabel(baseField.getLabel() + "(" + baseField.getFieldName() + ")");
		}
		return "developfix";
	}

	public String saveConfigActionField()
	{
		if (idString != null)
		{
			List<ActionFieldRelationModel> oldFieldList = hibernateDao.find("from ActionFieldRelationModel where baseSchema=? and stepNo=? and actionName=?", new String[] { baseSchema, stepNo, actionName });
			for (int i = 0; i < oldFieldList.size(); i++)
			{
				ActionFieldRelationModel actionFieldModel = oldFieldList.get(i);
				String tempIDFieldType = actionFieldModel.getFieldID() + "#" + actionFieldModel.getFieldType();
				if (idString.contains(tempIDFieldType))
				{
					int beginIndex = idString.indexOf(tempIDFieldType);
					int endIndex = idString.indexOf(tempIDFieldType) + tempIDFieldType.length();
					idString = idString.substring(0, beginIndex) + idString.substring(endIndex, idString.length());
				}
				else
				{
					hibernateDao.remove(actionFieldModel); 
				}
			}

			String[] newFieldID = idString.split(",");
			for (int i = 0; i < newFieldID.length; i++)
			{
				if (newFieldID[i].length() > 1)
				{
					String[] idAndFieldType = newFieldID[i].split("#");
					String fieldID = idAndFieldType[0];
					String fieldType = idAndFieldType[1];
					ActionFieldRelationModel tempModel = new ActionFieldRelationModel();
					tempModel.setId(UUIDGenerator.getId());
					tempModel.setBaseSchema(baseSchema);
					tempModel.setStepNo(stepNo);
					tempModel.setFieldID(fieldID);
					tempModel.setFieldType(fieldType);
					tempModel.setRequired(1);
					tempModel.setVisiable(1);
					tempModel.setActionName(actionName);

					hibernateDao.saveOrUpdate(tempModel);
				}
			}
		}
		return "refresh";
	}

	public String saveConfigStepField()
	{
		if (idString != null)
		{

			List<StepFieldRelationModel> oldFieldList = hibernateDao.find("from StepFieldRelationModel where baseSchema=? and stepNo=?", new String[] { baseSchema, stepNo });

			for (int i = 0; i < oldFieldList.size(); i++)
			{
				StepFieldRelationModel stepFieldModel = oldFieldList.get(i);
				String tempIDFieldType = stepFieldModel.getFieldID() + "#" + stepFieldModel.getFieldType();
				if (idString.contains(tempIDFieldType))
				{
					int beginIndex = idString.indexOf(tempIDFieldType);
					int endIndex = idString.indexOf(tempIDFieldType) + tempIDFieldType.length();

					idString = idString.substring(0, beginIndex) + idString.substring(endIndex, idString.length());
				}
				else
				{
					hibernateDao.remove(stepFieldModel);
				}
			}

			String[] newFieldID = idString.split(",");
			for (int i = 0; i < newFieldID.length; i++)
			{
				if (newFieldID[i].length() > 1)
				{
					String[] idAndFieldType = newFieldID[i].split("#");
					String fieldID = idAndFieldType[0];
					String fieldType = idAndFieldType[1];
					StepFieldRelationModel tempModel = new StepFieldRelationModel();
					tempModel.setId(UUIDGenerator.getId());
					tempModel.setBaseSchema(baseSchema);
					tempModel.setStepNo(stepNo);
					tempModel.setFieldID(fieldID);
					tempModel.setFieldType(fieldType);
					tempModel.setRequired(1);
					tempModel.setVisiable(1);
					hibernateDao.saveOrUpdate(tempModel);
				}
			}

		}
		return "refresh";
	}

	/**
	 * 异步保存环节编辑字段属性
	 * 
	 * @throws IOException
	 */
	public void saveStepField() throws IOException
	{

		String baseSchema = StringUtils.checkNullString(this.getRequest().getParameter("baseSchema"));
		String stepNo = StringUtils.checkNullString(this.getRequest().getParameter("stepNo"));
		String fieldID = StringUtils.checkNullString(this.getRequest().getParameter("fieldID"));
		String fieldType = StringUtils.checkNullString(this.getRequest().getParameter("fieldType"));
		String checkvalue = StringUtils.checkNullString(this.getRequest().getParameter("checkvalue"));
		int checkvalueTmp = Integer.valueOf(checkvalue);
		String checktype = StringUtils.checkNullString(this.getRequest().getParameter("checktype"));

		stepFieldRelationModel = (StepFieldRelationModel) hibernateDao.findUnique("from StepFieldRelationModel where baseSchema=? and stepNo=? and fieldID=?", new String[] { baseSchema, stepNo, fieldID });
 
		StepModel stepModel = (StepModel) hibernateDao.findUnique("from StepModel where baseSchema=? and stepNo=?", new String[] { baseSchema, stepNo });

		if ("visiable".equals(checktype))
		{
			stepFieldRelationModel.setVisiable(checkvalueTmp);
		}
		if ("required".equals(checktype))
		{
			stepFieldRelationModel.setRequired(checkvalueTmp);
		}

		hibernateDao.saveOrUpdate(stepFieldRelationModel);

		PrintWriter out = this.getResponse().getWriter();
		out.print(true);
	}

	/**
	 * 异步保存动作编辑字段属性
	 * 
	 * @throws IOException
	 */
	public void saveActionField() throws IOException
	{

		String baseSchema = StringUtils.checkNullString(this.getRequest().getParameter("baseSchema"));
		String stepNo = StringUtils.checkNullString(this.getRequest().getParameter("stepNo"));
		String actionName = StringUtils.checkNullString(this.getRequest().getParameter("actionName"));
		String fieldID = StringUtils.checkNullString(this.getRequest().getParameter("fieldID"));
		String fieldType = StringUtils.checkNullString(this.getRequest().getParameter("fieldType"));
		String checkvalue = StringUtils.checkNullString(this.getRequest().getParameter("checkvalue"));
		int checkvalueTmp = Integer.valueOf(checkvalue);
		String checktype = StringUtils.checkNullString(this.getRequest().getParameter("checktype"));

		actionFieldRelationModel = (ActionFieldRelationModel) hibernateDao.findUnique("from ActionFieldRelationModel where baseSchema=? and stepNo=? and actionName=? and fieldID=?", new String[] { baseSchema, stepNo,actionName, fieldID });

		ActionModel actionModel = (ActionModel) hibernateDao.findUnique("from ActionModel where baseSchema=? and stepNo=? and actionName=?", new String[] { baseSchema, stepNo, actionName });
		// 首先要判断当前组件的部署和部署前操作情况
		int hasDeploy = actionModel.getHasDeploy();
		if (hasDeploy == 1)
		{
			actionModel.setHasDeploy(0);
			actionModel.setOperate("update");
			hibernateDao.saveOrUpdate(actionModel);
		}

		if ("visiable".equals(checktype))
		{
			actionFieldRelationModel.setVisiable(checkvalueTmp);
		}
		if ("required".equals(checktype))
		{
			actionFieldRelationModel.setRequired(checkvalueTmp);
		}

		hibernateDao.saveOrUpdate(actionFieldRelationModel);

		PrintWriter out = this.getResponse().getWriter();
		out.print(true);
	}

	/**
	 * 删除字段组件 当主表单初次配置时，所有的添加，修改操作
	 * operate=‘add’，hasDeploy=‘0’，当（operate=‘add’&&hasDeploy=‘0’），完成删除操作，在后台数据中将真删除该条数据
	 * 当主表单完成部署后，添加字段如上，当进行修改是，hasDeploy=‘1’的情况下，设置operate=‘update’
	 * 并且设计hasDeploy=‘0’，此时进行删除操作，在后台完成设置operate=‘0’ and operate=‘delete’
	 * 以上删除逻辑适用范围是一般组件
	 * 当删除布局组件，容器或者容器组的时候，先删除容器下方组件，如果容器下方组件不能进行真删除操作，那么容器就假删除，并且它内部的所有字段都不显示在列表里面，只显示假删除的容器字段，该容器字段可以删除。
	 * 
	 * @return
	 */
	public String delWorksheetField()
	{
		List<TreeNode> nodeList = null;
		if (!delFieldType.toLowerCase().contains("panel"))
		{
			BaseField baseField = (BaseField) hibernateDao.findUnique("from " + delFieldType + " where baseSchema=? and id=?", new String[] { baseSchema, delFieldID });
			// 首先要判断当前组件的部署和部署前操作情况
			int hasDeploy = baseField.getHasDeploy();
			String operate = baseField.getOperate();
			if (hasDeploy == 0 && "add".equals(operate))
			{
				hibernateDao.remove(baseField);
			}
			else
			{
				baseField.setHasDeploy(0);
				baseField.setOperate("delete");
				hibernateDao.saveOrUpdate(baseField);
			}

		}
		else
		{

			if (delFieldType.equals("PanelField"))
			{
				BaseField baseField = (BaseField) hibernateDao.findUnique("from PanelField where baseSchema=? and id=?", new String[] { baseSchema, delFieldID });
				// 删除容器下得所有容器组与字段组件
				nodeList = templateService.getOtherChildList(delFieldID, baseSchema);

				if (nodeList.size() > 0)
				{
					List<BaseField> childFieldList = new ArrayList<BaseField>();
					childFieldList = this.treeNode2List(nodeList, childFieldList);
					boolean deleteFlag = true;
					for (BaseField childField : childFieldList)
					{
						// 自节点组件 已经完成部署，或者在部署后有修改或者删除，那么所以上级容器不允许删除操作
						if (childField.getHasDeploy() == 1 || "update".equals(childField.getOperate()) || "delete".equals(childField.getOperate()))
						{
							deleteFlag = false;
						}
					}
					if (!deleteFlag)
					{
						msg = "选中删除的容器组件内部存在已经部署完成的组件，不允许容器删除";
						return "getWorksheetList";
					}

					// 当逻辑运行到这步的时候，说明刚刚删除的容器或者容器组下方没有部署过的组件，所以列表中的所有对象都可以真删除
					for (BaseField childField : childFieldList)
					{
						int hasDeploy = childField.getHasDeploy();
						String operate = childField.getOperate();
						if (hasDeploy == 0 && "add".equals(operate))
						{
							hibernateDao.remove(childField);
						}

					}

				}

				int hasDeploy = baseField.getHasDeploy();
				String operate = baseField.getOperate();
				if (hasDeploy == 0 && "add".equals(operate))
				{
					hibernateDao.remove(baseField);
				}
				else
				{
					baseField.setHasDeploy(0);
					baseField.setOperate("delete");
					hibernateDao.saveOrUpdate(baseField);
				}

			}
			if (delFieldType.equals("PanelGroupField"))
			{
				// 删除容器组下面的所有容器，以及容器下面的所有容器组和字段组件。
				BaseField baseField = (BaseField) hibernateDao.findUnique("from PanelGroupField where baseSchema=? and id=?", new String[] { baseSchema, delFieldID });

				nodeList = templateService.getPanelChildList(delFieldID, baseSchema);
				if (nodeList.size() > 0)
				{
					List<BaseField> childFieldList = new ArrayList<BaseField>();
					childFieldList = this.treeNode2List(nodeList, childFieldList);
					boolean deleteFlag = true;
					for (BaseField childField : childFieldList)
					{
						// 自节点组件 已经完成部署，所以上级容器不允许删除操作
						if (childField.getHasDeploy() == 1 || "update".equals(childField.getOperate()) || "delete".equals(childField.getOperate()))
						{
							deleteFlag = false;
						}
					}
					if (!deleteFlag)
					{
						msg = "选中删除的容器组组件内部存在已经部署完成的组件，不允许容器删除";
						return "getWorksheetList";
					}

					for (BaseField childField : childFieldList)
					{
						int hasDeploy = childField.getHasDeploy();
						String operate = childField.getOperate();
						if (hasDeploy == 0 && "add".equals(operate))
						{
							hibernateDao.remove(childField);
						}

					}

				}
				if (!baseField.equals("PG_Main"))
				{
					int hasDeploy = baseField.getHasDeploy();
					String operate = baseField.getOperate();
					if (hasDeploy == 0 && "add".equals(operate))
					{
						hibernateDao.remove(baseField);
					}
					else
					{
						baseField.setHasDeploy(0);
						baseField.setOperate("delete");
						hibernateDao.saveOrUpdate(baseField);
					}
				}
			}

		}
		return this.findRedirectPar("deployMain.action?baseSchema=" + baseSchema);
	}

	public String rebackStep()
	{
		jdbcDao.doExecute("update bs_t_bpp_step s  set s.operate = 'update' where baseschema='" + baseSchema + "' and s.stepno='" + stepNo + "'");

		// return "getStepList";
		return this.findRedirectPar("stepList.action?baseSchema=" + baseSchema);

	}

	// shenxf 恢复实现
	public String rollBackField()
	{

		BaseField baseField = (BaseField) hibernateDao.findUnique("from " + delFieldType + " where baseSchema=? and id=?", new String[] { baseSchema, delFieldID });
		String operate = baseField.getOperate();
		baseField.setOperate("update");
		hibernateDao.saveOrUpdate(baseField);
		if (delvalue != null)
		{
			// return "getdeployMainFree";

			return this.findRedirectPar("deployMain.action?baseSchema=" + baseSchema + "&isFree=" + isFree);

		}
		else
		{
			// return "getWorksheetList";
			return this.findRedirectPar("deployMain.action?baseSchema=" + baseSchema);

		}

	}

	public String rebackAction()
	{
		ActionModel actionModel = (ActionModel) hibernateDao.findUnique("from ActionModel where  baseSchema=? and actionName=? and stepNo=?", new String[] { baseSchema, actionName, stepNo });
		actionModel.setOperate("update");
		hibernateDao.saveOrUpdate(actionModel);
		// return "getActionList";
		return this.findRedirectPar("actionList.action?baseSchema=" + baseSchema + "&stepNo=" + stepNo);

	}

	private List<BaseField> treeNode2List(List<TreeNode> nodeList, List<BaseField> childFieldList)
	{
		for (TreeNode node : nodeList)
		{
			childFieldList.add(node.getBaseField());
			if (node.getChiNodes() != null)
			{
				this.treeNode2List(node.getChiNodes(), childFieldList);
			}
		}
		return childFieldList;
	}

	public String delActionField()
	{
		ActionFieldRelationModel relationModel = (ActionFieldRelationModel) hibernateDao.findUnique("from ActionFieldRelationModel where baseSchema=? and stepNo=? and actionName=? and fieldID=? and fieldType=?", new String[] { baseSchema, stepNo, actionName, fieldID, fieldType });

		hibernateDao.remove(relationModel);

		ActionModel actionModel = (ActionModel) hibernateDao.findUnique("from ActionModel where actionName=? and stepNo=? and baseSchema=?", new String[] { actionName, stepNo, baseSchema });

		if (actionModel != null)
		{
			if (actionModel.getHasDeploy() == 1)
			{
				actionModel.setHasDeploy(0);
				actionModel.setOperate("update");
				hibernateDao.saveOrUpdate(actionModel);
			}
		}

		return this.findRedirectPar("deployAction.action?baseSchema=" + baseSchema + "&stepNo=" + stepNo + "&actionName=" + actionName);

	}

	public String rollBackActionField()
	{
		BaseField field = (BaseField) hibernateDao.findUnique("from " + fieldType + " where baseSchema=? and id=? ", new String[] { baseSchema, fieldID });
		ActionFieldRelationModel relationModel = (ActionFieldRelationModel) hibernateDao.findUnique("from ActionFieldRelationModel where baseSchema=? and stepNo=? and actionName=? and fieldID=? and fieldType=?", new String[] { baseSchema, stepNo, actionName, fieldID, fieldType });

		field.setHasDeploy(0);
		field.setOperate("update");
		hibernateDao.saveOrUpdate(field);

		actionN = actionName;

		// return "getActionFieldList";
		return this.findRedirectPar("deployAction.action?baseSchema=" + baseSchema + "&stepNo=" + stepNo + "&actionName=" + actionName);

	}

	public String delAction()
	{
		ActionModel actionModel = (ActionModel) hibernateDao.findUnique("from ActionModel where  baseSchema=? and actionName=? and stepNo=?", new String[] { baseSchema, actionName, stepNo });

		hibernateDao.remove(actionModel);

		actionFieldRelationList = templateService.actionFieldList(baseSchema, stepNo, actionName);
		for (ActionFieldRelationModel relationModel : actionFieldRelationList)
		{
			hibernateDao.remove(relationModel);
		}
		return this.findRedirectPar("actionList.action?baseSchema=" + baseSchema + "&stepNo=" + stepNo);

	}

	public String delStep()
	{
		// 删除环节的所有动作以及动作上的字段组件
		StepModel stepModel = (StepModel) hibernateDao.findUnique("from StepModel where baseSchema=? and stepNo=?", new String[] { baseSchema, stepNo });
		if (stepModel != null)
			hibernateDao.remove(stepModel);
		jdbcDao.doExecute("delete bs_t_bpp_stepfieldrel where baseschema='" + baseSchema + "' and stepno='" + stepNo + "'");

		// 删除环节下的所有动作 以及动作上的字段
		actionList = hibernateDao.find("from ActionModel where baseSchema=? and stepNo=? order by orderId", new String[] { baseSchema, stepNo });
		for (ActionModel actionModel : actionList)
		{
			actionFieldRelationList = templateService.actionFieldList(actionModel.getBaseSchema(), actionModel.getStepNo(), actionModel.getActionName());
			for (ActionFieldRelationModel relationModel : actionFieldRelationList)
			{
				hibernateDao.remove(relationModel);
			}
			hibernateDao.remove(actionModel);
		}

		return this.findRedirectPar("stepList.action?baseSchema=" + baseSchema);

	}

	public String delStepField()
	{
		jdbcDao.doExecute("delete bs_t_bpp_stepfieldrel where baseschema='" + baseSchema + "' and stepno='" + stepNo + "'");

		return this.findRedirectPar("stepFieldList.action?baseSchema=" + baseSchema + "&stepNo=" + stepNo);

	}

	public String updateStepField()
	{

		// hibernateDao.doExecute("delete bs_t_bpp_stepfieldrel where
		// baseschema='" +
		// baseSchema + "' and fieldtype='" + fieldType + "' and stepno='" +
		// stepNo + "' and fieldid='" + fieldID + "'");
		return "refresh";
	}

	public String saveField()
	{

		if (fieldType == null || fieldType.length() == 0)
		{
			msg = "字段类型没有正确传参！";
			return "refresh";
		}

		try
		{
			baseField = (BaseField) this.getClass().getMethod("get" + fieldType).invoke(this);
			baseField.setFieldType(fieldType);
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
		}

		if (baseField != null)
		{

			if (baseField.getId() == null || "".equals(baseField.getId()))
			{
				baseField.setId(UUIDGenerator.getId());
				baseField.setOperate("add");
				baseField.setHasDeploy(0);

			}
			else
			{
				if (baseField.getHasDeploy() == 1 || "update".equals(baseField.getOperate()))
				{
					baseField.setHasDeploy(0);
					baseField.setOperate("update");
				}
			}

			baseField.setFieldName(baseField.getFieldName().trim());
			baseField.setLabel(baseField.getLabel().trim());

			hibernateDao.saveOrUpdate(baseField);
		}

		return "refresh";
	}

	/***************************************************************************
	 * 验证主表单字段英文名称唯一
	 * 
	 * @throws IOException
	 */
	public void worksheetFieldNameCheckUnique() throws IOException
	{
		String baseSchema = StringUtils.checkNullString(this.getRequest().getParameter("baseSchema"));
		String fieldName = StringUtils.checkNullString(this.getRequest().getParameter("fieldName"));
		String fieldID = StringUtils.checkNullString(this.getRequest().getParameter("fieldID"));
		boolean result = true;

		// 获取主表单所有字段信息
		baseFieldList = templateService.mainFieldList(baseSchema);
		for (BaseField field : baseFieldList)
		{
			if (!field.getId().equals(fieldID) && field.getBaseSchema().equals(baseSchema) && field.getFieldName().equals(fieldName))
			{
				// 判断当前主表单的字段是否有
				result = false;
			}
		}
		// 获取动作所有字段信息
		actionFieldFreeList = templateService.actionFieldFreeList(baseSchema);
		// 判断当前动作字段是否有
		for (BaseField actionfield : actionFieldFreeList)
		{
			if (!actionfield.getId().equals(fieldID) && actionfield.getBaseSchema().equals(baseSchema) && actionfield.getFieldName().equals(fieldName))
			{
				// 判断当前动作的字段是否有
				result = false;
			}
		}
		PrintWriter out = this.getResponse().getWriter();
		out.print(result);
	}

	/***************************************************************************
	 * 验证环节标识唯一
	 */
	public void stepNoCheckUnique() throws IOException
	{
		String baseSchema = StringUtils.checkNullString(this.getRequest().getParameter("baseSchema"));
		String stepNo = StringUtils.checkNullString(this.getRequest().getParameter("stepNo"));
		String stepID = StringUtils.checkNullString(this.getRequest().getParameter("stepID"));

		boolean result = true;

		List<StepModel> stepList = hibernateDao.find("from StepModel where baseSchema=? and stepNo=?", new String[] { baseSchema, stepNo });

		if (stepList != null)
		{
			for (StepModel step : stepList)
			{
				if (!step.getId().equals(stepID))
				{
					result = false;
				}
			}
		}
		PrintWriter out = this.getResponse().getWriter();
		out.print(result);
	}

	/**
	 * 验证动作名称唯一
	 */
	public void actionNameCheckUnique() throws IOException
	{
		String baseSchema = StringUtils.checkNullString(this.getRequest().getParameter("baseSchema"));
		String actionName = StringUtils.checkNullString(this.getRequest().getParameter("actionName"));
		String actionID = StringUtils.checkNullString(this.getRequest().getParameter("actionID"));

		boolean result = true;

		List<ActionModel> list = hibernateDao.find("from ActionModel where baseSchema=? and actionName=? order by orderId", new String[] { baseSchema, actionName });
		if (list != null)
		{
			for (ActionModel actionModel : list)
			{
				if (!actionID.equals(actionModel.getId()))
				{
					result = false;
				}
			}
		}

		PrintWriter out = this.getResponse().getWriter();
		out.print(result);
	}

	/**
	 * 验证动作字段英文名称唯一
	 */
	public void actionFieldNameCheckUnique() throws IOException
	{

		String baseSchema = StringUtils.checkNullString(this.getRequest().getParameter("baseSchema"));
		String actionName = StringUtils.checkNullString(this.getRequest().getParameter("actionName"));
		String stepNo = StringUtils.checkNullString(this.getRequest().getParameter("stepNo"));
		String fieldName = StringUtils.checkNullString(this.getRequest().getParameter("fieldName"));
		String fieldID = StringUtils.checkNullString(this.getRequest().getParameter("fieldID"));

		actionFieldRelationList = templateService.actionFieldList(baseSchema, stepNo, actionName);

		boolean result = true;

		for (ActionFieldRelationModel field : actionFieldRelationList)
		{
			// 判断当前主表单的字段是否有
			if (!field.getId().equals(fieldID) && field.getBaseField().getFieldName().equals(fieldName))
			{
				result = false;
			}
		}

		PrintWriter out = this.getResponse().getWriter();
		out.print(result);

	}

	public String editField()
	{
		try
		{
			Map<String, FieldConfig> map = (Map<String, FieldConfig>) BaseCacheManager.get(FIELDINFO_CACHEKEY, FIELDINFO_CACHEKEY);
			FieldConfig fieldConfig = map.get(fieldType);
			Method setFieldMethod = this.getClass().getMethod("set" + fieldType, new Class<?>[] { Class.forName(fieldConfig.getFullName()) });

			if (fieldID == null || "".equals(fieldID))
			{
				baseField = (BaseField) Class.forName(fieldConfig.getFullName()).newInstance();
				baseField.setBaseSchema(baseSchema);
				baseField.setParentID(actionField);
				baseField.setHasDeploy(0);
				baseField.setOperate("add");
			}
			else
			{
				baseField = (BaseField) hibernateDao.findUnique("from " + fieldType + " where id=? and baseSchema=?", new String[] { fieldID, baseSchema });
			}
			if (baseField != null)
			{
				setFieldMethod.invoke(this, baseField);
			}

		}
		catch (Exception exception)
		{
			exception.printStackTrace();
		}
		if ("PanelField".equals(fieldType))
		{
			panelGroupFieldList = hibernateDao.find("from PanelGroupField where baseSchema=?", new String[] { baseSchema });
		}
		else
		{
			panelFieldList = hibernateDao.find("from PanelField where baseSchema=?", new String[] { baseSchema });
		}
		return this.findForward(fieldType.toLowerCase());
	}

	public String assigntreeConfigList()
	{

		configList = hibernateDao.find("from AssignTreeConfigModel where baseSchema=? order by stepNo,actionName,fieldName ", new String[] { baseSchema });
		return "developfix";
	}

	public void getActionListByStepNo() throws IOException
	{
		String baseSchema = StringUtils.checkNullString(this.getRequest().getParameter("baseSchema"));
		String stepNo = StringUtils.checkNullString(this.getRequest().getParameter("stepNo"));
		StringBuffer result = new StringBuffer();
		result.append("<select id='actionList' name='actionList' onchange='selectAction()'><option value='0'>请选择动作</option>");
		List<ActionModel> actionModelList = hibernateDao.find("from ActionModel where baseSchema=? and stepNo=? order by stepNo,orderId", new String[] { baseSchema, stepNo });
		for (ActionModel model : actionModelList)
		{
			result.append("<option value='" + model.getActionName() + "'>" + model.getLabel() + "</option>");
		}
		result.append("</select>");
		PrintWriter out = this.getResponse().getWriter();
		out.print(result.toString());
	}

	public void getFieldListByActionName() throws IOException
	{
		String baseSchema = StringUtils.checkNullString(this.getRequest().getParameter("baseSchema"));
		String stepNo = StringUtils.checkNullString(this.getRequest().getParameter("stepNo"));
		String actionName = StringUtils.checkNullString(this.getRequest().getParameter("actionName"));
		List<ActionFieldRelationModel> fieldList = hibernateDao.find("from ActionFieldRelationModel where baseSchema=? and stepNo=? and actionName=? and fieldType='AssignTreeField' ", new String[] { baseSchema, stepNo, actionName });
		StringBuffer hql = new StringBuffer();
		for (ActionFieldRelationModel rel : fieldList)
		{
			if (hql.length() == 0)
			{
				hql.append("from AssignTreeField where id='" + rel.getFieldID() + "' ");
			}
			else
			{
				hql.append(" or id='" + rel.getFieldID() + "' ");
			}
		}
		List<AssignTreeField> assigntreeList = null;
		if (hql.length() > 0)
		{
			assigntreeList = hibernateDao.find(hql.toString());
		}
		if (assigntreeList == null)
			assigntreeList = new ArrayList<AssignTreeField>();
		StringBuffer result = new StringBuffer();
		result.append("<select id='fieldList' name='fieldList' ><option value='0'>请选择派发树</option>");
		for (AssignTreeField field : assigntreeList)
		{
			result.append("<option value='" + field.getFieldName() + "'>" + field.getLabel() + "</option>");
		}
		result.append("</select>");
		PrintWriter out = this.getResponse().getWriter();
		out.print(result.toString());
	}

	public String configTreeEdit()
	{
		if (configID == null || configID.length() == 0)
		{
			stepList = hibernateDao.find("from StepModel where baseSchema=? order by stepNo", baseSchema);
			configOptions = "<select id='stepList' name='stepList' onchange='selectStep()'><option value='0'>请选择环节</option>";
			for (StepModel step : stepList)
			{
				configOptions += "<option value='" + step.getStepNo() + "'>" + step.getDescription() + "</option>";
			}
			configOptions += "</select>";
		}else{
			AssignTreeConfigModel model = (AssignTreeConfigModel) hibernateDao.findUnique("from AssignTreeConfigModel where id=?",configID);
			this.baseSchema = model.getBaseSchema();
			this.stepNo = model.getStepNo(); 
			this.stepDesc = model.getStepDesc();
			this.actionName = model.getActionName();
			this.actionLabel = model.getActionLabel();
			this.fieldName = model.getFieldName();
			this.fieldLabel = model.getFieldLabel();
			List<AssignTreeOrganizeModel> orginizeList = hibernateDao.find("from AssignTreeOrganizeModel where configid=? ", model.getId());
			StringBuffer organizeIDs = new StringBuffer();
			for(AssignTreeOrganizeModel organizeModel:orginizeList){
				if(organizeIDs.length()==0){
					organizeIDs.append(organizeModel.getOrganizeid());
				}else{
					organizeIDs.append(",").append(organizeModel.getOrganizeid());
				}
			}
			this.organizeInfo = organizeIDs.toString();
		}
		return "developfix";
	}

	public String saveAssignTreeConfig()
	{
		log.info("保存派发树配置信息。");
		configList = hibernateDao.find("from AssignTreeConfigModel where baseSchema=? and stepNo=? and actionName=? and fieldName=? order by stepNo,actionName,fieldName ", new String[] { baseSchema, stepNo, actionName, fieldName });
		if (configList != null && configList.size() > 0)
		{
			AssignTreeConfigModel model = configList.get(0);
			String configID = model.getId();
			List<AssignTreeOrganizeModel> orginizeList = hibernateDao.find("from AssignTreeOrganizeModel where configid=? ", configID);
			hibernateDao.remove(model);
			for (AssignTreeOrganizeModel organize : orginizeList)
			{
				hibernateDao.remove(organize);
			}
			//更新缓存
		}
		if (organizeInfo != null && organizeInfo.length() > 0)
		{
			String configID = UUIDGenerator.getId();
			AssignTreeConfigModel configModel = new AssignTreeConfigModel();
			configModel.setId(configID);
			configModel.setStepNo(stepNo);
			configModel.setStepDesc(stepDesc);
			configModel.setActionName(actionName);
			configModel.setActionLabel(actionLabel);
			configModel.setFieldLabel(fieldLabel);
			configModel.setFieldName(fieldName);
			configModel.setBaseSchema(baseSchema);
			hibernateDao.save(configModel);

			String[] organizeInfoArray = organizeInfo.split(";");
			for (String organizeInfo : organizeInfoArray)
			{
				String[] info = organizeInfo.split(":");
				String orgType = info[0];
				String orgID = info[1];
				String parentOrgID = info[2];
				AssignTreeOrganizeModel organizeModel = new AssignTreeOrganizeModel();
				organizeModel.setId(UUIDGenerator.getId());
				organizeModel.setConfigid(configID);
				organizeModel.setOrganizetype(orgType.equals("U") ? 2 : 1);
				organizeModel.setOrganizeid(orgID);
				organizeModel.setParentorgid(parentOrgID);
				hibernateDao.save(organizeModel);
			}
			//更新缓存  
		}

		return "refresh";
	}
	
	public String delConfigTree()
	{
		log.info("删除派发树配置信息。");
		AssignTreeConfigModel configModel = (AssignTreeConfigModel) hibernateDao.findUnique("from AssignTreeConfigModel where id=? ", new String[] {configID});
		if (configModel != null)
		{
			List<AssignTreeOrganizeModel> orginizeList = hibernateDao.find("from AssignTreeOrganizeModel where configid=? ", configID);
			for (AssignTreeOrganizeModel organize : orginizeList)
			{
				hibernateDao.remove(organize); 
			}
			hibernateDao.remove(configModel);
			//更新缓存
		}
		return this.findRedirectPar("assigntreeConfigList.action?baseSchema=" + baseSchema);
	}
	

	public TemplateService getTemplateService()
	{
		return templateService;
	}

	public void setTemplateService(TemplateService templateService)
	{
		this.templateService = templateService;
	}

	public String getBaseSchema()
	{
		return baseSchema;
	}

	public void setBaseSchema(String baseSchema)
	{
		this.baseSchema = baseSchema;
	}

	public String getFieldType()
	{
		return fieldType;
	}

	public void setFieldType(String fieldType)
	{
		this.fieldType = fieldType;
	}

	public IDao<WfType> getTypeDao()
	{
		return typeDao;
	}

	public void setTypeDao(IDao<WfType> typeDao)
	{
		this.typeDao = typeDao;
	}

	public List<StepModel> getStepList()
	{
		return stepList;
	}

	public void setStepList(List<StepModel> stepList)
	{
		this.stepList = stepList;
	}

	public String getFieldID()
	{
		return fieldID;
	}

	public void setFieldID(String fieldID)
	{
		this.fieldID = fieldID;
	}

	public SelectField getSelectField()
	{
		return selectField;
	}

	public void setSelectField(SelectField selectField)
	{
		this.selectField = selectField;
	}

	public CollectField getCollectField()
	{
		return collectField;
	}

	public void setCollectField(CollectField collectField)
	{
		this.collectField = collectField;
	}

	public CharacterField getCharacterField()
	{
		return characterField;
	}

	public void setCharacterField(CharacterField characterField)
	{
		this.characterField = characterField;
	}

	public List<BaseField> getBaseFieldList()
	{
		return baseFieldList;
	}

	public void setBaseFieldList(List<BaseField> baseFieldList)
	{
		this.baseFieldList = baseFieldList;
	}

	public IDao getHibernateDao()
	{
		return hibernateDao;
	}

	public void setHibernateDao(IDao hibernateDao)
	{
		this.hibernateDao = hibernateDao;
	}

	public StepModel getStepModel()
	{
		return stepModel;
	}

	public void setStepModel(StepModel stepModel)
	{
		this.stepModel = stepModel;
	}

	public String getStepNo()
	{
		return stepNo;
	}

	public void setStepNo(String stepNo)
	{
		this.stepNo = stepNo;
	}

	public List<StepFieldRelationModel> getStepFieldRelationList()
	{
		return stepFieldRelationList;
	}

	public void setStepFieldRelationList(List<StepFieldRelationModel> stepFieldRelationList)
	{
		this.stepFieldRelationList = stepFieldRelationList;
	}

	public List<ActionModel> getActionList()
	{
		return actionList;
	}

	public void setActionList(List<ActionModel> actionList)
	{
		this.actionList = actionList;
	}

	public TimeField getTimeField()
	{
		return timeField;
	}

	public void setTimeField(TimeField timeField)
	{
		this.timeField = timeField;
	}

	public PanelField getPanelField()
	{
		return panelField;
	}

	public void setPanelField(PanelField panelField)
	{
		this.panelField = panelField;
	}

	public AssignTreeField getAssignTreeField()
	{
		return assignTreeField;
	}

	public void setAssignTreeField(AssignTreeField assignTreeField)
	{
		this.assignTreeField = assignTreeField;
	}

	public BlankField getBlankField()
	{
		return blankField;
	}

	public void setBlankField(BlankField blankField)
	{
		this.blankField = blankField;
	}

	public ButtonField getButtonField()
	{
		return buttonField;
	}

	public void setButtonField(ButtonField buttonField)
	{
		this.buttonField = buttonField;
	}

	public LabelField getLabelField()
	{
		return labelField;
	}

	public void setLabelField(LabelField labelField)
	{
		this.labelField = labelField;
	}

	public PanelGroupField getPanelGroupField()
	{
		return panelGroupField;
	}

	public void setPanelGroupField(PanelGroupField panelGroupField)
	{
		this.panelGroupField = panelGroupField;
	}

	public BaseField getBaseField()
	{
		return baseField;
	}

	public void setBaseField(BaseField baseField)
	{
		this.baseField = baseField;
	}

	public String getActionID()
	{
		return actionID;
	}

	public void setActionID(String actionID)
	{
		this.actionID = actionID;
	}

	public ActionModel getActionModel()
	{
		return actionModel;
	}

	public void setActionModel(ActionModel actionModel)
	{
		this.actionModel = actionModel;
	}

	public ViewField getViewField()
	{
		return viewField;
	}

	public void setViewField(ViewField viewField)
	{
		this.viewField = viewField;
	}

	public List<ActionFieldRelationModel> getActionFieldRelationList()
	{
		return actionFieldRelationList;
	}

	public void setActionFieldRelationList(List<ActionFieldRelationModel> actionFieldRelationList)
	{
		this.actionFieldRelationList = actionFieldRelationList;
	}

	public String getActionName()
	{
		return actionName;
	}

	public void setActionName(String actionName)
	{
		this.actionName = actionName;
	}

	public List<PanelField> getPanelFieldList()
	{
		return panelFieldList;
	}

	public void setPanelFieldList(List<PanelField> panelFieldList)
	{
		this.panelFieldList = panelFieldList;
	}

	public List<PanelGroupField> getPanelGroupFieldList()
	{
		return panelGroupFieldList;
	}

	public void setPanelGroupFieldList(List<PanelGroupField> panelGroupFieldList)
	{
		this.panelGroupFieldList = panelGroupFieldList;
	}

	public String getIdString()
	{
		return idString;
	}

	public void setIdString(String idString)
	{
		this.idString = idString;
	}

	public IDao getJdbcDao()
	{
		return jdbcDao;
	}

	public void setJdbcDao(IDao jdbcDao)
	{
		this.jdbcDao = jdbcDao;
	}

	public String getActionField()
	{
		return actionField;
	}

	public void setActionField(String actionField)
	{
		this.actionField = actionField;
	}

	public String getDelFieldID()
	{
		return delFieldID;
	}

	public void setDelFieldID(String delFieldID)
	{
		this.delFieldID = delFieldID;
	}

	public String getDelFieldType()
	{
		return delFieldType;
	}

	public void setDelFieldType(String delFieldType)
	{
		this.delFieldType = delFieldType;
	}

	public AttachmentField getAttachmentField()
	{
		return attachmentField;
	}

	public void setAttachmentField(AttachmentField attachmentField)
	{
		this.attachmentField = attachmentField;
	}

	public RollbackListField getRollbackListField()
	{
		return rollbackListField;
	}

	public void setRollbackListField(RollbackListField rollbackListField)
	{
		this.rollbackListField = rollbackListField;
	}

	public RoleManagerService getRoleManagerService()
	{
		return roleManagerService;
	}

	public void setRoleManagerService(RoleManagerService roleManagerService)
	{
		this.roleManagerService = roleManagerService;
	}

	public WfSortMenuService getWfSortMenuFree()
	{
		return wfSortMenuFree;
	}

	public void setWfSortMenuFree(WfSortMenuService wfSortMenuFree)
	{
		this.wfSortMenuFree = wfSortMenuFree;
	}

	public WfSortMenuService getWfSortMenuFix()
	{
		return wfSortMenuFix;
	}

	public void setWfSortMenuFix(WfSortMenuService wfSortMenuFix)
	{
		this.wfSortMenuFix = wfSortMenuFix;
	}

	public String getIsFree()
	{
		return isFree;
	}

	public void setIsFree(String isFree)
	{
		this.isFree = isFree;
	}

	public List<BaseField> getActionFieldFreeList()
	{
		return actionFieldFreeList;
	}

	public void setActionFieldFreeList(List<BaseField> actionFieldFreeList)
	{
		this.actionFieldFreeList = actionFieldFreeList;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public DeployService getDeployService()
	{
		return deployService;
	}

	public void setDeployService(DeployService deployService)
	{
		this.deployService = deployService;
	}

	public boolean isForce()
	{
		return isForce;
	}

	public void setForce(boolean isForce)
	{
		this.isForce = isForce;
	}

	public List<FreeActionFieldRelationModel> getFreeActionfieldrelList()
	{
		return freeActionfieldrelList;
	}

	public void setFreeActionfieldrelList(List<FreeActionFieldRelationModel> freeActionfieldrelList)
	{
		this.freeActionfieldrelList = freeActionfieldrelList;
	}

	public String getActionN()
	{
		return actionN;
	}

	public void setActionN(String actionN)
	{
		this.actionN = actionN;
	}

	public FreeActionFieldRelationModel getFreeActionfieldrel()
	{
		return freeActionfieldrel;
	}

	public void setFreeActionfieldrel(FreeActionFieldRelationModel freeActionfieldrel)
	{
		this.freeActionfieldrel = freeActionfieldrel;
	}

	public String getAction()
	{
		return action;
	}

	public void setAction(String action)
	{
		this.action = action;
	}

	public List<ActionModel> getFreeActionList()
	{
		return freeActionList;
	}

	public void setFreeActionList(List<ActionModel> freeActionList)
	{
		this.freeActionList = freeActionList;
	}

	public String getDelvalue()
	{
		return delvalue;
	}

	public void setDelvalue(String delvalue)
	{
		this.delvalue = delvalue;
	}

	public String getFlag()
	{
		return flag;
	}

	public void setFlag(String flag)
	{
		this.flag = flag;
	}

	public List<BaseField> getActionFieldList()
	{
		return actionFieldList;
	}

	public void setActionFieldList(List<BaseField> actionFieldList)
	{
		this.actionFieldList = actionFieldList;
	}

	public StepFieldRelationModel getStepFieldRelationModel()
	{
		return stepFieldRelationModel;
	}

	public void setStepFieldRelationModel(StepFieldRelationModel stepFieldRelationModel)
	{
		this.stepFieldRelationModel = stepFieldRelationModel;
	}

	public ActionFieldRelationModel getActionFieldRelationModel()
	{
		return actionFieldRelationModel;
	}

	public void setActionFieldRelationModel(ActionFieldRelationModel actionFieldRelationModel)
	{
		this.actionFieldRelationModel = actionFieldRelationModel;
	}

	public List<Object[]> getStatusAndFreeActionList()
	{
		return statusAndFreeActionList;
	}

	public void setStatusAndFreeActionList(List<Object[]> statusAndFreeActionList)
	{
		this.statusAndFreeActionList = statusAndFreeActionList;
	}

	public List<FieldConfig> getFieldConfigList()
	{
		return fieldConfigList;
	}

	public void setFieldConfigList(List<FieldConfig> fieldConfigList)
	{
		this.fieldConfigList = fieldConfigList;
	}

	public HiddenField getHiddenField()
	{
		return hiddenField;
	}

	public void setHiddenField(HiddenField hiddenField)
	{
		this.hiddenField = hiddenField;
	}

	public String getOptionsString()
	{
		return optionsString;
	}

	public void setOptionsString(String optionsString)
	{
		this.optionsString = optionsString;
	}

	public String getOptionsStringOther()
	{
		return optionsStringOther;
	}

	public void setOptionsStringOther(String optionsStringOther)
	{
		this.optionsStringOther = optionsStringOther;
	}

	public List<AssignTreeConfigModel> getConfigList()
	{
		return configList;
	}

	public void setConfigList(List<AssignTreeConfigModel> configList)
	{
		this.configList = configList;
	}

	public Map<String, String> getConfigMap()
	{
		return configMap;
	}

	public void setConfigMap(Map<String, String> configMap)
	{
		this.configMap = configMap;
	}

	public String getConfigOptions()
	{
		return configOptions;
	}

	public void setConfigOptions(String configOptions)
	{
		this.configOptions = configOptions;
	}

	public String getStepDesc()
	{
		return stepDesc;
	}

	public void setStepDesc(String stepDesc)
	{
		this.stepDesc = stepDesc;
	}

	public String getActionLabel()
	{
		return actionLabel;
	}

	public void setActionLabel(String actionLabel)
	{
		this.actionLabel = actionLabel;
	}

	public String getFieldName()
	{
		return fieldName;
	}

	public void setFieldName(String fieldName)
	{
		this.fieldName = fieldName;
	}

	public String getFieldLabel()
	{
		return fieldLabel;
	}

	public void setFieldLabel(String fieldLabel)
	{
		this.fieldLabel = fieldLabel;
	}

	public String getOrganizeInfo()
	{
		return organizeInfo;
	}

	public void setOrganizeInfo(String organizeInfo)
	{
		this.organizeInfo = organizeInfo;
	}

	public String getConfigID()
	{
		return configID;
	}

	public void setConfigID(String configID)
	{
		this.configID = configID;
	}

	public String getInterfaceXMLName() {
		return interfaceXMLName;
	}

	public void setInterfaceXMLName(String interfaceXMLName) {
		this.interfaceXMLName = interfaceXMLName;
	}

}
