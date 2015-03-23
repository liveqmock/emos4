package com.ultrapower.eoms.msextend.notice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.ultrapower.eoms.common.core.util.TimeUtils;

@Entity
@Table(name = "BS_T_NOTICE_INFO")
public class NoticeInfo {
	private String pid;
	// 用于显示的通知的标题
	private String noticeTitle;
	// 通知内容
	private String noticeContent;
	// 通知的类型
	private String noticeLevel;
	// 通知创建的时间
	private long noticeCreateTime;
	// 通知启用时间
	private long noticeStartTime;
	private String noticeStartTimeStr;
	// 通知过期的时间
	private long noticeLostTime;
	private String noticeLostTimeStr;
	// 创建通知的人
	private String creatorId;
	// 创建通知人姓名
	private String creatorName;
	// 通知状态
	private String noticeStatus;
	// 流水号
	private String basesn;
	// 附件关联id
	private String attachmentId;
	// 自定义通知人（保存）
	private String selectUser;
	// 自定义通知人（显示字符串）
	private String customUser;
	// 公告类型 （1.普通公告、2.值班公告）
	private String noticetype;
	// 公告范围
	private String noticescope;
	// 公告范围ID
	private String noticescopeid;
	// 公告范围组
	private String noticescopegroup;
	// 公告范围组id
	private String noticescopegroupid;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	@Column(name = "NOTICE_TITLE")
	public String getNoticeTitle() {
		return noticeTitle;
	}

	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}

	@Column(name = "NOTICE_LEVEL")
	public String getNoticeLevel() {
		return noticeLevel;
	}

	public void setNoticeLevel(String noticeLevel) {
		this.noticeLevel = noticeLevel;
	}

	@Column(name = "notice_createTime")
	public long getNoticeCreateTime() {
		return noticeCreateTime;
	}

	public void setNoticeCreateTime(long noticeCreateTime) {
		this.noticeCreateTime = noticeCreateTime;
	}

	@Column(name = "notice_lostTime")
	public long getNoticeLostTime() {
		return noticeLostTime;
	}

	public void setNoticeLostTime(long noticeLostTime) {
		this.noticeLostTime = noticeLostTime;
	}

	@Column(name = "creator_id")
	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	@Column(name = "NOTICE_CONTENT")
	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	@Column(name = "NOTICE_STARTTIME")
	public long getNoticeStartTime() {
		return noticeStartTime;
	}

	public void setNoticeStartTime(long noticeStartTime) {
		this.noticeStartTime = noticeStartTime;
	}

	@Column(name = "CREATOR_NAME")
	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	@Column(name = "NOTICE_STATUS")
	public String getNoticeStatus() {
		return noticeStatus;
	}

	public void setNoticeStatus(String noticeStatus) {
		this.noticeStatus = noticeStatus;
	}

	@Column(name = "BASESN")
	public String getBasesn() {
		return basesn;
	}

	public void setBasesn(String basesn) {
		this.basesn = basesn;
	}

	@Column(name = "ATTACHMENTID")
	public String getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}

	public String getCustomUser() {
		return customUser;
	}

	public void setCustomUser(String customUser) {
		this.customUser = customUser;
	}

	@Column(name = "SELECTUSER")
	public String getSelectUser() {
		return selectUser;
	}

	public void setSelectUser(String selectUser) {
		this.selectUser = selectUser;
	}

	@Column(name = "NOTICETYPE")
	public String getNoticetype() {
		return noticetype;
	}

	public void setNoticetype(String noticetype) {
		this.noticetype = noticetype;
	}

	@Column(name = "NOTICESCOPEID")
	public String getNoticescopeid() {
		return noticescopeid;
	}

	public void setNoticescopeid(String noticescopeid) {
		this.noticescopeid = noticescopeid;
	}

	@Column(name = "NOTICESCOPE")
	public String getNoticescope() {
		return noticescope;
	}

	public void setNoticescope(String noticescope) {
		this.noticescope = noticescope;
	}

	@Column(name = "NOTICESCOPGROUP")
	public String getNoticescopegroup() {
		return noticescopegroup;
	}

	public void setNoticescopegroup(String noticescopegroup) {
		this.noticescopegroup = noticescopegroup;
	}

	@Column(name = "NOTICESCOPEGROUPID")
	public String getNoticescopegroupid() {
		return noticescopegroupid;
	}

	public void setNoticescopegroupid(String noticescopegroupid) {
		this.noticescopegroupid = noticescopegroupid;
	}

	@Transient
	public String getNoticeStartTimeStr() {
		if (noticeStartTime > 0 && (noticeStartTimeStr == null || "".equals(noticeStartTimeStr))) {
			noticeStartTimeStr = TimeUtils.formatIntToDateString(noticeStartTime);
		}
		return noticeStartTimeStr;
	}

	public void setNoticeStartTimeStr(String noticeStartTimeStr) {
		this.noticeStartTimeStr = noticeStartTimeStr;
	}

	@Transient
	public String getNoticeLostTimeStr() {
		if (noticeLostTime > 0 && (noticeLostTimeStr == null || "".equals(noticeLostTimeStr))) {
			noticeLostTimeStr = TimeUtils.formatIntToDateString(noticeLostTime);
		}
		return noticeLostTimeStr;
	}

	public void setNoticeLostTimeStr(String noticeLostTimeStr) {
		this.noticeLostTimeStr = noticeLostTimeStr;
	}

}
