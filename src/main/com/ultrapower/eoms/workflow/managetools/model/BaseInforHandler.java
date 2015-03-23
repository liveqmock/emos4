package com.ultrapower.eoms.workflow.managetools.model;

import java.util.ArrayList;
import java.util.List;

import com.ultrapower.eoms.workflow.managetools.control.context.CxtField;
import com.ultrapower.eoms.workflow.managetools.control.context.ImportContext;
import com.ultrapower.remedy4j.core.RemedyField;
import com.ultrapower.remedy4j.core.RemedyFieldType;
import com.ultrapower.remedy4j.core.RemedySession;

public class BaseInforHandler
{
	public void save(ImportContext context)
	{
		List<RemedyField> fields = new ArrayList<RemedyField>();
		
		fields.add(new RemedyField("700000000", "BaseID", RemedyFieldType.CHAR, context.getBaseInfor().getBaseID()));
		fields.add(new RemedyField("700000001", "BaseSchema", RemedyFieldType.CHAR, context.getBaseInfor().getBaseSchema()));
		fields.add(new RemedyField("700000002", "BaseName", RemedyFieldType.CHAR, context.getBaseInfor().getBaseName()));
		fields.add(new RemedyField("700000003", "BaseSN", RemedyFieldType.CHAR, context.getBaseInfor().getSerialNo()));
		fields.add(new RemedyField("700000004", "CreatorUserFullName", RemedyFieldType.CHAR, context.getFieldList().get(1).getFieldValue()));
		fields.add(new RemedyField("700000005", "CreatorUserLoginName", RemedyFieldType.CHAR, context.getFieldList().get(2).getFieldValue()));
		fields.add(new RemedyField("700000006", "CreateTime", RemedyFieldType.TIME, String.valueOf(context.getBaseInfor().getCreateTime())));
		fields.add(new RemedyField("700000009", "CloseTime", RemedyFieldType.TIME, String.valueOf(context.getBaseInfor().getCloseTime())));
		fields.add(new RemedyField("710000001", "EntryID", RemedyFieldType.CHAR, context.getEntryID()));
		fields.add(new RemedyField("700000022", "TplID", RemedyFieldType.CHAR, context.getVersion()));
		
		String baseSummary = "";
		String baseItem = "";
		String basePriority = "";
		String acceptOutTime = "";
		String dealOutTime = "";
		String closeSatisfy = "";
		
		for(CxtField field : context.getFieldList())
		{
			if(field.getFieldID().equals("C700000011")) baseSummary = field.getFieldValue();
			else if(field.getFieldID().equals("C700000014")) baseItem = field.getFieldValue();
			else if(field.getFieldID().equals("C700000015")) basePriority = field.getFieldValue();
			else if(field.getFieldID().equals("C700000017")) acceptOutTime = field.getFieldValue();
			else if(field.getFieldID().equals("C700000018")) dealOutTime = field.getFieldValue();
			else if(field.getFieldID().equals("C700000021")) closeSatisfy = field.getFieldValue();
		}
		
		if(baseSummary != null) fields.add(new RemedyField("700000011", "baseSummary", RemedyFieldType.CHAR, baseSummary));
		if(baseItem != null) fields.add(new RemedyField("700000014", "baseItem", RemedyFieldType.CHAR, baseItem));
		if(basePriority != null) fields.add(new RemedyField("700000015", "basePriority", RemedyFieldType.CHAR, basePriority));
		if(acceptOutTime != null) fields.add(new RemedyField("700000017", "acceptOutTime", RemedyFieldType.TIME, acceptOutTime));
		if(dealOutTime != null) fields.add(new RemedyField("700000018", "dealOutTime", RemedyFieldType.TIME, dealOutTime));
		if(closeSatisfy != null) fields.add(new RemedyField("700000021", "closeSatisfy", RemedyFieldType.CHAR, closeSatisfy));
		
		fields.add(new RemedyField("700000016", "CloseGroup", RemedyFieldType.ENUM, "0"));
		fields.add(new RemedyField("700000044", "StartNo", RemedyFieldType.INTEGER, String.valueOf(context.getBaseInfor().getCreateTime())));
		fields.add(new RemedyField("700000047", "EndNo", RemedyFieldType.INTEGER, String.valueOf(context.getBaseInfor().getCloseTime())));

		fields.add(new RemedyField("700000010", "BaseStatus", RemedyFieldType.CHAR, "已归档"));
		fields.add(new RemedyField("710000001", "entryID", RemedyFieldType.CHAR, context.getEntryID()));
		fields.add(new RemedyField("700000022", "version", RemedyFieldType.CHAR, context.getVersion()));
		fields.add(new RemedyField("700000041", "wfflag", RemedyFieldType.INTEGER, "1"));
		
		RemedySession session = new RemedySession();
		session.login("Demo", RemedySession.UtilInfor.DEMO_PASSWORD);
		fields.add(new RemedyField("8", "desc", RemedyFieldType.CHAR, "IMPORT"));
		session.addEntry("WF4:App_Base_Infor", fields);
	}
}
