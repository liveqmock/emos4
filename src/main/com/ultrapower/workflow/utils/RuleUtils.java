/*    */ package com.ultrapower.workflow.utils;
/*    */ 
/*    */ import com.ultrapower.eoms.common.core.component.rule.RuleException;
/*    */ import com.ultrapower.eoms.common.core.component.rule.RuleExpression;
/*    */ import com.ultrapower.workflow.engine.core.model.DataField;
/*    */ import com.ultrapower.workflow.exception.WorkflowEngineException;
/*    */ import java.io.PrintStream;
/*    */ import java.util.Map;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import org.apache.commons.collections.map.CaseInsensitiveMap;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ public class RuleUtils
/*    */ {
/* 19 */   private static Logger log = LoggerFactory.getLogger(RuleUtils.class);
/*    */ 
/*    */   public static boolean paseRule(String ruleTxt, Map<String, DataField> inputs) {
/* 22 */     boolean flag = true;
/* 23 */     if (StringUtils.isNotBlank(ruleTxt)) {
/* 24 */       String reg = ".*\\$(.*)\\$.*";
/* 25 */       Pattern p = Pattern.compile(reg);
/* 26 */       Matcher m = p.matcher(ruleTxt);
/* 27 */       while (m.find()) {
/* 28 */         String dataValue = "null";
/* 29 */         String group = m.group(1);
/* 30 */         CaseInsensitiveMap caseMap = new CaseInsensitiveMap(inputs);
/* 31 */         Object obj = caseMap.get(group);
/* 32 */         if (obj != null) {
/* 33 */           DataField data = (DataField)obj;
/* 34 */           dataValue = data.getValue();
/*    */         } else {
/* 36 */           log.info("input中没有[" + group + "]！！！！！");
/*    */         }
/* 38 */         ruleTxt = ruleTxt.replace("$" + group + "$", dataValue);
/* 39 */         m = p.matcher(ruleTxt);
/*    */       }
/*    */       try {
/* 42 */         log.info("开始解析规则串 : " + ruleTxt);
/* 43 */         flag = Boolean.parseBoolean(RuleExpression.execute(ruleTxt));
/*    */       } catch (RuleException e) {
/* 45 */         e.printStackTrace();
/* 46 */         throw new WorkflowEngineException(e);
/*    */       }
/*    */     }
/* 49 */     return flag;
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 55 */     String s = "'${Crea1teUser}' = 'wangxiaodong' || '${8882User}' = 'wangxiaodong'${CreateUs3er}' = 'wangxiaodong' || '${8488User}' = 'wangxiaodong'";
/*    */ 
/* 58 */     String reg = ".*\\$\\{([^\\$]*)\\}.*";
/* 59 */     Pattern p = Pattern.compile(reg);
/* 60 */     Matcher m = p.matcher(s);
/*    */ 
/* 62 */     while (m.find()) {
/* 63 */       String group = m.group(1);
/* 64 */       System.out.println(group);
/* 65 */       s = s.replace("${" + group + "}", "123");
/* 66 */       m = p.matcher(s);
/*    */     }
/*    */ 
/* 69 */     System.out.println("--" + s);
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.utils.RuleUtils
 * JD-Core Version:    0.6.0
 */