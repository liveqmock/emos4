package com.ultrapower.eoms.ultrasm.util;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class ExcelStyleUtil
{
	public static String defaultExcelName = "EXCEL导出通用标题";
	public static int defaultWidth = 4000;//默认列宽
	public static String defaultAlign = "left";//默认对齐方式
	
	// 设置导出的EXCEL字体的样式
	public static HSSFCellStyle setStyle(HSSFWorkbook workbook, String cellType, HSSFFont font, String align, String color)
	{
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		// 设置字体
		font.setFontHeightInPoints((short) 9); // 字体高度
		font.setColor(HSSFFont.BOLDWEIGHT_NORMAL); // 字体颜色
		font.setFontName("宋体"); // 字体
		//-----------------------
		/**
		 * 设置表格填充色
		*/
		HSSFPalette palette = workbook.getCustomPalette();
		
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL); // 宽度 (粗体)
		font.setFontHeightInPoints((short) 10);// 字号8
		// font.setItalic( true ); // 是否使用斜体
		// font.setStrikeout(true); // 是否使用划线
		// 设置单元格类型
		cellStyle.setFillBackgroundColor(HSSFColor.AQUA.index);
		cellStyle.setFillPattern(HSSFCellStyle.BIG_SPOTS);
		if(color.equals("LIGHTBLUE"))
			cellStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
		else if(color.equals("LIGHTGREEN"))
			cellStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
		else if(color.equals("GREY"))
			cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		else if(color.equals("ORANGE"))
			cellStyle.setFillForegroundColor(HSSFColor.ORANGE.index);
		else if(color.equals("RED"))
			cellStyle.setFillForegroundColor(HSSFColor.RED.index);
		else
			cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);	
		
//		cellStyle.setFillBackgroundColor(HSSFColor.AQUA.index);
//		cellStyle.setFillPattern(HSSFCellStyle.BIG_SPOTS);
//		cellStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
//		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		cellStyle.setFont(font);
		cellStyle.setWrapText(true);
		//设置单元格对齐方式
		if("center".equals(align))
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);	//水平局中
		else if("right".equals(align))
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);	//右对齐
		else
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);	//右对齐
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);	//垂直局中
		/**
		 * 设置边框线
		 */
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		return cellStyle;
	}
}
