package com.ultrapower.eoms.ultrasm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * MenuInfo entity. @author MyEclipse Persistence Tools
 */

@Entity
@Table(name = "BS_T_SM_MENUTREE")
public class MenuInfo implements java.io.Serializable {

	// Fields    

	private String pid;			//ID
	private String nodename;	//节点名称
	private Long nodetype;		//节点类型
	private String parentid;	//父节点ID
	private String nodeurl;		//节点链接
	private Long status;		//启用状态(1.启用,0.停用)
	private String nodemark;	//节点标识(要求唯一)
	private String openway;		//打开方式(1.右侧打开,2.弹出打开)
	private String menuicon;	//节点图标
	private String nodepath;	//节点路径
	private String classname;	//实现类(值班管理模块需要)
	private String applysysname;//应用系统名，用于访问外界系统
	private Long ordernum;		//排序值
	private String creater;		//创建人
	private Long createtime;	//创建时间
	private String lastmodifier;//最后修改人
	private Long lastmodifytime;//最后修改时间
	private String remark;		//备注

	// Constructors

	/** default constructor */
	public MenuInfo() {
	}

	/** minimal constructor */
	public MenuInfo(String pid) {
		this.pid = pid;
	}

	/** full constructor */
	public MenuInfo(String pid, String nodename, Long nodetype,
			String parentid, String nodeurl, Long status, String nodemark,
			String openway, String menuicon, String nodepath, String classname, String applysysname, 
			Long ordernum, String creater, Long createtime, String lastmodifier,
			Long lastmodifytime, String remark) {
		this.pid = pid;
		this.nodename = nodename;
		this.nodetype = nodetype;
		this.parentid = parentid;
		this.nodeurl = nodeurl;
		this.status = status;
		this.nodemark = nodemark;
		this.openway = openway;
		this.menuicon = menuicon;
		this.nodepath = nodepath;
		this.classname = classname;
		this.applysysname = applysysname;
		this.ordernum = ordernum;
		this.creater = creater;
		this.createtime = createtime;
		this.lastmodifier = lastmodifier;
		this.lastmodifytime = lastmodifytime;
		this.remark = remark;
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

	@Column(name = "NODENAME", unique = false, nullable = true, insertable = true, updatable = true, length = 80)
	public String getNodename() {
		return this.nodename;
	}

	public void setNodename(String nodename) {
		this.nodename = nodename;
	}

	@Column(name = "NODETYPE", unique = false, nullable = true, insertable = true, updatable = true, precision = 2, scale = 0)
	public Long getNodetype() {
		return this.nodetype;
	}

	public void setNodetype(Long nodetype) {
		this.nodetype = nodetype;
	}

	@Column(name = "PARENTID", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getParentid() {
		return this.parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	@Column(name = "NODEURL", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
	public String getNodeurl() {
		return this.nodeurl;
	}

	public void setNodeurl(String nodeurl) {
		this.nodeurl = nodeurl;
	}

	@Column(name = "STATUS", unique = false, nullable = true, insertable = true, updatable = true, precision = 2, scale = 0)
	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Column(name = "NODEMARK", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getNodemark() {
		return this.nodemark;
	}

	public void setNodemark(String nodemark) {
		this.nodemark = nodemark;
	}

	@Column(name = "OPENWAY", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public String getOpenway() {
		return this.openway;
	}

	public void setOpenway(String openway) {
		this.openway = openway;
	}

	@Column(name = "MENUICON", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getMenuicon() {
		return this.menuicon;
	}

	public void setMenuicon(String menuicon) {
		this.menuicon = menuicon;
	}

	@Column(name = "NODEPATH", unique = false, nullable = true, insertable = true, updatable = true, length = 400)
	public String getNodepath() {
		return nodepath;
	}

	public void setNodepath(String nodepath) {
		this.nodepath = nodepath;
	}

	@Column(name = "CLASSNAME", unique = false, nullable = true, insertable = true, updatable = true, length = 80)
	public String getClassname() {
		return this.classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	@Column(name = "APPLYSYSNAME", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public String getApplysysname() {
		return applysysname;
	}

	public void setApplysysname(String applysysname) {
		this.applysysname = applysysname;
	}

	@Column(name = "ORDERNUM", unique = false, nullable = true, insertable = true, updatable = true, precision = 5, scale = 0)
	public Long getOrdernum() {
		if(ordernum == null)
			ordernum = (long) 1000;
		return this.ordernum;
	}

	public void setOrdernum(Long ordernum) {
		this.ordernum = ordernum;
	}

	@Column(name = "CREATER", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getCreater() {
		return this.creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	@Column(name = "CREATETIME", unique = false, nullable = true, insertable = true, updatable = true, precision = 15, scale = 0)
	public Long getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Long createtime) {
		this.createtime = createtime;
	}

	@Column(name = "LASTMODIFIER", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getLastmodifier() {
		return this.lastmodifier;
	}

	public void setLastmodifier(String lastmodifier) {
		this.lastmodifier = lastmodifier;
	}

	@Column(name = "LASTMODIFYTIME", unique = false, nullable = true, insertable = true, updatable = true, precision = 15, scale = 0)
	public Long getLastmodifytime() {
		return this.lastmodifytime;
	}

	public void setLastmodifytime(Long lastmodifytime) {
		this.lastmodifytime = lastmodifytime;
	}

	@Column(name = "REMARK", unique = false, nullable = true, insertable = true, updatable = true, length = 500)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}