/*     */ package com.ultrapower.workflow.utils;
/*     */ 
/*     */ import com.ultrapower.eoms.common.core.support.PageLimit;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.security.MessageDigest;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.util.Assert;
/*     */ import org.springframework.util.StringUtils;
/*     */ 
/*     */ public class GenericsUtils
/*     */ {
/*  29 */   private static final Logger log = LoggerFactory.getLogger(GenericsUtils.class);
/*     */ 
/*     */   public static Class getSuperClassGenricType(Class clazz)
/*     */   {
/*  44 */     return getSuperClassGenricType(clazz, 0);
/*     */   }
/*     */ 
/*     */   public static Class getSuperClassGenricType(Class clazz, int index)
/*     */   {
/*  59 */     Type genType = clazz.getGenericSuperclass();
/*     */ 
/*  61 */     if (!(genType instanceof ParameterizedType))
/*     */     {
/*  64 */       return Object.class;
/*     */     }
/*     */ 
/*  67 */     Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
/*     */ 
/*  69 */     if ((index >= params.length) || (index < 0)) {
/*  70 */       log.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + 
/*  71 */         "'s Parameterized Type: " + params.length);
/*  72 */       return Object.class;
/*     */     }
/*  74 */     if (!(params[index] instanceof Class)) {
/*  75 */       log
/*  76 */         .warn(clazz.getSimpleName() + 
/*  77 */         " not set the actual class on superclass generic parameter");
/*  78 */       return Object.class;
/*     */     }
/*  80 */     return (Class)params[index];
/*     */   }
/*     */ 
/*     */   public static String removeSelect(String hql)
/*     */   {
/*  89 */     Assert.hasText(hql);
/*  90 */     int beginPos = hql.toLowerCase().indexOf("from");
/*  91 */     Assert.isTrue(beginPos != -1, " hql : " + hql + 
/*  92 */       " must has a keyword 'from'");
/*  93 */     return hql.substring(beginPos);
/*     */   }
/*     */ 
/*     */   public static String ordersfromHql(String hql)
/*     */   {
/* 104 */     Assert.hasText(hql);
/* 105 */     int beginPos = hql.toLowerCase().indexOf("order by");
/*     */ 
/* 107 */     if (beginPos > 0)
/* 108 */       return " " + hql.substring(beginPos);
/* 109 */     return "";
/*     */   }
/*     */ 
/*     */   public static String changeComma(String comma)
/*     */   {
/* 114 */     if (StringUtils.hasLength(comma)) {
/* 115 */       return comma.replaceAll(",", "','");
/*     */     }
/* 117 */     return "";
/*     */   }
/*     */ 
/*     */   public static String trimComma(String string)
/*     */   {
/*     */     try
/*     */     {
/* 128 */       if ((string != null) && (string.trim().length() > 0)) {
/* 129 */         if (string.lastIndexOf(",") == string.length() - 1) {
/* 130 */           string = string.substring(0, string.length() - 1);
/*     */         }
/* 132 */         if (string.indexOf(",") == 0)
/* 133 */           string = string.substring(1, string.length());
/*     */       }
/*     */       else {
/* 136 */         string = "";
/*     */       }
/*     */     } catch (Exception localException) {
/*     */     }
/* 140 */     return string;
/*     */   }
/*     */ 
/*     */   public static String removeInHql(String hql, String regular)
/*     */   {
/* 151 */     Assert.hasText(hql);
/* 152 */     Pattern p = Pattern.compile(regular, 2);
/* 153 */     Matcher m = p.matcher(hql);
/* 154 */     StringBuffer sb = new StringBuffer();
/* 155 */     while (m.find()) {
/* 156 */       m.appendReplacement(sb, "");
/*     */     }
/* 158 */     m.appendTail(sb);
/* 159 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static String removeOrders(String hql)
/*     */   {
/* 168 */     return removeInHql(hql, "order\\s*by[\\w|\\W|\\s|\\S]*");
/*     */   }
/*     */ 
/*     */   public static String removeWheres(String hql)
/*     */   {
/* 178 */     return removeInHql(hql, "where[\\w|\\W|\\s|\\S]*");
/*     */   }
/*     */ 
/*     */   public static String queryAccession(String hql, PageLimit pageLimit)
/*     */   {
/* 190 */     Assert.notNull(pageLimit.getEntity());
/*     */ 
/* 192 */     Object entity = pageLimit.getEntity();
/*     */ 
/* 195 */     Map aliasMap = pageLimit.getAliasMap();
/* 196 */     Map operMap = pageLimit.getOperMap();
/* 197 */     Map actualMap = pageLimit.getActualMap();
/*     */ 
/* 199 */     StringBuffer buffer = new StringBuffer();
/* 200 */     Iterator iterator = aliasMap.keySet().iterator();
/* 201 */     while (iterator.hasNext()) {
/*     */       try {
/* 203 */         String column = (String)iterator.next();
/* 204 */         Object fieldValue = null;
/* 205 */         if ((entity instanceof Map))
/* 206 */           fieldValue = ((Map)entity).get(column);
/*     */         else {
/* 208 */           fieldValue = BeanUtils.getCommaFieldValue(entity, column);
/*     */         }
/* 210 */         if ((fieldValue == null) || (fieldValue.equals("")))
/*     */           continue;
/* 212 */         String oper = " = ";
/*     */ 
/* 214 */         String fieldColumn = actualMap.get(column) == null ? 
/* 215 */           (String)aliasMap.get(column) : 
/* 216 */           (String)actualMap.get(column);
/*     */ 
/* 218 */         oper = operMap.get(column) == null ? oper : 
/* 219 */           (String)operMap.get(column);
/*     */ 
/* 221 */         buffer.append(" and ");
/* 222 */         if (oper.toLowerCase().equals("like"))
/* 223 */           buffer.append(fieldColumn + " " + oper + "'%'||:" + 
/* 224 */             column + "||'%'");
/*     */         else {
/* 226 */           buffer.append(fieldColumn + oper + ":" + column.replace(".", ""));
/*     */         }
/*     */ 
/* 229 */         pageLimit.setColumnValue(column.replace(".", ""), fieldValue);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 233 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */ 
/* 237 */     String result = removeOrders(hql) + (
/* 238 */       (hql.indexOf("where") > 0) || (hql.indexOf("WHERE") > 0) ? 
/* 239 */       buffer.toString() : 
/* 240 */       buffer.toString().replaceFirst("and", "where")) + 
/* 241 */       pageLimit.getSortAndDesc(ordersfromHql(hql));
/* 242 */     log.debug(result);
/* 243 */     return result;
/*     */   }
/*     */ 
/*     */   public static String encodePassword(String password)
/*     */   {
/* 250 */     byte[] unencodedPassword = password.getBytes();
/*     */ 
/* 252 */     MessageDigest md = null;
/*     */     try
/*     */     {
/* 256 */       md = MessageDigest.getInstance("MD5");
/*     */     } catch (Exception e) {
/* 258 */       log.error("Exception: " + e);
/*     */ 
/* 260 */       return password;
/*     */     }
/*     */ 
/* 263 */     md.reset();
/*     */ 
/* 267 */     md.update(unencodedPassword);
/*     */ 
/* 270 */     byte[] encodedPassword = md.digest();
/*     */ 
/* 272 */     StringBuffer buf = new StringBuffer();
/*     */ 
/* 274 */     for (byte anEncodedPassword : encodedPassword) {
/* 275 */       if ((anEncodedPassword & 0xFF) < 16) {
/* 276 */         buf.append("0");
/*     */       }
/*     */ 
/* 279 */       buf.append(Long.toString(anEncodedPassword & 0xFF, 16));
/*     */     }
/*     */ 
/* 282 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public static long Obj2long(Object s, long r)
/*     */   {
/* 294 */     long i = r;
/*     */ 
/* 296 */     String str = "";
/*     */     try {
/* 298 */       str = s.toString();
/* 299 */       i = Long.parseLong(str);
/*     */     } catch (Exception e) {
/* 301 */       i = r;
/*     */     }
/* 303 */     return i;
/*     */   }
/*     */ 
/*     */   public static String Obj2Str(Object s, String r)
/*     */   {
/* 313 */     String str = r;
/*     */     try {
/* 315 */       str = s.toString();
/* 316 */       if ((str.equals("null")) || (str == null) || (str.trim().length() == 0))
/* 317 */         str = r;
/*     */     }
/*     */     catch (Exception e) {
/* 320 */       str = r;
/*     */     }
/* 322 */     return str;
/*     */   }
/*     */ 
/*     */   public static String getQueryNum(String ids)
/*     */   {
/* 331 */     StringBuffer flag = new StringBuffer();
/* 332 */     String[] idArray = ids.split(",");
/* 333 */     for (String id : idArray) {
/* 334 */       if (flag.toString().equals(""))
/* 335 */         flag.append("?");
/*     */       else
/* 337 */         flag.append(",?");
/*     */     }
/* 339 */     return flag.toString();
/*     */   }
/*     */ 
/*     */   public static String queryAccession(Object entity)
/*     */   {
/* 350 */     StringBuffer buffer = new StringBuffer();
/* 351 */     Field[] fields = BeanUtils.getDeclaredFields(entity.getClass());
/* 352 */     for (Field field : fields) {
/*     */       try {
/* 354 */         Object fieldValue = BeanUtils.getFieldValue(entity, 
/* 355 */           field.getName());
/* 356 */         if ((fieldValue != null) && (!fieldValue.equals(""))) {
/* 357 */           String fieldColumn = field.getName();
/* 358 */           String oper = ((fieldValue instanceof String)) && 
/* 359 */             (String.valueOf(fieldValue).contains("%")) ? 
/* 359 */             " like " : 
/* 360 */             " = ";
/* 361 */           if (buffer.length() == 0) {
/* 362 */             buffer.append("from " + entity.getClass().getName());
/* 363 */             buffer.append(" where ");
/* 364 */             buffer.append(fieldColumn + oper + ":" + 
/* 365 */               field.getName());
/*     */           } else {
/* 367 */             buffer.append(" and ");
/* 368 */             buffer.append(fieldColumn + oper + " :" + fieldColumn);
/*     */           }
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 374 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */ 
/* 378 */     return buffer.toString();
/*     */   }
/*     */ 
/*     */   public static List<String> getHavingId(String allId, String compareId, boolean flag)
/*     */   {
/* 383 */     List list = new ArrayList();
/* 384 */     Map map = new HashMap();
/* 385 */     String[] allIdArray = allId.split(",");
/* 386 */     String[] compareIdArray = compareId.split(",");
/* 387 */     for (String allid : allIdArray) {
/* 388 */       map.put(allid, allid);
/*     */     }
/* 390 */     if (flag) {
/* 391 */       for (String compareid : compareIdArray)
/* 392 */         if (map.get(compareid) != null)
/* 393 */           list.add(compareid);
/*     */     }
/*     */     else {
/* 396 */       for (String compareid : compareIdArray) {
/* 397 */         if (map.get(compareid) == null)
/* 398 */           list.add(compareid);
/*     */       }
/*     */     }
/* 401 */     return list;
/*     */   }
/*     */ 
/*     */   public static String arrayFromMap(Map map) {
/* 405 */     String[] array = new String[map.size()];
/* 406 */     Iterator iterator = map.keySet().iterator();
/* 407 */     for (int i = 0; i < map.size(); i++) {
/* 408 */       String key = (String)iterator.next();
/* 409 */       String value = toString((String[])map.get(key), ",");
/* 410 */       array[i] = (key + "=" + value);
/*     */     }
/* 412 */     return toString(array, ",");
/*     */   }
/*     */ 
/*     */   public static String toString(String[] array, String spliteFlag)
/*     */   {
/* 417 */     StringBuffer buffer = new StringBuffer();
/* 418 */     String[] arrayOfString = array; int j = array.length; for (int i = 0; i < j; i++) { String a = arrayOfString[i];
/* 419 */       if (buffer.length() == 0) {
/* 420 */         buffer.append(a);
/*     */       } else {
/* 422 */         buffer.append(spliteFlag);
/* 423 */         buffer.append(a);
/*     */       }
/*     */     }
/* 426 */     return buffer.toString();
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.utils.GenericsUtils
 * JD-Core Version:    0.6.0
 */