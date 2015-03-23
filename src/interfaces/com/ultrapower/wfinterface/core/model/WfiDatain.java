package com.ultrapower.wfinterface.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ultrapower.randomutil.Random15;
import com.ultrapower.randomutil.RandomN;


/**
 * 接口入参记录表
 */
@Entity
@Table(name="BS_T_WFI_DATAIN")

public class WfiDatain  implements java.io.Serializable {

	private static final long serialVersionUID = 5590778553806532681L;
	private String pid;
     private String sheettype;
     private String opaction;
     private String servicetype;
     private String serialno;
     private String sersupplier;
     private String sercaller;
     private int sendnumber;
     private String calltime;
     private String opperson;
     private String opcorp;
     private String opdepart;
     private String opcontact;
     private String optime;
     private String opdetail;
     private String attachref;
     private Long createtime;
     private int opstate=0;
     private Long scantime;
     private String methodname;
     private String dealdesc;
     //private List<WfiTempAttachment> wfiTempAttachments = new ArrayList<WfiTempAttachment>();


    // Constructors

    /** default constructor */
    public WfiDatain() {
		RandomN ran = new Random15();
		this.pid = ran.getRandom(System.currentTimeMillis());
    }

	/** minimal constructor */
    public WfiDatain(String pid) {
        this.pid = pid;
    }
    
    /** full constructor */
    public WfiDatain(String pid, String sheettype, String opaction, String servicetype, String serialno, String sersupplier, String sercaller, Short sendnumber, String calltime, String opperson, String opcorp, String opdepart, String opcontact, String optime, String opdetail, String attachref, Long createtime, Byte opstate, Long scantime, String methodname) {
    	if (pid==null || "".equals(pid)){
    		RandomN ran = new Random15();
    		this.pid = ran.getRandom(System.currentTimeMillis());
    	} else {
    		this.pid = pid;
    	}
        this.sheettype = sheettype;
        this.opaction = opaction;
        this.servicetype = servicetype;
        this.serialno = serialno;
        this.sersupplier = sersupplier;
        this.sercaller = sercaller;
        this.sendnumber = sendnumber;
        this.calltime = calltime;
        this.opperson = opperson;
        this.opcorp = opcorp;
        this.opdepart = opdepart;
        this.opcontact = opcontact;
        this.optime = optime;
        this.opdetail = opdetail;
        this.attachref = attachref;
        this.createtime = createtime;
        this.opstate = opstate;
        this.scantime = scantime;
        this.methodname = methodname;
    }

   
    // Property accessors
    @Id 
    @Column(name="PID", unique=true, nullable=false, length=50)
    public String getPid() {
        return this.pid;
    }
    
    public void setPid(String pid) {
        this.pid = pid;
    }
    
    @Column(name="SHEETTYPE", length=50)

    public String getSheettype() {
        return this.sheettype;
    }
    
    public void setSheettype(String sheettype) {
        this.sheettype = sheettype;
    }
    
    @Column(name="OPACTION", length=30)

    public String getOpaction() {
        return this.opaction;
    }
    
    public void setOpaction(String opaction) {
        this.opaction = opaction;
    }
    
    @Column(name="SERVICETYPE", length=4)

    public String getServicetype() {
        return this.servicetype;
    }
    
    public void setServicetype(String servicetype) {
        this.servicetype = servicetype;
    }
    
    @Column(name="SERIALNO", length=50)

    public String getSerialno() {
        return this.serialno;
    }
    
    public void setSerialno(String serialno) {
        this.serialno = serialno;
    }
    
    @Column(name="SERSUPPLIER", length=20)

    public String getSersupplier() {
        return this.sersupplier;
    }
    
    public void setSersupplier(String sersupplier) {
        this.sersupplier = sersupplier;
    }
    
    @Column(name="SERCALLER", length=20)

    public String getSercaller() {
        return this.sercaller;
    }
    
    public void setSercaller(String sercaller) {
        this.sercaller = sercaller;
    }
    
    @Column(name="SENDNUMBER", precision=3, scale=0)

    public int getSendnumber() {
        return this.sendnumber;
    }
    
    public void setSendnumber(int sendnumber) {
        this.sendnumber = sendnumber;
    }
    
    @Column(name="CALLTIME", length=20)

    public String getCalltime() {
        return this.calltime;
    }
    
    public void setCalltime(String calltime) {
        this.calltime = calltime;
    }
    
    @Column(name="OPPERSON", length=50)

    public String getOpperson() {
        return this.opperson;
    }
    
    public void setOpperson(String opperson) {
        this.opperson = opperson;
    }
    
    @Column(name="OPCORP", length=50)

    public String getOpcorp() {
        return this.opcorp;
    }
    
    public void setOpcorp(String opcorp) {
        this.opcorp = opcorp;
    }
    
    @Column(name="OPDEPART", length=50)

    public String getOpdepart() {
        return this.opdepart;
    }
    
    public void setOpdepart(String opdepart) {
        this.opdepart = opdepart;
    }
    
    @Column(name="OPCONTACT", length=12)

    public String getOpcontact() {
        return this.opcontact;
    }
    
    public void setOpcontact(String opcontact) {
        this.opcontact = opcontact;
    }
    
    @Column(name="OPTIME", length=12)

    public String getOptime() {
        return this.optime;
    }
    
    public void setOptime(String optime) {
        this.optime = optime;
    }
    
    @Column(name="OPDETAIL")

    public String getOpdetail() {
        return this.opdetail;
    }
    
    public void setOpdetail(String opdetail) {
        this.opdetail = opdetail;
    }
    
    @Column(name="ATTACHREF")

    public String getAttachref() {
        return this.attachref;
    }
    
    public void setAttachref(String attachref) {
        this.attachref = attachref;
    }
    
    @Column(name="CREATETIME", precision=15, scale=0)

    public Long getCreatetime() {
        return this.createtime;
    }
    
    public void setCreatetime(Long createtime) {
        this.createtime = createtime;
    }
    
    @Column(name="OPSTATE", precision=2, scale=0)

    public int getOpstate() {
        return this.opstate;
    }
    
    public void setOpstate(int opstate) {
        this.opstate = opstate;
    }
    
    @Column(name="SCANTIME", precision=15, scale=0)

    public Long getScantime() {
        return this.scantime;
    }
    
    public void setScantime(Long scantime) {
        this.scantime = scantime;
    }
    
    @Column(name="METHODNAME", length=100)

    public String getMethodname() {
        return this.methodname;
    }
    
    public void setMethodname(String methodname) {
        this.methodname = methodname;
    }
    @Column(name="DEALDESC", length=500)
    public String getDealdesc() {
        return this.dealdesc;
    }
    
    public void setDealdesc(String dealdesc) {
        this.dealdesc = dealdesc;
    }    
    
    
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "wfiDatain")
//	public List<WfiTempAttachment> getWfiTempAttachments() {
//		return this.wfiTempAttachments;
//	}
//
//	public void setWfiTempAttachments(List<WfiTempAttachment> wfiTempAttachments) {
//		this.wfiTempAttachments = wfiTempAttachments;
//	}
   
}