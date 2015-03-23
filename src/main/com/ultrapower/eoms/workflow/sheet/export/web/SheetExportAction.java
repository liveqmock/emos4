package com.ultrapower.eoms.workflow.sheet.export.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.ultrapower.eoms.common.core.web.BaseAction;
import com.ultrapower.eoms.common.portal.model.UserSession;
import com.ultrapower.eoms.workflow.sheet.export.service.IsheetExportService;
import com.ultrapower.workflow.configuration.sort.model.WfType;

/**
 * 工单导出（Web层action）
 * 
 * @version UltraPower-Eoms4.0-0.1
 * @author：zhuboxing
 * @since jdk1.5
 * @date 2012-12-17 当前版本：v1.0 流程版本管理 web Copyright (c) 2012 神州泰岳服务管理事业部产品组
 */
public class SheetExportAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	protected IsheetExportService sheetExportServiceImpl;

	/**
	 * 待办工单Excel导出
	 */
	public String sheetExport() {
		String type = this.getRequest().getParameter("type");
		String isAll = this.getRequest().getParameter("isAll");
		String wfSortId = this.getRequest().getParameter("wfSortId");
		String wfTypeStr = setWfTypeStr(wfSortId);
		String baseSchema = this.getRequest().getParameter("baseSchema");
		String excel = sheetExportServiceImpl.exportExcel(type, this.getRequest(), isAll, wfTypeStr, baseSchema);
		try {
			sheetExportServiceImpl.downLoad(excel, this.getResponse(), false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected String setWfTypeStr(String wfSortId) {
		UserSession userSession = getUserSession();
		List<WfType> wfTypes = userSession.getWfTypes();
		String wfTypeStr = "";
		if (CollectionUtils.isNotEmpty(wfTypes) && StringUtils.isNotBlank(wfSortId)) {
			for (int i = 0; i < wfTypes.size(); i++) {
				WfType wfType = wfTypes.get(i);
				String sortId = wfType.getSortId();
				String code = wfType.getCode();
				if (wfSortId.equals(sortId)) {
					wfTypeStr += code + ",";
				}
			}
			if (StringUtils.isNotBlank(wfTypeStr)) {
				wfTypeStr = wfTypeStr.substring(0, wfTypeStr.length() - 1);
			}
		}
		return wfTypeStr;
	}
	public IsheetExportService getSheetExportServiceImpl() {
		return sheetExportServiceImpl;
	}

	public void setSheetExportServiceImpl(
			IsheetExportService sheetExportServiceImpl) {
		this.sheetExportServiceImpl = sheetExportServiceImpl;
	}

}
