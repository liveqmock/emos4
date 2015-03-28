/*    */ package com.ultrapower.workflow.utils;
/*    */ 
/*    */ import com.ultrapower.workflow.model.SerialNo;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import org.apache.commons.collections.CollectionUtils;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.dom4j.Document;
/*    */ import org.dom4j.Node;
/*    */ import org.dom4j.io.SAXReader;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ public class WorkflowConfigParser
/*    */ {
/* 21 */   private static Logger log = LoggerFactory.getLogger(WorkflowConfigParser.class);
/*    */ 
/* 23 */   private static Map<String, SerialNo> serMap = new HashMap();
/*    */ 
/*    */   public static void initWorkflowConfig()
/*    */   {
/* 29 */     String path = "/wfengine/configs/workflow-configs.xml";
/* 30 */     log.info("开始加载工单配置文件。path=" + path);
/*    */     try {
/* 32 */       SAXReader reader = new SAXReader();
/* 33 */       Document doc = reader.read(WorkflowConfigParser.class.getResourceAsStream(path));
/* 34 */       List nodes = doc.selectNodes("//workflow-configs/schema");
/* 35 */       if (CollectionUtils.isNotEmpty(nodes))
/* 36 */         for (int i = 0; i < nodes.size(); i++) {
/* 37 */           Node node = (Node)nodes.get(i);
/* 38 */           String code = node.valueOf("@code");
/* 39 */           String serial = node.valueOf("@serialno");
/* 40 */           if ((StringUtils.isNotBlank(code)) && (StringUtils.isNotBlank(serial))) {
/* 41 */             SerialNo ser = new SerialNo();
/* 42 */             ser.setBaseSchema(code);
/* 43 */             ser.setText(serial);
/* 44 */             Pattern p = Pattern.compile("\\{DATE:(.*?)\\}");
/* 45 */             Matcher m = p.matcher(serial);
/* 46 */             if (m.find()) {
/* 47 */               ser.setDateReg(m.group(1));
/*    */             }
/*    */ 
/* 50 */             p = Pattern.compile("\\{ID:(.*?)\\}");
/* 51 */             m = p.matcher(serial);
/* 52 */             if (m.find()) {
/* 53 */               ser.setLen(Integer.parseInt(m.group(1)));
/*    */             }
/* 55 */             serMap.put(code, ser);
/*    */           }
/*    */         }
/*    */     }
/*    */     catch (Exception e) {
/* 60 */       log.error("加载工单配置文件失败！工单配置文件配置有误 或者 不存在！ " + e.getMessage());
/*    */     }
/*    */   }
/*    */ 
/*    */   public static SerialNo getSerialNo(String baseSchema) {
/* 65 */     SerialNo serialNo = (SerialNo)serMap.get(baseSchema);
/* 66 */     if (serialNo == null) {
/* 67 */       serialNo = (SerialNo)serMap.get("*");
/* 68 */       if (serialNo == null) {
/* 69 */         serialNo = new SerialNo();
/*    */       }
/*    */     }
/* 72 */     return serialNo;
/*    */   }
/*    */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.utils.WorkflowConfigParser
 * JD-Core Version:    0.6.0
 */