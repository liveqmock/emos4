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
public class CustomModel {
	private String modelkey;
	private String modelname;
	private String imageurl;
	private String modeltype;
	private String modeldesc;
	private List<ModelMeta> metaList;
	private List<FunctionModel> prefunction;
	private List<FunctionModel> postfunction;
	private List<FunctionModel> interfunction;
	public String getModelkey() {
		return modelkey;
	}
	public void setModelkey(String modelkey) {
		this.modelkey = modelkey;
	}
	public String getModelname() {
		return modelname;
	}
	public void setModelname(String modelname) {
		this.modelname = modelname;
	}
	public String getImageurl() {
		return imageurl;
	}
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	public String getModeltype() {
		return modeltype;
	}
	public void setModeltype(String modeltype) {
		this.modeltype = modeltype;
	}
	public String getModeldesc() {
		return modeldesc;
	}
	public void setModeldesc(String modeldesc) {
		this.modeldesc = modeldesc;
	}
	public List<ModelMeta> getMetaList() {
		return metaList;
	}
	public void setMetaList(List<ModelMeta> metaList) {
		this.metaList = metaList;
	}
	public List<FunctionModel> getPrefunction() {
		return prefunction;
	}
	public void setPrefunction(List<FunctionModel> prefunction) {
		this.prefunction = prefunction;
	}
	public List<FunctionModel> getPostfunction() {
		return postfunction;
	}
	public void setPostfunction(List<FunctionModel> postfunction) {
		this.postfunction = postfunction;
	}
	public List<FunctionModel> getInterfunction() {
		return interfunction;
	}
	public void setInterfunction(List<FunctionModel> interfunction) {
		this.interfunction = interfunction;
	}

	
	
}
