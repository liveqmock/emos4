
package com.ultrapower.eoms.common.sheettag.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * 工单类型信息
 * 
 * @author <a href="mailto:liuzhuo@ultrapower.com.cn">liuzhuo</a>
 * 
 * @version 
 * 
 * @since 2013-1-14
 */
@Entity
@Table(name = "BS_T_SHEET_TYPE")
public class SheetType  {

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
	
	/** baseschema */
	@Column(name = "baseschema",length=50)
	private String baseschema;
	
	/**请求url  */
	@Column(name = "url",length=500)
	private String url;

	/**排序 */
	@Column(name = "taxis",length=20)
	private String taxis;
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
    
	/**baseschema 0 手动输入 1 非手动输入  */
	@Column(name = "bstype")
	private String bstype;
	
	/**0 普通工单1 服务请求类工单  */
	@Column(name = "sheettype")
	private String sheettype;

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
	 * @return the baseschema
	 */
	public String getBaseschema() {
		return baseschema;
	}

	/**
	 * @param baseschema the baseschema to set
	 */
	public void setBaseschema(String baseschema) {
		this.baseschema = baseschema;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getBstype() {
		return bstype;
	}

	public void setBstype(String bstype) {
		this.bstype = bstype;
	}

	public String getSheettype() {
		return sheettype;
	}

	public void setSheettype(String sheettype) {
		this.sheettype = sheettype;
	}
	
	public String getTaxis() {
		return taxis;
	}

	public void setTaxis(String taxis) {
		this.taxis = taxis;
	}

}
