package com.ultrapower.eoms.common.plugin.ecside.easyda;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;

import com.ultrapower.eoms.common.plugin.ecside.common.log.LogHandler;



public class BaseDAO {
	
	private Log logger = LogFactory.getLog(BaseDAO.class);
	
	private DataSource dataSource;

	protected int maxBatch=255;
	
	
	public void setDataSource(DataSource ds) {
		dataSource = ds;
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}
	
	
	public int getMaxBatch() {
		return maxBatch;
	}
	public void setMaxBatch(int maxBatch) {
		this.maxBatch = maxBatch;
	}

	////////////////////////////////////////////////////////
	

	
	public Connection getConnection(){
		return DataSourceUtils.getConnection(getDataSource());
	}		

	public void closeConnection(Connection con) {
		DataSourceUtils.releaseConnection(con, getDataSource());
	}
	
	
	public void close(ResultSet rest,Statement stmt,Connection conn){
		try {
			close(rest);
		} catch (SQLException e) {
			LogHandler.errorLog(logger, e);
		}
		close(stmt,conn);
	}

	public void close(Statement stmt,Connection conn){
		try {
			close(stmt);
		} catch (SQLException e) {
			LogHandler.errorLog(logger, e);
		}
		close(conn);
	}
	

	public void close(ResultSet rest) throws SQLException{
		if(rest != null) rest.close();
	}
	
	public void close(Statement stmt) throws SQLException{
		if(stmt != null) stmt.close();
	}
	
	public void close(Connection conn){
		if(conn != null) closeConnection(conn);
	}
}
