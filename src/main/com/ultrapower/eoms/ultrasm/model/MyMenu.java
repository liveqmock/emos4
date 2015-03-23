package com.ultrapower.eoms.ultrasm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * 我的菜单实体
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version Jul 14, 2010 2:14:55 PM
 */
@Entity
@Table(name = "BS_T_SM_MYMENU")
public class MyMenu implements java.io.Serializable{

	// Fields   
	
	private String pid;			//ID	
	private String userid;		//用户pid
	private String menuid;      //菜单节点pid
	private String nodename;	//节点名称
	private String parentid;	//父节点ID
	private String nodetype;    //节点类型 
	private String nodeurl;		//节点链接
	private Long status;		//启用状态(1.启用,0.停用)
	private String nodemark;	//节点标识(要求唯一)
	private Long ordernum;		//排序值
	private String creater;		//创建人
	private Long createtime;	//创建时间
	private String lastmodifier;//最后修改人
	private Long lastmodifytime;//最后修改时间
	private String remark;		//备注
	
	// Constructors
	
	/** default constructor */
	public MyMenu(){
	}
	
	public MyMenu(String pid){
		this.pid = pid;
	}
	
	/** full constructor */
	public MyMenu(String pid, String userid,String menuid,String nodename,
			String parentid,String nodetype, String nodeurl, Long status, String nodemark,
			Long ordernum,String creater, Long createtime, String lastmodifier,
			Long lastmodifytime, String remark) {
		this.pid = pid;
		this.userid = userid;
		this.menuid = menuid;
		this.nodename = nodename;
		this.parentid = parentid;
		this.nodetype = nodetype;
		this.nodeurl = nodeurl;
		this.status = status;
		this.nodemark = nodemark;
		this.ordernum = ordernum;
		this.creater = creater;
		this.createtime = createtime;
		this.lastmodifier = lastmodifier;
		this.lastmodifytime = lastmodifytime;
		this.remark = remark;
	}
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "PID", unique = true, nullable = false, insertable = true, updatable = true, length = 50)
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	
	@Column(name = "USERID", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	@Column(name = "MENUID", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getMenuid() {
		return menuid;
	}
	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}
	
	@Column(name = "NODENAME", unique = false, nullable = true, insertable = true, updatable = true, length = 80)
	public String getNodename() {
		return nodename;
	}
	public void setNodename(String nodename) {
		this.nodename = nodename;
	}
	
	@Column(name = "PARENTID", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	
	@Column(name = "NODETYPE", unique = false, nullable = true, insertable = true, updatable = true, precision = 2, scale = 0)
	public String getNodetype() {
		return nodetype;
	}

	public void setNodetype(String nodetype) {
		this.nodetype = nodetype;
	}

	@Column(name = "NODEURL", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
	public String getNodeurl() {
		return nodeurl;
	}
	public void setNodeurl(String nodeurl) {
		this.nodeurl = nodeurl;
	}
	
	@Column(name = "STATUS", unique = false, nullable = true, insertable = true, updatable = true, precision = 2, scale = 0)
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	
	@Column(name = "NODEMARK", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getNodemark() {
		return nodemark;
	}
	public void setNodemark(String nodemark) {
		this.nodemark = nodemark;
	}
	
	@Column(name = "ORDERNUM", unique = false, nullable = true, insertable = true, updatable = true, precision = 5, scale = 0)
	public Long getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(Long ordernum) {
		this.ordernum = ordernum;
	}
	
	@Column(name = "CREATER", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	
	@Column(name = "CREATETIME", unique = false, nullable = true, insertable = true, updatable = true, precision = 15, scale = 0)
	public Long getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Long createtime) {
		this.createtime = createtime;
	}
	
	@Column(name = "LASTMODIFIER", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getLastmodifier() {
		return lastmodifier;
	}
	public void setLastmodifier(String lastmodifier) {
		this.lastmodifier = lastmodifier;
	}
	
	@Column(name = "LASTMODIFYTIME", unique = false, nullable = true, insertable = true, updatable = true, precision = 15, scale = 0)
	public Long getLastmodifytime() {
		return lastmodifytime;
	}
	public void setLastmodifytime(Long lastmodifytime) {
		this.lastmodifytime = lastmodifytime;
	}
	
	@Column(name = "REMARK", unique = false, nullable = true, insertable = true, updatable = true, length = 500)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
