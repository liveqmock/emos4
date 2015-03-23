package com.ultrapower.eoms.workflow.sheet.ownfields.web;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.ultrasm.model.DicItem;
import com.ultrapower.eoms.ultrasm.service.DicManagerService;
import com.ultrapower.eoms.workflow.sheet.ownfields.model.OwnFields;
import com.ultrapower.eoms.workflow.sheet.ownfields.service.OwnFieldsService;
import com.ultrapower.workflow.client.WorkFlowServiceClient;
import com.ultrapower.workflow.configuration.sort.model.WfType;

public class OwnFieldsAction extends BaseAction{
	
	private OwnFields ownFields;
	
	private OwnFieldsService ownFieldsService;
	
	private String delIds;
	
	private List<OwnFields> ownFieldses;
	
	private List<WfType> wftypes;
	private List<DicItem> dicItems;
	
	private String baseSchema;
	private String actionType;
	private String stepCode;
	
	public OwnFieldsAction() {
		try {
			wftypes = WorkFlowServiceClient.clientInstance().getSortService().getAllWfType();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public String add() {
		ownFields = new OwnFields();
		ownFields.setBaseSchema(baseSchema);
		ownFields.setIsPrint(1);
		ownFields.setPrintOrder(0);
		ownFields.setFieldIsToLong(0);
		ownFields.setPrintOneLine(0);
		ownFields.setLogPosition(0);
		if (StringUtils.isNotBlank(stepCode)) {
			ownFields.setDefEditStep("#"+stepCode+"#");
		}
		if (StringUtils.isNotBlank(actionType)) {
			ownFields.setFreeEditStep("#"+actionType+"#");
		}
		return findForward("add");
	}
	
	public String edit() {
		String id = this.getRequest().getParameter("id");
		ownFields = ownFieldsService.getById(id);
		return findForward("add");
	}
	
	public String save() {
		String msg = "";
		String baseSchema = ownFields.getBaseSchema();
		String fieldCode = ownFields.getFieldCode();
		String id = ownFields.getId();
		OwnFields own = ownFieldsService.getOwnFieldbyCode(baseSchema, fieldCode);
		if (own != null) {
			String exitId = own.getId();
			if (StringUtils.isBlank(id)) {
				msg = fieldCode + " 字段标识已经存在！";
			} else if (StringUtils.isNotBlank(id) && !id.equals(exitId)) {
				msg = fieldCode + " 字段标识已经存在！";
			}
			if (StringUtils.isNotBlank(msg)) {
				getRequest().setAttribute("msg", msg);
				return findForward("add");
			}
		}
		ownFieldsService.saveOrUpdate(ownFields);
		return "result";
	}
	
	public String del() {
		if (StringUtils.isNotBlank(delIds)) {
			String[] ids = delIds.split(",");
			if (!ArrayUtils.isEmpty(ids)) {
				for (int i = 0; i < ids.length; i++) {
					String id = ids[i];
					if (StringUtils.isNotBlank(id)) {
						ownFieldsService.deleteById(id);
					}
				}
			}
		}
		return "result";
	}
	
	public String list() {
		DicManagerService dicManagerService  = (DicManagerService)WebApplicationManager.getBean("dicManagerService");
		dicItems = dicManagerService.getRootItsmByDicType("actionType");
		
		if (StringUtils.isBlank(baseSchema)) {
			if (CollectionUtils.isNotEmpty(wftypes)) {
				WfType wfType = wftypes.get(0);
				baseSchema = wfType.getCode();
			}
		}
		if (StringUtils.isNotBlank(baseSchema) && baseSchema.indexOf(",") > -1) {
			baseSchema = baseSchema.split(",")[0];
		}
		if (StringUtils.isNotBlank(stepCode) && stepCode.indexOf(",") > -1) {
			stepCode = stepCode.split(",")[0];
		}
		if (StringUtils.isNotBlank(actionType) && actionType.indexOf(",") > -1) {
			actionType = actionType.split(",")[0];
		}
		ownFieldses = ownFieldsService.getAll(baseSchema, stepCode, actionType);
		return findForward("list");
	}

	public String getDelIds() {
		return delIds;
	}

	public void setDelIds(String delIds) {
		this.delIds = delIds;
	}

	public OwnFields getOwnFields() {
		return ownFields;
	}

	public void setOwnFields(OwnFields ownFields) {
		this.ownFields = ownFields;
	}

	public OwnFieldsService getOwnFieldsService() {
		return ownFieldsService;
	}

	public void setOwnFieldsService(OwnFieldsService ownFieldsService) {
		this.ownFieldsService = ownFieldsService;
	}

	public List<OwnFields> getOwnFieldses() {
		return ownFieldses;
	}

	public void setOwnFieldses(List<OwnFields> ownFieldses) {
		this.ownFieldses = ownFieldses;
	}

	public List<WfType> getWftypes() {
		return wftypes;
	}

	public void setWftypes(List<WfType> wftypes) {
		this.wftypes = wftypes;
	}

	public String getBaseSchema() {
		return baseSchema;
	}

	public void setBaseSchema(String baseSchema) {
		this.baseSchema = baseSchema;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getStepCode() {
		return stepCode;
	}

	public void setStepCode(String stepCode) {
		this.stepCode = stepCode;
	}

	public List<DicItem> getDicItems() {
		return dicItems;
	}

	public void setDicItems(List<DicItem> dicItems) {
		this.dicItems = dicItems;
	}
}
