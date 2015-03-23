package com.ultrapower.eoms.workflow.sheet.agent.web;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.workflow.bizservice.AgencyService;
import com.ultrapower.workflow.bizservice.model.Agency;

public class AgencyAction extends BaseAction{
	
	private Agency agency;
	
	private AgencyService agencyService;
	
	private String delIds;
	
	private List<Agency> agencys;
	
	public String add() {
		return findForward("add");
	}
	
	public String userAdd() {
		return findForward("userAdd");
	}
	
	public String edit() {
		String id = this.getRequest().getParameter("id");
		agency = agencyService.getById(id);
		return findForward("add");
	}
	
	public String userEdit() {
		String id = this.getRequest().getParameter("id");
		agency = agencyService.getById(id);
		return findForward("userAdd");
	}
	
	public String save() {
		agencyService.saveOrUpdate(agency);
		return "result";
	}
	
	public String userSave() {
		UserSession userSession = getUserSession();
		String loginName = userSession.getLoginName();
		String fullName = userSession.getFullName();
		agency.setDealer(fullName);
		agency.setDealerId(loginName);
		agencyService.saveOrUpdate(agency);
		return "result";
	}
	
	public String del() {
		if (StringUtils.isNotBlank(delIds)) {
			String[] ids = delIds.split(",");
			if (!ArrayUtils.isEmpty(ids)) {
				for (int i = 0; i < ids.length; i++) {
					String id = ids[i];
					if (StringUtils.isNotBlank(id)) {
						agencyService.deleteById(id);
					}
				}
			}
		}
		return "result";
	}
	
	public String list() {
		agencys = agencyService.getAllByPage();
		return findForward("list");
	}
	
	public String userList() {
		UserSession userSession = getUserSession();
		agencys = agencyService.getByUser(userSession.getLoginName());
		return findForward("userList");
	}
	
	public AgencyService getAgencyService() {
		return agencyService;
	}

	public void setAgencyService(AgencyService agencyService) {
		this.agencyService = agencyService;
	}

	public Agency getAgency() {
		return agency;
	}

	public void setAgency(Agency agency) {
		this.agency = agency;
	}

	public String getDelIds() {
		return delIds;
	}

	public void setDelIds(String delIds) {
		this.delIds = delIds;
	}

	public List<Agency> getAgencys() {
		return agencys;
	}

	public void setAgencys(List<Agency> agencys) {
		this.agencys = agencys;
	}

}
