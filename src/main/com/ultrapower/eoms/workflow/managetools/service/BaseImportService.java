package com.ultrapower.eoms.workflow.managetools.service;

import java.io.File;
import java.util.List;

import com.ultrapower.eoms.workflow.managetools.control.template.ImportTemplate;

public interface BaseImportService
{
	public void importBase(ImportTemplate template, List<List<String>> dataList);
}
