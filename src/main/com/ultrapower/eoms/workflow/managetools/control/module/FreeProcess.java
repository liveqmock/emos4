package com.ultrapower.eoms.workflow.managetools.control.module;

import java.util.ArrayList;
import java.util.List;

import com.ultrapower.eoms.workflow.managetools.control.context.CxtField;
import com.ultrapower.eoms.workflow.managetools.control.context.CxtProcess;
import com.ultrapower.eoms.workflow.managetools.control.context.ImportContext;
import com.ultrapower.eoms.workflow.managetools.model.DealProcessHandler;
import com.ultrapower.eoms.workflow.managetools.model.DealProcessLogHandler;
import com.ultrapower.eoms.workflow.managetools.model.ModifyLogHandler;
import com.ultrapower.randomutil.Random15;

public class FreeProcess extends AbstractProcess
{

	@Override
	public ImportContext signal(ImportContext context)
	{
		if(this.preProcess == null && this.processType.equals("FREE"))
		{
			//生成CxtProccess对象
			CxtProcess process = new CxtProcess();
			process.setPhaseNo(new Random15().getRandom(System.currentTimeMillis()));
			process.setProcessType(processType);
			process.setTopPhaseNo(topPhaseNo);
			process.setSubFlowVersion(subFlowVersion);
			process.setRoleCode(roleCode);
			process.setRoleName(roleName);
			process.setActionName(actionName);
			process.setFieldStart(fieldStart);
			process.setFieldEnd(fieldEnd);
			
			List<CxtField> fields = new ArrayList<CxtField>();
			long dealTime = Long.parseLong(context.getFieldList().get(fieldStart).getFieldValue());
			String userName = context.getPreCxtProcess().getDealUser();
			String loginName = context.getPreCxtProcess().getLoginName();
			for(int i = fieldStart+3; i < fieldEnd; i++)
			{
				fields.add(context.getFieldList().get(i));
			}
			process.setDealTime(dealTime);
			process.setDealUser(userName);
			process.setLoginName(loginName);
			process.setDesc(context.getPreCxtProcess().getDealUser() + "新建");

			//保存DealProcess表
			DealProcessHandler dpHandler = new DealProcessHandler();
			dpHandler.save(context, process, this);

			//设置context
			context.setPreCxtProcess(process);
			
			this.preProcess = this;
		}
		
		//生成CxtProccess对象
		CxtProcess process = new CxtProcess();
		process.setPhaseNo(new Random15().getRandom(System.currentTimeMillis()));
		process.setProcessType(processType);
		process.setTopPhaseNo(topPhaseNo);
		process.setSubFlowVersion(subFlowVersion);
		process.setRoleCode(roleCode);
		process.setRoleName(roleName);
		process.setActionName(actionName);
		process.setFieldStart(fieldStart);
		process.setFieldEnd(fieldEnd);
		
		List<CxtField> fields = new ArrayList<CxtField>();
		long dealTime = Long.parseLong(context.getFieldList().get(fieldStart).getFieldValue());
		String userName = context.getFieldList().get(fieldStart+1).getFieldValue();
		String loginName = context.getFieldList().get(fieldStart+2).getFieldValue();
		String log = context.getFieldList().get(fieldEnd).getFieldValue();
		for(int i = fieldStart+3; i < fieldEnd; i++)
		{
			fields.add(context.getFieldList().get(i));
		}
		process.setDealTime(dealTime);
		process.setDealUser(userName);
		process.setLoginName(loginName);

		//保存DealProcess表
		DealProcessHandler dpHandler = new DealProcessHandler();
		dpHandler.save(context, process, this);
		
		process.setDesc(context.getPreCxtProcess().getDealUser() + "分派给" + userName);
		
		//保存DealProcessLog表
		DealProcessLogHandler dpLogHandler = new DealProcessLogHandler();
		dpLogHandler.save(context, process, log);
		
		//保存ModifyLog表
		ModifyLogHandler mLogHandler = new ModifyLogHandler();
		mLogHandler.save(context, process, fields);
		
		//设置context
		context.setPreCxtProcess(process);
		
		this.preProcess = null;
		
		return context;
	}

}
