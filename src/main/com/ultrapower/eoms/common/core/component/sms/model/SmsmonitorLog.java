package com.ultrapower.eoms.common.core.component.sms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SmsmonitorLog entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BS_T_SM_SMSMONITORLOG")
public class SmsmonitorLog implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = -1878268523364697940L;
	private String smsmonitorpid;
	private String content;
	private String systemtype;
	private String goal;
	private int sendflag;
	private long presendtime;
	private String relateid;
	private String gatewaycode;
	private String gatewaycribe;
	private int sendnum;
	private long firstsendtime; 
	private long sendtime;
	private int pri;
	private long infoinputtime;
	private String rematk;
	private long separatetime;

	// Constructors

	/** default constructor */
	public SmsmonitorLog() {
	}

	/** minimal constructor */
	public SmsmonitorLog(String smsmonitorpid) {
		this.smsmonitorpid = smsmonitorpid;
	}

	/** full constructor */
	public SmsmonitorLog(String smsmonitorpid, String content,
			String systemtype, String goal, int sendflag, long presendtime,
			String relateid, String gatewaycode, String gatewaycribe,
			int sendnum, long firstsendtime, int sendtime, int pri,
			long infoinputtime, String rematk, long separatetime) {
		this.smsmonitorpid = smsmonitorpid;
		this.content = content;
		this.systemtype = systemtype;
		this.goal = goal;
		this.sendflag = sendflag;
		this.presendtime = presendtime;
		this.relateid = relateid;
		this.gatewaycode = gatewaycode;
		this.gatewaycribe = gatewaycribe;
		this.sendnum = sendnum;
		this.firstsendtime = firstsendtime;
		this.sendtime = sendtime;
		this.pri = pri;
		this.infoinputtime = infoinputtime;
		this.rematk = rematk;
		this.separatetime = separatetime;
	}

	// Property accessors
	@Id
	//@GeneratedValue(generator = "system-uuid")
	//@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "SMSMONITORPID", unique = true, nullable = false, insertable = true, updatable = true, length = 50)
	public String getSmsmonitorpid() {
		return this.smsmonitorpid;
	}

	public void setSmsmonitorpid(String smsmonitorpid) {
		this.smsmonitorpid = smsmonitorpid;
	}

	@Column(name = "CONTENT", unique = false, nullable = true, insertable = true, updatable = true, length = 500)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "SYSTEMTYPE", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getSystemtype() {
		return this.systemtype;
	}

	public void setSystemtype(String systemtype) {
		this.systemtype = systemtype;
	}

	@Column(name = "GOAL", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public String getGoal() {
		return this.goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	@Column(name = "SENDFLAG", unique = false, nullable = true, insertable = true, updatable = true, precision = 2, scale = 0)
	public int getSendflag() {
		return this.sendflag;
	}

	public void setSendflag(int sendflag) {
		this.sendflag = sendflag;
	}

	@Column(name = "PRESENDTIME", unique = false, nullable = true, insertable = true, updatable = true, precision = 15, scale = 0)
	public long getPresendtime() {
		return this.presendtime;
	}

	public void setPresendtime(long presendtime) {
		this.presendtime = presendtime;
	}

	@Column(name = "RELATEID", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getRelateid() {
		return this.relateid;
	}

	public void setRelateid(String relateid) {
		this.relateid = relateid;
	}

	@Column(name = "GATEWAYCODE", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getGatewaycode() {
		return this.gatewaycode;
	}

	public void setGatewaycode(String gatewaycode) {
		this.gatewaycode = gatewaycode;
	}

	@Column(name = "GATEWAYCRIBE", unique = false, nullable = true, insertable = true, updatable = true, length = 300)
	public String getGatewaycribe() {
		return this.gatewaycribe;
	}

	public void setGatewaycribe(String gatewaycribe) {
		this.gatewaycribe = gatewaycribe;
	}

	@Column(name = "SENDNUM", unique = false, nullable = true, insertable = true, updatable = true, precision = 4, scale = 0)
	public int getSendnum() {
		return this.sendnum;
	}

	public void setSendnum(int sendnum) {
		this.sendnum = sendnum;
	}

    @Column(name="FIRSTSENDTIME", unique=false, nullable=true, insertable=true, updatable=true, precision=15, scale=0)

    public long getFirstsendtime() {
        return this.firstsendtime;
    }
    
    public void setFirstsendtime(long firstsendtime) {
        this.firstsendtime = firstsendtime;
    }

	@Column(name = "SENDTIME", unique = false, nullable = true, insertable = true, updatable = true, precision = 15, scale = 0)
	public long getSendtime() {
		return this.sendtime;
	}

	public void setSendtime(long sendtime) {
		this.sendtime = sendtime;
	}

	@Column(name = "PRI", unique = false, nullable = true, insertable = true, updatable = true, precision = 5, scale = 0)
	public int getPri() {
		return this.pri;
	}

	public void setPri(int pri) {
		this.pri = pri;
	}

	@Column(name = "INFOINPUTTIME", unique = false, nullable = true, insertable = true, updatable = true, precision = 15, scale = 0)
	public long getInfoinputtime() {
		return this.infoinputtime;
	}

	public void setInfoinputtime(long infoinputtime) {
		this.infoinputtime = infoinputtime;
	}

	@Column(name = "REMATK", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getRematk() {
		return this.rematk;
	}

	public void setRematk(String rematk) {
		this.rematk = rematk;
	}

	@Column(name = "SEPARATETIME", unique = false, nullable = true, insertable = true, updatable = true, precision = 15, scale = 0)
	public long getSeparatetime() {
		return this.separatetime;
	}

	public void setSeparatetime(long separatetime) {
		this.separatetime = separatetime;
	}

}