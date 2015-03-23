package com.ultrapower.eoms.workflow.managetools.model;

import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.workflow.managetools.control.context.CxtProcess;
import com.ultrapower.eoms.workflow.managetools.control.context.ImportContext;
import com.ultrapower.eoms.workflow.managetools.control.module.AbstractProcess;
import com.ultrapower.eoms.workflow.managetools.service.DealProcessManagerService;
import com.ultrapower.randomutil.Random15;

public class DealProcessHandler
{
	public void save(ImportContext context, CxtProcess process, AbstractProcess processModule)
	{
		String processID = new Random15().getRandom(System.currentTimeMillis());
		
		DealProcess dealProcess = new DealProcess(processID);
		
		process.setProcessID(processID);
		dealProcess.setProcessid(processID);
		//System.out.println("==========DealProcess | "+processID+" | BEGIN==========");
		//System.out.println("BaseID：" + context.getBaseInfor().getBaseID());
		if(processModule.getProcessType().equals("FREE"))
		{
			String phaseNo = new Random15().getRandom(System.currentTimeMillis());
			//System.out.println("环节号：" + phaseNo);
			//System.out.println("环节类型：FREE");
			process.setPhaseNo(phaseNo);
			dealProcess.setFlagpredefined(0l);
		}
		else
		{
			//System.out.println("环节号：" + process.getPhaseNo());
			//System.out.println("环节类型：DEFINE");
			dealProcess.setFlagpredefined(1l);
		}
		dealProcess.setPhaseno(process.getPhaseNo());
		if(processModule.getPreProcess() == null)
		{
			//System.out.println("前环节号：BEGIN");
			dealProcess.setPrephaseno("BEGIN");
			dealProcess.setStprocessaction("新建");
			//System.out.println("派发人：" + process.getDealUser());
			dealProcess.setAssigner(process.getDealUser());
			dealProcess.setAssignerid(process.getLoginName());
			//System.out.println("创建时间：" + TimeUtils.formatIntToDateString(process.getDealTime()));
			dealProcess.setStdate(process.getDealTime());
		}
		else
		{
			//System.out.println("前环节号：" + context.getPreCxtProcess().getPhaseNo());
			dealProcess.setPrephaseno(context.getPreCxtProcess().getPhaseNo());
			dealProcess.setStprocessaction("下一步");
			//System.out.println("派发人：" + context.getPreCxtProcess().getDealUser());
			dealProcess.setAssigner(context.getPreCxtProcess().getDealUser());
			dealProcess.setAssignerid(context.getPreCxtProcess().getLoginName());
			//System.out.println("创建时间：" + TimeUtils.formatIntToDateString(context.getPreCxtProcess().getDealTime()));
			dealProcess.setStdate(context.getPreCxtProcess().getDealTime());
		}
		dealProcess.setEdprocessaction("下一步");
		//System.out.println("动作：" + process.getActionName());
		dealProcess.setActionname(process.getActionName());
		dealProcess.setProcessstatus("已" + dealProcess.getActionname());
		//System.out.println("处理人：" + process.getDealUser());
		dealProcess.setAssignee(process.getDealUser());
		dealProcess.setAssigneeid(process.getLoginName());
		dealProcess.setDealer(process.getDealUser());
		dealProcess.setDealerid(process.getLoginName());
		//System.out.println("完成时间：" + TimeUtils.formatIntToDateString(process.getDealTime()));
		dealProcess.setBgdate(process.getDealTime());
		dealProcess.setEddate(process.getDealTime());
		
		dealProcess.setBasestartdate(dealProcess.getStdate());
		dealProcess.setBaseid(context.getBaseInfor().getBaseID());
		dealProcess.setBaseschema(context.getBaseInfor().getBaseSchema());
		
		if(process.getProcessType().equals("AUTO"))
		{
			//System.out.println("流程号：" + context.getFlowID());
			dealProcess.setFlowid(context.getFlowID());
		}
		else
		{
			//System.out.println("流程号：");
			dealProcess.setFlowid("");
		}
		
		if(context.getFlowID() != null && context.getEntryType().indexOf("SUB") > -1)
		{
			//System.out.println("父流程标识：" + context.getFlowID());
			dealProcess.setParentflowid(context.getFlowID());
			dealProcess.setParententryid(context.getTopEntryID());
		}
		else
		{
			//System.out.println("父流程标识：");
			dealProcess.setParentflowid("");
			dealProcess.setParententryid("");
		}

		//System.out.println("实体ID：" + context.getEntryID());
		dealProcess.setEntryid(context.getEntryID());
		//System.out.println("Top实体ID：" + context.getTopEntryID());
		dealProcess.setTopentryid(context.getTopEntryID());
		dealProcess.setActortype("USER");
		dealProcess.setDealtype("SHARE");
		dealProcess.setFlagassigntype("主办");
		
		if(process.getProcessType().equals("AUTO"))
		{
			//System.out.println("启动内部流程个数：" + 1);
			dealProcess.setInsideflowscount(1l);
			dealProcess.setFinishinsideflowscount(1l);
		}
		else
		{
			//System.out.println("启动内部流程个数：" + 0);
			dealProcess.setInsideflowscount(0l);
			dealProcess.setFinishinsideflowscount(0l);
		}
		
		dealProcess.setBasecreatetime(context.getBaseInfor().getCreateTime());
		dealProcess.setRoleid(process.getRoleCode());
		dealProcess.setRolename(process.getRoleName());
		
		//System.out.println("环节说明：" + process.getDesc());
		dealProcess.setDpdesc(process.getDesc());

		//System.out.println("===========DealProcess | "+processID+" | END===========");
		
		DealProcessManagerService dpService = (DealProcessManagerService)WebApplicationManager.getBean("dealProcessManagerServiceImpl");
		dpService.insert(dealProcess);
	}
}
