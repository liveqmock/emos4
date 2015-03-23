package com.ultrapower.eoms.common.core.component.rule;

/**
 * 例外处理
 * Copyright (c) 2010 神州泰岳服务管理事业部应用组
 * All rights reserved.
 * 描述：
 * @author 徐发球
 * @date   2010-3-27
 */
public class RuleException extends Exception{
	
	private static final long serialVersionUID = -3010345764122748053L;
	public RuleException(String message) {
		super(message);
	}
	public RuleException(Exception exception) {
		super(exception);
	}
	public RuleException(String message, Exception exception) {
		super(message, exception);
	}	
}
