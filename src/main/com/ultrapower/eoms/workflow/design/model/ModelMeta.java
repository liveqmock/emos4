package com.ultrapower.eoms.workflow.design.model;

import java.util.List;

/**
 * 
 * Copyright (c) 2010 神州泰岳服务管理事业部通用产品
 * All rights reserved.
 * 描述：
 * @author 范莹
 * @date   2010-4-21
 */ 
public class ModelMeta {

	private String metakey;
	private String metaname;
	private String metatype;
	private String metadict;
	private String mandatory;
	private String metadefault;
	public String getMetakey() {
		return metakey;
	}
	public void setMetakey(String metakey) {
		this.metakey = metakey;
	}
	public String getMetaname() {
		return metaname;
	}
	public void setMetaname(String metaname) {
		this.metaname = metaname;
	}
	public String getMetatype() {
		return metatype;
	}
	public void setMetatype(String metatype) {
		this.metatype = metatype;
	}
	public String getMetadict() {
		return metadict;
	}
	public void setMetadict(String metadict) {
		this.metadict = metadict;
	}
	public String getMandatory() {
		return mandatory;
	}
	public void setMandatory(String mandatory) {
		this.mandatory = mandatory;
	}
	public String getMetadefault() {
		return metadefault;
	}
	public void setMetadefault(String metadefault) {
		this.metadefault = metadefault;
	}
}
