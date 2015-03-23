package com.ultrapower.eoms.common.core.component.sms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * Smsmodel entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BS_T_SM_SMSMODEL")
public class Smsmodel implements java.io.Serializable {

	// Fields

	private String pid;
	private String modelType;
	private String content;
	private String createtime;
	private String creater;
	private String modifytime;
	private String modifier;

	// Constructors

	/** default constructor */
	public Smsmodel() {
	}

	/** minimal constructor */
	public Smsmodel(String pid) {
		this.pid = pid;
	}

	/** full constructor */
	public Smsmodel(String pid, String modelType, String content,
			String createtime, String creater, String modifytime,
			String modifier) {
		this.pid = pid;
		this.modelType = modelType;
		this.content = content;
		this.createtime = createtime;
		this.creater = creater;
		this.modifytime = modifytime;
		this.modifier = modifier;
	}

	// Property accessors
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "PID", nullable = false, length = 50)
	public String getPid() {
		return this.pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	@Column(name = "MODELTYPE", length = 50)
	public String getModelType() {
		return this.modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	@Column(name = "CONTENT", length = 4000)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "CREATETIME", length = 20)
	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	@Column(name = "CREATER", length = 20)
	public String getCreater() {
		return this.creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	@Column(name = "MODIFYTIME", length = 20)
	public String getModifytime() {
		return this.modifytime;
	}

	public void setModifytime(String modifytime) {
		this.modifytime = modifytime;
	}

	@Column(name = "MODIFIER", length = 20)
	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	@Override
	public boolean equals(Object obj) {
		boolean isEquals = false;
		if(null!=obj && obj instanceof Smsmodel){
			if(null!=this.pid && null!=((Smsmodel)obj).pid){
				if(this.pid.equals(((Smsmodel)obj).pid)){
					isEquals = true;
				}
			}
		}
		
		return isEquals;
	}
	
	

}