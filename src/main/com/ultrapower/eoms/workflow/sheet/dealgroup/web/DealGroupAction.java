package com.ultrapower.eoms.workflow.sheet.dealgroup.web;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.ultrasm.model.DealGroup;
import com.ultrapower.eoms.ultrasm.service.DealGroupService;

public class DealGroupAction extends BaseAction{
	
	private DealGroup dealGroup;
	
	private DealGroupService dealGroupService;
	
	private String delIds;
	
	private List<DealGroup> dealGroups;
	
	public String add() {
		return findForward("add");
	}
	
	public String edit() {
		String id = this.getRequest().getParameter("id");
		dealGroup = dealGroupService.getById(id);
		return findForward("add");
	}
	
	public String save() {
		dealGroupService.saveOrUpdate(dealGroup);
		return "result";
	}
	
	public String del() {
		if (StringUtils.isNotBlank(delIds)) {
			String[] ids = delIds.split(",");
			if (!ArrayUtils.isEmpty(ids)) {
				for (int i = 0; i < ids.length; i++) {
					String id = ids[i];
					if (StringUtils.isNotBlank(id)) {
						dealGroupService.deleteById(id);
					}
				}
			}
		}
		return "result";
	}
	
	public String list() {
		dealGroups = dealGroupService.getAll();
		return findForward("list");
	}

	public DealGroup getDealGroup() {
		return dealGroup;
	}

	public void setDealGroup(DealGroup dealGroup) {
		this.dealGroup = dealGroup;
	}

	public DealGroupService getDealGroupService() {
		return dealGroupService;
	}

	public void setDealGroupService(DealGroupService dealGroupService) {
		this.dealGroupService = dealGroupService;
	}

	public String getDelIds() {
		return delIds;
	}

	public void setDelIds(String delIds) {
		this.delIds = delIds;
	}

	public List<DealGroup> getDealGroups() {
		return dealGroups;
	}

	public void setDealGroups(List<DealGroup> dealGroups) {
		this.dealGroups = dealGroups;
	}
}
