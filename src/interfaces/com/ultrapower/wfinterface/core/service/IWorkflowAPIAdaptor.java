package com.ultrapower.wfinterface.core.service;

import java.util.List;

import com.ultrapower.wfinterface.core.model.Actor;
import com.ultrapower.wfinterface.core.model.Attachment;
import com.ultrapower.wfinterface.core.model.WorkflowField;

public interface IWorkflowAPIAdaptor
{
	public String doAction(String operateName, String baseID, String baseSchema, String taskID, String dealer, String actionText, List<Actor> actors, List<WorkflowField> inputFields, List<Attachment> attachments);
}
