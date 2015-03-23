package com.ultrapower.eoms.common.plugin.ecside.easyda;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

import com.ultrapower.eoms.common.plugin.ecside.common.log.LogHandler;
import com.ultrapower.eoms.common.plugin.ecside.core.ECSideConstants;
import com.ultrapower.eoms.common.plugin.ecside.easydataaccess.EasyDataAccessConstants;
import com.ultrapower.eoms.common.plugin.ecside.easydataaccess.EasyDataAccessUtil;
import com.ultrapower.eoms.common.plugin.ecside.easydataaccess.SqlParameter;

public class DataAccessModel extends BaseDAO implements InitializingBean  {
	
	private Log logger = LogFactory.getLog(DataAccessModel.class);
	
	protected Map sqls;
	
	protected Map sqlSnippets;
	
	protected Map querySqls;
	protected Map updateSqls;
	protected Map callSqls;
	protected Map attributes;


    protected Map interceptors=new HashMap();

	public void registerInterceptors(){
		;
	}
	
	public void addInterceptor(DataAccessInterceptor dataAccessInterceptor){
		interceptors.put(dataAccessInterceptor.getSqlName(), dataAccessInterceptor);
	}
	
	public DataAccessInterceptor getInterceptor(String sqlName){
		return (DataAccessInterceptor)interceptors.get(sqlName);
	}
	
	
	public void afterPropertiesSet() throws Exception {
		if (sqls==null) return;
		
		querySqls=new HashMap();
		updateSqls=new HashMap();
		callSqls=new HashMap();
		sqlSnippets=new HashMap();
		if (attributes==null){
			attributes=new HashMap();
		}
		
		for (Iterator iter=sqls.keySet().iterator();iter.hasNext();){
			String key=(String)iter.next();
			
			String orgSql=StringUtils.trimWhitespace((String)sqls.get(key));
			if (orgSql==null) {continue;}
			String newSql=EasyDataAccessUtil.parseEasySql(orgSql);
			
			sqlSnippets.put(key, EasyDataAccessUtil.createSqlSnippets(newSql) );
			
			StringBuffer newSqlBf=new StringBuffer(newSql);
			sqls.put(key, newSqlBf);
			if (StringUtils.startsWithIgnoreCase(newSql, "select ")){
				querySqls.put(key, newSqlBf);
			}else if (StringUtils.startsWithIgnoreCase(newSql, "update ")
					|| StringUtils.startsWithIgnoreCase(newSql, "insert ")
					|| StringUtils.startsWithIgnoreCase(newSql, "delete ") ){
				updateSqls.put(key, newSqlBf);
			}else if (StringUtils.startsWithIgnoreCase(newSql, "{")
					&& StringUtils.endsWithIgnoreCase(newSql, "}")){
				callSqls.put(key, newSqlBf);				
			}else{
				attributes.put(key, orgSql);
			}
		}
		
		registerInterceptors();
		
	}
	
	public Object executeSql(String name,Map parameterMap){
		
		Object result=null;
		if (isQuerySql(name)){
			result=executeQuery(name,parameterMap);
		}else if (isUpdateSql(name)){
			result=executeUpdate(name,parameterMap);
		}else if (isCallSql(name)){
			result=executeCall(name,parameterMap);
		}else{
			result=executeUpdate(name,parameterMap);
		}
		
		return result;
	}
	
	
	
	public List executeQuery(String name,Map parameterMap){
		String sql=((StringBuffer)querySqls.get(name)).toString();
		List sqlSnippetList=(List)sqlSnippets.get(name);
		return executeQuery(parameterMap,sql,sqlSnippetList);
	}
	
	
	public String executeQueryFunction(String name,Map parameterMap){
		String sql=((StringBuffer)querySqls.get(name)).toString();
		List sqlSnippetList=(List)sqlSnippets.get(name);
		return executeQueryFunction(parameterMap,sql,sqlSnippetList);
	}
	
	public Integer executeUpdate(String name,Map parameterMap){
		String sql=((StringBuffer)updateSqls.get(name)).toString();
		List sqlSnippetList=(List)sqlSnippets.get(name);
		return executeUpdate(parameterMap,sql,sqlSnippetList);
	}
	
	public Map executeCall(String name,Map parameterMap){
		String sql=((StringBuffer)callSqls.get(name)).toString();
		List sqlSnippetList=(List)sqlSnippets.get(name);
		return executeCall(parameterMap,sql,sqlSnippetList);
	}
	
	public List executeQuery(Map map,String sql,List sqlSnippetList){
		Connection conn=null;
		PreparedStatement pstmt = null;
		ResultSet rest = null;
		List recordList=null;
		
		try {
			conn = getConnection();
			pstmt = EasyDataAccessUtil.createPreparedStatement(conn, sql,sqlSnippetList, map);
			rest = pstmt.executeQuery();
			String[] columnName=DataAccessUtil.getColumnNames(rest);
			map.put(ECSideConstants.TABLE_FILEDS_KEY, columnName);
			recordList=new ArrayList();
			Map record=null;
			while (rest.next()) {
				record=new HashMap();
				DataAccessUtil.buildRecordMap(rest,columnName,record);
				recordList.add(record);
			}
		} catch (Exception e) {
			LogHandler.errorLog(logger, e);
			recordList=null;
		}finally{
			close(rest,pstmt,conn);
		}
		
		return recordList;
	}
	
	public String executeQueryFunction(Map map,String sql,List sqlSnippetList){
		Connection conn=null;
		PreparedStatement pstmt = null;
		ResultSet rest = null;
		String record=null;
		try {
			conn = getConnection();
			pstmt = EasyDataAccessUtil.createPreparedStatement(conn, sql,sqlSnippetList, map);
			rest = pstmt.executeQuery();
			if (rest.next()) {
				record=rest.getString(1);
			}
		} catch (Exception e) {
			LogHandler.errorLog(logger, e);
			record=null;
		}finally{
			close(rest,pstmt,conn);
		}
		
		return record;
	}
	
	public Integer executeUpdate(Map map,String sql,List sqlSnippetList){
		int opresult=-1;
		
		Connection conn=null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			pstmt = EasyDataAccessUtil.createPreparedStatement(conn, sql,sqlSnippetList, map);
			opresult = pstmt.executeUpdate();
		} catch (Exception e) {
			LogHandler.errorLog(logger, e);
			opresult=-1;
		}finally{
			close(pstmt,conn);
		}
		
		return new Integer(opresult);
	}


	public Map executeCall(Map map,String sql,List sqlSnippetList){
		boolean opresult=false;
		List paraList=new ArrayList();
		Map result=new HashMap();
		Connection conn=null;
		PreparedStatement pstmt = null;
		CallableStatement cstmt = null;
		try {
			conn = getConnection();
			cstmt =EasyDataAccessUtil.createPreparedCallStatement(conn, sql, sqlSnippetList, map,paraList);
			opresult=cstmt.execute();
			if (!opresult){
				for (int i=0;i<paraList.size();i++){
					SqlParameter sqlParameter=(SqlParameter)paraList.get(i);
					if (EasyDataAccessConstants.OUT_TYPE.equals(sqlParameter.getType())){
						result.put(sqlParameter.getName(),cstmt.getString(sqlParameter.getIndex()));
					}
				}
			}
		} catch (Exception e) {
			LogHandler.errorLog(logger, e);
			opresult=false;
		}finally{
			close(pstmt,conn);
		}
		
		return result;
	}
	
	public Object getAttribute(Object key) {
		return attributes.get(key);
	}
	public void setAttribute(Object key,Object value) {
		attributes.put(key,value);
	}
	
	
	
	public String getSql(String name){
		StringBuffer sql=(StringBuffer)sqls.get(name);
		return sql!=null?sql.toString():null;
	}
	public String getQuerySql(String name){
		StringBuffer sql=(StringBuffer)querySqls.get(name);
		return sql!=null?sql.toString():null;
	}
	public String getUpdateSql(String name){
		StringBuffer sql=(StringBuffer)updateSqls.get(name);
		return sql!=null?sql.toString():null;
	}
	public String getCallSql(String name){
		StringBuffer sql=(StringBuffer)callSqls.get(name);
		return sql!=null?sql.toString():null;
	}
	
	
	public boolean isQuerySql(String name){
		return querySqls.get(name)!=null;
	}
	
	public boolean isUpdateSql(String name){
		return updateSqls.get(name)!=null;
	}
	public boolean isCallSql(String name){
		return callSqls.get(name)!=null;
	}
	
	public Map getSqls() {
		return sqls;
	}

	public void setSqls(Map sqls) {
		this.sqls = sqls;
	}

	public Map getInterceptors() {
		return interceptors;
	}

	public Map getAttributes() {
		return attributes;
	}


	public void setAttributes(Map attributes) {
		this.attributes = attributes;
	}

	public Map getCallSqls() {
		return callSqls;
	}

	public void setCallSqls(Map callSqls) {
		this.callSqls = callSqls;
	}


	
}
