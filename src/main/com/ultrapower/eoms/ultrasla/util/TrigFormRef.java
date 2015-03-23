package com.ultrapower.eoms.ultrasla.util;

import java.util.List;

/**
 * @author RenChenglin
 * @date 2011-11-23 下午12:18:46
 * @version
 */
public class TrigFormRef {
	private String baseSchema; //工单类别
	private String name; //名称
	private List<String> trigRef; //所有的触发关联
	
	public TrigFormRef() {
		super();
	}
	
	public TrigFormRef(String baseSchema, String name, List<String> trigRef) {
		super();
		this.baseSchema = baseSchema;
		this.name = name;
		this.trigRef = trigRef;
	}
	
	public String getBaseSchema() {
		return baseSchema;
	}
	public void setBaseSchema(String baseSchema) {
		this.baseSchema = baseSchema;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getTrigRef() {
		return trigRef;
	}
	public void setTrigRef(List<String> trigRef) {
		this.trigRef = trigRef;
	}
}
