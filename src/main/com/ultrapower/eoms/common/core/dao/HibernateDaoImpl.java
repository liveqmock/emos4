package com.ultrapower.eoms.common.core.dao;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.transform.ResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.ultrapower.eoms.common.core.support.Page;
import com.ultrapower.eoms.common.core.support.PageLimit;
import com.ultrapower.eoms.common.core.util.BeanUtils;
import com.ultrapower.eoms.common.core.util.GenericsUtils;

/**
 * Hibernate Dao的泛型基类,提供分页函数和若干便捷查询方法,并对返回值作了泛型类型转换.
 * 
 * @author andy
 */
@SuppressWarnings("unchecked")
@Transactional
public class HibernateDaoImpl<T>  implements IDao<T> {

	protected transient final Logger log = LoggerFactory.getLogger(getClass());

	protected SessionFactory sessionFactory;

	protected Class<T> entityClass;

	protected boolean ifFindHql = false;
	
	

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * 采用@Autowired按类型注入SessionFactory,当有多个SesionFactory的时候Override本函数.
	 */
	@Autowired
	public void setSessionFactory(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public HibernateDaoImpl() {

	}

	public HibernateDaoImpl(final Class<T> entityClass,
			final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		this.entityClass = entityClass;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.bpm.common.core.dao.IDao#getSession()
	 */
	// public Session getSession() {
	// return sessionFactory.getCurrentSession();
	// }
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.bpm.common.core.dao.IDao#getEntityClass()
	 */
	public Class<T> getEntityClass() {
		return entityClass;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.bpm.common.core.dao.IDao#get(java.io.Serializable)
	 */
	public T get(final Serializable id) {
		return (T) getSession().get(entityClass, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.bpm.common.core.dao.IDao#remove(T)
	 */
	public void remove(final T entity) {
		Assert.notNull(entity);
		getSession().delete(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.bpm.common.core.dao.IDao#removeById(java.io.Serializable)
	 */
	public void removeById(final Serializable id) {
		remove(get(id));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.bpm.common.core.dao.IDao#save(T)
	 */
	public void save(final T entity) {
		Assert.notNull(entity);
		getSession().save(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.bpm.common.core.dao.IDao#saveOrUpdate(T)
	 */
	public void saveOrUpdate(final T entity) {
		Assert.notNull(entity);
		getSession().saveOrUpdate(entity);
	}

	public void merge(final T entity) {
		Assert.notNull(entity);
		getSession().merge(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.bpm.common.core.dao.IDao#executeUpdate(java.lang.String,
	 *      java.lang.Object)
	 */
	public int executeUpdate(final String ql, final Object... values) {
		String hql = this.getHql(ql);
		Query query = createQuery(hql, values);
		return query.executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.bpm.common.core.dao.IDao#createQuery(java.lang.String,
	 *      java.lang.Object)
	 */
	public Query createQuery(final String hql, final Object... values) {
		Assert.hasText(hql);
		Query query = getSession().createQuery(hql);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.bpm.common.core.dao.IDao#createCriteria(org.hibernate.criterion.Criterion)
	 */
	public Criteria createCriteria(final Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.bpm.common.core.dao.IDao#createSQLQuery(java.lang.String)
	 */
	public Query createSQLQuery(final String ql) {
		String sql = this.getHql(ql);
		Query query = getSession().createSQLQuery(sql);
		return query;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.bpm.common.core.dao.IDao#createCriteria(java.lang.String,
	 *      boolean, org.hibernate.criterion.Criterion)
	 */
	public Criteria createCriteria(final String orderBy, final boolean isAsc,
			final Criterion... criterions) {
		Assert.hasText(orderBy);
		Criteria criteria = createCriteria(criterions);
		if (isAsc)
			criteria.addOrder(Order.asc(orderBy));
		else
			criteria.addOrder(Order.desc(orderBy));

		return criteria;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.bpm.common.core.dao.IDao#find(java.lang.String,
	 *      java.lang.Object)
	 */

	public List find(final String ql, final Object... values) {
		String hql = this.getHql(ql);
		Assert.hasText(hql);
		return createQuery(hql, values).list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.bpm.common.core.dao.IDao#findCacheable(java.lang.String,
	 *      java.lang.Object)
	 */

	public List findCacheable(final String ql, final Object... values) {
		String hql = this.getHql(ql);
		Assert.hasText(hql);
		return createQuery(hql, values).setCacheable(true).list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.bpm.common.core.dao.IDao#findUnique(java.lang.String,
	 *      java.lang.Object)
	 */
	public T findUnique(final String ql, final Object... values) {

		try {
			String hql = this.getHql(ql);
			return (T) createQuery(hql, values).uniqueResult();
		} catch (HibernateException e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.bpm.common.core.dao.IDao#isUnique(T,
	 *      java.lang.String)
	 */
	public boolean isUnique(final T entity, final String uniquePropertyNames) {

		Assert.hasText(uniquePropertyNames);
		Criteria criteria = createCriteria();
		String[] nameList = uniquePropertyNames.split(",");
		try {
			// 循环加入唯一列
			for (String name : nameList) {
				criteria.add(Restrictions.eq(name, PropertyUtils.getProperty(
						entity, name)));
			}

			// 以下代码为了如果是update的情况,排除entity自身.

			String idName = getIdName();

			// 取得entity的主键值
			Serializable id = getId(entity);

			// 如果id!=null,说明对象已存在,该操作为update,加入排除自身的判断
			if (id != null)
				criteria.add(Restrictions.not(Restrictions.eq(idName, id)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (Integer) criteria.uniqueResult() == 0;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.bpm.common.core.dao.IDao#isPropertyUnique(java.lang.String,
	 *      java.lang.Object, java.lang.Object)
	 */
	public boolean isPropertyUnique(final String propertyName,
			final Object newValue, final Object orgValue) {
		if (newValue == null || newValue.equals(orgValue))
			return true;

		Object object = createCriteria(Restrictions.eq(propertyName, newValue))
				.uniqueResult();
		return (object == null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.bpm.common.core.dao.IDao#getIdName()
	 */
	public String getIdName() {
		ClassMetadata meta = sessionFactory.getClassMetadata(entityClass);
		String idName = meta.getIdentifierPropertyName();
		return idName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.bpm.common.core.dao.IDao#getId(T)
	 */
	public Serializable getId(final T entity) {
		Assert.notNull(entity);
		Serializable idValue = null;
		try {
			idValue = (Serializable) PropertyUtils.getProperty(entityClass,
					getIdName());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return idValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.bpm.common.core.dao.IDao#pagedQuery(com.ultrapower.bpm.common.core.support.PageLimit,
	 *      org.hibernate.criterion.Criterion)
	 */
	public List pagedQuery(final PageLimit pageLimit,
			final Criterion... criterions) {

		Assert.notNull(pageLimit.getEntity());

		int startIndex;
		int pageSize;
		int totalCount;

		pageSize = pageLimit.getPageSize();
		startIndex = Page.getStartOfPage(pageLimit.getCURRENT_ROWS_SIZE(),
				pageSize);

		Criteria c = createCriteria(criterions);

		c.add(Example.create(pageLimit.getEntity()));// 根据实体对象拼查询条件

		c.setFirstResult(startIndex);
		c.setMaxResults(pageSize);

		if (pageLimit.getSortColumn() != null) {
			if (pageLimit.getSortDesc().equals("asc")) {
				c.addOrder(Order.asc(pageLimit.getSortColumn()));
			} else {
				c.addOrder(Order.desc(pageLimit.getSortColumn()));
			}
		}

		totalCount = countQueryResult(c);

		pageLimit.getLimit().setRowAttributes(totalCount,
				pageLimit.getCURRENT_ROWS_SIZE());
		return c.list();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.bpm.common.core.dao.IDao#pagedQuery(java.lang.String,
	 *      com.ultrapower.bpm.common.core.support.PageLimit, java.lang.Object)
	 */
	public List pagedQuery(final String ql, final PageLimit pageLimit,
			final Object... values) {

		Page page = null;
		Query query = null;

		String hql = this.getHql(ql);

		if (pageLimit.getEntity() != null) {
			query = createQuery(GenericsUtils.queryAccession(hql, pageLimit),
					values);
			Iterator iterator = pageLimit.getValueMap().keySet().iterator();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				Object value = pageLimit.getValueMap().get(key);
				query.setParameter(key, value);
			}

		} else {
			query = createQuery(hql, values);

		}
		page = pagedQuery(query, pageLimit.getPageSize(), pageLimit
				.getCURRENT_ROWS_SIZE());

		if (pageLimit.getLimit() != null) {
			pageLimit.getLimit().setRowAttributes(
					new Long(page.getTotalCount()).intValue(),
					pageLimit.getCURRENT_ROWS_SIZE());
		}

		return (List) page.getResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.bpm.common.core.dao.IDao#pagedQuery(org.hibernate.Query,
	 *      int, int)
	 */

	protected Page pagedQuery(final Query query, final int pageNo,
			final int pageSize) {

		Assert.isTrue(pageNo >= 1, "pageNo should start from 1");

		// Count查询
		long totalCount = 0;
		int startIndex;

		query.getNamedParameters();

		ScrollableResults scrollableResults = query.scroll();
		scrollableResults.last();

		totalCount = scrollableResults.getRowNumber() + 1;

		startIndex = Page.getStartOfPage(pageNo, pageSize);

		List list = query.setFirstResult(startIndex).setMaxResults(pageSize)
				.list();

		return new Page(startIndex, totalCount, pageSize, list);
	}

	/**
	 * 对采用Criteria的查询对象自动计算合计
	 * 
	 * @param pageLimit
	 * @param criterions
	 * @return
	 */

	protected int countQueryResult(final Criteria c) {
		CriteriaImpl impl = (CriteriaImpl) c;

		// 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
		Projection projection = impl.getProjection();
		ResultTransformer transformer = impl.getResultTransformer();

		List<CriteriaImpl.OrderEntry> orderEntries = null;
		try {
			orderEntries = (List) BeanUtils.getFieldValue(impl, "orderEntries");
			BeanUtils.setFieldValue(impl, "orderEntries", new ArrayList());
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		// 执行Count查询
		int totalCount = (Integer) c.setProjection(Projections.rowCount())
				.uniqueResult();
		if (totalCount < 1)
			return -1;

		// 将之前的Projection和OrderBy条件重新设回去
		c.setProjection(projection);

		if (projection == null) {
			c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		if (transformer != null) {
			c.setResultTransformer(transformer);
		}

		try {
			BeanUtils.setFieldValue(impl, "orderEntries", orderEntries);
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return totalCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.bpm.common.core.dao.IDao#getAll()
	 */
	public List<T> getAll() {
		return this.createCriteria().list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.bpm.common.core.dao.IDao#flush()
	 */
	public void flush() {
		getSession().flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.bpm.common.core.dao.IDao#getListByIds(java.lang.String)
	 */
	public List<T> getListByIds(final String ids) {
		List<T> list = new ArrayList<T>();
		if (StringUtils.hasLength(ids)) {
			String[] idArray = ids.split(",");
			for (String id : idArray) {
				list.add(this.get(id));
			}
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ultrapower.bpm.common.core.dao.IDao#getIdsByList(java.util.List)
	 */
	public String getIdsByList(final List<T> list) {
		StringBuffer ids = new StringBuffer();
		for (T e : list) {
			ids.append(e);
			ids.append(",");
		}
		if (ids.toString().endsWith(","))
			return ids.subSequence(0, ids.length() - 1).toString();
		return ids.toString();
	}

	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	private String getHql(final String ql) {
		return ql;
	}

	public List findByProperty(Map<String, Object> map) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Iterator it = map.keySet().iterator(); it.hasNext();) {
			String name = (String) it.next();
			if (map.get(name) != null
					&& StringUtils.hasLength(map.get(name).toString())) {
				Criterion c = Restrictions.eq(name, map.get(name));
				criteria.add(c);
			}
		}
		return criteria.list();
	}

	public List findBy(String ql, Map<String, Object> map) {
		String hql = this.getHql(ql);
		Query query = this.createQuery(hql);
		for (Iterator it = map.keySet().iterator(); it.hasNext();) {
			String name = (String) it.next();
			query.setParameter(name, map.get(name));
		}
		return query.list();
	}

	public List Query(final String ql, final PageLimit pageLimit,
			final Object... values) {
		Query query = null;

		String hql = this.getHql(ql);

		if (pageLimit.getEntity() != null) {
			query = createQuery(GenericsUtils.queryAccession(hql, pageLimit),
					values);
			Iterator iterator = pageLimit.getValueMap().keySet().iterator();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				Object value = pageLimit.getValueMap().get(key);
				query.setParameter(key, value);
			}

		} else {
			query = createQuery(hql, values);

		}

		return query.list();
	}

	public boolean isIfFindHql() {
		return ifFindHql;
	}

	public void setIfFindHql(boolean ifFindHql) {
		this.ifFindHql = ifFindHql;
	}

	public Connection getConn() {
		return getSession().connection();
	}

	public void doExecute(String sql) {
		// TODO Auto-generated method stub

	}

	public Integer getInt(String sql, Object... values) {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getLong(String sql, Object... values) {
		List<Long> re = this.find(sql, values);
		if (re != null && re.size() > 0) {
			return re.get(0);
		} else {
			return 0L;
		}
	}

	public String getString(String sql, Object... values) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean sessionContains(Object obj) {
		return getSession().contains(obj);
	}

	public Session getHibernateSession() {
		return getSession();
	}
	
	public void refresh(Object entity) {
		flush();
		this.getSession().refresh(entity,LockMode.UPGRADE);
		//此处一定要用lockMode.UPGRADE模式进行锁定刷新
	}

	public List<Map<String, Object>> findByPage(String sql,
			String queryCountsSQL, List<String> attrAlias) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map getMap(String sql, Object... values) {
		// TODO Auto-generated method stub
		return null;
	}

}
