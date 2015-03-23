package com.ultrapower.eoms.common.core.component.rule.operator;


public class DivisionOperator extends Operator {
	
	public DivisionOperator()
	{
		super("/",6);
	}

	public double evaluate(final double leftOperand, final double rightOperand) 
	{
		if(rightOperand==0)
		{
			return 0;
		}
		else
		{
			Double rtnValue = new Double(leftOperand/rightOperand);
			return rtnValue.doubleValue();
		}

	}	
	
	
}
