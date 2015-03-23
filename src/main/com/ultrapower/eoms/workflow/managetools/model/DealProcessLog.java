package com.ultrapower.eoms.workflow.managetools.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BS_T_WF_DEALPROCESSLOG")
public class DealProcessLog implements Serializable
{
	 /**
	 * 
	 */
	private static final long serialVersionUID = -191113402203958688L;
	// Fields    

    private String processlogid;
    private String processid;
    private String processtype;
    private String phaseno;
    private String baseid;
    private String baseschema;
    private String flowid;
    private String parentbaseid;
    private String parentflowid;
    private String basebaseid;
    private String baseflowid;
    private String loguserid;
    private String loguser;
    private String loguserdepid;
    private String loguserdep;
    private String logusercorpid;
    private String logusercorp;
    private String loguserdnid;
    private String loguserdn;
    private String actionname;
    private Long basecreatetime = 0l;
    private String logdesc;
    private Long logtime = 0l;


   // Constructors

   /** default constructor */
   public DealProcessLog() {
   }

	/** minimal constructor */
   public DealProcessLog(String processlogid) {
       this.processlogid = processlogid;
   }
   
   /** full constructor */
   public DealProcessLog(String processlogid, String processid, String processtype, String phaseno, String baseid, String baseschema, String flowid, String parentbaseid, String parentflowid, String basebaseid, String baseflowid, String loguserid, String loguser, String loguserdepid, String loguserdep, String logusercorpid, String logusercorp, String loguserdnid, String loguserdn, String actionname, Long basecreatetime, String logdesc, Long logtime) {
       this.processlogid = processlogid;
       this.processid = processid;
       this.processtype = processtype;
       this.phaseno = phaseno;
       this.baseid = baseid;
       this.baseschema = baseschema;
       this.flowid = flowid;
       this.parentbaseid = parentbaseid;
       this.parentflowid = parentflowid;
       this.basebaseid = basebaseid;
       this.baseflowid = baseflowid;
       this.loguserid = loguserid;
       this.loguser = loguser;
       this.loguserdepid = loguserdepid;
       this.loguserdep = loguserdep;
       this.logusercorpid = logusercorpid;
       this.logusercorp = logusercorp;
       this.loguserdnid = loguserdnid;
       this.loguserdn = loguserdn;
       this.actionname = actionname;
       this.basecreatetime = basecreatetime;
       this.logdesc = logdesc;
       this.logtime = logtime;
   }

  
   // Property accessors
   @Id
   @Column(name="PROCESSLOGID", unique=true, nullable=false, insertable=true, updatable=true, length=15)
   public String getProcesslogid() {
       return this.processlogid;
   }
   
   public void setProcesslogid(String processlogid) {
       this.processlogid = processlogid;
   }
   
   @Column(name="PROCESSID", unique=false, nullable=true, insertable=true, updatable=true, length=254)

   public String getProcessid() {
       return this.processid;
   }
   
   public void setProcessid(String processid) {
       this.processid = processid;
   }
   
   @Column(name="PROCESSTYPE", unique=false, nullable=true, insertable=true, updatable=true, length=254)

   public String getProcesstype() {
       return this.processtype;
   }
   
   public void setProcesstype(String processtype) {
       this.processtype = processtype;
   }
   
   @Column(name="PHASENO", unique=false, nullable=true, insertable=true, updatable=true, length=254)

   public String getPhaseno() {
       return this.phaseno;
   }
   
   public void setPhaseno(String phaseno) {
       this.phaseno = phaseno;
   }
   
   @Column(name="BASEID", unique=false, nullable=true, insertable=true, updatable=true, length=254)

   public String getBaseid() {
       return this.baseid;
   }
   
   public void setBaseid(String baseid) {
       this.baseid = baseid;
   }
   
   @Column(name="BASESCHEMA", unique=false, nullable=true, insertable=true, updatable=true, length=254)

   public String getBaseschema() {
       return this.baseschema;
   }
   
   public void setBaseschema(String baseschema) {
       this.baseschema = baseschema;
   }
   
   @Column(name="FLOWID", unique=false, nullable=true, insertable=true, updatable=true, length=254)

   public String getFlowid() {
       return this.flowid;
   }
   
   public void setFlowid(String flowid) {
       this.flowid = flowid;
   }
   
   @Column(name="PARENTBASEID", unique=false, nullable=true, insertable=true, updatable=true, length=254)

   public String getParentbaseid() {
       return this.parentbaseid;
   }
   
   public void setParentbaseid(String parentbaseid) {
       this.parentbaseid = parentbaseid;
   }
   
   @Column(name="PARENTFLOWID", unique=false, nullable=true, insertable=true, updatable=true, length=254)

   public String getParentflowid() {
       return this.parentflowid;
   }
   
   public void setParentflowid(String parentflowid) {
       this.parentflowid = parentflowid;
   }
   
   @Column(name="BASEBASEID", unique=false, nullable=true, insertable=true, updatable=true, length=254)

   public String getBasebaseid() {
       return this.basebaseid;
   }
   
   public void setBasebaseid(String basebaseid) {
       this.basebaseid = basebaseid;
   }
   
   @Column(name="BASEFLOWID", unique=false, nullable=true, insertable=true, updatable=true, length=254)

   public String getBaseflowid() {
       return this.baseflowid;
   }
   
   public void setBaseflowid(String baseflowid) {
       this.baseflowid = baseflowid;
   }
   
   @Column(name="LOGUSERID", unique=false, nullable=true, insertable=true, updatable=true, length=254)

   public String getLoguserid() {
       return this.loguserid;
   }
   
   public void setLoguserid(String loguserid) {
       this.loguserid = loguserid;
   }
   
   @Column(name="LOGUSER", unique=false, nullable=true, insertable=true, updatable=true, length=254)

   public String getLoguser() {
       return this.loguser;
   }
   
   public void setLoguser(String loguser) {
       this.loguser = loguser;
   }
   
   @Column(name="LOGUSERDEPID", unique=false, nullable=true, insertable=true, updatable=true, length=254)

   public String getLoguserdepid() {
       return this.loguserdepid;
   }
   
   public void setLoguserdepid(String loguserdepid) {
       this.loguserdepid = loguserdepid;
   }
   
   @Column(name="LOGUSERDEP", unique=false, nullable=true, insertable=true, updatable=true, length=254)

   public String getLoguserdep() {
       return this.loguserdep;
   }
   
   public void setLoguserdep(String loguserdep) {
       this.loguserdep = loguserdep;
   }
   
   @Column(name="LOGUSERCORPID", unique=false, nullable=true, insertable=true, updatable=true, length=254)

   public String getLogusercorpid() {
       return this.logusercorpid;
   }
   
   public void setLogusercorpid(String logusercorpid) {
       this.logusercorpid = logusercorpid;
   }
   
   @Column(name="LOGUSERCORP", unique=false, nullable=true, insertable=true, updatable=true, length=254)

   public String getLogusercorp() {
       return this.logusercorp;
   }
   
   public void setLogusercorp(String logusercorp) {
       this.logusercorp = logusercorp;
   }
   
   @Column(name="LOGUSERDNID", unique=false, nullable=true, insertable=true, updatable=true, length=254)

   public String getLoguserdnid() {
       return this.loguserdnid;
   }
   
   public void setLoguserdnid(String loguserdnid) {
       this.loguserdnid = loguserdnid;
   }
   
   @Column(name="LOGUSERDN", unique=false, nullable=true, insertable=true, updatable=true, length=254)

   public String getLoguserdn() {
       return this.loguserdn;
   }
   
   public void setLoguserdn(String loguserdn) {
       this.loguserdn = loguserdn;
   }
   
   @Column(name="ACTIONNAME", unique=false, nullable=true, insertable=true, updatable=true, length=254)

   public String getActionname() {
       return this.actionname;
   }
   
   public void setActionname(String actionname) {
       this.actionname = actionname;
   }
   
   @Column(name="BASECREATETIME", unique=false, nullable=true, insertable=true, updatable=true, scale=0)

   public Long getBasecreatetime() {
       return this.basecreatetime;
   }
   
   public void setBasecreatetime(Long basecreatetime) {
       this.basecreatetime = basecreatetime;
   }
   
   @Column(name="LOGDESC", unique=false, nullable=true, insertable=true, updatable=true, length=2000)

   public String getLogdesc() {
       return this.logdesc;
   }
   
   public void setLogdesc(String logdesc) {
       this.logdesc = logdesc;
   }
   
   @Column(name="LOGTIME", unique=false, nullable=true, insertable=true, updatable=true, scale=0)

   public Long getLogtime() {
       return this.logtime;
   }
   
   public void setLogtime(Long logtime) {
       this.logtime = logtime;
   }
}
