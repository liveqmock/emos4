package com.ultrapower.eoms.ftrmaintain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * IndexCategory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "BS_T_FTR_INDEXCATEGORY")
public class IndexCategory implements java.io.Serializable {

	// Fields    

	private String pid;
	private String displayname;
	private String parentid;
	private Long isaddhlttext;
	private String hlttextpath;
	private Long isphysical;
	private String attachinfoclass; //附件信息实现类
	private String relationattclass; //附件内容实现类
	private String filesourcecreater; //文件实体实现类
	private Long ordernum;
	private String creater;
	private Long createtime;
	private String lastmodifier;
	private Long lastmodifytime;

	// Constructors

	/** default constructor */
	public IndexCategory() {
	}

	/** minimal constructor */
	public IndexCategory(String pid) {
		this.pid = pid;
	}

	/** full constructor */
	public IndexCategory(String pid, String displayname, String parentid,
			Long isaddhlttext, String creater, Long createtime,
			String lastmodifier, Long lastmodifytime, String hlttextpath,
			Long isphysical, Long ordernum, String filesourcecreater) {
		this.pid = pid;
		this.displayname = displayname;
		this.parentid = parentid;
		this.isaddhlttext = isaddhlttext;
		this.creater = creater;
		this.createtime = createtime;
		this.lastmodifier = lastmodifier;
		this.lastmodifytime = lastmodifytime;
		this.hlttextpath = hlttextpath;
		this.isphysical = isphysical;
		this.ordernum = ordernum;
		this.filesourcecreater = filesourcecreater;
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

	@Column(name = "DISPLAYNAME", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getDisplayname() {
		return this.displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	@Column(name = "PARENTID", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getParentid() {
		return this.parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	@Column(name = "ISADDHLTTEXT", unique = false, nullable = true, insertable = true, updatable = true, precision = 2, scale = 0)
	public Long getIsaddhlttext() {
		return this.isaddhlttext;
	}

	public void setIsaddhlttext(Long isaddhlttext) {
		this.isaddhlttext = isaddhlttext;
	}

	@Column(name = "HLTTEXTPATH", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
	public String getHlttextpath() {
		return this.hlttextpath;
	}

	public void setHlttextpath(String hlttextpath) {
		this.hlttextpath = hlttextpath;
	}

	@Column(name = "ISPHYSICAL", unique = false, nullable = true, insertable = true, updatable = true, precision = 2, scale = 0)
	public Long getIsphysical() {
		return this.isphysical;
	}

	public void setIsphysical(Long isphysical) {
		this.isphysical = isphysical;
	}

	@Column(name = "ATTACHINFOCLASS", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
	
	public String getAttachinfoclass() {
		return attachinfoclass;
	}

	public void setAttachinfoclass(String attachinfoclass) {
		this.attachinfoclass = attachinfoclass;
	}

	@Column(name = "RELATIONATTCLASS", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
	public String getRelationattclass() {
		return relationattclass;
	}

	public void setRelationattclass(String relationattclass) {
		this.relationattclass = relationattclass;
	}

	@Column(name = "FILESOURCECREATER", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
	public String getFilesourcecreater() {
		return filesourcecreater;
	}

	public void setFilesourcecreater(String filesourcecreater) {
		this.filesourcecreater = filesourcecreater;
	}
	
	@Column(name = "ORDERNUM", unique = false, nullable = true, insertable = true, updatable = true, precision = 5, scale = 0)
	public Long getOrdernum() {
		return this.ordernum;
	}

	public void setOrdernum(Long ordernum) {
		this.ordernum = ordernum;
	}
	
	@Column(name = "CREATER", unique = false, nullable = true, insertable = true, updatable = true, length = 60)
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

	@Column(name = "LASTMODIFIER", unique = false, nullable = true, insertable = true, updatable = true, length = 60)
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