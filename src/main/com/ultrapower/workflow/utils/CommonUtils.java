/*    */ package com.ultrapower.workflow.utils;
/*    */ 
/*    */ import com.ultrapower.workflow.engine.core.model.DataField;
/*    */ import java.lang.reflect.ParameterizedType;
/*    */ import java.lang.reflect.Type;
/*    */ import java.util.Map;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class CommonUtils
/*    */ {
/*    */   public static String getString(String str)
/*    */   {
/* 14 */     if (StringUtils.isBlank(str)) {
/* 15 */       return "";
/*    */     }
/* 17 */     return str;
/*    */   }
/*    */ 
/*    */   public static long getLong(String str) {
/* 21 */     long rtn = 0L;
/*    */     try {
/* 23 */       rtn = Long.parseLong(str);
/*    */     } catch (Exception e) {
/* 25 */       rtn = 0L;
/*    */     }
/* 27 */     return rtn;
/*    */   }
/*    */ 
/*    */   public static String getVal(Map<String, DataField> inputs, String key) {
/* 31 */     String str = null;
/* 32 */     if (inputs != null) {
/* 33 */       DataField df = (DataField)inputs.get(key);
/* 34 */       if (df != null) {
/* 35 */         str = df.getValue();
/*    */       }
/*    */     }
/* 38 */     return str;
/*    */   }
/*    */ 
/*    */   public static Class getSuperClassGenricType(Class clazz) {
/* 42 */     return getSuperClassGenricType(clazz, 0);
/*    */   }
/*    */ 
/*    */   public static Class getSuperClassGenricType(Class clazz, int index)
/*    */   {
/* 47 */     Type genType = clazz.getGenericSuperclass();
/*    */ 
/* 49 */     if (!(genType instanceof ParameterizedType)) {
/* 50 */       return Object.class;
/*    */     }
/*    */ 
/* 53 */     Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
/*    */ 
/* 55 */     if ((index >= params.length) || (index < 0)) {
/* 56 */       return Object.class;
/*    */     }
/* 58 */     if (!(params[index] instanceof Class)) {
/* 59 */       return Object.class;
/*    */     }
/* 61 */     return (Class)params[index];
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.utils.CommonUtils
 * JD-Core Version:    0.6.0
 */