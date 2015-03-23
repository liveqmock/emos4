package com.ultrapower.eoms.common.plugin.ecside.easyda;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.ResultSet;

public abstract class DataHtmlBuilder {
	
	private Writer write;
	private String[] columnNames;

	public DataHtmlBuilder(){
		
	}
	public DataHtmlBuilder(Writer write,String[] columnNames) {
		this.write=write;
		setColumnNames(columnNames);
	}
	
	public DataHtmlBuilder(OutputStream outputStream,String[] columnNames) {
		this(new PrintWriter(outputStream),columnNames);
	}
	
	public void flush() throws IOException{
		write.flush();
	}
	
	public void close() throws IOException{
		write.close();
	}
	
	public abstract void tableStart();
	
	public abstract void theadStart();
	public abstract void headTr(ResultSet rest);
	public abstract void headCells(ResultSet rest);
	public abstract void headTrEnd();
	public abstract void theadEnd();
	
	public abstract void tbodyStart();
	public abstract void tr(ResultSet rest);
	public abstract void cells(ResultSet rest);
	public abstract void trEnd();
	public abstract void tbodyEnd();
	
	public abstract void tableEnd();

	public Writer getWrite() {
		return write;
	}

	public void setWrite(Writer write) {
		this.write = write;
	}

	public String[] getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}
	
}
