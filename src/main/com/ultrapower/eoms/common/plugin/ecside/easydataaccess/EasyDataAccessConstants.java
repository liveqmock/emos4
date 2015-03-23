package com.ultrapower.eoms.common.plugin.ecside.easydataaccess;

public class EasyDataAccessConstants {

	public final static String NOTEQUAL_S="<>";
	public final static String LESSEQUAL_S="<=";
	public final static String GREATEQUAL_S=">=";
	public final static String EQUAL_S="=";
	public final static String LESS_S="<";
	public final static String GREAT_S=">";
	public final static String C_LESS="#{_L}";
	public final static String C_GREAT="#{_G}";
	public final static String C_NOTEQUAL="!=";
	public final static String REX_SQL="#\\{IF:([^\\}]+)\\}*";
	public final static String REX_PARAMETER="#\\{([^_](\\w|:)*[^_\\}])\\}*";
	public final static String END_TAG="#{/IF}";
	public final static String C_SQL_OUT_PARAMETER="OUT:";
	public final static String C_NULL="NULL";
	public final static String C_EMPTY="EMPTY";
	public final static String C_TRUE="TRUE";
	public final static String C_FALSE="FALSE";
	
	public final static String IN_TYPE="in";
	public final static String OUT_TYPE="out";
	public final static String DEFAULT_TYPE="in";

}
