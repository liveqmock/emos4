package com.ultrapower.eoms.msextend.notice.service;

/**
 * @author yxg
 * @version 创建时间：Dec 27, 2012 1:29:08 PM
 * 类说明：
 */

public interface INoticeLevelTreeService {
	/**
	 * 根据传入的父节点进行子节点树的xml拼接
	 * @param parentid
	 * @return
	 */
	public String getLevelTreeXml(String parentid, String dtcode);
}
