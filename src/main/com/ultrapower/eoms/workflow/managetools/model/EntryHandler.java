package com.ultrapower.eoms.workflow.managetools.model;

import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.workflow.managetools.control.context.ImportContext;
import com.ultrapower.eoms.workflow.managetools.service.EntryManagerService;
import com.ultrapower.randomutil.Random15;

public class EntryHandler
{
	public void save(ImportContext context)
	{
		String entryID = new Random15().getRandom(System.currentTimeMillis());
		Entry entry = new Entry(entryID);
		if(context.getEntryType().indexOf("SUB") > 0)
		{
			entry.setTopentryid(context.getTopEntryID());
			entry.setParententryid(context.getTopEntryID());
			entry.setParentflowid(context.getFlowID());
		}
		else
		{

			entry.setTopentryid(entry.getId());
		}
		if(context.getEntryType().indexOf("FREE") > 0)
		{
			entry.setType("FREE");
			entry.setDefname("FREESUBFLOW");
		}
		else if(context.getEntryType().indexOf("DEFINE") > 0)
		{
			entry.setType("DEFINE");
			entry.setDefname(context.getVersion());
		}
		entry.setState("finish");
		entry.setCreatetime(context.getBaseInfor().getCreateTime());
		entry.setClosetime(context.getBaseInfor().getCloseTime());
		entry.setTopflowid(StringUtils.checkNullString(context.getFlowID()));
		System.out.println("==========Entry | "+entryID+" | BEGIN==========");
		System.out.println("类型：");
		System.out.println("开始时间：" + entry.getCreatetime());
		System.out.println("结束时间：" + entry.getClosetime());
		System.out.println("===========Entry | "+entryID+" | END===========");
		
		EntryManagerService emService = (EntryManagerService)WebApplicationManager.getBean("entryManagerServiceImpl");
		emService.insert(entry);
		
		context.setEntryID(entry.getId());
	}
}
