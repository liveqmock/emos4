package com.ultrapower.eoms.common.core.component.rule.operator;

import com.ultrapower.eoms.common.core.component.rule.RuleException;

public abstract class Operator implements IOperator {
	
	private String symbol=null;
	private int precedence;
	public Operator(String symbol, int precedence)
	{
		this.symbol=symbol;
		this.precedence=precedence;
	}
	
	public String getSymbol()
	{
		return symbol;
	}
	public int getPrecedence()
	{
		return this.precedence;
	}
	public  double evaluate()
	{
		return 0;
	}
	public  String evaluate(final String leftOperand,final String rightOperand) throws RuleException
	{
		throw new RuleException("操作数表达式错误："+leftOperand+this.symbol+rightOperand);
	}
	
	public  double evaluate(final double leftOperand,final double rightOperand)
	{
		return 0;
	}
	public int getLength()
	{
		if(this.symbol!=null)
			return this.symbol.length();
		return 0;
	}

	
}
