package com.ultrapower.eoms.workflow.sort.service;

import java.util.List;

import com.ultrapower.workflow.configuration.sort.model.WfType;

/**
 * 
 * @author fying
 * @version
 * @since
 * @see TemplateDoc
 */
public interface WfSortMenuService
{
    public List<String> saveMenu_type(WfType wfType,String code);
    public String saveMenu_step(String baseSchema,String stepID,String stepNo);
    public void updateMenu_nodename(String baseSchema,String stepID,String stepNo);
}
