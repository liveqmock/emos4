package com.ultrapower.eoms.common.core.component.tree.model;

import java.util.HashMap;
import java.util.List;

/**
 * 树节点-节点属性
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version May 17, 2010 1:54:32 PM
 */
public class DtreeBean {

	/**
	 * 父节点id
	 */
	private String parentId = "";
	
	/**
	 * 节点显示的标签(强制参数)
	 */
	private String text = "";
	
	/**
	 * 节点的id(强制参数)
	 */
	private String id = "";

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
	
	/**
	 * 没有选中的结点的颜色(可选参数)
	 */
	private String acolor = "";
	
	/**
	 * 选中的结点的颜色(可选参数)
	 */
	private String scolor = "";
	
	/**
	 *在加载时选择此结点(可选参数)
	 */
	private String select = "";
	
	/**
	 * 当前节点是否要disabled，大于0表示Disabled
	 */
	private String disabled = ""; 
	
	/**
	 * 是否单选 如果非空 则此结点的子结点会有单选按钮
	 */
	private String isradio = ""; 
	
	/**
	 * 展开此结点-可以为任意值(可选参数)
	 */
	private String open = "";
	
	/**
	 * 选择时调用函数-可以为任意值(可选参数)
	 */
	private String call = "";
	
	/**
	 * 如果节点有儿子的时候为1否则为0
	 */
	private String child = "1";
	
	/**
	 * 自定义参数
	 */
	private HashMap<String, String> userdata = new HashMap<String, String>(); 
	
	/**
	 * 当前节点是否默认选中，空代表默认不选中，大于0代表选中,-1表示选中当前节点但是不选中子节点
	 */
	private String checked = "";
	
	/**
	 * 当前节点不允许选择，不出现选择框，空代表可以选择，大于0不可以选择
	 */
	private String nocheckbox = ""; 
	
	/**
	 * 实现类的注册id
	 */
	private String implClassId = "";
	
	private List<DtreeBean> childList;
	
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getChild() {
		return child;
	}

	public void setChild(String child) {
		this.child = child;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String getNocheckbox() {
		return nocheckbox;
	}

	public void setNocheckbox(String nocheckbox) {
		this.nocheckbox = nocheckbox;
	}

	public List<DtreeBean> getChildList() {
		return childList;
	}

	public void setChildList(List<DtreeBean> childList) {
		this.childList = childList;
	}

	public DtreeBean() {
	}

	public DtreeBean(String text, String id){
		this.text = text;
		this.id = id;
	}
	
	public DtreeBean(String text, String id, String select, String open,String userdata) {
		this.text = text;
		this.id = id;
		this.select = select;
		this.open = open;
	}
	
	public DtreeBean(String text, String id, String im0, String im1,
			String im2, String select,String open,String userdata) {
		this.text = text;
		this.id = id;
		this.im0 = im0;
		this.im1 = im1;
		this.im2 = im2;
		this.open = open;
		this.select = select;
	}

	public DtreeBean(String text, String id, String im0,
			String im1, String im2, String acolor, String scolor,
			String select, String open,String userdata) {
		this.text = text;
		this.id = id;
		this.im0 = im0;
		this.im1 = im1;
		this.im2 = im2;
		this.acolor = acolor;
		this.scolor = scolor;
		this.select = select;
		this.open = open;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getAcolor() {
		return acolor;
	}
	public void setAcolor(String acolor) {
		this.acolor = acolor;
	}
	public String getScolor() {
		return scolor;
	}
	public void setScolor(String scolor) {
		this.scolor = scolor;
	}
	public String getSelect() {
		return select;
	}
	public void setSelect(String select) {
		this.select = select;
	}
	
	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getIsradio() {
		return isradio;
	}

	public void setIsradio(String isradio) {
		this.isradio = isradio;
	}

	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	public String getCall() {
		return call;
	}
	public void setCall(String call) {
		this.call = call;
	}

	public HashMap<String, String> getUserdata() {
		return userdata;
	}

	public void setUserdata(HashMap<String, String> userdata) {
		this.userdata = userdata;
	}
}
