package com.ultrapower.eoms.ultrasla.util;
/**
 * @author RenChenglin
 * @date 2011-10-31 下午02:52:14
 * @version
 */
public class ConstantMark {
	//SLA配置信息
	public static boolean EVENT_SCAN_SWITCH					= false; // 事件扫描开关
	public static boolean EVENT_ROLL_SWITCH					= false; // 事件轮询开关
	public static String EVENT_SCAN_SQLNAME					= "";    // 事件扫描SQL
	public static long EVENT_ACTION_LIMIT_START				= 60*30; // 轮询指定开始时限（扫描事件动作执行时间的提前的时间限制：以免搜索遗漏的数据）
	public static long EVENT_ACTION_LIMIT_END				= 60;	 // 轮询指定结束时限（扫描事件动作执行时间的延后的时间限制：预留程序处理的时间）
	public static boolean OPEN_NOTICE_FILTER                = false; // 是否开启通知过滤
	
	//事件新建及销毁状态
	public static final long EVENT_ACTION_STATUS_NEW		= 1; //事件动作新建状态
	public static final long EVENT_ACTION_STATUS_DESTORY	= 0; //事件动作销毁状态
	
	// 事件动作状态常量
	public static final long EVENT_ACTION_STATUS_ACTIVE		= 1; // 活动（待处理动作）
	public static final long EVENT_ACTION_STATUS_COMPLETED	= 2; // 已完成
	public static final long EVENT_ACTION_STATUS_REACTIVE	= 3; // 补发活动（当前时间未在处理人规定接收短信范围内，设置补发的消息状态）
	public static final long EVENT_ACTION_STATUS_REDESTROY	= 4; // 补发销毁（当前时间未在处理人规定接收短信范围内，设置不补发的消息状态）
	
	//提醒类型（notice type）
	public static final long ACTION_NOTICE_TYPE_FORKNOWN    = 1; // 预知提醒
	public static final long ACTION_NOTICE_TYPE_AFTERKNOWN  = 2; // 超时提醒

	//消息发送类型
	public static final long SEND_TYPE_SM                   = 1; // 短信
	public static final long SEND_TYPE_EMAIL                = 2; // 电子邮件
	public static final long SEND_TYPE_SMANDEMAIL           = 3; // 短信+电子邮件
	public static final long SEND_TYPE_SELFNOTICE           = 9; // 自定义处理
	
	/**
	 * 组织机构类类型
	 * @author Administrator
	 * 人员，组，角色
	 */
	public enum OrgType{USER,GROUP,ROLE};
	
	/**
	 * 接收者类型
	 * @author Administrator
	 * 通知，抄送，升级，自定义，默认
	 */
	public enum RecType{NOTICE, DUPLICATE, UPGRADE, COMP, DEFAULT, FIXED};
	
	/**
	 * 时间类型
	 * @author Administrator
	 * 产生，销毁
	 */
	public enum EventType{PRODUCE,DESTROY};
}
