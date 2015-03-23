package com.ultrapower.eoms.workflow.api.bean;


public class BaseFieldInfo implements Cloneable{
	private String name;
	private String id;
	private String value;
	private int type;
	/**
	 * 如果是分支条件用到的判断字段设置为 true
	 */
	private boolean isConditionField;

	public BaseFieldInfo() {
		
	}

	public BaseFieldInfo(String name, String id, String value, int type) {
		super();
		this.name = name;
		this.id = id;
		this.value = value;
		this.type = type;
	}
	
	public BaseFieldInfo(String name, String id, String value, int type, boolean isCondition) {
		super();
		this.name = name;
		this.id = id;
		this.value = value;
		this.type = type;
		this.isConditionField = isCondition;
	}
	
	public BaseFieldInfo(String name, String id, String value) {
		super();
		this.name = name;
		this.id = id;
		this.value = value;
		this.type = 4;
	}
	
	public BaseFieldInfo(String name, String id, String value, boolean isConditon) {
		super();
		this.name = name;
		this.id = id;
		this.value = value;
		this.type = 4;
		this.isConditionField = isConditon;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}

	protected BaseFieldInfo clone()  {
		BaseFieldInfo c = null;
		try {
			c = (BaseFieldInfo) super.clone();
		} catch (Exception e) {
		}
		return c;
	}

	public boolean isConditionField() {
		return isConditionField;
	}

	public void setConditionField(boolean isConditionField) {
		this.isConditionField = isConditionField;
	}
}
