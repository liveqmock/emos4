package com.ultrapower.eoms.common.plugin.excelutils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;


public class ExcelReader {
  public ExcelReader() {
  }

  private InputStream excelfile = null;
  private HSSFWorkbook workbook = null;

  /**
   * 给定文件所在的路径，以备读取
   * @param path String
   */
  public ExcelReader(String path) {
    try {
      excelfile = new FileInputStream(path);
      //POIFSFileSystem fs = new POIFSFileSystem(excelfile);
      workbook = new HSSFWorkbook(excelfile);
    }
    catch (FileNotFoundException e) {
      ;
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }

  }

  /**
   * 直接给定输入流，然后进行文件的读取
   * @param in InputStream
   */
  public ExcelReader(InputStream in) {
    try {
      excelfile = in;
      workbook = new HSSFWorkbook(excelfile);
    }
    catch (FileNotFoundException e) {
      ;
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    finally {
      if (excelfile != null) {
        try {
          excelfile.close();
        }
        catch (Exception e) {}
      }
    }

  }

  /**
   * 读文件操作，指定工作区，行号，列号
   * @param sheetnum int  第几个Sheet，从0开始计算
   * @param rownum int    行号，从0开始计算
   * @param cellnum int   列号，从0开始计算
   * @return String       读到的内容
   */
  public String readExcelCell(int sheetnum, int rownum, int cellnum) {
    int type;
    String value = null;
    HSSFRow row = null;
    HSSFCell cell = null;

    HSSFSheet sheet = workbook.getSheetAt(sheetnum);
    row = sheet.getRow(rownum);
    cell = row.getCell( (short) cellnum);
    if (cell == null) {
      return "";
    }
    type = cell.getCellType();
    HSSFCellStyle cellStyle = cell.getCellStyle();
	int dataFormat = cellStyle.getDataFormat();
    switch (type) {
      case Cell.CELL_TYPE_STRING:
        value = cell.getStringCellValue();
        break;
      case Cell.CELL_TYPE_NUMERIC:
        if (dataFormat == 24 || dataFormat == 183) {
          value = "$" + String.valueOf(cell.getNumericCellValue());
        }
        else if (dataFormat == 5 || dataFormat == 6 || dataFormat == 7 ||
                 dataFormat == 8) {
          value = "￥" + String.valueOf(cell.getNumericCellValue());
        }
        else if (dataFormat == 0) {
          value = String.valueOf( (int) cell.getNumericCellValue());
        }
        else if (dataFormat == 14) {
          SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
          value = dateFormat.format(cell.getDateCellValue());
        } else if (dataFormat == 177 || dataFormat == 176) {
        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            value = dateFormat.format(cell.getDateCellValue());
		} else if (dataFormat == 22) {
        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            value = dateFormat.format(cell.getDateCellValue());
		}
        else {
          value = String.valueOf( (int) cell.getNumericCellValue());

        }
        break;
      case Cell.CELL_TYPE_BOOLEAN:
        value = String.valueOf(cell.getBooleanCellValue());
        break;
      case Cell.CELL_TYPE_BLANK:
        value = "";
        break;
      default:
        value = cell.getStringCellValue();
        break;
    }
    return value;

  }

  /**
   * 给定工作区和行号，返回一行的数据的一个List
   * @param sheetNum int  工作区号
   * @param rowNum int    行号
   * @return List         结果集
   */
  public List readExcelRow(int sheetNum, int rowNum) {
    List data = new ArrayList();
    int cols = getColNums(sheetNum);
    for (int i = 0; i < cols; i++) {
      String temp = readExcelCell(0, rowNum, i);
      data.add(temp);
    }
    return data;
  }

  /**
   * 根据Excel文件的第一行标题部分的内容，造一个Map，Map的Key是Excel的标题，Value为要读取的行
   * @param sheetNum int     工作区号
   * @param rowNum int       行号
   * @return Map             以标题为Key， 内容为Value的Map
   */
  public Map readExcelMap(int sheetNum, int rowNum) {
    Map data = new HashMap();
    int cols = getColNums(sheetNum);
    for (int i = 0; i < cols; i++) {
      String key = readExcelCell(sheetNum, 0, i);
      String value = readExcelCell(sheetNum, rowNum, i);
      data.put(key, value);
    }
    return data;
  }

  /**
   * 文件关闭的操作
   */
  public void close() {
    if (excelfile != null) {
      try {
        excelfile.close();
      }
      catch (Exception e) {} //此处可不进行处理
    }
  }

  /**
   * 得到Excel文件的行数
   * @param sheetnum int
   * @return int
   */
  public int getRowNums(int sheetnum) {
    HSSFSheet sheet = workbook.getSheetAt(sheetnum);
    return sheet.getLastRowNum() + 1;
  }

  /**
   * 得到Excel文件的列数
   * @param sheetnum int
   * @return int
   */
  public int getColNums(int sheetnum) {
    HSSFSheet sheet = workbook.getSheetAt(sheetnum);
    HSSFRow row = sheet.getRow(0);
    return row.getLastCellNum();
  }

  public static void main(String[] args) {
    ExcelReader read = new ExcelReader("c:\\test.xls");
    
//    String a = read.readExcelCell(0, 0, 0);
//    String b = read.readExcelCell(0, 0, 1);
//    String c = read.readExcelCell(0, 1, 0);
    String d = read.readExcelCell(0, 1, 1);
//    ;
//    ;
    ;
    
    int cols = read.getColNums(0);
    int rows = read.getRowNums(0);
    ;
  }
}
