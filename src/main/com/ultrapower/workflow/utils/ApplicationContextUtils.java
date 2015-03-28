/*   */ package com.ultrapower.workflow.utils;
/*   */ 
/*   */ import org.springframework.context.ApplicationContext;
/*   */ 
/*   */ public class ApplicationContextUtils
/*   */ {
/* 6 */   public static ApplicationContext ctx = null;
/*   */ 
/*   */   public static Object getBean(String beanName) {
/* 9 */     return ctx.getBean(beanName);
/*   */   }
/*   */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.utils.ApplicationContextUtils
 * JD-Core Version:    0.6.0
 */