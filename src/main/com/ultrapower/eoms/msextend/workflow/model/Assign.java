package com.ultrapower.eoms.msextend.workflow.model;

/**
 * 处理人
 * @author hhy
 *
 */
public class Assign {
	private String actorType;//⑴处理人类型，U（用户），G（组），R（角色细分）
	private String actorID;//⑵登录名，组id，角色细分id
	private String actionCode;//⑶动作类型
	private String dealType;//⑷组处理模式，1（共享）,2（独占）,3（管理者管理）
	private String acceptOutTime;//⑸受理时间
	private String assignOutTime;//⑹派发时间
	private String dealOutTime;//⑺处理时间
	private String rollbackStepID;//⑻驳回、追回等回退类动作需要指定的stepId
	private String targetPhaseNo;//⑼环节号，只有固定流程并行分支时才需指定，其余情况可以空着或为”dp0”
	private String subflowVersionID;//⑽子流程版本号，派发到多个固定子流程时需要指定具体的版本号，派发到单个固定子流程可以为空。
	private String desc;//派发说明。
	
	private String actorName;//人员姓名,组名
	public String getActorType() {
		return actorType;
	}
	public void setActorType(String actorType) {
		this.actorType = actorType;
	}
	public String getActorID() {
		return actorID;
	}
	public void setActorID(String actorID) {
		this.actorID = actorID;
	}
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
	public String getDealType() {
		return dealType;
	}
	public void setDealType(String dealType) {
		this.dealType = dealType;
	}
	public String getAcceptOutTime() {
		return acceptOutTime;
	}
	public void setAcceptOutTime(String acceptOutTime) {
		this.acceptOutTime = acceptOutTime;
	}
	public String getAssignOutTime() {
		return assignOutTime;
	}
	public void setAssignOutTime(String assignOutTime) {
		this.assignOutTime = assignOutTime;
	}
	public String getDealOutTime() {
		return dealOutTime;
	}
	public void setDealOutTime(String dealOutTime) {
		this.dealOutTime = dealOutTime;
	}
	public String getRollbackStepID() {
		return rollbackStepID;
	}
	public void setRollbackStepID(String rollbackStepID) {
		this.rollbackStepID = rollbackStepID;
	}
	public String getTargetPhaseNo() {
		return targetPhaseNo;
	}
	public void setTargetPhaseNo(String targetPhaseNo) {
		this.targetPhaseNo = targetPhaseNo;
	}
	public String getSubflowVersionID() {
		return subflowVersionID;
	}
	public void setSubflowVersionID(String subflowVersionID) {
		this.subflowVersionID = subflowVersionID;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getActorName() {
		return actorName;
	}
	public void setActorName(String actorName) {
		this.actorName = actorName;
	}
	
}
