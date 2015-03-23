package com.ultrapower.eoms.common.core.component.rule.operator;

public class AddtionOperator extends Operator {
	
	public AddtionOperator()
	{
		super("+",5);
	}
	
	public double evaluate(final double leftOperand, final double rightOperand)
	{
		Double rtnValue = new Double(leftOperand + rightOperand);
		return rtnValue.doubleValue();
	}
	
	public String evaluate(final String leftOperand, final String rightOperand) {
		String rtnValue = new String(leftOperand.substring(0, leftOperand.length() - 1)
				+ rightOperand.substring(1, rightOperand.length()));
		return rtnValue;
	}	
	
	
}
