package com.ultrapower.workflow.relate;

import com.ultrapower.workflow.relate.model.RelateModel;
import java.util.List;

public abstract interface RelateService
{
  public abstract List<RelateModel> getSyncRelateList(String paramString1, String paramString2);

  public abstract List<RelateModel> getASuncRelateList(String paramString1, String paramString2);

  public abstract void save(RelateModel paramRelateModel);
}

/* Location:           F:\Ultrapower\WebProject\BPP\eoms4\WebRoot\WEB-INF\lib\WFServer.jar
 * Qualified Name:     com.ultrapower.workflow.relate.RelateService
 * JD-Core Version:    0.6.0
 */