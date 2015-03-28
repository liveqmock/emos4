/*     */ package com.ultrapower.workflow.configuration.version.service.impl;
/*     */ 
/*     */ import com.ultrapower.eoms.common.core.component.cache.manager.BaseCacheManager;
/*     */ import com.ultrapower.eoms.common.core.dao.IDao;
/*     */ import com.ultrapower.eoms.common.core.util.TimeUtils;
/*     */ import com.ultrapower.workflow.configuration.sort.model.WfType;
/*     */ import com.ultrapower.workflow.configuration.sort.service.IWfSortService;
/*     */ import com.ultrapower.workflow.configuration.version.model.WfVersion;
/*     */ import com.ultrapower.workflow.engine.def.WorkflowFactory;
/*     */ import com.ultrapower.workflow.engine.def.model.ConditionDescriptor;
/*     */ import com.ultrapower.workflow.engine.def.model.SplitDescriptor;
/*     */ import com.ultrapower.workflow.engine.def.model.WorkflowDescriptor;
/*     */ import com.ultrapower.workflow.exception.WorkflowConfigException;
/*     */ import com.ultrapower.workflow.utils.PropertiesUtils;
/*     */ import com.ultrapower.workflow.utils.UUIDGenerator;
/*     */ import com.ultrapower.workflow.utils.WfEngineUtils;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.commons.collections.CollectionUtils;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.lang.ArrayUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.DocumentException;
/*     */ import org.dom4j.DocumentHelper;
/*     */ import org.dom4j.io.OutputFormat;
/*     */ import org.dom4j.io.SAXReader;
/*     */ import org.dom4j.io.XMLWriter;
/*     */ import org.slf4j.Logger;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Transactional
/*     */ public class XMLWfVersionService extends AbstractWfVersionService
/*     */ {
/*  47 */   private String path = XMLWfVersionService.class.getResource("/").getPath();
/*     */ 
/*     */   public boolean removeById(String id) {
/*  50 */     WfVersion wfVersion = getById(id);
/*  51 */     String baseCode = wfVersion.getBaseCode();
/*  52 */     String defName = wfVersion.getCode();
/*  53 */     removeFile(baseCode, defName);
/*  54 */     this.verDao.removeById(id);
/*     */ 
/*  56 */     BaseCacheManager.removeElement("WFVersion", wfVersion.getId());
/*  57 */     this.factory.removeCache(defName);
/*  58 */     System.out.println("删除成功");
/*  59 */     return true;
/*     */   }
/*     */ 
/*     */   public void init()
/*     */   {
/*  66 */     List versions = this.verDao.getAll();
/*  67 */     SAXReader reader = new SAXReader();
/*  68 */     if (CollectionUtils.isNotEmpty(versions)) {
/*  69 */       for (int i = 0; i < versions.size(); i++) {
/*  70 */         WfVersion ver = ((WfVersion)versions.get(i)).clone();
/*  71 */         String id = ver.getId();
/*  72 */         String baseCode = ver.getBaseCode();
/*  73 */         String defName = ver.getCode();
/*  74 */         String flowPath = getPath(baseCode, defName, true);
/*  75 */         String designPath = getPath(baseCode, defName, false);
/*     */         try {
/*  77 */           File flow = new File(flowPath);
/*  78 */           File design = new File(designPath);
/*  79 */           if ((flow.exists()) && (design.exists())) {
/*  80 */             Document flowDoc = reader.read(flow);
/*  81 */             Document designDoc = reader.read(design);
/*  82 */             ver.setWorkflowXml(flowDoc.asXML());
/*  83 */             ver.setDesignXml(designDoc.asXML());
/*     */ 
/*  85 */             BaseCacheManager.put("WFVersion", id, ver);
/*     */           } else {
/*  87 */             log.info("'" + ver.getName() + "'文件不存在");
/*     */           }
/*     */         } catch (DocumentException e) {
/*  90 */           e.printStackTrace();
/*  91 */           throw new WorkflowConfigException("读取文件出错！baseCode=" + baseCode + ",baseName=" + defName);
/*     */         }
/*     */       }
/*     */     }
/*  95 */     this.sortServiceImpl.getAllWfType();
/*     */   }
/*     */ 
/*     */   public boolean startVersion(String id)
/*     */   {
/* 133 */     WfVersion versionInCache = (WfVersion)BaseCacheManager.get("WFVersion", id);
/* 134 */     if (versionInCache != null) {
/* 135 */       String baseCode = versionInCache.getBaseCode();
/* 136 */       long now = TimeUtils.getCurrentTime();
/* 137 */       versionInCache.setIsPublish(1L);
/* 138 */       versionInCache.setPublishTime(now);
/* 139 */       WfVersion clone = versionInCache.clone();
/* 140 */       clone.setDesignXml(null);
/* 141 */       clone.setWorkflowXml(null);
/* 142 */       this.verDao.saveOrUpdate(clone);
/*     */ 
/* 144 */       BaseCacheManager.put("WFVersion", id, versionInCache);
/* 145 */       WfType wfType = this.sortServiceImpl.getWfTypeByCode(baseCode);
/* 146 */       wfType.setWfDefaultVersion(versionInCache.getCode());
/* 147 */       this.sortServiceImpl.saveWfType(wfType);
/*     */     }
/* 149 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean stopVersion(String id)
/*     */   {
/* 158 */     WfVersion versionInCache = (WfVersion)BaseCacheManager.get("WFVersion", id);
/* 159 */     if (versionInCache != null) {
/* 160 */       String baseCode = versionInCache.getBaseCode();
/* 161 */       long now = TimeUtils.getCurrentTime();
/* 162 */       versionInCache.setIsPublish(0L);
/* 163 */       versionInCache.setPublishTime(now);
/* 164 */       WfVersion clone = versionInCache.clone();
/* 165 */       clone.setDesignXml(null);
/* 166 */       clone.setWorkflowXml(null);
/* 167 */       this.verDao.saveOrUpdate(clone);
/*     */ 
/* 169 */       BaseCacheManager.put("WFVersion", id, versionInCache);
/* 170 */       WfType wfType = this.sortServiceImpl.getWfTypeByCode(baseCode);
/* 171 */       if ((wfType != null) && 
/* 172 */         (versionInCache.getCode().equals(wfType.getWfDefaultVersion()))) {
/* 173 */         wfType.setWfDefaultVersion(null);
/* 174 */         this.sortServiceImpl.saveWfType(wfType);
/*     */       }
/*     */     }
/*     */ 
/* 178 */     return true;
/*     */   }
/*     */ 
/*     */   public WfVersion exportWf(String code)
/*     */   {
/* 183 */     return null;
/*     */   }
/*     */ 
/*     */   public boolean importWf(WfVersion verion) {
/* 187 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean saveWfVersion(WfVersion version, boolean asNew) {
/* 191 */     if (StringUtils.isBlank(version.getId())) {
/* 192 */       version.setId(UUIDGenerator.getId());
/*     */     }
/*     */ 
/* 199 */     if (asNew) {
/* 200 */       version.setId(UUIDGenerator.getId());
/* 201 */       version.setTodayEntryCount(0L);
/* 202 */       version.setEntryCount(0L);
/* 203 */       version.setIsPublish(0L);
/* 204 */       version.setCreateTime(TimeUtils.getCurrentTime());
/*     */     }
/*     */ 
/* 208 */     WfVersion cacheVer = version.clone();
/* 209 */     String baseCode = version.getBaseCode();
/* 210 */     String designXml = version.getDesignXml();
/* 211 */     String workflowXml = version.getWorkflowXml();
/* 212 */     String defName = version.getCode();
/* 213 */     version.setDesignXml(null);
/* 214 */     version.setWorkflowXml(null);
/* 215 */     this.verDao.merge(version);
/*     */     try {
/* 217 */       saveFile(baseCode, defName, workflowXml, designXml);
/*     */     } catch (Exception e) {
/* 219 */       e.printStackTrace();
/* 220 */       throw new WorkflowConfigException("保存文件失败！");
/*     */     }
/*     */ 
/* 223 */     BaseCacheManager.put("WFVersion", version.getId(), cacheVer);
/* 224 */     this.factory.putCache(defName, workflowXml);
/* 225 */     return true;
/*     */   }
/*     */ 
/*     */   private void saveFile(String baseCode, String defName, String flowXml, String designXml) throws IOException, DocumentException {
/* 229 */     baseCode = baseCode.replace(":", "_").toUpperCase().toUpperCase();
/* 230 */     defName = defName.replace(":", "_").toUpperCase();
/* 231 */     String flowXmlPath = getPath(baseCode, defName, true);
/* 232 */     String designXmlPath = getPath(baseCode, defName, false);
/* 233 */     log.info("流程定义文件 flowXmlPath = " + flowXmlPath);
/* 234 */     log.info("流程定义坐标文件 designXml = " + designXmlPath);
/* 235 */     File file = new File(flowXmlPath);
/* 236 */     FileUtils.touch(file);
/* 237 */     file.createNewFile();
/* 238 */     OutputFormat format = OutputFormat.createPrettyPrint();
/* 239 */     XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
/* 240 */     writer.write(DocumentHelper.parseText(flowXml));
/* 241 */     writer.close();
/*     */ 
/* 243 */     file = new File(designXmlPath);
/* 244 */     FileUtils.touch(file);
/* 245 */     file.createNewFile();
/* 246 */     writer = new XMLWriter(new FileOutputStream(file), format);
/* 247 */     writer.write(DocumentHelper.parseText(designXml));
/* 248 */     writer.close();
/*     */   }
/*     */ 
/*     */   private void removeFile(String baseCode, String defName) {
/* 252 */     baseCode = baseCode.replace(":", "_").toUpperCase();
/* 253 */     defName = defName.replace(":", "_").toUpperCase();
/* 254 */     String flowXmlPath = getPath(baseCode, defName, true);
/* 255 */     String designXmlPath = getPath(baseCode, defName, false);
/* 256 */     boolean deleteFlow = new File(flowXmlPath).delete();
/* 257 */     boolean delteDesign = new File(designXmlPath).delete();
/* 258 */     log.info("删除流程定义文件！baseCode=" + baseCode + ",defName=" + defName + ",deleteFlow=" + deleteFlow + ",delteDesign=" + delteDesign);
/*     */   }
/*     */ 
/*     */   private String getPath(String baseCode, String defName, boolean isFlow) {
/* 262 */     baseCode = baseCode.replace(":", "_").toUpperCase();
/* 263 */     defName = defName.replace(":", "_").toUpperCase();
/* 264 */     String flowXmlPath = null;
/* 265 */     String designXmlPath = null;
/* 266 */     String path = PropertiesUtils.getProp("flowSavePath");
/* 267 */     String tmpFlowPath = baseCode + File.separator + defName + ".xml";
/* 268 */     String tmpDesignPath = baseCode + File.separator + defName + "_design.xml";
/* 269 */     if (StringUtils.isNotBlank(path)) {
/* 270 */       if (path.endsWith(File.separator)) {
/* 271 */         flowXmlPath = path + tmpFlowPath;
/* 272 */         designXmlPath = path + tmpDesignPath;
/*     */       } else {
/* 274 */         flowXmlPath = path + File.separator + tmpFlowPath;
/* 275 */         designXmlPath = path + File.separator + tmpDesignPath;
/*     */       }
/*     */     } else {
/* 278 */       flowXmlPath = this.path + File.separator + "wfengine" + File.separator + "workflows" + File.separator + tmpFlowPath;
/* 279 */       designXmlPath = this.path + File.separator + "wfengine" + File.separator + "workflows" + File.separator + tmpDesignPath;
/*     */     }
/* 281 */     if (isFlow) {
/* 282 */       return flowXmlPath;
/*     */     }
/* 284 */     return designXmlPath;
/*     */   }
/*     */ 
/*     */   public synchronized String getSerialNum(String baseSchema, String versionCode)
/*     */   {
/* 290 */     return super.getSerialNum(baseSchema, versionCode);
/*     */   }
/*     */ 
/*     */   public List<String> getConditions(String baseSchema, String defName)
/*     */   {
/* 299 */     List conList = new ArrayList();
/* 300 */     if (StringUtils.isBlank(defName)) {
/* 301 */       defName = WfEngineUtils.getDefVersionBySchema(baseSchema);
/* 302 */       if (StringUtils.isBlank(defName)) {
/* 303 */         return conList;
/*     */       }
/*     */     }
/* 306 */     WorkflowDescriptor wfDesc = this.factory.getWorkflowDescriptor(defName);
/* 307 */     if (wfDesc != null) {
/* 308 */       Map spMap = wfDesc.getSplitDescMap();
/* 309 */       if (spMap != null) {
/* 310 */         Set keySet = spMap.keySet();
/* 311 */         Iterator it = keySet.iterator();
/* 312 */         while (it.hasNext()) {
/* 313 */           String id = (String)it.next();
/* 314 */           SplitDescriptor spDesc = wfDesc.getSplitDescriptor(id);
/* 315 */           ConditionDescriptor conDesc = spDesc.getConDesc();
/* 316 */           String text = conDesc.getText();
/*     */ 
/* 318 */           if (StringUtils.isNotBlank(text)) {
/* 319 */             String[] splits = text.split("#");
/* 320 */             if (!ArrayUtils.isEmpty(splits)) {
/* 321 */               for (int j = 0; j < splits.length; j++) {
/* 322 */                 String reg = ".*\\$(.*)\\$.*";
/* 323 */                 Pattern p = Pattern.compile(reg);
/* 324 */                 Matcher m = p.matcher(splits[j]);
/* 325 */                 while (m.find()) {
/* 326 */                   String group = m.group(1);
/* 327 */                   if (!conList.contains(group)) {
/* 328 */                     conList.add(group);
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 337 */     conList.remove("else");
/* 338 */     return conList;
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.configuration.version.service.impl.XMLWfVersionService
 * JD-Core Version:    0.6.0
 */