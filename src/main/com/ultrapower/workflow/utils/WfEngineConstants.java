/*     */ package com.ultrapower.workflow.utils;
/*     */ 
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class WfEngineConstants
/*     */ {
/*  11 */   public static String DATABASE_TYPE = null;
/*  12 */   public static String DATABASE_TYPE_DB2 = "db2";
/*  13 */   public static String DATABASE_TYPE_ORACLE = "oracle";
/*     */   public static final String FLOW_TYPE_PROCESS = "EOMS";
/*     */   public static final String ENTRY_DEF = "DEFINE";
/*     */   public static final String ENTRY_FREE = "FREE";
/*     */   public static final String ENTRY_FREESUBFLOW = "FREESUBFLOW";
/*     */   public static final String ENTRY_STATE_NEW = "new";
/*     */   public static final String ENTRY_STATE_ACTIVE = "active";
/*     */   public static final String ENTRY_STATE_SUSPEND = "suspend";
/*     */   public static final String ENTRY_STATE_FINISH = "finish";
/*     */   public static final String ENTRY_STATE_CANCEL = "cancel";
/*     */   public static final String ENTRY_STATE_TERMINATE = "terminate";
/*     */   public static final String ENTRY_STATE_UNKNOWN = "unknown";
/*     */   public static final String ENTRY_STATE_UPDATE_FLAG = "entry_state_update_flag_no";
/*     */   public static final String STEP_START_CODE = "BEGIN";
/*     */   public static final String STEP_TYPE_DEAL = "DEAL";
/*     */   public static final String STEP_TYPE_AUDITING = "AUDITING";
/*     */   public static final String STEP_TYPE_ASSIST = "ASSIST";
/*     */   public static final String STEP_TYPE_COPY = "COPY";
/*     */   public static final String STEP_TYPE_JOIN = "JOIN";
/*     */   public static final String ACTION_SAVE = "SAVE";
/*     */   public static final String ACTION_NEW = "NEW";
/*     */   public static final String ACTION_CHASE = "CHASE";
/*     */   public static final String ACTION_SUSPEND = "SUSPEND";
/*     */   public static final String ACTION_ACTIVE = "ACTIVE";
/*     */   public static final String ACTION_CANCEL = "CANCEL";
/*     */   public static final String ACTION_ASSIGN = "ASSIGN";
/*     */   public static final String ACTION_REASSIGN = "REASSIGN";
/*     */   public static final String ACTION_APPENDASSIGN = "APPENDASSIGN";
/*     */   public static final String ACTION_AUDIT = "AUDIT";
/*     */   public static final String ACTION_REAUDIT = "REAUDIT";
/*     */   public static final String ACTION_AUDITINGPASS = "AUDITINGPASS";
/*     */   public static final String ACTION_AUDITINGNOPASS = "AUDITINGNOPASS";
/*     */   public static final String ACTION_ORGANIZEAUDIT = "ORGANIZEAUDIT";
/*     */   public static final String ACTION_ORGANIZEAUDITINGPASS = "ORGANIZEAUDITINGPASS";
/*     */   public static final String ACTION_ORGANIZEAUDITINGNOPASS = "ORGANIZEAUDITINGNOPASS";
/*     */   public static final String ACTION_FINISH = "FINISH";
/*     */   public static final String ACTION_REJECT = "REJECT";
/*     */   public static final String ACTION_SENDBACK = "SENDBACK";
/*     */   public static final String ACTION_ACCEPT = "ACCEPT";
/*     */   public static final String ACTION_CLOSE = "CLOSE";
/*     */   public static final String ACTION_ASSIST = "ASSIST";
/*     */   public static final String ACTION_MAKECOPY = "MAKECOPY";
/*     */   public static final String ACTION_AFFIRM = "CONFIRM";
/*     */   public static final String ACTION_HASTEN = "HASTEN";
/*     */   public static final String ACTION_NOTICE = "NOTICE";
/*     */   public static final String ACTION_SUGGEST = "SUGGEST";
/*     */   public static final String ACTION_CHECK = "CHECK";
/*     */   public static final String ACTION_TERMINATE = "TERMINATE";
/*     */   public static final String ACTION_CHASEALL = "CHASEALL";
/*     */   public static final String ACTION_NEXT = "NEXT";
/*     */   public static final String ACTION_RENEXT = "RENEXT";
/*     */   public static final String FLAG_TYPE_ASSIGN = "主办";
/*     */   public static final String FLAG_TYPE_ASSIST = "协办";
/*     */   public static final String FLAG_TYPE_COPY = "抄送";
/*     */   public static final String FLAG_TYPE_AUDIT = "审批";
/*     */   public static final String FLAG_TYPE_CHECK = "质检";
/*     */   public static final String FLAG_TYPE_ORGANIZEAUDIT = "会审";
/*     */   public static final String PASSIN_ASSIGNEE = "NextDefAssigne";
/*     */   public static final String PROCESS_WAITDEAL = "待处理";
/*     */   public static final String PROCESS_DEAL = "处理中";
/*     */   public static final String PROCESS_WAITAUDIT = "待审批";
/*     */   public static final String PROCESS_AUDIT = "审批中";
/*     */   public static final String PROCESS_AUDITED = "已审批";
/*     */   public static final String PROCESS_DEALED = "已处理";
/*     */   public static final String PARENT_ENTRYID = "parent_entryId";
/*     */   public static final String TOP_ENTRYID = "top_entryId";
/*     */   public static final String PARENT_TASKID = "parent_taskId";
/*     */   public static final String TOP_TASKID = "top_taskId";
/*     */   public static final String PARENT_FLOWID = "parent_flowId";
/*     */   public static final String TOP_FLOWID = "top_flowId";
/*     */   public static final String SERIALNUM = "serialNum";
/*     */   public static final String PARENT_STEPNO = "parent_stepNo";
/*     */   public static final String PARENT_STEPGROUP = "parent_stepGroup";
/*     */   public static final String CONDITION_CLASS = "class";
/*     */   public static final String CONDITION_CLASSNAME = "class.name";
/*     */   public static final String CONDITION_SCRIPT = "script";
/*     */   public static final String COND_SCRIPT_CONTENT = "script.content";
/*     */   public static final String ACTOR_TYPE_USER = "USER";
/*     */   public static final String ACTOR_TYPE_GROUP = "GROUP";
/*     */   public static final String ACTOR_TYPE_ROLE = "ROLE";
/*     */   public static final String SPLIT = ",";
/*     */   public static final String RELATE_TYPE_SYNC = "1";
/*     */   public static final String RELATE_TYPE_ASYNC = "0";
/*     */   public static final String POLICY_SHARE = "SHARE";
/*     */   public static final String POLICY_COMPLETE = "ONLY";
/*     */   public static final String POLICY_MANAGEMANT = "MANAGEMANT";
/* 139 */   public static boolean ISALIVE = true;
/*     */ 
/* 142 */   public static Vector<String> currentThreads = new Vector();
/*     */ 
/* 149 */   public static String CREATEUSER = "$CreateUser$";
/* 150 */   public static String STEPCODE = "$StepCode$";
/* 151 */   public static String ELSE = "$else$";
/*     */ 
/* 154 */   public static String MAILBUFFER = "mailBuffer_";
/*     */ 
/* 157 */   public static String CUSTOM_ACTIONS = "CUSTOMACTIONS";
/* 158 */   public static String flagSave = "FLAGSAVE";
/* 159 */   public static String flagAssign = "FLAGASSIGN";
/* 160 */   public static String flagAssist = "FLAGASSIST";
/* 161 */   public static String flagCopy = "FLAGCOPY";
/* 162 */   public static String flagTurnUp = "FLAGTURNUP";
/* 163 */   public static String flagRecall = "FLAGRECALL";
/* 164 */   public static String flagCancel = "FLAGCANCEL";
/* 165 */   public static String flagClose = "FLAGCLOSE";
/* 166 */   public static String flagTurnDown = "FLAGTURNDOWN";
/* 167 */   public static String flagToAuditing = "FLAGTOAUDITING";
/* 168 */   public static String flagStartInsideFlow = "FLAGSTARTINSIDEFLOW";
/* 169 */   public static String wfFreeAuditResult = "wfFreeAuditResult";
/* 170 */   public static String wfFreeAuditResult_1 = "1";
/* 171 */   public static String wfFreeAuditResult_0 = "0";
/*     */ 
/* 173 */   public static long FLAGACTIVE_SUSPEND = 17L;
/* 174 */   public static long FLAGACTIVE_TERMINATE = 18L;
/*     */ 
/* 177 */   public static String INPUTS_BASEID = "BaseID";
/* 178 */   public static String INPUTS_BASESCHEMA = "BaseSchema";
/* 179 */   public static String INPUTS_BASENAME = "BaseName";
/* 180 */   public static String INPUTS_BASESUMMARY = "BaseSummary";
/* 181 */   public static String INPUTS_BASEITEMS = "BaseItems";
/* 182 */   public static String INPUTS_BASEPRIORITY = "BasePriority";
/* 183 */   public static String INPUTS_BASEACCEPTOUTTIME = "BaseAcceptOutTime";
/* 184 */   public static String INPUTS_BASEDEALOUTTIME = "BaseDealOutTime";
/*     */   public static final String PROCESS_ACTION_DESC = "ActionText";
/*     */   public static final String PROCESS_DEAL_DESC = "DealDesc";
/*     */   public static final String PREASSIGN = "PreAssign";
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.utils.WfEngineConstants
 * JD-Core Version:    0.6.0
 */