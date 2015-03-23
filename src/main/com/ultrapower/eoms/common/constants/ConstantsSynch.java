package com.ultrapower.eoms.common.constants;

public class ConstantsSynch {
	public static boolean isSynch 				= false;				//开关,用来标识是否进行信息同步(与pasm)
	public static boolean isSynchToV2 			= false;				//是否向V2同步数据
	public static String synchToV2Content		= "";					//若isSynchToV2=true则此属性为向V2同步的内容
	public static boolean isSynchToPasm 		= false;				//是否向PASM同步数据
	public static String synchToPasmContent		= "";					//若isSynchToPasm=true则此属性为向Pasm同步的内容
	public static boolean isSynchFromV2 		= false;				//是否从V2同步数据到eoms
	public static String synchFromV2Content		= "";					//若isSynchFromV2=true则此属性为从V2同步到eoms的内容
	public static boolean isSynchFromPasm 		= false;				//是否从PASM同步数据到eoms
	public static String synchFromPasmContent	= "";					//若isSynchFromPasm=true则此属性为从Pasm同步到eoms的内容

	public static boolean isPasmSynchEoms		= false;				//开关,用来标识是否进行pasm向eoms同步
	public static boolean isUip					= false;				//开关,用来标识是否进行了uip的集成
	public final static boolean IS_SSO			= false;				//是否让其他系统单点登录本系统
	public final static String SSO_USER_PWD_SEPARATOR = "@semi@";		//单点登录中用户名和密码分隔符
	public final static String SSO_COOKIE_NAME = "eoms4_ultra";

	public final static String SYSTEM_PASM		= "PASM";				//代表系统为PASM系统
	public final static String SYSTEM_V2		= "V2";					//代表系统为V2系统
}
