package com.ultrapower.eoms.workflow.managetools.control;

import java.util.ArrayList;
import java.util.List;

import com.ultrapower.eoms.workflow.managetools.control.module.AbstractEntry;
import com.ultrapower.eoms.workflow.managetools.control.module.AbstractProcess;
import com.ultrapower.eoms.workflow.managetools.control.module.AutoProcess;
import com.ultrapower.eoms.workflow.managetools.control.module.DefProcess;
import com.ultrapower.eoms.workflow.managetools.control.module.FreeProcess;
import com.ultrapower.eoms.workflow.managetools.control.module.MainEntry;
import com.ultrapower.eoms.workflow.managetools.control.module.SubEntry;
import com.ultrapower.eoms.workflow.managetools.control.template.TemplateProcess;

public class WorkflowBuilder
{
	private int i = 0;
	
	private List<TemplateProcess> processList;
	
	private List<AbstractProcess> processes;
	
	public AbstractEntry build(List<TemplateProcess> _processList, boolean mainEntry)
	{
		processes = new ArrayList<AbstractProcess>();
		AbstractEntry entry = null;
		if(mainEntry)
		{
			entry = new MainEntry();
			entry.setEntryType("MAIN");
		}
		else
		{
			entry = new SubEntry();
			entry.setEntryType("SUB");
		}
		
		processList = _processList;
		buildProcess(null);
		
		entry.setProcesses(processes);
		return entry;
	}
	
	private void buildProcess(AbstractProcess preProcess)
	{
		if(i == processList.size())
		{
			return;
		}
		TemplateProcess tProcess = processList.get(i);
		
		AbstractProcess process = null;
		
		if(tProcess.getProcessType().equals("DEFINE"))
		{
			process = new DefProcess();
			process.buildProcess(tProcess);
			process.setPreProcess(preProcess);
			
		}
		else if(tProcess.getProcessType().equals("FREE"))
		{
			process = new FreeProcess();
			process.buildProcess(tProcess);
			process.setPreProcess(preProcess);
			
		}
		else
		{
			AutoProcess autoProcess = new AutoProcess();
			autoProcess.buildProcess(tProcess);
			autoProcess.setPreProcess(preProcess);
			autoProcess.setSubEntry(buildSubEntry(autoProcess));
			process = autoProcess;
		}

		processes.add(process);
		
		i++;
		buildProcess(process);
	}
	
	private AbstractEntry buildSubEntry(AutoProcess process)
	{
		WorkflowBuilder wfbuilder = new WorkflowBuilder();
		
		List<TemplateProcess> _processList = new ArrayList<TemplateProcess>();
		
		i++;
		while(true)
		{
			TemplateProcess tProcess = processList.get(i);
			if(tProcess.getTopPhaseNo().equals("dp_0"))
			{
				i--;
				break;
			}
			_processList.add(tProcess);
			i++;
		}
		
		AbstractEntry subEntry = wfbuilder.build(_processList, false);
		subEntry.setVersion(process.getSubFlowVersion());
		
		return subEntry;
	}
}
