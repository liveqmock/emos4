package com.ultrapower.eoms.common.core.component.rule.analysis;

import com.ultrapower.eoms.common.core.component.rule.operator.IOperator;

public class NextOperator {

	private IOperator operator;
	private int index=-1;

	public NextOperator(final IOperator operator, final int index) {
		this.operator = operator;
		this.index = index;
	}

	public IOperator getOperator() {
		return operator;
	}

	public int getIndex() {
		return index;
	}
	
}
