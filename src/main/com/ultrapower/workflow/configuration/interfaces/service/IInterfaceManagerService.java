package com.ultrapower.workflow.configuration.interfaces.service;

import com.ultrapower.workflow.configuration.interfaces.model.WfInterSetting;
import com.ultrapower.workflow.configuration.interfaces.model.WfInterface;
import java.util.List;

public abstract interface IInterfaceManagerService
{
  public abstract boolean saveOrUpdateInte(WfInterface paramWfInterface);

  public abstract boolean removeInterByCode(String paramString);

  public abstract List<WfInterface> getAllInte();

  public abstract WfInterface getInteByCode(String paramString);

  public abstract boolean saveOrUpdateSetting(WfInterSetting paramWfInterSetting);

  public abstract List<WfInterSetting> getInteSettingByWfCode(String paramString);

  public abstract WfInterSetting getInteSettingById(String paramString);

  public abstract List<WfInterSetting> getInteByWfCodeAndType(String paramString1, String paramString2);

  public abstract List<WfInterface> getInterfaceListByType(String paramString);
}

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.configuration.interfaces.service.IInterfaceManagerService
 * JD-Core Version:    0.6.0
 */