package com.ultrapower.eoms.common.core.component.rquery.util;

import java.util.HashMap;

public class RConstants {

	//存放xml文件对象
	
	public static final String  E_ACT_PARENTHESES="parentheses";
	public static final String  E_ACT_SWITCH="switch";
	public static final String  E_ACT_FIELD="field";
	public static final String  E_ACT_SQL="sql";
	public static final String  E_ACT_IF="if";
	public static final String  E_ACT_IF_SUCCESS="success";
	public static final String  E_ACT_IF_ELSE="else";
	
	public static final String OPERATOR_LIKE="like";
	public static final String OPERATOR_LEFT_LIKE="leftlike";
	public static final String OPERATOR_RIGHT_LIKE="rightlike";
	public static final String OPERATOR_LESSTHAN="<";
	
	public static final String DATA_SPILT=",";
	public static final int DATA_TYPE_STRING=4;
	public static final int DATA_TYPE_NUMBER=5;
	public static final int DATA_TYPE_DATATIME=7;
	
	public static final int DATA_VAR_TABLE=3;//表变量
	public static final int DATA_VAR_PAGE=2;//页面传入的数据变量
	public static final int DATA_VAR_SYSTEM=1;//系统变量
	
	public static final int DATA_VAR_DIRECT=4;//直接变量 只做替换
	
	public static String xmlPath="";
}
