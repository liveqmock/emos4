package com.ultrapower.eoms.common.plugin.excelutils;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

public class PoiList {

	private String getMarginValue(HSSFSheet sheet, int rowNum, int cellNum) {
		for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
			Region r = sheet.getMergedRegionAt(i);
			if (r == null)
				break;
			if (r.getRowFrom() <= rowNum && r.getRowTo() >= rowNum
					&& r.getColumnFrom() <= cellNum
					&& r.getColumnTo() >= cellNum) {
				HSSFCell cell = sheet.getRow(r.getRowFrom()).getCell(
						r.getColumnFrom());
				if (cell != null) {
					if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
						return getDateFormatValue(cell);
					return cell.toString();
				}
			}
		}

		return "";
	}

	private String getDateFormatValue(HSSFCell cell) {
		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC
				&& DateUtil.isCellDateFormatted(cell)) {
			double d = cell.getNumericCellValue();
			Date date = DateUtil.getJavaDate(d);
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			return formatter.format(date);
		}

		return cell.toString();

	}

	public Object[] getPoiList(String fileToBeRead) {
		Object[] sheets = new Object[] {};
		int sheetNum;

		try {
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(
					fileToBeRead));
			sheetNum = workbook.getNumberOfSheets();
			sheets = new Object[sheetNum];
			for (int i = 0; i < sheets.length; i++) {
				List<Map> list = new ArrayList<Map>();
				HSSFSheet sheet = workbook.getSheetAt(i);

				for (int j = 1; j <= sheet.getLastRowNum(); j++) {
					Map<String, String> map = new HashMap<String, String>();
					HSSFRow row = sheet.getRow(j);
					if (row == null)
						continue;
					for (int z = 0; z <= row.getLastCellNum(); z++) {
						String keyName = "";
						if (sheet.getRow(0) != null
								&& sheet.getRow(0).getCell(z) != null)
							keyName = sheet.getRow(0).getCell(z).toString();
						String keyValue = "";
						HSSFCell cell = row.getCell(z);
						if (cell != null) {
							if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
								keyValue = getDateFormatValue(cell);
							else
								keyValue = cell.toString();
							if (keyValue.equals(""))
								keyValue = getMarginValue(sheet, j, z);

						}
						map.put(keyName, keyValue);
					}
					list.add(map);
				}
				sheets[i] = list;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return sheets;

	}
}
