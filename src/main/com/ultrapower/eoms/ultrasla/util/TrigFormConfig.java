package com.ultrapower.eoms.ultrasla.util;

import java.util.Map;

/**
 * @author RenChenglin
 * @date 2011-11-23 下午12:12:46
 * @version
 */
public class TrigFormConfig {
	private Map<String,TrigCouple> trigs; //所有的工单触发规则
	private Map<String,TrigFormRef> trigFroms; //所有的工单触发关联
	public TrigFormConfig() {
		super();
	}
	
	public TrigFormConfig(Map<String, TrigCouple> trigs,
			Map<String, TrigFormRef> trigFroms) {
		super();
		this.trigs = trigs;
		this.trigFroms = trigFroms;
	}
	
	public Map<String, TrigCouple> getTrigs() {
		return trigs;
	}
	public void setTrigs(Map<String, TrigCouple> trigs) {
		this.trigs = trigs;
	}
	public Map<String, TrigFormRef> getTrigFroms() {
		return trigFroms;
	}
	public void setTrigFroms(Map<String, TrigFormRef> trigFroms) {
		this.trigFroms = trigFroms;
	}
}
