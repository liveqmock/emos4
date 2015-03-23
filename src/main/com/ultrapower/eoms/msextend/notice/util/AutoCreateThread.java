package com.ultrapower.eoms.msextend.notice.util;

import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.msextend.notice.service.INoticeViewLogService;

public class AutoCreateThread extends Thread {

	private String noticeLevel;
	private String noitceid;
	private boolean isCustomPerson;
	private String selectPerson;
	
	private INoticeViewLogService noticeViewLogService;
	
	public AutoCreateThread(String noticeLevel,String noitceid) {
		this.noticeLevel = noticeLevel;
		this.noitceid = noitceid;
	}
	
	/**
	 * 
	 * @param noticeLevel
	 * @param noitceid
	 * @param flag 是否为自定义查看人
	 */
	public AutoCreateThread(String selectPerson,String noitceid,boolean isCustomPerson) {
		this.selectPerson = selectPerson;
		this.noitceid = noitceid;
		this.isCustomPerson = isCustomPerson;
	}

	@Override
	public void run() {
		noticeViewLogService =(INoticeViewLogService)WebApplicationManager.getBean("noticeViewLogService");
		if(isCustomPerson){
			noticeViewLogService.createBySelectPerson(selectPerson,noitceid);
		}else{
			noticeViewLogService.create(noticeLevel, noitceid);
		}
	}

	public String getNoticeLevel() {
		return noticeLevel;
	}

	public void setNoticeLevel(String noticeLevel) {
		this.noticeLevel = noticeLevel;
	}

	public INoticeViewLogService getNoticeViewLogService() {
		return noticeViewLogService;
	}

	public void setNoticeViewLogService(INoticeViewLogService noticeViewLogService) {
		this.noticeViewLogService = noticeViewLogService;
	}

	public String getNoitceid() {
		return noitceid;
	}

	public void setNoitceid(String noitceid) {
		this.noitceid = noitceid;
	}

	public boolean isCustomPerson() {
		return isCustomPerson;
	}

	public void setCustomPerson(boolean isCustomPerson) {
		this.isCustomPerson = isCustomPerson;
	}

	public String getSelectPerson() {
		return selectPerson;
	}

	public void setSelectPerson(String selectPerson) {
		this.selectPerson = selectPerson;
	}
}
