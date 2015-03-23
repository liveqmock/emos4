package com.ultrapower.eoms.ultrabpp.develop.manager;

import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ultrapower.eoms.common.core.component.cache.manager.BaseCacheManager;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.ultrabpp.cache.model.ActionFieldRelationModel;
import com.ultrapower.eoms.ultrabpp.cache.model.ActionMeta;
import com.ultrapower.eoms.ultrabpp.cache.model.ActionModel;
import com.ultrapower.eoms.ultrabpp.cache.model.AssignTreeConfigModel;
import com.ultrapower.eoms.ultrabpp.cache.model.FieldConfig;
import com.ultrapower.eoms.ultrabpp.cache.model.FreeActionFieldRelationModel;
import com.ultrapower.eoms.ultrabpp.cache.model.StepFieldRelationModel;
import com.ultrapower.eoms.ultrabpp.cache.model.StepMeta;
import com.ultrapower.eoms.ultrabpp.cache.model.StepModel;
import com.ultrapower.eoms.ultrabpp.cache.model.WorksheetMeta;
import com.ultrapower.eoms.ultrabpp.cache.service.MetaCacheService;
import com.ultrapower.eoms.ultrabpp.develop.model.TreeNode;
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
import com.ultrapower.workflow.bizconfig.sort.IWfSortManager;
import com.ultrapower.workflow.client.WorkFlowServiceClient;
import com.ultrapower.workflow.configuration.sort.model.WfType;

/**
 * @author fying
 * @version
 * @since
 * @see TemplateDoc
 */
public class TemplateManagerImpl implements TemplateService
{
	private static Logger log = LoggerFactory.getLogger(TemplateManagerImpl.class);

	private final String FIELDINFO_CACHEKEY = "bppFieldInfoMap";
	private final String FREEACTION_CACHEKEY = "bppFreeActionMap";

	/**
	 * 工单类型信息
	 */
	private WfType wfType;

	/**
	 * 容器组
	 */ 
	private List<PanelGroupField> panelGroupFields;
	private List<PanelField> panelFields;

	private List<CharacterField> characterFields;
	private List<SelectField> selectFields;
	private List<CollectField> collectFields;
	private List<TimeField> timeFields;
	private List<HiddenField> hiddenFields;

	private List<BlankField> blankFields;
	private List<ButtonField> buttonFields;
	private List<LabelField> labelFields;
	private List<ViewField> viewFields;

	private List<AssignTreeField> assignTreeFields;
	private List<AttachmentField> attachmentFields;
	private List<RollbackListField> rollbackListFields;

	private IDao hibernateDao;

	private MetaCacheService metaCacheService;

	/**
	 * 数据加载成功的标识
	 */
	private boolean fieldDataLoadFlag = false;

	public String deployMain(String baseSchema)
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
		return null;
	}

	public String deployStep(String baseSchema, String stepCode)
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
		return null;
	}

	public String previewAction(String baseSchema, String actionID)
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
		return null;
	}

	public String previewMain(String baseSchema)
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
		return null;
	}

	public List<BaseField> mainFieldList(String baseSchema)
	{
		log.info("加载主表单字段 baseSchema:"+baseSchema);
		List<BaseField> list = new ArrayList<BaseField>();
		TreeNode treeNode = buildTree(baseSchema, true);
		if (treeNode.getBaseField() != null)
		{
			list.add(treeNode.getBaseField());
		}
		if (treeNode.getChiNodes() != null)
		{
			for (TreeNode node : treeNode.getChiNodes())
			{
				list.add(node.getBaseField());
				// -dfw-对node 不为空的校验
				if (node.getChiNodes() != null)
				{
					this.chiFieldList(list, node);
				}
			}
		}
		return list;
	}

	/**
	 * 自由流程 主表单配置页面 需要提前配置好主表单字段 和动作字段 接口提供baseSchema表单的所以可以与动作关联的字段。 与动作关联的表
	 * 没有容器，和容器组字段。
	 */
	public List<BaseField> actionFieldFreeList(String baseSchema)
	{
		String sql = " baseSchema=? and parentID='action' ";
		this.loadFieldData(baseSchema, sql, new String[] { baseSchema });

		List<BaseField> list = new ArrayList<BaseField>();

		Map<String, FieldConfig> fieldInfoMap = (Map<String, FieldConfig>) BaseCacheManager.get(FIELDINFO_CACHEKEY, FIELDINFO_CACHEKEY);

		Set<String> keySet = fieldInfoMap.keySet();

		try
		{
			for (String key : keySet)
			{
				FieldConfig fieldConfig = fieldInfoMap.get(key);
				List<BaseField> fieldList = (List<BaseField>) this.getClass().getMethod("get" + fieldConfig.getName() + "s").invoke(this);
				for (BaseField baseField : fieldList)
				{
					baseField.setFieldType(fieldConfig.getName());
				}
				list.addAll(fieldList);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		// 节点排序
		for (int i = 0; i < list.size() - 1; i++)
		{
			for (int j = 1; j < list.size() - i; j++)
			{
				BaseField model;

				if (list.get(j - 1).getOrderNum() > list.get(j).getOrderNum())
				{
					model = list.get(j - 1);
					list.set((j - 1), list.get(j));
					list.set(j, model);
				}
			}
		}

		return list;
	}

	private void chiFieldList(List<BaseField> list, TreeNode node)
	{
		for (TreeNode chiNode : node.getChiNodes())
		{
			list.add(chiNode.getBaseField());
			if (chiNode.getChiNodes() != null)
			{
				this.chiFieldList(list, chiNode);
			}
		}
	}

	/**
	 * 接口实现：返回baseSchema主表单的所有配置字段，包括添加，修改，删除（部署过的字段有删除概念）的字段
	 */
	public TreeNode buildTree(String baseSchema, boolean allFlag)
	{
		log.info("采用封装表单字段 baseSchema:"+baseSchema+" allFlag:"+allFlag);
		String sql = " baseSchema=? and parentID!='action' ";
		if (!allFlag)
		{
			sql = sql + " and operate!='delete' ";
		}

		this.loadFieldData(baseSchema, sql, new String[] { baseSchema });

		// 开始封装表单配置的节点树形结构
		TreeNode node = new TreeNode();

		for (PanelGroupField field : panelGroupFields)
		{
			if (field.getId().equals(wfType.getPanelGroupID()))
			{
				field.setFieldType("PanelGroupField");
				node.setBaseField(field);
				break;
			}

		}

		List<TreeNode> childNodes = this.getPanelChildList(wfType.getPanelGroupID(), baseSchema);

		if (childNodes != null)
		{
			node.setChiNodes(childNodes);
		}
		return node;
	}

	public List<ActionFieldRelationModel> actionFieldList(String baseSchema, String stepNo, String actionName)
	{
		List<ActionFieldRelationModel> list = null;
		list = hibernateDao.find("from ActionFieldRelationModel where baseSchema=? and stepNo=? and actionName=?", new String[] { baseSchema, stepNo, actionName });

		List<ActionFieldRelationModel> removeList = new ArrayList<ActionFieldRelationModel>();
		for (ActionFieldRelationModel fieldModel : list)
		{
			BaseField baseField = (BaseField) hibernateDao.findUnique("from " + fieldModel.getFieldType() + " where baseSchema=? and id=?", new String[] { baseSchema, fieldModel.getFieldID() });

			if (baseField == null)
			{
				removeList.add(fieldModel);
			} else
			{
				fieldModel.setBaseField(baseField);
			}
		}
		if (list == null)
		{
			list = new ArrayList<ActionFieldRelationModel>();
		}

		list.removeAll(removeList);

		// 节点排序
		for (int i = 0; i < list.size() - 1; i++)
		{
			for (int j = 1; j < list.size() - i; j++)
			{
				ActionFieldRelationModel model;

				if (list.get(j - 1).getBaseField().getOrderNum() > list.get(j).getBaseField().getOrderNum())
				{
					model = list.get(j - 1);
					list.set((j - 1), list.get(j));
					list.set(j, model);
				}
			}
		}

		return list;
	}

	public List<StepFieldRelationModel> stepFieldList(String baseSchema, String stepNo)
	{
		List<StepFieldRelationModel> list = hibernateDao.find("from StepFieldRelationModel where baseSchema=? and stepNo=?", new String[] { baseSchema, stepNo });

		List<StepFieldRelationModel> tempList = new ArrayList<StepFieldRelationModel>();
		for (int i = 0; i < list.size(); i++)
		{
			StepFieldRelationModel fieldModel = list.get(i);
			BaseField baseField = (BaseField) hibernateDao.findUnique("from " + fieldModel.getFieldType() + " where baseSchema=? and id=?", new String[] { baseSchema, fieldModel.getFieldID() });
			fieldModel.setBaseField(baseField);
			if (baseField == null)
			{
				hibernateDao.remove(fieldModel);
				tempList.add(fieldModel);
			}
		}
		if (tempList.size() > 0)
			list.removeAll(tempList);
		return list;
	}

	public List<TreeNode> getPanelChildList(String parentID, String baseSchema)
	{
		List<TreeNode> childNodes = new ArrayList<TreeNode>();

		if (!fieldDataLoadFlag)
		{
			this.loadFieldData(baseSchema, " baseSchema=? and parentID=? ", new String[] { baseSchema, parentID });
		}
		for (PanelField panelField : panelFields)
		{
			if (parentID.equals(panelField.getParentID()))
			{
				TreeNode node = new TreeNode();
				panelField.setFieldType("PanelField");
				node.setBaseField(panelField);
				node.setChiNodes(this.getOtherChildList(panelField.getId(), baseSchema));
				childNodes.add(node);
			}
		}
		// 节点排序
		for (int i = 0; i < childNodes.size() - 1; i++)
		{
			for (int j = 1; j < childNodes.size() - i; j++)
			{
				TreeNode node;

				if (childNodes.get(j - 1).getBaseField().getOrderNum() > childNodes.get(j).getBaseField().getOrderNum())
				{
					node = childNodes.get(j - 1);
					childNodes.set((j - 1), childNodes.get(j));
					childNodes.set(j, node);
				}
			}
		}

		return childNodes;
	}

	public List<TreeNode> getOtherChildList(String parentID, String baseSchema)
	{
		List<TreeNode> childNodes = new ArrayList<TreeNode>();

		if (!fieldDataLoadFlag)
		{
			String sql = " baseSchema=? and parentID=? ";
			this.loadFieldData(baseSchema, sql, new String[] { baseSchema, parentID });
		}

		Map<String, FieldConfig> map = (Map<String, FieldConfig>) BaseCacheManager.get(FIELDINFO_CACHEKEY, FIELDINFO_CACHEKEY);

		Set<String> keySet = map.keySet();
		try
		{
			for (String key : keySet)
			{
				FieldConfig fieldConfig = map.get(key);
				List<BaseField> list = (List<BaseField>) this.getClass().getMethod("get" + fieldConfig.getName() + "s").invoke(this);
				for (BaseField baseField : list)
				{
					if (parentID.equals(baseField.getParentID()))
					{
						TreeNode node = new TreeNode();
						baseField.setFieldType(fieldConfig.getName());
						node.setBaseField(baseField);
						if("PanelGroupField".equals(key)){
							node.setChiNodes(getPanelChildList(baseField.getId(), baseSchema));
						}else {
							node.setChiNodes(null);
						
						}
						childNodes.add(node);
					}
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		// 节点排序
		for (int i = 0; i < childNodes.size() - 1; i++)
		{
			for (int j = 1; j < childNodes.size() - i; j++)
			{
				TreeNode node;

				if (childNodes.get(j - 1).getBaseField().getOrderNum() > childNodes.get(j).getBaseField().getOrderNum())
				{
					node = childNodes.get(j - 1);
					childNodes.set((j - 1), childNodes.get(j));
					childNodes.set(j, node);
				}
			}
		}

		return childNodes;
	}

	public List<FreeActionFieldRelationModel> getFreeActionfieldrelList(String baseSchema)
	{
		List<FreeActionFieldRelationModel> freeActionFieldRelationList = null;
		freeActionFieldRelationList = hibernateDao.find("from FreeActionFieldRelationModel where baseSchema=? order by baseStatus", new String[] { baseSchema });
		// -dfw-通过 fieldType fieldID查出baseField 目的：拿到baseField里面的lable
		for (FreeActionFieldRelationModel freeActionFieldRelationModel : freeActionFieldRelationList)
		{
			String fieldType = freeActionFieldRelationModel.getFieldType();
			String fieldID = freeActionFieldRelationModel.getFieldID();
			BaseField basField = (BaseField) hibernateDao.findUnique("from " + fieldType + " where id=?", new String[] { fieldID });
			freeActionFieldRelationModel.setBaseField(basField);
		}

		return freeActionFieldRelationList;

	}

	public List<ActionModel> getFreeActionList()
	{
		List<ActionModel> list = new ArrayList<ActionModel>();
		Map<String, ActionModel> map = metaCacheService.getFreeActionMap();
		Set<String> keySet = map.keySet();
		for (String key : keySet)
		{
			list.add(map.get(key));
		}
		return list;
	}

	public TreeNode worksheetFieldForPage(String baseSchema)
	{
		TreeNode treeNode = this.buildTree(baseSchema, false);
		return treeNode;
	}

	public Map<String, List<BaseField>> worksheetFieldChangeMap(String baseSchema)
	{
		String sql = " baseSchema=? and hasDeploy=0 ";

		this.loadFieldData(baseSchema, sql, new String[] { baseSchema });

		Map<String, List<BaseField>> map = this.getFieldChangeMap();

		return map;
	}

	public void worksheetFieldChangeUpdate(String baseSchema)
	{
		String sql = " baseSchema=? and hasDeploy=0";
		this.loadFieldData(baseSchema, sql, new String[] { baseSchema });
		// 查询出来所有新增，修改，逻辑删除过的字段。
		Map<String, List<BaseField>> map = this.getFieldChangeMap();

		List<BaseField> deleteFieldList = map.get("delete");
		for (BaseField baseField : deleteFieldList)
		{
			if ("delete".equals(baseField.getOperate()) && baseField.getHasDeploy() == 0)
			{
				hibernateDao.remove(baseField);
			}
		}
		List<BaseField> updateFieldList = map.get("update");
		List<BaseField> addFieldList = map.get("add");
		addFieldList.addAll(updateFieldList);
		for (BaseField baseField : addFieldList)
		{
			if (baseField.getHasDeploy() == 0)
			{
				baseField.setHasDeploy(1);
				baseField.setOperate("add");
				hibernateDao.saveOrUpdate(baseField);
			}
		}

	}

	public List<BaseField> actionFieldForPage(String baseSchema, String stepNo, String actionName)
	{

		List<BaseField> actionFieldList = new ArrayList<BaseField>();

		String sqlString = "from ActionFieldRelationModel where baseSchema=? and stepNo=? and actionName=? ";
		List<ActionFieldRelationModel> actionFieldRlList = hibernateDao.find(sqlString, new String[] { baseSchema, stepNo, actionName });
		for (ActionFieldRelationModel fileModel : actionFieldRlList)
		{
			BaseField field = (BaseField) hibernateDao.findUnique(" from " + fileModel.getFieldType() + " where baseSchema=? and id=?", new String[] { baseSchema, fileModel.getFieldID() });
			if(field instanceof AssignTreeField){
				AssignTreeField assignTree = (AssignTreeField) field;
				assignTree.setActionName(actionName);
				assignTree.setStepno(stepNo);
				//fying 20130110 
				AssignTreeConfigModel configModel = (AssignTreeConfigModel)hibernateDao.findUnique("from AssignTreeConfigModel where baseSchema=? and stepNo=? and actionName=? and fieldName=?", new String[] { baseSchema, stepNo, actionName ,assignTree.getFieldName()});
				assignTree.setSelectType(configModel!=null?configModel.getSelectType():"2");
			}
			actionFieldList.add(field);
		}

		if (actionFieldList.size() > 0)
		{

			// 节点排序
			for (int i = 0; i < actionFieldList.size() - 1; i++)
			{
				for (int j = 1; j < actionFieldList.size() - i; j++)
				{
					BaseField baseFieldTmp;

					if (actionFieldList.get(j - 1).getOrderNum() > actionFieldList.get(j).getOrderNum())
					{
						baseFieldTmp = actionFieldList.get(j - 1);
						actionFieldList.set((j - 1), actionFieldList.get(j));
						actionFieldList.set(j, baseFieldTmp);
					}
				}
			}
		}

		return actionFieldList;
	}

	public List<BaseField> freeActionFieldDeploy(String baseSchema, String baseStatus, String actionName)
	{
		List<FreeActionFieldRelationModel> freeActionFieldRelationList = hibernateDao.find("from FreeActionFieldRelationModel where baseSchema=? and baseStatus=? and actionType=?", new String[] { baseSchema, baseStatus, actionName });
		List<BaseField> baseFieldList = new ArrayList<BaseField>();
		for (FreeActionFieldRelationModel freeActionFieldRelationModel : freeActionFieldRelationList)
		{
			String fieldType = freeActionFieldRelationModel.getFieldType();
			String fieldID = freeActionFieldRelationModel.getFieldID();
			BaseField basField = (BaseField) hibernateDao.findUnique("from " + fieldType + " where id=?", new String[] { fieldID });
			baseFieldList.add(basField);
		}

		// 节点排序
		for (int k = 0; k < baseFieldList.size() - 1; k++)
		{
			for (int j = 1; j < baseFieldList.size() - k; j++)
			{
				BaseField baseFieldTmp;

				if (baseFieldList.get(j - 1).getOrderNum() > baseFieldList.get(j).getOrderNum())
				{
					baseFieldTmp = baseFieldList.get(j - 1);
					baseFieldList.set((j - 1), baseFieldList.get(j));
					baseFieldList.set(j, baseFieldTmp);
				}
			}
		}
		return baseFieldList;
	}

	public Map<String, List<BaseField>> freeActionFieldForPage(String baseSchema, List<String> baseStatus, List<String> actionName, List<String> stepNo)
	{
		Map<String, List<BaseField>> map = new HashMap<String, List<BaseField>>();
		for (int i = 0; i < baseStatus.size(); i++)
		{
			List<FreeActionFieldRelationModel> freeActionFieldRelationList = hibernateDao.find("from FreeActionFieldRelationModel where baseSchema=? and baseStatus=? and actionType=?", new String[] { baseSchema, baseStatus.get(i), actionName.get(i) });
			List<BaseField> baseFieldList = new ArrayList<BaseField>();
			for (FreeActionFieldRelationModel freeActionFieldRelationModel : freeActionFieldRelationList)
			{
				String fieldType = freeActionFieldRelationModel.getFieldType();
				String fieldID = freeActionFieldRelationModel.getFieldID();
				BaseField basField = (BaseField) hibernateDao.findUnique("from " + fieldType + " where id=?", new String[] { fieldID });
				baseFieldList.add(basField);
			}

			// 节点排序
			for (int k = 0; k < baseFieldList.size() - 1; k++)
			{
				for (int j = 1; j < baseFieldList.size() - k; j++)
				{
					BaseField baseFieldTmp;

					if (baseFieldList.get(j - 1).getOrderNum() > baseFieldList.get(j).getOrderNum())
					{
						baseFieldTmp = baseFieldList.get(j - 1);
						baseFieldList.set((j - 1), baseFieldList.get(j));
						baseFieldList.set(j, baseFieldTmp);
					}
				}
			}

			if (CollectionUtils.isNotEmpty(stepNo) && stepNo.get(i) != null && stepNo.get(i).length() > 0)
			{
				map.put(actionName.get(i) + "_" + stepNo.get(i), baseFieldList);
			} else
			{
				map.put(actionName.get(i), baseFieldList);
			}

		}
		return map;
	}

	public Map<String, List<BaseField>> actionFieldChangeMap(String baseSchema, String stepNo, String actionName)
	{
		/*
		 * String sql = " baseSchema=? and hasDeploy=0 and parentID='action' ";
		 * this.loadFieldData(baseSchema, sql, new String[] { baseSchema }); Map<String,
		 * List<BaseField>> map = this.getFieldChangeMap();
		 */
		Map<String, List<BaseField>> map = new HashMap<String, List<BaseField>>();

		List<BaseField> addList = new ArrayList<BaseField>();
		List<BaseField> updateList = new ArrayList<BaseField>();
		List<BaseField> deleList = new ArrayList<BaseField>();

		String sqlString = "from ActionFieldRelationModel where baseSchema=? and stepNo=? and actionName=? and hasDeploy=0 ";
		List<ActionFieldRelationModel> actionFieldRlList = hibernateDao.find(sqlString, new String[] { baseSchema, stepNo, actionName });
		for (ActionFieldRelationModel fileModel : actionFieldRlList)
		{
			BaseField field = (BaseField) hibernateDao.findUnique(" from " + fileModel.getFieldType() + " where baseSchema=? and id=?", new String[] { baseSchema, fileModel.getFieldID() });
			if ("add".equals(field.getOperate()))
			{
				addList.add(field);
			}
			if ("update".equals(field.getOperate()))
			{
				updateList.add(field);
			}
			if ("delete".equals(field.getOperate()))
			{
				deleList.add(field);
			}
		}
		map.put("add", addList);
		map.put("update", updateList);
		map.put("delete", deleList);
		return map;
	}

	public Map<String, List<BaseField>> actionFieldChangeFreeMap(String baseSchema, String baseStatus, String actionType)
	{
		Map<String, List<BaseField>> map = new HashMap<String, List<BaseField>>();

		List<BaseField> addList = new ArrayList<BaseField>();
		List<BaseField> updateList = new ArrayList<BaseField>();
		List<BaseField> deleList = new ArrayList<BaseField>();

		String sqlString = "from FreeActionFieldRelationModel where baseSchema=? and baseStatus=? and actionType=? and hasDeploy=0 ";
		List<FreeActionFieldRelationModel> actionFieldRlList = hibernateDao.find(sqlString, new String[] { baseSchema, baseStatus, actionType });
		for (FreeActionFieldRelationModel fileModel : actionFieldRlList)
		{
			BaseField field = (BaseField) hibernateDao.findUnique(" from " + fileModel.getFieldType() + " where baseSchema=? and id=?", new String[] { baseSchema, fileModel.getFieldID() });
			if ("add".equals(field.getOperate()))
			{
				addList.add(field);
			}
			if ("update".equals(field.getOperate()))
			{
				updateList.add(field);
			}
			if ("delete".equals(field.getOperate()))
			{
				deleList.add(field);
			}
		}
		map.put("add", addList);
		map.put("update", updateList);
		map.put("delete", deleList);
		return map;
	}

	/*
	 * public void actionFieldChangeUpdate(String baseSchema, String stepNo,
	 * String actionName) { // 如果字段是动作上的字段，需要根据工单类型，操作不同的关联表。 List<ActionFieldRelationModel>
	 * fields = hibernateDao.find("from ActionFieldRelationModel where
	 * baseSchema=? and stepNo=? and actionName=?", new String[] { baseSchema,
	 * stepNo, actionName }); ActionModel actionModel = (ActionModel)
	 * hibernateDao.findUnique("from ActionModel where baseSchema=? and stepNo=?
	 * and actionName=?", new String[] { baseSchema, stepNo, actionName }); for
	 * (ActionFieldRelationModel field : fields) { field.setHasDeploy(1);
	 * field.setOperate("add"); hibernateDao.saveOrUpdate(field); }
	 * actionModel.setHasDeploy(1); actionModel.setOperate("add");
	 * hibernateDao.saveOrUpdate(actionModel); }
	 */

	/**
	 * 没用到
	 * 
	 * @param baseSchema
	 * @param status
	 * @param actionType
	 */
	/*
	 * public void actionFieldChangeUpdateFree(String baseSchema, String status,
	 * String actionType) { List<FreeActionFieldRelationModel> fields =
	 * hibernateDao.find("from FreeActionFieldRelationModel where baseSchema=?
	 * and baseStatus=? and actionType=? ", new String[] { baseSchema, status,
	 * actionType }); for (FreeActionFieldRelationModel field : fields) {
	 * BaseField baseField = (BaseField) hibernateDao.findUnique("from " +
	 * field.getFieldType() + " where baseSchema=? and id=?", new String[] {
	 * field.getBaseSchema(), field.getFieldID() }); if (field.getHasDeploy() ==
	 * 0 && "delete".equals(field.getOperate())) { hibernateDao.remove(field);
	 * hibernateDao.remove(baseField); } else { field.setHasDeploy(1);
	 * field.setOperate("add"); hibernateDao.saveOrUpdate(field);
	 * baseField.setHasDeploy(1); baseField.setOperate("add");
	 * hibernateDao.saveOrUpdate(baseField); } } }
	 */

	public void delByBaseSchema(String baseSchema)
	{
		log.info("删除工单类型为" + baseSchema + "的对应字段，开始>>>>>>>>>>");

		String sql = " baseSchema=? ";

		this.loadFieldData(baseSchema, sql, new String[] { baseSchema });

		Map<String, FieldConfig> fieldInfoMap = (Map<String, FieldConfig>) BaseCacheManager.get(FIELDINFO_CACHEKEY, FIELDINFO_CACHEKEY);
		try
		{
			Set<String> keySet = fieldInfoMap.keySet();
			for (String key : keySet)
			{
				FieldConfig fieldConfig = fieldInfoMap.get(key);
				Method setFieldMethod = this.getClass().getMethod("get" + fieldConfig.getName() + "s");
				List<BaseField> fieldList = (List<BaseField>) setFieldMethod.invoke(this);
				for (BaseField baseField : fieldList)
				{
					hibernateDao.remove(baseField);
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		log.info("删除工单类型为" + baseSchema + "的对应字段，结束<<<<<<<<<<<");

		log.info("删除工单类型为" + baseSchema + "的环节信息，包括所有环节字段关联关系");

		List<StepModel> stepList = hibernateDao.find("from StepModel where baseSchema=?", new String[] { baseSchema });
		for (StepModel stepModel : stepList)
		{
			hibernateDao.remove(stepModel);
		}

		List<StepFieldRelationModel> stepRlList = hibernateDao.find("from StepFieldRelationModel where baseSchema=?", new String[] { baseSchema });
		for (StepFieldRelationModel stepRlModel : stepRlList)
		{
			hibernateDao.remove(stepRlModel);
		}

		log.info("删除工单类型为" + baseSchema + "的动作信息，包括所有动作字段关联关系");

		List<ActionModel> actionList = hibernateDao.find("from ActionModel where baseSchema=? order by orderId", new String[] { baseSchema });
		for (ActionModel actionModel : actionList)
		{
			hibernateDao.remove(actionModel);
		}

		List<ActionFieldRelationModel> actionRLList = hibernateDao.find("from ActionFieldRelationModel where baseSchema=?", new String[] { baseSchema });
		for (ActionFieldRelationModel actionRLModel : actionRLList)
		{
			hibernateDao.remove(actionRLModel);
		}

		log.info("删除工单类型为" + baseSchema + "的状态动作字段关联关系");

		List<FreeActionFieldRelationModel> freeRLList = hibernateDao.find("from FreeActionFieldRelationModel where baseSchema=?", new String[] { baseSchema });
		for (FreeActionFieldRelationModel freeRLModel : freeRLList)
		{
			hibernateDao.remove(freeRLModel);
		}

		log.info("删除工单类型为" + baseSchema + "的相关字段成功！！！");

	}

	public List<FieldConfig> getFieldInfoList()
	{
		List<FieldConfig> list = new ArrayList<FieldConfig>();
		Map<String, FieldConfig> fieldInfoMap = (Map<String, FieldConfig>) BaseCacheManager.get(FIELDINFO_CACHEKEY, FIELDINFO_CACHEKEY);
		Set<String> keySet = fieldInfoMap.keySet();
		try
		{
			for (String key : keySet)
			{
				FieldConfig fieldConfig = fieldInfoMap.get(key);
				list.add(fieldConfig);
			}

			// 节点排序
			for (int k = 0; k < list.size() - 1; k++)
			{
				for (int j = 1; j < list.size() - k; j++)
				{
					FieldConfig fieldConfig;

					if (Integer.parseInt(list.get(j - 1).getOrderNum()) > Integer.parseInt(list.get(j).getOrderNum()))
					{
						fieldConfig = list.get(j - 1);
						list.set((j - 1), list.get(j));
						list.set(j, fieldConfig);
					}
				}
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	private void loadFieldData(String baseSchema, String sql, String[] values)
	{
		log.info("查询表单字段数据 baseSchema:"+baseSchema);
		log.info("sql:"+sql);
		fieldDataLoadFlag = false;
		IWfSortManager sortService = WorkFlowServiceClient.clientInstance().getSortService();
	    try
		{
			wfType = sortService.getWfTypeByCode(baseSchema);
		} catch (RemoteException e1)
		{
			e1.printStackTrace();
		}
		Map<String, FieldConfig> fieldInfoMap = (Map<String, FieldConfig>) BaseCacheManager.get(FIELDINFO_CACHEKEY, FIELDINFO_CACHEKEY);

		try
		{
			Set<String> keySet = fieldInfoMap.keySet();
			for (String key : keySet)
			{
				FieldConfig fieldConfig = fieldInfoMap.get(key);
				Method setFieldMethod = this.getClass().getMethod("set" + fieldConfig.getName() + "s", new Class<?>[] { Class.forName("java.util.List") });
				List<?> fieldList = null;
				fieldList = hibernateDao.find("from " + fieldConfig.getName() + " where " + sql, values);
				setFieldMethod.invoke(this, fieldList);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		log.info("所有字段加载成功！");

		fieldDataLoadFlag = true;

	}

	/*
	 * 环节以及环节下所有动作列表
	 */
	public WorksheetMeta getWorksheetMeta(String baseSchema)
	{
		WorksheetMeta worksheetMeta = new WorksheetMeta();
		List<StepModel> steps = hibernateDao.find("from StepModel where baseSchema=? order by stepNo", new String[] { baseSchema });

		List<StepMeta> stepMetas = new ArrayList<StepMeta>();

		for (StepModel stepModel : steps)
		{
			StepMeta stepMeta = new StepMeta();

			stepMeta.setStepModel(stepModel);

			List<ActionModel> actions = hibernateDao.find("from ActionModel where baseSchema=? and stepNo=? order by orderId", new String[] { baseSchema, stepModel.getStepNo() });

			List<ActionMeta> list = new ArrayList<ActionMeta>();

			for (ActionModel actionModel : actions)
			{

				ActionMeta actionMeta = new ActionMeta();

				actionMeta.setActionModel(actionModel);

				list.add(actionMeta);
			}

			stepMeta.setActions(list);

			stepMetas.add(stepMeta);
		}

		worksheetMeta.setSteps(stepMetas);

		return worksheetMeta;
	}

	public List<Object[]> getStatusAndActionType(String baseSchema)
	{
		List<Object[]> statusAndFreeActionList = hibernateDao.find("select baseStatus,actionType from FreeActionFieldRelationModel where baseSchema=? and actionType is not null group by baseStatus,actionType order by actionType", new String[] { baseSchema });
		List<Object[]> list = new ArrayList<Object[]>();
		Map<String, ActionModel> map = (Map<String, ActionModel>) BaseCacheManager.get(FREEACTION_CACHEKEY, FREEACTION_CACHEKEY);
		for (Object[] object : statusAndFreeActionList)
		{
			ActionModel actionModel = map.get(object[1]);
			if (actionModel != null && actionModel.getHasNext() == 1)
			{
				list.add(object);
			}
		}
		return list;
	}

	private Map<String, List<BaseField>> getFieldChangeMap()
	{
		List<BaseField> addList = new ArrayList<BaseField>();
		List<BaseField> updateList = new ArrayList<BaseField>();
		List<BaseField> deleList = new ArrayList<BaseField>();

		Map<String, FieldConfig> fieldInfoMap = (Map<String, FieldConfig>) BaseCacheManager.get(FIELDINFO_CACHEKEY, FIELDINFO_CACHEKEY);

		Set<String> keySet = fieldInfoMap.keySet();

		try
		{
			for (String key : keySet)
			{
				FieldConfig fieldConfig = fieldInfoMap.get(key);
				List<BaseField> fieldList = (List<BaseField>) this.getClass().getMethod("get" + fieldConfig.getName() + "s").invoke(this);
				for (BaseField baseField : fieldList)
				{
					String operate = "";

					Method method = null;
					method = Class.forName(fieldConfig.getFullName()).getMethod("getOperate");
					operate = (String) method.invoke(baseField);

					if ("add".equals(operate))
					{
						addList.add(baseField);
					}
					if ("update".equals(operate))
					{
						updateList.add(baseField);
					}
					if ("delete".equals(operate))
					{
						deleList.add(baseField);
					}

				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		Map<String, List<BaseField>> map = new HashMap<String, List<BaseField>>();
		map.put("add", addList);
		map.put("update", updateList);
		map.put("delete", deleList);

		return map;
	}

	public IDao getHibernateDao()
	{
		return hibernateDao;
	}

	public void setHibernateDao(IDao hibernateDao)
	{
		this.hibernateDao = hibernateDao;
	}

	public List<ButtonField> getButtonFields()
	{
		return buttonFields;
	}

	public void setButtonFields(List<ButtonField> buttonFields)
	{
		this.buttonFields = buttonFields;
	}

	public MetaCacheService getMetaCacheService()
	{
		return metaCacheService;
	}

	public void setMetaCacheService(MetaCacheService metaCacheService)
	{
		this.metaCacheService = metaCacheService;
	}

	public List<PanelGroupField> getPanelGroupFields()
	{
		return panelGroupFields;
	}

	public void setPanelGroupFields(List<PanelGroupField> panelGroupFields)
	{
		this.panelGroupFields = panelGroupFields;
	}

	public List<PanelField> getPanelFields()
	{
		return panelFields;
	}

	public void setPanelFields(List<PanelField> panelFields)
	{
		this.panelFields = panelFields;
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

	public List<TimeField> getTimeFields()
	{
		return timeFields;
	}

	public void setTimeFields(List<TimeField> timeFields)
	{
		this.timeFields = timeFields;
	}

	public List<BlankField> getBlankFields()
	{
		return blankFields;
	}

	public void setBlankFields(List<BlankField> blankFields)
	{
		this.blankFields = blankFields;
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

	public List<RollbackListField> getRollbackListFields()
	{
		return rollbackListFields;
	}

	public void setRollbackListFields(List<RollbackListField> rollbackListFields)
	{
		this.rollbackListFields = rollbackListFields;
	}

	public boolean isFieldDataLoadFlag()
	{
		return fieldDataLoadFlag;
	}

	public void setFieldDataLoadFlag(boolean fieldDataLoadFlag)
	{
		this.fieldDataLoadFlag = fieldDataLoadFlag;
	}

	public List<HiddenField> getHiddenFields()
	{
		return hiddenFields;
	}

	public void setHiddenFields(List<HiddenField> hiddenFields)
	{
		this.hiddenFields = hiddenFields;
	}

	public List<CollectField> getCollectFields()
	{
		return collectFields;
	}

	public void setCollectFields(List<CollectField> collectFields)
	{
		this.collectFields = collectFields;
	}
	
	

}
