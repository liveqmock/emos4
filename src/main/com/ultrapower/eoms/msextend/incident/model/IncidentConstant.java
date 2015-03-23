package com.ultrapower.eoms.msextend.incident.model;

/**
 * 事件流程常用参数
 * @author zhangxuegang
 *
 */
public class IncidentConstant {
	/**
	 * 建单受理环节 
	 */
	public static final String NEW_STEP_NO = "step01";
	
	/**
	 * 工单处理环节 
	 */
	public static final String DEAL_STEP_NO = "step02";
	
	/**
	 * 关单环节
	 */
	public static final String CLOSE_STEP_NO = "step03";
	
	/**
	 * 新建环节,保存
	 */
	public static final String SAVE = "SAVEStep01";
	
	/**
	 * 新建环节转派 
	 */
	public static final String TO_RENEXT = "RENEXTStep01";
	
	/**
	 * 新建环节,提交处理
	 */
	public static final String TO_ASSIGN = "ASSIGNStep01";
	
	/**
	 * 处理环节受理
	 */
	public static final String TO_ACCEPT_PROCESS = "AcceptedProcessing";
	
	/**
	 * 处理环节转派
	 */
	 public static final String TO_RENEXT_PROCESS = "RenextProcessing";
	 
	 
	/**
	 * 处理环节退回
	 */
	 public static final String TO_BACK_PROCESS = "SendBackProcessing";
	 
	 
	 /**
	  * 关单回顾环节受理
	  */
	 public static final String TO_ACCEPT_REVIEW = "AcceptedReview";
	 
	 /**
	  * 关单回顾环节退回
	  */
	 public static final String TO_BACK_REVIEW = "SendBackReview";
	 
	 /**
	  * 关单环节转派
	  */
	
	 public static final String TO_RENEXT_REVIEW = "RenextReview";
	 
	 /**
	  * 关单环节关单
	  */
	 
	 public static final String TO_CLOSE = "ToClose";

	/**
	 * 新建环节关单按钮英文名称
	 */
	public static final String TO_FINISH_STEP01 = "ToFinishStep01";
	 
	 
	 /**
	  *受理建单环节状态
	  */
	 
	 public static final String  BASE_STATUS_NEW ="受理建单";
	 
	 /**
	  * 处理环节状态
	  */
	 public static final String  BASE_STATUS_PROCESS ="处理中";
	 
	 /**
	  * 待关闭环节状态
	  */
	 
	 public static final String  BASE_STATUS_CLOSE ="待关闭";
	 
	 
	 
}
