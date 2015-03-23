package com.ultrapower.eoms.msextend.notice.manager;

import org.springframework.transaction.annotation.Transactional;

import com.ultrapower.eoms.common.core.dao.IDao;
import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.msextend.notice.model.NoticeInfo;
import com.ultrapower.eoms.msextend.notice.service.INoticeService;
import com.ultrapower.eoms.msextend.notice.service.INoticeViewLogService;
import com.ultrapower.eoms.msextend.notice.util.AutoCreateThread;
import com.ultrapower.eoms.ultrasm.RecordLog;

/**
 * 通知服务实现类
 * @author Zhe
 *
 */
@SuppressWarnings("unchecked")
@Transactional
public class NoticeManagerImpl implements INoticeService {
	
	private INoticeViewLogService noticeViewLogService;
	
	private IDao<NoticeInfo> noticeInfoDao;

	private int noticeNums ;
	
	public int getNoticeNums() {
		return noticeNums;
	}

	public void setNoticeNums(int noticeNums) {
		this.noticeNums = noticeNums;
	}

	/**
	 * 保存公告
	 */
	public boolean saveOrUpdateNotice(NoticeInfo noticeInfo) {
		
		boolean result = false;
		if(noticeInfo == null)
		{
			return result;
		}
		
		// 判断为添加
		if(noticeInfo.getPid() == null || "".equals(noticeInfo.getPid())){
			String basesn = this.getSerialNum();
			noticeInfo.setBasesn(basesn);
			long currentTime = TimeUtils.getCurrentTime();
			noticeInfo.setNoticeCreateTime(currentTime);
			noticeInfo.setNoticeStatus("0");
		}
		
		long noticeStartTime = TimeUtils.formatDateStringToInt(noticeInfo.getNoticeStartTimeStr());
		long noticeLostTime = TimeUtils.formatDateStringToInt(noticeInfo.getNoticeLostTimeStr());
		noticeInfo.setNoticeStartTime(noticeStartTime);
		noticeInfo.setNoticeLostTime(noticeLostTime);
		
		try
		{
			noticeInfoDao.saveOrUpdate(noticeInfo);
			// 根据配置添加通知人
			if("0".equals(noticeInfo.getNoticeLevel())){
				new AutoCreateThread(noticeInfo.getSelectUser(),noticeInfo.getPid(),true).start();
			}else{
				// 非自定义通知人
				new AutoCreateThread(noticeInfo.getNoticeLevel(),noticeInfo.getPid()).start();
			}
			result = true;
		}
		catch (Exception e)
		{
			RecordLog.printLog(e.getMessage(),RecordLog.LOG_LEVEL_ERROR);
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 根据id获得公告
	 */
	public NoticeInfo getNotice(String noticeId) {
		NoticeInfo noticeInfo = noticeInfoDao.get(noticeId);
		
		return noticeInfo;
	}
	
	/**
	 * 根据id获得公告
	 */
	public NoticeInfo getNotice(String noticeId,String userId) {
		NoticeInfo noticeInfo = noticeInfoDao.get(noticeId);
		if(noticeInfo != null){
			noticeViewLogService.setIsView(noticeId, userId);
		}
		
		return noticeInfo;
	}
	
	/**
	 * 删除通知
	 * @param delIds
	 */
	public void delNotice(String delIds) {
		String hql = "update NoticeInfo set noticeStatus = '4'   where pid = ?";
		if (delIds != null && !"".equals(delIds)) {
			String[] ids = delIds.split(",");
			if (ids != null && ids.length > 0) {
				for (int i = 0; i < ids.length; i++) {
					noticeInfoDao.executeUpdate(hql, new Object[]{ids[i]});
					RecordLog.printLog("delete id="+ids[i]);
				}
			}
		}
	}
	
	/**
	 * 获取流水号
	 * @return
	 */
	private String getSerialNum() {
		String serText = "NOTICE-"+TimeUtils.getCurrentDate("yyyyMMdd")+"-";
		String maxBasesn = this.getMaxBasesnByDay(serText);
		int maxNum = 1;
		if(!"".equals(maxBasesn)){
			String maxNumStr = maxBasesn.replace(serText, "");
			maxNum = Integer.parseInt(maxNumStr);
			maxNum++;
		}
		String sMaxNumStr = maxNum + "";
		String numStr = "0000" + sMaxNumStr;
		serText = serText + numStr.substring(sMaxNumStr.length());
		return serText;
	}
	
	private String getMaxBasesnByDay(String day){
		String hql = "select max(basesn) from NoticeInfo where BASESN like '" + day + "%'";
		Object obj = noticeInfoDao.findUnique(hql);
		String maxBasesn = StringUtils.checkNullString(obj);
		return maxBasesn;
	}
	
	
	public IDao<NoticeInfo> getNoticeInfoDao() {
		return noticeInfoDao;
	}

	public void setNoticeInfoDao(IDao<NoticeInfo> noticeInfoDao) {
		this.noticeInfoDao = noticeInfoDao;
	}

	public INoticeViewLogService getNoticeViewLogService() {
		return noticeViewLogService;
	}

	public void setNoticeViewLogService(INoticeViewLogService noticeViewLogService) {
		this.noticeViewLogService = noticeViewLogService;
	}
}
