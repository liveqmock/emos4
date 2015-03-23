package com.ultrapower.eoms.common.core.component.rule.node;

import com.ultrapower.eoms.common.core.component.rule.operator.IOperator;

/**
 * 操作符号
 * Copyright (c) 2010 神州泰岳服务管理事业部应用组
 * All rights reserved.
 * 描述：
 * @author 徐发球
 * @date   2010-3-27
 */
public class NodeOperator {
	
	private IOperator operator;
	
	public NodeOperator(IOperator operator)
	{
		this.operator=operator;
	}

	public IOperator getOperator() {
		return operator;
	}
	

}
