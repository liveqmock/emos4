package com.ultrapower.eoms.msextend.change.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ultrapower.eoms.ultrasm.model.UserInfo;

/**
 * 配置项关联表
 * @author hhy
 *
 */
@Entity
@Table(name="bs_t_wf_ci_rel")
public class CIRelevance{
	private String id;
	private String name;
	private String displayLabel;
	private CIClass ciClass;
	private String baseschema;
	private String baseid;
	private String ciId;
	private UserInfo creator;
	private String createtime;
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "assigned")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBaseschema() {
		return baseschema;
	}
	public void setBaseschema(String baseschema) {
		this.baseschema = baseschema;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCiId() {
		return ciId;
	}
	public void setCiId(String ciId) {
		this.ciId = ciId;
	}
	public String getDisplayLabel() {
		return displayLabel;
	}
	public void setDisplayLabel(String displayLabel) {
		this.displayLabel = displayLabel;
	}
	@ManyToOne
	@JoinColumn(name="classtype")
	@NotFound(action=NotFoundAction.IGNORE)
	public CIClass getCiClass() {
		return ciClass;
	}
	public void setCiClass(CIClass ciClass) {
		this.ciClass = ciClass;
	}
	public String getBaseid() {
		return baseid;
	}
	public void setBaseid(String baseid) {
		this.baseid = baseid;
	}
	@ManyToOne
	@JoinColumn(name="creator")
	public UserInfo getCreator() {
		return creator;
	}
	public void setCreator(UserInfo creator) {
		this.creator = creator;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	
}
