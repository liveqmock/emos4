package com.ultrapower.eoms.workflow.managetools.model;

import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.workflow.managetools.control.context.CxtProcess;
import com.ultrapower.eoms.workflow.managetools.control.context.ImportContext;
import com.ultrapower.eoms.workflow.managetools.service.DealProcessLogManagerService;

public class DealProcessLogHandler
{
	public void save(ImportContext context, CxtProcess process, String log)
	{
		DealProcessLog dpLog = new DealProcessLog(process.getProcessID());
		dpLog.setProcessid(process.getProcessID());
		dpLog.setProcesstype("DEAL");
		dpLog.setPhaseno(process.getPhaseNo());
		dpLog.setBaseid(context.getBaseInfor().getBaseID());
		dpLog.setBaseschema(context.getBaseInfor().getBaseSchema());
		if(context.getFlowID() != null && context.getEntryType().indexOf("SUB") > -1)
		{
			dpLog.setFlowid(context.getFlowID());
		}
		dpLog.setLoguserid(process.getLoginName());
		dpLog.setLoguser(process.getDealUser());
		dpLog.setActionname(process.getActionName());
		dpLog.setLogdesc(log);
		dpLog.setLogtime(process.getDealTime());
		
		DealProcessLogManagerService dplService = (DealProcessLogManagerService)WebApplicationManager.getBean("dpLogManagerServiceImpl");
		dplService.insert(dpLog);
	}
}
