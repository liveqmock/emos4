package com.ultrapower.eoms.workflow.managetools.control.module;

import com.ultrapower.eoms.workflow.managetools.control.context.ImportContext;
import com.ultrapower.eoms.workflow.managetools.model.EntryHandler;

public class SubEntry extends AbstractEntry
{

	@Override
	public ImportContext signal(ImportContext context)
	{
		context.setVersion(version);
		if(version.equals("FREE"))
		{
			context.setEntryType("SUB-FREE");
		}
		else
		{
			context.setEntryType("SUB-DEFINE");
		}
		
		//插入Entry表
		EntryHandler entryHandler = new EntryHandler();
		entryHandler.save(context);
		
		//处理环节
		for(AbstractProcess process : processes)
		{
			process.signal(context);
		}
		
		return context;
	}

}
