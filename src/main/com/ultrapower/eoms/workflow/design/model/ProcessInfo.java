package com.ultrapower.eoms.workflow.design.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Copyright (c) 2010 神州泰岳服务管理事业部通用产品
 * All rights reserved.
 * 描述：
 * @author 范莹
 * @date   2010-4-21
 */
public class ProcessInfo {

	private String phaseNo;
	private String processId;

	private boolean hasFreeProcess;
	private String title;
	private String content;
	private String status;
	private String prephaseNo;
	private String brerPhaseNo;//兄弟环节
	private String flagduplicated;//复制品标识
	private String flagendduplicated;//最后复制品标识
	private String flagpredefined;
	
	private String stepid;
	private String forwardstepid;
	private String forwardstepids;
	
	private String flowid;
	
	private String flowids;
	
	private String assigngroupid;
	private String assigngrouptype;
	private String dealer;
	private String desc;
	private String stDate;//派发到本环节的时间
	private String bgDate;//受理时间
	private String edDate;//完成时间

	private String flagactive;
	
	private String processType;
	
	private String finishDate;
	private String createDate;
	private String taskId;
	
	//中间经过分支的路径
	private String tracks;
	
	
	private String track;
	
	//服务器端封装连线
	private String edProcessAction;
	
    private List<ProcessInfoView> processinfoList = new ArrayList<ProcessInfoView>();
    
    //内部自由子流程处理信息
    private List<String[]> subProcessLogList = new ArrayList<String[]>();
    
    public ProcessInfo(){
    }
    
    public String getForwardstepid() {
		return forwardstepid;
	}

	public void setForwardstepid(String forwardstepid) {
		this.forwardstepid = forwardstepid;
	}

	public String getFlowid() {
		return flowid;
	}

	public void setFlowid(String flowid) {
		this.flowid = flowid;
	}

	

	public String getPhaseNo() {
		return phaseNo;
	}

	public void setPhaseNo(String phaseNo) {
		this.phaseNo = phaseNo;
	}

	public boolean isHasFreeProcess() {
		return hasFreeProcess;
	}

	public void setHasFreeProcess(boolean hasFreeProcess) {
		this.hasFreeProcess = hasFreeProcess;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPrephaseNo() {
		return prephaseNo;
	}

	public void setPrephaseNo(String prephaseNo) {
		this.prephaseNo = prephaseNo;
	}


	public String getBrerPhaseNo() {
		return brerPhaseNo;
	}

	public void setBrerPhaseNo(String brerPhaseNo) {
		this.brerPhaseNo = brerPhaseNo;
	}

	public String getStepid() {
		return stepid;
	}

	public void setStepid(String stepid) {
		this.stepid = stepid;
	}

	public String getFlagduplicated() {
		return flagduplicated;
	}

	public void setFlagduplicated(String flagduplicated) {
		this.flagduplicated = flagduplicated;
	}

	public String getFlagendduplicated() {
		return flagendduplicated;
	}

	public void setFlagendduplicated(String flagendduplicated) {
		this.flagendduplicated = flagendduplicated;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public List<ProcessInfoView> getProcessinfoList() {
		return processinfoList;
	}

	public void setProcessinfoList(List<ProcessInfoView> processinfoList) {
		this.processinfoList = processinfoList;
	}

	public String getStDate() {
		return stDate;
	}

	public void setStDate(String stDate) {
		this.stDate = stDate;
	}

	public String getBgDate() {
		return bgDate;
	}

	public void setBgDate(String bgDate) {
		this.bgDate = bgDate;
	}

	public String getEdDate() {
		return edDate;
	}

	public void setEdDate(String edDate) {
		this.edDate = edDate;
	}

	public String getDealer() {
		return dealer;
	}

	public void setDealer(String dealer) {
		this.dealer = dealer;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getFlagpredefined() {
		return flagpredefined;
	}

	public void setFlagpredefined(String flagpredefined) {
		this.flagpredefined = flagpredefined;}

	public String getFlowids() {
		return flowids;
	}

	public void setFlowids(String flowids) {
		this.flowids = flowids;
	}
	public String getFlagactive() {
		return flagactive;
	}
	public void setFlagactive(String flagactive) {
		this.flagactive = flagactive;
	}

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

	public String getForwardstepids() {
		return forwardstepids;
	}

	public void setForwardstepids(String forwardstepids) {
		this.forwardstepids = forwardstepids;
	}

	public String getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getAssigngroupid()
	{
		return assigngroupid;
	}

	public void setAssigngroupid(String assigngroupid)
	{
		this.assigngroupid = assigngroupid;
	}

	public String getAssigngrouptype()
	{
		return assigngrouptype;
	}

	public void setAssigngrouptype(String assigngrouptype)
	{
		this.assigngrouptype = assigngrouptype;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTracks()
	{
		return tracks;
	}

	public void setTracks(String tracks)
	{
		this.tracks = tracks;
	}

	public String getTrack()
	{
		return track;
	}

	public void setTrack(String track)
	{
		this.track = track;
	}

	public String getEdProcessAction()
	{
		return edProcessAction;
	}

	public void setEdProcessAction(String edProcessAction)
	{
		this.edProcessAction = edProcessAction;
	}

	public List<String[]> getSubProcessLogList()
	{
		return subProcessLogList;
	}

	public void setSubProcessLogList(List<String[]> subProcessLogList)
	{
		this.subProcessLogList = subProcessLogList;
	}
	
	
}
