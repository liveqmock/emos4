package com.ultrapower.eoms.workflow.api.bean;

public class BaseAttachmentInfo {
	
	private String path;
	private String name;
	private String attachFieldId;
	
	public BaseAttachmentInfo() {
		
	}
	
	public BaseAttachmentInfo(String path, String name) {
		this.path = path;
		this.name = name;
	}
	
	public BaseAttachmentInfo(String path, String name, String attachFieldId) {
		this.path = path;
		this.name = name;
		this.attachFieldId = attachFieldId;
	}


	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getAttachFieldId() {
		return attachFieldId;
	}

	public void setAttachFieldId(String attachFieldId) {
		this.attachFieldId = attachFieldId;
	}
}
