package com.ultrapower.eoms.workflow.sheet.attachment.model;

// default package

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ultrapower.randomutil.Random15;
import com.ultrapower.randomutil.RandomN;

/**
 * ChildRole entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BS_T_WF_ATTACHMENT")
public class WfAttachment implements java.io.Serializable {

	// Fields

	private String id;
	private String attachId;
	private String sheetId;
	private String processId;
	private String fieldId;
	
	public WfAttachment() {
		RandomN random = new Random15();
		this.id = random.getRandom(System.currentTimeMillis());
	}
	
	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSheetId() {
		return sheetId;
	}
	public void setSheetId(String sheetId) {
		this.sheetId = sheetId;
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getAttachId() {
		return attachId;
	}

	public void setAttachId(String attachId) {
		this.attachId = attachId;
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}
}