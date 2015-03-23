package com.ultrapower.eoms.ultrasm.model;

public class TitleProperties
{
	private String fieldName;//字段名称
	private String displayName;//字段显示名称
	private int width;//宽度
	private int rowSpan;//跨行数
	private int colSpan;//跨列数
	private int ordernum;//排序值
	private String align;//对其方式
	//private String colColor;//列数据背景颜色
	/**
	 * datatype:数据类型
	 * datainfo:数据信息
	 */
	private String datatype;//数据类型
	private String datainfo;//数据信息
	private String datalength;//数据长度
	private String enable;//是否可用
	private String operator;//操作符
	private String compareData;//比较数据
	private String displayColor;//显示颜色
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getRowSpan() {
		return rowSpan;
	}
	public void setRowSpan(int rowSpan) {
		this.rowSpan = rowSpan;
	}
	public int getColSpan() {
		return colSpan;
	}
	public void setColSpan(int colSpan) {
		this.colSpan = colSpan;
	}
	public int getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(int ordernum) {
		this.ordernum = ordernum;
	}
	public String getAlign() {
		return align;
	}
	public void setAlign(String align) {
		this.align = align;
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
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getCompareData() {
		return compareData;
	}
	public void setCompareData(String compareData) {
		this.compareData = compareData;
	}
	public String getDisplayColor() {
		return displayColor;
	}
	public void setDisplayColor(String displayColor) {
		this.displayColor = displayColor;
	}
}
