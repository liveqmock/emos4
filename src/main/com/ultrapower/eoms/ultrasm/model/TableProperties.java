package com.ultrapower.eoms.ultrasm.model;

import com.ultrapower.eoms.common.core.component.data.DataTable;

public class TableProperties
{
	private String excelName;//EXCEL名称
	private int titleRow;//标题行数
	private int titleCol;//标题列数
	private String groupField;//分组字段,作为导出EXCEL时分成多个sheet的标识 格式:  字段1,字段2;列数1,列数2 如:  type,depname;4,2
	private int sheetRow;//每个sheet行数
	private String colMerge;//分组合并列 存储方式:1-3 即:从第一列分组合并到第三列
	private DataTable titleTable;
	
	public String getExcelName() {
		return excelName;
	}
	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}
	public int getTitleRow() {
		return titleRow;
	}
	public void setTitleRow(int titleRow) {
		this.titleRow = titleRow;
	}
	public int getTitleCol() {
		return titleCol;
	}
	public void setTitleCol(int titleCol) {
		this.titleCol = titleCol;
	}
	public String getGroupField() {
		return groupField;
	}
	public void setGroupField(String groupField) {
		this.groupField = groupField;
	}
	public int getSheetRow() {
		return sheetRow;
	}
	public void setSheetRow(int sheetRow) {
		this.sheetRow = sheetRow;
	}
	public String getColMerge() {
		return colMerge;
	}
	public void setColMerge(String colMerge) {
		this.colMerge = colMerge;
	}
	public DataTable getTitleTable() {
		return titleTable;
	}
	public void setTitleTable(DataTable titleTable) {
		this.titleTable = titleTable;
	}
}
