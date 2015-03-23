package com.ultrapower.eoms.common.core.component.rule.operator;

public class MultiplicationOperator extends Operator {
	
	public MultiplicationOperator()
	{
		super("*",6);
	}
	
	public double evaluate(final double leftOperand, final double rightOperand)
	{
		Double rtnValue = new Double(leftOperand * rightOperand);
		return rtnValue.doubleValue();
	}	

}
