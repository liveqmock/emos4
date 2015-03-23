package com.ultrapower.eoms.workflow.managetools.model;

import java.util.ArrayList;
import java.util.List;

import com.ultrapower.eoms.workflow.managetools.control.context.CxtField;
import com.ultrapower.eoms.workflow.managetools.control.context.ImportContext;
import com.ultrapower.remedy4j.core.RemedyField;
import com.ultrapower.remedy4j.core.RemedyFieldType;
import com.ultrapower.remedy4j.core.RemedySession;

public class BaseHandler
{
	public void save(ImportContext context)
	{
		List<RemedyField> fields = new ArrayList<RemedyField>();
		
		for(CxtField field : context.getFieldList())
		{
			if(!field.getFieldID().equals("A")
					&& !field.getFieldID().equals("B")
					&& !field.getFieldID().equals("C")
					&& !field.getFieldID().equals("Z"))
			{
				RemedyField rField = new RemedyField(field.getFieldID().substring(1), field.getFieldName(), field.getFieldType(), field.getFieldValue());
				fields.add(rField);
			}
		}
		fields.add(new RemedyField("700000001", "baseSchema", RemedyFieldType.CHAR, context.getBaseInfor().getBaseSchema()));
		fields.add(new RemedyField("700000002", "baseName", RemedyFieldType.CHAR, context.getBaseInfor().getBaseName()));
		fields.add(new RemedyField("700000022", "tplBaseID", RemedyFieldType.CHAR, context.getVersion()));
		fields.add(new RemedyField("700000004", "creatorFullName", RemedyFieldType.CHAR, context.getFieldList().get(1).getFieldValue()));
		fields.add(new RemedyField("700000005", "creatorLoginName", RemedyFieldType.CHAR, context.getFieldList().get(2).getFieldValue()));
		fields.add(new RemedyField("700000006", "createTime", RemedyFieldType.TIME, String.valueOf(context.getBaseInfor().getCreateTime())));
		fields.add(new RemedyField("700000009", "closeTime", RemedyFieldType.TIME, String.valueOf(context.getBaseInfor().getCloseTime())));
		fields.add(new RemedyField("700000010", "baseStatus", RemedyFieldType.CHAR, "已归档"));
		fields.add(new RemedyField("700000044", "StartNo", RemedyFieldType.INTEGER, String.valueOf(context.getBaseInfor().getCreateTime())));
		fields.add(new RemedyField("700000047", "EndNo", RemedyFieldType.INTEGER, String.valueOf(context.getBaseInfor().getCloseTime())));
		fields.add(new RemedyField("710000001", "entryID", RemedyFieldType.CHAR, context.getEntryID()));
		fields.add(new RemedyField("700000041", "wfflag", RemedyFieldType.INTEGER, "1"));
		fields.add(new RemedyField("700000030", "BaseIsTrueClose", RemedyFieldType.INTEGER, "1"));
		
		RemedySession session = new RemedySession();
		session.login("Demo", RemedySession.UtilInfor.DEMO_PASSWORD);
		fields.add(new RemedyField("8", "desc", RemedyFieldType.CHAR, "IMPORT"));
		String baseID = session.addEntry(context.getBaseInfor().getBaseSchema(), fields);
		context.getBaseInfor().setBaseID(baseID);
	}
}
