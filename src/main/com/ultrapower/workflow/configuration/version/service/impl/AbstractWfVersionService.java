/*     */ package com.ultrapower.workflow.configuration.version.service.impl;
/*     */ 
/*     */ import com.ultrapower.eoms.common.core.component.cache.manager.BaseCacheManager;
/*     */ import com.ultrapower.eoms.common.core.dao.IDao;
/*     */ import com.ultrapower.eoms.common.core.util.TimeUtils;
/*     */ import com.ultrapower.workflow.configuration.sort.model.WfType;
/*     */ import com.ultrapower.workflow.configuration.sort.service.IWfSortService;
/*     */ import com.ultrapower.workflow.configuration.version.model.WfVersion;
/*     */ import com.ultrapower.workflow.configuration.version.service.IWfVersionService;
/*     */ import com.ultrapower.workflow.engine.def.WorkflowFactory;
/*     */ import com.ultrapower.workflow.model.SerialNo;
/*     */ import com.ultrapower.workflow.utils.WorkflowConfigParser;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Transactional
/*     */ public abstract class AbstractWfVersionService
/*     */   implements IWfVersionService
/*     */ {
/*  31 */   protected static Logger log = LoggerFactory.getLogger(AbstractWfVersionService.class);
/*     */   public WorkflowFactory factory;
/*     */   protected IDao<WfVersion> verDao;
/*     */   public IWfSortService sortServiceImpl;
/*     */ 
/*     */   public IDao<WfVersion> getVerDao()
/*     */   {
/*  41 */     return this.verDao;
/*     */   }
/*     */ 
/*     */   public void setVerDao(IDao<WfVersion> verDao) {
/*  45 */     this.verDao = verDao;
/*     */   }
/*     */ 
/*     */   public WorkflowFactory getFactory() {
/*  49 */     return this.factory;
/*     */   }
/*     */ 
/*     */   public void setFactory(WorkflowFactory factory) {
/*  53 */     this.factory = factory;
/*     */   }
/*     */ 
/*     */   public List<WfVersion> getEnableListByBaseCode(String baseCode)
/*     */   {
/*  60 */     List enableList = new ArrayList();
/*     */ 
/*  74 */     List eleList = BaseCacheManager.getAllElementByCacheName("WFVersion");
/*  75 */     for (Iterator it = eleList.iterator(); it.hasNext(); )
/*     */     {
/*  77 */       WfVersion version = (WfVersion)it.next();
/*  78 */       if (version.getIsPublish() != 1L)
/*     */         continue;
/*  80 */       enableList.add(version);
/*     */     }
/*     */ 
/*  84 */     return enableList;
/*     */   }
/*     */ 
/*     */   public WfVersion getById(String id) {
/*  88 */     return (WfVersion)BaseCacheManager.get("WFVersion", id);
/*     */   }
/*     */ 
/*     */   public List<WfVersion> getListByBaseCode(String baseCode)
/*     */   {
/*  95 */     List verList = new ArrayList();
/*     */ 
/* 109 */     List eleList = BaseCacheManager.getAllElementByCacheName("WFVersion");
/* 110 */     for (Iterator it = eleList.iterator(); it.hasNext(); )
/*     */     {
/* 112 */       WfVersion version = (WfVersion)it.next();
/* 113 */       if (!version.getBaseCode().equalsIgnoreCase(baseCode))
/*     */         continue;
/* 115 */       verList.add(version);
/*     */     }
/*     */ 
/* 119 */     return verList;
/*     */   }
/*     */ 
/*     */   public List<WfVersion> getMainListByBaseCode(String baseCode)
/*     */   {
/* 126 */     List verList = new ArrayList();
/*     */ 
/* 140 */     List eleList = BaseCacheManager.getAllElementByCacheName("WFVersion");
/* 141 */     for (Iterator it = eleList.iterator(); it.hasNext(); )
/*     */     {
/* 143 */       WfVersion version = (WfVersion)it.next();
/* 144 */       if ((!version.getBaseCode().equalsIgnoreCase(baseCode)) || (version.getSubflag() == 1L))
/*     */         continue;
/* 146 */       verList.add(version);
/*     */     }
/*     */ 
/* 150 */     return verList;
/*     */   }
/*     */ 
/*     */   public List<WfVersion> getSubListByBaseCode(String baseCode)
/*     */   {
/* 157 */     List verList = new ArrayList();
/*     */ 
/* 170 */     List eleList = BaseCacheManager.getAllElementByCacheName("WFVersion");
/* 171 */     for (Iterator it = eleList.iterator(); it.hasNext(); )
/*     */     {
/* 173 */       WfVersion version = (WfVersion)it.next();
/* 174 */       if ((!version.getBaseCode().equalsIgnoreCase(baseCode)) || (version.getSubflag() != 1L))
/*     */         continue;
/* 176 */       verList.add(version);
/*     */     }
/*     */ 
/* 180 */     return verList;
/*     */   }
/*     */ 
/*     */   public boolean startVersion(String id)
/*     */   {
/* 189 */     WfVersion versionInCache = (WfVersion)BaseCacheManager.get("WFVersion", id);
/* 190 */     if (versionInCache != null) {
/* 191 */       long now = TimeUtils.getCurrentTime();
/* 192 */       versionInCache.setIsPublish(1L);
/* 193 */       versionInCache.setPublishTime(now);
/* 194 */       this.verDao.saveOrUpdate(versionInCache);
/*     */ 
/* 196 */       BaseCacheManager.put("WFVersion", versionInCache.getId(), versionInCache);
/*     */     }
/* 198 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean stopVersion(String id)
/*     */   {
/* 207 */     WfVersion versionInCache = (WfVersion)BaseCacheManager.get("WFVersion", id);
/* 208 */     if (versionInCache != null) {
/* 209 */       long now = TimeUtils.getCurrentTime();
/* 210 */       versionInCache.setIsPublish(0L);
/* 211 */       versionInCache.setPublishTime(now);
/* 212 */       this.verDao.saveOrUpdate(versionInCache);
/*     */ 
/* 214 */       BaseCacheManager.put("WFVersion", versionInCache.getId(), versionInCache);
/*     */     }
/* 216 */     return true;
/*     */   }
/*     */ 
/*     */   public WfVersion getByCode(String verCode)
/*     */   {
/* 223 */     WfVersion version = null;
/*     */ 
/* 237 */     List eleList = BaseCacheManager.getAllElementByCacheName("WFVersion");
/* 238 */     for (Iterator it = eleList.iterator(); it.hasNext(); )
/*     */     {
/* 240 */       WfVersion ver = (WfVersion)it.next();
/* 241 */       if (!ver.getCode().equals(verCode))
/*     */         continue;
/* 243 */       version = ver;
/*     */     }
/*     */ 
/* 247 */     return version;
/*     */   }
/*     */ 
/*     */   public List<WfVersion> getAllWfList()
/*     */   {
/* 254 */     List verList = new ArrayList();
/*     */ 
/* 266 */     verList = BaseCacheManager.getAllElementByCacheName("WFVersion");
/*     */ 
/* 268 */     return verList;
/*     */   }
/*     */ 
/*     */   public synchronized String getSerialNum(String baseSchema, String verCode)
/*     */   {
/* 277 */     SerialNo serialNo = WorkflowConfigParser.getSerialNo(baseSchema);
/* 278 */     String serText = serialNo.getText();
/* 279 */     WfType wfType = this.sortServiceImpl.updateEntryCount(baseSchema);
/* 280 */     if (wfType != null) {
/* 281 */       serText = serText.replace("{KEY}", wfType.getBaseCategoryCode());
/* 282 */       serText = serText.replaceAll("\\{DATE:.*?\\}", TimeUtils.getCurrentDate(serialNo.getDateReg()));
/* 283 */       serText = serText.replaceAll("\\{ID:.*?\\}", "");
/* 284 */       String numStr = "00000000000000000000000" + wfType.getTodayEntryCount();
/* 285 */       serText = serText + numStr.substring(numStr.length() - serialNo.getLen());
/*     */     }
/*     */ 
/* 300 */     log.info("生成的流水号,baseSchema=" + baseSchema + ",serialNum=" + serText);
/* 301 */     return serText;
/*     */   }
/*     */ 
/*     */   public IWfSortService getSortServiceImpl() {
/* 305 */     return this.sortServiceImpl;
/*     */   }
/*     */ 
/*     */   public void setSortServiceImpl(IWfSortService sortServiceImpl) {
/* 309 */     this.sortServiceImpl = sortServiceImpl;
/*     */   }
/*     */ 
/*     */   public List<String> getConditions(String baseSchema, String defName) {
/* 313 */     return null;
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.configuration.version.service.impl.AbstractWfVersionService
 * JD-Core Version:    0.6.0
 */