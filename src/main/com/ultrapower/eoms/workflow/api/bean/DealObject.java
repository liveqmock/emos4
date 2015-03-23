package com.ultrapower.eoms.workflow.api.bean;


public class DealObject {

	private String assigneeId = "";
	private String groupId = "";
	private String roleId = "";
	private String dealDesc = "";
	private String dealType = "";//组处理模式
	private String processId = "";
	private String actorStr = "";//处理人字符串，可以支持传入拼好的处理人字符串，用以解决：固定下一步进入自由子流程，动作标识和处理人串中不一致的问题。
	private long assignOverTime;
	private long acceptOverTime;
	private long dealOverTime;
	
	/**
	 * @param assigneeId
	 * @param groupId
	 * @param roleId
	 * @param dealDesc
	 * @param assignOverTime
	 * @param acceptOverTime
	 * @param dealOverTime
	 */
	public DealObject(String assigneeId, String groupId, String roleId,
			String dealDesc, long assignOverTime, long acceptOverTime,
			long dealOverTime) {
		this.assigneeId = assigneeId;
		this.groupId = groupId;
		this.roleId = roleId;
		this.dealDesc = dealDesc;
		this.assignOverTime = assignOverTime;
		this.acceptOverTime = acceptOverTime;
		this.dealOverTime = dealOverTime;
	}

	/**
	 * @param assigneeId
	 * @param groupId
	 * @param roleId
	 */
	public DealObject(String assigneeId, String groupId, String roleId) {
		this.assigneeId = assigneeId;
		this.groupId = groupId;
		this.roleId = roleId;
	}
	
	/**
	 * 
	 */
	public DealObject() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getAssigneeId() {
		return assigneeId;
	}
	public void setAssigneeId(String assigneeId) {
		this.assigneeId = assigneeId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public long getAssignOverTime() {
		return assignOverTime;
	}
	public void setAssignOverTime(long assignOverTime) {
		this.assignOverTime = assignOverTime;
	}
	public long getAcceptOverTime() {
		return acceptOverTime;
	}
	public void setAcceptOverTime(long acceptOverTime) {
		this.acceptOverTime = acceptOverTime;
	}
	public String getDealDesc() {
		return dealDesc;
	}
	public void setDealDesc(String dealDesc) {
		this.dealDesc = dealDesc;
	}
	public long getDealOverTime() {
		return dealOverTime;
	}
	public void setDealOverTime(long dealOverTime) {
		this.dealOverTime = dealOverTime;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getDealType() {
		return dealType;
	}

	public void setDealType(String dealType) {
		this.dealType = dealType;
	}

	public String getActorStr() {
		return actorStr;
	}

	public void setActorStr(String actorStr) {
		this.actorStr = actorStr;
	}
}
