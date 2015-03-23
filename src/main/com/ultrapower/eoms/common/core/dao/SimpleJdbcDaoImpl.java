package com.ultrapower.eoms.common.core.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.ultrapower.eoms.common.core.exception.BaseException;
import com.ultrapower.eoms.common.core.support.Page;
import com.ultrapower.eoms.common.core.support.PageLimit;
import com.ultrapower.eoms.common.core.util.GenericsUtils;


@Transactional
@SuppressWarnings("unchecked")
public class SimpleJdbcDaoImpl<T extends Map> implements IDao<T> {

	protected Logger log = LoggerFactory.getLogger(getClass());

	protected String entityName;

	protected String entityId;

	protected boolean ifFindHql = false;

	private SimpleJdbcTemplate simpleJdbcTemplate;

	private SimpleJdbcInsert insertActor;

	private DataSource dataSource;

	public SimpleJdbcDaoImpl() {
	}

	public SimpleJdbcDaoImpl(String entityName) {
		this.entityName = entityName;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
		// this.insertActor = new SimpleJdbcInsert(dataSource)
		// .withTableName(entityName);
		// this.entityId = this.getIdName();
	}

	public boolean isIfFindHql() {
		return ifFindHql;
	}

	public void setIfFindHql(boolean ifFindHql) {
		this.ifFindHql = ifFindHql;
	}

	public void doExecute(String sql) {
		getSimpleJdbcTemplate().getJdbcOperations().execute(sql);
	}

	public Integer getInt(String ql, Object... values) {
		String sql = this.getSql(ql);
		return getSimpleJdbcTemplate().queryForInt(sql, values);
	}

	public String getString(String ql, Object... values) {
		String sql = this.getSql(ql);
		return getSimpleJdbcTemplate().queryForObject(sql,
				String.class, values);
	}

	public Long getLong(String ql, Object... values) {
		String sql = this.getSql(ql);
		return getSimpleJdbcTemplate().queryForLong(sql, values);
	}

	public Map getMap(String ql, Object... values) {
		String sql = this.getSql(ql);
		return getSimpleJdbcTemplate().queryForMap(sql, values);
	}

	public <T> T getEntity(String ql, Class<T> entityClass, Object... values) {
		String sql = this.getSql(ql);
		return getSimpleJdbcTemplate().queryForObject(sql, entityClass,
				values);
	}

	public Connection getConn() {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public List Query(String ql, PageLimit pageLimit, Object... values) {
		String sql = this.getSql(ql);
		List list;
		if (pageLimit.getEntity() != null) {
			list = this.find(GenericsUtils.queryAccession(sql, pageLimit),
					values);
			Iterator iterator = pageLimit.getValueMap().keySet().iterator();
			while (iterator.hasNext()) {

			}
		} else {

		}
		return null;
	}

	public int executeUpdate(String ql, Object... values) {
		String sql = this.getSql(ql);
		return this.getSimpleJdbcTemplate().update(sql, values);
	}

	public List find(String ql, Object... values) {
		String sql = this.getSql(ql);
		return getSimpleJdbcTemplate().queryForList(sql, values);
	}

	public List findBy(String ql, Map<String, Object> map) {
		String sql = this.getSql(ql);
		return this.getSimpleJdbcTemplate().queryForList(sql, map);
	}

	public List findByProperty(Map<String, Object> map) {
		List list = null;
		StringBuffer buffer = new StringBuffer("select * from  " + entityName
				+ " where ");
		for (Iterator<String> iterator = map.keySet().iterator(); iterator
				.hasNext();) {
			String param = iterator.next();
			buffer.append(param);
			buffer.append(" = ?, ");
			buffer.append(" and ");
		}
		String sql = buffer.toString();
		if (sql.toString().trim().endsWith("and")) {
			sql = sql.substring(0, sql.indexOf("and") - 1);
		}
		if (sql.toString().trim().endsWith("where")) {
			sql = sql.substring(0, sql.indexOf("where") - 1);
		}
		list = getSimpleJdbcTemplate().queryForList(sql, map);
		return list;
	}

	public List findCacheable(String ql, Object... values) {
		throw new BaseException(" unsupport this method ");
	}

	public T findUnique(String ql, Object... values) {
		List list = this.find(ql, values);
		if (list != null && list.size() > 0) {
			return (T) list.get(0);
		} else {
			return null;
		}
	}

	public T get(Serializable id) {
		return (T) this.getSimpleJdbcTemplate().queryForMap(
				"select * from  " + entityName, id);
	}

	public List<T> getAll() {
		return (List) getSimpleJdbcTemplate().queryForList(
				"select * from  " + entityName);
	}

	public Serializable getId(T entity) {
		Serializable id = (String) entity.get(entityId);
		return id;
	}

	public String getIdName() {
		String thePK = null;
		try {
			ResultSet rs = this.getConn().getMetaData().getPrimaryKeys(null,
					null, entityName.toUpperCase());
			while (rs.next()) {
				thePK = rs.getString("COLUMN_NAME");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return thePK;
	}

	public String getIdsByList(List<T> list) {
		StringBuffer ids = new StringBuffer();
		for (T e : list) {
			ids.append((String) e.get(entityId));
			ids.append(",");
		}
		if (ids.toString().endsWith(",")) {
			return ids.subSequence(0, ids.length() - 1).toString();
		} else {
			return ids.toString();
		}
	}

	public List<T> getListByIds(String ids) {
		List<T> list = new ArrayList<T>();
		if (StringUtils.hasLength(ids)) {
			String[] idArray = ids.split(",");
			for (String id : idArray) {
				list.add(this.get(id));
			}
		}
		return list;
	}

	public boolean isPropertyUnique(String propertyName, Object newValue,
			Object orgValue) {
		return false;
	}

	public boolean isUnique(T entity, String uniquePropertyNames) {
		String[] propertyNames = uniquePropertyNames.split(",");
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer buffer = new StringBuffer("select * from " + entityName
				+ " where ");
		for (String key : propertyNames) {
			if (entity.get(key) != null) {
				map.put(key, entity.get(key));
			} else {
				map.put(key, "");
			}
			buffer.append(key);
			buffer.append(" = ?, ");
			buffer.append(" and ");
		}
		String sql = buffer.toString();
		if (sql.toString().trim().endsWith("and")) {
			sql = sql.substring(0, sql.indexOf("and") - 1);
		}
		if (sql.toString().trim().endsWith("where")) {
			sql = sql.substring(0, sql.indexOf("where") - 1);
		}
		Serializable id = getId(entity);

		if (id != null || !id.toString().trim().equals("")) {// 修改的情况
			sql = sql + " and " + entityId + " != " + id;
		}

		List list = getSimpleJdbcTemplate().queryForList(sql, map);

		if (list != null && list.size() > 0) {
			return false;
		}
		return true;
	}

	private String getSql(final String ql) {
		return ql;
	}

	public List pagedQuery(String ql, PageLimit pl, Object... values) {
		Assert.notNull(ql, "SQL must not be null");
		String queryCountsSQL = "select count(*) from " + ql;
		final int startIndex = Page.getStartOfPage(pl.getPageSize(), pl
				.getCURRENT_ROWS_SIZE());
		final int page_num = pl.getCURRENT_ROWS_SIZE();
		final int total = getSimpleJdbcTemplate().getJdbcOperations()
				.queryForInt(queryCountsSQL);
		String sql_condition = Pattern.compile("\\*").matcher(ql).replaceFirst(
				"count(*)");
		int find_counts = getSimpleJdbcTemplate().getJdbcOperations()
				.queryForInt(sql_condition);
		final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// 如果查询到的总数是0，则直接返回而不进行ResultSet遍历
		if (total == 0 || find_counts == 0) {
			return list;
		}
		PreparedStatement pstat = null;
		ResultSet rs = null;
		Connection conn = null;

		try {
			conn = DataSourceUtils.getConnection(getDataSource());
			pstat = conn.prepareStatement(ql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = pstat.executeQuery();
			rs.first();
			rs.relative(startIndex);
			int i = 0;
			ResultSetMetaData rsmd = rs.getMetaData();
			while (i < page_num && !rs.isAfterLast()) {
				Map<String, Object> m = new HashMap<String, Object>();
				for (int j = 0; j < rsmd.getColumnCount(); j++) {
					// 4 保存字段名、字段值到变量中
					m.put(rsmd.getColumnName(j), rs.getObject(j));
				}
				list.add(m);
				i++;
				rs.next();
			}
		} catch (CannotGetJdbcConnectionException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSourceUtils.releaseConnection(conn, getDataSource());
		}
		Page page = new Page(startIndex, total, pl.getPageSize(), list);
		pl.getLimit().setRowAttributes(
				new Long(page.getTotalCount()).intValue(),
				pl.getCURRENT_ROWS_SIZE());
		return (List) page.getResult();
	}

	public void remove(T entity) {
		Map theMap = entity;
		this.removeById((String) theMap.get(entityId));
	}

	public void removeById(Serializable id) {
		this.executeUpdate("delete from " + entityName + " where "
				+ this.entityId + "=?", id);
	}

	public void save(T entity) {
		insertActor.execute(entity);
	}

	public void saveOrUpdate(T entity) {
		Map theMap = entity;
		if (theMap.get(entityId) == null)
			this.save(entity);
		else
			this.getSimpleJdbcTemplate().getNamedParameterJdbcOperations()
					.update(entityName, entity);
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public SimpleJdbcTemplate getSimpleJdbcTemplate() {
		return simpleJdbcTemplate;
	}

	public void setSimpleJdbcTemplate(SimpleJdbcTemplate simpleJdbcTemplate) {
		this.simpleJdbcTemplate = simpleJdbcTemplate;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void merge(T entity) {
	}

	public boolean sessionContains(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}

	public org.hibernate.Query createQuery(String hql, Object... values) {
		// TODO Auto-generated method stub
		return null;
	}

	public org.hibernate.Query createSQLQuery(String sql) {
		// TODO Auto-generated method stub
		return null;
	}

	public Session getHibernateSession() {
		// TODO Auto-generated method stub
		return null;
	}

	public void refresh(Object entity) {
		// 此处一定要用lockMode.UPGRADE模式进行锁定刷新
	}

	public List<Map<String, Object>> findByPage(final String sql,
			String queryCountsSQL, final List<String> attrAlias) {
		Assert.notNull(sql, "SQL must not be null");
		// String queryCountsSQL = "select count(*) from "+tableName;
		PageLimit pl = PageLimit.getInstance();
		final int startIndex = Page.getStartOfPage(pl.getPageSize(), pl
				.getCURRENT_ROWS_SIZE());
		final int page_num = pl.getCURRENT_ROWS_SIZE();
		final int total = getSimpleJdbcTemplate().getJdbcOperations()
				.queryForInt(queryCountsSQL);
		// String sql_condition =
		// Pattern.compile("\\*").matcher(sql).replaceFirst("count(*)");
		String sql_condition = "select count(*) "
				+ sql.substring(sql.indexOf("from"), sql.length());
		int find_counts = getSimpleJdbcTemplate().getJdbcOperations()
				.queryForInt(sql_condition);
		final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// 如果查询到的总数是0，则直接返回而不进行ResultSet遍历
		if (total == 0 || find_counts == 0) {
			return list;
		}
		PreparedStatement pstat = null;
		ResultSet rs = null;
		try {
			pstat = DataSourceUtils.getConnection(getDataSource())
					.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			rs = pstat.executeQuery();
			rs.first();
			rs.relative(startIndex);
			int i = 0;
			while (i < page_num && !rs.isAfterLast()) {
				Map<String, Object> m = new HashMap<String, Object>();
				for (String attr : attrAlias) {
					m.put(attr, rs.getObject(attr));
				}
				list.add(m);
				i++;
				rs.next();
			}
		} catch (CannotGetJdbcConnectionException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Page page = new Page(startIndex, total, pl.getPageSize(), list);
		pl.getLimit().setRowAttributes(
				new Long(page.getTotalCount()).intValue(),
				pl.getCURRENT_ROWS_SIZE());
		return (List) page.getResult();
	}

}