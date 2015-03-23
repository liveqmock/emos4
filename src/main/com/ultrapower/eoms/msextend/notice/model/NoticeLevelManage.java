package com.ultrapower.eoms.msextend.notice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="BS_T_NOTICE_LEVELMANAGE")
public class NoticeLevelManage {
	
	private String pid;
	
	//通知的类型
	private String noticeLevel;
	
	//需要通知到的人
	private String reciverId;
	
	private String reciverType;
	
	private long createTime;
	
	private String creatorId;
	
	private String creatorName;
	
	
	public NoticeLevelManage() {
	}
	
	public NoticeLevelManage(String pid, String noticeLevel, String reciverId,String reciverType,
			long createTime, String creatorId, String creatorName) {
		super();
		this.pid = pid;
		this.noticeLevel = noticeLevel;
		this.reciverId = reciverId;
		this.reciverType = reciverType;
		this.createTime = createTime;
		this.creatorId = creatorId;
		this.creatorName = creatorName;
	}


	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
	
	@Column(name="RECIVERID")
	public String getReciverId() {
		return reciverId;
	}

	public void setReciverId(String reciverId) {
		this.reciverId = reciverId;
	}

	@Column(name="RECIVERTYPE")
	public String getReciverType() {
		return reciverType;
	}

	public void setReciverType(String reciverType) {
		this.reciverType = reciverType;
	}

	@Column(name="NOTICE_LEVEL")
	public String getNoticeLevel() {
		return noticeLevel;
	}

	public void setNoticeLevel(String noticeLevel) {
		this.noticeLevel = noticeLevel;
	}

	@Column(name="CREATETIME")
	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	@Column(name="CREATOR_ID")
	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	@Column(name="CREATOR_NAME")
	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

}
