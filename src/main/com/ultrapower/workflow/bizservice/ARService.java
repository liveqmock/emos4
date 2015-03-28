package com.ultrapower.workflow.bizservice;

import com.ultrapower.eoms.ultrasm.model.UserInfo;
import java.util.List;

public abstract interface ARService
{
  public abstract List<String> getOwnFields(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);

  public abstract UserInfo getUserInfo(String paramString);
}

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.bizservice.ARService
 * JD-Core Version:    0.6.0
 */