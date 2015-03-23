package com.ultrapower.eoms.common.plugin.ecside.easyda;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ultrapower.eoms.common.plugin.ecside.common.log.LogHandler;
import com.ultrapower.eoms.common.plugin.ecside.util.ExportViewUtils;

public class DataAccessUtil {
	
	private static Log logger = LogFactory.getLog(DataAccessUtil.class);


	public static void buildRecordList(ResultSet resultSet,String[] columnName,List list) throws SQLException{
		for (int i=0;i<columnName.length;i++){
			list.add(resultSet.getString(columnName[i]));
		}
	}

	public static void buildRecordMap(ResultSet resultSet,String[] columnName,Map map) throws SQLException{
		for (int i=0;i<columnName.length;i++){
			map.put(columnName[i],resultSet.getString(columnName[i]));
		}
	}
	
	public static void buildRecordMap(ResultSet resultSet,Map map) throws SQLException{
		String[] columnName=getColumnNames(resultSet);
		for (int i=0;i<columnName.length;i++){
			map.put(columnName[i],resultSet.getString(columnName[i]));
		}
	}
	
	public static HashMap resultSetToMap(ResultSet resultSet) throws SQLException{
		HashMap map=new HashMap();
		String[] columnName=getColumnNames(resultSet);
		for (int i=0;i<columnName.length;i++){
			map.put(columnName[i],resultSet.getString(columnName[i]));
		}
		return map;
	}

	public static int[] getColumnTypes(ResultSet resultSet) throws SQLException{
		ResultSetMetaData metaData = resultSet.getMetaData();
		int cols = metaData.getColumnCount();
		int[] columnType=new int[cols];
		for (int i=0;i<columnType.length;i++){
			columnType[i]=metaData.getColumnType(i+1);
		}
		return columnType;
	}

	public static String[] getColumnNames(ResultSet resultSet) throws SQLException{
		ResultSetMetaData metaData = resultSet.getMetaData();
		int cols = metaData.getColumnCount();
		String[] columnName=new String[cols];
		for (int i=0;i<columnName.length;i++){
			columnName[i]=metaData.getColumnName(i+1).toUpperCase();
		}
		return columnName;
	}


	public static void outputText(ResultSet rest,OutputStream outputStream,Map mappingItems){
		DataAccessUtil.outputText(rest,outputStream,"\t", null,null ,mappingItems);
	}

	public static void outputText(ResultSet rest,OutputStream outputStream,String delimiter,Map mappingItems){
		DataAccessUtil.outputText(rest,outputStream,delimiter,null, null ,mappingItems);
	}

	public static void outputText(ResultSet rest,OutputStream outputStream,String[] titles,Map mappingItems){
		DataAccessUtil.outputText(rest,outputStream,"\t",null, titles ,mappingItems);
	}
	public static void outputText(ResultSet rest,OutputStream outputStream,String delimiter,String[] titles,Map mappingItems){
		DataAccessUtil.outputText(rest,outputStream,delimiter,null, titles ,mappingItems);
	}
	
	/////////////////////////////////////////////////////
	
	
	public static short createTextHeader(String[] titlesColl,PrintWriter out,String delimiter) throws WriteException, IOException{
		if (titlesColl==null || titlesColl.length<1){
			return 0;
		}
		for (int i=0;i<titlesColl.length;i++){
			String value=titlesColl[i];
			value=value==null?delimiter:value.trim()+delimiter;
			out.write(value);
		}
		out.write(ExportViewUtils.BR);
		return 1;
	}
	
	public static void outputText(ResultSet rest,OutputStream outputStream,String delimiter,String[] columnNames, String[] titlesColl ,Map mappingItems){
		PrintWriter out=null;
		try {
			if (mappingItems==null){
				mappingItems=new HashMap();
			}
			columnNames=columnNames==null?getColumnNames(rest):columnNames;
			titlesColl=titlesColl==null?columnNames:titlesColl;
			
			out=new PrintWriter(outputStream);
			
			createTextHeader(titlesColl, out, delimiter);
			int colNum=columnNames.length;
			while (rest.next()) {
				for (int i=0;i<colNum;i++){
					String value=rest.getString(1+i);
					
					Map mappingItem=(Map)mappingItems.get(columnNames[i]);
	
					if (mappingItem!=null){
						value=convertString(((String)mappingItem.get(value)),null);
					}
					
					value=value==null?delimiter:value.trim()+delimiter;
					out.write(value);
					value=null;
				}
				out.write(ExportViewUtils.BR);
			}
		} catch (SQLException e) {
			LogHandler.errorLog(logger, e);
		} catch (Exception e) {
			LogHandler.errorLog(logger, e);
		}finally{
			if (out!=null){
				out.flush();
				out.close();
			}
		}
	}
	

	
	public static short createCSVHeader(String[] titles,PrintWriter out) throws WriteException, IOException{
		if (titles==null || titles.length<1){
			return 0;
		}
		for (int i=0;i<titles.length;i++){
			String value=titles[i];
			value=ExportViewUtils.parseCSV(value);
			out.write(value);
			if (i<titles.length-1){
				out.write(ExportViewUtils.COMMA);
			}
		}
		out.write(ExportViewUtils.BR);
		return 1;
	}
	
	public static void outputCSV(ResultSet rest,OutputStream outputStream,String[] titles ,Map mappingItems){
		PrintWriter out=null;
		try {
			if (mappingItems==null){
				mappingItems=new HashMap();
			}
			String[] columnNames= getColumnNames(rest);
			titles=titles==null?columnNames:titles;
			
			out=new PrintWriter(outputStream);
			
			createCSVHeader(titles, out);
			int colNum=columnNames.length;
			while (rest.next()) {
				for (int i=0;i<colNum;i++){
					String value=rest.getString(i+1);
					
					Map mappingItem=(Map)mappingItems.get(columnNames[i]);
	
					if (mappingItem!=null){
						value=convertString(((String)mappingItem.get(value)),null);
					}
					
					value=ExportViewUtils.parseCSV(value);
					out.write(value);
					if (i<colNum-1){
						out.write(ExportViewUtils.COMMA);
					}
					
					value=null;
				}
				out.write(ExportViewUtils.BR);
			}
		} catch (SQLException e) {
			LogHandler.errorLog(logger, e);
		} catch (Throwable e) {
			LogHandler.warnLog(logger, e);
		}finally{
			if (out!=null){
				out.flush();
				out.close();
			}
		}
	}
	
	public static void outputXLS(ResultSet rest,OutputStream outputStream,String[] titles ,Map mappingItems){
		int sheetSize=50000;
		try {
			if (mappingItems==null){
				mappingItems=new HashMap();
			}
			String[] columnNames= getColumnNames(rest);
			titles=titles==null?columnNames:titles;
			
			WritableWorkbook workbook = Workbook.createWorkbook(outputStream);	
			
			int rowNum=1;
			int sheetNum=1;
			
			WritableSheet sheet=createSheet(workbook,sheetNum);
			buildExcelHeader(sheet,titles);

			while (rest.next()) {
				for (int i=0;i<columnNames.length;i++){
					String value=rest.getString(i+1);
					Map mappingItem=(Map)mappingItems.get(columnNames[i]);
					if (mappingItem!=null){
						value=convertString(((String)mappingItem.get(value)),null);
					}
					Label label = new Label (i,rowNum++,value);
					sheet.addCell(label);
					value=null;
				}
				if (rowNum>sheetSize){
					rowNum=1;
					sheet=createSheet(workbook,++sheetNum);
					buildExcelHeader(sheet,titles);
				}
			}
			workbook.write();
			workbook.close();
		} catch (Exception e) {
			LogHandler.errorLog(logger, e);
		}
	}

	public static void buildExcelHeader(WritableSheet sheet, String[] headerName) throws RowsExceededException, WriteException{
		for (short i=0;i<headerName.length;i++){
			Label label = new Label (i,0,headerName[i]);
			sheet.addCell(label); 
		}
	}

	public static void buildExcelRow(WritableSheet sheet, int rowNum,ResultSet resultSet) throws RowsExceededException, WriteException, SQLException{
		ResultSetMetaData metaData = resultSet.getMetaData();
		int cols = metaData.getColumnCount();
		for (short i=0;i<cols;i++){
			Label label = new Label (i,rowNum,resultSet.getString(i+1));
			sheet.addCell(label); 
		}
	}
	
	public static WritableSheet createSheet(WritableWorkbook workbook,int sheetNum){
		WritableSheet sheet = workbook.createSheet("Sheet "+sheetNum,sheetNum-1);
		return sheet;
	}
	

	public static String convertString(String obj,String nullTo){
		return obj==null?nullTo:obj;
	}
	public static String convertObject(Object obj,String nullTo){
		return obj==null?nullTo:obj.toString();
	}
}
