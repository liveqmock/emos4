package com.ultrapower.eoms.common.core.component.sla.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * SlaRuleProperty entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BS_T_SM_SLARULEPROPERTY")
public class SlaRuleProperty implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 7296767268497973996L;
	private String pid;
	private String ruleid;
	private String propertyid;
	private String value;
	private String displayvalue;
	private String operator;

	// Constructors

	/** default constructor */
	public SlaRuleProperty() {
	}

	/** minimal constructor */
	public SlaRuleProperty(String pid) {
		this.pid = pid;
	}

	/** full constructor */
	public SlaRuleProperty(String pid, String ruleid, String propertyid,
			String value, String displayvalue, String operator) {
		this.pid = pid;
		this.ruleid = ruleid;
		this.propertyid = propertyid;
		this.value = value;
		this.displayvalue = displayvalue;
		this.operator = operator;
	}

	// Property accessors
	@Id
    @GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "PID", unique = true, nullable = false, insertable = true, updatable = true, length = 50)
	public String getPid() {
		return this.pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	@Column(name = "RULEID", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getRuleid() {
		return this.ruleid;
	}

	public void setRuleid(String ruleid) {
		this.ruleid = ruleid;
	}

	@Column(name = "PROPERTYID", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getPropertyid() {
		return this.propertyid;
	}

	public void setPropertyid(String propertyid) {
		this.propertyid = propertyid;
	}

	@Column(name = "VALUE", unique = false, nullable = true, insertable = true, updatable = true, length = 500)
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Column(name = "DISPLAYVALUE", unique = false, nullable = true, insertable = true, updatable = true, length = 500)
	public String getDisplayvalue() {
		return this.displayvalue;
	}

	public void setDisplayvalue(String displayvalue) {
		this.displayvalue = displayvalue;
	}

	@Column(name = "OPERATOR", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

}