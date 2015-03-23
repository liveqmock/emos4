package com.ultrapower.eoms.common.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 数组转换
 * @author 朱朝辉(zhuzhaohui@ultrapower.com.cn)
 * @version Apr 22, 2010 11:09:17 AM
 */
public class ArrayTransferUtils {
	
	/**
	 * 合并六个List 合并(取出重复值)
	 * @param p_list1
	 * @param p_list2
	 * @param p_list3
	 * @param p_list4
	 * @param p_list5
	 * @param p_list6
	 * @return
	 * @throws Exception
	 */
	public static List copyListNoteTheSame(List p_list1,List p_list2,List p_list3,List p_list4,List p_list5,List p_list6) throws Exception{
		List list = new ArrayList();
		Set set = new HashSet();//set不包含重复元素collection
		if(p_list1 != null)
			set.addAll(p_list1);
		if(p_list2 != null)
			set.addAll(p_list2);
		if(p_list3 != null)
			set.addAll(p_list3);
		if(p_list4 != null)
			set.addAll(p_list4);
		if(p_list5 != null)
			set.addAll(p_list5);
		if(p_list6 != null)
			set.addAll(p_list6);
		for (Iterator it = set.iterator(); it.hasNext();)
			list.add(it.next());
		return list;
	}
	
	/**
	 * 合并四个List 合并(取出重复值)
	 * @param list1
	 * @param list2
	 * @param list3
	 * @param list4
	 * @return
	 * @throws Exception
	 */
	public static List copyListNoteTheSame(List p_list1,List p_list2,List p_list3,List p_list4) throws Exception{
		List list = new ArrayList();
		Set set = new HashSet();//set不包含重复元素collection
		if(p_list1 != null)
			set.addAll(p_list1);
		if(p_list2 != null)
			set.addAll(p_list2);
		if(p_list3 != null)
			set.addAll(p_list3);
		if(p_list4 != null)
			set.addAll(p_list4);
		for (Iterator it = set.iterator(); it.hasNext();)
			list.add(it.next());
		return list;
	}
	
	/**
	 * 合并五个List 合并(取出重复值)
	 * @param list1
	 * @param list2
	 * @param list3
	 * @param list4
	 * @return
	 * @throws Exception
	 */
	public static List copyListNoteTheSame(List p_list1,List p_list2,List p_list3,List p_list4,List p_list5) throws Exception{
		List list = new ArrayList();
		Set set = new HashSet();//set不包含重复元素collection
		if(p_list1 != null)
			set.addAll(p_list1);
		if(p_list2 != null)
			set.addAll(p_list2);
		if(p_list3 != null)
			set.addAll(p_list3);
		if(p_list4 != null)
			set.addAll(p_list4);
		if(p_list5 != null)
			set.addAll(p_list5);
		for (Iterator it = set.iterator(); it.hasNext();)
			list.add(it.next());
		return list;
	}
	
	/**
	 * 合并两个List 合并(取出重复值)
	 * @param list1
	 * @param list2
	 * @return List 返回新的List
	 */
	public static List copyListNoteTheSame(List list1, List list2) throws Exception{
		List list = new ArrayList();
		Set set = new HashSet();//set不包含重复元素collection
		if(list1 != null)
			set.addAll(list1);
		if(list2 != null)
			set.addAll(list2);
		for (Iterator it = set.iterator(); it.hasNext();)
			list.add(it.next());
		return list;
	}
	
	/**
	 * 合并两个List 简单合并
	 * @param list1 第一个List
	 * @param list2 第二个List
	 * @return list 返回合并的List
	 */
	public static List copyListSimple(List list1, List list2) throws Exception{
		List list = new ArrayList();
		if(list1 != null)
			list = list1;
		if(list2 != null)
			list.addAll(list2);
		return list;
	}
	
	/**
	 * 合并数组 随机合并(不进行排序、不去除重复)
	 * @param obj1 第一个数组对象
	 * @param obj2 第二个数组对象
	 * @return Object[] 合并后的新数组
	 */
	public static Object[] copyArraySimple(Object[] obj1, Object[] obj2) throws Exception{
		int len1 = 0;
		int len2 = 0;
		if (obj1 != null)
			len1 = obj1.length;
		if (obj2 != null)
			len2 = obj2.length;
		Object[] hb = new Object[len1 + len2];
		try {
			if (len1 > 0)
				System.arraycopy(obj1, 0, hb, 0, len1);
			if (len2 > 0)
				System.arraycopy(obj2, 0, hb, len1, len2);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return hb;
	}
	
	/**
	 * 数组合并 随机合并(不进行排序、不去除重复)
	 * @param obj1 第一个数组对象
	 * @param obj2 第二个数组对象
	 * @return Object[] 合并后的新数组
	 * @exception ArrayIndexOutOfBoundsException
	 */
	/*
	public static Object[] copyArrayRandom(Object[] obj1, Object[] obj2) throws Exception{
		int len1 = 0;
		int len2 = 0;
		if(obj1 != null)
			len1 = obj1.length;
		if(obj2 != null)
			len2 = obj2.length;
		Object[] hb = new Object[len1 + len2];
		for (int i = 0; i < len1; i++)
			hb[i] = obj1[i];
		for (int i = 0; i < len2; i++)
			hb[len1 + i] = obj2[i];
		return hb;
	}
	 */
	
	/**
	 * 静态数组 两个数组合并，保留相同的元素(排序)
	 * @param obj1 第一个数组对象
	 * @param obj2 第二个数组对象
	 * @return 合并后的新数组
	 * @exception ArrayIndexOutOfBoundsException
	 */
	public static Object[] copyStaticArrayIsTheSame(Object[] obj1, Object[] obj2)
			throws Exception {
		int len1 = 0;
		int len2 = 0;
		if(obj1 != null)
			len1 = obj1.length;
		if(obj2 != null)
			len2 = obj2.length;
		Object[] hb = new Object[len1 + len2];
		for (int i = 0; i < len1; i++)
			hb[i] = obj1[i];
		for (int i = 0; i < len2; i++)
			hb[len1 + i] = obj2[i];
		Arrays.sort(hb);//排序
		return hb;
	}
	
	
	
}
