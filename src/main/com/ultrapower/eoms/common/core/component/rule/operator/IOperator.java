package com.ultrapower.eoms.common.core.component.rule.operator;

import com.ultrapower.eoms.common.core.component.rule.RuleException;

public interface IOperator {
	public String getSymbol();
	public int getPrecedence();
	public abstract double evaluate();
	public abstract String evaluate(final String leftOperand,final String rightOperand)  throws RuleException;
	public abstract double evaluate(final double leftOperand,final double rightOperand) ;
	public abstract int getLength();
}
