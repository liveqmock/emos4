package com.ultrapower.eoms.ultrasm.model;

import java.util.List;

public class ImpTableConfig
{
	private String tableName;//导入表名
	private int startRow;//开始导入行
	private int startCol;//开始导入列
	private String readType;//获取数据类型 1.xml文件获取 2.页面配置获取
	private String opType;//1.只做添加操作 2.存在做更新，否则做添加(必须配置唯一标识字段) 默认1
	private String relation;//是否被关联 relation=false则未被关联 否则该表是关联表 则存放的值为比较字段 即：当这几个字段完全相同时则会采取去重操作
	private String createGuid;//创建UUID
	private String guidName;//创建此UUID存储的字段名
	private String extend;//导入调用的方法
	
	private List<ImpFieldConfig> impFieldConfigList;
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public int getStartRow() {
		return startRow;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	public int getStartCol() {
		return startCol;
	}
	public void setStartCol(int startCol) {
		this.startCol = startCol;
	}
	public String getOpType() {
		return opType;
	}
	public void setOpType(String opType) {
		this.opType = opType;
	}
	public String getReadType() {
		return readType;
	}
	public void setReadType(String readType) {
		this.readType = readType;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public String getCreateGuid() {
		return createGuid;
	}
	public void setCreateGuid(String createGuid) {
		this.createGuid = createGuid;
	}
	public String getGuidName() {
		return guidName;
	}
	public void setGuidName(String guidName) {
		this.guidName = guidName;
	}
	public List<ImpFieldConfig> getImpFieldConfigList() {
		return impFieldConfigList;
	}
	public void setImpFieldConfigList(List<ImpFieldConfig> impFieldConfigList) {
		this.impFieldConfigList = impFieldConfigList;
	}
	public String getExtend() {
		return extend;
	}
	public void setExtend(String extend) {
		this.extend = extend;
	}
}
