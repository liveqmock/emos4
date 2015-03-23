package com.ultrapower.eoms.ultrasm.model;
/**
 * 工单自定义派发树删除条件实体
 * @author zhuzhaohui E-mail:zhuzhaohui@ultrapower.com.cn
 * @version 2010-9-29 下午03:50:18
 */
public class FormCustSenderDelPara {

	private String custSenderPid;
	private String orginzedpid;
	private String orgtype;
	//fanying 2013-4-24 解决自定义派发树同一个人员出现两次的问题
	private String parentid;
	public String getCustSenderPid() {
		return custSenderPid;
	}
	public void setCustSenderPid(String custSenderPid) {
		this.custSenderPid = custSenderPid;
	}
	public String getOrginzedpid() {
		return orginzedpid;
	}
	public void setOrginzedpid(String orginzedpid) {
		this.orginzedpid = orginzedpid;
	}
	public String getOrgtype() {
		return orgtype;
	}
	public void setOrgtype(String orgtype) {
		this.orgtype = orgtype;
	}
	public String getParentid()
	{
		return parentid;
	}
	public void setParentid(String parentid)
	{
		this.parentid = parentid;
	}
}
