/*     */ package com.ultrapower.workflow.bizform.model;
/*     */ 
/*     */ import com.ultrapower.workflow.utils.UUIDGenerator;
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.Table;
/*     */ 
/*     */ @Entity
/*     */ @Table(name="BS_T_WF_NOTICE")
/*     */ public class Notice
/*     */   implements Serializable
/*     */ {
/*     */   private String noticeId;
/*     */   private String baseId;
/*     */   private String baseSchema;
/*     */   private String baseSn;
/*     */   private String taskId;
/*     */   private String phaseNo;
/*     */   private String fromTaskId;
/*     */   private String fromPhaseNo;
/*     */   private String baseSummary;
/*     */   private String baseItems;
/*     */   private String baseAction;
/*     */   private String noticeGroup;
/*     */   private String noticeGroupId;
/*     */   private String noticeUser;
/*     */   private String noticeUserId;
/*     */   private String content;
/*     */   private String mobiles;
/*     */   private String noticeType;
/*     */   private int isSent;
/*     */   private int isScaned;
/*     */   private String scanDesc;
/*     */   private long createTime;
/*     */   private int isPush;
/*     */   private String noticeUserLoginName;
/*     */ 
/*     */   public Notice()
/*     */   {
/*  47 */     this.noticeId = UUIDGenerator.getId();
/*     */   }
/*     */ 
/*     */   public Notice(String baseId, String baseSchema, String baseSn, String taskId, String baseSummary, String baseItems, String baseAction, String noticeGroup, String noticeGroupId, String noticeUser, String noticeUserId, String cotent, String scanDesc)
/*     */   {
/*  71 */     this.noticeId = UUIDGenerator.getId();
/*  72 */     this.baseId = baseId;
/*  73 */     this.baseSchema = baseSchema;
/*  74 */     this.baseSn = baseSn;
/*  75 */     this.taskId = taskId;
/*  76 */     this.baseSummary = baseSummary;
/*  77 */     this.baseItems = baseItems;
/*  78 */     this.baseAction = baseAction;
/*  79 */     this.noticeGroup = noticeGroup;
/*  80 */     this.noticeGroupId = noticeGroupId;
/*  81 */     this.noticeUser = noticeUser;
/*  82 */     this.noticeUserId = noticeUserId;
/*  83 */     this.content = cotent;
/*  84 */     this.scanDesc = scanDesc;
/*     */   }
/*     */ 
/*     */   @Id
/*     */   public String getNoticeId() {
/*  90 */     return this.noticeId;
/*     */   }
/*     */ 
/*     */   public void setNoticeId(String noticeId) {
/*  94 */     this.noticeId = noticeId;
/*     */   }
/*     */ 
/*     */   public String getBaseId() {
/*  98 */     return this.baseId;
/*     */   }
/*     */ 
/*     */   public void setBaseId(String baseId) {
/* 102 */     this.baseId = baseId;
/*     */   }
/*     */ 
/*     */   public String getBaseSchema() {
/* 106 */     return this.baseSchema;
/*     */   }
/*     */ 
/*     */   public void setBaseSchema(String baseSchema) {
/* 110 */     this.baseSchema = baseSchema;
/*     */   }
/*     */ 
/*     */   public String getBaseSn() {
/* 114 */     return this.baseSn;
/*     */   }
/*     */ 
/*     */   public void setBaseSn(String baseSn) {
/* 118 */     this.baseSn = baseSn;
/*     */   }
/*     */ 
/*     */   public String getBaseSummary() {
/* 122 */     return this.baseSummary;
/*     */   }
/*     */ 
/*     */   public void setBaseSummary(String baseSummary) {
/* 126 */     this.baseSummary = baseSummary;
/*     */   }
/*     */ 
/*     */   public String getBaseItems() {
/* 130 */     return this.baseItems;
/*     */   }
/*     */ 
/*     */   public void setBaseItems(String baseItems) {
/* 134 */     this.baseItems = baseItems;
/*     */   }
/*     */ 
/*     */   public String getBaseAction() {
/* 138 */     return this.baseAction;
/*     */   }
/*     */ 
/*     */   public void setBaseAction(String baseAction) {
/* 142 */     this.baseAction = baseAction;
/*     */   }
/*     */ 
/*     */   public String getNoticeGroup() {
/* 146 */     return this.noticeGroup;
/*     */   }
/*     */ 
/*     */   public void setNoticeGroup(String noticeGroup) {
/* 150 */     this.noticeGroup = noticeGroup;
/*     */   }
/*     */ 
/*     */   public String getNoticeGroupId() {
/* 154 */     return this.noticeGroupId;
/*     */   }
/*     */ 
/*     */   public void setNoticeGroupId(String noticeGroupId) {
/* 158 */     this.noticeGroupId = noticeGroupId;
/*     */   }
/*     */ 
/*     */   public String getNoticeUser() {
/* 162 */     return this.noticeUser;
/*     */   }
/*     */ 
/*     */   public void setNoticeUser(String noticeUser) {
/* 166 */     this.noticeUser = noticeUser;
/*     */   }
/*     */ 
/*     */   public String getNoticeUserId() {
/* 170 */     return this.noticeUserId;
/*     */   }
/*     */ 
/*     */   public void setNoticeUserId(String noticeUserId) {
/* 174 */     this.noticeUserId = noticeUserId;
/*     */   }
/*     */ 
/*     */   public String getContent() {
/* 178 */     return this.content;
/*     */   }
/*     */ 
/*     */   public void setContent(String cotent) {
/* 182 */     this.content = cotent;
/*     */   }
/*     */ 
/*     */   public String getMobiles() {
/* 186 */     return this.mobiles;
/*     */   }
/*     */ 
/*     */   public void setMobiles(String mobiles) {
/* 190 */     this.mobiles = mobiles;
/*     */   }
/*     */ 
/*     */   public String getTaskId() {
/* 194 */     return this.taskId;
/*     */   }
/*     */ 
/*     */   public void setTaskId(String taskId) {
/* 198 */     this.taskId = taskId;
/*     */   }
/*     */ 
/*     */   public String getScanDesc() {
/* 202 */     return this.scanDesc;
/*     */   }
/*     */ 
/*     */   public void setScanDesc(String scanDesc) {
/* 206 */     this.scanDesc = scanDesc;
/*     */   }
/*     */ 
/*     */   public int getIsSent()
/*     */   {
/* 211 */     return this.isSent;
/*     */   }
/*     */ 
/*     */   public void setIsSent(int isSent)
/*     */   {
/* 216 */     this.isSent = isSent;
/*     */   }
/*     */ 
/*     */   public int getIsScaned()
/*     */   {
/* 221 */     return this.isScaned;
/*     */   }
/*     */ 
/*     */   public void setIsScaned(int isScaned)
/*     */   {
/* 226 */     this.isScaned = isScaned;
/*     */   }
/*     */ 
/*     */   public int getIsPush()
/*     */   {
/* 231 */     return this.isPush;
/*     */   }
/*     */ 
/*     */   public void setIsPush(int isPush)
/*     */   {
/* 236 */     this.isPush = isPush;
/*     */   }
/*     */ 
/*     */   public String getNoticeUserLoginName()
/*     */   {
/* 241 */     return this.noticeUserLoginName;
/*     */   }
/*     */ 
/*     */   public void setNoticeUserLoginName(String noticeUserLoginName)
/*     */   {
/* 246 */     this.noticeUserLoginName = noticeUserLoginName;
/*     */   }
/*     */ 
/*     */   public String getNoticeType()
/*     */   {
/* 251 */     return this.noticeType;
/*     */   }
/*     */ 
/*     */   public void setNoticeType(String noticeType)
/*     */   {
/* 256 */     this.noticeType = noticeType;
/*     */   }
/*     */ 
/*     */   public long getCreateTime()
/*     */   {
/* 261 */     return this.createTime;
/*     */   }
/*     */ 
/*     */   public void setCreateTime(long createTime)
/*     */   {
/* 266 */     this.createTime = createTime;
/*     */   }
/*     */ 
/*     */   public String getPhaseNo()
/*     */   {
/* 271 */     return this.phaseNo;
/*     */   }
/*     */ 
/*     */   public void setPhaseNo(String phaseNo)
/*     */   {
/* 276 */     this.phaseNo = phaseNo;
/*     */   }
/*     */ 
/*     */   public String getFromTaskId()
/*     */   {
/* 281 */     return this.fromTaskId;
/*     */   }
/*     */ 
/*     */   public void setFromTaskId(String fromTaskId)
/*     */   {
/* 286 */     this.fromTaskId = fromTaskId;
/*     */   }
/*     */ 
/*     */   public String getFromPhaseNo()
/*     */   {
/* 291 */     return this.fromPhaseNo;
/*     */   }
/*     */ 
/*     */   public void setFromPhaseNo(String fromPhaseNo)
/*     */   {
/* 296 */     this.fromPhaseNo = fromPhaseNo;
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizform.model.Notice
 * JD-Core Version:    0.6.0
 */