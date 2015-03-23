
package com.ultrapower.eoms.common.sheettag.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ultrapower.eoms.common.sheettag.model.pk.TagTypePK;


/**
 * 首页工单标签和工单类型关联表实体类
 * 
 * @author <a href="mailto:liuzhuo@ultrapower.com.cn">liuzhuo</a>
 * 
 * @version 
 * 
 * @since 2013-1-14
 */
@Entity
@Table(name = "BS_T_SHEET_TAG_TYPE_LINK")
public class TagTypeLink  {
	/**
	 * 关联表联合主键
	 */
	@EmbeddedId
	private TagTypePK id;

	/**
	 * @return the id
	 */
	public TagTypePK getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(TagTypePK id) {
		this.id = id;
	}
	
	

}
