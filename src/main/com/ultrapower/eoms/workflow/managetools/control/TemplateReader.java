package com.ultrapower.eoms.workflow.managetools.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.plugin.excelutils.ExcelReader;
import com.ultrapower.eoms.workflow.managetools.control.module.AbstractEntry;
import com.ultrapower.eoms.workflow.managetools.control.template.ImportTemplate;
import com.ultrapower.eoms.workflow.managetools.control.template.TemplateBaseInfor;
import com.ultrapower.eoms.workflow.managetools.control.template.TemplateField;
import com.ultrapower.eoms.workflow.managetools.control.template.TemplateProcess;

public class TemplateReader
{
	private static Map<String, Integer> letterMap;
	
	public ImportTemplate read(ExcelReader reader)
	{
		ImportTemplate template = new ImportTemplate();
		
		//BaseInfor
		TemplateBaseInfor tBaseInfor = new TemplateBaseInfor();
		
		String baseName = reader.readExcelCell(0, 0, 0);
		System.out.println("##-工单名称：" + baseName);
		String baseSchema = reader.readExcelCell(0, 1, 0);
		System.out.println("##-工单类型：" + baseSchema);
		String version = reader.readExcelCell(0, 2, 0);
		System.out.println("##-工单版本：" + version);
		
		tBaseInfor.setBaseName(baseName);
		tBaseInfor.setBaseSchema(baseSchema);
		tBaseInfor.setVersion(version.substring(1));
		template.setBaseInfor(tBaseInfor);
		
		//Process
		Map<String, TemplateProcess> processMap = new HashMap<String, TemplateProcess>();
		List<TemplateProcess> processList = new ArrayList<TemplateProcess>();
		int tmp_i = 3;
		while(true)
		{
			String tip = reader.readExcelCell(0, tmp_i, 0);
			if(tip.equals("*"))
			{
				tmp_i++;
				break;
			}
			TemplateProcess tProccess = new TemplateProcess();
			String phaseNo = StringUtils.checkNullString(reader.readExcelCell(0, tmp_i, 0));
			String fieldStart = StringUtils.checkNullString(reader.readExcelCell(0, tmp_i, 1));
			String fieldEnd = StringUtils.checkNullString(reader.readExcelCell(0, tmp_i, 2));
			String actionName = StringUtils.checkNullString(reader.readExcelCell(0, tmp_i, 3));
			String topPhaseNo = StringUtils.checkNullString(reader.readExcelCell(0, tmp_i, 4));
			String roleCode = StringUtils.checkNullString(reader.readExcelCell(0, tmp_i, 5));
			String roleName = StringUtils.checkNullString(reader.readExcelCell(0, tmp_i, 6));
			String desc = StringUtils.checkNullString(reader.readExcelCell(0, tmp_i, 7));
			
			tProccess.setPhaseNo(phaseNo);
			tProccess.setProcessType("DEFINE");
			if(phaseNo.startsWith("FREE"))
			{
				tProccess.setProcessType("FREE");
			}
			if(fieldStart.equals("#"))
			{
				tProccess.setProcessType("AUTO");
				tProccess.setFieldStart(-1);
				tProccess.setFieldEnd(-1);
				if(fieldEnd.equals("FREE"))
				{
					tProccess.setSubFlowVersion("FREE");
				}
				else
				{
					tProccess.setSubFlowVersion(fieldEnd.substring(1));
				}
			}
			else
			{
				tProccess.setFieldStart(formatExcelColNo(fieldStart));
				tProccess.setFieldEnd(formatExcelColNo(fieldEnd));
			}
			tProccess.setActionName(actionName);
			tProccess.setTopPhaseNo(topPhaseNo);
			tProccess.setRoleCode(roleCode);
			tProccess.setRoleName(roleName);
			tProccess.setDesc(desc);
			
			processMap.put(phaseNo, tProccess);
			processList.add(tProccess);
			
			tmp_i++;
		}
		template.setProcessMap(processMap);
		WorkflowBuilder wfBuilder = new WorkflowBuilder();
		AbstractEntry entry = wfBuilder.build(processList, true);
		template.setEnterPoint(entry);
		
		//Field
		List<TemplateField> fieldList = new ArrayList<TemplateField>();
		int tmp_j = 0;
		while(true)
		{
			String fieldID = reader.readExcelCell(0, tmp_i, tmp_j);
			if(fieldID == null || fieldID.trim().equals(""))
			{
				break;
			}

			String fCode = reader.readExcelCell(0, tmp_i+1, tmp_j);
			
			String fType = "4";
			Map<String, String> dic = null;
			if(!fieldID.equals("A") && !fieldID.equals("B") && !fieldID.equals("C") && !fieldID.equals("Z"))
			{
				String fTypeStr = reader.readExcelCell(0, tmp_i+2, tmp_j);
				if(fTypeStr.length() > 1)
				{
					fType = fTypeStr.substring(0, 1);
					String[] dicArr = fTypeStr.substring(2, fTypeStr.length()-1).split(";");
					dic = new HashMap<String, String>();
					for(String dicStr : dicArr)
					{
						dic.put(dicStr.split("=")[1], dicStr.split("=")[0]);
					}
				}
				else
				{
					fType = fTypeStr;
				}
			}
			String fName = reader.readExcelCell(0, tmp_i+4, tmp_j);
			
			TemplateField field = new TemplateField();
			field.setFieldID(fieldID);
			field.setFieldName(fName);
			field.setFieldCode(fCode);
			field.setFieldType(Integer.parseInt(fType));
			field.setFieldDic(dic);
			
			fieldList.add(field);
			
			tmp_j++;
		}
		template.setFieldList(fieldList);
		
		return template;
	}
	
	private int formatExcelColNo(String colNo)
	{
		if(letterMap == null || letterMap.size() < 26)
		{
			String letterStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
			letterMap = new HashMap<String, Integer>();
			int i = 1;
			for(char letter : letterStr.toCharArray())
			{
				letterMap.put(String.valueOf(letter), i);
				i++;
			}
		}
		
		int number = 0;
		int size = colNo.length();
		if(size == 1)
		{
			number = letterMap.get(colNo);
		}
		else if(size == 2)
		{
			String letter_1 = colNo.substring(0, 1);
			String letter_2 = colNo.substring(1, 2);
			number = letterMap.get(letter_1) * 26;
			number += letterMap.get(letter_2);
		}
		
		return number - 1;
	}
	
	public static void main(String[] args)
	{
		TemplateReader reader = new TemplateReader();
		System.out.println(reader.formatExcelColNo("BC"));
	}
}
