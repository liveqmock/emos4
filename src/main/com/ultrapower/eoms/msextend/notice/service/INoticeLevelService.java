package com.ultrapower.eoms.msextend.notice.service;

import java.util.List;

import com.ultrapower.eoms.msextend.notice.model.NoticeLevelManage;
import com.ultrapower.eoms.msextend.notice.model.ReciverInfo;


/**
 * @author yxg
 * @version 创建时间：Dec 27, 2012 1:29:08 PM
 * 类说明：
 */

public interface INoticeLevelService {
	/**
	 * 保存公告
	 */
	public boolean saveOrUpdateNoticeLevel(NoticeLevelManage noticeLevelManage,String selectUser);
	
	/**
	 * 删除公告
	 * @param delIds
	 */
	public void delNoticeLevel(String delIds);
	
	/**
	 * 分解人员字符串
	 * @param userStr
	 * @return [0]人员id,[1]人员全名,[2]登录名
	 */
	public List<ReciverInfo> splitUserStr(String userStr);
}
