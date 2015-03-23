package com.ultrapower.eoms.common.core.component.cache.model;
/**
 * 缓存信息统计对象
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version Jun 25, 2010 9:49:24 AM
 */
public class Cache {

	private String cachename = "";
	private String status = "";
	private String cacheEleCount = "";
	private String memoryStoreSize = "";
	private String diskStoreSize = "";
	public String getCachename() {
		return cachename;
	}
	public void setCachename(String cachename) {
		this.cachename = cachename;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCacheEleCount() {
		return cacheEleCount;
	}
	public void setCacheEleCount(String cacheEleCount) {
		this.cacheEleCount = cacheEleCount;
	}
	public String getMemoryStoreSize() {
		return memoryStoreSize;
	}
	public void setMemoryStoreSize(String memoryStoreSize) {
		this.memoryStoreSize = memoryStoreSize;
	}
	public String getDiskStoreSize() {
		return diskStoreSize;
	}
	public void setDiskStoreSize(String diskStoreSize) {
		this.diskStoreSize = diskStoreSize;
	}
}
