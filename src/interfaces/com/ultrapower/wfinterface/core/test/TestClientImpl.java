package com.ultrapower.wfinterface.core.test;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.wfinterface.core.model.WfiDataout;
import com.ultrapower.wfinterface.core.service.AbsWorkflowClient;

public class TestClientImpl extends AbsWorkflowClient
{

	@Override
	protected boolean dataException(String errorString, Exception ex)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean dataHandle(WfiDataout clientContext, DataRow row)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean init(WfiDataout clientContext, DataRow row)
	{
		// TODO Auto-generated method stub
		return false;
	}

	public TestClientImpl()
	{
		dataTable = new DataTable("");
		DataRow row1 = new DataRow(); row1.put("field1", "a1"); dataTable.putDataRow(row1);
		DataRow row2 = new DataRow(); row2.put("field1", "b2"); dataTable.putDataRow(row2);
		DataRow row3 = new DataRow(); row3.put("field1", "c3"); dataTable.putDataRow(row3);
	}
}
