package com.ultrapower.eoms.ultrasla.util;

import java.util.Map;

/**
 * @author RenChenglin
 * @date 2011-11-23 下午12:07:45
 * @version
 */
public class TrigStepConfig {
	private Map<String,TrigCouple> commonTrigs;//通用环节触发规则

	public TrigStepConfig() {
		super();
	}

	public TrigStepConfig(Map<String, TrigCouple> commonTrigs) {
		super();
		this.commonTrigs = commonTrigs;
	}

	public Map<String, TrigCouple> getCommonTrigs() {
		return commonTrigs;
	}

	public void setCommonTrigs(Map<String, TrigCouple> commonTrigs) {
		this.commonTrigs = commonTrigs;
	}
}
