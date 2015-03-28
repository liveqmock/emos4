package com.ultrapower.workflow.configuration.version.service;

import com.ultrapower.workflow.configuration.version.model.WfVersion;
import java.util.List;

public abstract interface IWfVersionService
{
  public abstract boolean saveWfVersion(WfVersion paramWfVersion, boolean paramBoolean);

  public abstract boolean removeById(String paramString);

  public abstract List<WfVersion> getListByBaseCode(String paramString);

  public abstract List<WfVersion> getMainListByBaseCode(String paramString);

  public abstract List<WfVersion> getSubListByBaseCode(String paramString);

  public abstract List<WfVersion> getEnableListByBaseCode(String paramString);

  public abstract List<WfVersion> getAllWfList();

  public abstract WfVersion getById(String paramString);

  public abstract boolean startVersion(String paramString);

  public abstract boolean stopVersion(String paramString);

  public abstract boolean importWf(WfVersion paramWfVersion);

  public abstract WfVersion exportWf(String paramString);

  public abstract WfVersion getByCode(String paramString);

  public abstract String getSerialNum(String paramString1, String paramString2);

  public abstract List<String> getConditions(String paramString1, String paramString2);
}

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.configuration.version.service.IWfVersionService
 * JD-Core Version:    0.6.0
 */