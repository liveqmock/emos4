package com.ultrapower.eoms.ultrabpp.runtime.extend.UBP_TEST_FIX;

import java.util.ArrayList;
import java.util.List;

import com.ultrapower.eoms.ultrabpp.cache.model.ActionModel;
import com.ultrapower.eoms.ultrabpp.runtime.model.WorksheetCommitContext;
import com.ultrapower.eoms.ultrabpp.runtime.model.WorksheetDisplayContext;
import com.ultrapower.eoms.ultrabpp.runtime.service.ExtendFunction;
import com.ultrapower.workflow.engine.task.model.ProcessTask;

public class Test2FixExtendFunction implements ExtendFunction
{

	public void beginOpen(WorksheetDisplayContext displayCxt)
	{
		System.out.println("this beginOpen");
	}

	public void commitPhase1(WorksheetCommitContext commitCxt)
	{
		// TODO Auto-generated method stub
		String tmpStatus = commitCxt.getFieldMap().get("BASESTATUS");
		if(commitCxt.getCurrentTask()==null||commitCxt.getCurrentTask().getFlagActive()!=1)
		{
			return;
		}
		if("审批中".equals(tmpStatus)&&"AUDITINGPASS".equals(commitCxt.getActionID()))
		{
			commitCxt.getFieldMap().put("NextDefAssigne", commitCxt.getAssignString().replace("AUDITINGPASS", "NEXT"));
		}
		
		if("处理中".equals(tmpStatus)&&"FINISH".equals(commitCxt.getActionID()))
		{
			
			if(commitCxt.getCurrentTask()!=null){
				ProcessTask cTask = commitCxt.getCurrentTask(); 
				String stepNo = cTask.getStepNo();
				if(stepNo.equals("step043")&&commitCxt.getAssignString().length()>0){ 
					commitCxt.getFieldMap().put("NextDefAssigne", commitCxt.getAssignString().replace("FINISH", "ASSIGN"));
				}
				if(stepNo.equals("step042")&&commitCxt.getAssignString().length()>0){ 
					commitCxt.getFieldMap().put("NextDefAssigne", commitCxt.getAssignString().replace("FINISH", "NEXT"));
				}
			}
		}
		
		System.out.println("this commitPhase1");
	}

	public void commitPhase2(WorksheetCommitContext commitCxt)
	{
		
		// TODO Auto-generated method stub
		System.out.println("this commitPhase2");
	}

	public void commitPhase3(WorksheetCommitContext commitCxt)
	{
		// TODO Auto-generated method stub
		System.out.println("this commitPhase3");
	}

	public void endOpen(WorksheetDisplayContext displayCxt)
	{
		String tmpStatus = displayCxt.getFieldMap().get("BASESTATUS");
    	if(displayCxt.getCurrentTask() == null || displayCxt.getCurrentTask().getFlagActive() != 1)
		{
			return;
		}
    	String stepNo = displayCxt.getCurrentTask().getStepNo();
    	if(("step042").equals(stepNo)||("step043").equals(stepNo)){
    		List<ActionModel> freeAction = new ArrayList<ActionModel>();
    		freeAction.add(new ActionModel("FINISH", "FINISH", "完成", "完成", 1, 1, 1,0));
    		displayCxt.setFreeActionList(freeAction);
    	}
    	if(("step0411").equals(stepNo)||("step0433").equals(stepNo)){
    		List<ActionModel> freeAction = new ArrayList<ActionModel>();
    		freeAction.add(new ActionModel("FINISH", "FINISH", "完成", "完成",0, 1, 1,0));
    		displayCxt.setFreeActionList(freeAction);
    	}
    	if(("step051").equals(stepNo)){
    		List<ActionModel> freeAction = new ArrayList<ActionModel>();
    		freeAction.add(new ActionModel("FINISH", "FINISH", "完成", "完成",0, 1, 1,0));
    		displayCxt.setFreeActionList(freeAction);
    	}
    	
		System.out.println("this endOpen");
	}

}
