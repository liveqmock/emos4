/*     */ package com.ultrapower.workflow.configuration.sort.service.impl;
/*     */ 
/*     */ import com.ultrapower.eoms.common.core.component.cache.manager.BaseCacheManager;
/*     */ import com.ultrapower.eoms.common.core.dao.IDao;
/*     */ import com.ultrapower.eoms.common.core.util.TimeUtils;
/*     */ import com.ultrapower.workflow.configuration.sort.model.WfSort;
/*     */ import com.ultrapower.workflow.configuration.sort.model.WfType;
/*     */ import com.ultrapower.workflow.configuration.sort.service.IWfSortService;
/*     */ import java.io.Serializable;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.apache.commons.collections.CollectionUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Transactional
/*     */ public class WfSortServiceImpl
/*     */   implements IWfSortService
/*     */ {
/*     */   private IDao<WfSort> sortDao;
/*     */   private IDao<WfType> typeDao;
/*  33 */   protected static Logger log = LoggerFactory.getLogger(WfSortServiceImpl.class);
/*     */ 
/*     */   public boolean delWfSort(String id)
/*     */   {
/*  38 */     this.sortDao.removeById(id);
/*  39 */     List list = getWfTypeBySortId(id);
/*  40 */     for (WfType wf : list) {
/*  41 */       delWfTypeById(wf.getId());
/*     */     }
/*  43 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean delWfTypeById(String id) {
/*  47 */     WfType wfType = (WfType)this.typeDao.get(id);
/*     */ 
/*  49 */     BaseCacheManager.removeElement("WFType", wfType.getCode());
/*  50 */     this.typeDao.removeById(id);
/*  51 */     return true;
/*     */   }
/*     */ 
/*     */   public List<WfSort> getAllWfSort() {
/*  55 */     String hql = "from WfSort w order by w.orderby";
/*  56 */     return this.sortDao.find(hql, new Object[0]);
/*     */   }
/*     */ 
/*     */   public List<WfSort> getChildSortById(String sortId) {
/*  60 */     String hql = "from WfSort w where w.pid=? order by w.orderby";
/*  61 */     return this.sortDao.find(hql, new Object[] { sortId });
/*     */   }
/*     */ 
/*     */   public WfSort getWfSortByid(String id) {
/*  65 */     return (WfSort)this.sortDao.get(id);
/*     */   }
/*     */ 
/*     */   public WfSort getWfSortByCode(String code) {
/*  69 */     return (WfSort)this.sortDao.findUnique("from WfSort w where w.code=?", new Object[] { code });
/*     */   }
/*     */ 
/*     */   public List<WfType> getWfTypeBySortId(String sortId) {
/*  73 */     String hql = "from WfType where sortId = '" + sortId + "'";
/*  74 */     List list = this.sortDao.find(hql, null);
/*  75 */     return list;
/*     */   }
/*     */ 
/*     */   public WfType getWfTypeByid(String typeId)
/*     */   {
/*  82 */     return (WfType)this.typeDao.get(typeId);
/*     */   }
/*     */ 
/*     */   public WfType getWfTypeByCode(String typeCode)
/*     */   {
/*  90 */     WfType wfType = null;
/*  91 */     Serializable ser = BaseCacheManager.get("WFType", typeCode);
/*  92 */     if (ser != null) {
/*  93 */       wfType = (WfType)ser;
/*     */     }
/*  95 */     return wfType;
/*     */   }
/*     */ 
/*     */   public List<WfType> getAllWfType()
/*     */   {
/* 102 */     List cacheElements = BaseCacheManager.getAllElementByCacheName("WFType");
/* 103 */     if (CollectionUtils.isNotEmpty(cacheElements)) {
/* 104 */       return cacheElements;
/*     */     }
/* 106 */     String hql = "from WfType";
/* 107 */     List list = this.sortDao.find(hql, null);
/* 108 */     if (CollectionUtils.isNotEmpty(list)) {
/* 109 */       for (int i = 0; i < list.size(); i++) {
/* 110 */         WfType type = (WfType)list.get(i);
/*     */ 
/* 112 */         BaseCacheManager.put("WFType", type.getCode(), type);
/*     */       }
/*     */     }
/* 115 */     return list;
/*     */   }
/*     */   public boolean saveWfSort(WfSort wfSort) {
/* 118 */     this.sortDao.saveOrUpdate(wfSort);
/* 119 */     return true;
/*     */   }
/*     */ 
/*     */   public WfType updateEntryCount(String baseSchema) {
/* 123 */     WfType wfType = null;
/* 124 */     if (StringUtils.isNotBlank(baseSchema)) {
/* 125 */       long now = TimeUtils.getCurrentTime();
/* 126 */       Serializable ser = BaseCacheManager.get("WFType", baseSchema);
/* 127 */       if (ser != null) {
/* 128 */         wfType = (WfType)ser;
/*     */         try {
/* 130 */           Connection conn = this.typeDao.getConn();
/* 131 */           long entryCount = 0L;
/* 132 */           long todayEntryCount = 0L;
/* 133 */           long lastEntryTime = 0L;
/* 134 */           String wfCountType = null;
/* 135 */           Statement st = conn.createStatement();
/* 136 */           String sql = "select t.entryCount entryCount, t.todayEntryCount todayEntryCount, t.lastEntryTime lastEntryTime,t.wfCountType wfCountType from BS_T_WF_TYPE t where code='" + baseSchema + "'";
/* 137 */           ResultSet rs = st.executeQuery(sql);
/* 138 */           if (rs != null) {
/* 139 */             if (rs.next()) {
/* 140 */               entryCount = rs.getLong("entryCount");
/* 141 */               todayEntryCount = rs.getLong("todayEntryCount");
/* 142 */               lastEntryTime = rs.getLong("lastEntryTime");
/* 143 */               wfCountType = rs.getString("wfCountType");
/*     */             }
/*     */ 
/* 146 */             rs.close();
/*     */           }
/* 148 */           log.info("流水号更新前实体数量！baseSchema=" + baseSchema + ",todayEntryCount=" + todayEntryCount + ",entryCount=" + entryCount + ",lastEntryTime=" + lastEntryTime);
/*     */ 
/* 150 */           boolean rest = isDiff(wfCountType, Long.valueOf(lastEntryTime));
/* 151 */           String condition = "todayEntryCount=(todayEntryCount+1)";
/* 152 */           if (rest) {
/* 153 */             condition = "todayEntryCount=1";
/*     */           }
/* 155 */           sql = "update BS_T_WF_TYPE set entryCount = (entryCount+1)," + condition + ",lastEntryTime=" + now + " where code='" + baseSchema + "'";
/* 156 */           log.info("更新流水号Sql=" + sql);
/* 157 */           st.executeUpdate(sql);
/*     */ 
/* 159 */           sql = "select t.entryCount entryCount, t.todayEntryCount todayEntryCount, t.lastEntryTime lastEntryTime,t.wfCountType wfCountType from BS_T_WF_TYPE t where code='" + baseSchema + "'";
/* 160 */           rs = st.executeQuery(sql);
/* 161 */           if (rs != null) {
/* 162 */             while (rs.next()) {
/* 163 */               entryCount = rs.getLong("entryCount");
/* 164 */               todayEntryCount = rs.getLong("todayEntryCount");
/* 165 */               lastEntryTime = rs.getLong("lastEntryTime");
/* 166 */               wfCountType = rs.getString("wfCountType");
/*     */             }
/* 168 */             rs.close();
/*     */           }
/* 170 */           log.info("流水号更新后实体数量！！！baseSchema=" + baseSchema + ",todayEntryCount=" + todayEntryCount + ",entryCount=" + entryCount + ",lastEntryTime=" + lastEntryTime);
/* 171 */           conn.commit();
/*     */ 
/* 173 */           wfType.setEntryCount(entryCount);
/* 174 */           wfType.setTodayEntryCount(todayEntryCount);
/* 175 */           wfType.setLastEntryTime(lastEntryTime);
/* 176 */           BaseCacheManager.put("WFType", wfType.getCode(), wfType);
/*     */         } catch (SQLException e) {
/* 178 */           e.printStackTrace();
/* 179 */           log.error("更新流程实例数失败！！" + e.getMessage(), e);
/* 180 */           throw new RuntimeException("更新流程实例数失败！！" + e.getMessage(), e);
/*     */         }
/*     */       }
/*     */     }
/* 184 */     return wfType;
/*     */   }
/*     */ 
/*     */   private boolean isDiff(String type, Long lastTime)
/*     */   {
/* 191 */     if (lastTime.longValue() == 0L) {
/* 192 */       return false;
/*     */     }
/* 194 */     long now = TimeUtils.getCurrentTime();
/* 195 */     boolean bl = false;
/* 196 */     if (type.equals("D"))
/* 197 */       bl = TimeUtils.after(lastTime.longValue(), now, "yyyyMMdd");
/* 198 */     else if (type.equals("M"))
/* 199 */       bl = TimeUtils.after(lastTime.longValue(), now, "yyyyMM");
/* 200 */     else if (type.equals("Y")) {
/* 201 */       bl = TimeUtils.after(lastTime.longValue(), now, "yyyy");
/*     */     }
/* 203 */     return bl;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 210 */     new Thread(new Runnable()
/*     */     {
/*     */       public void run() {
/* 213 */         for (int i = 0; i < 10000; i++)
/* 214 */           TimeUtils.formatIntToDate(1428276510L);
/*     */       }
/*     */     }).start();
/*     */ 
/* 220 */     for (int i = 0; i < 10; i++) {
/* 221 */       long now = 1328276510L;
/* 222 */       long lastTime = 1328276354L;
/* 223 */       TimeUtils.after(lastTime, now, "yyyyMMdd");
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean saveWfType(WfType wfType) {
/* 228 */     this.typeDao.saveOrUpdate(wfType);
/*     */ 
/* 230 */     BaseCacheManager.put("WFType", wfType.getCode(), wfType);
/* 231 */     return true;
/*     */   }
/*     */ 
/*     */   public IDao<WfSort> getSortDao() {
/* 235 */     return this.sortDao;
/*     */   }
/*     */ 
/*     */   public void setSortDao(IDao<WfSort> sortDao) {
/* 239 */     this.sortDao = sortDao;
/*     */   }
/*     */ 
/*     */   public IDao<WfType> getTypeDao() {
/* 243 */     return this.typeDao;
/*     */   }
/*     */ 
/*     */   public void setTypeDao(IDao<WfType> typeDao) {
/* 247 */     this.typeDao = typeDao;
/*     */   }
/*     */ 
/*     */   public Map<String, WfType> getWfTypeMap() {
/* 251 */     Map wfTypeMap = new ConcurrentHashMap();
/* 252 */     List eleList = BaseCacheManager.getAllElementByCacheName("WFType");
/* 253 */     for (Iterator it = eleList.iterator(); it.hasNext(); )
/*     */     {
/* 255 */       WfType type = (WfType)it.next();
/* 256 */       wfTypeMap.put(type.getCode(), type);
/*     */     }
/* 258 */     return wfTypeMap;
/*     */   }
/*     */ 
/*     */   public void setWfTypeMap(Map<String, WfType> wfTypeMap)
/*     */   {
/*     */   }
/*     */ 
/*     */   public WfType getWfTypeFromDB(String schema) {
/* 266 */     String hql = "from WfType where code='" + schema + "'";
/* 267 */     List find = this.typeDao.find(hql, null);
/* 268 */     if (CollectionUtils.isNotEmpty(find)) {
/* 269 */       return (WfType)find.get(0);
/*     */     }
/* 271 */     return null;
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.configuration.sort.service.impl.WfSortServiceImpl
 * JD-Core Version:    0.6.0
 */