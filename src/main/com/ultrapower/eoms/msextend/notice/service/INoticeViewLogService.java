package com.ultrapower.eoms.msextend.notice.service;

import java.util.List;

import com.ultrapower.eoms.common.sheettag.common.PageInfo;
import com.ultrapower.eoms.msextend.notice.model.NoticeInfo;

public interface INoticeViewLogService {
	
	public void create(String noticeLevel,String noitceid);
	
	public void delete(String noitceid);
	
	/**
	 * 设置该人已查看公告
	 * @return
	 */
	public void setIsView(String noticeId,String userId);
	
	/**
	 * 弹出气泡
	 * @param userId
	 * @return
	 */
	public String getNoticePopView(String userId);
	
	/**
	 * 创建日志查看人
	 * @param selectPerson 格式为D:id,name,fullname;U:id,name,loginname;
	 * @param noitceid
	 */
	public void createBySelectPerson(String selectPerson,String noitceid);
	
	/**
	 * 设置所有公告为已读
	 * @return
	 */
	public void allToRead(String userId);
	
	/**
	 * 为值班平台提供首页公告
	 * @param userId
	 * @return
	 */
	public List<NoticeInfo> dutynoticeView(String userId,PageInfo page);
	
	/**
	 * 获取总数
	 * @return
	 */
	public Long getCount(String userId);
	
	
}
