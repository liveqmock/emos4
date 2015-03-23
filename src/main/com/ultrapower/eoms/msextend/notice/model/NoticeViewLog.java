package com.ultrapower.eoms.msextend.notice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 服务台系统公告用户显示日志
 * @author liuchuanzu
 */
@Entity
@Table(name="BS_T_NOTICE_VIEWLOG")
public class NoticeViewLog {
	private String id;
	private String noticeId;
	private String userId;
	private String isview;
	private String reciverId;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	@Column(name="NOTICE_ID")
	public String getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}

	@Column(name="USER_ID")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name="ISVIEW")
	public String getIsview() {
		return isview;
	}

	public void setIsview(String isview) {
		this.isview = isview;
	}

	@Column(name="RECIVERID")
	public String getReciverId() {
		return reciverId;
	}

	public void setReciverId(String reciverId) {
		this.reciverId = reciverId;
	}
}
