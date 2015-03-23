package com.ultrapower.eoms.workflow.managetools.control;

import java.util.ArrayList;
import java.util.List;

import com.ultrapower.eoms.common.core.util.StringUtils;
import com.ultrapower.eoms.common.plugin.excelutils.ExcelReader;

public class DataReader
{
	public List<List<String>> read(ExcelReader reader, int startRow, int endCol)
	{
		int row_i = startRow;
		List<List<String>> rowList = new ArrayList<List<String>>();
		while(true)
		{
			if(reader.getRowNums(0) == row_i)
			{
				break;
			}
			String tip = reader.readExcelCell(0, row_i, 0);
			if(tip == null || tip.trim().equals(""))
			{
				break;
			}
			
			List<String> colList = new ArrayList<String>();
			for(int i = 0; i < endCol; i++)
			{
				String value = reader.readExcelCell(0, row_i, i);
				if(value != null && value.length() > 5 && value.substring(0, 1).equals("#"))
				{
					value = value.substring(1);
				}
				colList.add(StringUtils.checkNullString(value));
			}
			rowList.add(colList);
			row_i++;
		}
		
		return rowList;
	}
}
