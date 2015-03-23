package com.ultrapower.eoms.ultrasm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 人员菜单树关系实体
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version Jul 12, 2010 10:40:57 AM
 */

@Entity
@Table(name = "BS_T_SM_USERMENUTREE")
public class UserMenuTree implements java.io.Serializable {

	// Fields 
	private String pid;
	private String userid;
	private String menuid;
	private String creater;
	private Long createtime;
	private String lastmodifier;
	private Long lastmodifytime;
	
	/** default constructor */
	public UserMenuTree() {
	}
	
	/** minimal constructor */
	public UserMenuTree(String pid) {
		this.pid = pid;
	}
	
	/** full constructor */
	public UserMenuTree(String pid, String userid, String menuid, String creater,
			Long createtime, String lastmodifier, Long lastmodifytime) {
		this.pid = pid;
		this.userid = userid;
		this.menuid = menuid;
		this.creater = creater;
		this.createtime = createtime;
		this.lastmodifier = lastmodifier;
		this.lastmodifytime = lastmodifytime;
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

	@Column(name = "USERID", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@Column(name = "MENUID", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getMenuid() {
		return this.menuid;
	}

	public void setMenuid(String menuid) {
		this.menuid = menuid;
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
}
