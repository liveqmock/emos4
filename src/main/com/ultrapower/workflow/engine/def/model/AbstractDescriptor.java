/*    */ package com.ultrapower.workflow.engine.def.model;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public abstract class AbstractDescriptor
/*    */   implements Serializable
/*    */ {
/*    */   protected String id;
/*    */   protected String name;
/*    */   protected String value;
/*    */   protected String type;
/*    */   protected String text;
/*    */   protected AbstractDescriptor parent;
/*    */ 
/*    */   public AbstractDescriptor getParent()
/*    */   {
/* 15 */     return this.parent;
/*    */   }
/*    */ 
/*    */   public void setParent(AbstractDescriptor parent) {
/* 19 */     this.parent = parent;
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 23 */     return this.name;
/*    */   }
/*    */ 
/*    */   public void setName(String name) {
/* 27 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public String getValue() {
/* 31 */     return this.value;
/*    */   }
/*    */ 
/*    */   public void setValue(String value) {
/* 35 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public String getId() {
/* 39 */     return this.id;
/*    */   }
/*    */ 
/*    */   public void setId(String id) {
/* 43 */     this.id = id;
/*    */   }
/*    */ 
/*    */   public String getType() {
/* 47 */     return this.type;
/*    */   }
/*    */ 
/*    */   public void setType(String type) {
/* 51 */     this.type = type;
/*    */   }
/*    */ 
/*    */   public String getText() {
/* 55 */     return this.text;
/*    */   }
/*    */ 
/*    */   public void setText(String text) {
/* 59 */     this.text = text;
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.engine.def.model.AbstractDescriptor
 * JD-Core Version:    0.6.0
 */