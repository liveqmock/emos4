package com.ultrapower.workflow.bizservice;

import com.ultrapower.workflow.bizservice.model.Agency;
import java.util.List;

public abstract interface AgencyService
{
  public abstract void saveOrUpdate(Agency paramAgency);

  public abstract void deleteById(String paramString);

  public abstract List<Agency> getAll();

  public abstract List<Agency> getAllByPage();

  public abstract List<Agency> getByUser(String paramString);

  public abstract Agency getById(String paramString);

  public abstract void saveDudyAgency(String paramString1, String paramString2, String paramString3, String paramString4, long paramLong1, long paramLong2);

  public abstract List<Agency> getAgencyListByAgentUser(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt);
}

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizservice.AgencyService
 * JD-Core Version:    0.6.0
 */