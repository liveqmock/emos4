package com.ultrapower.eoms.common.core.component.cache.web;

import java.util.ArrayList;
import java.util.List;

import com.ultrapower.eoms.common.core.component.cache.model.Cache;

import com.ultrapower.eoms.common.core.component.cache.manager.BaseCacheManager;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.web.BaseAction;

/**
 * 缓存管理
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version Jun 25, 2010 9:57:27 AM
 */
public class CacheAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	private List<Cache> caches;
	
	public String cachelist(){
		caches = new ArrayList<Cache>();
		String[] cachenameArr = BaseCacheManager.getCacheName();
		for (String cachename : cachenameArr) {
			Cache cCache = new Cache();
			cCache.setCachename(cachename);
			cCache.setCacheEleCount(String.valueOf(BaseCacheManager.getElementSize(cachename)));
			cCache.setStatus(BaseCacheManager.getStatus(cachename));
			cCache.setMemoryStoreSize(String.valueOf(BaseCacheManager.getElementMemorySize(cachename)));
			cCache.setDiskStoreSize(String.valueOf(BaseCacheManager.getElementDiskSize(cachename)));
			caches.add(cCache);
		}
		return SUCCESS;
	}
	
	public String reload(){
		
		String cachenames = StringUtils.checkNullString(this.getRequest().getParameter("cachenames"));
		//删除指定缓存记录
		if(!cachenames.equals("")){
			String[] cacheArr = cachenames.split(",");
			int cacheArrLen = 0;
			if(cacheArr!=null)
				cacheArrLen = cacheArr.length;
			for(int i=0;i<cacheArrLen;i++){
				String cacheName = cacheArr[i];
				net.sf.ehcache.Cache cache = BaseCacheManager.getCache(cacheName);
				List key = cache.getKeys();
				int keyLen = 0;
				if(key!=null)
					keyLen = key.size();
				for(int k=0;k<keyLen;k++){
					BaseCacheManager.removeElement(cacheName, key.get(k));
				}
			}
		}
		//重载一次
		caches = new ArrayList<Cache>();
		String[] cachenameArr = BaseCacheManager.getCacheName();
		for (String cachename : cachenameArr) {
			Cache cCache = new Cache();
			cCache.setCachename(cachename);
			cCache.setCacheEleCount(String.valueOf(BaseCacheManager.getElementSize(cachename)));
			cCache.setStatus(BaseCacheManager.getStatus(cachename));
			cCache.setMemoryStoreSize(String.valueOf(BaseCacheManager.getElementMemorySize(cachename)));
			cCache.setDiskStoreSize(String.valueOf(BaseCacheManager.getElementDiskSize(cachename)));
			caches.add(cCache);
		}
		return this.findForward("cachelist");
	}

	public List<Cache> getCaches() {
		return caches;
	}

	public void setCaches(List<Cache> caches) {
		this.caches = caches;
	}
}
