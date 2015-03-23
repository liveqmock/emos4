package com.ultrapower.eoms.workflow.managetools.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BS_T_WF_DEALPROCESS")
public class DealProcess implements Serializable
{
    // Fields    

    /**
	 * 
	 */
	private static final long serialVersionUID = -7283132967933777574L;
	private String processid;
    private String processtype = "DEAL";
    private String taskid;
    private String phaseno;
    private String stepid;
    private String forwardstepid;
    private String prephaseno;
    private String processstatus;
    private Long flagactive = 0l;
    private Long flagduplicated = 0l;
    private Long flagendduplicated = 0l;
    private Long flagendphase = 0l;
    private Long flagpredefined = 0l;
    private String stprocessaction;
    private String edprocessaction;
    private Long selfdeallength = 0l;
    private Long basestartdate = 0l;
    private String baseid;
    private String baseschema;
    private String flowid;
    private String parentflowid;
    private String baseflowid;
    private String entryid;
    private String parententryid;
    private String topentryid;
    private String actortype;
    private String dealtype;
    private String assigneeid;
    private String assignee;
    private String assigneedepid;
    private String assigneedep;
    private String assigneecorpid;
    private String assigneecorp;
    private String assigneednid;
    private String assigneedn;
    private String assigngroupid;
    private String assigngroup;
    private String flagassigntype;
    private String dealerid;
    private String dealer;
    private String dealerdepid;
    private String dealerdep;
    private String dealercorpid;
    private String dealercorp;
    private String dealerdnid;
    private String dealerdn;
    private String assignerid;
    private String assigner;
    private String assignerdepid;
    private String assignerdep;
    private String assignercorpid;
    private String assignercorp;
    private String assignerdnid;
    private String assignerdn;
    private Long stdate = 0l;
    private Long bgdate = 0l;
    private Long eddate = 0l;
    private Long assignovertimedate = 0l;
    private Long acceptovertimedate = 0l;
    private Long dealovertimedate = 0l;
    private Long flagassign = 0l;
    private Long flagassist = 0l;
    private Long flagcopy = 0l;
    private Long flagtransfer = 0l;
    private Long flagturnup = 0l;
    private Long flagrecall = 0l;
    private Long flagcancel = 0l;
    private Long flagclose = 0l;
    private Long flagturndown = 0l;
    private Long flagtoassistauditing = 0l;
    private Long flagtoauditing = 0l;
    private Long flagstartinsideflow = 0l;
    private Long flagauditingresult = 1l;
    private Long insideflowscount = 0l;
    private Long finishinsideflowscount = 0l;
    private Long hastencount = 0l;
    private Long basecreatetime = 0l;
    private String roleid;
    private String rolename;
    private String actionname;
    private String customactions;
    private String dpdesc;
    private String preassignstring;
    private String origphaseno;


   // Constructors

   /** default constructor */
   public DealProcess() {
   }

	/** minimal constructor */
   public DealProcess(String processid) {
       this.processid = processid;
   }
   
   /** full constructor */
   public DealProcess(String processid, String processtype, String taskid, String phaseno, String stepid, String forwardstepid, String prephaseno, String processstatus, Long flagactive, Long flagduplicated, Long flagendduplicated, Long flagendphase, Long flagpredefined, String stprocessaction, String edprocessaction, Long selfdeallength, Long basestartdate, String baseid, String baseschema, String flowid, String parentflowid, String baseflowid, String entryid, String parententryid, String topentryid, String actortype, String dealtype, String assigneeid, String assignee, String assigneedepid, String assigneedep, String assigneecorpid, String assigneecorp, String assigneednid, String assigneedn, String assigngroupid, String assigngroup, String flagassigntype, String dealerid, String dealer, String dealerdepid, String dealerdep, String dealercorpid, String dealercorp, String dealerdnid, String dealerdn, String assignerid, String assigner, String assignerdepid, String assignerdep, String assignercorpid, String assignercorp, String assignerdnid, String assignerdn, Long stdate, Long bgdate, Long eddate, Long assignovertimedate, Long acceptovertimedate, Long dealovertimedate, Long flagassign, Long flagassist, Long flagcopy, Long flagtransfer, Long flagturnup, Long flagrecall, Long flagcancel, Long flagclose, Long flagturndown, Long flagtoassistauditing, Long flagtoauditing, Long flagstartinsideflow, Long flagauditingresult, Long insideflowscount, Long finishinsideflowscount, Long hastencount, Long basecreatetime, String roleid, String rolename, String actionname, String customactions, String dpdesc, String preassignstring, String origphaseno) {
       this.processid = processid;
       this.processtype = processtype;
       this.taskid = taskid;
       this.phaseno = phaseno;
       this.stepid = stepid;
       this.forwardstepid = forwardstepid;
       this.prephaseno = prephaseno;
       this.processstatus = processstatus;
       this.flagactive = flagactive;
       this.flagduplicated = flagduplicated;
       this.flagendduplicated = flagendduplicated;
       this.flagendphase = flagendphase;
       this.flagpredefined = flagpredefined;
       this.stprocessaction = stprocessaction;
       this.edprocessaction = edprocessaction;
       this.selfdeallength = selfdeallength;
       this.basestartdate = basestartdate;
       this.baseid = baseid;
       this.baseschema = baseschema;
       this.flowid = flowid;
       this.parentflowid = parentflowid;
       this.baseflowid = baseflowid;
       this.entryid = entryid;
       this.parententryid = parententryid;
       this.topentryid = topentryid;
       this.actortype = actortype;
       this.dealtype = dealtype;
       this.assigneeid = assigneeid;
       this.assignee = assignee;
       this.assigneedepid = assigneedepid;
       this.assigneedep = assigneedep;
       this.assigneecorpid = assigneecorpid;
       this.assigneecorp = assigneecorp;
       this.assigneednid = assigneednid;
       this.assigneedn = assigneedn;
       this.assigngroupid = assigngroupid;
       this.assigngroup = assigngroup;
       this.flagassigntype = flagassigntype;
       this.dealerid = dealerid;
       this.dealer = dealer;
       this.dealerdepid = dealerdepid;
       this.dealerdep = dealerdep;
       this.dealercorpid = dealercorpid;
       this.dealercorp = dealercorp;
       this.dealerdnid = dealerdnid;
       this.dealerdn = dealerdn;
       this.assignerid = assignerid;
       this.assigner = assigner;
       this.assignerdepid = assignerdepid;
       this.assignerdep = assignerdep;
       this.assignercorpid = assignercorpid;
       this.assignercorp = assignercorp;
       this.assignerdnid = assignerdnid;
       this.assignerdn = assignerdn;
       this.stdate = stdate;
       this.bgdate = bgdate;
       this.eddate = eddate;
       this.assignovertimedate = assignovertimedate;
       this.acceptovertimedate = acceptovertimedate;
       this.dealovertimedate = dealovertimedate;
       this.flagassign = flagassign;
       this.flagassist = flagassist;
       this.flagcopy = flagcopy;
       this.flagtransfer = flagtransfer;
       this.flagturnup = flagturnup;
       this.flagrecall = flagrecall;
       this.flagcancel = flagcancel;
       this.flagclose = flagclose;
       this.flagturndown = flagturndown;
       this.flagtoassistauditing = flagtoassistauditing;
       this.flagtoauditing = flagtoauditing;
       this.flagstartinsideflow = flagstartinsideflow;
       this.flagauditingresult = flagauditingresult;
       this.insideflowscount = insideflowscount;
       this.finishinsideflowscount = finishinsideflowscount;
       this.hastencount = hastencount;
       this.basecreatetime = basecreatetime;
       this.roleid = roleid;
       this.rolename = rolename;
       this.actionname = actionname;
       this.customactions = customactions;
       this.dpdesc = dpdesc;
       this.preassignstring = preassignstring;
       this.origphaseno = origphaseno;
   }

  
   // Property accessors
   @Id
   @Column(name="PROCESSID", unique=true, nullable=false, insertable=true, updatable=true, length=15)
   public String getProcessid() {
       return this.processid;
   }
   
   public void setProcessid(String processid) {
       this.processid = processid;
   }
   
   @Column(name="PROCESSTYPE", unique=false, nullable=true, insertable=true, updatable=true, length=15)

   public String getProcesstype() {
       return this.processtype;
   }
   
   public void setProcesstype(String processtype) {
       this.processtype = processtype;
   }
   
   @Column(name="TASKID", unique=false, nullable=true, insertable=true, updatable=true, length=100)

   public String getTaskid() {
       return this.taskid;
   }
   
   public void setTaskid(String taskid) {
       this.taskid = taskid;
   }
   
   @Column(name="PHASENO", unique=false, nullable=true, insertable=true, updatable=true, length=100)

   public String getPhaseno() {
       return this.phaseno;
   }
   
   public void setPhaseno(String phaseno) {
       this.phaseno = phaseno;
   }
   
   @Column(name="STEPID", unique=false, nullable=true, insertable=true, updatable=true, length=100)

   public String getStepid() {
       return this.stepid;
   }
   
   public void setStepid(String stepid) {
       this.stepid = stepid;
   }
   
   @Column(name="FORWARDSTEPID", unique=false, nullable=true, insertable=true, updatable=true, length=100)

   public String getForwardstepid() {
       return this.forwardstepid;
   }
   
   public void setForwardstepid(String forwardstepid) {
       this.forwardstepid = forwardstepid;
   }
   
   @Column(name="PREPHASENO", unique=false, nullable=true, insertable=true, updatable=true, length=100)

   public String getPrephaseno() {
       return this.prephaseno;
   }
   
   public void setPrephaseno(String prephaseno) {
       this.prephaseno = prephaseno;
   }
   
   @Column(name="PROCESSSTATUS", unique=false, nullable=true, insertable=true, updatable=true, length=50)

   public String getProcessstatus() {
       return this.processstatus;
   }
   
   public void setProcessstatus(String processstatus) {
       this.processstatus = processstatus;
   }
   
   @Column(name="FLAGACTIVE", unique=false, nullable=true, insertable=true, updatable=true, scale=0)

   public Long getFlagactive() {
       return this.flagactive;
   }
   
   public void setFlagactive(Long flagactive) {
       this.flagactive = flagactive;
   }
   
   @Column(name="FLAGDUPLICATED", unique=false, nullable=true, insertable=true, updatable=true, scale=0)

   public Long getFlagduplicated() {
       return this.flagduplicated;
   }
   
   public void setFlagduplicated(Long flagduplicated) {
       this.flagduplicated = flagduplicated;
   }
   
   @Column(name="FLAGENDDUPLICATED", unique=false, nullable=true, insertable=true, updatable=true, scale=0)

   public Long getFlagendduplicated() {
       return this.flagendduplicated;
   }
   
   public void setFlagendduplicated(Long flagendduplicated) {
       this.flagendduplicated = flagendduplicated;
   }
   
   @Column(name="FLAGENDPHASE", unique=false, nullable=true, insertable=true, updatable=true, scale=0)

   public Long getFlagendphase() {
       return this.flagendphase;
   }
   
   public void setFlagendphase(Long flagendphase) {
       this.flagendphase = flagendphase;
   }
   
   @Column(name="FLAGPREDEFINED", unique=false, nullable=true, insertable=true, updatable=true, scale=0)

   public Long getFlagpredefined() {
       return this.flagpredefined;
   }
   
   public void setFlagpredefined(Long flagpredefined) {
       this.flagpredefined = flagpredefined;
   }
   
   @Column(name="STPROCESSACTION", unique=false, nullable=true, insertable=true, updatable=true, length=50)

   public String getStprocessaction() {
       return this.stprocessaction;
   }
   
   public void setStprocessaction(String stprocessaction) {
       this.stprocessaction = stprocessaction;
   }
   
   @Column(name="EDPROCESSACTION", unique=false, nullable=true, insertable=true, updatable=true, length=50)

   public String getEdprocessaction() {
       return this.edprocessaction;
   }
   
   public void setEdprocessaction(String edprocessaction) {
       this.edprocessaction = edprocessaction;
   }
   
   @Column(name="SELFDEALLENGTH", unique=false, nullable=true, insertable=true, updatable=true, scale=0)

   public Long getSelfdeallength() {
       return this.selfdeallength;
   }
   
   public void setSelfdeallength(Long selfdeallength) {
       this.selfdeallength = selfdeallength;
   }
   
   @Column(name="BASESTARTDATE", unique=false, nullable=true, insertable=true, updatable=true, scale=0)

   public Long getBasestartdate() {
       return this.basestartdate;
   }
   
   public void setBasestartdate(Long basestartdate) {
       this.basestartdate = basestartdate;
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
   
   @Column(name="FLOWID", unique=false, nullable=true, insertable=true, updatable=true, length=100)

   public String getFlowid() {
       return this.flowid;
   }
   
   public void setFlowid(String flowid) {
       this.flowid = flowid;
   }
   
   @Column(name="PARENTFLOWID", unique=false, nullable=true, insertable=true, updatable=true, length=100)

   public String getParentflowid() {
       return this.parentflowid;
   }
   
   public void setParentflowid(String parentflowid) {
       this.parentflowid = parentflowid;
   }
   
   @Column(name="BASEFLOWID", unique=false, nullable=true, insertable=true, updatable=true, length=100)

   public String getBaseflowid() {
       return this.baseflowid;
   }
   
   public void setBaseflowid(String baseflowid) {
       this.baseflowid = baseflowid;
   }
   
   @Column(name="ENTRYID", unique=false, nullable=true, insertable=true, updatable=true, length=50)

   public String getEntryid() {
       return this.entryid;
   }
   
   public void setEntryid(String entryid) {
       this.entryid = entryid;
   }
   
   @Column(name="PARENTENTRYID", unique=false, nullable=true, insertable=true, updatable=true, length=50)

   public String getParententryid() {
       return this.parententryid;
   }
   
   public void setParententryid(String parententryid) {
       this.parententryid = parententryid;
   }
   
   @Column(name="TOPENTRYID", unique=false, nullable=true, insertable=true, updatable=true, length=50)

   public String getTopentryid() {
       return this.topentryid;
   }
   
   public void setTopentryid(String topentryid) {
       this.topentryid = topentryid;
   }
   
   @Column(name="ACTORTYPE", unique=false, nullable=true, insertable=true, updatable=true, length=50)

   public String getActortype() {
       return this.actortype;
   }
   
   public void setActortype(String actortype) {
       this.actortype = actortype;
   }
   
   @Column(name="DEALTYPE", unique=false, nullable=true, insertable=true, updatable=true, length=50)

   public String getDealtype() {
       return this.dealtype;
   }
   
   public void setDealtype(String dealtype) {
       this.dealtype = dealtype;
   }
   
   @Column(name="ASSIGNEEID", unique=false, nullable=true, insertable=true, updatable=true, length=100)

   public String getAssigneeid() {
       return this.assigneeid;
   }
   
   public void setAssigneeid(String assigneeid) {
       this.assigneeid = assigneeid;
   }
   
   @Column(name="ASSIGNEE", unique=false, nullable=true, insertable=true, updatable=true, length=100)

   public String getAssignee() {
       return this.assignee;
   }
   
   public void setAssignee(String assignee) {
       this.assignee = assignee;
   }
   
   @Column(name="ASSIGNEEDEPID", unique=false, nullable=true, insertable=true, updatable=true, length=100)

   public String getAssigneedepid() {
       return this.assigneedepid;
   }
   
   public void setAssigneedepid(String assigneedepid) {
       this.assigneedepid = assigneedepid;
   }
   
   @Column(name="ASSIGNEEDEP", unique=false, nullable=true, insertable=true, updatable=true, length=200)

   public String getAssigneedep() {
       return this.assigneedep;
   }
   
   public void setAssigneedep(String assigneedep) {
       this.assigneedep = assigneedep;
   }
   
   @Column(name="ASSIGNEECORPID", unique=false, nullable=true, insertable=true, updatable=true, length=100)

   public String getAssigneecorpid() {
       return this.assigneecorpid;
   }
   
   public void setAssigneecorpid(String assigneecorpid) {
       this.assigneecorpid = assigneecorpid;
   }
   
   @Column(name="ASSIGNEECORP", unique=false, nullable=true, insertable=true, updatable=true, length=200)

   public String getAssigneecorp() {
       return this.assigneecorp;
   }
   
   public void setAssigneecorp(String assigneecorp) {
       this.assigneecorp = assigneecorp;
   }
   
   @Column(name="ASSIGNEEDNID", unique=false, nullable=true, insertable=true, updatable=true, length=100)

   public String getAssigneednid() {
       return this.assigneednid;
   }
   
   public void setAssigneednid(String assigneednid) {
       this.assigneednid = assigneednid;
   }
   
   @Column(name="ASSIGNEEDN", unique=false, nullable=true, insertable=true, updatable=true, length=400)

   public String getAssigneedn() {
       return this.assigneedn;
   }
   
   public void setAssigneedn(String assigneedn) {
       this.assigneedn = assigneedn;
   }
   
   @Column(name="ASSIGNGROUPID", unique=false, nullable=true, insertable=true, updatable=true, length=100)

   public String getAssigngroupid() {
       return this.assigngroupid;
   }
   
   public void setAssigngroupid(String assigngroupid) {
       this.assigngroupid = assigngroupid;
   }
   
   @Column(name="ASSIGNGROUP", unique=false, nullable=true, insertable=true, updatable=true, length=200)

   public String getAssigngroup() {
       return this.assigngroup;
   }
   
   public void setAssigngroup(String assigngroup) {
       this.assigngroup = assigngroup;
   }
   
   @Column(name="FLAGASSIGNTYPE", unique=false, nullable=true, insertable=true, updatable=true)

   public String getFlagassigntype() {
       return this.flagassigntype;
   }
   
   public void setFlagassigntype(String flagassigntype) {
       this.flagassigntype = flagassigntype;
   }
   
   @Column(name="DEALERID", unique=false, nullable=true, insertable=true, updatable=true, length=100)

   public String getDealerid() {
       return this.dealerid;
   }
   
   public void setDealerid(String dealerid) {
       this.dealerid = dealerid;
   }
   
   @Column(name="DEALER", unique=false, nullable=true, insertable=true, updatable=true, length=100)

   public String getDealer() {
       return this.dealer;
   }
   
   public void setDealer(String dealer) {
       this.dealer = dealer;
   }
   
   @Column(name="DEALERDEPID", unique=false, nullable=true, insertable=true, updatable=true, length=100)

   public String getDealerdepid() {
       return this.dealerdepid;
   }
   
   public void setDealerdepid(String dealerdepid) {
       this.dealerdepid = dealerdepid;
   }
   
   @Column(name="DEALERDEP", unique=false, nullable=true, insertable=true, updatable=true, length=200)

   public String getDealerdep() {
       return this.dealerdep;
   }
   
   public void setDealerdep(String dealerdep) {
       this.dealerdep = dealerdep;
   }
   
   @Column(name="DEALERCORPID", unique=false, nullable=true, insertable=true, updatable=true, length=100)

   public String getDealercorpid() {
       return this.dealercorpid;
   }
   
   public void setDealercorpid(String dealercorpid) {
       this.dealercorpid = dealercorpid;
   }
   
   @Column(name="DEALERCORP", unique=false, nullable=true, insertable=true, updatable=true, length=200)

   public String getDealercorp() {
       return this.dealercorp;
   }
   
   public void setDealercorp(String dealercorp) {
       this.dealercorp = dealercorp;
   }
   
   @Column(name="DEALERDNID", unique=false, nullable=true, insertable=true, updatable=true, length=100)

   public String getDealerdnid() {
       return this.dealerdnid;
   }
   
   public void setDealerdnid(String dealerdnid) {
       this.dealerdnid = dealerdnid;
   }
   
   @Column(name="DEALERDN", unique=false, nullable=true, insertable=true, updatable=true, length=400)

   public String getDealerdn() {
       return this.dealerdn;
   }
   
   public void setDealerdn(String dealerdn) {
       this.dealerdn = dealerdn;
   }
   
   @Column(name="ASSIGNERID", unique=false, nullable=true, insertable=true, updatable=true, length=100)

   public String getAssignerid() {
       return this.assignerid;
   }
   
   public void setAssignerid(String assignerid) {
       this.assignerid = assignerid;
   }
   
   @Column(name="ASSIGNER", unique=false, nullable=true, insertable=true, updatable=true, length=100)

   public String getAssigner() {
       return this.assigner;
   }
   
   public void setAssigner(String assigner) {
       this.assigner = assigner;
   }
   
   @Column(name="ASSIGNERDEPID", unique=false, nullable=true, insertable=true, updatable=true, length=100)

   public String getAssignerdepid() {
       return this.assignerdepid;
   }
   
   public void setAssignerdepid(String assignerdepid) {
       this.assignerdepid = assignerdepid;
   }
   
   @Column(name="ASSIGNERDEP", unique=false, nullable=true, insertable=true, updatable=true, length=200)

   public String getAssignerdep() {
       return this.assignerdep;
   }
   
   public void setAssignerdep(String assignerdep) {
       this.assignerdep = assignerdep;
   }
   
   @Column(name="ASSIGNERCORPID", unique=false, nullable=true, insertable=true, updatable=true, length=100)

   public String getAssignercorpid() {
       return this.assignercorpid;
   }
   
   public void setAssignercorpid(String assignercorpid) {
       this.assignercorpid = assignercorpid;
   }
   
   @Column(name="ASSIGNERCORP", unique=false, nullable=true, insertable=true, updatable=true, length=200)

   public String getAssignercorp() {
       return this.assignercorp;
   }
   
   public void setAssignercorp(String assignercorp) {
       this.assignercorp = assignercorp;
   }
   
   @Column(name="ASSIGNERDNID", unique=false, nullable=true, insertable=true, updatable=true, length=100)

   public String getAssignerdnid() {
       return this.assignerdnid;
   }
   
   public void setAssignerdnid(String assignerdnid) {
       this.assignerdnid = assignerdnid;
   }
   
   @Column(name="ASSIGNERDN", unique=false, nullable=true, insertable=true, updatable=true, length=400)

   public String getAssignerdn() {
       return this.assignerdn;
   }
   
   public void setAssignerdn(String assignerdn) {
       this.assignerdn = assignerdn;
   }
   
   @Column(name="STDATE", unique=false, nullable=true, insertable=true, updatable=true, scale=0)

   public Long getStdate() {
       return this.stdate;
   }
   
   public void setStdate(Long stdate) {
       this.stdate = stdate;
   }
   
   @Column(name="BGDATE", unique=false, nullable=true, insertable=true, updatable=true, scale=0)

   public Long getBgdate() {
       return this.bgdate;
   }
   
   public void setBgdate(Long bgdate) {
       this.bgdate = bgdate;
   }
   
   @Column(name="EDDATE", unique=false, nullable=true, insertable=true, updatable=true, scale=0)

   public Long getEddate() {
       return this.eddate;
   }
   
   public void setEddate(Long eddate) {
       this.eddate = eddate;
   }
   
   @Column(name="ASSIGNOVERTIMEDATE", unique=false, nullable=true, insertable=true, updatable=true, scale=0)

   public Long getAssignovertimedate() {
       return this.assignovertimedate;
   }
   
   public void setAssignovertimedate(Long assignovertimedate) {
       this.assignovertimedate = assignovertimedate;
   }
   
   @Column(name="ACCEPTOVERTIMEDATE", unique=false, nullable=true, insertable=true, updatable=true, scale=0)

   public Long getAcceptovertimedate() {
       return this.acceptovertimedate;
   }
   
   public void setAcceptovertimedate(Long acceptovertimedate) {
       this.acceptovertimedate = acceptovertimedate;
   }
   
   @Column(name="DEALOVERTIMEDATE", unique=false, nullable=true, insertable=true, updatable=true, scale=0)

   public Long getDealovertimedate() {
       return this.dealovertimedate;
   }
   
   public void setDealovertimedate(Long dealovertimedate) {
       this.dealovertimedate = dealovertimedate;
   }
   
   @Column(name="FLAGASSIGN", unique=false, nullable=true, insertable=true, updatable=true, scale=0)

   public Long getFlagassign() {
       return this.flagassign;
   }
   
   public void setFlagassign(Long flagassign) {
       this.flagassign = flagassign;
   }
   
   @Column(name="FLAGASSIST", unique=false, nullable=true, insertable=true, updatable=true, scale=0)

   public Long getFlagassist() {
       return this.flagassist;
   }
   
   public void setFlagassist(Long flagassist) {
       this.flagassist = flagassist;
   }
   
   @Column(name="FLAGCOPY", unique=false, nullable=true, insertable=true, updatable=true, scale=0)

   public Long getFlagcopy() {
       return this.flagcopy;
   }
   
   public void setFlagcopy(Long flagcopy) {
       this.flagcopy = flagcopy;
   }
   
   @Column(name="FLAGTRANSFER", unique=false, nullable=true, insertable=true, updatable=true, scale=0)

   public Long getFlagtransfer() {
       return this.flagtransfer;
   }
   
   public void setFlagtransfer(Long flagtransfer) {
       this.flagtransfer = flagtransfer;
   }
   
   @Column(name="FLAGTURNUP", unique=false, nullable=true, insertable=true, updatable=true, scale=0)

   public Long getFlagturnup() {
       return this.flagturnup;
   }
   
   public void setFlagturnup(Long flagturnup) {
       this.flagturnup = flagturnup;
   }
   
   @Column(name="FLAGRECALL", unique=false, nullable=true, insertable=true, updatable=true, scale=0)

   public Long getFlagrecall() {
       return this.flagrecall;
   }
   
   public void setFlagrecall(Long flagrecall) {
       this.flagrecall = flagrecall;
   }
   
   @Column(name="FLAGCANCEL", unique=false, nullable=true, insertable=true, updatable=true, scale=0)

   public Long getFlagcancel() {
       return this.flagcancel;
   }
   
   public void setFlagcancel(Long flagcancel) {
       this.flagcancel = flagcancel;
   }
   
   @Column(name="FLAGCLOSE", unique=false, nullable=true, insertable=true, updatable=true, scale=0)

   public Long getFlagclose() {
       return this.flagclose;
   }
   
   public void setFlagclose(Long flagclose) {
       this.flagclose = flagclose;
   }
   
   @Column(name="FLAGTURNDOWN", unique=false, nullable=true, insertable=true, updatable=true, scale=0)

   public Long getFlagturndown() {
       return this.flagturndown;
   }
   
   public void setFlagturndown(Long flagturndown) {
       this.flagturndown = flagturndown;
   }
   
   @Column(name="FLAGTOASSISTAUDITING", unique=false, nullable=true, insertable=true, updatable=true, scale=0)

   public Long getFlagtoassistauditing() {
       return this.flagtoassistauditing;
   }
   
   public void setFlagtoassistauditing(Long flagtoassistauditing) {
       this.flagtoassistauditing = flagtoassistauditing;
   }
   
   @Column(name="FLAGTOAUDITING", unique=false, nullable=true, insertable=true, updatable=true, scale=0)

   public Long getFlagtoauditing() {
       return this.flagtoauditing;
   }
   
   public void setFlagtoauditing(Long flagtoauditing) {
       this.flagtoauditing = flagtoauditing;
   }
   
   @Column(name="FLAGSTARTINSIDEFLOW", unique=false, nullable=true, insertable=true, updatable=true, scale=0)

   public Long getFlagstartinsideflow() {
       return this.flagstartinsideflow;
   }
   
   public void setFlagstartinsideflow(Long flagstartinsideflow) {
       this.flagstartinsideflow = flagstartinsideflow;
   }
   
   @Column(name="FLAGAUDITINGRESULT", unique=false, nullable=true, insertable=true, updatable=true, scale=0)

   public Long getFlagauditingresult() {
       return this.flagauditingresult;
   }
   
   public void setFlagauditingresult(Long flagauditingresult) {
       this.flagauditingresult = flagauditingresult;
   }
   
   @Column(name="INSIDEFLOWSCOUNT", unique=false, nullable=true, insertable=true, updatable=true, scale=0)

   public Long getInsideflowscount() {
       return this.insideflowscount;
   }
   
   public void setInsideflowscount(Long insideflowscount) {
       this.insideflowscount = insideflowscount;
   }
   
   @Column(name="FINISHINSIDEFLOWSCOUNT", unique=false, nullable=true, insertable=true, updatable=true, scale=0)

   public Long getFinishinsideflowscount() {
       return this.finishinsideflowscount;
   }
   
   public void setFinishinsideflowscount(Long finishinsideflowscount) {
       this.finishinsideflowscount = finishinsideflowscount;
   }
   
   @Column(name="HASTENCOUNT", unique=false, nullable=true, insertable=true, updatable=true, scale=0)

   public Long getHastencount() {
       return this.hastencount;
   }
   
   public void setHastencount(Long hastencount) {
       this.hastencount = hastencount;
   }
   
   @Column(name="BASECREATETIME", unique=false, nullable=true, insertable=true, updatable=true, scale=0)

   public Long getBasecreatetime() {
       return this.basecreatetime;
   }
   
   public void setBasecreatetime(Long basecreatetime) {
       this.basecreatetime = basecreatetime;
   }
   
   @Column(name="ROLEID", unique=false, nullable=true, insertable=true, updatable=true)

   public String getRoleid() {
       return this.roleid;
   }
   
   public void setRoleid(String roleid) {
       this.roleid = roleid;
   }
   
   @Column(name="ROLENAME", unique=false, nullable=true, insertable=true, updatable=true)

   public String getRolename() {
       return this.rolename;
   }
   
   public void setRolename(String rolename) {
       this.rolename = rolename;
   }
   
   @Column(name="ACTIONNAME", unique=false, nullable=true, insertable=true, updatable=true)

   public String getActionname() {
       return this.actionname;
   }
   
   public void setActionname(String actionname) {
       this.actionname = actionname;
   }
   
   @Column(name="CUSTOMACTIONS", unique=false, nullable=true, insertable=true, updatable=true, length=400)

   public String getCustomactions() {
       return this.customactions;
   }
   
   public void setCustomactions(String customactions) {
       this.customactions = customactions;
   }
   
   @Column(name="DPDESC", unique=false, nullable=true, insertable=true, updatable=true, length=400)

   public String getDpdesc() {
       return this.dpdesc;
   }
   
   public void setDpdesc(String dpdesc) {
       this.dpdesc = dpdesc;
   }
   
   @Column(name="PREASSIGNSTRING", unique=false, nullable=true, insertable=true, updatable=true, length=400)

   public String getPreassignstring() {
       return this.preassignstring;
   }
   
   public void setPreassignstring(String preassignstring) {
       this.preassignstring = preassignstring;
   }
   
   @Column(name="ORIGPHASENO", unique=false, nullable=true, insertable=true, updatable=true)

   public String getOrigphaseno() {
       return this.origphaseno;
   }
   
   public void setOrigphaseno(String origphaseno) {
       this.origphaseno = origphaseno;
   }
}
