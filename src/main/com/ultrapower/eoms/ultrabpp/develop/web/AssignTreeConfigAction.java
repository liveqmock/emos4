package com.ultrapower.eoms.ultrabpp.develop.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.Flags.Flag;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.component.tree.manager.FormTypeTreeImpl;
import com.ultrapower.eoms.common.core.component.tree.manager.UserDepTreeImpl;
import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.web.ActionContext;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.ultrabpp.cache.model.ActionFieldRelationModel;
import com.ultrapower.eoms.ultrabpp.cache.model.AssignTreeConfigModel;
import com.ultrapower.eoms.ultrabpp.cache.model.AssignTreeOrganizeModel;
import com.ultrapower.eoms.ultrabpp.cache.model.StepModel;
import com.ultrapower.eoms.ultrabpp.develop.manager.TemplateManagerImpl;
import com.ultrapower.eoms.ultrabpp.develop.model.TreeNode;
import com.ultrapower.eoms.ultrabpp.develop.service.AssignTreeConfigService;
import com.ultrapower.eoms.ultrabpp.develop.service.TemplateService;
import com.ultrapower.eoms.ultrabpp.model.BaseField;
import com.ultrapower.eoms.ultrabpp.model.component.specialfield.assigntreefield.AssignTreeField;
import com.ultrapower.eoms.ultrasm.model.DepInfo;
import com.ultrapower.eoms.ultrasm.model.FormCustSenderDelPara;
import com.ultrapower.eoms.ultrasm.service.FormCustSendTreeService;
import com.ultrapower.workflow.utils.UUIDGenerator;

public class AssignTreeConfigAction extends BaseAction
{
	//派发树配置列表
	private List<AssignTreeConfigModel> configList;
	private List<AssignTreeOrganizeModel> orginizeList;
	private AssignTreeConfigModel configModel;
	private Map<String, String> configMap;
	private String configOptions;
	private String stepDesc;
	private String actionLabel;
	private String fieldName;
	private String fieldLabel;
	private String selectType;
	private String tabShow;
	private String organizeInfo;
	private String configID;
	private String depids;
	private String userids;
	

	private String baseSchema;
	private String wftype;
	private IDao hibernateDao;
	private List<StepModel> stepList;
	private String stepNo;
	private String actionName;

	private UserDepTreeImpl usermenuDtree;
	private FormCustSendTreeService formCustSendTreeService; 
	private AssignTreeConfigService assignTreeConfigService;
	
	private TemplateService templateService;

	public String assigntreeConfigList()
	{
		if (baseSchema == null || baseSchema.length() == 0)
		{
			baseSchema = StringUtils.checkNullString(this.getRequest().getParameter("baseSchema"));
		}
		if (!"".equals(baseSchema))
		{
			configList = (List<AssignTreeConfigModel>) hibernateDao.find("from AssignTreeConfigModel where baseSchema=? order by stepNo,actionName,fieldName", new String[] { baseSchema });
		}
		return SUCCESS;
	}

	public String configTreeEdit()
	{
		if (configID == null || configID.length() == 0)
		{
			if("1".equals(wftype)){
				stepList = hibernateDao.find("from StepModel where baseSchema=? order by stepNo", baseSchema);
				configOptions = "<select id='stepList' name='stepList' onchange='selectStep()'><option value='0'>请选择环节</option>";
				for (StepModel step : stepList)
				{
					configOptions += "<option value='" + step.getStepNo() + "'>" + step.getDescription() + "</option>";
				}
				configOptions += "</select>";
			}else{
				List<AssignTreeField> assigntreeList  = hibernateDao.find("from AssignTreeField where baseSchema=? ", new String[] {baseSchema});
				if (assigntreeList == null)
					assigntreeList = new ArrayList<AssignTreeField>();
				configOptions = "<select id='fieldList' name='fieldList' ><option value='0'>请选择派发树</option>";
				for (AssignTreeField field : assigntreeList)
				{
					configOptions+="<option value='" + field.getFieldName() + "'>" + field.getLabel() + "</option>";
				}
				configOptions +="</select>";
			}
		}
		else
		{
			AssignTreeConfigModel model = (AssignTreeConfigModel) hibernateDao.findUnique("from AssignTreeConfigModel where id=?", configID);
			this.baseSchema = model.getBaseSchema();
			this.stepNo = model.getStepNo();
			this.stepDesc = model.getStepDesc();
			this.actionName = model.getActionName();
			this.actionLabel = model.getActionLabel();
			this.fieldName = model.getFieldName();
			this.fieldLabel = model.getFieldLabel();
			this.selectType = model.getSelectType();
			this.tabShow = model.getTabShow();
		}
		return SUCCESS;
	}

	/**
	 * 根据流程标识，获得流程的环节号
	 */
	public void getStepName(){
		String steps = "";
		stepList = hibernateDao.find("from StepModel where baseSchema=? order by stepNo", baseSchema);
		for (StepModel step : stepList){
			steps += step.getStepNo() + ","+step.getDescription()+";";
		}
		this.renderText(steps);
	}
	
	/**
	 * 获得某一个流程所对应的主工单字段
	 */
	public void mainFieldList()
	{
		String fieldName = "";
		if(baseSchema != null && !"".equals(baseSchema)){
			List<BaseField> list = templateService.mainFieldList(baseSchema);
			for(BaseField field : list){
				fieldName += field.getFieldName()+","+field.getLabel()+"("+field.getFieldName()+")"+";";
			}
			fieldName = fieldName.substring(0,fieldName.length()-1);
		}
		this.renderText(fieldName);
	}
	
	public String saveAssignTreeConfig()
	{
		log.info("保存派发树配置信息。");
		if (organizeInfo != null && organizeInfo.length() > 0)
		{
			if (configID == null || configID.length() == 0)
			{
				String configIDTemp = UUIDGenerator.getId();
				AssignTreeConfigModel configModel = new AssignTreeConfigModel();
				configModel.setId(configIDTemp);
				configModel.setStepNo(stepNo);
				configModel.setStepDesc(stepDesc);
				configModel.setActionName(actionName);
				configModel.setActionLabel(actionLabel);
				configModel.setFieldLabel(fieldLabel);
				configModel.setFieldName(fieldName);
				configModel.setBaseSchema(baseSchema);
				configModel.setSelectType(selectType);
				configModel.setTabShow(tabShow);
				hibernateDao.save(configModel);
				assignTreeConfigService.saveChildren(organizeInfo,configIDTemp);
				/*
				String[] organizeInfoArray = organizeInfo.split(";");
				for (String organizeInfo : organizeInfoArray)
				{
					String[] info = organizeInfo.split(":");
					String orgType = info[0];
					String orgID = info[1];
					String parentOrgID = info[2];
					AssignTreeOrganizeModel organizeModel = new AssignTreeOrganizeModel();
					organizeModel.setId(UUIDGenerator.getId());
					organizeModel.setConfigid(configIDTemp);
					organizeModel.setOrganizetype(orgType.equals("U") ? 2 : 1);
					organizeModel.setOrganizeid(orgID);
					organizeModel.setParentorgid(parentOrgID);
					hibernateDao.save(organizeModel);
				}
				*/
			}else{
				AssignTreeConfigModel configModel = (AssignTreeConfigModel) hibernateDao.findUnique("from AssignTreeConfigModel where id=? ", new String[] {configID});
				List<AssignTreeOrganizeModel> orginizeList = hibernateDao.find("from AssignTreeOrganizeModel where configid=? ", configModel.getId());
				for (AssignTreeOrganizeModel organize : orginizeList)
				{
					String infoString =(organize.getOrganizetype()==2?"U":"D")+":"+organize.getOrganizeid()+":"+organize.getParentorgid();
					if(organizeInfo.contains(infoString)){
						organizeInfo = organizeInfo.substring(0, organizeInfo.indexOf(infoString))+organizeInfo.subSequence(organizeInfo.indexOf(infoString)+infoString.length(), organizeInfo.length());
					}
				}
				assignTreeConfigService.saveChildren(organizeInfo,configModel.getId());
				/*
				String[] organizeInfoArray = organizeInfo.split(";");
				for (String organizeInfo : organizeInfoArray)
				{
					if(organizeInfo.length()==0)continue;
					String[] info = organizeInfo.split(":");
					String orgType = info[0];
					String orgID = info[1];
					String parentOrgID = info[2];
					AssignTreeOrganizeModel organizeModel = new AssignTreeOrganizeModel();
					organizeModel.setId(UUIDGenerator.getId());
					organizeModel.setConfigid(configModel.getId());
					organizeModel.setOrganizetype(orgType.equals("U") ? 2 : 1);
					organizeModel.setOrganizeid(orgID);
					organizeModel.setParentorgid(parentOrgID);
					hibernateDao.save(organizeModel);
				}
				*/
			}
			
		}

		return "refresh";
	}

	public void checkUniqueConfig() throws IOException
	{
		String stepNo = StringUtils.checkNullString(this.getRequest().getParameter("stepNo"));
		String actionName = StringUtils.checkNullString(this.getRequest().getParameter("actionName"));
		String fieldName = StringUtils.checkNullString(this.getRequest().getParameter("fieldName"));
		String configID = StringUtils.checkNullString(this.getRequest().getParameter("configID"));
		String baseSchema = StringUtils.checkNullString(this.getRequest().getParameter("baseSchema"));
		boolean result = true;
		String sql = "from AssignTreeConfigModel where stepNo=? and actionName=? and fieldName=?";
		if(!"".equals(StringUtils.checkNullString(baseSchema))){
			sql +=" and baseSchema = '"+baseSchema+"'";
		}
		AssignTreeConfigModel configModel = (AssignTreeConfigModel) hibernateDao.findUnique(sql, new String[] {stepNo,actionName,fieldName});
		if(configModel!=null&&!configID.equals(configModel.getId())){
			result =  false;
		}
		PrintWriter out = this.getResponse().getWriter();
		out.print(result);
	}
	
	public String delConfigTree()
	{
		log.info("删除派发树配置信息。");
		AssignTreeConfigModel configModel = (AssignTreeConfigModel) hibernateDao.findUnique("from AssignTreeConfigModel where id=? ", new String[] { configID });
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
		return this.findRedirectPar("assigntreeConfigList.action?baseSchema=" + baseSchema+"&wftype="+wftype);
	}
	
	/**
	 * 删除工单配发树
	 * @return
	 */
	public String delAssignTreeNode()
	{
		//String custSenderPid = StringUtils.checkNullString(getRequest().getParameter("configID"));
		//String depids = StringUtils.checkNullString(getRequest().getParameter("depids")).trim();
		//String userids = StringUtils.checkNullString(getRequest().getParameter("userids")).trim();
		if(!"".equals(configID))
		{
			List<FormCustSenderDelPara> delList = new ArrayList<FormCustSenderDelPara>();
			if(!"".equals(depids))
			{
				String[] depidArr = depids.split(",");
				FormCustSenderDelPara temp;
				for(String str:depidArr)
				{
					//fanying 2013-4-24 解决自定义派发树同一个人员出现两次的问题
					String[] idArray = str.split(":");
					temp = new FormCustSenderDelPara();
					temp.setCustSenderPid(configID);
					temp.setOrginzedpid(idArray[0]);
					temp.setParentid(idArray[1]);
					temp.setOrgtype("1");
					delList.add(temp);
				}
			}
			if(!"".equals(userids))
			{
				String[] useridArr = userids.split(",");
				FormCustSenderDelPara temp;
				for(String str:useridArr)
				{
					//fanying 2013-4-24 解决自定义派发树同一个人员出现两次的问题
					String[] idArray = str.split(":");
					temp = new FormCustSenderDelPara();
					temp.setCustSenderPid(configID);
					temp.setOrginzedpid(idArray[0]);
					temp.setParentid(idArray[1]);
					temp.setOrgtype("2");
					delList.add(temp);
				}
			}
			formCustSendTreeService.delAssignTreeOrganized(delList);
		}
		return this.findRedirectPar("loadFromCustSendTree.action?configID="+configID);
	} 
	

	public String formSendTreeFrame()
	{
		return "success";
	}

	public void getOrderTypeTree()
	{
		FormTypeTreeImpl otti = new FormTypeTreeImpl();
		this.renderXML(otti.getTreeXml());
	}

	/**
	 * 根据工单类别加载对应派发树
	 * 
	 * @return
	 */
	public String loadFromCustSendTree()
	{
		configModel = (AssignTreeConfigModel) hibernateDao.findUnique("from AssignTreeConfigModel where id=?", new String[] { configID });
		if (configModel != null)
		{
			orginizeList = hibernateDao.find("from AssignTreeOrganizeModel where configid=? ", configModel.getId());
		}
		return this.findForward("formSendTree_right");
	}
	public String saveConfigInfo(){
		hibernateDao.saveOrUpdate(configModel);
		return "refresh";
	}
	public String configInfoEdit(){
		configModel = (AssignTreeConfigModel) hibernateDao.findUnique("from AssignTreeConfigModel where id=?", new String[] { configID });
		return "success";
	}
	/**
	 * 验证流程标识树中，点击的是否为子节点
	 */
	public  void validateSheet(){
		String flag = "false";
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from BS_T_WF_SORT t");
		sql.append(" where t.code = '"+baseSchema+"'");
		QueryAdapter query = new QueryAdapter();
		DataTable table = query.executeQuery(sql.toString());
		if(table.length() > 0){
			flag = "true";
		}
		this.renderText(flag);
	}
	
	/**
	 * 配置派发树(展示)
	 */
	public void assignTreeOrgnized()
	{

		String isSelectType = StringUtils.checkNullString(this.getRequest().getParameter("isSelectType"));//0:部门  1:人员  2:默认
		String formCustSendTree = StringUtils.checkNullString(this.getRequest().getParameter("formCustSendTree"));//自定义工单派发树表id
		String returnXml = "";
		if (!formCustSendTree.equals(""))
		{//不为空,则查询自定义的树
			returnXml = usermenuDtree.getAssignTreeDepUserXml(id, isSelectType, formCustSendTree);
			this.renderXML(returnXml);
		}
		
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

	public List<AssignTreeOrganizeModel> getOrginizeList()
	{
		return orginizeList;
	}

	public void setOrginizeList(List<AssignTreeOrganizeModel> orginizeList)
	{
		this.orginizeList = orginizeList;
	}

	public AssignTreeConfigModel getConfigModel()
	{
		return configModel;
	}

	public void setConfigModel(AssignTreeConfigModel configModel)
	{
		this.configModel = configModel;
	}

	public String getBaseSchema()
	{
		return baseSchema;
	}

	public void setBaseSchema(String baseSchema)
	{
		this.baseSchema = baseSchema;
	}

	public IDao getHibernateDao()
	{
		return hibernateDao;
	}

	public void setHibernateDao(IDao hibernateDao)
	{
		this.hibernateDao = hibernateDao;
	}

	public List<StepModel> getStepList()
	{
		return stepList;
	}

	public void setStepList(List<StepModel> stepList)
	{
		this.stepList = stepList;
	}

	public String getStepNo()
	{
		return stepNo;
	}

	public void setStepNo(String stepNo)
	{
		this.stepNo = stepNo;
	}

	public String getActionName()
	{
		return actionName;
	}

	public void setActionName(String actionName)
	{
		this.actionName = actionName;
	}

	public void setUsermenuDtree(UserDepTreeImpl usermenuDtree)
	{
		this.usermenuDtree = usermenuDtree;
	}

	public String getDepids()
	{
		return depids;
	}

	public void setDepids(String depids)
	{
		this.depids = depids;
	}

	public String getUserids()
	{
		return userids;
	}

	public void setUserids(String userids)
	{
		this.userids = userids;
	}

	public FormCustSendTreeService getFormCustSendTreeService()
	{
		return formCustSendTreeService;
	}

	public void setFormCustSendTreeService(FormCustSendTreeService formCustSendTreeService)
	{
		this.formCustSendTreeService = formCustSendTreeService;
	}

	public UserDepTreeImpl getUsermenuDtree()
	{
		return usermenuDtree;
	}

	public String getWftype()
	{
		return wftype;
	}

	public void setWftype(String wftype)
	{
		this.wftype = wftype;
	}

	public String getSelectType()
	{
		return selectType;
	}

	public void setSelectType(String selectType)
	{
		this.selectType = selectType;
	}

	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}

	public String getTabShow()
	{
		return tabShow;
	}

	public void setTabShow(String tabShow)
	{
		this.tabShow = tabShow;
	}
	
	public AssignTreeConfigService getAssignTreeConfigService() {
		return assignTreeConfigService;
	}

	public void setAssignTreeConfigService(
			AssignTreeConfigService assignTreeConfigService) {
		this.assignTreeConfigService = assignTreeConfigService;
	}
	

}
