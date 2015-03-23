package com.ultrapower.eoms.common.plugin.ecside.easyda;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ultrapower.eoms.common.plugin.ecside.common.log.LogHandler;
import com.ultrapower.eoms.common.plugin.ecside.core.ECSideConstants;
import com.ultrapower.eoms.common.plugin.ecside.easydataaccess.EasyDataAccessUtil;
import com.ultrapower.eoms.common.plugin.ecside.util.ExportViewUtils;

public class DataExportModel extends DataAccessModel  {
	
	Log logger = LogFactory.getLog(DataExportModel.class);

	
    public static final int WIDTH_MULT = 240; // width per char
    public static final int MIN_CHARS = 8; // minimum char width
    public static final short DEFAULT_FONT_HEIGHT = 8;
    public static final double NON_NUMERIC = -.99999;
    public static final String DEFAULT_MONEY_FORMAT = "$###,###,##0.00";
    public static final String DEFAULT_PERCENT_FORMAT = "##0.0%";
    public static final String NBSP = "&nbsp;";
    
    public static final int colWidth=15;

    protected final static String CONFIG_DELIMITER = ",";
    protected final static String CSV_DELIMITER = ",";
    protected final static String TXT_DELIMITER = "\t";
    
    protected String columnTitles ;
    
    protected String[] titlesColl ;
	
    protected String format;
    
    protected String[] formatColl ;
    
    protected Map mappingItems;

	@Override
	public void afterPropertiesSet() throws Exception {
		
		if (StringUtils.isNotBlank(columnTitles)){
			titlesColl=columnTitles.split(CONFIG_DELIMITER);
		}else{
			titlesColl=new String[]{};
		}
		if (StringUtils.isNotBlank(format)){
			formatColl=format.split(CONFIG_DELIMITER);
		}else{
			formatColl=new String[]{};
		}
		

		super.afterPropertiesSet();
	}

	
	public void doExport(String sqlName,String type,Map parameterMap,OutputStream outputStream){
		String sql=((StringBuffer)querySqls.get(sqlName)).toString();
		List sqlSnippetList=(List)sqlSnippets.get(sqlName);
		if ("xls".equalsIgnoreCase(type)){
			beforeExportXLS();
			doExportXLS(parameterMap,sql,sqlSnippetList,outputStream);
			afterExportXLS();
		}else if ("csv".equalsIgnoreCase(type)){
			beforeExportCSV();
			doExportCSV(parameterMap,sql,sqlSnippetList,outputStream);
			afterExportCSV();
		}else if ("txt".equalsIgnoreCase(type)){
			beforeExportTXT();
			doExportTXT(parameterMap,sql,sqlSnippetList,outputStream);
			afterExportTXT();
		}else{
			//TODO:
			beforeExportTXT();
			doExportTXT(parameterMap,sql,sqlSnippetList,outputStream);
			afterExportTXT();
		}
	}
	
	public void doExportCSV(Map map,String sql,List sqlSnippetList,OutputStream outputStream){
		doExportText(map, sql, sqlSnippetList, outputStream, CSV_DELIMITER);
	}
	
	public void doExportTXT(Map map,String sql,List sqlSnippetList,OutputStream outputStream){
		doExportText(map, sql, sqlSnippetList, outputStream, TXT_DELIMITER);
	}
	
	public void beforeExportXLS(){ }
	public void afterExportXLS(){ }
	public void beforeExportCSV(){ }
	public void afterExportCSV(){ }
	public void beforeExportTXT(){ }
	public void afterExportTXT(){ }
	
	public void doExportText(Map map,String sql,List sqlSnippetList,OutputStream outputStream,String delimiter){
		Connection conn=null;
		PreparedStatement pstmt = null;
		ResultSet rest = null;
		try {
			conn = getConnection();
			pstmt = EasyDataAccessUtil.createPreparedStatement(conn, sql,sqlSnippetList, map);
			rest = pstmt.executeQuery();
			
			String[] columnNames=DataAccessUtil.getColumnNames(rest);
			map.put(ECSideConstants.TABLE_FILEDS_KEY, columnNames);
			
			DataAccessUtil.outputText(rest, outputStream,delimiter,columnNames,titlesColl, mappingItems);
	
		} catch (SQLException e) {
			LogHandler.errorLog(logger, e);
		} catch (Exception e) {
			LogHandler.errorLog(logger, e);
		}finally{
			close(rest,pstmt,conn);
		}
	}
	
	public void doExportXLS(Map map,String sql,List sqlSnippetList,OutputStream outputStream){
		Connection conn=null;
		PreparedStatement pstmt = null;
		ResultSet rest = null;
	    short cellnum=0;
	    int rownum=0;
		try {
			conn = getConnection();
			pstmt = EasyDataAccessUtil.createPreparedStatement(conn, sql,sqlSnippetList, map);
			rest = pstmt.executeQuery();
			String[] columnNames=DataAccessUtil.getColumnNames(rest);
			map.put(ECSideConstants.TABLE_FILEDS_KEY, columnNames);
		    
		    WritableWorkbook wb= Workbook.createWorkbook(outputStream);
		    WritableSheet sheet = wb.createSheet("Export Workbook",0);

		    rownum+=createXLSHeader(sheet,rownum);
		    
			while (rest.next()) {
				cellnum=0;
				for (int i=0;i<columnNames.length;i++){
					String value=ExportViewUtils.parseXLS(rest.getString(columnNames[i]));
					
					if (i<formatColl.length && "num".equalsIgnoreCase(formatColl[i]) ) {
						writeToCellFormatted(sheet,cellnum,rownum,value, null);
					} else {

						writeToCellAsText(sheet,cellnum,rownum,value, null);
					}
					
					cellnum++;
				}
				rownum++;
//				wb.write();
			}
        	wb.write();
			wb.close();
		} catch (SQLException e) {
			LogHandler.errorLog(logger, e);
		} catch (Exception e) {
			LogHandler.errorLog(logger, e);
		}finally{
			close(rest,pstmt,conn);
		}
	}
	
	public void doExportHTML(Map map,String sql,List sqlSnippetList,OutputStream outputStream,DataHtmlBuilder dataHtmlBuilder){
		Connection conn=null;
		PreparedStatement pstmt = null;
		ResultSet rest = null;
		try {
			conn = getConnection();
			pstmt = EasyDataAccessUtil.createPreparedStatement(conn, sql,sqlSnippetList, map);
			rest = pstmt.executeQuery();
			String[] columnNames=DataAccessUtil.getColumnNames(rest);
			map.put(ECSideConstants.TABLE_FILEDS_KEY, columnNames);
			
			dataHtmlBuilder.setWrite(new PrintWriter(outputStream,true));
			dataHtmlBuilder.setColumnNames(columnNames);
			
			dataHtmlBuilder.tableStart();
			dataHtmlBuilder.tbodyStart();
			while (rest.next()) {
				dataHtmlBuilder.tr(rest);
				dataHtmlBuilder.cells(rest);
				dataHtmlBuilder.trEnd();
			}
			dataHtmlBuilder.tbodyEnd();
			dataHtmlBuilder.tableEnd();
//			dataHtmlBuilder.flush();
//			dataHtmlBuilder.close();
			
		} catch (SQLException e) {
			LogHandler.errorLog(logger, e);
		} catch (Exception e) {
			LogHandler.errorLog(logger, e);
		}finally{
			close(rest,pstmt,conn);
		}
	}
	
	public static short createTextHeader(String[] titlesColl,OutputStream outputStream,String delimiter) throws WriteException, IOException{
		if (titlesColl==null || titlesColl.length<1){
			return 0;
		}
		for (int i=0;i<titlesColl.length;i++){
			String value=titlesColl[i];
			value=value==null?delimiter:value.trim()+delimiter;
			outputStream.write(value.getBytes());
		}
		outputStream.write(ExportViewUtils.BR.getBytes());
		return 1;
	}
	
	public static short createCSVHeader(String[] titles,OutputStream outputStream) throws WriteException, IOException{
		if (titles==null || titles.length<1){
			return 0;
		}
		for (int i=0;i<titles.length;i++){
			String value=titles[i];
			value=ExportViewUtils.parseCSV(value);
			outputStream.write(value.getBytes());
			if (i<titles.length-1){
				outputStream.write(ExportViewUtils.COMMA);
			}
		}
		outputStream.write(ExportViewUtils.BR.getBytes());
		return 1;
	}
	
	public short createXLSHeader(WritableSheet sheet,int rownum) throws WriteException{
		if (titlesColl==null || titlesColl.length<1){
			return 0;
		}
        WritableCellFormat cellFormat=new WritableCellFormat();
        WritableFont arial10font = new WritableFont(WritableFont.ARIAL,WritableFont.DEFAULT_POINT_SIZE,WritableFont.BOLD); 
        cellFormat.setBackground(Colour.GRAY_25);
        cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN,Colour.GRAY_50);
        cellFormat.setFont(arial10font);
		for (int i=0;i<titlesColl.length;i++){
			writeToCellAsText(sheet,(short)i,rownum,titlesColl[i],cellFormat);
		}
		return 1;
	}
	
	
    private void writeToCellAsText(WritableSheet sheet,short cellnum,int rownum,  String value, WritableCellFormat styleModifier) throws RowsExceededException, WriteException {
        // format text
        if (value.trim().equals(NBSP)) {
            value = "";
        }
        Label label=new Label(cellnum,rownum,value); 
        if (styleModifier!=null){
        	label.setCellFormat(styleModifier);
        }
        sheet.addCell(label);

    }

    private void writeToCellFormatted( WritableSheet sheet,short cellnum,int rownum,  String value, WritableCellFormat styleModifier) throws RowsExceededException, WriteException {
        double numeric = NON_NUMERIC;

        try {
            numeric = Double.parseDouble(value);
        } catch (Exception e) {
            numeric = NON_NUMERIC;
        }

        if (value.startsWith("$") || value.endsWith("%") || value.startsWith("($")) {
//            boolean moneyFlag = (value.startsWith("$") || value.startsWith("($"));
//            boolean percentFlag = value.endsWith("%");

            value = StringUtils.replace(value, "$", "");
            value = StringUtils.replace(value, "%", "");
            value = StringUtils.replace(value, ",", "");
            value = StringUtils.replace(value, "(", "-");
            value = StringUtils.replace(value, ")", "");

            try {
                numeric = Double.parseDouble(value);
            } catch (Exception e) {
                numeric = NON_NUMERIC;
            }

//            if (moneyFlag) {
//                // format money
//            	NumberFormat fivedps = new NumberFormat(moneyFormat); 
//            	WritableCellFormat fivedpsFormat = new WritableCellFormat(fivedps); 
//            	Number number = new Number(cellnum, rownum, numeric, fivedpsFormat); 
//                if (styleModifier!=null){
//                	number.setCellFormat(styleModifier);
//                }
//            	sheet.addCell(number); 
//             } else if (percentFlag) {
//                // format percent
//                numeric = numeric / 100;
//                
//            	NumberFormat fivedps = new NumberFormat(percentFormat); 
//            	WritableCellFormat fivedpsFormat = new WritableCellFormat(fivedps); 
//            	Number number = new Number(cellnum, rownum, numeric, fivedpsFormat);
//                if (styleModifier!=null){
//                	number.setCellFormat(styleModifier);
//                }
//            	sheet.addCell(number);
//            }
        } else if (Math.abs(numeric - NON_NUMERIC) >= .0000001) {
        	// numeric != NON_NUMERIC
            // format numeric
        	Number number = new Number(cellnum, rownum, numeric); 
            if (styleModifier!=null){
            	number.setCellFormat(styleModifier);
            }
        	sheet.addCell(number); 
        } else {
            // format text
            if (value.trim().equals(NBSP)) {
                value = "";
            }
            Label label=new Label(cellnum,rownum,value); 
            if (styleModifier!=null){
            	label.setCellFormat(styleModifier);
            }
            sheet.addCell(label);
        }

    }
	

	public String getFormat() {
		return format;
	}


	public void setFormat(String format) {
		this.format = format;
	}


	public String getColumnTitles() {
		return columnTitles;
	}


	public void setColumnTitles(String columnTitles) {
		this.columnTitles = columnTitles;
	}


	public Map getMappingItems() {
		return mappingItems;
	}


	public void setMappingItems(Map mappingItems) {
		this.mappingItems = mappingItems;
	}



	
	
	


	
}
