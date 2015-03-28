/*    */ package com.ultrapower.workflow.engine.core.model;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class DataField
/*    */   implements Serializable
/*    */ {
/*    */   private String name;
/*    */   private FieldDataType type;
/*    */   private String value;
/*    */   private Object objValue;
/*    */ 
/*    */   public DataField()
/*    */   {
/*    */   }
/*    */ 
/*    */   public DataField(String name, FieldDataType type, String value)
/*    */   {
/* 16 */     this.name = name;
/* 17 */     this.type = type;
/* 18 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public DataField(String name, FieldDataType type, Object objValue) {
/* 22 */     this.name = name;
/* 23 */     this.type = type;
/* 24 */     this.objValue = objValue;
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 28 */     return this.name;
/*    */   }
/*    */   public void setName(String name) {
/* 31 */     this.name = name;
/*    */   }
/*    */   public String getValue() {
/* 34 */     return this.value;
/*    */   }
/*    */   public void setValue(String value) {
/* 37 */     this.value = value;
/*    */   }
/*    */   public FieldDataType getType() {
/* 40 */     return this.type;
/*    */   }
/*    */   public void setType(FieldDataType type) {
/* 43 */     this.type = type;
/*    */   }
/*    */ 
/*    */   public Object getObjValue() {
/* 47 */     return this.objValue;
/*    */   }
/*    */ 
/*    */   public void setObjValue(Object objValue) {
/* 51 */     this.objValue = objValue;
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.engine.core.model.DataField
 * JD-Core Version:    0.6.0
 */