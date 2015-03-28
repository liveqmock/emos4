/*     */ package com.ultrapower.workflow.configuration.sort.model;
/*     */ 
/*     */ import com.ultrapower.workflow.utils.UUIDGenerator;
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.Table;
/*     */ 
/*     */ @Entity
/*     */ @Table(name="BS_T_WF_TYPE")
/*     */ public class WfType
/*     */   implements Serializable
/*     */ {
/*     */   private String id;
/*     */   private String sortId;
/*     */   private String code;
/*     */   private String name;
/*     */   private long entryCount;
/*     */   private long todayEntryCount;
/*     */   private String wfCountType;
/*     */   private long wfType;
/*     */   private String wfDefaultVersion;
/*     */   private long orderby;
/*     */   private String remark;
/*     */   private long lastEntryTime;
/*     */   private String baseCategorySchama;
/*     */   private String baseCategoryCode;
/*     */   private long baseCategoryIsDefaultFix;
/*  35 */   private long baseCategoryState = 1L;
/*     */   private String baseCategoryBtnAllIds;
/*     */   private String baseCategoryBtnFreeIds;
/*     */   private String baseCategoryBtnFixIds;
/*     */   private String baseCategoryPanelHids;
/*     */   private String baseCategoryPanelIds;
/*     */   private String baseCategoryCustomActions;
/*     */   private String baseCategoryBeginPhaseNo;
/*     */   private String panelGroupID;
/*     */   private String flowType;
/*     */   private String theme;
/*     */ 
/*     */   public WfType()
/*     */   {
/*  51 */     this.id = UUIDGenerator.getId();
/*     */   }
/*     */ 
/*     */   public WfType(String sortId, String code, String name)
/*     */   {
/*  56 */     this.id = UUIDGenerator.getId();
/*  57 */     this.sortId = sortId;
/*  58 */     this.code = code;
/*  59 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public WfType(String sortId, String code, String name, long entryCount, long todayEntryCount, String wfCountType, long wfType, String wfDefaultVersion, long orderby, String remark)
/*     */   {
/*  64 */     this.id = UUIDGenerator.getId();
/*  65 */     this.sortId = sortId;
/*  66 */     this.code = code;
/*  67 */     this.name = name;
/*  68 */     this.entryCount = entryCount;
/*  69 */     this.todayEntryCount = todayEntryCount;
/*  70 */     this.wfCountType = wfCountType;
/*  71 */     this.wfType = wfType;
/*  72 */     this.wfDefaultVersion = wfDefaultVersion;
/*  73 */     this.orderby = orderby;
/*  74 */     this.remark = remark;
/*     */   }
/*     */ 
/*     */   @Id
/*     */   public String getId()
/*     */   {
/*  82 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(String id) {
/*  86 */     this.id = id;
/*     */   }
/*     */ 
/*     */   public String getWfCountType() {
/*  90 */     return this.wfCountType;
/*     */   }
/*     */ 
/*     */   public void setWfCountType(String wfCountType) {
/*  94 */     this.wfCountType = wfCountType;
/*     */   }
/*     */ 
/*     */   public long getWfType()
/*     */   {
/* 100 */     return this.wfType;
/*     */   }
/*     */ 
/*     */   public void setWfType(long wfType) {
/* 104 */     this.wfType = wfType;
/*     */   }
/*     */ 
/*     */   public String getWfDefaultVersion() {
/* 108 */     return this.wfDefaultVersion;
/*     */   }
/*     */ 
/*     */   public void setWfDefaultVersion(String wfDefaultVersion) {
/* 112 */     this.wfDefaultVersion = wfDefaultVersion;
/*     */   }
/*     */ 
/*     */   public Long getOrderby() {
/* 116 */     return Long.valueOf(this.orderby);
/*     */   }
/*     */ 
/*     */   public void setOrderby(Long orderby) {
/* 120 */     this.orderby = orderby.longValue();
/*     */   }
/*     */ 
/*     */   public String getRemark() {
/* 124 */     return this.remark;
/*     */   }
/*     */ 
/*     */   public void setRemark(String remark) {
/* 128 */     this.remark = remark;
/*     */   }
/*     */ 
/*     */   public String getSortId() {
/* 132 */     return this.sortId;
/*     */   }
/*     */ 
/*     */   public void setSortId(String sortId) {
/* 136 */     this.sortId = sortId;
/*     */   }
/*     */ 
/*     */   public String getCode() {
/* 140 */     return this.code;
/*     */   }
/*     */ 
/*     */   public void setCode(String code) {
/* 144 */     this.code = code;
/*     */   }
/*     */ 
/*     */   public String getName() {
/* 148 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/* 152 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public long getEntryCount() {
/* 156 */     return this.entryCount;
/*     */   }
/*     */ 
/*     */   public void setEntryCount(long entryCount) {
/* 160 */     this.entryCount = entryCount;
/*     */   }
/*     */ 
/*     */   public long getTodayEntryCount() {
/* 164 */     return this.todayEntryCount;
/*     */   }
/*     */ 
/*     */   public void setTodayEntryCount(long todayEntryCount) {
/* 168 */     this.todayEntryCount = todayEntryCount;
/*     */   }
/*     */ 
/*     */   public void setOrderby(long orderby) {
/* 172 */     this.orderby = orderby;
/*     */   }
/*     */ 
/*     */   public long getLastEntryTime() {
/* 176 */     return this.lastEntryTime;
/*     */   }
/*     */ 
/*     */   public void setLastEntryTime(long lastEntryTime) {
/* 180 */     this.lastEntryTime = lastEntryTime;
/*     */   }
/*     */ 
/*     */   public String getBaseCategorySchama() {
/* 184 */     return this.baseCategorySchama;
/*     */   }
/*     */ 
/*     */   public void setBaseCategorySchama(String baseCategorySchama) {
/* 188 */     this.baseCategorySchama = baseCategorySchama;
/*     */   }
/*     */ 
/*     */   public String getBaseCategoryCode() {
/* 192 */     return this.baseCategoryCode;
/*     */   }
/*     */ 
/*     */   public void setBaseCategoryCode(String baseCategoryCode) {
/* 196 */     this.baseCategoryCode = baseCategoryCode;
/*     */   }
/*     */ 
/*     */   public long getBaseCategoryIsDefaultFix() {
/* 200 */     return this.baseCategoryIsDefaultFix;
/*     */   }
/*     */ 
/*     */   public void setBaseCategoryIsDefaultFix(long baseCategoryIsDefaultFix) {
/* 204 */     this.baseCategoryIsDefaultFix = baseCategoryIsDefaultFix;
/*     */   }
/*     */ 
/*     */   public long getBaseCategoryState() {
/* 208 */     return this.baseCategoryState;
/*     */   }
/*     */ 
/*     */   public void setBaseCategoryState(long baseCategoryState) {
/* 212 */     this.baseCategoryState = baseCategoryState;
/*     */   }
/*     */ 
/*     */   public String getBaseCategoryBtnAllIds() {
/* 216 */     return this.baseCategoryBtnAllIds;
/*     */   }
/*     */ 
/*     */   public void setBaseCategoryBtnAllIds(String baseCategoryBtnAllIds) {
/* 220 */     this.baseCategoryBtnAllIds = baseCategoryBtnAllIds;
/*     */   }
/*     */ 
/*     */   public String getBaseCategoryBtnFreeIds() {
/* 224 */     return this.baseCategoryBtnFreeIds;
/*     */   }
/*     */ 
/*     */   public void setBaseCategoryBtnFreeIds(String baseCategoryBtnFreeIds) {
/* 228 */     this.baseCategoryBtnFreeIds = baseCategoryBtnFreeIds;
/*     */   }
/*     */ 
/*     */   public String getBaseCategoryBtnFixIds() {
/* 232 */     return this.baseCategoryBtnFixIds;
/*     */   }
/*     */ 
/*     */   public void setBaseCategoryBtnFixIds(String baseCategoryBtnFixIds) {
/* 236 */     this.baseCategoryBtnFixIds = baseCategoryBtnFixIds;
/*     */   }
/*     */ 
/*     */   public String getBaseCategoryPanelHids() {
/* 240 */     return this.baseCategoryPanelHids;
/*     */   }
/*     */ 
/*     */   public void setBaseCategoryPanelHids(String baseCategoryPanelHids) {
/* 244 */     this.baseCategoryPanelHids = baseCategoryPanelHids;
/*     */   }
/*     */ 
/*     */   public String getBaseCategoryPanelIds() {
/* 248 */     return this.baseCategoryPanelIds;
/*     */   }
/*     */ 
/*     */   public void setBaseCategoryPanelIds(String baseCategoryPanelIds) {
/* 252 */     this.baseCategoryPanelIds = baseCategoryPanelIds;
/*     */   }
/*     */ 
/*     */   public String getBaseCategoryCustomActions() {
/* 256 */     return this.baseCategoryCustomActions;
/*     */   }
/*     */ 
/*     */   public void setBaseCategoryCustomActions(String baseCategoryCustomActions) {
/* 260 */     this.baseCategoryCustomActions = baseCategoryCustomActions;
/*     */   }
/*     */ 
/*     */   public String getBaseCategoryBeginPhaseNo() {
/* 264 */     return this.baseCategoryBeginPhaseNo;
/*     */   }
/*     */ 
/*     */   public void setBaseCategoryBeginPhaseNo(String baseCategoryBeginPhaseNo) {
/* 268 */     this.baseCategoryBeginPhaseNo = baseCategoryBeginPhaseNo;
/*     */   }
/*     */ 
/*     */   public String getPanelGroupID() {
/* 272 */     return this.panelGroupID;
/*     */   }
/*     */ 
/*     */   public void setPanelGroupID(String panelGroupID) {
/* 276 */     this.panelGroupID = panelGroupID;
/*     */   }
/*     */ 
/*     */   public String getTheme() {
/* 280 */     return this.theme;
/*     */   }
/*     */ 
/*     */   public void setTheme(String theme) {
/* 284 */     this.theme = theme;
/*     */   }
/*     */ 
/*     */   public String getFlowType() {
/* 288 */     return this.flowType;
/*     */   }
/*     */ 
/*     */   public void setFlowType(String flowType) {
/* 292 */     this.flowType = flowType;
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.configuration.sort.model.WfType
 * JD-Core Version:    0.6.0
 */