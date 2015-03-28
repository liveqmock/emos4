/*     */ package com.ultrapower.workflow.utils;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.util.Assert;
/*     */ import org.springframework.util.ReflectionUtils;
/*     */ 
/*     */ public class BeanUtils extends org.apache.commons.beanutils.BeanUtils
/*     */ {
/*  26 */   protected static final Logger log = LoggerFactory.getLogger(BeanUtils.class);
/*     */ 
/*     */   public static Object getCommaFieldValue(Object object, String fieldName)
/*     */   {
/*  37 */     Object result = object;
/*  38 */     String[] columns = fieldName.split("\\.");
/*  39 */     for (String column : columns) {
/*  40 */       if (result == null)
/*  41 */         return null;
/*  42 */       result = getFieldValue(result, column);
/*     */     }
/*  44 */     return result;
/*     */   }
/*     */ 
/*     */   public static Object getFieldValue(Object object, String fieldName)
/*     */   {
/*  52 */     Object result = null;
/*     */     try {
/*  54 */       Field field = getDeclaredField(object, fieldName);
/*  55 */       if (!field.isAccessible()) {
/*  56 */         field.setAccessible(true);
/*     */       }
/*     */ 
/*  59 */       result = field.get(object);
/*     */     } catch (IllegalAccessException e) {
/*  61 */       e.printStackTrace();
/*     */     }
/*     */     catch (NoSuchFieldException e) {
/*  64 */       e.printStackTrace();
/*     */     }
/*  66 */     return result;
/*     */   }
/*     */ 
/*     */   public static void setFieldValue(Object object, String fieldName, Object value)
/*     */     throws NoSuchFieldException
/*     */   {
/*  75 */     Field field = getDeclaredField(object, fieldName);
/*  76 */     if (!field.isAccessible())
/*  77 */       field.setAccessible(true);
/*     */     try
/*     */     {
/*  80 */       field.set(object, value);
/*     */     } catch (IllegalAccessException e) {
/*  82 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static Field getDeclaredField(Object object, String propertyName)
/*     */     throws NoSuchFieldException
/*     */   {
/*  94 */     Assert.notNull(object);
/*  95 */     Assert.hasText(propertyName);
/*  96 */     return getDeclaredField(object.getClass(), propertyName);
/*     */   }
/*     */ 
/*     */   public static Field getDeclaredField(Class clazz, String propertyName)
/*     */     throws NoSuchFieldException
/*     */   {
/* 107 */     Assert.notNull(clazz);
/* 108 */     Assert.hasText(propertyName);
/* 109 */     for (Class superClass = clazz; superClass != Object.class; ) {
/*     */       try
/*     */       {
/* 112 */         return superClass.getDeclaredField(propertyName);
/*     */       }
/*     */       catch (NoSuchFieldException localNoSuchFieldException)
/*     */       {
/* 109 */         superClass = superClass
/* 110 */           .getSuperclass();
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 117 */     throw new NoSuchFieldException("No such field: " + clazz.getName() + 
/* 118 */       '.' + propertyName);
/*     */   }
/*     */ 
/*     */   public static Object invokePrivateMethod(Object object, String methodName, Object[] params)
/*     */     throws NoSuchMethodException
/*     */   {
/* 129 */     Assert.notNull(object);
/* 130 */     Assert.hasText(methodName);
/* 131 */     Class[] types = new Class[params.length];
/* 132 */     for (int i = 0; i < params.length; i++) {
/* 133 */       types[i] = params[i].getClass();
/*     */     }
/*     */ 
/* 136 */     Class clazz = object.getClass();
/* 137 */     Method method = null;
/* 138 */     for (Class superClass = clazz; superClass != Object.class; ) {
/*     */       try
/*     */       {
/* 141 */         method = superClass.getDeclaredMethod(methodName, types);
/*     */       }
/*     */       catch (NoSuchMethodException localNoSuchMethodException)
/*     */       {
/* 138 */         superClass = superClass
/* 139 */           .getSuperclass();
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 148 */     if (method == null) {
/* 149 */       throw new NoSuchMethodException("No Such Method:" + 
/* 150 */         clazz.getSimpleName() + methodName);
/*     */     }
/* 152 */     boolean accessible = method.isAccessible();
/* 153 */     method.setAccessible(true);
/* 154 */     Object result = null;
/*     */     try {
/* 156 */       result = method.invoke(object, params);
/*     */     } catch (Exception e) {
/* 158 */       ReflectionUtils.handleReflectionException(e);
/*     */     }
/* 160 */     method.setAccessible(accessible);
/* 161 */     return result;
/*     */   }
/*     */ 
/*     */   public static List<Field> getFieldsByType(Object object, Class type)
/*     */   {
/* 168 */     List list = new ArrayList();
/* 169 */     Field[] fields = object.getClass().getDeclaredFields();
/* 170 */     for (Field field : fields) {
/* 171 */       if (field.getType().isAssignableFrom(type)) {
/* 172 */         list.add(field);
/*     */       }
/*     */     }
/* 175 */     return list;
/*     */   }
/*     */ 
/*     */   public static Class getPropertyType(Class type, String name)
/*     */     throws NoSuchFieldException
/*     */   {
/* 183 */     return getDeclaredField(type, name).getType();
/*     */   }
/*     */ 
/*     */   public static String getGetterName(Class type, String fieldName)
/*     */   {
/* 190 */     Assert.notNull(type, "Type required");
/* 191 */     Assert.hasText(fieldName, "FieldName required");
/*     */ 
/* 193 */     if (type.getName().equals("boolean")) {
/* 194 */       return "is" + StringUtils.capitalize(fieldName);
/*     */     }
/* 196 */     return "get" + StringUtils.capitalize(fieldName);
/*     */   }
/*     */ 
/*     */   public static Method getGetterMethod(Class type, String fieldName)
/*     */   {
/*     */     try
/*     */     {
/* 205 */       return type.getMethod(getGetterName(type, fieldName), new Class[0]);
/*     */     } catch (NoSuchMethodException e) {
/* 207 */       log.error(e.getMessage(), e);
/*     */     }
/* 209 */     return null;
/*     */   }
/*     */ 
/*     */   public static void copyPropertiesByModel(Object dest, Object orig)
/*     */   {
/*     */     try
/*     */     {
/* 217 */       copyProperties(dest, orig);
/*     */     } catch (Exception e) {
/* 219 */       log.error(e.getMessage(), e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void copyMap(Map dest, Map orig)
/*     */   {
/*     */     try
/*     */     {
/* 228 */       for (Iterator names = orig.keySet().iterator(); names.hasNext(); ) {
/* 229 */         String name = (String)names.next();
/* 230 */         if (name != null) {
/* 231 */           Object value = orig.get(name);
/* 232 */           dest.put(name, value);
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 236 */       log.error(e.getMessage(), e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static Map beanToMap(Object orig) {
/* 241 */     Map result = new HashMap();
/*     */     try {
/* 243 */       result = describe(orig);
/*     */     } catch (Exception e) {
/* 245 */       e.printStackTrace();
/*     */     }
/* 247 */     return result;
/*     */   }
/*     */ 
/*     */   public static Map beanToMap(Map dest, Object orig) {
/*     */     try {
/* 252 */       Map origMap = describe(orig);
/* 253 */       copyMap(dest, origMap);
/*     */     } catch (Exception e) {
/* 255 */       e.printStackTrace();
/*     */     }
/* 257 */     return dest;
/*     */   }
/*     */ 
/*     */   public static void mapToBean(Object dest, Map orig) {
/*     */     try {
/* 262 */       populate(dest, orig);
/*     */     } catch (Exception e) {
/* 264 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static Field[] getDeclaredFields(Class clazz)
/*     */   {
/* 273 */     Assert.notNull(clazz);
/* 274 */     Field[] field = new Field[0];
/* 275 */     for (Class superClass = clazz; superClass != Object.class; superClass = superClass
/* 276 */       .getSuperclass())
/*     */     {
/* 277 */       field = superClass.getDeclaredFields();
/* 278 */       if (field.length != 0)
/*     */         break;
/*     */     }
/* 281 */     return field;
/*     */   }
/*     */ 
/*     */   public static Object getStaticProperty(String expstr)
/*     */   {
/*     */     try
/*     */     {
/* 292 */       if ((expstr.startsWith("%{@")) && (expstr.endsWith("}")) && 
/* 293 */         (expstr.length() > 4)) {
/* 294 */         expstr = expstr.substring(3, expstr.length() - 1);
/* 295 */         String[] classobj = expstr.split("@");
/* 296 */         Class ownerClass = null;
/* 297 */         if ((classobj != null) && (classobj.length > 0)) {
/* 298 */           ownerClass = Class.forName(classobj[0]);
/*     */         }
/*     */ 
/* 301 */         Field field = null;
/*     */ 
/* 303 */         if ((ownerClass != null) && (classobj != null) && 
/* 304 */           (classobj.length > 0)) {
/* 305 */           field = ownerClass.getField(classobj[1]);
/*     */         }
/* 307 */         Object property = null;
/* 308 */         if (field != null) {
/* 309 */           property = field.get(ownerClass);
/*     */         }
/* 311 */         return property;
/*     */       }
/* 313 */       return null;
/*     */     }
/*     */     catch (Exception e) {
/* 316 */       log.error("通过反射获得静态变量错误。" + e.getCause());
/* 317 */     }return null;
/*     */   }
/*     */ 
/*     */   public static Constructor getConstructor(Class classobj, Class[] parameterTypes)
/*     */   {
/* 331 */     Constructor c = null;
/*     */     try {
/* 333 */       c = classobj.getConstructor(parameterTypes);
/*     */     } catch (Exception e) {
/* 335 */       c = null;
/*     */     }
/* 337 */     return c;
/*     */   }
/*     */ 
/*     */   public static <T> List<T> noDuplicate(List<T> src)
/*     */   {
/* 348 */     List rtn = new ArrayList();
/* 349 */     if (src != null) {
/* 350 */       Set set = new HashSet();
/* 351 */       set.addAll(src);
/* 352 */       rtn.addAll(set);
/*     */     }
/* 354 */     return rtn;
/*     */   }
/*     */ }

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.utils.BeanUtils
 * JD-Core Version:    0.6.0
 */