package com.ultrapower.eoms.common.core.component.tree.model;

import java.util.HashMap;

/**
 * 完整树形结构树节点对象
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version May 21, 2010 5:25:11 PM
 */
public class MenuDtree {

	/**
	 * 表示在整颗树中该节点的层级级别
	 */
	private int level = 0;
	
	/**
	 * 节点ID
	 */
	private String id = "";
	
	/**
	 * 树节点的显示数据
	 */
	private String text = "";
	
	
	private String mark = "";
	
	/**
	 * 该条记录的父Id
	 */
	private String parentid = "";
	
	private String child = "";
	
	/**
	 * 实现类的注册id
	 */
	private String implClassId = "";
	
	/**
	 * 用户数据
	 */
	private HashMap<String, String> userdata = new HashMap<String, String>(); 

	/**
	 * 没有子节点的结点显示的图片(将会从setImagePath(url)
	 * 方法指定的路径来获取图片)(可选参数)
	 */
	private String im0 = "";
	
	/**
	 * 包含子结点的结点展示时显示的图片(可选参数)
	 */
	private String im1 = "";
	
	/**
	 * 包含子结点的结点关闭时显示的图片(可选参数)
	 */
	private String im2 = "";
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getParentid() {
		return parentid;
	}

	public String getChild() {
		return child;
	}

	public void setChild(String child) {
		this.child = child;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public HashMap<String, String> getUserdata(){
		return userdata;
	}

	public void setUserdata(HashMap<String, String> userdata) {
		this.userdata = userdata;
	}

	public String getImplClassId() {
		return implClassId;
	}

	public void setImplClassId(String implClassId) {
		this.implClassId = implClassId;
	}

	public String getIm0() {
		return im0;
	}

	public void setIm0(String im0) {
		this.im0 = im0;
	}

	public String getIm1() {
		return im1;
	}

	public void setIm1(String im1) {
		this.im1 = im1;
	}

	public String getIm2() {
		return im2;
	}

	public void setIm2(String im2) {
		this.im2 = im2;
	}	
}
