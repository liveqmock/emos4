package com.ultrapower.eoms.common.core.component.rule.operator;


public class SubtractionOperator extends Operator {
	
	public SubtractionOperator() {
		super("-", 5);
	}

	public double evaluate(final double leftOperand, final double rightOperand)
	{
		Double rtnValue = new Double(leftOperand - rightOperand);
		return rtnValue.doubleValue();
	}
	
}
