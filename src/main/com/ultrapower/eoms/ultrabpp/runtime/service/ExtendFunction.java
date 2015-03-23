package com.ultrapower.eoms.ultrabpp.runtime.service;

import com.ultrapower.eoms.ultrabpp.runtime.model.WorksheetCommitContext;
import com.ultrapower.eoms.ultrabpp.runtime.model.WorksheetDisplayContext;

public interface ExtendFunction
{
	public void beginOpen(WorksheetDisplayContext displayCxt);
	
	public void endOpen(WorksheetDisplayContext displayCxt);
	
	public void commitPhase1(WorksheetCommitContext commitCxt);
	
	public void commitPhase2(WorksheetCommitContext commitCxt);
	
	public void commitPhase3(WorksheetCommitContext commitCxt);
}
