package com.ultrapower.eoms.msextend.serverQuest.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ultrapower.eoms.ultrasm.model.DepInfo;

@Entity
@Table(name="BS_T_WF_SERVERQUEST")
public class ServerQuestModel  implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String  pid;
	private String  serverquestname ;
	private String  serverquestvalue;
	private String  serverquestdn;
	private String  serverquestdns ;
	private String  serverquestfullname;
	private String  status ;
	private String  isCommon;//是否常用
	private Integer  ordernum;
	private String  parentid;
	private ServerQuestModel parent;//上级服务目录 
	private String  creater;
	private String  createtime ;
	private String  remark ;
	private Integer  dilever ;
	private String isSendAudit;
	
	private String  vipflog ;//是否有VIP服务

	private String  dealGroupName;//处理组名(可能是人)
	private String  dealGroupId;//处理组id(可能是人)
	private String  hy;//行员
	private String  wb;//外包
	private String  qt;//其他
	private String  worktime;//工作日响应时间
	private String  noworktime;//非工作日响应时间
	private String  workbegintime;//工作开始时间
	private String  workendtime;//工作结束时间
	private String  dealtimelimit;//解决时间上限
	private String  dealtimelimit2;//解决时间上限(更换硬件)
	
	private String  audit_0_id;//服务处理责任处室
	private String  audit_0_name;//服务处理责任处室
	private String 	audit_1_id;
	private String 	audit_1_name ;//承办处管理负责人(行员）
	private String  audit_2_id;//承办处处长id
	private String  audit_2_name;//承办处处长
	private String 	audit_3_id;//运行中心副主任id
	private String 	audit_3_name;//运行中心副主任
	private String  audit_4_id;//运行中心主管副局长id
	private String  audit_4_name;//运行中心主管副局长
	private String  audit_id;	//汇总审批人id
	private String  audit_name; //汇总名称
	
	private DepInfo  responsibleDep;//负责部室
	private String  serviceInfo;//服务说明
	
	private String attamentid; //附件id
	private String forWho;//对内，对外标识
	private String releaseScopeText;//发布范围中文名
	private String releaseScopeId;//发布范围id

	public ServerQuestModel() {
			super();
		}
		
	public ServerQuestModel(String pid, String serverquestname,
			String serverquestvalue, String serverquestdn,
			String serverquestdns, String serverquestfullname, String status,
			Integer ordernum, String parentid, String creater,
			String createtime, String remark, Integer dilever, String vipflog,
			String hy, String wb, String qt, String worktime,
			String noworktime, String dealtimelimit, String audit_0_id,
			String audit_0_name, String audit_1_id, String audit_1_name,
			String audit_2_id, String audit_2_name, String audit_3_id,
			String audit_3_name, String audit_4_id, String audit_4_name,
			String audit_id, String audit_name, String attamentid) {
		super();
		this.pid = pid;
		this.serverquestname = serverquestname;
		this.serverquestvalue = serverquestvalue;
		this.serverquestdn = serverquestdn;
		this.serverquestdns = serverquestdns;
		this.serverquestfullname = serverquestfullname;
		this.status = status;
		this.ordernum = ordernum;
		this.parentid = parentid;
		this.creater = creater;
		this.createtime = createtime;
		this.remark = remark;
		this.dilever = dilever;
		this.vipflog = vipflog;
		this.hy = hy;
		this.wb = wb;
		this.qt = qt;
		this.worktime = worktime;
		this.noworktime = noworktime;
		this.dealtimelimit = dealtimelimit;
		this.audit_0_id = audit_0_id;
		this.audit_0_name = audit_0_name;
		this.audit_1_id = audit_1_id;
		this.audit_1_name = audit_1_name;
		this.audit_2_id = audit_2_id;
		this.audit_2_name = audit_2_name;
		this.audit_3_id = audit_3_id;
		this.audit_3_name = audit_3_name;
		this.audit_4_id = audit_4_id;
		this.audit_4_name = audit_4_name;
		this.audit_id = audit_id;
		this.audit_name = audit_name;
		this.attamentid = attamentid;
	}









	@Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name="pid", unique=true, nullable=false, insertable=true, updatable=true)

	public String getPid() {
		return pid;
	}


	public void setPid(String pid) {
		this.pid = pid;
	}

	 @Column(name="remark", unique=false, nullable=true, insertable=true, updatable=true)  

	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	 @Column(name="serverquestname", unique=false, nullable=true, insertable=true, updatable=true)  
	public String getServerquestname() {
		return serverquestname;
	}


	public void setServerquestname(String serverquestname) {
		this.serverquestname = serverquestname;
	}

	 @Column(name="serverquestvalue", unique=false, nullable=true, insertable=true, updatable=true)  
	public String getServerquestvalue() {
		return serverquestvalue;
	}


	public void setServerquestvalue(String serverquestvalue) {
		this.serverquestvalue = serverquestvalue;
	}

	 @Column(name="serverquestdn", unique=false, nullable=true, insertable=true, updatable=true)  
	public String getServerquestdn() {
		return serverquestdn;
	}


	public void setServerquestdn(String serverquestdn) {
		this.serverquestdn = serverquestdn;
	}
	 @Column(name="serverquestdns", unique=false, nullable=true, insertable=true, updatable=true)  

	public String getServerquestdns() {
		return serverquestdns;
	}


	public void setServerquestdns(String serverquestdns) {
		this.serverquestdns = serverquestdns;
	}

	 @Column(name="serverquestfullname", unique=false, nullable=true, insertable=true, updatable=true)  
	public String getServerquestfullname() {
		return serverquestfullname;
	}


	public void setServerquestfullname(String serverquestfullname) {
		this.serverquestfullname = serverquestfullname;
	}

	 @Column(name="status", unique=false, nullable=true, insertable=true, updatable=true)  
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}
	 @Column(name="ordernum", unique=false, nullable=true, insertable=true, updatable=true)  

	public Integer getOrdernum() {
		return ordernum;
	}


	public void setOrdernum(Integer ordernum) {
		this.ordernum = ordernum;
	}

	 @Column(name="parentid", unique=false, nullable=true, insertable=true, updatable=true)  
	public String getParentid() {
		return parentid;
	}


	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	 @Column(name="creater", unique=false, nullable=true, insertable=true, updatable=true)  
	public String getCreater() {
		return creater;
	}


	public void setCreater(String creater) {
		this.creater = creater;
	}

	 @Column(name="createtime", unique=false, nullable=true, insertable=true, updatable=true)  
	public String getCreatetime() {
		return createtime;
	}


	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	 @Column(name="dilever", unique=false, nullable=true, insertable=true, updatable=true)  
	public Integer getDilever() {
		return dilever;
	}


	public void setDilever(Integer dilever) {
		this.dilever = dilever;
	}

	 @Column(name="vipflog", unique=false, nullable=true, insertable=true, updatable=true)  
	public String getVipflog() {
		return vipflog;
	}


	public void setVipflog(String vipflog) {
		this.vipflog = vipflog;
	}

	 @Column(name="audit_0_id", unique=false, nullable=true, insertable=true, updatable=true)  

	public String getAudit_0_id() {
		return audit_0_id;
	}
	 
	public void setAudit_0_id(String audit_0_id) {
		this.audit_0_id = audit_0_id;
	}

	 @Column(name="hy", unique=false, nullable=true, insertable=true, updatable=true)  

	public String getHy() {
		return hy;
	}


	public void setHy(String hy) {
		this.hy = hy;
	}

	 @Column(name="wb", unique=false, nullable=true, insertable=true, updatable=true)  

	public String getWb() {
		return wb;
	}


	public void setWb(String wb) {
		this.wb = wb;
	}

	 @Column(name="qt", unique=false, nullable=true, insertable=true, updatable=true)  

	public String getQt() {
		return qt;
	}


	public void setQt(String qt) {
		this.qt = qt;
	}

	 @Column(name="worktime", unique=false, nullable=true, insertable=true, updatable=true)  

	public String getWorktime() {
		return worktime;
	}


	public void setWorktime(String worktime) {
		this.worktime = worktime;
	}

	 @Column(name="noworktime", unique=false, nullable=true, insertable=true, updatable=true)  

	public String getNoworktime() {
		return noworktime;
	}


	public void setNoworktime(String noworktime) {
		this.noworktime = noworktime;
	}

	 @Column(name="dealtimelimit", unique=false, nullable=true, insertable=true, updatable=true)  

	public String getDealtimelimit() {
		return dealtimelimit;
	}


	public void setDealtimelimit(String dealtimelimit) {
		this.dealtimelimit = dealtimelimit;
	}

	 @Column(name="audit_1_name", unique=false, nullable=true, insertable=true, updatable=true)  

	public String getAudit_1_name() {
		return audit_1_name;
	}


	public void setAudit_1_name(String audit_1_name) {
		this.audit_1_name = audit_1_name;
	}

	 @Column(name="audit_2_name", unique=false, nullable=true, insertable=true, updatable=true)  

	public String getAudit_2_name() {
		return audit_2_name;
	}


	public void setAudit_2_name(String audit_2_name) {
		this.audit_2_name = audit_2_name;
	}

	 @Column(name="audit_3_name", unique=false, nullable=true, insertable=true, updatable=true)  

	public String getAudit_3_name() {
		return audit_3_name;
	}


	public void setAudit_3_name(String audit_3_name) {
		this.audit_3_name = audit_3_name;
	}

	 @Column(name="audit_4_name", unique=false, nullable=true, insertable=true, updatable=true)  

	public String getAudit_4_name() {
		return audit_4_name;
	}


	public void setAudit_4_name(String audit_4_name) {
		this.audit_4_name = audit_4_name;
	}
	 @Column(name="audit_1_id", unique=false, nullable=true, insertable=true, updatable=true)  

	public String getAudit_1_id() {
		return audit_1_id;
	}

	public void setAudit_1_id(String audit_1_id) {
		this.audit_1_id = audit_1_id;
	}




	 @Column(name="audit_2_id", unique=false, nullable=true, insertable=true, updatable=true)  

	public String getAudit_2_id() {
		return audit_2_id;
	}





	public void setAudit_2_id(String audit_2_id) {
		this.audit_2_id = audit_2_id;
	}



	 @Column(name="audit_3_id", unique=false, nullable=true, insertable=true, updatable=true)  


	public String getAudit_3_id() {
		return audit_3_id;
	}





	public void setAudit_3_id(String audit_3_id) {
		this.audit_3_id = audit_3_id;
	}



	 @Column(name="audit_4_id", unique=false, nullable=true, insertable=true, updatable=true)  


	public String getAudit_4_id() {
		return audit_4_id;
	}





	public void setAudit_4_id(String audit_4_id) {
		this.audit_4_id = audit_4_id;
	}
	 @Column(name="audit_0_name", unique=false, nullable=true, insertable=true, updatable=true)  

	public String getAudit_0_name() {
		return audit_0_name;
	}

	public void setAudit_0_name(String audit_0_name) {
		this.audit_0_name = audit_0_name;
	}
	 @Column(name="attamentid", unique=false, nullable=true, insertable=true, updatable=true)  

	public String getAttamentid() {
		return attamentid;
	}

	public void setAttamentid(String attamentid) {
		this.attamentid = attamentid;
	}
	 @Column(name="audit_id", unique=false, nullable=true, insertable=true, updatable=true)  

	public String getAudit_id() {
		return audit_id;
	}

	public void setAudit_id(String audit_id) {
		this.audit_id = audit_id;
	}
	 @Column(name="audit_name", unique=false, nullable=true, insertable=true, updatable=true)  

	public String getAudit_name() {
		return audit_name;
	}

	public void setAudit_name(String audit_name) {
		this.audit_name = audit_name;
	}

	@Column(name="iscommon")
	public String getIsCommon() {
		return isCommon;
	}

	public void setIsCommon(String isCommon) {
		this.isCommon = isCommon;
	}
	
	@Column(name = "service_info")
	public String getServiceInfo() {
		return serviceInfo;
	}

	public void setServiceInfo(String serviceInfo) {
		this.serviceInfo = serviceInfo;
	}
	@ManyToOne
	@JoinColumn(name = "responsible_dep")
	public DepInfo getResponsibleDep() {
		return responsibleDep;
	}

	public void setResponsibleDep(DepInfo responsibleDep) {
		this.responsibleDep = responsibleDep;
	}

	@Column(name="deal_group_name")
	public String getDealGroupName() {
		return dealGroupName;
	}

	public void setDealGroupName(String dealGroupName) {
		this.dealGroupName = dealGroupName;
	}

	@Column(name="deal_group_id")
	public String getDealGroupId() {
		return dealGroupId;
	}

	public void setDealGroupId(String dealGroupId) {
		this.dealGroupId = dealGroupId;
	}

	@JoinColumn(name="parentid",insertable=false,updatable=false)
	@ManyToOne
	@NotFound(action=NotFoundAction.IGNORE)
	public ServerQuestModel getParent() {
		return parent;
	}

	public void setParent(ServerQuestModel parent) {
		this.parent = parent;
	}

	@Column(name="forwho")
	public String getForWho() {
		return forWho;
	}

	public void setForWho(String forWho) {
		this.forWho = forWho;
	}

	@Column(name="release_scope_text")
	public String getReleaseScopeText() {
		return releaseScopeText;
	}

	public void setReleaseScopeText(String releaseScopeText) {
		this.releaseScopeText = releaseScopeText;
	}

	@Column(name="release_scope_id")
	public String getReleaseScopeId() {
		return releaseScopeId;
	}

	public void setReleaseScopeId(String releaseScopeId) {
		this.releaseScopeId = releaseScopeId;
	}
	@Column(name="dealtimelimit2")
	public String getDealtimelimit2() {
		return dealtimelimit2;
	}

	public void setDealtimelimit2(String dealtimelimit2) {
		this.dealtimelimit2 = dealtimelimit2;
	}

	@Column(name="workbegintime")
	public String getWorkbegintime() {
		return workbegintime;
	}

	public void setWorkbegintime(String workbegintime) {
		this.workbegintime = workbegintime;
	}
	@Column(name="workendtime")
	public String getWorkendtime() {
		return workendtime;
	}

	public void setWorkendtime(String workendtime) {
		this.workendtime = workendtime;
	}

	public String getIsSendAudit() {
		return isSendAudit;
	}

	public void setIsSendAudit(String isSendAudit) {
		this.isSendAudit = isSendAudit;
	}
	
	
}