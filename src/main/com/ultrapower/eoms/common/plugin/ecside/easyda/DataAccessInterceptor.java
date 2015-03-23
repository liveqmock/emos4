package com.ultrapower.eoms.common.plugin.ecside.easyda;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class DataAccessInterceptor {
	
	private String sqlName;
	
	
	public DataAccessInterceptor(String sqlName) {
		this.sqlName=sqlName;
	}
	
	public abstract void before(Map parameterMap,HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException;
	
	public abstract void after(Map parameterMap,HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException;


	public String getSqlName() {
		return sqlName;
	}

	public void setSqlName(String sqlName) {
		this.sqlName = sqlName;
	}
	
}
