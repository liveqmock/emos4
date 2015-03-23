package com.ultrapower.eoms.workflow.managetools.model;

import java.util.ArrayList;
import java.util.List;

import com.ultrapower.eoms.workflow.managetools.control.context.CxtField;
import com.ultrapower.eoms.workflow.managetools.control.context.CxtProcess;
import com.ultrapower.eoms.workflow.managetools.control.context.ImportContext;
import com.ultrapower.remedy4j.core.RemedyField;
import com.ultrapower.remedy4j.core.RemedyFieldType;
import com.ultrapower.remedy4j.core.RemedySession;

public class ModifyLogHandler
{
	public void save(ImportContext context, CxtProcess process, List<CxtField> _fields)
	{
		RemedySession session = new RemedySession();
		session.login("Demo", RemedySession.UtilInfor.DEMO_PASSWORD);
		
		for(CxtField _field : _fields)
		{
			List<RemedyField> fields = new ArrayList<RemedyField>();
			
			fields.add(new RemedyField("700021001", "BaseID", RemedyFieldType.CHAR, context.getBaseInfor().getBaseID()));
			fields.add(new RemedyField("700021002", "BaseSchema", RemedyFieldType.CHAR, context.getBaseInfor().getBaseSchema()));
			fields.add(new RemedyField("700021003", "PhaseNo", RemedyFieldType.CHAR, process.getPhaseNo()));
			fields.add(new RemedyField("700021004", "FieldID", RemedyFieldType.CHAR, _field.getFieldID().substring(1)));
			fields.add(new RemedyField("700021006", "FieldCode", RemedyFieldType.CHAR, _field.getFieldCode()));
			fields.add(new RemedyField("700021005", "editTime", RemedyFieldType.TIME, String.valueOf(process.getDealTime())));
			fields.add(new RemedyField("700021007", "loginName", RemedyFieldType.CHAR, process.getLoginName()));
			fields.add(new RemedyField("700021008", "fullName", RemedyFieldType.CHAR, process.getDealUser()));
			if(_field.getFieldType() == 7 || _field.getFieldType() == 6)
				fields.add(new RemedyField("700021010", "newValue", RemedyFieldType.CHAR, _field.getSourceValue()));
			else
				fields.add(new RemedyField("700021010", "newValue", RemedyFieldType.CHAR, _field.getFieldValue()));
			fields.add(new RemedyField("700021011", "processType", RemedyFieldType.CHAR, "DEAL"));
			fields.add(new RemedyField("700021012", "viewName", RemedyFieldType.CHAR, _field.getFieldName()));
			fields.add(new RemedyField("700021013", "processID", RemedyFieldType.CHAR, process.getProcessID()));
			fields.add(new RemedyField("700021014", "logID", RemedyFieldType.CHAR, process.getProcessID()));
			fields.add(new RemedyField("700021015", "fieldType", RemedyFieldType.CHAR, String.valueOf(_field.getFieldType())));
			if(_field.getFieldType() == 6)
			{
				StringBuilder dicStr = new StringBuilder();
				for(String key : _field.getFieldDic().keySet())
				{
					dicStr.append(key + "=" + _field.getFieldDic().get(key) + ";");
				}
				fields.add(new RemedyField("700021016", "logID", RemedyFieldType.CHAR, dicStr.toString()));
			}

			fields.add(new RemedyField("8", "desc", RemedyFieldType.CHAR, "IMPORT"));
			session.addEntry(context.getBaseInfor().getBaseSchema() + "_FieldModifyLog", fields);
		}
	}
}
