package com.ultrapower.workflow.configuration.sort.service;

import com.ultrapower.workflow.configuration.sort.model.WfSort;
import com.ultrapower.workflow.configuration.sort.model.WfType;
import java.util.List;
import java.util.Map;

public abstract interface IWfSortService
{
  public abstract List<WfSort> getAllWfSort();

  public abstract boolean saveWfSort(WfSort paramWfSort);

  public abstract boolean delWfSort(String paramString);

  public abstract WfSort getWfSortByid(String paramString);

  public abstract List<WfType> getWfTypeBySortId(String paramString);

  public abstract WfType getWfTypeByid(String paramString);

  public abstract WfType getWfTypeByCode(String paramString);

  public abstract boolean saveWfType(WfType paramWfType);

  public abstract boolean delWfTypeById(String paramString);

  public abstract List<WfSort> getChildSortById(String paramString);

  public abstract List<WfType> getAllWfType();

  public abstract WfSort getWfSortByCode(String paramString);

  public abstract Map<String, WfType> getWfTypeMap();

  public abstract WfType getWfTypeFromDB(String paramString);

  public abstract WfType updateEntryCount(String paramString);
}

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.configuration.sort.service.IWfSortService
 * JD-Core Version:    0.6.0
 */