package com.ultrapower.eoms.ultrabpp.cache.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author fying
 * @version
 * @since
 * @see TemplateDoc
 */
@Entity
@Table(name="BS_T_BPP_STEP")
public class StepModel
{
    /**
     * 主键ID
     */
    private String id;
    /**
     * 环节标识
     */
    private String stepNo;
    /**
     * 工单类型
     */
    private String baseSchema;
    /**
     * 环节简述
     */
    private String description;
    /**
     * 操作角色
     */
    private String roleName;
    /**
     * 操作角色ID
     */
    private String roleID;
    /**
     * 操作角色类型
     */
    private String roleProcessRoleType;
    /**
     * 操作人
     */
    private String assignee;
    /**
     * 操作人ID
     */
    private String assigneeID;
    /**
     * 操作组
     */
    private String groupName;
    /**
     * 操作组ID
     */
    private String groupID;
    /**
     * 关键字
     */
    private String roleKey;
    /**
     * 上下文关键字
     */
    private String contextKey;
    /**
     * 环节类型
     */
    private String actionType;
    /**
     * 处理模式
     */
    private String taskPolicy;
    /**
     * 受理时限
     */
    private String acceptOver;
    /**
     * 处理时限
     */
    private String dealOver;
    /**
     * 是否部署
     */
    private Integer hasDeploy;
    /**
     * 部署前操作
     */
    private String operate;
    /**
     * 内部子流程
     */
    private Integer hasSubFlow=0;
    

    @Id
    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getBaseSchema() {
	return baseSchema;
    }

    public void setBaseSchema(String baseSchema) {
	this.baseSchema = baseSchema;
    }



    public String getRoleName() {
	return roleName;
    }

    public void setRoleName(String roleName) {
	this.roleName = roleName;
    }

    public String getRoleID() {
	return roleID;
    }

    public void setRoleID(String roleID) {
	this.roleID = roleID;
    }

    public String getRoleProcessRoleType() {
	return roleProcessRoleType;
    }

    public void setRoleProcessRoleType(String roleProcessRoleType) {
	this.roleProcessRoleType = roleProcessRoleType;
    }

    public String getAssignee() {
	return assignee;
    }

    public void setAssignee(String assignee) {
	this.assignee = assignee;
    }

    public String getAssigneeID() {
	return assigneeID;
    }

    public void setAssigneeID(String assigneeID) {
	this.assigneeID = assigneeID;
    }




    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getGroupName()
    {
        return groupName;
    }

    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }

    public String getGroupID() {
	return groupID;
    }

    public void setGroupID(String groupID) {
	this.groupID = groupID;
    }

    public String getRoleKey() {
	return roleKey;
    }

    public void setRoleKey(String roleKey) {
	this.roleKey = roleKey;
    }

    public String getContextKey() {
	return contextKey;
    }

    public void setContextKey(String contextKey) {
	this.contextKey = contextKey;
    }

    public String getActionType() {
	return actionType;
    }

    public void setActionType(String actionType) {
	this.actionType = actionType;
    }

    public String getTaskPolicy() {
	return taskPolicy;
    }

    public void setTaskPolicy(String taskPolicy) {
	this.taskPolicy = taskPolicy;
    }

    public String getAcceptOver() {
	return acceptOver;
    }

    public void setAcceptOver(String acceptOver) {
	this.acceptOver = acceptOver;
    }

    public String getDealOver() {
	return dealOver;
    }

    public void setDealOver(String dealOver) {
	this.dealOver = dealOver;
    }

    public String getStepNo()
    {
        return stepNo;
    }

    public void setStepNo(String stepNo)
    {
        this.stepNo = stepNo;
    }

    public Integer getHasDeploy()
    {
        return hasDeploy;
    }

    public void setHasDeploy(Integer hasDeploy)
    {
        this.hasDeploy = hasDeploy;
    }

    public String getOperate()
    {
        return operate;
    }

    public void setOperate(String operate)
    {
        this.operate = operate;
    }

    public Integer getHasSubFlow()
    {
        return hasSubFlow;
    }

    public void setHasSubFlow(Integer hasSubFlow)
    {
        this.hasSubFlow = hasSubFlow;
    }

}
