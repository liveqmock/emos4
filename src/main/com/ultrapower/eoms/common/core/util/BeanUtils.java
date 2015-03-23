package com.ultrapower.eoms.common.core.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

/**
 * 扩展 Apache Commons BeanUtils, 提供一些反射方面缺失功能的封装.
 */
@SuppressWarnings("unchecked")
public class BeanUtils extends org.apache.commons.beanutils.BeanUtils {

	protected static final Logger log = LoggerFactory.getLogger(BeanUtils.class);

	private BeanUtils() {

	}

	/**
	 * 直接读取对象属性值,无视private/protected修饰符,不经过getter函数. fieldName中包含分割符.
	 */
	public static Object getCommaFieldValue(final Object object,
			final String fieldName) {
		Object result = object;
		String[] columns = fieldName.split("\\.");
		for (String column : columns) {
			if (result == null)
				return null;
			result = getFieldValue(result, column);
		}
		return result;
	}

	/**
	 * 直接读取对象属性值,无视private/protected修饰符,不经过getter函数.
	 */
	public static Object getFieldValue(final Object object,
			final String fieldName) {
		Object result = null;
		try {
			Field field = getDeclaredField(object, fieldName);
			if (!field.isAccessible()) {
				field.setAccessible(true);
			}

			result = field.get(object);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 直接设置对象属性值,无视private/protected修饰符,不经过setter函数.
	 */
	public static void setFieldValue(final Object object,
			final String fieldName, final Object value)
			throws NoSuchFieldException {
		Field field = getDeclaredField(object, fieldName);
		if (!field.isAccessible()) {
			field.setAccessible(true);
		}
		try {
			field.set(object, value);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 循环向上转型,获取对象的DeclaredField.
	 * 
	 * @throws NoSuchFieldException
	 *             如果没有该Field时抛出.
	 */
	public static Field getDeclaredField(Object object, String propertyName)
			throws NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);
		return getDeclaredField(object.getClass(), propertyName);
	}

	/**
	 * 循环向上转型,获取对象的DeclaredField.
	 * 
	 * @throws NoSuchFieldException
	 *             如果没有该Field时抛出.
	 */
	public static Field getDeclaredField(Class clazz, String propertyName)
			throws NoSuchFieldException {
		Assert.notNull(clazz);
		Assert.hasText(propertyName);
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredField(propertyName);
			} catch (NoSuchFieldException e) {
				// Field不在当前类定义,继续向上转型
			}
		}
		throw new NoSuchFieldException("No such field: " + clazz.getName()
				+ '.' + propertyName);
	}

	/**
	 * 暴力调用对象函数,忽略private,protected修饰符的限制.
	 * 
	 * @throws NoSuchMethodException
	 *             如果没有该Method时抛出.
	 */
	public static Object invokePrivateMethod(Object object, String methodName,
			Object... params) throws NoSuchMethodException {
		Assert.notNull(object);
		Assert.hasText(methodName);
		Class[] types = new Class[params.length];
		for (int i = 0; i < params.length; i++) {
			types[i] = params[i].getClass();
		}

		Class clazz = object.getClass();
		Method method = null;
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				method = superClass.getDeclaredMethod(methodName, types);
				break;
			} catch (NoSuchMethodException e) {
				// 方法不在当前类定义,继续向上转型
			}
		}

		if (method == null)
			throw new NoSuchMethodException("No Such Method:"
					+ clazz.getSimpleName() + methodName);

		boolean accessible = method.isAccessible();
		method.setAccessible(true);
		Object result = null;
		try {
			result = method.invoke(object, params);
		} catch (Exception e) {
			ReflectionUtils.handleReflectionException(e);
		}
		method.setAccessible(accessible);
		return result;
	}

	/**
	 * 按Filed的类型取得Field列表.
	 */
	public static List<Field> getFieldsByType(Object object, Class type) {
		List<Field> list = new ArrayList<Field>();
		Field[] fields = object.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (field.getType().isAssignableFrom(type)) {
				list.add(field);
			}
		}
		return list;
	}

	/**
	 * 按FiledName获得Field的类型.
	 */
	public static Class getPropertyType(Class type, String name)
			throws NoSuchFieldException {
		return getDeclaredField(type, name).getType();
	}

	/**
	 * 获得field的getter函数名称.
	 */
	public static String getGetterName(Class type, String fieldName) {
		Assert.notNull(type, "Type required");
		Assert.hasText(fieldName, "FieldName required");

		if (type.getName().equals("boolean")) {
			return "is" + StringUtils.capitalize(fieldName);
		} else {
			return "get" + StringUtils.capitalize(fieldName);
		}
	}

	/**
	 * 获得field的getter函数,如果找不到该方法,返回null.
	 */
	public static Method getGetterMethod(Class type, String fieldName) {
		try {
			return type.getMethod(getGetterName(type, fieldName));
		} catch (NoSuchMethodException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 基于反射,实现bean属性拷贝
	 */
	public static void copyPropertiesByModel(Object dest, Object orig) {
		try {
			copyProperties(dest, orig);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * Map和Map数据拷贝
	 */
	public static void copyMap(Map dest, Map orig) {
		try {
			for (Iterator names = orig.keySet().iterator(); names.hasNext();) {
				String name = (String) names.next();
				if (name != null) {
					Object value = orig.get(name);
					dest.put(name, value);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public static Map beanToMap(Object orig) {
		Map result = new HashMap();
		try {
			result = describe(orig);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Map beanToMap(Map dest, Object orig) {
		try {
			Map origMap = describe(orig);
			copyMap(dest, origMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dest;
	}

	public static void mapToBean(Object dest, Map orig) {
		try {
			populate(dest, orig);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 循环向上转型,获取对象的DeclaredField.
	 * 
	 */
	public static Field[] getDeclaredFields(Class clazz) {
		Assert.notNull(clazz);
		Field[] field = new Field[] {};
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			field = superClass.getDeclaredFields();
			if (field.length != 0)
				break;
		}
		return field;
	}

	/**
	 * 通过反射获得静态变量的值，返回Object对象
	 * 
	 * @param expstr
	 * @return
	 */
	public static Object getStaticProperty(String expstr) {
		try {
			if (expstr.startsWith("%{@") && expstr.endsWith("}")
					&& expstr.length() > 4) {
				expstr = expstr.substring(3, expstr.length() - 1);
				String[] classobj = expstr.split("@");
				Class ownerClass = null;
				if (classobj != null && classobj.length > 0) {
					ownerClass = Class.forName(classobj[0]);
				}

				Field field = null;

				if (ownerClass != null && classobj != null
						&& classobj.length > 0) {
					field = ownerClass.getField(classobj[1]);
				}
				Object property = null;
				if (field != null) {
					property = field.get(ownerClass);
				}
				return property;
			} else {
				return null;
			}
		} catch (Exception e) {
			log.error("通过反射获得静态变量错误。" + e.getCause());
			return null;
		}
	}

	/**
	 * 反射类的构造方法，如果找不到构造方法，返回null
	 * 
	 * @param classobj
	 * @param parameterTypes
	 * @return
	 */

	public static Constructor getConstructor(Class classobj,
			Class... parameterTypes) {
		Constructor c = null;
		try {
			c = classobj.getConstructor(parameterTypes);
		} catch (Exception e) {
			c = null;
		}
		return c;

	}

	/**
	 * 去掉集合里面的重复对象
	 * @param <T>
	 * @param src
	 * @return
	 */
	public static <T> List<T> noDuplicate(List<T> src) {
		List<T> rtn = new ArrayList<T>();
		if (src != null) {
			Set<T> set = new HashSet<T>();
			set.addAll(src);
			rtn.addAll(set);
		}
		return rtn;
	}
}
