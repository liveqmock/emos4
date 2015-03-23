package com.ultrapower.eoms.workflow.managetools.control.module;

import com.ultrapower.eoms.workflow.managetools.control.context.CxtProcess;
import com.ultrapower.eoms.workflow.managetools.control.context.ImportContext;
import com.ultrapower.eoms.workflow.managetools.model.DealProcessHandler;
import com.ultrapower.randomutil.Random15;

public class AutoProcess extends AbstractProcess
{
	private AbstractEntry subEntry;

	@Override
	public ImportContext signal(ImportContext context)
	{
		String topEntryID = context.getEntryID();
		CxtProcess startCxtProcess = new CxtProcess();
		startCxtProcess.setPhaseNo(context.getPreCxtProcess().getPhaseNo());
		startCxtProcess.setProcessType(context.getPreCxtProcess().getProcessType());
		startCxtProcess.setTopPhaseNo(context.getPreCxtProcess().getTopPhaseNo());
		startCxtProcess.setSubFlowVersion(context.getPreCxtProcess().getSubFlowVersion());
		startCxtProcess.setRoleCode(context.getPreCxtProcess().getRoleCode());
		startCxtProcess.setRoleName(context.getPreCxtProcess().getRoleName());
		startCxtProcess.setActionName(context.getPreCxtProcess().getActionName());
		startCxtProcess.setDesc(context.getPreCxtProcess().getDesc());
		startCxtProcess.setFieldStart(context.getPreCxtProcess().getFieldStart());
		startCxtProcess.setFieldEnd(context.getPreCxtProcess().getFieldEnd());
		startCxtProcess.setDealTime(context.getPreCxtProcess().getDealTime());
		startCxtProcess.setDealUser(context.getPreCxtProcess().getDealUser());
		startCxtProcess.setLoginName(context.getPreCxtProcess().getLoginName());
		
		//生成CxtProccess对象
		CxtProcess process = new CxtProcess();
		process.setPhaseNo(phaseNo);
		process.setProcessType(processType);
		process.setTopPhaseNo(topPhaseNo);
		process.setSubFlowVersion(subFlowVersion);
		process.setRoleCode(roleCode);
		process.setRoleName(roleName);
		process.setActionName(actionName);
		process.setDesc(desc);
		process.setFieldStart(fieldStart);
		process.setFieldEnd(fieldEnd);
		process.setDesc(desc);
		
		String flowID = new Random15().getRandom(System.currentTimeMillis());
		context.setFlowID(flowID);
		context.setTopEntryID(context.getEntryID());
		
		subEntry.signal(context);
		
		process.setDealTime(context.getPreCxtProcess().getDealTime());
		process.setDealUser(context.getPreCxtProcess().getDealUser());
		process.setLoginName(context.getPreCxtProcess().getLoginName());
		
		context.setPreCxtProcess(startCxtProcess);
		context.setEntryID(topEntryID);
		context.setEntryType("MAIN-DEFINE");
		
		//保存DealProcess表
		DealProcessHandler dpHandler = new DealProcessHandler();
		dpHandler.save(context, process, this);
		
		//设置context
		context.setPreCxtProcess(process);
		context.setFlowID("");
		
		return context;
	}

	/**
	 * @return the subEntry
	 */
	public AbstractEntry getSubEntry()
	{
		return subEntry;
	}

	/**
	 * @param subEntry the subEntry to set
	 */
	public void setSubEntry(AbstractEntry subEntry)
	{
		this.subEntry = subEntry;
	}
}
