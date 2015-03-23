package com.ultrapower.eoms.workflow.managetools.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.ultrapower.eoms.common.core.util.TimeUtils;
import com.ultrapower.eoms.common.plugin.excelutils.ExcelReader;
import com.ultrapower.eoms.workflow.managetools.control.context.CxtBaseInfor;
import com.ultrapower.eoms.workflow.managetools.control.context.CxtField;
import com.ultrapower.eoms.workflow.managetools.control.context.ImportContext;
import com.ultrapower.eoms.workflow.managetools.control.template.ImportTemplate;
import com.ultrapower.eoms.workflow.managetools.control.template.TemplateField;
import com.ultrapower.eoms.workflow.managetools.service.BaseImportService;

public class BaseImportXmlImpl implements BaseImportService
{
	private Random ran = new Random(System.currentTimeMillis());
	public void importBase(ImportTemplate _template, List<List<String>> _dataList)
	{
		try
		{
			ImportTemplate template = _template;
			List<List<String>> dataList = _dataList;
			
			int count = 1;
			for(List<String> row : dataList)
			{
				ImportContext context = new ImportContext();
				context.setEntryType("MAIN-DEFINE");
				CxtBaseInfor cxtBaseInfor = new CxtBaseInfor();
				cxtBaseInfor.setBaseName(template.getBaseInfor().getBaseName());
				cxtBaseInfor.setBaseSchema(template.getBaseInfor().getBaseSchema());
				cxtBaseInfor.setVersion(template.getBaseInfor().getVersion());
				context.setVersion(template.getBaseInfor().getVersion());
				context.setBaseInfor(cxtBaseInfor);
				context.setFieldList(buildFieldList(template.getFieldList(), row));
				context.setPreCxtProcess(null);
				
				try
				{
					template.getEnterPoint().signal(context);
				}
				catch (Exception e)
				{
					System.out.println("出错的行为：" + count);
					e.printStackTrace();
				}
				count++;
			}
			
		}
		catch (Exception e)
		{
			System.out.println("ERROR!ERROR!ERROR!ERROR!ERROR!ERROR!");
			e.printStackTrace();
		}
	}
	
	private List<CxtField> buildFieldList(List<TemplateField> fieldList, List<String> row)
	{
		List<CxtField> valueList = new ArrayList<CxtField>();
		for(int i = 0; i < fieldList.size(); i++)
		{
			TemplateField tField = fieldList.get(i);
			CxtField field = new CxtField();
			field.setFieldID(tField.getFieldID());
			field.setFieldName(tField.getFieldName());
			field.setFieldType(tField.getFieldType());
			field.setFieldCode(tField.getFieldCode());
			String value = row.get(i);
			value = value.trim();
			if(field.getFieldType() == 7)
			{
				if(value != null && !value.equals(""))
				{
					//int seed = ran.nextInt(240);
					int seed = 0;
					field.setFieldValue(String.valueOf(TimeUtils.formatDateStringToInt(row.get(i)) + seed));
				}
			}
			else if(field.getFieldType() == 6)
			{
				if(value != null && !value.equals(""))
				{
					field.setFieldValue(tField.getFieldDic().get(row.get(i)));
				}
			}
			else if(field.getFieldID().equals("A"))
			{
				field.setFieldValue(String.valueOf(TimeUtils.formatDateStringToInt(row.get(i))));
			}
			else
			{
				field.setFieldValue(value);
			}
			field.setFieldDic(tField.getFieldDic());
			field.setSourceValue(value);
			
			valueList.add(field);
		}
		return valueList;
	}

	public static void main(String[] args)
	{
		/*
		File excel = new File("G:\\模板,整理好\\故障\\故障工单二级处理完成.xls");
		
		ExcelReader reader;
		try
		{
			reader = new ExcelReader(new FileInputStream(excel));
			
			TemplateReader tReader = new TemplateReader();
			ImportTemplate template = tReader.read(reader);
			
			DataReader dReader = new DataReader();
			List<List<String>> dataList = dReader.read(reader, template.getProcessMap().size()+9, template.getFieldList().size());
			
			reader.close();
			
			//BaseImportService service = new BaseImportXmlImpl();
			//service.importBase(template, dataList);
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
}
