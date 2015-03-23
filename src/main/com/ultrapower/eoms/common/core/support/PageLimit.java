package com.ultrapower.eoms.common.core.support;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

import com.ultrapower.eoms.common.core.web.ActionContext;
import com.ultrapower.eoms.common.plugin.datagrid.grid.Limit;
import com.ultrapower.eoms.common.plugin.datagrid.util.RequestUtils;

public class PageLimit implements Serializable {
	private static final long serialVersionUID = -2682649394691545410L;
	private Limit limit;
	private Integer CURRENT_ROWS_SIZE = 20;
	private Integer totalRows;
	private boolean export;
	private Integer pageSize;
	private Map<String, String> sortValueMap;
	private String sortColumn;
	private String sortDesc;
	private Object entity;
	private Map<String, String> aliasMap = new HashMap<String, String>();
	private Map<String, String> operMap = new HashMap<String, String>();
	private Map<String, String> actualMap = new HashMap<String, String>();
	private Map<String, Object> valueMap = new HashMap<String, Object>();

	private PageLimit() {
	}

	/**
	 * 构建 ec limit 对象
	 */
	public static PageLimit getInstance() {

		PageLimit m_instance = new PageLimit();
		HttpServletRequest request = ActionContext.getRequest();
		if (request != null) {
//			m_instance.limit = RequestUtils.getLimit(request);
			//String request.getpa
			m_instance.limit = RequestUtils.getPageLimit(request);
		//	System.out.println(m_instance.limit.getPageSize());
//			if (RequestUtils.getCurrentRowsDisplayed(request) != 0)
//				m_instance.CURRENT_ROWS_SIZE = RequestUtils
//						.getCurrentRowsDisplayed(request);
//			m_instance.totalRows = RequestUtils
//					.getTotalRowsFromRequest(request);
//			m_instance.export = RequestUtils.isExported(request);
//			if (m_instance.export) {
//				int[] rows = RequestUtils.getRowStartEnd(request,
//						m_instance.totalRows, m_instance.CURRENT_ROWS_SIZE);
			//	m_instance.CURRENT_ROWS_SIZE = rows[1] - rows[0];
			//}
//			m_instance.pageSize = m_instance.limit.getPage();
			m_instance.pageSize=m_instance.limit.getPage();
			m_instance.CURRENT_ROWS_SIZE=m_instance.limit.getPageSize();

/*			Sort sort = m_instance.limit.getSort();
			m_instance.sortValueMap = sort.getSortValueMap();
			Iterator sortIterator = m_instance.sortValueMap.keySet().iterator();

			while (sortIterator.hasNext()) {
				m_instance.sortColumn = (String) sortIterator.next();
				m_instance.sortDesc = m_instance.sortValueMap
						.get(m_instance.sortColumn);
			}
*/		}else{
			m_instance.setPageSize(1);
		}
		return m_instance;

	}

	public Limit getLimit() {
		return limit;
	}

	public void setLimit(Limit limit) {
		this.limit = limit;
	}

	public int getCURRENT_ROWS_SIZE() {
		return CURRENT_ROWS_SIZE;
	}

	public void setCURRENT_ROWS_SIZE(int current_rows_size) {
		CURRENT_ROWS_SIZE = current_rows_size;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public boolean isExport() {
		return export;
	}

	public void setExport(boolean export) {
		this.export = export;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public void setCURRENT_ROWS_SIZE(Integer current_rows_size) {
		CURRENT_ROWS_SIZE = current_rows_size;
	}

	public String getSortAndDesc(String orders) {
		if (StringUtils.hasText(this.sortColumn)) {
			if (StringUtils.hasText(this.sortDesc))
				return " order by " + sortColumn + " " + sortDesc;
			return " order by " + sortColumn;
		}
		return orders;
	}

	public Map<String, String> getAliasMap() {
		return aliasMap;
	}

	public void setAliasColumn(String column) {
		String[] columns = column.split("\\.");
		if (columns.length >= 2)
			this.aliasMap.put(getColumn(columns[0].length(),column), column);
		if (columns.length == 1)
			this.aliasMap.put(columns[0], columns[0]);
	}

	public void setAliasColumn(String column, String operation) {
		setAliasColumn(column);
		String[] columns = column.split("\\.");
		if (columns.length >= 2)
			this.operMap.put(getColumn(columns[0].length(),column), operation);
		if (columns.length == 1)
			this.operMap.put(columns[0], operation);
	}

	public void setAliasColumn(String column, String operation,
			String actualColumn) {
		setAliasColumn(column, operation);
		String[] columns = column.split("\\.");
		if (columns.length >= 2)
			this.actualMap.put(getColumn(columns[0].length(),column), actualColumn);
		if (columns.length == 1)
			this.actualMap.put(columns[0], actualColumn);
	}
	
	private String getColumn(int index,String column){
		return column.substring(index+1);
	}
	
	public void setColumnValue(String column, Object value) {
        this.valueMap.put(column, value);
	}

	public Object getEntity() {
		return entity;
	}

	public void setEntity(Object entity) {
		this.entity = entity;
	}

	public Map<String, String> getOperMap() {
		return operMap;
	}

	public void setOperMap(Map<String, String> operMap) {
		this.operMap = operMap;
	}

	public Map<String, String> getActualMap() {
		return actualMap;
	}

	public void setActualMap(Map<String, String> actualMap) {
		this.actualMap = actualMap;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public String getSortDesc() {
		return sortDesc;
	}

	public void setSortDesc(String sortDesc) {
		this.sortDesc = sortDesc;
	}

	public Map<String, Object> getValueMap() {
		return valueMap;
	}

	public void setValueMap(Map<String, Object> valueMap) {
		this.valueMap = valueMap;
	}

}
