package com.ultrapower.eoms.common.core.component.cache.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.ultrapower.eoms.common.RecordLog;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * 缓存管理器,采用EHCache组件
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version Jun 22, 2010 11:28:37 AM
 */
public class BaseCacheManager {

	private static CacheManager cacheManager;
	
	static{
		try{
	        if(cacheManager==null)   
	        	  cacheManager = CacheManager.create();//使用默认配置文件创建CacheManager 
		}catch(CacheException e){
			RecordLog.printLog("初始化缓存管理器失败......"+e, RecordLog.LOG_LEVEL_ERROR);
		}
	}
	
	/**
	 * 给指定缓存添加Element
	 * @param cacheName
	 * @param key
	 * @param value
	 */
	public static void put(String cacheName, Serializable key, Serializable value) {
		Cache cache = getCache(cacheName);
		if(cache!=null){
			try {   
                cache.remove(key);   
                Element elem = new Element(key, value);   
                cache.put(elem);   
            } catch (Exception e) {   
            	RecordLog.printLog("添加缓存("+cacheName+") of "+key+" 失败!"+e, RecordLog.LOG_LEVEL_ERROR);
            }   
		}else{
			Element element = new Element(key, value);
			cache.put(element);
		}
	}   
    
	/**
	 * 给指定缓存添加Element
	 * @param cacheName
	 * @param key
	 * @param value
	 */
	public static void put(String cacheName, Object key, Object value) {
		Cache cache = getCache(cacheName);
		if(cache!=null){
			try {   
                cache.remove(key);   
                Element elem = new Element(key, value);   
                cache.put(elem);   
            } catch (Exception e) {   
            	RecordLog.printLog("添加缓存("+cacheName+") of "+key+" 失败!"+e, RecordLog.LOG_LEVEL_ERROR);
            }   
		}else{
			Element element = new Element(key, value);
			cache.put(element);
		}
	}
	
	/**  
     * 从缓存中获取元素  
     * @param cachename  
     * @param key  
     * @return  
     */  
    public static Serializable get(String cachename, Serializable key){   
        Cache cache = getCache(cachename);   
        if(cache!=null){
            try {   
                Element element = cache.get(key);   
                if(element!=null && !cache.isExpired(element))// isExpired 是否过期
                    return element.getValue();   
            } catch (Exception e) {   
            	RecordLog.printLog("获取缓存("+cachename+") of "+key+" 失败!"+e, RecordLog.LOG_LEVEL_ERROR);
            }   
        }   
        return null;   
    }   
    
	/**
	 * 从缓存中获取元素
	 * @param cacheName
	 * @param key
	 * @return
	 */
	public static Object get(String cacheName, Object key) {
		Cache cache = getCache(cacheName);
		if(cache!=null){
			try{
				Element element = cache.get(key);
				if(element!=null && !cache.isExpired(element))
					return element.getObjectValue();
			}catch(Exception e){
				RecordLog.printLog("获取缓存("+cacheName+") of "+key+" 失败!"+e, RecordLog.LOG_LEVEL_ERROR);
			}
		}
		return null;
	}
       
	/**
	 * 删除指定缓存
	 * @param cacheName
	 */
	public static void removeCache(String cacheName){
		if(cacheManager!=null)
			cacheManager.removeCache(cacheName);
	}
	
	/**
	 * 删除缓存中指定的Element
	 * @param cacheName
	 * @param object
	 * @throws CacheException
	 */
	public static void removeElement(String cacheName, Object object) throws CacheException {
		Cache cache = getCache(cacheName);
		if(cache!=null)
			cache.remove(object);
	} 
	
	/**
	 * 获取缓存集合
	 * @return
	 */
	public static String[] getCacheName(){
		if(cacheManager!=null){
			return cacheManager.getCacheNames();
		}else{ 
			return null;
		}
	}
	
	/**
	 * 获取指定缓存Element集合
	 * @param cacheName
	 * @return
	 * @throws CacheException
	 */
	public static List getAllElementByCacheName(String cacheName) throws CacheException{
		Cache cache = cacheManager.getCache(cacheName);
		List<String> keys = cache.getKeys();
		List list = new ArrayList();
		for (String key : keys) {
			list.add(get(cacheName, key));
		}
		return list;
	}
	
	/**
	 * 指定名称是否被缓存
	 * @param cacheName
	 * @return
	 */
	public static  boolean isCached(String cacheName) {
		boolean flag = true;
		Cache cache = getCache(cacheName);
		if(cache == null || cache.getKeys().size() == 0){
			flag = false;
		}
		return flag;
	}
	
    /**
	 * 获取指定名称的缓存
	 * @param cachename
	 * @return
	 * @throws IllegalStateException
	 */
	public static Cache getCache(String cachename) throws IllegalStateException{
		if(cacheManager!=null){
			return cacheManager.getCache(cachename);
		}else{
			return null;
		}
	}
	
	/**
	 * 获取指定缓存的Element数量
	 * @param cacheName
	 * @return
	 */
	public static int getElementSize(String cacheName){
		Cache cCache = getCache(cacheName);
		return cCache.getSize();
	}
	
	/**
	 * 获取指定缓存在内存中的Element数量
	 * @param cacheName
	 * @return
	 */
	public static long getElementMemorySize(String cacheName){
		Cache cCache = getCache(cacheName);
		return cCache.getMemoryStoreSize();
	}
	
	/**
	 * 获取指定缓存的状态
	 * @param cacheName
	 * @return
	 */
	public static String getStatus(String cacheName){
		Cache cCache = getCache(cacheName);
		return cCache.getStatus().toString();
	}
	
	/**
	 * 获取指定缓存在磁盘中的Element数量
	 * @param cacheName
	 * @return
	 */
	public static long getElementDiskSize(String cacheName){
		Cache cCache = getCache(cacheName);
		return cCache.getDiskStoreSize();
	}
	
    /**  
     * 获取缓存中的信息  
     * @param cache  
     * @param key  
     * @return  
     * @throws IllegalStateException  
     * @throws CacheException  
     */  
    public static Element getElement(String cacheName, Serializable key) throws IllegalStateException, CacheException{   
        Cache cCache = getCache(cacheName);
        return cCache.get(key);   
    }  
    
    /**
     * 删除指定缓存
     * @param cacheName
     */
    public static void clear(String cacheName){
    	if(cacheManager!=null)
    		cacheManager.removeCache(cacheName);
    }
    
	/**
	 * 清空所有缓存
	 */
	public static void clear(){
		if(cacheManager!=null)
			cacheManager.clearAll();
	}
	
    /**  
     * 停止缓存管理器  
     */  
    public static void shutdown(){   
        if(cacheManager!=null)   
        	cacheManager.shutdown();   
    }  
}
