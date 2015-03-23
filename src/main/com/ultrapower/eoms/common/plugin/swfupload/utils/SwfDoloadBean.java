package com.ultrapower.eoms.common.plugin.swfupload.utils;

public class SwfDoloadBean {
	/**
	 * 附件名称
	 */
	private String fileName;
	/**
	 * 附件真实名称（也即附件存储的名称）
	 */
	private String saveName;
	
	/**
	 * 附件存储的相对路径
	 */
	private String savePath;
	
	/**
	 * 自定义下载路径，将文件下载到该路径下
	 */
	private String customDownPath;
	
	/**
	 * 自定义下载文件名，将文件重命名为该名称
	 */
	private String customDownName;
	
	public SwfDoloadBean(){
		//
	}
	
	public SwfDoloadBean(String fileName,String saveName,String savePath){
		this.fileName = fileName;
		this.saveName = saveName;
		this.savePath = savePath;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSaveName() {
		return saveName;
	}

	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public String getCustomDownPath() {
		return customDownPath;
	}

	public void setCustomDownPath(String customDownPath) {
		this.customDownPath = customDownPath;
	}

	public String getCustomDownName() {
		return customDownName;
	}

	public void setCustomDownName(String customDownName) {
		this.customDownName = customDownName;
	}
	
}
