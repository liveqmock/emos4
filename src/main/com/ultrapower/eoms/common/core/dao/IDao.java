package com.ultrapower.eoms.common.core.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.ultrapower.eoms.common.core.support.PageLimit;

public interface IDao<T> {

	public abstract T get(final Serializable id);

	public abstract void remove(final T entity);

	public abstract void removeById(final Serializable id);

	public abstract void save(final T entity);

	public abstract void saveOrUpdate(final T entity);

	public abstract void merge(final T entity);

	public abstract Session getHibernateSession();

	/**
	 * 
	 * @param 对象批量更新或批量删除
	 * @param values
	 * @return
	 */
	public abstract int executeUpdate(final String ql, final Object... values);

	public abstract List find(final String ql, final Object... values);

	public abstract List findByProperty(final Map<String, Object> map);

	public abstract List findBy(final String ql, Map<String, Object> map);

	/**
	 * 查询，并把结果保存在二级缓存
	 * 
	 * @param hql
	 * @param values
	 * @return
	 */

	public abstract List findCacheable(final String ql, final Object... values);

	public abstract boolean sessionContains(Object obj);

	/**
	 * 根据属性名和属性值查询单个对象.
	 * 
	 * @return 符合条件的唯一对象 or null if not found
	 */
	public abstract T findUnique(final String ql, final Object... values);

	/**
	 * 判断对象的属性值在数据库内是否唯一.
	 * 
	 */
	public abstract boolean isUnique(final T entity,
			final String uniquePropertyNames);

	/**
	 * 判断对象的属性值在数据库内是否唯一.
	 * 
	 * 在修改对象的情景下,如果属性新修改的值(value)等于属性原值(orgValue)则不作比较.
	 */
	public abstract boolean isPropertyUnique(final String propertyName,
			final Object newValue, final Object orgValue);

	/**
	 * 取得对象的主键名,辅助函数.
	 */
	public abstract String getIdName();

	/**
	 * 取得对象的主键值,辅助函数.
	 */
	public abstract Serializable getId(final T entity);

	/**
	 * 分页查询 根据QL
	 * 
	 * @param pageLimit
	 * @param criterions
	 * @return
	 */
	public abstract List pagedQuery(final String ql, final PageLimit pageLimit,
			final Object... values);

	/**
	 * 获取全部对象
	 * 
	 * @return 符合条件的对象列表
	 */
	public abstract List<T> getAll();

	/**
	 * 获取集合从主键
	 * 
	 * @return
	 */
	public abstract List<T> getListByIds(final String ids);

	/**
	 * 获取主串从集合
	 * 
	 * @return
	 */
	public abstract String getIdsByList(final List<T> list);

	/**
	 * 动态查询
	 * 
	 * @param ql
	 * @param pageLimit
	 * @param values
	 * @return
	 */
	public List Query(final String ql, final PageLimit pageLimit,
			final Object... values);

	/**
	 * 获取数据库连接， TODO 仅提供jdbc方式的实现
	 * 
	 * @return
	 */
	public Connection getConn();

	/**
	 * 直接运行sql语句，TODO 仅提供jdbc方式的实现,并且可以只ddl语句操作，供配置管理中建模使用
	 * 
	 * @param sql
	 */
	public void doExecute(String sql);

	/**
	 * 获取查询结果的整型返回值, 支持参数绑定， TODO 仅提供jdbc方式的实现
	 * 
	 * @deprecated
	 * @param sql
	 * @param values
	 * @return
	 */
	@Deprecated
	public Integer getInt(String sql, Object... values);

	/**
	 * 获取查询结果的字符串返回值, 支持参数绑定， TODO 仅提供jdbc方式的实现
	 * 
	 * @deprecated
	 * @param sql
	 * @param values
	 * @return
	 */
	@Deprecated
	public String getString(String sql, Object... values);

	/**
	 * 获取查询结果的长整型返回值, 支持参数绑定， TODO 仅提供jdbc方式的实现
	 * 
	 * @deprecated
	 * @param sql
	 * @param values
	 * @return
	 */
	@Deprecated
	public Long getLong(String sql, Object... values);

	/**
	 * 创建Query对象.
	 * 对于需要first,max,fetchsize,cache,cacheRegion等诸多设置的函数,可以在返回Query后自行设置.
	 * 留意可以连续设置,如下：
	 * 
	 * <pre>
	 * dao.getQuery(hql).setMaxResult(100).setCacheable(true).list();
	 * </pre>
	 * 
	 * 调用方式如下：
	 * 
	 * <pre>
	 *        dao.createQuery(hql)
	 *        dao.createQuery(hql,arg0);
	 *        dao.createQuery(hql,arg0,arg1);
	 *        dao.createQuery(hql,new Object[arg0,arg1,arg2])
	 * </pre>
	 * 
	 * @param values
	 *            可变参数.
	 */
	abstract Query createQuery(String hql, Object... values);

	abstract Query createSQLQuery(String sql);

	public void refresh(Object entity);

	public List<Map<String, Object>> findByPage(String sql,
			String queryCountsSQL, List<String> attrAlias);

	/**
	 * 查询数据库，返回map对象
	 * 
	 * @param sql
	 * @param values
	 *            可变参数
	 * @return
	 */
	public Map getMap(String sql, Object... values);
}