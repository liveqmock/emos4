package com.ultrapower.workflow.engine.store.model;

import com.ultrapower.workflow.utils.UUIDGenerator;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Step<T>
  implements Serializable, Cloneable
{
  private String id;
  private String entryId;
  private String stepCode;
  private String stepNo;
  private String name;
  private String flowId;
  private String origId;
  private String origCode;
  private String state;
  private String track;
  private long createTime;
  private int subCount;
  private int finishCount;
  private String type;
  private String forwardId;
  private String forwardCode;
  private String backwardId;
  private String backwardCode;
  private String stepGroup;

  public Step()
  {
  }

  public Step(String id, String entryId, String stepCode, String forwardId, String forwardCode, String backwardId, String backwardCode, String flowId, String origId, String origCode, String state, String track, long createTime, int subCount, int finishCount, String type)
   {
     this.id = UUIDGenerator.getId();
     this.entryId = entryId;
     this.stepCode = stepCode;
     this.forwardId = forwardId;
     this.forwardCode = forwardCode;
     this.backwardId = backwardId;
     this.backwardCode = backwardCode;
     this.flowId = flowId;
     this.origId = origId;
     this.origCode = origCode;
     this.state = state;
     this.track = track;
     this.createTime = createTime;
     this.subCount = subCount;
     this.finishCount = finishCount;
     this.type = type;
   }
 
   @Id
   public String getId() {
     return this.id;
   }
 
   public void setId(String id) {
     this.id = id;
   }
 
   public String getEntryId() {
     return this.entryId;
   }
 
   public void setEntryId(String entryId) {
     this.entryId = entryId;
   }
 
   public String getFlowId() {
     return this.flowId;
   }
 
   public void setFlowId(String flowId) {
     this.flowId = flowId;
   }
 
   public String getOrigId() {
     return this.origId;
   }
 
   public void setOrigId(String origId) {
     this.origId = origId;
   }
 
   public String getState() {
     return this.state;
   }
 
   public void setState(String state) {
     this.state = state;
   }
 
   public String getTrack() {
     return this.track;
   }
 
   public void setTrack(String track) {
     this.track = track;
   }
 
   public String getType() {
     return this.type;
   }
 
   public void setType(String type) {
     this.type = type;
   }
 
   public long getCreateTime() {
     return this.createTime;
   }
 
   public void setCreateTime(long createTime) {
     this.createTime = createTime;
   }
 
   public int getSubCount() {
     return this.subCount;
   }
 
   public void setSubCount(int subCount) {
     this.subCount = subCount;
   }
 
   public int getFinishCount() {
     return this.finishCount;
   }
 
   public void setFinishCount(int finishCount) {
     this.finishCount = finishCount;
   }
 
   public String getForwardId()
   {
     return this.forwardId;
   }
 
   public void setForwardId(String forwardId)
   {
     this.forwardId = forwardId;
   }
 
   public String getBackwardId()
   {
     return this.backwardId;
   }
 
   public void setBackwardId(String backwardId)
   {
     this.backwardId = backwardId;
   }
 
   public String getStepCode()
   {
     return this.stepCode;
   }
 
   public void setStepCode(String stepCode)
   {
     this.stepCode = stepCode;
   }
 
   public String getForwardCode()
   {
     return this.forwardCode;
   }
 
   public void setForwardCode(String forwardCode)
   {
     this.forwardCode = forwardCode;
   }
 
   public String getBackwardCode()
   {
     return this.backwardCode;
   }
 
   public void setBackwardCode(String backwardCode)
   {
     this.backwardCode = backwardCode;
   }
 
   public String getOrigCode() {
     return this.origCode;
   }
 
   public void setOrigCode(String origCode) {
     this.origCode = origCode;
   }
 
   public String getName() {
     return this.name;
   }
 
   public void setName(String name) {
     this.name = name;
   }
 
   public String getStepNo() {
     return this.stepNo;
   }
 
   public void setStepNo(String stepNo) {
     this.stepNo = stepNo;
   }
 
   public String getStepGroup() {
     return this.stepGroup;
   }
 
   public void setStepGroup(String stepGroup) {
     this.stepGroup = stepGroup;
   }

   public Object clone() {
     Object result = null;
     try {
       result = super.clone();
     } catch (CloneNotSupportedException e) {
       e.printStackTrace();
     }
     return result;
   }
 }
