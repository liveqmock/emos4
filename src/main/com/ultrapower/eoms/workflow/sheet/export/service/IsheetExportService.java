package com.ultrapower.eoms.workflow.sheet.export.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IsheetExportService {
	public String exportExcel(String sqlName, HttpServletRequest request, String isAll, String wfTypeStr, String baseSchema);

	public void downLoad(String filePath, HttpServletResponse response,
			boolean isOnLine) throws IOException;
}
