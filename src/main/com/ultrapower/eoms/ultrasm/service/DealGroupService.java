package com.ultrapower.eoms.ultrasm.service;

import com.ultrapower.eoms.ultrasm.model.DealGroup;
import com.ultrapower.eoms.ultrasm.model.UserInfo;
import java.util.List;

public abstract interface DealGroupService
{
  public abstract void saveOrUpdate(DealGroup paramDealGroup);

  public abstract void deleteById(String paramString);

  public abstract DealGroup getById(String paramString);

  public abstract List<DealGroup> getAll();

  public abstract List<DealGroup> getDealGroupByUser(String paramString);

  public abstract List<UserInfo> getDealGroupUsers(DealGroup paramDealGroup);

  public abstract List<UserInfo> getDealGroupUsers(String paramString1, String paramString2);
}
