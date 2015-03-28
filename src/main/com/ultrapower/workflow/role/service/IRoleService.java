package com.ultrapower.workflow.role.service;

import com.ultrapower.workflow.engine.core.model.DataField;
import com.ultrapower.workflow.role.model.ChildRole;
import com.ultrapower.workflow.role.model.Dimension;
import com.ultrapower.workflow.role.model.DimensionValue;
import com.ultrapower.workflow.role.model.RoleMatchDimension;
import com.ultrapower.workflow.role.model.RoleUser;
import com.ultrapower.workflow.role.model.WorkflowRole;
import java.util.List;
import java.util.Map;

public abstract interface IRoleService
{
  public abstract boolean saveOrUpdateRole(WorkflowRole paramWorkflowRole);

  public abstract boolean saveOrUpdateDimension(Dimension paramDimension);

  public abstract Dimension getDimensionById(String paramString);

  public abstract List<Dimension> getDimensionBySchema(String paramString);

  public abstract void deleteDimensionById(String paramString);

  public abstract List<ChildRole> getChildRoleListByRoleCode(String paramString);

  public abstract List<ChildRole> getChildRoleFromTree(String paramString1, String paramString2);

  public abstract boolean isBelongChildRole(String paramString1, String paramString2);

  public abstract ChildRole getChildRoleById(String paramString);

  public abstract List<ChildRole> getChildRoleByValue(String paramString);

  public abstract void saveOrUpdate(RoleUser paramRoleUser);

  public abstract void deleteRoleUserById(String paramString);

  public abstract void saveChildRole(ChildRole paramChildRole);

  public abstract void removeChildRoleById(String paramString);

  public abstract WorkflowRole getWfRoleByCode(String paramString);

  public abstract List<WorkflowRole> getWfRoleBySchema(String paramString);

  public abstract List<RoleMatchDimension> getRoleMatchDemensionByRoleCode(String paramString);

  public abstract WorkflowRole getWfRoleByVersonAndProcessId(String paramString1, String paramString2);

  public abstract void saveRoleMatchCondition(RoleMatchDimension paramRoleMatchDimension);

  public abstract void deleteAllRoleMatchConditionByRoleCode(String paramString);

  public abstract List<WorkflowRole> getRoleByVersionCode(String paramString);

  public abstract void delWfRoleByRoleCode(String paramString1, String paramString2);

  public abstract List<DimensionValue> getDimensionValue(Dimension paramDimension);

  public abstract Dimension getDimensionByCode(String paramString);

  public abstract List<ChildRole> getChildRoleByHql(String paramString);

  public abstract List<RoleUser> getRoleUserByHql(String paramString);

  public abstract WorkflowRole getWorkflowRoleById(String paramString);

  public abstract void deleteWorkflowRoleById(String paramString);

  public abstract List<Dimension> getDimensionIds(String paramString1, String paramString2);

  public abstract ChildRole getChildRoleByDim(String paramString1, String paramString2, String paramString3);

  public abstract List<RoleUser> getRoleUserByCroleID(String paramString);

  public abstract List<ChildRole> matchChildRole(String paramString1, String paramString2, String paramString3, Map<String, DataField> paramMap);

  public abstract List<ChildRole> matchRole(String paramString1, String paramString2, String paramString3);
}

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.role.service.IRoleService
 * JD-Core Version:    0.6.0
 */