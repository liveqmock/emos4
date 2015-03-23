
package com.ultrapower.eoms.common.sheettag.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 *首页工单标签
 * 
 * @author <a href="mailto:liuzhuo@ultrapower.com.cn">liuzhuo</a>
 * 
 * @version 
 * 
 * @since 2013-1-14
 */
@Entity
@Table(name = "BS_T_SHEET_TAG")
public class SheetTag  {

	/**
	 * 主键id
	 */
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "id",length=50)
	private String id;
	/** 名称 */
	@Column(name = "name",length=50)
	private String name;
	
	/**级别  */
	@Column(name = "taglevel")
	private int level;
	
	/**父id  */
	@Column(name = "parentid")
	private String parentid;

	
	/**排序 */
	@Column(name = "taxis",length=20)
	private String taxis;
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getTaxis() {
		return taxis;
	}

	public void setTaxis(String taxis) {
		this.taxis = taxis;
	}
	
	
}
