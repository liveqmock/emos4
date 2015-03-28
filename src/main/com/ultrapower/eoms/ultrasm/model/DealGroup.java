package com.ultrapower.eoms.ultrasm.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="BS_T_WF_DEALGROUP")
public class DealGroup
  implements Serializable
{
  private String id;
  private String name;
  private String groupId;
  private String groupName;
  private String groupType;
  private String entryState;
  private int isEnable;
  private long createTime;

  @Id
  @GeneratedValue(generator="system-uuid")
  @GenericGenerator(name="system-uuid", strategy="uuid")
  public String getId()
  {
    return this.id;
  }
  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getGroupId() {
    return this.groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  public String getGroupName() {
    return this.groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public String getGroupType() {
    return this.groupType;
  }

  public void setGroupType(String groupType) {
    this.groupType = groupType;
  }

  public int getIsEnable() {
    return this.isEnable;
  }

  public void setIsEnable(int isEnable) {
    this.isEnable = isEnable;
  }

  public long getCreateTime() {
    return this.createTime;
  }

  public void setCreateTime(long createTime) {
    this.createTime = createTime;
  }

  public String getEntryState() {
    return this.entryState;
  }

  public void setEntryState(String entryState) {
    this.entryState = entryState;
  }
}
