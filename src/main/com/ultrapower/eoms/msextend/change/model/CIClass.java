package com.ultrapower.eoms.msextend.change.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Table(name="bs_t_wf_ciclass")
@Entity
public class CIClass implements Serializable{
	private static final long serialVersionUID = -4680518469782151332L;
	private String name;
	private CIClass parent;
	private String displayName;
	private List<CIClass> children;
	@Id
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@ManyToOne
	@JoinColumn(name="parent")
	@NotFound(action=NotFoundAction.IGNORE)
	public CIClass getParent() {
		return parent;
	}
	public void setParent(CIClass parent) {
		this.parent = parent;
	}
	@Column
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	@OneToMany(mappedBy="parent",fetch=FetchType.EAGER)
	public List<CIClass> getChildren() {
		return children;
	}
	public void setChildren(List<CIClass> children) {
		this.children = children;
	}
	
}
