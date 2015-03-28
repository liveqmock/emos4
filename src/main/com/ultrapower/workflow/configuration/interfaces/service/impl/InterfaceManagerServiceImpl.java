/*     */ package com.ultrapower.workflow.configuration.interfaces.service.impl;
/*     */ 
/*     */ import com.ultrapower.eoms.common.core.component.cache.manager.BaseCacheManager;
/*     */ import com.ultrapower.eoms.common.core.dao.IDao;
/*     */ import com.ultrapower.workflow.configuration.interfaces.model.WfInterSetting;
/*     */ import com.ultrapower.workflow.configuration.interfaces.model.WfInterface;
/*     */ import com.ultrapower.workflow.configuration.interfaces.service.IInterfaceManagerService;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Transactional
/*     */ public class InterfaceManagerServiceImpl
/*     */   implements IInterfaceManagerService
/*     */ {
/*     */   private IDao<WfInterface> infDao;
/*     */   private IDao<WfInterSetting> setDao;
/*     */ 
/*     */   public boolean removeInterByCode(String code)
/*     */   {
/*  26 */     WfInterface wf = (WfInterface)BaseCacheManager.get("WFInterface", code);
/*  27 */     this.infDao.removeById(wf.getId());
/*     */ 
/*  29 */     BaseCacheManager.removeElement("WFInterface", code);
/*  30 */     return true;
/*     */   }
/*     */ 
/*     */   public List<WfInterface> getAllInte() {
/*  34 */     List wflist = new ArrayList();
/*     */ 
/*  42 */     wflist = BaseCacheManager.getAllElementByCacheName("WFInterface");
/*     */ 
/*  44 */     return wflist;
/*     */   }
/*     */ 
/*     */   public WfInterface getInteByCode(String wfInteCode)
/*     */   {
/*  51 */     return (WfInterface)BaseCacheManager.get("WFInterface", wfInteCode);
/*     */   }
/*     */ 
/*     */   public List<WfInterSetting> getInteByWfCodeAndType(String code, String type) {
/*  55 */     String hql = "from WfInterSetting where code='" + code + "' and type='" + type + "'";
/*  56 */     return this.setDao.find(hql, null);
/*     */   }
/*     */ 
/*     */   public List<WfInterface> getInterfaceListByType(String type)
/*     */   {
/*  64 */     List list = new ArrayList();
/*  65 */     if ((type == null) || (type.equals(""))) {
/*  66 */       return list;
/*     */     }
/*     */ 
/*  77 */     List eleList = BaseCacheManager.getAllElementByCacheName("WFInterface");
/*  78 */     for (Iterator it = eleList.iterator(); it.hasNext(); )
/*     */     {
/*  80 */       WfInterface face = (WfInterface)it.next();
/*  81 */       if (!face.getType().equals(type))
/*     */         continue;
/*  83 */       list.add(face);
/*     */     }
/*     */ 
/*  87 */     return list;
/*     */   }
/*     */ 
/*     */   public WfInterSetting getInteSettingById(String id) {
/*  91 */     return (WfInterSetting)this.setDao.get(id);
/*     */   }
/*     */ 
/*     */   public List<WfInterSetting> getInteSettingByWfCode(String wfCode) {
/*  95 */     String hql = "from WfInterSetting where code='" + wfCode + "'";
/*  96 */     return this.setDao.find(hql, null);
/*     */   }
/*     */ 
/*     */   private void init()
/*     */   {
/* 104 */     List list = this.infDao.getAll();
/* 105 */     for (WfInterface wfi : list)
/*     */     {
/* 107 */       BaseCacheManager.put("WFInterface", wfi.getCode(), wfi);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean saveOrUpdateInte(WfInterface wfInte) {
/* 112 */     this.infDao.saveOrUpdate(wfInte);
/*     */ 
/* 114 */     BaseCacheManager.put("WFInterface", wfInte.getCode(), wfInte);
/* 115 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean saveOrUpdateSetting(WfInterSetting entity) {
/* 119 */     this.setDao.saveOrUpdate(entity);
/* 120 */     return true;
/*     */   }
/*     */ 
/*     */   public IDao<WfInterface> getInfDao() {
/* 124 */     return this.infDao;
/*     */   }
/*     */ 
/*     */   public void setInfDao(IDao<WfInterface> infDao) {
/* 128 */     this.infDao = infDao;
/*     */   }
/*     */ 
/*     */   public IDao<WfInterSetting> getSetDao() {
/* 132 */     return this.setDao;
/*     */   }
/*     */ 
/*     */   public void setSetDao(IDao<WfInterSetting> setDao) {
/* 136 */     this.setDao = setDao;
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.configuration.interfaces.service.impl.InterfaceManagerServiceImpl
 * JD-Core Version:    0.6.0
 */