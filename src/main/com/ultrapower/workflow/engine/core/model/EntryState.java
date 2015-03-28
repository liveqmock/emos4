/*    */ package com.ultrapower.workflow.engine.core.model;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class EntryState
/*    */   implements Serializable
/*    */ {
/*    */   private String code;
/*    */   private String cnname;
/*    */   private String entryState;
/*    */   private String inProcessState;
/*    */   private String outProcessState;
/*    */   private String desc;
/* 15 */   private boolean isTrans = true;
/*    */ 
/*    */   public EntryState(String code, String cnname, String entryState, String inProcessState, String outProcessState, String desc, String isTrans)
/*    */   {
/* 26 */     this.code = code;
/* 27 */     this.cnname = cnname;
/* 28 */     this.entryState = entryState;
/* 29 */     this.inProcessState = inProcessState;
/* 30 */     this.outProcessState = outProcessState;
/* 31 */     this.desc = desc;
/* 32 */     if (StringUtils.isNotBlank(isTrans))
/* 33 */       this.isTrans = Boolean.parseBoolean(isTrans);
/*    */   }
/*    */ 
/*    */   public String getCode() {
/* 37 */     return this.code;
/*    */   }
/*    */   public void setCode(String code) {
/* 40 */     this.code = code;
/*    */   }
/*    */   public String getCnname() {
/* 43 */     return this.cnname;
/*    */   }
/*    */   public void setCnname(String cnname) {
/* 46 */     this.cnname = cnname;
/*    */   }
/*    */   public String getEntryState() {
/* 49 */     return this.entryState;
/*    */   }
/*    */   public void setEntryState(String entryState) {
/* 52 */     this.entryState = entryState;
/*    */   }
/*    */   public String getInProcessState() {
/* 55 */     return this.inProcessState;
/*    */   }
/*    */   public void setInProcessState(String inProcessState) {
/* 58 */     this.inProcessState = inProcessState;
/*    */   }
/*    */   public String getOutProcessState() {
/* 61 */     return this.outProcessState;
/*    */   }
/*    */   public void setOutProcessState(String outProcessState) {
/* 64 */     this.outProcessState = outProcessState;
/*    */   }
/*    */   public String getDesc() {
/* 67 */     return this.desc;
/*    */   }
/*    */   public void setDesc(String desc) {
/* 70 */     this.desc = desc;
/*    */   }
/*    */   public boolean isTrans() {
/* 73 */     return this.isTrans;
/*    */   }
/*    */   public void setTrans(boolean isTrans) {
/* 76 */     this.isTrans = isTrans;
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.engine.core.model.EntryState
 * JD-Core Version:    0.6.0
 */