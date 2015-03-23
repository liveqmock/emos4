package com.ultrapower.eoms.common.plugin.ecside.easylist;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ultrapower.eoms.common.plugin.ecside.common.log.LogHandler;
import com.ultrapower.eoms.common.plugin.ecside.core.ECSideConstants;
import com.ultrapower.eoms.common.plugin.ecside.core.TableConstants;
import com.ultrapower.eoms.common.plugin.ecside.easyda.DataAccessModel;


public abstract class AbstractEasyListModel extends DataAccessModel {

	private Log logger = LogFactory.getLog(AbstractEasyListModel.class);

	
	protected String sqlSelect;
	protected String sqlSelectNum;

	protected int defaultPageSize=TableConstants.DEFAULT_PAGE_SIZE;
	protected String tableId;
	
	protected String listPage;
	protected String columnTitles;
	

	
	public String getColumnTitles() {
		return columnTitles;
	}

	public void setColumnTitles(String columnTitles) {
		this.columnTitles = columnTitles;
	}

	public abstract void beforeSelect(Map parameterMap,HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException;
	
	public abstract void afterSelect(Map parameterMap,HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException;

	
	public List getRecordsList(Map map){
		return executeQuery("sqlSelect", map);
	}
	
	
	public int getRecordsNumber(Map map){
		int number=-1;
		if (sqlSelectNum==null || sqlSelectNum.length()<1){
			return ECSideConstants.ONCE_SELECT_FLAG;
		}
		
		String record=executeQueryFunction("sqlSelectNum", map);
		try{
			number=new Integer(record).intValue();
		}catch (Exception e) {
			LogHandler.errorLog(logger, e);
			number=-1;
		}
		return number;
	}
	
	public void gotoListPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//TODO :  nothing;
//			String page=getListPage();
//			if (page==null || page.length()<1){
//				page=request.getServletPath();
//			}
//			RequestDispatcher rd=request.getRequestDispatcher(page);
//			rd.forward(request,response);
		}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if (sqls==null){
			sqls=new HashMap();
		}
		sqls.put("sqlSelect", sqlSelect);
		sqls.put("sqlSelectNum", sqlSelectNum);
		
		if (columnTitles!=null){
			columnTitles=columnTitles.trim();
		}
		
		super.afterPropertiesSet();
		
	}

	public int getDefaultPageSize() {
		return defaultPageSize;
	}

	public void setDefaultPageSize(int defaultPageSize) {
		this.defaultPageSize = defaultPageSize;
	}

	public String getListPage() {
		return listPage;
	}

	public void setListPage(String listPage) {
		this.listPage = listPage;
	}

	public String getSqlSelect() {
		return sqlSelect;
	}

	public void setSqlSelect(String sqlSelect) {
		this.sqlSelect = sqlSelect;
	}

	public String getSqlSelectNum() {
		return sqlSelectNum;
	}

	public void setSqlSelectNum(String sqlSelectNum) {
		this.sqlSelectNum = sqlSelectNum;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	
}
