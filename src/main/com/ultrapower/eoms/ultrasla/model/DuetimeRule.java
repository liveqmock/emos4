package com.ultrapower.eoms.ultrasla.model;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ultrapower.randomutil.Random15;
import com.ultrapower.randomutil.RandomN;


/**
 * DueTimeRule entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="BS_T_USLA_DUETIMERULE")
public class DuetimeRule  implements java.io.Serializable {

     private String pid;
     private String rulename;
     private String ruleexpress;
     private Long priority;
     private String ruletype;
     private Long accepttime;
     private Long processtime;
     private Long validstarttime = 0L;
     private Long validendtime = 4102415998L;
     private Long status = 1L;
     private Long createtime;
     private Long updatetime;

    public DuetimeRule() {
    	
    }

    public DuetimeRule(String pid, String ruleexpress, Long accepttime, Long processtime) {
        this.pid = pid;
        this.ruleexpress = ruleexpress;
        this.accepttime = accepttime;
        this.processtime = processtime;
    }
    
    public DuetimeRule(String pid, String rulename, String ruleexpress,Long priority,
			String ruletype, Long accepttime, Long processtime,
			Long validstarttime, Long validendtime, Long status,
			Long createtime, Long updatetime) {
		super();
		this.pid = pid;
		this.rulename = rulename;
		this.ruleexpress = ruleexpress;
		this.priority = priority;
		this.ruletype = ruletype;
		this.accepttime = accepttime;
		this.processtime = processtime;
		this.validstarttime = validstarttime;
		this.validendtime = validendtime;
		this.status = status;
		this.createtime = createtime;
		this.updatetime = updatetime;
	}

	@Id
    @Column(name="PID", unique=true, nullable=false, insertable=true, updatable=true, length=15)

    public String getPid() {
        return this.pid;
    }
    
    public void setPid(String pid) {
        this.pid = pid;
    }
    
    @Column(name="RULENAME", unique=false, nullable=true, insertable=true, updatable=true, length=150)

    public String getRulename() {
        return this.rulename;
    }
    
    public void setRulename(String rulename) {
        this.rulename = rulename;
    }
    
    @Column(name="RULEEXPRESS", unique=false, nullable=false, insertable=true, updatable=true, length=1000)

    public String getRuleexpress() {
        return this.ruleexpress;
    }
    
    public void setRuleexpress(String ruleexpress) {
        this.ruleexpress = ruleexpress;
    }
    
    @Column(name="PRIORITY", unique=false, nullable=true, insertable=true, updatable=true, precision=2, scale=0)
    
    public Long getPriority() {
		return priority;
	}

	public void setPriority(Long priority) {
		this.priority = priority;
	}

	@Column(name="RULETYPE", unique=false, nullable=true, insertable=true, updatable=true, length=100)

    public String getRuletype() {
        return this.ruletype;
    }
    
    public void setRuletype(String ruletype) {
        this.ruletype = ruletype;
    }
    
    @Column(name="ACCEPTTIME", unique=false, nullable=false, insertable=true, updatable=true, precision=15, scale=0)

    public Long getAccepttime() {
        return this.accepttime;
    }
    
    public void setAccepttime(Long accepttime) {
        this.accepttime = accepttime;
    }
    
    @Column(name="PROCESSTIME", unique=false, nullable=false, insertable=true, updatable=true, precision=15, scale=0)

    public Long getProcesstime() {
        return this.processtime;
    }
    
    public void setProcesstime(Long processtime) {
        this.processtime = processtime;
    }
    @Column(name="VALIDSTARTTIME", unique=false, nullable=true, insertable=true, updatable=true, precision=15, scale=0)

    public Long getValidstarttime() {
        return this.validstarttime;
    }
    
    public void setValidstarttime(Long validstarttime) {
        this.validstarttime = validstarttime;
    }
    
    @Column(name="VALIDENDTIME", unique=false, nullable=true, insertable=true, updatable=true, precision=15, scale=0)

    public Long getValidendtime() {
        return this.validendtime;
    }
    
    public void setValidendtime(Long validendtime) {
        this.validendtime = validendtime;
    }
    
    @Column(name="STATUS", unique=false, nullable=false, insertable=true, updatable=true, precision=2, scale=0)

    public Long getStatus() {
        return this.status;
    }
    
    public void setStatus(Long status) {
        this.status = status;
    }
    
    @Column(name="CREATETIME", unique=false, nullable=true, insertable=true, updatable=true, precision=15, scale=0)

    public Long getCreatetime() {
        return this.createtime;
    }
    
    public void setCreatetime(Long createtime) {
        this.createtime = createtime;
    }
    
    @Column(name="UPDATETIME", unique=false, nullable=true, insertable=true, updatable=true, precision=15, scale=0)

    public Long getUpdatetime() {
        return this.updatetime;
    }
    
    public void setUpdatetime(Long updatetime) {
        this.updatetime = updatetime;
    }
}