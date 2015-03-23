package com.ultrapower.eoms.ultrabpp.cache.manager;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.sf.json.JSONArray;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ultrapower.eoms.common.core.component.cache.manager.BaseCacheManager;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.ultrabpp.cache.model.ActionModel;

import com.ultrapower.eoms.ultrabpp.cache.model.ActionFieldRelationModel;
import com.ultrapower.eoms.ultrabpp.cache.model.ActionMeta;
import com.ultrapower.eoms.ultrabpp.cache.model.AttributeConfig;
import com.ultrapower.eoms.ultrabpp.cache.model.EditableFieldModel;
import com.ultrapower.eoms.ultrabpp.cache.model.FieldConfig;
import com.ultrapower.eoms.ultrabpp.cache.model.FreeActionFieldRelationModel;
import com.ultrapower.eoms.ultrabpp.cache.model.LayOutJsonBean;
import com.ultrapower.eoms.ultrabpp.cache.model.StepFieldRelationModel;
import com.ultrapower.eoms.ultrabpp.cache.model.StepMeta;
import com.ultrapower.eoms.ultrabpp.cache.model.StepModel;
import com.ultrapower.eoms.ultrabpp.cache.model.ThemeModel;
import com.ultrapower.eoms.ultrabpp.cache.model.WorksheetMeta;
import com.ultrapower.eoms.ultrabpp.cache.service.MetaCacheService;
import com.ultrapower.eoms.ultrabpp.model.BaseField;
import com.ultrapower.eoms.ultrabpp.model.component.compositefield.panel.PanelField;
import com.ultrapower.eoms.ultrabpp.model.component.compositefield.panelgroup.PanelGroupField;
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
import com.ultrapower.workflow.configuration.sort.model.WfType;
import com.ultrapower.workflow.exception.WorkflowException;
import com.ultrapower.workflow.utils.WfEngineUtils;

/**
 * 缓存操作类，配置数据从数据库获取的实现类。
 * 
 * @author BigMouse
 */
public class MetaCacheDBImpl implements MetaCacheService
{

	private IDao hibernateDao;

	private Map<String, WorksheetMeta> metaMap;

	private List<WfType> wfTypes;

	private List<CharacterField> characterFields;
	private List<SelectField> selectFields;
	private List<CollectField> collectFields;
	private List<TimeField> timeFields;
	private List<HiddenField> hiddenFields;

	private List<AssignTreeField> assignTreeFields;
	private List<AttachmentField> attachmentFields;
	private List<RollbackListField> rollbackListFields;

	private List<PanelField> panelFields;
	private List<PanelGroupField> panelGroupFields;

	private List<BlankField> blankFields;
	private List<ButtonField> buttonFields;
	private List<LabelField> labelFields;
	private List<ViewField> viewFields;

	private List<StepModel> steps;
	private List<ActionModel> actions;
	private List<StepFieldRelationModel> stepFieldRelations;
	private List<ActionFieldRelationModel> actionFieldRelations;
	private List<FreeActionFieldRelationModel> freeActionFieldRelations;

	private Map<String, Map<String, Map<String, BaseField>>> fieldMap;

	private final String WORKSHEET_CACHEKEY = "bppWorksheetMap";
	private final String FREEACTION_CACHEKEY = "bppFreeActionMap";
	private final String FIELDINFO_CACHEKEY = "bppFieldInfoMap";

	private Logger log = LoggerFactory.getLogger(MetaCacheDBImpl.class);

	public void reflushCache(String baseSchema)
	{
		log.info("刷新表单字段缓存 baseSchema："+baseSchema);
		this.loadFieldData(baseSchema);
		fieldMap = new HashMap<String, Map<String, Map<String, BaseField>>>();
		metaMap = (Map<String, WorksheetMeta>) BaseCacheManager.get(WORKSHEET_CACHEKEY, WORKSHEET_CACHEKEY);
		if (metaMap == null)
		{
			metaMap = new HashMap<String, WorksheetMeta>();
			BaseCacheManager.put(WORKSHEET_CACHEKEY, WORKSHEET_CACHEKEY, metaMap);
		}

		for (WfType worksheet : wfTypes)
		{

			fieldMap.put(worksheet.getCode(), this.getBaseSchemaFieldMap(worksheet.getCode()));

			WorksheetMeta worksheetMeta = new WorksheetMeta();
			worksheetMeta.setWorksheetModel(worksheet);
			worksheetMeta.setFields(this.getFieldList(worksheet.getCode()));
			worksheetMeta.setActionFields(this.getActionFieldList(worksheet.getCode()));
			worksheetMeta.setSteps(this.getStepMetaList(worksheet.getCode()));
			worksheetMeta.setFreeActionFields(this.getFreeActionFieldList(worksheet.getCode()));
			worksheetMeta.setLayoutJSON(getLayOutJSON(worksheet.getCode())); 

			metaMap.put(worksheet.getCode(), worksheetMeta);
		}

	}

	public Map<String, BaseField> getFieldsByBaseSchema(String baseSchema)
	{
		metaMap = (Map<String, WorksheetMeta>) BaseCacheManager.get(WORKSHEET_CACHEKEY, WORKSHEET_CACHEKEY);

		WorksheetMeta worksheetMeta = metaMap.get(baseSchema);

		return worksheetMeta.getFields();
	}
	

	public Map<String, BaseField> getAllFieldsByBaseSchema(String baseSchema)
	{
		metaMap = (Map<String, WorksheetMeta>) BaseCacheManager.get(WORKSHEET_CACHEKEY, WORKSHEET_CACHEKEY);

		WorksheetMeta worksheetMeta = metaMap.get(baseSchema);

		Map<String,BaseField> allFields = new HashMap<String,BaseField>();
		
		allFields.putAll(worksheetMeta.getFields());
		allFields.putAll(worksheetMeta.getActionFields());
		
		return allFields;
	}

	public Map<String, EditableFieldModel> getStepFields(String baseSchema, String stepNo)
	{
		Map<String, EditableFieldModel> stepFieldMap = new HashMap<String, EditableFieldModel>();

		metaMap = (Map<String, WorksheetMeta>) BaseCacheManager.get(WORKSHEET_CACHEKEY, WORKSHEET_CACHEKEY);

		WorksheetMeta worksheetMeta = metaMap.get(baseSchema);

		List<StepMeta> steps = worksheetMeta.getSteps();
		for (int i = 0; i < steps.size(); i++)
		{
			StepMeta stepMeta = steps.get(i);
			if (stepNo.equals(stepMeta.getStepModel().getStepNo()))
			{
				List<StepFieldRelationModel> stepFieldList = stepMeta.getFields();
				for (StepFieldRelationModel stepField : stepFieldList)
				{
					stepFieldMap.put(stepField.getBaseField().getFieldName(), stepField);
				}
				break;
			}
		}
		return stepFieldMap;
	}

	public Map<String, EditableFieldModel> getActionFields(String baseSchema, String stepNo, String actionName)
	{
		Map<String, EditableFieldModel> actionFieldMap = new HashMap<String, EditableFieldModel>();

		metaMap = (Map<String, WorksheetMeta>) BaseCacheManager.get(WORKSHEET_CACHEKEY, WORKSHEET_CACHEKEY);

		WorksheetMeta worksheetMeta = metaMap.get(baseSchema);

		List<StepMeta> steps = worksheetMeta.getSteps();
		for (int i = 0; i < steps.size(); i++)
		{
			StepMeta stepMeta = steps.get(i);
			String stepNoTemp = stepMeta.getStepModel().getStepNo();
			if (stepNoTemp.equals(stepNo))
			{
				List<ActionMeta> actionList = stepMeta.getActions();
				for (ActionMeta actionMeta : actionList)
				{
					if (actionName.equals(actionMeta.getActionModel().getActionName()))
					{
						List<ActionFieldRelationModel> actionFieldList = actionMeta.getFields();
						for (ActionFieldRelationModel actionField : actionFieldList)
						{
							actionFieldMap.put(actionField.getBaseField().getFieldName(), actionField);
						}
						break;
					}
				}
			}
		}
		return actionFieldMap;

	}

	/**
	 * 自由流程工单编辑字段
	 */
	public Map<String, EditableFieldModel> getStatusFields(String baseSchema, String baseStatus)
	{
		Map<String, EditableFieldModel> map = new HashMap<String, EditableFieldModel>();

		metaMap = (Map<String, WorksheetMeta>) BaseCacheManager.get(WORKSHEET_CACHEKEY, WORKSHEET_CACHEKEY);

		WorksheetMeta worksheetMeta = metaMap.get(baseSchema);

		List<FreeActionFieldRelationModel> fieldList = worksheetMeta.getFreeActionFields();

		for (FreeActionFieldRelationModel freeActionFieldRelation : fieldList)
		{
			if (baseStatus.equals(freeActionFieldRelation.getBaseStatus()) && (freeActionFieldRelation.getActionType() == null || freeActionFieldRelation.getActionType().equals("")))
			{
				map.put(freeActionFieldRelation.getBaseField().getFieldName(), freeActionFieldRelation);
			}
		}
		return map;
	}

	/**
	 * 自由流程动作编辑字段
	 */
	public Map<String, EditableFieldModel> getFreeActionFields(String baseSchema, String baseStatus, String actionType)
	{
		Map<String, EditableFieldModel> map = new HashMap<String, EditableFieldModel>();

		metaMap = (Map<String, WorksheetMeta>) BaseCacheManager.get(WORKSHEET_CACHEKEY, WORKSHEET_CACHEKEY);

		WorksheetMeta worksheetMeta = metaMap.get(baseSchema);

		List<FreeActionFieldRelationModel> fieldList = worksheetMeta.getFreeActionFields();

		for (FreeActionFieldRelationModel freeActionFieldRelation : fieldList)
		{
			if (baseStatus.equals(freeActionFieldRelation.getBaseStatus()) && actionType.equals(freeActionFieldRelation.getActionType()))
			{
				map.put(freeActionFieldRelation.getBaseField().getFieldName(), freeActionFieldRelation);
			}
		}
		return map;
	}

	/**
	 * * 获取固定的动作列表Map
	 */
	public Map<String, ActionModel> getFixActionMap(String baseSchema, String stepNo)
	{

		Map<String, ActionModel> map = new HashMap<String, ActionModel>();

		metaMap = (Map<String, WorksheetMeta>) BaseCacheManager.get(WORKSHEET_CACHEKEY, WORKSHEET_CACHEKEY);

		WorksheetMeta worksheetMeta = metaMap.get(baseSchema);

		List<StepMeta> stepList = worksheetMeta.getSteps();

		for (StepMeta stepMeta : stepList)
		{
			if (stepNo.equals(stepMeta.getStepModel().getStepNo()))
			{
				List<ActionMeta> actionList = stepMeta.getActions();
				for (ActionMeta actionMeta : actionList)
				{
					map.put(actionMeta.getActionModel().getActionName(), actionMeta.getActionModel());
				}
				break;
			}
		}
		return map;
	}

	/**
	 * 获取字段与容器布局关系，格式为 
	 * [
		{"fieldName":"BaseSummary","parentName":"P_main","childName":[]},
		{"fieldName":"P_main","parentName":"PG_Main","childName":[]},
		{"fieldName":"PG_Main","parentName":"BEGIN","childName":["P_main","P_DealList","HiddenPanelF"]}
		];
	 */
	public String getLayOutJSON(String baseSchema)
	{
		Map<String, Map<String, BaseField>> fieldTypeMap = fieldMap.get(baseSchema);
		//fieldTypeMap 中key是字段的类型，将容器，容器组类型的字段提取出来。提供给之后的逻辑调用。
		Map<String,BaseField> parentFieldMap = new HashMap<String, BaseField>();
		parentFieldMap.putAll(fieldTypeMap.get("PanelField"));
		parentFieldMap.putAll(fieldTypeMap.get("PanelGroupField"));
		// fieldList 存放所有字段
		List<LayOutJsonBean> fieldList = new ArrayList<LayOutJsonBean>();
		// parentJsonBeanMap 存放容器，容器组的json对象，
		Map<String,LayOutJsonBean> parentJsonBeanMap = new HashMap<String, LayOutJsonBean>();
		// 封装json对象过程，将一般的字段存入fieldList列表里，将容器，容器组字段放到parentJsonBeanMap里，如果是容器或者容器组字段，首先判断
		// parentJsonBeanMap里是否已经存在该字段，如果不存在，进行创建，如果存在且当前为容器类型字段，那么要取当前容器的上级容器组，并且将上级容器组的
		// childNameArray进行更新。当遍历完成后，parentJsonBeanMap内部是所有的容器，和容器组组件，且容器组对象的childNameArray已经封装完成。
		Set<String> keySet = fieldTypeMap.keySet();
		for (String key : keySet)
		{
			Map<String, BaseField> fieldByTypeMap = fieldTypeMap.get(key);
			Set<String> keyByNameSet = fieldByTypeMap.keySet();
			for (String keyByName : keyByNameSet)
			{
				BaseField baseField = fieldByTypeMap.get(keyByName);
				if (!"action".equals(baseField.getParentID()))
				{
					//容器或者容器组字段
					if(key.equals("PanelField")||key.equals("PanelGroupField"))
					{
						LayOutJsonBean panelTemp = parentJsonBeanMap.get(baseField.getId());
						BaseField parentField = parentFieldMap.get(baseField.getParentID());
						if(panelTemp==null){//如果不存在当前容器字段，将进行封装对象操作
							panelTemp = new LayOutJsonBean();
							panelTemp.setFieldName(baseField.getFieldName());
							if(parentField!=null)
								panelTemp.setParentName(parentField.getFieldName());
							parentJsonBeanMap.put(baseField.getId(), panelTemp);
						}
						//当前字段为容器字段时
						if(key.equals("PanelField")){
							if(parentField!=null){
								//封装当前容器字段的上级容器组字段
								PanelGroupField pgfield = (PanelGroupField)parentField;
								if(pgfield.getType().equals("tab")){
									//如果容器组类型为tab，需要封装容器组jsonbean，并且将容器组的下级容器fieldName进行封装
									LayOutJsonBean pgTemp = parentJsonBeanMap.get(pgfield.getId());
									if(pgTemp==null){
										pgTemp = new LayOutJsonBean();
										pgTemp.setFieldName(pgfield.getFieldName());
										if(pgfield.getParentID().equals("main"))
										{	//说明已经是最外层的容器组
											pgTemp.setParentName("main");
										}else{
											BaseField panelFieldTemp = parentFieldMap.get(pgfield.getParentID());
											if(panelFieldTemp!=null)
												pgTemp.setParentName(panelFieldTemp.getFieldName());
										}
										List childList = new ArrayList();
										childList.add(panelTemp.getFieldName());
										pgTemp.setChildNameArray(childList);
										parentJsonBeanMap.put(pgfield.getId(), pgTemp);
									}else{
										List childList = pgTemp.getChildNameArray();
										if(childList == null){
											childList = new ArrayList();
										}
										childList.add(panelTemp.getFieldName());
										pgTemp.setChildNameArray(childList);
									}
								}
							}
							
						}
						
					}else{
						//将非布局的字段封装到字段列表中。
						LayOutJsonBean fieldTemp = new LayOutJsonBean();
						fieldTemp.setFieldName(baseField.getFieldName());
						BaseField parentField = parentFieldMap.get(baseField.getParentID());
						if(parentField!=null)
							fieldTemp.setParentName(parentField.getFieldName());
						fieldList.add(fieldTemp);
					}
				}
			}
		}
		Iterator iterator = parentJsonBeanMap.keySet().iterator();
		for(int i=0;iterator.hasNext();i++){
			String key = (String)iterator.next();
			fieldList.add(parentJsonBeanMap.get(key));
		}
		String rejson = "";
		if(fieldList.size()>0){
			JSONArray jsonArray = JSONArray.fromObject( fieldList ); 
			rejson = jsonArray.toString();
		}
		return rejson;
	}

	/**
	 * 获取自由动作列表Map
	 */
	public Map<String, ActionModel> getFreeActionMap()
	{
		Map<String, ActionModel> map = (Map<String, ActionModel>) BaseCacheManager.get(FREEACTION_CACHEKEY, FREEACTION_CACHEKEY);
		return map;
	}

	public void reflushFreeAction()
	{
		Map<String, ActionModel> map = (Map<String, ActionModel>) BaseCacheManager.get(FREEACTION_CACHEKEY, FREEACTION_CACHEKEY);

		if (map == null)
		{
			map = new HashMap<String, ActionModel>();
			BaseCacheManager.put(FREEACTION_CACHEKEY, FREEACTION_CACHEKEY, map);
		}

		String path = "/wfengine/workflows/freeaction_config.xml";
		log.info("开始加载自由动作配置文件。path=" + path);
		SAXReader reader = new SAXReader();
		Document doc;
		try
		{
			doc = reader.read(WfEngineUtils.class.getResourceAsStream(path));

			List nodes = doc.selectNodes("//actions/action");
			if (CollectionUtils.isNotEmpty(nodes))
			{
				for (int i = 0; i < nodes.size(); i++)
				{
					ActionModel actionModel = new ActionModel();
					Node node = (Node) nodes.get(i);
					String actionType = node.valueOf("@actionType");
					if (StringUtils.isNotBlank(actionType))
					{
						String actionLabel = node.valueOf("@actionLabel");
						String hasNext = node.valueOf("@hasNext");
						String description = node.valueOf("@desc");
						String isClose = node.valueOf("@isClose");
						actionModel.setLabel(actionLabel);
						actionModel.setActionName(actionType);
						actionModel.setActionType(actionType);
						actionModel.setDescription(description);
						actionModel.setIsFree(1);
						actionModel.setIsClose(Integer.parseInt(isClose));
						actionModel.setHasNext(Integer.parseInt(hasNext));
						map.put(actionType, actionModel);
					} else
					{
						throw new WorkflowException("action的actionType属性不能为空！" + node.asXML());
					}
				}
			}
		} catch (DocumentException e)
		{
			e.printStackTrace();
		}

	}

	public void reflushFieldInfo()
	{
		Map<String, FieldConfig> map = (Map<String, FieldConfig>) BaseCacheManager.get(FIELDINFO_CACHEKEY, FIELDINFO_CACHEKEY);
		if (map == null)
		{
			map = new HashMap<String, FieldConfig>();
			BaseCacheManager.put(FIELDINFO_CACHEKEY, FIELDINFO_CACHEKEY, map);
		}

		String path = "/wfengine/workflows/fields_config.xml";
		log.info("开始加载字段配置文件。path=" + path);
		SAXReader reader = new SAXReader();
		Document doc;
		try
		{
			doc = reader.read(MetaCacheDBImpl.class.getResourceAsStream(path));

			//整理字段分类信息
			Map<String,List<AttributeConfig>> typesMap=new HashMap<String,List<AttributeConfig>>();
			List typeNodes=doc.selectNodes("//fields-config/field-types/type");
			for(int i=0;i<typeNodes.size();i++){
				Node node = (Node) typeNodes.get(i);
				List attrNodes = node.selectNodes("attr");
				List<AttributeConfig> attrList = new ArrayList<AttributeConfig>();
				for(int j=0;j<attrNodes.size();j++){
					Node attrNode = (Node)  attrNodes.get(j);
					AttributeConfig config = new AttributeConfig();
					config.setName(attrNode.valueOf("@name"));
					config.setType(attrNode.valueOf("@type"));
					config.setLabel(attrNode.valueOf("@label"));
					attrList.add(config);
				}
				typesMap.put(node.valueOf("@name"), attrList);
			}
			
			List nodes = doc.selectNodes("//fields-config/fields/field");
			if (CollectionUtils.isNotEmpty(nodes))
			{
				for (int i = 0; i < nodes.size(); i++)
				{
					FieldConfig fieldConfig = new FieldConfig();
					Node node = (Node) nodes.get(i);
					String name = node.valueOf("@name");
					if (StringUtils.isNotBlank(name))
					{
						String label = node.valueOf("@label");
						String fullName = node.valueOf("@fullName");
						String tableName = node.valueOf("@tableName");
						String extendsName = node.valueOf("@extendsName");
						String extendsLabel = node.valueOf("@extendsLabel");
						String orderNum = node.valueOf("@orderNum");
						fieldConfig.setName(name);
						fieldConfig.setLabel(label);
						fieldConfig.setFullName(fullName);
						fieldConfig.setTableName(tableName);
						fieldConfig.setExtendsName(extendsName);
						fieldConfig.setExtendsLabel(extendsLabel);
						fieldConfig.setOrderNum(orderNum);
						fieldConfig.setAttrList(typesMap.get(extendsName));
						
						map.put(name, fieldConfig);

						// Class<BaseField> field = (Class<BaseField>)
						// Class.forName(fullName);

					} else
					{
						throw new WorkflowException("field的field属性不能为空！" + node.asXML());
					}
				}
			}
		} catch (DocumentException e)
		{
			e.printStackTrace();
		}
	}

	public void reflushCacheByWorksheet(String baseSchema)
	{
		this.loadFieldData(baseSchema);

		fieldMap = new HashMap<String, Map<String, Map<String, BaseField>>>();

		metaMap = (Map<String, WorksheetMeta>) BaseCacheManager.get(WORKSHEET_CACHEKEY, WORKSHEET_CACHEKEY);

		fieldMap.put(baseSchema, this.getBaseSchemaFieldMap(baseSchema));

		WorksheetMeta worksheetMeta = metaMap.get(baseSchema);

		worksheetMeta.setFields(this.getFieldList(baseSchema));

	}

	public void reflushCacheByStep(String baseSchema, String stepNo)
	{
		this.loadFieldData(baseSchema);

		fieldMap = new HashMap<String, Map<String, Map<String, BaseField>>>();

		metaMap = (Map<String, WorksheetMeta>) BaseCacheManager.get(WORKSHEET_CACHEKEY, WORKSHEET_CACHEKEY);

		fieldMap.put(baseSchema, this.getBaseSchemaFieldMap(baseSchema));

		WorksheetMeta worksheetMeta = metaMap.get(baseSchema);

		List<StepMeta> stepMetaList = worksheetMeta.getSteps();

		for (StepMeta stepMeta : stepMetaList)
		{
			if (stepNo.equals(stepMeta.getStepModel().getStepNo()))
			{
				stepMeta.setFields(this.getStepFieldRelations(baseSchema, stepNo));
			}
		}
	}

	public String getLayOutJsonString(String baseSchema)
	{
		metaMap = (Map<String, WorksheetMeta>) BaseCacheManager.get(WORKSHEET_CACHEKEY, WORKSHEET_CACHEKEY);
		WorksheetMeta worksheetMeta = metaMap.get(baseSchema);
		return worksheetMeta.getLayoutJSON();
	}

	public void reflushCacheByAction(String baseSchema, String stepNo, String actionName)
	{

	}

	public void reflushCacheByStatusAction(String baseSchema)
	{
		/**
		 * 方法加载注释示例
		 * 
		 * @param note
		 *            参数及其意义
		 * @return String 返回值
		 * @exception IOException
		 *                异常类及抛出条件
		 * @see TemplateDoc
		 */

	}

	private void loadFieldData(String baseSchema)
	{
		String sql = "";
		if (baseSchema != null && baseSchema.length() > 0)
		{
			sql = " where baseSchema='" + baseSchema + "'";
		}

		wfTypes = hibernateDao.find("from WfType " + sql);

		Map<String, FieldConfig> fieldInfoMap = (Map<String, FieldConfig>) BaseCacheManager.get(FIELDINFO_CACHEKEY, FIELDINFO_CACHEKEY);

		try
		{
			Set<String> keySet = fieldInfoMap.keySet();
			for (String key : keySet)
			{
				FieldConfig fieldConfig = fieldInfoMap.get(key);
				Method setFieldMethod = this.getClass().getMethod("set" + fieldConfig.getName() + "s", new Class<?>[] { Class.forName("java.util.List") });
				List<?> fieldList = null;
				fieldList = hibernateDao.find("from " + fieldConfig.getName() + sql);
				setFieldMethod.invoke(this, fieldList);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			log.info("缓存 所有字段加载异常！");
		}

		steps = hibernateDao.find("from StepModel " + sql);
		actions = hibernateDao.find("from ActionModel " + sql + " order by orderId");
		stepFieldRelations = hibernateDao.find("from StepFieldRelationModel " + sql);
		actionFieldRelations = hibernateDao.find("from ActionFieldRelationModel " + sql);
		freeActionFieldRelations = hibernateDao.find("from FreeActionFieldRelationModel " + sql);

		log.info("缓存 所有字段加载成功！");
	}

	private Map<String, BaseField> getFieldList(String baseSchema)
	{

		Map<String, BaseField> map = new HashMap<String, BaseField>();

		Map<String, Map<String, BaseField>> fieldTypeMap = fieldMap.get(baseSchema);
		Set<String> keySet = fieldTypeMap.keySet();
		for (String key : keySet)
		{
			Map<String, BaseField> fieldByTypeMap = fieldTypeMap.get(key);
			Set<String> keyByNameSet = fieldByTypeMap.keySet();
			for (String keyByName : keyByNameSet)
			{
				BaseField baseField = fieldByTypeMap.get(keyByName);
				if (!"action".equals(baseField.getParentID()))
				{
					baseField.setFieldType(key);
					map.put(baseField.getFieldName(), baseField);
				}
			}
		}
		return map;
	}
	private Map<String, BaseField> getActionFieldList(String baseSchema)
	{

		Map<String, BaseField> map = new HashMap<String, BaseField>();

		Map<String, Map<String, BaseField>> fieldTypeMap = fieldMap.get(baseSchema);
		Set<String> keySet = fieldTypeMap.keySet();
		for (String key : keySet)
		{
			Map<String, BaseField> fieldByTypeMap = fieldTypeMap.get(key);
			Set<String> keyByNameSet = fieldByTypeMap.keySet();
			for (String keyByName : keyByNameSet)
			{
				BaseField baseField = fieldByTypeMap.get(keyByName);
				if ("action".equals(baseField.getParentID()))
				{
					baseField.setFieldType(key);
					map.put(baseField.getFieldName(), baseField);
				}
			}
		}
		return map;
	}

	/**
	 * 通过表单类型获得表单的所有环节信息
	 * 
	 * @param baseSchema
	 * @return
	 */
	private List<StepMeta> getStepMetaList(String baseSchema)
	{
		List<StepMeta> stepMetas = new ArrayList<StepMeta>();
		for (StepModel stepModel : steps)
		{

			if (!stepModel.getBaseSchema().equals(baseSchema))
				continue;

			StepMeta stepMeta = new StepMeta();

			stepMeta.setStepModel(stepModel);

			stepMeta.setFields(this.getStepFieldRelations(baseSchema, stepModel.getStepNo()));

			stepMeta.setActions(this.getActionMetas(baseSchema, stepModel.getStepNo()));

			stepMetas.add(stepMeta);
		}
		return stepMetas;
	}

	private List<StepFieldRelationModel> getStepFieldRelations(String baseSchema, String stepNo)
	{
		List<StepFieldRelationModel> list = new ArrayList<StepFieldRelationModel>();
		for (StepFieldRelationModel stepFieldRelationModel : stepFieldRelations)
		{
			if (!baseSchema.equals(stepFieldRelationModel.getBaseSchema()) || !stepNo.equals(stepFieldRelationModel.getStepNo()))
				continue;

			Map<String, Map<String, BaseField>> fieldTypeMap = fieldMap.get(baseSchema);

			Map<String, BaseField> fieldByTypeMap = fieldTypeMap.get(stepFieldRelationModel.getFieldType());

			if (fieldByTypeMap != null)
			{
				BaseField field = fieldByTypeMap.get(stepFieldRelationModel.getFieldID());
				if (field != null)
				{
					stepFieldRelationModel.setBaseField(field);
					field.setFieldType(stepFieldRelationModel.getFieldType());
					list.add(stepFieldRelationModel);
				}

			}

		}
		return list;
	}

	private List<ActionMeta> getActionMetas(String baseSchema, String stepNo)
	{
		List<ActionMeta> list = new ArrayList<ActionMeta>();
		for (ActionModel actionModel : actions)
		{
			if (!actionModel.getBaseSchema().equals(baseSchema) || !stepNo.equals(actionModel.getStepNo()))
				continue;

			ActionMeta actionMeta = new ActionMeta();

			actionMeta.setActionModel(actionModel);

			actionMeta.setFields(getActionFieldRelations(baseSchema,actionModel.getStepNo(), actionModel.getActionName()));

			list.add(actionMeta);
		}
		return list;
	}

	private List<ActionFieldRelationModel> getActionFieldRelations(String baseSchema,String stepNo, String actionName)
	{
		List<ActionFieldRelationModel> list = new ArrayList<ActionFieldRelationModel>();
		for (ActionFieldRelationModel actionFieldRelationModel : actionFieldRelations)
		{
			if (!baseSchema.equals(actionFieldRelationModel.getBaseSchema()) || !actionName.equals(actionFieldRelationModel.getActionName()) || !stepNo.equals(actionFieldRelationModel.getStepNo()))
				continue;

			Map<String, Map<String, BaseField>> fieldTypeMap = fieldMap.get(baseSchema);

			Map<String, BaseField> fieldByTypeMap = fieldTypeMap.get(actionFieldRelationModel.getFieldType());

			if (fieldByTypeMap != null)
			{
				BaseField field = fieldByTypeMap.get(actionFieldRelationModel.getFieldID());
				if (field != null)
				{
					field.setFieldType(actionFieldRelationModel.getFieldType());
					actionFieldRelationModel.setBaseField(field);
					list.add(actionFieldRelationModel);
				}

			}

		}
		return list;
	}

	private List<FreeActionFieldRelationModel> getFreeActionFieldList(String baseSchema)
	{
		List<FreeActionFieldRelationModel> list = new ArrayList<FreeActionFieldRelationModel>();

		Map<String, Map<String, BaseField>> fieldTypeMap = fieldMap.get(baseSchema);

		for (FreeActionFieldRelationModel freeActionFieldRelationModel : freeActionFieldRelations)
		{
			if (!baseSchema.equals(freeActionFieldRelationModel.getBaseSchema()))
				continue;

			Map<String, BaseField> fieldByTypeMap = fieldTypeMap.get(freeActionFieldRelationModel.getFieldType());

			if (fieldByTypeMap != null)
			{
				BaseField field = fieldByTypeMap.get(freeActionFieldRelationModel.getFieldID());
				if (field != null)
				{
					field.setFieldType(freeActionFieldRelationModel.getFieldType());
					freeActionFieldRelationModel.setBaseField(field);
					list.add(freeActionFieldRelationModel);
				}

			}

		}
		return list;
	}

	/**
	 * 将baseSchema工单类型的所有字段封装为 Map<String, Map<String, BaseField>> （Map<"FieldType",
	 * Map<"FieldID", BaseField>>）的形式
	 * 
	 * @param baseSchema
	 * @return
	 */
	private Map<String, Map<String, BaseField>> getBaseSchemaFieldMap(String baseSchema)
	{
		Map<String, Map<String, BaseField>> fieldTypeMap = new HashMap<String, Map<String, BaseField>>();

		Map<String, FieldConfig> fieldInfoMap = (Map<String, FieldConfig>) BaseCacheManager.get(FIELDINFO_CACHEKEY, FIELDINFO_CACHEKEY);
		Set<String> keySet = fieldInfoMap.keySet();
		try
		{

			for (String key : keySet)
			{
				FieldConfig fieldConfig = fieldInfoMap.get(key);
				Map<String, BaseField> fieldMap = new HashMap<String, BaseField>();

				List<BaseField> fieldList = (List<BaseField>) this.getClass().getMethod("get" + fieldConfig.getName() + "s").invoke(this);

				if (fieldList == null)
					continue;

				for (BaseField field : fieldList)
				{
					if (!baseSchema.equals(field.getBaseSchema()))
						continue;
					// field.setFieldType("action".equals(field.getParentID()) ?
					// "action" : "worksheet");
					field.setFieldType(key);
					fieldMap.put(field.getId(), field);
				}
				fieldTypeMap.put(fieldConfig.getName(), fieldMap);
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return fieldTypeMap;
	}

	public List<RollbackListField> getRollbackListFields()
	{
		return rollbackListFields;
	}

	public void setRollbackListFields(List<RollbackListField> rollbackListFields)
	{
		this.rollbackListFields = rollbackListFields;
	}

	public IDao getHibernateDao()
	{
		return hibernateDao;
	}

	public void setHibernateDao(IDao hibernateDao)
	{
		this.hibernateDao = hibernateDao;
	}

	public List<WfType> getWfTypes()
	{
		return wfTypes;
	}

	public void setWfTypes(List<WfType> wfTypes)
	{
		this.wfTypes = wfTypes;
	}

	public List<CharacterField> getCharacterFields()
	{
		return characterFields;
	}

	public void setCharacterFields(List<CharacterField> characterFields)
	{
		this.characterFields = characterFields;
	}

	public List<SelectField> getSelectFields()
	{
		return selectFields;
	}

	public void setSelectFields(List<SelectField> selectFields)
	{
		this.selectFields = selectFields;
	}

	
	public List<CollectField> getCollectFields()
	{
		return collectFields;
	}

	public void setCollectFields(List<CollectField> collectFields)
	{
		this.collectFields = collectFields;
	}

	public List<TimeField> getTimeFields()
	{
		return timeFields;
	}

	public void setTimeFields(List<TimeField> timeFields)
	{
		this.timeFields = timeFields;
	}

	public List<HiddenField> getHiddenFields()
	{
		return hiddenFields;
	}

	public void setHiddenFields(List<HiddenField> hiddenFields)
	{
		this.hiddenFields = hiddenFields;
	}

	public List<AssignTreeField> getAssignTreeFields()
	{
		return assignTreeFields;
	}

	public void setAssignTreeFields(List<AssignTreeField> assignTreeFields)
	{
		this.assignTreeFields = assignTreeFields;
	}

	public List<AttachmentField> getAttachmentFields()
	{
		return attachmentFields;
	}

	public void setAttachmentFields(List<AttachmentField> attachmentFields)
	{
		this.attachmentFields = attachmentFields;
	}

	public List<PanelField> getPanelFields()
	{
		return panelFields;
	}

	public void setPanelFields(List<PanelField> panelFields)
	{
		this.panelFields = panelFields;
	}

	public List<PanelGroupField> getPanelGroupFields()
	{
		return panelGroupFields;
	}

	public void setPanelGroupFields(List<PanelGroupField> panelGroupFields)
	{
		this.panelGroupFields = panelGroupFields;
	}

	public List<BlankField> getBlankFields()
	{
		return blankFields;
	}

	public void setBlankFields(List<BlankField> blankFields)
	{
		this.blankFields = blankFields;
	}

	public List<ButtonField> getButtonFields()
	{
		return buttonFields;
	}

	public void setButtonFields(List<ButtonField> buttonFields)
	{
		this.buttonFields = buttonFields;
	}

	public List<LabelField> getLabelFields()
	{
		return labelFields;
	}

	public void setLabelFields(List<LabelField> labelFields)
	{
		this.labelFields = labelFields;
	}

	public List<ViewField> getViewFields()
	{
		return viewFields;
	}

	public void setViewFields(List<ViewField> viewFields)
	{
		this.viewFields = viewFields;
	}

	public List<StepModel> getSteps()
	{
		return steps;
	}

	public void setSteps(List<StepModel> steps)
	{
		this.steps = steps;
	}

	public List<ActionModel> getActions()
	{
		return actions;
	}

	public void setActions(List<ActionModel> actions)
	{
		this.actions = actions;
	}

	public List<StepFieldRelationModel> getStepFieldRelations()
	{
		return stepFieldRelations;
	}

	public void setStepFieldRelations(List<StepFieldRelationModel> stepFieldRelations)
	{
		this.stepFieldRelations = stepFieldRelations;
	}

	public List<ActionFieldRelationModel> getActionFieldRelations()
	{
		return actionFieldRelations;
	}

	public void setActionFieldRelations(List<ActionFieldRelationModel> actionFieldRelations)
	{
		this.actionFieldRelations = actionFieldRelations;
	}

	public List<FreeActionFieldRelationModel> getFreeActionFieldRelations()
	{
		return freeActionFieldRelations;
	}

	public void setFreeActionFieldRelations(List<FreeActionFieldRelationModel> freeActionFieldRelations)
	{
		this.freeActionFieldRelations = freeActionFieldRelations;
	}
	
	

}
