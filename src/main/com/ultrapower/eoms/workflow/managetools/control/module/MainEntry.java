package com.ultrapower.eoms.workflow.managetools.control.module;

import com.ultrapower.eoms.workflow.managetools.control.context.ImportContext;
import com.ultrapower.eoms.workflow.managetools.model.BaseHandler;
import com.ultrapower.eoms.workflow.managetools.model.BaseInforHandler;
import com.ultrapower.eoms.workflow.managetools.model.EntryHandler;

public class MainEntry extends AbstractEntry
{

	@Override
	public ImportContext signal(ImportContext context)
	{
		//设置context
		context.getBaseInfor().setCreateTime(Long.parseLong(context.getFieldList().get(0).getFieldValue()));
		for(int i = context.getFieldList().size() - 1; i > 0; i--)
		{
			if(context.getFieldList().get(i).getFieldID().equals("A"))
			{
				context.getBaseInfor().setCloseTime(Long.parseLong(context.getFieldList().get(i).getFieldValue()));
				break;
			}
		}
		//context.setVersion(version);
		
		//插入Entry表
		EntryHandler entryHandler = new EntryHandler();
		entryHandler.save(context);
		context.setTopEntryID(context.getEntryID());
		
		//插入主表单
		BaseHandler baseHandler = new BaseHandler();
		baseHandler.save(context);
		
		//插入BaseInfor
		BaseInforHandler baseInforHandler = new BaseInforHandler();
		baseInforHandler.save(context);
		
		//处理环节
		for(AbstractProcess process : processes)
		{
			process.signal(context);
		}
		
		return context;
	}

}
