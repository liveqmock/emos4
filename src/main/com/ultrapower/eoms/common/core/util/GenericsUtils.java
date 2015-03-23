package com.ultrapower.eoms.common.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.ultrapower.eoms.common.core.support.PageLimit;

/**
 * Generics的util类.
 * 
 * @author andy
 */
public class GenericsUtils {

	private static final Logger log = LoggerFactory.getLogger(GenericsUtils.class);

	private GenericsUtils() {
	}

	/**
	 * 通过反射,获得定义Class时声明的父类的范型参数的类型. 如public templateManager extends
	 * GenricManager<Book>
	 * 
	 * @param clazz
	 *            The class to introspect
	 * @return the first generic declaration, or <code>Object.class</code> if
	 *         cannot be determined
	 */
	public static Class getSuperClassGenricType(Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	/**
	 * 通过反射,获得定义Class时声明的父类的范型参数的类型. 如public BookManager extends GenricManager<Book>
	 * 
	 * @param clazz
	 *            clazz The class to introspect
	 * @param index
	 *            the Index of the generic ddeclaration,start from 0.
	 * @return the index generic declaration, or <code>Object.class</code> if
	 *         cannot be determined
	 */
	public static Class getSuperClassGenricType(Class clazz, int index) {

		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			// log.warn(clazz.getSimpleName()
			// + "'s superclass not ParameterizedType");
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			log.warn("Index: " + index + ", Size of " + clazz.getSimpleName()
					+ "'s Parameterized Type: " + params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			log
					.warn(clazz.getSimpleName()
							+ " not set the actual class on superclass generic parameter");
			return Object.class;
		}
		return (Class) params[index];
	}

	/**
	 * 去除hql的select 子句，未考虑union的情况,用于pagedQuery.
	 * 
	 * @see #pagedQuery(String,int,int,Object[])
	 */
	public static String removeSelect(String hql) {
		Assert.hasText(hql);
		int beginPos = hql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos != -1, " hql : " + hql
				+ " must has a keyword 'from'");
		return hql.substring(beginPos);
	}

	/**
	 * 获取hql中的order by 子句
	 * 
	 * @param hql
	 * @return
	 */
	public static String ordersfromHql(String hql) {

		Assert.hasText(hql);
		int beginPos = hql.toLowerCase().indexOf("order by");

		if (beginPos > 0)
			return " " + hql.substring(beginPos);
		return "";

	}

	public static String changeComma(String comma) {
		if (StringUtils.hasLength(comma)) {
			return comma.replaceAll(",", "','");
		}
		return "";
	}

	/**
	 * 删除字符串的前后逗号
	 * 
	 * @param string
	 * @return
	 */
	public static String trimComma(String string) {
		try {
			if (string != null && string.trim().length() > 0) {
				if (string.lastIndexOf(",") == string.length() - 1) {
					string = string.substring(0, string.length() - 1);
				}
				if (string.indexOf(",") == 0) {
					string = string.substring(1, string.length());
				}
			} else {
				string = "";
			}
		} catch (Exception e) {
		}
		return string;
	}

	/**
	 * 根据正则截取HQL
	 * 
	 * @param hql
	 * @param regular
	 * @return
	 */
	public static String removeInHql(String hql, String regular) {
		Assert.hasText(hql);
		Pattern p = Pattern.compile(regular, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 去除hql的orderby 子句，用于pagedQuery.
	 * 
	 * @see #pagedQuery(String,int,int,Object[])
	 */
	public static String removeOrders(String hql) {
		return removeInHql(hql, "order\\s*by[\\w|\\W|\\s|\\S]*");
	}

	/**
	 * 去除hql的where子句，用于pagedQuery
	 * 
	 * @param hql
	 * @return
	 */
	public static String removeWheres(String hql) {
		return removeInHql(hql, "where[\\w|\\W|\\s|\\S]*");
	}

	/**
	 * 通过实体域对象
	 * 
	 * @param hql
	 * @param entity
	 * @param assembleColumn
	 * @return
	 */
	public static String queryAccession(String hql, PageLimit pageLimit) {
		Assert.notNull(pageLimit.getEntity());

		Object entity = pageLimit.getEntity();
		String result;

		Map<String, String> aliasMap = pageLimit.getAliasMap();
		Map<String, String> operMap = pageLimit.getOperMap();
		Map<String, String> actualMap = pageLimit.getActualMap();

		StringBuffer buffer = new StringBuffer();
		Iterator iterator = aliasMap.keySet().iterator();
		while (iterator.hasNext()) {
			try {
				String column = (String) iterator.next();
				Object fieldValue = null;
				if (entity instanceof Map)
					fieldValue = ((Map)entity).get(column);
				else
					fieldValue = BeanUtils.getCommaFieldValue(entity, column);

				if (fieldValue != null && !fieldValue.equals("")) {// 如果变量值非空

					String oper = " = ";

					String fieldColumn = actualMap.get(column) == null ? aliasMap
							.get(column)
							: actualMap.get(column);

					oper = operMap.get(column) == null ? oper : operMap
							.get(column);

					buffer.append(" and ");
					if (oper.toLowerCase().equals("like")) {
						buffer.append(fieldColumn + " " + oper + "'%'||:"
								+ column + "||'%'");
					} else {
						buffer.append(fieldColumn + oper + ":" + column.replace(".", ""));
					}

					pageLimit.setColumnValue(column.replace(".", ""), fieldValue);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		result = removeOrders(hql)
				+ ((hql.indexOf("where") > 0 || hql.indexOf("WHERE") > 0) ? buffer
						.toString()
						: buffer.toString().replaceFirst("and", "where"))
				+ pageLimit.getSortAndDesc(ordersfromHql(hql));
		log.debug(result);
		return result;

	}


	public static String encodePassword(String password) {

		byte[] unencodedPassword = password.getBytes();

		MessageDigest md = null;

		try {
			// first create an instance, given the provider
			md = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			log.error("Exception: " + e);

			return password;
		}

		md.reset();

		// call the update method one or more times
		// (useful when you don't know the size of your data, eg. stream)
		md.update(unencodedPassword);

		// now calculate the hash
		byte[] encodedPassword = md.digest();

		StringBuffer buf = new StringBuffer();

		for (byte anEncodedPassword : encodedPassword) {
			if ((anEncodedPassword & 0xff) < 0x10) {
				buf.append("0");
			}

			buf.append(Long.toString(anEncodedPassword & 0xff, 16));
		}

		return buf.toString();
	}


	/**
	 * Encode a string using Base64 encoding. Used when storing passwords as
	 * cookies.
	 * 
	 * This is weak encoding in that anyone can use the decodeString routine to
	 * reverse the encoding.
	 * 
	 * @param str
	 *            the string to encode
	 * @return the encoded string
	 */
	public static String encodeString(String str) {
		Base64 encoder = new Base64();
		return String.valueOf(encoder.encode(str.getBytes())).trim();
	}

	/**
	 * Decode a string using Base64 encoding.
	 * 
	 * @param str
	 *            the string to decode
	 * @return the decoded string
	 */
	public static String decodeString(String str) {
		Base64 dec = new Base64();
		try {
			return String.valueOf(dec.decode(str));
		} catch (DecoderException de) {
			throw new RuntimeException(de.getMessage(), de.getCause());
		}
	}


	/**
	 * @author chenliang 将通用对象s转换为long类型，如果字符穿为空或null，返回r；
	 * @param s
	 * @param r
	 * @return
	 */
	public static long Obj2long(Object s, long r) {
		long i = r;

		String str = "";
		try {
			str = s.toString();
			i = Long.parseLong(str);
		} catch (Exception e) {
			i = r;
		}
		return i;
	}

	/**
	 * @author chenliang 将通用对象s转换为String类型，如果字符穿为空或null，返回r；
	 * @param s
	 * @param r
	 * @return
	 */
	public static String Obj2Str(Object s, String r) {
		String str = r;
		try {
			str = s.toString();
			if (str.equals("null") || str == null || str.trim().length() == 0) {
				str = r;
			}
		} catch (Exception e) {
			str = r;
		}
		return str;
	}

	/**
	 * 截取字符用？标识返回
	 * 
	 * @return
	 */
	public static String getQueryNum(String ids) {
		StringBuffer flag = new StringBuffer();
		String[] idArray = ids.split(",");
		for (String id : idArray) {
			if (flag.toString().equals(""))
				flag.append("?");
			else
				flag.append(",?");
		}
		return flag.toString();
	}

	/**
	 * 根据对象属性拼装SQL
	 * 
	 * @param entity
	 * @return
	 */
	public static String queryAccession(Object entity) {

		StringBuffer buffer = new StringBuffer();
		Field[] fields = BeanUtils.getDeclaredFields(entity.getClass());
		for (Field field : fields) {
			try {
				Object fieldValue = BeanUtils.getFieldValue(entity, field
						.getName());
				if (fieldValue != null && !fieldValue.equals("")) {
					String fieldColumn = field.getName();
					String oper = (fieldValue instanceof String && String
							.valueOf(fieldValue).contains("%")) ? " like "
							: " = ";
					if (buffer.length() == 0) {
						buffer.append("from " + entity.getClass().getName());
						buffer.append(" where ");
						buffer.append(fieldColumn + oper + ":"
								+ field.getName());
					} else {
						buffer.append(" and ");
						buffer.append(fieldColumn + oper + " :" + fieldColumn);

					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return buffer.toString();
	}

	public static List<String> getHavingId(String allId, String compareId,
			boolean flag) {
		List<String> list = new ArrayList<String>();
		Map map = new HashMap();
		String[] allIdArray = allId.split(",");
		String[] compareIdArray = compareId.split(",");
		for (String allid : allIdArray) {
			map.put(allid, allid);
		}
		if (flag) {
			for (String compareid : compareIdArray) {
				if (map.get(compareid) != null)
					list.add(compareid);
			}
		} else {
			for (String compareid : compareIdArray) {
				if (map.get(compareid) == null)
					list.add(compareid);
			}
		}
		return list;
	}

	public static String arrayFromMap(Map map) {
		String[] array = new String[map.size()];
		Iterator iterator = map.keySet().iterator();
		for (int i = 0; i < map.size(); i++) {
			String key = (String) iterator.next();
			String value = toString((String[]) map.get(key), ",");
			array[i] = key + "=" + value;
		}
		return toString(array, ",");
	}
	

	public static String toString(String[] array, String spliteFlag) {
		StringBuffer buffer = new StringBuffer();
		for (String a : array) {
			if (buffer.length() == 0)
				buffer.append(a);
			else {
				buffer.append(spliteFlag);
				buffer.append(a);
			}
		}
		return buffer.toString();
	}

}
