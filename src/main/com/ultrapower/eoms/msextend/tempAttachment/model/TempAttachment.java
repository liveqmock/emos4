package com.ultrapower.eoms.msextend.tempAttachment.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Transient;

import com.ultrapower.eoms.common.core.util.TimeUtils;

/**
 * @author yxg
 * @version 创建时间：Jan 16, 2013 11:38:07 AM
 * 类说明：
 */
@Entity
@Table(name="BS_T_WF_TEMPATTACHMENT")
public class TempAttachment {
	
	private String pid;
	// 工单类型
	private String baseSchema;
	// 工单名称
	private String baseName;
	// 流程分类ID
	private String sortid;
	// 附件关联ID
	private String attachmentId;
	// 创建人
	private String creater;
	// 创建时间
	private long createTime;
	private String createTimeStr;
	// 最后修改人
	private String lastModifier;
	// 最后修改时间
	private long lastModifyTime;
	private String lastModifyTimeStr;
	// 描述
	private String remark;
	//关联字段值
	private String belongvalue;
	@Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	
	@Column(name="BASESCHEMA")
	public String getBaseSchema() {
		return baseSchema;
	}
	public void setBaseSchema(String baseSchema) {
		this.baseSchema = baseSchema;
	}
	
	@Column(name="BASENAME")
	public String getBaseName() {
		return baseName;
	}
	public void setBaseName(String baseName) {
		this.baseName = baseName;
	}
	
	@Column(name="ATTACHMENTID")
	public String getAttachmentId() {
		return attachmentId;
	}
	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}
	
	@Column(name="CREATER")
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	
	@Column(name="CREATETIME")
	public long getCreateTime() {
		if(createTime <=0 && createTimeStr != null && !"".equals(createTimeStr)){
			createTime = TimeUtils.formatDateStringToInt(createTimeStr);
		}
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
	@Column(name="LASTMODIFIER")
	public String getLastModifier() {
		return lastModifier;
	}
	public void setLastModifier(String lastModifier) {
		this.lastModifier = lastModifier;
	}
	
	@Column(name="LASTMODIFYTIME")
	public long getLastModifyTime() {
		if(lastModifyTime <=0 && lastModifyTimeStr != null && !"".equals(lastModifyTimeStr)){
			lastModifyTime = TimeUtils.formatDateStringToInt(lastModifyTimeStr);
		}
		return lastModifyTime;
	}
	public void setLastModifyTime(long lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	
	@Column(name="REMARK")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name="SORTID")
	public String getSortid() {
		return sortid;
	}
	public void setSortid(String sortid) {
		this.sortid = sortid;
	}
	
	@Column(name="BELONGVALUE")
	public String getBelongvalue() {
		return belongvalue;
	}
	public void setBelongvalue(String belongvalue) {
		this.belongvalue = belongvalue;
	}
	
	@Transient
	public String getCreateTimeStr() {
		if(createTime >0 && (createTimeStr == null || "".equals(createTimeStr))){
			createTimeStr = TimeUtils.formatIntToDateString(createTime);
		}
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	
	@Transient
	public String getLastModifyTimeStr() {
		if(lastModifyTime >0 && (lastModifyTimeStr == null || "".equals(lastModifyTimeStr))){
			lastModifyTimeStr = TimeUtils.formatIntToDateString(lastModifyTime);
		}
		return lastModifyTimeStr;
	}
	public void setLastModifyTimeStr(String lastModifyTimeStr) {
		this.lastModifyTimeStr = lastModifyTimeStr;
	}
	
}
