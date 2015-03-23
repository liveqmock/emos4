package com.ultrapower.eoms.ultrasm.model;

public class ImpFieldConfig
{
	private String fieldName;//数据库字段名
	private String excelcolName;//EXCEL列名
	private String isUnique;//是否唯一标识
	private String datatype;//数据类型
	private String datainfo;//数据信息
	private String datalength;//数据长度
	private String isnull;//是否为空
	private String defaultvalue;//默认值
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getExcelcolName() {
		return excelcolName;
	}
	public void setExcelcolName(String excelcolName) {
		this.excelcolName = excelcolName;
	}
	public String getIsUnique() {
		return isUnique;
	}
	public void setIsUnique(String isUnique) {
		this.isUnique = isUnique;
	}
	public String getDatatype() {
		return datatype;
	}
	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}
	public String getDatainfo() {
		return datainfo;
	}
	public void setDatainfo(String datainfo) {
		this.datainfo = datainfo;
	}
	public String getDatalength() {
		return datalength;
	}
	public void setDatalength(String datalength) {
		this.datalength = datalength;
	}
	public String getIsnull() {
		return isnull;
	}
	public void setIsnull(String isnull) {
		this.isnull = isnull;
	}
	public String getDefaultvalue() {
		return defaultvalue;
	}
	public void setDefaultvalue(String defaultvalue) {
		this.defaultvalue = defaultvalue;
	}
}
