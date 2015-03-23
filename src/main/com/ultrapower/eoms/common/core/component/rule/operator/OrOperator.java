package com.ultrapower.eoms.common.core.component.rule.operator;

/**
 * 
 * Copyright (c) 2010 神州泰岳服务管理事业部应用组
 * All rights reserved.
 * 描述：
 * @author 徐发球
 * @date   2010-3-27
 */
public class OrOperator extends Operator {

	public OrOperator()
	{
		super("or" ,2);
	}
	public double evaluate(final double leftOperand, final double rightOperand) {
		if (leftOperand == 1 || rightOperand == 1) {
			return 1;
		}

		return 0;
	}		
}
