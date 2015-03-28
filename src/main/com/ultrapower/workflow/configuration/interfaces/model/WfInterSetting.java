/*    */ package com.ultrapower.workflow.configuration.interfaces.model;
/*    */ 
/*    */ import com.ultrapower.workflow.utils.UUIDGenerator;
/*    */ import java.io.Serializable;
/*    */ import javax.persistence.Entity;
/*    */ import javax.persistence.Id;
/*    */ import javax.persistence.Table;
/*    */ 
/*    */ @Entity
/*    */ @Table(name="BS_T_WF_INTERFACESETTING")
/*    */ public class WfInterSetting
/*    */   implements Serializable
/*    */ {
/*    */   private String id;
/*    */   private String code;
/*    */   private String type;
/*    */   private String interfaceCode;
/*    */ 
/*    */   public WfInterSetting()
/*    */   {
/*    */   }
/*    */ 
/*    */   public WfInterSetting(String id, String code, String type, String interfaceCode)
/*    */   {
/* 37 */     this.id = UUIDGenerator.getId();
/* 38 */     this.code = code;
/* 39 */     this.type = type;
/* 40 */     this.interfaceCode = interfaceCode;
/*    */   }
/*    */ 
/*    */   @Id
/*    */   public String getId() {
/* 46 */     return this.id;
/*    */   }
/*    */ 
/*    */   public void setId(String id) {
/* 50 */     this.id = id;
/*    */   }
/*    */ 
/*    */   public String getInterfaceCode() {
/* 54 */     return this.interfaceCode;
/*    */   }
/*    */ 
/*    */   public void setInterfaceCode(String interfaceCode) {
/* 58 */     this.interfaceCode = interfaceCode;
/*    */   }
/*    */ 
/*    */   public String getCode() {
/* 62 */     return this.code;
/*    */   }
/*    */ 
/*    */   public void setCode(String code) {
/* 66 */     this.code = code;
/*    */   }
/*    */ 
/*    */   public String getType() {
/* 70 */     return this.type;
/*    */   }
/*    */ 
/*    */   public void setType(String type) {
/* 74 */     this.type = type;
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.configuration.interfaces.model.WfInterSetting
 * JD-Core Version:    0.6.0
 */