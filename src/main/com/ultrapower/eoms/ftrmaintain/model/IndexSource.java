package com.ultrapower.eoms.ftrmaintain.model;

/**
 * 取出索引类别的所有数据源用于在索引类别维护界面上显示
 * @author RenChenglin
 */
public class IndexSource {
	private String type; //1 数据库 2文件
	private String value;
	private long fileFrom = -1; //当type为2的时候，该值需要，0手工配置 1SQL数据源
	private long isCustomSource = 0; //是否是自定义数据源
	
	public IndexSource() {
		super();
	}
	
	public IndexSource(String type, String value,long isCustomSource) {
		super();
		this.type = type;
		this.value = value;
		this.isCustomSource = isCustomSource;
	}

	public IndexSource(String type, String value, long fileFrom,long isCustomSource) {
		super();
		this.type = type;
		this.value = value;
		this.fileFrom = fileFrom;
		this.isCustomSource = isCustomSource;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public long getFileFrom() {
		return fileFrom;
	}
	public void setFileFrom(long fileFrom) {
		this.fileFrom = fileFrom;
	}

	public long getIsCustomSource() {
		return isCustomSource;
	}

	public void setIsCustomSource(long isCustomSource) {
		this.isCustomSource = isCustomSource;
	}
	
}
