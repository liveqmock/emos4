package com.ultrapower.eoms.common.sheettag.model.pk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 *  首页工单标签和工单类型关联表联合主键对象
 * 
 * @author <a href="mailto:liuzhuo@ultrapower.com.cn">liuzhuo</a>
 * 
 * @version
 * 
 * @since Apr 18, 2012
 */
@Embeddable
public class TagTypePK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *  sheet tag id
	 */
	@Column(name = "sheettagid",length=50)
	private String sheettagid;
	/**
	 * sheet type  id
	 */
	@Column(name = "sheettypeid",length=50)
	private String sheettypeid;
	/**
	 * @return the sheettagid
	 */
	public String getSheettagid() {
		return sheettagid;
	}
	/**
	 * @param sheettagid the sheettagid to set
	 */
	public void setSheettagid(String sheettagid) {
		this.sheettagid = sheettagid;
	}
	/**
	 * @return the sheettypeid
	 */
	public String getSheettypeid() {
		return sheettypeid;
	}
	/**
	 * @param sheettypeid the sheettypeid to set
	 */
	public void setSheettypeid(String sheettypeid) {
		this.sheettypeid = sheettypeid;
	}
	
	public TagTypePK(String sheettagid, String sheettypeid) {
		this.sheettagid = sheettagid;
		this.sheettypeid = sheettypeid;
	}
	
	public TagTypePK() {
		super();
	}
	
	

}
