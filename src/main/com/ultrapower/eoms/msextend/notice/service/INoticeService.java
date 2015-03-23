package com.ultrapower.eoms.msextend.notice.service;

import com.ultrapower.eoms.msextend.notice.model.NoticeInfo;



/**
 * 通知服务接口
 * @author Zhe
 *
 */
public interface INoticeService {
		
	/**
	 * 保存公告
	 */
	public boolean saveOrUpdateNotice(NoticeInfo noticeInfo);
	
	/**
	 * 根据id获得公告
	 */
	public NoticeInfo getNotice(String noticeId);
	
	/**
	 * 根据id获得公告(更新查看人)
	 */
	public NoticeInfo getNotice(String noticeId,String userId);
	
	/**
	 * 删除公告
	 * @param delIds
	 */
	public void delNotice(String delIds);

}
