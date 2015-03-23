package com.ultrapower.eoms.msextend.serverQuest.model;


/**
 * 服务请求相关常数
 * @author hhy
 *
 */
public class ServerQuestConstant {
	/**
	 * 新建工单环节 
	 */
	public static final String BASE_STATUS_NEW = "新建";
	/**
	 * 送审
	 */
	public static final String BASE_STATUS_SENDAUDIT = "申请审批";
	/**
	 * 受理环节
	 */
	public static final String BASE_STATUS_ACCEPT = "受理";
	/**
	 * 审批环节
	 */
	public static final String BASE_STATUS_AUDIT = "审批";
	/**
	 * 处理环节
	 */
	public static final String BASE_STATUS_DEAL = "处理";
	/**
	 * 关单环节
	 */
	public static final String BASE_STATUS_CLOSE = "待关闭";
	/**
	 * 新建环节,保存
	 */
	public static final String SAVE = "SAVE";
	/**
	 * 新建环节,提交请求
	 */
	public static final String TO_REQUEST = "ToRequest";
	/**
	 * 新建环节,送审
	 */
	public static final String TO_SEND_AUDIT  = "ToSendAudit";
	/**
	 * 新建环节关单按钮英文名称
	 */
	public static final String TO_CLOSE_FROM_REQUEST = "ToCloseFromRequest";
	/**
	 * 提交审批环节,通过
	 */
	public static final String TO_PASS_REQUEST_AUDIT = "ToPassRequestAudit";
	/**
	 * 受理环节,退回
	 */
	public static final String TO_BACK_TO_REQUEST = "ToBackToRequestFromAccept";
	/**
	 * 受理环节,受理
	 */
	public static final String TO_ACCEPT_ACCEPT = "ToAcceptAccept";
	/**
	 * 受理环节,保存
	 */
	public static final String TO_SAVE_ACCEPT = "ToSaveAccept";
	/**
	 * 受理环节,转派
	 */
	public static final String TO_RENEXT_TO_ACCEPT = "ToRenextToAccept";
	/**
	 * 受理环节,提交审批
	 */
	public static final String TO_AUDIT = "ToAudit";
	/**
	 * 受理环节,提交处理
	 */
	public static final String TO_DEAL_FROM_ACCEPT = "ToDealFromAccept";
	/**
	 * 受理环节,处理完成
	 */
	public static final String TO_DEALFINISH_FROM_ACCEPT = "ToDealFinishFromAccept";
	/**
	 * 审批环节,通过
	 */
	public static final String TO_PASS = "ToPass";
	/**
	 * 审批环节,不通过
	 */
	public static final String TO_NOPASS = "ToNoPass";
	/**
	 * 处理环节,退回
	 */
	public static final String TO_BACK_TO_ACCEPT_FROM_DEAL = "ToBackToAcceptFromDeal";
	/**
	 * 处理环节,受理
	 */
	public static final String TO_ACCEPT_DEAL = "ToAcceptDeal";
	/**
	 * 处理环节,保存
	 */
	public static final String TO_SAVE_DEAL = "ToSaveDeal";
	/**
	 * 处理环节,转派
	 */
	public static final String TO_RENEXT_TO_DEAL = "ToRenextToDeal";
	/**
	 * 处理环节,处理完成
	 */
	public static final String TO_DEALFINISH_FROM_DEAL = "ToDealFinishFromDeal";
	/**
	 * 审批环节,受理
	 */
	public static final String TO_ACCEPT_AUDIT = "ToAcceptAudit";
	/**
	 * 关单环节,退回
	 */
	public static final String TO_BACK_FROM_CLOSE = "ToBackFromClose";
	/**
	 * 关单环节,受理
	 */
	public static final String TO_ACCEPT_CLOSE = "ToAcceptClose";
	/**
	 * 关单环节,转派
	 */
	public static final String TO_RENEXT_TO_CLOSE = "ToRenextToClose";
	/**
	 * 关单环节，关单
	 */
	public static final String TO_CLOSE_FROM_DEAL = "ToCloseFromDeal";
	/**
	 * 全局变量,当前审批人位置
	 */
	public static final String CURRENT_AUDIT_SEQUENCE = "current_audit_sequence";
	/**
	 * 全局变量,当前服务目录是否有受理人
	 */
	public static final String HAS_ACCEPT_PERSON = "has_accept_person";
}
